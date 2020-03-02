package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import android.graphics.Bitmap;

import com.bagevent.activity_manager.manager_fragment.manager_interface.LoadImageInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.LoadImageImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnLoadImageListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.LoadImageView;

/**
 * Created by zwj on 2016/8/8.
 */
public class LoadImagePresenter {
    private LoadImageInterface loadImage;
    private LoadImageView loadImageView;

    public LoadImagePresenter(LoadImageView loadImageView) {
        this.loadImageView = loadImageView;
        this.loadImage = new LoadImageImpls();
    }

    public void load() {
        loadImage.loadImage(loadImageView.mContext(),loadImageView.imgUrl(), new OnLoadImageListener() {
            @Override
            public void loadSuccess(Bitmap bitmap) {
                loadImageView.saveImageToLocal(bitmap);
            }
        });
    }
}
