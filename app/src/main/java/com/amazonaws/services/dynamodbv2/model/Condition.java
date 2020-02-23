package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Condition implements Serializable {
    private List<AttributeValue> attributeValueList;
    private String comparisonOperator;

    public List<AttributeValue> getAttributeValueList() {
        return this.attributeValueList;
    }

    public void setAttributeValueList(Collection<AttributeValue> attributeValueList2) {
        if (attributeValueList2 == null) {
            this.attributeValueList = null;
        } else {
            this.attributeValueList = new ArrayList(attributeValueList2);
        }
    }

    public Condition withAttributeValueList(AttributeValue... attributeValueList2) {
        if (getAttributeValueList() == null) {
            this.attributeValueList = new ArrayList(attributeValueList2.length);
        }
        for (AttributeValue value : attributeValueList2) {
            this.attributeValueList.add(value);
        }
        return this;
    }

    public Condition withAttributeValueList(Collection<AttributeValue> attributeValueList2) {
        setAttributeValueList(attributeValueList2);
        return this;
    }

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
    }

    public Condition withComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
        return this;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
    }

    public Condition withComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributeValueList() != null) {
            sb.append("AttributeValueList: " + getAttributeValueList() + ",");
        }
        if (getComparisonOperator() != null) {
            sb.append("ComparisonOperator: " + getComparisonOperator());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getAttributeValueList() == null ? 0 : getAttributeValueList().hashCode()) + 31) * 31;
        if (getComparisonOperator() != null) {
            i = getComparisonOperator().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Condition)) {
            return false;
        }
        Condition other = (Condition) obj;
        if ((other.getAttributeValueList() == null) ^ (getAttributeValueList() == null)) {
            return false;
        }
        if (other.getAttributeValueList() != null && !other.getAttributeValueList().equals(getAttributeValueList())) {
            return false;
        }
        if (other.getComparisonOperator() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getComparisonOperator() == null)) {
            return false;
        }
        if (other.getComparisonOperator() == null || other.getComparisonOperator().equals(getComparisonOperator())) {
            return true;
        }
        return false;
    }
}
