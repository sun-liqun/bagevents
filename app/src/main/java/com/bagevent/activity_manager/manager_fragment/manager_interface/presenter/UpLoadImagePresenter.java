package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.UpLoadImageInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.UpLoadImageImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnUpLoadImageListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;

/**
 * Created by zwj on 2016/8/16.
 */
public class UpLoadImagePresenter {
    private UpLoadImageInterface upLoadImage;

    private UpLoadImageView upLoadImageView;

    public UpLoadImagePresenter(UpLoadImageView upLoadImageView) {
        this.upLoadImageView = upLoadImageView;
        this.upLoadImage = new UpLoadImageImpls();
    }

    public void upLoadImageFile() {
        upLoadImage.upload(upLoadImageView.mContext(),upLoadImageView.upLoadFile(), upLoadImageView.userId(), new OnUpLoadImageListener() {
            @Override
            public void upLoadSuccess(String success) {
                upLoadImageView.uploadSuccess(success);
            }

            @Override
            public void upLoadFailed(String failed) {
                upLoadImageView.uploadFailed(failed);
            }
        });
    }
}
