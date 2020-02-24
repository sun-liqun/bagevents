package com.bagevent.new_home.data;

import android.widget.EditText;

import com.bagevent.view.FormChildItem;

/**
 * Created by zwj on 2016/9/23.
 */
public class MarkChildFormData {
    private FormChildItem item;
    private int id;
    private String key;
    private String oldValue;

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FormChildItem getItem() {
        return item;
    }

    public void setItem(FormChildItem item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
