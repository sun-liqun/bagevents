package com.bagevent.new_home.adapter;

import android.text.Html;
import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.new_home.data.CommentEntity;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.util.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/6
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class CommentAdapter extends BaseMultiItemQuickAdapter<CommentEntity, BaseViewHolder> {
    public CommentAdapter(List<CommentEntity> data) {
        super(data);
        addItemType(CommentEntity.TYPE_ITEM, R.layout.item_comment);
        addItemType(CommentEntity.TYPE_LINE, R.layout.item_line);
        addItemType(CommentEntity.TYPE_NAME, R.layout.item_name);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {

        switch (item.getItemType()) {
            case CommentEntity.TYPE_NAME:
                helper.setText(R.id.tv_name, item.getName());
                break;
            case CommentEntity.TYPE_ITEM:
                setItem(helper, item);
                break;
        }
    }

    private void setItem(final BaseViewHolder helper, CommentEntity item) {
        helper.setGone(R.id.iv_identity, false);
        DynamicListData.CommentList commentList = item.getCommentList();
        String url = commentList.getAvatar();
        DynamicListData.User user = commentList.getUser();
        ImageView imageView = helper.getView(R.id.iv_head);
        if (url.startsWith("//")) {
            url = "https:" + url;
        } else if (url.startsWith("/")) {
//            url = "https://www.bagevent.com" + url;
            url = "https://img.bagevent.com" + url;
        }
        RequestOptions options=new RequestOptions()
                .placeholder(R.mipmap.icon);
        Glide.with(mContext).load(url).apply(options).into(imageView);
//        Glide.with(mContext).load(url).placeholder(R.mipmap.icon).transform(new CircleTransform(mContext)).into(imageView);

        helper.setText(R.id.tv_user_name, user.getShowName());

        if (commentList.isReplyComment()) {
//            DynamicListData.User replayToUser = commentList.getReplayToUser();
            DynamicListData.User user1=commentList.getReplyComment1().getIsReplyToUser();
            helper.setText(R.id.tv_user_comment, Html.fromHtml(String.format("<font color='#898989'>回复&nbsp;</font><font color='#202020'>%1$s：%2$s</font>", user1.getShowName(), commentList.getCommentText())));
        } else {
            helper.setText(R.id.tv_user_comment, commentList.getCommentText());
        }

        if (commentList.getIdentityType() == 0 || commentList.getIdentityType()==3) {
            helper.setGone(R.id.iv_identity, false);
        } else if (commentList.getIdentityType() == 2 ) {
            helper.setGone(R.id.iv_identity, true);
            Glide.with(mContext).load(R.drawable.icon_organizer).into((ImageView) helper.getView(R.id.iv_identity));
        } else {
            helper.setGone(R.id.iv_identity, true);
            Glide.with(mContext).load(R.drawable.icon_guest).into((ImageView) helper.getView(R.id.iv_identity));
        }

        helper.addOnClickListener(R.id.iv_more);
    }
}
