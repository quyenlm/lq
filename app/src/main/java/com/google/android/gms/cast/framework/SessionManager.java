package com.google.android.gms.cast.framework;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzayo;

public class SessionManager {
    private static final zzayo zzarK = new zzayo("SessionManager");
    private final zzw zzasw;

    public SessionManager(zzw zzw) {
        this.zzasw = zzw;
    }

    /* access modifiers changed from: package-private */
    public final void addCastStateListener(CastStateListener castStateListener) throws NullPointerException {
        zzbo.zzu(castStateListener);
        try {
            this.zzasw.zza((zzo) new zzd(castStateListener));
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "addCastStateListener", zzw.class.getSimpleName());
        }
    }

    public void addSessionManagerListener(SessionManagerListener<Session> sessionManagerListener) throws NullPointerException {
        zzbo.zzcz("Must be called from the main thread.");
        addSessionManagerListener(sessionManagerListener, Session.class);
    }

    public <T extends Session> void addSessionManagerListener(SessionManagerListener<T> sessionManagerListener, Class<T> cls) throws NullPointerException {
        zzbo.zzu(sessionManagerListener);
        zzbo.zzu(cls);
        zzbo.zzcz("Must be called from the main thread.");
        try {
            this.zzasw.zza((zzy) new zzaf(sessionManagerListener, cls));
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "addSessionManagerListener", zzw.class.getSimpleName());
        }
    }

    public void endCurrentSession(boolean z) {
        zzbo.zzcz("Must be called from the main thread.");
        try {
            this.zzasw.zzb(true, z);
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "endCurrentSession", zzw.class.getSimpleName());
        }
    }

    /* access modifiers changed from: package-private */
    public final int getCastState() {
        try {
            return this.zzasw.getCastState();
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "addCastStateListener", zzw.class.getSimpleName());
            return 1;
        }
    }

    public CastSession getCurrentCastSession() {
        zzbo.zzcz("Must be called from the main thread.");
        Session currentSession = getCurrentSession();
        if (currentSession == null || !(currentSession instanceof CastSession)) {
            return null;
        }
        return (CastSession) currentSession;
    }

    public Session getCurrentSession() {
        zzbo.zzcz("Must be called from the main thread.");
        try {
            return (Session) zzn.zzE(this.zzasw.zzny());
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "getWrappedCurrentSession", zzw.class.getSimpleName());
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void removeCastStateListener(CastStateListener castStateListener) {
        if (castStateListener != null) {
            try {
                this.zzasw.zzb((zzo) new zzd(castStateListener));
            } catch (RemoteException e) {
                zzarK.zzb(e, "Unable to call %s on %s.", "removeCastStateListener", zzw.class.getSimpleName());
            }
        }
    }

    public void removeSessionManagerListener(SessionManagerListener<Session> sessionManagerListener) {
        zzbo.zzcz("Must be called from the main thread.");
        removeSessionManagerListener(sessionManagerListener, Session.class);
    }

    public <T extends Session> void removeSessionManagerListener(SessionManagerListener<T> sessionManagerListener, Class cls) {
        zzbo.zzu(cls);
        zzbo.zzcz("Must be called from the main thread.");
        if (sessionManagerListener != null) {
            try {
                this.zzasw.zzb((zzy) new zzaf(sessionManagerListener, cls));
            } catch (RemoteException e) {
                zzarK.zzb(e, "Unable to call %s on %s.", "removeSessionManagerListener", zzw.class.getSimpleName());
            }
        }
    }

    public final IObjectWrapper zznp() {
        try {
            return this.zzasw.zznu();
        } catch (RemoteException e) {
            zzarK.zzb(e, "Unable to call %s on %s.", "getWrappedThis", zzw.class.getSimpleName());
            return null;
        }
    }
}
