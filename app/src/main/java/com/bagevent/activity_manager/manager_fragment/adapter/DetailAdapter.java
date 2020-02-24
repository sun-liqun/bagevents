package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zwj on 2016/6/27.
 */
public class DetailAdapter extends BaseQuickAdapter<FormType.RespObjectBean, BaseViewHolder> {

    private JSONObject attendeeJsonObj;
    private List<FormType.RespObjectBean> form;

    public DetailAdapter(@Nullable List<FormType.RespObjectBean> form, String attendeeMap) {
        super(R.layout.attend_people_datail_type2, form);
        this.form = form;
        try {
            this.attendeeJsonObj = new JSONObject(attendeeMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, FormType.RespObjectBean item) {

        String text = "";
        try {
            text = attendeeJsonObj.getString(item.getFormFieldId() + "");
        } catch (JSONException e) {
            text = "";
            e.printStackTrace();
        }

        helper.setText(R.id.tv_form_key, item.getShowName())
                .setTag(R.id.tv_form_key, helper.getAdapterPosition());


        if (!TextUtils.isEmpty(text)) {
            if(item.getFieldTypeName().equals("attendee_avatar")) {
                helper.setGone(R.id.iv_form_avatar,true)
                        .setGone(R.id.tv_form_value,false);
                if(text.contains("wx.")) {
                    Glide.with(mContext).load("http:" + text).into((ImageView)helper.getView(R.id.iv_form_avatar));
                }else {
                    Glide.with(mContext).load(Constants.imgsURL + text).into((ImageView)helper.getView(R.id.iv_form_avatar));
                }
            }else if(item.getFieldTypeName().equals("sex")){
                if(text.equals("0")) {
                    helper.setGone(R.id.iv_form_avatar,false)
                            .setGone(R.id.tv_form_value,true);
                    helper.setText(R.id.tv_form_value, "")
                            .setTag(R.id.tv_form_value, helper.getAdapterPosition());
                }else {
//                    if(text.equals("女")){
                        helper.setGone(R.id.iv_form_avatar,false)
                                .setGone(R.id.tv_form_value,true);
                        helper.setText(R.id.tv_form_value, text)
                                .setTag(R.id.tv_form_value, helper.getAdapterPosition());
//                    }else {
//                        helper.setGone(R.id.iv_form_avatar,false)
//                                .setGone(R.id.tv_form_value,true);
//                        helper.setText(R.id.tv_form_value, "男")
//                                .setTag(R.id.tv_form_value, helper.getAdapterPosition());
//                    }

                }
            }else {
                helper.setGone(R.id.iv_form_avatar,false)
                        .setGone(R.id.tv_form_value,true);
                helper.setText(R.id.tv_form_value, text)
                        .setTag(R.id.tv_form_value, helper.getAdapterPosition());
            }

        } else {
            helper.setGone(R.id.iv_form_avatar,false);
            helper.setText(R.id.tv_form_value, "");
            helper.setTag(R.id.tv_form_value, helper.getAdapterPosition());
        }
    }

    public void setAttendeeMap(String attendeeMap) {
        try {
            this.attendeeJsonObj = new JSONObject(attendeeMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
