package com.bagevent.view.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;

/**
 * Created by zwj on 2016/8/29.
 */
public class CoodViewGroup extends FrameLayout {
    private Context mContext;
    private CoordView coordView;
    private FrameLayout fm;
    private int idsSize = -1;
    private int colorIds[] = {R.color.a00a0eb,R.color.a59C60A,R.color.a797d7f,R.color.acolorAccent,R.color.acolorPrimary,R.color.aFF1D00,R.color.black};

    public CoodViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CoodViewGroup(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public CoodViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.viewgroup_cood, this, true);
        fm = (FrameLayout) findViewById(R.id.fm_cood);
        coordView = (CoordView)findViewById(R.id.coordview);
        coordView = (CoordView) findViewById(R.id.coordview);
    }

    public CoordView returnChart() {
        return coordView;
    }

    public void setIdsLength(int length) {
        final int ids[] = new int[length];//存储生成按钮的Id
        idsSize = length;
        LinearLayout ll = new LinearLayout(mContext);
        fm.measure(0,0);//计算父控件的宽和高,便于设置子控件的宽高
        int width = fm.getMeasuredWidth() / idsSize - 10;
        int height = fm.getMeasuredHeight();
        ll.setOrientation(LinearLayout.HORIZONTAL);
    //    Log.e("fasdf",idsSize +"F");
        for (int i = 0; i < idsSize; i++) {
            TextView tv = new TextView(mContext);
            tv.setId(View.generateViewId());
            ids[i] = tv.getId();
            tv.setWidth(width);
            tv.setHeight(height);
            tv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transAlpha));
            ll.addView(tv);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < ids.length; j++) {
                        //   Log.e("fdf",ids[j]+"");
                        if(ids[j] == view.getId()) {
                          //  Toast.makeText(mContext,"您点击了"+ids[j]+"个区域",Toast.LENGTH_SHORT).show();
                            coordView.clickDrawText(j);
                        }
                    }
                }
            });
        }
        fm.addView(ll,0);
    }
}
