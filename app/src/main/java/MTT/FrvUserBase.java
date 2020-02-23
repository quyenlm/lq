package MTT;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public final class FrvUserBase extends JceStruct implements Cloneable {
    static int cache_eChannel;
    public String APN = "";
    public int eChannel = EFvrChannel.EFVRCHN_UNKNOW.value();
    public String sGUID = "";
    public String sKey = "";
    public String sQUA = "";
    public String sSID = "";
    public String sST = "";
    public String sUin = "";
    public String sUserIP = "";

    public String className() {
        return "MTT.FrvUserBase";
    }

    public String fullClassName() {
        return "MTT.FrvUserBase";
    }

    public FrvUserBase() {
    }

    public FrvUserBase(String sUin2, String sSID2, String sGUID2, String sQUA2, int eChannel2, String sUserIP2, String sST2, String sKey2, String APN2) {
        this.sUin = sUin2;
        this.sSID = sSID2;
        this.sGUID = sGUID2;
        this.sQUA = sQUA2;
        this.eChannel = eChannel2;
        this.sUserIP = sUserIP2;
        this.sST = sST2;
        this.sKey = sKey2;
        this.APN = APN2;
    }

    public void writeTo(JceOutputStream _os) {
        if (this.sUin != null) {
            _os.write(this.sUin, 0);
        }
        if (this.sSID != null) {
            _os.write(this.sSID, 1);
        }
        if (this.sGUID != null) {
            _os.write(this.sGUID, 2);
        }
        if (this.sQUA != null) {
            _os.write(this.sQUA, 3);
        }
        _os.write(this.eChannel, 4);
        if (this.sUserIP != null) {
            _os.write(this.sUserIP, 5);
        }
        if (this.sST != null) {
            _os.write(this.sST, 6);
        }
        if (this.sKey != null) {
            _os.write(this.sKey, 7);
        }
        if (this.APN != null) {
            _os.write(this.APN, 8);
        }
    }

    public void readFrom(JceInputStream _is) {
        this.sUin = _is.readString(0, false);
        this.sSID = _is.readString(1, false);
        this.sGUID = _is.readString(2, false);
        this.sQUA = _is.readString(3, false);
        this.eChannel = _is.read(this.eChannel, 4, false);
        this.sUserIP = _is.readString(5, false);
        this.sST = _is.readString(6, false);
        this.sKey = _is.readString(7, false);
        this.APN = _is.readString(8, false);
    }
}
