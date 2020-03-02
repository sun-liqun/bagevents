package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.db.ChatMessage;
import com.bagevent.util.CircleTransform;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;

/**
 * Created by ZWJ on 2017/11/27 0027.
 */

public class ChatMessageAdapter extends BaseQuickAdapter<ChatMessage, BaseViewHolder> {
    private String avatar;
    private List<ChatMessage> mData;
    private String hostAvatar;

    public ChatMessageAdapter(@Nullable List<ChatMessage> data, String avatar) {
        super(data);
        this.mData = data;
        this.avatar = avatar;
        setMultiTypeDelegate(new MultiTypeDelegate<ChatMessage>() {
            @Override
            protected int getItemType(ChatMessage chatMessage) {
                if (chatMessage.org) { //org为true表示是我的发的消息
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        getMultiTypeDelegate()
                .registerItemType(0, R.layout.layout_chat_b)
                .registerItemType(1, R.layout.layout_chat_a);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMessage item) {
        RequestOptions options=new RequestOptions().transform(new CircleTransform());
        switch (helper.getItemViewType()) {
            case 0:
                if (helper.getAdapterPosition() == 0) {
                    helper.setGone(R.id.tv_last_time, true)
                            .setText(R.id.tv_last_time, showTime(item.sendTime));
                } else {
                    String s = TimeUtil.getTimeInterval(mData.get(helper.getAdapterPosition() - 1).sendTime, item.sendTime,mContext);
                    if (!TextUtils.isEmpty(s)) {
                        helper.setGone(R.id.tv_last_time, true)
                                .setText(R.id.tv_last_time, s);
                    } else {
                        helper.setGone(R.id.tv_last_time, false);
                    }
                }

                if (item.isSending) {
                    helper.setGone(R.id.progress_send, true);
                } else {
                    helper.setGone(R.id.progress_send, false);
                }
                String headIcon = SharedPreferencesUtil.get(mContext, "avatar", "");
                if (headIcon.startsWith("http")) {
//                    Glide.with(mContext).load(headIcon).transform(new CircleTransform(mContext)).into((ImageView) helper.getView(R.id.iv_chat_org));
                    Glide.with(mContext).load(headIcon).apply(options).into((ImageView) helper.getView(R.id.iv_chat_org));
                } else if(headIcon.contains("wx")){
//                    Glide.with(mContext).load("http:" + headIcon).transform(new CircleTransform(mContext)).into((ImageView) helper.getView(R.id.iv_chat_org));
                    Glide.with(mContext).load("http:" + headIcon).apply(options).into((ImageView) helper.getView(R.id.iv_chat_org));
                }else {
                    Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_chat_org));
                }
                helper.setText(R.id.tv_org_chat_content, item.content)
                        .setTag(R.id.tv_org_chat_content, item.content);
                break;
            case 1:
                if (helper.getAdapterPosition() == 0) {
                    helper.setGone(R.id.tv_attendee_last_time, true)
                            .setText(R.id.tv_attendee_last_time, showTime(item.sendTime));
                } else {
                    String s = TimeUtil.getTimeInterval(mData.get(helper.getAdapterPosition() - 1).sendTime, item.sendTime,mContext);
                    if (!TextUtils.isEmpty(s)) {
                        helper.setGone(R.id.tv_attendee_last_time, true)
                                .setText(R.id.tv_attendee_last_time, s);
                    } else {
                        helper.setGone(R.id.tv_attendee_last_time, false);
                    }
                }

                if (item.content.contains(String.valueOf(R.string.order))) {
                    if (!TextUtils.isEmpty(item.eventId) && !TextUtils.isEmpty(item.attendeeId)) {
                        helper.setGone(R.id.tv_modify, true)
                                .setText(R.id.tv_modify, R.string.change_order_info);
                    } else {
                        helper.setGone(R.id.tv_modify, false);
                    }
                } else {
                    helper.setGone(R.id.tv_modify, false);
                }

                if (avatar.startsWith("http")) {
                    Glide.with(mContext).load(avatar).apply(options).into((ImageView) helper.getView(R.id.iv_chat_attendee));
                } else if(avatar.contains("wx")){
                    Glide.with(mContext).load("http:" + avatar).apply(options).into((ImageView) helper.getView(R.id.iv_chat_attendee));
                }else {
                    Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_chat_attendee));
                }

                helper.setText(R.id.tv_attendee_chat_content, item.content)
                        .setTag(R.id.tv_attendee_chat_content, item.content);
                helper.addOnClickListener(R.id.tv_modify);
                if (!TextUtils.isEmpty(item.attendeeId)) {
                    if (item.content.contains(String.valueOf(R.string.e_ticket)) || item.content.contains(String.valueOf(R.string.admission_ticket)) || item.content.contains(String.valueOf(R.string.participants))) {
                        helper.setGone(R.id.tv_modify, true)
                                .setText(R.id.tv_modify, R.string.ticket_again);
                    } else if (item.content.contains(String.valueOf(R.string.change))) {
                        helper.setGone(R.id.tv_modify, true)
                                .setText(R.id.tv_modify, R.string.change_order_info);
                    } else {
                        helper.setGone(R.id.tv_modify, false);
                    }
                } else {
                    helper.setGone(R.id.tv_modify, false);
                }
                break;
        }
    }

    private String showTime(long time) {
        return TimeUtil.getNewChatTime(time,mContext);
    }
}
