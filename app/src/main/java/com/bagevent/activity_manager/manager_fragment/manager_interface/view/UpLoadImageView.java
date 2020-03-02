package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import java.io.File;

/**
 * Created by zwj on 2016/8/16.
 */
public interface UpLoadImageView {
    Context mContext();
    File upLoadFile();
    String userId();

    void uploadSuccess(String response);
    void uploadFailed(String response);
}
