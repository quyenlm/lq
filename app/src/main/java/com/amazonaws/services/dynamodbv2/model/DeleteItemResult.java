package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeleteItemResult implements Serializable {
    private Map<String, AttributeValue> attributes;
    private ConsumedCapacity consumedCapacity;
    private ItemCollectionMetrics itemCollectionMetrics;

    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, AttributeValue> attributes2) {
        this.attributes = attributes2;
    }

    public DeleteItemResult withAttributes(Map<String, AttributeValue> attributes2) {
        this.attributes = attributes2;
        return this;
    }

    public DeleteItemResult addAttributesEntry(String key, AttributeValue value) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.attributes.put(key, value);
        return this;
    }

    public DeleteItemResult clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public ConsumedCapacity getConsumedCapacity() {
        return this.consumedCapacity;
    }

    public void setConsumedCapacity(ConsumedCapacity consumedCapacity2) {
        this.consumedCapacity = consumedCapacity2;
    }

    public DeleteItemResult withConsumedCapacity(ConsumedCapacity consumedCapacity2) {
        this.consumedCapacity = consumedCapacity2;
        return this;
    }

    public ItemCollectionMetrics getItemCollectionMetrics() {
        return this.itemCollectionMetrics;
    }

    public void setItemCollectionMetrics(ItemCollectionMetrics itemCollectionMetrics2) {
        this.itemCollectionMetrics = itemCollectionMetrics2;
    }

    public DeleteItemResult withItemCollectionMetrics(ItemCollectionMetrics itemCollectionMetrics2) {
        this.itemCollectionMetrics = itemCollectionMetrics2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getConsumedCapacity() != null) {
            sb.append("ConsumedCapacity: " + getConsumedCapacity() + ",");
        }
        if (getItemCollectionMetrics() != null) {
            sb.append("ItemCollectionMetrics: " + getItemCollectionMetrics());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getAttributes() == null ? 0 : getAttributes().hashCode()) + 31) * 31) + (getConsumedCapacity() == null ? 0 : getConsumedCapacity().hashCode())) * 31;
        if (getItemCollectionMetrics() != null) {
            i = getItemCollectionMetrics().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteItemResult)) {
            return false;
        }
        DeleteItemResult other = (DeleteItemResult) obj;
        if ((other.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (other.getAttributes() != null && !other.getAttributes().equals(getAttributes())) {
            return false;
        }
        if (other.getConsumedCapacity() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getConsumedCapacity() == null)) {
            return false;
        }
        if (other.getConsumedCapacity() != null && !other.getConsumedCapacity().equals(getConsumedCapacity())) {
            return false;
        }
        if (other.getItemCollectionMetrics() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getItemCollectionMetrics() == null)) {
            return false;
        }
        if (other.getItemCollectionMetrics() == null || other.getItemCollectionMetrics().equals(getItemCollectionMetrics())) {
            return true;
        }
        return false;
    }
}
