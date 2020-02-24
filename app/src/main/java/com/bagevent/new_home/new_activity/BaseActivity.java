package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.login.LoginActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2017/10/9.
 */

public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {
    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        initSwipeBackFinish();
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);
        initUI(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInvivideToken();
    }

    protected void isInvivideToken(){
        String value= SharedPreferencesUtil.get(this, "autoLoginExpireTime", "");
        if(!TextUtils.isEmpty(value)){
            Long nowTime = System.currentTimeMillis();
            Long autoLoginExpireTime = Long.parseLong(SharedPreferencesUtil.get(this, "autoLoginExpireTime", ""));
            String time = CompareRex.compareDay(nowTime, autoLoginExpireTime);
            if (TextUtils.isEmpty(time)) {
                OkHttpUtil.Post(this)
                        .url(Constants.NEW_URL + Constants.REFRESH_TOKEN)
                        .addParams("loginSource", "4")
                        .addParams("autoLoginToken", SharedPreferencesUtil.get(this, "autoLoginToken", ""))
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
                                    SharedPreferencesUtil.put(BaseActivity.this, "autoLoginExpireTime",stringData.getRespObject());
                                } else {
                                    SharedPreferencesUtil.put(BaseActivity.this, "autoLoginExpireTime","");
                                    SharedPreferencesUtil.put(BaseActivity.this, "userId", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "email", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "cellphone", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "userName", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "avatar", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "source", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "token", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "state", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "autoLoginToken", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, "autoLoginExpireTime", "");
                                    SharedPreferencesUtil.put(BaseActivity.this, KeyUtil.KEY_SELECT_EVENT_ID, "");
                                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    AppManager.getAppManager().finishAllActivity();
                                    Toast.makeText(BaseActivity.this, stringData.getRespObject(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }


    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(false);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(false);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        //   mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(false);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }


//    @Override
//    public void onBackPressed() {
//        // 正在滑动返回的时候取消返回按钮事件
//        if (mSwipeBackHelper.isSliding()) {
//            return;
//        }
//        mSwipeBackHelper.backward();
//    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initUI(Bundle savedInstanceState);
}
