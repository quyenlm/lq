package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchGetItemResult implements Serializable {
    private List<ConsumedCapacity> consumedCapacity;
    private Map<String, List<Map<String, AttributeValue>>> responses;
    private Map<String, KeysAndAttributes> unprocessedKeys;

    public Map<String, List<Map<String, AttributeValue>>> getResponses() {
        return this.responses;
    }

    public void setResponses(Map<String, List<Map<String, AttributeValue>>> responses2) {
        this.responses = responses2;
    }

    public BatchGetItemResult withResponses(Map<String, List<Map<String, AttributeValue>>> responses2) {
        this.responses = responses2;
        return this;
    }

    public BatchGetItemResult addResponsesEntry(String key, List<Map<String, AttributeValue>> value) {
        if (this.responses == null) {
            this.responses = new HashMap();
        }
        if (this.responses.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.responses.put(key, value);
        return this;
    }

    public BatchGetItemResult clearResponsesEntries() {
        this.responses = null;
        return this;
    }

    public Map<String, KeysAndAttributes> getUnprocessedKeys() {
        return this.unprocessedKeys;
    }

    public void setUnprocessedKeys(Map<String, KeysAndAttributes> unprocessedKeys2) {
        this.unprocessedKeys = unprocessedKeys2;
    }

    public BatchGetItemResult withUnprocessedKeys(Map<String, KeysAndAttributes> unprocessedKeys2) {
        this.unprocessedKeys = unprocessedKeys2;
        return this;
    }

    public BatchGetItemResult addUnprocessedKeysEntry(String key, KeysAndAttributes value) {
        if (this.unprocessedKeys == null) {
            this.unprocessedKeys = new HashMap();
        }
        if (this.unprocessedKeys.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.unprocessedKeys.put(key, value);
        return this;
    }

    public BatchGetItemResult clearUnprocessedKeysEntries() {
        this.unprocessedKeys = null;
        return this;
    }

    public List<ConsumedCapacity> getConsumedCapacity() {
        return this.consumedCapacity;
    }

    public void setConsumedCapacity(Collection<ConsumedCapacity> consumedCapacity2) {
        if (consumedCapacity2 == null) {
            this.consumedCapacity = null;
        } else {
            this.consumedCapacity = new ArrayList(consumedCapacity2);
        }
    }

    public BatchGetItemResult withConsumedCapacity(ConsumedCapacity... consumedCapacity2) {
        if (getConsumedCapacity() == null) {
            this.consumedCapacity = new ArrayList(consumedCapacity2.length);
        }
        for (ConsumedCapacity value : consumedCapacity2) {
            this.consumedCapacity.add(value);
        }
        return this;
    }

    public BatchGetItemResult withConsumedCapacity(Collection<ConsumedCapacity> consumedCapacity2) {
        setConsumedCapacity(consumedCapacity2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResponses() != null) {
            sb.append("Responses: " + getResponses() + ",");
        }
        if (getUnprocessedKeys() != null) {
            sb.append("UnprocessedKeys: " + getUnprocessedKeys() + ",");
        }
        if (getConsumedCapacity() != null) {
            sb.append("ConsumedCapacity: " + getConsumedCapacity());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getResponses() == null ? 0 : getResponses().hashCode()) + 31) * 31) + (getUnprocessedKeys() == null ? 0 : getUnprocessedKeys().hashCode())) * 31;
        if (getConsumedCapacity() != null) {
            i = getConsumedCapacity().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BatchGetItemResult)) {
            return false;
        }
        BatchGetItemResult other = (BatchGetItemResult) obj;
        if ((other.getResponses() == null) ^ (getResponses() == null)) {
            return false;
        }
        if (other.getResponses() != null && !other.getResponses().equals(getResponses())) {
            return false;
        }
        if (other.getUnprocessedKeys() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getUnprocessedKeys() == null)) {
            return false;
        }
        if (other.getUnprocessedKeys() != null && !other.getUnprocessedKeys().equals(getUnprocessedKeys())) {
            return false;
        }
        if (other.getConsumedCapacity() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getConsumedCapacity() == null)) {
            return false;
        }
        if (other.getConsumedCapacity() == null || other.getConsumedCapacity().equals(getConsumedCapacity())) {
            return true;
        }
        return false;
    }
}
