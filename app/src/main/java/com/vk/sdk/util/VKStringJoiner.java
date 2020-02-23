package com.vk.sdk.util;

import android.net.Uri;
import android.text.TextUtils;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.vk.sdk.api.model.VKAttachments;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class VKStringJoiner {
    public static String join(String[] what, String glue) {
        return join((Collection<?>) Arrays.asList(what), glue);
    }

    public static String join(Collection<?> what, String glue) {
        return TextUtils.join(glue, what);
    }

    public static String joinParams(Map<String, ?> queryParams, boolean isUri) {
        ArrayList<String> params = new ArrayList<>(queryParams.size());
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof VKAttachments) {
                value = ((VKAttachments) value).toAttachmentsString();
            }
            Object[] objArr = new Object[2];
            objArr[0] = entry.getKey();
            objArr[1] = isUri ? Uri.encode(String.valueOf(value)) : String.valueOf(value);
            params.add(String.format("%s=%s", objArr));
        }
        return join((Collection<?>) params, HttpRequest.HTTP_REQ_ENTITY_JOIN);
    }

    public static String joinParams(Map<String, ?> queryParams) {
        return joinParams(queryParams, false);
    }

    public static String joinUriParams(Map<String, ?> queryParams) {
        return joinParams(queryParams, true);
    }
}
