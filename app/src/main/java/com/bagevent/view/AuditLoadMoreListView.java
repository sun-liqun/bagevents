package com.bagevent.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bagevent.R;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * 上拉加载更多的listView,配合Google官方的下拉刷新使用
 * Created by chengguo on 2016/3/21.
 */
public class AuditLoadMoreListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener, AbsListView.OnScrollListener {


    private static final String TAG = "RefreshListView";
    private Context context;
    private GestureDetector mGestureDetector;
    private View swipeList;
    private ViewGroup itemLayout;
    private int selectedItem;
    private boolean isDelectShow;
    private boolean isRefuse = false;//标记审核是否被拒绝

    private int checkStatus = -1;//签到状态
    private AutoLinearLayout ll_refuse;//审核通过
    private AutoLinearLayout ll_pass;//审核拒绝
    private AutoLinearLayout list_swipe;//审核通过显示隐藏的控件
    private AutoLinearLayout ll_audit_vis;

    private AutoLinearLayout ll_checkIn;
    private ImageView iv_checkIn;
    private TextView tv_checkIn;
    /**
     * 底部加载更多部分
     */
    private boolean isScrollToBottom;//判断是不是滑到了底部
    private View footerView; //底部的footer   view
    private int footerViewHeight; //底部view的高度
    private boolean isLoadingMore = false; //判断是不是"加载更多"

    /**
     * listview的接口，监听listview的下来刷新和上拉加载更多
     * 设置签到接口
     * 设置签到状态接口
     * 跳转到参会人员详情界面接口
     */

    private AuditPassListener mAuditPassListener;
    private AuditRefuseListener mAuditRefuseListener;
    private OnRefreshListener mOnRefreshListener;
    private OnCheckInListener onCheckInListener;
    private OnSetStatusListener onSetStatusListener;
    private AuditIsRefuseListener auditIsRefuseListener;
    private ToAttendDetail toAttendDetail;

    public AuditLoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        initFooterView();
        this.setOnScrollListener(this);
        this.context = context;

    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        if (isDelectShow) {
            itemLayout.removeView(swipeList);
            swipeList = null;
            isDelectShow = false;
            mGestureDetector.onTouchEvent(arg1);
            return false;
        } else {
            return mGestureDetector.onTouchEvent(arg1);
        }

    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        if (!isDelectShow) {
            selectedItem = pointToPosition((int) arg0.getX(), (int) arg0.getY());
            return false;
        } else {
            if (selectedItem != pointToPosition((int) arg0.getX(), (int) arg0.getY())) {
                selectedItem = pointToPosition((int) arg0.getX(), (int) arg0.getY());
            }

        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
                           float arg3) {

        if((arg2 < 0 && arg3 > 0) || (arg2 <0 && arg3 <0)) {
            if (!isDelectShow && Math.abs(arg2) > Math.abs(arg3)) {
                //加载侧滑出的控件
                swipeList = LayoutInflater.from(getContext()).inflate(R.layout.list_audit_swipe, null);
                ll_pass = (AutoLinearLayout) swipeList.findViewById(R.id.ll_audit_pass);
                ll_refuse = (AutoLinearLayout) swipeList.findViewById(R.id.ll_audit_refuse);
                ll_audit_vis = (AutoLinearLayout) swipeList.findViewById(R.id.ll_audit_vis);
                list_swipe = (AutoLinearLayout) swipeList.findViewById(R.id.list_swipe);
                ll_checkIn = (AutoLinearLayout) swipeList.findViewById(R.id.ll_checkin);
                iv_checkIn = (ImageView) swipeList.findViewById(R.id.iv_checkin);
                tv_checkIn = (TextView) swipeList.findViewById(R.id.tv_checkin);
                try {
                    int s = -1;
                    int audit = auditIsRefuseListener.isRefuse(selectedItem);
                    s = onSetStatusListener.setCheck(selectedItem);
                   // Log.e("sss-->",s+"");
                    if( s == 1 ) {
                        setViewStatus(s);
                    }else {
                        setAuditOrCheckIn(audit);
                    }

                } catch (NullPointerException e) {

                }

                ll_pass.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuditPassListener.onPass(selectedItem);
                    }
                });

                ll_refuse.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemLayout.removeView(swipeList);
                        swipeList = null;
                        isDelectShow = false;
                        mAuditRefuseListener.onRefuse(selectedItem);
                    }
                });

                ll_checkIn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemLayout.removeView(swipeList);
                        swipeList = null;
                        isDelectShow = false;
                        onCheckInListener.checkIn(selectedItem);
                    }
                });

                itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                try {
                    itemLayout.addView(swipeList, layoutParams);
                    isDelectShow = true;
                } catch (NullPointerException e) {

                }
            }
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
                            float arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (!isDelectShow) {
            selectedItem = pointToPosition((int) arg0.getX(), (int) arg0.getY());
            toAttendDetail.toDetailPage(selectedItem);
            return false;
        } else {
            if (selectedItem != pointToPosition((int) arg0.getX(), (int) arg0.getY())) {
            //    selectedItem = pointToPosition((int) arg0.getX(), (int) arg0.getY());
            }
        }
        return false;
    }


    /**
     * 初始化底部view
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.footer_layout, null);
        //设置（0，0）以便系统测量footerView的宽高
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        this.addFooterView(footerView);
    }

    /**
     * 监听listview滚动的状态变化，如果滑到了底部，就“加载更多..."
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            if (isScrollToBottom && !isLoadingMore) {
                isLoadingMore = true;
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount());
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onLoadingMore();
                }
            }
        }
    }

    /**
     * 监听listview滚动的状态变化，判断当前是不是滑到了底部
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }

    }

    /**
     * 设置审核通过接口
     */
    public interface AuditPassListener {
        void onPass(int pos);
    }

    public void setOnAuditPassListener(AuditPassListener listener) {
        this.mAuditPassListener = listener;
    }

    /**
     * 设置审核拒绝接口
     */
    public interface AuditRefuseListener {
        void onRefuse(int pos);
    }

    public void setOnAuditRefuseListener(AuditRefuseListener listener) {
        this.mAuditRefuseListener = listener;
    }

    /**
     * 滑动的时候判断审核是否通过，如果通过则显示出侧滑菜单，否则无侧滑菜单
     */
    public interface AuditIsRefuseListener {
        int isRefuse(int pos);
    }

    public void setIsFilingSwipe(AuditIsRefuseListener listener) {
        this.auditIsRefuseListener = listener;
    }

    /**
     * 设置监听接口，当为
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }


    /**
     * 为外界提供的方法，当Activity中的加载更多数据加载完后，就调用这个方法来隐藏底部的footerView
     */
    public void loadMoreComplete() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }


    /**
     * 设置接口，供外界实现，监听listview的刷新和加载更多的状态
     */
    public interface OnRefreshListener {
        /**
         * 上拉加载更多
         */
        void onLoadingMore();
    }

    /**
     * 设置签到接口
     */

    public interface OnCheckInListener {

        void checkIn(int index);
    }

    public void setCheckInListener(OnCheckInListener Listener) {
        this.onCheckInListener = Listener;
    }

    /**
     * 设置item的跳转接口
     */
    public interface ToAttendDetail {
        void toDetailPage(int position);
    }

    public void setToAttendDetail(ToAttendDetail toAttendDetail) {
        this.toAttendDetail = toAttendDetail;
    }

    public void setAuditOrCheckIn(int audit) {
        if (audit == 2) {
            isRefuse = false;
            list_swipe.setVisibility(VISIBLE);
            ll_audit_vis.setVisibility(GONE);
        }
    }

    /**
     * 根据签到状态设置view的属性
     *
     * @param status
     */
    public void setViewStatus(int status) {
        // Log.e("status--->", status + "");
        if (status != 0) {
            list_swipe.setVisibility(VISIBLE);
            ll_audit_vis.setVisibility(GONE);
            iv_checkIn.setImageResource(R.drawable.checkin_cancle);
            tv_checkIn.setText(R.string.checkin_cancle);
            ll_checkIn.setBackgroundColor(ContextCompat.getColor(context,R.color.grey));
        } else {
            iv_checkIn.setImageResource(R.drawable.checkin);
            tv_checkIn.setText(R.string.checkin);
            ll_checkIn.setBackgroundColor(ContextCompat.getColor(context,R.color.a59c709));
        }
    }

    public void setStatus(OnSetStatusListener listener) {
        this.onSetStatusListener = listener;
    }

    public interface OnSetStatusListener {
        int setCheck(int i);
    }

}
