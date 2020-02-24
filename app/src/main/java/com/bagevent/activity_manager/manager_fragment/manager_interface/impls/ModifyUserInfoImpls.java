package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.ModifyUserInfoCallback;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyUserInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyUserInfoListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/1.
 */
public class ModifyUserInfoImpls implements ModifyUserInfoInterface {

    @Override
    public void modifyUserInfo(Context mContext, String eventId, String attendId, String map, final OnModifyUserInfoListener onModifyUserInfoListener) {

        OkHttpUtil.okGet(mContext).url(Constants.URL + Constants.MODIFYINFO + "eventId="+eventId+"&attendeeId="+attendId+"&attendeeMap="+map+Constants.ACCESS_TOKEN+Constants.ACCESS_SERCRET)
                .build()
                .execute(new ModifyUserInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        onModifyUserInfoListener.modifyUserInoFailed(null);
                    }

                    @Override
                    public void onResponse(ModifyData response,int id) {
                        if(response.getRetStatus() == 200){
                            onModifyUserInfoListener.modifyUserInfoSuccess(response);
                        }else {
                            onModifyUserInfoListener.modifyUserInoFailed(response);
                        }

                    }
                });
    }
}
