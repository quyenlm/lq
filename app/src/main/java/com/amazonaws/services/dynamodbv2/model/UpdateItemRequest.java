package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UpdateItemRequest extends AmazonWebServiceRequest implements Serializable {
    private Map<String, AttributeValueUpdate> attributeUpdates;
    private String conditionExpression;
    private String conditionalOperator;
    private Map<String, ExpectedAttributeValue> expected;
    private Map<String, String> expressionAttributeNames;
    private Map<String, AttributeValue> expressionAttributeValues;
    private Map<String, AttributeValue> key;
    private String returnConsumedCapacity;
    private String returnItemCollectionMetrics;
    private String returnValues;
    private String tableName;
    private String updateExpression;

    public UpdateItemRequest() {
    }

    public UpdateItemRequest(String tableName2, Map<String, AttributeValue> key2, Map<String, AttributeValueUpdate> attributeUpdates2) {
        setTableName(tableName2);
        setKey(key2);
        setAttributeUpdates(attributeUpdates2);
    }

    public UpdateItemRequest(String tableName2, Map<String, AttributeValue> key2, Map<String, AttributeValueUpdate> attributeUpdates2, String returnValues2) {
        setTableName(tableName2);
        setKey(key2);
        setAttributeUpdates(attributeUpdates2);
        setReturnValues(returnValues2);
    }

    public UpdateItemRequest(String tableName2, Map<String, AttributeValue> key2, Map<String, AttributeValueUpdate> attributeUpdates2, ReturnValue returnValues2) {
        setTableName(tableName2);
        setKey(key2);
        setAttributeUpdates(attributeUpdates2);
        setReturnValues(returnValues2.toString());
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public UpdateItemRequest withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public Map<String, AttributeValue> getKey() {
        return this.key;
    }

    public void setKey(Map<String, AttributeValue> key2) {
        this.key = key2;
    }

    public UpdateItemRequest withKey(Map<String, AttributeValue> key2) {
        this.key = key2;
        return this;
    }

    public UpdateItemRequest addKeyEntry(String key2, AttributeValue value) {
        if (this.key == null) {
            this.key = new HashMap();
        }
        if (this.key.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.key.put(key2, value);
        return this;
    }

    public UpdateItemRequest clearKeyEntries() {
        this.key = null;
        return this;
    }

    public Map<String, AttributeValueUpdate> getAttributeUpdates() {
        return this.attributeUpdates;
    }

    public void setAttributeUpdates(Map<String, AttributeValueUpdate> attributeUpdates2) {
        this.attributeUpdates = attributeUpdates2;
    }

    public UpdateItemRequest withAttributeUpdates(Map<String, AttributeValueUpdate> attributeUpdates2) {
        this.attributeUpdates = attributeUpdates2;
        return this;
    }

    public UpdateItemRequest addAttributeUpdatesEntry(String key2, AttributeValueUpdate value) {
        if (this.attributeUpdates == null) {
            this.attributeUpdates = new HashMap();
        }
        if (this.attributeUpdates.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.attributeUpdates.put(key2, value);
        return this;
    }

    public UpdateItemRequest clearAttributeUpdatesEntries() {
        this.attributeUpdates = null;
        return this;
    }

    public Map<String, ExpectedAttributeValue> getExpected() {
        return this.expected;
    }

    public void setExpected(Map<String, ExpectedAttributeValue> expected2) {
        this.expected = expected2;
    }

    public UpdateItemRequest withExpected(Map<String, ExpectedAttributeValue> expected2) {
        this.expected = expected2;
        return this;
    }

    public UpdateItemRequest addExpectedEntry(String key2, ExpectedAttributeValue value) {
        if (this.expected == null) {
            this.expected = new HashMap();
        }
        if (this.expected.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expected.put(key2, value);
        return this;
    }

    public UpdateItemRequest clearExpectedEntries() {
        this.expected = null;
        return this;
    }

    public String getConditionalOperator() {
        return this.conditionalOperator;
    }

    public void setConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
    }

    public UpdateItemRequest withConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
        return this;
    }

    public void setConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
    }

    public UpdateItemRequest withConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
        return this;
    }

    public String getReturnValues() {
        return this.returnValues;
    }

    public void setReturnValues(String returnValues2) {
        this.returnValues = returnValues2;
    }

    public UpdateItemRequest withReturnValues(String returnValues2) {
        this.returnValues = returnValues2;
        return this;
    }

    public void setReturnValues(ReturnValue returnValues2) {
        this.returnValues = returnValues2.toString();
    }

    public UpdateItemRequest withReturnValues(ReturnValue returnValues2) {
        this.returnValues = returnValues2.toString();
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public UpdateItemRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public UpdateItemRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public String getReturnItemCollectionMetrics() {
        return this.returnItemCollectionMetrics;
    }

    public void setReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
    }

    public UpdateItemRequest withReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
        return this;
    }

    public void setReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
    }

    public UpdateItemRequest withReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
        return this;
    }

    public String getUpdateExpression() {
        return this.updateExpression;
    }

    public void setUpdateExpression(String updateExpression2) {
        this.updateExpression = updateExpression2;
    }

    public UpdateItemRequest withUpdateExpression(String updateExpression2) {
        this.updateExpression = updateExpression2;
        return this;
    }

    public String getConditionExpression() {
        return this.conditionExpression;
    }

    public void setConditionExpression(String conditionExpression2) {
        this.conditionExpression = conditionExpression2;
    }

    public UpdateItemRequest withConditionExpression(String conditionExpression2) {
        this.conditionExpression = conditionExpression2;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
    }

    public UpdateItemRequest withExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
        return this;
    }

    public UpdateItemRequest addExpressionAttributeNamesEntry(String key2, String value) {
        if (this.expressionAttributeNames == null) {
            this.expressionAttributeNames = new HashMap();
        }
        if (this.expressionAttributeNames.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expressionAttributeNames.put(key2, value);
        return this;
    }

    public UpdateItemRequest clearExpressionAttributeNamesEntries() {
        this.expressionAttributeNames = null;
        return this;
    }

    public Map<String, AttributeValue> getExpressionAttributeValues() {
        return this.expressionAttributeValues;
    }

    public void setExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
    }

    public UpdateItemRequest withExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
        return this;
    }

    public UpdateItemRequest addExpressionAttributeValuesEntry(String key2, AttributeValue value) {
        if (this.expressionAttributeValues == null) {
            this.expressionAttributeValues = new HashMap();
        }
        if (this.expressionAttributeValues.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expressionAttributeValues.put(key2, value);
        return this;
    }

    public UpdateItemRequest clearExpressionAttributeValuesEntries() {
        this.expressionAttributeValues = null;
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
        if (getAttributeUpdates() != null) {
            sb.append("AttributeUpdates: " + getAttributeUpdates() + ",");
        }
        if (getExpected() != null) {
            sb.append("Expected: " + getExpected() + ",");
        }
        if (getConditionalOperator() != null) {
            sb.append("ConditionalOperator: " + getConditionalOperator() + ",");
        }
        if (getReturnValues() != null) {
            sb.append("ReturnValues: " + getReturnValues() + ",");
        }
        if (getReturnConsumedCapacity() != null) {
            sb.append("ReturnConsumedCapacity: " + getReturnConsumedCapacity() + ",");
        }
        if (getReturnItemCollectionMetrics() != null) {
            sb.append("ReturnItemCollectionMetrics: " + getReturnItemCollectionMetrics() + ",");
        }
        if (getUpdateExpression() != null) {
            sb.append("UpdateExpression: " + getUpdateExpression() + ",");
        }
        if (getConditionExpression() != null) {
            sb.append("ConditionExpression: " + getConditionExpression() + ",");
        }
        if (getExpressionAttributeNames() != null) {
            sb.append("ExpressionAttributeNames: " + getExpressionAttributeNames() + ",");
        }
        if (getExpressionAttributeValues() != null) {
            sb.append("ExpressionAttributeValues: " + getExpressionAttributeValues());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        int hashCode4 = ((((((((((((getTableName() == null ? 0 : getTableName().hashCode()) + 31) * 31) + (getKey() == null ? 0 : getKey().hashCode())) * 31) + (getAttributeUpdates() == null ? 0 : getAttributeUpdates().hashCode())) * 31) + (getExpected() == null ? 0 : getExpected().hashCode())) * 31) + (getConditionalOperator() == null ? 0 : getConditionalOperator().hashCode())) * 31) + (getReturnValues() == null ? 0 : getReturnValues().hashCode())) * 31;
        if (getReturnConsumedCapacity() == null) {
            hashCode = 0;
        } else {
            hashCode = getReturnConsumedCapacity().hashCode();
        }
        int i2 = (hashCode4 + hashCode) * 31;
        if (getReturnItemCollectionMetrics() == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = getReturnItemCollectionMetrics().hashCode();
        }
        int hashCode5 = (((((i2 + hashCode2) * 31) + (getUpdateExpression() == null ? 0 : getUpdateExpression().hashCode())) * 31) + (getConditionExpression() == null ? 0 : getConditionExpression().hashCode())) * 31;
        if (getExpressionAttributeNames() == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = getExpressionAttributeNames().hashCode();
        }
        int i3 = (hashCode5 + hashCode3) * 31;
        if (getExpressionAttributeValues() != null) {
            i = getExpressionAttributeValues().hashCode();
        }
        return i3 + i;
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
        boolean z14;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateItemRequest)) {
            return false;
        }
        UpdateItemRequest other = (UpdateItemRequest) obj;
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
        if (other.getAttributeUpdates() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getAttributeUpdates() == null)) {
            return false;
        }
        if (other.getAttributeUpdates() != null && !other.getAttributeUpdates().equals(getAttributeUpdates())) {
            return false;
        }
        if (other.getExpected() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getExpected() == null)) {
            return false;
        }
        if (other.getExpected() != null && !other.getExpected().equals(getExpected())) {
            return false;
        }
        if (other.getConditionalOperator() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getConditionalOperator() == null)) {
            return false;
        }
        if (other.getConditionalOperator() != null && !other.getConditionalOperator().equals(getConditionalOperator())) {
            return false;
        }
        if (other.getReturnValues() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getReturnValues() == null)) {
            return false;
        }
        if (other.getReturnValues() != null && !other.getReturnValues().equals(getReturnValues())) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getReturnConsumedCapacity() == null)) {
            return false;
        }
        if (other.getReturnConsumedCapacity() != null && !other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (getReturnItemCollectionMetrics() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z7 ^ z8) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() != null && !other.getReturnItemCollectionMetrics().equals(getReturnItemCollectionMetrics())) {
            return false;
        }
        if (other.getUpdateExpression() == null) {
            z9 = true;
        } else {
            z9 = false;
        }
        if (z9 ^ (getUpdateExpression() == null)) {
            return false;
        }
        if (other.getUpdateExpression() != null && !other.getUpdateExpression().equals(getUpdateExpression())) {
            return false;
        }
        if (other.getConditionExpression() == null) {
            z10 = true;
        } else {
            z10 = false;
        }
        if (z10 ^ (getConditionExpression() == null)) {
            return false;
        }
        if (other.getConditionExpression() != null && !other.getConditionExpression().equals(getConditionExpression())) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null) {
            z11 = true;
        } else {
            z11 = false;
        }
        if (getExpressionAttributeNames() == null) {
            z12 = true;
        } else {
            z12 = false;
        }
        if (z11 ^ z12) {
            return false;
        }
        if (other.getExpressionAttributeNames() != null && !other.getExpressionAttributeNames().equals(getExpressionAttributeNames())) {
            return false;
        }
        if (other.getExpressionAttributeValues() == null) {
            z13 = true;
        } else {
            z13 = false;
        }
        if (getExpressionAttributeValues() == null) {
            z14 = true;
        } else {
            z14 = false;
        }
        if (z13 ^ z14) {
            return false;
        }
        if (other.getExpressionAttributeValues() == null || other.getExpressionAttributeValues().equals(getExpressionAttributeValues())) {
            return true;
        }
        return false;
    }
}
