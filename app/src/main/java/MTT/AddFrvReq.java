package MTT;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import java.util.ArrayList;

public final class AddFrvReq extends JceStruct implements Cloneable {
    static int cache_eFvrType;
    static FrvUserBase cache_fub;
    static ArrayList<AddFrvInfo> cache_vURLInfo;
    public int eFvrType = EFvrFvrType.EFVRFVR_UNKNOW.value();
    public FrvUserBase fub = null;
    public int iAsyncFlag = 0;
    public String sCookie = "";
    public String sHttpHeader = "";
    public ArrayList<AddFrvInfo> vURLInfo = null;

    public String className() {
        return "MTT.AddFrvReq";
    }

    public String fullClassName() {
        return "MTT.AddFrvReq";
    }

    public AddFrvReq() {
    }

    public AddFrvReq(FrvUserBase fub2, int eFvrType2, String sCookie2, String sHttpHeader2, ArrayList<AddFrvInfo> vURLInfo2, int iAsyncFlag2) {
        this.fub = fub2;
        this.eFvrType = eFvrType2;
        this.sCookie = sCookie2;
        this.sHttpHeader = sHttpHeader2;
        this.vURLInfo = vURLInfo2;
        this.iAsyncFlag = iAsyncFlag2;
    }

    public void writeTo(JceOutputStream _os) {
        if (this.fub != null) {
            _os.write(this.fub, 0);
        }
        _os.write(this.eFvrType, 1);
        if (this.sCookie != null) {
            _os.write(this.sCookie, 2);
        }
        if (this.sHttpHeader != null) {
            _os.write(this.sHttpHeader, 3);
        }
        if (this.vURLInfo != null) {
            _os.write(this.vURLInfo, 4);
        }
        _os.write(this.iAsyncFlag, 5);
    }

    public void readFrom(JceInputStream _is) {
        if (cache_fub == null) {
            cache_fub = new FrvUserBase();
        }
        this.fub = _is.read(cache_fub, 0, false);
        this.eFvrType = _is.read(this.eFvrType, 1, false);
        this.sCookie = _is.readString(2, false);
        this.sHttpHeader = _is.readString(3, false);
        if (cache_vURLInfo == null) {
            cache_vURLInfo = new ArrayList<>();
            cache_vURLInfo.add(new AddFrvInfo());
        }
        this.vURLInfo = (ArrayList) _is.read(cache_vURLInfo, 4, false);
        this.iAsyncFlag = _is.read(this.iAsyncFlag, 5, false);
    }
}
