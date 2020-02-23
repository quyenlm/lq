package MTT;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import java.util.ArrayList;

public final class AddFrvInfo extends JceStruct implements Cloneable {
    static ArrayList<FvrContentField> cache_vContentField;
    static byte[] cache_vFileData;
    static ArrayList<Integer> cache_vTagId;
    public long iFrvTime = 0;
    public String sFrom = "";
    public String sImageUrl = "";
    public String sNewsId = "";
    public String sRefURL = "";
    public String sSummary = "";
    public String sTitle = "";
    public String sURL = "";
    public ArrayList<FvrContentField> vContentField = null;
    public byte[] vFileData = null;
    public ArrayList<Integer> vTagId = null;

    public String className() {
        return "MTT.AddFrvInfo";
    }

    public String fullClassName() {
        return "MTT.AddFrvInfo";
    }

    public AddFrvInfo() {
    }

    public AddFrvInfo(String sURL2, String sTitle2, String sRefURL2, ArrayList<FvrContentField> vContentField2, byte[] vFileData2, String sFrom2, ArrayList<Integer> vTagId2, String sNewsId2, long iFrvTime2, String sSummary2, String sImageUrl2) {
        this.sURL = sURL2;
        this.sTitle = sTitle2;
        this.sRefURL = sRefURL2;
        this.vContentField = vContentField2;
        this.vFileData = vFileData2;
        this.sFrom = sFrom2;
        this.vTagId = vTagId2;
        this.sNewsId = sNewsId2;
        this.iFrvTime = iFrvTime2;
        this.sSummary = sSummary2;
        this.sImageUrl = sImageUrl2;
    }

    public void writeTo(JceOutputStream _os) {
        if (this.sURL != null) {
            _os.write(this.sURL, 0);
        }
        if (this.sTitle != null) {
            _os.write(this.sTitle, 1);
        }
        if (this.sRefURL != null) {
            _os.write(this.sRefURL, 2);
        }
        if (this.vContentField != null) {
            _os.write(this.vContentField, 3);
        }
        if (this.vFileData != null) {
            _os.write(this.vFileData, 4);
        }
        if (this.sFrom != null) {
            _os.write(this.sFrom, 5);
        }
        if (this.vTagId != null) {
            _os.write(this.vTagId, 6);
        }
        if (this.sNewsId != null) {
            _os.write(this.sNewsId, 7);
        }
        _os.write(this.iFrvTime, 8);
        if (this.sSummary != null) {
            _os.write(this.sSummary, 9);
        }
        if (this.sImageUrl != null) {
            _os.write(this.sImageUrl, 10);
        }
    }

    public void readFrom(JceInputStream _is) {
        this.sURL = _is.readString(0, false);
        this.sTitle = _is.readString(1, false);
        this.sRefURL = _is.readString(2, false);
        if (cache_vContentField == null) {
            cache_vContentField = new ArrayList<>();
            cache_vContentField.add(new FvrContentField());
        }
        this.vContentField = (ArrayList) _is.read(cache_vContentField, 3, false);
        if (cache_vFileData == null) {
            cache_vFileData = new byte[1];
            cache_vFileData[0] = 0;
        }
        this.vFileData = _is.read(cache_vFileData, 4, false);
        this.sFrom = _is.readString(5, false);
        if (cache_vTagId == null) {
            cache_vTagId = new ArrayList<>();
            cache_vTagId.add(0);
        }
        this.vTagId = (ArrayList) _is.read(cache_vTagId, 6, false);
        this.sNewsId = _is.readString(7, false);
        this.iFrvTime = _is.read(this.iFrvTime, 8, false);
        this.sSummary = _is.readString(9, false);
        this.sImageUrl = _is.readString(10, false);
    }
}
