package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GlobalSecondaryIndexDescription implements Serializable {
    private Boolean backfilling;
    private String indexArn;
    private String indexName;
    private Long indexSizeBytes;
    private String indexStatus;
    private Long itemCount;
    private List<KeySchemaElement> keySchema;
    private Projection projection;
    private ProvisionedThroughputDescription provisionedThroughput;

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName2) {
        this.indexName = indexName2;
    }

    public GlobalSecondaryIndexDescription withIndexName(String indexName2) {
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

    public GlobalSecondaryIndexDescription withKeySchema(KeySchemaElement... keySchema2) {
        if (getKeySchema() == null) {
            this.keySchema = new ArrayList(keySchema2.length);
        }
        for (KeySchemaElement value : keySchema2) {
            this.keySchema.add(value);
        }
        return this;
    }

    public GlobalSecondaryIndexDescription withKeySchema(Collection<KeySchemaElement> keySchema2) {
        setKeySchema(keySchema2);
        return this;
    }

    public Projection getProjection() {
        return this.projection;
    }

    public void setProjection(Projection projection2) {
        this.projection = projection2;
    }

    public GlobalSecondaryIndexDescription withProjection(Projection projection2) {
        this.projection = projection2;
        return this;
    }

    public String getIndexStatus() {
        return this.indexStatus;
    }

    public void setIndexStatus(String indexStatus2) {
        this.indexStatus = indexStatus2;
    }

    public GlobalSecondaryIndexDescription withIndexStatus(String indexStatus2) {
        this.indexStatus = indexStatus2;
        return this;
    }

    public void setIndexStatus(IndexStatus indexStatus2) {
        this.indexStatus = indexStatus2.toString();
    }

    public GlobalSecondaryIndexDescription withIndexStatus(IndexStatus indexStatus2) {
        this.indexStatus = indexStatus2.toString();
        return this;
    }

    public Boolean isBackfilling() {
        return this.backfilling;
    }

    public Boolean getBackfilling() {
        return this.backfilling;
    }

    public void setBackfilling(Boolean backfilling2) {
        this.backfilling = backfilling2;
    }

    public GlobalSecondaryIndexDescription withBackfilling(Boolean backfilling2) {
        this.backfilling = backfilling2;
        return this;
    }

    public ProvisionedThroughputDescription getProvisionedThroughput() {
        return this.provisionedThroughput;
    }

    public void setProvisionedThroughput(ProvisionedThroughputDescription provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
    }

    public GlobalSecondaryIndexDescription withProvisionedThroughput(ProvisionedThroughputDescription provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
        return this;
    }

    public Long getIndexSizeBytes() {
        return this.indexSizeBytes;
    }

    public void setIndexSizeBytes(Long indexSizeBytes2) {
        this.indexSizeBytes = indexSizeBytes2;
    }

    public GlobalSecondaryIndexDescription withIndexSizeBytes(Long indexSizeBytes2) {
        this.indexSizeBytes = indexSizeBytes2;
        return this;
    }

    public Long getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Long itemCount2) {
        this.itemCount = itemCount2;
    }

    public GlobalSecondaryIndexDescription withItemCount(Long itemCount2) {
        this.itemCount = itemCount2;
        return this;
    }

    public String getIndexArn() {
        return this.indexArn;
    }

    public void setIndexArn(String indexArn2) {
        this.indexArn = indexArn2;
    }

    public GlobalSecondaryIndexDescription withIndexArn(String indexArn2) {
        this.indexArn = indexArn2;
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
            sb.append("Projection: " + getProjection() + ",");
        }
        if (getIndexStatus() != null) {
            sb.append("IndexStatus: " + getIndexStatus() + ",");
        }
        if (getBackfilling() != null) {
            sb.append("Backfilling: " + getBackfilling() + ",");
        }
        if (getProvisionedThroughput() != null) {
            sb.append("ProvisionedThroughput: " + getProvisionedThroughput() + ",");
        }
        if (getIndexSizeBytes() != null) {
            sb.append("IndexSizeBytes: " + getIndexSizeBytes() + ",");
        }
        if (getItemCount() != null) {
            sb.append("ItemCount: " + getItemCount() + ",");
        }
        if (getIndexArn() != null) {
            sb.append("IndexArn: " + getIndexArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((getIndexName() == null ? 0 : getIndexName().hashCode()) + 31) * 31) + (getKeySchema() == null ? 0 : getKeySchema().hashCode())) * 31) + (getProjection() == null ? 0 : getProjection().hashCode())) * 31) + (getIndexStatus() == null ? 0 : getIndexStatus().hashCode())) * 31) + (getBackfilling() == null ? 0 : getBackfilling().hashCode())) * 31) + (getProvisionedThroughput() == null ? 0 : getProvisionedThroughput().hashCode())) * 31) + (getIndexSizeBytes() == null ? 0 : getIndexSizeBytes().hashCode())) * 31) + (getItemCount() == null ? 0 : getItemCount().hashCode())) * 31;
        if (getIndexArn() != null) {
            i = getIndexArn().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GlobalSecondaryIndexDescription)) {
            return false;
        }
        GlobalSecondaryIndexDescription other = (GlobalSecondaryIndexDescription) obj;
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
        if (other.getProjection() != null && !other.getProjection().equals(getProjection())) {
            return false;
        }
        if (other.getIndexStatus() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getIndexStatus() == null)) {
            return false;
        }
        if (other.getIndexStatus() != null && !other.getIndexStatus().equals(getIndexStatus())) {
            return false;
        }
        if (other.getBackfilling() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getBackfilling() == null)) {
            return false;
        }
        if (other.getBackfilling() != null && !other.getBackfilling().equals(getBackfilling())) {
            return false;
        }
        if (other.getProvisionedThroughput() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getProvisionedThroughput() == null)) {
            return false;
        }
        if (other.getProvisionedThroughput() != null && !other.getProvisionedThroughput().equals(getProvisionedThroughput())) {
            return false;
        }
        if (other.getIndexSizeBytes() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getIndexSizeBytes() == null)) {
            return false;
        }
        if (other.getIndexSizeBytes() != null && !other.getIndexSizeBytes().equals(getIndexSizeBytes())) {
            return false;
        }
        if (other.getItemCount() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z7 ^ (getItemCount() == null)) {
            return false;
        }
        if (other.getItemCount() != null && !other.getItemCount().equals(getItemCount())) {
            return false;
        }
        if (other.getIndexArn() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 ^ (getIndexArn() == null)) {
            return false;
        }
        if (other.getIndexArn() == null || other.getIndexArn().equals(getIndexArn())) {
            return true;
        }
        return false;
    }
}
