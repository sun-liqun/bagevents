package com.bagevent.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bagevent.R;

import java.util.Random;

/**
 * Created by zwj on 2016/6/21.
 */
public class CircleTextView extends TextView{
    private float strokeWidth;
    private int ranColor;

    public CircleTextView(Context context) {
        super(context);
        Random random = new Random();
        ranColor = 0xff000000 | random.nextInt(0x00ffffff);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Random random = new Random();
        ranColor = 0xff000000 | random.nextInt(0x00ffffff);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Random random = new Random();
        ranColor = 0xff000000 | random.nextInt(0x00ffffff);
    }


    @Override
    public void draw(Canvas canvas) {

        Paint circlePaint = new Paint();
        circlePaint.setColor(ranColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        Paint strokePaint = new Paint();
        strokePaint.setColor(ranColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int  h = this.getHeight();
        int  w = this.getWidth();

        int diameter = ((h > w) ? h : w);
        int radius = diameter/2;

        this.setHeight(diameter);
        this.setWidth(diameter);

        canvas.drawCircle(diameter / 2 , diameter / 2, radius, strokePaint);

        canvas.drawCircle(diameter / 2, diameter / 2, radius-strokeWidth, circlePaint);

        super.draw(canvas);
    }


    private int getColor(int colorValue)
    {
        int color;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            color = ContextCompat.getColor(getContext(),colorValue);
        }
        else
        {
            color =  ContextCompat.getColor(getContext(),colorValue);
        }

        return color;
    }

    public void setStrokeWidth(int dp)
    {
        float scale = getContext().getResources().getDisplayMetrics().density;
        strokeWidth = dp*scale;

    }
}
