package com.bagevent.new_home.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.new_home.data.ExhibitorDynamicData;
import com.bagevent.new_home.new_activity.DynamicDetailActivity;
import com.bagevent.new_home.new_activity.NewExhibitionActivity;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.bagevent.util.KeyUtil;
import com.bagevent.view.HorizontalScrollImag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/02/27
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitionDynamicAdapter extends BaseQuickAdapter<ExhibitorDynamicData.DynamicList,BaseViewHolder> {

    public ExhibitionDynamicAdapter(@Nullable List<ExhibitorDynamicData.DynamicList> data) {
        super(R.layout.item_exhibition_dynamic, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ExhibitorDynamicData.DynamicList item) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.shadow_bg);
        RecyclerView rvCommentList=helper.getView(R.id.rv_commentList);
        RecyclerView rvPic=helper.getView(R.id.rv_pic);
        rvCommentList.setVisibility(View.GONE);
        rvPic.setVisibility(View.GONE);
        helper.setText(R.id.tv_company,item.getShowName());
        helper.setText(R.id.tv_time,item.getShowTime());
        helper.setText(R.id.tv_content,item.getCommentText());
        helper.setText(R.id.tv_like_count,item.getLikeNumber()+" "+mContext.getResources().getString(R.string.people_like));
        helper.setText(R.id.tv_comment_count,item.getCommentNumber()+" "+mContext.getResources().getString(R.string.strip_comment));

        ImageView iv_head = helper.getView(R.id.iv_head);
        String showAvatar=item.getShowAvatar();
        if (!TextUtils.isEmpty(showAvatar)){
            Glide.with(mContext).load(item.getShowAvatar()).apply(options).into(iv_head);
        }else {
            Glide.with(mContext).load(R.drawable.shadow_bg).into(iv_head);
        }

        if (!TextUtils.isEmpty(item.getImages())) {
            String[] urls= item.getImages().split(",");
            List<String> imgs=new ArrayList<>(Arrays.asList(urls));

            rvPic.setVisibility(View.VISIBLE);
//            if (rvPic.getAdapter()==null){
                NinePicAdapter adapter = new NinePicAdapter(imgs, helper.itemView.getContext());
////                adapter.setHasStableIds(true);
////                rvPic.setAdapter(adapter);
////                rvPic.setLayoutManager(new GridLayoutManager(
////                        helper.itemView.getContext(),3
////                ));
////                rvPic.setHasFixedSize(true);
////            }
            adapter.setHasStableIds(true);
            rvPic.setAdapter(adapter);
            rvPic.setTag(imgs);
            Object tag=rvPic.getTag();
            if (tag==null||!tag.equals(imgs)){
                rvPic.setAdapter(new NinePicAdapter(imgs,helper.itemView.getContext()));
            }
            rvPic.setLayoutManager(new GridLayoutManager(
                    helper.itemView.getContext(),3
            ));
            rvPic.setHasFixedSize(true);
        }
        helper.setGone(R.id.tv_more,false);

        if (item.getChildCommentLists().size()>0){
            rvCommentList.setVisibility(View.VISIBLE);
            helper.setGone(R.id.tv_more,true);
            if (rvCommentList.getAdapter()==null){
                ChildCommentAdapter childCommentAdapter = new ChildCommentAdapter(item.getChildCommentLists());
                childCommentAdapter.setHasStableIds(true);
//                childCommentAdapter.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        Intent intent=new Intent(helper.itemView.getContext(),DynamicDetailActivity.class);
//                        intent.putExtra(KeyUtil.KEY_EVENT_ID, item.getEventId());
//                        intent.putExtra(KeyUtil.KEY_COMMENT_ID, item.getCommentId());
//                        helper.itemView.getContext().startActivity(intent);
//                    }
//                });
                rvCommentList.setAdapter(childCommentAdapter);
                rvCommentList.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false));
                rvCommentList.setHasFixedSize(true);
            }
        }

    }

}
