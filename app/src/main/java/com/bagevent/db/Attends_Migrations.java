package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by zwj on 2016/8/16.
 */
@Migration(version = AppDatabase.version,database = AppDatabase.class)
public class Attends_Migrations extends AlterTableMigration<Attends> {

    public Attends_Migrations(Class<Attends> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        addColumn(SQLiteType.TEXT,Attends_Table.badgeMap.getNameAlias().name());
        addColumn(SQLiteType.TEXT,Attends_Table.modifyMap.getNameAlias().name());
        addColumn(SQLiteType.INTEGER,Attends_Table.userId.getNameAlias().name());
        addColumn(SQLiteType.TEXT,Attends_Table.imgPath.getNameAlias().name());
        addColumn(SQLiteType.TEXT,Attends_Table.firstName.getNameAlias().name());
        addColumn(SQLiteType.TEXT,Attends_Table.lastName.getNameAlias().name());
    }

}
