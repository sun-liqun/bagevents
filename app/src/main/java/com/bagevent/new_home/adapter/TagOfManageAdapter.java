package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bagevent.R;
import com.bagevent.new_home.data.TagsData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/06
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class TagOfManageAdapter extends BaseQuickAdapter<TagsData.TagDataList,BaseViewHolder> {

    private OnItemRemoveClickListener listener;
    private OnItemClickListener itemClickListener;

    public TagOfManageAdapter(@Nullable List<TagsData.TagDataList> data) {
        super(R.layout.item_tag, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, TagsData.TagDataList item) {
        helper.setText(R.id.tv_tag,item.getName()+"("+item.getCount()+")");

        helper.getView(R.id.ll_tag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, helper.getAdapterPosition());
            }
        });
        helper.getView(R.id.rl_remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemRemoveClick(helper.getAdapterPosition());
                    ((SwipeMenuLayout)helper.itemView).quickClose();
                }
        });
    }

    public void setItemRemoveListener(OnItemRemoveClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemRemoveClickListener {
        void onItemRemoveClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
