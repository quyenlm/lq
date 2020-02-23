package MTT;

public final class ThirdAppInfo implements Cloneable {
    public long iPv = 0;
    public String sAppName = "";
    public String sGuid = "";
    public String sLc = "";
    public String sQua = "";
    public String sTime = "";

    public String className() {
        return "MTT.ThirdAppInfo";
    }

    public String fullClassName() {
        return "MTT.ThirdAppInfo";
    }

    public ThirdAppInfo() {
    }

    public ThirdAppInfo(String sAppName2, String sTime2, String sQua2, String sLc2, String sGuid2, long iPv2) {
        this.sAppName = sAppName2;
        this.sTime = sTime2;
        this.sQua = sQua2;
        this.sLc = sLc2;
        this.sGuid = sGuid2;
        this.iPv = iPv2;
    }
}
