package com.tencent.component.event;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.event.Event;
import com.tencent.component.utils.collections.MultiSparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@PluginApi(since = 6)
public class EventCenter {
    private static final boolean DEFAULT_INVOKE_IN_UITHREAD = false;
    private static final EventCenter sInstance = new EventCenter();
    private List<EventInterceptor> mEventInterceptors = new CopyOnWriteArrayList();
    private ReadWriteLock mLock = new ReentrantReadWriteLock();
    private HashMap<EventSource, MultiSparseArray<ObserverBean>> mObserverMap = new HashMap<>();
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    private EventCenter() {
    }

    @PluginApi(since = 6)
    public static EventCenter getInstance() {
        return sInstance;
    }

    @PluginApi(since = 6)
    public void addObserver(Observer observingObject, String eventSourceName, int... whats) {
        addObserver(observingObject, (String) null, new EventSource(eventSourceName), false, whats);
    }

    public void addObserver(Observer observer, EventSource source, int... whats) {
        addObserver(observer, (String) null, source, false, whats);
    }

    @PluginApi(since = 6)
    public void addUIObserver(Observer observingObject, String eventSourceName, int... whats) {
        addObserver(observingObject, (String) null, new EventSource(eventSourceName), true, whats);
    }

    public void addUIObserver(Observer observer, EventSource source, int... whats) {
        addObserver(observer, (String) null, source, true, whats);
    }

    @PluginApi(since = 6)
    public void addObserver(Observer observingObject, String invocationMethod, EventSource source, boolean invokeInUIThread, int... whats) {
        if (observingObject == null) {
            throw new NullPointerException("observingObject can't be null!");
        } else if (source == null || TextUtils.isEmpty(source.name)) {
            throw new NullPointerException("you must specified eventSource!");
        } else if (whats != null) {
            if (invocationMethod == null) {
                invocationMethod = ObserverBean.DEFAULT_INVOKE_METHOD_NAME;
            }
            Lock writeLock = this.mLock.writeLock();
            try {
                writeLock.lock();
                ObserverBean observerBean = new ObserverBean(observingObject, source.sender, invocationMethod, invokeInUIThread);
                MultiSparseArray<ObserverBean> om = this.mObserverMap.get(source);
                if (om == null) {
                    om = new MultiSparseArray<>();
                    this.mObserverMap.put(source, om);
                }
                for (int what : whats) {
                    clearWeakObserver(om, what);
                    om.put(what, observerBean);
                }
            } finally {
                writeLock.unlock();
            }
        }
    }

    private void clearWeakObserver(MultiSparseArray<ObserverBean> om, int key) {
        List<ObserverBean> observerBeans;
        if (om != null && om.keySize() > 0 && (observerBeans = om.get(key)) != null && observerBeans.size() > 0) {
            Iterator<ObserverBean> it = observerBeans.iterator();
            while (it.hasNext()) {
                ObserverBean observerBean = it.next();
                if (observerBean != null && observerBean.getObservingObject() == null) {
                    it.remove();
                }
            }
        }
    }

    @PluginApi(since = 6)
    public void removeObserver(Object observingObject) {
        removeObserver(observingObject, (EventSource) null);
    }

    @PluginApi(since = 6)
    public void removeObserver(Object observingObject, EventSource source) {
        if (observingObject == null) {
            throw new NullPointerException("observingObject cannot be null");
        }
        Lock writeLock = this.mLock.writeLock();
        try {
            writeLock.lock();
            if (source != null) {
                removeAllObserverByEventSource(observingObject, source);
            } else {
                Collection<EventSource> collections = this.mObserverMap.keySet();
                if (collections != null) {
                    for (EventSource es : collections) {
                        removeAllObserverByEventSource(observingObject, es);
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @PluginApi(since = 6)
    public void removeObserver(Object observingObject, EventSource source, int... whats) {
        if (observingObject == null) {
            throw new NullPointerException("observingObject must not be null");
        } else if (whats != null) {
            Lock writeLock = this.mLock.writeLock();
            try {
                writeLock.lock();
                if (source != null) {
                    for (int what : whats) {
                        removeObserverByEventSource(observingObject, source, what);
                    }
                } else {
                    Collection<EventSource> collections = this.mObserverMap.keySet();
                    if (collections != null) {
                        for (EventSource es : collections) {
                            for (int what2 : whats) {
                                removeObserverByEventSource(observingObject, es, what2);
                            }
                        }
                    }
                }
            } finally {
                writeLock.unlock();
            }
        }
    }

    private void removeAllObserverByEventSource(Object observingObject, EventSource source) {
        MultiSparseArray<ObserverBean> om = this.mObserverMap.get(source);
        if (om != null) {
            int keySize = om.keySize();
            for (int i = 0; i < keySize; i++) {
                removeObserverFromCollection(om.get(om.keyAt(i)), observingObject);
            }
        }
    }

    private void removeObserverByEventSource(Object observingObject, EventSource source, int what) {
        MultiSparseArray<ObserverBean> om = this.mObserverMap.get(source);
        if (om != null) {
            removeObserverFromCollection(om.get(what), observingObject);
        }
    }

    private void removeObserverFromCollection(Collection<ObserverBean> observers, Object observingObject) {
        Object tmpObject;
        if (observers != null) {
            Iterator<ObserverBean> iterator = observers.iterator();
            while (iterator.hasNext()) {
                ObserverBean observer = iterator.next();
                if (!(observer == null || (tmpObject = observer.getObservingObject()) == null || !tmpObject.equals(observingObject))) {
                    iterator.remove();
                }
            }
        }
    }

    private ArrayList<EventInterceptor> getEventInterceptors() {
        ArrayList<EventInterceptor> interceptors = new ArrayList<>();
        for (EventInterceptor interceptor : this.mEventInterceptors) {
            if (interceptor != null) {
                interceptors.add(interceptor);
            }
        }
        return interceptors;
    }

    private Collection<ObserverBean> getObserverBeans(Event event) {
        MultiSparseArray<ObserverBean> om = this.mObserverMap.get(event.source);
        if (om != null) {
            clearWeakObserver(om, event.what);
            Collection<ObserverBean> observers = om.get(event.what);
            if (observers != null) {
                return new ArrayList(observers);
            }
        }
        return null;
    }

    @PluginApi(since = 6)
    public void notify(EventSource source, int what, Event.EventRank eventRank, Object... parameters) {
        if (eventRank == null) {
            eventRank = Event.EventRank.NORMAL;
        }
        notify(Event.obtain(what, source, parameters, eventRank));
    }

    @PluginApi(since = 6)
    public void notify(Event event) {
        if (event == null) {
            throw new NullPointerException("Event cannot be null");
        }
        EventSource source = event.source;
        if (source == null || TextUtils.isEmpty(source.name)) {
            throw new NullPointerException("EventSource cannot be null");
        }
        Lock readLock = this.mLock.readLock();
        try {
            readLock.lock();
            ArrayList<EventInterceptor> interceptors = getEventInterceptors();
            Collection<ObserverBean> observerBeans = getObserverBeans(event);
            if (interceptors != null) {
                Iterator<EventInterceptor> it = interceptors.iterator();
                while (it.hasNext()) {
                    EventInterceptor interceptor = it.next();
                    if (interceptor != null && interceptor.intercept(event)) {
                        return;
                    }
                }
            }
            if (observerBeans != null) {
                for (ObserverBean observer : observerBeans) {
                    notifyObserver(observer, event);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    private void notifyObserver(final ObserverBean observerBean, final Event event) {
        if (observerBean != null) {
            Object specifiedSender = observerBean.getEventSourceSender();
            if (specifiedSender != null && specifiedSender != event.source.sender) {
                return;
            }
            if (!observerBean.mInvokeInUIThread) {
                observerBean.invoke(event);
            } else if (Looper.myLooper() == Looper.getMainLooper()) {
                observerBean.invoke(event);
            } else {
                this.mUIHandler.post(new Runnable() {
                    public void run() {
                        observerBean.invoke(event);
                    }
                });
            }
        }
    }

    @PluginApi(since = 6)
    public void addEventInterceptor(EventInterceptor interceptor) {
        if (interceptor == null) {
            throw new NullPointerException("interceptor cannot be null");
        }
        Lock writeLock = this.mLock.writeLock();
        try {
            writeLock.lock();
            this.mEventInterceptors.add(interceptor);
        } finally {
            writeLock.unlock();
        }
    }

    @PluginApi(since = 6)
    public void removeEventInterceptor(EventInterceptor interceptor) {
        Lock writeLock = this.mLock.writeLock();
        try {
            writeLock.lock();
            this.mEventInterceptors.remove(interceptor);
        } finally {
            writeLock.unlock();
        }
    }
}
