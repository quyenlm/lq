package com.tencent.mna.b.d;

/* compiled from: DiagnoseSwitch */
final class d {

    /* compiled from: DiagnoseSwitch */
    enum a {
        Ping(1),
        RouterMacs(2),
        Export(4),
        Direct(8),
        NIC(16);
        
        int mCode;

        private a(int i) {
            this.mCode = i;
        }
    }

    static boolean a(int i, a aVar) {
        return (aVar.mCode & i) != 0;
    }
}
