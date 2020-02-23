package com.tencent.component.net.download.multiplex.http;

public class RequesterFactory {
    public static Requester getRequester() {
        return new HttpRequester();
    }
}
