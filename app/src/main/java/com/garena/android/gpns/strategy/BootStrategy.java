package com.garena.android.gpns.strategy;

import android.content.ComponentName;
import android.content.Context;

public abstract class BootStrategy {
    BootStrategyListener mListener = null;

    public interface BootStrategyListener {
        void onStrategyFailure(ComponentName componentName);

        void onStrategySuccess();
    }

    /* access modifiers changed from: protected */
    public abstract void fireStrategy(Context context);
}
