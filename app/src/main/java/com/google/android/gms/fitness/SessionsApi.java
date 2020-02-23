package com.google.android.gms.fitness;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;

public interface SessionsApi {

    public static class ViewIntentBuilder {
        private final Context mContext;
        private Session zzaTe;
        private String zzaTf;
        private boolean zzaTg = false;

        public ViewIntentBuilder(Context context) {
            this.mContext = context;
        }

        public Intent build() {
            Intent intent;
            ResolveInfo resolveActivity;
            zzbo.zza(this.zzaTe != null, (Object) "Session must be set");
            Intent intent2 = new Intent(Fitness.ACTION_VIEW);
            intent2.setType(Session.getMimeType(this.zzaTe.getActivity()));
            zze.zza(this.zzaTe, intent2, Session.EXTRA_SESSION);
            if (!this.zzaTg) {
                this.zzaTf = this.zzaTe.getAppPackageName();
            }
            if (this.zzaTf == null || (resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 0)) == null) {
                return intent2;
            }
            (intent = new Intent(intent2).setPackage(this.zzaTf)).setComponent(new ComponentName(this.zzaTf, resolveActivity.activityInfo.name));
            return intent;
        }

        public ViewIntentBuilder setPreferredApplication(String str) {
            this.zzaTf = str;
            this.zzaTg = true;
            return this;
        }

        public ViewIntentBuilder setSession(Session session) {
            this.zzaTe = session;
            return this;
        }
    }

    PendingResult<Status> insertSession(GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<SessionReadResult> readSession(GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest);

    PendingResult<Status> registerForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent);

    PendingResult<Status> startSession(GoogleApiClient googleApiClient, Session session);

    PendingResult<SessionStopResult> stopSession(GoogleApiClient googleApiClient, String str);

    PendingResult<Status> unregisterForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent);
}
