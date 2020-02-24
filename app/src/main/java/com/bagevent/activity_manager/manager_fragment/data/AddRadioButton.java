package com.bagevent.activity_manager.manager_fragment.data;

import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * Created by zwj on 2016/7/1.
 */
public class AddRadioButton {
    private int checkBoxId;
    private CheckBox radioButton;
    private int fieldId;
    private int required;
    private String fieldName;
    private String showName;

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getCheckBoxId() {
        return checkBoxId;
    }

    public void setCheckBoxId(int checkBoxId) {
        this.checkBoxId = checkBoxId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public CheckBox getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(CheckBox radioButton) {
        this.radioButton = radioButton;
    }
}
