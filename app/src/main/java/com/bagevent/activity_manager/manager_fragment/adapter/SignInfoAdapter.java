package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.new_home.adapter.SelectBindTagsAdapter;
import com.bagevent.new_home.adapter.SelectedTagsAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
 * time 2019/03/18
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class SignInfoAdapter extends  RecyclerView.Adapter<SignInfoAdapter.SignInfoViewHolder> {

    private ArrayList<String> pos;
    private ArrayList<String> filedName;

    private SignInfoAdapter.OnItemClickListener listener;
    public void setListener(SignInfoAdapter.OnItemClickListener listener){
        this.listener=listener;
    }
    public SignInfoAdapter(ArrayList<String> filedName, ArrayList<String> pos){
        this.filedName=filedName;
        this.pos=pos;
    }

    @NonNull
    @Override
    public SignInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tags_of_select, parent, false);
        return new SignInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SignInfoViewHolder holder, final int position) {
        holder.tvTagName.setText(filedName.get(position));
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
        return filedName.size();
    }

//    private ArrayList<String> fieldName;
//    private ArrayList<String> pos;
//    public SignInfoAdapter( @Nullable List<FormType.RespObjectBean> data,ArrayList<String> pos) {
//        super(R.layout.item_tags_of_select, data);
//        this.pos=pos;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, FormType.RespObjectBean item) {
//
//        helper.setText(R.id.tv_tag,item.getShowName());
//        if (pos.contains(String.valueOf(helper.getLayoutPosition()))) {
//            helper.setImageResource(R.id.iv_check,R.drawable.checked);
//        } else {
//            helper.setImageResource(R.id.iv_check,R.drawable.unchecked);
//        }
//    }

    public class SignInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTagName;
        @BindView(R.id.iv_check)
        ImageView ivCheck;
        public SignInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface  OnItemClickListener{
        void  onClick(View view,int position);
    }
}
