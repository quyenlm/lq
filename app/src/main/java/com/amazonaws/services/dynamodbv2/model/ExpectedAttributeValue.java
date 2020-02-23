package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExpectedAttributeValue implements Serializable {
    private List<AttributeValue> attributeValueList;
    private String comparisonOperator;
    private Boolean exists;
    private AttributeValue value;

    public ExpectedAttributeValue() {
    }

    public ExpectedAttributeValue(AttributeValue value2) {
        setValue(value2);
    }

    public ExpectedAttributeValue(Boolean exists2) {
        setExists(exists2);
    }

    public AttributeValue getValue() {
        return this.value;
    }

    public void setValue(AttributeValue value2) {
        this.value = value2;
    }

    public ExpectedAttributeValue withValue(AttributeValue value2) {
        this.value = value2;
        return this;
    }

    public Boolean isExists() {
        return this.exists;
    }

    public Boolean getExists() {
        return this.exists;
    }

    public void setExists(Boolean exists2) {
        this.exists = exists2;
    }

    public ExpectedAttributeValue withExists(Boolean exists2) {
        this.exists = exists2;
        return this;
    }

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
    }

    public ExpectedAttributeValue withComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
        return this;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
    }

    public ExpectedAttributeValue withComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
        return this;
    }

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

    public ExpectedAttributeValue withAttributeValueList(AttributeValue... attributeValueList2) {
        if (getAttributeValueList() == null) {
            this.attributeValueList = new ArrayList(attributeValueList2.length);
        }
        for (AttributeValue value2 : attributeValueList2) {
            this.attributeValueList.add(value2);
        }
        return this;
    }

    public ExpectedAttributeValue withAttributeValueList(Collection<AttributeValue> attributeValueList2) {
        setAttributeValueList(attributeValueList2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getValue() != null) {
            sb.append("Value: " + getValue() + ",");
        }
        if (getExists() != null) {
            sb.append("Exists: " + getExists() + ",");
        }
        if (getComparisonOperator() != null) {
            sb.append("ComparisonOperator: " + getComparisonOperator() + ",");
        }
        if (getAttributeValueList() != null) {
            sb.append("AttributeValueList: " + getAttributeValueList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((getValue() == null ? 0 : getValue().hashCode()) + 31) * 31) + (getExists() == null ? 0 : getExists().hashCode())) * 31) + (getComparisonOperator() == null ? 0 : getComparisonOperator().hashCode())) * 31;
        if (getAttributeValueList() != null) {
            i = getAttributeValueList().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ExpectedAttributeValue)) {
            return false;
        }
        ExpectedAttributeValue other = (ExpectedAttributeValue) obj;
        if ((other.getValue() == null) ^ (getValue() == null)) {
            return false;
        }
        if (other.getValue() != null && !other.getValue().equals(getValue())) {
            return false;
        }
        if (other.getExists() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getExists() == null)) {
            return false;
        }
        if (other.getExists() != null && !other.getExists().equals(getExists())) {
            return false;
        }
        if (other.getComparisonOperator() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getComparisonOperator() == null)) {
            return false;
        }
        if (other.getComparisonOperator() != null && !other.getComparisonOperator().equals(getComparisonOperator())) {
            return false;
        }
        if (other.getAttributeValueList() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getAttributeValueList() == null)) {
            return false;
        }
        if (other.getAttributeValueList() == null || other.getAttributeValueList().equals(getAttributeValueList())) {
            return true;
        }
        return false;
    }
}
