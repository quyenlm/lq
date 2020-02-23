package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import com.unity3d.player.j;
import com.unity3d.player.o;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UnityPlayer extends FrameLayout implements d {
    public static Activity currentActivity = null;
    private static boolean r;
    e a = new e(this, (byte) 0);
    i b = null;
    /* access modifiers changed from: private */
    public int c = -1;
    /* access modifiers changed from: private */
    public boolean d = false;
    private boolean e = true;
    /* access modifiers changed from: private */
    public l f = new l();
    private final ConcurrentLinkedQueue g = new ConcurrentLinkedQueue();
    private BroadcastReceiver h = null;
    private boolean i = false;
    private c j = new c(this, (byte) 0);
    private TelephonyManager k;
    /* access modifiers changed from: private */
    public j l;
    private GoogleARProxy m = null;
    private GoogleARCoreApi n = null;
    private a o = new a();
    /* access modifiers changed from: private */
    public Context p;
    /* access modifiers changed from: private */
    public SurfaceView q;
    /* access modifiers changed from: private */
    public boolean s;
    /* access modifiers changed from: private */
    public o t;

    /* renamed from: com.unity3d.player.UnityPlayer$3  reason: invalid class name */
    class AnonymousClass3 extends BroadcastReceiver {
        final /* synthetic */ UnityPlayer a;

        public void onReceive(Context context, Intent intent) {
            this.a.c();
        }
    }

    class a implements SensorEventListener {
        a() {
        }

        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
        }
    }

    enum b {
        ;

        static {
            a = 1;
            b = 2;
            c = 3;
            d = new int[]{a, b, c};
        }
    }

    private class c extends PhoneStateListener {
        private c() {
        }

        /* synthetic */ c(UnityPlayer unityPlayer, byte b) {
            this();
        }

        public final void onCallStateChanged(int i, String str) {
            boolean z = true;
            UnityPlayer unityPlayer = UnityPlayer.this;
            if (i != 1) {
                z = false;
            }
            unityPlayer.nativeMuteMasterAudio(z);
        }
    }

    enum d {
        PAUSE,
        RESUME,
        QUIT,
        SURFACE_LOST,
        SURFACE_ACQUIRED,
        FOCUS_LOST,
        FOCUS_GAINED,
        NEXT_FRAME
    }

    private class e extends Thread {
        Handler a;
        boolean b;
        boolean c;
        int d;
        int e;

        private e() {
            this.b = false;
            this.c = false;
            this.d = b.b;
            this.e = 5;
        }

        /* synthetic */ e(UnityPlayer unityPlayer, byte b2) {
            this();
        }

        private void a(d dVar) {
            if (this.a != null) {
                Message.obtain(this.a, 2269, dVar).sendToTarget();
            }
        }

        public final void a() {
            a(d.QUIT);
        }

        public final void a(Runnable runnable) {
            if (this.a != null) {
                a(d.PAUSE);
                Message.obtain(this.a, runnable).sendToTarget();
            }
        }

        public final void b() {
            a(d.RESUME);
        }

        public final void b(Runnable runnable) {
            if (this.a != null) {
                a(d.SURFACE_LOST);
                Message.obtain(this.a, runnable).sendToTarget();
            }
        }

        public final void c() {
            a(d.FOCUS_GAINED);
        }

        public final void c(Runnable runnable) {
            if (this.a != null) {
                Message.obtain(this.a, runnable).sendToTarget();
                a(d.SURFACE_ACQUIRED);
            }
        }

        public final void d() {
            a(d.FOCUS_LOST);
        }

        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.a = new Handler(new Handler.Callback() {
                private void a() {
                    if (e.this.d == b.c && e.this.c) {
                        UnityPlayer.this.nativeFocusChanged(true);
                        e.this.d = b.a;
                    }
                }

                public final boolean handleMessage(Message message) {
                    if (message.what != 2269) {
                        return false;
                    }
                    d dVar = (d) message.obj;
                    if (dVar == d.NEXT_FRAME) {
                        return true;
                    }
                    if (dVar == d.QUIT) {
                        Looper.myLooper().quit();
                    } else if (dVar == d.RESUME) {
                        e.this.b = true;
                    } else if (dVar == d.PAUSE) {
                        e.this.b = false;
                    } else if (dVar == d.SURFACE_LOST) {
                        e.this.c = false;
                    } else if (dVar == d.SURFACE_ACQUIRED) {
                        e.this.c = true;
                        a();
                    } else if (dVar == d.FOCUS_LOST) {
                        if (e.this.d == b.a) {
                            UnityPlayer.this.nativeFocusChanged(false);
                        }
                        e.this.d = b.b;
                    } else if (dVar == d.FOCUS_GAINED) {
                        e.this.d = b.c;
                        a();
                    }
                    return true;
                }
            });
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                public final boolean queueIdle() {
                    UnityPlayer.this.executeGLThreadJobs();
                    if (e.this.b && e.this.c) {
                        if (e.this.e >= 0) {
                            if (e.this.e == 0 && UnityPlayer.this.i()) {
                                UnityPlayer.this.a();
                            }
                            e eVar = e.this;
                            eVar.e--;
                        }
                        if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                            UnityPlayer.this.c();
                        }
                        Message.obtain(e.this.a, 2269, d.NEXT_FRAME).sendToTarget();
                    }
                    return true;
                }
            });
            Looper.loop();
        }
    }

    private abstract class f implements Runnable {
        private f() {
        }

        /* synthetic */ f(UnityPlayer unityPlayer, byte b) {
            this();
        }

        public abstract void a();

        public final void run() {
            if (!UnityPlayer.this.isFinishing()) {
                a();
            }
        }
    }

    static {
        new k().a();
        r = false;
        r = loadLibraryStatic("main");
    }

    public UnityPlayer(Context context) {
        super(context);
        if (context instanceof Activity) {
            currentActivity = (Activity) context;
            this.c = currentActivity.getRequestedOrientation();
        }
        a(currentActivity);
        this.p = context;
        if (currentActivity != null && i()) {
            this.l = new j(this.p, j.a.a()[getSplashMode()]);
            addView(this.l);
        }
        if (h.c) {
            if (currentActivity != null) {
                h.d.a(currentActivity, new Runnable() {
                    public final void run() {
                        UnityPlayer.this.a((Runnable) new Runnable() {
                            public final void run() {
                                UnityPlayer.this.f.d();
                                UnityPlayer.this.f();
                            }
                        });
                    }
                });
            } else {
                this.f.d();
            }
        }
        a(this.p.getApplicationInfo());
        if (!l.c()) {
            AlertDialog create = new AlertDialog.Builder(this.p).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    UnityPlayer.this.c();
                }
            }).setMessage("Your hardware does not support this application, sorry!").create();
            create.setCancelable(false);
            create.show();
            return;
        }
        initJni(context);
        this.q = b();
        addView(this.q);
        bringChildToFront(this.l);
        this.s = false;
        nativeInitWebRequest(UnityWebRequest.class);
        k();
        this.k = (TelephonyManager) this.p.getSystemService("phone");
        this.a.start();
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (!l.c()) {
            e.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
        } else {
            nativeUnitySendMessage(str, str2, str3);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        a((Runnable) new Runnable() {
            public final void run() {
                UnityPlayer.this.removeView(UnityPlayer.this.l);
                j unused = UnityPlayer.this.l = null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(int i2, Surface surface) {
        if (!this.d) {
            b(0, surface);
        }
    }

    private static void a(Activity activity) {
        View decorView;
        if (activity != null && activity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false) && activity.getWindow() != null && (decorView = activity.getWindow().getDecorView()) != null) {
            decorView.setSystemUiVisibility(7);
        }
    }

    private static void a(ApplicationInfo applicationInfo) {
        if (r && NativeLoader.load(applicationInfo.nativeLibraryDir)) {
            l.a();
        }
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.view.View r5, android.view.View r6) {
        /*
            r4 = this;
            r3 = 0
            com.unity3d.player.l r0 = r4.f
            boolean r0 = r0.e()
            if (r0 != 0) goto L_0x0045
            r4.pause()
            r0 = 1
            r2 = r0
        L_0x000e:
            if (r5 == 0) goto L_0x002f
            android.view.ViewParent r1 = r5.getParent()
            boolean r0 = r1 instanceof com.unity3d.player.UnityPlayer
            if (r0 == 0) goto L_0x001d
            r0 = r1
            com.unity3d.player.UnityPlayer r0 = (com.unity3d.player.UnityPlayer) r0
            if (r0 == r4) goto L_0x002f
        L_0x001d:
            boolean r0 = r1 instanceof android.view.ViewGroup
            if (r0 == 0) goto L_0x0026
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r1.removeView(r5)
        L_0x0026:
            r4.addView(r5)
            r4.bringChildToFront(r5)
            r5.setVisibility(r3)
        L_0x002f:
            if (r6 == 0) goto L_0x003f
            android.view.ViewParent r0 = r6.getParent()
            if (r0 != r4) goto L_0x003f
            r0 = 8
            r6.setVisibility(r0)
            r4.removeView(r6)
        L_0x003f:
            if (r2 == 0) goto L_0x0044
            r4.resume()
        L_0x0044:
            return
        L_0x0045:
            r2 = r3
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.UnityPlayer.a(android.view.View, android.view.View):void");
    }

    private void a(f fVar) {
        if (!isFinishing()) {
            b((Runnable) fVar);
        }
    }

    /* access modifiers changed from: private */
    public SurfaceView b() {
        SurfaceView surfaceView = new SurfaceView(this.p);
        surfaceView.getHolder().setFormat(-3);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                UnityPlayer.this.a(0, surfaceHolder.getSurface());
            }

            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.a(0, surfaceHolder.getSurface());
            }

            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.a(0, (Surface) null);
            }
        });
        surfaceView.setFocusable(true);
        surfaceView.setFocusableInTouchMode(true);
        return surfaceView;
    }

    private void b(Runnable runnable) {
        if (l.c()) {
            if (Thread.currentThread() == this.a) {
                runnable.run();
            } else {
                this.g.add(runnable);
            }
        }
    }

    private boolean b(final int i2, final Surface surface) {
        if (!l.c()) {
            return false;
        }
        final Semaphore semaphore = new Semaphore(0);
        AnonymousClass17 r0 = new Runnable() {
            public final void run() {
                UnityPlayer.this.nativeRecreateGfxState(i2, surface);
                semaphore.release();
            }
        };
        if (i2 != 0) {
            r0.run();
        } else if (surface == null) {
            this.a.b(r0);
        } else {
            this.a.c(r0);
        }
        if (surface == null && i2 == 0) {
            try {
                if (!semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                    e.Log(5, "Timeout while trying detaching primary window.");
                }
            } catch (InterruptedException e2) {
                e.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void c() {
        if ((this.p instanceof Activity) && !((Activity) this.p).isFinishing()) {
            ((Activity) this.p).finish();
        }
    }

    private void d() {
        reportSoftInputStr((String) null, 1, true);
        if (this.f.g()) {
            if (l.c()) {
                final Semaphore semaphore = new Semaphore(0);
                this.a.a(isFinishing() ? new Runnable() {
                    public final void run() {
                        UnityPlayer.this.e();
                        semaphore.release();
                    }
                } : new Runnable() {
                    public final void run() {
                        if (UnityPlayer.this.nativePause()) {
                            boolean unused = UnityPlayer.this.s = true;
                            UnityPlayer.this.e();
                            semaphore.release(2);
                            return;
                        }
                        semaphore.release();
                    }
                });
                try {
                    if (!semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                        e.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException e2) {
                    e.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore.drainPermits() > 0) {
                    quit();
                }
            }
            this.f.c(false);
            this.f.b(true);
            if (this.i) {
                this.k.listen(this.j, 0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        nativeDone();
    }

    /* access modifiers changed from: private */
    public void f() {
        if (this.f.f()) {
            this.f.c(true);
            b((Runnable) new Runnable() {
                public final void run() {
                    UnityPlayer.this.nativeResume();
                }
            });
            this.a.b();
        }
    }

    private static void g() {
        if (l.c()) {
            if (!NativeLoader.unload()) {
                throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
            }
            l.b();
        }
    }

    private ApplicationInfo h() {
        return this.p.getPackageManager().getApplicationInfo(this.p.getPackageName(), 128);
    }

    /* access modifiers changed from: private */
    public boolean i() {
        try {
            return h().metaData.getBoolean("unity.splash-enable");
        } catch (Exception e2) {
            return false;
        }
    }

    private final native void initJni(Context context);

    private boolean j() {
        try {
            return h().metaData.getBoolean("unity.tango-enable");
        } catch (Exception e2) {
            return false;
        }
    }

    private void k() {
        if (this.p instanceof Activity) {
            ((Activity) this.p).getWindow().setFlags(1024, 1024);
        }
    }

    protected static boolean loadLibraryStatic(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (UnsatisfiedLinkError e2) {
            e.Log(6, "Unable to find " + str);
            return false;
        } catch (Exception e3) {
            e.Log(6, "Unknown error " + e3);
            return false;
        }
    }

    private final native void nativeDone();

    /* access modifiers changed from: private */
    public final native void nativeFocusChanged(boolean z);

    private final native void nativeInitWebRequest(Class cls);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    /* access modifiers changed from: private */
    public final native boolean nativeIsAutorotationOn();

    /* access modifiers changed from: private */
    public final native void nativeLowMemory();

    /* access modifiers changed from: private */
    public final native void nativeMuteMasterAudio(boolean z);

    /* access modifiers changed from: private */
    public final native boolean nativePause();

    /* access modifiers changed from: private */
    public final native void nativeRecreateGfxState(int i2, Surface surface);

    /* access modifiers changed from: private */
    public final native boolean nativeRender();

    private final native void nativeRestartActivityIndicator();

    /* access modifiers changed from: private */
    public final native void nativeResume();

    /* access modifiers changed from: private */
    public final native void nativeSetInputString(String str);

    /* access modifiers changed from: private */
    public final native void nativeSoftInputCanceled();

    /* access modifiers changed from: private */
    public final native void nativeSoftInputClosed();

    private final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, String str3);

    /* access modifiers changed from: package-private */
    public final void a(Runnable runnable) {
        if (this.p instanceof Activity) {
            ((Activity) this.p).runOnUiThread(runnable);
        } else {
            e.Log(5, "Not running Unity from an Activity; ignored...");
        }
    }

    /* access modifiers changed from: protected */
    public void addPhoneCallListener() {
        this.i = true;
        this.k.listen(this.j, 32);
    }

    public boolean addViewToPlayer(View view, boolean z) {
        boolean z2 = true;
        a(view, (View) z ? this.q : null);
        boolean z3 = view.getParent() == this;
        boolean z4 = z && this.q.getParent() == null;
        boolean z5 = this.q.getParent() == this;
        if (!z3 || (!z4 && !z5)) {
            z2 = false;
        }
        if (!z2) {
            if (!z3) {
                e.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z4 && !z5) {
                e.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z2;
    }

    public void configurationChanged(Configuration configuration) {
        if (this.q instanceof SurfaceView) {
            this.q.getHolder().setSizeFromLayout();
        }
        if (this.t != null) {
            this.t.c();
        }
        GoogleVrProxy b2 = GoogleVrApi.b();
        if (b2 != null) {
            b2.c();
        }
    }

    /* access modifiers changed from: protected */
    public void disableLogger() {
        e.a = true;
    }

    public boolean displayChanged(int i2, Surface surface) {
        if (i2 == 0) {
            this.d = surface != null;
            a((Runnable) new Runnable() {
                public final void run() {
                    if (UnityPlayer.this.d) {
                        UnityPlayer.this.removeView(UnityPlayer.this.q);
                    } else {
                        UnityPlayer.this.addView(UnityPlayer.this.q);
                    }
                }
            });
        }
        return b(i2, surface);
    }

    /* access modifiers changed from: protected */
    public void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.g.poll();
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    public int getSplashMode() {
        try {
            return h().metaData.getInt("unity.splash-mode");
        } catch (Exception e2) {
            return 0;
        }
    }

    public View getView() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void hideSoftInput() {
        final AnonymousClass6 r0 = new Runnable() {
            public final void run() {
                if (UnityPlayer.this.b != null) {
                    UnityPlayer.this.b.dismiss();
                    UnityPlayer.this.b = null;
                }
            }
        };
        if (h.b) {
            a((f) new f() {
                public final void a() {
                    UnityPlayer.this.a(r0);
                }
            });
        } else {
            a((Runnable) r0);
        }
    }

    public void init(int i2, boolean z) {
    }

    /* access modifiers changed from: protected */
    public boolean initializeGoogleAr() {
        if (this.m == null && currentActivity != null && j()) {
            if (GoogleARProxy.a()) {
                this.m = new GoogleARProxy(this);
                this.m.a(currentActivity, this.p);
                this.m.b();
                if (!this.f.e()) {
                    this.m.d();
                }
                return this.m.e();
            }
            this.n = new GoogleARCoreApi();
            this.n.initializeARCore(currentActivity);
            if (!this.f.e()) {
                this.n.resumeARCore();
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean initializeGoogleVr() {
        final GoogleVrProxy b2 = GoogleVrApi.b();
        if (b2 == null) {
            GoogleVrApi.a(this);
            b2 = GoogleVrApi.b();
            if (b2 == null) {
                e.Log(6, "Unable to create Google VR subsystem.");
                return false;
            }
        }
        final Semaphore semaphore = new Semaphore(0);
        final AnonymousClass10 r3 = new Runnable() {
            public final void run() {
                UnityPlayer.this.injectEvent(new KeyEvent(0, 4));
                UnityPlayer.this.injectEvent(new KeyEvent(1, 4));
            }
        };
        a((Runnable) new Runnable() {
            public final void run() {
                if (!b2.a(UnityPlayer.currentActivity, UnityPlayer.this.p, UnityPlayer.this.b(), r3)) {
                    e.Log(6, "Unable to initialize Google VR subsystem.");
                }
                if (UnityPlayer.currentActivity != null) {
                    b2.a(UnityPlayer.currentActivity.getIntent());
                }
                semaphore.release();
            }
        });
        try {
            if (semaphore.tryAcquire(4, TimeUnit.SECONDS)) {
                return b2.a();
            }
            e.Log(5, "Timeout while trying to initialize Google VR.");
            return false;
        } catch (InterruptedException e2) {
            e.Log(5, "UI thread was interrupted while initializing Google VR. " + e2.getLocalizedMessage());
            return false;
        }
    }

    public boolean injectEvent(InputEvent inputEvent) {
        return nativeInjectEvent(inputEvent);
    }

    /* access modifiers changed from: protected */
    public boolean isFinishing() {
        if (!this.s) {
            boolean z = (this.p instanceof Activity) && ((Activity) this.p).isFinishing();
            this.s = z;
            if (!z) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void kill() {
        Process.killProcess(Process.myPid());
    }

    /* access modifiers changed from: protected */
    public boolean loadLibrary(String str) {
        return loadLibraryStatic(str);
    }

    public void lowMemory() {
        b((Runnable) new Runnable() {
            public final void run() {
                UnityPlayer.this.nativeLowMemory();
            }
        });
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyLongPress(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyMultiple(int i2, int i3, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    public void pause() {
        if (this.m != null) {
            this.m.c();
        } else if (this.n != null) {
            this.n.pauseARCore();
        }
        if (this.t != null) {
            this.t.a();
        }
        GoogleVrProxy b2 = GoogleVrApi.b();
        if (b2 != null) {
            b2.pauseGvrLayout();
        }
        d();
    }

    public void quit() {
        if (GoogleVrApi.b() != null) {
            GoogleVrApi.a();
        }
        this.s = true;
        if (!this.f.e()) {
            pause();
        }
        this.a.a();
        try {
            this.a.join(4000);
        } catch (InterruptedException e2) {
            this.a.interrupt();
        }
        if (this.h != null) {
            this.p.unregisterReceiver(this.h);
        }
        this.h = null;
        if (l.c()) {
            removeAllViews();
        }
        kill();
        g();
    }

    public void removeViewFromPlayer(View view) {
        boolean z = true;
        a((View) this.q, view);
        boolean z2 = view.getParent() == null;
        boolean z3 = this.q.getParent() == this;
        if (!z2 || !z3) {
            z = false;
        }
        if (!z) {
            if (!z2) {
                e.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
            }
            if (!z3) {
                e.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
            }
        }
    }

    public void reportError(String str, String str2) {
        e.Log(6, str + ": " + str2);
    }

    /* access modifiers changed from: protected */
    public void reportSoftInputStr(final String str, final int i2, final boolean z) {
        if (i2 == 1) {
            hideSoftInput();
        }
        a((f) new f() {
            public final void a() {
                if (z) {
                    UnityPlayer.this.nativeSoftInputCanceled();
                } else if (str != null) {
                    UnityPlayer.this.nativeSetInputString(str);
                }
                if (i2 == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    public void resume() {
        if (this.m != null) {
            this.m.d();
        } else if (this.n != null) {
            this.n.resumeARCore();
        }
        this.f.b(false);
        if (this.t != null) {
            this.t.b();
        }
        f();
        nativeRestartActivityIndicator();
        GoogleVrProxy b2 = GoogleVrApi.b();
        if (b2 != null) {
            b2.b();
        }
    }

    /* access modifiers changed from: protected */
    public void setSoftInputStr(final String str) {
        a((Runnable) new Runnable() {
            public final void run() {
                if (UnityPlayer.this.b != null && str != null) {
                    UnityPlayer.this.b.a(str);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void showSoftInput(String str, int i2, boolean z, boolean z2, boolean z3, boolean z4, String str2) {
        final String str3 = str;
        final int i3 = i2;
        final boolean z5 = z;
        final boolean z6 = z2;
        final boolean z7 = z3;
        final boolean z8 = z4;
        final String str4 = str2;
        a((Runnable) new Runnable() {
            public final void run() {
                UnityPlayer.this.b = new i(UnityPlayer.this.p, this, str3, i3, z5, z6, z7, str4);
                UnityPlayer.this.b.show();
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean showVideoPlayer(String str, int i2, int i3, int i4, boolean z, int i5, int i6) {
        if (this.t == null) {
            this.t = new o(this);
        }
        boolean a2 = this.t.a(this.p, str, i2, i3, i4, z, (long) i5, (long) i6, new o.a() {
            public final void a() {
                o unused = UnityPlayer.this.t = null;
            }
        });
        if (a2) {
            a((Runnable) new Runnable() {
                public final void run() {
                    if (UnityPlayer.this.nativeIsAutorotationOn() && (UnityPlayer.this.p instanceof Activity)) {
                        ((Activity) UnityPlayer.this.p).setRequestedOrientation(UnityPlayer.this.c);
                    }
                }
            });
        }
        return a2;
    }

    public void start() {
    }

    public void stop() {
    }

    /* access modifiers changed from: protected */
    public void toggleGyroscopeSensor(boolean z) {
        SensorManager sensorManager = (SensorManager) this.p.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z) {
            sensorManager.registerListener(this.o, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.o);
        }
    }

    public void windowFocusChanged(boolean z) {
        this.f.a(z);
        if (z && this.b != null) {
            nativeSoftInputLostFocus();
            reportSoftInputStr((String) null, 1, false);
        }
        if (z) {
            this.a.c();
        } else {
            this.a.d();
        }
        f();
    }
}
