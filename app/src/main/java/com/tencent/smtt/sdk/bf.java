package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.DexLoader;

class bf {
    private DexLoader a = null;

    public bf(DexLoader dexLoader) {
        this.a = dexLoader;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0014, code lost:
        r0 = r8.a.invokeMethod((r1 = r8.a.newInstance("com.tencent.tbs.utils.TbsVideoUtilsProxy", new java.lang.Class[0], new java.lang.Object[0])), "com.tencent.tbs.utils.TbsVideoUtilsProxy", "getCurWDPDecodeType", new java.lang.Class[]{android.content.Context.class}, r9);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(android.content.Context r9) {
        /*
            r8 = this;
            r7 = 1
            r6 = 0
            com.tencent.smtt.export.external.DexLoader r0 = r8.a
            if (r0 == 0) goto L_0x002f
            com.tencent.smtt.export.external.DexLoader r0 = r8.a
            java.lang.String r1 = "com.tencent.tbs.utils.TbsVideoUtilsProxy"
            java.lang.Class[] r2 = new java.lang.Class[r6]
            java.lang.Object[] r3 = new java.lang.Object[r6]
            java.lang.Object r1 = r0.newInstance(r1, r2, r3)
            if (r1 == 0) goto L_0x002f
            com.tencent.smtt.export.external.DexLoader r0 = r8.a
            java.lang.String r2 = "com.tencent.tbs.utils.TbsVideoUtilsProxy"
            java.lang.String r3 = "getCurWDPDecodeType"
            java.lang.Class[] r4 = new java.lang.Class[r7]
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r4[r6] = r5
            java.lang.Object[] r5 = new java.lang.Object[r7]
            r5[r6] = r9
            java.lang.Object r0 = r0.invokeMethod(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x002f
            java.lang.String r0 = java.lang.String.valueOf(r0)
        L_0x002e:
            return r0
        L_0x002f:
            java.lang.String r0 = ""
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.bf.a(android.content.Context):java.lang.String");
    }

    public void a(Context context, String str) {
        Object newInstance;
        if (this.a != null && (newInstance = this.a.newInstance("com.tencent.tbs.utils.TbsVideoUtilsProxy", new Class[0], new Object[0])) != null) {
            this.a.invokeMethod(newInstance, "com.tencent.tbs.utils.TbsVideoUtilsProxy", "deleteVideoCache", new Class[]{Context.class, String.class}, context, str);
        }
    }
}
