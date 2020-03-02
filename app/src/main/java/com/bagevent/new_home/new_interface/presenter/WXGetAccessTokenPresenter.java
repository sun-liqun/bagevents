package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.WXAccessTokenData;
import com.bagevent.new_home.new_interface.WXGetAccessTokenInterface;
import com.bagevent.new_home.new_interface.impl.WXGetAccessTokenImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnWXGetAccessTokenListener;
import com.bagevent.new_home.new_interface.new_view.WXGetAccessTokenView;

/**
/**
 * Created by zwj on 2016/12/30.
 */
public class WXGetAccessTokenPresenter {

    private WXGetAccessTokenInterface wxGetAccessToken;
    private WXGetAccessTokenView wxGetAccessTokenView;

    public WXGetAccessTokenPresenter(WXGetAccessTokenView wxGetAccessTokenView) {
        this.wxGetAccessTokenView = wxGetAccessTokenView;
        this.wxGetAccessToken = new WXGetAccessTokenImpl();
    }

    public void getAccessToken() {
        wxGetAccessToken.wxLogin(wxGetAccessTokenView.code(), wxGetAccessTokenView.appid(), wxGetAccessTokenView.secret(), new OnWXGetAccessTokenListener() {
            @Override
            public void getAccessTokenSuccess(WXAccessTokenData response) {
                wxGetAccessTokenView.GetAccessTokenOk(response);
            }

            @Override
            public void getAccessTokenFailed() {
                wxGetAccessTokenView.GetAccessTokenErr();
            }
        });
    }
}
