package com.bagevent.home.home_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.home.data.ExportData;
import com.bagevent.home.home_interface.OnPostCollectionInfo;
import com.bagevent.home.home_interface.PostCollectionInfoInterface;
import com.bagevent.home.home_interface.callback.ExportCalback;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2016/7/14.
 */
public class PostCollectionInfoImpls implements PostCollectionInfoInterface {
    @Override
    public void postCollectionInfo(Context mContext, String collectionId, String barcode, String checkinTime, final OnPostCollectionInfo listener) {
        Log.e("PostCollectionInfoImpls","timeTmp="+checkinTime);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.POST_COLLECT_INFO + collectionId + "?barcode=" + barcode + "&checkInTime=" + checkinTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
           //     .url(Constants.URL + Constants.POST_COLLECT_INFO + collectionId + "?barcode=1111111"  + "&checkInTime=" + checkinTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new ExportCalback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        Log.e("PostCollectionInfoImpls",e.getMessage());
                      //  call.cancel();
                    }

                    @Override
                    public void onResponse(ExportData response,int id) {
//                        if((response.getRetStatus() == 200 && response.getRespObject() == null ) || (response.getRetStatus() == 300 ) || response.getRetStatus() == -1004 || response.getRetStatus() == -1 || response.getRetStatus() == -1005) {
//                            listener.postSuccess(response);
//                        }else {
//                            listener.postFailed();
//                        }
//
                        if((response.getRetStatus() == 200 ) || (response.getRetStatus() == 300 ) || response.getRetStatus() == -1004 || response.getRetStatus() == -1 || response.getRetStatus() == -1005) {
                            listener.postSuccess(response);
                        }else {
                            listener.postFailed();
                        }
                    }
                });

    }
}
