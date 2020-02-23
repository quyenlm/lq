package com.facebook.share.internal;

import com.facebook.share.model.CameraEffectArguments;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CameraEffectJSONUtility {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    public interface Setter {
        void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String str, Object obj) throws JSONException;

        void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException;
    }

    static {
        SETTERS.put(String.class, new Setter() {
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String key, Object value) throws JSONException {
                builder.putArgument(key, (String) value);
            }

            public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
                json.put(key, value);
            }
        });
        SETTERS.put(String[].class, new Setter() {
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String key, Object value) throws JSONException {
                throw new IllegalArgumentException("Unexpected type from JSON");
            }

            public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
                JSONArray jsonArray = new JSONArray();
                for (String stringValue : (String[]) value) {
                    jsonArray.put(stringValue);
                }
                json.put(key, jsonArray);
            }
        });
        SETTERS.put(JSONArray.class, new Setter() {
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String key, Object value) throws JSONException {
                JSONArray jsonArray = (JSONArray) value;
                String[] argsArray = new String[jsonArray.length()];
                int i = 0;
                while (i < jsonArray.length()) {
                    Object current = jsonArray.get(i);
                    if (current instanceof String) {
                        argsArray[i] = (String) current;
                        i++;
                    } else {
                        throw new IllegalArgumentException("Unexpected type in an array: " + current.getClass());
                    }
                }
                builder.putArgument(key, argsArray);
            }

            public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
                throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
            }
        });
    }

    public static JSONObject convertToJSON(CameraEffectArguments arguments) throws JSONException {
        if (arguments == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        for (String key : arguments.keySet()) {
            Object value = arguments.get(key);
            if (value != null) {
                Setter setter = SETTERS.get(value.getClass());
                if (setter == null) {
                    throw new IllegalArgumentException("Unsupported type: " + value.getClass());
                }
                setter.setOnJSON(json, key, value);
            }
        }
        return json;
    }

    public static CameraEffectArguments convertToCameraEffectArguments(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }
        CameraEffectArguments.Builder builder = new CameraEffectArguments.Builder();
        Iterator<String> jsonIterator = jsonObject.keys();
        while (jsonIterator.hasNext()) {
            String key = jsonIterator.next();
            Object value = jsonObject.get(key);
            if (!(value == null || value == JSONObject.NULL)) {
                Setter setter = SETTERS.get(value.getClass());
                if (setter == null) {
                    throw new IllegalArgumentException("Unsupported type: " + value.getClass());
                }
                setter.setOnArgumentsBuilder(builder, key, value);
            }
        }
        return builder.build();
    }
}
