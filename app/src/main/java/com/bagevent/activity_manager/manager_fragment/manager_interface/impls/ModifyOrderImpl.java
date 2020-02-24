package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/10/30.
 */

public class ModifyOrderImpl implements ModifyOrderInterface {
    @Override
    public void modifyOrderInfo(Context mContext, int eventId, String orderNumber, String buerName, String buerEmail, String buyerCellphone, String areaCode, final OnModifyOrderListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.MODIFY_ORDER + eventId + "/" + orderNumber)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("buyerName",buerName)
                .addParams("buyerEmail",buerEmail)
                .addParams("buyerCellphone",buyerCellphone)
                .addParams("areaCode",areaCode)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showModifySuccess(response.getRespObject());
                        }else {
                            listener.showModifyFailed(response.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void modifyOrderAuditStatus(Context mContext, int eventId, String orderNumber, int audit, final OnModifyOrderListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_AUDIT + eventId + "/" + orderNumber)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("audit",audit+"")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showModifySuccess(response.getRespObject());
                        }else {
                            listener.showModifyFailed(response.getRespObject());
                        }
                    }
                });
    }
}
