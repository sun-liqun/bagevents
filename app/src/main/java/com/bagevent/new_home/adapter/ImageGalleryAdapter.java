package com.bagevent.new_home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/4
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ImageGalleryAdapter extends PagerAdapter {

    private Context context;
    private String[] urls;

    public ImageGalleryAdapter(Context context, String[] urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        if (urls == null || urls.length == 0)
            return 0;
        else
            return urls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        String url = urls[position];
        if (url.startsWith("//")) {
            url = "https:" + url;
        } else if (url.startsWith("/")) {
            url = "https://img.bagevent.com" + url;
//            url = "https://www.bagevent.com" + url;
        }
        Glide.with(context).load(url).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
