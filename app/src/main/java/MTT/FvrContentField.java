package MTT;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public final class FvrContentField extends JceStruct implements Cloneable {
    static int cache_eCff;
    public int eCff = 0;
    public String sVal = "";

    public String className() {
        return "MTT.FvrContentField";
    }

    public String fullClassName() {
        return "MTT.FvrContentField";
    }

    public FvrContentField() {
    }

    public FvrContentField(int eCff2, String sVal2) {
        this.eCff = eCff2;
        this.sVal = sVal2;
    }

    public void writeTo(JceOutputStream _os) {
        _os.write(this.eCff, 0);
        if (this.sVal != null) {
            _os.write(this.sVal, 1);
        }
    }

    public void readFrom(JceInputStream _is) {
        this.eCff = _is.read(this.eCff, 0, false);
        this.sVal = _is.readString(1, false);
    }
}
