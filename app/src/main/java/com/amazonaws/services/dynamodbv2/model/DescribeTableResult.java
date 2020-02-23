package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class DescribeTableResult implements Serializable {
    private TableDescription table;

    public TableDescription getTable() {
        return this.table;
    }

    public void setTable(TableDescription table2) {
        this.table = table2;
    }

    public DescribeTableResult withTable(TableDescription table2) {
        this.table = table2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTable() != null) {
            sb.append("Table: " + getTable());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getTable() == null ? 0 : getTable().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeTableResult)) {
            return false;
        }
        DescribeTableResult other = (DescribeTableResult) obj;
        if (other.getTable() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getTable() == null)) {
            return false;
        }
        if (other.getTable() == null || other.getTable().equals(getTable())) {
            return true;
        }
        return false;
    }
}
