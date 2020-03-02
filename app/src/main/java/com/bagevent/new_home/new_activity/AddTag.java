package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class AddTag extends BaseActivity {

    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightTitle;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.et_tag_name)
    EditText etTagName;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;

    private String userId;
    private int eventId;
    private int tagId;
    private int exhibitorId;
    private String name;

    @OnClick({R.id.ll_title_back,R.id.ll_right_click,R.id.iv_delete})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_title_back:
                finish();
                break;
            case R.id.ll_right_click:
                if (!TextUtils.isEmpty(etTagName.getText().toString().trim())){
                    AddTagToServer();
                    finish();
                }else {
                    Toast.makeText(this, R.string.et_hint, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_delete:
                etTagName.setText("");
        }
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_tag);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
    }


    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.add_tag);
        tvRightTitle.setText(R.string.complete1);
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        userId=SharedPreferencesUtil.get(this,"userId","");
        eventId=intent.getIntExtra("eventId",0);
        exhibitorId=intent.getIntExtra("exhibitorId",0);
    }

    private void AddTagToServer() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.ADD_OR_UPDATETAG)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",tagId==0?"":tagId+"")
                .addParams("tagName",etTagName.getText().toString())
                .build()
                .writeTimeOut(5000)
                .connTimeOut(5000)
                .readTimeOut(5000)
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        return result;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AddTag.this, R.string.error_add_tag, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                        } else {
                           Toast.makeText(AddTag.this, R.string.error_add_tag, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
