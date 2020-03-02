package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.AddFormItemData;
import com.bagevent.new_home.new_interface.AddFormItemInterface;
import com.bagevent.new_home.new_interface.callback.AddFormItemCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormItemListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/26.
 */
public class AddFormItemImpl implements AddFormItemInterface {

    @Override
    public void addFormItem(Context mContext, String detailEventId, String userId, int fieldId, final OnAddFormItemListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.ADD_FORM_ITEM + detailEventId)
                .addParams("userId",userId)
                .addParams("formFieldId",fieldId+"")
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new AddFormItemCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddFormItemData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.addItemSuccess(response);
                        }else {
                            listener.addItemFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
