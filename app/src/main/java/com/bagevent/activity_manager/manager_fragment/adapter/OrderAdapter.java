package com.bagevent.activity_manager.manager_fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.db.Order;

import java.util.List;

/**
 * Created by zwj on 2017/4/24.
 */

public class OrderAdapter extends BaseAdapter {
    private List<Order> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public OrderAdapter(List<Order> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderHolder orderHolder = null;
        if (convertView == null) {
            orderHolder = new OrderHolder();
            convertView = mInflater.inflate(R.layout.order_item, parent, false);
            orderHolder.orderNum = (TextView) convertView.findViewById(R.id.tv_order_num);
            orderHolder.orderState = (TextView) convertView.findViewById(R.id.tv_order_state);
            orderHolder.orderName = (TextView) convertView.findViewById(R.id.tv_order_name);
            orderHolder.orderDate = (TextView) convertView.findViewById(R.id.tv_order_date);
            orderHolder.orderPrice = (TextView) convertView.findViewById(R.id.tv_order_price);
            convertView.setTag(orderHolder);
        } else {
            orderHolder = (OrderHolder) convertView.getTag();
        }
        if (mList.get(position).payStatus == 0) {
            orderHolder.orderState.setText(R.string.unPaid);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.aFF1D00));
        } else if (mList.get(position).payStatus == 1) {
            orderHolder.orderState.setText(R.string.paid);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
        } else if (mList.get(position).payStatus == 2) {
            orderHolder.orderState.setText(R.string.overtimeUnpaid);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.aFF1D00));
        } else if (mList.get(position).payStatus == 4) {
            orderHolder.orderState.setText(R.string.paying);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.a59c709));
        } else if (mList.get(position).payStatus == 5) {
            orderHolder.orderState.setText(R.string.cancelPaid);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
        } else if (mList.get(position).payStatus == 6) {
            orderHolder.orderState.setText(R.string.offLinePay);
            orderHolder.orderState.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
        }

        orderHolder.orderNum.setText(mList.get(position).orderNumber);
        orderHolder.orderName.setText(mList.get(position).buyerName);
        orderHolder.orderPrice.setText(R.string.tv_money + mList.get(position).totalPrice + "");
        orderHolder.orderDate.setText(mList.get(position).orderTime);
        return convertView;
    }

    class OrderHolder {
        TextView orderNum;
        TextView orderState;
        TextView orderName;
        TextView orderDate;
        TextView orderPrice;
    }
}
