package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/10/31.
 */
@Table(database = AppDatabase.class)
public class BackupCheckin extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int eventId;
    @Column
    public int attendId;

    @Column
    public String barcodes;

    @Column
    public int checkins;
    @Column
    public String checkinCodes;
    @Column
    public String checkinTimes;
    @Column
    public String names;
    @Column
    public String notes;
    @Column
    public String gsonUser;
    @Column
    public int payStatuss;
    @Column
    public String refCodes;
    @Column
    public int ticketIds;
}
