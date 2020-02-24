package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bagevent.R;
import com.bagevent.new_home.data.CollectionAttendeeData;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/07
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class SelectAttendeeAdapter extends BaseQuickAdapter<CollectionAttendeeData.CollectionList,BaseViewHolder> {

    private ArrayList<String> pos;
    public SelectAttendeeAdapter( @Nullable List<CollectionAttendeeData.CollectionList> data,ArrayList<String> pos) {
        super(R.layout.item_select_attendee, data);
        this.pos=pos;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionAttendeeData.CollectionList item) {
        String tagsName="";
        String tagsData="";

        CircleImageView iv= helper.getView(R.id.iv_circle_avatar);
        ArrayList<CollectionAttendeeData.TagList> tagList=item.getTagLists();
        if (!TextUtils.isEmpty(item.getAvater())){
            helper.setVisible(R.id.iv_circle_avatar,true);
            helper.setVisible(R.id.tv_circle_avatar,false);
            Glide.with(mContext).load(item.getAvater()).into(iv);
        }else {
            helper.setVisible(R.id.iv_circle_avatar,false);
            helper.setVisible(R.id.tv_circle_avatar,true);
            helper.setText(R.id.tv_circle_avatar,item.getAttendeeName().substring(0,1));
        }

        if(tagList!=null){
            if (item.getTagLists().size()==1){
                tagsName="#"+tagList.get(0).getName()+"#";
            }else {
                for (int i = 0; i < tagList.size() ; i++) {
                    String tagName=tagList.get(i).getName();
                    tagsData=tagsData+tagName;
                    if (i!=tagList.size()-1){
                        tagsData+=",";
                    }
                }
            }
        }else {
            tagsName="";
        }

        if (tagsData.contains(",")){
            tagsName="#"+tagsData.replaceAll(",","# #")+"#";
        }

        helper.setText(R.id.tv_name,item.getAttendeeName());
        helper.setText(R.id.tv_time,item.getUpdateTime().substring(5,16));
//        helper.setText(R.id.tv_time,item.getUpdateTime());
        helper.setText(R.id.tv_tag,tagsName);


        if (pos.contains(String.valueOf(helper.getLayoutPosition()))) {
            helper.setImageResource(R.id.iv_check,R.drawable.checked);
        } else {
            helper.setImageResource(R.id.iv_check,R.drawable.unchecked);
        }
    }
}
