package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeysAndAttributes implements Serializable {
    private List<String> attributesToGet;
    private Boolean consistentRead;
    private Map<String, String> expressionAttributeNames;
    private List<Map<String, AttributeValue>> keys;
    private String projectionExpression;

    public List<Map<String, AttributeValue>> getKeys() {
        return this.keys;
    }

    public void setKeys(Collection<Map<String, AttributeValue>> keys2) {
        if (keys2 == null) {
            this.keys = null;
        } else {
            this.keys = new ArrayList(keys2);
        }
    }

    public KeysAndAttributes withKeys(Map<String, AttributeValue>... keys2) {
        if (getKeys() == null) {
            this.keys = new ArrayList(keys2.length);
        }
        for (Map<String, AttributeValue> value : keys2) {
            this.keys.add(value);
        }
        return this;
    }

    public KeysAndAttributes withKeys(Collection<Map<String, AttributeValue>> keys2) {
        setKeys(keys2);
        return this;
    }

    public List<String> getAttributesToGet() {
        return this.attributesToGet;
    }

    public void setAttributesToGet(Collection<String> attributesToGet2) {
        if (attributesToGet2 == null) {
            this.attributesToGet = null;
        } else {
            this.attributesToGet = new ArrayList(attributesToGet2);
        }
    }

    public KeysAndAttributes withAttributesToGet(String... attributesToGet2) {
        if (getAttributesToGet() == null) {
            this.attributesToGet = new ArrayList(attributesToGet2.length);
        }
        for (String value : attributesToGet2) {
            this.attributesToGet.add(value);
        }
        return this;
    }

    public KeysAndAttributes withAttributesToGet(Collection<String> attributesToGet2) {
        setAttributesToGet(attributesToGet2);
        return this;
    }

    public Boolean isConsistentRead() {
        return this.consistentRead;
    }

    public Boolean getConsistentRead() {
        return this.consistentRead;
    }

    public void setConsistentRead(Boolean consistentRead2) {
        this.consistentRead = consistentRead2;
    }

    public KeysAndAttributes withConsistentRead(Boolean consistentRead2) {
        this.consistentRead = consistentRead2;
        return this;
    }

    public String getProjectionExpression() {
        return this.projectionExpression;
    }

    public void setProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
    }

    public KeysAndAttributes withProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
    }

    public KeysAndAttributes withExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
        return this;
    }

    public KeysAndAttributes addExpressionAttributeNamesEntry(String key, String value) {
        if (this.expressionAttributeNames == null) {
            this.expressionAttributeNames = new HashMap();
        }
        if (this.expressionAttributeNames.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.expressionAttributeNames.put(key, value);
        return this;
    }

    public KeysAndAttributes clearExpressionAttributeNamesEntries() {
        this.expressionAttributeNames = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKeys() != null) {
            sb.append("Keys: " + getKeys() + ",");
        }
        if (getAttributesToGet() != null) {
            sb.append("AttributesToGet: " + getAttributesToGet() + ",");
        }
        if (getConsistentRead() != null) {
            sb.append("ConsistentRead: " + getConsistentRead() + ",");
        }
        if (getProjectionExpression() != null) {
            sb.append("ProjectionExpression: " + getProjectionExpression() + ",");
        }
        if (getExpressionAttributeNames() != null) {
            sb.append("ExpressionAttributeNames: " + getExpressionAttributeNames());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((getKeys() == null ? 0 : getKeys().hashCode()) + 31) * 31) + (getAttributesToGet() == null ? 0 : getAttributesToGet().hashCode())) * 31) + (getConsistentRead() == null ? 0 : getConsistentRead().hashCode())) * 31) + (getProjectionExpression() == null ? 0 : getProjectionExpression().hashCode())) * 31;
        if (getExpressionAttributeNames() != null) {
            i = getExpressionAttributeNames().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof KeysAndAttributes)) {
            return false;
        }
        KeysAndAttributes other = (KeysAndAttributes) obj;
        if ((other.getKeys() == null) ^ (getKeys() == null)) {
            return false;
        }
        if (other.getKeys() != null && !other.getKeys().equals(getKeys())) {
            return false;
        }
        if (other.getAttributesToGet() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getAttributesToGet() == null)) {
            return false;
        }
        if (other.getAttributesToGet() != null && !other.getAttributesToGet().equals(getAttributesToGet())) {
            return false;
        }
        if (other.getConsistentRead() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getConsistentRead() == null)) {
            return false;
        }
        if (other.getConsistentRead() != null && !other.getConsistentRead().equals(getConsistentRead())) {
            return false;
        }
        if (other.getProjectionExpression() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getProjectionExpression() == null)) {
            return false;
        }
        if (other.getProjectionExpression() != null && !other.getProjectionExpression().equals(getProjectionExpression())) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (getExpressionAttributeNames() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z4 ^ z5) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null || other.getExpressionAttributeNames().equals(getExpressionAttributeNames())) {
            return true;
        }
        return false;
    }
}
