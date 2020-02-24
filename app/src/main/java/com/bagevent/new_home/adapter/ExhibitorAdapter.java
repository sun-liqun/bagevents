package com.bagevent.new_home.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.EventList;
import com.bagevent.new_home.data.ExhibitionListData;
import com.bagevent.new_home.new_activity.ExhibitionActivity;
import com.bagevent.new_home.new_activity.NewExhibitionActivity;
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
 * time 2019/02/25
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorAdapter extends BaseQuickAdapter<ExhibitionListData.ExhibitionList, BaseViewHolder> {
    public ExhibitorAdapter(@Nullable ArrayList<ExhibitionListData.ExhibitionList> data) {
        super(R.layout.new_event_pandect_type1, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ExhibitionListData.ExhibitionList item) {
        final ExhibitionListData.Event event = item.getEvent();
                helper.setText(R.id.tv_home_event_name, event.getEventName()
                        .replaceAll("&#40;", "(")
                        .replaceAll("&#41;", ")")
                        .replaceAll("&gt;", ">")
                        .replaceAll("&lt;", "<")
                        .replaceAll("&amp;","&")
                        .replaceAll("&quot;","\"")
                        .replaceAll("&apos;","'")
                        .replaceAll("&nbsp;"," ")
                )
                        .setText(R.id.tv_home_event_address, event.getAddress())
                        .setText(R.id.tv_join_favorite, R.string.attention_degree1)
                        .setText(R.id.tv_home_have_join, item.getExExhibitorInfo().getFavouriteNum() + "")
                        .setText(R.id.tv_income_rank, R.string.rank1)
                        .setText(R.id.tv_home_event_income, item.getRank() + "")
                        .setText(R.id.tv_sort, R.string.exhibition)
                        .setBackgroundRes(R.id.tv_sort, R.drawable.bg_exhibiton_blue);
                helper.setGone(R.id.ll_home_audit, false);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(helper.itemView.getContext(), ExhibitionActivity.class);
                        intent.putExtra("eventId", event.getEventId());
                        intent.putExtra("exhibitorId", item.getExExhibitorInfo().getExhibitorId());
                        intent.putExtra("rank",item.getRank());
                        helper.itemView.getContext().startActivity(intent);
        //                intent.putExtra("eventName",event.getEventName());
        ////              intent.putExtra("attendeeCount",event.getAttendeeCount());
        ////              intent.putExtra("boothNo",item.getExExhibitorInfo().getBoothNo());
        ////              intent.putExtra("boothHall",item.getExExhibitorInfo().getBoothHall());
        ////              intent.putExtra("viewCount",item.getExExhibitorInfo().getViewCount());
        //                intent.putExtra("favouriteNum",item.getExExhibitorInfo().getFavouriteNum());
        //                intent.putExtra("logo",item.getExExhibitorInfo().getExExhibitorInfoDTO().getLogo());
        //                intent.putExtra("company",item.getExExhibitorInfo().getExExhibitorInfoDTO().getCompany());
                    }
                });
    }

}
