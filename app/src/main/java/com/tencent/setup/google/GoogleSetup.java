package com.tencent.setup.google;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.gms.common.GoogleApiAvailability;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMProxyRunner;
import com.tencent.imsdk.IMProxyTask;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.etc.IMLogger;

public class GoogleSetup {
    private static final int FIX_REQUEST_CODE = 65297;
    private Context currentContext;

    public boolean initialize(Context context) {
        this.currentContext = context;
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == 0) {
            IMLogger.d("gms is available");
            return true;
        }
        IMLogger.w("gms is Not available");
        return false;
    }

    public int check() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.currentContext);
    }

    public void autoFix(IMCallback<IMResult> callback) {
        fix(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.currentContext), callback);
    }

    public void fix(final int code, final IMCallback<IMResult> callback) {
        if (code == 0) {
            callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
        } else {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
                boolean callbacked = false;

                public void onPreProxy() {
                    IMLogger.d("try to fix google env : " + code);
                }

                public void onPostProxy(final Activity activity) {
                    GoogleApiAvailability.getInstance().showErrorDialogFragment(activity, code, GoogleSetup.FIX_REQUEST_CODE, new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialogInterface) {
                            if (callback != null && !AnonymousClass1.this.callbacked) {
                                callback.onError(IMRetCode.rebuild(new IMException(1004, "need gms : " + GoogleSetup.this.check()), 2, -1, (String) null, (String) null));
                                AnonymousClass1.this.callbacked = true;
                            }
                            activity.finish();
                        }
                    });
                }

                public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
                    new Thread(new Runnable() {
                        public void run() {
                            if (callback != null && !AnonymousClass1.this.callbacked) {
                                if (resultCode == -1) {
                                    callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
                                } else if (resultCode == 0) {
                                    callback.onError(IMRetCode.rebuild(new IMException(1004, "need gms : " + GoogleSetup.this.check()), 2, -1, (String) null, (String) null));
                                } else {
                                    callback.onError(IMRetCode.rebuild(new IMException(1004, "need gms : " + GoogleSetup.this.check()), 15, resultCode, "activity result : " + requestCode, (String) null));
                                }
                            }
                        }
                    }).start();
                }
            });
        }
    }
}
