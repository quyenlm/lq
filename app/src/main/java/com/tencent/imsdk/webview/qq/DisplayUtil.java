package com.tencent.imsdk.webview.qq;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayUtil {
    public static int dp2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    static String filterUrlParaKey(String url, List<String> keyArray) {
        IMLogger.d("filterUrlParaKey -|- origin url = " + url + ", keyArray = " + keyArray);
        if (T.ckIsEmpty(url) || !url.contains("?") || keyArray.size() == 0) {
            IMLogger.d("filterUrlParaKey -|- do not need process!");
            return url;
        }
        String paraUrl = url.substring(url.indexOf("?") + 1);
        String targetUrl = url.substring(0, url.indexOf("?"));
        String[] paraArray = paraUrl.split(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        List<String> paraList = new ArrayList<>();
        for (String add : paraArray) {
            paraList.add(add);
        }
        IMLogger.d("filterUrlParaKey -|- paraList start = " + paraList);
        Iterator<String> it = paraList.iterator();
        while (it.hasNext()) {
            String paraStr = it.next();
            if (paraStr.contains(HttpRequest.HTTP_REQ_ENTITY_MERGE) && keyArray.contains(paraStr.substring(0, paraStr.indexOf(HttpRequest.HTTP_REQ_ENTITY_MERGE)))) {
                it.remove();
            }
        }
        IMLogger.d("filterUrlParaKey -|- paraList end = " + paraList);
        StringBuilder targetUrlSB = new StringBuilder();
        if (paraList.size() > 0) {
            String targetUrl2 = targetUrl + "?";
            for (int i = 0; i < paraList.size(); i++) {
                targetUrlSB.append(paraList.get(i));
                targetUrlSB.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            String targetUrl3 = targetUrl2 + targetUrlSB.toString();
            targetUrl = targetUrl3.substring(0, targetUrl3.lastIndexOf(HttpRequest.HTTP_REQ_ENTITY_JOIN));
        }
        IMLogger.d("filterUrlParaKey -|- target url = " + targetUrl);
        return targetUrl;
    }
}
