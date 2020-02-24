package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.SponsorData;

/**
 * Created by zwj on 2016/9/18.
 */
public interface OnGetSponsorListener {
    void getSuccess(SponsorData reponse);
    void getFailed(String errInfo);
}
