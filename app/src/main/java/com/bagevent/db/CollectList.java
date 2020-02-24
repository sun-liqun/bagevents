package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/7/15.
 */
@Table(database = AppDatabase.class)
public class CollectList extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public int eventId;
    @Column
    public int collectPointId;
    @Column
    public String collectionName;
    @Column
    public String userEmail;
    @Column
    public int collectionType;
    @Column
    public int isAllTicket;
    @Column
    public int availableDateType;
    @Column
    public String startTime;
    @Column
    public String endTime;
    @Column
    public int isBegin;
    @Column
    public int isRepeat;
    @Column
    public int export;
    @Column
    public int checkinCount;
    @Column
    public String ticketStr;
    @Column
    public String ticketIdStr;
    @Column
    public int showNum;
    @Column
    public int isAllProduct;//0:所有商品 1 ：指定商品
    @Column
    public int limitType;
    @Column
    public String productStr;
    @Column
    public String productIdStr;

}
