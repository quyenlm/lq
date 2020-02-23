package com.tencent.imsdk.android.friend.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMProxyRunner;
import com.tencent.imsdk.IMProxyTask;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IntentModuleFactory {
    private static volatile IntentModuleFactory instance = null;
    private static Context mCtx;

    private IntentModuleFactory() {
    }

    public static IntentModuleFactory getInstance(Context ctx) {
        if (instance == null) {
            synchronized (IntentModuleFactory.class) {
                if (instance == null) {
                    mCtx = ctx;
                    instance = new IntentModuleFactory();
                }
            }
        }
        return instance;
    }

    public Intent getTextIntent(Intent intent, String content) {
        if (mCtx == null) {
            IMLogger.d("getTextIntent you must init context! ");
            return null;
        }
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", content);
        return intent;
    }

    public Intent getImageIntent(Intent intent, String path) {
        Uri uri;
        if (mCtx == null) {
            IMLogger.d("getImageIntent you must init context! ");
            return null;
        }
        try {
            intent.setType("image/*");
            if (path.contains("content://")) {
                uri = Uri.parse(path);
            } else {
                uri = IMSDKFileProvider.getUriFromPath(path, mCtx);
            }
            IMLogger.d(" getImageIntent path = " + path + " uri = " + uri);
            if (uri != null) {
                intent.setFlags(1);
                intent.putExtra("android.intent.extra.STREAM", uri);
                return intent;
            }
        } catch (Exception e) {
            IMLogger.d(" intent error = " + e.getMessage());
        }
        return null;
    }

    public <T> void dispatchIntent(final IMCallback<T> listener, final Intent shareIntent) {
        if (mCtx != null) {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(mCtx) {
                private static final int FRIEND_INTENT_REQUEST = 144;
                boolean bActivityCallbackFlag = false;
                protected Activity proxyActivity;

                public void onPreProxy() {
                    this.bActivityCallbackFlag = false;
                }

                public void onPostProxy(Activity activity) {
                    try {
                        this.proxyActivity = activity;
                        this.proxyActivity.startActivityForResult(shareIntent, FRIEND_INTENT_REQUEST);
                    } catch (Exception e) {
                        IMLogger.d("dispatchIntent exception = " + e.getMessage());
                        IMRetCode.retByIMSDK(3, 3, "dispatchIntent error = " + e.getMessage(), listener);
                    }
                }

                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    IMLogger.d("dispatchIntent request code = " + requestCode + " result code = " + resultCode);
                    if (requestCode == FRIEND_INTENT_REQUEST) {
                        this.bActivityCallbackFlag = true;
                        IMRetCode.retByIMSDK(1, 1, "dispatchintent over !!", listener);
                        dismissProxyActivity();
                        return;
                    }
                    IMLogger.e("unknown request : " + requestCode + ", result : " + resultCode);
                }

                public void onDestroy() {
                    super.onDestroy();
                    IMLogger.d("dispatchIntent onActivityDestroy ");
                    dismissProxyActivity();
                    if (!this.bActivityCallbackFlag) {
                        IMRetCode.retByIMSDK(2, 2, "activity destroyed with out dispatchIntent callback !!", listener);
                    }
                }

                private void dismissProxyActivity() {
                    if (this.proxyActivity != null) {
                        this.proxyActivity.finish();
                        this.proxyActivity = null;
                    }
                }
            });
        } else {
            IMRetCode.retByIMSDK(11, 11, "context is null", listener);
        }
    }

    public void shareNetworkImage(final String path, final IMCallback<String> callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    File networkPic = IntentModuleFactory.this.saveImage(IntentModuleFactory.this.getNetWorkBitmap(path));
                    if (networkPic != null) {
                        callback.onSuccess(networkPic.getAbsolutePath());
                        return;
                    }
                    callback.onError(new IMException(8, "shareNetworkImage download image path is null!!", 8, ""));
                } catch (Exception e) {
                    IMLogger.e("shareNetworkImage error = " + e.getMessage());
                }
            }
        }).start();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008c A[SYNTHETIC, Splitter:B:17:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b1 A[SYNTHETIC, Splitter:B:24:0x00b1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.File saveImage(android.graphics.Bitmap r13) {
        /*
            r12 = this;
            r7 = 0
            android.content.Context r8 = mCtx
            if (r8 != 0) goto L_0x000c
            java.lang.String r8 = "system saveImage you must init activity! "
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r8)
            r5 = r7
        L_0x000b:
            return r5
        L_0x000c:
            r0 = 0
            android.content.Context r8 = mCtx     // Catch:{ Exception -> 0x006f }
            java.io.File r3 = r8.getExternalCacheDir()     // Catch:{ Exception -> 0x006f }
            java.lang.String r6 = r3.getPath()     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006f }
            r8.<init>()     // Catch:{ Exception -> 0x006f }
            java.lang.String r9 = "itop_tmp_"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x006f }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r8 = r8.append(r10)     // Catch:{ Exception -> 0x006f }
            java.lang.String r9 = ".png"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x006f }
            java.lang.String r4 = r8.toString()     // Catch:{ Exception -> 0x006f }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x006f }
            r5.<init>(r6, r4)     // Catch:{ Exception -> 0x006f }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x006f }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006f }
            r8.<init>(r5)     // Catch:{ Exception -> 0x006f }
            r1.<init>(r8)     // Catch:{ Exception -> 0x006f }
            android.graphics.Bitmap$CompressFormat r8 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
            r9 = 100
            r13.compress(r8, r9, r1)     // Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
            r1.flush()     // Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
            if (r1 == 0) goto L_0x000b
            r1.close()     // Catch:{ Exception -> 0x0053 }
            goto L_0x000b
        L_0x0053:
            r2 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = " saveImage exception = "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r2.getMessage()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
            goto L_0x000b
        L_0x006f:
            r2 = move-exception
        L_0x0070:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ae }
            r8.<init>()     // Catch:{ all -> 0x00ae }
            java.lang.String r9 = " saveImage exception = "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00ae }
            java.lang.String r9 = r2.getMessage()     // Catch:{ all -> 0x00ae }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00ae }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00ae }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r8)     // Catch:{ all -> 0x00ae }
            if (r0 == 0) goto L_0x008f
            r0.close()     // Catch:{ Exception -> 0x0092 }
        L_0x008f:
            r5 = r7
            goto L_0x000b
        L_0x0092:
            r2 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = " saveImage exception = "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r2.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r8)
            goto L_0x008f
        L_0x00ae:
            r7 = move-exception
        L_0x00af:
            if (r0 == 0) goto L_0x00b4
            r0.close()     // Catch:{ Exception -> 0x00b5 }
        L_0x00b4:
            throw r7
        L_0x00b5:
            r2 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = " saveImage exception = "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r2.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r8)
            goto L_0x00b4
        L_0x00d1:
            r7 = move-exception
            r0 = r1
            goto L_0x00af
        L_0x00d4:
            r2 = move-exception
            r0 = r1
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.android.friend.tools.IntentModuleFactory.saveImage(android.graphics.Bitmap):java.io.File");
    }

    /* access modifiers changed from: protected */
    public Bitmap getNetWorkBitmap(String urlString) {
        Bitmap bitmap = null;
        try {
            HttpURLConnection urlConn = (HttpURLConnection) new URL(urlString).openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            InputStream is = urlConn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            IMLogger.d(" getNetWorkBitmap exception = " + e.getMessage());
            return bitmap;
        }
    }
}
