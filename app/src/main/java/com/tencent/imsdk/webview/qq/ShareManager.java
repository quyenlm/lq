package com.tencent.imsdk.webview.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.sns.api.IMShare;
import com.tencent.imsdk.sns.api.IMShareListener;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareManager {
    private static final String FORMAT_SHARE_NAME = "share_%s";
    public static final int ID_SHARE_BROWSER = 99;
    private static final String NAME_SHARE_BROWSER = "Browser";
    private static final int TOAST_POSITION_X = 0;
    private static final int TOAST_POSITION_Y = 200;

    private static int getStringId(Context context, String paramString) {
        int defaultID = ResourceUtil.getStringId(context, "share_browser");
        int resID = ResourceUtil.getStringId(context, paramString);
        return resID != 0 ? resID : defaultID;
    }

    private static int getDrawableId(Context context, String paramString) {
        int defaultID = ResourceUtil.getDrawableId(context, "share_browser");
        int resID = ResourceUtil.getDrawableId(context, paramString);
        return resID != 0 ? resID : defaultID;
    }

    public static List<Map<String, Object>> getChannelListData(Context context) {
        Resources resources = context.getResources();
        Map<Integer, String> channelMap = new LinkedHashMap<>();
        int resID = ResourceUtil.getArrayId(context, "ShareChannel");
        if (resID != 0) {
            String[] channelArray = resources.getStringArray(resID);
            for (int i = 0; i < channelArray.length; i++) {
                channelMap.put(Integer.valueOf(i), channelArray[i]);
            }
        } else {
            IMLogger.e("ShareChannel not set!");
        }
        channelMap.put(99, NAME_SHARE_BROWSER);
        List<Map<String, Object>> shareDataList = new ArrayList<>();
        for (Integer key : channelMap.keySet()) {
            try {
                String resourceName = String.format(FORMAT_SHARE_NAME, new Object[]{channelMap.get(key).toLowerCase(Locale.US)});
                Map<String, Object> itemMap = new HashMap<>();
                int icon = getDrawableId(context, resourceName);
                if (icon <= 0) {
                    IMLogger.w("no " + resourceName + " found in resource files");
                } else {
                    itemMap.put("image", Integer.valueOf(icon));
                    int stringID = getStringId(context, resourceName);
                    String text = stringID != 0 ? resources.getString(stringID) : "";
                    if (T.ckIsEmpty(text)) {
                        IMLogger.w("no  " + resourceName + " found in resource files!");
                    } else {
                        itemMap.put("channel", text);
                        itemMap.put("id", key);
                        shareDataList.add(itemMap);
                    }
                }
            } catch (Exception e) {
                IMLogger.e("add share channel error : " + e.getMessage());
            }
        }
        return shareDataList;
    }

    private static void initShareChannel(final Activity activity, String channel) {
        IMShare.initialize(activity);
        if (!IMShare.setChannel(channel)) {
            IMLogger.e("set channel failed !");
        }
        IMShare.setListener(new IMShareListener() {
            public void onShare(IMResult result) {
                if (result != null) {
                    try {
                        IMLogger.d(result.toJSONString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ShareManager.showToast(activity.getApplicationContext(), result.imsdkRetCode);
                    return;
                }
                IMLogger.e("shareToFacebook onResult error, result = null");
            }
        });
    }

    public static void shareToBrowser(Context context, String url) {
        List<String> paraList = new ArrayList<>();
        int resID = ResourceUtil.getArrayId(context, "urlKeyFilter");
        if (resID != 0) {
            for (String trim : context.getResources().getStringArray(resID)) {
                paraList.add(trim.trim());
            }
        } else {
            IMLogger.e("urlKeyFilter not set!");
        }
        paraList.add(HttpRequestParams.WEBVIEW_TICKET);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(DisplayUtil.filterUrlParaKey(url, paraList)));
        context.startActivity(intent);
    }

    public static void shareToChannel(Activity activity, String channel, String title, String url) {
        initShareChannel(activity, channel);
        List<String> paraList = new ArrayList<>();
        int resID = ResourceUtil.getArrayId(activity, "urlKeyFilter");
        if (resID != 0) {
            for (String trim : activity.getResources().getStringArray(resID)) {
                paraList.add(trim.trim());
            }
        } else {
            IMLogger.e("urlKeyFilter not set!");
        }
        paraList.add(HttpRequestParams.WEBVIEW_TICKET);
        IMShare.shareLinkDialog(DisplayUtil.filterUrlParaKey(url, paraList), title, "", (String) null, (JSONObject) null);
    }

    public static void showToast(Context context, int resultCode) {
        View view;
        int resID;
        TextView title;
        int resID2 = ResourceUtil.getLayoutId(context, "layout_toast");
        if (resID2 != 0 && (view = LayoutInflater.from(context).inflate(resID2, (ViewGroup) null)) != null && (resID = ResourceUtil.getId(context, "toast_title")) != 0 && (title = (TextView) view.findViewById(resID)) != null) {
            Typeface tf = ResourceUtil.getTypeface(context, "share_result");
            if (tf != null) {
                title.setTypeface(tf);
            }
            if (resultCode == 1) {
                int resID3 = ResourceUtil.getStringId(context, "share_success");
                title.setText(resID3 != 0 ? context.getString(resID3) : "Shared");
            } else if (resultCode == 2) {
                int resID4 = ResourceUtil.getStringId(context, "share_cancel");
                title.setText(resID4 != 0 ? context.getString(resID4) : "Sharing canceled");
            } else {
                int resID5 = ResourceUtil.getStringId(context, "share_failure");
                title.setText(resID5 != 0 ? context.getString(resID5) : "Sharing failed");
            }
            Toast toast = new Toast(context);
            toast.setGravity(81, 0, 200);
            toast.setDuration(0);
            toast.setView(view);
            toast.show();
        }
    }
}
