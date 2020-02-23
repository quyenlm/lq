package com.tencent.qqgamemi.event.ue;

public class UnrealEngineEventManager {
    private static final String LOG_TAG = "UnrealEngineEvent";
    private UnrealEngineEventDispatcher mEventDispatcher;

    private UnrealEngineEventManager() {
        this.mEventDispatcher = new UnrealEngineEventDispatcher();
    }

    public static UnrealEngineEventManager getInstance() {
        return SingletonHolder.sInstance;
    }

    public void notify(int event, int msg) {
        if (this.mEventDispatcher != null) {
            this.mEventDispatcher.onNotify(event, msg);
        }
    }

    public void notify(int event, boolean msg) {
        if (this.mEventDispatcher != null) {
            this.mEventDispatcher.onNotify(event, msg);
        }
    }

    public void notify(int event, long msg) {
        if (this.mEventDispatcher != null) {
            this.mEventDispatcher.onNotify(event, msg);
        }
    }

    public void notify(int event, Object msg) {
        if (this.mEventDispatcher != null) {
            this.mEventDispatcher.onNotify(event, msg);
        }
    }

    private static class SingletonHolder {
        /* access modifiers changed from: private */
        public static UnrealEngineEventManager sInstance = new UnrealEngineEventManager();

        private SingletonHolder() {
        }
    }
}
