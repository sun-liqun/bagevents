package com.bagevent.dialog;

import android.app.FragmentManager;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.bagevent.dialog.listener.OnBindView;
import com.bagevent.dialog.listener.OnViewClick;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/7
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class LDialog extends BaseDialogFragment {
    private Builder builder;


    public void setBuilder(Builder builder) {
        this.builder = builder;
    }


    public LDialog show() {
        show(builder.fragmentManager, getClass().getSimpleName());
        return this;
    }


    @Override
    protected int getHeight() {
        if (builder.heightPx != 0) {
            return builder.heightPx;
        }
        return super.getHeight();
    }

    @Override
    protected int getWidth() {
        if (builder.widthPx != 0) {
            return builder.widthPx;
        }
        return super.getWidth();
    }

    @Override
    protected int getGravity() {
        if (builder.gravity > 0) {
            return builder.gravity;
        }
        return super.getGravity();
    }

    @Override
    public boolean isCancelableOutside() {
        return builder.cancelableOutside;
    }

    @Override
    protected float getDimAmount() {
        return builder.dim;
    }

    @Override
    protected int getLayoutId() {
        return builder.layoutId;
    }

    @Override
    protected int getDialogAnimationRes() {
        return builder.animationRes;
    }

    @Override
    protected void bindView(View view) {
        ViewHelper viewHelper = new ViewHelper(view);
        if (builder.bindView != null) {
            builder.bindView.bindView(viewHelper, this);
        }

        if (builder.clickIds != null && builder.clickIds.length > 0 && builder.viewClick != null) {
            for (int i = 0; i < builder.clickIds.length; i++) {
                onViewClicked(viewHelper, builder.clickIds[i]);
            }
        }

    }

    private void onViewClicked(final ViewHelper helper, final int id) {
        helper.setOnClickListener(id, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.viewClick.viewClick(helper, view, LDialog.this);
            }
        });
    }

    /*********************************************************************
     * 使用Builder模式实现
     *
     */
    public static class Builder {
        private int layoutId;
        private int animationRes = -1;
        private int[] clickIds;
        private OnViewClick viewClick;
        private OnBindView bindView;
        private float dim = -1;
        private boolean cancelableOutside;
        private int gravity = -1;
        private int widthPx;
        private int heightPx;
        private FragmentManager fragmentManager;

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        /**
         * 传入弹窗xmL布局文件
         *
         * @return
         */
        public Builder setLayoutId(@LayoutRes int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationRes
         * @return
         */
        public Builder setDialogAnimationRes(int animationRes) {
            this.animationRes = animationRes;
            return this;
        }

        /**
         * 通过回调拿到弹窗布局控件对象
         *
         * @return
         */
        public Builder setOnBindView(OnBindView bindView) {
            this.bindView = bindView;
            return this;
        }

        /**
         * 弹窗控件点击回调
         *
         * @param viewClick
         * @return
         */
        public Builder setOnViewClick(OnViewClick viewClick) {
            this.viewClick = viewClick;
            return this;
        }

        /**
         * 添加弹窗控件的点击事件
         *
         * @param clickIds 传入需要点击的控件id
         * @return
         */
        public Builder addOnClick(int... clickIds) {
            this.clickIds = clickIds;
            return this;
        }

        /**
         * 设置弹窗背景透明度(0-1f)
         *
         * @param dim
         * @return
         */
        public Builder setDimAmount(float dim) {
            this.dim = dim;
            return this;
        }

        /**
         * 设置弹窗在弹窗区域外是否可以取消
         *
         * @return
         */
        public Builder setCancelableOutside(boolean cancelableOutside) {
            this.cancelableOutside = cancelableOutside;
            return this;
        }

        /**
         * 设置弹窗在屏幕中显示的位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 设置弹窗宽度(单位:像素)
         *
         * @param widthPx
         * @return
         */
        public Builder setWidth(int widthPx) {
            this.widthPx = widthPx;
            return this;
        }

        /**
         * 设置弹窗高度(px)
         *
         * @param heightPx
         * @return
         */
        public Builder setHeight(int heightPx) {
            this.heightPx = heightPx;
            return this;
        }

        /**
         * 创建LDiloag实例
         *
         * @return
         */
        public LDialog build() {
            LDialog lDialog = new LDialog();
            lDialog.setBuilder(this);
            return lDialog;
        }
    }
}
