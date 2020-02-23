package bitter.jnibridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JNIBridge {

    private static class a implements InvocationHandler {
        private Object a = new Object[0];
        private long b;

        public a(long j) {
            this.b = j;
        }

        public final void a() {
            synchronized (this.a) {
                this.b = 0;
            }
        }

        public final void finalize() {
            synchronized (this.a) {
                if (this.b != 0) {
                    JNIBridge.delete(this.b);
                }
            }
        }

        public final Object invoke(Object obj, Method method, Object[] objArr) {
            Object invoke;
            synchronized (this.a) {
                invoke = this.b == 0 ? null : JNIBridge.invoke(this.b, method.getDeclaringClass(), method, objArr);
            }
            return invoke;
        }
    }

    static native void delete(long j);

    static void disableInterfaceProxy(Object obj) {
        ((a) Proxy.getInvocationHandler(obj)).a();
    }

    static native Object invoke(long j, Class cls, Method method, Object[] objArr);

    static Object newInterfaceProxy(long j, Class[] clsArr) {
        return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), clsArr, new a(j));
    }
}
