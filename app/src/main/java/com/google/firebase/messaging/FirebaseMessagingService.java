package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.firebase.iid.zzb;
import com.google.firebase.iid.zzq;
import java.util.Iterator;

public class FirebaseMessagingService extends zzb {
    static boolean zzJ(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return "1".equals(bundle.getString("google.c.a.e"));
    }

    static void zzt(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0075, code lost:
        if (r0.equals(com.google.android.gms.gcm.GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE) != false) goto L_0x0056;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleIntent(android.content.Intent r6) {
        /*
            r5 = this;
            r3 = 1
            r1 = 0
            r2 = -1
            java.lang.String r0 = r6.getAction()
            if (r0 != 0) goto L_0x000b
            java.lang.String r0 = ""
        L_0x000b:
            int r4 = r0.hashCode()
            switch(r4) {
                case 75300319: goto L_0x003a;
                case 366519424: goto L_0x0030;
                default: goto L_0x0012;
            }
        L_0x0012:
            r0 = r2
        L_0x0013:
            switch(r0) {
                case 0: goto L_0x0044;
                case 1: goto L_0x010b;
                default: goto L_0x0016;
            }
        L_0x0016:
            java.lang.String r1 = "FirebaseMessaging"
            java.lang.String r2 = "Unknown intent action: "
            java.lang.String r0 = r6.getAction()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r3 = r0.length()
            if (r3 == 0) goto L_0x011a
            java.lang.String r0 = r2.concat(r0)
        L_0x002c:
            android.util.Log.d(r1, r0)
        L_0x002f:
            return
        L_0x0030:
            java.lang.String r4 = "com.google.android.c2dm.intent.RECEIVE"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0012
            r0 = r1
            goto L_0x0013
        L_0x003a:
            java.lang.String r4 = "com.google.firebase.messaging.NOTIFICATION_DISMISS"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0012
            r0 = r3
            goto L_0x0013
        L_0x0044:
            java.lang.String r0 = "message_type"
            java.lang.String r0 = r6.getStringExtra(r0)
            if (r0 != 0) goto L_0x004e
            java.lang.String r0 = "gcm"
        L_0x004e:
            int r4 = r0.hashCode()
            switch(r4) {
                case -2062414158: goto L_0x0078;
                case 102161: goto L_0x006f;
                case 814694033: goto L_0x008c;
                case 814800675: goto L_0x0082;
                default: goto L_0x0055;
            }
        L_0x0055:
            r1 = r2
        L_0x0056:
            switch(r1) {
                case 0: goto L_0x0096;
                case 1: goto L_0x00d6;
                case 2: goto L_0x00db;
                case 3: goto L_0x00e6;
                default: goto L_0x0059;
            }
        L_0x0059:
            java.lang.String r1 = "FirebaseMessaging"
            java.lang.String r2 = "Received message with unknown type: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r3 = r0.length()
            if (r3 == 0) goto L_0x0104
            java.lang.String r0 = r2.concat(r0)
        L_0x006b:
            android.util.Log.w(r1, r0)
            goto L_0x002f
        L_0x006f:
            java.lang.String r3 = "gcm"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0055
            goto L_0x0056
        L_0x0078:
            java.lang.String r1 = "deleted_messages"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0055
            r1 = r3
            goto L_0x0056
        L_0x0082:
            java.lang.String r1 = "send_event"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0055
            r1 = 2
            goto L_0x0056
        L_0x008c:
            java.lang.String r1 = "send_error"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0055
            r1 = 3
            goto L_0x0056
        L_0x0096:
            android.os.Bundle r0 = r6.getExtras()
            boolean r0 = zzJ(r0)
            if (r0 == 0) goto L_0x00a3
            com.google.firebase.messaging.zzd.zzg(r5, r6)
        L_0x00a3:
            android.os.Bundle r0 = r6.getExtras()
            if (r0 != 0) goto L_0x00ae
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
        L_0x00ae:
            java.lang.String r1 = "android.support.content.wakelockid"
            r0.remove(r1)
            boolean r1 = com.google.firebase.messaging.zza.zzG(r0)
            if (r1 == 0) goto L_0x00cc
            com.google.firebase.messaging.zza r1 = com.google.firebase.messaging.zza.zzbM(r5)
            boolean r1 = r1.zzv(r0)
            if (r1 != 0) goto L_0x002f
            boolean r1 = zzJ(r0)
            if (r1 == 0) goto L_0x00cc
            com.google.firebase.messaging.zzd.zzj(r5, r6)
        L_0x00cc:
            com.google.firebase.messaging.RemoteMessage r1 = new com.google.firebase.messaging.RemoteMessage
            r1.<init>(r0)
            r5.onMessageReceived(r1)
            goto L_0x002f
        L_0x00d6:
            r5.onDeletedMessages()
            goto L_0x002f
        L_0x00db:
            java.lang.String r0 = "google.message_id"
            java.lang.String r0 = r6.getStringExtra(r0)
            r5.onMessageSent(r0)
            goto L_0x002f
        L_0x00e6:
            java.lang.String r0 = "google.message_id"
            java.lang.String r0 = r6.getStringExtra(r0)
            if (r0 != 0) goto L_0x00f4
            java.lang.String r0 = "message_id"
            java.lang.String r0 = r6.getStringExtra(r0)
        L_0x00f4:
            com.google.firebase.messaging.SendException r1 = new com.google.firebase.messaging.SendException
            java.lang.String r2 = "error"
            java.lang.String r2 = r6.getStringExtra(r2)
            r1.<init>(r2)
            r5.onSendError(r0, r1)
            goto L_0x002f
        L_0x0104:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
            goto L_0x006b
        L_0x010b:
            android.os.Bundle r0 = r6.getExtras()
            boolean r0 = zzJ(r0)
            if (r0 == 0) goto L_0x002f
            com.google.firebase.messaging.zzd.zzi(r5, r6)
            goto L_0x002f
        L_0x011a:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessagingService.handleIntent(android.content.Intent):void");
    }

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onMessageSent(String str) {
    }

    @WorkerThread
    public void onSendError(String str, Exception exc) {
    }

    /* access modifiers changed from: protected */
    public final Intent zzn(Intent intent) {
        return zzq.zzJX().zzJY();
    }

    public final boolean zzo(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (zzJ(intent.getExtras())) {
            zzd.zzh(this, intent);
        }
        return true;
    }
}
