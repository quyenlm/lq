package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class AttributeDefinition implements Serializable {
    private String attributeName;
    private String attributeType;

    public AttributeDefinition() {
    }

    public AttributeDefinition(String attributeName2, String attributeType2) {
        setAttributeName(attributeName2);
        setAttributeType(attributeType2);
    }

    public AttributeDefinition(String attributeName2, ScalarAttributeType attributeType2) {
        setAttributeName(attributeName2);
        setAttributeType(attributeType2.toString());
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String attributeName2) {
        this.attributeName = attributeName2;
    }

    public AttributeDefinition withAttributeName(String attributeName2) {
        this.attributeName = attributeName2;
        return this;
    }

    public String getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(String attributeType2) {
        this.attributeType = attributeType2;
    }

    public AttributeDefinition withAttributeType(String attributeType2) {
        this.attributeType = attributeType2;
        return this;
    }

    public void setAttributeType(ScalarAttributeType attributeType2) {
        this.attributeType = attributeType2.toString();
    }

    public AttributeDefinition withAttributeType(ScalarAttributeType attributeType2) {
        this.attributeType = attributeType2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributeName() != null) {
            sb.append("AttributeName: " + getAttributeName() + ",");
        }
        if (getAttributeType() != null) {
            sb.append("AttributeType: " + getAttributeType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getAttributeName() == null ? 0 : getAttributeName().hashCode()) + 31) * 31;
        if (getAttributeType() != null) {
            i = getAttributeType().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AttributeDefinition)) {
            return false;
        }
        AttributeDefinition other = (AttributeDefinition) obj;
        if ((other.getAttributeName() == null) ^ (getAttributeName() == null)) {
            return false;
        }
        if (other.getAttributeName() != null && !other.getAttributeName().equals(getAttributeName())) {
            return false;
        }
        if (other.getAttributeType() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getAttributeType() == null)) {
            return false;
        }
        if (other.getAttributeType() == null || other.getAttributeType().equals(getAttributeType())) {
            return true;
        }
        return false;
    }
}
