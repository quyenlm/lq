package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.fe;

final class zzc {
    static Rect zza(Text text) {
        Point[] cornerPoints = text.getCornerPoints();
        int length = cornerPoints.length;
        int i = 0;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MAX_VALUE;
        while (i < length) {
            Point point = cornerPoints[i];
            i5 = Math.min(i5, point.x);
            int max = Math.max(i4, point.x);
            int min = Math.min(i3, point.y);
            i2 = Math.max(i2, point.y);
            i++;
            i3 = min;
            i4 = max;
        }
        return new Rect(i5, i3, i4, i2);
    }

    static Point[] zza(fe feVar) {
        Point[] pointArr = new Point[4];
        double sin = Math.sin(Math.toRadians((double) feVar.zzbNW));
        double cos = Math.cos(Math.toRadians((double) feVar.zzbNW));
        pointArr[0] = new Point(feVar.left, feVar.top);
        pointArr[1] = new Point((int) (((double) feVar.left) + (((double) feVar.width) * cos)), (int) (((double) feVar.top) + (((double) feVar.width) * sin)));
        pointArr[2] = new Point((int) (((double) pointArr[1].x) - (sin * ((double) feVar.height))), (int) ((cos * ((double) feVar.height)) + ((double) pointArr[1].y)));
        pointArr[3] = new Point(pointArr[0].x + (pointArr[2].x - pointArr[1].x), pointArr[0].y + (pointArr[2].y - pointArr[1].y));
        return pointArr;
    }
}
