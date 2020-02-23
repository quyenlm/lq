package com.facebook.messenger;

import android.net.Uri;
import com.vk.sdk.api.VKApiConst;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpHost;

public class ShareToMessengerParams {
    public static final Set<String> VALID_EXTERNAL_URI_SCHEMES;
    public static final Set<String> VALID_MIME_TYPES;
    public static final Set<String> VALID_URI_SCHEMES;
    public final Uri externalUri;
    public final String metaData;
    public final String mimeType;
    public final Uri uri;

    static {
        Set<String> validMimeTypes = new HashSet<>();
        validMimeTypes.add("image/*");
        validMimeTypes.add("image/jpeg");
        validMimeTypes.add("image/png");
        validMimeTypes.add("image/gif");
        validMimeTypes.add("image/webp");
        validMimeTypes.add("video/*");
        validMimeTypes.add("video/mp4");
        validMimeTypes.add("audio/*");
        validMimeTypes.add("audio/mpeg");
        VALID_MIME_TYPES = Collections.unmodifiableSet(validMimeTypes);
        Set<String> validUriSchemes = new HashSet<>();
        validUriSchemes.add("content");
        validUriSchemes.add("android.resource");
        validUriSchemes.add(TransferTable.COLUMN_FILE);
        VALID_URI_SCHEMES = Collections.unmodifiableSet(validUriSchemes);
        Set<String> validExternalUriSchemes = new HashSet<>();
        validExternalUriSchemes.add(HttpHost.DEFAULT_SCHEME_NAME);
        validExternalUriSchemes.add(VKApiConst.HTTPS);
        VALID_EXTERNAL_URI_SCHEMES = Collections.unmodifiableSet(validExternalUriSchemes);
    }

    ShareToMessengerParams(ShareToMessengerParamsBuilder builder) {
        this.uri = builder.getUri();
        this.mimeType = builder.getMimeType();
        this.metaData = builder.getMetaData();
        this.externalUri = builder.getExternalUri();
        if (this.uri == null) {
            throw new NullPointerException("Must provide non-null uri");
        } else if (this.mimeType == null) {
            throw new NullPointerException("Must provide mimeType");
        } else if (!VALID_URI_SCHEMES.contains(this.uri.getScheme())) {
            throw new IllegalArgumentException("Unsupported URI scheme: " + this.uri.getScheme());
        } else if (!VALID_MIME_TYPES.contains(this.mimeType)) {
            throw new IllegalArgumentException("Unsupported mime-type: " + this.mimeType);
        } else if (this.externalUri != null && !VALID_EXTERNAL_URI_SCHEMES.contains(this.externalUri.getScheme())) {
            throw new IllegalArgumentException("Unsupported external uri scheme: " + this.externalUri.getScheme());
        }
    }

    public static ShareToMessengerParamsBuilder newBuilder(Uri uri2, String mimeType2) {
        return new ShareToMessengerParamsBuilder(uri2, mimeType2);
    }
}
