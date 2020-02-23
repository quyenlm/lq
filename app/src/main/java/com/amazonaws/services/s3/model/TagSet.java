package com.amazonaws.services.s3.model;

import java.util.HashMap;
import java.util.Map;

public class TagSet {
    private Map<String, String> tags = new HashMap(1);

    public TagSet() {
    }

    public TagSet(Map<String, String> tags2) {
        this.tags.putAll(tags2);
    }

    public String getTag(String key) {
        return this.tags.get(key);
    }

    public void setTag(String key, String value) {
        this.tags.put(key, value);
    }

    public Map<String, String> getAllTags() {
        return this.tags;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("Tags: " + getAllTags());
        sb.append("}");
        return sb.toString();
    }
}
