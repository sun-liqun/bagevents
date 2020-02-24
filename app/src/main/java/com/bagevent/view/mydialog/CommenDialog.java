package com.bagevent.view.mydialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.view.listener.CancelListener;
import com.bagevent.view.listener.ConfirmListener;
import com.xw.repo.XEditText;
import com.zhy.autolayout.AutoLinearLayout;

public class CommenDialog extends BaseDialog1 implements View.OnClickListener{

    private TextView tvDialogConfirm;
    private TextView tv_go;
    private ConfirmListener confirmListener;
    public CommenDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_commen_dialog,null);
        tvDialogConfirm = (TextView)v.findViewById(R.id.tv_dialog_confirm);
        tv_go = (TextView)v.findViewById(R.id.tv_go);
        initListener();
        return v;
    }



    private void initListener() {
        tvDialogConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_confirm:
                confirmListener.confirm(tvDialogConfirm);
                break;
        }
    }

    public void setConfirmListener(ConfirmListener listener) {
        this.confirmListener = listener;
    }

    public void setText(String text) {
        tv_go.setText(text);
    }

    public String getText() {
        return tv_go.getText().toString();
    }
}
