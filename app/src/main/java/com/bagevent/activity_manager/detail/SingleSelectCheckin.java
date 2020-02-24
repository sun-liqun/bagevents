package com.bagevent.activity_manager.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.util.AppManager;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.DBUtil;
import com.githang.android.snippet.adapter.ChoiceListAdapter;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by zwj on 2016/7/20.
 */
public class SingleSelectCheckin extends Activity implements CheckInView, View.OnClickListener {
    private TextView tv_single_checkin;
    private ListView mListView;
    private AutoLinearLayout ll_single_back;

    private CheckInPresenter presenter;

    private int eventId = -1;
    private int attendId = -1;
    private int checkStatus = 1;
    private String ref_code = "";
    private String key = "";
    private String checkinTime = "";
    private static final String checkinStr_attendId = "barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.rb_listview);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        eventId = intent.getIntExtra("eventId", 0);
        //  Log.e("key-->",key);
        //   Log.e("eventId-->",eventId+"");
        initView();
        initData();


    }

    private void initData() {
        final List<Attends> data = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.cellphones.like("%" + key + "%")).and(Attends_Table.payStatuss.is(1))
                                    .and(OperatorGroup.clause().and(Attends_Table.audits.is(0)).or(Attends_Table.audits.is(2))).queryList();

        final ChoiceListAdapter adapter = new ChoiceListAdapter<Attends>(this, R.layout.rb_listview_item,
                data, R.id.checkedView) {
            @Override
            protected void holdView(ChoiceLayout view) {
                view.hold(R.id.tv_single_name);
                view.hold(R.id.tv_single_ticket);
                view.hold(R.id.checkedView);
            }


            @Override
            protected void bindData(ChoiceLayout view, int position, Attends data) {
                EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).and(EventTicket_Table.ticketIds.is(data.ticketIds)).querySingle();
                TextView text = view.get(R.id.tv_single_name);
                TextView text1 = view.get(R.id.tv_single_ticket);
                RadioButton rb = view.get(R.id.checkedView);

                if(data.buyingGroupId!=0){
                    if(data.bgState==1){
                        if (data.checkins == 1) {//判断是否签到,如果此人已签到，则将radiobutton改为不可选中,通过drawable中的selector完成更改
                            rb.setButtonDrawable(R.drawable.radiobtn_unselect);
                            text.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                            text1.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                        } else {
                            text.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.black));
                            text1.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.black));
                            rb.setButtonDrawable(R.drawable.radiobtn_select);
                        }
                    }else{
                        rb.setButtonDrawable(R.drawable.radiobtn_unselect);
                        text.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                        text1.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                    }
                }else{
                    if (data.checkins == 1) {//判断是否签到,如果此人已签到，则将radiobutton改为不可选中,通过drawable中的selector完成更改
                        rb.setButtonDrawable(R.drawable.radiobtn_unselect);
                        text.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                        text1.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.a3aaad));
                    } else {
                        text.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.black));
                        text1.setTextColor(ContextCompat.getColor(SingleSelectCheckin.this, R.color.black));
                        rb.setButtonDrawable(R.drawable.radiobtn_select);
                    }
                }

                text.setText(data.names);
                if (ticket != null) {
                    text1.setText(ticket.ticketNames);
                }
            }
        };
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).checkins != 1) {
                    attendId = data.get(position).attendId;
                    ref_code = data.get(position).refCodes;
                } else {//如果用户选中的是已经签到过的,则将attendId和ref_code分别初始化,不允许用户进行签到
                    attendId = -1;
                    ref_code = "";
                }
            }
        });
    }

    private String checkInTime() {
        Long time = System.currentTimeMillis();
       // String currentTime = time.toString();
        return time.toString();
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String checkEventId() {
        return eventId + "";
    }

    @Override
    public String checkAttendId() {
        return attendId + "";
    }

    @Override
    public String checkStatus() {
        return checkStatus + "";
    }

    @Override
    public String checkUpdateTime() {
        return checkinTime;
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {

        if (checkIn.getRetStatus() != -1) {
            Toast.makeText(this, R.string.checkIn_success, Toast.LENGTH_SHORT).show();

            DBUtil.updateAttendId(checkStatus, eventId, attendId, ref_code,checkinTime ,Constants.SYNC);
            EventBus.getDefault().postSticky(new MsgEvent(eventId, attendId + "", checkinStr_attendId));
            //  SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).async().execute();
            //    SQLite.update(Attends.class).set(Attends_Table.isSync.is(Constants.SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).async().execute();
        }
    }

    @Override
    public void showCheckInFailed(String errInfo) {
         Toast.makeText(this, R.string.modify_failed, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        tv_single_checkin = (TextView) findViewById(R.id.tv_single_checkin);
        mListView = (ListView) findViewById(android.R.id.list);
        ll_single_back = (AutoLinearLayout) findViewById(R.id.ll_single_back);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tv_single_checkin.setOnClickListener(this);
        ll_single_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_single_checkin:
                if(attendId != -1) {
//                    checkinTime = checkInTime();
//                    Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).and(Attends_Table.refCodes.is(ref_code)).querySingle();
//                    DBUtil.addCheckinToBackup(attends);
//                    if (NetUtil.isConnected(this)) {
//                        DBUtil.updateAttendId(checkStatus, eventId, attendId, ref_code,checkinTime, Constants.NOTSYNC);
//                        presenter = new CheckInPresenter(this);
//                        presenter.attendCheckIn();
//                        AppManager.getAppManager().finishActivity();
//
//                    } else {
//                        DBUtil.updateAttendId(checkStatus, eventId, attendId, ref_code,checkinTime ,Constants.NOTSYNC);
//                        EventBus.getDefault().postSticky(new MsgEvent(eventId, attendId + "", checkinStr_attendId));
//                        AppManager.getAppManager().finishActivity();
//                    }

                    Intent intent = new Intent();
                    intent.putExtra("attendId",attendId);
                    setResult(-66,intent);
                    AppManager.getAppManager().finishActivity();
                }
                break;
            case R.id.ll_single_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
