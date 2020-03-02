package com.bagevent.view.mydialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.view.listener.ConfirmListener;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by zwj on 2017/10/10.
 */

public class MyPayDialog extends BaseDialog implements View.OnClickListener {
    private AutoLinearLayout llCancelPay;
    private EditText etPayPassword;
    private TextView tvForgetPassword;
    private TextView tvConfirmPay;
    private ConfirmListener confirmListener;

    public MyPayDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_pay,null);
        llCancelPay = (AutoLinearLayout)v.findViewById(R.id.ll_cancel_pay);
        etPayPassword = (EditText)v.findViewById(R.id.et_pay_password);
        tvForgetPassword = (TextView)v.findViewById(R.id.tv_forget_password);
        tvConfirmPay = (TextView)v.findViewById(R.id.tv_confirm_pay);
        initViewEvents();
        return v;
    }

    private void initViewEvents() {
        llCancelPay.setOnClickListener(this);
        tvConfirmPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cancel_pay:
                dismiss();
                break;
            case R.id.tv_confirm_pay:
                confirmListener.confirm(tvConfirmPay);
                break;
        }
    }

    public void setPassword() {
        etPayPassword.setText("");
    }

    public String getPassword() {
        return etPayPassword.getText().toString();
    }


    public void setConfirmListener(ConfirmListener listener) {
        this.confirmListener = listener;
    }
}
