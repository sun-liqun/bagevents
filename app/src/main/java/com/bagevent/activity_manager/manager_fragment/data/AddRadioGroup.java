package com.bagevent.activity_manager.manager_fragment.data;

import android.widget.RadioGroup;

/**
 * Created by zwj on 2016/7/1.
 */
public class AddRadioGroup {
    private RadioGroup radioGroup;
    private int fieldId;

    private int required;

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

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }
}
