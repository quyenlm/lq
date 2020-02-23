package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListTablesRequest extends AmazonWebServiceRequest implements Serializable {
    private String exclusiveStartTableName;
    private Integer limit;

    public ListTablesRequest() {
    }

    public ListTablesRequest(String exclusiveStartTableName2) {
        setExclusiveStartTableName(exclusiveStartTableName2);
    }

    public ListTablesRequest(String exclusiveStartTableName2, Integer limit2) {
        setExclusiveStartTableName(exclusiveStartTableName2);
        setLimit(limit2);
    }

    public String getExclusiveStartTableName() {
        return this.exclusiveStartTableName;
    }

    public void setExclusiveStartTableName(String exclusiveStartTableName2) {
        this.exclusiveStartTableName = exclusiveStartTableName2;
    }

    public ListTablesRequest withExclusiveStartTableName(String exclusiveStartTableName2) {
        this.exclusiveStartTableName = exclusiveStartTableName2;
        return this;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit2) {
        this.limit = limit2;
    }

    public ListTablesRequest withLimit(Integer limit2) {
        this.limit = limit2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getExclusiveStartTableName() != null) {
            sb.append("ExclusiveStartTableName: " + getExclusiveStartTableName() + ",");
        }
        if (getLimit() != null) {
            sb.append("Limit: " + getLimit());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (getExclusiveStartTableName() == null) {
            hashCode = 0;
        } else {
            hashCode = getExclusiveStartTableName().hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (getLimit() != null) {
            i = getLimit().hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTablesRequest)) {
            return false;
        }
        ListTablesRequest other = (ListTablesRequest) obj;
        if ((other.getExclusiveStartTableName() == null) ^ (getExclusiveStartTableName() == null)) {
            return false;
        }
        if (other.getExclusiveStartTableName() != null && !other.getExclusiveStartTableName().equals(getExclusiveStartTableName())) {
            return false;
        }
        if (other.getLimit() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getLimit() == null)) {
            return false;
        }
        if (other.getLimit() == null || other.getLimit().equals(getLimit())) {
            return true;
        }
        return false;
    }
}
