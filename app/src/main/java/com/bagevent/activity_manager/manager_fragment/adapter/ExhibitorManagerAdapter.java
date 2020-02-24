package com.bagevent.activity_manager.manager_fragment.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
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
 * time 2019/03/11
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorManagerAdapter extends BaseQuickAdapter<ExhibitorData.ExhibitorList,BaseViewHolder> {
    public ExhibitorManagerAdapter(@Nullable List<ExhibitorData.ExhibitorList> data) {
        super(R.layout.item_exhibitor_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitorData.ExhibitorList item) {

        String levels="";
        if (item.getAudit()==0){
            ArrayList<ExhibitorData.ExhibitorSponsorLevels> exhibitorSponsorLevels = item.getExhibitorSponsorLevels();
            int levelsCount=exhibitorSponsorLevels.size();
            if (levelsCount==1){
                helper.setText(R.id.tv_boothInfo,R.string.application_level+item.getExhibitorSponsorLevels().get(0).getDescription());
            }else if (levelsCount>1){
                for (int i = 0; i < levelsCount ; i++) {
                    String levelsText=exhibitorSponsorLevels.get(i).getDescription();
                    levels=levels+levelsText;
                    if (i!=levelsCount-1){
                        levels+=",";
                    }
                }
                helper.setText(R.id.tv_boothInfo,R.string.application_level+levels);
            }else {
                helper.setGone(R.id.tv_boothInfo,false);
            }
            helper.setGone(R.id.tv_status,false);
        }else {
            helper.setGone(R.id.tv_status,true);
            helper.setText(R.id.tv_boothInfo,item.getBooth_hall()+" "+item.getBooth_no());
        }
        switch (item.getCondition()){
            case 0:
                helper.setText(R.id.tv_status,R.string.no_status);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FF898989"));
                break;
            case 1:
                helper.setText(R.id.tv_status,R.string.in_contract);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FF4AB800"));
                break;
            case 2:
                helper.setText(R.id.tv_status,R.string.fees_to_be_paid);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FFFF9000"));
                break;
            case 3:
                helper.setText(R.id.tv_status,R.string.expenses_paid);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FF2CA9E9"));
                break;
            case 4:
                helper.setText(R.id.tv_status,R.string.confirmation_of_participation);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FF4AB800"));
                break;
            case 5:
                helper.setText(R.id.tv_status,R.string.confirmation_not_to_participation);
                helper.setTextColor(R.id.tv_status,Color.parseColor("#FFFF9000"));
                break;
        }
        helper.setText(R.id.tv_name,item.getCompany());

    }
}
