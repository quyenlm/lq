package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.appsflyer.b;
import com.appsflyer.j;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.lang.ref.WeakReference;

final class y {
    y() {
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static boolean m165(Context context) {
        return m166(context) | m163(context);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static boolean m166(Context context) {
        boolean z;
        boolean z2;
        boolean z3;
        if (AppsFlyerLib.getInstance().isTrackingStopped()) {
            return false;
        }
        try {
            Class.forName("com.google.android.gms.iid.InstanceIDListenerService");
            Intent intent = new Intent("com.google.android.gms.iid.InstanceID", (Uri) null, context, GcmInstanceIdListener.class);
            Intent intent2 = new Intent("com.google.android.gms.iid.InstanceID", (Uri) null, context, InstanceIDListenerService.class);
            if (context.getPackageManager().queryIntentServices(intent, 0).size() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (context.getPackageManager().queryIntentServices(intent2, 0).size() > 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    return false;
                }
            }
            if (context.getPackageManager().queryBroadcastReceivers(new Intent("com.google.android.c2dm.intent.RECEIVE", (Uri) null, context, Class.forName("com.google.android.gms.gcm.GcmReceiver")), 0).size() > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (j.AnonymousClass1.m91(context, new StringBuilder().append(context.getPackageName()).append(".permission.C2D_MESSAGE").toString())) {
                    return true;
                }
                AFLogger.afWarnLog("Cannot verify existence of the app's \"permission.C2D_MESSAGE\" permission in the manifest. Please refer to documentation.");
                return false;
            }
            AFLogger.afWarnLog("Cannot verify existence of GcmReceiver receiver in the manifest. Please refer to documentation.");
            return false;
        } catch (ClassNotFoundException e) {
            AFLogger.afRDLog(e.getMessage());
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest declarations: ", th);
            return false;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private static boolean m163(Context context) {
        boolean z;
        boolean z2;
        if (AppsFlyerLib.getInstance().isTrackingStopped()) {
            return false;
        }
        try {
            Class.forName("com.google.firebase.iid.FirebaseInstanceIdService");
            Intent intent = new Intent("com.google.firebase.INSTANCE_ID_EVENT", (Uri) null, context, FirebaseInstanceIdListener.class);
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT", (Uri) null, context, FirebaseInstanceIdService.class);
            if (context.getPackageManager().queryIntentServices(intent, 0).size() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (context.getPackageManager().queryIntentServices(intent2, 0).size() > 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    AFLogger.afWarnLog("Cannot verify existence of our InstanceID Listener Service in the manifest. Please refer to documentation.");
                    return false;
                }
            }
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest declarations: ", th);
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public static String m162(WeakReference<Context> weakReference, String str) {
        try {
            Class<?> cls = Class.forName("com.google.android.gms.iid.InstanceID");
            Class.forName("com.google.android.gms.gcm.GcmReceiver");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(cls, new Object[]{weakReference.get()});
            String str2 = (String) cls.getDeclaredMethod("getToken", new Class[]{String.class, String.class}).invoke(invoke, new Object[]{str, GoogleCloudMessaging.INSTANCE_ID_SCOPE});
            if (str2 != null) {
                return str2;
            }
            AFLogger.afWarnLog("Couldn't get token using reflection.");
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Throwable th) {
            AFLogger.afErrorLog("Couldn't get token using GoogleCloudMessaging. ", th);
            return null;
        }
    }

    /* renamed from: ॱ  reason: contains not printable characters */
    static void m167(Context context, b.e.C0035b bVar) {
        AFLogger.afInfoLog(new StringBuilder("updateServerUninstallToken called with: ").append(bVar.toString()).toString());
        b.e.C0035b r0 = b.e.C0035b.m51(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
        if (!context.getSharedPreferences("appsflyer-data", 0).getBoolean("sentRegisterRequestToAF", false) || r0.m52() == null || !r0.m52().equals(bVar.m52())) {
            AppsFlyerProperties.getInstance().set("afUninstallToken", bVar.toString());
            AppsFlyerLib.getInstance().m227(context, bVar.m52());
        }
    }

    static class b extends AsyncTask<Void, Void, String> {

        /* renamed from: ˏ  reason: contains not printable characters */
        private final WeakReference<Context> f218;

        /* renamed from: ॱ  reason: contains not printable characters */
        private String f219;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return m168();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                String string = AppsFlyerProperties.getInstance().getString("afUninstallToken");
                b.e.C0035b bVar = new b.e.C0035b(str);
                if (string == null) {
                    y.m167(this.f218.get(), bVar);
                    return;
                }
                b.e.C0035b r2 = b.e.C0035b.m51(string);
                if (r2.m53(bVar)) {
                    y.m167(this.f218.get(), r2);
                }
            }
        }

        b(WeakReference<Context> weakReference) {
            this.f218 = weakReference;
        }

        /* access modifiers changed from: protected */
        public final void onPreExecute() {
            super.onPreExecute();
            this.f219 = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        private String m168() {
            try {
                if (this.f219 != null) {
                    return y.m162(this.f218, this.f219);
                }
                return null;
            } catch (Throwable th) {
                AFLogger.afErrorLog("Error registering for uninstall feature", th);
                return null;
            }
        }
    }
}
