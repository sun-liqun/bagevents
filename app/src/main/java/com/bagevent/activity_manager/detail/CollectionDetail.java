package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendPeoplePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendeeFilePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.CollectChild;
import com.bagevent.db.CollectChildQuery;
//import com.bagevent.db.CollectChild_Table;
import com.bagevent.db.CollectChild_Table;
import com.bagevent.home.adapter.CollectionDetailAdapter;
import com.bagevent.home.data.CollectDetailData;
import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.home_interface.presenter.EventDetailPresenter;
import com.bagevent.home.home_interface.presenter.ExportCollectPresenter;
import com.bagevent.home.home_interface.presenter.GetCollectPresenter;
import com.bagevent.home.home_interface.view.ExportCollectView;
import com.bagevent.home.home_interface.view.GetCollectChildView;
import com.bagevent.home.home_interface.view.GetEventDetailView;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncAttendeeUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.util.image_download.ImageUtils;
import com.bagevent.view.LoadMoreListView;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.wevey.selector.dialog.MDEditDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by zwj on 2016/7/12.
 */
public class CollectionDetail extends BaseActivity implements View.OnClickListener, GetEventDetailView, GetAttendPeopleView, GetAttendeeJsonFileView, GetFormTypeView, GetTicketInfoView, GetCollectChildView, SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnRefreshListener, TextWatcher, ExportCollectView {
    private AutoLinearLayout rl_collect_title;
    private AutoLinearLayout ll_collect_back;
    private TextView tv_collect;
    private AutoLinearLayout ll_collect;
    private TextView tv_collect_count;
    private AutoLinearLayout ll_collect_search;
    private AutoLinearLayout ll_collect_email;
    private AutoRelativeLayout ll_collect_title;
    private AutoLinearLayout ll_collect_search_title;
    private SwipeRefreshLayout refresh_collect_detail;
    private LoadMoreListView lv_collect_detail;
    private SwipeRefreshLayout refresh_none_collect_people;
    private AutoLinearLayout ll_collect_parent;

    //搜索
    private LoadMoreListView lv_collect_search;
    private EditText et_search;
    private ImageView iv_search_clear;
    private Button btn_search_cancle;
    private CollectionDetailAdapter resultAdapter;
    private LinearLayout empty_view;
    private InputMethodManager inputMethodManager;//软键盘

    private int eventId = -1;
    private int collectionId = -1;
    private String collectionName = "";

    private GetCollectPresenter getCollectPresenter;
    private List<CollectChild> collect = new ArrayList<CollectChild>();//将数据存到list中，然后插入数据库
    private List<CollectDetailData.RespObjectBean.CheckInListBean> bean;
    private List<CollectDetailData.RespObjectBean.CheckInListBean> resultBean = new ArrayList<CollectDetailData.RespObjectBean.CheckInListBean>();
    private CollectionDetailAdapter adapter;

    private ExportCollectPresenter presenter;
    private String email = "";

    private int start = 0;//数据库查询偏移量
    private int resultStart = 0;
    private boolean isSearch = false;//标记是否为搜索的上拉
    private String key = "";
    private static final int LIMIT = 50;
    private String total = "";

    private GetFormTypePresenter formPresenter;

    /**
     * 分页
     */
    private int pageNum = 1;//当前页码
    private int pageCounts = -1;//总页数
    private int pageCount = -1;//总页数
    private String currentTime = "";//数据更新时间
    private int attendeeCount = -1;
    private GetAttendPeoplePresenter getAttendPeoplePresenter;
    private GetAttendeeFilePresenter getattendeeFile;
    private int show = -1;

    /**
     *
     */
    private String isManager = "";//标识是哪个页面进入此页面,collect_manger为采集点管理页面进入,collect_child为首页采集点进入
    private AutoLinearLayout ll_collect_sweep;//标题栏扫描框
    private AutoFrameLayout fm_collect_sweep;//底部扫描框
    private ImageView iv_collect_sweep;
    private TextView iv_collect_set;
    private MDEditDialog exportDialog;
    private boolean isFromCollectBarcode = false;//判断是否为eventbus事件触发
    private static final int REQUECT_CODE_SDCARD = 2;

    /**
     * 获得活动详情
     */
    private EventDetailPresenter eventDetailPresenter;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_collection);
//        EventBus.getDefault().register(this);
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId", 0); //2170092
//        collectionId = intent.getIntExtra("collectionId", 0); //2158
//        collectionName = intent.getStringExtra("collectionName");
//        isManager = intent.getStringExtra(Common.COLLECT);
//        initView();
//        autoRefreshAnimation();
//        //initData();
////        if (isManager.equals(Common.COLLECT_CHILD)) {
////            getEventDetail();
////        } else {
////            initData();
////        }
//        isFromManager();
//        setListener();
//        exportDialog();
//        getFormType();
//    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        ImageUtils.loadImage(CollectionDetail.this, eventId);
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
    }

    @Override
    protected void onResume() {

        if (isManager.equals(Common.COLLECT_CHILD)) {
            getEventDetail();
        } else {
            refreshData();
        }
        isFromCollectBarcode = true;
        super.onResume();
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_collection);
        EventBus.getDefault().register(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0); //2170092
        collectionId = intent.getIntExtra("collectionId", 0); //2158
        collectionName = intent.getStringExtra("collectionName");
        isManager = intent.getStringExtra(Common.COLLECT);
        initView();
        autoRefreshAnimation();
        //initData();
//        if (isManager.equals(Common.COLLECT_CHILD)) {
//            getEventDetail();
//        } else {
//            initData();
//        }
        isFromManager();
        setListener();
        exportDialog();
        getFormType();
    }

    /**
     * 根据活动详情判断是否需要同步参会人员
     */
    private void getEventDetail() {
        if (NetUtil.isConnected(this)) {
            eventDetailPresenter = new EventDetailPresenter(this);
            eventDetailPresenter.getEventDetail();
        } else {
            setListFromHome();
        }
    }

    private void setListFromHome() {
        List<Attends> attendses = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).queryList();
        if (attendses.size() >= attendeeCount) {//当本地参会人员和线上一样时,则显示采集人员列表
            initData();
        } else {
            show = 1;
            refresh_collect_detail.setVisibility(View.GONE);
            refresh_none_collect_people.setVisibility(View.VISIBLE);
            getTicketInfo();
            getAttendPeopleInfo();
        }
    }

    private void autoRefreshAnimation() {
        if (NetUtil.isConnected(this)) {
            refresh_collect_detail.post(new Runnable() {
                @Override
                public void run() {
                    refresh_collect_detail.setRefreshing(true);
                }
            });
        }
    }

    private void getFormType() {
        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
        if (formType == null) {
            if (NetUtil.isConnected(this)) {
                formPresenter = new GetFormTypePresenter(this);
                formPresenter.getFormType();
            }
        }
    }

    private void getTicketInfo() {
        if (NetUtil.isConnected(this)) {
            GetTicketPresenter getTicketPresenter = new GetTicketPresenter(this);
            getTicketPresenter.getTicket();
        }
    }

    private void getAttendPeopleInfo() {
        if (NetUtil.isConnected(this)) {
            if (TextUtils.isEmpty(currentTime())) {
                //首次请求,直接下载参会人员文件
                getattendeeFile = new GetAttendeeFilePresenter(this);
                getattendeeFile.attendeeJsonFile();
            } else {
                if (pageNum > 1) {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getAttendPeoples();
                } else {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getRefreshAttendPeople();
                }
            }
        } else {
            initData();
        }
    }

    private void setListview() {

//        List<Attends> tem = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.names.is("a")).queryList();
//        Log.e("tem", "name=" + tem.size());
//
//        for (int i = 0; i < tem.size(); i++) {
//            Log.e("tem", "name=" + tem.get(i).names + "," + "attendeeId=" + tem.get(i).attendId);
//        }

        if (refresh_collect_detail.isRefreshing()) {
            refresh_collect_detail.setRefreshing(false);
        }
        start = 0;
        if (!NetUtil.isConnected(this) || TextUtils.isEmpty(total) || isFromCollectBarcode) {//当无网络的时候,采集点的人数以数据库中的为准,否则以网络返回的人数为准
            List<CollectChild> children = new Select().from(CollectChild.class).where(CollectChild_Table.eventId.is(eventId)).and(CollectChild_Table.collectionId.is(collectionId)).and(CollectChild_Table.isLegal.isNot(1)).queryList();
            total = children.size() + "";
            LogUtil.e("当无网络的时候，采集的数据为" + total);
        }
        SQLite.select(Attends_Table.attendId, Attends_Table.names, Attends_Table.pinyinNames,
                Attends_Table.avatars, CollectChild_Table.attendeeCheckInTime).from(CollectChild.class)
                .leftOuterJoin(Attends.class)
                .on(Attends_Table.barcodes.withTable().eq(CollectChild_Table.attendeeBarcode.withTable()),
                        Attends_Table.eventId.withTable().eq(CollectChild_Table.eventId.withTable()))
                .where(CollectChild_Table.collectionId.is(collectionId))
                .and(Attends_Table.names.isNotNull())
                .and(CollectChild_Table.isLegal.isNot(1))
                .offset(start)
                .limit(LIMIT)
                .async()
                .queryResultCallback(new QueryTransaction.QueryResultCallback<CollectChild>() {
                    @Override
                    public void onQueryResult(@NonNull QueryTransaction<CollectChild> transaction, @NonNull CursorResult<CollectChild> tResult) {
                        List<CollectChildQuery> mList = tResult.toCustomListClose(CollectChildQuery.class);
                        LogUtil.e("数据库查询的采集数为" + mList.size());
                        start += LIMIT;
                        bean = new ArrayList<CollectDetailData.RespObjectBean.CheckInListBean>();
                        if (mList != null && mList.size() > 0) {
                            show = 0;
                            refresh_collect_detail.setVisibility(View.VISIBLE);
                            refresh_none_collect_people.setVisibility(View.GONE);
                            for (int i = 0; i < mList.size(); i++) {
                                CollectDetailData.RespObjectBean.CheckInListBean check = new CollectDetailData.RespObjectBean.CheckInListBean();
                                check.setAttendeeId(mList.get(i).attendId);
                                check.setAttendeeCheckInTime(mList.get(i).attendeeCheckInTime);
                                check.setAttendeeImg(mList.get(i).avatars);
                                check.setAttendeeName(mList.get(i).names);
                                check.setAttendeePinyinName(mList.get(i).pinyinNames);
                                bean.add(check);
                            }
                            sortList(bean);
                            tv_collect_count.setText(total);
                            adapter = new CollectionDetailAdapter(bean, CollectionDetail.this);
                            lv_collect_detail.setAdapter(adapter);
                        } else {
                            show = 1;
                            refresh_collect_detail.setVisibility(View.GONE);
                            refresh_none_collect_people.setVisibility(View.VISIBLE);
                        }
                        isFromCollectBarcode = false;//使用完将状态置为false
                    }
                }).execute();

    }

    private void initData() {
        tv_collect.setText(collectionName);
        if (NetUtil.isConnected(this)) {
            getCollectPresenter = new GetCollectPresenter(this);
            getCollectPresenter.getCollectChild();
        } else {
            List<CollectChild> mList = new Select().from(CollectChild.class).where(CollectChild_Table.eventId.is(eventId)).and(CollectChild_Table.collectionId.is(collectionId)).queryList();
            if (mList.size() > 0) {
                total = mList.size() + "";
            }
            setListview();
        }

    }

    private void setListener() {
        ll_collect_search.setOnClickListener(this);
        ll_collect_email.setOnClickListener(this);
        ll_collect_back.setOnClickListener(this);
        refresh_collect_detail.setOnRefreshListener(this);
        btn_search_cancle.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        iv_search_clear.setOnClickListener(this);
        ll_collect_sweep.setOnClickListener(this);
        fm_collect_sweep.setOnClickListener(this);

        lv_collect_detail.setOnRefreshListener(this);//数据显示的上拉加载
        lv_collect_search.setOnRefreshListener(this);//搜索结果的上拉加载

        refresh_none_collect_people.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetUtil.isConnected(CollectionDetail.this)) {
                    getCollectPresenter = new GetCollectPresenter(CollectionDetail.this);
                    getCollectPresenter.getCollectChild();
                } else {
                    setListview();
                }
                refresh_none_collect_people.setRefreshing(false);
            }
        });

        lv_collect_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(bean.get(position).getAttendeeId())).querySingle();
                if (attends != null) {
                    //Log.e("names",attends.names);
                    Intent intent = new Intent(CollectionDetail.this, CollectionAttendeeDetailInfo.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("attendId", attends.attendId);
                    intent.putExtra("ref_code", attends.refCodes);
                    intent.putExtra("position", position);
                    intent.putExtra("remark", attends.notes);
                    intent.putExtra("detailType", 0);
                    intent.putExtra("orderId", attends.orderIds);
                    //   Log.e("aaaa",attends.notes);
                    startActivity(intent);
                }
            }
        });

        lv_collect_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(resultBean.get(position).getAttendeeId())).querySingle();
                if (attends != null) {
                    Intent intent = new Intent(CollectionDetail.this, CollectionAttendeeDetailInfo.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("attendId", attends.attendId);
                    intent.putExtra("ref_code", attends.refCodes);
                    intent.putExtra("position", position);
                    intent.putExtra("remark", attends.notes);
                    intent.putExtra("detailType", 0);
                    intent.putExtra("orderId", attends.orderIds);
                    //   Log.e("aaaa",attends.notes);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 判断是否为manager页面进入,从而控制相关控件的显示与隐藏
     */
    private void isFromManager() {
        if (isManager.equals(Common.COLLECT_MANAGER)) {
            iv_collect_sweep.setVisibility(View.GONE);
            iv_collect_set.setVisibility(View.VISIBLE);
            fm_collect_sweep.setVisibility(View.VISIBLE);
        } else if (isManager.equals(Common.COLLECT_CHILD)) {
            iv_collect_sweep.setVisibility(View.VISIBLE);
            iv_collect_set.setVisibility(View.GONE);
            fm_collect_sweep.setVisibility(View.GONE);
        }
    }

    private void initView() {
        ll_collect_parent = (AutoLinearLayout) findViewById(R.id.ll_collect_parent);
        rl_collect_title = findViewById(R.id.rl_collect_title);
        ll_collect_back = (AutoLinearLayout) findViewById(R.id.ll_collect_back);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        ll_collect = (AutoLinearLayout) findViewById(R.id.ll_collect);
        ll_collect_title = (AutoRelativeLayout) findViewById(R.id.ll_collect_title);
        ll_collect_search_title = (AutoLinearLayout) findViewById(R.id.ll_collect_search_title);
        tv_collect_count = (TextView) findViewById(R.id.tv_collect_count);
        ll_collect_search = (AutoLinearLayout) findViewById(R.id.ll_collect_search);
        ll_collect_email = (AutoLinearLayout) findViewById(R.id.ll_collect_email);
        refresh_collect_detail = (SwipeRefreshLayout) findViewById(R.id.refresh_collect_detail);
        lv_collect_detail = (LoadMoreListView) findViewById(R.id.lv_collect_detail);
        refresh_none_collect_people = (SwipeRefreshLayout) findViewById(R.id.refresh_none_collect_people);
        //扫描
        ll_collect_sweep = (AutoLinearLayout) findViewById(R.id.ll_collect_sweep);
        fm_collect_sweep = (AutoFrameLayout) findViewById(R.id.fm_collect_sweep);
        iv_collect_sweep = (ImageView) findViewById(R.id.iv_collect_sweep);
        iv_collect_set = (TextView) findViewById(R.id.iv_collect_set);
        //搜索
        lv_collect_search = (LoadMoreListView) findViewById(R.id.lv_collect_search);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search_clear = (ImageView) findViewById(R.id.iv_search_clear);
        btn_search_cancle = (Button) findViewById(R.id.btn_search_cancle);
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
    }

    private void exportDialog() {
        exportDialog = new MDEditDialog.Builder(this)
                .setTitleVisible(true)
                .setTitleText(getString(R.string.please_input_eamil))
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(14)
                .setHintText(getString(R.string.email))
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
                .setMaxLines(1)
                .setContentTextColor(R.color.black)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.grey)
                .setLeftButtonText(getString(R.string.cancel))
                .setRightButtonTextColor(R.color.fe6900)
                .setRightButtonText(getString(R.string.export))
                .setLineColor(R.color.black)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(View view, String text) {
                        exportDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view, String text) {
                        if (!TextUtils.isEmpty(text)) {
                            if (CompareRex.checkEmail(text)) {
                                email = text;
                                if (NetUtil.isConnected(CollectionDetail.this)) {
                                    presenter = new ExportCollectPresenter(CollectionDetail.this);
                                    presenter.export();
                                } else {
                                    Toast toast = Toast.makeText(CollectionDetail.this, R.string.check_your_net, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                exportDialog.dismiss();
                            } else {
                                Toast toast = Toast.makeText(CollectionDetail.this, R.string.emai_err, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(CollectionDetail.this, R.string.please_input_eamil, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        String currentTime = SharedPreferencesUtil.get(this, "currentTime" + eventId, "");
        if (event.mMsg.equals("collect")) {
            setListview();
        } else if (event.mMsg.equals("fromCollectionBarcode")) {
//            isFromCollectBarcode = true;
//            autoRefreshAnimation();
//            initData();
        } else if (event.mMsg.contains(Common.ATTENDEE_PAGE)) {
            pageNum = event.pageNumber;
            getAttendPeopleInfo();
        } else if (event.mMsg.equals("success")) {//请求参会人员成功
            initData();
        } else if (event.mMsg.equals(Common.SYNC_ATTEND_INFO_SUCCESS)) {
            initData();
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String eventId() {
        return eventId + "";
    }

    @Override
    public String userEmail() {
        return email;
    }

    @Override
    public void showExportSuccess() {
        Toast toast = Toast.makeText(this, R.string.daochu_success, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showExportFailed() {

    }

    @Override
    public String collectionId() {
        return collectionId + "";
    }

    @Override
    public void showGetSuccess(CollectDetailData data) {
        refresh_collect_detail.setRefreshing(false);
        collect.clear();
        total = data.getRespObject().getCheckInList().size() + "";
        LogUtil.e("采集数量为" + total);
        Delete.table(CollectChild.class, OperatorGroup.clause().and(CollectChild_Table.eventId.is(eventId))
                .and(CollectChild_Table.collectionId.is(collectionId)).and(CollectChild_Table.collectIsSync.is(Constants.COLLECT_SYNV)));
        for (int i = 0; i < data.getRespObject().getCheckInList().size(); i++) {
            CollectChild child = new CollectChild();
            CollectDetailData.RespObjectBean.CheckInListBean bean = data.getRespObject().getCheckInList().get(i);
            child.eventId = eventId;
            child.collectionId = collectionId;
            child.attendeeBarcode = bean.getAttendeeBarcode();
            child.attendeeCheckInTime = bean.getAttendeeCheckInTime();
            child.collectIsSync = Constants.COLLECT_SYNV;
            collect.add(child);
        }
        if (collect.size() > 0) {
            setListToDB(collect);
        } else {
            EventBus.getDefault().post(new MsgEvent("collect"));
        }
    }

    @Override
    public void showGetFailed() {//请求超时或失败,如果数据库中有数据则显示，否则显示暂无信息
        setListview();
    }

    private void setListToDB(final List<CollectChild> collect) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<CollectChild>() {
                                    @Override
                                    public void processModel(CollectChild model, DatabaseWrapper wrapper) {
                                        model.save();
                                    }
                                }).addAll(collect).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                LogUtil.e("采集数据保存失败");

                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                LogUtil.e("采集数据保存成功");
                                EventBus.getDefault().post(new MsgEvent("collect"));
                            }
                        }).build().execute();
            }
        };
        runnable.run();

    }


    /**
     * 刷新采集点数据
     */
    private void refreshData() {
        if (NetUtil.isConnected(this)) {
            getAttendPeopleInfo();
        } else {
            Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        if (!isFromCollectBarcode) {
            refreshData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect_search:
                isSearch = true;
                et_search.requestFocus();
                et_search.setFocusable(true);
                fm_collect_sweep.setVisibility(View.GONE);
                inputMethodManager.showSoftInput(et_search, 0);
                empty_view.setVisibility(View.VISIBLE);
                rl_collect_title.setVisibility(View.GONE);
                ll_collect.setVisibility(View.GONE);
                ll_collect_title.setVisibility(View.GONE);
                refresh_collect_detail.setVisibility(View.GONE);
                ll_collect_search_title.setVisibility(View.VISIBLE);
                ll_collect_parent.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                refresh_none_collect_people.setVisibility(View.GONE);
                break;
            case R.id.ll_collect_email:
                exportDialog.show();
                break;
            case R.id.ll_collect_sweep:
                if (isManager.equals(Common.COLLECT_CHILD)) {
                    Intent intent = new Intent(this, CollectionBarcode.class);
                    intent.putExtra("collectionId", collectionId);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("isExhibitor",false);
                    intent.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_HOMEPAGE);
                    startActivity(intent);
                } else if (isManager.equals(Common.COLLECT_MANAGER)) {
                    Intent toQrCode = new Intent(this, CollectionQRCode.class);
                    toQrCode.putExtra("collectionId", collectionId);
                    toQrCode.putExtra("eventId", eventId);
                    toQrCode.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_MANAGER);
                    startActivity(toQrCode);
                }
                break;
            case R.id.fm_collect_sweep: //进入采集页面
                Intent toBarcodeCollect = new Intent(this, CollectionBarcode.class);
                toBarcodeCollect.putExtra("collectionId", collectionId);
                toBarcodeCollect.putExtra("eventId", eventId);
                toBarcodeCollect.putExtra("isExhibitor",false);
                toBarcodeCollect.putExtra("export", getIntent().getIntExtra("export", 0));
                toBarcodeCollect.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_MANAGER);
                startActivity(toBarcodeCollect);
                break;
            case R.id.btn_search_cancle:
                isSearch = false;
                inputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
                et_search.setText("");
                fm_collect_sweep.setVisibility(View.VISIBLE);
                iv_search_clear.setVisibility(View.GONE);
                rl_collect_title.setVisibility(View.VISIBLE);
                ll_collect.setVisibility(View.VISIBLE);
                ll_collect_title.setVisibility(View.VISIBLE);
                refresh_collect_detail.setVisibility(View.VISIBLE);
                ll_collect_search_title.setVisibility(View.GONE);
                ll_collect_parent.setBackgroundColor(ContextCompat.getColor(this, R.color.e6edf2));
                if (show == 1) {
                    refresh_collect_detail.setVisibility(View.GONE);
                    refresh_none_collect_people.setVisibility(View.VISIBLE);
                } else {
                    refresh_collect_detail.setVisibility(View.VISIBLE);
                    refresh_none_collect_people.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_search_clear:
                isSearch = true;
                et_search.setText("");
                break;
            case R.id.ll_collect_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        key = s.toString();
        if (TextUtils.isEmpty(key)) {
            empty_view.setVisibility(View.VISIBLE);
            lv_collect_search.setVisibility(View.GONE);
            iv_search_clear.setVisibility(View.GONE);
        } else {
            resultStart = 0;
            resultBean.clear();
            iv_search_clear.setVisibility(View.VISIBLE);
            SQLite.select(Attends_Table.attendId, Attends_Table.names, Attends_Table.pinyinNames, Attends_Table.avatars, CollectChild_Table.attendeeCheckInTime).from(CollectChild.class)
                    .leftOuterJoin(Attends.class)
                    .on(Attends_Table.barcodes.withTable().eq(CollectChild_Table.attendeeBarcode.withTable()), Attends_Table.eventId.withTable().eq(CollectChild_Table.eventId.withTable()))
                    .where(CollectChild_Table.collectionId.is(collectionId))
                    .and(OperatorGroup.clause().and(Attends_Table.names.like("%" + key + "%"))
                            .or(Attends_Table.pinyinNames.like("%" + key + "%")))
                    .offset(resultStart)
                    .limit(LIMIT)
                    .async()
                    .queryResultCallback(new QueryTransaction.QueryResultCallback<CollectChild>() {
                        @Override
                        public void onQueryResult(@NonNull QueryTransaction<CollectChild> transaction, @NonNull CursorResult<CollectChild> tResult) {
                            List<CollectChildQuery> search = tResult.toCustomListClose(CollectChildQuery.class);
                            resultStart += LIMIT;
                            if (search.size() == 0) {
                                empty_view.setVisibility(View.VISIBLE);
                            } else {
                                empty_view.setVisibility(View.GONE);
                                lv_collect_search.setVisibility(View.VISIBLE);
                                for (int i = 0; i < search.size(); i++) {
                                    CollectDetailData.RespObjectBean.CheckInListBean check = new CollectDetailData.RespObjectBean.CheckInListBean();
                                    //    check.setAttendeeBarcode(search.get(i).barcodes);
                                    check.setAttendeeId(search.get(i).attendId);
                                    check.setAttendeeCheckInTime(search.get(i).attendeeCheckInTime);
                                    check.setAttendeeImg(search.get(i).avatars);
                                    check.setAttendeeName(search.get(i).names);
                                    check.setAttendeePinyinName(search.get(i).pinyinNames);
                                    resultBean.add(check);
                                }
                                sortList(resultBean);
                                resultAdapter = new CollectionDetailAdapter(resultBean, CollectionDetail.this);
                                lv_collect_search.setAdapter(resultAdapter);
                            }
                        }
                    }).execute();


        }
    }

    private int sortList(List<CollectDetailData.RespObjectBean.CheckInListBean> lists) {

        Collections.sort(lists, new Comparator<CollectDetailData.RespObjectBean.CheckInListBean>() {
            @Override
            public int compare(CollectDetailData.RespObjectBean.CheckInListBean lhs, CollectDetailData.RespObjectBean.CheckInListBean rhs) {
                Date date1 = CompareRex.stringToDate(lhs.getAttendeeCheckInTime());
                Date date2 = CompareRex.stringToDate(rhs.getAttendeeCheckInTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1 != null && date2 != null) {
                    if (date1.before(date2)) {
                        return 1;
                    } else if (date1.after(date2)) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
                return 1;
            }
        });
        return 1;
    }


    @Override
    public void onLoadingMore() {
        if (!isSearch) {
            SQLite.select(Attends_Table.attendId, Attends_Table.names, Attends_Table.pinyinNames, Attends_Table.avatars, CollectChild_Table.attendeeCheckInTime).from(CollectChild.class)
                    .leftOuterJoin(Attends.class)
                    .on(Attends_Table.barcodes.withTable().eq(CollectChild_Table.attendeeBarcode.withTable()), Attends_Table.eventId.withTable().eq(CollectChild_Table.eventId.withTable()))
                    .where(CollectChild_Table.collectionId.is(collectionId))
                    .and(CollectChild_Table.isLegal.isNot(1))
                    .offset(start)
                    .limit(LIMIT)
                    .async()
                    .queryResultCallback(new QueryTransaction.QueryResultCallback<CollectChild>() {
                        @Override
                        public void onQueryResult(@NonNull QueryTransaction<CollectChild> transaction, @NonNull CursorResult<CollectChild> tResult) {
                            List<CollectChildQuery> mList = tResult.toCustomListClose(CollectChildQuery.class);
                            start += LIMIT;
                            if (mList != null && mList.size() > 0) {
                                for (int i = 0; i < mList.size(); i++) {
                                    CollectDetailData.RespObjectBean.CheckInListBean check = new CollectDetailData.RespObjectBean.CheckInListBean();
                                    // check.setAttendeeBarcode(mList.get(i).barcodes);
                                    check.setAttendeeId(mList.get(i).attendId);
                                    check.setAttendeeCheckInTime(mList.get(i).attendeeCheckInTime);
                                    check.setAttendeeImg(mList.get(i).avatars);
                                    check.setAttendeeName(mList.get(i).names);
                                    check.setAttendeePinyinName(mList.get(i).pinyinNames);
                                    bean.add(check);
                                }
                                sortList(bean);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }).execute();
            lv_collect_detail.loadMoreComplete();
        } else {
            SQLite.select(Attends_Table.attendId, Attends_Table.names, Attends_Table.pinyinNames, Attends_Table.avatars, CollectChild_Table.attendeeCheckInTime).from(CollectChild.class)
                    .leftOuterJoin(Attends.class)
                    .on(Attends_Table.barcodes.withTable().eq(CollectChild_Table.attendeeBarcode.withTable()), Attends_Table.eventId.withTable().eq(CollectChild_Table.eventId.withTable()))
                    .where(CollectChild_Table.collectionId.is(collectionId))
                    .and(OperatorGroup.clause().and(Attends_Table.names.like("%" + key + "%"))
                            .or(Attends_Table.pinyinNames.like("%" + key + "%")))
                    .offset(resultStart)
                    .limit(LIMIT)
                    .async()
                    .queryResultCallback(new QueryTransaction.QueryResultCallback<CollectChild>() {
                        @Override
                        public void onQueryResult(@NonNull QueryTransaction<CollectChild> transaction, @NonNull CursorResult<CollectChild> tResult) {
                            List<CollectChildQuery> search = tResult.toCustomListClose(CollectChildQuery.class);
                            resultStart += LIMIT;
                            if (search.size() > 0) {
                                for (int i = 0; i < search.size(); i++) {
                                    CollectDetailData.RespObjectBean.CheckInListBean check = new CollectDetailData.RespObjectBean.CheckInListBean();
                                    check.setAttendeeId(search.get(i).attendId);
                                    check.setAttendeeCheckInTime(search.get(i).attendeeCheckInTime);
                                    check.setAttendeeImg(search.get(i).avatars);
                                    check.setAttendeeName(search.get(i).names);
                                    check.setAttendeePinyinName(search.get(i).pinyinNames);
                                    resultBean.add(check);
                                }
                                sortList(resultBean);
                                if (resultAdapter != null) {//防止出现空指针异常
                                    resultAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }).execute();
            lv_collect_search.loadMoreComplete();
        }
    }

    @Override
    public String detailEventId() {
        return eventId + "";
    }

    @Override
    public int sType() {
        return 0;
    }

    @Override
    public Context getContext() {
        return CollectionDetail.this;
    }

    @Override
    public void showTicketInfo(final TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(this, eventId, ticketInfo, false);
        util.syncTicket();

    }

    @Override
    public void showErrInfo(String errInfo) {

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

    private String currentTime() {
        //根据eventId将currentTime从sp中取出
        currentTime = SharedPreferencesUtil.get(this, "currentTime" + eventId, "");
        return currentTime;
    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public String userId() {
        return SharedPreferencesUtil.get(this, "userId", "");
    }

    @Override
    public void eventDetailSuccess(EventDetailData response) {
        attendeeCount = response.getRespObject().getAttendeeCount();
        setListFromHome();
    }

    @Override
    public void eventDetailFailed(String errInfo) {

    }

    @Override
    public void attendeeJsonFile(String jsonFile) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(this, jsonFile, eventId, 0);
        util.syncAttendeeFile();

    }

    @Override
    public void attendeeJsonFileFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPageNum() {
        return pageNum + "";
    }

    @Override
    public String getUpdateTime() {
        return currentTime();
    }

    @Override
    public void showSuccessInfo(final String people) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(this, people, eventId, 0);
        util.syncAttendees();
    }

    @Override
    public void showFailInfo(String errInfo) {

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ll_collect_search_title.getVisibility() == View.VISIBLE) {
                isSearch = false;
                et_search.setText("");
                empty_view.setVisibility(View.GONE);
                fm_collect_sweep.setVisibility(View.VISIBLE);
                rl_collect_title.setVisibility(View.VISIBLE);
                ll_collect.setVisibility(View.VISIBLE);
                ll_collect_title.setVisibility(View.VISIBLE);
                refresh_collect_detail.setVisibility(View.VISIBLE);
                ll_collect_search_title.setVisibility(View.GONE);
                ll_collect_parent.setBackgroundColor(ContextCompat.getColor(this, R.color.e6edf2));
                if (show == 1) {
                    refresh_none_collect_people.setVisibility(View.VISIBLE);
                    refresh_collect_detail.setVisibility(View.GONE);
                } else {
                    refresh_collect_detail.setVisibility(View.VISIBLE);
                    refresh_none_collect_people.setVisibility(View.GONE);
                }
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
