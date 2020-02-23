package com.garena.android.gpns.notification;

import com.garena.android.gpns.notification.event.NotifyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NotificationBus {
    private HashMap<String, ArrayList<NotifyItem>> mMap = new HashMap<>();

    public void register(String key, NotifyItem item) {
        if (key == null) {
            return;
        }
        if (this.mMap.containsKey(key)) {
            this.mMap.get(key).add(item);
            return;
        }
        ArrayList<NotifyItem> list = new ArrayList<>();
        list.add(item);
        this.mMap.put(key, list);
    }

    public void unregister(String key, NotifyItem item) {
        if (key != null && this.mMap.containsKey(key)) {
            this.mMap.get(key).remove(item);
        }
    }

    public void fire(String key, NotifyEvent event) {
        if (key != null && this.mMap.containsKey(key)) {
            Iterator<NotifyItem> it = this.mMap.get(key).iterator();
            while (it.hasNext()) {
                NotifyItem item = it.next();
                if (item != null) {
                    item.onNotify(event);
                }
            }
        }
    }

    public void clearAll() {
        this.mMap.clear();
    }
}
