package com.tencent.smtt.sdk;

import android.content.Context;
import android.util.Log;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.k;
import java.nio.channels.FileLock;

class br {
    private static br a;
    private static FileLock e = null;
    private bs b;
    private boolean c;
    private boolean d;

    private br() {
    }

    public static br a() {
        if (a == null) {
            synchronized (br.class) {
                if (a == null) {
                    a = new br();
                }
            }
        }
        return a;
    }

    public bs a(boolean z) {
        return z ? this.b : c();
    }

    public synchronized void a(Context context) {
        Object obj = null;
        synchronized (this) {
            o a2 = o.a(true);
            a2.a(context, false, false);
            StringBuilder sb = new StringBuilder();
            bh a3 = a2.a();
            if (!a2.b() || a3 == null) {
                this.c = false;
                sb.append("can not use X5 by !tbs available");
                th = null;
            } else {
                if (!this.d) {
                    this.b = new bs(a3.b());
                    try {
                        this.c = this.b.a();
                        if (!this.c) {
                            sb.append("can not use X5 by x5corewizard return false");
                        }
                        th = null;
                    } catch (NoSuchMethodException e2) {
                        this.c = true;
                        th = null;
                    } catch (Throwable th) {
                        th = th;
                        this.c = false;
                        sb.append("can not use x5 by throwable " + Log.getStackTraceString(th));
                    }
                    if (this.c) {
                        CookieManager.getInstance().a(context, true, true);
                        CookieManager.getInstance().a();
                    }
                }
                th = null;
            }
            if (!this.c) {
                TbsLog.e("X5CoreEngine", "mCanUseX5 is false --> report");
                if (a2.b() && a3 != null && th == null) {
                    try {
                        DexLoader b2 = a3.b();
                        if (b2 != null) {
                            obj = b2.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "getLoadFailureDetails", new Class[0], new Object[0]);
                        }
                        if (obj instanceof Throwable) {
                            Throwable th2 = (Throwable) obj;
                            sb.append("#" + th2.getMessage() + "; cause: " + th2.getCause() + "; th: " + th2);
                        }
                        if (obj instanceof String) {
                            sb.append("failure detail:" + obj);
                        }
                    } catch (Throwable th3) {
                        th3.printStackTrace();
                    }
                    if (sb != null) {
                        if (sb.toString().contains("isPreloadX5Disabled:-10000")) {
                            TbsCoreLoadStat.getInstance().a(context, 408, new Throwable("X5CoreEngine::init, mCanUseX5=false, available true, details: " + sb.toString()));
                        }
                    }
                    TbsCoreLoadStat.getInstance().a(context, 407, new Throwable("X5CoreEngine::init, mCanUseX5=false, available true, details: " + sb.toString()));
                } else if (a2.b()) {
                    TbsCoreLoadStat.getInstance().a(context, 409, new Throwable("mCanUseX5=false, available true, reason: " + th));
                } else {
                    TbsCoreLoadStat.getInstance().a(context, 410, new Throwable("mCanUseX5=false, available false, reason: " + th));
                }
            } else if (e == null) {
                b(context);
            }
            this.d = true;
        }
    }

    public synchronized FileLock b(Context context) {
        FileLock fileLock;
        if (e != null) {
            fileLock = e;
        } else {
            e = k.e(context);
            if (e == null) {
            }
            fileLock = e;
        }
        return fileLock;
    }

    public boolean b() {
        if (QbSdk.a) {
            return false;
        }
        return this.c;
    }

    public bs c() {
        if (QbSdk.a) {
            return null;
        }
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return this.d;
    }
}
