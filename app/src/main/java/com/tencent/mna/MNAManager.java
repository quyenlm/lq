package com.tencent.mna;

import android.app.Activity;
import android.content.Context;
import com.tencent.mna.base.utils.o;

public class MNAManager {
    public static int Init(Context context, String str, boolean z, int i, boolean z2, boolean z3) {
        MNAPlatform.MNAInit(context, str, z, i, z2, false, "");
        if (z3) {
            return o.a(context, true);
        }
        return 0;
    }

    public static void SetObserver(GHObserver gHObserver) {
        MNAPlatform.MNASetGHObserver(gHObserver);
    }

    public static void SetUserName(int i, String str) {
        MNAPlatform.MNASetUserName(i, str);
    }

    public static void QueryKartin(String str) {
        MNAPlatform.MNAQueryKartin(str);
    }

    public static int StartWifiActivity(Activity activity) {
        return o.a(activity);
    }
}
