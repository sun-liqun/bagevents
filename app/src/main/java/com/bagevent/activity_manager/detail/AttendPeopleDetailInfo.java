package com.bagevent.activity_manager.detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.DetailAdapter;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AddPeopleCheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AuditPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.QuitTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ReSendTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpdateNotesPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.QuitTicketFromAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ReSendTicketFromAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpdateAttendeeNotes;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.Chat;
import com.bagevent.db.ChatMessage;
//import com.bagevent.db.ChatMessage_Table;
//import com.bagevent.db.Chat_Table;
import com.bagevent.db.ChatMessage_Table;
import com.bagevent.db.Chat_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_activity.ChattingActivity;
import com.bagevent.new_home.new_interface.new_view.AttendeeChatView;
import com.bagevent.new_home.new_interface.new_view.RemoveChatView;
import com.bagevent.new_home.new_interface.presenter.AttendeeSingleChatPresenter;
import com.bagevent.new_home.new_interface.presenter.RemoveChatPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.SyncChatListUtil;
import com.bagevent.view.CircleTextView;
import com.bagevent.view.RecyclerViewItemDecoration;
import com.bagevent.view.listener.CancelListener;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.EditDialog;
import com.bagevent.view.mydialog.PointDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import name.gudong.loading.LoadingView;


/**
 * Created by zwj on 2016/6/27.
 */
public class AttendPeopleDetailInfo extends BaseActivity implements CheckInView, AddPeopleCheckInView, PopupMenu.OnMenuItemClickListener, ReSendTicketFromAttendeeView,
        AuditView, QuitTicketFromAttendeeView, UpdateAttendeeNotes, AttendeeChatView, BaseQuickAdapter.OnItemClickListener, RemoveChatView {

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
    private int checkInStatus = 1;//签到状态
    private String ref_code = "";
    private int position = -1;
    private int isSendEmail;
    private String notes;
    private int detailType;//0 = 普通列表,1 = 审核列表
    private int payStatus;
    private String auditTime;
    private int orderId;
    private int contactId;
    private int stType;
    private PopupMenu menu;
    private NormalAlertDialog callPhoneDialog;


    private CheckInPresenter checkInPresenters = null;
    private AddPeopleCheckInPresenter addPeopleCheckInPresenter = null;
    private ReSendTicketPresenter reSendTicketPresenter;
    private AuditPresenter auditPresenter;
    private QuitTicketPresenter quitTicketPresenter;
    private UpdateNotesPresenter updateOrderNotes;
    private AttendeeSingleChatPresenter attendeeSingleChatPresenter;
    private RemoveChatPresenter removeChatPresenter;

    private Attends attends;
    private String checkinTime = "";
    private boolean isJumpToChat = false;//防止由于eventbus事件引起的重复进入聊天界面,post的消息未清空，重复执行
    private static final int REQUECT_CODE_CALLPHONE = 5;
    private String text;
    private int audit;


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
        showPopMenu(llRightClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (contactId != 0) {
            List<ChatMessage> messages = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                    .and(ChatMessage_Table.contactId.is(contactId))
                    .and(ChatMessage_Table.isSending.is(false)).queryList();
            if (messages.size() == 0) {
                if (NetUtil.isConnected(this)) {
                    removeChatPresenter = new RemoveChatPresenter(this);
                    removeChatPresenter.removeChat();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.MODIFY_ATTNEDEE_SUCCESS)) {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(event.pageNumber)).querySingle();
            if (attends != null) {
                setHeaderValue();
                detailAdapter.setAttendeeMap(attends.gsonUser);
                detailAdapter.notifyDataSetChanged();
            }
        } else if (event.mMsg.equals(Common.MODIFY_ATTENDEE_REFCODE_SUCCESS)) {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(event.changeName)).querySingle();
            if (attends != null) {
                setHeaderValue();
                detailAdapter.setAttendeeMap(attends.gsonUser);
                detailAdapter.notifyDataSetChanged();
            }
        } else if (event.mMsg.equals(Common.CHANGE_TICKET)) {
            EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).and(EventTicket_Table.ticketIds.is(event.pageNumber)).querySingle();
            if (ticket != null) {
                hearderViewBind.tvDetailTicket.setText(ticket.showTicketNames);
                detailAdapter.notifyDataSetChanged();
            }
        } else if (event.mMsg.equals(Common.CURRENT_CHAT_LIST)) {
            Chat chat = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId))).and(Chat_Table.contactId.is(contactId)).querySingle();

            if (chat != null) {
                if (isJumpToChat) {
                    isJumpToChat = false;
                    Intent intent = new Intent(this, ChattingActivity.class);
                    intent.putExtra("sendToken", chat.token);
                    intent.putExtra("eventId", eventId + "");
                    intent.putExtra("chatName", chat.showName);
                    intent.putExtra("avatar", chat.avatar);
                    intent.putExtra("peopleId", chat.peopleId);
                    intent.putExtra("attendeeId", attendId);
                    intent.putExtra("contactId", chat.contactId);
                    startActivity(intent);
                }

            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mDetail.get(position).getFieldName().equals("mobile_phone")) {
            try {
                JSONObject json = new JSONObject(attends.gsonUser);
                text = json.optString(mDetail.get(position).getFormFieldId() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MPermissions.requestPermissions(this, REQUECT_CODE_CALLPHONE, Manifest.permission.CALL_PHONE);
        }
    }

    @PermissionGrant(REQUECT_CODE_CALLPHONE)
    public void requestCameraSuccess() {

        showCallphoneDialog(text);
        //  Toast.makeText(this,"拨打电话",Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_CALLPHONE)
    public void requestCameraFailed() {
        Toast.makeText(this, R.string.tel_permission, Toast.LENGTH_SHORT).show();
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

        @OnClick(R.id.tv_attendee_status)
        public void onViewClicked() {
            checkinAction(tvAttendeeStatus);
        }
    }

    private void showResendTicketDialog() {
        callPhoneDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_ticket_again))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        callPhoneDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        if (NetUtil.isConnected(AttendPeopleDetailInfo.this)) {
//                            Toast toast = Toast.makeText(AttendPeopleDetailInfo.this, "重发中...", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
                            reSendTicketPresenter = new ReSendTicketPresenter(AttendPeopleDetailInfo.this);
                            reSendTicketPresenter.resendTicketFromAttendee();
                        } else {
                            Toast.makeText(AttendPeopleDetailInfo.this, R.string.check_network2, Toast.LENGTH_SHORT).show();
                        }
                        callPhoneDialog.dismiss();
                    }
                })
                .build();
        callPhoneDialog.show();
    }

    private void showCallphoneDialog(final String text) {
        callPhoneDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_call) + text)
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        callPhoneDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        if (!TextUtils.isEmpty(text)) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + text));
                            if (ActivityCompat.checkSelfPermission(AttendPeopleDetailInfo.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);
                        }
                        callPhoneDialog.dismiss();
                    }
                })
                .build();
        callPhoneDialog.show();
    }

    private void showPopMenu(View v) {


        menu = new PopupMenu(this, v);
        menu.getMenuInflater().inflate(R.menu.attendee_menu, menu.getMenu());
        if(attends.buyingGroupId!=0){
            if(attends.bgState==1){
                menu.getMenu().findItem(R.id.menu_change_ticket).setVisible(true);
                menu.getMenu().findItem(R.id.menu_quit_ticket).setVisible(true);
                menu.getMenu().findItem(R.id.menu_add_remark).setVisible(true);
            }else{
                menu.getMenu().findItem(R.id.menu_change_ticket).setVisible(false);
                menu.getMenu().findItem(R.id.menu_quit_ticket).setVisible(false);
                menu.getMenu().findItem(R.id.menu_add_remark).setVisible(false);
            }
        }else{
            menu.getMenu().findItem(R.id.menu_change_ticket).setVisible(true);
            menu.getMenu().findItem(R.id.menu_quit_ticket).setVisible(true);
            menu.getMenu().findItem(R.id.menu_add_remark).setVisible(true);
        }
        menu.setOnMenuItemClickListener(this);
        setForceShowIcon(menu);
    }

    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void checkinAction(View view) {
        if (attends.audits != 3 && payStatus == 1) {
            checkinTime = checkinTime();
            if (attends.attendId != 0) {
                attendId = attends.attendId;
                if (NetUtil.isConnected(this)) {
                    if (checkInStatus==0){
                        checkInStatus=1;
                    }else {
                        checkInStatus=0;
                    }
                    checkInPresenters = new CheckInPresenter(this);
                    checkInPresenters.attendCheckIn();
                } else {
                    TosUtil.show(getString(R.string.check_network));
                }
            } else {
                ref_code = attends.refCodes;
                if (NetUtil.isConnected(this)) {
                    addPeopleCheckInPresenter = new AddPeopleCheckInPresenter(this);
                    addPeopleCheckInPresenter.addPeopleCheckIn(ref_code);
                } else {
                    TosUtil.show(getString(R.string.check_network));
                }
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_modify_attendee_info: //修改参会信息
                Intent intent = new Intent(this, ModifyUserInfo.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("attendId", attendId);
                intent.putExtra("ref_code", ref_code);
                intent.putExtra("position", position);
                intent.putExtra("ticketId", attends.ticketIds);
                startActivity(intent);
                break;
            case R.id.menu_change_ticket: //更换门票
                Intent intent1 = new Intent(this, ChangeTicket.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("ticketId", ticketId);
                intent1.putExtra("attendeeId", attendId);
                intent1.putExtra("position", position);
                startActivity(intent1);
                break;
            case R.id.menu_quit_ticket: //退票
                final PointDialog dialog = new PointDialog(this);
                dialog.setText(getString(R.string.confirm_receivables1));
                dialog.setCancelText(getString(R.string.cancel));
                dialog.setConfirmText(getString(R.string.confirm));
                dialog.setConfirmListener(new ConfirmListener() {
                    @Override
                    public void confirm(View v) {
                        if (NetUtil.isConnected(AttendPeopleDetailInfo.this)) {
                            isSendEmail = 1;
                            quitTicketPresenter = new QuitTicketPresenter(AttendPeopleDetailInfo.this);
                            quitTicketPresenter.quitTicketFromAttendeeView();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(AttendPeopleDetailInfo.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.setCancelListener(new CancelListener() {
                    @Override
                    public void cancel(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.menu_add_remark: //查看订单详情
                Intent intents = new Intent(this, ActivityOrderDetail.class);
                intents.putExtra("eventId", eventId);
                intents.putExtra("orderId", orderId);
                intents.putExtra("position", position);
                intents.putExtra("enterStatus", 4);
                startActivity(intents);
                break;
        }
        return false;
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click, R.id.ll_audit_refuse, R.id.ll_audit_pass, R.id.ll_modify_invoice, R.id.ll_resend_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                if (payStatus == 1) {
                    menu.show();
                } else if (payStatus == 10) {
                    Toast.makeText(this, R.string.ticket_back, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.order_no_complete, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_audit_refuse:  //审核拒绝
                audit(3);
                break;
            case R.id.ll_audit_pass: //审核通过
                audit(2);
                break;
            case R.id.ll_modify_invoice:
                final EditDialog editDialog = new EditDialog(this);
                if (!TextUtils.isEmpty(notes)) {
                    editDialog.setText(notes);
                }
                editDialog.setConfirmListener(new ConfirmListener() {
                    @Override
                    public void confirm(View view) {
                        if (!TextUtils.isEmpty(editDialog.getText())) {
                            if (NetUtil.isConnected(AttendPeopleDetailInfo.this)) {
                                notes = editDialog.getText();
                                updateOrderNotes = new UpdateNotesPresenter(AttendPeopleDetailInfo.this);
                                updateOrderNotes.updateAttendeeNotes();
                                editDialog.setText("");
                            } else {
                                Toast.makeText(AttendPeopleDetailInfo.this, R.string.check_network, Toast.LENGTH_SHORT).show();
                            }
                            editDialog.dismiss();
                        } else {
                            Toast.makeText(AttendPeopleDetailInfo.this, R.string.input_remark, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                editDialog.show();
                break;
            case R.id.ll_resend_ticket: //重发电子票
                showResendTicketDialog();
                break;
//            case R.id.ll_attendee_chat:
//                if (NetUtil.isConnected(this)) {
//                    isJumpToChat = true;
//                    attendeeSingleChatPresenter = new AttendeeSingleChatPresenter(this);
//                    attendeeSingleChatPresenter.getAttendeeSingleChat();
//                } else {
//                    Toast.makeText(this, R.string.check_network2, Toast.LENGTH_SHORT).show();
//                }
//                break;
        }
    }

    private void initAdapter() {
        loadingFinished();
        if (attends != null) {
            detailAdapter = new DetailAdapter(mDetail, attends.gsonUser);
            detailAdapter.addHeaderView(view);
            detailAdapter.addFooterView(footView);
            detailAdapter.setOnItemClickListener(this);
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


    /**
     * 审核人员
     *
     * @param audit
     */
    private void audit(int audit) {
        this.audit = audit;
        auditTime = checkinTime();

        if (NetUtil.isConnected(this)) {
            auditPresenter = new AuditPresenter(this);
            auditPresenter.getAudit(audit + "");
        } else {
            TosUtil.show(getString(R.string.check_network));
        }
    }

    private void setHeaderValue() {
        if (attends != null) {
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

            //  Log.e("fdsf",attends.attendId + "");
            if(attends.buyingGroupId!=0){
                if(attends.bgState==1){
                    hearderViewBind.tvAttendeeStatus.setVisibility(View.VISIBLE);
                }else{
                    hearderViewBind.tvAttendeeStatus.setVisibility(View.GONE);
                }
            }else{
                hearderViewBind.tvAttendeeStatus.setVisibility(View.VISIBLE);
            }
            if (payStatus == 10) {
                hearderViewBind.tvAttendeeStatus.setText(R.string.back_order_already);
                hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
                hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_grey);
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

            if (!TextUtils.isEmpty(attends.notes)) {
                footerViewBind.llNoteFooter.setVisibility(View.VISIBLE);
                footerViewBind.tvNotes.setText(attends.notes);
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
        stType = intent.getIntExtra("stType", 0);
        checkInStatus=intent.getIntExtra("checkins",-1);
        //Log.e("fdsfsdff",attendId + "");


        if (stType == 1) {
            ivRight2.setVisibility(View.GONE);
            llRightClick.setClickable(false);
        }

        if (attendId != 0) {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).querySingle();
        } else {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).querySingle();
        }
        //     Log.e("orderDetail attendId",attendId+"");

        if (attends != null) {
            orderId = attends.orderIds;

            if (attends.payStatuss == 10 || attends.payStatuss == 0) {
                llAttendeeAction.setVisibility(View.GONE);
                llAuditAction.setVisibility(View.GONE);
            } else {
                if (attends.audits == 1) {
                    llAttendeeAction.setVisibility(View.GONE);
                    llAuditAction.setVisibility(View.VISIBLE);
                } else if (attends.audits == 3) {
                    llAttendeeAction.setVisibility(View.GONE);
                    llAuditAction.setVisibility(View.GONE);
                } else {
                    llAttendeeAction.setVisibility(View.VISIBLE);
                    if(attends.buyingGroupId!=0){
                        if(attends.bgState==1){
                            llResendTicket.setVisibility(View.VISIBLE);
                        }else{
                            llResendTicket.setVisibility(View.GONE);
                        }
                    }else{
                        llResendTicket.setVisibility(View.VISIBLE);
                    }
                    llAuditAction.setVisibility(View.GONE);
                }
            }
            LogUtil.i("-----------------hasBuyingGrouping:"+attends.hasBuyingGrouping+"bgState:"+attends.bgState+"buyingGroupId:"+attends.buyingGroupId+attends.names);
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
        Glide.with(this).load(R.drawable.gengduo).into(ivRight2);
        Glide.with(this)
                .load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.attend_info);
        rvAttendDetail.setLayoutManager(new LinearLayoutManager(this));
        rvAttendDetail.addItemDecoration(new RecyclerViewItemDecoration());
    }

    private String checkinTime() {
        Long time = System.currentTimeMillis();
        return time.toString();
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int contactId() {
        return contactId;
    }

    @Override
    public void removeChatSuccess() {
        Log.e("AttendPeopleDetail", "Remove Chat List Is Success");
    }

    @Override
    public void removeChatFailed(String errInfo) {
        Log.e("AttendPeopleDetail", "Remove Chat List is Failed" + errInfo);
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
        return checkInStatus + "";
    }

    @Override
    public String checkUpdateTime() {
        return checkinTime;
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {//签到成功同步数据到本地
        if (attends.checkins == 0) {
            checkInStatus = 1;
        } else {
            checkInStatus = 0;
        }
        attends.checkins = checkInStatus;
        setAttendeeStatus(checkInStatus);
        if (detailType == 0) {
            EventBus.getDefault().postSticky(new MsgEvent(position, checkInStatus, Common.CHECKIN_SUCCESS));
        } else {
            EventBus.getDefault().postSticky(new MsgEvent(position, attendId, Common.AUDIT_ATTENDEE));
        }
        if (attends.attendId != 0) {
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkInStatus), Attends_Table.checkinTimes.is(checkinTime), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        } else {
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkInStatus), Attends_Table.checkinTimes.is(checkinTime), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).execute();
        }
        SQLite.update(Attends.class).set(Attends_Table.isSync.is(Constants.SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        detailAdapter.notifyDataSetChanged();
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public int orderId() {
        return orderId;
    }

    @Override
    public String attendeeId() {
        return attendId + "";
    }

    @Override
    public void showAttendeeSingleChatSuccess(ChatData response) {
        for (int i = 0; i < response.getRespObject().size(); i++) {
            contactId = response.getRespObject().get(i).getContactId();
        }
        SyncChatListUtil util = new SyncChatListUtil(this, response, true, true);
        util.startSyncChatList();
    }

    @Override
    public void showAttendeeSingleChatFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String notes() {
        return notes;
    }

    @Override
    public void editNotesSuccess(String response) {
        // Log.e("attendeeDetail",notes + attendId+"eventId="+eventId);
        footerViewBind.llNoteFooter.setVisibility(View.VISIBLE);
        footerViewBind.tvNotes.setText(notes);
        SQLite.update(Attends.class).set(Attends_Table.notes.is(notes)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        detailAdapter.notifyDataSetChanged();
    }

    @Override
    public void editNotesFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int isSendEmail() {
        return isSendEmail;
    }

    @Override
    public void showQuitSuccess(String response) {
        payStatus = 10;
        SQLite.update(Attends.class).set(Attends_Table.payStatuss.is(10)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        hearderViewBind.tvAttendeeStatus.setText(R.string.back_order_already);
        hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.gray));
        hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.tv_border_no_fill_grey);
        detailAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.back_ticket_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuitFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String upDateTime() {
        return auditTime;
    }

    @Override
    public void showAuditSuccess() {
        if (audit == 3) {
            llAuditAction.setVisibility(View.GONE);
            llAttendeeAction.setVisibility(View.GONE);
            hearderViewBind.tvAttendeeStatus.setVisibility(View.VISIBLE);
            hearderViewBind.tvAttendeeStatus.setText(R.string.audit_refuse);
            hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
            hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_red);
            hearderViewBind.tvAttendeeStatus.setOnClickListener(null);
        } else {
            llAuditAction.setVisibility(View.GONE);
            llAttendeeAction.setVisibility(View.VISIBLE);
            hearderViewBind.tvAttendeeStatus.setVisibility(View.VISIBLE);
            hearderViewBind.tvAttendeeStatus.setText(R.string.confirm_checkin);
            hearderViewBind.tvAttendeeStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
            hearderViewBind.tvAttendeeStatus.setBackgroundResource(R.drawable.bg_text_organge);
        }
        EventBus.getDefault().postSticky(new MsgEvent(position, attendId, Common.AUDIT_ATTENDEE));
        SQLite.update(Attends.class).set(Attends_Table.audits.is(audit), Attends_Table.auditTimes.is(auditTime), Attends_Table.auditSync.is(Constants.AUDIT_NOT_SYNC))
                .where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        SQLite.update(Attends.class).set(Attends_Table.auditSync.is(Constants.AUDIT_SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
    }

    @Override
    public void showAuditFailed() {
        Toast.makeText(this, R.string.audit_failed, Toast.LENGTH_SHORT).show();
        SQLite.update(Attends.class).set(Attends_Table.audits.is(1), Attends_Table.auditSync.is(Constants.AUDIT_SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        EventBus.getDefault().postSticky(new MsgEvent("AuditFailed"));
    }

    @Override
    public int sendToAttendee() {
        return 1;
    }

    @Override
    public void showSendSuccess(String response) {
        Toast toast = Toast.makeText(this, response, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showSendFailed(String errInfo) {
        Toast toast = Toast.makeText(this, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public String checkInStatus() {
        return checkInStatus + "";
    }

    @Override
    public String checkInupdateTime() {
        return checkinTime;
    }


    @Override
    public void showCheckInSuccess(CheckIn checkIn) {
        SQLite.update(Attends.class).set(Attends_Table.isSync.is(Constants.SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).execute();
    }

    @Override
    public void showCheckInFailed(String errInfo) {
        TosUtil.show(errInfo);
    }

}
