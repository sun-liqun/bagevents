package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/13.
 */
public interface SummitRequireView {
    Context mContext();
    String userId();
    String cellPhone();
    String randomCode();
    String demandStartTime();
    String demandEndTime();
    String cityName();
    String demandOfPerson();
    String demandOtherRequire();
    String type();

    void summitRequireSuccess();
    void summitRequireFailed(String errInfo);
}
