package com.facebook.internal;

import java.util.EnumSet;
import java.util.Iterator;

public enum SmartLoginOption {
    None(0),
    Enabled(1),
    RequireConfirm(2);
    
    public static final EnumSet<SmartLoginOption> ALL = null;
    private final long mValue;

    static {
        ALL = EnumSet.allOf(SmartLoginOption.class);
    }

    public static EnumSet<SmartLoginOption> parseOptions(long bitmask) {
        EnumSet<SmartLoginOption> result = EnumSet.noneOf(SmartLoginOption.class);
        Iterator it = ALL.iterator();
        while (it.hasNext()) {
            SmartLoginOption opt = (SmartLoginOption) it.next();
            if ((opt.getValue() & bitmask) != 0) {
                result.add(opt);
            }
        }
        return result;
    }

    private SmartLoginOption(long value) {
        this.mValue = value;
    }

    public long getValue() {
        return this.mValue;
    }
}
