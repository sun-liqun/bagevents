package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import java.io.File;

/**
 * Created by zwj on 2017/9/7.
 */

public interface OnGetAttendeeJsonFile {
    void onGetAttendeeJsonFile(File attendeeFile);
    void onGetAttendeeJsonFileFailed(String errInfo);
}
