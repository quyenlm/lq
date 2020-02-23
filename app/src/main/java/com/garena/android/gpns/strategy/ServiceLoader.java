package com.garena.android.gpns.strategy;

import android.content.ComponentName;
import android.content.Context;
import com.garena.android.gpns.strategy.BootStrategy;
import com.garena.android.gpns.utility.AppLogger;

public class ServiceLoader {
    private static Context mApplicationContext;
    /* access modifiers changed from: private */
    public ServiceStatusListener mListener;
    private BootStrategy.BootStrategyListener strategyListener = new BootStrategy.BootStrategyListener() {
        public void onStrategySuccess() {
            AppLogger.d("Strategy is successful. Service can be safely started");
            ServiceLoader.this.mListener.onServiceStarted();
        }

        public void onStrategyFailure(ComponentName name) {
            AppLogger.d("Strategy Unsuccessful, Should not start service. Its already running.");
            ServiceLoader.this.mListener.onOtherServiceRunning(name);
        }
    };

    public interface ServiceStatusListener {
        void onOtherServiceRunning(ComponentName componentName);

        void onServiceStarted();
    }

    public ServiceLoader(Context context, ServiceStatusListener listener) {
        mApplicationContext = context;
        this.mListener = listener;
    }

    public static Context getApplicationContext() {
        return mApplicationContext;
    }

    public void loadService() {
        BootStrategy strategy = new CompetitiveBootStrategy(this.strategyListener);
        AppLogger.d("fire strategy by " + mApplicationContext.getPackageName());
        strategy.fireStrategy(mApplicationContext);
    }

    public void setListener(ServiceStatusListener mListener2) {
        this.mListener = mListener2;
    }
}
