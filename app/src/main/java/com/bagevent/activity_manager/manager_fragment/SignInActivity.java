package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.adapter.SignInfoAdapter;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.new_home.adapter.SelectBindTagsAdapter;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_activity.CollectorManagerActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_sign_info)
    RecyclerView rvSignInfo;
    @OnClick({R.id.ll_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_title_back:
                saveInfo();
                finish();
                break;
        }
    }

    private int eventId=-1;
    private int exType=-1;
    private List<FormType.RespObjectBean> mDetail = new ArrayList<FormType.RespObjectBean>();
    private ArrayList<String> filedName=new ArrayList<>();
    private SignInfoAdapter signInfoAdapter;
//    private ArrayList<String> pos=new ArrayList<>();
    private ArrayList<String> selectName=new ArrayList<>();
    private ArrayList<String> selectInfo=new ArrayList<>();

    private SharedPreferences sharedPreferences;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//        ButterKnife.bind(this);
//        initView();
//        getIntentValue();
//        getSearch();
//        initData();
//        initAdapter();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        getSearch();
        initData();
        initAdapter();
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        exType=intent.getIntExtra("exType",0);
    }

    public ArrayList<String> getSearch() {
        sharedPreferences = getSharedPreferences("SignList", MODE_PRIVATE);
        int infoNum=sharedPreferences.getInt(String.valueOf(eventId),0);
        for (int i = 0; i < infoNum ; i++) {
            String selectPos=sharedPreferences.getString(String.valueOf(eventId)+i,null);
            String selectText=sharedPreferences.getString(String.valueOf(eventId)+i+"name",null);
            if (selectPos!=null){
                selectInfo.add(selectPos);
                selectName.add(selectText);
            }
        }
        return selectInfo;
    }

    private void initView() {
        tvTitle.setText(R.string.checkin_set);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        sharedPreferences=getBaseContext().getSharedPreferences("SignList",Context.MODE_PRIVATE);
    }

    private void initData() {

        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
        if (formType != null) {
            FormType mType = new Gson().fromJson(formType, FormType.class);//解析表单数据
            int size = mType.getRespObject().size();
            for (int i = 0; i < size ; i++) {
                if (!mType.getRespObject().get(i).getFieldName().equals("username")){
                    filedName.add(mType.getRespObject().get(i).getShowName());
                }
            }
        } else {
            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void initAdapter() {
        signInfoAdapter=new SignInfoAdapter(filedName,selectInfo);
        rvSignInfo.setAdapter(signInfoAdapter);
        signInfoAdapter.setListener(new SignInfoAdapter.OnItemClickListener() {
           @Override
           public void onClick(View view, int position) {
               if (selectInfo.contains(String.valueOf(position))){
                   selectInfo.remove(String.valueOf(position));
               } else {
                   if (selectInfo.size()==3){
                       Toast.makeText(SignInActivity.this, R.string.select_three, Toast.LENGTH_SHORT).show();
                   }else {
                       selectInfo.add(String.valueOf(position));
                   }
               }
               if (selectName.contains(filedName.get(position))){
                   selectName.remove(filedName.get(position));
               }else {
                   if (selectInfo.size()>=0&&selectInfo.size()<=3){
                       selectName.add(filedName.get(position));
                   }
               }
               signInfoAdapter.notifyDataSetChanged();
           }
       });
        rvSignInfo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void saveInfo() {
        SharedPreferences.Editor editor=getSharedPreferences("SignList",MODE_PRIVATE).edit();
        editor.putInt(String.valueOf(eventId),selectInfo.size());
        for (int i = 0; i < selectInfo.size(); i++) {
            editor.putString(String.valueOf(eventId)+i,selectInfo.get(i));
            editor.putString(String.valueOf(eventId)+i+"name",selectName.get(i));
        }
        editor.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
              saveInfo();
//            sharedPreferences = getSharedPreferences("SignList", MODE_PRIVATE);
//            SharedPreferences.Editor editor=getSharedPreferences("SignList",MODE_PRIVATE).edit();
//            editor.clear();
//            editor.commit();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
