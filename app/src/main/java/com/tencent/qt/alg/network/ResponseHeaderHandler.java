package com.tencent.qt.alg.network;

import java.net.HttpURLConnection;

public interface ResponseHeaderHandler {
    int handle(HttpURLConnection httpURLConnection);
}
