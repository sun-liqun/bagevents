package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.ReportComment;
import com.bagevent.util.CircleRoundTransform;
import com.bagevent.util.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/1/3
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ReportListAdapter extends BaseQuickAdapter<ReportComment, BaseViewHolder> {
    public ReportListAdapter(@Nullable List<ReportComment> data) {
        super(R.layout.item_report_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ReportComment item) {
        helper.setText(R.id.tv_report_content, item.getComment().getCommentText());

        int reportUserCount = item.getUserList().size();
        String reportUser = "";
        if (reportUserCount == 1) {
            reportUser = getUserName(item.getUserList().get(0));
        } else {
            reportUser = getUserName(item.getUserList().get(0)) + "、" + getUserName(item.getUserList().get(1));
        }

        helper.setText(R.id.tv_report_names, Html.fromHtml(String.format("<font color='#000000'>%s</font>&nbsp;<font color='#545454'>等%d人举报了</font>&nbsp;<font color='#000000'>%s</font>&nbsp;<font color='#545454'>发表的</font>",
                reportUser, reportUserCount, getUserName(item.getComment().getUser()))));

        if (TextUtils.isEmpty(item.getComment().getImages())) {
            helper.setVisible(R.id.fl_image, false);
        } else {
            helper.setVisible(R.id.fl_image, true);
            String[] urls = item.getComment().getImages().split(",");

            String url = urls[0];
            if (url.startsWith("//")) {
                url = "https:" + url;
            } else if (url.startsWith("/")) {
//                url = "https://www.bagevent.com" + url;
                url = "https://img.bagevent.com" + url;
            }
            RequestOptions options=new RequestOptions().transform(new CircleRoundTransform(mContext, 4));
//            Glide.with(mContext).load(url).transform(new CircleRoundTransform(mContext, 4)).into((ImageView) helper.getView(R.id.iv_report_image));
            Glide.with(mContext).load(url).apply(options).into((ImageView) helper.getView(R.id.iv_report_image));

            if (urls.length == 1) {
                helper.setVisible(R.id.tv_image_count, false);
            } else {
                helper.setVisible(R.id.tv_image_count, true);
                helper.setText(R.id.tv_image_count, "+" + urls.length);
            }

        }

        helper.addOnClickListener(R.id.tv_shield_message);
        helper.addOnClickListener(R.id.tv_ignore_message);
    }


    private String getUserName(ReportComment.User user) {
        String name = "";
        if (!TextUtils.isEmpty(user.getShowName())) {
            name = user.getShowName();
        } else if (!TextUtils.isEmpty(user.getNickName())) {
            name = user.getNickName();
        } else if (!TextUtils.isEmpty(user.getUserName())) {
            name = user.getUserName();
        } else if (!TextUtils.isEmpty(user.getEmail())) {
            name = user.getEmail();
        } else if (!TextUtils.isEmpty(user.getCellphone())) {
            name = user.getCellphone();
        }
        return name;
    }
}
