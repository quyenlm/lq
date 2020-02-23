package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class AttributeValueUpdate implements Serializable {
    private String action;
    private AttributeValue value;

    public AttributeValueUpdate() {
    }

    public AttributeValueUpdate(AttributeValue value2, String action2) {
        setValue(value2);
        setAction(action2);
    }

    public AttributeValueUpdate(AttributeValue value2, AttributeAction action2) {
        setValue(value2);
        setAction(action2.toString());
    }

    public AttributeValue getValue() {
        return this.value;
    }

    public void setValue(AttributeValue value2) {
        this.value = value2;
    }

    public AttributeValueUpdate withValue(AttributeValue value2) {
        this.value = value2;
        return this;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public AttributeValueUpdate withAction(String action2) {
        this.action = action2;
        return this;
    }

    public void setAction(AttributeAction action2) {
        this.action = action2.toString();
    }

    public AttributeValueUpdate withAction(AttributeAction action2) {
        this.action = action2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getValue() != null) {
            sb.append("Value: " + getValue() + ",");
        }
        if (getAction() != null) {
            sb.append("Action: " + getAction());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getValue() == null ? 0 : getValue().hashCode()) + 31) * 31;
        if (getAction() != null) {
            i = getAction().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AttributeValueUpdate)) {
            return false;
        }
        AttributeValueUpdate other = (AttributeValueUpdate) obj;
        if ((other.getValue() == null) ^ (getValue() == null)) {
            return false;
        }
        if (other.getValue() != null && !other.getValue().equals(getValue())) {
            return false;
        }
        if (other.getAction() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getAction() == null)) {
            return false;
        }
        if (other.getAction() == null || other.getAction().equals(getAction())) {
            return true;
        }
        return false;
    }
}
