package MTT;

import java.io.Serializable;

public final class EFvrFvrType implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!EFvrFvrType.class.desiredAssertionStatus());
    public static final EFvrFvrType EFVRFVR_IMG = new EFvrFvrType(2, 2, "EFVRFVR_IMG");
    public static final EFvrFvrType EFVRFVR_MHT = new EFvrFvrType(7, 7, "EFVRFVR_MHT");
    public static final EFvrFvrType EFVRFVR_PAGE = new EFvrFvrType(1, 1, "EFVRFVR_PAGE");
    public static final EFvrFvrType EFVRFVR_RAWDATA = new EFvrFvrType(6, 6, "EFVRFVR_RAWDATA");
    public static final EFvrFvrType EFVRFVR_TEXT = new EFvrFvrType(3, 3, "EFVRFVR_TEXT");
    public static final EFvrFvrType EFVRFVR_TFETCH = new EFvrFvrType(5, 5, "EFVRFVR_TFETCH");
    public static final EFvrFvrType EFVRFVR_UNKNOW = new EFvrFvrType(0, 0, "EFVRFVR_UNKNOW");
    public static final EFvrFvrType EFVRFVR_VIDEO = new EFvrFvrType(4, 4, "EFVRFVR_VIDEO");
    public static final int _EFVRFVR_IMG = 2;
    public static final int _EFVRFVR_MHT = 7;
    public static final int _EFVRFVR_PAGE = 1;
    public static final int _EFVRFVR_RAWDATA = 6;
    public static final int _EFVRFVR_TEXT = 3;
    public static final int _EFVRFVR_TFETCH = 5;
    public static final int _EFVRFVR_UNKNOW = 0;
    public static final int _EFVRFVR_VIDEO = 4;
    private static EFvrFvrType[] __values = new EFvrFvrType[8];
    private String __T = new String();
    private int __value;

    public static EFvrFvrType convert(int val) {
        for (int __i = 0; __i < __values.length; __i++) {
            if (__values[__i].value() == val) {
                return __values[__i];
            }
        }
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public static EFvrFvrType convert(String val) {
        for (int __i = 0; __i < __values.length; __i++) {
            if (__values[__i].toString().equals(val)) {
                return __values[__i];
            }
        }
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public int value() {
        return this.__value;
    }

    public String toString() {
        return this.__T;
    }

    private EFvrFvrType(int index, int val, String s) {
        this.__T = s;
        this.__value = val;
        __values[index] = this;
    }
}
