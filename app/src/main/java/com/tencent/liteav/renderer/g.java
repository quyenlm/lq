package com.tencent.liteav.renderer;

import android.graphics.Matrix;
import android.os.Handler;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXCTextureViewWrapper */
public class g {
    private TextureView a;
    private Handler b;
    private int c = 0;
    private int d = 0;
    /* access modifiers changed from: private */
    public int e = 640;
    /* access modifiers changed from: private */
    public int f = 480;
    private int g = 0;
    private int h = 0;
    /* access modifiers changed from: private */
    public int i = 1;
    /* access modifiers changed from: private */
    public int j = 0;
    private float k = 1.0f;
    private int l = 0;

    public g(TextureView textureView) {
        this.a = textureView;
        this.c = textureView.getWidth();
        this.d = textureView.getHeight();
        this.b = new Handler(textureView.getContext().getMainLooper());
    }

    public void a(final int i2) {
        try {
            this.b.post(new Runnable() {
                public void run() {
                    g.this.b(i2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void b(int i2) {
        float f2 = 1.0f;
        this.i = i2;
        if (this.a != null) {
            if (i2 == 1) {
                if (!(this.j == 0 || this.j == 180 || (this.j != 270 && this.j != 90))) {
                    if (this.g != 0 && this.h != 0) {
                        float f3 = ((float) this.d) / ((float) this.g);
                        f2 = ((float) this.c) / ((float) this.h);
                        if (f3 <= f2) {
                            f2 = f3;
                        }
                    } else {
                        return;
                    }
                }
            } else if (i2 == 0) {
                if (this.g != 0 && this.h != 0) {
                    if (this.j == 0 || this.j == 180) {
                        float f4 = ((float) this.d) / ((float) this.h);
                        f2 = ((float) this.c) / ((float) this.g);
                        if (f4 >= f2) {
                            f2 = f4;
                        }
                    } else if (this.j == 270 || this.j == 90) {
                        float f5 = ((float) this.d) / ((float) this.g);
                        f2 = ((float) this.c) / ((float) this.h);
                        if (f5 >= f2) {
                            f2 = f5;
                        }
                    }
                } else {
                    return;
                }
            }
            if (this.k < 0.0f) {
                f2 = -f2;
            }
            this.a.setScaleX(f2);
            this.a.setScaleY(Math.abs(f2));
            this.k = f2;
        }
    }

    public void c(final int i2) {
        try {
            this.b.post(new Runnable() {
                public void run() {
                    g.this.d(i2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void d(int i2) {
        float f2;
        int i3 = i2 % 360;
        this.j = i3;
        if (this.a != null) {
            if (i3 == 0 || i3 == 180) {
                this.a.setRotation((float) (360 - i3));
                if (this.i == 1) {
                    f2 = 1.0f;
                } else {
                    if (this.i == 0) {
                        if (this.g != 0 && this.h != 0) {
                            float f3 = ((float) this.d) / ((float) this.h);
                            f2 = ((float) this.c) / ((float) this.g);
                            if (f3 >= f2) {
                                f2 = f3;
                            }
                        } else {
                            return;
                        }
                    }
                    f2 = 1.0f;
                }
            } else {
                if (i3 == 270 || i3 == 90) {
                    if (this.g != 0 && this.h != 0) {
                        this.a.setRotation((float) (360 - i3));
                        float f4 = ((float) this.d) / ((float) this.g);
                        f2 = ((float) this.c) / ((float) this.h);
                        if (this.i == 1) {
                            if (f4 <= f2) {
                                f2 = f4;
                            }
                        } else if (this.i == 0) {
                            if (f4 >= f2) {
                                f2 = f4;
                            }
                        }
                    } else {
                        return;
                    }
                }
                f2 = 1.0f;
            }
            if (this.k < 0.0f) {
                f2 = -f2;
            }
            this.a.setScaleX(f2);
            this.a.setScaleY(Math.abs(f2));
            this.k = f2;
        }
    }

    private void a() {
        try {
            this.b.post(new Runnable() {
                public void run() {
                    g.this.c(g.this.e, g.this.f);
                    g.this.b(g.this.i);
                    g.this.d(g.this.j);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void c(int i2, int i3) {
        if (this.a != null && i2 != 0 && i3 != 0 && this.c != 0 && this.d != 0) {
            double d2 = ((double) i3) / ((double) i2);
            if (this.d > ((int) (((double) this.c) * d2))) {
                this.g = this.c;
                this.h = (int) (d2 * ((double) this.c));
            } else {
                this.g = (int) (((double) this.d) / d2);
                this.h = this.d;
            }
            float f2 = ((float) this.g) / ((float) this.c);
            float f3 = ((float) this.h) / ((float) this.d);
            Matrix matrix = new Matrix();
            this.a.getTransform(matrix);
            matrix.setScale(f2, f3);
            matrix.postTranslate(((float) (this.c - this.g)) / 2.0f, ((float) (this.d - this.h)) / 2.0f);
            this.a.setTransform(matrix);
            this.a.requestLayout();
        }
    }

    public void a(int i2, int i3) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set view size:" + i2 + "," + i3);
        this.c = i2;
        this.d = i3;
        a();
    }

    public void b(int i2, int i3) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set video size:" + i2 + "," + i3);
        this.e = i2;
        this.f = i3;
        a();
    }
}
