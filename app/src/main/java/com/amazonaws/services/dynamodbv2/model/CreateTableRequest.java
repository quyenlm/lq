package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateTableRequest extends AmazonWebServiceRequest implements Serializable {
    private List<AttributeDefinition> attributeDefinitions;
    private List<GlobalSecondaryIndex> globalSecondaryIndexes;
    private List<KeySchemaElement> keySchema;
    private List<LocalSecondaryIndex> localSecondaryIndexes;
    private ProvisionedThroughput provisionedThroughput;
    private StreamSpecification streamSpecification;
    private String tableName;

    public CreateTableRequest() {
    }

    public CreateTableRequest(String tableName2, List<KeySchemaElement> keySchema2) {
        setTableName(tableName2);
        setKeySchema(keySchema2);
    }

    public CreateTableRequest(List<AttributeDefinition> attributeDefinitions2, String tableName2, List<KeySchemaElement> keySchema2, ProvisionedThroughput provisionedThroughput2) {
        setAttributeDefinitions(attributeDefinitions2);
        setTableName(tableName2);
        setKeySchema(keySchema2);
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

    public CreateTableRequest withAttributeDefinitions(AttributeDefinition... attributeDefinitions2) {
        if (getAttributeDefinitions() == null) {
            this.attributeDefinitions = new ArrayList(attributeDefinitions2.length);
        }
        for (AttributeDefinition value : attributeDefinitions2) {
            this.attributeDefinitions.add(value);
        }
        return this;
    }

    public CreateTableRequest withAttributeDefinitions(Collection<AttributeDefinition> attributeDefinitions2) {
        setAttributeDefinitions(attributeDefinitions2);
        return this;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public CreateTableRequest withTableName(String tableName2) {
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

    public CreateTableRequest withKeySchema(KeySchemaElement... keySchema2) {
        if (getKeySchema() == null) {
            this.keySchema = new ArrayList(keySchema2.length);
        }
        for (KeySchemaElement value : keySchema2) {
            this.keySchema.add(value);
        }
        return this;
    }

    public CreateTableRequest withKeySchema(Collection<KeySchemaElement> keySchema2) {
        setKeySchema(keySchema2);
        return this;
    }

    public List<LocalSecondaryIndex> getLocalSecondaryIndexes() {
        return this.localSecondaryIndexes;
    }

    public void setLocalSecondaryIndexes(Collection<LocalSecondaryIndex> localSecondaryIndexes2) {
        if (localSecondaryIndexes2 == null) {
            this.localSecondaryIndexes = null;
        } else {
            this.localSecondaryIndexes = new ArrayList(localSecondaryIndexes2);
        }
    }

    public CreateTableRequest withLocalSecondaryIndexes(LocalSecondaryIndex... localSecondaryIndexes2) {
        if (getLocalSecondaryIndexes() == null) {
            this.localSecondaryIndexes = new ArrayList(localSecondaryIndexes2.length);
        }
        for (LocalSecondaryIndex value : localSecondaryIndexes2) {
            this.localSecondaryIndexes.add(value);
        }
        return this;
    }

    public CreateTableRequest withLocalSecondaryIndexes(Collection<LocalSecondaryIndex> localSecondaryIndexes2) {
        setLocalSecondaryIndexes(localSecondaryIndexes2);
        return this;
    }

    public List<GlobalSecondaryIndex> getGlobalSecondaryIndexes() {
        return this.globalSecondaryIndexes;
    }

    public void setGlobalSecondaryIndexes(Collection<GlobalSecondaryIndex> globalSecondaryIndexes2) {
        if (globalSecondaryIndexes2 == null) {
            this.globalSecondaryIndexes = null;
        } else {
            this.globalSecondaryIndexes = new ArrayList(globalSecondaryIndexes2);
        }
    }

    public CreateTableRequest withGlobalSecondaryIndexes(GlobalSecondaryIndex... globalSecondaryIndexes2) {
        if (getGlobalSecondaryIndexes() == null) {
            this.globalSecondaryIndexes = new ArrayList(globalSecondaryIndexes2.length);
        }
        for (GlobalSecondaryIndex value : globalSecondaryIndexes2) {
            this.globalSecondaryIndexes.add(value);
        }
        return this;
    }

    public CreateTableRequest withGlobalSecondaryIndexes(Collection<GlobalSecondaryIndex> globalSecondaryIndexes2) {
        setGlobalSecondaryIndexes(globalSecondaryIndexes2);
        return this;
    }

    public ProvisionedThroughput getProvisionedThroughput() {
        return this.provisionedThroughput;
    }

    public void setProvisionedThroughput(ProvisionedThroughput provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
    }

    public CreateTableRequest withProvisionedThroughput(ProvisionedThroughput provisionedThroughput2) {
        this.provisionedThroughput = provisionedThroughput2;
        return this;
    }

    public StreamSpecification getStreamSpecification() {
        return this.streamSpecification;
    }

    public void setStreamSpecification(StreamSpecification streamSpecification2) {
        this.streamSpecification = streamSpecification2;
    }

    public CreateTableRequest withStreamSpecification(StreamSpecification streamSpecification2) {
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
        if (getKeySchema() != null) {
            sb.append("KeySchema: " + getKeySchema() + ",");
        }
        if (getLocalSecondaryIndexes() != null) {
            sb.append("LocalSecondaryIndexes: " + getLocalSecondaryIndexes() + ",");
        }
        if (getGlobalSecondaryIndexes() != null) {
            sb.append("GlobalSecondaryIndexes: " + getGlobalSecondaryIndexes() + ",");
        }
        if (getProvisionedThroughput() != null) {
            sb.append("ProvisionedThroughput: " + getProvisionedThroughput() + ",");
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
        int hashCode2 = ((((((((getAttributeDefinitions() == null ? 0 : getAttributeDefinitions().hashCode()) + 31) * 31) + (getTableName() == null ? 0 : getTableName().hashCode())) * 31) + (getKeySchema() == null ? 0 : getKeySchema().hashCode())) * 31) + (getLocalSecondaryIndexes() == null ? 0 : getLocalSecondaryIndexes().hashCode())) * 31;
        if (getGlobalSecondaryIndexes() == null) {
            hashCode = 0;
        } else {
            hashCode = getGlobalSecondaryIndexes().hashCode();
        }
        int hashCode3 = (((hashCode2 + hashCode) * 31) + (getProvisionedThroughput() == null ? 0 : getProvisionedThroughput().hashCode())) * 31;
        if (getStreamSpecification() != null) {
            i = getStreamSpecification().hashCode();
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
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateTableRequest)) {
            return false;
        }
        CreateTableRequest other = (CreateTableRequest) obj;
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
        if (other.getGlobalSecondaryIndexes() != null && !other.getGlobalSecondaryIndexes().equals(getGlobalSecondaryIndexes())) {
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
        if (other.getStreamSpecification() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getStreamSpecification() == null)) {
            return false;
        }
        if (other.getStreamSpecification() == null || other.getStreamSpecification().equals(getStreamSpecification())) {
            return true;
        }
        return false;
    }
}
