package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.adapter.DetailAdapter;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.CircleTextView;
import com.bagevent.view.RecyclerViewItemDecoration;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import name.gudong.loading.LoadingView;

/**
 * Created by ZWJ on 2017/12/22 0022.
 */

public class CollectionAttendeeDetailInfo extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.rv_attend_detail)
    RecyclerView rvAttendDetail;
    @BindView(R.id.ll_audit_refuse)
    AutoLinearLayout llAuditRefuse;
    @BindView(R.id.ll_audit_pass)
    AutoLinearLayout llAuditPass;
    @BindView(R.id.ll_audit_action)
    AutoLinearLayout llAuditAction;
    @BindView(R.id.ll_modify_invoice)
    AutoLinearLayout llModifyInvoice;
    @BindView(R.id.ll_resend_ticket)
    AutoLinearLayout llResendTicket;
    @BindView(R.id.ll_attendee_action)
    AutoLinearLayout llAttendeeAction;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
//    @BindView(R.id.ll_attendee_chat)
//    AutoLinearLayout llAttendeeChat;
    @BindView(R.id.ll_attendee_bottom)
    AutoLinearLayout llAttendeeBottom;
    @BindView(R.id.rl_detail)
    AutoRelativeLayout rlDetail;
    private View view;
    private View footView;
    private HearderViewBind hearderViewBind;
    private FooterViewBind footerViewBind;
    private List<FormType.RespObjectBean> mDetail = new ArrayList<FormType.RespObjectBean>();
    private DetailAdapter detailAdapter;

    private int eventId = -1;
    private int attendId = -1;
    private String userId;
    private int ticketId;
    private int checkInStatus = -1;//签到状态
    private String ref_code = "";
    private int position = -1;
    private int isSendEmail;
    private String notes;
    private int detailType;//0 = 普通列表,1 = 审核列表
    private int payStatus;
    private String auditTime;
    private int orderId;
    private int contactId;

    private Attends attends;
    private String checkinTime = "";
    private boolean isJumpToChat = false;//防止由于eventbus事件引起的重复进入聊天界面,post的消息未清空，重复执行
    private static final int REQUECT_CODE_CALLPHONE = 5;
    private String text;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.attend_people_detail);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        initView();
        isLoading();
        getHeaderView();
        getFooterView();
        hearderViewBind = new HearderViewBind(view);
        footerViewBind = new FooterViewBind(footView);
        initData();
        setHeaderValue();
        initAdapter();
    }


    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }


    class FooterViewBind {
        @BindView(R.id.tv_notes)
        TextView tvNotes;
        @BindView(R.id.ll_note_footer)
        AutoLinearLayout llNoteFooter;

        public FooterViewBind(View footView) {
            ButterKnife.bind(this, footView);
        }
    }

    class HearderViewBind {
        @BindView(R.id.tv_attendee_avatar)
        CircleTextView tvAttendeeAvatar;
        @BindView(R.id.iv_attendee_avatar)
        CircleImageView ivAttendeeAvatar;
        @BindView(R.id.tv_detail_name)
        TextView tvDetailName;
        @BindView(R.id.tv_detail_ticket)
        TextView tvDetailTicket;
        @BindView(R.id.tv_attendee_status)
        TextView tvAttendeeStatus;


        public HearderViewBind(View headerView) {
            ButterKnife.bind(this, headerView);
        }
    }


    private void initAdapter() {
        loadingFinished();
        if (attends != null) {
            detailAdapter = new DetailAdapter(mDetail, attends.gsonUser);
            detailAdapter.addHeaderView(view);
            detailAdapter.addFooterView(footView);
            detailAdapter.openLoadAnimation();
            rvAttendDetail.setAdapter(detailAdapter);
        } else {
            Toast.makeText(this, R.string.no_info, Toast.LENGTH_SHORT).show();
        }

    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        rvAttendDetail.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        rvAttendDetail.setVisibility(View.VISIBLE);
    }

    private void setAttendeeStatus(int checkins) {
        hearderViewBind.tvAttendeeStatus.setVisibility(View.GONE);
        if (checkins == 0) {
            hearderViewBind.tvAttendeeStatus.setText(R.string.confirm_checkin);
            hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
            hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_organge);
        } else {
            hearderViewBind.tvAttendeeStatus.setText(R.string.checkin_cancle);
            hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
            hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_grey);
        }
    }


    private void setHeaderValue() {
        if (attends != null) {
            tvTitle.setText(attends.names);
            payStatus = attends.payStatuss;
            if (!TextUtils.isEmpty(attends.avatars)) {
                if (NetUtil.isConnected(this)) {
                    hearderViewBind.tvAttendeeAvatar.setVisibility(View.GONE);
                    hearderViewBind.ivAttendeeAvatar.setVisibility(View.VISIBLE);
                    if (attends.avatars.contains("wx")) {
                        Glide.with(this).load("http:" + attends.avatars).into(hearderViewBind.ivAttendeeAvatar);
                    } else {
                        Glide.with(this).load(Constants.imgsURL + attends.avatars).into(hearderViewBind.ivAttendeeAvatar);
                    }
                } else {
                    hearderViewBind.ivAttendeeAvatar.setVisibility(View.GONE);
                    hearderViewBind.tvAttendeeAvatar.setVisibility(View.VISIBLE);
                    if (attends.names.length() > 0) {
                        hearderViewBind.tvAttendeeAvatar.setText(attends.names.substring(0, 1));
                    } else {
                        hearderViewBind.tvAttendeeAvatar.setText(attends.strSort);
                    }
                }
            } else {
                hearderViewBind.ivAttendeeAvatar.setVisibility(View.GONE);
                hearderViewBind.tvAttendeeAvatar.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(attends.names)) {
                    if (attends.names.length() > 0) {
                        hearderViewBind.tvAttendeeAvatar.setText(attends.names.substring(0, 1));
                    } else {
                        hearderViewBind.tvAttendeeAvatar.setText(attends.strSort);
                    }
                }

            }
            hearderViewBind.tvDetailName.setText(attends.names);
            EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).and(EventTicket_Table.ticketIds.is(attends.ticketIds)).querySingle();
            if (ticket != null) {
                ticketId = ticket.ticketIds;
                //  Log.e("attend",ticketId+"");
                hearderViewBind.tvDetailTicket.setText(ticket.showTicketNames);
            }

            setAttendeeStatus(attends.checkins);


            if (payStatus == 10) {
                hearderViewBind.tvAttendeeStatus.setText(R.string.back_order_already);
                hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
                hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_red);
            } else if (payStatus == 0 || payStatus == 2 || payStatus == 4 || payStatus == 5) {
                hearderViewBind.tvAttendeeStatus.setText(R.string.unPaid);
                hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
                hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_grey);
            } else {
                if (attends.audits == 1) {
                    hearderViewBind.tvAttendeeStatus.setVisibility(View.GONE);
                } else if (attends.audits == 3) {
                    hearderViewBind.tvAttendeeStatus.setText(R.string.audit_refuse);
                    hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
                    hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_red);
                }
            }

            if (!TextUtils.isEmpty(notes)) {
                footerViewBind.llNoteFooter.setVisibility(View.VISIBLE);
                footerViewBind.tvNotes.setText(notes);
            } else {
                footerViewBind.llNoteFooter.setVisibility(View.GONE);
            }
        }
    }


    private void initData() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        attendId = intent.getIntExtra("attendId", 0);
        ref_code = intent.getStringExtra("ref_code");
        position = intent.getIntExtra("position", 0);
        notes = intent.getStringExtra("remark");
        detailType = intent.getIntExtra("detailType", 0);
        orderId = intent.getIntExtra("orderId", -1);
        userId = SharedPreferencesUtil.get(this, "userId", "");

        if (attendId != 0) {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).querySingle();
        } else {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).querySingle();
        }

        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
        if (formType != null) {
            FormType mType = new Gson().fromJson(formType, FormType.class);//解析表单数据
            mDetail.addAll(mType.getRespObject());
        } else {
            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
        }


    }

    private void getFooterView() {
        footView = getLayoutInflater().inflate(R.layout.attend_people_detail_footer, (ViewGroup) rvAttendDetail.getParent(), false);
    }

    private void getHeaderView() {
        view = getLayoutInflater().inflate(R.layout.attend_people_datail_type1, (ViewGroup) rvAttendDetail.getParent(), false);
    }

    private void initView() {
        ivRight.setVisibility(View.GONE);
        ivRight2.setVisibility(View.GONE);
        //     Glide.with(this).load(R.drawable.gengduo).into(ivRight2);
        Glide.with(this)
                .load(R.drawable.fanhui).into(ivTitleBack);
        rvAttendDetail.setLayoutManager(new LinearLayoutManager(this));
        rvAttendDetail.addItemDecoration(new RecyclerViewItemDecoration());
    }


}
