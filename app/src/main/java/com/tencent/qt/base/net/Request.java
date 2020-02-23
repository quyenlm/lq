package com.tencent.qt.base.net;

public class Request {
    public static final int FLAG_ENCRYPT = 1;
    public int command;
    public byte[] extra;
    public int flag;
    public boolean needSequenceNumber;
    public byte[] payload;
    public byte[] reserved;
    public int sequenceNumber = 0;
    public int subcmd;

    public static Request createEncryptRequest(int command2, int subcmd2, byte[] payload2, byte[] reserve, byte[] extra2) {
        Request request = new Request();
        request.command = command2;
        request.subcmd = subcmd2;
        request.payload = payload2;
        request.reserved = reserve;
        request.flag = 1;
        request.extra = extra2;
        request.needSequenceNumber = true;
        return request;
    }
}
