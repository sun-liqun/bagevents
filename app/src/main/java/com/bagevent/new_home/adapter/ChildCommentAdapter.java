package com.bagevent.new_home.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.new_home.data.ExhibitorDynamicData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/02/28
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ChildCommentAdapter extends BaseQuickAdapter<ExhibitorDynamicData.ChildCommentList,BaseViewHolder> {


    public ChildCommentAdapter( @Nullable List<ExhibitorDynamicData.ChildCommentList> data) {
        super(R.layout.item_dynamic_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitorDynamicData.ChildCommentList item) {
        RequestOptions options=new RequestOptions()
                .error(R.drawable.img_failed)
                .dontAnimate();
        ExhibitorDynamicData.User user=item.getUser();

        ImageView iv_head=helper.getView(R.id.iv_head);

        if (item.isReplyComment()){
            helper.setText(R.id.tv_name,item.getShowName()+" "+mContext.getResources().getString(R.string.reply1)+" "+item.getReplyComment().getIsReplyToUser().getShowName());
        }else {
            helper.setText(R.id.tv_name,user.getShowName());
        }
        if (!TextUtils.isEmpty(item.getShowAvater())){
            Glide.with(mContext).load(item.getShowAvater()).apply(options).into(iv_head);
        }else {
            String url=user.getAvatar();
            if (!TextUtils.isEmpty(url)){
                if (url.startsWith("//")) {
                    url = "https:" + url;
                } else if (url.startsWith("/")) {
//                    url = "https://www.bagevent.com" + url;
                    url = "https://img.bagevent.com" + url;
                }
                Glide.with(mContext).load(url).apply(options).into(iv_head);
            }else {
                Glide.with(mContext).load(R.drawable.shadow_bg).into(iv_head);
            }
        }
        helper.setText(R.id.tv_comment,item.getCommentText());
        helper.setText(R.id.tv_time,item.getShowTime());

    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
