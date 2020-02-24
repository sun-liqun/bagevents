package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.new_home.new_interface.callback.RecentContactsCallback;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.uinfo.constant.GenderEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;
import java.util.Map;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/29
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class RecentContactAdapter extends BaseQuickAdapter<RecentContact, BaseViewHolder> {
    private OnItemClickListener itemClickListener;
    private RecentContactsCallback callback;
    public RecentContactAdapter( @Nullable List<RecentContact> data) {
        super(R.layout.layout_new_message_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecentContact item) {
        String nickname = UserInfoHelper.getUserTitleName(item.getContactId(), item.getSessionType());
        helper.setText(R.id.tv_chat_name, nickname)
                .setText(R.id.tv_message, item.getContent())
                .setText(R.id.tv_message_time, TimeUtil.getNewChatTime(item.getTime(),mContext));
        if (item.getUnreadCount() == 0) {
            helper.setGone(R.id.tv_unread_count, false);
        } else {
            helper.setGone(R.id.tv_unread_count, true);
            if (item.getUnreadCount() > 99) {
                helper.setText(R.id.tv_unread_count, R.string.num_more);
            } else {
                helper.setText(R.id.tv_unread_count, item.getUnreadCount() + "");
            }
        }

        helper.getView(R.id.ll_chat_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, helper.getAdapterPosition());
            }
        });
        HeadImageView img_head=helper.getView(R.id.iv_message_avatar);
        img_head.loadBuddyAvatar(item.getContactId());
    }


    public void setItemClickListener(RecentContactAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecentContactsCallback getCallback() {
        return callback;
    }

    public void setCallback(RecentContactsCallback callback) {
        this.callback = callback;
    }
}
