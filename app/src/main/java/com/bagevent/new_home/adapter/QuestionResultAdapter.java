package com.bagevent.new_home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
public class QuestionResultAdapter extends RecyclerView.Adapter<QuestionResultAdapter.QuestionResultHolder> {


    @Override
    public QuestionResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionResultHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_result, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionResultHolder holder, int position) {

        int mode = position % 3;
        int imageId = R.drawable.collection;
        if (mode == 0) {
            imageId = R.drawable.collection;
        } else if (mode == 1) {
            imageId = R.drawable.not_publish;
        } else {
            imageId = R.drawable.over;
        }

        holder.iv_status.setImageResource(imageId);

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class QuestionResultHolder extends RecyclerView.ViewHolder {
        ImageView iv_status;

        public QuestionResultHolder(View itemView) {
            super(itemView);
            iv_status = itemView.findViewById(R.id.iv_status);
        }
    }
}
