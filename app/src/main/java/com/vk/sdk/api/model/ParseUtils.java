package com.vk.sdk.api.model;

import android.os.Parcelable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseUtils {
    private ParseUtils() {
    }

    public static boolean parseBoolean(String from) throws JSONException {
        return new JSONObject(from).optInt("response", 0) == 1;
    }

    public static boolean parseBoolean(JSONObject from, String name) {
        return from != null && from.optInt(name, 0) == 1;
    }

    public static int parseInt(JSONObject from, String name) {
        if (from == null) {
            return 0;
        }
        return from.optInt(name, 0);
    }

    public static int parseInt(String from) throws JSONException {
        if (from == null) {
            return 0;
        }
        return new JSONObject(from).optInt("response");
    }

    public static long parseLong(JSONObject from, String name) {
        if (from == null) {
            return 0;
        }
        return from.optLong(name, 0);
    }

    public static int[] parseIntArray(JSONArray from) {
        int[] result = new int[from.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = from.optInt(i);
        }
        return result;
    }

    public static JSONObject rootJSONObject(String source) throws JSONException {
        return new JSONObject(source).getJSONObject("response");
    }

    public static JSONArray rootJSONArray(String source) throws JSONException {
        return new JSONObject(source).getJSONArray("response");
    }

    public static <T> T parseViaReflection(T object, JSONObject source) throws JSONException {
        boolean z;
        if (source.has("response")) {
            source = source.optJSONObject("response");
        }
        if (source != null) {
            Field[] fields = object.getClass().getFields();
            int length = fields.length;
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= length) {
                    break;
                }
                Field field = fields[i2];
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                Object value = source.opt(fieldName);
                if (value != null) {
                    try {
                        if (!fieldType.isPrimitive() || !(value instanceof Number)) {
                            Object result = field.get(object);
                            if (value.getClass().equals(fieldType)) {
                                result = value;
                            } else if (fieldType.isArray() && (value instanceof JSONArray)) {
                                result = parseArrayViaReflection((JSONArray) value, fieldType);
                            } else if (VKPhotoSizes.class.isAssignableFrom(fieldType) && (value instanceof JSONArray)) {
                                result = fieldType.getConstructor(new Class[]{JSONArray.class}).newInstance(new Object[]{(JSONArray) value});
                            } else if (VKAttachments.class.isAssignableFrom(fieldType) && (value instanceof JSONArray)) {
                                result = fieldType.getConstructor(new Class[]{JSONArray.class}).newInstance(new Object[]{(JSONArray) value});
                            } else if (VKList.class.equals(fieldType)) {
                                Class<?> genericType = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                                if (VKApiModel.class.isAssignableFrom(genericType) && Parcelable.class.isAssignableFrom(genericType) && Identifiable.class.isAssignableFrom(genericType)) {
                                    if (value instanceof JSONArray) {
                                        result = new VKList((JSONArray) value, genericType);
                                    } else if (value instanceof JSONObject) {
                                        result = new VKList((JSONObject) value, genericType);
                                    }
                                }
                            } else if (VKApiModel.class.isAssignableFrom(fieldType) && (value instanceof JSONObject)) {
                                result = ((VKApiModel) fieldType.newInstance()).parse((JSONObject) value);
                            }
                            field.set(object, result);
                        } else {
                            Number number = (Number) value;
                            if (fieldType.equals(Integer.TYPE)) {
                                field.setInt(object, number.intValue());
                            } else if (fieldType.equals(Long.TYPE)) {
                                field.setLong(object, number.longValue());
                            } else if (fieldType.equals(Float.TYPE)) {
                                field.setFloat(object, number.floatValue());
                            } else if (fieldType.equals(Double.TYPE)) {
                                field.setDouble(object, number.doubleValue());
                            } else if (fieldType.equals(Boolean.TYPE)) {
                                if (number.intValue() == 1) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                field.setBoolean(object, z);
                            } else if (fieldType.equals(Short.TYPE)) {
                                field.setShort(object, number.shortValue());
                            } else if (fieldType.equals(Byte.TYPE)) {
                                field.setByte(object, number.byteValue());
                            }
                        }
                    } catch (InstantiationException e) {
                        throw new JSONException(e.getMessage());
                    } catch (IllegalAccessException e2) {
                        throw new JSONException(e2.getMessage());
                    } catch (NoSuchMethodException e3) {
                        throw new JSONException(e3.getMessage());
                    } catch (InvocationTargetException e4) {
                        throw new JSONException(e4.getMessage());
                    } catch (NoSuchMethodError e5) {
                        throw new JSONException(e5.getMessage());
                    }
                }
                i = i2 + 1;
            }
        }
        return object;
    }

    private static Object parseArrayViaReflection(JSONArray array, Class arrayClass) throws JSONException {
        Object result = Array.newInstance(arrayClass.getComponentType(), array.length());
        Class<?> subType = arrayClass.getComponentType();
        int i = 0;
        while (i < array.length()) {
            try {
                Object item = array.opt(i);
                if (VKApiModel.class.isAssignableFrom(subType) && (item instanceof JSONObject)) {
                    item = ((VKApiModel) subType.newInstance()).parse((JSONObject) item);
                }
                Array.set(result, i, item);
                i++;
            } catch (InstantiationException e) {
                throw new JSONException(e.getMessage());
            } catch (IllegalAccessException e2) {
                throw new JSONException(e2.getMessage());
            } catch (IllegalArgumentException e3) {
                throw new JSONException(e3.getMessage());
            }
        }
        return result;
    }
}
