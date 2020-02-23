package com.vk.sdk.util;

import com.vk.sdk.api.model.VKApiModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKJsonHelper {
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

    public static Object toArray(JSONArray array, Class arrayClass) {
        Object ret = Array.newInstance(arrayClass.getComponentType(), array.length());
        Class<?> subType = arrayClass.getComponentType();
        for (int i = 0; i < array.length(); i++) {
            try {
                Object jsonItem = array.get(i);
                Object newInstance = subType.newInstance();
                if (jsonItem instanceof JSONObject) {
                    JSONObject jsonItem2 = (JSONObject) jsonItem;
                    if (newInstance instanceof VKApiModel) {
                        ((VKApiModel) newInstance).parse(jsonItem2);
                        Array.set(ret, i, (VKApiModel) newInstance);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | JSONException e) {
            }
        }
        return ret;
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
