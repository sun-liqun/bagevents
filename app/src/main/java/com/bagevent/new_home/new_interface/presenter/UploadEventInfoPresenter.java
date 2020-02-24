package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UploadEventInfoInterface;
import com.bagevent.new_home.new_interface.impl.UploadEventInfoImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUploadEventInfoListener;
import com.bagevent.new_home.new_interface.new_view.UploadEventInfoView;

/**
 * Created by zwj on 2016/9/21.
 */
public class UploadEventInfoPresenter {
    private UploadEventInfoInterface uploadEventLogoInterface;
    private UploadEventInfoView uploadEventLogoView;

    public UploadEventInfoPresenter(UploadEventInfoView uploadEventLogoView) {
        this.uploadEventLogoInterface = new UploadEventInfoImpl();
        this.uploadEventLogoView = uploadEventLogoView;
    }

    public void eventLogo() {
        uploadEventLogoInterface.uploadLogo(uploadEventLogoView.mContext(),uploadEventLogoView.eventId(), uploadEventLogoView.userIds(), uploadEventLogoView.textName(), uploadEventLogoView.textValue(), new OnUploadEventInfoListener() {
            @Override
            public void upLoadLogoSuccess() {
                uploadEventLogoView.upEventLogoSuccess();
            }

            @Override
            public void upLoadLogoFailed(String errInfo) {
                uploadEventLogoView.upEventLogoFailed(errInfo);
            }
        });
    }
}
