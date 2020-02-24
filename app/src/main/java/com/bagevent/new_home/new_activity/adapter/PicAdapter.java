package com.bagevent.new_home.new_activity.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {
    Context context;
    private List<String> pathList;
    public PicAdapter(List<String> pathList,Context context){
        this.pathList=pathList;
        this.context=context;
    }
    @NonNull
    @Override
    public PicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pic, viewGroup, false);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicViewHolder picViewHolder, final int i) {
        String img =pathList.get(i);
        RequestOptions options=new RequestOptions()
                .error(R.mipmap.icon);
        Glide.with(context).load(img).apply(options).into(picViewHolder.iv);
        picViewHolder.iv_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           pathList.remove(i);
           notifyDataSetChanged();

           EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_PIC,pathList.size()));
           }
       });
    }


    @Override
    public int getItemCount() {
        return pathList.size();
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        public ImageView iv;
        @BindView(R.id.iv_cancel)
        public ImageView iv_cancel;
        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
