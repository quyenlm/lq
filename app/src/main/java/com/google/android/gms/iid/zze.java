package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.beetalk.sdk.helper.Security;
import com.google.android.gms.common.util.zzq;
import com.vk.sdk.api.model.VKAttachments;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public final class zze {
    private static String zzbgZ = null;
    private static boolean zzbha = false;
    private static int zzbhb = 0;
    private static int zzbhc = 0;
    private static int zzbhd = 0;
    private static BroadcastReceiver zzbhe = null;
    private PendingIntent zzbfP;
    private Messenger zzbfT;
    private Map<String, Object> zzbhf = new HashMap();
    private Messenger zzbhg;
    private MessengerCompat zzbhh;
    private long zzbhi;
    private long zzbhj;
    private int zzbhk;
    private int zzbhl;
    private long zzbhm;
    private Context zzqD;

    public zze(Context context) {
        this.zzqD = context;
    }

    private final void zzB(Object obj) {
        synchronized (getClass()) {
            for (String next : this.zzbhf.keySet()) {
                Object obj2 = this.zzbhf.get(next);
                this.zzbhf.put(next, obj);
                zze(obj2, obj);
            }
        }
    }

    private static String zza(KeyPair keyPair, String... strArr) {
        try {
            byte[] bytes = TextUtils.join("\n", strArr).getBytes("UTF-8");
            try {
                PrivateKey privateKey = keyPair.getPrivate();
                Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? Security.SHA256_WITH_RSA : "SHA256withECDSA");
                instance.initSign(privateKey);
                instance.update(bytes);
                return InstanceID.zzj(instance.sign());
            } catch (GeneralSecurityException e) {
                Log.e("InstanceID/Rpc", "Unable to sign registration request", e);
                return null;
            }
        } catch (UnsupportedEncodingException e2) {
            Log.e("InstanceID/Rpc", "Unable to encode string", e2);
            return null;
        }
    }

    private static boolean zza(PackageManager packageManager) {
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(new Intent("com.google.iid.TOKEN_REQUEST"), 0)) {
            if (zza(packageManager, resolveInfo.activityInfo.packageName, "com.google.iid.TOKEN_REQUEST")) {
                zzbha = true;
                return true;
            }
        }
        return false;
    }

    private static boolean zza(PackageManager packageManager, String str, String str2) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", str) == 0) {
            return zzb(packageManager, str);
        }
        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(str).length() + 56 + String.valueOf(str2).length()).append("Possible malicious package ").append(str).append(" declares ").append(str2).append(" without permission").toString());
        return false;
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        	at java.util.ArrayList.get(ArrayList.java:429)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    private final android.content.Intent zzb(android.os.Bundle r12, java.security.KeyPair r13) throws java.io.IOException {
        /*
            r11 = this;
            r10 = 3
            android.os.ConditionVariable r1 = new android.os.ConditionVariable
            r1.<init>()
            java.lang.String r2 = zzvO()
            java.lang.Class r3 = r11.getClass()
            monitor-enter(r3)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r11.zzbhf     // Catch:{ all -> 0x0058 }
            r0.put(r2, r1)     // Catch:{ all -> 0x0058 }
            monitor-exit(r3)     // Catch:{ all -> 0x0058 }
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r6 = r11.zzbhm
            r8 = 0
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x005b
            long r6 = r11.zzbhm
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x005b
            java.lang.String r0 = "InstanceID/Rpc"
            long r2 = r11.zzbhm
            long r2 = r2 - r4
            int r1 = r11.zzbhl
            r4 = 78
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Backoff mode, next request attempt: "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r3 = " interval: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            android.util.Log.w(r0, r1)
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "RETRY_LATER"
            r0.<init>(r1)
            throw r0
        L_0x0058:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0058 }
            throw r0
        L_0x005b:
            r11.zzvN()
            java.lang.String r0 = zzbgZ
            if (r0 != 0) goto L_0x006a
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "MISSING_INSTANCEID_SERVICE"
            r0.<init>(r1)
            throw r0
        L_0x006a:
            long r4 = android.os.SystemClock.elapsedRealtime()
            r11.zzbhi = r4
            android.content.Intent r3 = new android.content.Intent
            boolean r0 = zzbha
            if (r0 == 0) goto L_0x01bf
            java.lang.String r0 = "com.google.iid.TOKEN_REQUEST"
        L_0x0078:
            r3.<init>(r0)
            java.lang.String r0 = zzbgZ
            r3.setPackage(r0)
            android.content.Context r0 = r11.zzqD
            int r0 = zzbe(r0)
            java.lang.String r4 = "gmsv"
            java.lang.String r0 = java.lang.Integer.toString(r0)
            r12.putString(r4, r0)
            java.lang.String r0 = "osv"
            int r4 = android.os.Build.VERSION.SDK_INT
            java.lang.String r4 = java.lang.Integer.toString(r4)
            r12.putString(r0, r4)
            java.lang.String r0 = "app_ver"
            android.content.Context r4 = r11.zzqD
            int r4 = com.google.android.gms.iid.InstanceID.zzba(r4)
            java.lang.String r4 = java.lang.Integer.toString(r4)
            r12.putString(r0, r4)
            java.lang.String r0 = "app_ver_name"
            android.content.Context r4 = r11.zzqD
            java.lang.String r4 = com.google.android.gms.iid.InstanceID.zzbb(r4)
            r12.putString(r0, r4)
            java.lang.String r0 = "cliv"
            java.lang.String r4 = "iid-11020000"
            r12.putString(r0, r4)
            java.lang.String r0 = "appid"
            java.lang.String r4 = com.google.android.gms.iid.InstanceID.zza(r13)
            r12.putString(r0, r4)
            java.security.PublicKey r0 = r13.getPublic()
            byte[] r0 = r0.getEncoded()
            java.lang.String r0 = com.google.android.gms.iid.InstanceID.zzj(r0)
            java.lang.String r4 = "pub2"
            r12.putString(r4, r0)
            java.lang.String r4 = "sig"
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]
            r6 = 0
            android.content.Context r7 = r11.zzqD
            java.lang.String r7 = r7.getPackageName()
            r5[r6] = r7
            r6 = 1
            r5[r6] = r0
            java.lang.String r0 = zza((java.security.KeyPair) r13, (java.lang.String[]) r5)
            r12.putString(r4, r0)
            r3.putExtras(r12)
            r11.zzg(r3)
            long r4 = android.os.SystemClock.elapsedRealtime()
            r11.zzbhi = r4
            java.lang.String r0 = "kid"
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "|ID|"
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r5 = "|"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.putExtra(r0, r4)
            java.lang.String r0 = "X-kid"
            java.lang.String r4 = java.lang.String.valueOf(r2)
            int r4 = r4.length()
            int r4 = r4 + 5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "|ID|"
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r5 = "|"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.putExtra(r0, r4)
            java.lang.String r0 = "com.google.android.gsf"
            java.lang.String r4 = zzbgZ
            boolean r0 = r0.equals(r4)
            java.lang.String r4 = "useGsf"
            java.lang.String r4 = r3.getStringExtra(r4)
            if (r4 == 0) goto L_0x015f
            java.lang.String r0 = "1"
            boolean r0 = r0.equals(r4)
        L_0x015f:
            java.lang.String r4 = "InstanceID/Rpc"
            boolean r4 = android.util.Log.isLoggable(r4, r10)
            if (r4 == 0) goto L_0x0191
            java.lang.String r4 = "InstanceID/Rpc"
            android.os.Bundle r5 = r3.getExtras()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r6 = java.lang.String.valueOf(r5)
            int r6 = r6.length()
            int r6 = r6 + 8
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.String r6 = "Sending "
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0191:
            android.os.Messenger r4 = r11.zzbhg
            if (r4 == 0) goto L_0x01d3
            java.lang.String r4 = "google.messenger"
            android.os.Messenger r5 = r11.zzbfT
            r3.putExtra(r4, r5)
            android.os.Message r4 = android.os.Message.obtain()
            r4.obj = r3
            android.os.Messenger r5 = r11.zzbhg     // Catch:{ RemoteException -> 0x01c3 }
            r5.send(r4)     // Catch:{ RemoteException -> 0x01c3 }
        L_0x01a7:
            r4 = 30000(0x7530, double:1.4822E-319)
            r1.block(r4)
            java.lang.Class r1 = r11.getClass()
            monitor-enter(r1)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r11.zzbhf     // Catch:{ all -> 0x0262 }
            java.lang.Object r0 = r0.remove(r2)     // Catch:{ all -> 0x0262 }
            boolean r2 = r0 instanceof android.content.Intent     // Catch:{ all -> 0x0262 }
            if (r2 == 0) goto L_0x0256
            android.content.Intent r0 = (android.content.Intent) r0     // Catch:{ all -> 0x0262 }
            monitor-exit(r1)     // Catch:{ all -> 0x0262 }
            return r0
        L_0x01bf:
            java.lang.String r0 = "com.google.android.c2dm.intent.REGISTER"
            goto L_0x0078
        L_0x01c3:
            r4 = move-exception
            java.lang.String r4 = "InstanceID/Rpc"
            boolean r4 = android.util.Log.isLoggable(r4, r10)
            if (r4 == 0) goto L_0x01d3
            java.lang.String r4 = "InstanceID/Rpc"
            java.lang.String r5 = "Messenger failed, fallback to startService"
            android.util.Log.d(r4, r5)
        L_0x01d3:
            if (r0 == 0) goto L_0x0215
            monitor-enter(r11)
            android.content.BroadcastReceiver r0 = zzbhe     // Catch:{ all -> 0x0212 }
            if (r0 != 0) goto L_0x020b
            com.google.android.gms.iid.zzg r0 = new com.google.android.gms.iid.zzg     // Catch:{ all -> 0x0212 }
            r0.<init>(r11)     // Catch:{ all -> 0x0212 }
            zzbhe = r0     // Catch:{ all -> 0x0212 }
            java.lang.String r0 = "InstanceID/Rpc"
            r4 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r4)     // Catch:{ all -> 0x0212 }
            if (r0 == 0) goto L_0x01f1
            java.lang.String r0 = "InstanceID/Rpc"
            java.lang.String r4 = "Registered GSF callback receiver"
            android.util.Log.d(r0, r4)     // Catch:{ all -> 0x0212 }
        L_0x01f1:
            android.content.IntentFilter r0 = new android.content.IntentFilter     // Catch:{ all -> 0x0212 }
            java.lang.String r4 = "com.google.android.c2dm.intent.REGISTRATION"
            r0.<init>(r4)     // Catch:{ all -> 0x0212 }
            android.content.Context r4 = r11.zzqD     // Catch:{ all -> 0x0212 }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ all -> 0x0212 }
            r0.addCategory(r4)     // Catch:{ all -> 0x0212 }
            android.content.Context r4 = r11.zzqD     // Catch:{ all -> 0x0212 }
            android.content.BroadcastReceiver r5 = zzbhe     // Catch:{ all -> 0x0212 }
            java.lang.String r6 = "com.google.android.c2dm.permission.SEND"
            r7 = 0
            r4.registerReceiver(r5, r0, r6, r7)     // Catch:{ all -> 0x0212 }
        L_0x020b:
            monitor-exit(r11)     // Catch:{ all -> 0x0212 }
            android.content.Context r0 = r11.zzqD
            r0.sendBroadcast(r3)
            goto L_0x01a7
        L_0x0212:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0212 }
            throw r0
        L_0x0215:
            java.lang.String r0 = "google.messenger"
            android.os.Messenger r4 = r11.zzbfT
            r3.putExtra(r0, r4)
            java.lang.String r0 = "messenger2"
            java.lang.String r4 = "1"
            r3.putExtra(r0, r4)
            com.google.android.gms.iid.MessengerCompat r0 = r11.zzbhh
            if (r0 == 0) goto L_0x0244
            android.os.Message r0 = android.os.Message.obtain()
            r0.obj = r3
            com.google.android.gms.iid.MessengerCompat r4 = r11.zzbhh     // Catch:{ RemoteException -> 0x0234 }
            r4.send(r0)     // Catch:{ RemoteException -> 0x0234 }
            goto L_0x01a7
        L_0x0234:
            r0 = move-exception
            java.lang.String r0 = "InstanceID/Rpc"
            boolean r0 = android.util.Log.isLoggable(r0, r10)
            if (r0 == 0) goto L_0x0244
            java.lang.String r0 = "InstanceID/Rpc"
            java.lang.String r4 = "Messenger failed, fallback to startService"
            android.util.Log.d(r0, r4)
        L_0x0244:
            boolean r0 = zzbha
            if (r0 == 0) goto L_0x024f
            android.content.Context r0 = r11.zzqD
            r0.sendBroadcast(r3)
            goto L_0x01a7
        L_0x024f:
            android.content.Context r0 = r11.zzqD
            r0.startService(r3)
            goto L_0x01a7
        L_0x0256:
            boolean r2 = r0 instanceof java.lang.String     // Catch:{ all -> 0x0262 }
            if (r2 == 0) goto L_0x0265
            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0262 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0262 }
            r2.<init>(r0)     // Catch:{ all -> 0x0262 }
            throw r2     // Catch:{ all -> 0x0262 }
        L_0x0262:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0262 }
            throw r0
        L_0x0265:
            java.lang.String r2 = "InstanceID/Rpc"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0262 }
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0262 }
            int r3 = r3.length()     // Catch:{ all -> 0x0262 }
            int r3 = r3 + 12
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0262 }
            r4.<init>(r3)     // Catch:{ all -> 0x0262 }
            java.lang.String r3 = "No response "
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x0262 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0262 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0262 }
            android.util.Log.w(r2, r0)     // Catch:{ all -> 0x0262 }
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0262 }
            java.lang.String r2 = "TIMEOUT"
            r0.<init>(r2)     // Catch:{ all -> 0x0262 }
            throw r0     // Catch:{ all -> 0x0262 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zze.zzb(android.os.Bundle, java.security.KeyPair):android.content.Intent");
    }

    private static boolean zzb(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            zzbgZ = applicationInfo.packageName;
            zzbhc = applicationInfo.uid;
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean zzbc(Context context) {
        if (zzbgZ != null) {
            zzbd(context);
        }
        return zzbha;
    }

    public static String zzbd(Context context) {
        boolean z;
        if (zzbgZ != null) {
            return zzbgZ;
        }
        zzbhb = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        if (!zzq.isAtLeastO()) {
            Iterator<ResolveInfo> it = packageManager.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0).iterator();
            while (true) {
                if (it.hasNext()) {
                    if (zza(packageManager, it.next().serviceInfo.packageName, "com.google.android.c2dm.intent.REGISTER")) {
                        zzbha = false;
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                return zzbgZ;
            }
        }
        if (zza(packageManager)) {
            return zzbgZ;
        }
        Log.w("InstanceID/Rpc", "Failed to resolve IID implementation package, falling back");
        if (zzb(packageManager, "com.google.android.gms")) {
            zzbha = zzq.isAtLeastO();
            return zzbgZ;
        } else if (zzq.zzse() || !zzb(packageManager, "com.google.android.gsf")) {
            Log.w("InstanceID/Rpc", "Google Play services is missing, unable to get tokens");
            return null;
        } else {
            zzbha = false;
            return zzbgZ;
        }
    }

    private static int zzbe(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzbd(context), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private static void zze(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                String valueOf = String.valueOf(e);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Failed to send response ").append(valueOf).toString());
            }
        }
    }

    private final synchronized void zzg(Intent intent) {
        if (this.zzbfP == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzbfP = PendingIntent.getBroadcast(this.zzqD, 0, intent2, 0);
        }
        intent.putExtra(VKAttachments.TYPE_APP, this.zzbfP);
    }

    static String zzh(Intent intent) throws IOException {
        if (intent == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String stringExtra = intent.getStringExtra("registration_id");
        if (stringExtra == null) {
            stringExtra = intent.getStringExtra("unregistered");
        }
        intent.getLongExtra("Retry-After", 0);
        if (stringExtra != null) {
            return stringExtra;
        }
        String stringExtra2 = intent.getStringExtra("error");
        if (stringExtra2 != null) {
            throw new IOException(stringExtra2);
        }
        String valueOf = String.valueOf(intent.getExtras());
        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 29).append("Unexpected response from GCM ").append(valueOf).toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private final void zzi(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.zzbhf.get(str);
            this.zzbhf.put(str, obj);
            zze(obj2, obj);
        }
    }

    private final void zzvN() {
        if (this.zzbfT == null) {
            zzbd(this.zzqD);
            this.zzbfT = new Messenger(new zzf(this, Looper.getMainLooper()));
        }
    }

    private static synchronized String zzvO() {
        String num;
        synchronized (zze.class) {
            int i = zzbhd;
            zzbhd = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* access modifiers changed from: package-private */
    public final Intent zza(Bundle bundle, KeyPair keyPair) throws IOException {
        Intent zzb = zzb(bundle, keyPair);
        if (zzb == null || !zzb.hasExtra("google.messenger")) {
            return zzb;
        }
        Intent zzb2 = zzb(bundle, keyPair);
        if (zzb2 == null || !zzb2.hasExtra("google.messenger")) {
            return zzb2;
        }
        return null;
    }

    public final void zzc(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if (intent.hasExtra("google.messenger")) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.zzbhh = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.zzbhg = (Messenger) parcelableExtra;
                    }
                }
                zzi((Intent) message.obj);
                return;
            }
            Log.w("InstanceID/Rpc", "Dropping invalid message");
        }
    }

    public final void zzi(Intent intent) {
        String str;
        if (intent != null) {
            String action = intent.getAction();
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(action) || "com.google.android.gms.iid.InstanceID".equals(action)) {
                String stringExtra = intent.getStringExtra("registration_id");
                String stringExtra2 = stringExtra == null ? intent.getStringExtra("unregistered") : stringExtra;
                if (stringExtra2 == null) {
                    String stringExtra3 = intent.getStringExtra("error");
                    if (stringExtra3 == null) {
                        String valueOf = String.valueOf(intent.getExtras());
                        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
                        return;
                    }
                    if (Log.isLoggable("InstanceID/Rpc", 3)) {
                        String valueOf2 = String.valueOf(stringExtra3);
                        Log.d("InstanceID/Rpc", valueOf2.length() != 0 ? "Received InstanceID error ".concat(valueOf2) : new String("Received InstanceID error "));
                    }
                    if (stringExtra3.startsWith("|")) {
                        String[] split = stringExtra3.split("\\|");
                        if (!"ID".equals(split[1])) {
                            String valueOf3 = String.valueOf(stringExtra3);
                            Log.w("InstanceID/Rpc", valueOf3.length() != 0 ? "Unexpected structured response ".concat(valueOf3) : new String("Unexpected structured response "));
                        }
                        if (split.length > 2) {
                            str = split[2];
                            String str2 = split[3];
                            stringExtra3 = str2.startsWith(":") ? str2.substring(1) : str2;
                        } else {
                            stringExtra3 = "UNKNOWN";
                            str = null;
                        }
                        intent.putExtra("error", stringExtra3);
                    } else {
                        str = null;
                    }
                    if (str == null) {
                        zzB(stringExtra3);
                    } else {
                        zzi(str, stringExtra3);
                    }
                    long longExtra = intent.getLongExtra("Retry-After", 0);
                    if (longExtra > 0) {
                        this.zzbhj = SystemClock.elapsedRealtime();
                        this.zzbhl = ((int) longExtra) * 1000;
                        this.zzbhm = SystemClock.elapsedRealtime() + ((long) this.zzbhl);
                        Log.w("InstanceID/Rpc", new StringBuilder(52).append("Explicit request from server to backoff: ").append(this.zzbhl).toString());
                    } else if (("SERVICE_NOT_AVAILABLE".equals(stringExtra3) || "AUTHENTICATION_FAILED".equals(stringExtra3)) && "com.google.android.gsf".equals(zzbgZ)) {
                        this.zzbhk++;
                        if (this.zzbhk >= 3) {
                            if (this.zzbhk == 3) {
                                this.zzbhl = new Random().nextInt(1000) + 1000;
                            }
                            this.zzbhl <<= 1;
                            this.zzbhm = SystemClock.elapsedRealtime() + ((long) this.zzbhl);
                            Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(stringExtra3).length() + 31).append("Backoff due to ").append(stringExtra3).append(" for ").append(this.zzbhl).toString());
                        }
                    }
                } else {
                    this.zzbhi = SystemClock.elapsedRealtime();
                    this.zzbhm = 0;
                    this.zzbhk = 0;
                    this.zzbhl = 0;
                    String str3 = null;
                    if (stringExtra2.startsWith("|")) {
                        String[] split2 = stringExtra2.split("\\|");
                        if (!"ID".equals(split2[1])) {
                            String valueOf4 = String.valueOf(stringExtra2);
                            Log.w("InstanceID/Rpc", valueOf4.length() != 0 ? "Unexpected structured response ".concat(valueOf4) : new String("Unexpected structured response "));
                        }
                        String str4 = split2[2];
                        if (split2.length > 4) {
                            if ("SYNC".equals(split2[3])) {
                                Context context = this.zzqD;
                                Intent intent2 = new Intent("com.google.android.gms.iid.InstanceID");
                                intent2.putExtra("CMD", "SYNC");
                                intent2.setClassName(context, "com.google.android.gms.gcm.GcmReceiver");
                                context.sendBroadcast(intent2);
                            } else if ("RST".equals(split2[3])) {
                                Context context2 = this.zzqD;
                                InstanceID.getInstance(this.zzqD);
                                InstanceIDListenerService.zza(context2, InstanceID.zzvM());
                                intent.removeExtra("registration_id");
                                zzi(str4, intent);
                                return;
                            }
                        }
                        String str5 = split2[split2.length - 1];
                        if (str5.startsWith(":")) {
                            str5 = str5.substring(1);
                        }
                        intent.putExtra("registration_id", str5);
                        str3 = str4;
                    }
                    if (str3 == null) {
                        zzB(intent);
                    } else {
                        zzi(str3, intent);
                    }
                }
            } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                String valueOf5 = String.valueOf(intent.getAction());
                Log.d("InstanceID/Rpc", valueOf5.length() != 0 ? "Unexpected response ".concat(valueOf5) : new String("Unexpected response "));
            }
        } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
            Log.d("InstanceID/Rpc", "Unexpected response: null");
        }
    }
}
