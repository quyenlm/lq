package com.google.android.gms.internal;

import com.appsflyer.share.Constants;
import com.facebook.internal.AnalyticsEvents;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

enum ql implements qt {
    INSTANCE;
    
    static ThreadFactory zzcdb;
    static final ti zzcdc = null;

    static {
        zzcdc = new qm();
    }

    public static boolean isActive() {
        return zzGX() != null;
    }

    private static ThreadFactory zzGX() {
        if (zzcdb == null) {
            try {
                Class<?> cls = Class.forName("com.google.appengine.api.ThreadManager");
                if (cls != null) {
                    zzcdb = (ThreadFactory) cls.getMethod("backgroundThreadFactory", new Class[0]).invoke((Object) null, new Object[0]);
                }
            } catch (ClassNotFoundException e) {
                return null;
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException(e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException(e4);
            }
        }
        return zzcdb;
    }

    public final on zza(qd qdVar, oj ojVar, ol olVar, oo ooVar) {
        return new op(qdVar.zzGR(), olVar, ooVar);
    }

    public final pu zza(ScheduledExecutorService scheduledExecutorService) {
        throw new RuntimeException("Authentication is not implemented yet");
    }

    public final qk zza(qd qdVar) {
        return new tk(zzGX(), zzcdc);
    }

    public final uh zza(qd qdVar, String str) {
        return null;
    }

    public final wm zza(qd qdVar, wn wnVar, List<String> list) {
        return new wj(wnVar, (List<String>) null);
    }

    public final sd zzb(qd qdVar) {
        return new qo(this, qdVar.zzgP("RunLoop"));
    }

    public final String zzc(qd qdVar) {
        String property = System.getProperty("java.specification.version", AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        return new StringBuilder(String.valueOf(property).length() + 1 + String.valueOf("AppEngine").length()).append(property).append(Constants.URL_PATH_DELIMITER).append("AppEngine").toString();
    }
}
