package com.tencent.component.event;

import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;

class ObserverBean {
    public static final String DEFAULT_INVOKE_METHOD_NAME = "onNotify";
    private static LruCache<Class<?>, HashMap<String, Method>> sMethodsCache = new LruCache<>(20);
    public static final boolean warnings = true;
    private final WeakReference<Object> mEventSourceSenderReference;
    public final String mInvokationMethod;
    public final boolean mInvokeInUIThread;
    private final int mObservingObjectHashCode;
    private final int mSpecifiedSenderHashCode;
    private final WeakReference<Object> observingObjectReference;

    public ObserverBean(Object observingObject, Object sender, String invokationMethod, boolean invokeInUIThread) {
        if (observingObject == null) {
            throw new NullPointerException("ObserverBean cannot be null");
        } else if (invokationMethod == null) {
            throw new NullPointerException("mInvokationMethod cannot be null");
        } else {
            this.observingObjectReference = new WeakReference<>(observingObject);
            this.mObservingObjectHashCode = observingObject.hashCode();
            if (sender != null) {
                this.mEventSourceSenderReference = new WeakReference<>(sender);
                this.mSpecifiedSenderHashCode = sender.hashCode();
            } else {
                this.mEventSourceSenderReference = null;
                this.mSpecifiedSenderHashCode = 0;
            }
            this.mInvokationMethod = invokationMethod;
            this.mInvokeInUIThread = invokeInUIThread;
        }
    }

    public int hashCode() {
        return (((((((this.mInvokationMethod == null ? 0 : this.mInvokationMethod.hashCode()) + 31) * 31) + (this.mInvokeInUIThread ? 1231 : 1237)) * 31) + this.mObservingObjectHashCode) * 31) + this.mSpecifiedSenderHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ObserverBean other = (ObserverBean) obj;
        if (this.mInvokationMethod == null) {
            if (other.mInvokationMethod != null) {
                return false;
            }
        } else if (!this.mInvokationMethod.equals(other.mInvokationMethod)) {
            return false;
        }
        if (this.mInvokeInUIThread != other.mInvokeInUIThread) {
            return false;
        }
        if (this.mObservingObjectHashCode != other.mObservingObjectHashCode) {
            return false;
        }
        if (this.mSpecifiedSenderHashCode != other.mSpecifiedSenderHashCode) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "ObserverBean [observer=" + getObservingObject() + " invk=" + this.mInvokationMethod + "]";
    }

    public Object getObservingObject() {
        return this.observingObjectReference.get();
    }

    public Object getEventSourceSender() {
        if (this.mEventSourceSenderReference != null) {
            return this.mEventSourceSenderReference.get();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void invoke(Object args) {
        Object observingObject = getObservingObject();
        if (observingObject == null) {
            return;
        }
        if (!(observingObject instanceof Observer) || !DEFAULT_INVOKE_METHOD_NAME.equals(this.mInvokationMethod)) {
            Method method = getMethod();
            if (method != null) {
                try {
                    method.setAccessible(true);
                    method.invoke(observingObject, new Object[]{args});
                } catch (Exception e) {
                    error(e.getMessage(), e);
                }
            } else {
                warn("method->" + this.mInvokationMethod + " not exists in " + observingObject);
            }
        } else {
            ((Observer) observingObject).onNotify((Event) args);
        }
    }

    public boolean invocationMethodExists() {
        return getMethod() != null;
    }

    private static Method getMethodFromCache(Class<?> clazz, String methodName) {
        HashMap<String, Method> methodMap;
        if (clazz == null || TextUtils.isEmpty(methodName) || (methodMap = sMethodsCache.get(clazz)) == null) {
            return null;
        }
        return methodMap.get(methodName);
    }

    private static void putMethodToCache(Class<?> clazz, String methodName, Method method) {
        if (clazz != null && !TextUtils.isEmpty(methodName) && method != null) {
            HashMap<String, Method> methodMap = sMethodsCache.get(clazz);
            if (methodMap == null) {
                methodMap = new HashMap<>();
                sMethodsCache.put(clazz, methodMap);
            }
            methodMap.put(methodName, method);
        }
    }

    private Method getMethod() {
        Class<?> c;
        Method[] methods;
        Object observingObject = getObservingObject();
        String metohdName = this.mInvokationMethod;
        Method targetMethod = null;
        if (observingObject != null && !TextUtils.isEmpty(metohdName) && (targetMethod = getMethodFromCache(c, metohdName)) == null && (methods = c.getDeclaredMethods()) != null) {
            for (Method method : methods) {
                if (method != null && metohdName.equals(method.getName())) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        if (parameterTypes[0] == Event.class) {
                            targetMethod = method;
                            putMethodToCache((c = observingObject.getClass()), metohdName, method);
                        } else {
                            warn("Looking to invoke '" + this.mInvokationMethod + "', found in " + c + " but parameterClass does not match. Expected " + Event.class + ", potential invokation method has " + parameterTypes[0]);
                        }
                    } else if (parameterTypes.length == 0) {
                        warn("Looking to invoke '" + this.mInvokationMethod + "', found in " + c + " but has no parameter");
                    } else if (parameterTypes.length > 1) {
                        warn("Looking to invoke '" + this.mInvokationMethod + "', found in " + c + " but there are too many parameters");
                    }
                }
            }
        }
        return targetMethod;
    }

    private void warn(String message) {
        LogUtil.w("EventCenter", message);
    }

    private void error(String message, Exception e) {
        LogUtil.e("EventCenter", message);
    }
}
