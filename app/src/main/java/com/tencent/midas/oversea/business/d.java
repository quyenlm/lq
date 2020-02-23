package com.tencent.midas.oversea.business;

import android.content.DialogInterface;

class d implements DialogInterface.OnClickListener {
    final /* synthetic */ APPayMananger a;

    d(APPayMananger aPPayMananger) {
        this.a = aPPayMananger;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.a(3, "can not find the channel jar file or maybe anything wrong with the channel name!!");
    }
}
