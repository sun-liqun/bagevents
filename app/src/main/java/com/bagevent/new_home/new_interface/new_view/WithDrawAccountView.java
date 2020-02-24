package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.WithDrawAccountData;

/**
 * Created by WenJie on 2017/11/16.
 */

public interface WithDrawAccountView {
    Context mContext();
    String userId();
    void withDrawAccountSuccess(WithDrawAccountData response);
    void withDrawAccoountFailed(String errInfo);
}
