package com.amazonaws.services.dynamodbv2.model;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperator {
    EQ("EQ"),
    NE("NE"),
    IN("IN"),
    LE("LE"),
    LT("LT"),
    GE("GE"),
    GT("GT"),
    BETWEEN("BETWEEN"),
    NOT_NULL("NOT_NULL"),
    NULL("NULL"),
    CONTAINS("CONTAINS"),
    NOT_CONTAINS("NOT_CONTAINS"),
    BEGINS_WITH("BEGINS_WITH");
    
    private static final Map<String, ComparisonOperator> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("EQ", EQ);
        enumMap.put("NE", NE);
        enumMap.put("IN", IN);
        enumMap.put("LE", LE);
        enumMap.put("LT", LT);
        enumMap.put("GE", GE);
        enumMap.put("GT", GT);
        enumMap.put("BETWEEN", BETWEEN);
        enumMap.put("NOT_NULL", NOT_NULL);
        enumMap.put("NULL", NULL);
        enumMap.put("CONTAINS", CONTAINS);
        enumMap.put("NOT_CONTAINS", NOT_CONTAINS);
        enumMap.put("BEGINS_WITH", BEGINS_WITH);
    }

    private ComparisonOperator(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ComparisonOperator fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
