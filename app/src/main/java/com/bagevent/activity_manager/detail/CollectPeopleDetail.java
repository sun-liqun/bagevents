package com.bagevent.activity_manager.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.adapter.DetailAdapter;
import com.bagevent.activity_manager.manager_fragment.data.FormData;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/7/20.
 */
public class CollectPeopleDetail extends Activity implements View.OnClickListener {

    private ListView lv_collect_people;
    private TextView tv_collect_detail;
    private AutoLinearLayout ll_collect_people_back;

    private int eventId = -1;
    private int attendId = -1;
    private int ticketId = -1;
    private String userName = "";
    private List<FormData> mDetail = new ArrayList<FormData>();
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.collect_people_detail);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white), Constants.STATUS_ALPHA);

        initView();
        initData();
        setListener();
    }

    private void initData() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        attendId = intent.getIntExtra("attendId", 0);
        ticketId = intent.getIntExtra("ticketId", 0);
        userName = intent.getStringExtra("userName");
        tv_collect_detail.setText(userName);
        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
        if (formType != null) {
            FormType mType = new Gson().fromJson(formType, FormType.class);//解析表单数据
            for (int i = 0; i < mType.getRespObject().size(); i++) {
                FormData data = new FormData();
                data.setFormName(mType.getRespObject().get(i).getShowName());
                data.setFormFieldId(mType.getRespObject().get(i).getFormFieldId());
                data.setFieldTypeName(mType.getRespObject().get(i).getFieldTypeName());
                mDetail.add(data);
            }
        //    adapter = new DetailAdapter(mDetail, this, eventId, attendId, ticketId, "");
       //     lv_collect_people.setAdapter(adapter);
        }

    }

    private void setListener() {
        ll_collect_people_back.setOnClickListener(this);
    }

    private void initView() {
        lv_collect_people = (ListView) findViewById(R.id.lv_collect_people);
        tv_collect_detail = (TextView) findViewById(R.id.tv_collect_detail);
        ll_collect_people_back = (AutoLinearLayout) findViewById(R.id.ll_collect_people_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect_people_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
