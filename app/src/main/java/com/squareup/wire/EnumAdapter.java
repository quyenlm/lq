package com.squareup.wire;

import com.squareup.wire.ProtoEnum;
import java.util.Arrays;
import java.util.Comparator;

final class EnumAdapter<E extends ProtoEnum> {
    private static final Comparator<ProtoEnum> COMPARATOR = new Comparator<ProtoEnum>() {
        public int compare(ProtoEnum o1, ProtoEnum o2) {
            return o1.getValue() - o2.getValue();
        }
    };
    private final E[] constants;
    private final boolean isDense;
    private final Class<E> type;
    private final int[] values;

    EnumAdapter(Class<E> type2) {
        this.type = type2;
        this.constants = (ProtoEnum[]) type2.getEnumConstants();
        Arrays.sort(this.constants, COMPARATOR);
        int length = this.constants.length;
        if (this.constants[0].getValue() == 1 && this.constants[length - 1].getValue() == length) {
            this.isDense = true;
            this.values = null;
            return;
        }
        this.isDense = false;
        this.values = new int[length];
        for (int i = 0; i < length; i++) {
            this.values[i] = this.constants[i].getValue();
        }
    }

    public int toInt(E e) {
        return e.getValue();
    }

    public E fromInt(int value) {
        try {
            return this.constants[this.isDense ? value - 1 : Arrays.binarySearch(this.values, value)];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum tag " + value + " for " + this.type.getCanonicalName());
        }
    }
}
