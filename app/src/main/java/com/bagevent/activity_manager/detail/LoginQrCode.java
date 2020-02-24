package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by zwj on 2016/10/14.
 */
public class LoginQrCode extends BaseActivity implements View.OnClickListener{

    private AutoLinearLayout ll_login_qrcode_back;
    private ImageView login_qrcode;
    private int eventId = -1;
    private String token = "";

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.login_qrcode);
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        token = SharedPreferencesUtil.get(this,"autoLoginToken","");
//        initView();
//        setListener();
//        createQRCode();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.login_qrcode);
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        token = SharedPreferencesUtil.get(this,"autoLoginToken","");
        initView();
        setListener();
        createQRCode();
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   EventBus.getDefault().unregister(this);
    }

//    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
//    public void onEvent(MsgEvent event) {
//      //  Log.e("fdf","fdf");
//        if (event.mMsg.contains("loginQrcode")) {
//            login_qrcode.setImageBitmap(event.bitmap);
//        }
//    }

    private void createQRCode(){
        Bitmap logo = BitmapFactory.decodeResource(getResources(),R.mipmap.icon);
        //文本类型
        Bitmap bitmap = new QREncode.Builder(LoginQrCode.this)
                .setColor(getResources().getColor(R.color.black))//二维码颜色
                //.setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                .setContents(token + "eventId:" + eventId)//二维码内容
                .setLogoBitmap(logo)//二维码中间logo
                .build().encodeAsBitmap();
        login_qrcode.setImageBitmap(bitmap);

        //  Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(token + "eventId:" + eventId,250,Color.BLACK,Color.WHITE,logo);
       //         EventBus.getDefault().postSticky(new MsgEvent("loginQrcode",bitmap));

    }

    private void setListener() {
        ll_login_qrcode_back.setOnClickListener(this);
    }

    private void initView() {
        ll_login_qrcode_back = (AutoLinearLayout)findViewById(R.id.ll_login_qrcode_back);
        login_qrcode = (ImageView)findViewById(R.id.login_qrcode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login_qrcode_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
