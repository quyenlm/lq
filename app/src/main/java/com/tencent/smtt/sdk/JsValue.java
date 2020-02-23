package com.tencent.smtt.sdk;

import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;
import java.nio.ByteBuffer;

public class JsValue {
    private final JsContext a;
    /* access modifiers changed from: private */
    public final IX5JsValue b;

    private static class a implements IX5JsValue.JsValueFactory {
        private a() {
        }

        public String getJsValueClassName() {
            return JsValue.class.getName();
        }

        public IX5JsValue unwrap(Object obj) {
            if (obj == null || !(obj instanceof JsValue)) {
                return null;
            }
            return ((JsValue) obj).b;
        }

        public Object wrap(IX5JsValue iX5JsValue) {
            JsContext current;
            if (iX5JsValue == null || (current = JsContext.current()) == null) {
                return null;
            }
            return new JsValue(current, iX5JsValue);
        }
    }

    protected JsValue(JsContext jsContext, IX5JsValue iX5JsValue) {
        this.a = jsContext;
        this.b = iX5JsValue;
    }

    protected static IX5JsValue.JsValueFactory a() {
        return new a();
    }

    private JsValue a(IX5JsValue iX5JsValue) {
        if (iX5JsValue == null) {
            return null;
        }
        return new JsValue(this.a, iX5JsValue);
    }

    public JsValue call(Object... objArr) {
        return a(this.b.call(objArr));
    }

    public JsValue construct(Object... objArr) {
        return a(this.b.construct(objArr));
    }

    public JsContext context() {
        return this.a;
    }

    public boolean isArray() {
        return this.b.isArray();
    }

    public boolean isArrayBufferOrArrayBufferView() {
        return this.b.isArrayBufferOrArrayBufferView();
    }

    public boolean isBoolean() {
        return this.b.isBoolean();
    }

    public boolean isFunction() {
        return this.b.isFunction();
    }

    public boolean isInteger() {
        return this.b.isInteger();
    }

    public boolean isJavascriptInterface() {
        return this.b.isJavascriptInterface();
    }

    public boolean isNull() {
        return this.b.isNull();
    }

    public boolean isNumber() {
        return this.b.isNumber();
    }

    public boolean isObject() {
        return this.b.isObject();
    }

    public boolean isPromise() {
        return this.b.isPromise();
    }

    public boolean isString() {
        return this.b.isString();
    }

    public boolean isUndefined() {
        return this.b.isUndefined();
    }

    public void reject(Object obj) {
        this.b.resolveOrReject(obj, false);
    }

    public void resolve(Object obj) {
        this.b.resolveOrReject(obj, true);
    }

    public boolean toBoolean() {
        return this.b.toBoolean();
    }

    public ByteBuffer toByteBuffer() {
        return this.b.toByteBuffer();
    }

    public int toInteger() {
        return this.b.toInteger();
    }

    public Object toJavascriptInterface() {
        return this.b.toJavascriptInterface();
    }

    public Number toNumber() {
        return this.b.toNumber();
    }

    public <T> T toObject(Class<T> cls) {
        return this.b.toObject(cls);
    }

    public String toString() {
        return this.b.toString();
    }
}
