package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.db.ChatMessage;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.util.CircleTransform;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public class ChatSysMessageAdapter extends BaseQuickAdapter<ChatMessage, BaseViewHolder> {
    private List<ChatMessage> mData;

    public ChatSysMessageAdapter(@Nullable List<ChatMessage> data) {
        super(R.layout.layout_chat_sys_item, data);
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMessage item) {
        RequestOptions options=new RequestOptions().transform(new CircleTransform());
//        Glide.with(mContext).load(R.mipmap.icon).transform(new CircleTransform(mContext)).into((ImageView) helper.getView(R.id.iv_chat_attendee));
        Glide.with(mContext).load(R.mipmap.icon).apply(options).into((ImageView) helper.getView(R.id.iv_chat_attendee));
        if (helper.getAdapterPosition() == 0) {
            helper.setGone(R.id.tv_attendee_last_time,true)
                    .setText(R.id.tv_attendee_last_time,showTime(item.sendTime));
        }else {
            String s = TimeUtil.getTimeInterval(mData.get(helper.getAdapterPosition() - 1).sendTime,item.sendTime,mContext);
            if(!TextUtils.isEmpty(s)) {
                helper.setGone(R.id.tv_attendee_last_time,true)
                        .setText(R.id.tv_attendee_last_time,s);
            }else {
                helper.setGone(R.id.tv_attendee_last_time,false);
            }
        }
        try {
            String json = removeUnicode(item.content);
            JSONObject object = new JSONObject(json);
            String orderNumer = object.optString("orderNumber");
            String buyerEmail = object.optString("buyerEmail");
            String title = object.optString("title");
            String buyerCellphone = object.optString("buyerCellphone");
            String buyerName = object.optString("buyerName");
            String orderTime = object.optString("orderTime");
            String eventId = object.optString("eventId");

            if(!TextUtils.isEmpty(eventId)) {
                EventList event = new Select().from(EventList.class).where(EventList_Table.eventId.is(Integer.parseInt(eventId))).querySingle();
                if(event != null) {
                    helper.setText(R.id.tv_msg_event_name,event.eventName)
                            .setGone(R.id.ll_msg_event,true);
                }else {
                    helper.setGone(R.id.ll_msg_event,false);
                }
            }else {
                helper.setGone(R.id.ll_msg_event,false);
            }

            if(!TextUtils.isEmpty(orderTime)) {
                helper.setText(R.id.tv_msg_order_create_time,orderTime)
                        .setTag(R.id.tv_msg_order_create_time,helper.getAdapterPosition());
            }else {
                helper.setText(R.id.tv_msg_order_create_time,"")
                        .setTag(R.id.tv_msg_order_create_time,helper.getAdapterPosition());
            }

            if(!TextUtils.isEmpty(orderNumer)) {
                helper.setText(R.id.tv_notification_title, title)
                        .setTag(R.id.tv_notification_title, helper.getAdapterPosition())
                        .setText(R.id.tv_msg_orderNumer, orderNumer)
                        .setTag(R.id.tv_msg_orderNumer, helper.getAdapterPosition())
                        .setText(R.id.tv_msg_buyer, buyerName)
                        .setTag(R.id.tv_msg_buyer, helper.getAdapterPosition())
                        .setText(R.id.tv_msg_buyer_email, buyerEmail)
                        .setTag(R.id.tv_msg_buyer_email, helper.getAdapterPosition());

                if (TextUtils.isEmpty(buyerCellphone)) {
                    helper.setGone(R.id.ll_msg_cellphone, false);
                } else {
                    helper.setGone(R.id.ll_msg_cellphone, true)
                            .setText(R.id.tv_msg_buyer_cellphone, buyerCellphone)
                            .setTag(R.id.tv_msg_buyer_cellphone, helper.getAdapterPosition());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String removeUnicode(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }

    private String showTime(long time) {
        return TimeUtil.getNewChatTime(time,mContext);
    }
}
