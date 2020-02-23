package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import com.tencent.tbs.video.interfaces.a;

public class TbsVideo {
    public static boolean canUseTbsPlayer(Context context) {
        return bd.a(context).a();
    }

    public static boolean canUseYunbo(Context context) {
        return bd.a(context).a() && QbSdk.canUseVideoFeatrue(context, 1);
    }

    public static void openVideo(Context context, String str) {
        openVideo(context, str, (Bundle) null);
    }

    public static void openVideo(Context context, String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            Log.e("TbsVideo", "videoUrl is empty!");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("videoUrl", str);
        Intent intent = new Intent("com.tencent.smtt.tbs.video.PLAY");
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        intent.setPackage(context.getPackageName());
        intent.putExtra("extraData", bundle);
        context.startActivity(intent);
    }

    public static boolean openYunboVideo(Context context, String str, Bundle bundle, a aVar) {
        if (canUseYunbo(context)) {
            return bd.a(context).a(str, bundle, aVar);
        }
        return false;
    }
}
