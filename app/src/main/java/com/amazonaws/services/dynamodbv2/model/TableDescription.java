package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TableDescription implements Serializable {
    private List<AttributeDefinition> attributeDefinitions;
    private Date creationDateTime;
    private List<GlobalSecondaryIndexDescription> globalSecondaryIndexes;
    private Long itemCount;
    private List<KeySchemaElement> keySchema;
    private String latestStreamArn;
    private String latestStreamLabel;
    private List<LocalSecondaryIndexDescription> localSecondaryIndexes;
    private ProvisionedThroughputDescription provisionedThroughput;
    private StreamSpecification streamSpecification;
    private String tableArn;
    private String tableName;
    private Long tableSizeBytes;
    private String tableStatus;

    public List<AttributeDefinition> getAttributeDefinitions() {
        return this.attributeDefinitions;
    }

    public void setAttributeDefinitions(Collection<AttributeDefinition> attributeDefinitions2) {
        if (attributeDefinitions2 == null) {
            this.attributeDefinitions = null;
        } else {
            this.attributeDefinitions = new ArrayList(attributeDefinitions2);
        }
    }

    public TableDescription withAttributeDefinitions(AttributeDefinition... attributeDefinitions2) {
        if (getAttributeDefinitions() == null) {
            this.attributeDefinitions = new ArrayList(attributeDefinitions2.length);
        }
        for (AttributeDefinition value : attributeDefinitions2) {
            this.attributeDefinitions.add(value);
        }
        return this;
    }

    public TableDescription withAttributeDefinitions(Collection<AttributeDefinition> attributeDefinitions2) {
        setAttributeDefinitions(attributeDefinitions2);
        return this;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public TableDescription withTableName(String tableName2) {
        this.tableName = tableName2;
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

    public TableDescription withKeySchema(KeySchemaElement... keySchema2) {
        if (getKeySchema() == null) {
            this.keySchema = new ArrayList(keySchema2.length);
        }
        for (KeySchemaElement value : keySchema2) {
            this.keySchema.add(value);
        }
        return this;
    }

    public TableDescription withKeySchema(Collection<KeySchemaElement> keySchema2) {
        setKeySchema(keySchema2);
        return this;
    }

    public String getTableStatus() {
        return this.tableStatus;
    }

    public void setTableStatus(String tableStatus2) {
        this.tableStatus = tableStatus2;
    }

    public TableDescription withTableStatus(String tableStatus2) {
        this.tableStatus = tableStatus2;
        return this;
    }

    public void setTableStatus(TableStatus tableStatus2) {
        this.tableStatus = tableStatus2.toString();
    }

    public TableDescription withTableStatus(TableStatus tableStatus2) {
        this.tableStatus = tableStatus2.toString();
        return this;
    }

    public Date getCreationDateTime() {
        return this.creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime2) {
        this.creationDateTime = creationDateTime2;
    }

    public TableDescription withCreationDateTime(Date creationDateTime2) {
        this.creationDateTime = creationDateTime2;
        return this;
    }

    public ProvisionedThroughputDescription getProvisionedThroughput() {
        return this.provisionedThroughput;
    }

    public void setProvisionedThroughput(ProvisionedThroughputDescription provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
    }

    public TableDescription withProvisionedThroughput(ProvisionedThroughputDescription provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
        return this;
    }

    public Long getTableSizeBytes() {
        return this.tableSizeBytes;
    }

    public void setTableSizeBytes(Long tableSizeBytes2) {
        this.tableSizeBytes = tableSizeBytes2;
    }

    public TableDescription withTableSizeBytes(Long tableSizeBytes2) {
        this.tableSizeBytes = tableSizeBytes2;
        return this;
    }

    public Long getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Long itemCount2) {
        this.itemCount = itemCount2;
    }

    public TableDescription withItemCount(Long itemCount2) {
        this.itemCount = itemCount2;
        return this;
    }

    public String getTableArn() {
        return this.tableArn;
    }

    public void setTableArn(String tableArn2) {
        this.tableArn = tableArn2;
    }

    public TableDescription withTableArn(String tableArn2) {
        this.tableArn = tableArn2;
        return this;
    }

    public List<LocalSecondaryIndexDescription> getLocalSecondaryIndexes() {
        return this.localSecondaryIndexes;
    }

    public void setLocalSecondaryIndexes(Collection<LocalSecondaryIndexDescription> localSecondaryIndexes2) {
        if (localSecondaryIndexes2 == null) {
            this.localSecondaryIndexes = null;
        } else {
            this.localSecondaryIndexes = new ArrayList(localSecondaryIndexes2);
        }
    }

    public TableDescription withLocalSecondaryIndexes(LocalSecondaryIndexDescription... localSecondaryIndexes2) {
        if (getLocalSecondaryIndexes() == null) {
            this.localSecondaryIndexes = new ArrayList(localSecondaryIndexes2.length);
        }
        for (LocalSecondaryIndexDescription value : localSecondaryIndexes2) {
            this.localSecondaryIndexes.add(value);
        }
        return this;
    }

    public TableDescription withLocalSecondaryIndexes(Collection<LocalSecondaryIndexDescription> localSecondaryIndexes2) {
        setLocalSecondaryIndexes(localSecondaryIndexes2);
        return this;
    }

    public List<GlobalSecondaryIndexDescription> getGlobalSecondaryIndexes() {
        return this.globalSecondaryIndexes;
    }

    public void setGlobalSecondaryIndexes(Collection<GlobalSecondaryIndexDescription> globalSecondaryIndexes2) {
        if (globalSecondaryIndexes2 == null) {
            this.globalSecondaryIndexes = null;
        } else {
            this.globalSecondaryIndexes = new ArrayList(globalSecondaryIndexes2);
        }
    }

    public TableDescription withGlobalSecondaryIndexes(GlobalSecondaryIndexDescription... globalSecondaryIndexes2) {
        if (getGlobalSecondaryIndexes() == null) {
            this.globalSecondaryIndexes = new ArrayList(globalSecondaryIndexes2.length);
        }
        for (GlobalSecondaryIndexDescription value : globalSecondaryIndexes2) {
            this.globalSecondaryIndexes.add(value);
        }
        return this;
    }

    public TableDescription withGlobalSecondaryIndexes(Collection<GlobalSecondaryIndexDescription> globalSecondaryIndexes2) {
        setGlobalSecondaryIndexes(globalSecondaryIndexes2);
        return this;
    }

    public StreamSpecification getStreamSpecification() {
        return this.streamSpecification;
    }

    public void setStreamSpecification(StreamSpecification streamSpecification2) {
        this.streamSpecification = streamSpecification2;
    }

    public TableDescription withStreamSpecification(StreamSpecification streamSpecification2) {
        this.streamSpecification = streamSpecification2;
        return this;
    }

    public String getLatestStreamLabel() {
        return this.latestStreamLabel;
    }

    public void setLatestStreamLabel(String latestStreamLabel2) {
        this.latestStreamLabel = latestStreamLabel2;
    }

    public TableDescription withLatestStreamLabel(String latestStreamLabel2) {
        this.latestStreamLabel = latestStreamLabel2;
        return this;
    }

    public String getLatestStreamArn() {
        return this.latestStreamArn;
    }

    public void setLatestStreamArn(String latestStreamArn2) {
        this.latestStreamArn = latestStreamArn2;
    }

    public TableDescription withLatestStreamArn(String latestStreamArn2) {
        this.latestStreamArn = latestStreamArn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributeDefinitions() != null) {
            sb.append("AttributeDefinitions: " + getAttributeDefinitions() + ",");
        }
        if (getTableName() != null) {
            sb.append("TableName: " + getTableName() + ",");
        }
        if (getKeySchema() != null) {
            sb.append("KeySchema: " + getKeySchema() + ",");
        }
        if (getTableStatus() != null) {
            sb.append("TableStatus: " + getTableStatus() + ",");
        }
        if (getCreationDateTime() != null) {
            sb.append("CreationDateTime: " + getCreationDateTime() + ",");
        }
        if (getProvisionedThroughput() != null) {
            sb.append("ProvisionedThroughput: " + getProvisionedThroughput() + ",");
        }
        if (getTableSizeBytes() != null) {
            sb.append("TableSizeBytes: " + getTableSizeBytes() + ",");
        }
        if (getItemCount() != null) {
            sb.append("ItemCount: " + getItemCount() + ",");
        }
        if (getTableArn() != null) {
            sb.append("TableArn: " + getTableArn() + ",");
        }
        if (getLocalSecondaryIndexes() != null) {
            sb.append("LocalSecondaryIndexes: " + getLocalSecondaryIndexes() + ",");
        }
        if (getGlobalSecondaryIndexes() != null) {
            sb.append("GlobalSecondaryIndexes: " + getGlobalSecondaryIndexes() + ",");
        }
        if (getStreamSpecification() != null) {
            sb.append("StreamSpecification: " + getStreamSpecification() + ",");
        }
        if (getLatestStreamLabel() != null) {
            sb.append("LatestStreamLabel: " + getLatestStreamLabel() + ",");
        }
        if (getLatestStreamArn() != null) {
            sb.append("LatestStreamArn: " + getLatestStreamArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((((((((((((((((((((getAttributeDefinitions() == null ? 0 : getAttributeDefinitions().hashCode()) + 31) * 31) + (getTableName() == null ? 0 : getTableName().hashCode())) * 31) + (getKeySchema() == null ? 0 : getKeySchema().hashCode())) * 31) + (getTableStatus() == null ? 0 : getTableStatus().hashCode())) * 31) + (getCreationDateTime() == null ? 0 : getCreationDateTime().hashCode())) * 31) + (getProvisionedThroughput() == null ? 0 : getProvisionedThroughput().hashCode())) * 31) + (getTableSizeBytes() == null ? 0 : getTableSizeBytes().hashCode())) * 31) + (getItemCount() == null ? 0 : getItemCount().hashCode())) * 31) + (getTableArn() == null ? 0 : getTableArn().hashCode())) * 31) + (getLocalSecondaryIndexes() == null ? 0 : getLocalSecondaryIndexes().hashCode())) * 31;
        if (getGlobalSecondaryIndexes() == null) {
            hashCode = 0;
        } else {
            hashCode = getGlobalSecondaryIndexes().hashCode();
        }
        int hashCode3 = (((((hashCode2 + hashCode) * 31) + (getStreamSpecification() == null ? 0 : getStreamSpecification().hashCode())) * 31) + (getLatestStreamLabel() == null ? 0 : getLatestStreamLabel().hashCode())) * 31;
        if (getLatestStreamArn() != null) {
            i = getLatestStreamArn().hashCode();
        }
        return hashCode3 + i;
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
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TableDescription)) {
            return false;
        }
        TableDescription other = (TableDescription) obj;
        if ((other.getAttributeDefinitions() == null) ^ (getAttributeDefinitions() == null)) {
            return false;
        }
        if (other.getAttributeDefinitions() != null && !other.getAttributeDefinitions().equals(getAttributeDefinitions())) {
            return false;
        }
        if (other.getTableName() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getTableName() == null)) {
            return false;
        }
        if (other.getTableName() != null && !other.getTableName().equals(getTableName())) {
            return false;
        }
        if (other.getKeySchema() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getKeySchema() == null)) {
            return false;
        }
        if (other.getKeySchema() != null && !other.getKeySchema().equals(getKeySchema())) {
            return false;
        }
        if (other.getTableStatus() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getTableStatus() == null)) {
            return false;
        }
        if (other.getTableStatus() != null && !other.getTableStatus().equals(getTableStatus())) {
            return false;
        }
        if (other.getCreationDateTime() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getCreationDateTime() == null)) {
            return false;
        }
        if (other.getCreationDateTime() != null && !other.getCreationDateTime().equals(getCreationDateTime())) {
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
        if (other.getTableSizeBytes() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getTableSizeBytes() == null)) {
            return false;
        }
        if (other.getTableSizeBytes() != null && !other.getTableSizeBytes().equals(getTableSizeBytes())) {
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
        if (other.getTableArn() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 ^ (getTableArn() == null)) {
            return false;
        }
        if (other.getTableArn() != null && !other.getTableArn().equals(getTableArn())) {
            return false;
        }
        if (other.getLocalSecondaryIndexes() == null) {
            z9 = true;
        } else {
            z9 = false;
        }
        if (z9 ^ (getLocalSecondaryIndexes() == null)) {
            return false;
        }
        if (other.getLocalSecondaryIndexes() != null && !other.getLocalSecondaryIndexes().equals(getLocalSecondaryIndexes())) {
            return false;
        }
        if (other.getGlobalSecondaryIndexes() == null) {
            z10 = true;
        } else {
            z10 = false;
        }
        if (z10 ^ (getGlobalSecondaryIndexes() == null)) {
            return false;
        }
        if (other.getGlobalSecondaryIndexes() != null && !other.getGlobalSecondaryIndexes().equals(getGlobalSecondaryIndexes())) {
            return false;
        }
        if (other.getStreamSpecification() == null) {
            z11 = true;
        } else {
            z11 = false;
        }
        if (z11 ^ (getStreamSpecification() == null)) {
            return false;
        }
        if (other.getStreamSpecification() != null && !other.getStreamSpecification().equals(getStreamSpecification())) {
            return false;
        }
        if (other.getLatestStreamLabel() == null) {
            z12 = true;
        } else {
            z12 = false;
        }
        if (z12 ^ (getLatestStreamLabel() == null)) {
            return false;
        }
        if (other.getLatestStreamLabel() != null && !other.getLatestStreamLabel().equals(getLatestStreamLabel())) {
            return false;
        }
        if (other.getLatestStreamArn() == null) {
            z13 = true;
        } else {
            z13 = false;
        }
        if (z13 ^ (getLatestStreamArn() == null)) {
            return false;
        }
        if (other.getLatestStreamArn() == null || other.getLatestStreamArn().equals(getLatestStreamArn())) {
            return true;
        }
        return false;
    }
}
