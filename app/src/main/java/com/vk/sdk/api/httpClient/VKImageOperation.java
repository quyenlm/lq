package com.vk.sdk.api.httpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.httpClient.VKHttpClient;

public class VKImageOperation extends VKHttpOperation<Bitmap> {
    public float imageDensity;

    public static abstract class VKImageOperationListener extends VKAbstractOperation.VKAbstractCompleteListener<VKImageOperation, Bitmap> {
    }

    public VKImageOperation(String imageUrl) {
        super(new VKHttpClient.VKHTTPRequest(imageUrl));
    }

    public Bitmap getResultObject() {
        byte[] response = getResponseData();
        Bitmap image = BitmapFactory.decodeByteArray(response, 0, response.length);
        if (this.imageDensity > 0.0f) {
            return Bitmap.createScaledBitmap(image, (int) (((float) image.getWidth()) * this.imageDensity), (int) (((float) image.getHeight()) * this.imageDensity), true);
        }
        return image;
    }

    public void setImageOperationListener(final VKImageOperationListener listener) {
        setCompleteListener(new VKAbstractOperation.VKOperationCompleteListener() {
            public void onComplete() {
                if (VKImageOperation.this.state() == VKAbstractOperation.VKOperationState.Finished && VKImageOperation.this.mLastException == null) {
                    final Bitmap result = VKImageOperation.this.getResultObject();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            listener.onComplete(VKImageOperation.this, result);
                        }
                    });
                    return;
                }
                listener.onError(VKImageOperation.this, VKImageOperation.this.generateError(VKImageOperation.this.mLastException));
            }
        });
    }
}
