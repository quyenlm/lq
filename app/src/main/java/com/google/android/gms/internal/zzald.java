package com.google.android.gms.internal;

import android.webkit.ConsoleMessage;

final /* synthetic */ class zzald {
    static final /* synthetic */ int[] zzacK = new int[ConsoleMessage.MessageLevel.values().length];

    static {
        try {
            zzacK[ConsoleMessage.MessageLevel.ERROR.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            zzacK[ConsoleMessage.MessageLevel.WARNING.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            zzacK[ConsoleMessage.MessageLevel.LOG.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            zzacK[ConsoleMessage.MessageLevel.TIP.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            zzacK[ConsoleMessage.MessageLevel.DEBUG.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
    }
}
