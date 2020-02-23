package com.tencent.midas.oversea.comm;

import android.view.View;
import com.tencent.midas.oversea.comm.APAlertDialog;

class a implements View.OnClickListener {
    final /* synthetic */ APAlertDialog a;
    final /* synthetic */ APAlertDialog.Builder b;

    a(APAlertDialog.Builder builder, APAlertDialog aPAlertDialog) {
        this.b = builder;
        this.a = aPAlertDialog;
    }

    public void onClick(View view) {
        this.b.m.onClick(this.a, -1);
    }
}
