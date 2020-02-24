package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.dialog.LDialog;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.fragment.DynamicFragment;
import com.bagevent.util.AppManager;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class CreateInform extends BaseActivity {
    ArrayList<String> pos;
    ArrayList<Integer> eventId;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.btn_create)
    Button btn_create;
    @BindView(R.id.tv_select_inform)
    TextView tv_select_inform;

    private LDialog lDialog;
    private NormalAlertDialog backDialog;

    String strId;
    int etLength;
    String userId;

    @OnClick({R.id.rl_select_activity,R.id.btn_create,R.id.iv_back})
    public void click(View view){
        String etContext=et.getText().toString().trim();
        switch (view.getId()){
            case R.id.rl_select_activity:
                Intent intent=new Intent(CreateInform.this,SelectActivityOfInform.class);
                intent.putExtra(KeyUtil.KEY_SELECT_EVENTS,pos);
                intent.putExtra(KeyUtil.KEY_TRANSLATE_EVENTS,eventId);
                startActivity(intent);
                break;
            case R.id.btn_create:
                if (TextUtils.isEmpty(etContext) ||etLength<10){
                    TosUtil.show(getString(R.string.et_inform1));
                }else {
                    if (eventId!=null){
                        StringBuilder builder=new StringBuilder();
                        for (int i = 0; i < eventId.size() ; i++) {
                            if (builder.length()>0){
                                builder.append(",");
                            }
                            builder.append(eventId.get(i));
                        }
                        strId=builder.toString();
                        uploadNoticeToServer();
                        showLoading();
                    }else {
                        TosUtil.show(getString(R.string.select_activity));
                    }
                }
                break;
            case R.id.iv_back:
                if (!TextUtils.isEmpty(etContext)||eventId!=null){
                    showBackDialog();
                }else {
                    AppManager.getAppManager().finishActivity();
                }
                break;
        }

    }

    private void showBackDialog() {
         backDialog = new NormalAlertDialog.Builder(CreateInform.this)
                .setHeight(0.23f)
                .setWidth(0.65f)
                .setTitleVisible(false)
                .setContentText(getString(R.string.weather_give_up))
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        backDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        backDialog.dismiss();
                        finish();
                    }
                })
                .build();
        backDialog.show();
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.create_inform);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initEditText();
        initSelectActivity();
    }

    private void initSelectActivity() {
        eventId=new ArrayList<>();
        pos=new ArrayList<>();
        userId=SharedPreferencesUtil.get(getApplication(),"userId","");
        List<EventList> queryEventList = new Select().from(EventList.class)
                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
                        .or(EventList_Table.status.is(2))
                        .or(EventList_Table.status.is(4)))
                .and(EventList_Table.mark.is("event")).queryList();
        if (queryEventList.size()==1){
            eventId.add(queryEventList.get(0).eventId);
            tv_select_inform.setText(queryEventList.get(0).eventName);
        }
        if (queryEventList.size()>1){
            for (int i = 0; i < queryEventList.size() ; i++) {
                eventId.add(queryEventList.get(i).eventId);
                pos.add(i+"");
            }
            tv_select_inform.setText(queryEventList.get(0).eventName+getString(R.string.wait)+queryEventList.size()+getString(R.string.activity_num));
        }
    }

    private void initEditText() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etLength = s.length();
                tv_num.setText(etLength+"/500");
                if (etLength==500){
                    Toast.makeText(CreateInform.this, R.string.most_five_hundred, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event){
        if(event.getAction() == MessageAction.ACTION_SELECT_EVENT){
            eventId= (ArrayList<Integer>) event.getValue();
                if (eventId.size()==1){
                    EventList eventList= new Select().from(EventList.class)
                            .where(EventList_Table.eventId.in(eventId))
                            .querySingle();
                    tv_select_inform.setText(eventList.eventName);
                }
                if (eventId.size()>1){
                    EventList eventList= new Select().from(EventList.class)
                            .where(EventList_Table.eventId.in(eventId.get(0)))
                            .querySingle();
                    tv_select_inform.setText(eventList.eventName+" 等"+eventId.size()+"个活动");
                }
        }else if (event.getAction()==MessageAction.ACTION_SELECT_POSITION){
            pos= (ArrayList<String>) event.getValue();
        }else if (event.getAction()==MessageAction.ACTION_SELECT_EVENTS){
            eventId= (ArrayList<Integer>)event.getValue();
        }
    }

    private void showLoading() {
        if (lDialog == null) {
            lDialog = new LDialog.Builder(getFragmentManager())
                    .setLayoutId(R.layout.view_loading)
                    .setWidth(DxPxUtils.dip2px(this, 150))
                    .setHeight(DxPxUtils.dip2px(this, 150))
                    .build()
                    .show();
        } else {
            lDialog.show();
        }
    }

    private void hideLoading() {
        lDialog.dismiss();
    }

    private void uploadNoticeToServer() {
        OkHttpUtil.Post(getApplication())
                .url(Constants.NEW_URL + Constants.NOTICE_UPLOAD)
                .addParams("eventId", strId)
                .addParams("commentText", et.getText().toString().trim())
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoading();
                        TosUtil.show(getString(R.string.send_error));
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        if (response.contains("\"retStatus\":200")) {
                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                            finish();
                            TosUtil.show(getString(R.string.release_success));
                        } else {
                            TosUtil.show(getString(R.string.send_error));
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String etContext=et.getText().toString().trim();
            if (!TextUtils.isEmpty(etContext)||eventId!=null) {
                showBackDialog();
            }else {
                AppManager.getAppManager().finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
