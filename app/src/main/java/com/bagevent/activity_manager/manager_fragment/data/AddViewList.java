package com.bagevent.activity_manager.manager_fragment.data;

import android.widget.CheckBox;

/**
 * Created by zwj on 2016/6/30.
 */
public class AddViewList {
    private int id;
    private CheckBox checkBox;
    private int fieldId;

    private String fieldName;

    private String optionItems;
    private String showName;

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getOptionItems() {
        return optionItems;
    }

    public void setOptionItems(String optionItems) {
        this.optionItems = optionItems;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    private int required;

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }
}
