package com.bagevent.new_home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/08
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class SelectBindTagsAdapter extends RecyclerView.Adapter<SelectBindTagsAdapter.SelectedBindTagsHolder> {

    private ArrayList<String> pos;
    private ArrayList<String> tagsName;
    private SelectBindTagsAdapter.OnItemClickListener listener;
    public void setListener(SelectBindTagsAdapter.OnItemClickListener listener){
        this.listener=listener;
    }
    public SelectBindTagsAdapter(ArrayList<String> tagsName, ArrayList<String> pos){
        this.tagsName=tagsName;
        this.pos=pos;
    }
    @NonNull
    @Override
    public SelectedBindTagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tags_of_select, parent, false);
        return new SelectedBindTagsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedBindTagsHolder holder, final int position) {
        holder.tvTagName.setText(tagsName.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(holder.itemView,position);
                }
            }
        });
        if (pos.contains(String.valueOf(holder.getLayoutPosition()))) {
            holder.ivCheck.setImageResource(R.drawable.checked);
        } else {
            holder.ivCheck.setImageResource(R.drawable.unchecked);
        }


    }

    @Override
    public int getItemCount() {
        return tagsName.size();
    }

    public class SelectedBindTagsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTagName;
        @BindView(R.id.iv_check)
        ImageView ivCheck;
        public SelectedBindTagsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface  OnItemClickListener{
        void  onClick(View view,int position);
    }
}
