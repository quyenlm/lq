package com.tencent.component.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.tencent.component.db.DefaultSQLiteOpenHelper;
import com.tencent.component.db.EntityManager;
import com.tencent.component.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class SdcardSQLiteOpenHelper implements ISQLiteOpenHelper {
    private static ConcurrentHashMap<String, SdcardSQLiteOpenHelper> sInstanceMap = new ConcurrentHashMap<>();
    private String mDBPath;
    private DefaultSQLiteOpenHelper.DBHelper mOpenHelper;

    public void init(Context context, String name, int version) {
        init(context, name, version, (EntityManager.UpdateListener) null);
    }

    public void init(Context context, String name, int version, EntityManager.UpdateListener updateListener) {
        if (this.mOpenHelper == null) {
            this.mOpenHelper = new DefaultSQLiteOpenHelper.DBHelper(new SdcardDatabaseContext(context, this.mDBPath), name, (SQLiteDatabase.CursorFactory) null, version, updateListener);
        }
    }

    public ISQLiteDatabase getWritableDatabase() {
        return new DefaultSQLiteDatabase(this.mOpenHelper.getWritableDatabase());
    }

    public ISQLiteDatabase getReadableDatabase() {
        return new DefaultSQLiteDatabase(this.mOpenHelper.getReadableDatabase());
    }

    public static SdcardSQLiteOpenHelper getInstance(String dbName, String dbPath) {
        if (!TextUtils.isEmpty(dbPath)) {
            dbName = dbName + "_" + dbPath.hashCode();
        }
        SdcardSQLiteOpenHelper openHelper = sInstanceMap.get(dbName);
        if (openHelper == null) {
            synchronized (SdcardSQLiteOpenHelper.class) {
                openHelper = sInstanceMap.get(dbName);
                if (openHelper == null) {
                    openHelper = new SdcardSQLiteOpenHelper(dbPath);
                }
            }
        }
        return openHelper;
    }

    private SdcardSQLiteOpenHelper(String dbPath) {
        this.mDBPath = dbPath;
    }

    public static class SdcardDatabaseContext extends ContextWrapper {
        private String mDBPath;

        public SdcardDatabaseContext(Context base, String dbPath) {
            super(base);
            this.mDBPath = dbPath;
        }

        public File getDatabasePath(String name) {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
                return null;
            }
            String dbDir = this.mDBPath;
            if (TextUtils.isEmpty(dbDir)) {
                dbDir = FileUtil.getExternalCacheDirExt(getBaseContext(), "databases");
            }
            if (TextUtils.isEmpty(dbDir)) {
                dbDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Tecent" + File.separator + getBaseContext().getPackageName() + File.separator + "databases";
            }
            String dbPath = dbDir + Constants.URL_PATH_DELIMITER + name;
            File dirFile = new File(dbDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            boolean isFileCreateSuccess = false;
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            if (!isFileCreateSuccess) {
                return null;
            }
            return dbFile;
        }

        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), (SQLiteDatabase.CursorFactory) null);
        }

        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), (SQLiteDatabase.CursorFactory) null);
        }
    }
}
