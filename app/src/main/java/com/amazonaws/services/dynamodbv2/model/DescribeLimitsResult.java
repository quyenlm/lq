package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class DescribeLimitsResult implements Serializable {
    private Long accountMaxReadCapacityUnits;
    private Long accountMaxWriteCapacityUnits;
    private Long tableMaxReadCapacityUnits;
    private Long tableMaxWriteCapacityUnits;

    public Long getAccountMaxReadCapacityUnits() {
        return this.accountMaxReadCapacityUnits;
    }

    public void setAccountMaxReadCapacityUnits(Long accountMaxReadCapacityUnits2) {
        this.accountMaxReadCapacityUnits = accountMaxReadCapacityUnits2;
    }

    public DescribeLimitsResult withAccountMaxReadCapacityUnits(Long accountMaxReadCapacityUnits2) {
        this.accountMaxReadCapacityUnits = accountMaxReadCapacityUnits2;
        return this;
    }

    public Long getAccountMaxWriteCapacityUnits() {
        return this.accountMaxWriteCapacityUnits;
    }

    public void setAccountMaxWriteCapacityUnits(Long accountMaxWriteCapacityUnits2) {
        this.accountMaxWriteCapacityUnits = accountMaxWriteCapacityUnits2;
    }

    public DescribeLimitsResult withAccountMaxWriteCapacityUnits(Long accountMaxWriteCapacityUnits2) {
        this.accountMaxWriteCapacityUnits = accountMaxWriteCapacityUnits2;
        return this;
    }

    public Long getTableMaxReadCapacityUnits() {
        return this.tableMaxReadCapacityUnits;
    }

    public void setTableMaxReadCapacityUnits(Long tableMaxReadCapacityUnits2) {
        this.tableMaxReadCapacityUnits = tableMaxReadCapacityUnits2;
    }

    public DescribeLimitsResult withTableMaxReadCapacityUnits(Long tableMaxReadCapacityUnits2) {
        this.tableMaxReadCapacityUnits = tableMaxReadCapacityUnits2;
        return this;
    }

    public Long getTableMaxWriteCapacityUnits() {
        return this.tableMaxWriteCapacityUnits;
    }

    public void setTableMaxWriteCapacityUnits(Long tableMaxWriteCapacityUnits2) {
        this.tableMaxWriteCapacityUnits = tableMaxWriteCapacityUnits2;
    }

    public DescribeLimitsResult withTableMaxWriteCapacityUnits(Long tableMaxWriteCapacityUnits2) {
        this.tableMaxWriteCapacityUnits = tableMaxWriteCapacityUnits2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccountMaxReadCapacityUnits() != null) {
            sb.append("AccountMaxReadCapacityUnits: " + getAccountMaxReadCapacityUnits() + ",");
        }
        if (getAccountMaxWriteCapacityUnits() != null) {
            sb.append("AccountMaxWriteCapacityUnits: " + getAccountMaxWriteCapacityUnits() + ",");
        }
        if (getTableMaxReadCapacityUnits() != null) {
            sb.append("TableMaxReadCapacityUnits: " + getTableMaxReadCapacityUnits() + ",");
        }
        if (getTableMaxWriteCapacityUnits() != null) {
            sb.append("TableMaxWriteCapacityUnits: " + getTableMaxWriteCapacityUnits());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        int hashCode2;
        int hashCode3;
        int i = 0;
        if (getAccountMaxReadCapacityUnits() == null) {
            hashCode = 0;
        } else {
            hashCode = getAccountMaxReadCapacityUnits().hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        if (getAccountMaxWriteCapacityUnits() == null) {
            hashCode2 = 0;
        } else {
            hashCode2 = getAccountMaxWriteCapacityUnits().hashCode();
        }
        int i3 = (i2 + hashCode2) * 31;
        if (getTableMaxReadCapacityUnits() == null) {
            hashCode3 = 0;
        } else {
            hashCode3 = getTableMaxReadCapacityUnits().hashCode();
        }
        int i4 = (i3 + hashCode3) * 31;
        if (getTableMaxWriteCapacityUnits() != null) {
            i = getTableMaxWriteCapacityUnits().hashCode();
        }
        return i4 + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeLimitsResult)) {
            return false;
        }
        DescribeLimitsResult other = (DescribeLimitsResult) obj;
        boolean z8 = other.getAccountMaxReadCapacityUnits() == null;
        if (getAccountMaxReadCapacityUnits() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z8 ^ z) {
            return false;
        }
        if (other.getAccountMaxReadCapacityUnits() != null && !other.getAccountMaxReadCapacityUnits().equals(getAccountMaxReadCapacityUnits())) {
            return false;
        }
        if (other.getAccountMaxWriteCapacityUnits() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (getAccountMaxWriteCapacityUnits() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 ^ z3) {
            return false;
        }
        if (other.getAccountMaxWriteCapacityUnits() != null && !other.getAccountMaxWriteCapacityUnits().equals(getAccountMaxWriteCapacityUnits())) {
            return false;
        }
        if (other.getTableMaxReadCapacityUnits() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (getTableMaxReadCapacityUnits() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z4 ^ z5) {
            return false;
        }
        if (other.getTableMaxReadCapacityUnits() != null && !other.getTableMaxReadCapacityUnits().equals(getTableMaxReadCapacityUnits())) {
            return false;
        }
        if (other.getTableMaxWriteCapacityUnits() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (getTableMaxWriteCapacityUnits() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z6 ^ z7) {
            return false;
        }
        if (other.getTableMaxWriteCapacityUnits() == null || other.getTableMaxWriteCapacityUnits().equals(getTableMaxWriteCapacityUnits())) {
            return true;
        }
        return false;
    }
}
