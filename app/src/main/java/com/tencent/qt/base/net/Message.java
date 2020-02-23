package com.tencent.qt.base.net;

public class Message {
    public int clientType;
    public int command;
    public byte[] extra;
    public int flag;
    public byte[] payload;
    public byte[] reserved;
    public int sequenceNumber = 0;
    public int subcmd;

    public boolean isAccessDenied() {
        return (this.flag & 16) != 0;
    }

    public static Message createMessage(int command2, int subcmd2, int clienttype, int seq, byte[] payload2, byte[] reserve, byte[] extra2) {
        return createMessage(command2, subcmd2, clienttype, 0, seq, payload2, reserve, extra2);
    }

    public static Message createMessage(int command2, int subcmd2, int clienttype, int flag2, int seq, byte[] payload2, byte[] reserve, byte[] extra2) {
        Message msg = new Message();
        msg.command = command2;
        msg.subcmd = subcmd2;
        msg.clientType = clienttype;
        msg.sequenceNumber = seq;
        msg.payload = payload2;
        msg.reserved = reserve;
        msg.extra = extra2;
        msg.flag = flag2;
        return msg;
    }
}
