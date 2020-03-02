package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/18.
 */
public interface SummitSponsorView {
    Context mContext();
    String userId();
    String organizerName();
    String contactPhone();
    String contactMail();
    String officialHomePage();
    String brief();
    String logoUrl();

    void summitSponsorSuccess();
    void summitSponsorFailed(String errInfo);
}
