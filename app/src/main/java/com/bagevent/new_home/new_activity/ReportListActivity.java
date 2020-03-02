package com.bagevent.new_home.new_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.ReportComment;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.ReportListAdapter;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/1/2
 * <p>
 * desp 举报信息列表页面
 * <p>
 * <p>
 * =============================================
 */
public class ReportListActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private ArrayList<ReportComment> reportComments;
    private ReportListAdapter reportListAdapter;
    private int reportListCount;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report_list);
        ButterKnife.bind(this);

        reportListCount = getIntent().getIntExtra("reportListCount", 0);
        tv_title.setText(Html.fromHtml(String.format("<font color='#000000'>"+getString(R.string.report)+"</font>&nbsp;<font color='#ff9000'>(%d)</font>", reportListCount)));

        reportComments = getIntent().getParcelableArrayListExtra("reportComments");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reportListAdapter = new ReportListAdapter(reportComments);
        reportListAdapter.setOnItemChildClickListener(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(reportListAdapter);

    }


    @OnClick(R.id.ll_title_back)
    public void back() {
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ReportComment reportComment = reportComments.get(position);
        switch (view.getId()) {
            case R.id.tv_shield_message:
                shieldReportComment(reportComment.getComment(), reportComment.getReportCommentId(), position);
                break;
            case R.id.tv_ignore_message:
                ignoreReportComment(reportComment.getReportCommentId(), position);
                break;
        }
    }

    private void shieldReportComment(ReportComment.Comment comment, int reportCommentId, final int position) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                .addParams("reportCommentId", String.valueOf(reportCommentId))
                .addParams("eventId", String.valueOf(comment.getEventId()))
                .addParams("commentId", String.valueOf(comment.getCommentId()))
                .addParams("status", String.valueOf(0))
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.showCenter(getString(R.string.error_shield));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            reportListCount--;
                            tv_title.setText(Html.fromHtml(String.format("<font color='#000000'>"+getString(R.string.report)+"</font>&nbsp;<font color='#ff9000'>(%d)</font>", reportListCount)));
                            reportListAdapter.remove(position);
                            EventBus.getDefault().post(new MsgEvent("sheild_report", position));
                            if (reportListCount == 0) {
                                AppManager.getAppManager().finishActivity(ReportListActivity.this);
                            }

                        } else {
                            TosUtil.showCenter(getString(R.string.error_shield));
                        }
                    }
                });
    }

    private void ignoreReportComment(int reportCommentId, final int position) {

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.IGNORE_REPORT_COMMENT)
                .addParams("reportCommentId", String.valueOf(reportCommentId))
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.showCenter(getString(R.string.error_shield));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.e("ignoreReportComment->" + response);
                        if (response.contains("\"retStatus\":200")) {
                            reportListCount--;
                            tv_title.setText(Html.fromHtml(String.format("<font color='#000000'>"+getString(R.string.report)+"</font>&nbsp;<font color='#ff9000'>(%d)</font>", reportListCount)));
                            reportListAdapter.remove(position);
                            EventBus.getDefault().post(new MsgEvent("ignore_report", position));
                            if (reportListCount == 0) {
                                AppManager.getAppManager().finishActivity(ReportListActivity.this);
                            }
                        } else {
                            TosUtil.showCenter(getString(R.string.error_ignore));
                        }
                    }
                });
    }


}
