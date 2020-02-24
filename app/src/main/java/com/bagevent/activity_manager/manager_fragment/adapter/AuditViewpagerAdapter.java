package com.bagevent.activity_manager.manager_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.FormData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AuditPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.view.CircleTextView;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zwj on 2016/7/8.
 */
public class AuditViewpagerAdapter extends PagerAdapter implements View.OnClickListener, CheckInView, AuditView {

    private Context mContext;
    private ArrayList<AttendPeople.RespObjectBean.ObjectsBean> mData;
    private List<FormData> mFormData;
    private LayoutInflater mInflater;

    AutoLinearLayout ll_parent_refuse;
    AutoLinearLayout ll_parent_audit;
    AutoLinearLayout ll_checkin_parent;

    private View currentView;
    ImageView iv_audit_checkin;
    TextView tv_audit_checkin;

    private int eventId = -1;
    private int attendId = -1;
    private int count = -1;
    private int checkStatus = -1;
    private int audit = -1;
    private int pos = -1;
    private String ref_code;
    private String checkinTime = "";
    private String auditTime = "";

    /**
     * 审核和签到
     */
    private AuditPresenter auditPresenter;
    private CheckInPresenter checkInPresenter;
    private Attends attends;

    public AuditViewpagerAdapter(ArrayList<AttendPeople.RespObjectBean.ObjectsBean> mData, List<FormData> mFormData, int eventId, int attendId, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mFormData = mFormData;
        this.eventId = eventId;
        this.attendId = attendId;
        count = mFormData.size();
        attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).querySingle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
        attendId = mData.get(position).getAttendeeId();
        ref_code = mData.get(position).getRefCode();
        if (mData.get(position).getCheckin() == 0) {
            checkStatus = 1;
        } else {
            checkStatus = 0;
        }
        pos = position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).and(EventTicket_Table.ticketIds.is(mData.get(position).getTicketId())).querySingle();
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams horLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AttendPeople.RespObjectBean.ObjectsBean bean = mData.get(position);
        View layout = mInflater.inflate(R.layout.activity_audit_detail_item, container, false);
        AutoLinearLayout ll_audit_detail_back = (AutoLinearLayout) layout.findViewById(R.id.ll_audit_detail_back);
        TextView tv_audit_detail_detail = (TextView) layout.findViewById(R.id.tv_audit_detail_detail);
        CircleTextView tv_audit_letter = (CircleTextView) layout.findViewById(R.id.tv_audit_letter);
        TextView tv_audit_name = (TextView) layout.findViewById(R.id.tv_audit_name);
        TextView tv_audit_ticket = (TextView) layout.findViewById(R.id.tv_audit_ticket);
        LinearLayout ll_audit_parent = (LinearLayout) layout.findViewById(R.id.ll_audit_parent);

        ll_parent_refuse = (AutoLinearLayout) layout.findViewById(R.id.ll_parent_refuse);
        ll_parent_audit = (AutoLinearLayout) layout.findViewById(R.id.ll_parent_audit);
        ll_checkin_parent = (AutoLinearLayout) layout.findViewById(R.id.ll_checkin_parent);
        AutoLinearLayout audit_pass = (AutoLinearLayout) layout.findViewById(R.id.audit_pass);
        AutoLinearLayout audit_refuse = (AutoLinearLayout) layout.findViewById(R.id.audit_refuse);
        AutoLinearLayout ll_audit_checkin = (AutoLinearLayout) layout.findViewById(R.id.ll_audit_checkin);
        iv_audit_checkin = (ImageView) layout.findViewById(R.id.iv_audit_checkin);
        tv_audit_checkin = (TextView) layout.findViewById(R.id.tv_audit_checkin);
        if (attends != null) {
            if (attends.audits == 2) {
                setCheckInStatus(attends.checkins);
            }
        }
        //  Log.e("tag","");

        ll_audit_detail_back.setOnClickListener(this);
        audit_pass.setOnClickListener(this);
        audit_refuse.setOnClickListener(this);
        ll_audit_checkin.setOnClickListener(this);

        tv_audit_name.setText(mData.get(position).getName());

        tv_audit_letter.setText(mData.get(position).getName().substring(0, 1));
        if (ticket != null) {
            tv_audit_ticket.setText(ticket.ticketNames);
        }
        tv_audit_detail_detail.setText(mData.get(position).getName());

        for (int i = 0; i < count; i++) {
            FormData form = mFormData.get(i);
            initTextView(ll_audit_parent, form.getFormName(), tvLp, 25, 10, 15, 12);
            try {
                initTextView(ll_audit_parent, userInfo(form.getFormFieldId(), bean.getAttendeeId()), lps, 25, 10, 10, 16);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            initViewLine(ll_audit_parent, horLp, 10);
        }
        ((ViewPager) container).addView(layout, 0);
        return layout;
    }

    private void initTextView(LinearLayout lin, String name, LinearLayout.LayoutParams lp, int left, int bottom, int top, int textSize) {
        TextView tv_form_userName = new TextView(mContext);
        lp.setMargins(0, top, 0, bottom);
        tv_form_userName.setText(name);
        tv_form_userName.setLayoutParams(lp);
        tv_form_userName.setPadding(left, 0, 0, 0);
        tv_form_userName.setTextSize(textSize);
        tv_form_userName.setTextColor(ContextCompat.getColor(mContext,R.color.a797d7f));
        lin.addView(tv_form_userName);
    }

    private void initViewLine(LinearLayout lin, LinearLayout.LayoutParams lps, int top) {
        View view_line = new View(mContext);
        lps.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lps.height = 1;
        lps.setMargins(0, top, 0, 0);
        view_line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.e6edf2));
        view_line.setLayoutParams(lps);
        lin.addView(view_line);
    }

    /**
     * 获得attendeeMap中的数据
     *
     * @param filedId
     * @return
     * @throws JSONException
     */
    private String userInfo(int filedId, int mAttendId) throws JSONException {
        String info = "";
        Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(mAttendId)).querySingle();
        if (attends != null) {
            JSONObject object = new JSONObject(attends.gsonUser);
            info = object.getString(filedId + "");
        }
        return info;
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    private String currentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return format.format(curDate);
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_audit_detail_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.audit_pass://审核通过
                audit = 2;
                auditTime = currentTime();
                if (NetUtil.isConnected(mContext)) {
                    DBUtil.updateAudit(audit, eventId, attendId,auditTime ,Constants.AUDIT_NOT_SYNC);
                    auditPresenter = new AuditPresenter(this);
                    auditPresenter.getAudit(audit + "");
                 //   Log.e("attendId--->",attendId+"");
                  //  Log.e("ref_code--->",ref_code+"");
                } else {
                    ll_parent_audit = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_audit);
                    ll_parent_refuse = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_refuse);
                    ll_checkin_parent = (AutoLinearLayout) currentView.findViewById(R.id.ll_checkin_parent);
                    ll_parent_audit.setVisibility(View.GONE);
                    ll_parent_refuse.setVisibility(View.GONE);
                    ll_checkin_parent.setVisibility(View.VISIBLE);
                    DBUtil.updateAudit(audit, eventId, attendId,auditTime ,Constants.AUDIT_NOT_SYNC);
                }
                break;
            case R.id.audit_refuse://审核拒绝
                audit = 3;
                auditTime = currentTime();
                if (NetUtil.isConnected(mContext)) {
                    DBUtil.updateAudit(audit, eventId, attendId,auditTime ,Constants.AUDIT_NOT_SYNC);
                    auditPresenter = new AuditPresenter(this);
                    auditPresenter.getAudit(audit + "");
                } else {
                    ll_parent_audit = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_audit);
                    ll_parent_refuse = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_refuse);
                    ll_checkin_parent = (AutoLinearLayout) currentView.findViewById(R.id.ll_checkin_parent);
                    ll_parent_audit.setVisibility(View.GONE);
                    ll_checkin_parent.setVisibility(View.GONE);
                    ll_parent_refuse.setVisibility(View.VISIBLE);
                    DBUtil.updateAudit(audit, eventId, attendId,auditTime ,Constants.AUDIT_NOT_SYNC);
                }
                break;
            case R.id.ll_audit_checkin://签到
                checkinTime = currentTime();
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).querySingle();
                DBUtil.addCheckinToBackup(attends);
                if (NetUtil.isConnected(mContext)) {
                    DBUtil.updateAttendIdNoRefcode(checkStatus, eventId, attendId,checkinTime ,Constants.NOTSYNC);
                    checkInPresenter = new CheckInPresenter(this);
                    checkInPresenter.attendCheckIn();
                } else {
                    iv_audit_checkin = (ImageView) currentView.findViewById(R.id.iv_audit_checkin);
                    tv_audit_checkin = (TextView) currentView.findViewById(R.id.tv_audit_checkin);
                    if (checkStatus == 0) {
                        iv_audit_checkin.setImageResource(R.drawable.solid_checkin);
                        tv_audit_checkin.setText(R.string.checkin);
                    } else {
                        iv_audit_checkin.setImageResource(R.drawable.solid_checkin_cancel);
                        tv_audit_checkin.setText(R.string.checkin_cancle);
                        tv_audit_checkin.setTextColor(ContextCompat.getColor(mContext,R.color.grey));
                    }
               //     mData.get(pos).setCheckin(checkStatus);
                    DBUtil.updateAttendIdNoRefcode(checkStatus, eventId, attendId,checkinTime ,Constants.NOTSYNC);
                }
                break;

        }
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String attendeeId() {
        return attendId + "";
    }

    @Override
    public String upDateTime() {
        return auditTime;
    }

    @Override
    public void showAuditSuccess() {
        //     Log.e("audit--->",audit+"");
        ll_parent_audit = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_audit);
        ll_parent_refuse = (AutoLinearLayout) currentView.findViewById(R.id.ll_parent_refuse);
        ll_checkin_parent = (AutoLinearLayout) currentView.findViewById(R.id.ll_checkin_parent);
        if (audit == 2) {
            ll_parent_audit.setVisibility(View.GONE);
            ll_parent_refuse.setVisibility(View.GONE);
            ll_checkin_parent.setVisibility(View.VISIBLE);
        } else if (audit == 3) {
            ll_parent_audit.setVisibility(View.GONE);
            ll_checkin_parent.setVisibility(View.GONE);
            ll_parent_refuse.setVisibility(View.VISIBLE);
        }
        DBUtil.updateAudit(audit, eventId, attendId,auditTime ,Constants.AUDIT_SYNC);
    }

    @Override
    public void showAuditFailed() {
        Toast.makeText(mContext, R.string.audit_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context mContext() {
        return mContext;
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

    private void setCheckInStatus(int checkStatus) {
        ll_parent_audit.setVisibility(View.GONE);
        ll_parent_refuse.setVisibility(View.GONE);
        ll_checkin_parent.setVisibility(View.VISIBLE);
        if (checkStatus == 0) {
            iv_audit_checkin.setImageResource(R.drawable.solid_checkin);
            tv_audit_checkin.setText(R.string.checkin);
        } else {
            iv_audit_checkin.setImageResource(R.drawable.solid_checkin_cancel);
            tv_audit_checkin.setText(R.string.checkin_cancle);
            tv_audit_checkin.setTextColor(ContextCompat.getColor(mContext,R.color.grey));
        }
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {
        if (checkIn.getRetStatus() != -1) {
            iv_audit_checkin = (ImageView) currentView.findViewById(R.id.iv_audit_checkin);
            tv_audit_checkin = (TextView) currentView.findViewById(R.id.tv_audit_checkin);
            if (checkStatus == 0) {
                iv_audit_checkin.setImageResource(R.drawable.solid_checkin);
                tv_audit_checkin.setText(R.string.checkin);
            } else {
                iv_audit_checkin.setImageResource(R.drawable.solid_checkin_cancel);
                tv_audit_checkin.setText(R.string.checkin_cancle);
                tv_audit_checkin.setTextColor(ContextCompat.getColor(mContext,R.color.grey));
            }
           // mData.get(pos).setCheckin(checkStatus);
            DBUtil.updateAttendIdNoRefcode(checkStatus, eventId, attendId,checkinTime,Constants.SYNC);
        }
    }

    @Override
    public void showCheckInFailed(String errInfo) {

    }
}
