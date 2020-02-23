package com.tencent.mna;

public interface MNAObserver {
    void OnBatteryChangedNotify(int i, int i2);

    void OnQueryKartinNotify(String str, int i, String str2, int i2, int i3, int i4, int i5, String str3, int i6, int i7, String str4, int i8, int i9, String str5, int i10, int i11, String str6, int i12, int i13, int i14, String str7, int i15, String str8, int i16);

    void OnStartSpeedNotify(int i, int i2, String str);
}
