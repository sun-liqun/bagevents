package com.bagevent.new_home.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.data.TagsData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

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
public class TagsAdapter extends BaseQuickAdapter<TagsData.TagDataList,BaseViewHolder> {

    private ArrayList<String> pos;
    public TagsAdapter( @Nullable List<TagsData.TagDataList> data,ArrayList<String> pos) {
        super(R.layout.item_filter_tag, data);
        this.pos=pos;
    }


    @Override
    protected void convert(BaseViewHolder helper, TagsData.TagDataList item) {
        TextView tvTag=helper.getView(R.id.tv_tag);
        helper.setText(R.id.tv_tag,item.getName());

        if (pos.contains(String.valueOf(helper.getLayoutPosition()))) {
            helper.setBackgroundRes(R.id.fl_filter,R.drawable.dynamic_filter_select);
            helper.setTextColor(R.id.tv_tag,Color.parseColor("#FFFF9C42"));
//            Drawable drawable = helper.itemView.getResources().getDrawable(R.drawable.icon_gou);
            Drawable drawable = ContextCompat.getDrawable(helper.itemView.getContext(),R.drawable.icon_gou);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvTag.setCompoundDrawables( null, null,drawable, null);
        } else {
            helper.setBackgroundRes(R.id.fl_filter,R.drawable.dynamic_filter_unselect);
            helper.setTextColor(R.id.tv_tag,Color.parseColor("#FF898989"));
            tvTag.setCompoundDrawables(null, null, null, null);
        }
    }
}
