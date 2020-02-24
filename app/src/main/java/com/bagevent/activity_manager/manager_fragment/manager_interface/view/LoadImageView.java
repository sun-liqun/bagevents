package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by zwj on 2016/8/8.
 */
public interface LoadImageView {
    Context mContext();

    String imgUrl();

    void saveImageToLocal(Bitmap bitmap);

}
