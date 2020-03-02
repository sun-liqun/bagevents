/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.bagevent.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.login.LoginActivity;
import com.bagevent.login.model.UserInfo;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.data.CheckAccessTokenData;
import com.bagevent.new_home.data.WXAccessTokenData;
import com.bagevent.new_home.data.WXUserInfoData;
import com.bagevent.new_home.data.YunXinToken;
import com.bagevent.new_home.new_activity.RechargeMessage;
import com.bagevent.new_home.new_activity.RechargeResult;
import com.bagevent.new_home.new_interface.new_view.CheckAccessTokenView;
import com.bagevent.new_home.new_interface.new_view.WXGetAccessTokenView;
import com.bagevent.new_home.new_interface.new_view.WXUserInfoView;
import com.bagevent.new_home.new_interface.new_view.WeChatLoginView;
import com.bagevent.new_home.new_interface.presenter.CheckAccessTokenPresenter;
import com.bagevent.new_home.new_interface.presenter.WXGetAccessTokenPresenter;
import com.bagevent.new_home.new_interface.presenter.WXUserInfoPresenter;
import com.bagevent.new_home.new_interface.presenter.WeChatLoginPresenter;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler, WXGetAccessTokenView, CheckAccessTokenView, WeChatLoginView {

    private WXGetAccessTokenPresenter getAccessTokenPresenter;
    private CheckAccessTokenPresenter checkAccessTokenPresenter;
    private WXUserInfoPresenter wxUserInfoPresenter;

    private String code = "";//发起授权请求返回的code值,用来获取access_token
    private String access_token = "";
    private String openId = "";
    private String refreshToken = "";
    private String unionId = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	Log.e("fdsa","fdsf00");
        MyApplication.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyApplication.iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        baseReq.getType();
        //	Log.e("aa","ssas");
    }

    @Override
    public void onResp(BaseResp resp) {
//		Log.e("fdsa","fdsdsf00");

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Intent intent = new Intent(this, RechargeResult.class);
                intent.putExtra(RechargeResult.RECHARGE, RechargeResult.RECHARGE_SUCCESS);
//                        intent.putExtra(RechargeResult.ORDERNUM, orderNum);
//                        intent.putExtra(RechargeResult.PRODUCT, "短信充值");
//                        intent.putExtra(RechargeResult.FEE, "10");
                startActivity(intent);
            }
            finish();
            Log.e("WXEntryactivity", "OnPay finish errCode = " + resp.errCode);
//			if(resp.errCode == 0) {
//
//			}else {
//
//			}
        } else {
            if (resp instanceof SendMessageToWX.Resp) {
                Intent intent = new Intent(this, WXShareEntryActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                finish();
            } else {
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        code = ((SendAuth.Resp) resp).code;
                        getAccessToken();
                        break;
                    default:
                        finish();
                        break;
                }

            }
        }
    }

    private void getAccessToken() {
//		access_token = SharedPreferencesUtil.get(this,"wx_access_token","");
//		openId = SharedPreferencesUtil.get(this,"wx_openId","");
//		if(!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(openId)) {//如果access_token或openId为空，则进行重新获取
//			checkAccessTokenEnable();
//		}else {
        getAccessTokenPresenter = new WXGetAccessTokenPresenter(this);
        getAccessTokenPresenter.getAccessToken();
        //	}

    }

    private void checkAccessTokenEnable() {
        checkAccessTokenPresenter = new CheckAccessTokenPresenter(this);
        checkAccessTokenPresenter.checkAccessToken();
    }


    @Override
    public String appid() {
        return Constants.WX_APPID;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String secret() {
        return Constants.WX_SECRET;
    }

    @Override
    public void GetAccessTokenOk(WXAccessTokenData response) {
        access_token = response.getAccess_token();
        openId = response.getOpenid();
        refreshToken = response.getRefresh_token();
        unionId = response.getUnionid();
        SharedPreferencesUtil.put(this, "wx_access_token", access_token);
        SharedPreferencesUtil.put(this, "wx_openId", openId);
        SharedPreferencesUtil.put(this, "refresh_token", refreshToken);
        checkAccessTokenEnable();
    }

    @Override
    public void GetAccessTokenErr() {
        TosUtil.show(getString(R.string.login_error));
        finish();
    }

    @Override
    public String accessToken() {
        return access_token;
    }

    @Override
    public String openId() {
        return openId;
    }


    @Override
    public void accessTokenEnable(CheckAccessTokenData response) {
        //Toast.makeText(this,response.getErrcode()+"",Toast.LENGTH_SHORT).show();
        WeChatLogin();
    }

    private void WeChatLogin() {
        WeChatLoginPresenter weChatLoginPresenter = new WeChatLoginPresenter(this);
        weChatLoginPresenter.login(this);
    }

    @Override
    public void accessTokenUnable() {//access_token无效
        TosUtil.show(getString(R.string.login_error));
        finish();
    }

    @Override
    public String getUnionId() {
        return unionId;
    }

    @Override
    public void showFailedErr(String msg) {
        TosUtil.show(msg);
        finish();
    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
        SharedPreferencesUtil.put(this, "userId", userInfo.getReturnObj().getUserId() + "");
        SharedPreferencesUtil.put(this, "email", userInfo.getReturnObj().getEmail());
        SharedPreferencesUtil.put(this, "cellphone", userInfo.getReturnObj().getCellphone());
        SharedPreferencesUtil.put(this, "userName", userInfo.getReturnObj().getUserName());
        SharedPreferencesUtil.put(this, "avatar", userInfo.getReturnObj().getAvatar());
        SharedPreferencesUtil.put(this, "source", userInfo.getReturnObj().getSource() + "");
        SharedPreferencesUtil.put(this, "token", userInfo.getReturnObj().getToken());
        SharedPreferencesUtil.put(this, "state", userInfo.getReturnObj().getState() + "");
        SharedPreferencesUtil.put(this, "autoLoginToken", userInfo.getReturnObj().getAutoLoginToken());
        SharedPreferencesUtil.put(this, "autoLoginExpireTime", userInfo.getReturnObj().getAutoLoginExpireTime() + "");

        String token=userInfo.getReturnObj().getToken();
        OkHttpUtil.Get(this)
                .url(Constants.NEW_URL + Constants.YUNXIN_TOKEN)
                .addParams("action","")
                .addParams("userToken",token)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("云信获取token失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            YunXinToken yunXin=new Gson().fromJson(response,YunXinToken.class);
                            String accid=yunXin.getRespObject().getAccid();
                            String yunxin_token=yunXin.getRespObject().getToken();
                            LogUtil.e("云信获取token成功"+yunxin_token);
                            LoginInfo loginInfo=new LoginInfo(accid,yunxin_token);
                            RequestCallback<LoginInfo> callback =
                                    new RequestCallback<LoginInfo>() {
                                        @Override
                                        public void onSuccess(LoginInfo param) {
                                            putYunXinInfo(accid,yunxin_token);
                                            LogUtil.e("云信登录成功");
                                            NimUIKit.loginSuccess(accid);
                                            // 初始化消息提醒配置
                                            initNotificationConfig();
                                            EventBus.getDefault().post(new MessageEvent(MessageAction.REFRESH_YUNXIN_MESSAGE));
                                        }

                                        @Override
                                        public void onFailed(int code) {
                                            LogUtil.e("云信登录失败"+code+"");
                                            Toast.makeText(WXEntryActivity.this, "失败"+code, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onException(Throwable exception) {
                                            Toast.makeText(WXEntryActivity.this, "异常"+exception, Toast.LENGTH_SHORT).show();
                                            LogUtil.e("云信登录异常"+exception+"");
                                        }
                                    };
                            NIMClient.getService(AuthService.class).login(loginInfo)
                                    .setCallback(callback);
                        } else {
                            LogUtil.e("云信获取token失败");
                        }
                    }
                });

        Intent intent = new Intent(this, HomePage.class);//这边修改跳转 MainActivity-->Homepage
        startActivity(intent);
        finish();
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(true);
    }

    private void putYunXinInfo(String accid, String yunxin_token) {
        SharedPreferencesUtil.put(this, "accid", accid);
        SharedPreferencesUtil.put(this, "yunxin_token", yunxin_token);
    }
}
