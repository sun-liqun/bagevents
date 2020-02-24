package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.adapter.SelectBindTagsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/18
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class BarCodeShowInfoAdapter extends RecyclerView.Adapter<BarCodeShowInfoAdapter.ShowInfoHolder> {

    private ArrayList<String> info;
    private ArrayList<String> value;

    public BarCodeShowInfoAdapter(ArrayList<String> info,ArrayList<String> value){
        this.info=info;
        this.value=value;
    }
    @NonNull
    @Override
    public ShowInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show_info, parent, false);
        return new ShowInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowInfoHolder holder, int position) {
        if (info.size()>0&&value.size()>0){
            holder.tvInfo.setText(info.get(position)+":"+value.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class ShowInfoHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_info)
        TextView tvInfo;
        public ShowInfoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
