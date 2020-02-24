package com.bagevent.activity_manager.manager_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.SerializableMap;
import com.bagevent.db.EventTicket;
import com.bagevent.util.Airth;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zwj on 2016/7/4.
 */
public class AddPeopleAdapter extends BaseAdapter {

    private List<EventTicket> mTicket;

    private LayoutInflater mInflater;
    private Context mContext;

    private SerializableMap serMap = new SerializableMap();
    private Map<String, String> map = new HashMap<String, String>();
    private AddNumListener addNumlistener;
    private ReduceLisetner reduceLisetner;

    private int count = 0;//用户选择添加的人员数
    private int ticketIdNum = -1;//一共选择的门票种类
    private double ticketPrice = 0.00;//所选的门票总价

    public AddPeopleAdapter(List<EventTicket> mTicket, Context mContext) {
        this.mTicket = mTicket;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mTicket.size();
    }

    @Override
    public Object getItem(int position) {
        return mTicket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AddPeopleViewHolder holder = null;
        DecimalFormat format = new DecimalFormat( "0.00 ");

        if (convertView == null) {
            holder = new AddPeopleViewHolder();
            convertView = mInflater.inflate(R.layout.activity_add_people_item, parent,false);
            holder.tv_add_ticket_name = (TextView) convertView.findViewById(R.id.tv_add_ticket_name);
            holder.tv_add_ticketnum = (TextView) convertView.findViewById(R.id.tv_add_ticketnum);
            holder.tv_ticket_price = (TextView) convertView.findViewById(R.id.tv_ticket_type);
            holder.tv_a_money = (TextView) convertView.findViewById(R.id.tv_a_money);
            holder.tv_residue = (TextView) convertView.findViewById(R.id.tv_residue);
            holder.rl_add = (AutoRelativeLayout) convertView.findViewById(R.id.rl_add);
            holder.ll_reduce = (AutoLinearLayout) convertView.findViewById(R.id.ll_reduce);
            holder.ll_plus = (AutoLinearLayout) convertView.findViewById(R.id.ll_plus);
            holder.tv_ticketnum = (TextView) convertView.findViewById(R.id.tv_ticketnum);
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            convertView.setTag(holder);
            AutoUtils.auto(convertView);

        } else {
            holder = (AddPeopleViewHolder) convertView.getTag();
        }

        holder.tv_add_ticket_name.setText(mTicket.get(position).ticketNames);
      //  String sCount = count + "";
      //  holder.tv_add_ticketnum.setText(sCount);
        //根据是否有剩余门票决定显示控件
        if ((mTicket.get(position).limitCounts == 0)) {
            holder.tv_residue.setText("0");
        } else {
            String select = map.get(mTicket.get(position).ticketIds +"");
            if(!TextUtils.isEmpty(select)) {
                String ticketNum = (mTicket.get(position).limitCounts - Integer.parseInt(select)) + "";
                holder.tv_ticketnum.setVisibility(View.VISIBLE);
                holder.tv_residue.setText(ticketNum);
                holder.tv_add_ticketnum.setText(select);
            }else {
                String ticketNum = mTicket.get(position).limitCounts + "";
                holder.tv_ticketnum.setVisibility(View.VISIBLE);
                holder.tv_residue.setText(ticketNum);
                holder.tv_add_ticketnum.setText("0");
            }

        }
        //根据门票价格显示控件
        if (mTicket.get(position).ticketPrices == 0) {
            holder.tv_a_money.setVisibility(View.GONE);
            holder.tv_ticket_price.setText(R.string.free);
        } else {
            String price = format.format(mTicket.get(position).ticketPrices) + "";
            holder.tv_a_money.setVisibility(View.VISIBLE);
            holder.tv_ticket_price.setText(price);
        }

        final AddPeopleViewHolder finalHolder = holder;
        holder.ll_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTicket.get(position).limitCounts != 0) {
                    int nums = 0;
                    if (Integer.parseInt(finalHolder.tv_add_ticketnum.getText().toString()) < mTicket.get(position).limitCounts) {
                        if (mTicket.get(position).ticketPrices != 0) {
                            ticketPrice = Airth.add(ticketPrice, Airth.floatToDouble(mTicket.get(position).ticketPrices));
                        }
                        count = count + 1;
                        nums = Integer.parseInt(finalHolder.tv_add_ticketnum.getText().toString()) + 1;
                        setDataToMap(position, nums);
                        int c = Integer.parseInt(finalHolder.tv_residue.getText().toString());
                        String ticketNum = (c - 1) + "";
                        finalHolder.tv_residue.setText(ticketNum);
                    } else if (Integer.parseInt(finalHolder.tv_add_ticketnum.getText().toString()) >= mTicket.get(position).limitCounts) {
                        nums = mTicket.get(position).limitCounts;
                        finalHolder.tv_residue.setText("0");
                    }
                 //   finalHolder.rl_add.setBackgroundResource(R.drawable.orangeback);
             //       finalHolder.tv_add.setTextColor(ContextCompat.getColor(mContext,R.color.fe6900));
             //       finalHolder.tv_add_ticketnum.setTextColor(ContextCompat.getColor(mContext,R.color.fe6900));
                    finalHolder.tv_add_ticketnum.setText(nums + "");
                    addNumlistener.add(count, serMap, ticketPrice);
                }
            }

        });

        holder.ll_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTicket.get(position).limitCounts != 0) {
                    int nums = 0;
                    if (Integer.parseInt(finalHolder.tv_add_ticketnum.getText().toString()) != 0) {
                        if (mTicket.get(position).ticketPrices != 0) {
                            ticketPrice = Airth.sub(ticketPrice, Airth.floatToDouble(mTicket.get(position).ticketPrices));
                        }
                        count = count - 1;
                        nums = Integer.parseInt(finalHolder.tv_add_ticketnum.getText().toString()) - 1;
                        setDataToMap(position, nums);
                        int c = Integer.parseInt(finalHolder.tv_residue.getText().toString());
                        String ticketNum = (c + 1) + "";
                        finalHolder.tv_residue.setText(ticketNum);
                    }
                    if (nums == 0) {
                     //   finalHolder.rl_add.setBackgroundResource(R.drawable.buttonback);
                        map.remove(mTicket.get(position).ticketIds + "");
                        finalHolder.tv_residue.setText(mTicket.get(position).limitCounts + "");
                     //   finalHolder.tv_add_ticketnum.setTextColor(ContextCompat.getColor(mContext,R.color.tv_grey));
                 //       finalHolder.tv_add.setTextColor(ContextCompat.getColor(mContext,R.color.tv_grey));
                    }
                    finalHolder.tv_add_ticketnum.setText(nums + "");
                    reduceLisetner.reduce(count, serMap, ticketPrice);
                }
            }
        });

        return convertView;
    }

    private void setDataToMap(int position, int nums) {
        map.put(mTicket.get(position).ticketIds + "", nums + "");
        serMap.setMap(map);
    }

    class AddPeopleViewHolder {
        TextView tv_add_ticket_name;
        TextView tv_a_money;//人民币符号
        TextView tv_ticket_price;
        TextView tv_ticketnum;//余票
        TextView tv_add_ticketnum;
        AutoLinearLayout ll_reduce;
        AutoLinearLayout ll_plus;
        TextView tv_add;
        TextView tv_residue;//余票的数量

        AutoRelativeLayout rl_add;//当门票数量增加的时候更改background

    }

    //增加人员
    public interface AddNumListener {
        void add(int count, SerializableMap map, double price);
    }

    public void setAddNumListener(AddNumListener listener) {
        this.addNumlistener = listener;
    }

    //减少人员
    public interface ReduceLisetner {
        void reduce(int count, SerializableMap map, double price);
    }

    public void setReduceLisetner(ReduceLisetner listener) {
        this.reduceLisetner = listener;
    }

    /**
     * 如果添加参会人员成功，则将选择的添加参会人员数量置为0
     */

    public void setNumsToZero() {
        count = 0;
        notifyDataSetChanged();
    }


}
