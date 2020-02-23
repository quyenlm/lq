package com.tencent.beacon.cover;

import android.content.Context;
import com.tencent.component.debug.TraceFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Map;

/* compiled from: CoverLocker */
public final class d {
    private static d c = null;
    private Context a = null;
    private Map<String, FileChannel> b = null;

    private d(Context context) {
        this.a = context;
        this.b = new HashMap(5);
    }

    public static synchronized d a(Context context) {
        d dVar;
        synchronized (d.class) {
            if (c == null) {
                c = new d(context);
            }
            dVar = c;
        }
        return dVar;
    }

    public final synchronized boolean a(String str) {
        boolean z;
        if (str.trim().length() <= 0) {
            z = false;
        } else if (this.b.containsKey(str)) {
            z = true;
        } else {
            File c2 = c(str);
            if (c2 == null) {
                z = true;
            } else {
                try {
                    FileChannel fileChannel = this.b.get(str);
                    if (fileChannel == null || !fileChannel.isOpen()) {
                        fileChannel = new FileOutputStream(c2).getChannel();
                        this.b.put(str, fileChannel);
                    }
                    FileLock lock = fileChannel.lock();
                    if (lock != null && lock.isValid()) {
                        z = true;
                    }
                } catch (Throwable th) {
                    g.a(TraceFormat.STR_WARN, "lock task error.", new Object[0]);
                    th.printStackTrace();
                }
                z = false;
            }
        }
        return z;
    }

    public final synchronized void b(String str) {
        if (str.trim().length() > 0) {
            FileChannel fileChannel = this.b.get(str);
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Throwable th) {
                    g.a(TraceFormat.STR_WARN, "release task error.", new Object[0]);
                    th.printStackTrace();
                }
            }
        }
        return;
    }

    private synchronized File c(String str) {
        File file;
        File file2 = null;
        synchronized (this) {
            if (this.a.getFilesDir() != null) {
                try {
                    file = new File(this.a.getFilesDir(), "beacon_cover_" + str + ".lock");
                    if (!file.exists()) {
                        g.a(TraceFormat.STR_DEBUG, " create lock file: %s", file.getAbsolutePath());
                        file.createNewFile();
                    }
                } catch (IOException e) {
                    g.a(TraceFormat.STR_WARN, "create lock file error.", new Object[0]);
                    file = null;
                }
                file2 = file;
            }
        }
        return file2;
    }
}
