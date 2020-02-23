package MTT;

import java.io.Serializable;

public final class ELoginTriggerType implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!ELoginTriggerType.class.desiredAssertionStatus());
    public static final ELoginTriggerType EQQ_PLUGIN_TRIGGER = new ELoginTriggerType(2, 2, "EQQ_PLUGIN_TRIGGER");
    public static final ELoginTriggerType EQZONE_PLUGIN_TRIGGER = new ELoginTriggerType(3, 3, "EQZONE_PLUGIN_TRIGGER");
    public static final ELoginTriggerType ESREVICE_LOGIN_TRIGGER = new ELoginTriggerType(1, 1, "ESREVICE_LOGIN_TRIGGER");
    public static final ELoginTriggerType EUSER_LOGIN_TRIGGER = new ELoginTriggerType(0, 0, "EUSER_LOGIN_TRIGGER");
    public static final ELoginTriggerType EWECHAT_PLUGIN_TRIGGER = new ELoginTriggerType(5, 5, "EWECHAT_PLUGIN_TRIGGER");
    public static final ELoginTriggerType EWEIBO_PLUGIN_TRIGGER = new ELoginTriggerType(4, 4, "EWEIBO_PLUGIN_TRIGGER");
    public static final int _EQQ_PLUGIN_TRIGGER = 2;
    public static final int _EQZONE_PLUGIN_TRIGGER = 3;
    public static final int _ESREVICE_LOGIN_TRIGGER = 1;
    public static final int _EUSER_LOGIN_TRIGGER = 0;
    public static final int _EWECHAT_PLUGIN_TRIGGER = 5;
    public static final int _EWEIBO_PLUGIN_TRIGGER = 4;
    private static ELoginTriggerType[] __values = new ELoginTriggerType[6];
    private String __T = new String();
    private int __value;

    public static ELoginTriggerType convert(int val) {
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

    public static ELoginTriggerType convert(String val) {
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

    private ELoginTriggerType(int index, int val, String s) {
        this.__T = s;
        this.__value = val;
        __values[index] = this;
    }
}
