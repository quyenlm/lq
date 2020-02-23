package com.tencent.ieg.ntv.event;

public class EventGetCDNFile extends Event {
    public String url;

    public EventGetCDNFile(String url2) {
        this.url = url2;
    }
}
