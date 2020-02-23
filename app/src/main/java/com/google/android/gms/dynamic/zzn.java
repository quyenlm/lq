package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.lang.reflect.Field;

public final class zzn<T> extends IObjectWrapper.zza {
    private final T mWrappedObject;

    private zzn(T t) {
        this.mWrappedObject = t;
    }

    public static <T> T zzE(IObjectWrapper iObjectWrapper) {
        if (iObjectWrapper instanceof zzn) {
            return ((zzn) iObjectWrapper).mWrappedObject;
        }
        IBinder asBinder = iObjectWrapper.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        Field field = null;
        int length = declaredFields.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Field field2 = declaredFields[i];
            if (!field2.isSynthetic()) {
                i2++;
            } else {
                field2 = field;
            }
            i++;
            field = field2;
        }
        if (i2 != 1) {
            throw new IllegalArgumentException(new StringBuilder(64).append("Unexpected number of IObjectWrapper declared fields: ").append(declaredFields.length).toString());
        } else if (!field.isAccessible()) {
            field.setAccessible(true);
            try {
                return field.get(asBinder);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Binder object is null.", e);
            } catch (IllegalAccessException e2) {
                throw new IllegalArgumentException("Could not access the field in remoteBinder.", e2);
            }
        } else {
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
    }

    public static <T> IObjectWrapper zzw(T t) {
        return new zzn(t);
    }
}
