package com.tencent.midas.oversea.utils;

import android.app.Activity;
import android.view.View;
import com.tencent.midas.oversea.comm.APCommMethod;

public class APFindViewUtils {
    public static <T extends View> T findViewById(Activity activity, String str) {
        return activity.findViewById(APCommMethod.getId(activity, str));
    }

    public static <T extends View> T findViewById(View view, String str) {
        return view.findViewById(APCommMethod.getId(view.getContext(), str));
    }
}
