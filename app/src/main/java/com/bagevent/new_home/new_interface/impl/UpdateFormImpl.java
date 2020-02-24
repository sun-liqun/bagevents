package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UpdateFormInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateFormListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/26.
 */
public class UpdateFormImpl implements UpdateFormInterface {
    @Override
    public void updateForm(final Context mContext, String detailEventId, String userId, int formFieldId, int type, String formFieldValue, final OnUpdateFormListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_FORM + detailEventId)
                .addParams("userId",userId)
                .addParams("formFieldId",formFieldId+"")
                .addParams("type",type+"")
                .addParams("formFieldValue",formFieldValue)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.updateFormSuceecss();
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.updateFormFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
