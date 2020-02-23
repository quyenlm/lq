package com.tencent.component;

import android.content.Context;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.ResourceUtil;

public class UtilitiesInitial {
    public static void init(Context context) {
        ComponentContext.setContext(context);
        FileUtil.init(context.getApplicationContext());
        ResourceUtil.setContext(context.getApplicationContext());
    }
}
