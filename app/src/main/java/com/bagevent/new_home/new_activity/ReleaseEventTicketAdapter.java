package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/19.
 */
public class ReleaseEventTicketAdapter extends BaseAdapter {

    private ArrayList<TicketInfo.RespObjectBean> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnDeleteTicketClickListener deleteTicketClickListener;

    public ReleaseEventTicketAdapter(ArrayList<TicketInfo.RespObjectBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ReleaseTicketHolder holder = null;
        if(view == null) {
            holder = new ReleaseTicketHolder();
            view = mInflater.inflate(R.layout.release_event_ticket_item,null);
            holder.releaseTicketName = (TextView)view.findViewById(R.id.tv_ticket_name);
            holder.releaseTicketPrice = (TextView)view.findViewById(R.id.tv_ticket_price);
            holder.releaseTicketDelete = (ImageView)view.findViewById(R.id.iv_delete_ticket);
            view.setTag(holder);
        }else {
            holder = (ReleaseTicketHolder) view.getTag();
        }
        String name = mData.get(position).getTicketName();
        String price = mData.get(position).getTicketPrice() + "";
        holder.releaseTicketName.setText(name);
        holder.releaseTicketPrice.setText(price);

        holder.releaseTicketDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deleteTicketClickListener != null) {
                    deleteTicketClickListener.OnDeleteClick(position);
                }
            }
        });
        return view;
    }

    class ReleaseTicketHolder {
        TextView releaseTicketName;
        TextView releaseTicketPrice;
        ImageView releaseTicketDelete;
    }

    public void setOnDeleteTicketClickListener(OnDeleteTicketClickListener listener) {
        this.deleteTicketClickListener = listener;
    }

    public interface OnDeleteTicketClickListener {
        void OnDeleteClick(int position);
    }

}
