package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PutRequest implements Serializable {
    private Map<String, AttributeValue> item;

    public PutRequest() {
    }

    public PutRequest(Map<String, AttributeValue> item2) {
        setItem(item2);
    }

    public Map<String, AttributeValue> getItem() {
        return this.item;
    }

    public void setItem(Map<String, AttributeValue> item2) {
        this.item = item2;
    }

    public PutRequest withItem(Map<String, AttributeValue> item2) {
        this.item = item2;
        return this;
    }

    public PutRequest addItemEntry(String key, AttributeValue value) {
        if (this.item == null) {
            this.item = new HashMap();
        }
        if (this.item.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.item.put(key, value);
        return this;
    }

    public PutRequest clearItemEntries() {
        this.item = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getItem() == null ? 0 : getItem().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PutRequest)) {
            return false;
        }
        PutRequest other = (PutRequest) obj;
        if (other.getItem() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getItem() == null)) {
            return false;
        }
        if (other.getItem() == null || other.getItem().equals(getItem())) {
            return true;
        }
        return false;
    }
}
