package com.amazonaws.services.s3.model;

public class RequestPaymentConfiguration {
    private Payer payer;

    public enum Payer {
        Requester,
        BucketOwner
    }

    public RequestPaymentConfiguration(Payer payer2) {
        this.payer = payer2;
    }

    public Payer getPayer() {
        return this.payer;
    }

    public void setPayer(Payer payer2) {
        this.payer = payer2;
    }
}
