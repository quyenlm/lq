package com.facebook;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareGraphRequest {
    public static GraphRequest createOpenGraphObject(ShareOpenGraphObject openGraphObject) throws FacebookException {
        String type = openGraphObject.getString("type");
        if (type == null) {
            type = openGraphObject.getString("og:type");
        }
        if (type == null) {
            throw new FacebookException("Open graph object type cannot be null");
        }
        try {
            Bundle parameters = new Bundle();
            parameters.putString("object", ((JSONObject) OpenGraphJSONUtility.toJSONValue(openGraphObject, new OpenGraphJSONUtility.PhotoJSONProcessor() {
                public JSONObject toJSONObject(SharePhoto photo) {
                    Uri photoUri = photo.getImageUrl();
                    JSONObject photoJSONObject = new JSONObject();
                    try {
                        photoJSONObject.put("url", photoUri.toString());
                        return photoJSONObject;
                    } catch (Exception e) {
                        throw new FacebookException("Unable to attach images", (Throwable) e);
                    }
                }
            })).toString());
            return new GraphRequest(AccessToken.getCurrentAccessToken(), String.format(Locale.ROOT, "%s/%s", new Object[]{"me", "objects/" + type}), parameters, HttpMethod.POST);
        } catch (JSONException e) {
            throw new FacebookException(e.getMessage());
        }
    }
}
