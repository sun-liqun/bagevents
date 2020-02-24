package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.UpdateVersionData;
import com.bagevent.new_home.new_interface.UpdateVersionInterface;
import com.bagevent.new_home.new_interface.impl.UpdateVersionImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateVersionListener;
import com.bagevent.new_home.new_interface.new_view.UpdateVersionView;

/**
 * Created by zwj on 2016/12/7.
 */
public class UpDateVersionPresenter {
    private UpdateVersionInterface updateVersionInterface;
    private UpdateVersionView updateVersionView;

    public UpDateVersionPresenter(UpdateVersionView updateVersionView) {
        this.updateVersionInterface = new UpdateVersionImpl();
        this.updateVersionView = updateVersionView;
    }

    public void version() {
        updateVersionInterface.updateVersion(updateVersionView.mContext(), new OnUpdateVersionListener() {
            @Override
            public void getVersionInfo(UpdateVersionData response) {
                updateVersionView.versionInfo(response);
            }

            @Override
            public void getVersionInfoFailed(String errInfo) {
                updateVersionView.errVersionInfo(errInfo);
            }
        });
    }
}
