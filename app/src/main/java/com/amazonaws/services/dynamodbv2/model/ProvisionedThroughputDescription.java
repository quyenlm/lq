package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.Date;

public class ProvisionedThroughputDescription implements Serializable {
    private Date lastDecreaseDateTime;
    private Date lastIncreaseDateTime;
    private Long numberOfDecreasesToday;
    private Long readCapacityUnits;
    private Long writeCapacityUnits;

    public Date getLastIncreaseDateTime() {
        return this.lastIncreaseDateTime;
    }

    public void setLastIncreaseDateTime(Date lastIncreaseDateTime2) {
        this.lastIncreaseDateTime = lastIncreaseDateTime2;
    }

    public ProvisionedThroughputDescription withLastIncreaseDateTime(Date lastIncreaseDateTime2) {
        this.lastIncreaseDateTime = lastIncreaseDateTime2;
        return this;
    }

    public Date getLastDecreaseDateTime() {
        return this.lastDecreaseDateTime;
    }

    public void setLastDecreaseDateTime(Date lastDecreaseDateTime2) {
        this.lastDecreaseDateTime = lastDecreaseDateTime2;
    }

    public ProvisionedThroughputDescription withLastDecreaseDateTime(Date lastDecreaseDateTime2) {
        this.lastDecreaseDateTime = lastDecreaseDateTime2;
        return this;
    }

    public Long getNumberOfDecreasesToday() {
        return this.numberOfDecreasesToday;
    }

    public void setNumberOfDecreasesToday(Long numberOfDecreasesToday2) {
        this.numberOfDecreasesToday = numberOfDecreasesToday2;
    }

    public ProvisionedThroughputDescription withNumberOfDecreasesToday(Long numberOfDecreasesToday2) {
        this.numberOfDecreasesToday = numberOfDecreasesToday2;
        return this;
    }

    public Long getReadCapacityUnits() {
        return this.readCapacityUnits;
    }

    public void setReadCapacityUnits(Long readCapacityUnits2) {
        this.readCapacityUnits = readCapacityUnits2;
    }

    public ProvisionedThroughputDescription withReadCapacityUnits(Long readCapacityUnits2) {
        this.readCapacityUnits = readCapacityUnits2;
        return this;
    }

    public Long getWriteCapacityUnits() {
        return this.writeCapacityUnits;
    }

    public void setWriteCapacityUnits(Long writeCapacityUnits2) {
        this.writeCapacityUnits = writeCapacityUnits2;
    }

    public ProvisionedThroughputDescription withWriteCapacityUnits(Long writeCapacityUnits2) {
        this.writeCapacityUnits = writeCapacityUnits2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getLastIncreaseDateTime() != null) {
            sb.append("LastIncreaseDateTime: " + getLastIncreaseDateTime() + ",");
        }
        if (getLastDecreaseDateTime() != null) {
            sb.append("LastDecreaseDateTime: " + getLastDecreaseDateTime() + ",");
        }
        if (getNumberOfDecreasesToday() != null) {
            sb.append("NumberOfDecreasesToday: " + getNumberOfDecreasesToday() + ",");
        }
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
        int hashCode;
        int i = 0;
        int hashCode2 = ((((getLastIncreaseDateTime() == null ? 0 : getLastIncreaseDateTime().hashCode()) + 31) * 31) + (getLastDecreaseDateTime() == null ? 0 : getLastDecreaseDateTime().hashCode())) * 31;
        if (getNumberOfDecreasesToday() == null) {
            hashCode = 0;
        } else {
            hashCode = getNumberOfDecreasesToday().hashCode();
        }
        int hashCode3 = (((hashCode2 + hashCode) * 31) + (getReadCapacityUnits() == null ? 0 : getReadCapacityUnits().hashCode())) * 31;
        if (getWriteCapacityUnits() != null) {
            i = getWriteCapacityUnits().hashCode();
        }
        return hashCode3 + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ProvisionedThroughputDescription)) {
            return false;
        }
        ProvisionedThroughputDescription other = (ProvisionedThroughputDescription) obj;
        if ((other.getLastIncreaseDateTime() == null) ^ (getLastIncreaseDateTime() == null)) {
            return false;
        }
        if (other.getLastIncreaseDateTime() != null && !other.getLastIncreaseDateTime().equals(getLastIncreaseDateTime())) {
            return false;
        }
        if (other.getLastDecreaseDateTime() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getLastDecreaseDateTime() == null)) {
            return false;
        }
        if (other.getLastDecreaseDateTime() != null && !other.getLastDecreaseDateTime().equals(getLastDecreaseDateTime())) {
            return false;
        }
        if (other.getNumberOfDecreasesToday() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getNumberOfDecreasesToday() == null)) {
            return false;
        }
        if (other.getNumberOfDecreasesToday() != null && !other.getNumberOfDecreasesToday().equals(getNumberOfDecreasesToday())) {
            return false;
        }
        if (other.getReadCapacityUnits() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getReadCapacityUnits() == null)) {
            return false;
        }
        if (other.getReadCapacityUnits() != null && !other.getReadCapacityUnits().equals(getReadCapacityUnits())) {
            return false;
        }
        if (other.getWriteCapacityUnits() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getWriteCapacityUnits() == null)) {
            return false;
        }
        if (other.getWriteCapacityUnits() == null || other.getWriteCapacityUnits().equals(getWriteCapacityUnits())) {
            return true;
        }
        return false;
    }
}
