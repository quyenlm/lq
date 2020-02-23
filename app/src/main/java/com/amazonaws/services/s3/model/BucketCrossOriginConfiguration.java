package com.amazonaws.services.s3.model;

import java.util.Arrays;
import java.util.List;

public class BucketCrossOriginConfiguration {
    private List<CORSRule> rules;

    public List<CORSRule> getRules() {
        return this.rules;
    }

    public void setRules(List<CORSRule> rules2) {
        this.rules = rules2;
    }

    public BucketCrossOriginConfiguration withRules(List<CORSRule> rules2) {
        setRules(rules2);
        return this;
    }

    public BucketCrossOriginConfiguration withRules(CORSRule... rules2) {
        setRules(Arrays.asList(rules2));
        return this;
    }

    public BucketCrossOriginConfiguration(List<CORSRule> rules2) {
        this.rules = rules2;
    }

    public BucketCrossOriginConfiguration() {
    }
}
