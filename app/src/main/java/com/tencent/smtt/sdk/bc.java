package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Bundle;
import com.tencent.smtt.export.external.DexLoader;

class bc {
    private DexLoader a = null;
    private Object b = null;

    public bc(DexLoader dexLoader) {
        this.a = dexLoader;
    }

    public Object a(Context context, Object obj, Bundle bundle) {
        if (this.a != null) {
            this.b = this.a.newInstance("com.tencent.tbs.cache.TbsVideoCacheTaskProxy", new Class[]{Context.class, Object.class, Bundle.class}, context, obj, bundle);
        }
        return this.b;
    }

    public void a() {
        if (this.a != null) {
            this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "pauseTask", new Class[0], new Object[0]);
        }
    }

    public void a(boolean z) {
        if (this.a != null) {
            this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "removeTask", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public void b() {
        if (this.a != null) {
            this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "resumeTask", new Class[0], new Object[0]);
        }
    }

    public void c() {
        if (this.a != null) {
            this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "stopTask", new Class[0], new Object[0]);
        }
    }

    public long d() {
        if (this.a != null) {
            Object invokeMethod = this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getContentLength", new Class[0], new Object[0]);
            if (invokeMethod instanceof Long) {
                return ((Long) invokeMethod).longValue();
            }
        }
        return 0;
    }

    public int e() {
        if (this.a != null) {
            Object invokeMethod = this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getDownloadedSize", new Class[0], new Object[0]);
            if (invokeMethod instanceof Integer) {
                return ((Integer) invokeMethod).intValue();
            }
        }
        return 0;
    }

    public int f() {
        if (this.a != null) {
            Object invokeMethod = this.a.invokeMethod(this.b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getProgress", new Class[0], new Object[0]);
            if (invokeMethod instanceof Integer) {
                return ((Integer) invokeMethod).intValue();
            }
        }
        return 0;
    }
}
