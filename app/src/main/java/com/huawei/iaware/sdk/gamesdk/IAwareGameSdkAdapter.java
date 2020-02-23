package com.huawei.iaware.sdk.gamesdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Process;
import android.rms.iaware.IAwareSdkCore;
import android.util.Log;
import com.huawei.iaware.sdk.gamesdk.IAwareGameSdk;

public class IAwareGameSdkAdapter {
    private static final int GAME_SDK_DATA_EVENT_ID = 3005;
    private static int INTERFACE_ID_REGISTER_GAME_CALLBACK = 4;
    private static int INTERFACE_ID_REPORT_DATA = 1;
    private static final int mDataFromSDK = 1;
    /* access modifiers changed from: private */
    public boolean isRegistedSuccess = false;
    /* access modifiers changed from: private */
    public IAwareGameSdk.GameCallBack mGameCbk = null;
    private String mPackageName = "";
    private SDKCallback mSdkCbk = null;
    private int myPid = 0;

    private class SDKCallback extends Binder implements IInterface {
        private static final String SDK_CALLBACK_DESCRIPTOR = "com.huawei.iaware.sdk.ISDKCallbak";
        private static final int TRANSACTION_updatePhoneInfo = 1;

        public SDKCallback() {
            attachInterface(this, SDK_CALLBACK_DESCRIPTOR);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i < 1 || i > 16777215) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            switch (i) {
                case 1:
                    try {
                        parcel.enforceInterface(SDK_CALLBACK_DESCRIPTOR);
                        String readString = parcel.readString();
                        Log.i("IAwareGameSdkAdapter", "info: " + readString + " isRegistedSuccess: " + IAwareGameSdkAdapter.this.isRegistedSuccess);
                        if (IAwareGameSdkAdapter.this.mGameCbk == null || !IAwareGameSdkAdapter.this.isRegistedSuccess) {
                            return true;
                        }
                        Log.i("IAwareGameSdkAdapter", "CBK");
                        IAwareGameSdkAdapter.this.mGameCbk.getPhoneInfo(readString);
                        return true;
                    } catch (SecurityException e) {
                        return false;
                    }
                default:
                    return false;
            }
        }
    }

    private boolean registerSdkCallback(String str, SDKCallback sDKCallback) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeString(str);
        obtain.writeStrongBinder(sDKCallback);
        IAwareSdkCore.handleEvent(INTERFACE_ID_REGISTER_GAME_CALLBACK, obtain, obtain2);
        int readInt = obtain2.readInt();
        obtain2.recycle();
        obtain.recycle();
        return readInt > 0;
    }

    public boolean registerGameCallback(String str, IAwareGameSdk.GameCallBack gameCallBack) {
        Log.e("IAwareGameSdkAdapter", "registerGameCallback");
        this.mGameCbk = gameCallBack;
        this.mPackageName = str;
        this.myPid = Process.myPid();
        if (this.mGameCbk != null && this.mSdkCbk == null) {
            this.mSdkCbk = new SDKCallback();
            Log.i("IAwareGameSdkAdapter", "new SDKCallback");
            this.isRegistedSuccess = registerSdkCallback(str, this.mSdkCbk);
        }
        return this.isRegistedSuccess;
    }

    public void reportData(String str) {
        Log.i("IAwareGameSdkAdapter", "reportData package:" + this.mPackageName + " isRegistedSuccess: " + this.isRegistedSuccess);
        if (this.isRegistedSuccess) {
            Parcel obtain = Parcel.obtain();
            obtain.writeInt(3005);
            obtain.writeLong(0);
            obtain.writeString(String.valueOf(1) + "&&" + this.mPackageName + "&&" + this.myPid + "&&" + str);
            IAwareSdkCore.handleEvent(INTERFACE_ID_REPORT_DATA, obtain, (Parcel) null, 3005);
            obtain.recycle();
        }
    }
}
