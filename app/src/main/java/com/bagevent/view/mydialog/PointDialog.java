package com.bagevent.view.mydialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.view.listener.CancelListener;
import com.bagevent.view.listener.ConfirmListener;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by WenJie on 2017/11/1.
 */

public class PointDialog extends BaseDialog implements View.OnClickListener {

    private AutoLinearLayout llDialogClose;
    private TextView tvPoint;
    private TextView tvDialogCancel;
    private TextView tvDialogConfirm;
    private ConfirmListener confirmListener;
    private CancelListener cancelListener;

    public PointDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_point,null);
        llDialogClose = (AutoLinearLayout)v.findViewById(R.id.ll_dialog_close);
        tvPoint = (TextView)v.findViewById(R.id.tv_point);
        tvDialogCancel = (TextView)v.findViewById(R.id.tv_dialog_cancel);
        tvDialogConfirm = (TextView)v.findViewById(R.id.tv_dialog_confirm);
        initViewEvents();
        return v;
    }

    private void initViewEvents() {
        llDialogClose.setOnClickListener(this);
        tvDialogCancel.setOnClickListener(this);
        tvDialogConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dialog_close:
                dismiss();
                break;
            case R.id.tv_dialog_cancel:
                cancelListener.cancel(tvDialogCancel);
                break;
            case R.id.tv_dialog_confirm:
                confirmListener.confirm(tvDialogConfirm);
                break;
        }
    }

    public void setText(String text) {
        tvPoint.setText(text);
    }

    public void setCancelText(String text) {
        tvDialogCancel.setText(text);
    }

    public void setConfirmText(String text) {
        tvDialogConfirm.setText(text);
    }


    public void setCancelListener(CancelListener listener) {
        this.cancelListener = listener;
    }


    public void setConfirmListener(ConfirmListener listener) {
        this.confirmListener = listener;
    }
}
