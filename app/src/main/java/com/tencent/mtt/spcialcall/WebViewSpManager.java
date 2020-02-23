package com.tencent.mtt.spcialcall;

import com.tencent.mtt.engine.IWebView;
import java.util.HashMap;
import java.util.Map;

public class WebViewSpManager {
    private static Map<Long, IWebView> sWebViewMap = new HashMap();

    public static void addWebView(long id, IWebView view) {
        sWebViewMap.put(Long.valueOf(id), view);
    }

    public static IWebView getWebView(long id) {
        return sWebViewMap.get(Long.valueOf(id));
    }

    public static void removeWebView(long viewId) {
        sWebViewMap.remove(Long.valueOf(viewId));
    }
}
