package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchWriteItemRequest extends AmazonWebServiceRequest implements Serializable {
    private Map<String, List<WriteRequest>> requestItems;
    private String returnConsumedCapacity;
    private String returnItemCollectionMetrics;

    public BatchWriteItemRequest() {
    }

    public BatchWriteItemRequest(Map<String, List<WriteRequest>> requestItems2) {
        setRequestItems(requestItems2);
    }

    public Map<String, List<WriteRequest>> getRequestItems() {
        return this.requestItems;
    }

    public void setRequestItems(Map<String, List<WriteRequest>> requestItems2) {
        this.requestItems = requestItems2;
    }

    public BatchWriteItemRequest withRequestItems(Map<String, List<WriteRequest>> requestItems2) {
        this.requestItems = requestItems2;
        return this;
    }

    public BatchWriteItemRequest addRequestItemsEntry(String key, List<WriteRequest> value) {
        if (this.requestItems == null) {
            this.requestItems = new HashMap();
        }
        if (this.requestItems.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.requestItems.put(key, value);
        return this;
    }

    public BatchWriteItemRequest clearRequestItemsEntries() {
        this.requestItems = null;
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public BatchWriteItemRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public BatchWriteItemRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public String getReturnItemCollectionMetrics() {
        return this.returnItemCollectionMetrics;
    }

    public void setReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
    }

    public BatchWriteItemRequest withReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
        return this;
    }

    public void setReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
    }

    public BatchWriteItemRequest withReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRequestItems() != null) {
            sb.append("RequestItems: " + getRequestItems() + ",");
        }
        if (getReturnConsumedCapacity() != null) {
            sb.append("ReturnConsumedCapacity: " + getReturnConsumedCapacity() + ",");
        }
        if (getReturnItemCollectionMetrics() != null) {
            sb.append("ReturnItemCollectionMetrics: " + getReturnItemCollectionMetrics());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((getRequestItems() == null ? 0 : getRequestItems().hashCode()) + 31) * 31;
        if (getReturnConsumedCapacity() == null) {
            hashCode = 0;
        } else {
            hashCode = getReturnConsumedCapacity().hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (getReturnItemCollectionMetrics() != null) {
            i = getReturnItemCollectionMetrics().hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BatchWriteItemRequest)) {
            return false;
        }
        BatchWriteItemRequest other = (BatchWriteItemRequest) obj;
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
        if (other.getReturnConsumedCapacity() != null && !other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (getReturnItemCollectionMetrics() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 ^ z3) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() == null || other.getReturnItemCollectionMetrics().equals(getReturnItemCollectionMetrics())) {
            return true;
        }
        return false;
    }
}
