package com.beetalk.sdk.update;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.update.GPGameProviderContract;

public class UpdateManager {
    public static final String EXTRA_APP_ID = "extra_app_id";
    public static final String EXTRA_VERSION = "extra_version";

    public static boolean isUpdateDownloadedInGas(Context context, int versionCode) {
        int appId = Helper.getIntegerAppId(context).intValue();
        if (appId == -1) {
            BBLogger.e("invalid app id: " + appId, new Object[0]);
            return false;
        }
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(GPGameProviderContract.AUTHORITY);
        builder.path(GPGameProviderContract.Path.UPDATE);
        Cursor cursor = context.getContentResolver().query(ContentUris.appendId(builder, (long) appId).build(), (String[]) null, (String) null, (String[]) null, (String) null);
        if (cursor == null) {
            return false;
        }
        boolean result = false;
        while (true) {
            if (!cursor.moveToNext()) {
                break;
            }
            int updateAppId = cursor.getInt(cursor.getColumnIndex("app_id"));
            int updateVersion = cursor.getInt(cursor.getColumnIndex(GPGameProviderContract.Column.VERSION_CODE));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            BBLogger.d("obtained update info: appId=" + updateAppId + " ver=" + versionCode + " status=" + status, new Object[0]);
            if (updateAppId == appId && updateVersion == versionCode && status == 2) {
                result = true;
                break;
            }
        }
        cursor.close();
        return result;
    }

    public static void installUpdateInGas(Context context, int versionCode) {
        int appId = Helper.getIntegerAppId(context).intValue();
        if (appId == -1) {
            BBLogger.e("invalid app id: " + appId, new Object[0]);
            return;
        }
        Intent intent = new Intent(SDKConstants.GAS_ACTION_INSTALL_GAME_UPDATE);
        intent.putExtra(EXTRA_APP_ID, Helper.getIntegerAppId(context));
        intent.putExtra(EXTRA_VERSION, versionCode);
        try {
            context.startActivity(intent);
            BBLogger.d("launching gas to install update", new Object[0]);
        } catch (ActivityNotFoundException e) {
            BBLogger.e("launching gas failed", new Object[0]);
        }
    }
}
