package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class DeleteGlobalSecondaryIndexAction implements Serializable {
    private String indexName;

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName2) {
        this.indexName = indexName2;
    }

    public DeleteGlobalSecondaryIndexAction withIndexName(String indexName2) {
        this.indexName = indexName2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIndexName() != null) {
            sb.append("IndexName: " + getIndexName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (getIndexName() == null ? 0 : getIndexName().hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteGlobalSecondaryIndexAction)) {
            return false;
        }
        DeleteGlobalSecondaryIndexAction other = (DeleteGlobalSecondaryIndexAction) obj;
        if ((other.getIndexName() == null) ^ (getIndexName() == null)) {
            return false;
        }
        if (other.getIndexName() == null || other.getIndexName().equals(getIndexName())) {
            return true;
        }
        return false;
    }
}
