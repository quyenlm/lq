package com.facebook.internal;

import android.graphics.Bitmap;

public class ImageResponse {
    private Bitmap bitmap;
    private Exception error;
    private boolean isCachedRedirect;
    private ImageRequest request;

    ImageResponse(ImageRequest request2, Exception error2, boolean isCachedRedirect2, Bitmap bitmap2) {
        this.request = request2;
        this.error = error2;
        this.bitmap = bitmap2;
        this.isCachedRedirect = isCachedRedirect2;
    }

    public ImageRequest getRequest() {
        return this.request;
    }

    public Exception getError() {
        return this.error;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public boolean isCachedRedirect() {
        return this.isCachedRedirect;
    }
}
