package com.tencent.mtt.spcialcall.sdk;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.mtt.des.DesUtils;
import com.vk.sdk.api.model.VKApiPhotoSize;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RecommendParams {
    public static final String BROWSER_VER = "qb_ver";
    public static final int FROM_TYPE_AIO = 2;
    public static final int FROM_TYPE_DYM = 4;
    public static final int FROM_TYPE_PUB_ACCOUNT = 1;
    public static final int FROM_TYPE_QZONE = 3;
    public static final String KEY_FROM = "from";
    public static final String KEY_PUB_UIN = "puin";
    public static final String KEY_REFFER = "ref";
    public static final String KEY_TITLE = "title";
    public static final String KEY_UIN = "uin";
    public static final String KEY_URL = "url";
    private static char[] base64EncodeChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', VKApiPhotoSize.M, 'n', VKApiPhotoSize.O, VKApiPhotoSize.P, VKApiPhotoSize.Q, 'r', VKApiPhotoSize.S, 't', 'u', 'v', VKApiPhotoSize.W, VKApiPhotoSize.X, VKApiPhotoSize.Y, VKApiPhotoSize.Z, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private Bundle mBundle = new Bundle();

    public RecommendParams put(String key, String value) {
        this.mBundle.putCharSequence(key, value);
        return this;
    }

    public String buildUpon(String url) {
        StringBuilder sb = new StringBuilder();
        for (String key : this.mBundle.keySet()) {
            String value = this.mBundle.getString(key);
            if (!TextUtils.isEmpty(value)) {
                if (sb.length() != 0) {
                    sb.append('&');
                }
                sb.append(key).append('=').append(URLEncoder.encode(value));
            }
        }
        String p = encode(sb.toString());
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("p", p);
        return builder.toString();
    }

    private String encode(String value) {
        try {
            return base64Encode(DesUtils.DesEncrypt("24Xdf8j6".getBytes("utf-8"), value.getBytes("utf-8"), 1));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        while (true) {
            if (i >= len) {
                int i2 = i;
                break;
            }
            int i3 = i + 1;
            int b1 = data[i] & 255;
            if (i3 == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 3) << 4]);
                sb.append("==");
                break;
            }
            int i4 = i3 + 1;
            int b2 = data[i3] & 255;
            if (i4 == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 3) << 4) | ((b2 & 240) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 15) << 2]);
                sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                int i5 = i4;
                break;
            }
            int b3 = data[i4] & 255;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 3) << 4) | ((b2 & 240) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 15) << 2) | ((b3 & DownloaderService.STATUS_RUNNING) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 63]);
            i = i4 + 1;
        }
        return sb.toString();
    }
}
