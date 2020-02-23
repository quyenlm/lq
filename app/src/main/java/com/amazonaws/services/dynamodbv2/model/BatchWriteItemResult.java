package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchWriteItemResult implements Serializable {
    private List<ConsumedCapacity> consumedCapacity;
    private Map<String, List<ItemCollectionMetrics>> itemCollectionMetrics;
    private Map<String, List<WriteRequest>> unprocessedItems;

    public Map<String, List<WriteRequest>> getUnprocessedItems() {
        return this.unprocessedItems;
    }

    public void setUnprocessedItems(Map<String, List<WriteRequest>> unprocessedItems2) {
        this.unprocessedItems = unprocessedItems2;
    }

    public BatchWriteItemResult withUnprocessedItems(Map<String, List<WriteRequest>> unprocessedItems2) {
        this.unprocessedItems = unprocessedItems2;
        return this;
    }

    public BatchWriteItemResult addUnprocessedItemsEntry(String key, List<WriteRequest> value) {
        if (this.unprocessedItems == null) {
            this.unprocessedItems = new HashMap();
        }
        if (this.unprocessedItems.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.unprocessedItems.put(key, value);
        return this;
    }

    public BatchWriteItemResult clearUnprocessedItemsEntries() {
        this.unprocessedItems = null;
        return this;
    }

    public Map<String, List<ItemCollectionMetrics>> getItemCollectionMetrics() {
        return this.itemCollectionMetrics;
    }

    public void setItemCollectionMetrics(Map<String, List<ItemCollectionMetrics>> itemCollectionMetrics2) {
        this.itemCollectionMetrics = itemCollectionMetrics2;
    }

    public BatchWriteItemResult withItemCollectionMetrics(Map<String, List<ItemCollectionMetrics>> itemCollectionMetrics2) {
        this.itemCollectionMetrics = itemCollectionMetrics2;
        return this;
    }

    public BatchWriteItemResult addItemCollectionMetricsEntry(String key, List<ItemCollectionMetrics> value) {
        if (this.itemCollectionMetrics == null) {
            this.itemCollectionMetrics = new HashMap();
        }
        if (this.itemCollectionMetrics.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.itemCollectionMetrics.put(key, value);
        return this;
    }

    public BatchWriteItemResult clearItemCollectionMetricsEntries() {
        this.itemCollectionMetrics = null;
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

    public BatchWriteItemResult withConsumedCapacity(ConsumedCapacity... consumedCapacity2) {
        if (getConsumedCapacity() == null) {
            this.consumedCapacity = new ArrayList(consumedCapacity2.length);
        }
        for (ConsumedCapacity value : consumedCapacity2) {
            this.consumedCapacity.add(value);
        }
        return this;
    }

    public BatchWriteItemResult withConsumedCapacity(Collection<ConsumedCapacity> consumedCapacity2) {
        setConsumedCapacity(consumedCapacity2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUnprocessedItems() != null) {
            sb.append("UnprocessedItems: " + getUnprocessedItems() + ",");
        }
        if (getItemCollectionMetrics() != null) {
            sb.append("ItemCollectionMetrics: " + getItemCollectionMetrics() + ",");
        }
        if (getConsumedCapacity() != null) {
            sb.append("ConsumedCapacity: " + getConsumedCapacity());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getUnprocessedItems() == null ? 0 : getUnprocessedItems().hashCode()) + 31) * 31) + (getItemCollectionMetrics() == null ? 0 : getItemCollectionMetrics().hashCode())) * 31;
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
        if (obj == null || !(obj instanceof BatchWriteItemResult)) {
            return false;
        }
        BatchWriteItemResult other = (BatchWriteItemResult) obj;
        if ((other.getUnprocessedItems() == null) ^ (getUnprocessedItems() == null)) {
            return false;
        }
        if (other.getUnprocessedItems() != null && !other.getUnprocessedItems().equals(getUnprocessedItems())) {
            return false;
        }
        if (other.getItemCollectionMetrics() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getItemCollectionMetrics() == null)) {
            return false;
        }
        if (other.getItemCollectionMetrics() != null && !other.getItemCollectionMetrics().equals(getItemCollectionMetrics())) {
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
