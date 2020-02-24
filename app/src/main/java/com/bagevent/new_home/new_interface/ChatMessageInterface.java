package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetChatMessageListener;

/**
 * Created by ZWJ on 2017/11/24 0024.
 */

public interface ChatMessageInterface {
    void chatMessage(Context mContext, String userId, String sendToken, int eventId, int page, OnGetChatMessageListener listener);
    void upFetchChatMessage(Context mContext,String userId,String sendToken,int eventId,String sendTime,OnGetChatMessageListener listener);

}
