package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.beetalk.sdk.helper.Security;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.MessengerCompat;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.Iterator;
import java.util.Random;

public final class zzl {
    private static PendingIntent zzbfP;
    private static String zzbgZ = null;
    private static boolean zzbha = false;
    private static int zzbhb = 0;
    private static int zzbhc = 0;
    private static int zzbhd = 0;
    private static BroadcastReceiver zzbhe = null;
    private Messenger zzbfT;
    private Messenger zzbhg;
    private MessengerCompat zzbhh;
    private long zzbhi;
    private long zzbhj;
    private int zzbhk;
    private int zzbhl;
    private long zzbhm;
    private final SimpleArrayMap<String, zzp> zzckI = new SimpleArrayMap<>();
    private Context zzqD;

    public zzl(Context context) {
        this.zzqD = context;
    }

    private static String zza(KeyPair keyPair, String... strArr) {
        try {
            byte[] bytes = TextUtils.join("\n", strArr).getBytes("UTF-8");
            try {
                PrivateKey privateKey = keyPair.getPrivate();
                Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? Security.SHA256_WITH_RSA : "SHA256withECDSA");
                instance.initSign(privateKey);
                instance.update(bytes);
                return FirebaseInstanceId.zzj(instance.sign());
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

    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzah(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            android.support.v4.util.SimpleArrayMap<java.lang.String, com.google.firebase.iid.zzp> r2 = r5.zzckI
            monitor-enter(r2)
            if (r6 != 0) goto L_0x0025
            r0 = 0
            r1 = r0
        L_0x0007:
            android.support.v4.util.SimpleArrayMap<java.lang.String, com.google.firebase.iid.zzp> r0 = r5.zzckI     // Catch:{ all -> 0x0046 }
            int r0 = r0.size()     // Catch:{ all -> 0x0046 }
            if (r1 >= r0) goto L_0x001e
            android.support.v4.util.SimpleArrayMap<java.lang.String, com.google.firebase.iid.zzp> r0 = r5.zzckI     // Catch:{ all -> 0x0046 }
            java.lang.Object r0 = r0.valueAt(r1)     // Catch:{ all -> 0x0046 }
            com.google.firebase.iid.zzp r0 = (com.google.firebase.iid.zzp) r0     // Catch:{ all -> 0x0046 }
            r0.onError(r7)     // Catch:{ all -> 0x0046 }
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0007
        L_0x001e:
            android.support.v4.util.SimpleArrayMap<java.lang.String, com.google.firebase.iid.zzp> r0 = r5.zzckI     // Catch:{ all -> 0x0046 }
            r0.clear()     // Catch:{ all -> 0x0046 }
        L_0x0023:
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
        L_0x0024:
            return
        L_0x0025:
            android.support.v4.util.SimpleArrayMap<java.lang.String, com.google.firebase.iid.zzp> r0 = r5.zzckI     // Catch:{ all -> 0x0046 }
            java.lang.Object r0 = r0.remove(r6)     // Catch:{ all -> 0x0046 }
            com.google.firebase.iid.zzp r0 = (com.google.firebase.iid.zzp) r0     // Catch:{ all -> 0x0046 }
            if (r0 != 0) goto L_0x004f
            java.lang.String r1 = "InstanceID/Rpc"
            java.lang.String r3 = "Missing callback for "
            java.lang.String r0 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0046 }
            int r4 = r0.length()     // Catch:{ all -> 0x0046 }
            if (r4 == 0) goto L_0x0049
            java.lang.String r0 = r3.concat(r0)     // Catch:{ all -> 0x0046 }
        L_0x0041:
            android.util.Log.w(r1, r0)     // Catch:{ all -> 0x0046 }
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            goto L_0x0024
        L_0x0046:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            throw r0
        L_0x0049:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x0046 }
            r0.<init>(r3)     // Catch:{ all -> 0x0046 }
            goto L_0x0041
        L_0x004f:
            r0.onError(r7)     // Catch:{ all -> 0x0046 }
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzl.zzah(java.lang.String, java.lang.String):void");
    }

    private final Intent zzb(Bundle bundle, KeyPair keyPair) throws IOException {
        String zzvO = zzvO();
        zzo zzo = new zzo((zzm) null);
        synchronized (this.zzckI) {
            this.zzckI.put(zzvO, zzo);
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzbhm == 0 || elapsedRealtime > this.zzbhm) {
            zzvN();
            if (zzbgZ == null) {
                throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
            }
            this.zzbhi = SystemClock.elapsedRealtime();
            Intent intent = new Intent(zzbha ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzbgZ);
            bundle.putString("gmsv", Integer.toString(FirebaseInstanceId.zzO(this.zzqD, zzbd(this.zzqD))));
            bundle.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
            bundle.putString("app_ver", Integer.toString(FirebaseInstanceId.zzbF(this.zzqD)));
            bundle.putString("app_ver_name", FirebaseInstanceId.zzbb(this.zzqD));
            bundle.putString("cliv", "fiid-11020000");
            bundle.putString("appid", FirebaseInstanceId.zza(keyPair));
            String zzj = FirebaseInstanceId.zzj(keyPair.getPublic().getEncoded());
            bundle.putString("pub2", zzj);
            bundle.putString(VKApiConst.SIG, zza(keyPair, this.zzqD.getPackageName(), zzj));
            intent.putExtras(bundle);
            zzd(this.zzqD, intent);
            this.zzbhi = SystemClock.elapsedRealtime();
            intent.putExtra("kid", new StringBuilder(String.valueOf(zzvO).length() + 5).append("|ID|").append(zzvO).append("|").toString());
            intent.putExtra("X-kid", new StringBuilder(String.valueOf(zzvO).length() + 5).append("|ID|").append(zzvO).append("|").toString());
            boolean equals = "com.google.android.gsf".equals(zzbgZ);
            if (Log.isLoggable("InstanceID/Rpc", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                Log.d("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
            }
            if (equals) {
                synchronized (this) {
                    if (zzbhe == null) {
                        zzbhe = new zzn(this);
                        if (Log.isLoggable("InstanceID/Rpc", 3)) {
                            Log.d("InstanceID/Rpc", "Registered GSF callback receiver");
                        }
                        IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
                        intentFilter.addCategory(this.zzqD.getPackageName());
                        this.zzqD.registerReceiver(zzbhe, intentFilter, "com.google.android.c2dm.permission.SEND", (Handler) null);
                    }
                }
                this.zzqD.startService(intent);
            } else {
                intent.putExtra("google.messenger", this.zzbfT);
                if (!(this.zzbhg == null && this.zzbhh == null)) {
                    Message obtain = Message.obtain();
                    obtain.obj = intent;
                    try {
                        if (this.zzbhg != null) {
                            this.zzbhg.send(obtain);
                        } else {
                            this.zzbhh.send(obtain);
                        }
                    } catch (RemoteException e) {
                        if (Log.isLoggable("InstanceID/Rpc", 3)) {
                            Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                        }
                    }
                }
                if (zzbha) {
                    this.zzqD.sendBroadcast(intent);
                } else {
                    this.zzqD.startService(intent);
                }
            }
            try {
                Intent zzJW = zzo.zzJW();
                synchronized (this.zzckI) {
                    this.zzckI.remove(zzvO);
                }
                return zzJW;
            } catch (Throwable th) {
                synchronized (this.zzckI) {
                    this.zzckI.remove(zzvO);
                    throw th;
                }
            }
        } else {
            Log.w("InstanceID/Rpc", new StringBuilder(78).append("Backoff mode, next request attempt: ").append(this.zzbhm - elapsedRealtime).append(" interval: ").append(this.zzbhl).toString());
            throw new IOException(InstanceID.ERROR_BACKOFF);
        }
    }

    private final void zzb(String str, Intent intent) {
        synchronized (this.zzckI) {
            zzp remove = this.zzckI.remove(str);
            if (remove == null) {
                String valueOf = String.valueOf(str);
                Log.w("InstanceID/Rpc", valueOf.length() != 0 ? "Missing callback for ".concat(valueOf) : new String("Missing callback for "));
                return;
            }
            remove.zzq(intent);
        }
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

    public static synchronized void zzd(Context context, Intent intent) {
        synchronized (zzl.class) {
            if (zzbfP == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzbfP = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra(VKAttachments.TYPE_APP, zzbfP);
        }
    }

    private final void zzvN() {
        if (this.zzbfT == null) {
            zzbd(this.zzqD);
            this.zzbfT = new Messenger(new zzm(this, Looper.getMainLooper()));
        }
    }

    public static synchronized String zzvO() {
        String num;
        synchronized (zzl.class) {
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

    /* access modifiers changed from: package-private */
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

    /* access modifiers changed from: package-private */
    public final void zzi(Intent intent) {
        String str;
        String str2 = null;
        if (intent == null) {
            if (Log.isLoggable("InstanceID/Rpc", 3)) {
                Log.d("InstanceID/Rpc", "Unexpected response: null");
            }
        } else if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                String stringExtra2 = intent.getStringExtra("error");
                if (stringExtra2 == null) {
                    String valueOf = String.valueOf(intent.getExtras());
                    Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
                    return;
                }
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    String valueOf2 = String.valueOf(stringExtra2);
                    Log.d("InstanceID/Rpc", valueOf2.length() != 0 ? "Received InstanceID error ".concat(valueOf2) : new String("Received InstanceID error "));
                }
                if (stringExtra2.startsWith("|")) {
                    String[] split = stringExtra2.split("\\|");
                    if (!"ID".equals(split[1])) {
                        String valueOf3 = String.valueOf(stringExtra2);
                        Log.w("InstanceID/Rpc", valueOf3.length() != 0 ? "Unexpected structured response ".concat(valueOf3) : new String("Unexpected structured response "));
                    }
                    if (split.length > 2) {
                        str = split[2];
                        String str3 = split[3];
                        stringExtra2 = str3.startsWith(":") ? str3.substring(1) : str3;
                    } else {
                        stringExtra2 = "UNKNOWN";
                        str = null;
                    }
                    intent.putExtra("error", stringExtra2);
                } else {
                    str = null;
                }
                zzah(str, stringExtra2);
                long longExtra = intent.getLongExtra("Retry-After", 0);
                if (longExtra > 0) {
                    this.zzbhj = SystemClock.elapsedRealtime();
                    this.zzbhl = ((int) longExtra) * 1000;
                    this.zzbhm = SystemClock.elapsedRealtime() + ((long) this.zzbhl);
                    Log.w("InstanceID/Rpc", new StringBuilder(52).append("Explicit request from server to backoff: ").append(this.zzbhl).toString());
                } else if (("SERVICE_NOT_AVAILABLE".equals(stringExtra2) || "AUTHENTICATION_FAILED".equals(stringExtra2)) && "com.google.android.gsf".equals(zzbgZ)) {
                    this.zzbhk++;
                    if (this.zzbhk >= 3) {
                        if (this.zzbhk == 3) {
                            this.zzbhl = new Random().nextInt(1000) + 1000;
                        }
                        this.zzbhl <<= 1;
                        this.zzbhm = SystemClock.elapsedRealtime() + ((long) this.zzbhl);
                        Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf(stringExtra2).length() + 31).append("Backoff due to ").append(stringExtra2).append(" for ").append(this.zzbhl).toString());
                    }
                }
            } else {
                this.zzbhi = SystemClock.elapsedRealtime();
                this.zzbhm = 0;
                this.zzbhk = 0;
                this.zzbhl = 0;
                if (stringExtra.startsWith("|")) {
                    String[] split2 = stringExtra.split("\\|");
                    if (!"ID".equals(split2[1])) {
                        String valueOf4 = String.valueOf(stringExtra);
                        Log.w("InstanceID/Rpc", valueOf4.length() != 0 ? "Unexpected structured response ".concat(valueOf4) : new String("Unexpected structured response "));
                    }
                    String str4 = split2[2];
                    if (split2.length > 4) {
                        if ("SYNC".equals(split2[3])) {
                            FirebaseInstanceId.zzbG(this.zzqD);
                        } else if ("RST".equals(split2[3])) {
                            Context context = this.zzqD;
                            zzj.zzb(this.zzqD, (Bundle) null);
                            FirebaseInstanceId.zza(context, zzj.zzJT());
                            intent.removeExtra("registration_id");
                            zzb(str4, intent);
                            return;
                        }
                    }
                    String str5 = split2[split2.length - 1];
                    if (str5.startsWith(":")) {
                        str5 = str5.substring(1);
                    }
                    intent.putExtra("registration_id", str5);
                    str2 = str4;
                }
                if (str2 != null) {
                    zzb(str2, intent);
                } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Ignoring response without a request ID");
                }
            }
        } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String valueOf5 = String.valueOf(intent.getAction());
            Log.d("InstanceID/Rpc", valueOf5.length() != 0 ? "Unexpected response ".concat(valueOf5) : new String("Unexpected response "));
        }
    }
}
