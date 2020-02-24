package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSummitSponsorListener;

/**
 * Created by zwj on 2016/9/18.
 */
public interface SummitSponsorInterface {
    void summitSponsor(Context mContext, String userId, String orzanierName, String contactPhone, String contactMail, String officalHomePage, String brief, String logUrl, OnSummitSponsorListener listener);
}
