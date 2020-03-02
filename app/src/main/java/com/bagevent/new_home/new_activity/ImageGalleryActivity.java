package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bagevent.R;
import com.bagevent.new_home.adapter.ImageGalleryAdapter;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.KeyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
public class ImageGalleryActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.ll_points)
    LinearLayout ll_points;
    private int position;
    String[] urls=new String[]{};
    @Override
    protected void initUI(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_gallery);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        urls = intent.getStringArrayExtra(KeyUtil.KEY_IMAGE_URL);
        position = intent.getIntExtra(KeyUtil.KEY_POSITION, 0);
        viewPager.setAdapter(new ImageGalleryAdapter(this, urls));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setCurrentPoint(position);
            }
        });

        if (urls!=null&&urls.length > 1) {
            for (int i = 0; i < urls.length; i++) {
                View view = new View(this);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_check);
                } else {
                    view.setBackgroundResource(R.drawable.point_uncheck);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DxPxUtils.dip2px(this, 10), DxPxUtils.dip2px(this, 10));
                layoutParams.leftMargin = DxPxUtils.dip2px(this, 5);
                ll_points.addView(view, layoutParams);
            }
            viewPager.setCurrentItem(position);
        }

    }

    private void setCurrentPoint(int pos) {
        ll_points.getChildAt(position).setBackgroundResource(R.drawable.point_uncheck);
        ll_points.getChildAt(pos).setBackgroundResource(R.drawable.point_check);
        position = pos;
    }
}
