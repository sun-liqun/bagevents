package com.bagevent.activity_manager.manager_fragment.data;

import android.widget.EditText;

/**
 * Created by zwj on 2016/6/30.
 */
public class AddEditTextList {
    private EditText editText;
    private int fieldId;
    private int required;
    private String showName;
    private String filedName;
    private String filedTypeName;


    public String getFiledTypeName() {
        return filedTypeName;
    }

    public void setFiledTypeName(String filedTypeName) {
        this.filedTypeName = filedTypeName;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
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

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
