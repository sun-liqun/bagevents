package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.BaseFragment;
import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.activity_manager.detail.AddAttendPeople;
import com.bagevent.activity_manager.detail.AttendPeopleDetailInfo;
import com.bagevent.activity_manager.detail.AuditAttendeeActivity;
import com.bagevent.activity_manager.manager_fragment.adapter.MeetingAdapter;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AddPeopleCheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendPeoplePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendeeFilePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.home.data.ExercisingData;
import com.bagevent.home.home_interface.presenter.GetExercisingPresenter;
import com.bagevent.home.home_interface.view.GetExercisingView;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.RecyclerViewNoBugLinearLayoutManager;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.SyncAttendeeUtil;
import com.bagevent.util.dbutil.SyncEventUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.view.ItemDragAndSwipeCallback;
import com.bagevent.view.OnItemSwipeListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zwj on 2016/5/30.
 * <p/>
 * 参会人员
 */
public class MeetingPersonFragment extends BaseFragment implements GetAttendPeopleView, SwipeRefreshLayout.OnRefreshListener, TextWatcher, CheckInView, AddPeopleCheckInView,
        GetFormTypeView, GetTicketInfoView, FragmentBackHandler, GetAttendeeJsonFileView, OnItemSwipeListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

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
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_loading_msg)
    TextView tv_loading_msg;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_search_cancle)
    Button btnSearchCancle;
    @BindView(R.id.fl_search)
    AutoFrameLayout flSearch;
    @BindView(R.id.rv_attendee)
    RecyclerView rvAttendee;
    @BindView(R.id.sfl_attendee)
    SwipeRefreshLayout sflAttendee;
    @BindView(R.id.v_transparent)
    View vTransparent;
    Unbinder unbinder;

    public static final int PAYSTATUS = 1;
    @BindView(R.id.tv_audit_order_num)
    TextView tvAuditOrderNum;
    @BindView(R.id.ll_audit_order)
    AutoLinearLayout llAuditOrder;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.iv_barcode_checkin)
    ImageView ivBarcodeCheckin;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.fm_attendee)
    AutoFrameLayout fmAttendee;
    @BindView(R.id.tv_retry)
    TextView tvRetry;

    private NormalAlertDialog exitDialog;
    private GetTicketPresenter getTicketPresenter;
    private GetFormTypePresenter getFormTypePresenter;
    private GetAttendeeFilePresenter getattendeeFile;
    private GetAttendPeoplePresenter getAttendPeoplePresenter;
    private CheckInPresenter checkInPresenter;
    private AddPeopleCheckInPresenter addPeopleCheckInPresenter;
//    private GetExercisingPresenter getExPresenter;

    private int eventId = -1;
    private int attendId = -1;
    private int checkStatus = -1;
    private String refcode;
    private String auto = "";//判断是否为扫描登录
    private int stType = -1;// 0--普通活动,1---学术会议
    private String checkInTimes;
    private int pageNum = -1;
    private int offset = 0;
    private int limit = 50;
    private Paint paint;
    private Paint paint2;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private MeetingAdapter meetingAdapter;
    private List<Attends> attendsList = new ArrayList<Attends>();
    private List<Attends> search = new ArrayList<Attends>();


    private boolean isSearch = false;
    private boolean isSwipe = false;
    private String result;//搜索关键词
    private InputMethodManager inputMethodManager;//软键盘

    private static final int REQUECT_CODE_SDCARD = 2;
    private String userId = "";

    private Handler handler = new Handler(Looper.getMainLooper());

    private boolean barcodeLogin=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meeting_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        eventId = bundle.getInt("eventId");
        auto = bundle.getString(Common.BARCODE_LOGIN);
        stType = bundle.getInt("stType");
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        EventBus.getDefault().register(this);
        initView();
        isLoading();
        //是否为扫码登录
        isBarcodeLogin();
        if (!NetUtil.isConnected(getActivity())) {
            attendsList.clear();
            offset = 0;
            getAttendeeList(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSearch) {
//            Log.e("aaa", search.size() + "f");
            if (meetingAdapter != null) {
                meetingAdapter.replaceData(search);
                //  meetingAdapter.setNewData(search);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag("MeetingPersonFragment");
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos, int direction) {
        if (NetUtil.isConnected(getActivity())){
            if (meetingAdapter != null) { //1
                if (direction == ItemTouchHelper.END) {//签到
                    Attends attends = null;
                    if (search.size() > 0) {
                        attends = search.get(pos);
                    } else {
                        attends = attendsList.get(pos);
                    }
                    if(attends.buyingGroupId!=0){
                        if(attends.bgState==1){
                            checkInAction(attends, true);
                        }else{
                            TosUtil.show("该参会人员拼团还未成功！");
                        }
                    }else{
                        checkInAction(attends, true);
                    }

                } else if (direction == ItemTouchHelper.START) {//取消签到
                    Attends attends = null;
                    if (search.size() > 0) {
                        attends = search.get(pos);
                    } else {
                        attends = attendsList.get(pos);
                    }
                    if(attends.buyingGroupId!=0){
                        if(attends.bgState==1){
                            checkInAction(attends, false);
                        }else{
                            TosUtil.show("该参会人员拼团还未成功！");
                        }
                    }else{
                        checkInAction(attends, false);
                    }
                }
                meetingAdapter.notifyItemChanged(pos);
            }
        }else {
            Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
            if (meetingAdapter != null) {
                meetingAdapter.notifyItemChanged(pos);
            }
        }
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

//        if (dX > 0) {
//            canvas.drawColor(ContextCompat.getColor(getActivity(), R.color.color_light_organge));
//            canvas.drawText("签到", dX - 100, 120, paint);
//            Resources resources = getResources();
//            Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.checkin_left);
//            canvas.drawBitmap(bmp, dX - 155, 50, paint);
//        } else {
//            canvas.drawColor(ContextCompat.getColor(getActivity(), R.color.color_light_red));
//            canvas.drawText("取消签到", 100, 120, paint2);
//            Resources resources = getResources();
//            Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.attendeecheckincancel);
//            canvas.drawBitmap(bmp, 128, 50, paint);
//        }
    }

    @Override
    public void onRefresh() {
        isSwipe = true;
        offset = 0;
        meetingAdapter.setEnableLoadMore(false);
        if (NetUtil.isConnected(getActivity())) {
            getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
            getAttendPeoplePresenter.getRefreshAttendPeople();
        }
        meetingAdapter.setEnableLoadMore(true);
        sflAttendee.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        sflAttendee.setEnabled(false);
        offset = offset + limit;
        getAttendeeList(offset);
        sflAttendee.setEnabled(true);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SYNC_ATTEND_INFO_SUCCESS)) {
            if (!isSearch) {
                attendsList.clear();
                if (meetingAdapter!=null){
                    meetingAdapter.notifyDataSetChanged();
                }
                if (!isSwipe) {
                    getAttendeeList(offset);
                } else {
                    isSwipe = false;
                    offset = 0;
                    getAttendeeList(0);
                }
            }
        } else if (event.mMsg.equals("attendeeSearch")) {
            llLoading.setVisibility(View.GONE);
            if (search.size() > 0) {
                llPageStatus.setVisibility(View.GONE);
                vTransparent.setVisibility(View.GONE);
                fmAttendee.setVisibility(View.VISIBLE);
                meetingAdapter.setAttendList(search);
                meetingAdapter.setNewData(search);
                meetingAdapter.setEnableLoadMore(false);
            } else {
                vTransparent.setVisibility(View.GONE);
                fmAttendee.setVisibility(View.GONE);
                llPageStatus.setVisibility(View.VISIBLE);
                tvRetry.setVisibility(View.GONE);
                llPageStatus.setClickable(false);
                Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
                tvPageStatus.setText(R.string.no_find_attendee);
            }
        } else if (event.mMsg.equals(Common.CHECKIN_SUCCESS)) {
            if (search.size() > 0) {
                Attends attends = search.get(event.pageCount);
                attends.checkins = event.pageNumber;
            } else {
                Attends attends = attendsList.get(event.pageCount);
                attends.checkins = event.pageNumber;
            }
            meetingAdapter.notifyItemChanged(event.pageCount);
        } else if (event.mMsg.equals(Common.MODIFY_ATTNEDEE_SUCCESS)) {
            if (search.size() > 0) {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(event.pageNumber)).querySingle();
                search.set(event.pageCount, attends);
            } else {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(event.pageNumber)).querySingle();
                attendsList.set(event.pageCount, attends);
            }
            meetingAdapter.notifyItemChanged(event.pageCount);
        } else if (event.mMsg.equals(Common.MODIFY_ATTENDEE_REFCODE_SUCCESS)) {
            if (search.size() > 0) {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(event.changeName)).querySingle();
                search.set(event.pageCount, attends);
            } else {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(event.changeName)).querySingle();
                attendsList.set(event.pageCount, attends);
            }
            meetingAdapter.notifyItemChanged(event.pageCount);
        } else if (event.mMsg.equals(Common.ADD_ATTENDEE_SUCCESS)) {
            if (NetUtil.isConnected(getActivity())) {
                getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                getAttendPeoplePresenter.getRefreshAttendPeople();
            } else {
                attendsList.clear();
                if (meetingAdapter!=null){
                    meetingAdapter.notifyDataSetChanged();
                }
                offset = 0;
                getAttendeeList(offset);
            }
        } else if (event.mMsg.equals(Common.AUDIT_ATTENDEE)) {
            getAuditAttendee();
            if (NetUtil.isConnected(getActivity())) {
                getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                getAttendPeoplePresenter.getRefreshAttendPeople();
            }
        } else if (event.mMsg.equals(Common.CHANGE_TICKET)) {
            if (search.size() > 0) {
                Attends attends = search.get(event.pageCount);
                attends.ticketIds = event.pageNumber;
            } else {
                Attends attends = attendsList.get(event.pageCount);
                attends.ticketIds = event.pageNumber;
            }
            meetingAdapter.notifyItemChanged(event.pageCount);
        } else if (event.mMsg.equals("refreshAttend")) {
            onRefresh();
        }
        if (!event.mMsg.equals(Common.SYNC_ATTEND_INFO_SUCCESS)) {
            EventBus.getDefault().removeStickyEvent(event);
        } else if (event.mMsg.equals(Common.SYNC_ATTEND_INFO_FAIED)) {
            MyApplication.getInstance().getSynacAttends().put(eventId, false);
        }
    }


    @OnClick({R.id.ll_title_back, R.id.ll_right_click, R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle, R.id.ll_audit_order, R.id.iv_barcode_checkin, R.id.ll_page_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                if (auto != null && auto.contains(Common.BARCODE_LOGIN)) {
                    exitDialog.show();
                }else {
                    AppManager.getAppManager().finishActivity();
                }
                break;
            case R.id.ll_right_click: //添加参会人员
                Intent intent = new Intent(getActivity(), AddAttendPeople.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("stType", stType);
                startActivity(intent);
                break;
            case R.id.ll_search:
                isSearch = true;
                etSearch.requestFocus();
                etSearch.setFocusable(true);
                vTransparent.setVisibility(View.VISIBLE);
                llAuditOrder.setVisibility(View.GONE);
                llCommonTitle.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
                flSearch.setVisibility(View.VISIBLE);
                inputMethodManager.showSoftInput(etSearch, 0);
                sflAttendee.setEnabled(false);
                if (meetingAdapter != null) {
                    meetingAdapter.setEnableLoadMore(false);
                }
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                search.clear();
                vTransparent.setVisibility(View.VISIBLE);
                ivSearchClear.setVisibility(View.GONE);
                if (meetingAdapter != null) {
                    meetingAdapter.setAttendList(attendsList);
                    meetingAdapter.setNewData(attendsList);
                }
                break;
            case R.id.btn_search_cancle:
                cancelSearch();
                break;
            case R.id.ll_audit_order: //参会人员审核
                Intent intent1 = new Intent(getActivity(), AuditAttendeeActivity.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("type", 0);
                startActivity(intent1);
                break;
            case R.id.iv_barcode_checkin:
                Intent checkinIntent = new Intent(getActivity(), BarCodeCheckIn.class);
                checkinIntent.putExtra("eventId", eventId);
                if(barcodeLogin){
                    checkinIntent.putExtra("isBarcodeLogin", 1);
                }
                startActivity(checkinIntent);
                break;
            case R.id.ll_page_status:
                isLoading();
                getAttendPeopleInfo();
                break;
        }
    }

    private void attendeeIdCheckIn() {
//        SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus), Attends_Table.checkinTimes.is(checkInTimes), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        if (NetUtil.isConnected(getActivity())) {
            checkInPresenter = new CheckInPresenter(this);
            checkInPresenter.attendCheckIn();
        }
    }

    private void refCodeCheckin() {
//        SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus), Attends_Table.checkinTimes.is(checkInTimes), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(refcode)).execute();
        if (NetUtil.isConnected(getActivity())) {
            addPeopleCheckInPresenter = new AddPeopleCheckInPresenter(this);
            addPeopleCheckInPresenter.addPeopleCheckIn(refcode);
        }
    }

    private void checkInAction(Attends attends, boolean isCheckin) {
        LogUtil.i("------------attqqends"+attends.attendId+"checkaian"+attends.checkins);
        if (attends.attendId != 0) {
            if (isCheckin) {
                if (attends.checkins != 1) {
                    checkInTimes = checkInTime();
                    attendId = attends.attendId;
                    checkStatus = 1;
                    attends.checkins = checkStatus;
                    attendeeIdCheckIn();
                }
            } else {
                if (attends.checkins != 0) {
                    checkInTimes = checkInTime();
                    attendId = attends.attendId;
                    checkStatus = 0;
                    attends.checkins = checkStatus;
                    attendeeIdCheckIn();
                }
            }
        } else {
            if (isCheckin) {
                if (attends.checkins != 1) {
                    checkInTimes = checkInTime();
                    refcode = attends.refCodes;
                    checkStatus = 1;
                    attends.checkins = checkStatus;
                    refCodeCheckin();
                }
            } else {
                if (attends.checkins != 0) {
                    checkInTimes = checkInTime();
                    refcode = attends.refCodes;
                    checkStatus = 0;
                    attends.checkins = checkStatus;
                    refCodeCheckin();
                }
            }
        }

    }

    private void cancelSearch() {
        isSearch = false;
        etSearch.setText("");
        search.clear();
        getAuditAttendee();
        sflAttendee.setEnabled(true);
        vTransparent.setVisibility(View.GONE);
        flSearch.setVisibility(View.GONE);
        llCommonTitle.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.VISIBLE);
        fmAttendee.setVisibility(View.VISIBLE);
        if (meetingAdapter != null) {
            meetingAdapter.setEnableLoadMore(true);
            meetingAdapter.setAttendList(attendsList);
            meetingAdapter.setNewData(attendsList);
        }
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }

    private void getAttendPeopleInfo() {
        if (NetUtil.isConnected(getActivity())) {
            if (TextUtils.isEmpty(currentTime())) {
                //首次请求,直接下载参会人员文件
                if (!MyApplication.getInstance().getSynacAttends().get(eventId, false)) {
                    getattendeeFile = new GetAttendeeFilePresenter(this);
                    getattendeeFile.attendeeJsonFile();
                }

            } else {
                if (pageNum > 1) {//分页下载,pageNum默认为-1 后面没有对其进行赋值
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getAttendPeoples();
                } else {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getRefreshAttendPeople();
                }
            }
        }
    }

    private void getAuditAttendee() {
        List<Attends> auditAttends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.payStatuss.is(PAYSTATUS)).and(Attends_Table.audits.is(1)).queryList();
        if (auditAttends.size() > 0) {
            llAuditOrder.setVisibility(View.VISIBLE);
            tvMark.setText(R.string.audit_wait_person1);
            if (auditAttends.size() > 99) {
                tvAuditOrderNum.setText(R.string.num_more);
            } else {
                tvAuditOrderNum.setText(auditAttends.size() + "");
            }
        } else {
            if (llAuditOrder != null) {
                llAuditOrder.setVisibility(View.GONE);
            }
        }
    }

    private void getAttendeeList(int offset) {
        loadFinished();
        getAuditAttendee();
        List<Attends> attends = new Select().from(Attends.class)
                .where(Attends_Table.eventId.is(eventId)) //2032869
                .and(Attends_Table.payStatuss.is(PAYSTATUS))
                .and(OperatorGroup.clause().and(Attends_Table.audits.is(0)).or(Attends_Table.audits.is(2)))
                .orderBy(Attends_Table.strSort, true)
                .offset(offset)
                .limit(limit)
                .queryList();
        attendsList.addAll(attends);
//        Log.e("attendsList", attendsList.size() + "");
        if (meetingAdapter == null) {
            initAdapter();
        } else {
            if (attends.size() > 0) {
                meetingAdapter.setNewData(attendsList);
                if (attends.size() < limit) {
                    meetingAdapter.loadMoreComplete();
                    meetingAdapter.loadMoreEnd();
                } else {
                    meetingAdapter.loadMoreComplete();
                }
            } else {
                meetingAdapter.loadMoreEnd();
            }
        }
    }

    private String checkInTime() {
        Long time = System.currentTimeMillis();
        return time.toString();
    }

    private String currentTime() {
        return SharedPreferencesUtil.get(getActivity(), "currentTime" + eventId, "");
    }

    private void setDialog() {
        exitDialog = new NormalAlertDialog.Builder(getActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.autologin_exit))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        exitDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        exitDialog.dismiss();
                        SharedPreferencesUtil.clear(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        AppManager.getAppManager().finishAllActivity();
//                        AppManager.getAppManager().finishActivity();
                    }
                })
                .build();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        result = s.toString();
        search.clear();
        if (TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.GONE);
        } else {
            ivSearchClear.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Attends> seResult = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId))
                            .and(Attends_Table.payStatuss.is(PAYSTATUS))
                            .and(OperatorGroup.clause().and(Attends_Table.audits.is(0))
                                    .or(Attends_Table.audits.is(2)))
                            .and(OperatorGroup.clause().and(Attends_Table.names.like("%" + result + "%"))
                                    .or(Attends_Table.pinyinNames.like("%" + result + "%"))
                                    .or(Attends_Table.gsonUser.like("%" + result + "%")))
                            .orderBy(Attends_Table.strSort, true).queryList();

                    search.addAll(seResult);
                    EventBus.getDefault().post(new MsgEvent("attendeeSearch"));
                }
            });
            thread.start();
        }
    }

    private void noRecord() {
        if (attendsList.size() == 0) {
            llPageStatus.setVisibility(View.VISIBLE);
            fmAttendee.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText(R.string.none_people);
        } else {
            llPageStatus.setVisibility(View.GONE);
            fmAttendee.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        noRecord();
        if (attendsList!=null&&attendsList.size() > 0) {
            meetingAdapter = new MeetingAdapter(attendsList);
            // meetingAdapter.openLoadAnimation();
            mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(meetingAdapter);
            mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
            mItemTouchHelper.attachToRecyclerView(rvAttendee);
            mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
            meetingAdapter.setOnLoadMoreListener(this, rvAttendee);
            meetingAdapter.enableSwipeItem();
            meetingAdapter.setOnItemSwipeListener(this);
            meetingAdapter.setOnItemClickListener(this);
            rvAttendee.setAdapter(meetingAdapter);
        }
    }

    private void isBarcodeLogin() {
        if (auto != null && auto.contains(Common.BARCODE_LOGIN) || auto.contains("no")) {
            barcodeLogin=true;
            setDialog();
            if (NetUtil.isConnected(getActivity())) {
//                getExPresenter = new GetExercisingPresenter(this);
//                getExPresenter.getExercise();
                getAttendPeopleInfo();
            } else {
                attendsList.clear();
                if (meetingAdapter!=null){
                    meetingAdapter.notifyDataSetChanged();
                }
                getAttendeeList(0);
            }
            String formType = (String) NetUtil.readObject(getActivity(), Constants.FORM_FILE_NAME + eventId + "");
            if (formType == null) {
                getFormTypePresenter = new GetFormTypePresenter(this);
                getFormTypePresenter.getFormType();
                if (stType == 1) {
                    getFormTypePresenter.getBadgeFormType();
                }
            }
            getTicketInfo();
        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        fmAttendee.setVisibility(View.GONE);
    }

    private void loadFinished() {
        llLoading.setVisibility(View.GONE);
        fmAttendee.setVisibility(View.VISIBLE);
    }

    private void initView() {
        tvTitle.setText(R.string.attendee);
        ivRight2.setVisibility(View.GONE);
        tv_loading_msg.setVisibility(View.VISIBLE);
        if (stType == 1) {
            ivRight.setVisibility(View.GONE);
            llRightClick.setClickable(false);
        }
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.drawable.ic_add_people).into(ivRight);
//        rvAttendee.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewNoBugLinearLayoutManager linearLayoutManager =
                new RecyclerViewNoBugLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvAttendee.setLayoutManager(linearLayoutManager);
        // rvAttendee.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.item)));
        etSearch.addTextChangedListener(this);
        sflAttendee.setOnRefreshListener(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(28);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.WHITE);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(28);
        paint2.setColor(Color.WHITE);
    }


    @Override
    public Context mContext() {
        return getActivity();
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String checkInStatus() {
        return checkStatus + "";
    }

    @Override
    public String checkInupdateTime() {
        return checkInTimes;
    }

    @Override
    public void showCheckInSuccess(CheckIn checkIn) {
        SQLite.update(Attends.class).set(Attends_Table.isSync.is(Constants.SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(refcode)).execute();
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
        return checkInTimes;
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {//attendId签到成功
        Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).querySingle();
        if (attends.checkins == 0) {
            checkStatus=1;
        } else {
            checkStatus=0;
        }
        if (attends.attendId != 0) {
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus), Attends_Table.checkinTimes.is(checkInTimes), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
        } else {
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus), Attends_Table.checkinTimes.is(checkInTimes), Attends_Table.isSync.is(Constants.NOTSYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(refcode)).execute();
        }
        SQLite.update(Attends.class).set(Attends_Table.isSync.is(Constants.SYNC)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
    }

    @Override
    public void showCheckInFailed(String errInfo) {
//        Toast.makeText(getActivity(),errInfo,Toast.LENGTH_SHORT).show();
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

    /**
     * 首次文件下载失败
     *
     * @param errInfo
     */
    @Override
    public void attendeeJsonFileFailed(final String errInfo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                llLoading.setVisibility(View.GONE);
                noRecord();
                Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public String getPageNum() {
        return pageNum + "";
    }

    @Override
    public String getUpdateTime() {
        return currentTime();
    }

    /**
     * 刷新成功的回调
     *
     * @param people
     */
    @Override
    public void showSuccessInfo(final String people) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(getActivity(), people, eventId, stType);
        util.syncAttendees();
    }

    @Override
    public void showFailInfo(String errInfo) {
        getAttendeeList(0);
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

    }

    @Override
    public void showDetailErrInfo(String errInfo) {

    }

    @Override
    public void showBadgeFormInfo(FormType formType) {

    }

    @Override
    public void showBadgeFormErrInfo(String errInfo) {

    }

    /**
     * 获得门票信息
     */
    private void getTicketInfo() {
        if (NetUtil.isConnected(getActivity())) {
            getTicketPresenter = new GetTicketPresenter(this);
            getTicketPresenter.getTicket();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrInfo(String errInfo) {

    }

    @Override
    public void showTicketInfo(final TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(getActivity(), eventId, ticketInfo, false);
        util.syncTicket();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Attends attends = null;

        if (isSearch) {
            if (search.size() > 0) {
                attends = search.get(position);
            }
        } else if (attendsList.size() > 0) {
            attends = attendsList.get(position);
        }

        if (attends != null) {
            Intent intent = new Intent(getActivity(), AttendPeopleDetailInfo.class);
            intent.putExtra("eventId", eventId);
            //Log.e("meet attendId",attends.attendId+"");
            intent.putExtra("attendId", attends.attendId);
            intent.putExtra("ref_code", attends.refCodes);
            intent.putExtra("position", position);
            intent.putExtra("remark", attends.notes);
            intent.putExtra("detailType", 0);
            intent.putExtra("orderId", attends.orderIds);
            intent.putExtra("stType", stType);
            intent.putExtra("checkins",attends.checkins);

            //   Log.e("aaaa",attends.notes);
            startActivity(intent);
        }

    }

    @Override
    public boolean onBackPressed() {
        if (llCommonTitle.getVisibility() == View.GONE) {
            cancelSearch();
            return true;
        } else {
            return BackHandlerHelper.handleBackPress(this);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag("GetAttendPeopleInfo");
        OkHttpUtils.getInstance().cancelTag("GetTicketInfo");
        OkHttpUtils.getInstance().cancelTag("GetFormType");
        OkHttpUtils.getInstance().cancelTag("AttendeeCheckIn");
    }

//    @Override
//    public String getUserId() {
//        return userId;
//    }



//    @Override
//    public void showSuccess(ExercisingData data) {
//        SyncEventUtil util = new SyncEventUtil(getActivity(), Integer.parseInt(userId), data);
//        util.startSyncEventUtil();
//    }
//
//    @Override
//    public void showFailed(String errInfo) {
//
//    }

//    @Override
//    public void showHideView() {
//
//    }
//
//    @Override
//    public void hideView() {
//
//    }
}
