package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnChatListListener;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public interface ChatListInterface {
    void chat(Context mContext, String userId, OnChatListListener listListener);
    void singleChat(Context mContext,String userId,int contactId,OnChatListListener listListener);
    void attendeeSingleChat(Context mContext,String userId,int eventId,int attendeeId,OnChatListListener listListener);
}
