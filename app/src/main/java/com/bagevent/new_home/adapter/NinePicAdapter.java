package com.bagevent.new_home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bagevent.R;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.new_home.new_activity.ImageGalleryActivity;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.bagevent.util.KeyUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

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
 * time 2019/02/28
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class NinePicAdapter extends RecyclerView.Adapter<NinePicAdapter.NineViewHolder>{

    Context context;
    private List<String> pathList;

    public NinePicAdapter(List<String> pathList,Context context){
        this.pathList=pathList;
        this.context=context;
    }
    @NonNull
    @Override
    public NineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nine_pic, parent, false);
        return new NinePicAdapter.NineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NineViewHolder holder, final int position) {
        String img =pathList.get(position);
        final String[] imgs=pathList.toArray(new String[pathList.size()]);
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_failed);
//        Glide.with(context).load("https://www.bagevent.com"+img).apply(options).into(holder.iv_pic);
        Glide.with(context).load("https://img.bagevent.com"+img).apply(options).into(holder.iv_pic);
        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageGalleryActivity.class);
                intent.putExtra(KeyUtil.KEY_POSITION, position);
                intent.putExtra(KeyUtil.KEY_IMAGE_URL, imgs);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    public class NineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        public ImageView iv_pic;
        @BindView(R.id.relativeLayout)
        public RelativeLayout relativeLayout;
        public NineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
