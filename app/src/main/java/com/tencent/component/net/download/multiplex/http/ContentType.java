package com.tencent.component.net.download.multiplex.http;

import android.text.TextUtils;
import com.appsflyer.share.Constants;

public class ContentType {
    public static final String CHARSET_BINARY = "binary";
    public static final String CHARSET_UTF8 = "utf-8";
    public static final String SUBTYPE_CSS = "css";
    public static final String SUBTYPE_GIF = "gif";
    public static final String SUBTYPE_HTML = "html";
    public static final String SUBTYPE_JAVASCRIPT = "javascript";
    public static final String SUBTYPE_JPEG = "jpeg";
    public static final String SUBTYPE_OCTETSTREAM = "octet-stream";
    public static final String SUBTYPE_PLAIN = "plain";
    public static final String SUBTYPE_PNG = "png";
    public static final String TYPE_APPLICATION = "application";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_TEXT = "text";
    private String mEncoding;
    private String mType;
    private String mTypeValue;

    public ContentType() {
    }

    public ContentType(String contentType) {
        parseContentType(contentType);
    }

    private void parseContentType(String contentType) {
        String strSemiPre;
        int equalIdx;
        if (!TextUtils.isEmpty(contentType)) {
            String contentType2 = contentType.trim();
            int semiIdx = contentType2.indexOf(59);
            String strSemiAfter = null;
            if (semiIdx != -1) {
                strSemiPre = contentType2.substring(0, semiIdx);
                strSemiAfter = contentType2.substring(semiIdx + 1);
            } else {
                strSemiPre = contentType2;
            }
            if (strSemiPre != null) {
                int slashIdx = strSemiPre.indexOf(47);
                if (slashIdx != -1) {
                    this.mType = strSemiPre.substring(0, slashIdx);
                    this.mTypeValue = strSemiPre.substring(slashIdx + 1);
                } else {
                    this.mType = strSemiPre;
                }
            }
            if (strSemiAfter != null && (equalIdx = strSemiAfter.indexOf(61)) != -1) {
                this.mEncoding = strSemiAfter.substring(equalIdx + 1);
            }
        }
    }

    public ContentType(String type, String typeValue, String encoding) {
        this.mType = type;
        this.mTypeValue = typeValue;
        this.mEncoding = encoding;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getTypeValue() {
        return this.mTypeValue;
    }

    public void setTypeValue(String typeValue) {
        this.mTypeValue = typeValue;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public void setEncoding(String encoding) {
        this.mEncoding = encoding;
    }

    public String toString() {
        if (this.mType != null) {
            return this.mType.concat(Constants.URL_PATH_DELIMITER).concat(this.mTypeValue);
        }
        return null;
    }
}
