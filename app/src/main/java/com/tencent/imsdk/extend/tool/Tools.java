package com.tencent.imsdk.extend.tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.facebook.share.internal.ShareConstants;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.HashMap;
import java.util.List;

public class Tools extends DeviceInfoUtils {
    public static void initSchemeUrl(Activity context, List<String> schmemeDate, IMSchemeListener listener) {
        IMLogger.d("java initSchemeUrl start");
        try {
            HashMap<String, String> backMap = new HashMap<>();
            if (context != null) {
                IMLogger.d("java initSchemeUrl context is not null");
                Intent i_getvalue = context.getIntent();
                IMLogger.d("java initSchemeUrl i_getvalue");
                String action = i_getvalue.getAction();
                IMLogger.d("java initSchemeUrl action:" + action);
                if ("android.intent.action.VIEW".equals(action)) {
                    IMLogger.d("java initSchemeUrl Intent.ACTION_VIEW.equals(action)");
                    Uri uri = i_getvalue.getData();
                    if (uri == null) {
                        IMLogger.d("uri from Intent is null");
                        return;
                    }
                    IMLogger.d("uri:" + uri.toString());
                    if (!schmemeDate.isEmpty()) {
                        for (String parameter : schmemeDate) {
                            IMLogger.d("parameter:" + parameter + " parameterDate:" + uri.getQueryParameter(parameter));
                            backMap.put(parameter, uri.getQueryParameter(parameter));
                        }
                    }
                    backMap.put("scheme", i_getvalue.getScheme());
                    backMap.put(ShareConstants.MEDIA_URI, uri.toString());
                    if (listener != null) {
                        listener.OnSchemeDataBackCallback(backMap);
                    } else {
                        IMLogger.w("schemeListener not init");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            IMLogger.e(e.getMessage());
        }
    }
}
