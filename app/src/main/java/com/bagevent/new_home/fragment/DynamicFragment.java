package com.bagevent.new_home.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.BaseFragment;
import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.ExhibitorManageFragment;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.ReportComment;
import com.bagevent.common.Constants;
import com.bagevent.dialog.LDialog;
import com.bagevent.dialog.ViewHelper;
import com.bagevent.dialog.listener.OnBindView;
import com.bagevent.dialog.listener.OnViewClick;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.adapter.DynamicAdapter;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.new_home.new_activity.CreateInform;
import com.bagevent.new_home.new_activity.DynamicDetailActivity;
import com.bagevent.new_home.new_activity.ReleaseDynamicCondition;
import com.bagevent.new_home.new_activity.ReportListActivity;
import com.bagevent.util.AppUtils;
import com.bagevent.util.BitmapUtils;
import com.bagevent.util.CompareRex;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/13
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class DynamicFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.iv_publish_dynamic)
    ImageView iv_publish_dynamic;

    private int pageSize = 20;
    private int page;
    private String eventId;
    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    private DynamicAdapter dynamicAdapter;
    private Unbinder unbinder;
    private RecyclerViewBind recyclerViewBind;
    private LoadingViewBind loadingViewBind;
    private NoDataViewBind noDataViewBind;
    private HeadViewBind headViewBind;
    private View view_publish_dynamic;
    private boolean isPublishLayoutShow;
    private ArrayList<String> pos = new ArrayList<>(1);
    private ArrayList<ReportComment> reportComments;
    private int reportListCount;
    private MyHandler handler = new MyHandler(this);
    private String eventName;

    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private int popupWidth;
    private int popupHeight;
    private TextView tv_like;
    private TextView tv_comment_dynamic;
    private int location[];

    DynamicListData.CommentList commentList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
       // View view = inflater.inflate(R.layout.dynamic, null);
        View view = inflater.inflate(R.layout.dynamic, container,false);
        unbinder = ButterKnife.bind(this, view);
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                getActivity().dispatchTouchEvent(motionEvent);
//                return false;
//            }
//        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initLayout();

        lazyLoad();
        initPop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    /**
     * 初始化页面布局
     */
    private void initLayout() {
        recyclerViewBind = new RecyclerViewBind(LayoutInflater.from(getContext()).inflate(R.layout.dynamic_recyclerview, fl_container, false));
        loadingViewBind = new LoadingViewBind(LayoutInflater.from(getContext()).inflate(R.layout.dynamic_loading, fl_container, false));
        headViewBind = new HeadViewBind(LayoutInflater.from(getContext()).inflate(R.layout.item_message, fl_container,false));
        noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.dynamic_no_data, fl_container,false));
        if (SharedPreferencesUtil.getSettingBoolean(getContext(), KeyUtil.KEY_GUIDE_SIGN_ONE, false)) {
            iv_publish_dynamic.setVisibility(View.VISIBLE);
        } else {
            iv_publish_dynamic.setVisibility(View.GONE);
        }
    }

    /**
     * 加载数据
     */
    public void lazyLoad() {
        showLoading();
        if (dynamicAdapter != null && headViewBind != null) {
            dynamicAdapter.removeHeaderView(headViewBind.itemView);
        }
        dynamicAdapter = null;
        reportListCount = 0;
        page = 1;
        eventId = ((HomePage) getActivity()).getSelectEventId();
        tv_event_name.setText(((HomePage) getActivity()).getSelectEventName());
        loadDataFromServer(eventId, ACTION_LAOD);
    }

    private void initPop() {
        mPopUpView = getLayoutInflater().inflate(R.layout.activity_comment_pop, null);
        mPopUpView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = mPopUpView.getMeasuredWidth();    //  获取测量后的宽度
        popupHeight = mPopUpView.getMeasuredHeight();  //获取测量后的高度
        location = new int[2];
        tv_like = mPopUpView.findViewById(R.id.tv_like);
        tv_comment_dynamic = mPopUpView.findViewById(R.id.tv_comment_dynamic);
        mPopUpWindow = new PopupWindow(mPopUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) {
            @Override
            public void dismiss() {
                ValueAnimator valueAnimator = createDropAnimator(mPopUpView, popupWidth, 0);
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        myDismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
            }

            public void myDismiss() {
                super.dismiss();
            }
        };

        mPopUpWindow.setTouchable(true);
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setFocusable(true);
//        mPopUpWindow.setOutsideTouchable(true);
//        mPopUpWindow.setAnimationStyle(R.style.PopWindowAnimation);
        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                ValueAnimator animator=createDropAnimator(mPopUpView,popupWidth,0);
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mPopUpView.setVisibility(View.GONE);
//                    }
//                });
//                animator.start();
//            }
//        });
        mPopUpWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                        mPopUpWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentList.getType() == 1 || commentList.getType() == 2) {
                    OkHttpUtil.Post(getContext())
                            .url(Constants.NEW_URL + Constants.EDIT_COMMENT_LIKE + "/" + commentList.getCommentId())
                            .addParams("eventId", String.valueOf(commentList.getEventId()))
                            .build()
                            .execute(new Callback<String>() {
                                @Override
                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                    return response.body().string();
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    TosUtil.show(getString(R.string.send_error));
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if (response.contains("\"retStatus\":200")) {
                                        page = 1;
                                        recyclerViewBind.spl_refresh.setRefreshing(true);
                                        loadDataFromServer(eventId, ACTION_REFRESH);
                                    } else {
                                        TosUtil.show(getString(R.string.send_error));
                                    }
                                }
                            });
                } else if (commentList.getType() == 5) {
                    OkHttpUtil.Post(getContext())
                            .url(Constants.NEW_URL + Constants.EDIT_COMMENT_LIKE + "/" +
                                    commentList.getParentComment().getCommentId())
                            .addParams("eventId", String.valueOf(commentList.getEventId()))
                            .build()
                            .execute(new Callback<String>() {

                                @Override
                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                    return response.body().string();
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    TosUtil.show(getString(R.string.send_error));
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if (response.contains("\"retStatus\":200")) {
                                        page = 1;
                                        recyclerViewBind.spl_refresh.setRefreshing(true);
                                        loadDataFromServer(eventId, ACTION_REFRESH);
                                    } else {
                                        TosUtil.show(getString(R.string.send_error));
                                    }
                                }
                            });

                }
                if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                    mPopUpWindow.dismiss();
                }
//                mPopUpWindow.dismiss();
            }
        });
        tv_comment_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_GET_NICKNAME, commentList.getUser().getShowName()));
                new WeakHandler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((HomePage) getActivity()).showCommentLayout();
                    }
                }, 1000);
                if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                    mPopUpWindow.dismiss();
                }
//                mPopUpWindow.dismiss();
            }
        });
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", end, start);
        objectAnimator.setDuration(100);
        return objectAnimator;
    }


    /**
     * 第一次网络加载请求
     *
     * @param eventId
     * @param action
     */
    private void loadDataFromServer(String eventId, final byte action) {

        if (!NetUtil.isConnected(getContext())) {
            TosUtil.show(getString(R.string.check_network));
            return;
        }

        String fromTime = "";
        if (action != ACTION_LOADMORE) {
            //刷新,首次加载
            fromTime = String.valueOf(System.currentTimeMillis());
            SharedPreferencesUtil.put(getContext(), KeyUtil.KEY_FROM_TIME, fromTime);
        } else {
            //加载更多
            fromTime = SharedPreferencesUtil.get(getContext(), KeyUtil.KEY_FROM_TIME, String.valueOf(System.currentTimeMillis()));
        }

        if (action != ACTION_LOADMORE) {
            getReportList(action);
        }


        // 加载动态列表数据
        OkHttpUtil.Post(getContext())
                .url(Constants.NEW_URL + Constants.FIND_COMMENT_LIST)
                .addParams("eventId", eventId) //2113951
                .addParams("page", String.valueOf(page))
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("type", ((HomePage) getActivity()).getCurrentSelctIndex())
                .addParams("deleteCount", "0")
                .addParams("from_time", fromTime)
                .tag("findCommentList")
                .build()
                .writeTimeOut(5000)
                .connTimeOut(5000)
                .readTimeOut(50000)
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        return result;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("findCommentList请求取消");
                        }else {
                            parserError(action);
                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action);
                        } else {
                            parserError(action);
                        }
                    }
                });

    }

    public void getReportList(final byte action) {
        //加载举报列表数据
        OkHttpUtil.Post(getContext())
                .url(Constants.NEW_URL + Constants.FIND_REPORT_COMMENT)
                .addParams("eventId", eventId)
                .tag("findReportComment")
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("findReportComment请求取消");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserReportList(response, action);
                        }
                    }
                });
    }

    /**
     * 解析举报列表信息
     *
     * @param response
     * @param action
     */
    private void parserReportList(String response, byte action) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject respObject = jsonObject.getAsJsonObject("respObject");
        JsonArray reportCommentList = respObject.getAsJsonArray("reportCommentList");
        reportComments = new ArrayList<>();
        reportListCount = reportCommentList.size();
        if (reportCommentList.size() != 0) {
            reportComments.clear();
            Gson gson = new Gson();
            for (JsonElement element : reportCommentList) {
                ReportComment reportComment = gson.fromJson(element, new TypeToken<ReportComment>() {
                }.getType());
                reportComments.add(reportComment);
            }
        }

        if (action == ACTION_LAOD && dynamicAdapter != null) {
            dynamicAdapter.addHeaderView(headViewBind.itemView);
            dynamicAdapter.notifyDataSetChanged();
            headViewBind.v_unread.setText(String.valueOf(reportListCount));
        } else if (action == ACTION_REFRESH) {
            if (reportListCount <= 0 && dynamicAdapter.getHeaderLayoutCount() > 0) {
                dynamicAdapter.getHeaderLayout().removeAllViews();
            } else if (reportListCount > 0) {
                if (dynamicAdapter.getHeaderLayoutCount() <= 0) {
                    dynamicAdapter.addHeaderView(headViewBind.itemView);
                    dynamicAdapter.notifyDataSetChanged();
                }
                headViewBind.v_unread.setText(String.valueOf(reportListCount));
            }
        }
    }

    private void parserSuccess(String response, byte action) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response);
                break;
            case ACTION_LOADMORE:
                parserLoadMoreData(response);
                break;
            case ACTION_REFRESH:
                parserRefreshData(response);
                break;
        }

    }

    private void parserError(byte action) {
        showNoData();
    }

    private void initRecyclerView(ArrayList<DynamicListData.CommentList> listData) {
        showRecycelrView();
        dynamicAdapter = new DynamicAdapter((HomePage) getActivity(), pos, listData);
        dynamicAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewBind.dynamic_recyclerview.setLayoutManager(linearLayoutManager);
        recyclerViewBind.dynamic_recyclerview.setAdapter(dynamicAdapter);
        dynamicAdapter.setOnItemClickListener(this);
        dynamicAdapter.setOnItemChildClickListener(this);

        if (reportListCount > 0) {
            dynamicAdapter.addHeaderView(headViewBind.itemView);
            headViewBind.v_unread.setText(String.valueOf(reportListCount));
        }

        if (listData.size() < pageSize) {
            dynamicAdapter.loadMoreEnd();
        }

        recyclerViewBind.spl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadDataFromServer(eventId, ACTION_REFRESH);
            }
        });
        dynamicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadDataFromServer(eventId, ACTION_LOADMORE);
            }
        }, recyclerViewBind.dynamic_recyclerview);
    }

    private void parserLoadData(String response) {
        ArrayList<DynamicListData.CommentList> listData = getListData(response);
        initRecyclerView(listData);
        if (listData.size() == 0) {
            showNoData();
        } else {
            page++;
        }
    }

    private ArrayList<DynamicListData.CommentList> getListData(String response) {
        DynamicListData dynamicListData = new DynamicListData(new JsonParser().parse(response).getAsJsonObject());
        if (dynamicListData.getRespObject() == null || dynamicListData.getRespObject().getCommentLists() == null) {
            return new ArrayList<>();
        } else {

            return dynamicListData.getRespObject().getCommentLists();
        }
    }

    private void parserRefreshData(String response) {
        ArrayList<DynamicListData.CommentList> listData = getListData(response);
        recyclerViewBind.spl_refresh.setRefreshing(false);
        dynamicAdapter.setNewData(listData);
        if (listData.size() == 0) {
            showNoData();
        } else {
            page++;
        }
    }

    private void parserLoadMoreData(String response) {
        ArrayList<DynamicListData.CommentList> listData = getListData(response);
        if (listData == null) {
            dynamicAdapter.loadMoreFail();
            return;
        } else {
            page++;
        }

        if (listData.size() < pageSize) {
            dynamicAdapter.loadMoreComplete();
            dynamicAdapter.loadMoreEnd();
        } else {
            dynamicAdapter.loadMoreComplete();
        }
        dynamicAdapter.addData(listData);
    }

    private void showLoading() {
        fl_container.removeView(recyclerViewBind.itemView);
        fl_container.removeView(loadingViewBind.itemView);
        fl_container.removeView(noDataViewBind.itemView);
        fl_container.addView(loadingViewBind.itemView);
        if (!SharedPreferencesUtil.getBoolean(getContext(), KeyUtil.KEY_TIPS, false)) {
            loadingViewBind.ll_tips.setVisibility(View.VISIBLE);
        }
    }

    private void showNoData() {
        if (dynamicAdapter != null) {
            if (dynamicAdapter.getData() != null) {
                dynamicAdapter.getData().clear();
                dynamicAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.dynamic_no_data, null));
            dynamicAdapter.setEmptyView(noDataViewBind.itemView);
        }else {
            fl_container.removeView(loadingViewBind.itemView);
            fl_container.removeView(recyclerViewBind.itemView);
            fl_container.removeView(noDataViewBind.itemView);
            fl_container.addView(noDataViewBind.itemView);
        }
        if (!SharedPreferencesUtil.getBoolean(getContext(), KeyUtil.KEY_TIPS, false)) {
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.dynamic_no_data, null));
            noDataViewBind.ll_tips.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getContext() {
        if (super.getContext() != null) {
            return super.getContext();
        } else {
            return AppUtils.getContext();
        }
    }

    private void showRecycelrView() {
        fl_container.removeView(loadingViewBind.itemView);
        fl_container.removeView(recyclerViewBind.itemView);
        fl_container.addView(recyclerViewBind.itemView, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("refresh_dynamic")) {
            new WeakHandler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = 1;
                    recyclerViewBind.spl_refresh.setRefreshing(true);
                    loadDataFromServer(eventId, ACTION_REFRESH);
                }
            }, 1000);
        } else if (event.mMsg.equals("ignore_report")) {
            reportComments.remove(event.pos);
            reportListCount = reportComments.size();
            if (reportListCount <= 0) {
                dynamicAdapter.getHeaderLayout().removeAllViews();
            } else {
                headViewBind.v_unread.setText(String.valueOf(reportListCount));
            }
        } else if (event.mMsg.equals("sheild_report")) {
            recyclerViewBind.spl_refresh.setRefreshing(true);
            page = 1;
            loadDataFromServer(eventId, ACTION_REFRESH);
        }
    }

    /**
     * 显示举报布局
     */
    private void showReport() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, null);
        dynamicAdapter.addHeaderView(headView);
    }


    @OnClick({R.id.tv_event_name, R.id.iv_event_filter, R.id.iv_refresh, R.id.iv_publish_dynamic})
    public void onClicked(View view) {
        InputMethodUtil.close(getContext(), view);
        switch (view.getId()) {
            case R.id.tv_event_name:
                ((HomePage) getActivity()).showLeftMenu();
                break;
            case R.id.iv_event_filter:
                ((HomePage) getActivity()).showRightMenu();
                break;
            case R.id.iv_refresh:
                page = 1;
                recyclerViewBind.spl_refresh.setRefreshing(true);
                loadDataFromServer(eventId, ACTION_REFRESH);
                break;
            case R.id.iv_publish_dynamic: //发布动态
                showPublishDynamicLayout();
                break;
        }
    }

    /**
     * 显示发布动态布局
     */
    private void showPublishDynamicLayout() {
        isPublishLayoutShow = true;
        final ViewGroup content = (FrameLayout) getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        view_publish_dynamic = LayoutInflater.from(getContext()).inflate(R.layout.view_publish_dynamic, null);
        content.addView(view_publish_dynamic);

        view_publish_dynamic.findViewById(R.id.tv_publish_dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(eventId)){
                    Toast.makeText(getContext(), R.string.now_no_activity, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), ReleaseDynamicCondition.class);
                    startActivity(intent);
                }
                closePublishDynamicLaout();
            }
        });

        view_publish_dynamic.findViewById(R.id.tv_create_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(eventId)){
                    Toast.makeText(getContext(), R.string.now_no_activity, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), CreateInform.class);
                    startActivity(intent);
                }
                closePublishDynamicLaout();
            }
        });

        view_publish_dynamic.findViewById(R.id.iv_dynamic_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(eventId)){
                    Toast.makeText(getContext(), R.string.now_no_activity, Toast.LENGTH_SHORT).show();
                }
                isPublishLayoutShow = false;
                content.removeView(view_publish_dynamic);
            }
        });
    }

    /**
     * 关闭发布动态布局
     */
    public void closePublishDynamicLaout() {
        isPublishLayoutShow = false;
        final ViewGroup content = (FrameLayout) getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        content.removeView(view_publish_dynamic);
    }

    public boolean isPublishLayoutShow() {
        return isPublishLayoutShow;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag("findCommentList");
        OkHttpUtils.getInstance().cancelTag("findReportComment");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DynamicListData.CommentList commentList = dynamicAdapter.getData().get(position);
        if (commentList.getType() != 4) {
            Intent intent = new Intent(getContext(), DynamicDetailActivity.class);
            if (commentList.getType() == 2 || commentList.getType() == 1 || commentList.getType() == 5) {
                intent.putExtra(KeyUtil.KEY_EVENT_ID, commentList.getEventId());
                intent.putExtra(KeyUtil.KEY_COMMENT_ID, commentList.getCommentId());
            } else if (commentList.getType() == 3) {
                DynamicListData.CommentList parentComment = commentList.getParentComment();
                intent.putExtra(KeyUtil.KEY_EVENT_ID, parentComment.getEventId());
                intent.putExtra(KeyUtil.KEY_COMMENT_ID, parentComment.getCommentId());
            }
            intent.putExtra(KeyUtil.KEY_TYPE,0);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
        commentList = dynamicAdapter.getData().get(position);
        int likeNumber = commentList.getLikeNumber();
        final View rl_event_comment = view.findViewById(R.id.rl_event_comment);
        switch (view.getId()) {
            case R.id.rl_event_comment:
                if (commentList.getType() == 3 || commentList.getType() == 1) {
//                    new WeakHandler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
                    EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_GET_NICKNAME_TWO, commentList.getUser().getShowName()));
                    ((HomePage) getActivity()).showCommentLayout();
//                        }, 1000);

                } else if (commentList.getType() != 4) {
                    rl_event_comment.getLocationOnScreen(location);
                    if (likeNumber == 0) {
                        tv_like.setText(R.string.like);
                    } else {
                        tv_like.setText(R.string.cancel);
                    }
                    mPopUpWindow.showAtLocation(rl_event_comment, Gravity.NO_GRAVITY, location[0] - popupWidth, location[1] + 10);
                    ValueAnimator animator = createDropAnimator(mPopUpView, 0, popupWidth);
                    animator.start();
//                  animator.setDuration(200);
                }

//                if (pos.size() == 0) {
//                    pos.add(String.valueOf(position));
//                } else if (pos.contains(String.valueOf(position))) {
//                    pos.remove(String.valueOf(position));
//                } else {
//                    pos.add(String.valueOf(position));
//                    pos.remove(pos.get(0));
//                }
//                dynamicAdapter.notifyDataSetChanged();

                if (commentList.getType() == 2 || commentList.getType() == 1) {
                    EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_DYNAMIC_EVENT_ID, commentList.getEventId()));
                    EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_DYNAMIC_COMMENT_ID, commentList.getCommentId()));
                } else if (commentList.getType() == 3 || commentList.getType() == 5) {
                    DynamicListData.CommentList parentComment = commentList.getParentComment();
                    EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_DYNAMIC_EVENT_ID, parentComment.getEventId()));
                    EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_DYNAMIC_COMMENT_ID, parentComment.getCommentId()));
                }
                break;
            case R.id.tv_comment_dynamic:
                ((HomePage) getActivity()).showCommentLayout();
                pos.remove(String.valueOf(position));
                dynamicAdapter.notifyDataSetChanged();
                InputMethodUtil.showOrHide(getContext());
                break;
            case R.id.iv_more:
                final DynamicListData.CommentList data = (DynamicListData.CommentList) adapter.getData().get(position);
                new LDialog.Builder(getActivity().getFragmentManager())
                        .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                        .setLayoutId(R.layout.dialog_dynamic_detail_bottom)
                        .setGravity(Gravity.BOTTOM)
                        .setDialogAnimationRes(R.style.BottomDialogAnimation)
                        .setCancelableOutside(true)
                        .setOnBindView(new OnBindView() {
                            @Override
                            public void bindView(ViewHelper helper, LDialog dialog) {
                                if (data.getStatus() == 0) {
                                    helper.setText(R.id.tv_reply, R.string.cancel_shiled);
                                } else {
                                    helper.setText(R.id.tv_reply, R.string.shiled1);
                                }

                                if (data.getPriority() == 1) {
                                    helper.setText(R.id.tv_shield, R.string.cancel_top);
                                } else {
                                    helper.setText(R.id.tv_shield, R.string.top);
                                }
                            }
                        })
                        .addOnClick(R.id.tv_reply, R.id.tv_shield, R.id.tv_cancel)
                        .setOnViewClick(new OnViewClick() {
                            @Override
                            public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                                switch (view.getId()) {
                                    case R.id.tv_reply:
                                        if (commentList.getStatus() == 1) {
                                            OkHttpUtil.Post(getContext())
                                                    .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                                    .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                    .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                    .addParams("status", "0")
                                                    .build()
                                                    .execute(new Callback<String>() {

                                                        @Override
                                                        public String parseNetworkResponse(Response response, int id) throws Exception {
                                                            return response.body().string();
                                                        }

                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }

                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            if (response.contains("\"retStatus\":200")) {
                                                                page = 1;
                                                                recyclerViewBind.spl_refresh.setRefreshing(true);
                                                                loadDataFromServer(eventId, ACTION_REFRESH);
                                                            } else {
                                                                TosUtil.show(getString(R.string.send_error));
                                                            }
                                                        }
                                                    });
                                        } else {
                                            OkHttpUtil.Post(getContext())
                                                    .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                                    .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                    .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                    .addParams("status", "1")
                                                    .build()
                                                    .execute(new Callback<String>() {

                                                        @Override
                                                        public String parseNetworkResponse(Response response, int id) throws Exception {
                                                            return response.body().string();
                                                        }

                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }

                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            if (response.contains("\"retStatus\":200")) {
                                                                page = 1;
                                                                recyclerViewBind.spl_refresh.setRefreshing(true);
                                                                loadDataFromServer(eventId, ACTION_REFRESH);
                                                            } else {
                                                                TosUtil.show(getString(R.string.send_error));
                                                            }
                                                        }
                                                    });
                                        }
                                        dialog.dismiss();
                                        break;
                                    case R.id.tv_shield:
                                        if (commentList.getPriority() == 0) {
                                            OkHttpUtil.Post(getContext())
                                                    .url(Constants.NEW_URL + Constants.PRIORITY_COMMENT)
                                                    .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                    .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                    .addParams("priority", "1")
                                                    .build()
                                                    .execute(new Callback<String>() {

                                                        @Override
                                                        public String parseNetworkResponse(Response response, int id) throws Exception {
                                                            return response.body().string();
                                                        }

                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }

                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            if (response.contains("\"retStatus\":200")) {
                                                                page = 1;
                                                                recyclerViewBind.spl_refresh.setRefreshing(true);
                                                                loadDataFromServer(eventId, ACTION_REFRESH);
                                                            } else {
                                                                TosUtil.show(getString(R.string.send_error));
                                                            }
                                                        }
                                                    });
                                        } else {
                                            OkHttpUtil.Post(getContext())
                                                    .url(Constants.NEW_URL + Constants.PRIORITY_COMMENT)
                                                    .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                    .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                    .addParams("priority", "0")
                                                    .build()
                                                    .execute(new Callback<String>() {

                                                        @Override
                                                        public String parseNetworkResponse(Response response, int id) throws Exception {
                                                            return response.body().string();
                                                        }

                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }

                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            if (response.contains("\"retStatus\":200")) {
                                                                page = 1;
                                                                recyclerViewBind.spl_refresh.setRefreshing(true);
                                                                loadDataFromServer(eventId, ACTION_REFRESH);
                                                            } else {
                                                                TosUtil.show(getString(R.string.send_error));
                                                            }
                                                        }
                                                    });
                                        }
                                        dialog.dismiss();
                                        break;
                                    case R.id.tv_cancel:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.tv_queston_name:
                final DynamicListData.Speaker speaker = commentList.getSpeakerList().get(0);
                new LDialog.Builder(getActivity().getFragmentManager())
                        .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                        .setLayoutId(R.layout.dialog_invite)
                        .setGravity(Gravity.BOTTOM)
                        .setDialogAnimationRes(R.style.BottomDialogAnimation)
                        .setCancelableOutside(true)
                        .addOnClick(R.id.tv_invite_sms, R.id.tv_invite_email, R.id.tv_invite_weichat, R.id.tv_cancel)
                        .setOnViewClick(new OnViewClick() {
                            @Override
                            public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                                dialog.dismiss();
                                switch (view.getId()) {
                                    case R.id.tv_invite_sms:
                                        if (TextUtils.isEmpty(speaker.getContactPhone())) {
                                            showInviteSms(String.valueOf(speaker.getSpeakerId()), String.valueOf(commentList.getCommentId()), "2");
                                        } else {
                                            sendSmsInvite(String.valueOf(speaker.getSpeakerId()), String.valueOf(commentList.getCommentId()), "2");
                                        }
                                        break;
                                    case R.id.tv_invite_email:
                                        if (TextUtils.isEmpty(speaker.getContactEmail())) {
                                            showInviteEmail(String.valueOf(speaker.getSpeakerId()), String.valueOf(commentList.getCommentId()), "1");
                                        } else {
                                            sendEmailInvite(String.valueOf(speaker.getSpeakerId()), String.valueOf(commentList.getCommentId()), "1");
                                        }
                                        break;
                                    case R.id.tv_invite_weichat:
//                                        final String imgUrl = "https://www.bagevent.com" + commentList.getEventLogo();
                                        final String imgUrl = "https://img.bagevent.com" + commentList.getEventLogo();
                                        eventName = commentList.getEventName();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    URL url = new URL(imgUrl);
                                                    Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                                                    Message message = new Message();
                                                    message.obj = bitmap;
                                                    message.what = 666;
                                                    handler.sendMessage(message);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        break;
                                }
                            }
                        })
                        .build()
                        .show();
                break;
        }
    }

    public void showPublishButton() {
        iv_publish_dynamic.setVisibility(View.VISIBLE);
    }

    public void hidePublishButton() {
        iv_publish_dynamic.setVisibility(View.GONE);
    }


    private void sendSmsInvite(String speakerId, String commentId, String type) {
        OkHttpUtil.Post(getContext())
                .url(Constants.NEW_URL + Constants.SEND_INVITE_GUEST)
                .addParams("speakerId", speakerId)
                .addParams("commentId", commentId)
                .addParams("type", type)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.send_error));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            TosUtil.show(getString(R.string.success_invite));
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    private void sendEmailInvite(String speakerId, String commentId, String type) {
        sendSmsInvite(speakerId, commentId, type);
    }


    private void sendWeichatInvite(String eventName, Bitmap bitmap) {
        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = "https://www.bagevent.com/";//自定义
        miniProgram.userName = "gh_db5f6792ed3e";//小程序端提供参数
        miniProgram.path = "pages/index/index";//小程序端提供参数
        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
        mediaMessage.title = getString(R.string.from_left) + eventName + getString(R.string.from_right);//自定义
        mediaMessage.description = getString(R.string.from_left) + eventName + getString(R.string.from_right);//自定义
        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        bitmap.recycle();
        mediaMessage.thumbData = BitmapUtils.bmpToByteArray(sendBitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "";
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        MyApplication.iwxapi.sendReq(req);

    }

    private void showInviteSms(final String speakerId, final String commentId, final String type) {
        new LDialog.Builder(getActivity().getFragmentManager())
                .setLayoutId(R.layout.dialog_invite_sms)
                .setWidth(DxPxUtils.dip2px(getActivity(), 270.0f))
                .setGravity(Gravity.CENTER)
                .setDialogAnimationRes(R.style.CenterDialogAnimation)
                .setCancelableOutside(false)
                .addOnClick(R.id.tv_cancel, R.id.tv_ok)
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                        dialog.dismiss();
                        if (view.getId() == R.id.tv_ok) {
                            EditText et_input_cellphone = helper.getView(R.id.et_input_cellphone);
                            String cellphone = et_input_cellphone.getText().toString();

                            if (TextUtils.isEmpty(cellphone)) {
                                TosUtil.show(getString(R.string.input_phone));
                                return;
                            }
                            if (!CompareRex.isCellPhone(et_input_cellphone.getText().toString())) {
                                TosUtil.show(getString(R.string.phone_format_error));
                                return;
                            }
                            bindcellPhone(cellphone, speakerId, commentId, type);
                        }
                    }
                })
                .build()
                .show();
    }

    private void bindcellPhone(String cellPhone, final String speakerId, final String commentId, final String type) {
        OkHttpUtil.Post(getContext())
                .url(Constants.NEW_URL + Constants.BIND_SPEAKER_CELLPHONE)
                .addParams("cellPhone", cellPhone)
                .addParams("speakerId", speakerId)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_invite));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            sendSmsInvite(speakerId, commentId, type);
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    private void showInviteEmail(final String speakerId, final String commentId, final String type) {
        new LDialog.Builder(getActivity().getFragmentManager())
                .setLayoutId(R.layout.dialog_invite_sms)
                .setWidth(DxPxUtils.dip2px(getActivity(), 270.0f))
                .setGravity(Gravity.CENTER)
                .setDialogAnimationRes(R.style.CenterDialogAnimation)
                .setCancelableOutside(false)
                .addOnClick(R.id.tv_cancel, R.id.tv_ok)
                .setOnBindView(new OnBindView() {
                    @Override
                    public void bindView(ViewHelper helper, LDialog dialog) {
                        helper.setText(R.id.tv_title, R.string.input_guest_email);
                    }
                })
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {

                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;
                            case R.id.tv_ok:
                                EditText et_input_cellphone = helper.getView(R.id.et_input_cellphone);
                                String email = et_input_cellphone.getText().toString();

                                if (TextUtils.isEmpty(email)) {
                                    TosUtil.show(getString(R.string.please_input_eamil));
                                    return;
                                }
                                if (!CompareRex.checkEmail(et_input_cellphone.getText().toString())) {
                                    TosUtil.show(getString(R.string.email_format_error));
                                    return;
                                }

                                bindEmail(email, speakerId, commentId, type);
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    private void bindEmail(String email, final String speakerId, final String commentId, final String type) {
        OkHttpUtil.Post(getContext())
                .url(Constants.NEW_URL + Constants.BIND_SPEAKER_EMIAL)
                .addParams("speakerId", speakerId)
                .addParams("email", email)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_invite));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            sendEmailInvite(speakerId, commentId, type);
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    static class MyHandler extends Handler {
        private WeakReference<DynamicFragment> fragmentWeakReference;

        public MyHandler(DynamicFragment dynamicFragment) {
            fragmentWeakReference = new WeakReference<>(dynamicFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            DynamicFragment dynamicFragment = fragmentWeakReference.get();
            if (msg.what == 666) {
                dynamicFragment.sendWeichatInvite(dynamicFragment.eventName, (Bitmap) msg.obj);
            }
        }
    }

    class HeadViewBind {
        @BindView(R.id.v_unread)
        TextView v_unread;
        View itemView;

        public HeadViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }

        @OnClick(R.id.ll_message)
        public void onClicked() {
            Bundle bundle = new Bundle();
            bundle.putInt("reportListCount", reportListCount);
            bundle.putParcelableArrayList("reportComments", reportComments);
            PageTool.go(getActivity(), ReportListActivity.class, bundle);
        }
    }


    class RecyclerViewBind {
        @BindView(R.id.dynamic_recyclerview)
        RecyclerView dynamic_recyclerview;
        @BindView(R.id.spl_refresh)
        SwipeRefreshLayout spl_refresh;
        View itemView;

        public RecyclerViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }

    class LoadingViewBind {
        @BindView(R.id.ll_loading)
        LinearLayout ll_loading;
        @BindView(R.id.ll_tips)
        LinearLayout ll_tips;
        View itemView;

        public LoadingViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }

        @OnClick(R.id.iv_know_tips)
        public void onClicked() {
            SharedPreferencesUtil.putBoolean(getContext(), KeyUtil.KEY_TIPS, true);
            ll_tips.setVisibility(View.GONE);
        }
    }

    class NoDataViewBind {
        @BindView(R.id.ll_no_dynamic)
        AutoLinearLayout ll_no_dynamic;
        @BindView(R.id.ll_tips)
        AutoLinearLayout ll_tips;
        View itemView;

        public NoDataViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }

        @OnClick(R.id.iv_know_tips)
        public void onClicked() {
            SharedPreferencesUtil.putBoolean(getContext(), KeyUtil.KEY_TIPS, true);
            ll_tips.setVisibility(View.GONE);
        }
    }
}
