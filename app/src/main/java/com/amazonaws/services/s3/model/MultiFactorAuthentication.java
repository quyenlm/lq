package com.amazonaws.services.s3.model;

public class MultiFactorAuthentication {
    private String deviceSerialNumber;
    private String token;

    public MultiFactorAuthentication(String deviceSerialNumber2, String token2) {
        this.deviceSerialNumber = deviceSerialNumber2;
        this.token = token2;
    }

    public String getDeviceSerialNumber() {
        return this.deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber2) {
        this.deviceSerialNumber = deviceSerialNumber2;
    }

    public MultiFactorAuthentication withDeviceSerialNumber(String deviceSerialNumber2) {
        setDeviceSerialNumber(deviceSerialNumber2);
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public MultiFactorAuthentication withToken(String token2) {
        setToken(token2);
        return this;
    }
}
