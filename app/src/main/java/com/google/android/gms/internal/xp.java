package com.google.android.gms.internal;

public final class xp {
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c A[Catch:{ ClassCastException -> 0x00d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031 A[Catch:{ ClassCastException -> 0x00d7 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.xm zza(java.lang.Object r8, com.google.android.gms.internal.xm r9) throws com.google.firebase.database.DatabaseException {
        /*
            boolean r2 = r8 instanceof java.util.Map     // Catch:{ ClassCastException -> 0x00d7 }
            if (r2 == 0) goto L_0x015d
            r0 = r8
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r0
            java.lang.String r3 = ".priority"
            boolean r3 = r2.containsKey(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x001b
            java.lang.String r3 = ".priority"
            java.lang.Object r3 = r2.get(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            r4 = 0
            com.google.android.gms.internal.xm r9 = com.google.android.gms.internal.xs.zzc(r4, r3)     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x001b:
            java.lang.String r3 = ".value"
            boolean r3 = r2.containsKey(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x015d
            java.lang.String r3 = ".value"
            java.lang.Object r8 = r2.get(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r8
        L_0x002a:
            if (r2 != 0) goto L_0x0031
            com.google.android.gms.internal.xd r2 = com.google.android.gms.internal.xd.zzJb()     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x0030:
            return r2
        L_0x0031:
            boolean r3 = r2 instanceof java.lang.String     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x003e
            com.google.android.gms.internal.xu r3 = new com.google.android.gms.internal.xu     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x003e:
            boolean r3 = r2 instanceof java.lang.Long     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x004b
            com.google.android.gms.internal.xk r3 = new com.google.android.gms.internal.xk     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x004b:
            boolean r3 = r2 instanceof java.lang.Integer     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x0061
            com.google.android.gms.internal.xk r3 = new com.google.android.gms.internal.xk     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ ClassCastException -> 0x00d7 }
            int r2 = r2.intValue()     // Catch:{ ClassCastException -> 0x00d7 }
            long r4 = (long) r2     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x0061:
            boolean r3 = r2 instanceof java.lang.Double     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x006e
            com.google.android.gms.internal.xc r3 = new com.google.android.gms.internal.xc     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Double r2 = (java.lang.Double) r2     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x006e:
            boolean r3 = r2 instanceof java.lang.Boolean     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x007b
            com.google.android.gms.internal.wo r3 = new com.google.android.gms.internal.wo     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x007b:
            boolean r3 = r2 instanceof java.util.Map     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 != 0) goto L_0x0083
            boolean r3 = r2 instanceof java.util.List     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x0137
        L_0x0083:
            boolean r3 = r2 instanceof java.util.Map     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x00ed
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r3 = ".sv"
            boolean r3 = r2.containsKey(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x0098
            com.google.android.gms.internal.xb r3 = new com.google.android.gms.internal.xb     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r2, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            r2 = r3
            goto L_0x0030
        L_0x0098:
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ ClassCastException -> 0x00d7 }
            int r3 = r2.size()     // Catch:{ ClassCastException -> 0x00d7 }
            r4.<init>(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            java.util.Set r3 = r2.keySet()     // Catch:{ ClassCastException -> 0x00d7 }
            java.util.Iterator r5 = r3.iterator()     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x00a9:
            boolean r3 = r5.hasNext()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x00e0
            java.lang.Object r3 = r5.next()     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r6 = "."
            boolean r6 = r3.startsWith(r6)     // Catch:{ ClassCastException -> 0x00d7 }
            if (r6 != 0) goto L_0x00a9
            java.lang.Object r6 = r2.get(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.xd r7 = com.google.android.gms.internal.xd.zzJb()     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.xm r6 = zza(r6, r7)     // Catch:{ ClassCastException -> 0x00d7 }
            boolean r7 = r6.isEmpty()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r7 != 0) goto L_0x00a9
            com.google.android.gms.internal.wp r3 = com.google.android.gms.internal.wp.zzgT(r3)     // Catch:{ ClassCastException -> 0x00d7 }
            r4.put(r3, r6)     // Catch:{ ClassCastException -> 0x00d7 }
            goto L_0x00a9
        L_0x00d7:
            r2 = move-exception
            com.google.firebase.database.DatabaseException r3 = new com.google.firebase.database.DatabaseException
            java.lang.String r4 = "Failed to parse node"
            r3.<init>(r4, r2)
            throw r3
        L_0x00e0:
            r2 = r4
        L_0x00e1:
            boolean r3 = r2.isEmpty()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r3 == 0) goto L_0x012a
            com.google.android.gms.internal.xd r2 = com.google.android.gms.internal.xd.zzJb()     // Catch:{ ClassCastException -> 0x00d7 }
            goto L_0x0030
        L_0x00ed:
            java.util.List r2 = (java.util.List) r2     // Catch:{ ClassCastException -> 0x00d7 }
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ ClassCastException -> 0x00d7 }
            int r4 = r2.size()     // Catch:{ ClassCastException -> 0x00d7 }
            r3.<init>(r4)     // Catch:{ ClassCastException -> 0x00d7 }
            r4 = 0
        L_0x00f9:
            int r5 = r2.size()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r4 >= r5) goto L_0x015b
            r5 = 11
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ClassCastException -> 0x00d7 }
            r6.<init>(r5)     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.StringBuilder r5 = r6.append(r4)     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r5 = r5.toString()     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.Object r6 = r2.get(r4)     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.xd r7 = com.google.android.gms.internal.xd.zzJb()     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.xm r6 = zza(r6, r7)     // Catch:{ ClassCastException -> 0x00d7 }
            boolean r7 = r6.isEmpty()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r7 != 0) goto L_0x0127
            com.google.android.gms.internal.wp r5 = com.google.android.gms.internal.wp.zzgT(r5)     // Catch:{ ClassCastException -> 0x00d7 }
            r3.put(r5, r6)     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x0127:
            int r4 = r4 + 1
            goto L_0x00f9
        L_0x012a:
            java.util.Comparator<com.google.android.gms.internal.wp> r3 = com.google.android.gms.internal.wr.zzchR     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.nh r3 = com.google.android.gms.internal.ni.zza(r2, r3)     // Catch:{ ClassCastException -> 0x00d7 }
            com.google.android.gms.internal.wr r2 = new com.google.android.gms.internal.wr     // Catch:{ ClassCastException -> 0x00d7 }
            r2.<init>(r3, r9)     // Catch:{ ClassCastException -> 0x00d7 }
            goto L_0x0030
        L_0x0137:
            com.google.firebase.database.DatabaseException r3 = new com.google.firebase.database.DatabaseException     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r4 = "Failed to parse node with class "
            java.lang.Class r2 = r2.getClass()     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r2 = r2.toString()     // Catch:{ ClassCastException -> 0x00d7 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ ClassCastException -> 0x00d7 }
            int r5 = r2.length()     // Catch:{ ClassCastException -> 0x00d7 }
            if (r5 == 0) goto L_0x0155
            java.lang.String r2 = r4.concat(r2)     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x0151:
            r3.<init>(r2)     // Catch:{ ClassCastException -> 0x00d7 }
            throw r3     // Catch:{ ClassCastException -> 0x00d7 }
        L_0x0155:
            java.lang.String r2 = new java.lang.String     // Catch:{ ClassCastException -> 0x00d7 }
            r2.<init>(r4)     // Catch:{ ClassCastException -> 0x00d7 }
            goto L_0x0151
        L_0x015b:
            r2 = r3
            goto L_0x00e1
        L_0x015d:
            r2 = r8
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.xp.zza(java.lang.Object, com.google.android.gms.internal.xm):com.google.android.gms.internal.xm");
    }
}
