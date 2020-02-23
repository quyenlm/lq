package com.tencent.tp.a;

import android.content.DialogInterface;
import android.widget.Button;

class ah implements DialogInterface.OnShowListener {
    final /* synthetic */ af a;

    ah(af afVar) {
        this.a = afVar;
    }

    public void onShow(DialogInterface dialogInterface) {
        Button button = this.a.a.getButton(-3);
        if (button != null) {
            button.setTextSize(2, 18.0f);
        }
    }
}
