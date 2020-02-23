package com.samsung.android.game.tencentsdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.game.tencentsdk.ISceneSdkListener;

public interface ISceneSdkService extends IInterface {

    public static abstract class Stub extends Binder implements ISceneSdkService {
        private static final String DESCRIPTOR = "com.samsung.android.game.tencentsdk.ISceneSdkService";
        static final int TRANSACTION_applyHardwareResource = 4;
        static final int TRANSACTION_applyThreadGuarantee = 5;
        static final int TRANSACTION_getVersion = 6;
        static final int TRANSACTION_initSceneSdk = 1;
        static final int TRANSACTION_setSceneSdkListener = 2;
        static final int TRANSACTION_updateGameInfo = 3;

        private static class Proxy implements ISceneSdkService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public int applyHardwareResource(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int applyThreadGuarantee(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public float getVersion() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean initSceneSdk() {
                boolean z = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setSceneSdkListener(ISceneSdkListener iSceneSdkListener) {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iSceneSdkListener != null ? iSceneSdkListener.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int updateGameInfo(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISceneSdkService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISceneSdkService)) ? new Proxy(iBinder) : (ISceneSdkService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            int i3 = 0;
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean initSceneSdk = initSceneSdk();
                    parcel2.writeNoException();
                    if (initSceneSdk) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean sceneSdkListener = setSceneSdkListener(ISceneSdkListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (sceneSdkListener) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int updateGameInfo = updateGameInfo(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(updateGameInfo);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    int applyHardwareResource = applyHardwareResource(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(applyHardwareResource);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    int applyThreadGuarantee = applyThreadGuarantee(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(applyThreadGuarantee);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    float version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeFloat(version);
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    int applyHardwareResource(String str);

    int applyThreadGuarantee(String str);

    float getVersion();

    boolean initSceneSdk();

    boolean setSceneSdkListener(ISceneSdkListener iSceneSdkListener);

    int updateGameInfo(String str);
}
