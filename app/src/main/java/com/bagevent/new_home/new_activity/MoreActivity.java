package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.LoginQrCode;
import com.bagevent.activity_manager.manager_fragment.CollectManagerFragment;
import com.bagevent.activity_manager.manager_fragment.SignInActivity;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetInvoicePresenter;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.new_view.GetLiveView;
import com.bagevent.new_home.new_interface.presenter.GetLiveUrlPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.AppUtils;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.vcloud.video.effect.VideoEffect;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;

public class MoreActivity extends BaseActivity implements GetLiveView {

    private Bundle bundle = null;
    private int eventId=-1;
    private int hasLiveModule=-1;
    private PublishParam publishParam = null;
    private String liveUrl="";
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_open_live)
    AutoRelativeLayout rlOpenLive;
    @BindView(R.id.tv_live_status)
    TextView tvLiveStatus;

    @OnClick({R.id.rl_check_in,R.id.rl_collection_manager,R.id.ll_title_back,R.id.rl_login_in,R.id.rl_login_pc,R.id.rl_open_live})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_check_in:
                Intent intent=new Intent(this,SignInActivity.class);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                break;
            case R.id.rl_collection_manager:
                PageTool.go(this, CollectorManagerActivity.class,bundle);
                break;
            case R.id.rl_login_in:
                Intent intent1=new Intent(this,LoginQrCode.class);
                intent1.putExtra("eventId",eventId);
                startActivity(intent1);
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.rl_login_pc:
                Intent intentPc=new Intent(this,BarcodeLoginPcActivity.class);
                intentPc.putExtra("eventId",eventId);
                startActivity(intentPc);
                break;
            case R.id.rl_open_live:
                if(!bPermission){
                    TosUtil.show("请先允许app所需要的权限");
                    return;
                }
                if (NetUtil.isConnected(this)) {
                    liveUrlPresenter = new GetLiveUrlPresenter(this);
                    liveUrlPresenter.getEventLiveUrl();
                } else {
                    Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**   6.0权限处理     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MoreActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MoreActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MoreActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MoreActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(MoreActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }

    private MsgReceiver msgReceiver = null;

    private GetLiveUrlPresenter liveUrlPresenter;
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        intentValue();
        initView();
    }

    private void intentValue() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        eventId = bundle.getInt("eventId");
        hasLiveModule=bundle.getInt("hasLiveModule");
        bPermission = checkPublishPermission();
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LiveStreamingStopFinished");
        registerReceiver(msgReceiver, intentFilter);
        publishParam = new PublishParam();
        LogUtil.i("------------hasLiveModule"+hasLiveModule);
    }

    private void initView() {
        tvTitle.setText(R.string.more);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        if(hasLiveModule==1){
            rlOpenLive.setVisibility(View.VISIBLE);
        }else{
            rlOpenLive.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(msgReceiver);
        msgReceiver = null;
        super.onDestroy();
    }


    //用于接收Service发送的消息
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int value = intent.getIntExtra("LiveStreamingStopFinished", 0);
            if(value == 1)//finished
            {
                rlOpenLive.setEnabled(true);
                tvLiveStatus.setText("开启直播");
            }
            else//not yet finished
            {
                rlOpenLive.setEnabled(false);
                tvLiveStatus.setText("直播停止中...");
            }
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
    public void eventGetLiveUrlSuccess(String url) {
        publishParam.pushUrl=url;
        if(TextUtils.isEmpty(publishParam.pushUrl)){
            TosUtil.show("请先设置正确的推流地址");
        }else{
            Intent intentLive = new Intent(MoreActivity.this, LiveStreamingActivity.class);
            intentLive.putExtra("data", publishParam);
            startActivity(intentLive);
        }
    }

    @Override
    public void eventGetLiveUrlFailed(String errInfo) {
        TosUtil.show(errInfo);
    }

    public static class PublishParam implements Serializable {
        DateFormat formatter_file_name = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());

//        String pushUrl = "rtmp://p1bfd918b.live.126.net/live/60830b9f2a8e4e248d4fe865ae0faea4?wsSecret=97bab5ec1695d6829edafed496ad1a2c&wsTime=1582769151"; //推流地址
        String pushUrl = null; //推流地址
        lsMediaCapture.StreamType streamType = AV;  // 推流类型
        lsMediaCapture.FormatType formatType = RTMP; // 推流格式
        String recordPath="/sdcard/bagLive/" + formatter_file_name.format(new Date()) + ".mp4"; //文件录制地址，当formatType 为 MP4 或 RTMP_AND_MP4 时有效
        lsMediaCapture.VideoQuality videoQuality = lsMediaCapture.VideoQuality.SUPER; //清晰度
        boolean isScale_16x9 = false; //是否强制16:9
        boolean useFilter = true; //是否使用滤镜
        VideoEffect.FilterType filterType = VideoEffect.FilterType.clean; //滤镜类型
        boolean frontCamera = false; //是否默认前置摄像头
        boolean watermark = false; //是否添加水印
        boolean qosEnable = true;  //是否开启QOS
        int qosEncodeMode = 1; // 1:流畅优先, 2:清晰优先
        boolean graffitiOn = false; //是否添加涂鸦
        boolean uploadLog = true; //是否上传SDK运行日志

    }

}
