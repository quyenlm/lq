package com.beetalk.sdk.vk;

import android.content.Context;
import android.support.annotation.NonNull;
import com.btalk.helper.BBAppLogger;
import com.vk.sdk.VKAccessToken;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUtils {
    public static final String KEY_APP_ID = "com_vk_sdk_AppId";

    public static int lookUpAppId(Context context) {
        int id = context.getResources().getIdentifier("com_vk_sdk_AppId", "integer", context.getPackageName());
        if (id <= 0) {
            return -1;
        }
        return context.getResources().getInteger(id);
    }

    public static String tokenToJson(@NonNull VKAccessToken token) {
        JSONObject json = new JSONObject();
        try {
            json.put("access_token", token.accessToken);
            json.put("expires_in", token.expiresIn);
            json.put("user_id", token.userId);
            json.put(VKAccessToken.SECRET, token.secret);
            json.put(VKAccessToken.HTTPS_REQUIRED, token.httpsRequired);
            json.put(VKAccessToken.CREATED, token.created);
            json.put("email", token.email);
            return json.toString();
        } catch (JSONException e) {
            BBAppLogger.e(e);
            return null;
        }
    }
}
