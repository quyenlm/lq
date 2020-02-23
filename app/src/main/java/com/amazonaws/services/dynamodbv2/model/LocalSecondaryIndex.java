package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocalSecondaryIndex implements Serializable {
    private String indexName;
    private List<KeySchemaElement> keySchema;
    private Projection projection;

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName2) {
        this.indexName = indexName2;
    }

    public LocalSecondaryIndex withIndexName(String indexName2) {
        this.indexName = indexName2;
        return this;
    }

    public List<KeySchemaElement> getKeySchema() {
        return this.keySchema;
    }

    public void setKeySchema(Collection<KeySchemaElement> keySchema2) {
        if (keySchema2 == null) {
            this.keySchema = null;
        } else {
            this.keySchema = new ArrayList(keySchema2);
        }
    }

    public LocalSecondaryIndex withKeySchema(KeySchemaElement... keySchema2) {
        if (getKeySchema() == null) {
            this.keySchema = new ArrayList(keySchema2.length);
        }
        for (KeySchemaElement value : keySchema2) {
            this.keySchema.add(value);
        }
        return this;
    }

    public LocalSecondaryIndex withKeySchema(Collection<KeySchemaElement> keySchema2) {
        setKeySchema(keySchema2);
        return this;
    }

    public Projection getProjection() {
        return this.projection;
    }

    public void setProjection(Projection projection2) {
        this.projection = projection2;
    }

    public LocalSecondaryIndex withProjection(Projection projection2) {
        this.projection = projection2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIndexName() != null) {
            sb.append("IndexName: " + getIndexName() + ",");
        }
        if (getKeySchema() != null) {
            sb.append("KeySchema: " + getKeySchema() + ",");
        }
        if (getProjection() != null) {
            sb.append("Projection: " + getProjection());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getIndexName() == null ? 0 : getIndexName().hashCode()) + 31) * 31) + (getKeySchema() == null ? 0 : getKeySchema().hashCode())) * 31;
        if (getProjection() != null) {
            i = getProjection().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof LocalSecondaryIndex)) {
            return false;
        }
        LocalSecondaryIndex other = (LocalSecondaryIndex) obj;
        if ((other.getIndexName() == null) ^ (getIndexName() == null)) {
            return false;
        }
        if (other.getIndexName() != null && !other.getIndexName().equals(getIndexName())) {
            return false;
        }
        if (other.getKeySchema() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getKeySchema() == null)) {
            return false;
        }
        if (other.getKeySchema() != null && !other.getKeySchema().equals(getKeySchema())) {
            return false;
        }
        if (other.getProjection() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getProjection() == null)) {
            return false;
        }
        if (other.getProjection() == null || other.getProjection().equals(getProjection())) {
            return true;
        }
        return false;
    }
}
