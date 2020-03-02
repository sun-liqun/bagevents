package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.AddFormResponse;

/**
 * Created by zwj on 2016/9/23.
 */
public interface GetLiveView {
    Context mContext();

    int eventId();


    void eventGetLiveUrlSuccess(String url);
    void eventGetLiveUrlFailed(String errInfo);


}
