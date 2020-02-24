package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.AuditAdapter;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
//import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/7/7.
 */
public class AuditAttendeeActivity extends BaseActivity implements TextWatcher, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    public static final int PAYSTATUS = 1;
    public static final int AUDIT = 1;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.tv_hint)
    TextView tvHint;
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
    @BindView(R.id.v_transparent)
    View vTransparent;
    @BindView(R.id.fm_attendee)
    AutoFrameLayout fmAttendee;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ArrayList<Attends> mAllPeople = new ArrayList<Attends>();
    private ArrayList<Attends> search = new ArrayList<Attends>();
    private int limit = 50;
    private int offset = 0;
    private int eventId = -1;
    private int type = 0;
    private int ticketId;
    private int checkin;
    private boolean isSearch = false;
    private AuditAdapter auditAdapter;
    private InputMethodManager inputMethodManager;//软键盘


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_audit);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        //Log.e("onEvent", event.mMsg);
        if (event.mMsg.equals("search")) {
            if (search.size() > 0) {
                llPageStatus.setVisibility(View.GONE);
                vTransparent.setVisibility(View.GONE);
                fmAttendee.setVisibility(View.VISIBLE);
                auditAdapter.setAttendList(search);
                auditAdapter.setNewData(search);
                auditAdapter.setEnableLoadMore(false);
            } else {
                vTransparent.setVisibility(View.GONE);
                fmAttendee.setVisibility(View.GONE);
                llPageStatus.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
                tvPageStatus.setText(R.string.no_find_attendee);
            }
        } else if (event.mMsg.equals(Common.AUDIT_ATTENDEE)) {
            if (search.size() > 0) {
                search.remove(event.pageCount);
                for (int i = 0; i < mAllPeople.size(); i++) {
                    if (event.pageNumber == mAllPeople.get(i).attendId) {
                        mAllPeople.remove(i);
                    }
                }
                auditAdapter.notifyDataSetChanged();
            } else {
                mAllPeople.remove(event.pageCount);
                auditAdapter.notifyDataSetChanged();
            }
            if (mAllPeople.size() == 0 || search.size() == 0) {
                llPageStatus.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
                tvPageStatus.setText(R.string.no_find_audit);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        offset = offset + limit;
        if (type == 0) {
            setList(offset);
        } else {
            setCheckInList(offset);
        }
    }

    private void setList(int offset) {
        List<Attends> mList = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId))
                .and(Attends_Table.payStatuss.is(PAYSTATUS)).and(Attends_Table.audits.is(AUDIT)).orderBy(Attends_Table.strSort, true).offset(offset).limit(limit).queryList();
        mAllPeople.addAll(mList);
        if (auditAdapter == null) {
            initAdapter();
        } else {
            if (mList.size() > 0) {
                auditAdapter.setNewData(mAllPeople);
                if (mList.size() < limit) {
                    auditAdapter.loadMoreComplete();
                    auditAdapter.loadMoreEnd();
                } else {
                    auditAdapter.loadMoreComplete();
                }
            } else {
                auditAdapter.loadMoreEnd();
            }
        }
    }

    private void setCheckInList(int offset) {
        List<Attends> mList = new Select().from(Attends.class)
                .where(Attends_Table.eventId.is(eventId))
                .and(Attends_Table.payStatuss.is(PAYSTATUS))
                .and(OperatorGroup.clause().and(Attends_Table.audits.is(0)).or(Attends_Table.audits.is(2)))
                .and(Attends_Table.ticketIds.is(ticketId))
                .and(Attends_Table.checkins.is(checkin))
                .orderBy(Attends_Table.strSort, true)
                .offset(offset).limit(limit).queryList();
        mAllPeople.addAll(mList);
        if (auditAdapter == null) {
            initAdapter();
        } else {
            if (mList.size() > 0) {
                auditAdapter.setNewData(mAllPeople);
                if (mList.size() < limit) {
                    auditAdapter.loadMoreComplete();
                    auditAdapter.loadMoreEnd();
                } else {
                    auditAdapter.loadMoreComplete();
                }
            } else {
                auditAdapter.loadMoreEnd();
            }
        }
    }


    private void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);//0==审核订单 1==签到人员
        eventId = intent.getIntExtra("eventId", 0);
        if (type == 0) {
            tvTitle.setText(R.string.audit_wait_person);
            setList(0);
        } else {
            ticketId = intent.getIntExtra("ticketId", 0);
            checkin = intent.getIntExtra("checkin", -1);
            if (checkin == 1) {
                tvTitle.setText(R.string.already_checkin);
            } else {
                tvTitle.setText(R.string.no_checkin);
            }
            setCheckInList(0);
        }
    }

    private void initAdapter() {
        auditAdapter = new AuditAdapter(mAllPeople);
        auditAdapter.openLoadAnimation();
        auditAdapter.setOnLoadMoreListener(this, rvAttendee);
        auditAdapter.setOnItemClickListener(this);
        rvAttendee.setAdapter(auditAdapter);
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rvAttendee.setLayoutManager(new LinearLayoutManager(this));
        //   rvAttendee.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.item)));
        etSearch.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        final String result = s.toString();
        search.clear();
        if (TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.GONE);
        } else {
            ivSearchClear.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (type == 0) {
                        List<Attends> seResult = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId))
                                .and(Attends_Table.payStatuss.is(PAYSTATUS))
                                .and(Attends_Table.audits.is(1))
                                .and(OperatorGroup.clause().and(Attends_Table.names.like("%" + result + "%"))
                                        .or(Attends_Table.pinyinNames.like("%" + result + "%"))
                                        .or(Attends_Table.gsonUser.like("%" + result + "%")))
                                .orderBy(Attends_Table.strSort, true).queryList();
                        search.addAll(seResult);
                    } else {
                        List<Attends> seResult = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId))
                                .and(Attends_Table.payStatuss.is(PAYSTATUS))
                                .and(OperatorGroup.clause().and(Attends_Table.audits.is(0)).or(Attends_Table.audits.is(2)))
                                .and(Attends_Table.ticketIds.is(ticketId))
                                .and(Attends_Table.checkins.is(checkin))
                                .and(OperatorGroup.clause().and(Attends_Table.names.like("%" + result + "%"))
                                        .or(Attends_Table.pinyinNames.like("%" + result + "%"))
                                        .or(Attends_Table.gsonUser.like("%" + result + "%")))
                                .orderBy(Attends_Table.strSort, true).queryList();
                        search.addAll(seResult);
                    }

                    EventBus.getDefault().post(new MsgEvent("search"));
                }
            });
            thread.start();
        }
    }


    @OnClick({R.id.ll_title_back, R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_search:
                isSearch = true;
                etSearch.requestFocus();
                etSearch.setFocusable(true);
                vTransparent.setVisibility(View.VISIBLE);
                llCommonTitle.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
                flSearch.setVisibility(View.VISIBLE);
                inputMethodManager.showSoftInput(etSearch, 0);
                auditAdapter.setEnableLoadMore(false);
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                search.clear();
                vTransparent.setVisibility(View.VISIBLE);
                ivSearchClear.setVisibility(View.GONE);
                auditAdapter.setAttendList(mAllPeople);
                auditAdapter.setNewData(mAllPeople);
                break;
            case R.id.btn_search_cancle:
                cancelSearch();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(type == 0) {
            Attends attends = null;
            if (isSearch) {
                if(search.size() > 0) {
                    attends = search.get(position);
                }
            } else {
                if(mAllPeople.size() > 0) {
                    attends = mAllPeople.get(position);
                }
            }
            if (attends != null) {
                Intent intent = new Intent(this, AttendPeopleDetailInfo.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("attendId", attends.attendId);
                intent.putExtra("ref_code", attends.refCodes);
                intent.putExtra("position", position);
                intent.putExtra("remark", attends.notes);
                intent.putExtra("detailType", 1);
                intent.putExtra("orderId", attends.orderIds);
                //   Log.e("aaaa",attends.notes);
                startActivity(intent);
            }
        }
    }

    private void cancelSearch() {
        isSearch = false;
        etSearch.setText("");
        search.clear();
        auditAdapter.setEnableLoadMore(true);
        auditAdapter.setAttendList(mAllPeople);
        auditAdapter.setNewData(mAllPeople);
        vTransparent.setVisibility(View.GONE);
        flSearch.setVisibility(View.GONE);
        llCommonTitle.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.VISIBLE);
        fmAttendee.setVisibility(View.VISIBLE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }


}
