package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;

class GoogleARProxy extends b {
    private boolean f = false;

    GoogleARProxy(d dVar) {
        super("Google AR", dVar);
    }

    public static boolean a() {
        try {
            Class<?> loadClass = UnityPlayer.class.getClassLoader().loadClass("com.unity3d.unitygar.GoogleAR");
            m mVar = new m(loadClass, loadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            mVar.a("getClassVersion", new Class[0]);
            if (((Number) mVar.a("getClassVersion", new Object[0])).intValue() > 0) {
                Log.d("Unity", "Loading ARCore V1+ path.");
                return false;
            }
            Log.d("Unity", "Loading ARCore Preview path (Version <= 1).");
            return true;
        } catch (Exception e) {
            Log.d("Unity", "Loading ARCore Preview path.");
            return true;
        }
    }

    private boolean a(ClassLoader classLoader) {
        if (this.f) {
            return true;
        }
        try {
            Class<?> loadClass = classLoader.loadClass("com.unity3d.unitygar.GoogleAR");
            m mVar = new m(loadClass, loadClass.getConstructor(new Class[0]).newInstance(new Object[0]));
            mVar.a("initialize", new Class[]{Activity.class});
            mVar.a("create", new Class[0]);
            mVar.a("pause", new Class[0]);
            mVar.a("resume", new Class[0]);
            this.a = mVar;
            this.f = true;
            return true;
        } catch (Exception e) {
            this.b.reportError("Google AR Error", e.toString() + e.getLocalizedMessage());
            return false;
        }
    }

    private final native void tangoOnCreate(Activity activity);

    private final native void tangoOnServiceConnected(IBinder iBinder);

    private final native void tangoOnStop();

    /* access modifiers changed from: package-private */
    public final void a(final Activity activity, Context context) {
        if (a(UnityPlayer.class.getClassLoader())) {
            this.c = context;
            runOnUiThread(new Runnable() {
                public final void run() {
                    try {
                        if (GoogleARProxy.this.a != null) {
                            GoogleARProxy.this.a.a("initialize", activity);
                        }
                    } catch (Exception e) {
                        GoogleARProxy.this.reportError("Exception creating " + GoogleARProxy.this.e + " VR on UI Thread. " + e.getLocalizedMessage());
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public final void b() {
        runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    if (GoogleARProxy.this.a != null) {
                        GoogleARProxy.this.a.a("create", new Object[0]);
                    }
                } catch (Exception e) {
                    GoogleARProxy.this.reportError("Exception creating " + GoogleARProxy.this.e + " VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public final void c() {
        runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    if (GoogleARProxy.this.a != null) {
                        GoogleARProxy.this.a.a("pause", new Object[0]);
                    }
                } catch (Exception e) {
                    GoogleARProxy.this.reportError("Exception pausing " + GoogleARProxy.this.e + " VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        runOnUiThread(new Runnable() {
            public final void run() {
                try {
                    if (GoogleARProxy.this.a != null) {
                        GoogleARProxy.this.a.a("resume", new Object[0]);
                    }
                } catch (Exception e) {
                    GoogleARProxy.this.reportError("Exception resuming " + GoogleARProxy.this.e + " VR on UI Thread. " + e.getLocalizedMessage());
                }
            }
        });
    }

    public final boolean e() {
        return this.f;
    }
}
