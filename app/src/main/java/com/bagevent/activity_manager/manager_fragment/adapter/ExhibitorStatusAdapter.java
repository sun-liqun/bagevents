package com.bagevent.activity_manager.manager_fragment.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.google.android.flexbox.FlexboxLayout;

import java.lang.reflect.Array;
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
 * time 2019/03/11
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorStatusAdapter extends RecyclerView.Adapter<ExhibitorStatusAdapter.StatusHolder> {
    private ArrayList<String> status;
    private ArrayList<String> pos;
    private OnRecyclerViewItemClickListener listener;

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public ExhibitorStatusAdapter(ArrayList<String> status, ArrayList<String> pos){
        this.status=status;
        this.pos=pos;
    }
    @NonNull
    @Override
    public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filter_tag, parent, false);
        return new StatusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusHolder holder, final int position) {
        holder.tvTag.setText(status.get(position));
        holder.fl.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        holder.tvTag.setTextColor(Color.parseColor("#FF898989"));
        holder.tvTag.setCompoundDrawables(null,null,null,null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(holder.itemView,position);
                    if (pos.contains(String.valueOf(holder.getLayoutPosition()))) {
                        holder.fl.setBackgroundResource(R.drawable.dynamic_filter_select);
                        holder.tvTag.setTextColor(Color.parseColor("#FFFF9C42"));
                        Drawable drawable = holder.itemView.getResources().getDrawable(R.drawable.icon_gou);
                        drawable.setBounds(0, 0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        holder.tvTag.setCompoundDrawables( null, null,drawable, null);
                    } else {
                        holder.fl.setBackgroundResource(R.drawable.dynamic_filter_unselect);
                        holder.tvTag.setTextColor(Color.parseColor("#FF898989"));
                        holder.tvTag.setCompoundDrawables(null,null,null,null);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    public class StatusHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag)
        public TextView tvTag;
        @BindView(R.id.fl_filter)
        public FlexboxLayout fl;
        public StatusHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnRecyclerViewItemClickListener{
        void onClick(View view,int position);
    }
}
