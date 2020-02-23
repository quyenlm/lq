package com.tencent.qqgamemi.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventRouter {
    private static final String TAG = "EventRouter";
    private static volatile EventRouter sInstance;
    private Map<String, List<WeakReference<EventHandler>>> mEventTable = new HashMap();

    private EventRouter() {
    }

    public static EventRouter getInstance() {
        if (sInstance == null) {
            synchronized (EventRouter.class) {
                if (sInstance == null) {
                    sInstance = new EventRouter();
                }
            }
        }
        return sInstance;
    }

    public void addEventHandler(String eventType, EventHandler handler) {
        addEventHandlerInner(eventType, handler, new Object[0]);
    }

    private void addEventHandlerInner(String eventType, EventHandler handler, Object... args) {
        if (eventType != null && eventType.length() != 0 && handler != null && !hasSameHandler(eventType, handler)) {
            addEventHandlerReally(eventType, handler, args);
        }
    }

    private void addEventHandlerReally(String eventType, EventHandler handler, Object... args) {
        List<WeakReference<EventHandler>> oldHandlers = this.mEventTable.get(eventType);
        if (oldHandlers == null) {
            List<WeakReference<EventHandler>> oldHandlers2 = new ArrayList<>();
            oldHandlers2.add(new WeakReference(handler));
            this.mEventTable.put(eventType, oldHandlers2);
        } else if (!containsEventHandler(oldHandlers, handler)) {
            oldHandlers.add(new WeakReference(handler));
        }
    }

    public void removeEventHandler(String eventType, EventHandler handler) {
        if (handler != null && this.mEventTable.containsKey(eventType)) {
            List<WeakReference<EventHandler>> handlers = this.mEventTable.get(eventType);
            if (handlers != null) {
                removeEventHandler(handlers, handler);
            }
            if (handlers == null || handlers.size() <= 0) {
                this.mEventTable.remove(eventType);
            }
        }
    }

    private void removeEventHandler(List<WeakReference<EventHandler>> handlers, EventHandler handler) {
        if (handlers != null && handlers.size() > 0) {
            Iterator<WeakReference<EventHandler>> iterator = handlers.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().get() == handler) {
                    iterator.remove();
                }
            }
        }
    }

    public void broadcastEvent(String eventType) {
        List<EventHandler> handlers;
        if (onBroadCasting(eventType) && (handlers = getHandlersByEventType(eventType)) != null && handlers.size() > 0) {
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.onReceive(new Object[0]);
                }
            }
        }
    }

    public <T> void broadcastEvent(String eventType, T arg1) {
        List<EventHandler> handlers;
        if (onBroadCasting(eventType) && (handlers = getHandlersByEventType(eventType)) != null && handlers.size() > 0) {
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.onReceive(arg1);
                }
            }
        }
    }

    private List<EventHandler> getHandlersByEventType(String eventType) {
        List<WeakReference<EventHandler>> handlers;
        if (!this.mEventTable.containsKey(eventType) || (handlers = this.mEventTable.get(eventType)) == null) {
            return null;
        }
        return getHandlersFromReferences(handlers);
    }

    private List<EventHandler> getHandlersFromReferences(List<WeakReference<EventHandler>> handlers) {
        List<EventHandler> list = null;
        if (handlers != null && handlers.size() > 0) {
            list = new ArrayList<>();
            for (WeakReference<EventHandler> handlerRef : handlers) {
                if (handlerRef != null) {
                    list.add(handlerRef.get());
                }
            }
        }
        return list;
    }

    public <T1, T2> void broadcastEvent(String eventType, T1 arg1, T2 arg2) {
        List<EventHandler> handlers;
        if (onBroadCasting(eventType) && (handlers = getHandlersByEventType(eventType)) != null && handlers.size() > 0) {
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.onReceive(arg1, arg2);
                }
            }
        }
    }

    public <T1, T2, T3> void broadcastEvent(String eventType, T1 arg1, T2 arg2, T3 arg3) {
        List<EventHandler> handlers;
        if (onBroadCasting(eventType) && (handlers = getHandlersByEventType(eventType)) != null && handlers.size() > 0) {
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.onReceive(arg1, arg2, arg3);
                }
            }
        }
    }

    public <T1, T2, T3, T4> void broadcastEvent(String eventType, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        List<EventHandler> handlers;
        if (onBroadCasting(eventType) && (handlers = getHandlersByEventType(eventType)) != null && handlers.size() > 0) {
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.onReceive(arg1, arg2, arg3, arg4);
                }
            }
        }
    }

    private boolean hasSameHandler(String eventType, EventHandler handler) {
        List<WeakReference<EventHandler>> oldHandlers;
        if (!this.mEventTable.containsKey(eventType) || (oldHandlers = this.mEventTable.get(eventType)) == null) {
            return false;
        }
        return containsEventHandler(oldHandlers, handler);
    }

    private boolean containsEventHandler(List<WeakReference<EventHandler>> handlers, EventHandler handler) {
        if (handlers != null && handlers.size() > 0) {
            for (WeakReference<EventHandler> h : handlers) {
                if (h != null && h.get() != null && ((EventHandler) h.get()) == handler) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onBroadCasting(String eventType) {
        return this.mEventTable.containsKey(eventType);
    }
}
