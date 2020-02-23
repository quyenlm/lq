package com.tencent.ieg.ntv.network;

import android.content.Context;
import android.util.LruCache;
import android.util.Pair;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetCDNFile;
import com.tencent.ieg.ntv.event.EventGetCDNFileFinish;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.utils.CDNFileDownloader;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CDNFileManager {
    private static final String CACHE_DIR = "NTVCache";
    public static final int DEFAULT_CACHE_SIZE = 10485760;
    private static final String TAG = CDNFileManager.class.getSimpleName();
    private static CDNFileManager sInstance;
    /* access modifiers changed from: private */
    public LruCache<String, Pair<Boolean, byte[]>> mCache;
    private File mCacheDir;
    /* access modifiers changed from: private */
    public CDNFileDownloader.IDownloadListener mDownloadListener = new CDNFileDownloader.IDownloadListener() {
        public void onComplete(String url, byte[] data) {
            CDNFileManager.log("download complete, cache and post data for url:" + url);
            CDNFileManager.this.mCache.put(url, new Pair(false, data));
            CDNFileManager.this.saveFile(url, data);
            EventManager.getInstance().post(3002, new EventGetCDNFileFinish(url, data));
        }
    };
    private IEventListener mEventListener = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            String url = ((EventGetCDNFile) event).url;
            CDNFileManager.log("onEvent url:" + url);
            if (!url.isEmpty()) {
                Pair<Boolean, byte[]> pair = (Pair) CDNFileManager.this.mCache.get(url);
                if (pair != null) {
                    if (((Boolean) pair.first).booleanValue()) {
                        CDNFileManager.log("downloading");
                        return;
                    } else if (pair.second != null) {
                        CDNFileManager.log("data cached, post directly");
                        EventManager.getInstance().post(3002, new EventGetCDNFileFinish(url, (byte[]) pair.second));
                        return;
                    } else {
                        CDNFileManager.log("not downloading and no data");
                        CDNFileManager.this.mCache.remove(url);
                    }
                }
                byte[] data = CDNFileManager.this.loadFile(url);
                if (data != null) {
                    CDNFileManager.log("file exist, add to cache and post");
                    CDNFileManager.this.mCache.put(url, new Pair(false, data));
                    EventManager.getInstance().post(3002, new EventGetCDNFileFinish(url, data));
                    return;
                }
                CDNFileManager.log("file not exist");
                if (CDNFileManager.this.mCache.get(url) == null) {
                    CDNFileManager.log("do download");
                    CDNFileManager.this.mCache.put(url, new Pair(true, (Object) null));
                    new CDNFileDownloader(url, CDNFileManager.this.mDownloadListener).start();
                }
            }
        }
    };
    private boolean mStoragePermission = false;

    private CDNFileManager() {
    }

    public static CDNFileManager getInstance() {
        if (sInstance == null) {
            sInstance = new CDNFileManager();
        }
        return sInstance;
    }

    public void init(Context context) {
        File file;
        this.mStoragePermission = Util.permissionGranted("android.permission.WRITE_EXTERNAL_STORAGE");
        if (this.mStoragePermission) {
            file = context.getExternalFilesDir(CACHE_DIR);
        } else {
            file = new File(context.getFilesDir(), CACHE_DIR);
        }
        this.mCacheDir = file;
        log("init mStoragePermission:" + this.mStoragePermission + ", mCacheDir:" + this.mCacheDir.getAbsolutePath());
        log("mCacheDir exists:" + this.mCacheDir.exists() + ", isDirectory:" + this.mCacheDir.isDirectory());
        if (!this.mCacheDir.exists()) {
            this.mCacheDir.mkdirs();
        } else if (!this.mCacheDir.isDirectory()) {
            this.mCacheDir.delete();
            this.mCacheDir.mkdirs();
        }
        this.mCache = new LruCache<String, Pair<Boolean, byte[]>>(DEFAULT_CACHE_SIZE) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, Pair<Boolean, byte[]> value) {
                if (value.second == null) {
                    return 0;
                }
                return ((byte[]) value.second).length;
            }
        };
        EventManager.getInstance().register(3001, this.mEventListener);
    }

    public static void destroyInstance() {
        if (sInstance != null) {
            sInstance.cleanUp();
        }
    }

    private void cleanUp() {
        this.mCache.evictAll();
        this.mCache = null;
    }

    /* access modifiers changed from: private */
    public byte[] loadFile(String url) {
        try {
            log("loadFile url:" + url);
            File file = new File(this.mCacheDir, Util.getMd5(url));
            log("filepath:" + file.getAbsolutePath());
            if (file.exists() && file.isFile()) {
                FileInputStream fins = new FileInputStream(file);
                byte[] data = Util.getBytes(fins);
                fins.close();
                return data;
            }
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void saveFile(String url, byte[] data) {
        try {
            log("saveFile url:" + url);
            File file = new File(this.mCacheDir, Util.getMd5(url));
            log("saveFile filepath:" + file.getAbsolutePath());
            if (!file.exists()) {
                log("file not exist, save");
                FileOutputStream fouts = new FileOutputStream(file);
                fouts.write(data);
                fouts.close();
            } else if (file.isFile()) {
                log("file exist, no need to save");
            } else {
                log("file exist, but not file");
            }
        } catch (Exception e) {
            Logger.w(TAG, e);
        }
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }
}
