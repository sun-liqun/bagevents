package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/7/27.
 */
@Table(database = AppDatabase.class)
public class EventList extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String mark;
    @Column
    public String address;
    @Column
    public int userId;
    @Column
    public int attendeeCount;
    @Column
    public int auditCount;
    @Column
    public int brand;
    @Column
    public int checkinCount;
    @Column
    public int collectInvoice;
    @Column
    public String endTime;
    @Column
    public int eventId;
    @Column
    public String eventName;
    @Column
    public int eventType;
    @Column
    public int nameType;
    @Column
    public double income;
    @Column
    public String logo;
    @Column
    public String officialWebsite;
    @Column
    public int participantsCount;
    @Column
    public String startTime;
    @Column
    public int status;////初始化：-1 0 ：草稿  1：己发布 2 正在进行 3 已经结束 4 暂停售票 5 等待审核 10 取消发布
    @Column
    public int ticketCount;
    @Column
    public int websiteId;
    @Column
    public String collectionName;
    @Column
    public int collectPointId;
    @Column
    public int export;
    @Column
    public int isRepeat;
    @Column
    public String eventTypes;
    @Column
    public String ticketIds;
    @Column
    public double rmbIncome;
    @Column
    public double dollarIncome;
    @Column
    public int newAttendee;

    @Column
    public int sType;

    @Column
    public int exType;
}
