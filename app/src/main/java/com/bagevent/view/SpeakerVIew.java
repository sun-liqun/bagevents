package com.bagevent.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.util.CircleTransform;
import com.bagevent.util.DxPxUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/3
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class SpeakerVIew extends LinearLayout {
    public SpeakerVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    RequestOptions options=new RequestOptions().transform(new CircleTransform());
    public void setSpeakers(ArrayList<DynamicListData.Speaker> speakers) {
        removeAllViews();
        for (int i = 0; i < speakers.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams imagParams = new LinearLayout.LayoutParams(DxPxUtils.dip2px(getContext(), 23), DxPxUtils.dip2px(getContext(), 23));
            imagParams.rightMargin = DxPxUtils.dip2px(getContext(), 10);
            linearLayout.addView(imageView, imagParams);
            String url = speakers.get(i).getAvatar();
            if (url.startsWith("//")) {
                url = "https:" + url;
            } else if (url.startsWith("/")) {
                url = "http://tanlangdark.mynatapp.cc" + url;
            }
            Glide.with(getContext()).load(url).apply(options).into(imageView);

            TextView textView = new TextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText(Html.fromHtml(String.format("<font color='#202020'>@%s</font>&nbsp;&nbsp;<font color='#ff9000'>嘉宾已入驻</font>", speakers.get(i).getSpeakerName())));
            linearLayout.addView(textView);
            removeAllViews();

            if (i > 1) {
                LinearLayout.LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
                layoutParams.topMargin = DxPxUtils.dip2px(getContext(), 5);
            }
            addView(linearLayout);
        }
        requestLayout();
    }
}
