package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.new_interface.new_view.SaveEventTimeView;
import com.bagevent.new_home.new_interface.presenter.SaveEventTimePresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.pickerview.TimePickerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zwj on 2016/9/14.
 */
public class ReleaseEventTime extends BaseActivity implements View.OnClickListener,SaveEventTimeView {
    private AutoRelativeLayout eventStartTime;
    private AutoRelativeLayout eventEndTime;
    private AutoLinearLayout eventTimeBack;
    private AutoLinearLayout eventTimeConfirm;

    private TimePickerView pvTime;

    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tempTime;

    private String userId = "";
    private int eventId = -1;
    private SaveEventTimePresenter saveEventTimePresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_time);
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        initView();
//        setListener();
//        initTimeWidget();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_time);
        userId = SharedPreferencesUtil.get(this,"userId","");
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        initView();
        setListener();
        initTimeWidget();
    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void getEventTime() {
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        if(NetUtil.isConnected(this)) {
            if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                if(CompareRex.compareStartAndEndTime(startTime,endTime)) {
                    saveEventTimePresenter = new SaveEventTimePresenter(this);
                    saveEventTimePresenter.saveEventTime();
                }else {
                    setToast(getString(R.string.greater_then));
                }
            }else {
                setToast(getString(R.string.select_time));
            }
        }else {
            Toast toast = Toast.makeText(this,R.string.net_err,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(date);
    }

    private void initTimeWidget() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 20);
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tempTime.setText(getTime(date));
            }
        });
    }

    private void setListener() {
        eventStartTime.setOnClickListener(this);
        eventEndTime.setOnClickListener(this);
        eventTimeBack.setOnClickListener(this);
        eventTimeConfirm.setOnClickListener(this);
    }

    private void initView() {
        eventStartTime = (AutoRelativeLayout) findViewById(R.id.event_start_time);
        eventEndTime = (AutoRelativeLayout) findViewById(R.id.event_end_time);
        eventTimeBack = (AutoLinearLayout) findViewById(R.id.ll_event_time_back);
        eventTimeConfirm = (AutoLinearLayout) findViewById(R.id.ll_event_time_confirm);

        tvStartTime = (TextView) findViewById(R.id.tv_event_start_time);
        tvEndTime = (TextView) findViewById(R.id.tv_event_end_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_start_time:
                tempTime = tvStartTime;
                pvTime.show();
                break;
            case R.id.event_end_time:
                tempTime = tvEndTime;
                pvTime.show();
                break;
            case R.id.ll_event_time_confirm:
                getEventTime();
//                Toast.makeText(this,"提交时间",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_event_time_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String textName() {//表示保存的为时间
        return "time";
    }

    @Override
    public String startTime() {
        return tvStartTime.getText().toString();
    }

    @Override
    public String endTime() {
        return tvEndTime.getText().toString();
    }

    @Override
    public void showSaveTimeSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showSaveTimeFailed(String errInfo) {
        setToast(errInfo);
    }
}
