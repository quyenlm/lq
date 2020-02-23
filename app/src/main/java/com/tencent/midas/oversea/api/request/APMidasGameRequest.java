package com.tencent.midas.oversea.api.request;

public class APMidasGameRequest extends APMidasBaseRequest {
    private static final long serialVersionUID = -1453333674013082952L;

    public APMidasGameRequest() {
        this.mType = APMidasBaseRequest.OFFER_TYPE_GAME;
        this.mOldType = 0;
    }
}
