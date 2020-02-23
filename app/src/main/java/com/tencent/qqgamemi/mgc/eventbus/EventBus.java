package com.tencent.qqgamemi.mgc.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final String TAG = "NotificationCenter";
    private final Object listenerLock;
    private Handler mHandler;
    private Map subscribersByClass;
    private Map subscribersByTopic;

    private EventBus() {
        this.subscribersByTopic = new HashMap();
        this.subscribersByClass = new HashMap();
        this.listenerLock = new Object();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static EventBus getInstance() {
        return CenterInstance._instanceCenter;
    }

    public void setLooper(Looper looper) {
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        if (this.mHandler.getLooper() != looper) {
            this.mHandler = new Handler(looper);
        }
    }

    public void clearAllSubscribers() {
        synchronized (this.listenerLock) {
            unsubscribeAllInMap(this.subscribersByTopic);
            unsubscribeAllInMap(this.subscribersByClass);
        }
    }

    private void unsubscribeAllInMap(Map subscriberMap) {
        synchronized (this.listenerLock) {
            for (Object key : subscriberMap.keySet()) {
                List subscribers = (List) subscriberMap.get(key);
                while (!subscribers.isEmpty()) {
                    unsubscribe(key, subscriberMap, subscribers.get(0));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0015, code lost:
        r0 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getRealSubscriberAndCleanStaleSubscriberIfNecessary(java.util.Iterator r3, java.lang.Object r4) {
        /*
            r2 = this;
            r0 = 0
            boolean r1 = r4 instanceof java.lang.ref.WeakReference
            if (r1 == 0) goto L_0x0011
            java.lang.ref.WeakReference r4 = (java.lang.ref.WeakReference) r4
            java.lang.Object r4 = r4.get()
            if (r4 != 0) goto L_0x0010
            r3.remove()
        L_0x0010:
            return r4
        L_0x0011:
            boolean r1 = r4 instanceof com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber
            if (r1 == 0) goto L_0x0010
            r0 = r4
            com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber r0 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r0
            java.lang.Object r4 = r0.getProxiedSubscriber()
            if (r4 == 0) goto L_0x0010
            r2.removeProxySubscriber(r0, r3)
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.mgc.eventbus.EventBus.getRealSubscriberAndCleanStaleSubscriberIfNecessary(java.util.Iterator, java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public void removeProxySubscriber(ProxySubscriber proxy, Iterator iter) {
        iter.remove();
        proxy.proxyUnsubscribed();
    }

    /* access modifiers changed from: protected */
    public boolean subscribe(Object classTopicOrPatternWrapper, Map<Object, Object> subscriberMap, Object subscriber) {
        boolean z;
        if (classTopicOrPatternWrapper == null) {
            throw new IllegalArgumentException("Can't subscribe to null.");
        } else if (subscriber == null) {
            throw new IllegalArgumentException("Can't subscribe null subscriber to " + classTopicOrPatternWrapper);
        } else {
            boolean alreadyExists = false;
            Object realSubscriber = subscriber;
            boolean isWeakRef = subscriber instanceof WeakReference;
            if (isWeakRef) {
                realSubscriber = ((WeakReference) subscriber).get();
            }
            boolean isWeakProxySubscriber = false;
            if (subscriber instanceof ProxySubscriber) {
                isWeakProxySubscriber = ((ProxySubscriber) subscriber).getReferenceStrength() == ReferenceStrength.WEAK;
                if (isWeakProxySubscriber) {
                    realSubscriber = ((ProxySubscriber) subscriber).getProxiedSubscriber();
                }
            }
            if (isWeakRef && isWeakProxySubscriber) {
                throw new IllegalArgumentException("ProxySubscribers should always be subscribed strongly.");
            } else if (realSubscriber == null) {
                return false;
            } else {
                synchronized (this.listenerLock) {
                    List currentSubscribers = (List) subscriberMap.get(classTopicOrPatternWrapper);
                    if (currentSubscribers == null) {
                        LogUtil.d(TAG, "Creating new subscriber map for:" + classTopicOrPatternWrapper);
                        currentSubscribers = new ArrayList();
                        subscriberMap.put(classTopicOrPatternWrapper, currentSubscribers);
                    } else {
                        Iterator iterator = currentSubscribers.iterator();
                        while (iterator.hasNext()) {
                            if (realSubscriber.equals(getRealSubscriberAndCleanStaleSubscriberIfNecessary(iterator, iterator.next()))) {
                                iterator.remove();
                                alreadyExists = true;
                            }
                        }
                    }
                    currentSubscribers.add(realSubscriber);
                    z = !alreadyExists;
                }
                return z;
            }
        }
    }

    public boolean subscriber(Class eventClass, Subscriber subscriber) {
        if (eventClass == null) {
            throw new IllegalArgumentException("Event class must not be null");
        } else if (subscriber == null) {
            throw new IllegalArgumentException("Event subscriber must not be null");
        } else {
            LogUtil.v(TAG, "Subscribing by class, class:" + eventClass + ", subscriber:" + subscriber);
            return subscribe(eventClass, this.subscribersByClass, new WeakReference(subscriber));
        }
    }

    public boolean subscribeStrongly(Class cl, Subscriber eh) {
        if (eh != null) {
            return subscribe(cl, this.subscribersByClass, eh);
        }
        throw new IllegalArgumentException("Subscriber cannot be null.");
    }

    public boolean subscriber(String topic, TopicSubscriber subscriber) {
        if (TextUtils.isEmpty(topic)) {
            throw new IllegalArgumentException("Topic must not be null or empty");
        } else if (subscriber == null) {
            throw new IllegalArgumentException("Event subscriber must not be null");
        } else {
            LogUtil.v(TAG, "Subscribing by topic, topic:" + topic + ", subscriber:" + subscriber);
            return subscribe(topic, this.subscribersByTopic, new WeakReference(subscriber));
        }
    }

    /* access modifiers changed from: protected */
    public boolean unsubscribe(Object o, Map subscriberMap, Object subscriber) {
        boolean removeFromSetResolveWeakReferences;
        LogUtil.v(TAG, "unsubscribe(" + o + "," + subscriber + h.b);
        if (o == null) {
            throw new IllegalArgumentException("Can't unsubscribe to null.");
        } else if (subscriber == null) {
            throw new IllegalArgumentException("Can't unsubscribe null subscriber to " + o);
        } else {
            synchronized (this.listenerLock) {
                removeFromSetResolveWeakReferences = removeFromSetResolveWeakReferences(subscriberMap, o, subscriber);
            }
            return removeFromSetResolveWeakReferences;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        r2 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean removeFromSetResolveWeakReferences(java.util.Map r10, java.lang.Object r11, java.lang.Object r12) {
        /*
            r9 = this;
            r6 = 0
            r7 = 1
            java.lang.Object r4 = r10.get(r11)
            java.util.List r4 = (java.util.List) r4
            if (r4 != 0) goto L_0x000b
        L_0x000a:
            return r6
        L_0x000b:
            boolean r8 = r4.remove(r12)
            if (r8 == 0) goto L_0x0020
            boolean r6 = r12 instanceof java.lang.ref.WeakReference
            if (r6 == 0) goto L_0x0015
        L_0x0015:
            boolean r6 = r12 instanceof com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber
            if (r6 == 0) goto L_0x001e
            com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber r12 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r12
            r12.proxyUnsubscribed()
        L_0x001e:
            r6 = r7
            goto L_0x000a
        L_0x0020:
            java.util.Iterator r1 = r4.iterator()
        L_0x0024:
            boolean r8 = r1.hasNext()
            if (r8 == 0) goto L_0x000a
            java.lang.Object r0 = r1.next()
            boolean r8 = r0 instanceof com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber
            if (r8 == 0) goto L_0x0040
            r2 = r0
            com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber r2 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r2
            java.lang.Object r0 = r2.getProxiedSubscriber()
            if (r0 != r12) goto L_0x0040
            r9.removeProxySubscriber(r2, r1)
            r6 = r7
            goto L_0x000a
        L_0x0040:
            boolean r8 = r0 instanceof java.lang.ref.WeakReference
            if (r8 == 0) goto L_0x0024
            r5 = r0
            java.lang.ref.WeakReference r5 = (java.lang.ref.WeakReference) r5
            java.lang.Object r3 = r5.get()
            if (r3 != 0) goto L_0x0052
            r1.remove()
            r6 = r7
            goto L_0x000a
        L_0x0052:
            if (r3 != r12) goto L_0x0059
            r1.remove()
            r6 = r7
            goto L_0x000a
        L_0x0059:
            boolean r8 = r3 instanceof com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber
            if (r8 == 0) goto L_0x0024
            r2 = r3
            com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber r2 = (com.tencent.qqgamemi.mgc.eventbus.ProxySubscriber) r2
            java.lang.Object r0 = r2.getProxiedSubscriber()
            if (r0 != r12) goto L_0x0024
            r9.removeProxySubscriber(r2, r1)
            r6 = r7
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.mgc.eventbus.EventBus.removeFromSetResolveWeakReferences(java.util.Map, java.lang.Object, java.lang.Object):boolean");
    }

    public boolean unsubscribe(Class cl, Subscriber subscriber) {
        return unsubscribe(cl, this.subscribersByClass, subscriber);
    }

    public boolean unsubscribe(String topic, TopicSubscriber subscriber) {
        return unsubscribe(topic, this.subscribersByTopic, subscriber);
    }

    private List createCopyOfContentsRemoveWeakRefs(Collection subscribersOrVetoListeners) {
        if (subscribersOrVetoListeners == null) {
            return null;
        }
        List copyOfSubscribersOrVetolisteners = new ArrayList(subscribersOrVetoListeners.size());
        Iterator iter = subscribersOrVetoListeners.iterator();
        while (iter.hasNext()) {
            Object elem = iter.next();
            if (elem instanceof ProxySubscriber) {
                ProxySubscriber proxy = (ProxySubscriber) elem;
                if (proxy.getProxiedSubscriber() == null) {
                    removeProxySubscriber(proxy, iter);
                } else {
                    copyOfSubscribersOrVetolisteners.add(proxy);
                }
            } else if (elem instanceof WeakReference) {
                Object hardRef = ((WeakReference) elem).get();
                if (hardRef == null) {
                    iter.remove();
                } else {
                    copyOfSubscribersOrVetolisteners.add(hardRef);
                }
            } else {
                copyOfSubscribersOrVetolisteners.add(elem);
            }
        }
        return copyOfSubscribersOrVetolisteners;
    }

    public <T> List<T> getSubscribersToClass(Class<T> eventClass) {
        List result = null;
        Map classMap = this.subscribersByClass;
        for (Class cl : classMap.keySet()) {
            if (cl.isAssignableFrom(eventClass)) {
                Collection subscribers = (Collection) classMap.get(cl);
                if (result == null) {
                    result = new ArrayList();
                }
                result.addAll(createCopyOfContentsRemoveWeakRefs(subscribers));
            }
        }
        return result;
    }

    public <T> List<T> getSubscribers(Class<T> eventClass) {
        List<T> subscribersToClass;
        synchronized (this.listenerLock) {
            subscribersToClass = getSubscribersToClass(eventClass);
        }
        return subscribersToClass;
    }

    public void publish(Object event) {
        if (event == null) {
            throw new IllegalArgumentException("Cannot publish null event.");
        }
        this.mHandler.post(new PublishRunnable(event, (String) null, (Object) null, getSubscribers(event.getClass())));
    }

    private List getSubscribers(Object classOrTopic, Map subscriberMap) {
        return createCopyOfContentsRemoveWeakRefs((List) subscriberMap.get(classOrTopic));
    }

    public <T> List<T> getSubscribersToTopic(String topic) {
        List<T> subscribers;
        synchronized (this.listenerLock) {
            subscribers = getSubscribers(topic, this.subscribersByTopic);
        }
        return subscribers;
    }

    public void publish(String topicName, Object eventObj) {
        this.mHandler.post(new PublishRunnable((Object) null, topicName, eventObj, getSubscribersToTopic(topicName)));
    }

    /* access modifiers changed from: protected */
    public void publish(Object event, String topic, Object eventObj, List subscribers) {
        if (event == null && topic == null) {
            throw new IllegalArgumentException("Can't publish to null topic/event.");
        } else if (subscribers == null || subscribers.isEmpty()) {
            LogUtil.w(TAG, "No subscribers for event or topic. Event:" + event + ", Topic:" + topic);
        } else {
            for (int i = 0; i < subscribers.size(); i++) {
                Object eh = subscribers.get(i);
                if (event != null) {
                    try {
                        ((Subscriber) eh).onEvent(event);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TopicSubscriber) eh).onEvent(topic, eventObj);
                    } catch (Throwable e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    class PublishRunnable implements Runnable {
        Object theEvent;
        Object theEventObject;
        List theSubscribers;
        String theTopic;

        public PublishRunnable(Object event, String topic, Object eventObj, List subscribers) {
            this.theEvent = event;
            this.theTopic = topic;
            this.theEventObject = eventObj;
            this.theSubscribers = subscribers;
        }

        public void run() {
            EventBus.this.publish(this.theEvent, this.theTopic, this.theEventObject, this.theSubscribers);
        }
    }

    private static class CenterInstance {
        /* access modifiers changed from: private */
        public static EventBus _instanceCenter = new EventBus();

        private CenterInstance() {
        }
    }
}
