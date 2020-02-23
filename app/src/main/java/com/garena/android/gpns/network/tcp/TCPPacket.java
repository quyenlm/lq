package com.garena.android.gpns.network.tcp;

public class TCPPacket {
    private int mCommand;
    private byte[] mData;
    private boolean mIsTimed = false;
    private int timeout = 60000;

    public TCPPacket(int mCommand2, byte[] mData2) {
        this.mCommand = mCommand2;
        this.mData = mData2;
    }

    public int getCommand() {
        return this.mCommand;
    }

    public byte[] getData() {
        return this.mData;
    }

    private static void assignInt(byte[] arr, int len) {
        arr[0] = (byte) (len & 255);
        arr[1] = (byte) ((65280 & len) >> 8);
        arr[2] = (byte) ((16711680 & len) >> 16);
        arr[3] = (byte) ((-16777216 & len) >> 24);
    }

    public byte[] toByteArray() {
        int nCount = this.mData.length + 5;
        byte[] arr = new byte[nCount];
        arr[4] = (byte) this.mCommand;
        assignInt(arr, nCount - 4);
        System.arraycopy(this.mData, 0, arr, 5, this.mData.length);
        return arr;
    }

    public void setTimed(boolean isTimed) {
        this.mIsTimed = isTimed;
    }

    public boolean isTimed() {
        return this.mIsTimed;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout2) {
        this.timeout = timeout2;
    }
}
