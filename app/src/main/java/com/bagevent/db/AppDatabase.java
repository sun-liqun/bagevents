package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by zwj on 2016/6/3.
 *
 * 数据库名称为AppDatabase
 *
 *数据库版本号=1
 */
@Database(name = AppDatabase.NAME,version = AppDatabase.version)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int version = 16;
}
