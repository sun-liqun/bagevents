package com.bagevent.activity_manager.manager_fragment.adapter;

import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorDetail;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorEntity;
import com.bagevent.new_home.data.AttendeeInfoEntity;
import com.bagevent.new_home.new_activity.AttendeeDetailActivity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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
 * time 2019/03/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorDetialAdapter extends BaseMultiItemQuickAdapter<ExhibitorEntity,BaseViewHolder> {


    public ExhibitorDetialAdapter(List<ExhibitorEntity> data) {
        super(data);
        addItemType(ExhibitorEntity.TYPE_HEADER,R.layout.item_exhibitor_hearder);
        addItemType(ExhibitorEntity.TYPE_BOOTH,R.layout.item_exhibitor_booth);
        addItemType(ExhibitorEntity.TYPE_ITEM,R.layout.item_exhibitor_pay);
        addItemType(ExhibitorEntity.TYPE_NAME,R.layout.item_name);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitorEntity item) {

        ExhibitorDetail.ExExhibitorInfo exExhibitorInfo = item.getExExhibitorInfo();

        ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO =
                item.getExExhibitorInfoDTO();

        ExhibitorDetail.ExExhibitorReceiptOrgs exExhibitorReceiptOrgs = item.getExhibitorReceiptOrgs();

        switch (item.getItemType()){
        case ExhibitorEntity.TYPE_HEADER:
                helper.setText(R.id.tv_company,exExhibitorInfoDTO.getCompany());
                helper.setText(R.id.tv_view_count,Html.fromHtml(String.format("<font color='#010101'>看过&nbsp;</font><font color='#FF9000'>%s</font>",exExhibitorInfo.getViewCount())));
                helper.setText(R.id.tv_favourite_num,Html.fromHtml(String.format("<font color='#010101'>关注&nbsp;</font><font color='#FF9000'>%s</font>",exExhibitorInfo.getFavouriteNum())));
                helper.setText(R.id.tv_rank,Html.fromHtml(String.format("<font color='#010101'>排名&nbsp;</font><font color='#FF9000'>%s</font>",item.getExhibitorData().getRank())));
                if (exExhibitorInfo.getAudit()==1){
                    helper.setText(R.id.iv_audit,R.string.audit_tongguo);
                }else if (exExhibitorInfo.getAudit()==0){
                    helper.setGone(R.id.iv_audit,false);
                }else if (exExhibitorInfo.getAudit()==2){
                    helper.setText(R.id.iv_audit,R.string.audit_refuse1);
                }
                helper.setText(R.id.tv_name,item.getExhibitorData().getContact());
                helper.setText(R.id.tv_email,exExhibitorInfoDTO.getEmail());
                helper.setText(R.id.tv_phone,exExhibitorInfoDTO.getPhone());
                helper.setText(R.id.tv_remark,exExhibitorInfo.getRemark());
                break;
            case ExhibitorEntity.TYPE_BOOTH:
                if (TextUtils.isEmpty(exExhibitorInfo.getBoothHall()) ||TextUtils.isEmpty(exExhibitorInfo.getBoothNo())){
                    helper.setText(R.id.tv_booth,R.string.no_booth);
                }else {
                    helper.setText(R.id.tv_booth,exExhibitorInfo.getBoothHall()+" "+exExhibitorInfo.getBoothNo());
                }
                break;
            case ExhibitorEntity.TYPE_ITEM:
                helper.setText(R.id.tv_pay_money,mContext.getResources().getString(R.string.tv_money)+exExhibitorReceiptOrgs.getAmount());
                helper.setText(R.id.tv_stop_time,mContext.getResources().getString(R.string.deadline_for_payment)+exExhibitorReceiptOrgs.getReceiptTime());
                if (exExhibitorReceiptOrgs.getConfirm()==0){
                    helper.setGone(R.id.iv_pay_status,true);
                    helper.setGone(R.id.tv_already_pay,false);
                    helper.setImageResource(R.id.iv_pay_status,R.drawable.no_pay);
                }else if (exExhibitorReceiptOrgs.getConfirm()==1){
                    helper.setGone(R.id.tv_already_pay,true);
                    helper.setGone(R.id.iv_pay_status,false);
                }
                break;
            case ExhibitorEntity.TYPE_NAME:
                helper.setGone(R.id.view,false);
                helper.setText(R.id.tv_name, item.getName());
                helper.setBackgroundRes(R.id.ll_name,R.drawable.dynamic_filter_unselect);
                helper.setTextColor(R.id.tv_name,Color.parseColor("#FF898989"));
                break;
        }
    }
}
