package com.bagevent.view;

import android.content.Context;
import android.widget.TextView;

import com.bagevent.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class CustomMarkView extends MarkerView {
    private TextView tvContent;
    private TextView tvContent1;
    private float income;
    private float attendee;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent= (TextView) findViewById(R.id.tvContent);
        tvContent1 = (TextView) findViewById(R.id.tvContent1);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        DecimalFormat format = new DecimalFormat( "0.00 ");
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText("￥" + format.format(income));
            tvContent1.setText(Utils.formatNumber(attendee, 0, true) + "人");
        } else {
            tvContent.setText("￥" + format.format(income));
            tvContent1.setText(Utils.formatNumber(attendee, 0, true) + "人");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public void setValue(float income,float attendee) {
        this.income = income;
        this.attendee = attendee;
    }

}
