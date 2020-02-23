package com.google.android.gms.cast.framework;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public interface zzu extends IInterface {

    public static abstract class zza extends zzee implements zzu {
        public static zzu zzD(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.framework.ISession");
            return queryLocalInterface instanceof zzu ? (zzu) queryLocalInterface : new zzv(iBinder);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            switch (i) {
                case 1:
                    IObjectWrapper zznw = zznw();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, (IInterface) zznw);
                    return true;
                case 2:
                    String category = getCategory();
                    parcel2.writeNoException();
                    parcel2.writeString(category);
                    return true;
                case 3:
                    String sessionId = getSessionId();
                    parcel2.writeNoException();
                    parcel2.writeString(sessionId);
                    return true;
                case 4:
                    String zznx = zznx();
                    parcel2.writeNoException();
                    parcel2.writeString(zznx);
                    return true;
                case 5:
                    boolean isConnected = isConnected();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isConnected);
                    return true;
                case 6:
                    boolean isConnecting = isConnecting();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isConnecting);
                    return true;
                case 7:
                    boolean isDisconnecting = isDisconnecting();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isDisconnecting);
                    return true;
                case 8:
                    boolean isDisconnected = isDisconnected();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isDisconnected);
                    return true;
                case 9:
                    boolean isResuming = isResuming();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isResuming);
                    return true;
                case 10:
                    boolean isSuspended = isSuspended();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isSuspended);
                    return true;
                case 11:
                    notifySessionStarted(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    notifyFailedToStartSession(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    notifySessionEnded(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    notifySessionResumed(zzef.zza(parcel));
                    parcel2.writeNoException();
                    return true;
                case 15:
                    notifyFailedToResumeSession(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 16:
                    notifySessionSuspended(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                default:
                    return false;
            }
        }
    }

    String getCategory() throws RemoteException;

    String getSessionId() throws RemoteException;

    boolean isConnected() throws RemoteException;

    boolean isConnecting() throws RemoteException;

    boolean isDisconnected() throws RemoteException;

    boolean isDisconnecting() throws RemoteException;

    boolean isResuming() throws RemoteException;

    boolean isSuspended() throws RemoteException;

    void notifyFailedToResumeSession(int i) throws RemoteException;

    void notifyFailedToStartSession(int i) throws RemoteException;

    void notifySessionEnded(int i) throws RemoteException;

    void notifySessionResumed(boolean z) throws RemoteException;

    void notifySessionStarted(String str) throws RemoteException;

    void notifySessionSuspended(int i) throws RemoteException;

    IObjectWrapper zznw() throws RemoteException;

    String zznx() throws RemoteException;
}
