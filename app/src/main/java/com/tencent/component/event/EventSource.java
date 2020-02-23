package com.tencent.component.event;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public class EventSource {
    @PluginApi(since = 6)
    public String name;
    @PluginApi(since = 6)
    public Object sender;

    @PluginApi(since = 6)
    public EventSource(String name2) {
        this(name2, (Object) null);
    }

    @PluginApi(since = 6)
    public EventSource(String name2, Object sender2) {
        if (name2 == null || name2.length() == 0) {
            throw new NullPointerException("The mEventName of EventSource cannot be empty");
        }
        this.sender = sender2;
        this.name = name2;
    }

    @PluginApi(since = 6)
    public EventSource(Class<?> clazz, Object sender2) {
        this(clazz.getName(), sender2);
    }

    public int hashCode() {
        return (this.name == null ? 0 : this.name.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventSource other = (EventSource) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
            return true;
        } else if (!this.name.equals(other.name)) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        return "EventSource [mName=" + this.name + ", mSender=" + this.sender + "]";
    }
}
