package com.tencent.qqgamemi.mgc.connection;

public interface ProtoFactory {

    public static class ProtoRequest {
        public int command;
        public byte[] data;
        public byte[] extra;
        public byte[] reverve;
        public int subcmd;

        public ProtoRequest(int command2, int subcmd2, byte[] data2) {
            this.command = command2;
            this.subcmd = subcmd2;
            this.data = data2;
        }
    }
}
