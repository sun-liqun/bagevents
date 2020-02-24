package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.SponsorData;

/**
 * Created by zwj on 2016/9/18.
 */
public interface GetSponsorView {
    Context mContext();

    String userId();

    void getSponsor(SponsorData response);
    void getSponsorFailed(String errInfo);
}
