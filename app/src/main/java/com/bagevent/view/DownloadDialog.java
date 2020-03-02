package com.bagevent.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bagevent.R;


public class DownloadDialog  extends AlertDialog {
    private ProgressBar progressBar;
    private  Context context;
    private  View rootView;
    private TextView progress_tv;
    public DownloadDialog(Context context){
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle();
        initView();
    }
    private void setStyle() {
        this.setCancelable(false);//设置点击back建不能取消
        this.setCanceledOnTouchOutside(false);//触屏幕中dialog以外部分，不取消
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams= this.getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;//设置dialog位置
        layoutParams.width=(displayMetrics.widthPixels/4)*3;//设置dialog宽度
    }

    private void initView() {
        rootView=View.inflate(context, R.layout.dialog_download,null);
        progressBar=(ProgressBar) rootView.findViewById(R.id.dialog_download_progressbar);
        progress_tv=(TextView) rootView.findViewById(R.id.dialog_download_progress_tv);
        this.setContentView(rootView);
    }

    public void setProgress(int progress){
        this.progressBar.setProgress(progress);
        this.progress_tv.setText(progress+"%");
    }
}
