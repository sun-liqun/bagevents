package com.bagevent.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.ImageGalleryActivity;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.KeyUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
public class HorizontalScrollImag extends HorizontalScrollView {
    LinearLayout ll_container;

    public HorizontalScrollImag(Context context, AttributeSet attrs) {
        super(context, attrs);
        ll_container = new LinearLayout(context);
        ll_container.setOrientation(LinearLayout.HORIZONTAL);
        addView(ll_container);
    }

    public void setImgs(final String[] imgs) {
        ll_container.removeAllViews();
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String url = imgs[i];
            if (url.startsWith("//")) {
                url = "https:" + url;
            } else if (url.startsWith("/")) {
//                url = "https://www.bagevent.com" + url;
                url = "https://img.bagevent.com" + url;
            }
//            Glide.with(getContext()).load(url).placeholder(R.drawable.img_loading).error(R.drawable.img_failed).into(imageView);
            RequestOptions options=new RequestOptions()
                    .placeholder(R.drawable.img_loading)
                    .error(R.drawable.img_failed);
            Glide.with(getContext()).load(url).apply(options).into(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DxPxUtils.dip2px(getContext(), 85), DxPxUtils.dip2px(getContext(), 85));
            layoutParams.rightMargin = DxPxUtils.dip2px(getContext(), 2);
            imageView.setTag(i);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Intent intent = new Intent(getContext(), ImageGalleryActivity.class);
                    intent.putExtra(KeyUtil.KEY_POSITION, position);
                    intent.putExtra(KeyUtil.KEY_IMAGE_URL, imgs);
                    getContext().startActivity(intent);
                }
            });
            ll_container.addView(imageView, layoutParams);
        }
        requestLayout();
    }


}
