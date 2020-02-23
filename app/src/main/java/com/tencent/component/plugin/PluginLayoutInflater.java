package com.tencent.component.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import com.tencent.component.plugin.LayoutInflaterProxy;
import com.tencent.component.utils.log.LogUtil;
import java.lang.reflect.Constructor;
import java.util.HashMap;

final class PluginLayoutInflater extends LayoutInflater implements LayoutInflaterProxy.InflaterImpl {
    private static final String TAG = "PluginLayoutInflater";
    private static final String[] sClassPrefixList = {"android.widget.", "android.webkit."};
    static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private final Object[] mConstructorArgs;
    private final HashMap<String, Constructor<? extends View>> mConstructorMap;
    private HashMap<String, Boolean> mFilterMap;
    private final Plugin mPlugin;

    protected PluginLayoutInflater(Context context, Plugin plugin) {
        this(context, new LayoutInflaterProxy(context), plugin);
    }

    private PluginLayoutInflater(Context context, LayoutInflaterProxy proxy, Plugin plugin) {
        super(proxy, context);
        this.mConstructorArgs = new Object[2];
        this.mConstructorMap = new HashMap<>();
        this.mPlugin = plugin;
        proxy.setInflaterImpl(this);
    }

    protected PluginLayoutInflater(PluginLayoutInflater original, Context newContext) {
        super(original, newContext);
        this.mConstructorArgs = new Object[2];
        this.mConstructorMap = new HashMap<>();
        this.mPlugin = original.mPlugin;
    }

    public void setFilter(LayoutInflater.Filter filter) {
        super.setFilter(filter);
        if (filter != null) {
            this.mFilterMap = new HashMap<>();
        }
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return new PluginLayoutInflater(this, newContext);
    }

    /* access modifiers changed from: protected */
    public View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        String[] strArr = sClassPrefixList;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            try {
                View view = createView(name, strArr[i], attrs);
                if (view != null) {
                    return view;
                }
                i++;
            } catch (ClassNotFoundException e) {
            }
        }
        return super.onCreateView(name, attrs);
    }

    public View createViewImpl(View parent, String name, Context context, AttributeSet attrs) throws ClassNotFoundException {
        View view = null;
        if (-1 != name.indexOf(46)) {
            try {
                view = createViewInternal(name, attrs);
            } catch (Throwable th) {
                LogUtil.d(TAG, "fail to create view internal for " + name + " with " + this.mPlugin.getClass().getClassLoader());
            }
        }
        if (view != null) {
            return view;
        }
        try {
            return createViewFromParent(name, attrs);
        } catch (Throwable th2) {
            return view;
        }
    }

    private View createViewFromParent(String name, AttributeSet attrs) throws ClassNotFoundException {
        if (-1 == name.indexOf(46)) {
            return onCreateView(name, attrs);
        }
        return createView(name, (String) null, attrs);
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    private View createViewInternal(String name, AttributeSet attrs) throws ClassNotFoundException, InflateException {
        String name2;
        View view;
        synchronized (this.mConstructorArgs) {
            this.mConstructorArgs[0] = getContext();
            Constructor<? extends U> constructor = this.mConstructorMap.get(name);
            Class<? extends View> clazz = null;
            try {
                LayoutInflater.Filter filter = getFilter();
                if (constructor == null) {
                    Class<? extends U> asSubclass = this.mPlugin.getClass().getClassLoader().loadClass(name).asSubclass(View.class);
                    if (!(filter == null || asSubclass == null || filter.onLoadClass(asSubclass))) {
                        failNotAllowed(name, attrs);
                    }
                    constructor = asSubclass.getConstructor(sConstructorSignature);
                    this.mConstructorMap.put(name, constructor);
                } else if (filter != null) {
                    Boolean allowedState = this.mFilterMap.get(name);
                    if (allowedState == null) {
                        Class<? extends U> asSubclass2 = this.mPlugin.getClass().getClassLoader().loadClass(name).asSubclass(View.class);
                        boolean allowed = asSubclass2 != null && filter.onLoadClass(asSubclass2);
                        this.mFilterMap.put(name, Boolean.valueOf(allowed));
                        if (!allowed) {
                            failNotAllowed(name, attrs);
                        }
                    } else if (allowedState.equals(Boolean.FALSE)) {
                        failNotAllowed(name, attrs);
                    }
                }
                Object[] args = this.mConstructorArgs;
                args[1] = attrs;
                view = (View) constructor.newInstance(args);
                if (Build.VERSION.SDK_INT >= 16 && (view instanceof ViewStub)) {
                    ((ViewStub) view).setLayoutInflater(this);
                }
            } catch (NoSuchMethodException e) {
                InflateException ie = new InflateException(attrs.getPositionDescription() + ": Error inflating class " + name);
                ie.initCause(e);
                throw ie;
            } catch (ClassCastException e2) {
                InflateException ie2 = new InflateException(attrs.getPositionDescription() + ": Class is not a View " + name);
                ie2.initCause(e2);
                throw ie2;
            } catch (ClassNotFoundException e3) {
                throw e3;
            } catch (Exception e4) {
                StringBuilder append = new StringBuilder().append(attrs.getPositionDescription()).append(": Error inflating class ");
                if (clazz == null) {
                    name2 = "<unknown>";
                } else {
                    name2 = clazz.getName();
                }
                InflateException ie3 = new InflateException(append.append(name2).toString());
                ie3.initCause(e4);
                throw ie3;
            }
        }
        return view;
    }

    private void failNotAllowed(String name, AttributeSet attrs) {
        throw new InflateException(attrs.getPositionDescription() + ": Class not allowed to be inflated " + name);
    }
}
