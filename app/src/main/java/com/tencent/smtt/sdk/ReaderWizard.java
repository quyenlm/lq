package com.tencent.smtt.sdk;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.sdk.TbsReaderView;

public class ReaderWizard {
    private DexLoader a;
    private TbsReaderView.ReaderCallback b;

    public ReaderWizard(TbsReaderView.ReaderCallback readerCallback) {
        this.a = null;
        this.b = null;
        this.a = a();
        this.b = readerCallback;
    }

    private static DexLoader a() {
        bh c = o.a(true).c();
        if (c != null) {
            return c.b();
        }
        return null;
    }

    public static Drawable getResDrawable(int i) {
        DexLoader a2 = a();
        if (a2 != null) {
            Object invokeStaticMethod = a2.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "getResDrawable", new Class[]{Integer.class}, Integer.valueOf(i));
            if (invokeStaticMethod instanceof Drawable) {
                return (Drawable) invokeStaticMethod;
            }
        }
        return null;
    }

    public static String getResString(int i) {
        DexLoader a2 = a();
        if (a2 != null) {
            Object invokeStaticMethod = a2.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "getResString", new Class[]{Integer.class}, Integer.valueOf(i));
            if (invokeStaticMethod instanceof String) {
                return (String) invokeStaticMethod;
            }
        }
        return "";
    }

    public static boolean isSupportCurrentPlatform(Context context) {
        DexLoader a2 = a();
        if (a2 != null) {
            Object invokeStaticMethod = a2.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "isSupportCurrentPlatform", new Class[]{Context.class}, context);
            if (invokeStaticMethod instanceof Boolean) {
                return ((Boolean) invokeStaticMethod).booleanValue();
            }
        }
        return false;
    }

    public static boolean isSupportExt(String str) {
        DexLoader a2 = a();
        if (a2 != null) {
            Object invokeStaticMethod = a2.invokeStaticMethod("com.tencent.tbs.reader.TbsReader", "isSupportExt", new Class[]{String.class}, str);
            if (invokeStaticMethod instanceof Boolean) {
                return ((Boolean) invokeStaticMethod).booleanValue();
            }
        }
        return false;
    }

    public boolean checkPlugin(Object obj, Context context, String str, boolean z) {
        if (this.a == null) {
            Log.e("ReaderWizard", "checkPlugin:Unexpect null object!");
            return false;
        }
        Object invokeMethod = this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "checkPlugin", new Class[]{Context.class, String.class, Boolean.class}, context, str, Boolean.valueOf(z));
        if (invokeMethod instanceof Boolean) {
            return ((Boolean) invokeMethod).booleanValue();
        }
        Log.e("ReaderWizard", "Unexpect return value type of call checkPlugin!");
        return false;
    }

    public void destroy(Object obj) {
        this.b = null;
        if (this.a == null || obj == null) {
            Log.e("ReaderWizard", "destroy:Unexpect null object!");
            return;
        }
        this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "destroy", new Class[0], new Object[0]);
    }

    public void doCommand(Object obj, Integer num, Object obj2, Object obj3) {
        if (this.a == null) {
            Log.e("ReaderWizard", "doCommand:Unexpect null object!");
            return;
        }
        this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "doCommand", new Class[]{Integer.class, Object.class, Object.class}, new Integer(num.intValue()), obj2, obj3);
    }

    public Object getTbsReader() {
        return this.a.newInstance("com.tencent.tbs.reader.TbsReader", new Class[0], new Object[0]);
    }

    public boolean initTbsReader(Object obj, Context context) {
        if (this.a == null || obj == null) {
            Log.e("ReaderWizard", "initTbsReader:Unexpect null object!");
            return false;
        }
        Object invokeMethod = this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "init", new Class[]{Context.class, DexLoader.class, Object.class}, context, this.a, this);
        if (invokeMethod instanceof Boolean) {
            return ((Boolean) invokeMethod).booleanValue();
        }
        Log.e("ReaderWizard", "Unexpect return value type of call initTbsReader!");
        return false;
    }

    public void onCallBackAction(Integer num, Object obj, Object obj2) {
        if (this.b != null) {
            this.b.onCallBackAction(num, obj, obj2);
        }
    }

    public void onSizeChanged(Object obj, int i, int i2) {
        if (this.a == null) {
            Log.e("ReaderWizard", "onSizeChanged:Unexpect null object!");
            return;
        }
        this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "onSizeChanged", new Class[]{Integer.class, Integer.class}, new Integer(i), new Integer(i2));
    }

    public boolean openFile(Object obj, Context context, Bundle bundle, FrameLayout frameLayout) {
        if (this.a == null) {
            Log.e("ReaderWizard", "openFile:Unexpect null object!");
            return false;
        }
        Object invokeMethod = this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "openFile", new Class[]{Context.class, Bundle.class, FrameLayout.class}, context, bundle, frameLayout);
        if (invokeMethod instanceof Boolean) {
            return ((Boolean) invokeMethod).booleanValue();
        }
        Log.e("ReaderWizard", "Unexpect return value type of call openFile!");
        return false;
    }

    public void userStatistics(Object obj, String str) {
        if (this.a == null) {
            Log.e("ReaderWizard", "userStatistics:Unexpect null object!");
            return;
        }
        this.a.invokeMethod(obj, "com.tencent.tbs.reader.TbsReader", "userStatistics", new Class[]{String.class}, str);
    }
}
