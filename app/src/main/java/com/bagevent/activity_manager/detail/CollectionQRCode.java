package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.db.CollectList;
//import com.bagevent.db.CollectList_Table;
import com.bagevent.db.CollectList_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;


/**
 * Created by zwj on 2016/8/11.
 */
public class CollectionQRCode extends BaseActivity implements View.OnClickListener{

    private ImageView iv_qrCode;
    private AutoLinearLayout ll_qr_back;
    private TextView tv_qrcode_collectionName;
    /**
     * 生成采集点二维码所需要的一些变量
     */
    private String collectionName = "";
    private int eventId = -1;
    private int collectionId = -1;
    private String userId = "";

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.collection_qrcode);
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.ff9a3b), Constants.STATUS_ALPHA);
//        Intent intent = getIntent();
//        collectionId = intent.getIntExtra("collectionId",0);
//        eventId = intent.getIntExtra("eventId",0);
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        initView();
//        setListener();
//        createQRCode();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.collection_qrcode);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.ff9a3b), Constants.STATUS_ALPHA);
        Intent intent = getIntent();
        collectionId = intent.getIntExtra("collectionId",0);
        eventId = intent.getIntExtra("eventId",0);
        userId = SharedPreferencesUtil.get(this,"userId","");
        initView();
        setListener();
        createQRCode();
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
      //  EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    //    EventBus.getDefault().removeAllStickyEvents();
    }

    private void createQRCode(){
        String autoLoginToken = SharedPreferencesUtil.get(CollectionQRCode.this,"autoLoginToken","");

        Bitmap bitmap = new QREncode.Builder(this)
                .setColor(getResources().getColor(R.color.black))//二维码颜色
                //.setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                .setContents("collection:"+ eventId +":"+ collectionId +":"+ userId + ":" +autoLoginToken)//二维码内容
                .build().encodeAsBitmap();
        iv_qrCode.setImageBitmap(bitmap);
        //  Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("collection:"+ eventId +":"+ collectionId +":"+ userId + ":" +autoLoginToken,450);
       // EventBus.getDefault().postSticky(new MsgEvent("qrCode",bitmap));

    }

    private void initView(){
        iv_qrCode = (ImageView)findViewById(R.id.iv_qrcode);
        ll_qr_back= (AutoLinearLayout)findViewById(R.id.ll_qr_back);
        tv_qrcode_collectionName = (TextView)findViewById(R.id.tv_qrcode_collectionName);
        CollectList list = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).and(CollectList_Table.collectPointId.is(collectionId)).querySingle();
        if(list != null) {
            tv_qrcode_collectionName.setText(list.collectionName);
        }
    }

//    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
//    public void onEvent(MsgEvent event) {
//        if (event.mMsg.contains("qrCode")) {
//            iv_qrCode.setImageBitmap(event.bitmap);
//        }
//    }

    private void setListener() {
        ll_qr_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_qr_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
