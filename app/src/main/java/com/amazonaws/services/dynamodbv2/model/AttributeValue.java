package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeValue implements Serializable {
    private ByteBuffer b;
    private Boolean bOOL;
    private List<ByteBuffer> bS;
    private List<AttributeValue> l;
    private Map<String, AttributeValue> m;
    private String n;
    private List<String> nS;
    private Boolean nULL;
    private String s;
    private List<String> sS;

    public AttributeValue() {
    }

    public AttributeValue(String s2) {
        setS(s2);
    }

    public AttributeValue(List<String> sS2) {
        setSS(sS2);
    }

    public String getS() {
        return this.s;
    }

    public void setS(String s2) {
        this.s = s2;
    }

    public AttributeValue withS(String s2) {
        this.s = s2;
        return this;
    }

    public String getN() {
        return this.n;
    }

    public void setN(String n2) {
        this.n = n2;
    }

    public AttributeValue withN(String n2) {
        this.n = n2;
        return this;
    }

    public ByteBuffer getB() {
        return this.b;
    }

    public void setB(ByteBuffer b2) {
        this.b = b2;
    }

    public AttributeValue withB(ByteBuffer b2) {
        this.b = b2;
        return this;
    }

    public List<String> getSS() {
        return this.sS;
    }

    public void setSS(Collection<String> sS2) {
        if (sS2 == null) {
            this.sS = null;
        } else {
            this.sS = new ArrayList(sS2);
        }
    }

    public AttributeValue withSS(String... sS2) {
        if (getSS() == null) {
            this.sS = new ArrayList(sS2.length);
        }
        for (String value : sS2) {
            this.sS.add(value);
        }
        return this;
    }

    public AttributeValue withSS(Collection<String> sS2) {
        setSS(sS2);
        return this;
    }

    public List<String> getNS() {
        return this.nS;
    }

    public void setNS(Collection<String> nS2) {
        if (nS2 == null) {
            this.nS = null;
        } else {
            this.nS = new ArrayList(nS2);
        }
    }

    public AttributeValue withNS(String... nS2) {
        if (getNS() == null) {
            this.nS = new ArrayList(nS2.length);
        }
        for (String value : nS2) {
            this.nS.add(value);
        }
        return this;
    }

    public AttributeValue withNS(Collection<String> nS2) {
        setNS(nS2);
        return this;
    }

    public List<ByteBuffer> getBS() {
        return this.bS;
    }

    public void setBS(Collection<ByteBuffer> bS2) {
        if (bS2 == null) {
            this.bS = null;
        } else {
            this.bS = new ArrayList(bS2);
        }
    }

    public AttributeValue withBS(ByteBuffer... bS2) {
        if (getBS() == null) {
            this.bS = new ArrayList(bS2.length);
        }
        for (ByteBuffer value : bS2) {
            this.bS.add(value);
        }
        return this;
    }

    public AttributeValue withBS(Collection<ByteBuffer> bS2) {
        setBS(bS2);
        return this;
    }

    public Map<String, AttributeValue> getM() {
        return this.m;
    }

    public void setM(Map<String, AttributeValue> m2) {
        this.m = m2;
    }

    public AttributeValue withM(Map<String, AttributeValue> m2) {
        this.m = m2;
        return this;
    }

    public AttributeValue addMEntry(String key, AttributeValue value) {
        if (this.m == null) {
            this.m = new HashMap();
        }
        if (this.m.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        }
        this.m.put(key, value);
        return this;
    }

    public AttributeValue clearMEntries() {
        this.m = null;
        return this;
    }

    public List<AttributeValue> getL() {
        return this.l;
    }

    public void setL(Collection<AttributeValue> l2) {
        if (l2 == null) {
            this.l = null;
        } else {
            this.l = new ArrayList(l2);
        }
    }

    public AttributeValue withL(AttributeValue... l2) {
        if (getL() == null) {
            this.l = new ArrayList(l2.length);
        }
        for (AttributeValue value : l2) {
            this.l.add(value);
        }
        return this;
    }

    public AttributeValue withL(Collection<AttributeValue> l2) {
        setL(l2);
        return this;
    }

    public Boolean isNULL() {
        return this.nULL;
    }

    public Boolean getNULL() {
        return this.nULL;
    }

    public void setNULL(Boolean nULL2) {
        this.nULL = nULL2;
    }

    public AttributeValue withNULL(Boolean nULL2) {
        this.nULL = nULL2;
        return this;
    }

    public Boolean isBOOL() {
        return this.bOOL;
    }

    public Boolean getBOOL() {
        return this.bOOL;
    }

    public void setBOOL(Boolean bOOL2) {
        this.bOOL = bOOL2;
    }

    public AttributeValue withBOOL(Boolean bOOL2) {
        this.bOOL = bOOL2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getS() != null) {
            sb.append("S: " + getS() + ",");
        }
        if (getN() != null) {
            sb.append("N: " + getN() + ",");
        }
        if (getB() != null) {
            sb.append("B: " + getB() + ",");
        }
        if (getSS() != null) {
            sb.append("SS: " + getSS() + ",");
        }
        if (getNS() != null) {
            sb.append("NS: " + getNS() + ",");
        }
        if (getBS() != null) {
            sb.append("BS: " + getBS() + ",");
        }
        if (getM() != null) {
            sb.append("M: " + getM() + ",");
        }
        if (getL() != null) {
            sb.append("L: " + getL() + ",");
        }
        if (getNULL() != null) {
            sb.append("NULL: " + getNULL() + ",");
        }
        if (getBOOL() != null) {
            sb.append("BOOL: " + getBOOL());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((getS() == null ? 0 : getS().hashCode()) + 31) * 31) + (getN() == null ? 0 : getN().hashCode())) * 31) + (getB() == null ? 0 : getB().hashCode())) * 31) + (getSS() == null ? 0 : getSS().hashCode())) * 31) + (getNS() == null ? 0 : getNS().hashCode())) * 31) + (getBS() == null ? 0 : getBS().hashCode())) * 31) + (getM() == null ? 0 : getM().hashCode())) * 31) + (getL() == null ? 0 : getL().hashCode())) * 31) + (getNULL() == null ? 0 : getNULL().hashCode())) * 31;
        if (getBOOL() != null) {
            i = getBOOL().hashCode();
        }
        return hashCode + i;
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
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AttributeValue)) {
            return false;
        }
        AttributeValue other = (AttributeValue) obj;
        if ((other.getS() == null) ^ (getS() == null)) {
            return false;
        }
        if (other.getS() != null && !other.getS().equals(getS())) {
            return false;
        }
        if (other.getN() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getN() == null)) {
            return false;
        }
        if (other.getN() != null && !other.getN().equals(getN())) {
            return false;
        }
        if (other.getB() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getB() == null)) {
            return false;
        }
        if (other.getB() != null && !other.getB().equals(getB())) {
            return false;
        }
        if (other.getSS() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 ^ (getSS() == null)) {
            return false;
        }
        if (other.getSS() != null && !other.getSS().equals(getSS())) {
            return false;
        }
        if (other.getNS() == null) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z4 ^ (getNS() == null)) {
            return false;
        }
        if (other.getNS() != null && !other.getNS().equals(getNS())) {
            return false;
        }
        if (other.getBS() == null) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5 ^ (getBS() == null)) {
            return false;
        }
        if (other.getBS() != null && !other.getBS().equals(getBS())) {
            return false;
        }
        if (other.getM() == null) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6 ^ (getM() == null)) {
            return false;
        }
        if (other.getM() != null && !other.getM().equals(getM())) {
            return false;
        }
        if (other.getL() == null) {
            z7 = true;
        } else {
            z7 = false;
        }
        if (z7 ^ (getL() == null)) {
            return false;
        }
        if (other.getL() != null && !other.getL().equals(getL())) {
            return false;
        }
        if (other.getNULL() == null) {
            z8 = true;
        } else {
            z8 = false;
        }
        if (z8 ^ (getNULL() == null)) {
            return false;
        }
        if (other.getNULL() != null && !other.getNULL().equals(getNULL())) {
            return false;
        }
        if (other.getBOOL() == null) {
            z9 = true;
        } else {
            z9 = false;
        }
        if (z9 ^ (getBOOL() == null)) {
            return false;
        }
        if (other.getBOOL() == null || other.getBOOL().equals(getBOOL())) {
            return true;
        }
        return false;
    }
}
