package com.beetalk.sdk.plugin.impl.line;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.line.LinePostItem;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.pay.android.GGErrorCode;
import java.io.File;

public class LineSharePlugin extends GGPlugin<LinePostItem, PluginResult> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.LINE_SHARE;
    }

    public Integer getRequestCode() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, LinePostItem data) {
        if (data == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.setPackage(SDKConstants.LINE_PACKAGE);
        if (data.type == LinePostItem.PostType.TEXT_LINK) {
            shareIntent.setType("text/plain");
            String content = "";
            if (!TextUtils.isEmpty(data.message)) {
                content = content + data.message;
            }
            if (!TextUtils.isEmpty(data.link)) {
                content = content + "\n" + data.link;
            }
            shareIntent.putExtra("android.intent.extra.TEXT", content);
        } else if (data.type == LinePostItem.PostType.IMAGE) {
            shareIntent.setType("image/*");
            shareIntent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(activity, "com.garena.android.fileprovider" + GGLoginSession.getCurrentSession().getApplicationId(), new File(data.imagePath)));
        }
        try {
            activity.startActivity(shareIntent);
            publishResult(activity, GGErrorCode.SUCCESS.getCode().intValue());
        } catch (ActivityNotFoundException e) {
            BBLogger.e(e);
            BBLogger.d("line not installed", new Object[0]);
            publishResult(activity, GGErrorCode.APP_NOT_INSTALLED.getCode().intValue());
        }
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}
