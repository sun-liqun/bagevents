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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.LoginQrCode;
import com.bagevent.activity_manager.manager_fragment.SignInActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.PageTool;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.vcloud.video.effect.VideoEffect;

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

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;

public class OpenLiveActivity extends BaseActivity implements View.OnClickListener {

    private PublishParam publishParam = null;
    private EditText editMainPushUrl  = null;
    private RadioGroup filterGroup = null;
    private RadioGroup qosGroup = null;
    private TextView txtUsingFilter = null, txtWatermark = null, txtQos = null, txtGraffiti = null,txtFrontCamera = null,txtUpload = null;
    private ImageView imageScan = null;
    private DateFormat formatter_file_name = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());

    @BindView(R.id.main_start)
    Button mainStartBtn;

    /**   6.0权限处理     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(OpenLiveActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(OpenLiveActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(OpenLiveActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(OpenLiveActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(OpenLiveActivity.this,
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

    private OpenLiveActivity.MsgReceiver msgReceiver = null;

    @OnClick({R.id.main_start})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_start:
//                publishParam.pushUrl="rtmp://p1bfd918b.live.126.net/live/60830b9f2a8e4e248d4fe865ae0faea4?wsSecret=97bab5ec1695d6829edafed496ad1a2c&wsTime=1582769151";
//                publishParam.recordPath="/sdcard/bagLive/" + formatter_file_name.format(new Date()) + ".mp4";
//                publishParam.streamType = lsMediaCapture.StreamType.AV;
//                publishParam.formatType = lsMediaCapture.FormatType.RTMP;
//                publishParam.videoQuality = lsMediaCapture.VideoQuality.SUPER;
//                publishParam.filterType = VideoEffect.FilterType.clean;
//                publishParam.qosEncodeMode = 1;
//                publishParam.isScale_16x9 = false;
                if(!bPermission){
                    Toast.makeText(getApplication(),"请先允许app所需要的权限",Toast.LENGTH_LONG).show();
                    bPermission = checkPublishPermission();
                    return;
                }

                Intent intent=new Intent(this,LiveStreamingActivity.class);
                intent.putExtra("data", publishParam);
                startActivity(intent);
                break;
        }
    }

    public static class PublishParam implements Serializable {
        DateFormat formatter_file_name = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());

        String pushUrl = "rtmp://p1bfd918b.live.126.net/live/60830b9f2a8e4e248d4fe865ae0faea4?wsSecret=97bab5ec1695d6829edafed496ad1a2c&wsTime=1582769151"; //推流地址
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

    @Override
    protected void initUI(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_open_live);
        ButterKnife.bind(this);
        bPermission = checkPublishPermission();
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LiveStreamingStopFinished");
        registerReceiver(msgReceiver, intentFilter);
        publishParam = new PublishParam();
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
                mainStartBtn.setEnabled(true);
                mainStartBtn.setText("进入直播");
            }
            else//not yet finished
            {
                mainStartBtn.setEnabled(false);
                mainStartBtn.setText("直播停止中...");
            }
        }
    }
}
