package com.bagevent.new_home.new_activity.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.db.EventList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityAdapter extends BaseQuickAdapter<EventList, BaseViewHolder> {

    private int position = -1;

    HashMap<Integer,Boolean> mSelectedPositions = new HashMap<>();
    private ArrayList<String> pos;

    public ActivityAdapter(@Nullable List<EventList> data, ArrayList<String> pos) {
        super(R.layout.item_activity, data);
        this.pos = pos;
        for (int i = 0; i < data.size(); i++) {
            mSelectedPositions.put(i,false);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    public Boolean isItemChecked(int position) {
        if(!mSelectedPositions.containsKey(position)){
            return false;
        }
        return mSelectedPositions.get(position);
    }

//    public void setOrderItem(int position){
//        for (int key:mSelectedPositions.keySet()) {
//            Log.i("-----------",key+""+mSelectedPositions.get(key)+"value"+mSelectedPositions.get(key));
//        }
//    }


    @Override
    protected void convert(final BaseViewHolder helper, EventList item) {

        String startTime = item.startTime;
        String endTime = item.endTime;
        String reSt = startTime.replace("-", ".");
        String reEt = endTime.replace("-", ".");

        helper.setText(R.id.tv_title, item.eventName)
                .setText(R.id.tv_time, reSt.substring(0, reSt.indexOf(" "))
                        + "-" + reEt.substring(0, reEt.indexOf(" ")));

        switch (item.status) {
            case 1:
                helper.setBackgroundColor(R.id.line, Color.parseColor("#FE9300"));
                break;
            case 2:
                helper.setBackgroundColor(R.id.line, Color.parseColor("#4ABB00"));
                break;
            case 3:
                helper.setBackgroundColor(R.id.line, Color.parseColor("#A2AAAD"));
                break;
        }

//        if (helper.getLayoutPosition() == position) {
//            helper.setVisible(R.id.iv_check,true);
//        } else {
//            helper.setVisible(R.id.iv_check, false);
//        }


        if(pos.contains(String.valueOf(helper.getLayoutPosition()))){
            helper.setVisible(R.id.iv_check,true);
        }else {
            helper.setVisible(R.id.iv_check, false);
        }
    }
}
