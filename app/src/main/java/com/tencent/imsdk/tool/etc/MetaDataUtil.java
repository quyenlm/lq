package com.tencent.imsdk.tool.etc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

public class MetaDataUtil {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003c, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta NameNotFoundException : " + r1.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005a, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("Meta Key \"" + r8 + " \" is not exist");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0077, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0078, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta error : " + r1.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0059 A[ExcHandler: NullPointerException (e java.lang.NullPointerException), Splitter:B:0:0x0000] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003b A[ExcHandler: NameNotFoundException (r1v1 'e' android.content.pm.PackageManager$NameNotFoundException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readMetaDataFromApplication(android.content.Context r7, java.lang.String r8) {
        /*
            android.content.pm.PackageManager r4 = r7.getPackageManager()     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.String r5 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            r6 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r0 = r4.getApplicationInfo(r5, r6)     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            android.os.Bundle r4 = r0.metaData     // Catch:{ Exception -> 0x001d, NameNotFoundException -> 0x003b, NullPointerException -> 0x0059 }
            java.lang.Object r3 = r4.get(r8)     // Catch:{ Exception -> 0x001d, NameNotFoundException -> 0x003b, NullPointerException -> 0x0059 }
            boolean r4 = r3 instanceof java.lang.String     // Catch:{ Exception -> 0x001d, NameNotFoundException -> 0x003b, NullPointerException -> 0x0059 }
            if (r4 == 0) goto L_0x0056
            java.lang.String r4 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x001d, NameNotFoundException -> 0x003b, NullPointerException -> 0x0059 }
        L_0x001c:
            return r4
        L_0x001d:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            r4.<init>()     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.String r5 = "get meta error : "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.String r5 = r1.getMessage()     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.String r4 = r4.toString()     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)     // Catch:{ NameNotFoundException -> 0x003b, NullPointerException -> 0x0059, Exception -> 0x0077 }
            java.lang.String r4 = ""
            goto L_0x001c
        L_0x003b:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "get application meta NameNotFoundException : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
        L_0x0056:
            java.lang.String r4 = ""
            goto L_0x001c
        L_0x0059:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Meta Key \""
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r5 = " \" is not exist"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            goto L_0x0056
        L_0x0077:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "get application meta error : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.MetaDataUtil.readMetaDataFromApplication(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004e, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("Meta Key \"" + r7 + " \" is not exist");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x006b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006c, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta error : " + r1.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0031, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0032, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta NameNotFoundException : " + r1.getMessage());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0031 A[ExcHandler: NameNotFoundException (r1v1 'e' android.content.pm.PackageManager$NameNotFoundException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x004d A[ExcHandler: NullPointerException (e java.lang.NullPointerException), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean readMetaDataFromApplication(android.content.Context r6, java.lang.String r7, boolean r8) {
        /*
            android.content.pm.PackageManager r3 = r6.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            java.lang.String r4 = r6.getPackageName()     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            r5 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r0 = r3.getApplicationInfo(r4, r5)     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            android.os.Bundle r3 = r0.metaData     // Catch:{ Exception -> 0x0015, NameNotFoundException -> 0x0031, NullPointerException -> 0x004d }
            boolean r8 = r3.getBoolean(r7)     // Catch:{ Exception -> 0x0015, NameNotFoundException -> 0x0031, NullPointerException -> 0x004d }
        L_0x0014:
            return r8
        L_0x0015:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            r3.<init>()     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            java.lang.String r4 = "get meta error : "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            java.lang.String r4 = r1.getMessage()     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            java.lang.String r3 = r3.toString()     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r3)     // Catch:{ NameNotFoundException -> 0x0031, NullPointerException -> 0x004d, Exception -> 0x006b }
            goto L_0x0014
        L_0x0031:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "get application meta NameNotFoundException : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r1.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r3)
            goto L_0x0014
        L_0x004d:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Meta Key \""
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r4 = " \" is not exist"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r3)
            goto L_0x0014
        L_0x006b:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "get application meta error : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r1.getMessage()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r3)
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.MetaDataUtil.readMetaDataFromApplication(android.content.Context, java.lang.String, boolean):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004f, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("Meta Key \"" + r8 + " \" is not exist");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006c, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006d, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta error : " + r1.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0032, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0033, code lost:
        com.tencent.imsdk.tool.etc.IMLogger.d("get application meta NameNotFoundException : " + r1.getMessage());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x004e A[ExcHandler: NullPointerException (e java.lang.NullPointerException), Splitter:B:1:0x0001] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0032 A[ExcHandler: NameNotFoundException (r1v1 'e' android.content.pm.PackageManager$NameNotFoundException A[CUSTOM_DECLARE]), Splitter:B:1:0x0001] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int readMetaIntFromApplication(android.content.Context r7, java.lang.String r8) {
        /*
            r3 = -1
            android.content.pm.PackageManager r4 = r7.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            java.lang.String r5 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            r6 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r0 = r4.getApplicationInfo(r5, r6)     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            android.os.Bundle r4 = r0.metaData     // Catch:{ Exception -> 0x0016, NameNotFoundException -> 0x0032, NullPointerException -> 0x004e }
            int r3 = r4.getInt(r8)     // Catch:{ Exception -> 0x0016, NameNotFoundException -> 0x0032, NullPointerException -> 0x004e }
        L_0x0015:
            return r3
        L_0x0016:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            r4.<init>()     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            java.lang.String r5 = "get meta error : "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            java.lang.String r5 = r1.getMessage()     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            java.lang.String r4 = r4.toString()     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)     // Catch:{ NameNotFoundException -> 0x0032, NullPointerException -> 0x004e, Exception -> 0x006c }
            goto L_0x0015
        L_0x0032:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "get application meta NameNotFoundException : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            goto L_0x0015
        L_0x004e:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Meta Key \""
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r5 = " \" is not exist"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            goto L_0x0015
        L_0x006c:
            r1 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "get application meta error : "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r4)
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.MetaDataUtil.readMetaIntFromApplication(android.content.Context, java.lang.String):int");
    }

    public static String readMetaDataFromActivity(Context context, String key) {
        try {
            if (!(context instanceof Activity)) {
                return "";
            }
            return context.getPackageManager().getActivityInfo(((Activity) context).getComponentName(), 128).metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            IMLogger.d("get activity meta NameNotFoundException : " + e.getMessage());
            return "";
        } catch (NullPointerException e2) {
            IMLogger.d("Meta Key \"" + key + " \" is not exist");
            return "";
        } catch (Exception e3) {
            IMLogger.d("get application meta error : " + e3.getMessage());
            return "";
        }
    }

    public static String readMetaDataFromBroadCast(Context context, String key, Class<?> cls) {
        try {
            return context.getPackageManager().getReceiverInfo(new ComponentName(context, cls), 128).metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            IMLogger.d("get BroadCast meta NameNotFoundException : " + e.getMessage());
        } catch (NullPointerException e2) {
            IMLogger.d("Meta Key \"" + key + " \" is not exist");
        } catch (Exception e3) {
            IMLogger.d("get application meta error : " + e3.getMessage());
        }
        return "";
    }

    public static String readMetaDataFromService(Context context, String key, Class<?> cls) {
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context, cls), 128).metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            IMLogger.d("get Service meta NameNotFoundException : " + e.getMessage());
        } catch (NullPointerException e2) {
            IMLogger.d("Meta Key \"" + key + " \" is not exist");
        } catch (Exception e3) {
            IMLogger.d("get application meta error : " + e3.getMessage());
        }
        return "";
    }
}
