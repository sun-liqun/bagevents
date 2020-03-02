package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.RequireContentData;

/**
 * Created by zwj on 2016/9/12.
 */
public interface RequireContentView {
    Context mContext();

    String userId();

    void showRequireContent(RequireContentData response);
    void showRequireContentFailed(String errInfo);
}
