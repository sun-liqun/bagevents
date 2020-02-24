package com.bagevent.new_home.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/22.
 */
public class ReleaseEventFormAdapter extends BaseAdapter {

    private ArrayList<FormType.RespObjectBean> mAllForm;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnDeleteFormListener listener;
    private OnRequiredListener requiredListener;
    public ReleaseEventFormAdapter(ArrayList<FormType.RespObjectBean> mAllForm, Context mContext) {
        this.mAllForm = mAllForm;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mAllForm == null ? 0 : mAllForm.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllForm == null ? null : mAllForm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        FormViewHolder holder = null;
        if(view == null) {
            holder = new FormViewHolder();
            view = mInflater.inflate(R.layout.release_event_form_item,null);
            holder.iv_formDelete = (ImageView)view.findViewById(R.id.iv_form_delete);
            holder.iv_formRequired = (ImageView)view.findViewById(R.id.iv_form_required);
            holder.tv_formName = (TextView)view.findViewById(R.id.tv_form_name);
            view.setTag(holder);
        }else {
            holder = (FormViewHolder)view.getTag();
        }
        FormType.RespObjectBean temp = mAllForm.get(position);
        holder.tv_formName.setText(temp.getShowName());
        if(temp.getFieldName().equals("username") || temp.getFieldName().equals("email_address")) {
            holder.iv_formRequired.setVisibility(View.GONE);
            holder.iv_formDelete.setVisibility(View.GONE);
        }else if(temp.getFieldName().equals("mobile_phone")) {
            holder.iv_formDelete.setVisibility(View.GONE);
            holder.iv_formRequired.setVisibility(View.VISIBLE);
        }else {
            holder.iv_formDelete.setVisibility(View.VISIBLE);
            holder.iv_formRequired.setVisibility(View.VISIBLE);

        }
        if(holder.iv_formRequired.getVisibility() == View.VISIBLE && temp.getRequired() == 1) {
            Glide.with(mContext).load(R.drawable.isrequired).into(holder.iv_formRequired);
        }else {
            Glide.with(mContext).load(R.drawable.notrequired).into(holder.iv_formRequired);
        }

        holder.iv_formRequired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requiredListener.isRequired(position);
            }
        });

        holder.iv_formDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteForm(position);
            }
        });
        return view;
    }

    class FormViewHolder {
        TextView tv_formName;
        ImageView iv_formRequired;
        ImageView iv_formDelete;
    }

    public void setOnRequiredListener(OnRequiredListener listener) {
        this.requiredListener = listener;
    }

    public interface OnRequiredListener {
        void isRequired(int position);
    }

    public void setOnDeleteFormListener(OnDeleteFormListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteFormListener {
        void onDeleteForm(int position);
    }
}
