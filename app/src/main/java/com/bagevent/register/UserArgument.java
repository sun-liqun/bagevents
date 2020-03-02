package com.bagevent.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by zwj on 2016/8/5.
 */
public class UserArgument extends BaseActivity implements View.OnClickListener {
    private AutoLinearLayout rl_back;
    private WebView wv;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.user_argument);
//        initView();
//        initWebView();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.user_argument);
        initView();
        initWebView();
    }

    private void initWebView() {
        String url = "http://www.bagevent.com/introduce/privacy.html";
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);
        wv.getSettings().setSupportZoom(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    private void initView() {
        rl_back = (AutoLinearLayout) findViewById(R.id.ll_argument_back);
        wv = (WebView)findViewById(R.id.wv);
        rl_back.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_argument_back:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }
    }
}
