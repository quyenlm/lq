package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.vk.sdk.api.VKApiConst;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

@zzzn
public final class zzzi implements zzzl {
    private static zzzl zzSg = null;
    private static final Object zzuF = new Object();
    private final String mPackageName;
    private final Object zzSh = new Object();
    private final WeakHashMap<Thread, Boolean> zzSi = new WeakHashMap<>();
    private final zzaje zzuK;

    private zzzi(String str, zzaje zzaje) {
        this.mPackageName = str;
        this.zzuK = zzaje;
        Thread thread = Looper.getMainLooper().getThread();
        if (thread != null) {
            synchronized (this.zzSh) {
                this.zzSi.put(thread, true);
            }
            thread.setUncaughtExceptionHandler(new zzzk(this, thread.getUncaughtExceptionHandler()));
        }
        Thread.setDefaultUncaughtExceptionHandler(new zzzj(this, Thread.getDefaultUncaughtExceptionHandler()));
    }

    private static boolean zzat(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.startsWith((String) zzbs.zzbL().zzd(zzmo.zzCe))) {
            return true;
        }
        try {
            return Class.forName(str).isAnnotationPresent(zzzn.class);
        } catch (Exception e) {
            Exception exc = e;
            String valueOf = String.valueOf(str);
            zzafr.zza(valueOf.length() != 0 ? "Fail to check class type for class ".concat(valueOf) : new String("Fail to check class type for class "), exc);
            return false;
        }
    }

    public static zzzl zzc(Context context, zzaje zzaje) {
        String packageName;
        synchronized (zzuF) {
            if (zzSg == null) {
                if (((Boolean) zzbs.zzbL().zzd(zzmo.zzCc)).booleanValue()) {
                    try {
                        packageName = context.getApplicationContext().getPackageName();
                    } catch (Throwable th) {
                        zzafr.zzaT("Cannot obtain package name, proceeding.");
                    }
                    zzSg = new zzzi(packageName, zzaje);
                } else {
                    zzSg = new zzzm();
                }
            }
        }
        return zzSg;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        if (r2 == false) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.Thread r11, java.lang.Throwable r12) {
        /*
            r10 = this;
            r1 = 1
            r3 = 0
            if (r12 == 0) goto L_0x0046
            r2 = r3
            r0 = r3
            r5 = r12
        L_0x0007:
            if (r5 == 0) goto L_0x003a
            java.lang.StackTraceElement[] r6 = r5.getStackTrace()
            int r7 = r6.length
            r4 = r3
        L_0x000f:
            if (r4 >= r7) goto L_0x0034
            r8 = r6[r4]
            java.lang.String r9 = r8.getClassName()
            boolean r9 = zzat(r9)
            if (r9 == 0) goto L_0x001e
            r0 = r1
        L_0x001e:
            java.lang.Class r9 = r10.getClass()
            java.lang.String r9 = r9.getName()
            java.lang.String r8 = r8.getClassName()
            boolean r8 = r9.equals(r8)
            if (r8 == 0) goto L_0x0031
            r2 = r1
        L_0x0031:
            int r4 = r4 + 1
            goto L_0x000f
        L_0x0034:
            java.lang.Throwable r4 = r5.getCause()
            r5 = r4
            goto L_0x0007
        L_0x003a:
            if (r0 == 0) goto L_0x0046
            if (r2 != 0) goto L_0x0046
        L_0x003e:
            if (r1 == 0) goto L_0x0045
            java.lang.String r0 = ""
            r10.zza((java.lang.Throwable) r12, (java.lang.String) r0)
        L_0x0045:
            return
        L_0x0046:
            r1 = r3
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzzi.zza(java.lang.Thread, java.lang.Throwable):void");
    }

    public final void zza(Throwable th, String str) {
        Throwable th2;
        Throwable th3;
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzCd)).booleanValue()) {
            th2 = th;
        } else {
            LinkedList linkedList = new LinkedList();
            for (Throwable th4 = th; th4 != null; th4 = th4.getCause()) {
                linkedList.push(th4);
            }
            th2 = null;
            while (!linkedList.isEmpty()) {
                Throwable th5 = (Throwable) linkedList.pop();
                StackTraceElement[] stackTrace = th5.getStackTrace();
                ArrayList arrayList = new ArrayList();
                arrayList.add(new StackTraceElement(th5.getClass().getName(), "<filtered>", "<filtered>", 1));
                boolean z = false;
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (zzat(stackTraceElement.getClassName())) {
                        z = true;
                        arrayList.add(stackTraceElement);
                    } else {
                        String className = stackTraceElement.getClassName();
                        if (!TextUtils.isEmpty(className) && (className.startsWith("android.") || className.startsWith("java."))) {
                            arrayList.add(stackTraceElement);
                        } else {
                            arrayList.add(new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1));
                        }
                    }
                }
                if (z) {
                    th3 = th2 == null ? new Throwable(th5.getMessage()) : new Throwable(th5.getMessage(), th2);
                    th3.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
                } else {
                    th3 = th2;
                }
                th2 = th3;
            }
        }
        if (th2 != null) {
            Class<?> cls = th.getClass();
            ArrayList arrayList2 = new ArrayList();
            StringWriter stringWriter = new StringWriter();
            th2.printStackTrace(new PrintWriter(stringWriter));
            Uri.Builder appendQueryParameter = new Uri.Builder().scheme(VKApiConst.HTTPS).path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("id", "gmob-apps-report-exception").appendQueryParameter(HttpRequestParams.OS, Build.VERSION.RELEASE).appendQueryParameter("api", String.valueOf(Build.VERSION.SDK_INT));
            zzbs.zzbz();
            arrayList2.add(appendQueryParameter.appendQueryParameter("device", zzagz.zzhQ()).appendQueryParameter("js", this.zzuK.zzaP).appendQueryParameter("appid", this.mPackageName).appendQueryParameter("exceptiontype", cls.getName()).appendQueryParameter("stacktrace", stringWriter.toString()).appendQueryParameter("eids", TextUtils.join(",", zzmo.zzdJ())).appendQueryParameter("exceptionkey", str).appendQueryParameter("cl", "162978962").appendQueryParameter("rc", APGlobalInfo.DevEnv).appendQueryParameter("session_id", zzbs.zzbD().getSessionId()).toString());
            zzbs.zzbz();
            zzagz.zza((List<String>) arrayList2, zzbs.zzbD().zzht());
        }
    }
}
