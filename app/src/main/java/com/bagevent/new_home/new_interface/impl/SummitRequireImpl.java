package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SummitRequireInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnSummitRequireListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/13.
 */
public class SummitRequireImpl implements SummitRequireInterface {
    @Override
    public void summitRequire(Context mContext, String userId, String cellPhone, String smsCode, String demandStartTime, String demandEndTime, String cityName, String demandNumOfPerson, String demandOtherRequire, String type, final OnSummitRequireListener listener) {
        String url= Constants.URL + Constants.SUMMIT_REQUIRE_CONTENT;
//        Log.e("smscode-->", smsCode +"F");
        if(TextUtils.isEmpty(smsCode)) {
            OkHttpUtil.okPost(mContext)
                    .url(url)
                    .addParams("userId",userId)
                    .addParams("demandStartTime",demandStartTime)
                    .addParams("demandEndTime",demandEndTime)
                    .addParams("cityName",cityName)
                    .addParams("demandNumOfPerson",demandNumOfPerson)
                    .addParams("demandOtherRequire",demandOtherRequire)
                    .addParams("type",type)
                    .addParams("access_token","ipad")
                    .addParams("access_secret","ipad_secret")
                    .build()
                    .execute(new StringDataCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(StringData response, int id) {
                            if(response.getRetStatus() == 200) {
                                listener.summitRequireSuccess();

                            }else {
                                listener.summitRequireFailed(response.getRespObject());
                            }
                        }
                    });
        }else {
           // Log.e("fdf",cellPhone);
           // Log.e("fdf",smsCode);
            OkHttpUtil.okPost(mContext)
                    .url(url)
                    .addParams("cellphone",cellPhone)
                    .addParams("smsCode",smsCode)
                    .addParams("demandStartTime",demandStartTime)
                    .addParams("demandEndTime",demandEndTime)
                    .addParams("cityName",cityName)
                    .addParams("demandNumOfPerson",demandNumOfPerson)
                    .addParams("demandOtherRequire",demandOtherRequire)
                    .addParams("type",type)
                    .addParams("access_token","ipad")
                    .addParams("access_secret","ipad_secret")
                    .build()
                    .execute(new StringDataCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(StringData response, int id) {
                            if(response.getRetStatus() == 200) {
                                listener.summitRequireSuccess();

                            }else {
                                listener.summitRequireFailed(response.getRespObject());
                            }
                        }
                    });
        }


    }
}
