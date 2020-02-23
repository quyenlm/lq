package com.tencent.mna.b.a.b;

import com.tencent.mna.b.a.c.c;
import com.tencent.mna.base.utils.h;
import java.io.Serializable;

/* compiled from: CommonSpeedComparator */
public class a implements b, Serializable {
    private int mDiffThreshold;
    private int mForwardAvgMax;
    private int mForwardAvgMin;
    private int mForwardStdMax;
    private int mForwardTimeoutCountMax;

    public a(int i, int i2, int i3, int i4, int i5) {
        this.mDiffThreshold = i;
        this.mForwardAvgMax = i2;
        this.mForwardAvgMin = i3;
        this.mForwardStdMax = i4;
        this.mForwardTimeoutCountMax = i5;
        h.a("CommonSpeedComparator() called with: diffThreshold = [" + i + "], forwardAvgMax = [" + i2 + "], forwardAvgMin = [" + i3 + "], forwardStdMax = [" + i4 + "], forwardTimeoutNumMax = [" + i5 + "]");
    }

    public int compare(c cVar, c cVar2) {
        if (cVar == null || cVar2 == null) {
            h.d("CommonSpeedComparator compare input is null");
            return 0;
        }
        double b = cVar.b();
        double b2 = cVar2.b();
        double f = cVar.f();
        int e = cVar.e();
        h.b("[N]CommonSpeedComparator compare() forwardAvg = [" + b + "], directAvg = [" + b2 + "], mDiffThreshold = [" + this.mDiffThreshold + "]; mForwardAvgMin = [" + this.mForwardAvgMin + "], forwardAvg = [" + b + "], mForwardAvgMax = [" + this.mForwardAvgMax + "]; forwardStdDeviation = [" + f + "], mForwardStdDeviationMax = [" + this.mForwardStdMax + "]; forwardTimeoutCount = [" + e + "], mForwardTimeoutCountMax = [" + this.mForwardTimeoutCountMax + "]");
        if (b2 - b <= ((double) this.mDiffThreshold) || ((double) this.mForwardAvgMin) >= b || b >= ((double) this.mForwardAvgMax) || f >= ((double) this.mForwardStdMax) || e >= this.mForwardTimeoutCountMax) {
            h.b("CommonSpeedComparator return -1");
            return -1;
        }
        h.b("CommonSpeedComparator return 1");
        return 1;
    }
}
