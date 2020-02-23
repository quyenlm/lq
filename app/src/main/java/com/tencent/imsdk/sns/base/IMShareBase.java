package com.tencent.imsdk.sns.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class IMShareBase extends IMSNSBase {

    public static class ShareType {
        public static final int IMAGE = 4;
        public static final int IMAGE_DIALOG = 5;
        public static final int LINK = 2;
        public static final int LINK_DIALOG = 3;
        public static final int TEXT = 0;
        public static final int TEXT_DIALOG = 1;
    }

    public abstract void shareImage(Bitmap bitmap, String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareImage(Uri uri, String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareImageDialog(Bitmap bitmap, String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareImageDialog(Uri uri, String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareLink(String str, String str2, String str3, String str4, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareLinkDialog(String str, String str2, String str3, String str4, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareText(String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public abstract void shareTextDialog(String str, String str2, JSONObject jSONObject, IMCallback<IMResult> iMCallback);

    public boolean initialize(Context context) {
        return super.setContext(context);
    }

    public void share(IMShareContent content, IMCallback<IMResult> callback) {
        IMLogger.d("share type : " + content.type);
        switch (content.type) {
            case 0:
                shareText(content, callback);
                return;
            case 1:
                shareTextDialog(content, callback);
                return;
            case 2:
                shareLink(content, callback);
                return;
            case 3:
                shareLinkDialog(content, callback);
                return;
            case 4:
                shareImage(content, callback);
                return;
            case 5:
                shareImageDialog(content, callback);
                return;
            default:
                callback.onError(new IMException(7));
                return;
        }
    }

    public void shareText(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        shareText(content.title, content.content, jsonObject, callback);
    }

    public void shareTextDialog(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        shareTextDialog(content.title, content.content, jsonObject, callback);
    }

    public void shareLink(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        shareLink(content.link, content.title, content.content, content.imagePath, jsonObject, callback);
    }

    public void shareLinkDialog(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        shareLinkDialog(content.link, content.title, content.content, content.imagePath, jsonObject, callback);
    }

    public void shareImage(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        if (IMBitmapTool.isHttpUrl(content.imagePath)) {
            Uri uri = Uri.parse(content.imagePath);
            if (uri != null) {
                shareImage(uri, content.title, content.content, jsonObject, callback);
                return;
            }
            return;
        }
        Bitmap bitmap = IMBitmapTool.createFromPath(this.currentContext, content.imagePath);
        if (bitmap != null) {
            shareImage(bitmap, content.title, content.content, jsonObject, callback);
            return;
        }
        callback.onError(new IMException(8, "unknown image path : " + content.imagePath));
    }

    public void shareImageDialog(IMShareContent content, IMCallback<IMResult> callback) {
        JSONObject jsonObject = null;
        try {
            if (content.extraJson != null && content.extraJson.length() > 0) {
                jsonObject = new JSONObject(content.extraJson);
            }
        } catch (JSONException e) {
            IMLogger.e("extra json data parse error : " + e.getMessage() + ", " + content.extraJson);
        }
        if (IMBitmapTool.isHttpUrl(content.imagePath)) {
            Uri uri = Uri.parse(content.imagePath);
            if (uri != null) {
                shareImageDialog(uri, content.title, content.content, jsonObject, callback);
                return;
            }
            return;
        }
        Bitmap bitmap = IMBitmapTool.createFromPath(this.currentContext, content.imagePath);
        if (bitmap != null) {
            shareImageDialog(bitmap, content.title, content.content, jsonObject, callback);
            return;
        }
        callback.onError(new IMException(8, "unknown image path : " + content.imagePath));
    }

    public void shareImage(String imgPath, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.d("in share image file : " + imgPath);
        if (new File(imgPath).exists()) {
            IMLogger.d("file exists !");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            shareImage(BitmapFactory.decodeFile(imgPath, options), title, description, extras, callback);
            return;
        }
        IMLogger.e("file " + imgPath + " not exists !");
        callback.onError(new IMException(8, "file not exists"));
    }

    public void shareImageDialog(String imgPath, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.d("in share image file dialog: " + imgPath);
        if (new File(imgPath).exists()) {
            IMLogger.d("file exists !");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            shareImageDialog(BitmapFactory.decodeFile(imgPath, options), title, description, extras, callback);
            return;
        }
        IMLogger.e("file " + imgPath + " not exists !");
        callback.onError(new IMException(8, "file not exists"));
    }
}
