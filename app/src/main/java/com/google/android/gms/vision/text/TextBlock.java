package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.fk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextBlock implements Text {
    private Point[] cornerPoints;
    private fk[] zzbNQ;
    private List<Line> zzbNR;
    private String zzbNS;
    private Rect zzbNT;

    TextBlock(SparseArray<fk> sparseArray) {
        this.zzbNQ = new fk[sparseArray.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.zzbNQ.length) {
                this.zzbNQ[i2] = sparseArray.valueAt(i2);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static Point[] zza(int i, int i2, int i3, int i4, fe feVar) {
        int i5 = feVar.left;
        int i6 = feVar.top;
        double sin = Math.sin(Math.toRadians((double) feVar.zzbNW));
        double cos = Math.cos(Math.toRadians((double) feVar.zzbNW));
        Point[] pointArr = {new Point(i, i2), new Point(i3, i2), new Point(i3, i4), new Point(i, i4)};
        for (int i7 = 0; i7 < 4; i7++) {
            int i8 = (int) ((((double) pointArr[i7].x) * sin) + (((double) pointArr[i7].y) * cos));
            pointArr[i7].x = (int) ((((double) pointArr[i7].x) * cos) - (((double) pointArr[i7].y) * sin));
            pointArr[i7].y = i8;
            pointArr[i7].offset(i5, i6);
        }
        return pointArr;
    }

    public Rect getBoundingBox() {
        if (this.zzbNT == null) {
            this.zzbNT = zzc.zza((Text) this);
        }
        return this.zzbNT;
    }

    public List<? extends Text> getComponents() {
        if (this.zzbNQ.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzbNR == null) {
            this.zzbNR = new ArrayList(this.zzbNQ.length);
            for (fk line : this.zzbNQ) {
                this.zzbNR.add(new Line(line));
            }
        }
        return this.zzbNR;
    }

    public Point[] getCornerPoints() {
        if (this.cornerPoints == null) {
            if (this.zzbNQ.length == 0) {
                this.cornerPoints = new Point[0];
            } else {
                int i = Integer.MAX_VALUE;
                int i2 = Integer.MIN_VALUE;
                int i3 = Integer.MAX_VALUE;
                int i4 = Integer.MIN_VALUE;
                for (fk fkVar : this.zzbNQ) {
                    fe feVar = fkVar.zzbNY;
                    fe feVar2 = this.zzbNQ[0].zzbNY;
                    double sin = Math.sin(Math.toRadians((double) feVar2.zzbNW));
                    double cos = Math.cos(Math.toRadians((double) feVar2.zzbNW));
                    Point[] pointArr = new Point[4];
                    pointArr[0] = new Point(feVar.left, feVar.top);
                    pointArr[0].offset(-feVar2.left, -feVar2.top);
                    int i5 = (int) ((((double) pointArr[0].x) * cos) + (((double) pointArr[0].y) * sin));
                    int i6 = (int) ((((double) (-pointArr[0].x)) * sin) + (((double) pointArr[0].y) * cos));
                    pointArr[0].x = i5;
                    pointArr[0].y = i6;
                    pointArr[1] = new Point(feVar.width + i5, i6);
                    pointArr[2] = new Point(feVar.width + i5, feVar.height + i6);
                    pointArr[3] = new Point(i5, feVar.height + i6);
                    for (int i7 = 0; i7 < 4; i7++) {
                        Point point = pointArr[i7];
                        i = Math.min(i, point.x);
                        i2 = Math.max(i2, point.x);
                        i3 = Math.min(i3, point.y);
                        i4 = Math.max(i4, point.y);
                    }
                }
                this.cornerPoints = zza(i, i3, i2, i4, this.zzbNQ[0].zzbNY);
            }
        }
        return this.cornerPoints;
    }

    public String getLanguage() {
        if (this.zzbNS != null) {
            return this.zzbNS;
        }
        HashMap hashMap = new HashMap();
        for (fk fkVar : this.zzbNQ) {
            hashMap.put(fkVar.zzbNS, Integer.valueOf((hashMap.containsKey(fkVar.zzbNS) ? ((Integer) hashMap.get(fkVar.zzbNS)).intValue() : 0) + 1));
        }
        this.zzbNS = (String) ((Map.Entry) Collections.max(hashMap.entrySet(), new zza(this))).getKey();
        if (this.zzbNS == null || this.zzbNS.isEmpty()) {
            this.zzbNS = "und";
        }
        return this.zzbNS;
    }

    public String getValue() {
        if (this.zzbNQ.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(this.zzbNQ[0].zzbOb);
        for (int i = 1; i < this.zzbNQ.length; i++) {
            sb.append("\n");
            sb.append(this.zzbNQ[i].zzbOb);
        }
        return sb.toString();
    }
}
