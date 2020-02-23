package com.beetalk.sdk.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;
import com.beetalk.sdk.SDKConstants;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private CacheHelper cacheHelper;
    private final WeakReference<ImageView> mImageView;
    private final int mPlaceHolderRes;

    public static ImageTarget load(String url) {
        return new ImageTarget(url);
    }

    private ImageLoader(ImageView imageView, int placeHolderRes) {
        this.mImageView = new WeakReference<>(imageView);
        this.mPlaceHolderRes = placeHolderRes;
        this.cacheHelper = CacheHelper.get(imageView.getContext(), CacheHelper.CACHE_IMAGE_FOLDER);
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        ImageView imageView = (ImageView) this.mImageView.get();
        if (imageView != null) {
            imageView.setImageResource(this.mPlaceHolderRes);
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = this.cacheHelper.getAsBitmap(url);
        if (bitmap != null) {
            return bitmap;
        }
        try {
            bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
            this.cacheHelper.put(url, bitmap, (int) SDKConstants.AUTH_REFRESH_TIME_INTERVAL);
            return bitmap;
        } catch (IOException e) {
            BBLogger.e(e);
            return bitmap;
        } catch (OutOfMemoryError e2) {
            BBLogger.e("ImageLoader failed to load image: out of memory", new Object[0]);
            return bitmap;
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            ImageView imageView = (ImageView) this.mImageView.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            } else if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static final class ImageTarget {
        private int mPlaceHolderRes;
        private final String mUrl;

        private ImageTarget(String url) {
            this.mPlaceHolderRes = 0;
            this.mUrl = url;
        }

        public ImageTarget placeholder(int res) {
            this.mPlaceHolderRes = res;
            return this;
        }

        public void into(ImageView imageView) {
            if (imageView != null) {
                ImageLoader loadTask = new ImageLoader(imageView, this.mPlaceHolderRes);
                if (Build.VERSION.SDK_INT >= 11) {
                    loadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{this.mUrl});
                    return;
                }
                loadTask.execute(new String[]{this.mUrl});
            }
        }
    }
}
