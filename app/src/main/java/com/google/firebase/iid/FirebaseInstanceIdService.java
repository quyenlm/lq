package com.google.firebase.iid;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class FirebaseInstanceIdService extends zzb {
    @VisibleForTesting
    private static Object zzckB = new Object();
    @VisibleForTesting
    private static boolean zzckC = false;
    private boolean zzckD = false;

    static class zza extends BroadcastReceiver {
        @Nullable
        private static BroadcastReceiver receiver;
        private int zzckE;

        private zza(int i) {
            this.zzckE = i;
        }

        static synchronized void zzl(Context context, int i) {
            synchronized (zza.class) {
                if (receiver == null) {
                    receiver = new zza(i);
                    context.getApplicationContext().registerReceiver(receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }
            }
        }

        public void onReceive(Context context, Intent intent) {
            synchronized (zza.class) {
                if (receiver == this) {
                    if (FirebaseInstanceIdService.zzbJ(context)) {
                        if (Log.isLoggable("FirebaseInstanceId", 3)) {
                            Log.d("FirebaseInstanceId", "connectivity changed. starting background sync.");
                        }
                        context.getApplicationContext().unregisterReceiver(this);
                        receiver = null;
                        zzq.zzJX().zze(context, FirebaseInstanceIdService.zzbZ(this.zzckE));
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        if (r0.zzhp(com.google.firebase.iid.zzj.zzbgW) != false) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        if (com.google.firebase.iid.FirebaseInstanceId.zzJS().zzJV() == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        zzbI(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000a, code lost:
        r0 = r3.zzJQ();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r0 == null) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void zza(android.content.Context r2, com.google.firebase.iid.FirebaseInstanceId r3) {
        /*
            java.lang.Object r1 = zzckB
            monitor-enter(r1)
            boolean r0 = zzckC     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
        L_0x0008:
            return
        L_0x0009:
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            com.google.firebase.iid.zzs r0 = r3.zzJQ()
            if (r0 == 0) goto L_0x0022
            java.lang.String r1 = com.google.firebase.iid.zzj.zzbgW
            boolean r0 = r0.zzhp(r1)
            if (r0 != 0) goto L_0x0022
            com.google.firebase.iid.zzk r0 = com.google.firebase.iid.FirebaseInstanceId.zzJS()
            java.lang.String r0 = r0.zzJV()
            if (r0 == 0) goto L_0x0008
        L_0x0022:
            zzbI(r2)
            goto L_0x0008
        L_0x0026:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceIdService.zza(android.content.Context, com.google.firebase.iid.FirebaseInstanceId):void");
    }

    private final void zza(Intent intent, String str) {
        int i = 28800;
        boolean zzbJ = zzbJ(this);
        int intExtra = intent == null ? 10 : intent.getIntExtra("next_retry_delay_in_seconds", 0);
        if (intExtra < 10 && !zzbJ) {
            i = 30;
        } else if (intExtra < 10) {
            i = 10;
        } else if (intExtra <= 28800) {
            i = intExtra;
        }
        Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(str).length() + 47).append("background sync failed: ").append(str).append(", retry in ").append(i).append("s").toString());
        synchronized (zzckB) {
            ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).set(3, SystemClock.elapsedRealtime() + ((long) (i * 1000)), zzq.zza(this, 0, zzbZ(i << 1), 134217728));
            zzckC = true;
        }
        if (!zzbJ) {
            if (this.zzckD) {
                Log.d("FirebaseInstanceId", "device not connected. Connectivity change received registered");
            }
            zza.zzl(this, i);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00a4 A[Catch:{ IOException -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c1 A[SYNTHETIC, Splitter:B:59:0x00c1] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0087 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(android.content.Intent r9, boolean r10, boolean r11) {
        /*
            r8 = this;
            r2 = 1
            r1 = 0
            java.lang.Object r3 = zzckB
            monitor-enter(r3)
            r0 = 0
            zzckC = r0     // Catch:{ all -> 0x0010 }
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            java.lang.String r0 = com.google.firebase.iid.zzl.zzbd(r8)
            if (r0 != 0) goto L_0x0013
        L_0x000f:
            return
        L_0x0010:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            com.google.firebase.iid.FirebaseInstanceId r0 = com.google.firebase.iid.FirebaseInstanceId.getInstance()
            com.google.firebase.iid.zzs r3 = r0.zzJQ()
            if (r3 == 0) goto L_0x0025
            java.lang.String r4 = com.google.firebase.iid.zzj.zzbgW
            boolean r4 = r3.zzhp(r4)
            if (r4 == 0) goto L_0x0063
        L_0x0025:
            java.lang.String r1 = r0.zzJR()     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            if (r1 == 0) goto L_0x0054
            boolean r2 = r8.zzckD     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            if (r2 == 0) goto L_0x0036
            java.lang.String r2 = "FirebaseInstanceId"
            java.lang.String r4 = "get master token succeeded"
            android.util.Log.d(r2, r4)     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
        L_0x0036:
            zza((android.content.Context) r8, (com.google.firebase.iid.FirebaseInstanceId) r0)     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            if (r11 != 0) goto L_0x0047
            if (r3 == 0) goto L_0x0047
            if (r3 == 0) goto L_0x000f
            java.lang.String r0 = r3.zzbPJ     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            boolean r0 = r1.equals(r0)     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            if (r0 != 0) goto L_0x000f
        L_0x0047:
            r8.onTokenRefresh()     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            goto L_0x000f
        L_0x004b:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            r8.zza((android.content.Intent) r9, (java.lang.String) r0)
            goto L_0x000f
        L_0x0054:
            java.lang.String r0 = "returned token is null"
            r8.zza((android.content.Intent) r9, (java.lang.String) r0)     // Catch:{ IOException -> 0x004b, SecurityException -> 0x005a }
            goto L_0x000f
        L_0x005a:
            r0 = move-exception
            java.lang.String r1 = "FirebaseInstanceId"
            java.lang.String r2 = "Unable to get master token"
            android.util.Log.e(r1, r2, r0)
            goto L_0x000f
        L_0x0063:
            com.google.firebase.iid.zzk r4 = com.google.firebase.iid.FirebaseInstanceId.zzJS()
            java.lang.String r0 = r4.zzJV()
            r3 = r0
        L_0x006c:
            if (r3 == 0) goto L_0x00d4
            java.lang.String r0 = "!"
            java.lang.String[] r0 = r3.split(r0)
            int r5 = r0.length
            r6 = 2
            if (r5 != r6) goto L_0x0087
            r5 = r0[r1]
            r6 = r0[r2]
            r0 = -1
            int r7 = r5.hashCode()     // Catch:{ IOException -> 0x00b7 }
            switch(r7) {
                case 83: goto L_0x0090;
                case 84: goto L_0x0084;
                case 85: goto L_0x009a;
                default: goto L_0x0084;
            }
        L_0x0084:
            switch(r0) {
                case 0: goto L_0x00a4;
                case 1: goto L_0x00c1;
                default: goto L_0x0087;
            }
        L_0x0087:
            r4.zzhj(r3)
            java.lang.String r0 = r4.zzJV()
            r3 = r0
            goto L_0x006c
        L_0x0090:
            java.lang.String r7 = "S"
            boolean r5 = r5.equals(r7)     // Catch:{ IOException -> 0x00b7 }
            if (r5 == 0) goto L_0x0084
            r0 = r1
            goto L_0x0084
        L_0x009a:
            java.lang.String r7 = "U"
            boolean r5 = r5.equals(r7)     // Catch:{ IOException -> 0x00b7 }
            if (r5 == 0) goto L_0x0084
            r0 = r2
            goto L_0x0084
        L_0x00a4:
            com.google.firebase.iid.FirebaseInstanceId r0 = com.google.firebase.iid.FirebaseInstanceId.getInstance()     // Catch:{ IOException -> 0x00b7 }
            r0.zzhg(r6)     // Catch:{ IOException -> 0x00b7 }
            boolean r0 = r8.zzckD     // Catch:{ IOException -> 0x00b7 }
            if (r0 == 0) goto L_0x0087
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r5 = "subscribe operation succeeded"
            android.util.Log.d(r0, r5)     // Catch:{ IOException -> 0x00b7 }
            goto L_0x0087
        L_0x00b7:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            r8.zza((android.content.Intent) r9, (java.lang.String) r0)
            goto L_0x000f
        L_0x00c1:
            com.google.firebase.iid.FirebaseInstanceId r0 = com.google.firebase.iid.FirebaseInstanceId.getInstance()     // Catch:{ IOException -> 0x00b7 }
            r0.zzhh(r6)     // Catch:{ IOException -> 0x00b7 }
            boolean r0 = r8.zzckD     // Catch:{ IOException -> 0x00b7 }
            if (r0 == 0) goto L_0x0087
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r5 = "unsubscribe operation succeeded"
            android.util.Log.d(r0, r5)     // Catch:{ IOException -> 0x00b7 }
            goto L_0x0087
        L_0x00d4:
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r1 = "topic sync succeeded"
            android.util.Log.d(r0, r1)
            goto L_0x000f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceIdService.zza(android.content.Intent, boolean, boolean):void");
    }

    static void zzbI(Context context) {
        if (zzl.zzbd(context) != null) {
            synchronized (zzckB) {
                if (!zzckC) {
                    zzq.zzJX().zze(context, zzbZ(0));
                    zzckC = true;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean zzbJ(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* access modifiers changed from: private */
    public static Intent zzbZ(int i) {
        Intent intent = new Intent("ACTION_TOKEN_REFRESH_RETRY");
        intent.putExtra("next_retry_delay_in_seconds", i);
        return intent;
    }

    private final zzj zzhi(String str) {
        if (str == null) {
            return zzj.zzb(this, (Bundle) null);
        }
        Bundle bundle = new Bundle();
        bundle.putString("subtype", str);
        return zzj.zzb(this, bundle);
    }

    private static String zzp(Intent intent) {
        String stringExtra = intent.getStringExtra("subtype");
        return stringExtra == null ? "" : stringExtra;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleIntent(android.content.Intent r10) {
        /*
            r9 = this;
            r1 = 0
            r8 = 1
            java.lang.String r0 = r10.getAction()
            if (r0 != 0) goto L_0x000a
            java.lang.String r0 = ""
        L_0x000a:
            r2 = -1
            int r3 = r0.hashCode()
            switch(r3) {
                case -1737547627: goto L_0x0093;
                default: goto L_0x0012;
            }
        L_0x0012:
            r0 = r2
        L_0x0013:
            switch(r0) {
                case 0: goto L_0x009e;
                default: goto L_0x0016;
            }
        L_0x0016:
            java.lang.String r0 = zzp(r10)
            com.google.firebase.iid.zzj r2 = r9.zzhi(r0)
            java.lang.String r3 = "CMD"
            java.lang.String r3 = r10.getStringExtra(r3)
            boolean r4 = r9.zzckD
            if (r4 == 0) goto L_0x0078
            java.lang.String r4 = "FirebaseInstanceId"
            android.os.Bundle r5 = r10.getExtras()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r6 = java.lang.String.valueOf(r0)
            int r6 = r6.length()
            int r6 = r6 + 18
            java.lang.String r7 = java.lang.String.valueOf(r3)
            int r7 = r7.length()
            int r6 = r6 + r7
            java.lang.String r7 = java.lang.String.valueOf(r5)
            int r7 = r7.length()
            int r6 = r6 + r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.String r6 = "Service command "
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r3)
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0078:
            java.lang.String r4 = "unregistered"
            java.lang.String r4 = r10.getStringExtra(r4)
            if (r4 == 0) goto L_0x00a2
            com.google.firebase.iid.zzr r1 = com.google.firebase.iid.zzj.zzJT()
            if (r0 != 0) goto L_0x0088
            java.lang.String r0 = ""
        L_0x0088:
            r1.zzdr(r0)
            com.google.firebase.iid.zzl r0 = com.google.firebase.iid.zzj.zzJU()
            r0.zzi(r10)
        L_0x0092:
            return
        L_0x0093:
            java.lang.String r3 = "ACTION_TOKEN_REFRESH_RETRY"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0012
            r0 = r1
            goto L_0x0013
        L_0x009e:
            r9.zza(r10, r1, r1)
            goto L_0x0092
        L_0x00a2:
            java.lang.String r4 = "gcm.googleapis.com/refresh"
            java.lang.String r5 = "from"
            java.lang.String r5 = r10.getStringExtra(r5)
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00bb
            com.google.firebase.iid.zzr r2 = com.google.firebase.iid.zzj.zzJT()
            r2.zzdr(r0)
            r9.zza(r10, r1, r8)
            goto L_0x0092
        L_0x00bb:
            java.lang.String r4 = "RST"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x00ca
            r2.zzvL()
            r9.zza(r10, r8, r8)
            goto L_0x0092
        L_0x00ca:
            java.lang.String r4 = "RST_FULL"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x00ea
            com.google.firebase.iid.zzr r0 = com.google.firebase.iid.zzj.zzJT()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0092
            r2.zzvL()
            com.google.firebase.iid.zzr r0 = com.google.firebase.iid.zzj.zzJT()
            r0.zzvP()
            r9.zza(r10, r8, r8)
            goto L_0x0092
        L_0x00ea:
            java.lang.String r2 = "SYNC"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x00fd
            com.google.firebase.iid.zzr r2 = com.google.firebase.iid.zzj.zzJT()
            r2.zzdr(r0)
            r9.zza(r10, r1, r8)
            goto L_0x0092
        L_0x00fd:
            java.lang.String r0 = "PING"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0092
            android.os.Bundle r0 = r10.getExtras()
            java.lang.String r1 = com.google.firebase.iid.zzl.zzbd(r9)
            if (r1 != 0) goto L_0x0118
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r1 = "Unable to respond to ping due to missing target package"
            android.util.Log.w(r0, r1)
            goto L_0x0092
        L_0x0118:
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = "com.google.android.gcm.intent.SEND"
            r2.<init>(r3)
            r2.setPackage(r1)
            r2.putExtras(r0)
            com.google.firebase.iid.zzl.zzd(r9, r2)
            java.lang.String r0 = "google.to"
            java.lang.String r1 = "google.com/iid"
            r2.putExtra(r0, r1)
            java.lang.String r0 = "google.message_id"
            java.lang.String r1 = com.google.firebase.iid.zzl.zzvO()
            r2.putExtra(r0, r1)
            java.lang.String r0 = "com.google.android.gtalkservice.permission.GTALK_SERVICE"
            r9.sendOrderedBroadcast(r2, r0)
            goto L_0x0092
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceIdService.handleIntent(android.content.Intent):void");
    }

    @WorkerThread
    public void onTokenRefresh() {
    }

    /* access modifiers changed from: protected */
    public final Intent zzn(Intent intent) {
        return zzq.zzJX().zzckP.poll();
    }

    public final boolean zzo(Intent intent) {
        this.zzckD = Log.isLoggable("FirebaseInstanceId", 3);
        if (intent.getStringExtra("error") == null && intent.getStringExtra("registration_id") == null) {
            return false;
        }
        String zzp = zzp(intent);
        if (this.zzckD) {
            String valueOf = String.valueOf(zzp);
            Log.d("FirebaseInstanceId", valueOf.length() != 0 ? "Register result in service ".concat(valueOf) : new String("Register result in service "));
        }
        zzhi(zzp);
        zzj.zzJU().zzi(intent);
        return true;
    }
}
