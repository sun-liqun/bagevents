package com.bagevent.activity_manager.manager_fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.db.CollectList;
import com.bagevent.util.CompareRex;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by zwj on 2016/7/15.
 */
public class CollectManagerAdapter extends BaseAdapter {
    private List<CollectList> mCollectData;
    private LayoutInflater mInflater;
    private Context mContext;

    public CollectManagerAdapter(Context mContext, List<CollectList> mList) {
        this.mContext = mContext;
        this.mCollectData = mList;
        this.mInflater = LayoutInflater.from(mContext);
        //   Log.e("fd",mCollectData.size()+"");
    }

    @Override
    public int getCount() {
        return mCollectData.size();
    }

    @Override
    public Object getItem(int position) {
        return mCollectData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //    Log.e("fd",mCollectData.get(position).collectionName);
        CollectManagerViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.collect_manager_item, parent, false);
            holder = new CollectManagerViewHolder();
            holder.ll_manager_count = (AutoLinearLayout) convertView.findViewById(R.id.ll_manager_count);
            holder.tv_manager_count = (TextView) convertView.findViewById(R.id.tv_manager_count);
            holder.tv_manager_state = (TextView) convertView.findViewById(R.id.tv_manager_state);
            holder.tv_manager_ticketname = (TextView) convertView.findViewById(R.id.tv_manager_ticketname);
            holder.tv_manager_use = (TextView) convertView.findViewById(R.id.tv_manager_use);
            holder.tv_manager_name = (TextView) convertView.findViewById(R.id.tv_manager_name);
            holder.tv_mark2 = (TextView) convertView.findViewById(R.id.tv_mark2);
            convertView.setTag(holder);
            AutoUtils.auto(convertView);
        } else {
            holder = (CollectManagerViewHolder) convertView.getTag();
        }

        holder.tv_manager_name.setText(mCollectData.get(position).collectionName);

        if (!TextUtils.isEmpty(mCollectData.get(position).ticketStr)) {
            holder.tv_manager_ticketname.setText(mCollectData.get(position).ticketStr);
        } else if (!TextUtils.isEmpty(mCollectData.get(position).productStr)) {
            holder.tv_manager_ticketname.setText(mCollectData.get(position).productStr);
        } else {
            holder.tv_manager_ticketname.setText(R.string.no_limit);
        }

        //Log.e("CollectManagerAdapter",mCollectData.get(position).collectionName + "  " + mCollectData.get(position).limitType);

        if (mCollectData.get(position).collectionType == 0) {
            if (mCollectData.get(position).limitType == 0) {
                holder.tv_manager_use.setText(R.string.collect_info);
                holder.tv_mark2.setText(R.string.tv_mark2);
            } else {
                holder.tv_manager_use.setText("商品采集");
                holder.tv_mark2.setText(R.string.tv_mark3);
            }
        } else {
            holder.tv_manager_use.setText(R.string.collect_out_in);
            holder.tv_mark2.setText(R.string.tv_mark2);
        }

        String time = CompareRex.dateCompare(mCollectData.get(position).startTime, mCollectData.get(position).endTime);
        String count = mCollectData.get(position).checkinCount + "";
        if (time.contains(R.string.start + "")) {
            holder.tv_manager_state.setVisibility(View.GONE);
            holder.ll_manager_count.setVisibility(View.VISIBLE);
            holder.tv_manager_count.setText(count);
        } else if (time.contains(R.string.not_start + "")) {
            holder.tv_manager_state.setVisibility(View.VISIBLE);
            holder.ll_manager_count.setVisibility(View.GONE);
            holder.tv_manager_count.setText("0");
            holder.tv_manager_state.setText(R.string.not_start1);
        } else if (time.contains(R.string.stop + "")) {
            holder.tv_manager_state.setVisibility(View.VISIBLE);
            holder.ll_manager_count.setVisibility(View.GONE);
            holder.tv_manager_count.setText("0");
            holder.tv_manager_state.setText(R.string.stop);
        }

        return convertView;
    }

    class CollectManagerViewHolder {
        TextView tv_manager_name;
        TextView tv_manager_use;
        TextView tv_manager_ticketname;
        TextView tv_manager_state;
        TextView tv_manager_count;
        AutoLinearLayout ll_manager_count;
        TextView tv_mark2;
    }
}
