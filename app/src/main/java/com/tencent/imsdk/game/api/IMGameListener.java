package com.tencent.imsdk.game.api;

import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import java.util.ArrayList;

public abstract class IMGameListener {
    public void onSuccess(ArrayList<IMResult> arrayList) {
    }

    public void onError(IMException ex) {
    }
}
