package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.WXUserInfoData;
import com.bagevent.new_home.new_interface.WXUserInfoInterface;
import com.bagevent.new_home.new_interface.impl.WXUserInfoImpl;
import com.bagevent.new_home.new_interface.listenerInterface.WXUserInfoListener;
import com.bagevent.new_home.new_interface.new_view.WXUserInfoView;

/**
 * Created by zwj on 2017/1/3.
 */
public class WXUserInfoPresenter {
    private WXUserInfoInterface wxUserInfo;
    private WXUserInfoView wxUserInfoView;

    public WXUserInfoPresenter(WXUserInfoView wxUserInfoView) {
        this.wxUserInfo = new WXUserInfoImpl();
        this.wxUserInfoView = wxUserInfoView;
    }

    public void getWXUserInfo() {
        wxUserInfo.getWXUserInfo(wxUserInfoView.accessToken(), wxUserInfoView.openId(), new WXUserInfoListener() {
            @Override
            public void userInfo(WXUserInfoData response) {
                wxUserInfoView.getUserInfoOK(response);
            }

            @Override
            public void userInfoErr() {
                wxUserInfoView.getUserInfoErr();
            }
        });
    }
}
