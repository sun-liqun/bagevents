package com.bagevent.db.migration;

import com.bagevent.db.AppDatabase;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by zwj on 2016/8/26.
 */
@Migration(version = AppDatabase.version,database = AppDatabase.class)
public class NewEventMigrations extends AlterTableMigration<EventList> {
    public NewEventMigrations(Class<EventList> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.INTEGER,EventList_Table.sType.getNameAlias().name());
        addColumn(SQLiteType.REAL, EventList_Table.rmbIncome.getNameAlias().name());
        addColumn(SQLiteType.REAL, EventList_Table.dollarIncome.getNameAlias().name());
        addColumn(SQLiteType.INTEGER, EventList_Table.newAttendee.getNameAlias().name());
        addColumn(SQLiteType.INTEGER,EventList_Table.exType.getNameAlias().name());
    }
}
