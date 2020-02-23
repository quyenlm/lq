package com.tencent.imsdk.sns.api;

import java.util.LinkedList;

public final class IMEventQueue {
    private static volatile IMEventQueue IMEventQueueInstance;
    private LinkedList<IEventQueueHandler> linkedList = new LinkedList<>();

    public static IMEventQueue getInstance() {
        if (IMEventQueueInstance == null) {
            synchronized (IMEventQueue.class) {
                if (IMEventQueueInstance == null) {
                    IMEventQueueInstance = new IMEventQueue();
                }
            }
        }
        return IMEventQueueInstance;
    }

    public LinkedList<IEventQueueHandler> getEventHandlerQueue() {
        return this.linkedList;
    }

    public void addEventHandler(IEventQueueHandler iEventQueueHandler) {
        if (this.linkedList != null && !this.linkedList.contains(iEventQueueHandler)) {
            this.linkedList.add(iEventQueueHandler);
        }
    }

    public void deleteEventHandler(IEventQueueHandler iEventQueueHandler) {
        if (this.linkedList != null && this.linkedList.contains(iEventQueueHandler)) {
            this.linkedList.remove(iEventQueueHandler);
        }
    }

    public void cleanEventHandlerQueue() {
        if (this.linkedList != null) {
            this.linkedList.clear();
        }
    }
}
