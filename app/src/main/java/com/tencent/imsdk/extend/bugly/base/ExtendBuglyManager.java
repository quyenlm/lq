package com.tencent.imsdk.extend.bugly.base;

import android.content.Context;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

public class ExtendBuglyManager {
    private static ExtendBuglyManager instance = null;
    private Context mContext;

    public static ExtendBuglyManager getInstance() {
        if (instance == null) {
            synchronized (ExtendBuglyManager.class) {
                if (instance == null) {
                    instance = new ExtendBuglyManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public void setLogCache(int byteSize) {
        if (byteSize > 0) {
            try {
                BuglyLog.setCache(byteSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void buglyLog(int level, String tag, String log) {
        switch (level) {
            case 2:
                BuglyLog.v(tag, log);
                return;
            case 3:
                BuglyLog.d(tag, log);
                return;
            case 4:
                BuglyLog.i(tag, log);
                return;
            case 5:
                BuglyLog.w(tag, log);
                return;
            case 6:
                BuglyLog.e(tag, log);
                return;
            default:
                return;
        }
    }

    public void buglyPutUserData(String key, String value) {
        if (this.mContext != null) {
            CrashReport.putUserData(this.mContext, key, value);
        }
    }
}
