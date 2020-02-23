package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import java.util.Arrays;

public class BooleanCondition extends Condition {
    public BooleanCondition(String key, boolean value) {
        this.type = "Bool";
        this.conditionKey = key;
        this.values = Arrays.asList(new String[]{Boolean.toString(value)});
    }
}
