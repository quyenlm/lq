package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeleteRequest implements Serializable {
    private Map<String, AttributeValue> key;

    public DeleteRequest() {
    }

    public DeleteRequest(Map<String, AttributeValue> key2) {
        setKey(key2);
    }

    public Map<String, AttributeValue> getKey() {
        return this.key;
    }

    public void setKey(Map<String, AttributeValue> key2) {
        this.key = key2;
    }

    public DeleteRequest withKey(Map<String, AttributeValue> key2) {
        this.key = key2;
        return this;
    }

    public DeleteRequest addKeyEntry(String key2, AttributeValue value) {
        if (this.key == null) {
            this.key = new HashMap();
        }
        if (this.key.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.key.put(key2, value);
        return this;
    }

    public DeleteRequest clearKeyEntries() {
        this.key = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKey() != null) {
            sb.append("Key: " + getKey());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getKey() == null ? 0 : getKey().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteRequest)) {
            return false;
        }
        DeleteRequest other = (DeleteRequest) obj;
        if (other.getKey() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getKey() == null)) {
            return false;
        }
        if (other.getKey() == null || other.getKey().equals(getKey())) {
            return true;
        }
        return false;
    }
}
