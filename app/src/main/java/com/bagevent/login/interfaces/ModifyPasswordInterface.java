package com.bagevent.login.interfaces;

import android.content.Context;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public interface ModifyPasswordInterface {
    void modifyPassword(Context mContext,String phoneNum,String smsCode,String p1,String p2,OnModifyPwListener listener);
}
