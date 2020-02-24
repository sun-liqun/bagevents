package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */
@Table(database = AppDatabase.class)
public class Chat extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public long userId;
    @Column
    public int contactId;
    @Column
    public int sort;
    @Column
    public long updateTime;
    @Column
    public int unReadCount;
    @Column
    public String lastMessage;
    @Column
    public int peopleId;
    @Column
    public String token;
    @Column
    public int type;
    @Column
    public long createTime;
    @Column
    public String eventId;
    @Column
    public String showName;
    @Column
    public String pinyinName;
    @Column
    public String avatar;
    @Column
    public boolean organizer;
    @Column
    public boolean sys;
    @Column
    public boolean isTop;
    @Column
    public String top;
    @Column
    public boolean isRemove;
}
