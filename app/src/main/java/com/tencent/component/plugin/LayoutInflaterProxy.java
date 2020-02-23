package com.tencent.component.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

final class LayoutInflaterProxy extends LayoutInflater {
    private InflaterImpl mInflaterImpl;

    public interface InflaterImpl {
        View createViewImpl(View view, String str, Context context, AttributeSet attributeSet) throws ClassNotFoundException;
    }

    protected LayoutInflaterProxy(Context context) {
        this(context, (InflaterImpl) null);
    }

    protected LayoutInflaterProxy(Context context, InflaterImpl inflater) {
        super(context);
        setInflaterImpl(inflater);
        init();
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return null;
    }

    public void setInflaterImpl(InflaterImpl inflater) {
        this.mInflaterImpl = inflater;
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    private void init() {
        boolean init = false;
        if (supportFactory2()) {
            try {
                setFactory2(new LayoutInflater.Factory2() {
                    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                        return LayoutInflaterProxy.this.createViewSafely(parent, name, context, attrs);
                    }

                    public View onCreateView(String name, Context context, AttributeSet attrs) {
                        return null;
                    }
                });
                init = true;
            } catch (Throwable th) {
            }
        }
        if (!init) {
            setFactory(new LayoutInflater.Factory() {
                public View onCreateView(String name, Context context, AttributeSet attrs) {
                    return LayoutInflaterProxy.this.createViewSafely((View) null, name, context, attrs);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public View createViewSafely(View parent, String name, Context context, AttributeSet attrs) {
        InflaterImpl inflater = this.mInflaterImpl;
        if (inflater == null) {
            return null;
        }
        try {
            return inflater.createViewImpl(parent, name, context, attrs);
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean supportFactory2() {
        return Build.VERSION.SDK_INT >= 11;
    }
}
