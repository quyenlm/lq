package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.z;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Parcelable.Creator<CrashDetailBean> CREATOR = new Parcelable.Creator<CrashDetailBean>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CrashDetailBean[i];
        }
    };
    public String A = "";
    public String B = "";
    public long C = -1;
    public long D = -1;
    public long E = -1;
    public long F = -1;
    public long G = -1;
    public long H = -1;
    public String I = "";
    public String J = "";
    public String K = "";
    public String L = "";
    public long M = -1;
    public boolean N = false;
    public Map<String, String> O = null;
    public int P = -1;
    public int Q = -1;
    public Map<String, String> R = null;
    public Map<String, String> S = null;
    public byte[] T = null;
    public String U = null;
    public String V = null;
    private String W = "";
    public long a = -1;
    public int b = 0;
    public String c = UUID.randomUUID().toString();
    public boolean d = false;
    public String e = "";
    public String f = "";
    public String g = "";
    public Map<String, PlugInBean> h = null;
    public Map<String, PlugInBean> i = null;
    public boolean j = false;
    public boolean k = false;
    public int l = 0;
    public String m = "";
    public String n = "";
    public String o = "";
    public String p = "";
    public String q = "";
    public long r = -1;
    public String s = null;
    public int t = 0;
    public String u = "";
    public String v = "";
    public String w = null;
    public String x = null;
    public byte[] y = null;
    public Map<String, String> z = null;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        CrashDetailBean crashDetailBean = (CrashDetailBean) obj;
        if (crashDetailBean != null) {
            long j2 = this.r - crashDetailBean.r;
            if (j2 <= 0) {
                return j2 < 0 ? -1 : 0;
            }
        }
        return 1;
    }

    public CrashDetailBean() {
    }

    public CrashDetailBean(Parcel parcel) {
        boolean z2;
        boolean z3;
        boolean z4 = true;
        this.b = parcel.readInt();
        this.c = parcel.readString();
        this.d = parcel.readByte() == 1;
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        if (parcel.readByte() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.j = z2;
        if (parcel.readByte() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.k = z3;
        this.l = parcel.readInt();
        this.m = parcel.readString();
        this.n = parcel.readString();
        this.o = parcel.readString();
        this.p = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readLong();
        this.s = parcel.readString();
        this.t = parcel.readInt();
        this.u = parcel.readString();
        this.v = parcel.readString();
        this.w = parcel.readString();
        this.z = z.b(parcel);
        this.A = parcel.readString();
        this.B = parcel.readString();
        this.C = parcel.readLong();
        this.D = parcel.readLong();
        this.E = parcel.readLong();
        this.F = parcel.readLong();
        this.G = parcel.readLong();
        this.H = parcel.readLong();
        this.I = parcel.readString();
        this.W = parcel.readString();
        this.J = parcel.readString();
        this.K = parcel.readString();
        this.L = parcel.readString();
        this.M = parcel.readLong();
        this.N = parcel.readByte() != 1 ? false : z4;
        this.O = z.b(parcel);
        this.h = z.a(parcel);
        this.i = z.a(parcel);
        this.P = parcel.readInt();
        this.Q = parcel.readInt();
        this.R = z.b(parcel);
        this.S = z.b(parcel);
        this.T = parcel.createByteArray();
        this.y = parcel.createByteArray();
        this.U = parcel.readString();
        this.V = parcel.readString();
        this.x = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        int i3;
        int i4;
        int i5 = 1;
        parcel.writeInt(this.b);
        parcel.writeString(this.c);
        parcel.writeByte((byte) (this.d ? 1 : 0));
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        if (this.j) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        parcel.writeByte((byte) i3);
        if (this.k) {
            i4 = 1;
        } else {
            i4 = 0;
        }
        parcel.writeByte((byte) i4);
        parcel.writeInt(this.l);
        parcel.writeString(this.m);
        parcel.writeString(this.n);
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeString(this.q);
        parcel.writeLong(this.r);
        parcel.writeString(this.s);
        parcel.writeInt(this.t);
        parcel.writeString(this.u);
        parcel.writeString(this.v);
        parcel.writeString(this.w);
        z.b(parcel, this.z);
        parcel.writeString(this.A);
        parcel.writeString(this.B);
        parcel.writeLong(this.C);
        parcel.writeLong(this.D);
        parcel.writeLong(this.E);
        parcel.writeLong(this.F);
        parcel.writeLong(this.G);
        parcel.writeLong(this.H);
        parcel.writeString(this.I);
        parcel.writeString(this.W);
        parcel.writeString(this.J);
        parcel.writeString(this.K);
        parcel.writeString(this.L);
        parcel.writeLong(this.M);
        if (!this.N) {
            i5 = 0;
        }
        parcel.writeByte((byte) i5);
        z.b(parcel, this.O);
        z.a(parcel, this.h);
        z.a(parcel, this.i);
        parcel.writeInt(this.P);
        parcel.writeInt(this.Q);
        z.b(parcel, this.R);
        z.b(parcel, this.S);
        parcel.writeByteArray(this.T);
        parcel.writeByteArray(this.y);
        parcel.writeString(this.U);
        parcel.writeString(this.V);
        parcel.writeString(this.x);
    }
}
