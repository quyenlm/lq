package com.garena.pay.android.data;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class GGRebateItem {
    private double amount = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private int days = 0;
    private String description = "";
    private long id = 0;
    private String name = "";
    private boolean valid = false;

    public long getId() {
        return this.id;
    }

    public void setId(long id2) {
        this.id = id2;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days2) {
        this.days = days2;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount2) {
        this.amount = amount2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid2) {
        this.valid = valid2;
    }
}
