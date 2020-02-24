package com.bagevent.new_home.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.data.AttendeeInfo;
import com.bagevent.new_home.data.AttendeeInfoEntity;
import com.bagevent.new_home.data.CommentEntity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.guanaj.easyswipemenulibrary.State;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
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
public class AttendeeDetailAdapter extends BaseMultiItemQuickAdapter<AttendeeInfoEntity,BaseViewHolder> {


    private OnItemRemoveClickListener listener;
    public AttendeeDetailAdapter(List<AttendeeInfoEntity> data) {
        super(data);
        addItemType(AttendeeInfoEntity.TYPE_TAG, R.layout.item_new_tag);
        addItemType(AttendeeInfoEntity.TYPE_LINE, R.layout.item_line);
        addItemType(AttendeeInfoEntity.TYPE_NAME, R.layout.item_name);
        addItemType(AttendeeInfoEntity.TYPE_INFO, R.layout.attend_people_datail_type2);
        addItemType(AttendeeInfoEntity.TYPE_NO_TAG, R.layout.item_text_no_menu);

    }

    @Override
    protected void convert(final BaseViewHolder helper, AttendeeInfoEntity item) {
//        Test.AttendeeMap attendeeMap=item.getAttendeeMap();
        switch (item.getItemType()) {
            case AttendeeInfoEntity.TYPE_NAME:
                helper.setText(R.id.tv_name, item.getName());
                helper.setBackgroundRes(R.id.ll_name,R.drawable.dynamic_filter_unselect);
                helper.setTextColor(R.id.tv_name,Color.parseColor("#FF898989"));
                break;
            case AttendeeInfoEntity.TYPE_TAG:
                setItemTag(helper, item);
                helper.getView(R.id.rl_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemRemoveClick(helper.getAdapterPosition());
                        ((SwipeMenuLayout)helper.itemView).quickClose();
                    }
                });
                break;
            case AttendeeInfoEntity.TYPE_INFO:
                setItemInfo(helper, item);
                break;
            case AttendeeInfoEntity.TYPE_NO_TAG:
                helper.setText(R.id.tv_tag, item.getName());
                break;
        }

    }

    public void setItemRemoveListener(OnItemRemoveClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemRemoveClickListener {
        void onItemRemoveClick(int position);
    }

    private void setItemTag(final BaseViewHolder helper, AttendeeInfoEntity item) {
        if (item.getTagList()!=null){
            AttendeeInfo.TagList tagList=item.getTagList();
            String name = tagList.getName();
            helper.setText(R.id.tv_tag,name);
        }

    }

    private void setItemInfo(BaseViewHolder helper, AttendeeInfoEntity item) {

        AttendeeInfo.FromFieldList fromFieldList=item.getFromFieldList();
        helper.setText(R.id.tv_form_key,fromFieldList.getShowName());
        helper.setTextColor(R.id.tv_form_key,Color.parseColor("#FF898989"));
        helper.setText(R.id.tv_form_value,item.getValue());

    }



}
