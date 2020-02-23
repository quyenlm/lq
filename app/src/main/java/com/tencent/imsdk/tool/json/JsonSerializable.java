package com.tencent.imsdk.tool.json;

import com.tencent.imsdk.tool.etc.IMLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSerializable {
    public JSONObject rawObject = null;

    public JSONObject toUnityJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        Iterator<Field> it = JsonInfo.getFields((Object) this).iterator();
        while (it.hasNext()) {
            Field f = it.next();
            try {
                Object o = f.get(this);
                if (o != null) {
                    if (o instanceof JsonSerializable) {
                        obj.put(f.getName(), ((JsonSerializable) o).toUnityJSONObject());
                    } else if (o instanceof List) {
                        JSONArray ary = new JSONArray();
                        for (Object item : (List) o) {
                            if (JsonSerializable.class.isAssignableFrom(item.getClass())) {
                                ary.put(((JsonSerializable) item).toUnityJSONObject());
                            } else {
                                ary.put(item);
                            }
                        }
                        obj.put(f.getName(), ary);
                    } else {
                        obj.put(f.getName(), o);
                    }
                }
            } catch (IllegalAccessException e) {
                IMLogger.e(e.getMessage());
            }
        }
        return obj;
    }

    public String toUnityString() throws JSONException {
        return toUnityJSONObject().toString();
    }

    public String toJSONString() throws JSONException {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        Iterator<Field> it = JsonInfo.getFields((Object) this).iterator();
        while (it.hasNext()) {
            Field f = it.next();
            JsonProp a = (JsonProp) f.getAnnotation(JsonProp.class);
            if (a != null) {
                JsonList l = (JsonList) f.getAnnotation(JsonList.class);
                try {
                    Object o = f.get(this);
                    if (o != null) {
                        if (l != null) {
                            JSONArray ary = new JSONArray();
                            if (o instanceof List) {
                                for (Object item : (List) o) {
                                    ary.put(item);
                                }
                                obj.put(a.name(), ary);
                            }
                        } else if (!JsonSerializable.class.isAssignableFrom(f.getType())) {
                            obj.put(a.name(), o);
                        } else if (o instanceof JsonSerializable) {
                            obj.put(a.name(), ((JsonSerializable) o).toJSONObject());
                        }
                    }
                } catch (IllegalAccessException e) {
                    IMLogger.e("parse to json string error : " + e.getMessage());
                }
            }
        }
        return obj;
    }

    public JsonSerializable(JSONObject json) throws JSONException {
        fillWithJSON(json);
    }

    public JsonSerializable(String json) throws JSONException {
        fillWithJSON(new JSONObject(json));
    }

    public JsonSerializable() {
    }

    private Object getDefaultValue(Field field) {
        Class ft = field.getType();
        if (Integer.TYPE.isAssignableFrom(ft)) {
            if (field.isAnnotationPresent(JsonInt.class)) {
                return Integer.valueOf(((JsonInt) field.getAnnotation(JsonInt.class)).def());
            }
            return null;
        } else if (Long.TYPE.isAssignableFrom(ft)) {
            if (field.isAnnotationPresent(JsonLong.class)) {
                return Long.valueOf(((JsonLong) field.getAnnotation(JsonLong.class)).def());
            }
            return null;
        } else if (String.class.isAssignableFrom(ft)) {
            if (field.isAnnotationPresent(JsonString.class)) {
                return ((JsonString) field.getAnnotation(JsonString.class)).def();
            }
            return null;
        } else if (Boolean.TYPE.isAssignableFrom(ft)) {
            if (field.isAnnotationPresent(JsonBoolean.class)) {
                return Boolean.valueOf(((JsonBoolean) field.getAnnotation(JsonBoolean.class)).def());
            }
            return null;
        } else if (Short.TYPE.isAssignableFrom(JsonShort.class)) {
            if (field.isAnnotationPresent(JsonShort.class)) {
                return Short.valueOf(((JsonShort) field.getAnnotation(JsonShort.class)).def());
            }
            return null;
        } else if (Float.TYPE.isAssignableFrom(JsonFloat.class)) {
            if (field.isAnnotationPresent(JsonFloat.class)) {
                return Float.valueOf(((JsonFloat) field.getAnnotation(JsonFloat.class)).def());
            }
            return null;
        } else if (!Double.TYPE.isAssignableFrom(JsonDouble.class) || !field.isAnnotationPresent(JsonDouble.class)) {
            return null;
        } else {
            return Double.valueOf(((JsonDouble) field.getAnnotation(JsonDouble.class)).def());
        }
    }

    private void fillWithJSON(JSONObject json) throws JSONException {
        Iterator<Field> it = JsonInfo.getFields((Object) this).iterator();
        while (it.hasNext()) {
            Field f = it.next();
            if (f.isAnnotationPresent(JsonProp.class)) {
                JsonProp a = (JsonProp) f.getAnnotation(JsonProp.class);
                JsonList l = (JsonList) f.getAnnotation(JsonList.class);
                try {
                    Object o = json.opt(a.name());
                    if (o != null || (o = getDefaultValue(f)) != null) {
                        if (l == null) {
                            if (!JsonSerializable.class.isAssignableFrom(f.getType())) {
                                f.set(this, o);
                            } else if (o instanceof JSONObject) {
                                f.set(this, constructWithJSON(f.getType(), (JSONObject) o));
                            }
                        } else if (o instanceof JSONArray) {
                            JSONArray list = (JSONArray) o;
                            Object field = f.get(this);
                            if (f.getType() == List.class && field == null) {
                                field = new LinkedList();
                            }
                            List fieldList = (List) field;
                            for (int i = 0; i < list.length(); i++) {
                                Object midObj = list.get(i);
                                Class midCls = Class.forName(l.type());
                                if (JsonSerializable.class.isAssignableFrom(midCls)) {
                                    fieldList.add(constructWithJSON(midCls, (JSONObject) midObj));
                                } else {
                                    fieldList.add(list.get(i));
                                }
                            }
                            f.set(this, fieldList);
                        }
                    }
                } catch (IllegalAccessException e) {
                    IMLogger.e(e.getMessage());
                } catch (IllegalArgumentException e2) {
                    IMLogger.e(e2.getMessage());
                } catch (NullPointerException e3) {
                    IMLogger.e(e3.getMessage());
                } catch (ExceptionInInitializerError e4) {
                    IMLogger.e(e4.getMessage());
                } catch (Exception e5) {
                    IMLogger.e(e5.getMessage());
                }
            }
        }
        this.rawObject = json;
    }

    private Object constructWithJSON(Class type, JSONObject object) throws JSONException {
        try {
            Constructor constructor = type.getDeclaredConstructor(new Class[]{JSONObject.class});
            if (constructor == null) {
                return null;
            }
            return constructor.newInstance(new Object[]{object});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return null;
        }
    }
}
