package com.tencent.abase;

import java.util.HashMap;
import java.util.Map;

public class URLResponse {
    public String URL = "";
    public byte[] body;
    public Map<String, String> headers = new HashMap();
    public int status;
    public String statusMsg = "";
    public String version = "";

    URLResponse() {
    }
}
