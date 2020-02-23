package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class in {
    @Nullable
    private static aen zzA(@NonNull byte[] bArr) {
        try {
            return aen.zzL(bArr);
        } catch (ado e) {
            return null;
        }
    }

    private static String zzS(@NonNull Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String) Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mName").get(obj);
    }

    private static String zzT(@NonNull Object obj) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String) Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mValue").get(obj);
    }

    private static Bundle zzX(@NonNull String str, @NonNull String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    public static long zza(long j, @Nullable List<byte[]> list) {
        aen zzA;
        if (list != null) {
            for (byte[] next : list) {
                if (!(next == null || (zzA = zzA(next)) == null || zzA.zzcup <= j)) {
                    j = zzA.zzcup;
                }
            }
        }
        return j;
    }

    private static Bundle zza(@NonNull aen aen) {
        return zzX(aen.zzcun, aen.zzcuo);
    }

    @Nullable
    private static Object zza(@NonNull aen aen, @NonNull String str, @NonNull im imVar) {
        Object obj;
        String str2 = null;
        try {
            Class<?> cls = Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            Bundle zza = zza(aen);
            obj = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
            try {
                cls.getField("mOrigin").set(obj, str);
                cls.getField("mCreationTimestamp").set(obj, Long.valueOf(aen.zzcup));
                cls.getField("mName").set(obj, aen.zzcun);
                cls.getField("mValue").set(obj, aen.zzcuo);
                if (!TextUtils.isEmpty(aen.zzcuq)) {
                    str2 = aen.zzcuq;
                }
                cls.getField("mTriggerEventName").set(obj, str2);
                cls.getField("mTimedOutEventName").set(obj, !TextUtils.isEmpty(aen.zzcuv) ? aen.zzcuv : imVar.zzEw());
                cls.getField("mTimedOutEventParams").set(obj, zza);
                cls.getField("mTriggerTimeout").set(obj, Long.valueOf(aen.zzcur));
                cls.getField("mTriggeredEventName").set(obj, !TextUtils.isEmpty(aen.zzcut) ? aen.zzcut : imVar.zzEv());
                cls.getField("mTriggeredEventParams").set(obj, zza);
                cls.getField("mTimeToLive").set(obj, Long.valueOf(aen.zzaLt));
                cls.getField("mExpiredEventName").set(obj, !TextUtils.isEmpty(aen.zzcuw) ? aen.zzcuw : imVar.zzEx());
                cls.getField("mExpiredEventParams").set(obj, zza);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                e = e;
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
                return obj;
            }
        } catch (ClassNotFoundException e2) {
            e = e2;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        } catch (NoSuchMethodException e3) {
            e = e3;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        } catch (IllegalAccessException e4) {
            e = e4;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        } catch (InvocationTargetException e5) {
            e = e5;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        } catch (NoSuchFieldException e6) {
            e = e6;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        } catch (InstantiationException e7) {
            e = e7;
            obj = null;
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return obj;
        }
        return obj;
    }

    private static String zza(@Nullable aen aen, @NonNull im imVar) {
        return (aen == null || TextUtils.isEmpty(aen.zzcuu)) ? imVar.zzEy() : aen.zzcuu;
    }

    private static List<Object> zza(@NonNull AppMeasurement appMeasurement, @NonNull String str) {
        List<Object> list;
        ArrayList arrayList = new ArrayList();
        try {
            Method declaredMethod = AppMeasurement.class.getDeclaredMethod("getConditionalUserProperties", new Class[]{String.class, String.class});
            declaredMethod.setAccessible(true);
            list = (List) declaredMethod.invoke(appMeasurement, new Object[]{str, ""});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            list = arrayList;
        }
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str).length() + 55).append("Number of currently set _Es for origin: ").append(str).append(" is ").append(list.size()).toString());
        }
        return list;
    }

    private static void zza(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String valueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", valueOf.length() != 0 ? "_CE(experimentId) called by ".concat(valueOf) : new String("_CE(experimentId) called by "));
        }
        if (zzbD(context)) {
            AppMeasurement zzaQ = zzaQ(context);
            try {
                Method declaredMethod = AppMeasurement.class.getDeclaredMethod("clearConditionalUserProperty", new Class[]{String.class, String.class, Bundle.class});
                declaredMethod.setAccessible(true);
                if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                    Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(str2).length() + 17 + String.valueOf(str3).length()).append("Clearing _E: [").append(str2).append(", ").append(str3).append("]").toString());
                }
                declaredMethod.invoke(zzaQ, new Object[]{str2, str4, zzX(str2, str3)});
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        }
    }

    public static void zza(@NonNull Context context, @NonNull String str, @NonNull List<byte[]> list, int i, @NonNull im imVar, long j) {
        if (Log.isLoggable("FirebaseAbtUtil", 2)) {
            String valueOf = String.valueOf(str);
            Log.v("FirebaseAbtUtil", valueOf.length() != 0 ? "_UE called by ".concat(valueOf) : new String("_UE called by "));
        }
        if (zzbD(context)) {
            AppMeasurement zzaQ = zzaQ(context);
            try {
                Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
                List<Object> zza = zza(zzaQ, str);
                new ArrayList();
                List<aen> zzc = zzc(list, zza);
                for (Object next : zzd(list, zza)) {
                    String zzS = zzS(next);
                    String zzT = zzT(next);
                    if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(zzS).length() + 30).append("Clearing _E as part of _UE: [").append(zzS).append("]").toString());
                    }
                    zza(context, str, zzS, zzT, zza((aen) null, imVar));
                }
                for (aen next2 : zzc) {
                    if (next2.zzcup > j) {
                        String valueOf2 = String.valueOf(next2.zzcun);
                        String valueOf3 = String.valueOf(next2.zzcuo);
                        Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(valueOf2).length() + 106 + String.valueOf(valueOf3).length()).append("Setting _E as part of _UE: [").append(valueOf2).append(", ").append(valueOf3).append(", ").append(next2.zzcup).append("], latestOriginKnownExpStartTime: ").append(j).toString());
                        zza(zzaQ, context, str, next2, imVar, 1);
                    } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        String valueOf4 = String.valueOf(next2.zzcun);
                        String valueOf5 = String.valueOf(next2.zzcuo);
                        Log.v("FirebaseAbtUtil", new StringBuilder(String.valueOf(valueOf4).length() + 118 + String.valueOf(valueOf5).length()).append("Not setting _E, due to lastUpdateTime: [").append(valueOf4).append(", ").append(valueOf5).append(", ").append(next2.zzcup).append("], latestOriginKnownExpStartTime: ").append(j).toString());
                    }
                }
            } catch (ClassNotFoundException | NoSuchFieldException e) {
                e = e;
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0121, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x017e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x01d8, code lost:
        r0 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x01d8 A[Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }, ExcHandler: NoSuchFieldException (e java.lang.NoSuchFieldException), Splitter:B:4:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(@android.support.annotation.NonNull com.google.android.gms.measurement.AppMeasurement r7, @android.support.annotation.NonNull android.content.Context r8, @android.support.annotation.NonNull java.lang.String r9, @android.support.annotation.NonNull com.google.android.gms.internal.aen r10, @android.support.annotation.NonNull com.google.android.gms.internal.im r11, int r12) {
        /*
            r1 = 1
            r2 = 2
            java.lang.String r0 = "FirebaseAbtUtil"
            boolean r0 = android.util.Log.isLoggable(r0, r2)
            if (r0 == 0) goto L_0x004b
            java.lang.String r0 = "FirebaseAbtUtil"
            java.lang.String r2 = r10.zzcun
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r3 = r10.zzcuo
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 7
            java.lang.String r5 = java.lang.String.valueOf(r3)
            int r5 = r5.length()
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "_SEI: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r4 = " "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.v(r0, r2)
        L_0x004b:
            java.lang.String r0 = "com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty"
            java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.util.List r2 = zza((com.google.android.gms.measurement.AppMeasurement) r7, (java.lang.String) r9)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r0 = zzb(r7, r9)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.util.List r3 = zza((com.google.android.gms.measurement.AppMeasurement) r7, (java.lang.String) r9)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3.size()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r3 < r0) goto L_0x00af
            int r0 = r10.zzcux     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r0 == 0) goto L_0x012a
            int r0 = r10.zzcux     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
        L_0x0068:
            if (r0 != r1) goto L_0x012d
            r0 = 0
            java.lang.Object r0 = r2.get(r0)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = zzS(r0)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r0 = zzT(r0)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = "FirebaseAbtUtil"
            r4 = 2
            boolean r3 = android.util.Log.isLoggable(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r3 == 0) goto L_0x00a8
            java.lang.String r3 = "FirebaseAbtUtil"
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4 + 38
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            r5.<init>(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = "Clearing _E due to overflow policy: ["
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = "]"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = r4.toString()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            android.util.Log.v(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
        L_0x00a8:
            java.lang.String r3 = zza((com.google.android.gms.internal.aen) r10, (com.google.android.gms.internal.im) r11)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            zza(r8, r9, r1, r0, r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
        L_0x00af:
            java.util.Iterator r0 = r2.iterator()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
        L_0x00b3:
            boolean r1 = r0.hasNext()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r1 == 0) goto L_0x0180
            java.lang.Object r1 = r0.next()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = zzS(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = zzT(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = r10.zzcun     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            boolean r3 = r2.equals(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r3 == 0) goto L_0x00b3
            java.lang.String r3 = r10.zzcuo     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            boolean r3 = r1.equals(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r3 != 0) goto L_0x00b3
            java.lang.String r3 = "FirebaseAbtUtil"
            r4 = 2
            boolean r3 = android.util.Log.isLoggable(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r3 == 0) goto L_0x00b3
            java.lang.String r3 = "FirebaseAbtUtil"
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4 + 77
            java.lang.String r5 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r5 = r5.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4 + r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            r5.<init>(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = "Clearing _E, as only one _V of the same _E can be set atany given time: ["
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = ", "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = "]."
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = r4.toString()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            android.util.Log.v(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = zza((com.google.android.gms.internal.aen) r10, (com.google.android.gms.internal.im) r11)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            zza(r8, r9, r2, r1, r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            goto L_0x00b3
        L_0x0121:
            r0 = move-exception
        L_0x0122:
            java.lang.String r1 = "FirebaseAbtUtil"
            java.lang.String r2 = "Could not complete the operation due to an internal error."
            android.util.Log.e(r1, r2, r0)
        L_0x0129:
            return
        L_0x012a:
            r0 = r1
            goto L_0x0068
        L_0x012d:
            java.lang.String r0 = "FirebaseAbtUtil"
            r1 = 2
            boolean r0 = android.util.Log.isLoggable(r0, r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r0 == 0) goto L_0x0129
            java.lang.String r0 = "FirebaseAbtUtil"
            java.lang.String r1 = r10.zzcun     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = r10.zzcuo     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3 + 44
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            r4.<init>(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = "_E won't be set due to overflow policy. ["
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = ", "
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = "]"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = r1.toString()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            android.util.Log.v(r0, r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            goto L_0x0129
        L_0x017e:
            r0 = move-exception
            goto L_0x0122
        L_0x0180:
            java.lang.Object r1 = zza(r10, r9, r11)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r1 != 0) goto L_0x01db
            java.lang.String r0 = "FirebaseAbtUtil"
            r1 = 2
            boolean r0 = android.util.Log.isLoggable(r0, r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r0 == 0) goto L_0x0129
            java.lang.String r0 = "FirebaseAbtUtil"
            java.lang.String r1 = r10.zzcun     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = r10.zzcuo     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3 + 42
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r4 = r4.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            r4.<init>(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = "Could not create _CUP for: ["
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = ", "
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = "]. Skipping."
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r1 = r1.toString()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            android.util.Log.v(r0, r1)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            goto L_0x0129
        L_0x01d8:
            r0 = move-exception
            goto L_0x0122
        L_0x01db:
            java.lang.String r0 = "FirebaseAbtUtil"
            r2 = 2
            boolean r0 = android.util.Log.isLoggable(r0, r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            if (r0 == 0) goto L_0x0244
            java.lang.String r0 = "FirebaseAbtUtil"
            java.lang.String r2 = r10.zzcun     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = r10.zzcuo     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = r10.zzcuq     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r5 = r5.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r5 = r5 + 27
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r6 = r6.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r5 = r5 + r6
            java.lang.String r6 = java.lang.String.valueOf(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r6 = r6.length()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            int r5 = r5 + r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            r6.<init>(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = "Setting _CUP for _E: ["
            java.lang.StringBuilder r5 = r6.append(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r5 = ", "
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = ", "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r3 = "]"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            java.lang.String r2 = r2.toString()     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            android.util.Log.v(r0, r2)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
        L_0x0244:
            java.lang.String r0 = "com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            java.lang.Class<com.google.android.gms.measurement.AppMeasurement> r2 = com.google.android.gms.measurement.AppMeasurement.class
            java.lang.String r3 = "setConditionalUserProperty"
            r4 = 1
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r5 = 0
            r4[r5] = r0     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r0 = 1
            r2.setAccessible(r0)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            java.lang.String r0 = r10.zzcus     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            if (r0 != 0) goto L_0x0282
            java.lang.String r0 = r10.zzcus     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
        L_0x0266:
            android.os.Bundle r3 = zza(r10)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r7.logEventInternal(r9, r0, r3)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r3 = 0
            r0[r3] = r1     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            r2.invoke(r7, r0)     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            goto L_0x0129
        L_0x0278:
            r0 = move-exception
        L_0x0279:
            java.lang.String r1 = "FirebaseAbtUtil"
            java.lang.String r2 = "Could not complete the operation due to an internal error."
            android.util.Log.e(r1, r2, r0)     // Catch:{ ClassNotFoundException -> 0x0121, IllegalAccessException -> 0x017e, NoSuchFieldException -> 0x01d8 }
            goto L_0x0129
        L_0x0282:
            java.lang.String r0 = r11.zzEu()     // Catch:{ ClassNotFoundException -> 0x0278, NoSuchMethodException -> 0x0289, IllegalAccessException -> 0x0287, InvocationTargetException -> 0x028b, NoSuchFieldException -> 0x01d8 }
            goto L_0x0266
        L_0x0287:
            r0 = move-exception
            goto L_0x0279
        L_0x0289:
            r0 = move-exception
            goto L_0x0279
        L_0x028b:
            r0 = move-exception
            goto L_0x0279
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.in.zza(com.google.android.gms.measurement.AppMeasurement, android.content.Context, java.lang.String, com.google.android.gms.internal.aen, com.google.android.gms.internal.im, int):void");
    }

    @Nullable
    private static AppMeasurement zzaQ(Context context) {
        try {
            return AppMeasurement.getInstance(context);
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }

    private static int zzb(@NonNull AppMeasurement appMeasurement, @NonNull String str) {
        try {
            Method declaredMethod = AppMeasurement.class.getDeclaredMethod("getMaxUserProperties", new Class[]{String.class});
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(appMeasurement, new Object[]{str})).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            return 20;
        }
    }

    private static boolean zzbD(Context context) {
        if (zzaQ(context) != null) {
            try {
                Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
                return true;
            } catch (ClassNotFoundException e) {
                if (!Log.isLoggable("FirebaseAbtUtil", 2)) {
                    return false;
                }
                Log.v("FirebaseAbtUtil", "Firebase Analytics library is missing support for abt. Please update to a more recent version.");
                return false;
            }
        } else if (!Log.isLoggable("FirebaseAbtUtil", 2)) {
            return false;
        } else {
            Log.v("FirebaseAbtUtil", "Firebase Analytics not available");
            return false;
        }
    }

    private static List<aen> zzc(@NonNull List<byte[]> list, @NonNull List<Object> list2) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        for (byte[] zzA : list) {
            aen zzA2 = zzA(zzA);
            if (zzA2 != null) {
                Iterator<Object> it = list2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    Object next = it.next();
                    try {
                        Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
                        zzS(next);
                        String zzT = zzT(next);
                        if (zzA2.zzcun.equals(zzS(next)) && zzA2.zzcuo.equals(zzT)) {
                            z = true;
                            break;
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                        Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
                    }
                }
                if (!z) {
                    arrayList.add(zzA2);
                }
            } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                Log.v("FirebaseAbtUtil", "Couldn't deserialize the payload; skipping.");
            }
        }
        return arrayList;
    }

    private static List<Object> zzd(@NonNull List<byte[]> list, @NonNull List<Object> list2) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        for (Object next : list2) {
            try {
                Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
                String zzS = zzS(next);
                String zzT = zzT(next);
                Iterator<byte[]> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    aen zzA = zzA(it.next());
                    if (zzA != null) {
                        if (zzA.zzcun.equals(zzS) && zzA.zzcuo.equals(zzT)) {
                            z = false;
                            break;
                        }
                    } else if (Log.isLoggable("FirebaseAbtUtil", 2)) {
                        Log.v("FirebaseAbtUtil", "Couldn't deserialize the payload; skipping.");
                    }
                }
                if (z) {
                    arrayList.add(next);
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                Log.e("FirebaseAbtUtil", "Could not complete the operation due to an internal error.", e);
            }
        }
        return arrayList;
    }
}
