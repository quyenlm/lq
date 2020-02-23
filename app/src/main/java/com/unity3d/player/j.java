package com.unity3d.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

public final class j extends View {
    final int a;
    final int b = getResources().getIdentifier("unity_static_splash", "drawable", getContext().getPackageName());
    Bitmap c;
    Bitmap d;

    /* renamed from: com.unity3d.player.j$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[a.a().length];

        static {
            try {
                a[a.a - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.b - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.c - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    enum a {
        ;

        static {
            a = 1;
            b = 2;
            c = 3;
            d = new int[]{a, b, c};
        }

        public static int[] a() {
            return (int[]) d.clone();
        }
    }

    public j(Context context, int i) {
        super(context);
        this.a = i;
        if (this.b != 0) {
            forceLayout();
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.c != null) {
            this.c.recycle();
            this.c = null;
        }
        if (this.d != null) {
            this.d.recycle();
            this.d = null;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ba, code lost:
        r3 = (int) (((float) r0) * r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
        /*
            r10 = this;
            r1 = 1
            r2 = 0
            int r0 = r10.b
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            android.graphics.Bitmap r0 = r10.c
            if (r0 != 0) goto L_0x001e
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r0.inScaled = r2
            android.content.res.Resources r3 = r10.getResources()
            int r4 = r10.b
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeResource(r3, r4, r0)
            r10.c = r0
        L_0x001e:
            android.graphics.Bitmap r0 = r10.c
            int r6 = r0.getWidth()
            android.graphics.Bitmap r0 = r10.c
            int r4 = r0.getHeight()
            int r5 = r10.getWidth()
            int r3 = r10.getHeight()
            if (r5 == 0) goto L_0x0006
            if (r3 == 0) goto L_0x0006
            float r0 = (float) r6
            float r7 = (float) r4
            float r7 = r0 / r7
            float r0 = (float) r5
            float r8 = (float) r3
            float r0 = r0 / r8
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 > 0) goto L_0x00b0
            r0 = r1
        L_0x0042:
            int[] r8 = com.unity3d.player.j.AnonymousClass1.a
            int r9 = r10.a
            int r9 = r9 + -1
            r8 = r8[r9]
            switch(r8) {
                case 1: goto L_0x00b2;
                case 2: goto L_0x00be;
                case 3: goto L_0x00be;
                default: goto L_0x004d;
            }
        L_0x004d:
            r0 = r4
            r3 = r6
        L_0x004f:
            android.graphics.Bitmap r4 = r10.d
            if (r4 == 0) goto L_0x0071
            android.graphics.Bitmap r4 = r10.d
            int r4 = r4.getWidth()
            if (r4 != r3) goto L_0x0063
            android.graphics.Bitmap r4 = r10.d
            int r4 = r4.getHeight()
            if (r4 == r0) goto L_0x0006
        L_0x0063:
            android.graphics.Bitmap r4 = r10.d
            android.graphics.Bitmap r5 = r10.c
            if (r4 == r5) goto L_0x0071
            android.graphics.Bitmap r4 = r10.d
            r4.recycle()
            r4 = 0
            r10.d = r4
        L_0x0071:
            android.graphics.Bitmap r4 = r10.c
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createScaledBitmap(r4, r3, r0, r1)
            r10.d = r0
            android.graphics.Bitmap r0 = r10.d
            android.content.res.Resources r3 = r10.getResources()
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
            int r3 = r3.densityDpi
            r0.setDensity(r3)
            android.graphics.drawable.ColorDrawable r0 = new android.graphics.drawable.ColorDrawable
            r3 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0.<init>(r3)
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable
            android.content.res.Resources r4 = r10.getResources()
            android.graphics.Bitmap r5 = r10.d
            r3.<init>(r4, r5)
            r4 = 17
            r3.setGravity(r4)
            android.graphics.drawable.LayerDrawable r4 = new android.graphics.drawable.LayerDrawable
            r5 = 2
            android.graphics.drawable.Drawable[] r5 = new android.graphics.drawable.Drawable[r5]
            r5[r2] = r0
            r5[r1] = r3
            r4.<init>(r5)
            r10.setBackground(r4)
            goto L_0x0006
        L_0x00b0:
            r0 = r2
            goto L_0x0042
        L_0x00b2:
            if (r5 >= r6) goto L_0x00d4
            float r0 = (float) r5
            float r0 = r0 / r7
            int r0 = (int) r0
        L_0x00b7:
            if (r3 >= r0) goto L_0x00d1
            r0 = r3
        L_0x00ba:
            float r3 = (float) r0
            float r3 = r3 * r7
            int r3 = (int) r3
            goto L_0x004f
        L_0x00be:
            int r4 = r10.a
            int r6 = com.unity3d.player.j.a.c
            if (r4 != r6) goto L_0x00cd
            r4 = r1
        L_0x00c5:
            r0 = r0 ^ r4
            if (r0 == 0) goto L_0x00cf
            float r0 = (float) r5
            float r0 = r0 / r7
            int r0 = (int) r0
            r3 = r5
            goto L_0x004f
        L_0x00cd:
            r4 = r2
            goto L_0x00c5
        L_0x00cf:
            r0 = r3
            goto L_0x00ba
        L_0x00d1:
            r3 = r5
            goto L_0x004f
        L_0x00d4:
            r0 = r4
            r5 = r6
            goto L_0x00b7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.j.onLayout(boolean, int, int, int, int):void");
    }
}
