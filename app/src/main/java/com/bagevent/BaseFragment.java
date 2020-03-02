package com.bagevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/11/24
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        isInvivideToken();
    }
    protected void isInvivideToken(){
        String value= SharedPreferencesUtil.get(getActivity(), "autoLoginExpireTime", "");
        if(!TextUtils.isEmpty(value)){
            Long nowTime = System.currentTimeMillis();
            Long autoLoginExpireTime = Long.parseLong(SharedPreferencesUtil.get(getActivity(), "autoLoginExpireTime", ""));
            String time = CompareRex.compareDay(nowTime, autoLoginExpireTime);
            if (TextUtils.isEmpty(time)) {
                OkHttpUtil.Post(getActivity())
                        .url(Constants.NEW_URL + Constants.REFRESH_TOKEN)
                        .addParams("loginSource", "4")
                        .addParams("autoLoginToken", SharedPreferencesUtil.get(getActivity(), "autoLoginToken", ""))
                        .build()
                        .execute(new Callback<String>() {

                            @Override
                            public String parseNetworkResponse(Response response, int id) throws Exception {
                                return response.body().string();
                            }

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                TosUtil.show(getString(R.string.send_error));
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                StringData stringData = new Gson().fromJson(response, StringData.class);
                                if (response.contains("\"retStatus\":200")) {
                                    SharedPreferencesUtil.put(getActivity(), "autoLoginExpireTime",stringData.getRespObject());
                                } else {
                                    SharedPreferencesUtil.put(getActivity(), "autoLoginExpireTime","");
                                    SharedPreferencesUtil.put(getActivity(), "userId", "");
                                    SharedPreferencesUtil.put(getActivity(), "email", "");
                                    SharedPreferencesUtil.put(getActivity(), "cellphone", "");
                                    SharedPreferencesUtil.put(getActivity(), "userName", "");
                                    SharedPreferencesUtil.put(getActivity(), "avatar", "");
                                    SharedPreferencesUtil.put(getActivity(), "source", "");
                                    SharedPreferencesUtil.put(getActivity(), "token", "");
                                    SharedPreferencesUtil.put(getActivity(), "state", "");
                                    SharedPreferencesUtil.put(getActivity(), "autoLoginToken", "");
                                    SharedPreferencesUtil.put(getActivity(), "autoLoginExpireTime", "");
                                    SharedPreferencesUtil.put(getActivity(), KeyUtil.KEY_SELECT_EVENT_ID, "");
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                    AppManager.getAppManager().finishAllActivity();
                                    Toast.makeText(getActivity(), stringData.getRespObject(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }


}
