package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.UserInfoData;
import com.bagevent.new_home.new_interface.GetUserInfoInterface;
import com.bagevent.new_home.new_interface.impl.GetUserInfoImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetUserInfoListener;
import com.bagevent.new_home.new_interface.new_view.GetUserInfoView;

/**
 * Created by zwj on 2016/10/11.
 */
public class GetUserInfoPresenter {
    private GetUserInfoInterface getUserInfoInterface;
    private GetUserInfoView getUserInfoView;

    public GetUserInfoPresenter(GetUserInfoView getUserInfoView) {
        this.getUserInfoInterface = new GetUserInfoImpl();
        this.getUserInfoView = getUserInfoView;
    }

    public void getUserInfo() {
        getUserInfoInterface.userInfoo(getUserInfoView.mContext(),getUserInfoView.userId(), new OnGetUserInfoListener() {
            @Override
            public void userInfoSuccess(UserInfoData response) {
                getUserInfoView.getUserInfoSuccess(response);
            }

            @Override
            public void userInfoFailed(String errInfo) {
                getUserInfoView.getUserInfoFailed(errInfo);
            }
        });
    }
}
