package com.bagevent.new_home.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/06
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class AttendeeInfoEntity implements MultiItemEntity {
    private AttendeeInfo.RespObjectData respObjectData;
    private AttendeeInfo.TagList tagList;
    private AttendeeInfo.FromFieldList fromFieldList;
//    private Test.ApiAttendee apiAttendee;
//    private Test.AttendeeMap attendeeMap;
    private String value;

    private String name;
    private int type;

    public String getValue() {
        return value;
    }

    public static final int TYPE_LINE = 0;
    public static final int TYPE_NAME = 1;
    public static final int TYPE_TAG = 2;
    public static final int TYPE_INFO = 3;
    public static final int TYPE_NO_TAG = 4;


//    public Test.ApiAttendee getApiAttendee() {
//        return apiAttendee;
//    }

    public AttendeeInfoEntity(int type, AttendeeInfo.TagList tagList) {
        this.type=type;
        this.tagList=tagList;
    }

    public AttendeeInfoEntity(AttendeeInfo.TagList tagList) {
        this.tagList = tagList;
    }

    public int getItemType() {
        return type;
    }


//    public AttendeeInfoEntity(int type, Test.FromFieldList fromFieldList, Test.AttendeeMap attendeeMap){
//        this.type=type;
//        this.fromFieldList=fromFieldList;
//        this.attendeeMap=attendeeMap;
//    }

    public AttendeeInfoEntity(int type,String name){
        this.type=type;
        this.name=name;
    }

    public AttendeeInfoEntity(int type, AttendeeInfo.RespObjectData respObjectData){
        this.type=type;
        this.respObjectData=respObjectData;
    }

    public AttendeeInfoEntity(int type, AttendeeInfo.FromFieldList fromFieldList, String value){
        this.type=type;
        this.fromFieldList=fromFieldList;
        this.value=value;
    }

    public AttendeeInfo.RespObjectData getRespObjectData() {
        return respObjectData;
    }

    public AttendeeInfo.TagList getTagList() {
        return tagList;
    }

    public AttendeeInfo.FromFieldList getFromFieldList() {
        return fromFieldList;
    }

//    public Test.AttendeeMap getAttendeeMap() {
//        return attendeeMap;
//    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
