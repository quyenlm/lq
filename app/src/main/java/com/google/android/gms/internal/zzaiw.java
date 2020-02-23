package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.util.Range;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@zzzn
public final class zzaiw {
    private static List<MediaCodecInfo> zzaaA;
    private static final Object zzaaB = new Object();
    private static Map<String, List<Map<String, Object>>> zzaaz = new HashMap();

    @TargetApi(21)
    private static Integer[] zza(Range<Integer> range) {
        return new Integer[]{range.getLower(), range.getUpper()};
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        	at java.util.ArrayList.get(ArrayList.java:429)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    @android.annotation.TargetApi(16)
    public static java.util.List<java.util.Map<java.lang.String, java.lang.Object>> zzaQ(java.lang.String r15) {
        /*
            r14 = 21
            r2 = 0
            java.lang.Object r3 = zzaaB
            monitor-enter(r3)
            java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, java.lang.Object>>> r0 = zzaaz     // Catch:{ all -> 0x00c1 }
            boolean r0 = r0.containsKey(r15)     // Catch:{ all -> 0x00c1 }
            if (r0 == 0) goto L_0x0018
            java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, java.lang.Object>>> r0 = zzaaz     // Catch:{ all -> 0x00c1 }
            java.lang.Object r0 = r0.get(r15)     // Catch:{ all -> 0x00c1 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x00c1 }
            monitor-exit(r3)     // Catch:{ all -> 0x00c1 }
        L_0x0017:
            return r0
        L_0x0018:
            java.lang.Object r1 = zzaaB     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            monitor-enter(r1)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.util.List<android.media.MediaCodecInfo> r0 = zzaaA     // Catch:{ all -> 0x009b }
            if (r0 == 0) goto L_0x0085
            monitor-exit(r1)     // Catch:{ all -> 0x009b }
        L_0x0020:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r1.<init>()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.util.List<android.media.MediaCodecInfo> r0 = zzaaA     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.util.Iterator r4 = r0.iterator()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
        L_0x002b:
            boolean r0 = r4.hasNext()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            if (r0 == 0) goto L_0x0161
            java.lang.Object r0 = r4.next()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            android.media.MediaCodecInfo r0 = (android.media.MediaCodecInfo) r0     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            boolean r5 = r0.isEncoder()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            if (r5 != 0) goto L_0x002b
            java.lang.String[] r5 = r0.getSupportedTypes()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.util.List r5 = java.util.Arrays.asList(r5)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            boolean r5 = r5.contains(r15)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            if (r5 == 0) goto L_0x002b
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.<init>()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r6 = "codecName"
            java.lang.String r7 = r0.getName()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r6, r7)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            android.media.MediaCodecInfo$CodecCapabilities r6 = r0.getCapabilitiesForType(r15)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r7.<init>()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            android.media.MediaCodecInfo$CodecProfileLevel[] r8 = r6.profileLevels     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            int r9 = r8.length     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r0 = r2
        L_0x0066:
            if (r0 >= r9) goto L_0x00eb
            r10 = r8[r0]     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r11 = 2
            java.lang.Integer[] r11 = new java.lang.Integer[r11]     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r12 = 0
            int r13 = r10.profile     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r11[r12] = r13     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r12 = 1
            int r10 = r10.level     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r11[r12] = r10     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r7.add(r11)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            int r0 = r0 + 1
            goto L_0x0066
        L_0x0085:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x009b }
            if (r0 < r14) goto L_0x00c4
            android.media.MediaCodecList r0 = new android.media.MediaCodecList     // Catch:{ all -> 0x009b }
            r4 = 0
            r0.<init>(r4)     // Catch:{ all -> 0x009b }
            android.media.MediaCodecInfo[] r0 = r0.getCodecInfos()     // Catch:{ all -> 0x009b }
            java.util.List r0 = java.util.Arrays.asList(r0)     // Catch:{ all -> 0x009b }
            zzaaA = r0     // Catch:{ all -> 0x009b }
        L_0x0099:
            monitor-exit(r1)     // Catch:{ all -> 0x009b }
            goto L_0x0020
        L_0x009b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x009b }
            throw r0     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
        L_0x009e:
            r0 = move-exception
        L_0x009f:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x00c1 }
            r1.<init>()     // Catch:{ all -> 0x00c1 }
            java.lang.String r2 = "error"
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x00c1 }
            java.lang.String r0 = r0.getSimpleName()     // Catch:{ all -> 0x00c1 }
            r1.put(r2, r0)     // Catch:{ all -> 0x00c1 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00c1 }
            r0.<init>()     // Catch:{ all -> 0x00c1 }
            r0.add(r1)     // Catch:{ all -> 0x00c1 }
            java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, java.lang.Object>>> r1 = zzaaz     // Catch:{ all -> 0x00c1 }
            r1.put(r15, r0)     // Catch:{ all -> 0x00c1 }
            monitor-exit(r3)     // Catch:{ all -> 0x00c1 }
            goto L_0x0017
        L_0x00c1:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00c1 }
            throw r0
        L_0x00c4:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x009b }
            r4 = 16
            if (r0 < r4) goto L_0x00e4
            int r4 = android.media.MediaCodecList.getCodecCount()     // Catch:{ all -> 0x009b }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x009b }
            r0.<init>(r4)     // Catch:{ all -> 0x009b }
            zzaaA = r0     // Catch:{ all -> 0x009b }
            r0 = r2
        L_0x00d6:
            if (r0 >= r4) goto L_0x0099
            android.media.MediaCodecInfo r5 = android.media.MediaCodecList.getCodecInfoAt(r0)     // Catch:{ all -> 0x009b }
            java.util.List<android.media.MediaCodecInfo> r6 = zzaaA     // Catch:{ all -> 0x009b }
            r6.add(r5)     // Catch:{ all -> 0x009b }
            int r0 = r0 + 1
            goto L_0x00d6
        L_0x00e4:
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x009b }
            zzaaA = r0     // Catch:{ all -> 0x009b }
            goto L_0x0099
        L_0x00eb:
            java.lang.String r0 = "profileLevels"
            r5.put(r0, r7)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            if (r0 < r14) goto L_0x0146
            android.media.MediaCodecInfo$VideoCapabilities r0 = r6.getVideoCapabilities()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "bitRatesBps"
            android.util.Range r8 = r0.getBitrateRange()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer[] r8 = zza(r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "widthAlignment"
            int r8 = r0.getWidthAlignment()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "heightAlignment"
            int r8 = r0.getHeightAlignment()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "frameRates"
            android.util.Range r8 = r0.getSupportedFrameRates()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer[] r8 = zza(r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "widths"
            android.util.Range r8 = r0.getSupportedWidths()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer[] r8 = zza(r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r8)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.String r7 = "heights"
            android.util.Range r0 = r0.getSupportedHeights()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer[] r0 = zza(r0)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r7, r0)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
        L_0x0146:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r7 = 23
            if (r0 < r7) goto L_0x0159
            java.lang.String r0 = "instancesLimit"
            int r6 = r6.getMaxSupportedInstances()     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r5.put(r0, r6)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
        L_0x0159:
            r1.add(r5)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            goto L_0x002b
        L_0x015e:
            r0 = move-exception
            goto L_0x009f
        L_0x0161:
            java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, java.lang.Object>>> r0 = zzaaz     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            r0.put(r15, r1)     // Catch:{ RuntimeException -> 0x009e, LinkageError -> 0x015e }
            monitor-exit(r3)     // Catch:{ all -> 0x00c1 }
            r0 = r1
            goto L_0x0017
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaiw.zzaQ(java.lang.String):java.util.List");
    }
}
