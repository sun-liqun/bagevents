package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.AddFormResponse;

/**
 * Created by zwj on 2016/9/23.
 */
public interface AddFormView {
    Context mContext();

    String detailEventId();
    String userId();
    String filedName();
    String showName();
    String items();
    int filedType();

    void eventAddFormSuccess(AddFormResponse response);
    void eventAddFormFailed(String errInfo);

    void eventAddCustomFormSuccess(AddFormResponse  response);
    void eventAddCustomFormFailed(String errInfo);

    void eventAddMultiFormSuccess(AddFormResponse response);
    void eventAddMultiFormFailed(String errInfo);
}
