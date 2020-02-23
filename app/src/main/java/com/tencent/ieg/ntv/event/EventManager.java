package com.tencent.ieg.ntv.event;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EventManager {
    private static EventManager sInstance;
    private Map<Integer, Set<WeakReference<IEventListener>>> mListeners;

    private EventManager() {
    }

    public static EventManager getInstance() {
        if (sInstance == null) {
            sInstance = new EventManager();
            sInstance.init();
        }
        return sInstance;
    }

    private void init() {
        this.mListeners = new HashMap();
    }

    public static void destroyInstance() {
        if (sInstance != null) {
            sInstance.cleanUp();
            sInstance = null;
        }
    }

    private void cleanUp() {
        this.mListeners.clear();
        this.mListeners = null;
    }

    public void register(int eventId, IEventListener listener) {
        if (listener != null) {
            if (!this.mListeners.containsKey(Integer.valueOf(eventId))) {
                this.mListeners.put(Integer.valueOf(eventId), new HashSet<>());
            }
            Set<WeakReference<IEventListener>> listenerSet = this.mListeners.get(Integer.valueOf(eventId));
            if (!listenerSet.contains(listener)) {
                listenerSet.add(new WeakReference(listener));
            }
        }
    }

    public void unregister(int eventId, IEventListener listener) {
        if (listener != null && this.mListeners.containsKey(Integer.valueOf(eventId))) {
            Iterator<WeakReference<IEventListener>> it = this.mListeners.get(Integer.valueOf(eventId)).iterator();
            while (it.hasNext()) {
                if (it.next().get() == listener) {
                    it.remove();
                    return;
                }
            }
        }
    }

    public void post(int eventId, Event event) {
        if (this.mListeners.containsKey(Integer.valueOf(eventId))) {
            for (WeakReference<IEventListener> listenerRef : new HashSet<>(this.mListeners.get(Integer.valueOf(eventId)))) {
                if (listenerRef.get() != null) {
                    ((IEventListener) listenerRef.get()).onEvent(eventId, event);
                }
            }
        }
    }

    public static class TestEventManager {
        private static IEventListener sTestListener1 = new IEventListener() {
            public void onEvent(int eventId, Event event) {
                TestEventManager.log("sTestListener1 onEvent:" + eventId + ", " + ((TestEvent) event).msg);
            }
        };
        private static IEventListener sTestListener2 = new IEventListener() {
            public void onEvent(int eventId, Event event) {
                TestEventManager.log("sTestListener2 onEvent:" + eventId + ", " + ((TestEvent) event).msg);
                EventManager.getInstance().unregister(eventId, this);
            }
        };

        /* access modifiers changed from: private */
        public static void log(String msg) {
            System.out.println(msg);
        }

        public static void main(String[] args) {
            Test();
        }

        private static void Test() {
            EventManager.getInstance().register(1, sTestListener1);
            EventManager.getInstance().register(2, sTestListener2);
            IEventListener testListener = new IEventListener() {
                public void onEvent(int eventId, Event event) {
                    TestEventManager.log("testListener onEvent:" + eventId + ", " + ((TestEvent) event).msg);
                }
            };
            EventManager.getInstance().register(1, testListener);
            EventManager.getInstance().register(2, testListener);
            System.gc();
            EventManager.getInstance().post(1, new TestEvent("test 1"));
            EventManager.getInstance().post(2, new TestEvent("test 2"));
            EventManager.getInstance().post(1, new TestEvent("test 11"));
            EventManager.getInstance().post(2, new TestEvent("test 22"));
        }

        private static class TestEvent extends Event {
            public String msg;

            public TestEvent(String msg2) {
                this.msg = msg2;
            }
        }
    }
}
