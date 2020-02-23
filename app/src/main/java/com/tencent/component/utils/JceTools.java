package com.tencent.component.utils;

import com.qq.taf.jce.JceDecodeException;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.tencent.tp.a.h;

class JceTools {
    private static final String ENCODE_CODE = "UTF-8";

    JceTools() {
    }

    public static byte[] encodeWup(JceStruct struct) {
        if (struct == null) {
            return null;
        }
        JceOutputStream os = null;
        try {
            JceOutputStream os2 = new JceOutputStream();
            try {
                os2.setServerEncoding("UTF-8");
                struct.writeTo(os2);
                os = os2;
            } catch (Exception e) {
                e = e;
                os = os2;
                e.printStackTrace();
                return os.toByteArray();
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return os.toByteArray();
        }
        return os.toByteArray();
    }

    public static <T extends JceStruct> T decodeWup(Class<T> clazz, byte[] datas) {
        if (!(clazz == null || datas == null || datas.length <= 0)) {
            try {
                T t = (JceStruct) clazz.newInstance();
                JceInputStream is = new JceInputStream(datas);
                is.setServerEncoding("UTF-8");
                t.readFrom(is);
                return t;
            } catch (IllegalAccessException | InstantiationException e) {
            } catch (JceDecodeException e2) {
                e2.printStackTrace();
                throw new RuntimeException("decode wup failed(class:" + clazz.getName() + h.b, e2);
            }
        }
        return null;
    }
}
