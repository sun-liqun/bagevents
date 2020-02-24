package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bagevent.R;
import com.bagevent.db.EventList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/28
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class DynamicEventAdapter extends BaseQuickAdapter<EventList, BaseViewHolder> {

    private ArrayList<String> currentSelectEventId;

    public DynamicEventAdapter(@Nullable List<EventList> data, ArrayList<String> currentSelectEventId) {
        super(R.layout.item_dynamic_event, data);
        this.currentSelectEventId = currentSelectEventId;
    }

    @Override
    protected void convert(BaseViewHolder helper, EventList item) {
        helper.setText(R.id.tv_evnt_name, item.eventName);
        String startTime = item.startTime.split(" ")[0].replace("-", ".");
        String endTime = item.endTime.split(" ")[0].replace("-", ".");
        helper.setText(R.id.tv_event_time, startTime + "-" + endTime);

        View line = helper.getView(R.id.line);

        if (item.status == 1) {
            //已发布
            line.setBackgroundColor(0xfffe9300);
        } else if (item.status == 2) {
            //正在进行
            line.setBackgroundColor(0xff4ABB00);
        } else if (item.status == 3) {
            //已结束
            line.setBackgroundColor(0xffA2AAAD);
        }

        if (currentSelectEventId.contains(String.valueOf(item.eventId))) {
            helper.setVisible(R.id.iv_select, true);
        } else {
            helper.setVisible(R.id.iv_select, false);
        }


    }
}
