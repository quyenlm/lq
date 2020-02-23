package com.tencent.component.db.exception;

import android.content.Context;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.tencent.component.utils.PlatformUtil;
import com.tencent.component.utils.log.LogUtil;

public class DbCacheExceptionHandler {
    private static final boolean DEBUG = true;
    private static final String TAG = "DbCacheExceptionHandler";
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private volatile Context mContext;

    private DbCacheExceptionHandler() {
    }

    public void attachContext(Context context) {
        this.mContext = context != null ? context.getApplicationContext() : null;
    }

    public void handleException(Throwable e) {
        if (e != null) {
            LogUtil.e(TAG, "handle exception", e);
            if ((e instanceof SQLiteDiskIOException) || (e instanceof SQLiteDatabaseCorruptException) || ((PlatformUtil.version() >= 11 && e.getClass().getSimpleName().equals("SQLiteCantOpenDatabaseException")) || ((PlatformUtil.version() >= 11 && e.getClass().getSimpleName().equals("SQLiteAccessPermException")) || ((e instanceof SQLiteException) && e.getMessage().contains("no such table"))))) {
                notifyDbError();
            } else if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            } else {
                throw new DbCacheError(e);
            }
        }
    }

    private void notifyDbError() {
        if (isUIThread()) {
            showNotify("存取数据失败，请尝试清理手机存储空间或重新安装");
        } else {
            runOnUIThread(new Runnable() {
                public void run() {
                    DbCacheExceptionHandler.this.showNotify("存取数据失败，请尝试清理手机存储空间或重新安装");
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void showNotify(CharSequence msg) {
        if (this.mContext != null) {
            Toast.makeText(this.mContext, msg, 1).show();
        }
    }

    private static void runOnUIThread(Runnable runnable) {
        if (isUIThread()) {
            runnable.run();
        } else {
            sMainHandler.post(runnable);
        }
    }

    private static boolean isUIThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    static final class DbCacheError extends Error {
        public DbCacheError(Throwable t) {
            super(t);
        }
    }

    static final class InstanceHolder {
        static final DbCacheExceptionHandler INSTANCE = new DbCacheExceptionHandler();

        InstanceHolder() {
        }
    }

    public static DbCacheExceptionHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
