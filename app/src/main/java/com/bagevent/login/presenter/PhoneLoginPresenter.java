package com.bagevent.login.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.login.callback.LoginCallback;
import com.bagevent.login.loginview.PhoneLoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.register.callback.GetSMSCallback;
import com.bagevent.register.data.GetSMSData;
import com.bagevent.util.CompareRex;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.TosUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/8
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class PhoneLoginPresenter {
    private PhoneLoginViewInterface phoneLoginViewInterface;
    private CountDownTimer countDownTimer;

    public PhoneLoginPresenter(PhoneLoginViewInterface phoneLoginViewInterface) {

        this.phoneLoginViewInterface = phoneLoginViewInterface;
    }

    /**
     * 检查手机号
     */
    public boolean checkPhone(Context context) {
        String phone = phoneLoginViewInterface.getUserName();
        if (!TextUtils.isEmpty(phone)) {
            if (CompareRex.isCellPhone(phone)) {
                return true;
            } else {
                TosUtil.show(context.getResources().getString(R.string.phone_err1));
                return false;
            }
        } else {
            TosUtil.show(context.getResources().getString(R.string.input_phone));
            return false;
        }
    }

    /**
     * 开始倒计时
     */
    private void startCountDwon(final Context context) {
        phoneLoginViewInterface.setAuthCodeEnable(false);

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                if (time == 0) {
                    phoneLoginViewInterface.updateCountDown(context.getString(R.string.send_verification_code));
                    phoneLoginViewInterface.setAuthCodeEnable(true);
                } else {
                    phoneLoginViewInterface.updateCountDown(time + context.getResources().getString(R.string.second));
                }
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
    }


    /**
     * 发送验证码
     *
     * @param phone
     */
    public void sendAuthCode(final Context context, String phone) {

        startCountDwon(context);

        OkHttpUtils.post()
                .url(Constants.URL + Constants.GETSMS + "source=0" + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .addParams("account", phone)
                .build()
                .execute(new GetSMSCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                        phoneLoginViewInterface.showFailedErr(codeUtil.ErrCode(101));
                    }

                    @Override
                    public void onResponse(GetSMSData response, int id) {
                        if (response.getCode() == 200) {
                            TosUtil.show(context.getResources().getString(R.string.verification_code_send));
                        } else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            phoneLoginViewInterface.showFailedErr(codeUtil.ErrCode(response.getCode()));
                        }
                    }
                });
    }

    /**
     * 登录
     */
    public void login(Context context) {

        if (checkPhone(context)) {
            String phone = phoneLoginViewInterface.getUserName();
            String code = phoneLoginViewInterface.getAuthCode();

            if (TextUtils.isEmpty(code)) {
                TosUtil.show(context.getResources().getString(R.string.login_user_code));
            } else {
                login(context,phone, code);
            }
        }

    }


    private void login(final Context context, String phone, String code) {

        OkHttpUtils.post()
                .url(Constants.URL + Constants.PHONE_LOGIN)
                .addParams("loginType", "2")
                .addParams("loginSource", "4")
                .addParams("smsCode", code)
                .addParams("cellPhone", phone)
                .build()
                .execute(new LoginCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //        Log.e("", "login failed" + e.getMessage());
                        phoneLoginViewInterface.showFailedErr(context.getResources().getString(R.string.error_username_pwd));
                    }

                    @Override
                    public void onResponse(UserInfo response, int id) {
                        if (response.getCode() == 200) {
                            phoneLoginViewInterface.toMainActivity(response);
                        } else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            phoneLoginViewInterface.showFailedErr(codeUtil.ErrCode(response.getCode()));
                        }

                    }
                });
    }
}
