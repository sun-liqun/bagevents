package com.bagevent.activity_manager.manager_fragment.data;

import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by zwj on 2016/8/16.
 */
public class AddImage {
    private ImageView ivAdd;
    private FrameLayout fm_del;
    private int fm_id;
    private int id;
    private int fieldId;
    private int required;
    private String showName;
    private String filedName;
    private String imgPath;

    public int getFm_id() {
        return fm_id;
    }

    public void setFm_id(int fm_id) {
        this.fm_id = fm_id;
    }

    public FrameLayout getFm_del() {
        return fm_del;
    }

    public void setFm_del(FrameLayout fm_del) {
        this.fm_del = fm_del;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getIvAdd() {
        return ivAdd;
    }

    public void setIvAdd(ImageView ivAdd) {
        this.ivAdd = ivAdd;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }
}
