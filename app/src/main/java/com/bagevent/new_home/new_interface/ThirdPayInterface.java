package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnThirdPayListener;

/**
 * Created by zwj on 2017/10/23.
 */

public interface ThirdPayInterface {
    void getThirdPay(Context mContext, String userId,String orderNum, String payway, OnThirdPayListener listener);
}
