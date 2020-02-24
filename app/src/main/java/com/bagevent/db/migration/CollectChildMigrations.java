package com.bagevent.db.migration;

import com.bagevent.db.AppDatabase;
import com.bagevent.db.CollectChild;
//import com.bagevent.db.CollectChild_Table;
import com.bagevent.db.CollectChild_Table;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by zwj on 2016/9/28.
 */
@Migration(version = AppDatabase.version,database = AppDatabase.class)
public class CollectChildMigrations extends AlterTableMigration<CollectChild> {

    public CollectChildMigrations(Class<CollectChild> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.INTEGER, CollectChild_Table.isLegal.getNameAlias().name());
    }
}
