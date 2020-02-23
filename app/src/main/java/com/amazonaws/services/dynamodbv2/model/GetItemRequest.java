package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetItemRequest extends AmazonWebServiceRequest implements Serializable {
    private List<String> attributesToGet;
    private Boolean consistentRead;
    private Map<String, String> expressionAttributeNames;
    private Map<String, AttributeValue> key;
    private String projectionExpression;
    private String returnConsumedCapacity;
    private String tableName;

    public GetItemRequest() {
    }

    public GetItemRequest(String tableName2, Map<String, AttributeValue> key2) {
        setTableName(tableName2);
        setKey(key2);
    }

    public GetItemRequest(String tableName2, Map<String, AttributeValue> key2, Boolean consistentRead2) {
        setTableName(tableName2);
        setKey(key2);
        setConsistentRead(consistentRead2);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public GetItemRequest withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public Map<String, AttributeValue> getKey() {
        return this.key;
    }

    public void setKey(Map<String, AttributeValue> key2) {
        this.key = key2;
    }

    public GetItemRequest withKey(Map<String, AttributeValue> key2) {
        this.key = key2;
        return this;
    }

    public GetItemRequest addKeyEntry(String key2, AttributeValue value) {
        if (this.key == null) {
            this.key = new HashMap();
        }
        if (this.key.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.key.put(key2, value);
        return this;
    }

    public GetItemRequest clearKeyEntries() {
        this.key = null;
        return this;
    }

    public List<String> getAttributesToGet() {
        return this.attributesToGet;
    }

    public void setAttributesToGet(Collection<String> attributesToGet2) {
        if (attributesToGet2 == null) {
            this.attributesToGet = null;
        } else {
            this.attributesToGet = new ArrayList(attributesToGet2);
        }
    }

    public GetItemRequest withAttributesToGet(String... attributesToGet2) {
        if (getAttributesToGet() == null) {
            this.attributesToGet = new ArrayList(attributesToGet2.length);
        }
        for (String value : attributesToGet2) {
            this.attributesToGet.add(value);
        }
        return this;
    }

    public GetItemRequest withAttributesToGet(Collection<String> attributesToGet2) {
        setAttributesToGet(attributesToGet2);
        return this;
    }

    public Boolean isConsistentRead() {
        return this.consistentRead;
    }

    public Boolean getConsistentRead() {
        return this.consistentRead;
    }

    public void setConsistentRead(Boolean consistentRead2) {
        this.consistentRead = consistentRead2;
    }

    public GetItemRequest withConsistentRead(Boolean consistentRead2) {
        this.consistentRead = consistentRead2;
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public GetItemRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public GetItemRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public String getProjectionExpression() {
        return this.projectionExpression;
    }

    public void setProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
    }

    public GetItemRequest withProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
    }

    public GetItemRequest withExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
        return this;
    }

    public GetItemRequest addExpressionAttributeNamesEntry(String key2, String value) {
        if (this.expressionAttributeNames == null) {
            this.expressionAttributeNames = new HashMap();
        }
        if (this.expressionAttributeNames.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expressionAttributeNames.put(key2, value);
        return this;
    }

    public GetItemRequest clearExpressionAttributeNamesEntries() {
        this.expressionAttributeNames = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTableName() != null) {
            sb.append("TableName: " + getTableName() + ",");
        }
        if (getKey() != null) {
            sb.append("Key: " + getKey() + ",");
        }
        if (getAttributesToGet() != null) {
            sb.append("AttributesToGet: " + getAttributesToGet() + ",");
        }
        if (getConsistentRead() != null) {
            sb.append("ConsistentRead: " + getConsistentRead() + ",");
        }
        if (getReturnConsumedCapacity() != null) {
            sb.append("ReturnConsumedCapacity: " + getReturnConsumedCapacity() + ",");
        }
        if (getProjectionExpression() != null) {
            sb.append("ProjectionExpression: " + getProjectionExpression() + ",");
        }
        if (getExpressionAttributeNames() != null) {
            sb.append("ExpressionAttributeNames: " + getExpressionAttributeNames());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = ((((((((getTableName() == null ? 0 : getTableName().hashCode()) + 31) * 31) + (getKey() == null ? 0 : getKey().hashCode())) * 31) + (getAttributesToGet() == null ? 0 : getAttributesToGet().hashCode())) * 31) + (getConsistentRead() == null ? 0 : getConsistentRead().hashCode())) * 31;
        if (getReturnConsumedCapacity() == null) {
            hashCode = 0;
        } else {
            hashCode = getReturnConsumedCapacity().hashCode();
        }
        int hashCode3 = (((hashCode2 + hashCode) * 31) + (getProjectionExpression() == null ? 0 : getProjectionExpression().hashCode())) * 31;
        if (getExpressionAttributeNames() != null) {
            i = getExpressionAttributeNames().hashCode();
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
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetItemRequest)) {
            return false;
        }
        GetItemRequest other = (GetItemRequest) obj;
        if ((other.getTableName() == null) ^ (getTableName() == null)) {
            return false;
        }
        if (other.getTableName() != null && !other.getTableName().equals(getTableName())) {
            return false;
        }
        if (other.getKey() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getKey() == null)) {
            return false;
        }
        if (other.getKey() != null && !other.getKey().equals(getKey())) {
            return false;
        }
        if (other.getAttributesToGet() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getAttributesToGet() == null)) {
            return false;
        }
        if (other.getAttributesToGet() != null && !other.getAttributesToGet().equals(getAttributesToGet())) {
            return false;
        }
        if (other.getConsistentRead() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getConsistentRead() == null)) {
            return false;
        }
        if (other.getConsistentRead() != null && !other.getConsistentRead().equals(getConsistentRead())) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getReturnConsumedCapacity() == null)) {
            return false;
        }
        if (other.getReturnConsumedCapacity() != null && !other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return false;
        }
        if (other.getProjectionExpression() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getProjectionExpression() == null)) {
            return false;
        }
        if (other.getProjectionExpression() != null && !other.getProjectionExpression().equals(getProjectionExpression())) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (getExpressionAttributeNames() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z6 ^ z7) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null || other.getExpressionAttributeNames().equals(getExpressionAttributeNames())) {
            return true;
        }
        return false;
    }
}
