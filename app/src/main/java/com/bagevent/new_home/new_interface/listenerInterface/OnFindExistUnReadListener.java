package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.FindUnreadData;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface OnFindExistUnReadListener {
    void findExistUnRead(FindUnreadData response);
    void findExistUnReadFailed(String errInfo);
}
