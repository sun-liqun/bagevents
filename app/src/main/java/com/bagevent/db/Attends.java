package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/6/7.
 */
@Table(database = AppDatabase.class)
public class Attends extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public int auditSync;

    @Column
    public int addSync;

    @Column
    public int modifyIsSync;
    @Column
    public int isSync;

    @Column
    public String attendeeAvatar;

    @Column
    public String strSort;
    @Column
    public int eventId;
    @Column
    public int attendId;
    @Column
    public int audits;
    @Column
    public String auditTimes;
    @Column
    public String avatars;
    @Column
    public String barcodes;
    @Column
    public int buyWays;
    @Column
    public String cellphones;
    @Column
    public int checkins;
    @Column
    public String checkinCodes;
    @Column
    public String checkinTimes;
    @Column
    public String emailAddrs;
    @Column
    public String names;
    @Column
    public String notes;
    @Column
    public int orderIds;
    @Column
    public int payStatuss;
    @Column
    public String pinyinNames;
    @Column
    public String refCodes;
    @Column
    public int ticketIds;
    @Column
    public double ticketPrices;
    @Column
    public String updateTimes;
    @Column
    public String weixinIds;
    @Column
    public String gsonUser;
    @Column
    public String addMap;//添加参会人员的map
    @Column
    public String badgeMap;
    @Column
    public String modifyMap;
    @Column
    public int userId;
    @Column
    public String imgPath;
    @Column
    public String productIds;
    @Column
    public int orderItemId;
    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public int buyingGroupId;
    @Column
    public int bgState;
    @Column
    public boolean hasBuyingGrouping;
}
