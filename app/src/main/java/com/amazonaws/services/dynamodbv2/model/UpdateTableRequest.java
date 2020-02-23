package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UpdateTableRequest extends AmazonWebServiceRequest implements Serializable {
    private List<AttributeDefinition> attributeDefinitions;
    private List<GlobalSecondaryIndexUpdate> globalSecondaryIndexUpdates;
    private ProvisionedThroughput provisionedThroughput;
    private StreamSpecification streamSpecification;
    private String tableName;

    public UpdateTableRequest() {
    }

    public UpdateTableRequest(String tableName2, ProvisionedThroughput provisionedThroughput2) {
        setTableName(tableName2);
        setProvisionedThroughput(provisionedThroughput2);
    }

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

    public UpdateTableRequest withAttributeDefinitions(AttributeDefinition... attributeDefinitions2) {
        if (getAttributeDefinitions() == null) {
            this.attributeDefinitions = new ArrayList(attributeDefinitions2.length);
        }
        for (AttributeDefinition value : attributeDefinitions2) {
            this.attributeDefinitions.add(value);
        }
        return this;
    }

    public UpdateTableRequest withAttributeDefinitions(Collection<AttributeDefinition> attributeDefinitions2) {
        setAttributeDefinitions(attributeDefinitions2);
        return this;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public UpdateTableRequest withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public ProvisionedThroughput getProvisionedThroughput() {
        return this.provisionedThroughput;
    }

    public void setProvisionedThroughput(ProvisionedThroughput provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
    }

    public UpdateTableRequest withProvisionedThroughput(ProvisionedThroughput provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
        return this;
    }

    public List<GlobalSecondaryIndexUpdate> getGlobalSecondaryIndexUpdates() {
        return this.globalSecondaryIndexUpdates;
    }

    public void setGlobalSecondaryIndexUpdates(Collection<GlobalSecondaryIndexUpdate> globalSecondaryIndexUpdates2) {
        if (globalSecondaryIndexUpdates2 == null) {
            this.globalSecondaryIndexUpdates = null;
        } else {
            this.globalSecondaryIndexUpdates = new ArrayList(globalSecondaryIndexUpdates2);
        }
    }

    public UpdateTableRequest withGlobalSecondaryIndexUpdates(GlobalSecondaryIndexUpdate... globalSecondaryIndexUpdates2) {
        if (getGlobalSecondaryIndexUpdates() == null) {
            this.globalSecondaryIndexUpdates = new ArrayList(globalSecondaryIndexUpdates2.length);
        }
        for (GlobalSecondaryIndexUpdate value : globalSecondaryIndexUpdates2) {
            this.globalSecondaryIndexUpdates.add(value);
        }
        return this;
    }

    public UpdateTableRequest withGlobalSecondaryIndexUpdates(Collection<GlobalSecondaryIndexUpdate> globalSecondaryIndexUpdates2) {
        setGlobalSecondaryIndexUpdates(globalSecondaryIndexUpdates2);
        return this;
    }

    public StreamSpecification getStreamSpecification() {
        return this.streamSpecification;
    }

    public void setStreamSpecification(StreamSpecification streamSpecification2) {
        this.streamSpecification = streamSpecification2;
    }

    public UpdateTableRequest withStreamSpecification(StreamSpecification streamSpecification2) {
        this.streamSpecification = streamSpecification2;
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
        if (getProvisionedThroughput() != null) {
            sb.append("ProvisionedThroughput: " + getProvisionedThroughput() + ",");
        }
        if (getGlobalSecondaryIndexUpdates() != null) {
            sb.append("GlobalSecondaryIndexUpdates: " + getGlobalSecondaryIndexUpdates() + ",");
        }
        if (getStreamSpecification() != null) {
            sb.append("StreamSpecification: " + getStreamSpecification());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((((((getAttributeDefinitions() == null ? 0 : getAttributeDefinitions().hashCode()) + 31) * 31) + (getTableName() == null ? 0 : getTableName().hashCode())) * 31) + (getProvisionedThroughput() == null ? 0 : getProvisionedThroughput().hashCode())) * 31;
        if (getGlobalSecondaryIndexUpdates() == null) {
            hashCode = 0;
        } else {
            hashCode = getGlobalSecondaryIndexUpdates().hashCode();
        }
        int i2 = (hashCode2 + hashCode) * 31;
        if (getStreamSpecification() != null) {
            i = getStreamSpecification().hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateTableRequest)) {
            return false;
        }
        UpdateTableRequest other = (UpdateTableRequest) obj;
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
        if (other.getProvisionedThroughput() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getProvisionedThroughput() == null)) {
            return false;
        }
        if (other.getProvisionedThroughput() != null && !other.getProvisionedThroughput().equals(getProvisionedThroughput())) {
            return false;
        }
        if (other.getGlobalSecondaryIndexUpdates() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (getGlobalSecondaryIndexUpdates() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z3 ^ z4) {
            return false;
        }
        if (other.getGlobalSecondaryIndexUpdates() != null && !other.getGlobalSecondaryIndexUpdates().equals(getGlobalSecondaryIndexUpdates())) {
            return false;
        }
        if (other.getStreamSpecification() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getStreamSpecification() == null)) {
            return false;
        }
        if (other.getStreamSpecification() == null || other.getStreamSpecification().equals(getStreamSpecification())) {
            return true;
        }
        return false;
    }
}
