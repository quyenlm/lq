package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeleteItemRequest extends AmazonWebServiceRequest implements Serializable {
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

    public DeleteItemRequest() {
    }

    public DeleteItemRequest(String tableName2, Map<String, AttributeValue> key2) {
        setTableName(tableName2);
        setKey(key2);
    }

    public DeleteItemRequest(String tableName2, Map<String, AttributeValue> key2, String returnValues2) {
        setTableName(tableName2);
        setKey(key2);
        setReturnValues(returnValues2);
    }

    public DeleteItemRequest(String tableName2, Map<String, AttributeValue> key2, ReturnValue returnValues2) {
        setTableName(tableName2);
        setKey(key2);
        setReturnValues(returnValues2.toString());
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public DeleteItemRequest withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public Map<String, AttributeValue> getKey() {
        return this.key;
    }

    public void setKey(Map<String, AttributeValue> key2) {
        this.key = key2;
    }

    public DeleteItemRequest withKey(Map<String, AttributeValue> key2) {
        this.key = key2;
        return this;
    }

    public DeleteItemRequest addKeyEntry(String key2, AttributeValue value) {
        if (this.key == null) {
            this.key = new HashMap();
        }
        if (this.key.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.key.put(key2, value);
        return this;
    }

    public DeleteItemRequest clearKeyEntries() {
        this.key = null;
        return this;
    }

    public Map<String, ExpectedAttributeValue> getExpected() {
        return this.expected;
    }

    public void setExpected(Map<String, ExpectedAttributeValue> expected2) {
        this.expected = expected2;
    }

    public DeleteItemRequest withExpected(Map<String, ExpectedAttributeValue> expected2) {
        this.expected = expected2;
        return this;
    }

    public DeleteItemRequest addExpectedEntry(String key2, ExpectedAttributeValue value) {
        if (this.expected == null) {
            this.expected = new HashMap();
        }
        if (this.expected.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expected.put(key2, value);
        return this;
    }

    public DeleteItemRequest clearExpectedEntries() {
        this.expected = null;
        return this;
    }

    public String getConditionalOperator() {
        return this.conditionalOperator;
    }

    public void setConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
    }

    public DeleteItemRequest withConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
        return this;
    }

    public void setConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
    }

    public DeleteItemRequest withConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
        return this;
    }

    public String getReturnValues() {
        return this.returnValues;
    }

    public void setReturnValues(String returnValues2) {
        this.returnValues = returnValues2;
    }

    public DeleteItemRequest withReturnValues(String returnValues2) {
        this.returnValues = returnValues2;
        return this;
    }

    public void setReturnValues(ReturnValue returnValues2) {
        this.returnValues = returnValues2.toString();
    }

    public DeleteItemRequest withReturnValues(ReturnValue returnValues2) {
        this.returnValues = returnValues2.toString();
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public DeleteItemRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public DeleteItemRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public String getReturnItemCollectionMetrics() {
        return this.returnItemCollectionMetrics;
    }

    public void setReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
    }

    public DeleteItemRequest withReturnItemCollectionMetrics(String returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2;
        return this;
    }

    public void setReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
    }

    public DeleteItemRequest withReturnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics2) {
        this.returnItemCollectionMetrics = returnItemCollectionMetrics2.toString();
        return this;
    }

    public String getConditionExpression() {
        return this.conditionExpression;
    }

    public void setConditionExpression(String conditionExpression2) {
        this.conditionExpression = conditionExpression2;
    }

    public DeleteItemRequest withConditionExpression(String conditionExpression2) {
        this.conditionExpression = conditionExpression2;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
    }

    public DeleteItemRequest withExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
        return this;
    }

    public DeleteItemRequest addExpressionAttributeNamesEntry(String key2, String value) {
        if (this.expressionAttributeNames == null) {
            this.expressionAttributeNames = new HashMap();
        }
        if (this.expressionAttributeNames.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expressionAttributeNames.put(key2, value);
        return this;
    }

    public DeleteItemRequest clearExpressionAttributeNamesEntries() {
        this.expressionAttributeNames = null;
        return this;
    }

    public Map<String, AttributeValue> getExpressionAttributeValues() {
        return this.expressionAttributeValues;
    }

    public void setExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
    }

    public DeleteItemRequest withExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
        return this;
    }

    public DeleteItemRequest addExpressionAttributeValuesEntry(String key2, AttributeValue value) {
        if (this.expressionAttributeValues == null) {
            this.expressionAttributeValues = new HashMap();
        }
        if (this.expressionAttributeValues.containsKey(key2)) {
            throw new IllegalArgumentException("Duplicated keys (" + key2.toString() + ") are provided.");
        }
        this.expressionAttributeValues.put(key2, value);
        return this;
    }

    public DeleteItemRequest clearExpressionAttributeValuesEntries() {
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
        int hashCode4 = ((((((((((getTableName() == null ? 0 : getTableName().hashCode()) + 31) * 31) + (getKey() == null ? 0 : getKey().hashCode())) * 31) + (getExpected() == null ? 0 : getExpected().hashCode())) * 31) + (getConditionalOperator() == null ? 0 : getConditionalOperator().hashCode())) * 31) + (getReturnValues() == null ? 0 : getReturnValues().hashCode())) * 31;
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
        int hashCode5 = (((i2 + hashCode2) * 31) + (getConditionExpression() == null ? 0 : getConditionExpression().hashCode())) * 31;
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
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteItemRequest)) {
            return false;
        }
        DeleteItemRequest other = (DeleteItemRequest) obj;
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
        if (other.getExpected() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getExpected() == null)) {
            return false;
        }
        if (other.getExpected() != null && !other.getExpected().equals(getExpected())) {
            return false;
        }
        if (other.getConditionalOperator() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getConditionalOperator() == null)) {
            return false;
        }
        if (other.getConditionalOperator() != null && !other.getConditionalOperator().equals(getConditionalOperator())) {
            return false;
        }
        if (other.getReturnValues() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getReturnValues() == null)) {
            return false;
        }
        if (other.getReturnValues() != null && !other.getReturnValues().equals(getReturnValues())) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getReturnConsumedCapacity() == null)) {
            return false;
        }
        if (other.getReturnConsumedCapacity() != null && !other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (getReturnItemCollectionMetrics() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z6 ^ z7) {
            return false;
        }
        if (other.getReturnItemCollectionMetrics() != null && !other.getReturnItemCollectionMetrics().equals(getReturnItemCollectionMetrics())) {
            return false;
        }
        if (other.getConditionExpression() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 ^ (getConditionExpression() == null)) {
            return false;
        }
        if (other.getConditionExpression() != null && !other.getConditionExpression().equals(getConditionExpression())) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null) {
            z9 = true;
        } else {
            z9 = false;
        }
        if (getExpressionAttributeNames() == null) {
            z10 = true;
        } else {
            z10 = false;
        }
        if (z9 ^ z10) {
            return false;
        }
        if (other.getExpressionAttributeNames() != null && !other.getExpressionAttributeNames().equals(getExpressionAttributeNames())) {
            return false;
        }
        if (other.getExpressionAttributeValues() == null) {
            z11 = true;
        } else {
            z11 = false;
        }
        if (getExpressionAttributeValues() == null) {
            z12 = true;
        } else {
            z12 = false;
        }
        if (z11 ^ z12) {
            return false;
        }
        if (other.getExpressionAttributeValues() == null || other.getExpressionAttributeValues().equals(getExpressionAttributeValues())) {
            return true;
        }
        return false;
    }
}
