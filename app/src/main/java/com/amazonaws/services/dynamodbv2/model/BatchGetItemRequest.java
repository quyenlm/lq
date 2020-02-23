package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BatchGetItemRequest extends AmazonWebServiceRequest implements Serializable {
    private Map<String, KeysAndAttributes> requestItems;
    private String returnConsumedCapacity;

    public BatchGetItemRequest() {
    }

    public BatchGetItemRequest(Map<String, KeysAndAttributes> requestItems2) {
        setRequestItems(requestItems2);
    }

    public BatchGetItemRequest(Map<String, KeysAndAttributes> requestItems2, String returnConsumedCapacity2) {
        setRequestItems(requestItems2);
        setReturnConsumedCapacity(returnConsumedCapacity2);
    }

    public BatchGetItemRequest(Map<String, KeysAndAttributes> requestItems2, ReturnConsumedCapacity returnConsumedCapacity2) {
        setRequestItems(requestItems2);
        setReturnConsumedCapacity(returnConsumedCapacity2.toString());
    }

    public Map<String, KeysAndAttributes> getRequestItems() {
        return this.requestItems;
    }

    public void setRequestItems(Map<String, KeysAndAttributes> requestItems2) {
        this.requestItems = requestItems2;
    }

    public BatchGetItemRequest withRequestItems(Map<String, KeysAndAttributes> requestItems2) {
        this.requestItems = requestItems2;
        return this;
    }

    public BatchGetItemRequest addRequestItemsEntry(String key, KeysAndAttributes value) {
        if (this.requestItems == null) {
            this.requestItems = new HashMap();
        }
        if (this.requestItems.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.requestItems.put(key, value);
        return this;
    }

    public BatchGetItemRequest clearRequestItemsEntries() {
        this.requestItems = null;
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public BatchGetItemRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public BatchGetItemRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRequestItems() != null) {
            sb.append("RequestItems: " + getRequestItems() + ",");
        }
        if (getReturnConsumedCapacity() != null) {
            sb.append("ReturnConsumedCapacity: " + getReturnConsumedCapacity());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getRequestItems() == null ? 0 : getRequestItems().hashCode()) + 31) * 31;
        if (getReturnConsumedCapacity() != null) {
            i = getReturnConsumedCapacity().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BatchGetItemRequest)) {
            return false;
        }
        BatchGetItemRequest other = (BatchGetItemRequest) obj;
        if ((other.getRequestItems() == null) ^ (getRequestItems() == null)) {
            return false;
        }
        if (other.getRequestItems() != null && !other.getRequestItems().equals(getRequestItems())) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getReturnConsumedCapacity() == null)) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null || other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return true;
        }
        return false;
    }
}
