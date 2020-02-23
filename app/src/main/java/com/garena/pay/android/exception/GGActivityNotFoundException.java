package com.garena.pay.android.exception;

public class GGActivityNotFoundException extends GGException {
    public GGActivityNotFoundException() {
        super("Activity Not Found.");
    }

    public GGActivityNotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
