package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.BadgeFormTypeCallback;
import com.bagevent.activity_manager.manager_fragment.callback.GetFormTypeCallback;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetFormTypeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetFormTypeListener;
import com.bagevent.common.Constants;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/6/27.
 */
public class GetFormTypeIms implements GetFormTypeInterface {

    @Override
    public void getFormType(final Context context, String eventId, final OnGetFormTypeListener onGetFormTypeListener) {
        OkHttpUtil.okGet(context)
                .url(Constants.URL + Constants.FORMTYPE + eventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET)
                .tag("GetFormType")
                .build()
                .execute(new GetFormTypeCallback(context,eventId) {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetFormType请求取消");
                            return;
                        }
                    }

                    @Override
                    public void onResponse(FormType response,int id) {
                        if( response.getRetStatus() == 200 ) {
                            onGetFormTypeListener.getFormTypeSuccess(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            onGetFormTypeListener.getFormTypeFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });

    }

    @Override
    public void getBadgeFormType(final Context context, String eventId, int stType, final OnGetFormTypeListener listener) {
        OkHttpUtil.okGet(context)
                .url(Constants.URL + Constants.FORMTYPE + eventId + "?st_type="+stType + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetBadgeFormType")
                .build()
                .execute(new BadgeFormTypeCallback(context,eventId) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetBadgeFormType请求取消");
                            return;
                        }
                    }

                    @Override
                    public void onResponse(FormType response, int id) {

                        if( response.getRetStatus() == 200 ) {
                            listener.getBadgeFormTypeSuccess(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            listener.getBadgeFormTypeFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }


}
