package com.bagevent.db.migration;

import com.bagevent.db.AppDatabase;
import com.bagevent.db.CollectList;
import com.bagevent.db.CollectList_Table;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by zwj on 2016/11/7.
 */
@Migration(version = AppDatabase.version,database = AppDatabase.class)
public class CollectListMigrations extends AlterTableMigration<CollectList> {

    public CollectListMigrations(Class<CollectList> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.INTEGER, CollectList_Table.showNum.getNameAlias().name());
        addColumn(SQLiteType.TEXT,CollectList_Table.ticketIdStr.getNameAlias().name());
    }
}
