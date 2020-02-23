package com.tencent.midas.oversea.comm;

import android.content.DialogInterface;
import android.view.KeyEvent;
import com.tencent.midas.oversea.comm.APUICommonMethod;

final class j implements DialogInterface.OnKeyListener {
    final /* synthetic */ APUICommonMethod.OnError a;

    j(APUICommonMethod.OnError onError) {
        this.a = onError;
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        dialogInterface.dismiss();
        if (this.a == null) {
            return false;
        }
        this.a.onError();
        return false;
    }
}
