package com.tencent.imsdk.extend.tool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static JSONObject mapToJson(Map<?, ?> data) {
        JSONObject object = new JSONObject();
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            String key = (String) entry.getKey();
            if (key == null) {
                throw new NullPointerException("key == null");
            }
            try {
                object.put(key, wrap(entry.getValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static JSONArray collectionToJson(Collection data) {
        JSONArray jsonArray = new JSONArray();
        if (data != null) {
            for (Object aData : data) {
                jsonArray.put(wrap(aData));
            }
        }
        return jsonArray;
    }

    public static JSONArray arrayToJson(Object data) throws JSONException {
        if (!data.getClass().isArray()) {
            throw new JSONException("Not a primitive data: " + data.getClass());
        }
        int length = Array.getLength(data);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < length; i++) {
            jsonArray.put(wrap(Array.get(data, i)));
        }
        return jsonArray;
    }

    private static Object wrap(Object o) {
        if (o == null) {
            return null;
        }
        if ((o instanceof JSONArray) || (o instanceof JSONObject)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return collectionToJson((Collection) o);
            }
            if (o.getClass().isArray()) {
                return arrayToJson(o);
            }
            if (o instanceof Map) {
                return mapToJson((Map) o);
            }
            if ((o instanceof Boolean) || (o instanceof Byte) || (o instanceof Character) || (o instanceof Double) || (o instanceof Float) || (o instanceof Integer) || (o instanceof Long) || (o instanceof Short) || (o instanceof String)) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
            return null;
        } catch (Exception e) {
        }
    }

    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (!(object instanceof Iterable)) {
            return object;
        } else {
            JSONArray json2 = new JSONArray();
            for (Object value : (Iterable) object) {
                json2.put(value);
            }
            return json2;
        }
    }

    public static boolean isEmptyObject(JSONObject object) {
        return object.names() == null;
    }

    public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
        return toMap(object.getJSONObject(key));
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    public static List toList(String jsonStr) throws JSONException {
        return toList(new JSONArray(jsonStr));
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        }
        if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        }
        if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        }
        return json;
    }
}
