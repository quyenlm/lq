package com.tencent.tdm.defines;

public class DBEvent {
    public byte[] Data;
    public int DataLen;
    public int EventID;
    public long ID;
    public int SrcId;

    public DBEvent() {
    }

    public DBEvent(long id, int eId, int SrcId2, int dataLen, byte[] data) {
        this.Data = data;
        this.EventID = eId;
        this.SrcId = SrcId2;
        this.DataLen = dataLen;
        this.ID = id;
    }
}
