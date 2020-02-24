package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.ChatData;

/**
 * Created by ZWJ on 2017/12/7 0007.
 */

public interface AttendeeChatView {
    Context mContext();
    String userId();
    int eventId();
    String attendeeId();

    void showAttendeeSingleChatSuccess(ChatData response);
    void showAttendeeSingleChatFailed(String errInfo);

}
