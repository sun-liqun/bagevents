package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.CheckAccessTokenData;
import com.bagevent.new_home.new_interface.CheckAccessTokenInterface;
import com.bagevent.new_home.new_interface.impl.CheckAccessTokenImpl;
import com.bagevent.new_home.new_interface.listenerInterface.CheckAccessTokenlistener;
import com.bagevent.new_home.new_interface.new_view.CheckAccessTokenView;

/**
 * Created by zwj on 2017/1/3.
 */
public class CheckAccessTokenPresenter {
    private CheckAccessTokenInterface checkAccessToken;
    private CheckAccessTokenView checkAccessTokenView;

    public CheckAccessTokenPresenter(CheckAccessTokenView checkAccessTokenView) {
        this.checkAccessToken = new CheckAccessTokenImpl();
        this.checkAccessTokenView = checkAccessTokenView;
    }

    public void checkAccessToken() {
        checkAccessToken.checkAccessToken(checkAccessTokenView.accessToken(), checkAccessTokenView.openId(), new CheckAccessTokenlistener() {
            @Override
            public void accessTokenEnable(CheckAccessTokenData response) {
                checkAccessTokenView.accessTokenEnable(response);
            }

            @Override
            public void accessTokenUnable() {
                checkAccessTokenView.accessTokenUnable();
            }
        });
    }
}
