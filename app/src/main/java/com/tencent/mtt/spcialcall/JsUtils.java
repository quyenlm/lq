package com.tencent.mtt.spcialcall;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.spcialcall.sdk.WebViewProxy;
import java.lang.reflect.Method;
import org.json.JSONArray;

public class JsUtils {
    private static final String TAG = "JsUtils";

    public static String generateJs(String name, Object object) {
        if (TextUtils.isEmpty(name) || object == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("var ").append(name).append("={};");
        try {
            Class<?> objClass = Class.forName("java.lang.Object");
            for (Method m : object.getClass().getMethods()) {
                if (m.getDeclaringClass() != objClass) {
                    String methodName = m.getName();
                    Class[] paramTypes = m.getParameterTypes();
                    if (!checkParameterType(paramTypes)) {
                        Log.w(TAG, "Only string parameters are supported!");
                    } else {
                        sb.append(generateJs(name, methodName, paramTypes.length));
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
    }

    private static boolean checkParameterType(Class[] paramTypes) {
        for (Class type : paramTypes) {
            if (!"java.lang.String".equals(type.getName())) {
                return false;
            }
        }
        return true;
    }

    private static String generateJs(String service, String method, int numOfArgs) {
        StringBuilder jsArgs = new StringBuilder();
        if (numOfArgs > 0) {
            int N = numOfArgs;
            jsArgs.append("$").append(0);
            for (int i = 1; i < N; i++) {
                jsArgs.append(",").append("$").append(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(service).append('.').append(method).append("=function(").append(jsArgs).append("){return mttsp_exec('").append(service).append("','").append(method).append("',[").append(jsArgs).append("]);};");
        return sb.toString();
    }

    public static String exec(WebViewProxy proxy, String service, String name, JSONArray jsonArgs) {
        Object object = proxy.getJavascriptInterface(service);
        if (object == null) {
            return null;
        }
        try {
            Class<?> stringCls = Class.forName("java.lang.String");
            int numOfArgs = jsonArgs.length();
            Class[] argTypes = new Class[numOfArgs];
            for (int i = 0; i < numOfArgs; i++) {
                argTypes[i] = stringCls;
            }
            String[] args = new String[numOfArgs];
            for (int i2 = 0; i2 < numOfArgs; i2++) {
                args[i2] = jsonArgs.getString(i2);
            }
            Object ret = object.getClass().getMethod(name, argTypes).invoke(object, args);
            if (ret != null) {
                return ret.toString();
            }
            return null;
        } catch (Exception e) {
            Log.w(TAG, e);
            return null;
        }
    }
}
