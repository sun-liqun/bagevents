package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.db.Chat;
import com.bagevent.util.CircleTransform;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public class ChatListAdapter extends BaseQuickAdapter<Chat, BaseViewHolder> {

    private OnTopAndRemoveListener listener;
    private OnItemClickListener itemClickListener;
    private boolean isVisible = false;

    public ChatListAdapter(@Nullable List<Chat> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Chat>() {
            @Override
            protected int getItemType(Chat chat) {
                if (chat.sys) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        getMultiTypeDelegate()
                .registerItemType(0, R.layout.layout_message_list_item_type1)
                .registerItemType(1, R.layout.layout_message_list_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Chat item) {
        RequestOptions options=new RequestOptions().transform(new CircleTransform());
        switch (helper.getItemViewType()) {
            case 0:
                helper.setGone(R.id.v_unread, isVisible);
                String lastMessage = "";
                if (!TextUtils.isEmpty(item.lastMessage)) {
                    try {
                        JSONObject object = new JSONObject(item.lastMessage);
                        lastMessage = object.optString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    helper.setText(R.id.tv_sys_message, R.string.have_new_order)
                            .setTag(R.id.tv_sys_message, helper.getAdapterPosition());
//                    Glide.with(mContext).load(Constants.imgsURL + item.avatar).placeholder(R.mipmap.icon).into((ImageView) helper.getView(R.id.iv_sys_message_avatar));
                } else {
                    helper.setGone(R.id.tv_sys_message, false)
                            .setTag(R.id.tv_sys_message, helper.getAdapterPosition());
//                    Glide.with(mContext).load(Constants.imgsURL + item.avatar).placeholder(R.mipmap.icon).into((ImageView) helper.getView(R.id.iv_sys_message_avatar));
                }

                Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_sys_message_avatar));
//                Glide.with(mContext).load(R.mipmap.icon).transform(new CircleTransform(mContext)).into((ImageView) helper.getView(R.id.iv_sys_message_avatar));
                helper.getView(R.id.ll_sys_items).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, helper.getAdapterPosition());
                    }
                });
                break;
            case 1:
                if (item.isTop) {
                    helper.setText(R.id.tv_top, R.string.cancel_top);
                } else {
                    helper.setText(R.id.tv_top, R.string.top);
                }
                helper.setText(R.id.tv_chat_name, item.showName)
                        .setText(R.id.tv_message, item.lastMessage)
                        .setText(R.id.tv_message_time, TimeUtil.getNewChatTime(item.updateTime,mContext));

                if (item.unReadCount == 0) {
                    helper.setGone(R.id.tv_unread_count, false);
                } else {
                    helper.setGone(R.id.tv_unread_count, true);
                    if (item.unReadCount > 99) {
                        helper.setText(R.id.tv_unread_count, R.string.num_more);
                    } else {
                        helper.setText(R.id.tv_unread_count, item.unReadCount + "");
                    }
                }

                if (!TextUtils.isEmpty(item.avatar)) {

                    if (item.avatar.startsWith("http")) {
                        Glide.with(mContext).load(item.avatar).apply(options).into((ImageView) helper.getView(R.id.iv_message_avatar));
                    } else if (item.avatar.contains("wx")) {
                        Glide.with(mContext).load("http:" + item.avatar).into((ImageView) helper.getView(R.id.iv_message_avatar));

                    } else {
                        Glide.with(mContext).load(Constants.imgsURL + item.avatar).into((ImageView) helper.getView(R.id.iv_message_avatar));
                    }

//                    if (item.avatar.contains("wx")) {
//                        Glide.with(mContext).load("http:" + item.avatar).into((ImageView) helper.getView(R.id.iv_message_avatar));
//                    } else if (item.avatar.contains("http://")) {
//                        Glide.with(mContext).load(item.avatar).into((ImageView) helper.getView(R.id.iv_message_avatar));
//                    } else {
//                        Glide.with(mContext).load(Constants.imgsURL + item.avatar).into((ImageView) helper.getView(R.id.iv_message_avatar));
//                    }
                } else {
                    Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_message_avatar));
                }


                helper.getView(R.id.ll_chat_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, helper.getAdapterPosition());
                    }
                });

                helper.getView(R.id.ll_top).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.top(helper.getAdapterPosition());
                    }
                });

                helper.getView(R.id.ll_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.remove(helper.getAdapterPosition());
                    }
                });
                break;
        }

    }

    public void setTopAndRemoveListener(OnTopAndRemoveListener listener) {
        this.listener = listener;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnTopAndRemoveListener {
        void top(int postion);
        void remove(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setUnReadView(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
