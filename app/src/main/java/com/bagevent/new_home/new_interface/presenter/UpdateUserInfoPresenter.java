package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UpdateUserInfoInterface;
import com.bagevent.new_home.new_interface.impl.UpdateUserInfoImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateUserInfoListener;
import com.bagevent.new_home.new_interface.new_view.UpdateUserInfoView;

/**
 * Created by zwj on 2016/10/13.
 */
public class UpdateUserInfoPresenter {
    private UpdateUserInfoInterface updateUserInfoInterface;
    private UpdateUserInfoView updateUserInfoView;

    public UpdateUserInfoPresenter(UpdateUserInfoView updateUserInfoView) {
        this.updateUserInfoInterface = new UpdateUserInfoImpl();
        this.updateUserInfoView = updateUserInfoView;
    }

    public void updateUserInfo() {
        updateUserInfoInterface.updateUserInfo(updateUserInfoView.mContext(), updateUserInfoView.userId(), updateUserInfoView.formName(), updateUserInfoView.formValue(), new OnUpdateUserInfoListener() {
            @Override
            public void userInfoSuccess() {
                updateUserInfoView.updateUserInfoSuccess();
            }

            @Override
            public void userInfoFailed(String errInfo) {
                updateUserInfoView.updateUserInfoFailed(errInfo);
            }
        });
    }
}
