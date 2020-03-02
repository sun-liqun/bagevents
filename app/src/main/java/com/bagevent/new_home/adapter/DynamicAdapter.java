package com.bagevent.new_home.adapter;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.util.CircleRoundTransform;
import com.bagevent.util.LogUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.view.HorizontalScrollImag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/21
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class DynamicAdapter extends BaseQuickAdapter<DynamicListData.CommentList, BaseViewHolder> {
    private HomePage homePage;
    private ArrayList<String> pos;
    //    AutoFrameLayout fl_bg;
    private MediaPlayer mediaPlayer;
    String[] image = {};


    public DynamicAdapter(HomePage homePage, ArrayList<String> pos, @Nullable List<DynamicListData.CommentList> data) {
        super(R.layout.item_dynamic, data);
        this.pos = pos;
        this.homePage = homePage;
        EventBus.getDefault().register(this);
    }

    //防止隐藏item出现空白
      public void setVisibility(boolean isVisible, View itemView) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)
                itemView.getLayoutParams();
        if (isVisible) {
            param.height = AutoRelativeLayout.LayoutParams.WRAP_CONTENT;
                  param.width = AutoRelativeLayout.LayoutParams.MATCH_PARENT;
             itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final DynamicListData.CommentList item) {
        RequestOptions optionsCircle = new RequestOptions()
                .transform(new CircleRoundTransform(mContext, 2));

        helper.setGone(R.id.rv_pic, false);
//        fl_bg = helper.itemView.findViewById(R.id.fl_bg);
//        helper.setGone(R.id.fl_bg, false);
        helper.setGone(R.id.fl_dynamic_img, false);
//        helper.setGone(R.id.ll_dynamic_all, true);
        setVisibility(true,helper.getView(R.id.ll_dynamic_all));
//        helper.setGone(R.id.hsl_imgs,false);

        DynamicListData.User user = item.getUser();
        helper.setText(R.id.tv_name, user.getShowName());

        ImageView iv_head = helper.getView(R.id.iv_head);
        if (TextUtils.isEmpty(user.getAvatar())) {
            iv_head.setImageResource(R.mipmap.icon);
        } else {
            String url = user.getAvatar();
            if (url.startsWith("//")) {
                url = "https:" + url;
            } else if (url.startsWith("/")) {
//                url = "https://www.bagevent.com" + url;
                url = "https://img.bagevent.com" + url;

            }
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.icon);
            Glide.with(mContext).load(url).apply(options).into(iv_head);
        }

        helper.setGone(R.id.iv_identity, false);
        if (item.getIdentityType() != 0) {
            helper.setGone(R.id.iv_identity, true);
            ImageView iv_identity = helper.getView(R.id.iv_identity);

            if (item.getIdentityType() == 2) {
                Glide.with(mContext).load(R.drawable.icon_organizer).into(iv_identity);
            } else if (item.getIdentityType() == 3) {
                helper.setGone(R.id.iv_identity, false);
            } else {
                Glide.with(mContext).load(R.drawable.icon_guest).into(iv_identity);
            }
        }

        if (item.getStatus() == 0) {
            helper.setGone(R.id.tv_status, true);
        } else {
            helper.setGone(R.id.tv_status, false);
        }

        if (item.getPriority() == 1) {
            helper.setGone(R.id.tv_place_top, true);
        } else {
            helper.setGone(R.id.tv_place_top, false);
        }

        helper.setGone(R.id.ll_type_replay, false);
        helper.setGone(R.id.ll_type_question, false);
        helper.setGone(R.id.ll_type_dynamic, false);
//        helper.setGone(R.id.hsl_imgs, false);
        helper.setGone(R.id.ll_play_voice, false);

        if(item.getType()==7){
            setVisibility(false,helper.getView(R.id.ll_dynamic_all));
        }else{
            setVisibility(true,helper.getView(R.id.ll_dynamic_all));
        }

        if (item.getType() == 1) {
            //带有问答的
            helper.setGone(R.id.ll_type_question, true);
            helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, item.getQuestionContent());
            helper.setText(R.id.tv_focus_question_num, Html.fromHtml(String.format("<font color='#ff9000'>%d</font><font color='#545454'>&nbsp;" + mContext.getResources().getString(R.string.people_follow) + "</font>", item.getCollectionNumber())));
            ArrayList<DynamicListData.Speaker> speakerList = item.getSpeakerList();

            helper.setGone(R.id.iv_spaeker_head, false);

            if (item.getCommentKey().equals("AGENDA")) {
                helper.setText(R.id.tv_queston_name, "#" + item.getQuestionName() + "#");
            } else {
                helper.setGone(R.id.iv_spaeker_head, true);
                if (speakerList != null && speakerList.size() > 0) {
                    DynamicListData.Speaker speaker = speakerList.get(0);
                    if (speaker.isHasInvite()) {
                        helper.setText(R.id.tv_queston_name, Html.fromHtml(String.format("<font color='#545454'>@%s</font>&nbsp;&nbsp;<font color='#ff9000'>" + mContext.getResources().getString(R.string.guest) + "</font>", speaker.getSpeakerName())));
                    } else {
                        helper.setText(R.id.tv_queston_name, Html.fromHtml(String.format("<font color='#545454'>@%s</font>&nbsp;&nbsp;<font color='#009feb'>" + mContext.getResources().getString(R.string.invite_ta) + "</font>", speaker.getSpeakerName())));
                        helper.addOnClickListener(R.id.tv_queston_name);
                    }

                    String url = speaker.getAvatar();
                    if (TextUtils.isEmpty(url)) {
                        Glide.with(mContext).load(R.drawable.default_head).into((ImageView) helper.getView(R.id.iv_spaeker_head));
                    } else {
                        if (url.startsWith("//")) {
                            url = "https:" + url;
                        } else if (url.startsWith("/")) {
//                            url = "https://www.bagevent.com" + url;
                            url = "https://img.bagevent.com" + url;
                        }

                        Glide.with(mContext).load(url).apply(optionsCircle).into((ImageView) helper.getView(R.id.iv_spaeker_head));
//                        Glide.with(mContext).load(url).transform(new CircleRoundTransform(mContext, 2)).into((ImageView) helper.getView(R.id.iv_spaeker_head));
                    }
                }
            }
        } else if (item.getType() == 2) {
            //普通的
            if (TextUtils.isEmpty(item.getCommentText())) {
                helper.setGone(R.id.tv_content, false);
            } else {
                helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, item.getCommentText());
            }
            if (!TextUtils.isEmpty(item.getImages())) {
//                helper.setGone(R.id.hsl_imgs, true);
//                HorizontalScrollImag hsl_imgs = helper.getView(R.id.hsl_imgs);

                String[] urls = item.getImages().split(",");
                List<String> imgs = new ArrayList<>(Arrays.asList(urls));
                RecyclerView rvPic = helper.getView(R.id.rv_pic);

                helper.setGone(R.id.rv_pic, true);

//                if (rvPic.getAdapter()==null ){
                NinePicAdapter adapter = new NinePicAdapter(imgs, helper.itemView.getContext());
                adapter.setHasStableIds(true);
                rvPic.setAdapter(adapter);
                rvPic.setTag(imgs);
                Object tag = rvPic.getTag();
                if (tag == null || !tag.equals(imgs)) {
                    rvPic.setAdapter(new NinePicAdapter(imgs, helper.itemView.getContext()));
                }
                rvPic.setLayoutManager(new GridLayoutManager(
                        helper.itemView.getContext(), 3
                ));
                rvPic.setHasFixedSize(true);
//                }else {
//                    Log.i("-----------2",user.getShowName()+item.getCommentText());
//                }
//                hsl_imgs.setTag(imags);
//                hsl_imgs.setImgs(imags);
//
//                Object tag = hsl_imgs.getTag();
//                if (tag == null || !tag.equals(imags)) {
//                    hsl_imgs.setImgs(imags);
//                }
            }
        } else if (item.getType() == 3) {
            //带动态的
            helper.setGone(R.id.ll_type_dynamic, true);
            DynamicListData.CommentList parentComment = item.getParentComment();
            DynamicListData.User parentUser = parentComment.getUser();
            helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, Html.fromHtml(String.format("<font color='#898989'>" + mContext.getResources().getString(R.string.reply) + "&nbsp;</font><font color='#202020'>%1$s：</font>", item.getCommentText())));
//            helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, Html.fromHtml(String.format("<font color='#898989'>回复：</font><font color='#202020'>%s</font>", item.getCommentText())));
//            helper.setText(R.id.tv_dynamic_name, Html.fromHtml(String.format("<font color='#202020'>%1$s</font>&nbsp;<font color='#898989'>发布动态：</font><font color='#202020'>%2$s</font>", parentUser.getShowName(), parentComment.getCommentText())));
            helper.setText(R.id.tv_dynamic_name, Html.fromHtml(String.format("<font color='#202020'>%1$s</font>&nbsp;<font color='#898989'>&nbsp;" + mContext.getResources().getString(R.string.release_dynamic1) + "</font><font color='#202020'>%2$s</font>", parentUser.getShowName(), parentComment.getCommentText())));
            helper.setText(R.id.tv_dynamic_zan, parentComment.getLikeNumber() + " " + mContext.getResources().getString(R.string.people_like));
            helper.setText(R.id.tv_dynamic_comment, item.getCommentNumber() + " " + mContext.getResources().getString(R.string.strip_comment));
            if (TextUtils.isEmpty(parentComment.getImages())) {
                helper.setGone(R.id.fl_dynamic_img, false);
            } else {
                helper.setGone(R.id.fl_dynamic_img, true);
                ImageView iv_dynamic_img = helper.getView(R.id.iv_dynamic_img);
                String[] urls = parentComment.getImages().split(",");
                helper.setGone(R.id.tv_dynamic_img_num, false);
                if (urls[0].startsWith("//")) {
                    urls[0] = "https:" + urls[0];
                } else if (urls[0].startsWith("/")) {
//                    urls[0] = "https://www.bagevent.com" + urls[0];
                    urls[0] = "https://img.bagevent.com" + urls[0];
                }
                if (urls.length > 1) {
                    helper.setGone(R.id.tv_dynamic_img_num, true);
                    helper.setText(R.id.tv_dynamic_img_num, "+ " + String.valueOf(urls.length));
                }
                Glide.with(mContext).load(urls[0]).into(iv_dynamic_img);
            }
        } else if (item.getType() == 4) {
            //带有通知的
            helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, mContext.getResources().getString(R.string.inform) + item.getCommentText());
        } else if (item.getType() == 5) {
            //带有回复的
            DynamicListData.CommentList parentComment = item.getParentComment();
            DynamicListData.User parentUser = parentComment.getUser();
            helper.setGone(R.id.ll_type_replay, true);
            helper.setText(R.id.tv_replay_question, Html.fromHtml(String.format("<font color='#202020'>%1$s</font><font color='#545454'>&nbsp;" + mContext.getResources().getString(R.string.question) + "</font><font color='#202020'>%2$s</font>", parentUser.getShowName(), parentComment.getQuestionContent())));
            helper.setText(R.id.tv_replay_focus_num, Html.fromHtml(String.format("<font color='#ff9000'>%d</font><font color='#545454'>&nbsp;" + mContext.getResources().getString(R.string.people_follow), parentComment.getCollectionNumber())));

            if (TextUtils.isEmpty(item.getVoiceUrl())) {
                helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, Html.fromHtml(String.format("<font color='#898989'>" + mContext.getResources().getString(R.string.reply) + "&nbsp;</font><font color='#202020'>%1$s：%2$s</font>", parentUser.getShowName(), item.getCommentText())));
            } else {
                helper.setGone(R.id.ll_play_voice, true);
                helper.setGone(R.id.tv_content, true).setText(R.id.tv_content, Html.fromHtml(String.format("<font color='#898989'>" + mContext.getResources().getString(R.string.reply) + "&nbsp;</font><font color='#202020'>%1$s：</font>", parentUser.getShowName())));
                helper.setText(R.id.tv_duration, item.getDuration());
                helper.getView(R.id.tv_play).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            setTextViewLeftImg((TextView) v, R.drawable.icon_stop);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(item.getVoiceUrl());
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mediaPlayer.start();
                                }
                            });
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    setTextViewLeftImg((TextView) v, R.drawable.icon_play);
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
            }

            if (TextUtils.isEmpty(parentComment.getQuestionName())) {
                helper.setGone(R.id.tv_replay_event, false);
            } else {
                helper.setGone(R.id.tv_replay_event, true);
                helper.setText(R.id.tv_replay_event, "@" + parentComment.getQuestionName());
            }

//            Glide.with(mContext).load(parentComment.getQuestionImage()).transform(new CircleRoundTransform(mContext, 2)).into((ImageView) helper.getView(R.id.iv_replay_head));
            Glide.with(mContext).load(parentComment.getQuestionImage()).apply(optionsCircle).into((ImageView) helper.getView(R.id.iv_replay_head));
        }

        if (item.getType() == 4 || item.getType() == 3 || item.getType() == 1) {
            helper.setGone(R.id.ll_zan, false);
        } else {
            helper.setGone(R.id.ll_zan, true);
            helper.setText(R.id.tv_zan, item.getLikeNumber() + " " + mContext.getResources().getString(R.string.people_like));
            helper.setText(R.id.tv_comment, item.getCommentNumber() + " " + mContext.getResources().getString(R.string.strip_comment));
        }

        if (item.getType() != 4) {
            helper.setGone(R.id.iv_event_comment, true);
        } else {
            helper.setGone(R.id.iv_event_comment, false);
        }
        helper.addOnClickListener(R.id.iv_more);
        helper.addOnClickListener(R.id.rl_event_comment);
        helper.setText(R.id.tv_publish_time, TimeUtil.getTimeStringFormat(item.getCommentSubTime(), mContext));
        helper.setText(R.id.tv_event_name, "#" + item.getEventName()
                .replaceAll("&amp;", "&")
                .replaceAll("&#40;", "(")
                .replaceAll("&#41;", ")")
                .replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "'")
                .replaceAll("&nbsp;", " ")
                + "#");
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void setTextViewLeftImg(TextView textView, int imgId) {
        Drawable drawable = mContext.getResources().getDrawable(imgId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("refresh_finish")) {
//            fl_bg.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
