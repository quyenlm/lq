package com.tencent.liteav;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXSDKUtil */
public class g {
    public static e a(Context context, int i) {
        if (i != 2 && i != 4 && i != 4 && i != 6 && i != 3) {
            return new b(context);
        }
        TXCLog.e("TXSDKUtil", "create player error not support type : " + i);
        return null;
    }

    public static String a() {
        return "";
    }
}
