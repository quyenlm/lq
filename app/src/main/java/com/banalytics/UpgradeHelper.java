package com.banalytics;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

class UpgradeHelper {
    private SQLiteDatabase databaseDao;
    private int newVersion;
    private int oldVersion;
    private UpgradeScript rebuildTable = new UpgradeScript(2, 3) {
        public String getScriptSQL() {
            return "DROP TABLE 'ba_event';CREATE TABLE `ba_event` (`app_key` VARCHAR , `channel` VARCHAR , `cmd_type` VARCHAR , `country` VARCHAR , `description` VARCHAR , `device_id` VARCHAR , `device_info` VARCHAR , `manufacturer` VARCHAR , `referrer` VARCHAR , `api_level` INTEGER , `app_version` INTEGER , `event_id` INTEGER PRIMARY KEY AUTOINCREMENT , `status` INTEGER DEFAULT 0 , `time_stamp` INTEGER , `user_id` INTEGER );";
        }
    };
    private UpgradeScript rebuildTable2 = new UpgradeScript(3, 4) {
        public String getScriptSQL() {
            return "DROP TABLE 'ba_event';CREATE TABLE `ba_event` (`app_key` VARCHAR , `channel` VARCHAR , `cmd_type` VARCHAR , `country` VARCHAR , `description` VARCHAR , `device_id` VARCHAR , `device_info` VARCHAR , `manufacturer` VARCHAR , `referrer` VARCHAR , `api_level` INTEGER , `app_version` INTEGER , `event_id` INTEGER PRIMARY KEY AUTOINCREMENT , `status` INTEGER DEFAULT 0 , `time_stamp` INTEGER , `user_id` VARCHAR);";
        }
    };
    private ArrayList<UpgradeScript> scripts;

    private static abstract class UpgradeScript {
        private int minVersion;
        private int scriptVersion;

        public abstract String getScriptSQL();

        UpgradeScript(int minVersion2, int scriptVersion2) {
            this.minVersion = minVersion2;
            this.scriptVersion = scriptVersion2;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldRunScript(int oldVersion, int newVersion) {
            return oldVersion >= this.minVersion && oldVersion < this.scriptVersion && newVersion >= this.scriptVersion;
        }
    }

    UpgradeHelper(SQLiteDatabase database, int oldVersion2, int newVersion2) {
        this.oldVersion = oldVersion2;
        this.newVersion = newVersion2;
        this.databaseDao = database;
        this.scripts = new ArrayList<>();
        loadScripts();
    }

    private void loadScripts() {
        this.scripts.add(this.rebuildTable);
        this.scripts.add(this.rebuildTable2);
    }

    /* access modifiers changed from: package-private */
    public void runAllUpgrades() {
        try {
            Iterator<UpgradeScript> it = this.scripts.iterator();
            while (it.hasNext()) {
                UpgradeScript script = it.next();
                if (script.shouldRunScript(this.oldVersion, this.newVersion)) {
                    for (String statement : script.getScriptSQL().split(";")) {
                        if (!TextUtils.isEmpty(statement)) {
                            this.databaseDao.execSQL(statement);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
