package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/10/25.
 * 采集点备份数据库
 */
@Table(database = AppDatabase.class)
public class BackupCollection extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int eventId;
    @Column
    public int collectionId;
    @Column
    public String attendeeName;
    @Column
    public String attendeePinyinName;
    @Column
    public String attendeeImg;
    @Column
    public String attendeeCheckInTime;
    @Column
    public String attendeeBarcode;
}
