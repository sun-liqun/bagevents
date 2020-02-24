package com.bagevent.new_home.data;


import com.pickerview.model.IPickerViewData;

/**
 * Created by Sai on 15/11/22.
 */
public class ProvinceBean implements IPickerViewData {
    private long id;
    private String name;

    public ProvinceBean(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //这个用来显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }
}
