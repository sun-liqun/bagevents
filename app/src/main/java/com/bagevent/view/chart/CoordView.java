package com.bagevent.view.chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bagevent.R;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/8/24.
 */
public class CoordView extends View {

    private Context mContext;
    //画纵横轴
    private Paint axisPaint;

    //画点
    private Paint mDotPaint;

    //画线
    private Paint mLinePaint;

    //画提示信息
    private Paint mHintPaint;


    //画路径
    private Path mPath;

    //View 的宽和高
    private int mWidth, mHeight;

    //Y轴字体的大小
    private float mYAxisFontSize = 32;

    //线的颜色
    private int mLineColor = Color.parseColor("#fe6900");
    private int mDotColor = Color.parseColor("#fe6900");

    //点的半径
    private float mPointRadius = 10;

    //X轴的文字
    private String[] mXAxis = {};

    //Y轴的文字
    private float[] mYAxis = {};

    private ArrayList<PointF> mPoint = new ArrayList<PointF>();//需要绘制的点
    private ArrayList<PointF> mTempPoint = new ArrayList<PointF>();//获得点击事件绘制数据
    private int rangeCount = 20;//两点之间生成点的数量
    private PointF pn;
    private int index = -1;

    private PathMeasure pathMeasure = new PathMeasure();
    private float mAnimatorValue;
    private Path mDst = new Path();

    public CoordView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public CoordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public CoordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = 400;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
            mHeight = 300;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mXAxis.length == 0 || mYAxis.length == 0) {
            return;
        }
        /**
         * 画Y轴
         *计算Y轴的间距
         */
        mLinePaint.setColor(mLineColor);
        mDotPaint.setColor(mDotColor);
        mHintPaint.setColor(mDotColor);
        axisPaint.setColor(Color.TRANSPARENT);
        float max = getMaxValue(mYAxis);
        float[] yPoints = new float[mYAxis.length];
        float yInterval = 0;
        if( max != 0) {
            yInterval = (mHeight - 120) / max;
        }else {
            yInterval = (mHeight - 120) / 2;
        }
        //    Log.e("ee",yInterval+"");
        for (int i = 0; i < mYAxis.length; i++) {
            // Log.e("ee",yInterval+"");
            canvas.drawText(mYAxis[i] + "", 0, (mHeight - 48) - (mYAxis[i] * yInterval) - 30, axisPaint);
            yPoints[i] = (float) ((mHeight - 48) - (mYAxis[i] * yInterval) - 30);
        }
        /**
         * 画X轴
         * x轴的刻度集合
         */
        //计算Y轴开始的原点坐标
        float[] xPoints = new float[mXAxis.length];
        //计算X轴的间距
        float xInterval = mWidth / (mXAxis.length + 1);
        //  Log.e("fdf",mWidth+"");
        axisPaint.setColor(Color.BLACK);
        for (int i = 0; i < mXAxis.length; i++) {
            canvas.drawText(mXAxis[i], xInterval / 2 + i * xInterval, mHeight, axisPaint);
            xPoints[i] = i * xInterval + xInterval / 2 + 42;
        }

        /**
         * 画点
         */
        mPoint.clear();
        for (int i = 0; i < mXAxis.length; i++) {
            PointF pointf = new PointF();
            pointf.x = xPoints[i];
            pointf.y = yPoints[i];
            mPoint.add(pointf);
            mTempPoint.add(pointf);

            if (i == 0 || i == 6) {
                mPoint.add(pointf);
            }
            canvas.drawCircle(pointf.x, pointf.y, mPointRadius, mDotPaint);
        }
        mPath.reset();
        mPath.moveTo(mPoint.get(0).x, mPoint.get(0).y);

        for (int i = 1; i < mPoint.size() - 2; i++) {
            PointF p0 = mPoint.get(i - 1);
            PointF p1 = mPoint.get(i);
            PointF p2 = mPoint.get(i + 1);
            PointF p3 = mPoint.get(i + 2);
            for (int j = 0; j < rangeCount; j++) {
                pn.x = 0;
                pn.y = 0;
                float t = (float) j * (1.0f / (float) rangeCount);
                float tt = t * t;
                float ttt = tt * t;
                pn.x = (float) (0.5 * (2 * p1.x + (p2.x - p0.x) * t + (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * tt + (3 * p1.x - p0.x - 3 * p2.x + p3.x) * ttt));
                pn.y = (float) (0.5 * (2 * p1.y + (p2.y - p0.y) * t + (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * tt + (3 * p1.y - p0.y - 3 * p2.y + p3.y) * ttt));
                mPath.lineTo(pn.x, pn.y);
            }
            canvas.drawCircle(p0.x, p0.y, mPointRadius, mDotPaint);
            mPath.lineTo(p2.x, p2.y);
        }

//        pathMeasure.setPath(mPath,false);
//        float length = pathMeasure.getLength();
//        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
//                invalidate();
//            }
//        });
//        valueAnimator.setDuration(2000);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.start();
//        mDst.reset();
//        // 硬件加速的BUG
//        mDst.lineTo(0,0);
//        float stop = length * mAnimatorValue;
//        pathMeasure.getSegment(0, stop, mDst, true);
        mPath.lineTo(mPoint.get(mPoint.size() - 1).x, mPoint.get(mPoint.size() - 1).y);
        canvas.drawPath(mPath, mLinePaint);
        if (index != -1) {
            canvas.drawText(mYAxis[index] + "", xPoints[index] - 30, yPoints[index] - 13, mHintPaint);
        }
    }

    private void initPaint() {
        axisPaint = new Paint();
        axisPaint.setTextSize(mYAxisFontSize);
        axisPaint.setColor(Color.BLACK);

        mDotPaint = new Paint();
        mDotPaint.setStyle(Paint.Style.FILL);
      //  mDotPaint.setColor(mDotColor);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
    //    mLinePaint.setColor(mLineColor);

        mHintPaint = new Paint();
        mHintPaint.setAntiAlias(true);
        mHintPaint.setTextSize(40);
      //  mHintPaint.setColor(mDotColor);
        //绘制路径
        mPath = new Path();
        //两点之间生成的点
        pn = new PointF();
    }

    private float getMaxValue(float[] maxValue) {
        float y = maxValue[0];
   //     Log.e("fdfds", y + "");

        for (int i = 1; i < maxValue.length; i++) {

            if (y < maxValue[i]) {
                y = maxValue[i];
            }
          //  Log.e("fdfd", y + "");
        }
        return y;
    }

    /**
     * 设置Y轴文字
     *
     * @param yItem
     */
    public void setYItem(float[] yItem) {
        index = -1;
        mYAxis = yItem;
    }

    /**
     * 设置X轴文字
     *
     * @param xItem
     */
    public void setXItem(String[] xItem) {
        mXAxis = xItem;
    }

    /**
     * 绘制选中点的文字
     *
     * @param i
     */
    public void clickDrawText(int i) {
        index = i;
        invalidate();
    }

    public void setPainColor(String color) {
        mLineColor = Color.parseColor(color);
        mDotColor = Color.parseColor(color);
    }
}
