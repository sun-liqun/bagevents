package com.bagevent.home.home_interface.impls;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.home.data.ExportData;
import com.bagevent.home.home_interface.ExportCollectionInterface;
import com.bagevent.home.home_interface.OnExportCollection;
import com.bagevent.home.home_interface.callback.ExportCalback;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/14.
 */
public class ExportCollectionImpls implements ExportCollectionInterface {
    @Override
    public void exportCollect(Context mContext, String collectionId, String eventId, String email, final OnExportCollection listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.EXPORT_COLLECT + collectionId + "?eventId=" + eventId + "&userEmail=" + email + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new ExportCalback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {

                    }

                    @Override
                    public void onResponse(ExportData response,int id) {
                        if(response.getRespObject() == null && response.getRetStatus() == 200 ){
                            listener.exportSuccess();
                        }else {
                            listener.exportFailed();
                        }
                    }
                });
    }
}
