package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bumptech.glide.Glide;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/28 0028.
 */

public class ScannerExpress extends BaseActivity implements OnScannerCompletionListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scanner_express)
    ScannerView scannerExpress;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_scanner_express);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        scannerExpress.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        scannerExpress.onPause();
        super.onPause();
    }


    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }

    private void initView() {
        tvTitle.setText(R.string.scanning_express_number);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        scannerExpress.setOnScannerCompletionListener(this);
        scannerExpress.setLaserFrameBoundColor(0xfffe6900);
        scannerExpress.setLaserLineHeight(1);
        scannerExpress.setLaserColor(0xfffe6900);
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        String string = parsedResult.toString();
        Intent intent = new Intent();
        intent.putExtra("expressNumber",string);
        setResult(2,intent);
        AppManager.getAppManager().finishActivity();
    }
}
