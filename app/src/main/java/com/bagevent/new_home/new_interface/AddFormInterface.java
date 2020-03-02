package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;

/**
 * Created by zwj on 2016/9/23.
 */
public interface AddFormInterface {
    void addForm(Context mContext, String eventId, String userId, String filedName, String showName , int filedType, OnAddFormListener listener);
    void addMutiForm(Context mContext,String eventId, String userId, String filedName,String showName,String items,int filedType, OnAddFormListener listener);
}
