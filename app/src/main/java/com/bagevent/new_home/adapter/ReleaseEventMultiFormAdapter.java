package com.bagevent.new_home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.bagevent.R;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/23.
 */
public class ReleaseEventMultiFormAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private Context mContext;
    private LayoutInflater mInflater;

    public ReleaseEventMultiFormAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.data = data;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data == null ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MultiFormViewholder holder = null;
        if(view == null) {
            holder = new MultiFormViewholder();
            view = mInflater.inflate(R.layout.release_event_multichoice_item,null);
            holder.et_multi_form_name = (EditText)view.findViewById(R.id.et_form_child_name);
            holder.iv_multi_form_name = (ImageView)view.findViewById(R.id.iv_clear_form_child_name);
            view.setTag(holder);
        }else {
            holder = (MultiFormViewholder)view.getTag();
        }
        holder.et_multi_form_name.setHint(data.get(i));
        return view;
    }

    class MultiFormViewholder {
        EditText et_multi_form_name;
        ImageView iv_multi_form_name;
    }
}
