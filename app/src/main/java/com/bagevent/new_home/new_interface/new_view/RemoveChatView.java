package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface RemoveChatView {
    Context mContext();
    String userId();
    int contactId();
    void removeChatSuccess();
    void removeChatFailed(String errInfo);
}
