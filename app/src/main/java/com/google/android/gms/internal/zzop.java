package com.google.android.gms.internal;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

@zzzn
public final class zzop extends zzox implements View.OnClickListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private static String[] zzIs = {"2011", "1009"};
    private final Object mLock = new Object();
    @Nullable
    private zzny zzHM;
    private final FrameLayout zzIt;
    private Map<String, WeakReference<View>> zzIu = new HashMap();
    @Nullable
    private View zzIv;
    private boolean zzIw = false;
    private Point zzIx = new Point();
    private Point zzIy = new Point();
    private WeakReference<zzge> zzIz = new WeakReference<>((Object) null);
    @Nullable
    FrameLayout zzss;

    public zzop(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzIt = frameLayout;
        this.zzss = frameLayout2;
        zzbs.zzbX();
        zzajv.zza((View) this.zzIt, (ViewTreeObserver.OnGlobalLayoutListener) this);
        zzbs.zzbX();
        zzajv.zza((View) this.zzIt, (ViewTreeObserver.OnScrollChangedListener) this);
        this.zzIt.setOnTouchListener(this);
        this.zzIt.setOnClickListener(this);
        zzmo.initialize(this.zzIt.getContext());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzoc r7) {
        /*
            r6 = this;
            java.lang.Object r2 = r6.mLock
            monitor-enter(r2)
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r0 = r6.zzIu     // Catch:{ all -> 0x003e }
            r7.zzd((java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>>) r0)     // Catch:{ all -> 0x003e }
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r0 = r6.zzIu     // Catch:{ all -> 0x003e }
            if (r0 == 0) goto L_0x0032
            java.lang.String[] r3 = zzIs     // Catch:{ all -> 0x003e }
            int r4 = r3.length     // Catch:{ all -> 0x003e }
            r0 = 0
            r1 = r0
        L_0x0011:
            if (r1 >= r4) goto L_0x0032
            r0 = r3[r1]     // Catch:{ all -> 0x003e }
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r5 = r6.zzIu     // Catch:{ all -> 0x003e }
            java.lang.Object r0 = r5.get(r0)     // Catch:{ all -> 0x003e }
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0     // Catch:{ all -> 0x003e }
            if (r0 == 0) goto L_0x002e
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x003e }
            android.view.View r0 = (android.view.View) r0     // Catch:{ all -> 0x003e }
        L_0x0025:
            boolean r1 = r0 instanceof android.widget.FrameLayout     // Catch:{ all -> 0x003e }
            if (r1 != 0) goto L_0x0034
            r7.zzev()     // Catch:{ all -> 0x003e }
            monitor-exit(r2)     // Catch:{ all -> 0x003e }
        L_0x002d:
            return
        L_0x002e:
            int r0 = r1 + 1
            r1 = r0
            goto L_0x0011
        L_0x0032:
            r0 = 0
            goto L_0x0025
        L_0x0034:
            com.google.android.gms.internal.zzor r1 = new com.google.android.gms.internal.zzor     // Catch:{ all -> 0x003e }
            r1.<init>(r6, r0)     // Catch:{ all -> 0x003e }
            r7.zza((android.view.View) r0, (com.google.android.gms.internal.zznw) r1)     // Catch:{ all -> 0x003e }
            monitor-exit(r2)     // Catch:{ all -> 0x003e }
            goto L_0x002d
        L_0x003e:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x003e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzop.zza(com.google.android.gms.internal.zzoc):void");
    }

    private final void zzg(@Nullable View view) {
        if (this.zzHM != null) {
            zzny zzer = this.zzHM instanceof zznx ? ((zznx) this.zzHM).zzer() : this.zzHM;
            if (zzer != null) {
                zzer.zzg(view);
            }
        }
    }

    private final int zzm(int i) {
        zzji.zzds();
        return zzaiy.zzd(this.zzHM.getContext(), i);
    }

    public final void destroy() {
        synchronized (this.mLock) {
            if (this.zzss != null) {
                this.zzss.removeAllViews();
            }
            this.zzss = null;
            this.zzIu = null;
            this.zzIv = null;
            this.zzHM = null;
            this.zzIx = null;
            this.zzIy = null;
            this.zzIz = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r8) {
        /*
            r7 = this;
            java.lang.Object r6 = r7.mLock
            monitor-enter(r6)
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r6)     // Catch:{ all -> 0x0076 }
        L_0x0008:
            return
        L_0x0009:
            android.os.Bundle r3 = new android.os.Bundle     // Catch:{ all -> 0x0076 }
            r3.<init>()     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = "x"
            android.graphics.Point r1 = r7.zzIx     // Catch:{ all -> 0x0076 }
            int r1 = r1.x     // Catch:{ all -> 0x0076 }
            int r1 = r7.zzm(r1)     // Catch:{ all -> 0x0076 }
            float r1 = (float) r1     // Catch:{ all -> 0x0076 }
            r3.putFloat(r0, r1)     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = "y"
            android.graphics.Point r1 = r7.zzIx     // Catch:{ all -> 0x0076 }
            int r1 = r1.y     // Catch:{ all -> 0x0076 }
            int r1 = r7.zzm(r1)     // Catch:{ all -> 0x0076 }
            float r1 = (float) r1     // Catch:{ all -> 0x0076 }
            r3.putFloat(r0, r1)     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = "start_x"
            android.graphics.Point r1 = r7.zzIy     // Catch:{ all -> 0x0076 }
            int r1 = r1.x     // Catch:{ all -> 0x0076 }
            int r1 = r7.zzm(r1)     // Catch:{ all -> 0x0076 }
            float r1 = (float) r1     // Catch:{ all -> 0x0076 }
            r3.putFloat(r0, r1)     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = "start_y"
            android.graphics.Point r1 = r7.zzIy     // Catch:{ all -> 0x0076 }
            int r1 = r1.y     // Catch:{ all -> 0x0076 }
            int r1 = r7.zzm(r1)     // Catch:{ all -> 0x0076 }
            float r1 = (float) r1     // Catch:{ all -> 0x0076 }
            r3.putFloat(r0, r1)     // Catch:{ all -> 0x0076 }
            android.view.View r0 = r7.zzIv     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x0086
            android.view.View r0 = r7.zzIv     // Catch:{ all -> 0x0076 }
            boolean r0 = r0.equals(r8)     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x0086
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            boolean r0 = r0 instanceof com.google.android.gms.internal.zznx     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x0079
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            com.google.android.gms.internal.zznx r0 = (com.google.android.gms.internal.zznx) r0     // Catch:{ all -> 0x0076 }
            com.google.android.gms.internal.zzny r0 = r0.zzer()     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x0074
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            com.google.android.gms.internal.zznx r0 = (com.google.android.gms.internal.zznx) r0     // Catch:{ all -> 0x0076 }
            com.google.android.gms.internal.zzny r0 = r0.zzer()     // Catch:{ all -> 0x0076 }
            java.lang.String r2 = "1007"
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r4 = r7.zzIu     // Catch:{ all -> 0x0076 }
            android.widget.FrameLayout r5 = r7.zzIt     // Catch:{ all -> 0x0076 }
            r1 = r8
            r0.zza(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0076 }
        L_0x0074:
            monitor-exit(r6)     // Catch:{ all -> 0x0076 }
            goto L_0x0008
        L_0x0076:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0076 }
            throw r0
        L_0x0079:
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            java.lang.String r2 = "1007"
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r4 = r7.zzIu     // Catch:{ all -> 0x0076 }
            android.widget.FrameLayout r5 = r7.zzIt     // Catch:{ all -> 0x0076 }
            r1 = r8
            r0.zza(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0076 }
            goto L_0x0074
        L_0x0086:
            com.google.android.gms.internal.zzny r0 = r7.zzHM     // Catch:{ all -> 0x0076 }
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r1 = r7.zzIu     // Catch:{ all -> 0x0076 }
            android.widget.FrameLayout r2 = r7.zzIt     // Catch:{ all -> 0x0076 }
            r0.zza(r8, r1, r3, r2)     // Catch:{ all -> 0x0076 }
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzop.onClick(android.view.View):void");
    }

    public final void onGlobalLayout() {
        synchronized (this.mLock) {
            if (this.zzIw) {
                int measuredWidth = this.zzIt.getMeasuredWidth();
                int measuredHeight = this.zzIt.getMeasuredHeight();
                if (!(measuredWidth == 0 || measuredHeight == 0 || this.zzss == null)) {
                    this.zzss.setLayoutParams(new FrameLayout.LayoutParams(measuredWidth, measuredHeight));
                    this.zzIw = false;
                }
            }
            if (this.zzHM != null) {
                this.zzHM.zzc(this.zzIt, this.zzIu);
            }
        }
    }

    public final void onScrollChanged() {
        synchronized (this.mLock) {
            if (this.zzHM != null) {
                this.zzHM.zzc(this.zzIt, this.zzIu);
            }
        }
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this.mLock) {
            if (this.zzHM != null) {
                int[] iArr = new int[2];
                this.zzIt.getLocationOnScreen(iArr);
                Point point = new Point((int) (motionEvent.getRawX() - ((float) iArr[0])), (int) (motionEvent.getRawY() - ((float) iArr[1])));
                this.zzIx = point;
                if (motionEvent.getAction() == 0) {
                    this.zzIy = point;
                }
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setLocation((float) point.x, (float) point.y);
                this.zzHM.zzd(obtain);
                obtain.recycle();
            }
        }
        return false;
    }

    public final IObjectWrapper zzL(String str) {
        View view = null;
        synchronized (this.mLock) {
            if (this.zzIu == null) {
                return null;
            }
            WeakReference weakReference = this.zzIu.get(str);
            if (weakReference != null) {
                view = (View) weakReference.get();
            }
            IObjectWrapper zzw = zzn.zzw(view);
            return zzw;
        }
    }

    public final void zzb(IObjectWrapper iObjectWrapper, int i) {
        zzge zzge;
        if (zzbs.zzbY().zzr(this.zzIt.getContext()) && this.zzIz != null && (zzge = (zzge) this.zzIz.get()) != null) {
            zzge.zzcB();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzd(java.lang.String r5, com.google.android.gms.dynamic.IObjectWrapper r6) {
        /*
            r4 = this;
            java.lang.Object r0 = com.google.android.gms.dynamic.zzn.zzE(r6)
            android.view.View r0 = (android.view.View) r0
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r2 = r4.zzIu     // Catch:{ all -> 0x0018 }
            if (r2 != 0) goto L_0x000f
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
        L_0x000e:
            return
        L_0x000f:
            if (r0 != 0) goto L_0x001b
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r0 = r4.zzIu     // Catch:{ all -> 0x0018 }
            r0.remove(r5)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
            goto L_0x000e
        L_0x0018:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
            throw r0
        L_0x001b:
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r2 = r4.zzIu     // Catch:{ all -> 0x0018 }
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0018 }
            r3.<init>(r0)     // Catch:{ all -> 0x0018 }
            r2.put(r5, r3)     // Catch:{ all -> 0x0018 }
            java.lang.String r2 = "1098"
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x0018 }
            if (r2 == 0) goto L_0x002f
            monitor-exit(r1)     // Catch:{ all -> 0x0018 }
            goto L_0x000e
        L_0x002f:
            r0.setOnTouchListener(r4)     // Catch:{ all -> 0x0018 }
            r2 = 1
            r0.setClickable(r2)     // Catch:{ all -> 0x0018 }
            r0.setOnClickListener(r4)     // Catch:{ all -> 0x0018 }
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzop.zzd(java.lang.String, com.google.android.gms.dynamic.IObjectWrapper):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zze(com.google.android.gms.dynamic.IObjectWrapper r11) {
        /*
            r10 = this;
            r3 = 1
            r5 = 0
            r4 = 0
            java.lang.Object r6 = r10.mLock
            monitor-enter(r6)
            r1 = 0
            r10.zzg(r1)     // Catch:{ all -> 0x016f }
            java.lang.Object r1 = com.google.android.gms.dynamic.zzn.zzE(r11)     // Catch:{ all -> 0x016f }
            boolean r2 = r1 instanceof com.google.android.gms.internal.zzoc     // Catch:{ all -> 0x016f }
            if (r2 != 0) goto L_0x0019
            java.lang.String r1 = "Not an instance of native engine. This is most likely a transient error"
            com.google.android.gms.internal.zzafr.zzaT(r1)     // Catch:{ all -> 0x016f }
            monitor-exit(r6)     // Catch:{ all -> 0x016f }
        L_0x0018:
            return
        L_0x0019:
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x002e
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout$LayoutParams r7 = new android.widget.FrameLayout$LayoutParams     // Catch:{ all -> 0x016f }
            r8 = 0
            r9 = 0
            r7.<init>(r8, r9)     // Catch:{ all -> 0x016f }
            r2.setLayoutParams(r7)     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r2 = r10.zzIt     // Catch:{ all -> 0x016f }
            r2.requestLayout()     // Catch:{ all -> 0x016f }
        L_0x002e:
            r2 = 1
            r10.zzIw = r2     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzoc r1 = (com.google.android.gms.internal.zzoc) r1     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x0052
            com.google.android.gms.internal.zzme<java.lang.Boolean> r2 = com.google.android.gms.internal.zzmo.zzFw     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzmm r7 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x016f }
            java.lang.Object r2 = r7.zzd(r2)     // Catch:{ all -> 0x016f }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x016f }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x0052
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r7 = r10.zzIt     // Catch:{ all -> 0x016f }
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r8 = r10.zzIu     // Catch:{ all -> 0x016f }
            r2.zzb(r7, r8)     // Catch:{ all -> 0x016f }
        L_0x0052:
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            boolean r2 = r2 instanceof com.google.android.gms.internal.zzoc     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x008d
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzoc r2 = (com.google.android.gms.internal.zzoc) r2     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x008d
            android.content.Context r7 = r2.getContext()     // Catch:{ all -> 0x016f }
            if (r7 == 0) goto L_0x008d
            com.google.android.gms.internal.zzaew r7 = com.google.android.gms.ads.internal.zzbs.zzbY()     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r8 = r10.zzIt     // Catch:{ all -> 0x016f }
            android.content.Context r8 = r8.getContext()     // Catch:{ all -> 0x016f }
            boolean r7 = r7.zzr(r8)     // Catch:{ all -> 0x016f }
            if (r7 == 0) goto L_0x008d
            com.google.android.gms.internal.zzaev r7 = r2.zzew()     // Catch:{ all -> 0x016f }
            if (r7 == 0) goto L_0x007e
            r2 = 0
            r7.zzu(r2)     // Catch:{ all -> 0x016f }
        L_0x007e:
            java.lang.ref.WeakReference<com.google.android.gms.internal.zzge> r2 = r10.zzIz     // Catch:{ all -> 0x016f }
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzge r2 = (com.google.android.gms.internal.zzge) r2     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x008d
            if (r7 == 0) goto L_0x008d
            r2.zzb(r7)     // Catch:{ all -> 0x016f }
        L_0x008d:
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            boolean r2 = r2 instanceof com.google.android.gms.internal.zznx     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x0172
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zznx r2 = (com.google.android.gms.internal.zznx) r2     // Catch:{ all -> 0x016f }
            boolean r2 = r2.zzeq()     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x0172
            com.google.android.gms.internal.zzny r2 = r10.zzHM     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zznx r2 = (com.google.android.gms.internal.zznx) r2     // Catch:{ all -> 0x016f }
            r2.zzc(r1)     // Catch:{ all -> 0x016f }
        L_0x00a4:
            com.google.android.gms.internal.zzme<java.lang.Boolean> r2 = com.google.android.gms.internal.zzmo.zzFw     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzmm r7 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x016f }
            java.lang.Object r2 = r7.zzd(r2)     // Catch:{ all -> 0x016f }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x016f }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x00bc
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            r7 = 0
            r2.setClickable(r7)     // Catch:{ all -> 0x016f }
        L_0x00bc:
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            r2.removeAllViews()     // Catch:{ all -> 0x016f }
            boolean r7 = r1.zzep()     // Catch:{ all -> 0x016f }
            if (r7 == 0) goto L_0x00e4
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r2 = r10.zzIu     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x00e4
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r2 = r10.zzIu     // Catch:{ all -> 0x016f }
            java.lang.String r8 = "1098"
            java.lang.Object r2 = r2.get(r8)     // Catch:{ all -> 0x016f }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x0182
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x016f }
            android.view.View r2 = (android.view.View) r2     // Catch:{ all -> 0x016f }
        L_0x00dd:
            boolean r8 = r2 instanceof android.view.ViewGroup     // Catch:{ all -> 0x016f }
            if (r8 == 0) goto L_0x00e4
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2     // Catch:{ all -> 0x016f }
            r5 = r2
        L_0x00e4:
            if (r7 == 0) goto L_0x0185
            if (r5 == 0) goto L_0x0185
            r2 = r3
        L_0x00e9:
            android.view.View r3 = r1.zza((android.view.View.OnClickListener) r10, (boolean) r2)     // Catch:{ all -> 0x016f }
            r10.zzIv = r3     // Catch:{ all -> 0x016f }
            android.view.View r3 = r10.zzIv     // Catch:{ all -> 0x016f }
            if (r3 == 0) goto L_0x010f
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r3 = r10.zzIu     // Catch:{ all -> 0x016f }
            if (r3 == 0) goto L_0x0105
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r3 = r10.zzIu     // Catch:{ all -> 0x016f }
            java.lang.String r4 = "1007"
            java.lang.ref.WeakReference r7 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x016f }
            android.view.View r8 = r10.zzIv     // Catch:{ all -> 0x016f }
            r7.<init>(r8)     // Catch:{ all -> 0x016f }
            r3.put(r4, r7)     // Catch:{ all -> 0x016f }
        L_0x0105:
            if (r2 == 0) goto L_0x0188
            r5.removeAllViews()     // Catch:{ all -> 0x016f }
            android.view.View r2 = r10.zzIv     // Catch:{ all -> 0x016f }
            r5.addView(r2)     // Catch:{ all -> 0x016f }
        L_0x010f:
            android.widget.FrameLayout r2 = r10.zzIt     // Catch:{ all -> 0x016f }
            java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>> r3 = r10.zzIu     // Catch:{ all -> 0x016f }
            r1.zza((android.view.View) r2, (java.util.Map<java.lang.String, java.lang.ref.WeakReference<android.view.View>>) r3, (android.view.View.OnTouchListener) r10, (android.view.View.OnClickListener) r10)     // Catch:{ all -> 0x016f }
            android.os.Handler r2 = com.google.android.gms.internal.zzagz.zzZr     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzoq r3 = new com.google.android.gms.internal.zzoq     // Catch:{ all -> 0x016f }
            r3.<init>(r10, r1)     // Catch:{ all -> 0x016f }
            r2.post(r3)     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r1 = r10.zzIt     // Catch:{ all -> 0x016f }
            r10.zzg(r1)     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzny r1 = r10.zzHM     // Catch:{ all -> 0x016f }
            boolean r1 = r1 instanceof com.google.android.gms.internal.zzoc     // Catch:{ all -> 0x016f }
            if (r1 == 0) goto L_0x016c
            com.google.android.gms.internal.zzny r1 = r10.zzHM     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzoc r1 = (com.google.android.gms.internal.zzoc) r1     // Catch:{ all -> 0x016f }
            if (r1 == 0) goto L_0x016c
            android.content.Context r2 = r1.getContext()     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x016c
            com.google.android.gms.internal.zzaew r2 = com.google.android.gms.ads.internal.zzbs.zzbY()     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r3 = r10.zzIt     // Catch:{ all -> 0x016f }
            android.content.Context r3 = r3.getContext()     // Catch:{ all -> 0x016f }
            boolean r2 = r2.zzr(r3)     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x016c
            java.lang.ref.WeakReference<com.google.android.gms.internal.zzge> r2 = r10.zzIz     // Catch:{ all -> 0x016f }
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x016f }
            com.google.android.gms.internal.zzge r2 = (com.google.android.gms.internal.zzge) r2     // Catch:{ all -> 0x016f }
            if (r2 != 0) goto L_0x0165
            com.google.android.gms.internal.zzge r2 = new com.google.android.gms.internal.zzge     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r3 = r10.zzIt     // Catch:{ all -> 0x016f }
            android.content.Context r3 = r3.getContext()     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r4 = r10.zzIt     // Catch:{ all -> 0x016f }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x016f }
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x016f }
            r3.<init>(r2)     // Catch:{ all -> 0x016f }
            r10.zzIz = r3     // Catch:{ all -> 0x016f }
        L_0x0165:
            com.google.android.gms.internal.zzaev r1 = r1.zzew()     // Catch:{ all -> 0x016f }
            r2.zza((com.google.android.gms.internal.zzgi) r1)     // Catch:{ all -> 0x016f }
        L_0x016c:
            monitor-exit(r6)     // Catch:{ all -> 0x016f }
            goto L_0x0018
        L_0x016f:
            r1 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x016f }
            throw r1
        L_0x0172:
            r10.zzHM = r1     // Catch:{ all -> 0x016f }
            boolean r2 = r1 instanceof com.google.android.gms.internal.zznx     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x00a4
            r0 = r1
            com.google.android.gms.internal.zznx r0 = (com.google.android.gms.internal.zznx) r0     // Catch:{ all -> 0x016f }
            r2 = r0
            r7 = 0
            r2.zzc(r7)     // Catch:{ all -> 0x016f }
            goto L_0x00a4
        L_0x0182:
            r2 = r5
            goto L_0x00dd
        L_0x0185:
            r2 = r4
            goto L_0x00e9
        L_0x0188:
            android.content.Context r2 = r1.getContext()     // Catch:{ all -> 0x016f }
            com.google.android.gms.ads.formats.AdChoicesView r3 = new com.google.android.gms.ads.formats.AdChoicesView     // Catch:{ all -> 0x016f }
            r3.<init>(r2)     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams     // Catch:{ all -> 0x016f }
            r4 = -1
            r5 = -1
            r2.<init>(r4, r5)     // Catch:{ all -> 0x016f }
            r3.setLayoutParams(r2)     // Catch:{ all -> 0x016f }
            android.view.View r2 = r10.zzIv     // Catch:{ all -> 0x016f }
            r3.addView(r2)     // Catch:{ all -> 0x016f }
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x010f
            android.widget.FrameLayout r2 = r10.zzss     // Catch:{ all -> 0x016f }
            r2.addView(r3)     // Catch:{ all -> 0x016f }
            goto L_0x010f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzop.zze(com.google.android.gms.dynamic.IObjectWrapper):void");
    }
}
