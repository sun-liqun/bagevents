package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.detail.CollectionDetail;
import com.bagevent.activity_manager.manager_fragment.adapter.CollectManagerAdapter;
import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CollectManagerPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectManagerView;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.CollectList;
//import com.bagevent.db.CollectList_Table;
import com.bagevent.db.CollectList_Table;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bumptech.glide.Glide;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
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
 * 采集点管理
 */
public class CollectManagerFragment extends BaseFragment implements CollectManagerView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher, FragmentBackHandler {
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    Unbinder unbinder;
    private View view;

    // private AutoRelativeLayout rl_collect_manager_title;
    private AutoLinearLayout ll_collect_manager;
    //  private AutoLinearLayout ll_collect_manager_back;
    private AutoFrameLayout fm_collect;
    private SwipeRefreshLayout collect_refresh;
    private ListView lv_collect_manager_list;
    private SwipeRefreshLayout refresh_none_collect;
    /**
     * 搜索相关控件
     */
    private AutoLinearLayout ll_search;
    private AutoFrameLayout fl_search;
    private View search_collect_line;
    private ListView lv_collect_result;
    private TextView tv_hint;
    private EditText et_search;
    private Button btn_search_cancle;
    private LinearLayout empty_view;
    private ImageView iv_search_clear;

    private int eventId = -1;

    private List<CollectList> mList;
    private List<CollectList> list;
    private CollectManagerPresenter presenter;
    private CollectManagerAdapter adapter;
    private CollectManagerAdapter resultAdapter;

    private InputMethodManager inputMethodManager;
    private int show = -1;//标识采集点是否为空

    private int type=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collect_manager, container, false);
        // StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.white), Constants.STATUS_ALPHA);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (type==0){
            Bundle bundle = getArguments();
            eventId = bundle.getInt("eventId");
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isLoading();
        //autoRefresh();
        initData();
        setListener();
    }

    /**
     * 如果有网络则显示加载动画
     */
    private void autoRefresh() {
        if (NetUtil.isConnected(getActivity())) {
            collect_refresh.post(new Runnable() {
                @Override
                public void run() {
                    collect_refresh.setRefreshing(true);
                }
            });
        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        collect_refresh.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        if (llLoading != null) {
            llLoading.setVisibility(View.GONE);
            collect_refresh.setVisibility(View.VISIBLE);
        }
    }

    private void setListener() {
        ll_search.setOnClickListener(this);
        btn_search_cancle.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        iv_search_clear.setOnClickListener(this);
        collect_refresh.setOnRefreshListener(this);
        refresh_none_collect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                refresh_none_collect.setRefreshing(false);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.contains(Common.SYNC_INFO_SUCCESS)) {
            getData();
        }
    }

    private void getData() {
        if (collect_refresh.isRefreshing()) {
            collect_refresh.setRefreshing(false);
        }
        loadingFinished();
        mList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).queryList();
        if (mList.size() > 0) {
            show = 0;
            collect_refresh.setVisibility(View.VISIBLE);
            refresh_none_collect.setVisibility(View.GONE);
            adapter = new CollectManagerAdapter(getActivity(), mList);
            lv_collect_manager_list.setAdapter(adapter);
        } else {
            show = 1;
            collect_refresh.setVisibility(View.GONE);
            refresh_none_collect.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        if (NetUtil.isConnected(getActivity())) {
            presenter = new CollectManagerPresenter(this);
            presenter.getCollectList();
        } else {
            getData();
        }
    }

    private void initView() {
        tvTitle.setText(R.string.text_collect_manager);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        ll_collect_manager = (AutoLinearLayout) view.findViewById(R.id.collect_manager);
        //  rl_collect_manager_title = (AutoRelativeLayout) view.findViewById(R.id.rl_collect_manager_title);
        fm_collect = (AutoFrameLayout) view.findViewById(R.id.fm_collect);
        collect_refresh = (SwipeRefreshLayout) view.findViewById(R.id.collect_refresh);
        lv_collect_manager_list = (ListView) view.findViewById(R.id.lv_collect_manager_list);
        //   ll_collect_manager_back = (AutoLinearLayout) view.findViewById(R.id.ll_collect_manager_back);
        refresh_none_collect = (SwipeRefreshLayout) view.findViewById(R.id.refresh_none_collect);

        ll_search = (AutoLinearLayout) view.findViewById(R.id.ll_search);
        fl_search = (AutoFrameLayout) view.findViewById(R.id.fl_search);
        search_collect_line = (View) view.findViewById(R.id.search_collect_line);
        lv_collect_result = (ListView) view.findViewById(R.id.lv_collect_result);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        et_search = (EditText) view.findViewById(R.id.et_search);
        btn_search_cancle = (Button) view.findViewById(R.id.btn_search_cancle);
        empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
        iv_search_clear = (ImageView) view.findViewById(R.id.iv_search_clear);
        tv_hint.setText(R.string.hint_search_collect);

        lv_collect_manager_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectList collectList = mList.get(position);
                Intent intent1 = new Intent(getActivity(), CollectionDetail.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("collectionId", collectList.collectPointId);
                intent1.putExtra("collectionName", collectList.collectionName);
                intent1.putExtra("export", collectList.export);
                intent1.putExtra(Common.COLLECT, Common.COLLECT_MANAGER);//用于判断是否是采集点管理页面进入详情页
                startActivity(intent1);
            }
        });

        lv_collect_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectList collectList = list.get(position);
                Intent intent1 = new Intent(getActivity(), CollectionDetail.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("collectionId", collectList.collectPointId);
                intent1.putExtra("collectionName", collectList.collectionName);
                intent1.putExtra("export", collectList.export);
                intent1.putExtra(Common.COLLECT, Common.COLLECT_MANAGER);//用于判断是否是采集点管理页面进入详情页
                startActivity(intent1);
            }
        });
    }

    private void setToDB(final List<CollectList> list) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<CollectList>() {
                                    @Override
                                    public void processModel(CollectList collectList, DatabaseWrapper wrapper) {
                                        collectList.save();
                                    }

                                }).addAll(list).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                //Log.e("list", "Database transaction failed.", error);
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                EventBus.getDefault().post(new MsgEvent(Common.SYNC_INFO_SUCCESS));
                            }
                        }).build().execute();
            }
        };
        runnable.run();
    }

    @Override
    public Context mContext() {
        return getActivity();
    }

    @Override
    public String eventId() {
        return eventId + "";
    }

    @Override
    public void showCollectListSuccess(CollectManagerData data) {
        List<CollectList> list = new ArrayList<CollectList>();
        Delete.table(CollectList.class, OperatorGroup.clause().and(CollectList_Table.eventId.is(eventId)));
        for (int i = 0; i < data.getRespObject().getCollectionList().size(); i++) {
            CollectManagerData.RespObjectBean.CollectionListBean temp = data.getRespObject().getCollectionList().get(i);
            CollectList collect = new CollectList();
            collect.eventId = eventId;
            collect.collectPointId = temp.getCollectPointId();
            collect.collectionName = temp.getCollectionName();
            collect.userEmail = temp.getUserEmail();
            collect.collectionType = temp.getCollectionType();
            collect.isAllTicket = temp.getIsAllTicket();
            collect.availableDateType = temp.getAvailableDateType();
            collect.startTime = temp.getStartTime();
            collect.endTime = temp.getEndTime();
            collect.isBegin = temp.getIsBegin();
            collect.isRepeat = temp.getIsRepeat();
            collect.export = temp.getExport();
            collect.checkinCount = temp.getCheckinCount();
            collect.ticketStr = temp.getTicketStr();
            collect.ticketIdStr = temp.getTicketIdStr();
            collect.showNum = temp.getShowNum();
            collect.isAllProduct = temp.getIsAllProduct();
            collect.limitType = temp.getLimitType();
            collect.productStr = temp.getProductStr();
            collect.productIdStr = temp.getProductIdStr();
            list.add(collect);
        }
        if (list.size() != 0) {
            setToDB(list);
        } else {
            getData();
        }
    }

    @Override
    public void showCollectListFailed() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                hideView();
                break;
            case R.id.btn_search_cancle:
                showView();
                break;
            case R.id.iv_search_clear:
                et_search.setText("");
                empty_view.setVisibility(View.VISIBLE);
                iv_search_clear.setVisibility(View.GONE);
                lv_collect_result.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    private void hideView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        et_search.setFocusable(true);
        et_search.requestFocus();
        et_search.setHint(getString(R.string.hint_search_collect));
        inputMethodManager.showSoftInput(et_search, 0);
        ll_search.setVisibility(View.GONE);
        empty_view.setVisibility(View.VISIBLE);
        llCommonTitle.setVisibility(View.GONE);
        collect_refresh.setVisibility(View.GONE);
        search_collect_line.setVisibility(View.VISIBLE);
        fl_search.setVisibility(View.VISIBLE);
        lv_collect_manager_list.setVisibility(View.GONE);
        ll_collect_manager.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        refresh_none_collect.setVisibility(View.GONE);
    }

    private void showView() {
        inputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
        ll_search.setVisibility(View.VISIBLE);
        llCommonTitle.setVisibility(View.VISIBLE);
        collect_refresh.setVisibility(View.VISIBLE);
        search_collect_line.setVisibility(View.GONE);
        fl_search.setVisibility(View.GONE);
        lv_collect_manager_list.setVisibility(View.VISIBLE);
        ll_collect_manager.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.ced3d8));
        et_search.setText("");
        iv_search_clear.setVisibility(View.GONE);
        if (show == 1) {
            refresh_none_collect.setVisibility(View.VISIBLE);
            collect_refresh.setVisibility(View.GONE);
        } else {
            collect_refresh.setVisibility(View.VISIBLE);
            refresh_none_collect.setVisibility(View.GONE);
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
        String key = s.toString();
        if (TextUtils.isEmpty(key)) {
            empty_view.setVisibility(View.VISIBLE);
            lv_collect_result.setVisibility(View.GONE);
            iv_search_clear.setVisibility(View.GONE);
        } else {
            list = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).and(CollectList_Table.collectionName.like("%" + key + "%")).queryList();
            if (list.size() != 0) {
                empty_view.setVisibility(View.GONE);
                lv_collect_result.setVisibility(View.VISIBLE);
                iv_search_clear.setVisibility(View.VISIBLE);
                resultAdapter = new CollectManagerAdapter(getActivity(), list);
                lv_collect_result.setAdapter(resultAdapter);
            } else {
                lv_collect_result.setVisibility(View.GONE);
                iv_search_clear.setVisibility(View.VISIBLE);
                empty_view.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRefresh() {
        if (NetUtil.isConnected(getActivity())) {
            initData();
        } else {
            Toast.makeText(getActivity(), R.string.check_network1, Toast.LENGTH_SHORT).show();
        }
        collect_refresh.setRefreshing(false);
    }

    @Override
    public boolean onBackPressed() {
        if (llCommonTitle.getVisibility() == View.GONE) {
            inputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
            ll_search.setVisibility(View.VISIBLE);
            llCommonTitle.setVisibility(View.VISIBLE);
            collect_refresh.setVisibility(View.VISIBLE);
            search_collect_line.setVisibility(View.GONE);
            fl_search.setVisibility(View.GONE);
            lv_collect_manager_list.setVisibility(View.VISIBLE);
            ll_collect_manager.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.ced3d8));
            et_search.setText("");
            iv_search_clear.setVisibility(View.GONE);
            if (show == 1) {
                collect_refresh.setVisibility(View.GONE);
                refresh_none_collect.setVisibility(View.VISIBLE);
            } else {
                collect_refresh.setVisibility(View.VISIBLE);
                refresh_none_collect.setVisibility(View.GONE);
            }
            return true;
        } else {
            return BackHandlerHelper.handleBackPress(this);
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag("CollectManagerFragment");
        super.onDestroyView();

    }

    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
//        AppManager.getAppManager().finishActivity();
        getActivity().finish();
    }
}
