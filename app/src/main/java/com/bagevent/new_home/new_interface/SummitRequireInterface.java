package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSummitRequireListener;

/**
 * Created by zwj on 2016/9/12.
 */
public interface SummitRequireInterface {
    void summitRequire(Context mContext, String userId, String cellPhone, String smsCode, String demandStartTime, String demandEndTime, String cityName, String demandNumOfPerson, String demandOtherRequire, String type, OnSummitRequireListener listener);
}
