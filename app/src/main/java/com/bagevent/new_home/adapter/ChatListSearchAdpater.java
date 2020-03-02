package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ChatSearchData;
import com.bagevent.util.CircleTransform;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/7 0007.
 */

public class ChatListSearchAdpater extends BaseQuickAdapter<ChatSearchData,BaseViewHolder> {
    private OnItemClickListener itemClickListener;

    public ChatListSearchAdpater(@Nullable List<ChatSearchData> data) {
        super(R.layout.layout_message_list_item, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ChatSearchData item) {


        helper.setText(R.id.tv_chat_name, item.getShowName())
                .setText(R.id.tv_message, item.getLastMessage())
                .setText(R.id.tv_message_time, TimeUtil.getNewChatTime(TimeUtil.timeStmp(item.getUpdateTime()),mContext));

            helper.setGone(R.id.tv_unread_count, false)
                    .setGone(R.id.right,false);

        if (!TextUtils.isEmpty(item.getAvatar())) {
            if (item.getAvatar().contains("wx")) {
                Glide.with(mContext).load("http:" + item.getAvatar()).into((ImageView) helper.getView(R.id.iv_message_avatar));
            } else {
                Glide.with(mContext).load(Constants.imgsURL + item.getAvatar()).into((ImageView) helper.getView(R.id.iv_message_avatar));

            }
        } else {
            RequestOptions options=new RequestOptions().transform(new CircleTransform());
            Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_message_avatar));
        }


        helper.getView(R.id.ll_chat_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, helper.getAdapterPosition());
            }
        });
    }
    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
