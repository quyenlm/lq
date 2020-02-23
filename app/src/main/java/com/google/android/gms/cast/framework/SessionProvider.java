package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;

public abstract class SessionProvider {
    private final Context mApplicationContext;
    private final String mCategory;
    private final zza zzasz = new zza();

    class zza extends zzab {
        private zza() {
        }

        public final String getCategory() {
            return SessionProvider.this.getCategory();
        }

        public final boolean isSessionRecoverable() {
            return SessionProvider.this.isSessionRecoverable();
        }

        public final IObjectWrapper zzcd(String str) {
            Session createSession = SessionProvider.this.createSession(str);
            if (createSession == null) {
                return null;
            }
            return createSession.zznw();
        }
    }

    protected SessionProvider(Context context, String str) {
        this.mApplicationContext = ((Context) zzbo.zzu(context)).getApplicationContext();
        this.mCategory = zzbo.zzcF(str);
    }

    public abstract Session createSession(String str);

    public final String getCategory() {
        return this.mCategory;
    }

    public final Context getContext() {
        return this.mApplicationContext;
    }

    public abstract boolean isSessionRecoverable();

    public final IBinder zznH() {
        return this.zzasz;
    }
}
