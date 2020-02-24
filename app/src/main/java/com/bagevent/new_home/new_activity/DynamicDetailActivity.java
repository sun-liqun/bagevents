package com.bagevent.new_home.new_activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.dialog.LDialog;
import com.bagevent.dialog.ViewHelper;
import com.bagevent.dialog.listener.OnBindView;
import com.bagevent.dialog.listener.OnViewClick;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.adapter.CommentAdapter;
import com.bagevent.new_home.adapter.NinePicAdapter;
import com.bagevent.new_home.data.CommentEntity;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.bagevent.util.BitmapUtils;
import com.bagevent.util.CircleRoundTransform;
import com.bagevent.util.CompareRex;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.view.HorizontalScrollImag;
import com.bagevent.view.MyLinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonParser;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/4
 * <p>
 * desp 动态详情页面
 * <p>
 * <p>
 * =============================================
 */
public class DynamicDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, MyLinearLayout.OnResizeListener {
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.iv_spaeker_head)
    ImageView iv_spaeker_head;
    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_publish_time)
    TextView tv_publish_time;
    @BindView(R.id.tv_focus_question_num)
    TextView tv_focus_question_num;
    @BindView(R.id.iv_identity)
    ImageView iv_identity;
    @BindView(R.id.iv_replay_head)
    ImageView iv_replay_head;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_place_top)
    TextView tv_place_top;
    @BindView(R.id.tv_comment_num)
    TextView tv_comment_num;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_queston_name)
    TextView tv_queston_name;
    @BindView(R.id.tv_speaker_name)
    TextView tv_speaker_name;
    //    @BindView(R.id.hsl_imgs)
//    HorizontalScrollImag hsl_imgs;
    @BindView(R.id.ll_head_info)
    AutoLinearLayout llHeadInfo;
    @BindView(R.id.ll_zan)
    AutoLinearLayout ll_zan;
    @BindView(R.id.ll_play_voice)
    AutoLinearLayout ll_play_voice;
    @BindView(R.id.ll_type_replay)
    AutoLinearLayout ll_type_replay;
    @BindView(R.id.ll_type_question)
    AutoLinearLayout ll_type_question;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    @BindView(R.id.tv_replay_event)
    TextView tv_replay_event;
    @BindView(R.id.tv_replay_question)
    TextView tv_replay_question;
    @BindView(R.id.tv_replay_focus_num)
    TextView tv_replay_focus_num;
    @BindView(R.id.tv_play)
    TextView tv_play;
    @BindView(R.id.spl_refresh)
    SwipeRefreshLayout splRefresh;
    @BindView(R.id.et_send_content)
    EditText et_send_content;
    @BindView(R.id.myLinearLayout)
    MyLinearLayout myLinearLayout;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.rv_ninePic)
    RecyclerView rvPic;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_type_dynamic)
    AutoLinearLayout llTypeDynamic;
    @BindView(R.id.tv_dynamic_name)
    TextView tvDynamicName;
    @BindView(R.id.fl_dynamic_img)
    AutoFrameLayout flDynamicImg;
    @BindView(R.id.tv_dynamic_img_num)
    TextView tvDynamicImgNum;
    @BindView(R.id.iv_dynamic_img)
    ImageView ivDynamicImg;
    @BindView(R.id.tv_dynamic_zan)
    TextView tvDynamicZan;
    @BindView(R.id.tv_dynamic_comment)
    TextView tvDynamicComment;
    private int eventId;
    private int commentId;
    private DynamicListData dynamicListData;
    private final byte LOAD = 0;
    private final byte REFRESH = 1;
    private CommentAdapter commentAdapter;
    private NinePicAdapter ninePicAdapter;
    private DynamicListData.CommentList comment;
    private LDialog lDialog;
    private String eventName;
    private MyHandler handler = new MyHandler(this);
    private int flag = -1;
    private int exhibitorFlag = -1;
    private int exhibitorId;
    private int replyCommentId;
//
//    DynamicListData.CommentList commentList;
//    private PopupWindow mPopUpWindow;
//    private View mPopUpView;
//    private int popupWidth;
//    private int popupHeight;
//    private TextView tv_like;
//    private TextView tv_comment_dynamic;
//    private int location[];

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_detail);
        ButterKnife.bind(this);

        eventId = getIntent().getIntExtra(KeyUtil.KEY_EVENT_ID, 0);
        commentId = getIntent().getIntExtra(KeyUtil.KEY_COMMENT_ID, 0);
        flag = getIntent().getIntExtra(KeyUtil.KEY_TYPE, 0);
        exhibitorId = getIntent().getIntExtra("exhibitorId", 0);

        if (flag == 1) {//展商进入
            ivMore.setVisibility(View.GONE);
        } else {
            ivMore.setVisibility(View.VISIBLE);
        }
        myLinearLayout.setOnResizeListener(this);

        splRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        splRefresh.setRefreshing(true);
        getDataFromNet(eventId, commentId, LOAD);
        InputMethodUtil.close(getApplication(), getWindow().getDecorView());

//        initPop();
    }

//    private void initPop() {
//        mPopUpView = getLayoutInflater().inflate(R.layout.activity_comment_pop, null);
//        mPopUpView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupWidth = mPopUpView.getMeasuredWidth();    //  获取测量后的宽度
//        popupHeight = mPopUpView.getMeasuredHeight();  //获取测量后的高度
//        location = new int[2];
//        tv_like = mPopUpView.findViewById(R.id.tv_like);
//        tv_comment_dynamic = mPopUpView.findViewById(R.id.tv_comment_dynamic);
//        mPopUpWindow = new PopupWindow(mPopUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) {
//            @Override
//            public void dismiss() {
//                ValueAnimator valueAnimator = createDropAnimator(mPopUpView, popupWidth, 0);
//                valueAnimator.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        myDismiss();
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                valueAnimator.start();
//            }
//
//
//            public void myDismiss() {
//                super.dismiss();
//            }
//        };
//
//        mPopUpWindow.setTouchable(true);
//        mPopUpWindow.setOutsideTouchable(true);
//        mPopUpWindow.setFocusable(true);
//
//        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        mPopUpWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
//                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
//                        mPopUpWindow.dismiss();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//        tv_like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (commentList.getType() == 1 || commentList.getType() == 2) {
//                    OkHttpUtil.Post(DynamicDetailActivity.this)
//                            .url(Constants.NEW_URL + Constants.EDIT_COMMENT_LIKE + "/" + commentList.getCommentId())
//                            .addParams("eventId", String.valueOf(commentList.getEventId()))
//                            .build()
//                            .execute(new Callback<String>() {
//
//                                @Override
//                                public String parseNetworkResponse(Response response, int id) throws Exception {
//                                    return response.body().string();
//                                }
//
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//                                    TosUtil.show("发送失败，请稍后重试！");
//                                }
//
//                                @Override
//                                public void onResponse(String response, int id) {
//                                    if (response.contains("\"retStatus\":200")) {
////                                        page = 1;
////                                        recyclerViewBind.spl_refresh.setRefreshing(true);
////                                        loadDataFromServer(eventId, ACTION_REFRESH);
//                                    } else {
//                                        TosUtil.show("发送失败，请稍后重试！");
//                                    }
//                                }
//                            });
//                } else if (commentList.getType() == 5) {
//                    OkHttpUtil.Post(DynamicDetailActivity.this)
//                            .url(Constants.NEW_URL + Constants.EDIT_COMMENT_LIKE + "/" +
//                                    commentList.getParentComment().getCommentId())
//                            .addParams("eventId", String.valueOf(commentList.getEventId()))
//                            .build()
//                            .execute(new Callback<String>() {
//
//                                @Override
//                                public String parseNetworkResponse(Response response, int id) throws Exception {
//                                    return response.body().string();
//                                }
//
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//                                    TosUtil.show("发送失败，请稍后重试！");
//                                }
//
//                                @Override
//                                public void onResponse(String response, int id) {
//                                    if (response.contains("\"retStatus\":200")) {
////                                        page = 1;
////                                        recyclerViewBind.spl_refresh.setRefreshing(true);
////                                        loadDataFromServer(eventId, ACTION_REFRESH);
//                                    } else {
//                                        TosUtil.show("发送失败，请稍后重试！");
//                                    }
//                                }
//                            });
//
//                }
//                if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
//                    mPopUpWindow.dismiss();
//                }
//            }
//        });
//        tv_comment_dynamic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_GET_NICKNAME, commentList.getUser().getShowName()));
//
//                if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
//                    mPopUpWindow.dismiss();
//                }
//            }
//        });
//    }
//
//    private ValueAnimator createDropAnimator(final View view, int start, int end) {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", end, start);
//        objectAnimator.setDuration(100);
//        return objectAnimator;
//    }

    private void refreshData() {
        getDataFromNet(eventId, commentId, REFRESH);
    }

    private void getDataFromNet(int eventId, int commentId, final byte action) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.GET_COMMENT_DETAIL)
                .addParams("eventId", String.valueOf(eventId))
                .addParams("commentId", String.valueOf(commentId))
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        splRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action);
                        } else {
                            splRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    private void parserSuccess(String response, byte action) {
        dynamicListData = new DynamicListData(new JsonParser().parse(response).getAsJsonObject());
        comment = dynamicListData.getRespObject().getComment();
        DynamicListData.User user = comment.getUser();

        splRefresh.setRefreshing(false);
        llHeadInfo.setVisibility(View.VISIBLE);


//        commentList=dynamicListData.getRespObject().getComment();
//        int likeNumber = commentList.getLikeNumber();
//        iv_comment.getLocationOnScreen(location);
//        if (likeNumber == 0) {
//            tv_like.setText("赞");
//        } else {
//            tv_like.setText("取消");
//        }
//        mPopUpWindow.showAtLocation(iv_comment, Gravity.NO_GRAVITY, location[0] - popupWidth, location[1] + 10);
//        ValueAnimator animator = createDropAnimator(mPopUpView, 0, popupWidth);
//        animator.start();
//
        String url = user.getAvatar();
        if (url.startsWith("//")) {
            url = "https:" + url;
        } else if (url.startsWith("/")) {
//            url = "https://www.bagevent.com" + url;
            url = "https://img.bagevent.com" + url;
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon);
        Glide.with(this).load(url).apply(requestOptions).into(iv_head);

        String eventName = comment.getEventName()
                .replaceAll("&amp;", "&")
                .replaceAll("&#40;", "(")
                .replaceAll("&#41;", ")")
                .replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "'")
                .replaceAll("&nbsp;", " ");
        tv_event_name.setText("#" + eventName + "#");

        tv_publish_time.setText(TimeUtil.getTimeStringFormat(comment.getCommentSubTime(), getApplicationContext()));

        tv_user_name.setText(user.getShowName());

        if (comment.getIdentityType() == 0 || comment.getIdentityType() == 3) {
            iv_identity.setVisibility(View.GONE);
        } else if (comment.getIdentityType() == 2 || comment.getIdentityType() == 3) {
            Glide.with(this).load(R.drawable.icon_organizer).into(iv_identity);
        } else {
            Glide.with(this).load(R.drawable.icon_guest).into(iv_identity);
        }

        if (comment.getStatus() == 0) {
            tv_status.setVisibility(View.VISIBLE);
        } else {
            tv_status.setVisibility(View.GONE);
        }

        if (comment.getPriority() == 1) {
            tv_place_top.setVisibility(View.VISIBLE);
        } else {
            tv_place_top.setVisibility(View.GONE);
        }

        if (comment.getType() == 1) {
            ll_type_question.setVisibility(View.VISIBLE);
            ll_zan.setVisibility(View.GONE);
            tv_content.setText(comment.getQuestionContent());
            tv_focus_question_num.setText(Html.fromHtml(String.format("<font color='#ff9000'>%d</font><font color='#545454'>&nbsp;人关注了此问题</font>", comment.getCollectionNumber())));

            ArrayList<DynamicListData.Speaker> speakerList = comment.getSpeakerList();
            if (comment.getCommentKey().equals("AGENDA")) {
                tv_queston_name.setVisibility(View.VISIBLE);
                tv_queston_name.setText("#" + comment.getQuestionName() + "#");
            }
            if (speakerList != null && speakerList.size() > 0) {
                final DynamicListData.Speaker speaker = speakerList.get(0);
                if (speaker.isHasInvite()) {
                    tv_speaker_name.setText(Html.fromHtml(String.format("<font color='#545454'>@%s</font>&nbsp;&nbsp;<font color='#ff9000'>" + getString(R.string.guest) + "</font>", speaker.getSpeakerName())));
                } else {
                    tv_speaker_name.setText(Html.fromHtml(String.format("<font color='#545454'>@%s</font>&nbsp;&nbsp;<font color='#009feb'>" + getString(R.string.invite_ta) + "</font>", speaker.getSpeakerName())));
                    tv_speaker_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showInviteDialog(speaker, comment);
                        }
                    });
                }

                String speakerUrl = speaker.getAvatar();
                if (TextUtils.isEmpty(speakerUrl)) {
                    Glide.with(this).load(R.drawable.default_head).into(iv_spaeker_head);
                } else {
                    if (speakerUrl.startsWith("//")) {
                        speakerUrl = "https:" + speakerUrl;
                    } else if (speakerUrl.startsWith("/")) {
//                        speakerUrl = "https://www.bagevent.com" + speakerUrl;
                        speakerUrl = "https://img.bagevent.com" + speakerUrl;
                    }
                    RequestOptions options = new RequestOptions().transform(new CircleRoundTransform(this, 2));
                    Glide.with(this).load(speakerUrl).apply(options).into(iv_spaeker_head);
//                    Glide.with(this).load(speakerUrl).transform(new CircleRoundTransform(this, 2)).into(iv_spaeker_head);
                }
            }
        } else if (comment.getType() == 2) {
            //普通的
//            line.setVisibility(View.GONE);
//            tv_comment_num.setVisibility(View.GONE);
            tv_comment_num.setText(comment.getCommentNumber() + getString(R.string.strip_comment));
            if (TextUtils.isEmpty(comment.getCommentText())) {
                tv_content.setVisibility(View.GONE);
            } else {
                tv_content.setText(comment.getCommentText());
            }
            tv_zan.setText(comment.getLikeNumber() + getString(R.string.people_like));
//            tv_zan.setText(Html.fromHtml(String.format("<font color='#545454'>点赞</font>&nbsp;<font color='#ff9000'>%d</font>", comment.getLikeNumber())));
            if (!TextUtils.isEmpty(comment.getImages())) {
                String[] urls = comment.getImages().split(",");
//                hsl_imgs.setVisibility(View.VISIBLE);
//                hsl_imgs.setImgs(urls);
                rvPic.setVisibility(View.VISIBLE);
                List<String> pics = new ArrayList<>(Arrays.asList(urls));
                ninePicAdapter = new NinePicAdapter(pics, DynamicDetailActivity.this);
                rvPic.setAdapter(ninePicAdapter);
                rvPic.setLayoutManager(new GridLayoutManager(
                        DynamicDetailActivity.this, 3
                ));
                rvPic.setHasFixedSize(true);

            }
        } else if (comment.getType() == 3) {
            //带动态的
            llTypeDynamic.setVisibility(View.VISIBLE);
//            tv_comment_num.setText(comment.getCommentNumber()+"条评论");
//            tv_zan.setText(comment.getLikeNumber()+"人赞");
            tvDynamicComment.setText(comment.getCommentNumber() + getString(R.string.strip_comment));
            tvDynamicZan.setText(comment.getLikeNumber() + getString(R.string.people_like));
            ll_zan.setVisibility(View.GONE);
            DynamicListData.CommentList parentComment = comment.getParentComment();
            DynamicListData.User user1 = comment.getUser();
//            if (TextUtils.isEmpty(parentComment.getCommentText())) {
//                tv_content.setVisibility(View.GONE);
//            } else {
//                tv_content.setText(parentComment.getCommentText());
//            }
            if (TextUtils.isEmpty(comment.getCommentText())) {
                tv_content.setVisibility(View.GONE);
            } else {
                tv_content.setText(Html.fromHtml(String.format("<font color='#898989'>" + getString(R.string.reply2) + "</font><font color='#202020'>%s</font>", comment.getCommentText())));
            }
            tvDynamicName.setText(Html.fromHtml(String.format("<font color='#202020'>%1$s</font>&nbsp;<font color='#898989'>" + getString(R.string.release_dynamic1) + "</font><font color='#202020'>%2$s</font>", user1.getShowName(), parentComment.getCommentText())));

            if (!TextUtils.isEmpty(parentComment.getImages())) {
                String[] urls = parentComment.getImages().split(",");
                if (urls[0].startsWith("//")) {
                    urls[0] = "https:" + urls[0];
                } else if (urls[0].startsWith("/")) {
//                    urls[0] = "https://www.bagevent.com" + urls[0];
                    urls[0] = "https://img.bagevent.com" + urls[0];
                }
                if (urls.length > 1) {
                    tvDynamicImgNum.setVisibility(View.VISIBLE);
                    tvDynamicImgNum.setText("+ " + String.valueOf(urls.length));
                }
                Glide.with(getApplication()).load(urls[0]).into(ivDynamicImg);
////                hsl_imgs.setVisibility(View.VISIBLE);
////                hsl_imgs.setImgs(urls);
//                rvPic.setVisibility(View.VISIBLE);
//                List<String> pics=new ArrayList<>(Arrays.asList(urls));
//                ninePicAdapter = new NinePicAdapter(pics, DynamicDetailActivity.this);
//                rvPic.setAdapter(ninePicAdapter);
//                rvPic.setLayoutManager(new GridLayoutManager(
//                        DynamicDetailActivity.this,3
//                ));
//                rvPic.setHasFixedSize(true);
            } else {
                flDynamicImg.setVisibility(View.GONE);
            }
        } else if (comment.getType() == 5) {
            //带有回复的
            ll_type_replay.setVisibility(View.VISIBLE);
//            tv_comment_num.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);

            tv_comment_num.setText(comment.getCommentNumber() + getString(R.string.strip_comment));

            DynamicListData.CommentList parentComment = comment.getParentComment();
            DynamicListData.User parentUser = parentComment.getUser();

            tv_replay_question.setText(Html.fromHtml(String.format("<font color='#202020'>%1$s</font><font color='#545454'>&nbsp;" + getString(R.string.question) + "</font><font color='#202020'>%2$s</font>", parentUser.getShowName(), parentComment.getQuestionContent())));
            tv_replay_focus_num.setText(Html.fromHtml(String.format("<font color='#ff9000'>%d</font><font color='#545454'>&nbsp;" + getString(R.string.people_follow), parentComment.getCollectionNumber())));
//            tv_zan.setText(Html.fromHtml(String.format("<font color='#545454'>点赞</font>&nbsp;<font color='#ff9000'>%d</font><font color='#545454'>", comment.getLikeNumber())));
            tv_zan.setText(comment.getLikeNumber() + getString(R.string.people_like));
            if (TextUtils.isEmpty(comment.getVoiceUrl())) {
                tv_content.setText(Html.fromHtml(String.format("<font color='#898989'>" + getString(R.string.reply) + "&nbsp;</font><font color='#202020'>%1$s：%2$s</font>", parentUser.getShowName(), comment.getCommentText())));
            } else {
                ll_play_voice.setVisibility(View.VISIBLE);
                tv_content.setText(Html.fromHtml(String.format("<font color='#898989'>" + getString(R.string.reply) + "&nbsp;</font><font color='#202020'>%1$s：</font>", parentUser.getShowName())));
                tv_duration.setText(comment.getDuration());
                tv_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            setTextViewLeftImg((TextView) v, R.drawable.icon_stop);
                            final MediaPlayer mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(comment.getVoiceUrl());
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
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
            }

            if (!TextUtils.isEmpty(parentComment.getQuestionName())) {
                tv_replay_event.setVisibility(View.VISIBLE);
                tv_replay_event.setText("@" + parentComment.getQuestionName());
            }
            RequestOptions options = new RequestOptions().transform(new CircleRoundTransform(this, 2));
            Glide.with(this).load(parentComment.getQuestionImage()).apply(options).into(iv_replay_head);
        }

        ArrayList<DynamicListData.CommentList> childCommentList = dynamicListData.getRespObject().getChildCommentList();
        ArrayList<DynamicListData.CommentList> organizerCommentList = dynamicListData.getRespObject().getOrganizerCommentList();
        if ((childCommentList == null || childCommentList.size() == 0) && (organizerCommentList == null || organizerCommentList.size() == 0)) {
            tvComment.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        } else {
            tvComment.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            ArrayList<CommentEntity> commentEntities = new ArrayList<>();

            if (childCommentList.size() > 0 || organizerCommentList.size() > 0) {
                commentEntities.add(new CommentEntity(CommentEntity.TYPE_LINE));
            }


            if (organizerCommentList != null && organizerCommentList.size() > 0) {
                commentEntities.add(new CommentEntity(CommentEntity.TYPE_NAME, getString(R.string.my_reply)));
                for (int i = 0; i < organizerCommentList.size(); i++) {
                    commentEntities.add(new CommentEntity(CommentEntity.TYPE_ITEM, organizerCommentList.get(i)));
                }
            }

            commentEntities.add(new CommentEntity(CommentEntity.TYPE_LINE));
            if (childCommentList != null && childCommentList.size() > 0) {
                commentEntities.add(new CommentEntity(CommentEntity.TYPE_NAME, String.format(getString(R.string.all) + "%s" + getString(R.string.strip_comment), childCommentList.size())));
                for (int i = 0; i < childCommentList.size(); i++) {
                    commentEntities.add(new CommentEntity(CommentEntity.TYPE_ITEM, childCommentList.get(i)));
                }
            }

            if (action == LOAD) {
                initRecyclerView(commentEntities);
            } else {
                if (commentAdapter == null) {
                    initRecyclerView(commentEntities);
                } else {
                    commentAdapter.setNewData(commentEntities);
                }
            }
        }
    }

    private void showInviteDialog(final DynamicListData.Speaker speaker, final DynamicListData.CommentList comment) {
        new LDialog.Builder(getFragmentManager())
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setLayoutId(R.layout.dialog_invite)
                .setGravity(Gravity.BOTTOM)
                .setDialogAnimationRes(R.style.BottomDialogAnimation)
                .setCancelableOutside(true)
                .addOnClick(R.id.tv_invite_sms, R.id.tv_invite_email, R.id.tv_invite_weichat, R.id.tv_cancel)
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                        dialog.dismiss();
                        switch (view.getId()) {
                            case R.id.tv_invite_sms:
                                if (TextUtils.isEmpty(speaker.getContactPhone())) {
                                    showInviteSms(String.valueOf(speaker.getSpeakerId()), String.valueOf(comment.getCommentId()), "2");
                                } else {
                                    sendSmsInvite(String.valueOf(speaker.getSpeakerId()), String.valueOf(comment.getCommentId()), "2");
                                }
                                break;
                            case R.id.tv_invite_email:
                                if (TextUtils.isEmpty(speaker.getContactEmail())) {
                                    showInviteEmail(String.valueOf(speaker.getSpeakerId()), String.valueOf(comment.getCommentId()), "1");
                                } else {
                                    sendEmailInvite(String.valueOf(speaker.getSpeakerId()), String.valueOf(comment.getCommentId()), "1");
                                }
                                break;
                            case R.id.tv_invite_weichat:
//                                final String imgUrl = "https://www.bagevent.com" + comment.getEventLogo();
                                final String imgUrl = "https://img.bagevent.com" + comment.getEventLogo();
                                eventName = comment.getEventName();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            URL url = new URL(imgUrl);
                                            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                                            Message message = new Message();
                                            message.obj = bitmap;
                                            message.what = 666;
                                            handler.sendMessage(message);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;
                        }

                    }
                })
                .build()
                .show();
    }

    private void sendWeichatInvite(String eventName, Bitmap bitmap) {
        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = "https://www.bagevent.com/";//自定义
        miniProgram.userName = "gh_db5f6792ed3e";//小程序端提供参数
        miniProgram.path = "pages/index/index";//小程序端提供参数
        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
        mediaMessage.title = getString(R.string.from_left) + eventName + getString(R.string.from_right);//自定义
        mediaMessage.description = getString(R.string.from_left) + eventName + getString(R.string.from_right);//自定义
        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        bitmap.recycle();
        mediaMessage.thumbData = BitmapUtils.bmpToByteArray(sendBitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "";
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        MyApplication.iwxapi.sendReq(req);

    }

    private void showInviteEmail(final String speakerId, final String commentId, final String type) {
        new LDialog.Builder(getFragmentManager())
                .setLayoutId(R.layout.dialog_invite_sms)
                .setWidth(DxPxUtils.dip2px(this, 270.0f))
                .setGravity(Gravity.CENTER)
                .setDialogAnimationRes(R.style.CenterDialogAnimation)
                .setCancelableOutside(false)
                .addOnClick(R.id.tv_cancel, R.id.tv_ok)
                .setOnBindView(new OnBindView() {
                    @Override
                    public void bindView(ViewHelper helper, LDialog dialog) {
                        helper.setText(R.id.tv_title, R.string.input_guest_email);
                    }
                })
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {

                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;
                            case R.id.tv_ok:
                                EditText et_input_cellphone = helper.getView(R.id.et_input_cellphone);
                                String email = et_input_cellphone.getText().toString();

                                if (TextUtils.isEmpty(email)) {
                                    TosUtil.show(getString(R.string.please_input_eamil));
                                    return;
                                }
                                if (!CompareRex.checkEmail(et_input_cellphone.getText().toString())) {
                                    TosUtil.show(getString(R.string.email_format_error));
                                    return;
                                }

                                bindEmail(email, speakerId, commentId, type);
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    private void bindEmail(String email, final String speakerId, final String commentId, final String type) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.BIND_SPEAKER_EMIAL)
                .addParams("speakerId", speakerId)
                .addParams("email", email)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_invite));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            sendEmailInvite(speakerId, commentId, type);
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    private void showInviteSms(final String speakerId, final String commentId, final String type) {
        new LDialog.Builder(getFragmentManager())
                .setLayoutId(R.layout.dialog_invite_sms)
                .setWidth(DxPxUtils.dip2px(this, 270.0f))
                .setGravity(Gravity.CENTER)
                .setDialogAnimationRes(R.style.CenterDialogAnimation)
                .setCancelableOutside(false)
                .addOnClick(R.id.tv_cancel, R.id.tv_ok)
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {

                        dialog.dismiss();
                        if (view.getId() == R.id.tv_ok) {
                            EditText et_input_cellphone = helper.getView(R.id.et_input_cellphone);
                            String cellphone = et_input_cellphone.getText().toString();

                            if (TextUtils.isEmpty(cellphone)) {
                                TosUtil.show(getString(R.string.input_phone));
                                return;
                            }
                            if (!CompareRex.isCellPhone(et_input_cellphone.getText().toString())) {
                                TosUtil.show(getString(R.string.phone_format_error));
                                return;
                            }

                            bindcellPhone(cellphone, speakerId, commentId, type);
                        }
                    }
                })
                .build()
                .show();
    }

    private void sendEmailInvite(String speakerId, String commentId, String type) {
        sendSmsInvite(speakerId, commentId, type);
    }

    private void bindcellPhone(String cellPhone, final String speakerId, final String commentId, final String type) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.BIND_SPEAKER_CELLPHONE)
                .addParams("cellPhone", cellPhone)
                .addParams("speakerId", speakerId)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_invite));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            sendSmsInvite(speakerId, commentId, type);
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    private void sendSmsInvite(String speakerId, String commentId, String type) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.SEND_INVITE_GUEST)
                .addParams("speakerId", speakerId)
                .addParams("commentId", commentId)
                .addParams("type", type)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_invite));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            TosUtil.show(getString(R.string.success_invite));
                        } else {
                            TosUtil.show(getString(R.string.error_invite));
                        }
                    }
                });
    }

    private void setTextViewLeftImg(TextView textView, int imgId) {
        Drawable drawable = getResources().getDrawable(imgId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    private void initRecyclerView(ArrayList<CommentEntity> commentEntities) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commentAdapter = new CommentAdapter(commentEntities);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(this);
        commentAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.iv_back, R.id.iv_more, R.id.tv_send, R.id.iv_small_more})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send: //发送评论
                if (exhibitorFlag == 1) {
                    sentExhibitorComment();
                } else {
                    sendComment();
                }
                break;
            case R.id.iv_small_more:
                notReplyAndTop();
                break;
            case R.id.iv_more:
                notReplyAndTop();
                break;
        }
    }

    private void notReplyAndTop() {
        if (comment == null) {
            return;
        }
        new LDialog.Builder(getFragmentManager())
                .setLayoutId(R.layout.dialog_dynamic_detail_bottom)
                .setGravity(Gravity.BOTTOM)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setDialogAnimationRes(R.style.BottomDialogAnimation)
                .setCancelableOutside(true)
                .setOnBindView(new OnBindView() {
                    @Override
                    public void bindView(ViewHelper helper, LDialog dialog) {
                        if (comment.getStatus() == 0) {
                            helper.setText(R.id.tv_reply, R.string.cancel_shiled);
                        } else {
                            helper.setText(R.id.tv_reply, R.string.shiled1);
                        }

                        if (comment.getPriority() == 1) {
                            helper.setText(R.id.tv_shield, R.string.cancel_top);
                        } else {
                            helper.setText(R.id.tv_shield, R.string.top);
                        }
                    }
                })
                .addOnClick(R.id.tv_reply, R.id.tv_shield, R.id.tv_cancel)
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                        switch (view.getId()) {
                            case R.id.tv_reply:
                                if (comment.getStatus() == 1) {
                                    OkHttpUtil.Post(getApplication())
                                            .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                            .addParams("eventId", String.valueOf(eventId))
                                            .addParams("commentId", String.valueOf(comment.getCommentId()))
                                            .addParams("status", "0")
                                            .build()
                                            .execute(new Callback<String>() {

                                                @Override
                                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                                    return response.body().string();
                                                }

                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    TosUtil.show(getString(R.string.send_error));
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if (response.contains("\"retStatus\":200")) {
                                                        EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                        splRefresh.setRefreshing(true);
                                                        refreshData();
                                                    } else {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }
                                                }
                                            });
                                } else {
                                    OkHttpUtil.Post(getApplication())
                                            .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                            .addParams("eventId", String.valueOf(comment.getEventId()))
                                            .addParams("commentId", String.valueOf(comment.getCommentId()))
                                            .addParams("status", "1")
                                            .build()
                                            .execute(new Callback<String>() {

                                                @Override
                                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                                    return response.body().string();
                                                }

                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    TosUtil.show(getString(R.string.send_error));
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if (response.contains("\"retStatus\":200")) {
                                                        EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                        splRefresh.setRefreshing(true);
                                                        refreshData();
                                                    } else {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }
                                                }
                                            });
                                }

                                dialog.dismiss();
                                break;
                            case R.id.tv_shield:
                                if (comment.getPriority() == 0) {
                                    OkHttpUtil.Post(getApplication())
                                            .url(Constants.NEW_URL + Constants.PRIORITY_COMMENT)
                                            .addParams("eventId", String.valueOf(comment.getEventId()))
                                            .addParams("commentId", String.valueOf(comment.getCommentId()))
                                            .addParams("priority", "1")
                                            .build()
                                            .execute(new Callback<String>() {

                                                @Override
                                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                                    return response.body().string();
                                                }

                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    TosUtil.show(getString(R.string.send_error));
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if (response.contains("\"retStatus\":200")) {
                                                        EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                        splRefresh.setRefreshing(true);
                                                        refreshData();
                                                    } else {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(DynamicDetailActivity.this, "bb", Toast.LENGTH_SHORT).show();
                                    OkHttpUtil.Post(getApplication())
                                            .url(Constants.NEW_URL + Constants.PRIORITY_COMMENT)
                                            .addParams("eventId", String.valueOf(comment.getEventId()))
                                            .addParams("commentId", String.valueOf(comment.getCommentId()))
                                            .addParams("priority", "0")
                                            .build()
                                            .execute(new Callback<String>() {

                                                @Override
                                                public String parseNetworkResponse(Response response, int id) throws Exception {
                                                    return response.body().string();
                                                }

                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    TosUtil.show(getString(R.string.send_error));
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if (response.contains("\"retStatus\":200")) {
                                                        EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                        splRefresh.setRefreshing(true);
                                                        refreshData();
                                                    } else {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }
                                                }
                                            });
                                }
                                dialog.dismiss();
                                break;
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .build()
                .show();
    }


    private void showLoading() {
        if (lDialog == null) {
            lDialog = new LDialog.Builder(getFragmentManager())
                    .setLayoutId(R.layout.view_loading)
                    .setWidth(DxPxUtils.dip2px(this, 150))
                    .setHeight(DxPxUtils.dip2px(this, 150))
                    .build()
                    .show();
        } else {
            lDialog.show();
        }
    }

    private void hideLoading() {
        lDialog.dismiss();
    }

    /**
     * 发送评论
     */
    private void sendComment() {
        String comment = et_send_content.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            TosUtil.show(getString(R.string.send_message_not_null));
            return;
        }
        showLoading();
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.ORGANIZER_REPLY_COMMENT)
                .addParams("eventId", String.valueOf(eventId))
                .addParams("commentId", String.valueOf(commentId))
                .addParams("replyCommentId", String.valueOf(replyCommentId))
                .addParams("commentText", comment)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        showOrHideInput();
                        hideLoading();
                        TosUtil.show(getString(R.string.send_error));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        if (response.contains("\"retStatus\":200")) {
                            InputMethodUtil.close(DynamicDetailActivity.this, et_send_content);
                            et_send_content.setText("");
                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                            splRefresh.setRefreshing(true);
                            refreshData();
                        } else {
                            TosUtil.show(getString(R.string.send_error));
                        }
                    }
                });
    }

    /**
     * 展商发送评论
     */
    private void sentExhibitorComment() {
        String comment = et_send_content.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            TosUtil.show(getString(R.string.send_message_not_null));
            return;
        }

        showLoading();
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.EXHIBITOR_REPLY)
                .addParams("eventId", String.valueOf(eventId))
                .addParams("exhibitorId", String.valueOf(exhibitorId))
                .addParams("commentText", comment)
                .addParams("commentId", String.valueOf(commentId))
                .addParams("replyCommentId", String.valueOf(replyCommentId))
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        showOrHideInput();
                        hideLoading();
                        TosUtil.show(getString(R.string.send_error));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        if (response.contains("\"retStatus\":200")) {
                            InputMethodUtil.close(DynamicDetailActivity.this, et_send_content);
                            et_send_content.setText("");
                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                            EventBus.getDefault().post(new MsgEvent("refresh_exbition_dynamic"));
                            splRefresh.setRefreshing(true);
                            refreshData();
                        } else {
                            TosUtil.show(getString(R.string.send_error));
                        }
                    }
                });
    }

    private void showOrHideInput() {
        InputMethodUtil.showOrHide(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArrayList<CommentEntity> commentEntities = (ArrayList<CommentEntity>) adapter.getData();
        final DynamicListData.CommentList commentList = commentEntities.get(position).getCommentList();
        replyCommentId = commentList.getCommentId();
        if (flag == 1) {
            exhibitorFlag = 1;
        } else {
            exhibitorFlag = 0;
        }
        showOrHideInput();
        et_send_content.setHint(getString(R.string.reply1) + commentList.getUser().getShowName() + "：");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        @SuppressWarnings("unchecked")
        ArrayList<CommentEntity> commentEntities = (ArrayList<CommentEntity>) adapter.getData();
        final DynamicListData.CommentList commentList = commentEntities.get(position).getCommentList();
        replyCommentId = commentList.getCommentId();
        if (flag == 1) {
            exhibitorFlag = 1;
        } else {
            exhibitorFlag = 0;
        }
        if (commentList.getUser() != null && commentList.getUser().getShowName() != null) {
            new LDialog.Builder(getFragmentManager())
                    .setLayoutId(R.layout.dialog_dynamic_detail_bottom)
                    .setGravity(Gravity.BOTTOM)
                    .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                    .setDialogAnimationRes(R.style.BottomDialogAnimation)
                    .setCancelableOutside(true)
                    .addOnClick(R.id.tv_reply, R.id.tv_shield, R.id.tv_cancel)
                    .setOnBindView(new OnBindView() {
                        @Override
                        public void bindView(ViewHelper helper, LDialog dialog) {
                            if (commentList.getStatus() == 0) {
                                helper.setText(R.id.tv_shield, R.string.cancel_shiled);
                            } else {
                                helper.setText(R.id.tv_shield, R.string.shiled1);
                            }
                        }
                    })
                    .setOnViewClick(new OnViewClick() {
                        @Override
                        public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                            switch (view.getId()) {
                                case R.id.tv_reply:
                                    dialog.dismiss();
                                    showOrHideInput();
                                    et_send_content.setHint(getString(R.string.reply1) + commentList.getUser().getShowName() + "：");
                                    break;
                                case R.id.tv_shield:
                                    if (commentList.getStatus() == 1) {
                                        OkHttpUtil.Post(getApplication())
                                                .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                                .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                .addParams("status", "0")
                                                .build()
                                                .execute(new Callback<String>() {

                                                    @Override
                                                    public String parseNetworkResponse(Response response, int id) throws Exception {
                                                        return response.body().string();
                                                    }

                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        if (response.contains("\"retStatus\":200")) {
                                                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                            splRefresh.setRefreshing(true);
                                                            refreshData();
                                                        } else {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }
                                                    }
                                                });
                                    } else {
                                        OkHttpUtil.Post(getApplication())
                                                .url(Constants.NEW_URL + Constants.SHIELD_COMMENT)
                                                .addParams("eventId", String.valueOf(commentList.getEventId()))
                                                .addParams("commentId", String.valueOf(commentList.getCommentId()))
                                                .addParams("status", "1")
                                                .build()
                                                .execute(new Callback<String>() {

                                                    @Override
                                                    public String parseNetworkResponse(Response response, int id) throws Exception {
                                                        return response.body().string();
                                                    }

                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        TosUtil.show(getString(R.string.send_error));
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        if (response.contains("\"retStatus\":200")) {
                                                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                                            splRefresh.setRefreshing(true);
                                                            refreshData();
                                                        } else {
                                                            TosUtil.show(getString(R.string.send_error));
                                                        }
                                                    }
                                                });
                                    }
                                    dialog.dismiss();
                                    break;
                                case R.id.tv_cancel:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    })
                    .build()
                    .show();
        }
    }

    @Override
    public void onResize(int w, int h, int oldw, int oldh) {
        if (oldh == 0)
            return;

        if (h > oldh) {
            et_send_content.setHint(getString(R.string.et_send_hint));
        }
    }


    static class MyHandler extends Handler {
        private WeakReference<DynamicDetailActivity> detailActivityWeakReference;

        public MyHandler(DynamicDetailActivity dynamicDetailActivity) {
            detailActivityWeakReference = new WeakReference<>(dynamicDetailActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            DynamicDetailActivity dynamicDetailActivity = detailActivityWeakReference.get();
            if (msg.what == 666) {
                dynamicDetailActivity.sendWeichatInvite(dynamicDetailActivity.eventName, (Bitmap) msg.obj);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Util.isOnMainThread() && !isFinishing() && !isDestroyed()) {
                Glide.with(this).pauseRequests();
            }
        } else {
            if (Util.isOnMainThread() && !isFinishing()) {
                Glide.with(this).pauseRequests();
            }
        }

    }
}
