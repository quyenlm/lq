package com.tencent.ieg.ntv.event;

public class EventGetCDNFileFinish extends Event {
    public byte[] data;
    public String url;

    public EventGetCDNFileFinish(String url2, byte[] data2) {
        this.url = url2;
        this.data = data2;
    }
}
