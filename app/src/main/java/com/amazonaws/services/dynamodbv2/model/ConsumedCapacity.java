package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConsumedCapacity implements Serializable {
    private Double capacityUnits;
    private Map<String, Capacity> globalSecondaryIndexes;
    private Map<String, Capacity> localSecondaryIndexes;
    private Capacity table;
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public ConsumedCapacity withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public Double getCapacityUnits() {
        return this.capacityUnits;
    }

    public void setCapacityUnits(Double capacityUnits2) {
        this.capacityUnits = capacityUnits2;
    }

    public ConsumedCapacity withCapacityUnits(Double capacityUnits2) {
        this.capacityUnits = capacityUnits2;
        return this;
    }

    public Capacity getTable() {
        return this.table;
    }

    public void setTable(Capacity table2) {
        this.table = table2;
    }

    public ConsumedCapacity withTable(Capacity table2) {
        this.table = table2;
        return this;
    }

    public Map<String, Capacity> getLocalSecondaryIndexes() {
        return this.localSecondaryIndexes;
    }

    public void setLocalSecondaryIndexes(Map<String, Capacity> localSecondaryIndexes2) {
        this.localSecondaryIndexes = localSecondaryIndexes2;
    }

    public ConsumedCapacity withLocalSecondaryIndexes(Map<String, Capacity> localSecondaryIndexes2) {
        this.localSecondaryIndexes = localSecondaryIndexes2;
        return this;
    }

    public ConsumedCapacity addLocalSecondaryIndexesEntry(String key, Capacity value) {
        if (this.localSecondaryIndexes == null) {
            this.localSecondaryIndexes = new HashMap();
        }
        if (this.localSecondaryIndexes.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.localSecondaryIndexes.put(key, value);
        return this;
    }

    public ConsumedCapacity clearLocalSecondaryIndexesEntries() {
        this.localSecondaryIndexes = null;
        return this;
    }

    public Map<String, Capacity> getGlobalSecondaryIndexes() {
        return this.globalSecondaryIndexes;
    }

    public void setGlobalSecondaryIndexes(Map<String, Capacity> globalSecondaryIndexes2) {
        this.globalSecondaryIndexes = globalSecondaryIndexes2;
    }

    public ConsumedCapacity withGlobalSecondaryIndexes(Map<String, Capacity> globalSecondaryIndexes2) {
        this.globalSecondaryIndexes = globalSecondaryIndexes2;
        return this;
    }

    public ConsumedCapacity addGlobalSecondaryIndexesEntry(String key, Capacity value) {
        if (this.globalSecondaryIndexes == null) {
            this.globalSecondaryIndexes = new HashMap();
        }
        if (this.globalSecondaryIndexes.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.globalSecondaryIndexes.put(key, value);
        return this;
    }

    public ConsumedCapacity clearGlobalSecondaryIndexesEntries() {
        this.globalSecondaryIndexes = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTableName() != null) {
            sb.append("TableName: " + getTableName() + ",");
        }
        if (getCapacityUnits() != null) {
            sb.append("CapacityUnits: " + getCapacityUnits() + ",");
        }
        if (getTable() != null) {
            sb.append("Table: " + getTable() + ",");
        }
        if (getLocalSecondaryIndexes() != null) {
            sb.append("LocalSecondaryIndexes: " + getLocalSecondaryIndexes() + ",");
        }
        if (getGlobalSecondaryIndexes() != null) {
            sb.append("GlobalSecondaryIndexes: " + getGlobalSecondaryIndexes());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((getTableName() == null ? 0 : getTableName().hashCode()) + 31) * 31) + (getCapacityUnits() == null ? 0 : getCapacityUnits().hashCode())) * 31) + (getTable() == null ? 0 : getTable().hashCode())) * 31) + (getLocalSecondaryIndexes() == null ? 0 : getLocalSecondaryIndexes().hashCode())) * 31;
        if (getGlobalSecondaryIndexes() != null) {
            i = getGlobalSecondaryIndexes().hashCode();
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
        if (obj == null || !(obj instanceof ConsumedCapacity)) {
            return false;
        }
        ConsumedCapacity other = (ConsumedCapacity) obj;
        if ((other.getTableName() == null) ^ (getTableName() == null)) {
            return false;
        }
        if (other.getTableName() != null && !other.getTableName().equals(getTableName())) {
            return false;
        }
        if (other.getCapacityUnits() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getCapacityUnits() == null)) {
            return false;
        }
        if (other.getCapacityUnits() != null && !other.getCapacityUnits().equals(getCapacityUnits())) {
            return false;
        }
        if (other.getTable() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getTable() == null)) {
            return false;
        }
        if (other.getTable() != null && !other.getTable().equals(getTable())) {
            return false;
        }
        if (other.getLocalSecondaryIndexes() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getLocalSecondaryIndexes() == null)) {
            return false;
        }
        if (other.getLocalSecondaryIndexes() != null && !other.getLocalSecondaryIndexes().equals(getLocalSecondaryIndexes())) {
            return false;
        }
        if (other.getGlobalSecondaryIndexes() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getGlobalSecondaryIndexes() == null)) {
            return false;
        }
        if (other.getGlobalSecondaryIndexes() == null || other.getGlobalSecondaryIndexes().equals(getGlobalSecondaryIndexes())) {
            return true;
        }
        return false;
    }
}
