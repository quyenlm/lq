package com.tencent.component.net.download.multiplex.download;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.download.extension.DBHelper;

public class DownloadDBHelper {
    public static final String ANNOTATION = "annotation";
    public static final String ANNOTATIONEXT = "annotationext";
    public static final String[] COLUMNS = {"id", "url", "filename", FILEFOLDERPATH, TOTALSIZE, DOWNLOADEDSIZE, "status", CREATEDATE, DONEDATE, ISSUPPORTRESUME, REFERER, FLAG, COSTTIME, "etag", THREADNUM, ANNOTATION, EXTEND_1, EXTEND_2, EXTEND_3};
    public static final String COSTTIME = "costtime";
    public static final String CREATEDATE = "createdate";
    public static final String DONEDATE = "donedate";
    public static final String DOWNLOADEDSIZE = "downloadsize";
    public static final String ETAG = "etag";
    public static final String EXTEND_1 = "extend_1";
    public static final String EXTEND_2 = "extend_2";
    public static final String EXTEND_3 = "extend_3";
    public static final String FILEFOLDERPATH = "filefolderpath";
    public static final String FILENAME = "filename";
    public static final String FLAG = "flag";
    public static final String ID = "id";
    public static final String ISSUPPORTRESUME = "supportresume";
    public static final String REFERER = "referer";
    public static final String SQL_CREATE_INDEX = "CREATE INDEX url_fileName_index on download (url, filename);";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE download (id INTEGER PRIMARY KEY autoincrement, url TEXT, filename TEXT, filefolderpath TEXT, totalsize LONG, downloadsize LONG, status BYTE, supportresume INTEGER, createdate INTEGER, donedate INTEGER, referer TEXT DEFAULT '', flag INTEGER DEFAULT 0,costtime INTEGER,  INTEGER, etag TEXT, threadnum INTEGER DEFAULT 0,annotation TEXT, annotationext TEXT, extend_1 INTEGER DEFAULT 0,  extend_2 INTEGER DEFAULT 0,  extend_3 INTEGER DEFAULT 0);";
    public static final String SQL_DROP_INDEX = "DROP INDEX url_fileName_index;";
    public static final String SQL_DROP_TABLE = "DROP TABLE download;";
    public static final String STATUS = "status";
    public static final String TABLE_DOWNLOAD = "download";
    private static final String TAG = "DownloadDBHelper";
    public static final String THREADNUM = "threadnum";
    public static final String TOTALSIZE = "totalsize";
    public static final String URL = "url";
    private static final String WHERE_ID = "id=?";
    private static final String WHERE_PKG_NAME = "extend_1=?";
    private static final String WHERE_SKIN_ANME = "annotation=?";
    private static final String WHERE_URL = "url=?";
    private DBHelper mDBHelper = DBHelper.getPublicInstance();

    public DownloadDBHelper() {
        init();
    }

    public synchronized void init() {
        try {
            if (!this.mDBHelper.exist(TABLE_DOWNLOAD)) {
                this.mDBHelper.execSQL(SQL_CREATE_TABLE);
                this.mDBHelper.execSQL(SQL_CREATE_INDEX);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean addTask(DownloadTask task) {
        int i;
        if (task == null) {
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put("url", task.getTaskUrl());
        cv.put("filename", task.getFileName());
        cv.put(FILEFOLDERPATH, task.getFileFolderPath());
        cv.put(TOTALSIZE, Long.valueOf(task.getTotalSize()));
        cv.put(DOWNLOADEDSIZE, Long.valueOf(task.getDownloadedSize()));
        cv.put("status", Byte.valueOf(task.getStatus()));
        if (task.getIsSupportResume()) {
            i = 1;
        } else {
            i = 0;
        }
        cv.put(ISSUPPORTRESUME, Integer.valueOf(i));
        long currentTime = System.currentTimeMillis();
        cv.put(CREATEDATE, Long.valueOf(currentTime));
        cv.put(DONEDATE, Long.valueOf(currentTime));
        cv.put(REFERER, task.getReferer());
        cv.put(FLAG, Integer.valueOf(task.getFlag()));
        cv.put(COSTTIME, 0);
        String etag = task.getETag();
        if (!TextUtils.isEmpty(etag)) {
            cv.put("etag", etag);
        }
        cv.put(THREADNUM, Integer.valueOf(task.getMaxThreadNum()));
        cv.put(ANNOTATION, task.getAnnotation());
        cv.put(ANNOTATIONEXT, task.getAnnotationExt());
        cv.put(EXTEND_1, task.getPackageName());
        cv.put(EXTEND_2, Long.valueOf(task.getSaveFlowSize()));
        int taskId = -1;
        try {
            taskId = this.mDBHelper.insert(TABLE_DOWNLOAD, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownloaderLog.d(TAG, "New insert task id - " + taskId);
        if (taskId <= -1) {
            return false;
        }
        task.setTaskId(taskId);
        task.setCreateTime(currentTime);
        return true;
    }

    public boolean updateTask(DownloadTask task) {
        if (task == null) {
            return false;
        }
        String url = task.getTaskUrl();
        String fileName = task.getFileName();
        ContentValues cv = new ContentValues();
        cv.put("url", url);
        cv.put("filename", fileName);
        cv.put(FILEFOLDERPATH, task.getFileFolderPath());
        cv.put(TOTALSIZE, Long.valueOf(task.getTotalSize()));
        cv.put(DOWNLOADEDSIZE, Long.valueOf(task.getDownloadedSize()));
        cv.put("status", Byte.valueOf(task.getStatus()));
        cv.put(ISSUPPORTRESUME, Integer.valueOf(task.getIsSupportResume() ? 1 : 0));
        cv.put(REFERER, task.getReferer());
        cv.put(FLAG, Integer.valueOf(task.getFlag()));
        cv.put(DONEDATE, Long.valueOf(System.currentTimeMillis()));
        cv.put(COSTTIME, Long.valueOf(task.getCostTime()));
        cv.put("etag", task.getETag());
        cv.put(THREADNUM, Integer.valueOf(task.getMaxThreadNum()));
        cv.put(ANNOTATION, task.getAnnotation());
        cv.put(ANNOTATIONEXT, task.getAnnotationExt());
        cv.put(EXTEND_1, task.getPackageName());
        cv.put(EXTEND_2, Long.valueOf(task.getSaveFlowSize()));
        try {
            if (this.mDBHelper.update(TABLE_DOWNLOAD, cv, WHERE_ID, new String[]{String.valueOf(task.getTaskId())}) > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int taskId) {
        try {
            return this.mDBHelper.delete(TABLE_DOWNLOAD, WHERE_ID, new String[]{String.valueOf(taskId)}) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getDownloadingList() throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, "status!=3", "createdate ASC");
    }

    public Cursor getDownloadedList() throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, "status=3", "donedate DESC");
    }

    public Cursor getAllDownloadList() throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, (String) null, (String) null);
    }

    public Cursor getDownloadTask(int taskId) throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, "id=" + taskId);
    }

    public Cursor getDownloadTask(String url) throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, (String[]) null, WHERE_URL, new String[]{url}, (String) null);
    }

    public Cursor getApkDownloadTask(String packageName) throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, (String[]) null, WHERE_PKG_NAME, new String[]{packageName}, (String) null);
    }

    public Cursor getDownloadCompletedTask(String url) throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, (String[]) null, "url=? AND status=3", new String[]{url}, (String) null);
    }

    public Cursor getDownloadedSkinTask(String skinName) throws Exception {
        return this.mDBHelper.query(TABLE_DOWNLOAD, (String[]) null, "annotation=? AND status=3", new String[]{skinName}, (String) null);
    }

    public SQLiteDatabase getDatabase() {
        try {
            return this.mDBHelper.getSQLiteDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
