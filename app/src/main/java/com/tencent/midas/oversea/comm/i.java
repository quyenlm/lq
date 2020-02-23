package com.tencent.midas.oversea.comm;

import android.content.DialogInterface;
import com.tencent.midas.oversea.comm.APUICommonMethod;

final class i implements DialogInterface.OnClickListener {
    final /* synthetic */ APUICommonMethod.OnError a;

    i(APUICommonMethod.OnError onError) {
        this.a = onError;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        if (this.a != null) {
            this.a.onError();
        }
    }
}
