package com.bagevent.activity_manager.manager_fragment.data;

import android.view.View;

import com.bagevent.view.wheelview.WheelView;

/**
 * Created by zwj on 2016/7/1.
 */
public class AddDatePicker {

    private int filed;
    private WheelView year;
    private WheelView month;
    private WheelView day;

    private String date;
    private String filedName;

    public WheelView getYear() {
        return year;
    }

    public void setYear(WheelView year) {
        this.year = year;
    }

    public WheelView getMonth() {
        return month;
    }

    public void setMonth(WheelView month) {
        this.month = month;
    }

    public WheelView getDay() {
        return day;
    }

    public void setDay(WheelView day) {
        this.day = day;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFiled() {
        return filed;
    }

    public void setFiled(int filed) {
        this.filed = filed;
    }
}
