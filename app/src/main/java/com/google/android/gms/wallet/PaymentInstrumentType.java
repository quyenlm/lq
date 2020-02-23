package com.google.android.gms.wallet;

import java.util.ArrayList;
import java.util.Arrays;

public final class PaymentInstrumentType {
    public static final int AMEX = 3;
    public static final int DISCOVER = 4;
    public static final int JCB = 5;
    public static final int MASTER_CARD = 2;
    public static final int VISA = 1;

    public static ArrayList<Integer> getAll() {
        return new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
    }
}
