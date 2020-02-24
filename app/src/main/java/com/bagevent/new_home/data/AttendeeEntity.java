package com.bagevent.new_home.data;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/09
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class AttendeeEntity implements MultiItemEntity {
    private ArrayList<CollectionAttendeeData.CollectionList> attendeeList;

    private CollectionAttendeeData.CollectionList collectionList;
    private int type;

    public static final int TYPE_TAG = 0;
    public static final int TYPE_REMOVE = 1;
    public static final int TYPE_ITEM = 2;

    @Override
    public int getItemType() {
        return type;
    }

    public AttendeeEntity(int type) {
        this.type = type;
    }

    public AttendeeEntity(int type,CollectionAttendeeData.CollectionList collectionList) {
        this.type = type;
        this.collectionList = collectionList;
    }

    public CollectionAttendeeData.CollectionList getCollectionList() {
        return collectionList;
    }

    public int getType() {
        return type;
    }
}
