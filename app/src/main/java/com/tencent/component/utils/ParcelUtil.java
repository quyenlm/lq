package com.tencent.component.utils;

import android.os.Parcel;
import android.os.Parcelable;
import com.qq.taf.jce.JceStruct;
import com.tencent.component.utils.log.LogUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParcelUtil {
    private static final String TAG = "ParcelUtil";

    public static JceStruct readJceStruct(byte[] datas, ClassLoader classLoader) {
        if (datas != null) {
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(datas, 0, datas.length);
            parcel.setDataPosition(0);
            String name = parcel.readString();
            if (name != null) {
                if (classLoader == null) {
                    try {
                        classLoader = ParcelUtil.class.getClassLoader();
                    } catch (ClassNotFoundException e) {
                        LogUtil.e(TAG, e.getMessage(), e);
                        parcel.recycle();
                    } catch (Throwable th) {
                        parcel.recycle();
                        throw th;
                    }
                }
                Class<?> cls = Class.forName(name, true, classLoader);
                int length = parcel.readInt();
                if (length > 0) {
                    byte[] jceDatas = new byte[length];
                    parcel.readByteArray(jceDatas);
                    JceStruct decodeWup = JceTools.decodeWup(cls, jceDatas);
                    parcel.recycle();
                    return decodeWup;
                }
                parcel.recycle();
            } else {
                parcel.recycle();
            }
        }
        return null;
    }

    public static byte[] writeJceStruct(JceStruct struct) {
        Parcel dest = Parcel.obtain();
        dest.setDataPosition(0);
        writeJceStruct(dest, struct);
        byte[] datas = dest.marshall();
        dest.recycle();
        return datas;
    }

    public static void writeJceStruct(Parcel dest, JceStruct struct) {
        if (struct == null) {
            dest.writeString((String) null);
            return;
        }
        dest.writeString(struct.getClass().getName());
        byte[] jceDatas = JceTools.encodeWup(struct);
        if (jceDatas != null) {
            dest.writeInt(jceDatas.length);
            dest.writeByteArray(jceDatas);
            return;
        }
        dest.writeInt(0);
    }

    public static Parcelable readParcelable(byte[] datas, ClassLoader classLoader) {
        Parcelable parcelable = null;
        if (datas != null) {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.unmarshall(datas, 0, datas.length);
                parcel.setDataPosition(0);
                if (classLoader == null) {
                    classLoader = ParcelUtil.class.getClassLoader();
                }
                parcelable = parcel.readParcelable(classLoader);
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            } finally {
                parcel.recycle();
            }
        }
        return parcelable;
    }

    public static byte[] writeParcelable(Parcelable p) {
        byte[] datas = null;
        Parcel dest = Parcel.obtain();
        try {
            dest.setDataPosition(0);
            dest.writeParcelable(p, 0);
            datas = dest.marshall();
        } catch (Throwable e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } finally {
            dest.recycle();
        }
        return datas;
    }

    public static List readList(byte[] datas, ClassLoader classLoader) {
        ArrayList list = null;
        if (datas != null) {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.unmarshall(datas, 0, datas.length);
                parcel.setDataPosition(0);
                if (classLoader == null) {
                    classLoader = ParcelUtil.class.getClassLoader();
                }
                list = parcel.readArrayList(classLoader);
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            } finally {
                parcel.recycle();
            }
        }
        return list;
    }

    public static byte[] writeList(List list) {
        byte[] datas = null;
        Parcel dest = Parcel.obtain();
        try {
            dest.setDataPosition(0);
            dest.writeList(list);
            datas = dest.marshall();
        } catch (Throwable e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } finally {
            dest.recycle();
        }
        return datas;
    }

    public static Serializable readSerializable(byte[] datas) {
        Serializable val = null;
        if (datas != null) {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.unmarshall(datas, 0, datas.length);
                parcel.setDataPosition(0);
                val = parcel.readSerializable();
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            } finally {
                parcel.recycle();
            }
        }
        return val;
    }

    public static byte[] writeSerializable(Serializable obj) {
        byte[] datas = null;
        Parcel dest = Parcel.obtain();
        try {
            dest.setDataPosition(0);
            dest.writeSerializable(obj);
            datas = dest.marshall();
        } catch (Throwable e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } finally {
            dest.recycle();
        }
        return datas;
    }
}
