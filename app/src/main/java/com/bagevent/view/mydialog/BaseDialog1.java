package com.bagevent.view.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bagevent.R;

/**
 * Created by zwj on 2017/10/10.
 */

public abstract class BaseDialog1 {
    protected Context context;
    private Display display;//这个设置显示属性用的
    private Dialog dialog;//自定义Dialog，Dialog还是要有一个的吧
    private Point point;
    protected abstract int getDialogStyleId();
    protected abstract View getView();
    public static int DIALOG_COMMON_STYLE = R.style.common_dialog_style;

    //构造方法 来实现 最基本的对话框
    public BaseDialog1(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        //在这里初始化 基础对话框s
        if (getDialogStyleId() == 0){
            dialog = new Dialog(context, DIALOG_COMMON_STYLE );
        }else {
            dialog = new Dialog(context, getDialogStyleId());
        }
        dialog.setCanceledOnTouchOutside(false);
        // 调整dialog背景大小
      //  getView().setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth()), LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setContentView(getView());
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int)(point.x * 0.75f);//宽高可设置具体大小
        lp.height = (int) (point.x * 0.28f);
        dialog.getWindow().setAttributes(lp);

        //隐藏系统输入盘
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /** * Dialog 的基础方法 */

    //像这类设置对话框属性的方法，就返回值写自己，这样就可以一条链式设置了
    public BaseDialog1 setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public BaseDialog1 setdismissListeren(DialogInterface.OnDismissListener dismissListener){
        dialog.setOnDismissListener(dismissListener);
        return this;
    }

}
