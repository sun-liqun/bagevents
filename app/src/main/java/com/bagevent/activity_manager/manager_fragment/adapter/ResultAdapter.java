package com.bagevent.activity_manager.manager_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.bagevent.db.Attends;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by zwj on 2016/6/7.
 */
public class ResultAdapter extends BaseAdapter{
    private List<AttendPeople.RespObjectBean.ObjectsBean> mDatas;
    private LayoutInflater mInflater;

    public ResultAdapter(Context context,List<AttendPeople.RespObjectBean.ObjectsBean> mDatas) {
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultViewHolder resultHolder = null;
        if(convertView == null){
            resultHolder = new ResultViewHolder();
            convertView = mInflater.inflate(R.layout.search_result_list,parent,false);
            resultHolder.tv_result_name = (TextView)convertView.findViewById(R.id.tv_result_name);
            resultHolder.tv_result_ticket = (TextView)convertView.findViewById(R.id.tv_result_ticket_name);
            convertView.setTag(resultHolder);
            AutoUtils.auto(convertView);
        }else{
            resultHolder = (ResultViewHolder)convertView.getTag();
        }
        resultHolder.tv_result_name.setText(mDatas.get(position).getName());
        return convertView;
    }
    class ResultViewHolder{
        TextView tv_result_name;
        TextView tv_result_ticket;
    }
}
