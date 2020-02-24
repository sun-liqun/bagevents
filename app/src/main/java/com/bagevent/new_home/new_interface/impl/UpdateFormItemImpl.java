package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UpdateFormItemInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateFormItemListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/26.
 */
public class UpdateFormItemImpl implements UpdateFormItemInterface {
    @Override
    public void updateFormItem(final Context mContext, String detailEventId, String userId, int formFieldId, String itemName, String itemValue, final OnUpdateFormItemListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_FORM_ITEM + detailEventId)
                .addParams("userId",userId)
                .addParams("formFieldId",formFieldId+"")
                .addParams("itemName",itemName)
                .addParams("itemValue",itemValue)
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
                            listener.updateFormItemSuccess();
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.updateFormItemFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
