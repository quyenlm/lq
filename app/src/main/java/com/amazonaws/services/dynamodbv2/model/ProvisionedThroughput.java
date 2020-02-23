package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class ProvisionedThroughput implements Serializable {
    private Long readCapacityUnits;
    private Long writeCapacityUnits;

    public ProvisionedThroughput() {
    }

    public ProvisionedThroughput(Long readCapacityUnits2, Long writeCapacityUnits2) {
        setReadCapacityUnits(readCapacityUnits2);
        setWriteCapacityUnits(writeCapacityUnits2);
    }

    public Long getReadCapacityUnits() {
        return this.readCapacityUnits;
    }

    public void setReadCapacityUnits(Long readCapacityUnits2) {
        this.readCapacityUnits = readCapacityUnits2;
    }

    public ProvisionedThroughput withReadCapacityUnits(Long readCapacityUnits2) {
        this.readCapacityUnits = readCapacityUnits2;
        return this;
    }

    public Long getWriteCapacityUnits() {
        return this.writeCapacityUnits;
    }

    public void setWriteCapacityUnits(Long writeCapacityUnits2) {
        this.writeCapacityUnits = writeCapacityUnits2;
    }

    public ProvisionedThroughput withWriteCapacityUnits(Long writeCapacityUnits2) {
        this.writeCapacityUnits = writeCapacityUnits2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getReadCapacityUnits() != null) {
            sb.append("ReadCapacityUnits: " + getReadCapacityUnits() + ",");
        }
        if (getWriteCapacityUnits() != null) {
            sb.append("WriteCapacityUnits: " + getWriteCapacityUnits());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getReadCapacityUnits() == null ? 0 : getReadCapacityUnits().hashCode()) + 31) * 31;
        if (getWriteCapacityUnits() != null) {
            i = getWriteCapacityUnits().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProvisionedThroughput)) {
            return false;
        }
        ProvisionedThroughput other = (ProvisionedThroughput) obj;
        if ((other.getReadCapacityUnits() == null) ^ (getReadCapacityUnits() == null)) {
            return false;
        }
        if (other.getReadCapacityUnits() != null && !other.getReadCapacityUnits().equals(getReadCapacityUnits())) {
            return false;
        }
        if (other.getWriteCapacityUnits() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getWriteCapacityUnits() == null)) {
            return false;
        }
        if (other.getWriteCapacityUnits() == null || other.getWriteCapacityUnits().equals(getWriteCapacityUnits())) {
            return true;
        }
        return false;
    }
}
