package com.garena.pay.android.helper;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryString {
    private String query;

    public QueryString(HashMap<String, String> map) {
        this.query = "";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = it.next();
            if (pairs.getValue() != null) {
                this.query += URLEncoder.encode(pairs.getKey().toString()) + HttpRequest.HTTP_REQ_ENTITY_MERGE + URLEncoder.encode(pairs.getValue().toString());
                if (it.hasNext()) {
                    this.query += HttpRequest.HTTP_REQ_ENTITY_JOIN;
                }
            }
        }
    }

    public QueryString(Object name, Object value) {
        this.query = "";
        this.query = URLEncoder.encode(name.toString()) + HttpRequest.HTTP_REQ_ENTITY_MERGE + URLEncoder.encode(value.toString());
    }

    public QueryString() {
        this.query = "";
        this.query = "";
    }

    public synchronized void add(Object name, Object value) {
        if (!this.query.trim().equals("")) {
            this.query += HttpRequest.HTTP_REQ_ENTITY_JOIN;
        }
        this.query += URLEncoder.encode(name.toString()) + HttpRequest.HTTP_REQ_ENTITY_MERGE + URLEncoder.encode(value.toString());
    }

    public String toString() {
        return this.query;
    }
}
