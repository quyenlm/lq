package com.tencent.component.plugin.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface PluginServerBroadcast extends IInterface {
    void onPendingInstallFinish(boolean z, boolean z2, String str, String str2) throws RemoteException;

    void onPlatformInitialFinish() throws RemoteException;

    void onPlatformInitialStart() throws RemoteException;

    void onPluginInstalled(String str, int i, int i2) throws RemoteException;

    void onPluginStateChange(String str, int i, int i2) throws RemoteException;

    void onPluginUninstalled(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements PluginServerBroadcast {
        private static final String DESCRIPTOR = "com.tencent.component.plugin.server.PluginServerBroadcast";
        static final int TRANSACTION_onPendingInstallFinish = 5;
        static final int TRANSACTION_onPlatformInitialFinish = 2;
        static final int TRANSACTION_onPlatformInitialStart = 1;
        static final int TRANSACTION_onPluginInstalled = 3;
        static final int TRANSACTION_onPluginStateChange = 6;
        static final int TRANSACTION_onPluginUninstalled = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static PluginServerBroadcast asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof PluginServerBroadcast)) {
                return new Proxy(obj);
            }
            return (PluginServerBroadcast) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0;
            boolean _arg1;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onPlatformInitialStart();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onPlatformInitialFinish();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onPluginInstalled(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onPluginUninstalled(data.readString());
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    } else {
                        _arg0 = false;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = true;
                    } else {
                        _arg1 = false;
                    }
                    onPendingInstallFinish(_arg0, _arg1, data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    onPluginStateChange(data.readString(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements PluginServerBroadcast {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void onPlatformInitialStart() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPlatformInitialFinish() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPluginInstalled(String pluginId, int oldVersion, int version) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pluginId);
                    _data.writeInt(oldVersion);
                    _data.writeInt(version);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPluginUninstalled(String pluginId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pluginId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) throws RemoteException {
                int i = 1;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(success ? 1 : 0);
                    if (!corePlugin) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    _data.writeString(extraInfo);
                    _data.writeString(errorMsg);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onPluginStateChange(String pluginId, int changeFlags, int statusFlags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pluginId);
                    _data.writeInt(changeFlags);
                    _data.writeInt(statusFlags);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
