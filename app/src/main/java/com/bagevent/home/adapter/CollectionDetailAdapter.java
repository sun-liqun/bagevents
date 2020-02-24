package com.bagevent.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.home.data.CollectDetailData;
import com.bagevent.view.CircleTextView;

import java.util.List;

/**
 * Created by zwj on 2016/7/13.
 */
public class CollectionDetailAdapter extends BaseAdapter {

    private List<CollectDetailData.RespObjectBean.CheckInListBean> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public CollectionDetailAdapter(List<CollectDetailData.RespObjectBean.CheckInListBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.size() == 0 ? 0 :mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CollectChildViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new CollectChildViewHolder();
            convertView = mInflater.inflate(R.layout.activity_collection_item,parent,false);
            viewHolder.tv_collect_letter = (CircleTextView) convertView.findViewById(R.id.tv_collect_letter);
            viewHolder.tv_collect_name = (TextView) convertView.findViewById(R.id.tv_collect_name);
            viewHolder.tv_collect_time = (TextView) convertView.findViewById(R.id.tv_collect_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CollectChildViewHolder)convertView.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(position).getAttendeeName())) {
            viewHolder.tv_collect_letter.setText(mData.get(position).getAttendeeName().substring(0,1));
            viewHolder.tv_collect_name.setText(mData.get(position).getAttendeeName());
            viewHolder.tv_collect_time.setText(mData.get(position).getAttendeeCheckInTime());
        }
        return convertView;
    }

    class CollectChildViewHolder {
        private CircleTextView tv_collect_letter;
        private TextView tv_collect_name;
        private TextView tv_collect_time;
    }
}
