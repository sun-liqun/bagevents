package com.bagevent.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bagevent.R;


public class ChangeColorIconWithTextView extends View {

	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;

    private Paint mCirclePaint;//画圆

	/**
	 * 颜色
	 */
	private int mColor = 0xFF45C01A;
	/**
	 * 透明度 0.0-1.0
	 */
	private float mAlpha = 0f;
	/**
	 * 图标
	 */
	private Bitmap mIconBitmap;
	/**
	 * 限制绘制icon的范围
	 */
	private Rect mIconRect;

	public ChangeColorIconWithTextView(Context context) {
		super(context);
        Log.e("fdf","fdf");
	}

	/**
	 * 初始化自定义属性值
	 * 
	 * @param context
	 * @param attrs
	 */
	public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(ContextCompat.getColor(context,R.color.fe6900));
        mCirclePaint.setStyle(Paint.Style.FILL);

        // 获取设置的图标
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SlideMode);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlideMode_a_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    if (drawable != null) {
                        mIconBitmap = drawable.getBitmap();
                    }
                    break;
            }
        }
        a.recycle();

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 得到绘制icon的宽
		int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom());

		int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
		int top = getMeasuredHeight() / 2 - bitmapWidth
				/ 2;
		// 设置icon的绘制范围
		mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);

	}

	@Override
	protected void onDraw(Canvas canvas) {
        canvas.drawCircle(320,320,100,mCirclePaint);

		int alpha = (int) Math.ceil((255 * 1.0f));
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
		setupTargetBitmap(alpha);
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}

	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
	}


	public void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
	}


}
