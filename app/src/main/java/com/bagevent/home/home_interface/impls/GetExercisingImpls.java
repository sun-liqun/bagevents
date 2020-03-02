package com.bagevent.home.home_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.home.data.ExercisingData;
import com.bagevent.home.home_interface.GetExercisingIn;
import com.bagevent.home.home_interface.OnGetExercisingIn;
import com.bagevent.home.home_interface.callback.GetExercisingCallback;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2016/5/31.
 */
public class GetExercisingImpls implements GetExercisingIn {
    @Override
    public void getExercising(Context mContext, String userId, int page, int pageSize, String status, int showType, OnGetExercisingIn getExercisingIn) {
//        https://www.bagevent.com/api/v1/events/97939?page=1&size=50&status=3&showType=1&apiType=1&access_token=ipad&access_secret=ipad_secret
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.EXEREVENTLIST + userId +"?page=" +page+"&size="+pageSize+"&status="+status+"&showType="+showType+ "&"+Constants.APITYPE + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetExercising")
                .build()
//                .execute(new GetExercisingCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        if (call.isCanceled()) {
//                            LogUtil.e("GetExercising取消请求");
//                        }else {
//                            getExercisingIn.getFailed(mContext.getResources().getString(R.string.nodata));
//                        }
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (response.contains("\"retStatus\":200")) {
//                            LogUtil.i("-----------------respomse"+response);
//                            getExercisingIn.getSuccess(response);
//                        } else {
//                            ErrorJsonData errorString = new Gson().fromJson(response, ErrorJsonData.class);
//                            getExercisingIn.getFailed(errorString.getRespObject());
//                        }
//                    }
//                });
                .execute(new GetExercisingCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()) {
                            LogUtil.e("GetExercising取消请求");
                        } else {
                            getExercisingIn.getFailed(mContext.getResources().getString(R.string.nodata));
                        }
                    }

                    @Override
                    public void onResponse(ExercisingData response, int id) {
                        getExercisingIn.getSuccess(response);
                    }
                });
    }
}
