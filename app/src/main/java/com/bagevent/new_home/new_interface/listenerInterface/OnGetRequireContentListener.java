package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.RequireContentData;

/**
 * Created by zwj on 2016/9/12.
 */
public interface OnGetRequireContentListener {
    void showRequireContent(RequireContentData response);
    void showRequireContentFailed(String errInfo);
}
