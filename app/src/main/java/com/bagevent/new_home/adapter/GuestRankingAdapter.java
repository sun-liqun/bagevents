package com.bagevent.new_home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagevent.R;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/21
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class GuestRankingAdapter extends RecyclerView.Adapter<GuestRankingAdapter.GuestRankHolder> {
    private Context context;

    public GuestRankingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GuestRankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuestRankHolder(LayoutInflater.from(context).inflate(R.layout.item_guest_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(GuestRankHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }


    class GuestRankHolder extends RecyclerView.ViewHolder {

        public GuestRankHolder(View itemView) {
            super(itemView);
        }
    }
}
