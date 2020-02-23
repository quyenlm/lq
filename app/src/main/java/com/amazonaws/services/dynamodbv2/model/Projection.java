package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Projection implements Serializable {
    private List<String> nonKeyAttributes;
    private String projectionType;

    public String getProjectionType() {
        return this.projectionType;
    }

    public void setProjectionType(String projectionType2) {
        this.projectionType = projectionType2;
    }

    public Projection withProjectionType(String projectionType2) {
        this.projectionType = projectionType2;
        return this;
    }

    public void setProjectionType(ProjectionType projectionType2) {
        this.projectionType = projectionType2.toString();
    }

    public Projection withProjectionType(ProjectionType projectionType2) {
        this.projectionType = projectionType2.toString();
        return this;
    }

    public List<String> getNonKeyAttributes() {
        return this.nonKeyAttributes;
    }

    public void setNonKeyAttributes(Collection<String> nonKeyAttributes2) {
        if (nonKeyAttributes2 == null) {
            this.nonKeyAttributes = null;
        } else {
            this.nonKeyAttributes = new ArrayList(nonKeyAttributes2);
        }
    }

    public Projection withNonKeyAttributes(String... nonKeyAttributes2) {
        if (getNonKeyAttributes() == null) {
            this.nonKeyAttributes = new ArrayList(nonKeyAttributes2.length);
        }
        for (String value : nonKeyAttributes2) {
            this.nonKeyAttributes.add(value);
        }
        return this;
    }

    public Projection withNonKeyAttributes(Collection<String> nonKeyAttributes2) {
        setNonKeyAttributes(nonKeyAttributes2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getProjectionType() != null) {
            sb.append("ProjectionType: " + getProjectionType() + ",");
        }
        if (getNonKeyAttributes() != null) {
            sb.append("NonKeyAttributes: " + getNonKeyAttributes());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getProjectionType() == null ? 0 : getProjectionType().hashCode()) + 31) * 31;
        if (getNonKeyAttributes() != null) {
            i = getNonKeyAttributes().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Projection)) {
            return false;
        }
        Projection other = (Projection) obj;
        if ((other.getProjectionType() == null) ^ (getProjectionType() == null)) {
            return false;
        }
        if (other.getProjectionType() != null && !other.getProjectionType().equals(getProjectionType())) {
            return false;
        }
        if (other.getNonKeyAttributes() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getNonKeyAttributes() == null)) {
            return false;
        }
        if (other.getNonKeyAttributes() == null || other.getNonKeyAttributes().equals(getNonKeyAttributes())) {
            return true;
        }
        return false;
    }
}
