package com.bagevent.new_home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
 * time 2019/03/05
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class SelectedTagsAdapter extends RecyclerView.Adapter<SelectedTagsAdapter.SelectedTagsHolder> {

    Context context;
    private List<String> tagsName;
    private OnItemClickListener listener;
    public void setListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public SelectedTagsAdapter(List<String> tagsName,Context context){
        this.tagsName=tagsName;
        this.context=context;
    }
    @NonNull
    @Override
    public SelectedTagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_tags, parent, false);
        return new SelectedTagsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedTagsHolder holder, final int position) {
        holder.tvTagName.setText(tagsName.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (listener!=null){
                   listener.onClick(holder.itemView,position);
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return tagsName.size();
    }
    public interface  OnItemClickListener{
        void  onClick(View view,int position);
    }

    public class SelectedTagsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag_name)
        public TextView tvTagName;
        public SelectedTagsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
