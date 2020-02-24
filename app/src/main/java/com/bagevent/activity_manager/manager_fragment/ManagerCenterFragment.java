package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.BaseFragment;
import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.activity_manager.detail.AuditAttendeeActivity;
import com.bagevent.activity_manager.detail.AuditOrder;
import com.bagevent.activity_manager.manager_fragment.adapter.TicketAdapter;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ActivityOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendPeoplePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendeeFilePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetShareInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ActivityOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetShareInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.Order;
import com.bagevent.db.Order_Table;
import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.home_interface.presenter.EventDetailPresenter;
import com.bagevent.home.home_interface.view.GetEventDetailView;
import com.bagevent.new_home.HomePage;
import com.bagevent.util.AppManager;
import com.bagevent.util.AppUtils;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncAttendeeUtil;
import com.bagevent.util.dbutil.SyncOrderUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.util.image_download.ImageUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import name.gudong.loading.LoadingView;

/**
 * Created by zwj on 2016/5/30.
 * <p>
 * 管理中心
 */
public class ManagerCenterFragment extends BaseFragment implements GetAttendPeopleView, SwipeRefreshLayout.OnRefreshListener, GetTicketInfoView, GetFormTypeView,
        GetEventDetailView, GetShareInfoView, GetAttendeeJsonFileView, ActivityOrderView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_ticket)
    RecyclerView rvTicket;
    Unbinder unbinder;
    @BindView(R.id.manager_swiperefresh)
    SwipeRefreshLayout managerSwiperefresh;
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
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_checkin)
    TextView tvCheckin;
    @BindView(R.id.tv_audit_order_count)
    TextView tvAuditOrderCount;
    @BindView(R.id.ll_audit_order)
    AutoLinearLayout llAuditOrder;
    @BindView(R.id.tv_audit_attendee_count)
    TextView tvAuditAttendeeCount;
    @BindView(R.id.ll_audit_attendee)
    AutoLinearLayout llAuditAttendee;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.tv_retry)
    TextView tvRetry;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;

    private int eventId = -1;
    private String userId = "";
    private String imgURl = "";
    private String eventName = "";
    private int stType = 0;
    private String attendee = "";
    private String income = "";
    private String checkin = "";

    private GetTicketPresenter getTicketPresenter;
    private EventDetailPresenter eventDetailPresenter;
    private GetAttendeeFilePresenter getattendeeFile;
    private GetAttendPeoplePresenter getAttendPeoplePresenter;
    private GetFormTypePresenter getFormTypePresenter;
    private GetShareInfoPresenter getShareInfoPresenter;
    private ActivityOrderPresenter orderPresenter;

    private List<EventTicket> ticketList = new ArrayList<EventTicket>();
    private TicketAdapter ticketAdapter;

    private boolean mViewBound=false;

    //获取参会人员
    private int pageNum = 1;//当前页码
    private String currentTime = "";//数据更新时间
    private String fromTime;//订单更新时间
    private int orderPageNum = 1;

    private static final int REQUECT_CODE_SDCARD = 2;

    //分享
    private String shareImgUrl = "";
    private String shareTitle = "";
    private String shareContent = "";
    private String shareUrl = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket, container, false);
        unbinder = ButterKnife.bind(this, view);
        mViewBound=true;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        eventName = bundle.getString("eventName");
        eventId = bundle.getInt("eventId");
        stType = bundle.getInt("stType");
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        isLoading();
        getFormType();
        getEventDetail();
        getTicketInfo();
        getAttendPeopleInfo();
//          getOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SYNC_SUCCESS)) {
            setTicketList();
        } else if (event.mMsg.equals(Common.ATTENDEE_PAGE)) {//下拉刷新的人数超出一页，采用分页获取
            pageNum = event.pageNumber;
            getAttendPeopleInfo();
        } else if (event.mMsg.equals(Common.SYNC_INFO_SUCCESS) || event.mMsg.equals(Common.SYNC_ATTEND_INFO_SUCCESS)) {
            auditAttendee();
            auditOrder();
        } else if (event.mMsg.equals(Common.AUDIT_ORDER)) {
            auditOrder();
        } else if (event.mMsg.equals(Common.AUDIT_ATTENDEE)) {
            auditAttendee();
        } else if (event.mMsg.equals(Common.CHECKIN_SUCCESS)) {
            getEventDetail();
            getTicketInfo();
        }else if(event.mMsg.equals(Common.SYNC_ATTEND_INFO_FAIED)){
            MyApplication.getInstance().getSynacAttends().put(eventId,false);
        }
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click, R.id.ll_audit_order, R.id.ll_audit_attendee, R.id.ll_page_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                if (NetUtil.isConnected(getActivity())) {
                    getShareInfoPresenter = new GetShareInfoPresenter(this);
                    getShareInfoPresenter.showShareInfo();
                }
                break;
            case R.id.ll_audit_order:
                Intent intent = new Intent(getActivity(), AuditOrder.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                break;
            case R.id.ll_audit_attendee:
                Intent intent1 = new Intent(getActivity(), AuditAttendeeActivity.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("type", 0);
                startActivity(intent1);
                break;
            case R.id.ll_page_status:
                isLoading();
                getTicketInfo();
                break;
        }
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        ImageUtils.loadImage(getActivity(), eventId);
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        // Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    private void auditAttendee() {
        List<Attends> auditAttends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.payStatuss.is(1)).and(Attends_Table.audits.is(1)).queryList();
        if (auditAttends.size() > 0) {
            llAuditAttendee.setVisibility(View.VISIBLE);
            if (auditAttends.size() > 99) {
                tvAuditAttendeeCount.setText("99+");
            } else {
                tvAuditAttendeeCount.setText(auditAttends.size() + "");
            }
        } else {
            llAuditAttendee.setVisibility(View.GONE);
        }
    }

    private void auditOrder() {
        List<Order> auditCount = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.payStatus.is(12)).queryList();
        if (auditCount.size() > 0) {
            llAuditOrder.setVisibility(View.VISIBLE);
            if (auditCount.size() > 99) {
                tvAuditOrderCount.setText(R.string.num_more);
            } else {
                tvAuditOrderCount.setText(auditCount.size() + "");
            }
        } else {
            llAuditOrder.setVisibility(View.GONE);
        }
    }

    private void getFormType() {
        getFormTypePresenter = new GetFormTypePresenter(this);
        getFormTypePresenter.getFormType();
        if (stType == 1) {
            getFormTypePresenter.getBadgeFormType();
        }
    }

    private void getEventDetail() {
        if (NetUtil.isConnected(getActivity())) {
            eventDetailPresenter = new EventDetailPresenter(this);
            eventDetailPresenter.getEventDetail();
        } else {
            //  Log.e("aaa", "fdf");
        }
    }

    private void getTicketInfo() {
        if (NetUtil.isConnected(getActivity())) {
            getTicketPresenter = new GetTicketPresenter(this);
            getTicketPresenter.getTicket();
        } else {
            setTicketList();
        }
//        https://www.bagevent.com/api/v1/event/getAttendeeJsonFile/1585042?access_token=ipad&access_secret=ipad_secret&sync_all=1
    }

    /**
     * 获得参会人员信息，如果有网络，则向服务器请求最新数据，否则直接从数据库读取
     */
    private void getAttendPeopleInfo() {
        if (NetUtil.isConnected(getActivity())) {
            if (TextUtils.isEmpty(currentTime())) {
                if(!MyApplication.getInstance().getSynacAttends().get(eventId)) {
                    //首次请求,直接下载参会人员文件
                    MyApplication.getInstance().getSynacAttends().put(eventId, true);
                    getattendeeFile = new GetAttendeeFilePresenter(this);
                    getattendeeFile.attendeeJsonFile();
                }
            } else {
                if (pageNum > 1) {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getAttendPeoples();
                } else {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getRefreshAttendPeople();
                }
            }
        }
    }

    private void getOrder() {
        if (NetUtil.isConnected(getActivity())) {
            if (TextUtils.isEmpty(orderUpdateTime())) {
                orderPresenter = new ActivityOrderPresenter(this);
                orderPresenter.getOrderFirsst();
            } else {
                orderPresenter = new ActivityOrderPresenter(this);
                orderPresenter.getOrderFromTime();
            }
        }
    }

    @Override
    public void onRefresh() {
        managerSwiperefresh.setRefreshing(true);
        getTicketInfo();
        getEventDetail();
    }

    private void setTicketList() {
        if (mViewBound){
            loadingFinished();
            managerSwiperefresh.setRefreshing(false);
            ticketList.clear();
            List<EventTicket> ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).queryList();
            ticketList.addAll(ticket);
            if (ticketAdapter == null) {
                initAdapter();
            } else {
                if (ticketList.size() > 0) {
                    ticketAdapter.setNewData(ticketList);
                    ticketAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void initAdapter() {
        if (ticketList.size() == 0) {
            llPageStatus.setVisibility(View.VISIBLE);
            rvTicket.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_ticket_record);
        } else {
            if (llPageStatus==null){
                llPageStatus.setVisibility(View.GONE);
            }
            rvTicket.setVisibility(View.VISIBLE);
        }
        ticketAdapter = new TicketAdapter(ticketList);
        ticketAdapter.openLoadAnimation();
        ticketAdapter.setOnItemChildClickListener(this);
        rvTicket.setAdapter(ticketAdapter);
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.start();
        managerSwiperefresh.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        if (llLoading!=null){
            llLoading.setVisibility(View.GONE);
        }
        loading.stop();
        managerSwiperefresh.setVisibility(View.VISIBLE);
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.drawable.share).into(ivRight);
        ivRight2.setVisibility(View.GONE);
        tvTitle.setSelected(true);
        tvTitle.setText(eventName);
        rvTicket.setLayoutManager(new LinearLayoutManager(getActivity()));
        managerSwiperefresh.setOnRefreshListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
    }

    @Override
    public Context mContext() {
        if (super.getContext() != null) {
            return super.getContext();
        } else {
            return AppUtils.getContext();
        }
    }

    @Override
    public String eventId() {
        return eventId + "";
    }

    @Override
    public String fromTime() {
        return orderUpdateTime();
    }

    @Override
    public int page() {
        return orderPageNum;
    }

    @Override
    public void showOrderSuccess(String response) {
        SyncOrderUtil orderUtil = new SyncOrderUtil(getActivity(), eventId, response);
        orderUtil.syncOrder();
    }

    @Override
    public void showOrderSuccess2(String response) {

    }

    @Override
    public void showOrderFailed(String errInfo) {
        EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_SUCCESS));
    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public void attendeeJsonFile(String jsonFile) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(getActivity(), jsonFile, eventId, stType);
        util.syncAttendeeFile();
    }

    @Override
    public void attendeeJsonFileFailed(String errInfo) {//下载文件失败改用分页进行同步参会人员
        MyApplication.getInstance().getSynacAttends().put(eventId,false);
//        Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();
//        if (TextUtils.isEmpty(currentTime())) {
//            getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
//            getAttendPeoplePresenter.getAttendPeoples();
//        } else {
//            getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
//            getAttendPeoplePresenter.getRefreshAttendPeople();
//        }
        // EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_SUCCESS));
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void showShareInfo(ShareInfoData response) {
        ShareInfoData.RespObjectBean share = response.getRespObject();
        shareContent = share.getContent();
        shareImgUrl = share.getImg();
        shareTitle = share.getTitle();
        shareUrl = share.getEventUrl();
        showShare(getActivity(), null, true);
    }

    @Override
    public void showShareInfoErr(String errInfo) {

    }

    @Override
    public void eventDetailSuccess(EventDetailData response) {
        Log.e("------------","活动详情成功");
        if (response.getRespObject() != null) {
            EventDetailData.RespObjectBean bean = response.getRespObject();
            DecimalFormat format = new DecimalFormat("0.00 ");
            try {
                tvCheckin.setText(bean.getCheckinCount() + " ");
                tvIncome.setText(format.format(bean.getIncome()) + " ");
                tvSignUp.setText(bean.getAttendeeCount() + " ");
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void eventDetailFailed(String errInfo) {
        Log.e("------------","活动详情失败");
        setTicketList();
    }

    @Override
    public String getPageNum() {
        return pageNum + "";
    }

    private String currentTime() {
        //根据eventId将currentTime从sp中取出
        currentTime = SharedPreferencesUtil.get(getContext(), "currentTime" + eventId, "");
        return currentTime;
    }

    @Override
    public Context getContext() {

        if (super.getContext() != null) {
            return super.getContext();
        } else {
            return AppUtils.getContext();
        }
    }

    private String orderUpdateTime() {
        fromTime = SharedPreferencesUtil.get(getActivity(), "orderTime" + eventId, "");
        return fromTime;
    }

    @Override
    public String getUpdateTime() {
        return currentTime;
    }

    @Override
    public void showSuccessInfo(String people) {
        Log.e("-------------","获取参会人员成功");
        SyncAttendeeUtil util = new SyncAttendeeUtil(mContext(), people, eventId, stType);
        util.syncAttendees();
    }


    @Override
    public void showFailInfo(String errInfo) {
        Log.e("-------------","获取参会人员失败");
        EventBus.getDefault().postSticky(new MsgEvent("success"));
    }


    @Override
    public void showTicketInfo(final TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(getActivity(), eventId, ticketInfo, true);
        util.syncTicket();
    }

    @Override
    public void showErrInfo(String errInfo) {
        setTicketList();
    }

    @Override
    public String detailEventId() {
        return eventId + "";
    }

    @Override
    public int sType() {
        return stType;
    }

    @Override
    public void showDetailInfo(FormType formType) {
        // Log.e("", "Get FormType Success");
    }

    @Override
    public void showDetailErrInfo(String errInfo) {
        //   Log.e("", "Get FormType err");
    }

    @Override
    public void showBadgeFormInfo(FormType formType) {

    }

    @Override
    public void showBadgeFormErrInfo(String errInfo) {

    }

    /**
     * 分享
     */

    private void showShare(Context context, String platformToShare, boolean showContentEdit) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        //    oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText(shareContent + " " + shareUrl);
                } else {
                    paramsToShare.setText(shareContent);
                }
                paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                paramsToShare.setTitle(shareTitle);
                paramsToShare.setTitleUrl(shareUrl);
                paramsToShare.setImageUrl(Constants.imgsURL + shareImgUrl);
                paramsToShare.setUrl(shareUrl); //微信不绕过审核分享链接
                paramsToShare.setSite(getString(R.string.app_name));  //QZone分享完之后返回应用时提示框上显示的名称
                paramsToShare.setSiteUrl("https://www.bagevent.com/");//QZone分享参数
                paramsToShare.setVenueName(getString(R.string.app_name));
            }
        });

        oks.addHiddenPlatform("ShortMessage");
        oks.addHiddenPlatform("Email");
        oks.addHiddenPlatform("WechatFavorite");
        oks.addHiddenPlatform("QZone");
        // 启动分享
        oks.show(context);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
        }
        mViewBound=false;
        Log.i("----------onDestroyView","onDestroyView");
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag("MeetingPersonFragment");
        OkHttpUtils.getInstance().cancelTag("GetFormType");
        OkHttpUtils.getInstance().cancelTag("GetBadgeFormType");
        OkHttpUtils.getInstance().cancelTag("GetEventDetail");
        OkHttpUtils.getInstance().cancelTag("GetTicketInfo");
        OkHttpUtils.getInstance().cancelTag("GetAttendPeopleInfo");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.ll_have_checkin:
                Log.e("ManagerFragment", "已签到");
//                if (ticketList.get(position).checkinCounts != 0) {
//                    Intent intent = new Intent(getActivity(), AuditAttendeeActivity.class);
//                    intent.putExtra("type", 1);
//                    intent.putExtra("eventId", eventId);
//                    intent.putExtra("ticketId", ticketList.get(position).ticketIds);
//                    intent.putExtra("checkin", 1);
//                    startActivity(intent);
//                }
                break;
            case R.id.ll_no_checkin:
                Log.e("ManagerFragment", "未签到");
//                if (ticketList.get(position).selledCounts - ticketList.get(position).checkinCounts != 0) {
//                    Intent intent1 = new Intent(getActivity(), AuditAttendeeActivity.class);
//                    intent1.putExtra("type", 1);
//                    intent1.putExtra("eventId", eventId);
//                    intent1.putExtra("ticketId", ticketList.get(position).ticketIds);
//                    intent1.putExtra("checkin", 0);
//                    startActivity(intent1);
//                }
                break;
        }
    }
}
