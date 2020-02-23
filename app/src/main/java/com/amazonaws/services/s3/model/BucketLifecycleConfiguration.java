package com.amazonaws.services.s3.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BucketLifecycleConfiguration {
    public static final String DISABLED = "Disabled";
    public static final String ENABLED = "Enabled";
    private List<Rule> rules;

    public List<Rule> getRules() {
        return this.rules;
    }

    public void setRules(List<Rule> rules2) {
        this.rules = rules2;
    }

    public BucketLifecycleConfiguration withRules(List<Rule> rules2) {
        setRules(rules2);
        return this;
    }

    public BucketLifecycleConfiguration withRules(Rule... rules2) {
        setRules(Arrays.asList(rules2));
        return this;
    }

    public BucketLifecycleConfiguration(List<Rule> rules2) {
        this.rules = rules2;
    }

    public BucketLifecycleConfiguration() {
    }

    public static class Rule {
        private Date expirationDate;
        private int expirationInDays = -1;
        private String id;
        private int noncurrentVersionExpirationInDays = -1;
        private NoncurrentVersionTransition noncurrentVersionTransition;
        private String prefix;
        private String status;
        private Transition transition;

        public void setId(String id2) {
            this.id = id2;
        }

        public void setPrefix(String prefix2) {
            this.prefix = prefix2;
        }

        public void setExpirationInDays(int expirationInDays2) {
            this.expirationInDays = expirationInDays2;
        }

        public void setNoncurrentVersionExpirationInDays(int value) {
            this.noncurrentVersionExpirationInDays = value;
        }

        public String getId() {
            return this.id;
        }

        public Rule withId(String id2) {
            this.id = id2;
            return this;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public Rule withPrefix(String prefix2) {
            this.prefix = prefix2;
            return this;
        }

        public int getExpirationInDays() {
            return this.expirationInDays;
        }

        public Rule withExpirationInDays(int expirationInDays2) {
            this.expirationInDays = expirationInDays2;
            return this;
        }

        public int getNoncurrentVersionExpirationInDays() {
            return this.noncurrentVersionExpirationInDays;
        }

        public Rule withNoncurrentVersionExpirationInDays(int value) {
            setNoncurrentVersionExpirationInDays(value);
            return this;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status2) {
            this.status = status2;
        }

        public Rule withStatus(String status2) {
            setStatus(status2);
            return this;
        }

        public void setExpirationDate(Date expirationDate2) {
            this.expirationDate = expirationDate2;
        }

        public Date getExpirationDate() {
            return this.expirationDate;
        }

        public Rule withExpirationDate(Date expirationDate2) {
            this.expirationDate = expirationDate2;
            return this;
        }

        public void setTransition(Transition transition2) {
            this.transition = transition2;
        }

        public Transition getTransition() {
            return this.transition;
        }

        public Rule withTransition(Transition transition2) {
            this.transition = transition2;
            return this;
        }

        public void setNoncurrentVersionTransition(NoncurrentVersionTransition value) {
            this.noncurrentVersionTransition = value;
        }

        public NoncurrentVersionTransition getNoncurrentVersionTransition() {
            return this.noncurrentVersionTransition;
        }

        public Rule withNoncurrentVersionTransition(NoncurrentVersionTransition value) {
            setNoncurrentVersionTransition(value);
            return this;
        }
    }

    public static class Transition {
        private Date date;
        private int days = -1;
        private StorageClass storageClass;

        public void setDays(int expirationInDays) {
            this.days = expirationInDays;
        }

        public int getDays() {
            return this.days;
        }

        public Transition withDays(int expirationInDays) {
            this.days = expirationInDays;
            return this;
        }

        public void setStorageClass(StorageClass storageClass2) {
            this.storageClass = storageClass2;
        }

        public StorageClass getStorageClass() {
            return this.storageClass;
        }

        public Transition withStorageClass(StorageClass storageClass2) {
            this.storageClass = storageClass2;
            return this;
        }

        public void setDate(Date expirationDate) {
            this.date = expirationDate;
        }

        public Date getDate() {
            return this.date;
        }

        public Transition withDate(Date expirationDate) {
            this.date = expirationDate;
            return this;
        }
    }

    public static class NoncurrentVersionTransition {
        private int days = -1;
        private StorageClass storageClass;

        public void setDays(int expirationInDays) {
            this.days = expirationInDays;
        }

        public int getDays() {
            return this.days;
        }

        public NoncurrentVersionTransition withDays(int expirationInDays) {
            this.days = expirationInDays;
            return this;
        }

        public void setStorageClass(StorageClass storageClass2) {
            this.storageClass = storageClass2;
        }

        public StorageClass getStorageClass() {
            return this.storageClass;
        }

        public NoncurrentVersionTransition withStorageClass(StorageClass storageClass2) {
            this.storageClass = storageClass2;
            return this;
        }
    }
}
