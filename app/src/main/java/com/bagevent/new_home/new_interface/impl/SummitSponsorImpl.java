package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SummitSponsorInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnSummitSponsorListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/18.
 */
public class SummitSponsorImpl implements SummitSponsorInterface {

    @Override
    public void summitSponsor(final Context mContext, String userId, String orizanierName, String contactPhone, String contactMail, String officalHomePage, String brief, String logoUrl, final OnSummitSponsorListener listener) {
        String url = Constants.URL + Constants.ADD_ORGANIZER +"userId=" +userId +"&organizerName=" +orizanierName + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET;
        if(!TextUtils.isEmpty(contactPhone)) {
            url = url + "&contactPhone=" + contactPhone;
        }

        if(!TextUtils.isEmpty(contactMail)) {
            url = url + "&contactMail=" + contactMail;
        }

        if(!TextUtils.isEmpty(officalHomePage)) {
            url = url + "&officialHomePage=" + officalHomePage;
        }

        if(!TextUtils.isEmpty(brief)) {
            url = url + "&brief=" + brief;
        }

        if(!TextUtils.isEmpty(logoUrl)) {
            url = url + "&logoUrl=" + logoUrl;
        }
        OkHttpUtil.okPost(mContext)

                .url(url)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.summitSponsorSuccess();
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.summitSponsorFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });

    }
}
