package com.bagevent.home.adapter;

import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.db.EventList;
import com.bagevent.util.CompareRex;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zwj on 2016/5/24.
 */
public class ExercisingAdapter extends BaseQuickAdapter<EventList,BaseViewHolder> {

    public ExercisingAdapter(@Nullable List<EventList> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<EventList>() {
            @Override
            protected int getItemType(EventList eventList) {
                if (eventList.mark.equals("event")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        getMultiTypeDelegate()
                .registerItemType(0, R.layout.exercising_item)
                .registerItemType(1, R.layout.exercising_item_type2);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventList item) {
        switch (helper.getItemViewType()) {
            case 0:
                DecimalFormat format = new DecimalFormat( "0.00 ");

                helper.setText(R.id.tv_event_name, item.eventName)
                        .setText(R.id.tv_home_event_income, "￥" + format.format(item.income));
                String time = CompareRex.compareDiff(item.startTime, item.endTime);
                if (time.equals("start")) {
                    helper.setText(R.id.tv_eventstatus,R.string.doing)
                            .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
                    helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.zhengzaijinxing);
                    if (item.checkinCount == 0 || item.attendeeCount == 0) {
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount + mContext.getResources().getString(R.string.people))
                                .setText(R.id.tv_home_event_status, R.string.have_checkin1)
                                .setProgress(R.id.home_progress, (int) 0, 100);
                    } else {
                        float progress = new BigDecimal((float) item.checkinCount / item.attendeeCount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        progress = progress * 100;//progress设置为33,max设置为3
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join, item.attendeeCount +" "+mContext.getResources().getString(R.string.people))
                                .setText(R.id.tv_home_event_status, R.string.have_checkin1);
                        //Log.e("adapter","progress= " + progress);
                        helper.setProgress(R.id.home_progress,(int)progress,100);
                    }

                } else {
                    helper.setText(R.id.tv_eventstatus,R.string.sign_up_doing)
                            .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
                    helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.baomingzhong);
                    if (item.ticketCount == 0 || item.attendeeCount == 0) {
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join,item.attendeeCount+" "+mContext.getResources().getString(R.string.people))
                                .setProgress(R.id.home_progress, 0, 100)
                                .setText(R.id.tv_home_event_status, R.string.sign_up);
                    } else {
                        float progress = new BigDecimal((float) item.attendeeCount / item.ticketCount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        progress = progress * 100;
                        helper.setGone(R.id.ll_home_attendee, true)
                                .setText(R.id.tv_home_have_join,item.attendeeCount+" "+mContext.getResources().getString(R.string.people))
                                .setProgress(R.id.home_progress, (int) progress, 100)
                                .setText(R.id.tv_home_event_status, R.string.sign_up);
                    }

                }
                break;
//            case 1:
//                helper.setText(R.id.tv_event_name, item.eventName)
//                        .setText(R.id.tv_collect_name, item.collectionName)
//                        .setText(R.id.tv_collect_checkincounts, item.checkinCount + "");
//                break;
        }

    }
}
