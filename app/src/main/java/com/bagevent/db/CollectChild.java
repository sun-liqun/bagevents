package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by zwj on 2016/7/13.
 */
@Table(database = AppDatabase.class)
public class CollectChild extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public int collectIsSync;//同步状态
    @Column
    public int eventId;
    @Column
    public int collectionId;
    @Column
    public String attendeeCheckInTime;
    @Column
    public String attendeeBarcode;

    @Column
    public int isLegal;//非法数据

}
