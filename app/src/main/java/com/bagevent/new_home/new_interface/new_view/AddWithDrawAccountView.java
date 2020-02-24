package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public interface AddWithDrawAccountView {
    Context mContext();
    String userId();
    int type();
    String accountName();
    String account();
    String bankName();

    void showAddAccountSucess(String response);
    void showAddAccountFailed(String errInfo);
}
