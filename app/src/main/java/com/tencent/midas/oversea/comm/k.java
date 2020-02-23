package com.tencent.midas.oversea.comm;

import android.app.Activity;
import android.content.DialogInterface;

final class k implements DialogInterface.OnClickListener {
    final /* synthetic */ Activity a;

    k(Activity activity) {
        this.a = activity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        APUICommonMethod.dissMessageDialog((APBaseActivity) this.a);
    }
}
