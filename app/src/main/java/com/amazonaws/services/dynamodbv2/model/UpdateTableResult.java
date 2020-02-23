package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class UpdateTableResult implements Serializable {
    private TableDescription tableDescription;

    public TableDescription getTableDescription() {
        return this.tableDescription;
    }

    public void setTableDescription(TableDescription tableDescription2) {
        this.tableDescription = tableDescription2;
    }

    public UpdateTableResult withTableDescription(TableDescription tableDescription2) {
        this.tableDescription = tableDescription2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTableDescription() != null) {
            sb.append("TableDescription: " + getTableDescription());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getTableDescription() == null ? 0 : getTableDescription().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateTableResult)) {
            return false;
        }
        UpdateTableResult other = (UpdateTableResult) obj;
        if ((other.getTableDescription() == null) ^ (getTableDescription() == null)) {
            return false;
        }
        if (other.getTableDescription() == null || other.getTableDescription().equals(getTableDescription())) {
            return true;
        }
        return false;
    }
}
