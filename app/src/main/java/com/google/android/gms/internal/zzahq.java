package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.zzbs;
import java.util.ArrayList;
import java.util.List;

@zzzn
public final class zzahq {
    /* access modifiers changed from: private */
    public final Context mContext;
    private int mState;
    private final float zzNX;
    private String zzZB;
    private float zzZC;
    private float zzZD;
    private float zzZE;
    /* access modifiers changed from: private */
    public String zztV;
    /* access modifiers changed from: private */
    public String zzwH;

    public zzahq(Context context) {
        this.mState = 0;
        this.mContext = context;
        this.zzNX = context.getResources().getDisplayMetrics().density;
    }

    public zzahq(Context context, String str) {
        this(context);
        this.zzZB = str;
    }

    private static int zza(List<String> list, String str, boolean z) {
        if (!z) {
            return -1;
        }
        list.add(str);
        return list.size() - 1;
    }

    private final void zza(int i, float f, float f2) {
        if (i == 0) {
            this.mState = 0;
            this.zzZC = f;
            this.zzZD = f2;
            this.zzZE = f2;
        } else if (this.mState == -1) {
        } else {
            if (i == 2) {
                if (f2 > this.zzZD) {
                    this.zzZD = f2;
                } else if (f2 < this.zzZE) {
                    this.zzZE = f2;
                }
                if (this.zzZD - this.zzZE > 30.0f * this.zzNX) {
                    this.mState = -1;
                    return;
                }
                if (this.mState == 0 || this.mState == 2) {
                    if (f - this.zzZC >= 50.0f * this.zzNX) {
                        this.zzZC = f;
                        this.mState++;
                    }
                } else if ((this.mState == 1 || this.mState == 3) && f - this.zzZC <= -50.0f * this.zzNX) {
                    this.zzZC = f;
                    this.mState++;
                }
                if (this.mState == 1 || this.mState == 3) {
                    if (f > this.zzZC) {
                        this.zzZC = f;
                    }
                } else if (this.mState == 2 && f < this.zzZC) {
                    this.zzZC = f;
                }
            } else if (i == 1 && this.mState == 4) {
                showDialog();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x006f, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x0071;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzhY() {
        /*
            r6 = this;
            android.content.Context r0 = r6.mContext
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 != 0) goto L_0x000c
            java.lang.String r0 = "Can not create dialog without Activity Context"
            com.google.android.gms.internal.zzafr.zzaS(r0)
        L_0x000b:
            return
        L_0x000c:
            java.lang.String r0 = r6.zzZB
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x009d
            java.lang.String r1 = "\\+"
            java.lang.String r2 = "%20"
            java.lang.String r0 = r0.replaceAll(r1, r2)
            android.net.Uri$Builder r1 = new android.net.Uri$Builder
            r1.<init>()
            android.net.Uri$Builder r0 = r1.encodedQuery(r0)
            android.net.Uri r0 = r0.build()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.google.android.gms.ads.internal.zzbs.zzbz()
            java.util.Map r2 = com.google.android.gms.internal.zzagz.zzg((android.net.Uri) r0)
            java.util.Set r0 = r2.keySet()
            java.util.Iterator r3 = r0.iterator()
        L_0x003d:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0063
            java.lang.Object r0 = r3.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.StringBuilder r4 = r1.append(r0)
            java.lang.String r5 = " = "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.Object r0 = r2.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r4 = "\n\n"
            r0.append(r4)
            goto L_0x003d
        L_0x0063:
            java.lang.String r0 = r1.toString()
            java.lang.String r0 = r0.trim()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x009d
        L_0x0071:
            android.app.AlertDialog$Builder r1 = new android.app.AlertDialog$Builder
            android.content.Context r2 = r6.mContext
            r1.<init>(r2)
            r1.setMessage(r0)
            java.lang.String r2 = "Ad Information"
            r1.setTitle(r2)
            java.lang.String r2 = "Share"
            com.google.android.gms.internal.zzahs r3 = new com.google.android.gms.internal.zzahs
            r3.<init>(r6, r0)
            r1.setPositiveButton(r2, r3)
            java.lang.String r0 = "Close"
            com.google.android.gms.internal.zzaht r2 = new com.google.android.gms.internal.zzaht
            r2.<init>(r6)
            r1.setNegativeButton(r0, r2)
            android.app.AlertDialog r0 = r1.create()
            r0.show()
            goto L_0x000b
        L_0x009d:
            java.lang.String r0 = "No debug information"
            goto L_0x0071
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzahq.zzhY():void");
    }

    /* access modifiers changed from: private */
    public final void zzhZ() {
        zzafr.zzaC("Debug mode [Creative Preview] selected.");
        zzagt.zza((Runnable) new zzahu(this));
    }

    /* access modifiers changed from: private */
    public final void zzia() {
        zzafr.zzaC("Debug mode [Troubleshooting] selected.");
        zzagt.zza((Runnable) new zzahv(this));
    }

    public final void setAdUnitId(String str) {
        this.zztV = str;
    }

    public final void showDialog() {
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzGs)).booleanValue()) {
            if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzGr)).booleanValue()) {
                zzhY();
                return;
            }
        }
        if (!(this.mContext instanceof Activity)) {
            zzafr.zzaS("Can not create dialog without Activity Context");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int zza = zza((List<String>) arrayList, "Ad Information", true);
        int zza2 = zza((List<String>) arrayList, "Creative Preview", ((Boolean) zzbs.zzbL().zzd(zzmo.zzGr)).booleanValue());
        int zza3 = zza((List<String>) arrayList, "Troubleshooting", ((Boolean) zzbs.zzbL().zzd(zzmo.zzGs)).booleanValue());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, zzbs.zzbB().zzhX());
        builder.setTitle("Select a Debug Mode").setItems((CharSequence[]) arrayList.toArray(new String[0]), new zzahr(this, zza, zza2, zza3));
        builder.create().show();
    }

    public final void zzaO(String str) {
        this.zzwH = str;
    }

    public final void zzaP(String str) {
        this.zzZB = str;
    }

    public final void zzf(MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            zza(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i), motionEvent.getHistoricalY(0, i));
        }
        zza(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }
}
