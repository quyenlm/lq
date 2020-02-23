package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class KeySchemaElement implements Serializable {
    private String attributeName;
    private String keyType;

    public KeySchemaElement() {
    }

    public KeySchemaElement(String attributeName2, String keyType2) {
        setAttributeName(attributeName2);
        setKeyType(keyType2);
    }

    public KeySchemaElement(String attributeName2, KeyType keyType2) {
        setAttributeName(attributeName2);
        setKeyType(keyType2.toString());
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String attributeName2) {
        this.attributeName = attributeName2;
    }

    public KeySchemaElement withAttributeName(String attributeName2) {
        this.attributeName = attributeName2;
        return this;
    }

    public String getKeyType() {
        return this.keyType;
    }

    public void setKeyType(String keyType2) {
        this.keyType = keyType2;
    }

    public KeySchemaElement withKeyType(String keyType2) {
        this.keyType = keyType2;
        return this;
    }

    public void setKeyType(KeyType keyType2) {
        this.keyType = keyType2.toString();
    }

    public KeySchemaElement withKeyType(KeyType keyType2) {
        this.keyType = keyType2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributeName() != null) {
            sb.append("AttributeName: " + getAttributeName() + ",");
        }
        if (getKeyType() != null) {
            sb.append("KeyType: " + getKeyType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getAttributeName() == null ? 0 : getAttributeName().hashCode()) + 31) * 31;
        if (getKeyType() != null) {
            i = getKeyType().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof KeySchemaElement)) {
            return false;
        }
        KeySchemaElement other = (KeySchemaElement) obj;
        if ((other.getAttributeName() == null) ^ (getAttributeName() == null)) {
            return false;
        }
        if (other.getAttributeName() != null && !other.getAttributeName().equals(getAttributeName())) {
            return false;
        }
        if (other.getKeyType() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getKeyType() == null)) {
            return false;
        }
        if (other.getKeyType() == null || other.getKeyType().equals(getKeyType())) {
            return true;
        }
        return false;
    }
}
