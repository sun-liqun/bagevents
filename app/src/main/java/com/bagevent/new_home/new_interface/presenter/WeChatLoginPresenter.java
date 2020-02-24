package com.bagevent.new_home.new_interface.presenter;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.login.callback.LoginCallback;
import com.bagevent.login.model.UserInfo;
import com.bagevent.new_home.new_interface.new_view.WeChatLoginView;
import com.bagevent.util.ErrCodeUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class WeChatLoginPresenter {
    private WeChatLoginView chatLoginView;

    public WeChatLoginPresenter(WeChatLoginView chatLoginView) {
        this.chatLoginView = chatLoginView;
    }

    public void login(final Context context) {

        String unionId = chatLoginView.getUnionId();

        OkHttpUtils.post()
                .url(Constants.URL + Constants.PHONE_LOGIN)
                .addParams("loginType", "3")
                .addParams("loginSource", "4")
                .addParams("weixinLoginType", "0")
                .addParams("unionId", unionId)
                .build()
                .execute(new LoginCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        chatLoginView.showFailedErr(context.getResources().getString(R.string.login_error));
                    }

                    @Override
                    public void onResponse(UserInfo response, int id) {
                        if (response.getCode() == 200) {
                            chatLoginView.toMainActivity(response);
                        } else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            chatLoginView.showFailedErr(codeUtil.ErrCode(response.getCode()));
                        }
                    }
                });
    }
}
