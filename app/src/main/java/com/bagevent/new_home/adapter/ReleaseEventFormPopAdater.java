package com.bagevent.new_home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.GetAllFormData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/22.
 */
public class ReleaseEventFormPopAdater extends BaseAdapter {
    private ArrayList<GetAllFormData.RespObjectBean> mCustomForm;
    private Context mContext;
    private LayoutInflater mInflater;

    public ReleaseEventFormPopAdater(ArrayList<GetAllFormData.RespObjectBean> mCustomForm,Context mContext) {
        this.mCustomForm = mCustomForm;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mCustomForm == null ? 0 : mCustomForm.size();
    }

    @Override
    public Object getItem(int i) {
        return mCustomForm == null ? null : mCustomForm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PopFormViewholder holder = null;
        if(view == null) {
            holder = new PopFormViewholder();
            view = mInflater.inflate(R.layout.release_event_pop_item,null);
            holder.iv_form_logo = (ImageView)view.findViewById(R.id.iv_custom_form_logo);
            holder.tv_form_name = (TextView)view.findViewById(R.id.tv_custom_form_name);
            view.setTag(holder);
        }else {
            holder = (PopFormViewholder)view.getTag();
        }

        Glide.with(mContext).load(Constants.imgsURL + mCustomForm.get(i).getFieldIcon()).into(holder.iv_form_logo);
        holder.tv_form_name.setText(mCustomForm.get(i).getShowName());
        return view;
    }

    class PopFormViewholder {
        ImageView iv_form_logo;
        TextView tv_form_name;
    }
}
