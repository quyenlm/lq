package com.amazonaws.services.dynamodbv2.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanRequest extends AmazonWebServiceRequest implements Serializable {
    private List<String> attributesToGet;
    private String conditionalOperator;
    private Boolean consistentRead;
    private Map<String, AttributeValue> exclusiveStartKey;
    private Map<String, String> expressionAttributeNames;
    private Map<String, AttributeValue> expressionAttributeValues;
    private String filterExpression;
    private String indexName;
    private Integer limit;
    private String projectionExpression;
    private String returnConsumedCapacity;
    private Map<String, Condition> scanFilter;
    private Integer segment;
    private String select;
    private String tableName;
    private Integer totalSegments;

    public ScanRequest() {
    }

    public ScanRequest(String tableName2) {
        setTableName(tableName2);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public ScanRequest withTableName(String tableName2) {
        this.tableName = tableName2;
        return this;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName2) {
        this.indexName = indexName2;
    }

    public ScanRequest withIndexName(String indexName2) {
        this.indexName = indexName2;
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

    public ScanRequest withAttributesToGet(String... attributesToGet2) {
        if (getAttributesToGet() == null) {
            this.attributesToGet = new ArrayList(attributesToGet2.length);
        }
        for (String value : attributesToGet2) {
            this.attributesToGet.add(value);
        }
        return this;
    }

    public ScanRequest withAttributesToGet(Collection<String> attributesToGet2) {
        setAttributesToGet(attributesToGet2);
        return this;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit2) {
        this.limit = limit2;
    }

    public ScanRequest withLimit(Integer limit2) {
        this.limit = limit2;
        return this;
    }

    public String getSelect() {
        return this.select;
    }

    public void setSelect(String select2) {
        this.select = select2;
    }

    public ScanRequest withSelect(String select2) {
        this.select = select2;
        return this;
    }

    public void setSelect(Select select2) {
        this.select = select2.toString();
    }

    public ScanRequest withSelect(Select select2) {
        this.select = select2.toString();
        return this;
    }

    public Map<String, Condition> getScanFilter() {
        return this.scanFilter;
    }

    public void setScanFilter(Map<String, Condition> scanFilter2) {
        this.scanFilter = scanFilter2;
    }

    public ScanRequest withScanFilter(Map<String, Condition> scanFilter2) {
        this.scanFilter = scanFilter2;
        return this;
    }

    public ScanRequest addScanFilterEntry(String key, Condition value) {
        if (this.scanFilter == null) {
            this.scanFilter = new HashMap();
        }
        if (this.scanFilter.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.scanFilter.put(key, value);
        return this;
    }

    public ScanRequest clearScanFilterEntries() {
        this.scanFilter = null;
        return this;
    }

    public String getConditionalOperator() {
        return this.conditionalOperator;
    }

    public void setConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
    }

    public ScanRequest withConditionalOperator(String conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2;
        return this;
    }

    public void setConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
    }

    public ScanRequest withConditionalOperator(ConditionalOperator conditionalOperator2) {
        this.conditionalOperator = conditionalOperator2.toString();
        return this;
    }

    public Map<String, AttributeValue> getExclusiveStartKey() {
        return this.exclusiveStartKey;
    }

    public void setExclusiveStartKey(Map<String, AttributeValue> exclusiveStartKey2) {
        this.exclusiveStartKey = exclusiveStartKey2;
    }

    public ScanRequest withExclusiveStartKey(Map<String, AttributeValue> exclusiveStartKey2) {
        this.exclusiveStartKey = exclusiveStartKey2;
        return this;
    }

    public ScanRequest addExclusiveStartKeyEntry(String key, AttributeValue value) {
        if (this.exclusiveStartKey == null) {
            this.exclusiveStartKey = new HashMap();
        }
        if (this.exclusiveStartKey.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.exclusiveStartKey.put(key, value);
        return this;
    }

    public ScanRequest clearExclusiveStartKeyEntries() {
        this.exclusiveStartKey = null;
        return this;
    }

    public String getReturnConsumedCapacity() {
        return this.returnConsumedCapacity;
    }

    public void setReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
    }

    public ScanRequest withReturnConsumedCapacity(String returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2;
        return this;
    }

    public void setReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
    }

    public ScanRequest withReturnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity2) {
        this.returnConsumedCapacity = returnConsumedCapacity2.toString();
        return this;
    }

    public Integer getTotalSegments() {
        return this.totalSegments;
    }

    public void setTotalSegments(Integer totalSegments2) {
        this.totalSegments = totalSegments2;
    }

    public ScanRequest withTotalSegments(Integer totalSegments2) {
        this.totalSegments = totalSegments2;
        return this;
    }

    public Integer getSegment() {
        return this.segment;
    }

    public void setSegment(Integer segment2) {
        this.segment = segment2;
    }

    public ScanRequest withSegment(Integer segment2) {
        this.segment = segment2;
        return this;
    }

    public String getProjectionExpression() {
        return this.projectionExpression;
    }

    public void setProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
    }

    public ScanRequest withProjectionExpression(String projectionExpression2) {
        this.projectionExpression = projectionExpression2;
        return this;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression2) {
        this.filterExpression = filterExpression2;
    }

    public ScanRequest withFilterExpression(String filterExpression2) {
        this.filterExpression = filterExpression2;
        return this;
    }

    public Map<String, String> getExpressionAttributeNames() {
        return this.expressionAttributeNames;
    }

    public void setExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
    }

    public ScanRequest withExpressionAttributeNames(Map<String, String> expressionAttributeNames2) {
        this.expressionAttributeNames = expressionAttributeNames2;
        return this;
    }

    public ScanRequest addExpressionAttributeNamesEntry(String key, String value) {
        if (this.expressionAttributeNames == null) {
            this.expressionAttributeNames = new HashMap();
        }
        if (this.expressionAttributeNames.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.expressionAttributeNames.put(key, value);
        return this;
    }

    public ScanRequest clearExpressionAttributeNamesEntries() {
        this.expressionAttributeNames = null;
        return this;
    }

    public Map<String, AttributeValue> getExpressionAttributeValues() {
        return this.expressionAttributeValues;
    }

    public void setExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
    }

    public ScanRequest withExpressionAttributeValues(Map<String, AttributeValue> expressionAttributeValues2) {
        this.expressionAttributeValues = expressionAttributeValues2;
        return this;
    }

    public ScanRequest addExpressionAttributeValuesEntry(String key, AttributeValue value) {
        if (this.expressionAttributeValues == null) {
            this.expressionAttributeValues = new HashMap();
        }
        if (this.expressionAttributeValues.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.expressionAttributeValues.put(key, value);
        return this;
    }

    public ScanRequest clearExpressionAttributeValuesEntries() {
        this.expressionAttributeValues = null;
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

    public ScanRequest withConsistentRead(Boolean consistentRead2) {
        this.consistentRead = consistentRead2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTableName() != null) {
            sb.append("TableName: " + getTableName() + ",");
        }
        if (getIndexName() != null) {
            sb.append("IndexName: " + getIndexName() + ",");
        }
        if (getAttributesToGet() != null) {
            sb.append("AttributesToGet: " + getAttributesToGet() + ",");
        }
        if (getLimit() != null) {
            sb.append("Limit: " + getLimit() + ",");
        }
        if (getSelect() != null) {
            sb.append("Select: " + getSelect() + ",");
        }
        if (getScanFilter() != null) {
            sb.append("ScanFilter: " + getScanFilter() + ",");
        }
        if (getConditionalOperator() != null) {
            sb.append("ConditionalOperator: " + getConditionalOperator() + ",");
        }
        if (getExclusiveStartKey() != null) {
            sb.append("ExclusiveStartKey: " + getExclusiveStartKey() + ",");
        }
        if (getReturnConsumedCapacity() != null) {
            sb.append("ReturnConsumedCapacity: " + getReturnConsumedCapacity() + ",");
        }
        if (getTotalSegments() != null) {
            sb.append("TotalSegments: " + getTotalSegments() + ",");
        }
        if (getSegment() != null) {
            sb.append("Segment: " + getSegment() + ",");
        }
        if (getProjectionExpression() != null) {
            sb.append("ProjectionExpression: " + getProjectionExpression() + ",");
        }
        if (getFilterExpression() != null) {
            sb.append("FilterExpression: " + getFilterExpression() + ",");
        }
        if (getExpressionAttributeNames() != null) {
            sb.append("ExpressionAttributeNames: " + getExpressionAttributeNames() + ",");
        }
        if (getExpressionAttributeValues() != null) {
            sb.append("ExpressionAttributeValues: " + getExpressionAttributeValues() + ",");
        }
        if (getConsistentRead() != null) {
            sb.append("ConsistentRead: " + getConsistentRead());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        int hashCode4 = ((((((((((((((((getTableName() == null ? 0 : getTableName().hashCode()) + 31) * 31) + (getIndexName() == null ? 0 : getIndexName().hashCode())) * 31) + (getAttributesToGet() == null ? 0 : getAttributesToGet().hashCode())) * 31) + (getLimit() == null ? 0 : getLimit().hashCode())) * 31) + (getSelect() == null ? 0 : getSelect().hashCode())) * 31) + (getScanFilter() == null ? 0 : getScanFilter().hashCode())) * 31) + (getConditionalOperator() == null ? 0 : getConditionalOperator().hashCode())) * 31) + (getExclusiveStartKey() == null ? 0 : getExclusiveStartKey().hashCode())) * 31;
        if (getReturnConsumedCapacity() == null) {
            hashCode = 0;
        } else {
            hashCode = getReturnConsumedCapacity().hashCode();
        }
        int hashCode5 = (((((((((hashCode4 + hashCode) * 31) + (getTotalSegments() == null ? 0 : getTotalSegments().hashCode())) * 31) + (getSegment() == null ? 0 : getSegment().hashCode())) * 31) + (getProjectionExpression() == null ? 0 : getProjectionExpression().hashCode())) * 31) + (getFilterExpression() == null ? 0 : getFilterExpression().hashCode())) * 31;
        if (getExpressionAttributeNames() == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = getExpressionAttributeNames().hashCode();
        }
        int i2 = (hashCode5 + hashCode2) * 31;
        if (getExpressionAttributeValues() == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = getExpressionAttributeValues().hashCode();
        }
        int i3 = (i2 + hashCode3) * 31;
        if (getConsistentRead() != null) {
            i = getConsistentRead().hashCode();
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
        boolean z15;
        boolean z16;
        boolean z17;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ScanRequest)) {
            return false;
        }
        ScanRequest other = (ScanRequest) obj;
        if ((other.getTableName() == null) ^ (getTableName() == null)) {
            return false;
        }
        if (other.getTableName() != null && !other.getTableName().equals(getTableName())) {
            return false;
        }
        if (other.getIndexName() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getIndexName() == null)) {
            return false;
        }
        if (other.getIndexName() != null && !other.getIndexName().equals(getIndexName())) {
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
        if (other.getLimit() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getLimit() == null)) {
            return false;
        }
        if (other.getLimit() != null && !other.getLimit().equals(getLimit())) {
            return false;
        }
        if (other.getSelect() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getSelect() == null)) {
            return false;
        }
        if (other.getSelect() != null && !other.getSelect().equals(getSelect())) {
            return false;
        }
        if (other.getScanFilter() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getScanFilter() == null)) {
            return false;
        }
        if (other.getScanFilter() != null && !other.getScanFilter().equals(getScanFilter())) {
            return false;
        }
        if (other.getConditionalOperator() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getConditionalOperator() == null)) {
            return false;
        }
        if (other.getConditionalOperator() != null && !other.getConditionalOperator().equals(getConditionalOperator())) {
            return false;
        }
        if (other.getExclusiveStartKey() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z7 ^ (getExclusiveStartKey() == null)) {
            return false;
        }
        if (other.getExclusiveStartKey() != null && !other.getExclusiveStartKey().equals(getExclusiveStartKey())) {
            return false;
        }
        if (other.getReturnConsumedCapacity() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 ^ (getReturnConsumedCapacity() == null)) {
            return false;
        }
        if (other.getReturnConsumedCapacity() != null && !other.getReturnConsumedCapacity().equals(getReturnConsumedCapacity())) {
            return false;
        }
        if (other.getTotalSegments() == null) {
            z9 = true;
        } else {
            z9 = false;
        }
        if (z9 ^ (getTotalSegments() == null)) {
            return false;
        }
        if (other.getTotalSegments() != null && !other.getTotalSegments().equals(getTotalSegments())) {
            return false;
        }
        if (other.getSegment() == null) {
            z10 = true;
        } else {
            z10 = false;
        }
        if (z10 ^ (getSegment() == null)) {
            return false;
        }
        if (other.getSegment() != null && !other.getSegment().equals(getSegment())) {
            return false;
        }
        if (other.getProjectionExpression() == null) {
            z11 = true;
        } else {
            z11 = false;
        }
        if (z11 ^ (getProjectionExpression() == null)) {
            return false;
        }
        if (other.getProjectionExpression() != null && !other.getProjectionExpression().equals(getProjectionExpression())) {
            return false;
        }
        if (other.getFilterExpression() == null) {
            z12 = true;
        } else {
            z12 = false;
        }
        if (z12 ^ (getFilterExpression() == null)) {
            return false;
        }
        if (other.getFilterExpression() != null && !other.getFilterExpression().equals(getFilterExpression())) {
            return false;
        }
        if (other.getExpressionAttributeNames() == null) {
            z13 = true;
        } else {
            z13 = false;
        }
        if (getExpressionAttributeNames() == null) {
            z14 = true;
        } else {
            z14 = false;
        }
        if (z13 ^ z14) {
            return false;
        }
        if (other.getExpressionAttributeNames() != null && !other.getExpressionAttributeNames().equals(getExpressionAttributeNames())) {
            return false;
        }
        if (other.getExpressionAttributeValues() == null) {
            z15 = true;
        } else {
            z15 = false;
        }
        if (getExpressionAttributeValues() == null) {
            z16 = true;
        } else {
            z16 = false;
        }
        if (z15 ^ z16) {
            return false;
        }
        if (other.getExpressionAttributeValues() != null && !other.getExpressionAttributeValues().equals(getExpressionAttributeValues())) {
            return false;
        }
        if (other.getConsistentRead() == null) {
            z17 = true;
        } else {
            z17 = false;
        }
        if (z17 ^ (getConsistentRead() == null)) {
            return false;
        }
        if (other.getConsistentRead() == null || other.getConsistentRead().equals(getConsistentRead())) {
            return true;
        }
        return false;
    }
}
