package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.EventTicket;
import com.bagevent.db.EventTicket_Table;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by zwj on 2017/10/26.
 */

public class ActivityOrderDetailAdapter extends BaseQuickAdapter<Attends, BaseViewHolder> {

    private List<Attends> mOrder;
    private int payStatus;

    public ActivityOrderDetailAdapter(@LayoutRes int layoutResId, @Nullable List<Attends> data) {
        super(layoutResId, data);
        this.mOrder = data;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, Attends item) {
        if (!TextUtils.isEmpty(item.avatars)) {
            helper.setGone(R.id.ct_avater, false)
                    .setGone(R.id.cv_avater, true);
            if (item.avatars.contains("wx")) {
                Glide.with(mContext).load("https:" + item.avatars).into((ImageView) helper.getView(R.id.cv_avater));

            } else {
                Glide.with(mContext).load(Constants.imgsURL + item.avatars).into((ImageView) helper.getView(R.id.cv_avater));

            }
        } else {
            helper.setGone(R.id.ct_avater, true)
                    .setGone(R.id.cv_avater, false);
            if (item.names.length() > 0) {
                helper.setText(R.id.ct_avater, item.names.substring(0, 1));
            } else {
                helper.setText(R.id.ct_avater, item.strSort);
            }
        }

        //    Log.e("fd",item.payStatuss +" ");

        if (item.payStatuss == 10) {
            helper.setGone(R.id.iv_attendee_paystatus, true);
            Glide.with(mContext).load(R.drawable.quit_ticket).into((ImageView) helper.getView(R.id.iv_attendee_paystatus));
        } else if (item.audits == 1) {
            helper.setGone(R.id.iv_attendee_paystatus, true);
            Glide.with(mContext).load(R.drawable.daishenhe).into((ImageView) helper.getView(R.id.iv_attendee_paystatus));
        } else if (item.audits == 3 || payStatus == 13) {
            helper.setGone(R.id.iv_attendee_paystatus, true);
            Glide.with(mContext).load(R.drawable.refuse_audit).into((ImageView) helper.getView(R.id.iv_attendee_paystatus));
        } else {
            helper.setGone(R.id.iv_attendee_paystatus, false);
        }
        if (item.checkins == 1) {
            helper.setVisible(R.id.v_checkin, true);
        } else {
            helper.setVisible(R.id.v_checkin, false);
        }

        helper.setText(R.id.tv_attendee_name, item.names);
        List<EventTicket> tickets = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(item.eventId)).queryList();
        //  Log.e("ticketSize",tickets.size()+"");


        EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(item.eventId)).and(EventTicket_Table.ticketIds.is(item.ticketIds)).querySingle();
        if (ticket != null) {
            //  Log.e("coorect","ticket is null " + item.ticketIds + "  " + item.eventId);
            helper.setText(R.id.tv_attendee_ticket, ticket.ticketNames);
        } else {
            Log.e("err", "ticket is null ");
        }
    }

    public void setOrderPaystatus(int orderPayStatus) {
        this.payStatus = orderPayStatus;
    }
}
