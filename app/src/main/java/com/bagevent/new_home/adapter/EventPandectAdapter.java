package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.db.EventList;
import com.bagevent.util.CompareRex;
import com.bagevent.view.CircleProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zwj on 2016/8/26.
 */
public class EventPandectAdapter extends BaseQuickAdapter<EventList, BaseViewHolder> {

    public EventPandectAdapter(@Nullable List<EventList> data) {
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
                .registerItemType(0, R.layout.new_event_pandect_type1)
                .registerItemType(1, R.layout.exercising_item_type2);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventList item) {
        switch (helper.getItemViewType()) {
            case 0:
                DecimalFormat format = new DecimalFormat( "0.00");

                helper.setText(R.id.tv_home_event_name, item.eventName)
                        .setText(R.id.tv_home_event_income, mContext.getResources().getString(R.string.tv_money) + format.format(item.income))
                        .setText(R.id.tv_home_event_address,item.address)
                        .setText(R.id.tv_home_have_join,item.attendeeCount+"")
                        .setText(R.id.tv_sort,R.string.event);
                String time = CompareRex.compareDiff(item.startTime, item.endTime);
                //Log.e("adapter","time=" + time + "  eventName=" + item.eventName);
                if (time.equals("start")) {
//                    if (item.checkinCount == 0 || item.attendeeCount == 0) {
//                        helper.setGone(R.id.ll_home_attendee, true)
//                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
//                                .setText(R.id.tv_home_event_status, "已签到")
//                                .setProgress(R.id.home_progress, (int) 0, item.attendeeCount);
//                    } else {
//                        float progress = new BigDecimal(((float) item.checkinCount / item.attendeeCount)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//                        progress = progress * 100;
//                        helper.setGone(R.id.ll_home_attendee, true)
//                                .setText(R.id.tv_home_have_join, item.attendeeCount + "")
//                                .setText(R.id.tv_home_event_status, "已签到");
//                  //      Log.e("adapter","progress= " + progress);
//                        helper.setProgress(R.id.home_progress,(int)progress,100);
//                    }

                } else {

                    if (item.ticketCount == 0 || item.attendeeCount == 0) {
//                        helper.setGone(R.id.ll_home_attendee, true)
//                                .setText(R.id.tv_home_have_join,item.attendeeCount+"")
////                                .setProgress(R.id.home_progress, 0, 100)
//                                .setText(R.id.tv_home_event_status, "已报名");
                    } else {
//                        float progress = new BigDecimal((float) item.attendeeCount / item.ticketCount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//                        progress = progress * 100;
//                        helper.setGone(R.id.ll_home_attendee, true)
//                                .setText(R.id.tv_home_have_join,item.attendeeCount+"")
//                                .setProgress(R.id.home_progress, (int) progress, 100)
//                                .setText(R.id.tv_home_event_status, "已报名");
                    }

                }

//                if (item.auditCount != 0) {
//                    helper.setGone(R.id.ll_home_audit, true)
//                            .setText(R.id.tv_home_audit, item.auditCount + "");
//                } else {
                    helper.setGone(R.id.ll_home_audit, false);
//                }

                break;
            case 1:
                helper.setText(R.id.tv_event_name, item.eventName)
                        .setText(R.id.tv_collect_name, item.collectionName)
                        .setText(R.id.tv_collect_checkincounts, item.checkinCount + "");

                break;
        }
    }
}
