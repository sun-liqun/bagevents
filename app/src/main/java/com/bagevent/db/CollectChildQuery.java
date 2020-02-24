package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;
import com.raizlabs.android.dbflow.structure.BaseQueryModel;

/**
 * Created by zwj on 2016/11/16.
 */
@QueryModel(database = AppDatabase.class)
public class CollectChildQuery extends BaseQueryModel {

    @Column
    public int attendId;

    @Column
    public String names;

    @Column
    public String pinyinNames;

    @Column
    public String avatars;

    @Column
    public String attendeeCheckInTime;
}
