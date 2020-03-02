package com.bagevent.activity_manager.manager_fragment.data;

/**
 * Created by zwj on 2016/6/28.
 */
public class FormData {
    private String formName;
    private int formFieldId;
    private String fieldTypeName;

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getFormFieldId() {
        return formFieldId;
    }

    public void setFormFieldId(int formFieldId) {
        this.formFieldId = formFieldId;
    }

    @Override
    public String toString() {
        return "FormData{" +
                "formName='" + formName + '\'' +
                ", formFieldId=" + formFieldId +
                '}';
    }
}
