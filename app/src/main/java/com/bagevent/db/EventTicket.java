package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.QueryModel;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zwj on 2016/6/3.
 */
@Table(database = AppDatabase.class)
public class EventTicket extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int eventIds;
    @Column
    public int audits;
    @Column
    public boolean auditTickets;
    @Column
    public String descriptions;
    @Column
    public String endSaleTimes;
    @Column
    public boolean freeTickets;
    @Column
    public int hideStatuss;
    @Column
    public int selledTimeStatuss;
    @Column
    public String showDescriptions;
    @Column
    public String showTicketNames;
    @Column
    public int limitCounts;
    @Column
    public int maxCounts;
    @Column
    public int salesTimes;
    @Column
    public int selledCounts;
    @Column
    public int checkinCounts;
    @Column
    public int sorts;
    @Column
    public String startSaleTimes;
    @Column
    public int statuss;
    @Column
    public int ticketCounts;
    @Column
    public int ticketFees;
    @Column
    public int ticketIds;
    @Column
    public String ticketNames;
    @Column
    public float ticketPrices;
    @Column
    public boolean validTickets;

}
