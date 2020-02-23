package com.tencent.midas.oversea.comm;

import android.content.DialogInterface;
import com.tencent.midas.oversea.network.http.APNetworkManager;

final class l implements DialogInterface.OnCancelListener {
    final /* synthetic */ DialogInterface.OnCancelListener a;

    l(DialogInterface.OnCancelListener onCancelListener) {
        this.a = onCancelListener;
    }

    public void onCancel(DialogInterface dialogInterface) {
        APLog.d("APUICommonMethod", "ProgressDialog onCancel");
        APNetworkManager.getInstance().cancelPreRequest();
        if (this.a != null) {
            this.a.onCancel(dialogInterface);
        }
    }
}
