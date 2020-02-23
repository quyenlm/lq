package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanResult implements Serializable {
    private ConsumedCapacity consumedCapacity;
    private Integer count;
    private List<Map<String, AttributeValue>> items;
    private Map<String, AttributeValue> lastEvaluatedKey;
    private Integer scannedCount;

    public List<Map<String, AttributeValue>> getItems() {
        return this.items;
    }

    public void setItems(Collection<Map<String, AttributeValue>> items2) {
        if (items2 == null) {
            this.items = null;
        } else {
            this.items = new ArrayList(items2);
        }
    }

    public ScanResult withItems(Map<String, AttributeValue>... items2) {
        if (getItems() == null) {
            this.items = new ArrayList(items2.length);
        }
        for (Map<String, AttributeValue> value : items2) {
            this.items.add(value);
        }
        return this;
    }

    public ScanResult withItems(Collection<Map<String, AttributeValue>> items2) {
        setItems(items2);
        return this;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count2) {
        this.count = count2;
    }

    public ScanResult withCount(Integer count2) {
        this.count = count2;
        return this;
    }

    public Integer getScannedCount() {
        return this.scannedCount;
    }

    public void setScannedCount(Integer scannedCount2) {
        this.scannedCount = scannedCount2;
    }

    public ScanResult withScannedCount(Integer scannedCount2) {
        this.scannedCount = scannedCount2;
        return this;
    }

    public Map<String, AttributeValue> getLastEvaluatedKey() {
        return this.lastEvaluatedKey;
    }

    public void setLastEvaluatedKey(Map<String, AttributeValue> lastEvaluatedKey2) {
        this.lastEvaluatedKey = lastEvaluatedKey2;
    }

    public ScanResult withLastEvaluatedKey(Map<String, AttributeValue> lastEvaluatedKey2) {
        this.lastEvaluatedKey = lastEvaluatedKey2;
        return this;
    }

    public ScanResult addLastEvaluatedKeyEntry(String key, AttributeValue value) {
        if (this.lastEvaluatedKey == null) {
            this.lastEvaluatedKey = new HashMap();
        }
        if (this.lastEvaluatedKey.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.lastEvaluatedKey.put(key, value);
        return this;
    }

    public ScanResult clearLastEvaluatedKeyEntries() {
        this.lastEvaluatedKey = null;
        return this;
    }

    public ConsumedCapacity getConsumedCapacity() {
        return this.consumedCapacity;
    }

    public void setConsumedCapacity(ConsumedCapacity consumedCapacity2) {
        this.consumedCapacity = consumedCapacity2;
    }

    public ScanResult withConsumedCapacity(ConsumedCapacity consumedCapacity2) {
        this.consumedCapacity = consumedCapacity2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getItems() != null) {
            sb.append("Items: " + getItems() + ",");
        }
        if (getCount() != null) {
            sb.append("Count: " + getCount() + ",");
        }
        if (getScannedCount() != null) {
            sb.append("ScannedCount: " + getScannedCount() + ",");
        }
        if (getLastEvaluatedKey() != null) {
            sb.append("LastEvaluatedKey: " + getLastEvaluatedKey() + ",");
        }
        if (getConsumedCapacity() != null) {
            sb.append("ConsumedCapacity: " + getConsumedCapacity());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((getItems() == null ? 0 : getItems().hashCode()) + 31) * 31) + (getCount() == null ? 0 : getCount().hashCode())) * 31) + (getScannedCount() == null ? 0 : getScannedCount().hashCode())) * 31) + (getLastEvaluatedKey() == null ? 0 : getLastEvaluatedKey().hashCode())) * 31;
        if (getConsumedCapacity() != null) {
            i = getConsumedCapacity().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ScanResult)) {
            return false;
        }
        ScanResult other = (ScanResult) obj;
        if ((other.getItems() == null) ^ (getItems() == null)) {
            return false;
        }
        if (other.getItems() != null && !other.getItems().equals(getItems())) {
            return false;
        }
        if (other.getCount() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getCount() == null)) {
            return false;
        }
        if (other.getCount() != null && !other.getCount().equals(getCount())) {
            return false;
        }
        if (other.getScannedCount() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getScannedCount() == null)) {
            return false;
        }
        if (other.getScannedCount() != null && !other.getScannedCount().equals(getScannedCount())) {
            return false;
        }
        if (other.getLastEvaluatedKey() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getLastEvaluatedKey() == null)) {
            return false;
        }
        if (other.getLastEvaluatedKey() != null && !other.getLastEvaluatedKey().equals(getLastEvaluatedKey())) {
            return false;
        }
        if (other.getConsumedCapacity() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getConsumedCapacity() == null)) {
            return false;
        }
        if (other.getConsumedCapacity() == null || other.getConsumedCapacity().equals(getConsumedCapacity())) {
            return true;
        }
        return false;
    }
}
