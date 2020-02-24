package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.util.AppManager;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

public class BarcodeLoginPcActivity extends BaseActivity implements  View.OnClickListener, OnScannerCompletionListener {

    private static final String TAG = "BarcodeLoginPcActivity";
    private ScannerView scannerLogin;
    private AutoLinearLayout ll_login_back;
    private TextView isAllowCamera;
    private int eventId = -1;
    private long scanTime = 0L;//扫描间隔时间
    private String tempScanResult = "";//扫描间隔时间
    private static final int REQUECT_CODE_CAMERA = 2;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_barcode_login_pc);
        initView();
        MPermissions.requestPermissions(this, REQUECT_CODE_CAMERA, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        setListener();
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
    }

    @Override
    protected void onResume() {
        scannerLogin.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        scannerLogin.onPause();
        super.onPause();
    }

    @PermissionGrant(REQUECT_CODE_CAMERA)
    public void requestCameraSuccess() {
        isAllowCamera.setVisibility(View.GONE);
    }

    @PermissionDenied(REQUECT_CODE_CAMERA)
    public void requestCameraFailed() {
        isAllowCamera.setVisibility(View.VISIBLE);
    }

    private void initView() {
        scannerLogin = (ScannerView) findViewById(R.id.scanner_login);
        ll_login_back = (AutoLinearLayout) findViewById(R.id.ll_login_back);
        isAllowCamera = (TextView) findViewById(R.id.isAllowCamera);
        scannerLogin.setLaserFrameBoundColor(0xfffe6900);
        scannerLogin.setLaserLineHeight(1);
        scannerLogin.setLaserColor(0xfffe6900);
    }

    private void setListener() {
        ll_login_back.setOnClickListener(this);
        scannerLogin.setOnScannerCompletionListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_login_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        scannerLogin.restartPreviewAfterDelay(0L);
        scanTime(rawResult.toString());
    }

    private void scanTime(String result) {
        if (tempScanResult.equals(result)) {
            if (System.currentTimeMillis() - scanTime > 5000) {
                if (result.startsWith("BAG_L_C")) {
                    Intent intent=new Intent(this,SelectDeviceFunctionActivity.class);
                    intent.putExtra("eventId",eventId);
                    intent.putExtra("confirmQrCode",result);
                    startActivity(intent);
                    AppManager.getAppManager().finishActivity();
                } else {
                    Toast toast = Toast.makeText(this, R.string.check_barcode, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                scanTime = System.currentTimeMillis();
            }
        } else {
            if (result.startsWith("BAG_L_C")) {
                Intent intent=new Intent(this,SelectDeviceFunctionActivity.class);
                intent.putExtra("eventId",eventId);
                intent.putExtra("confirmQrCode",result);
                startActivity(intent);
                AppManager.getAppManager().finishActivity();
            } else {
                Toast toast = Toast.makeText(this, R.string.check_barcode, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            scanTime = System.currentTimeMillis();
        }
        tempScanResult = result;
    }
}
