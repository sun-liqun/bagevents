package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.new_view.SaveEventDetailView;
import com.bagevent.new_home.new_interface.presenter.SaveEventDetailPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.richeditor.RichEditor;


/**
 * Created by zwj on 2016/9/14.
 */
public class ReleaseEventDetail extends BaseActivity implements View.OnClickListener,SaveEventDetailView {

    private AutoLinearLayout eventDetailBack;
    private AutoLinearLayout eventDetailConfirm;
    private RichEditor et_event_detail;
    private String detail = "";//html标签
    private String brief = "";//内容
    private int eventId = -1;
    private String userId = "";
    private SaveEventDetailPresenter presenter;
    private InputMethodManager inputMethodManager;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_detail);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",0);
//        detail = intent.getStringExtra("detail");
//        detail = modifyImgUrl(detail);//修改图片url
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        initView();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_detail);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",0);
        detail = intent.getStringExtra("detail");
        detail = modifyImgUrl(detail);//修改图片url
        userId = SharedPreferencesUtil.get(this,"userId","");
        initView();
        setListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inputMethodManager.isActive(et_event_detail)) {
            inputMethodManager.hideSoftInputFromWindow(et_event_detail.getWindowToken(),0);
        }
    }

    private void postEventDetail() {
        //获得最终的详情数据
        brief = clearHtmlTag(et_event_detail.getHtml());
        detail = et_event_detail.getHtml();
        if(TextUtils.isEmpty(detail)) {
           detail = "\n";
        }
        if(NetUtil.isConnected(this)) {
            presenter = new SaveEventDetailPresenter(this);
            presenter.saveEventDetail();
        }else {
            Toast toast = Toast.makeText(this,R.string.net_err,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void setListener() {
        eventDetailBack.setOnClickListener(this);
        eventDetailConfirm.setOnClickListener(this);
    }

    private void initView() {
        eventDetailBack = (AutoLinearLayout) findViewById(R.id.ll_event_detail_back);
        eventDetailConfirm = (AutoLinearLayout) findViewById(R.id.ll_event_detail_confirm);
        et_event_detail = (RichEditor) findViewById(R.id.et_event_detail);
        et_event_detail.setEditorHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        et_event_detail.setEditorWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        et_event_detail.setFontSize(2);
        et_event_detail.setBold();
        et_event_detail.setHtml(detail);
    }

    /**
     * 清除html标签得到纯文本
     */
    private String clearHtmlTag(String tag) {
        Pattern p = Pattern.compile("<[^>]*>|\\n");
        Matcher matcher = p.matcher(tag);
   //     Log.e("fdf",matcher.replaceAll("").trim());
        return matcher.replaceAll("").trim();
    }

    /**
     * 修改html标签中的图片url
     */
    private String modifyImgUrl(String url) {
        String tempUrl = "";
        if (!TextUtils.isEmpty(url)) {
            tempUrl = url.replace("http://img.bagevent.com", Constants.imgsURL);
            if (url.equals(tempUrl)) {
                tempUrl = url.replace("//img.bagevent.com", Constants.imgsURL);
            }
        }
        return tempUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_detail_back:
                clearEditFoucus();
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_detail_confirm:
                postEventDetail();
                break;
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String textName() {
        return "content";
    }

    @Override
    public String eventContent() {
        return detail;
    }

    @Override
    public String eventBrief() {
        return brief;
    }

    @Override
    public void saveDetailSuccess() {
        clearEditFoucus();
        EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void saveDetailFailed(String errInfo) {
        Toast toast = Toast.makeText(this,errInfo,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void clearEditFoucus() {//清除富文本的焦点,防止造成leak window
        et_event_detail.clearFocusEditor();
        et_event_detail.removeAllViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            clearEditFoucus();
            AppManager.getAppManager().finishActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
