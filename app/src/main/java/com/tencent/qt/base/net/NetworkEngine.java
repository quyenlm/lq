package com.tencent.qt.base.net;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.tencent.qt.alg.network.NetworkFlowController;
import com.tencent.qt.base.net.NetworkHelper;
import com.tencent.qt.base.net.PLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkEngine {
    public static final int CONN_TYPE_DIR = 1;
    public static final int CONN_TYPE_PROXY = 0;
    private static final int DEFAULT_HELLO_TIME_INTERVAL = 270000;
    public static final int DEFAULT_TIMEOUT = 20000;
    public static final String HELLO_ACTION = "com.tencent.qt.base.net.HELLO_ACTION";
    private static final int HELLO_MAX_INTERVAL = 900000;
    private static final int MAX_PROTOCOL_TIMEOUT = 3;
    private static final int MSG_BROADCAST = 2;
    private static final long PROTOCOL_STAT_TIMEOUT = 10000;
    public static final String TAG = "QTNetwork";
    private static NetworkEngine instance_;
    private static int mInitClienttype;
    private static Context mInitContext;
    private static Looper mInitLooper;
    private static int mInitVersion;
    private static boolean mLoadLibary;
    private boolean isLogin = false;
    boolean isNeedHello;
    private boolean isSetUinSt = false;
    List<BroadcastHandler> mBroadcastHandlers = new ArrayList();
    Context mContext;
    NetworkFlowController mFlowController;
    Handler mHelloHandler;
    HelloHelper mHelloHelper;
    private HelloBroadcastReceiver mHelloReceiver = null;
    int mHelloTimeInterval = DEFAULT_HELLO_TIME_INTERVAL;
    private Map<Integer, IPAdress> mIPMaps = new HashMap();
    long mLastHelloTimestamp = 0;
    long mLastTimeoutProtocolTime = 0;
    Looper mLooper;
    private List<BroadcastHandler> mMatchedHandlers = new ArrayList();
    private long mNativeInJavaObj;
    NetworkReportHandler mNetworkHandler;
    int mTimeoutRequests = 0;
    /* access modifiers changed from: private */
    public long mUin = 0;
    VerifyHelper mVerifyHelper;
    /* access modifiers changed from: private */
    public byte[] mstDefaultKey;
    /* access modifiers changed from: private */
    public byte[] mstNormalKey;

    private native void native_close();

    private native int native_create_engine(int i, int i2);

    /* access modifiers changed from: private */
    public native void native_reconnect();

    private native void native_release_engine();

    /* access modifiers changed from: private */
    public native int native_send_request(int i, Request request, MessageHandler messageHandler, int i2);

    private native void native_set_hosts(int i, String[] strArr, int[] iArr);

    private native void native_set_support_64_uin(boolean z);

    private native void native_set_uin_default_normalkey(long j, byte[] bArr, byte[] bArr2);

    private native void native_set_uin_defaultkey(long j, byte[] bArr);

    static {
        mLoadLibary = true;
        GlobalPref.getInstant().loadLibary();
        mLoadLibary = GlobalPref.getInstant().isLoadLibary();
    }

    public static synchronized boolean init(Context context, Looper looper, int clienttype, int version) {
        boolean z;
        synchronized (NetworkEngine.class) {
            mInitContext = context;
            mInitLooper = looper;
            mInitClienttype = clienttype;
            mInitVersion = version;
            if (!mLoadLibary) {
                z = false;
            } else {
                z = true;
            }
        }
        return z;
    }

    public static synchronized boolean judgeLibiaryExist() {
        boolean z = true;
        synchronized (NetworkEngine.class) {
            if (!mLoadLibary) {
                try {
                    if (new File((("/data/data/" + mInitContext.getPackageName()) + "/lib") + File.separator, "libnetworkhelper.so").exists()) {
                        PLog.i(TAG, "libnetworkhelper.so" + "true", new Object[0]);
                    } else {
                        PLog.i(TAG, "libnetworkhelper.so" + "false", new Object[0]);
                        z = false;
                    }
                } catch (Exception e) {
                }
            }
        }
        return z;
    }

    private static synchronized void ensureInit() {
        synchronized (NetworkEngine.class) {
            if (instance_ == null) {
                PLog.i(TAG, "inited !", new Object[0]);
                NetworkHelper.sharedHelper().registerNetworkSensor(mInitContext);
                instance_ = new NetworkEngine(mInitContext, mInitLooper, mInitClienttype, mInitVersion);
                instance_.create(mInitClienttype, mInitVersion);
            }
        }
    }

    public static synchronized void unInit() {
        synchronized (NetworkEngine.class) {
            if (instance_ != null) {
                instance_.stopHello();
                instance_.destroy();
            }
        }
    }

    public static NetworkEngine shareEngine() {
        ensureInit();
        return instance_;
    }

    public static int send(int command, int subcmd, byte[] payload, MessageHandler handler) {
        NetworkEngine networkEngine = shareEngine();
        if (networkEngine == null) {
            return -1;
        }
        return networkEngine.sendRequest(command, subcmd, payload, handler);
    }

    public static void enableLogging(boolean enabled, int level) {
        PLog.enableLog(enabled, level);
    }

    public static boolean traceLogging(PLog.TraceMode mode, PLog.StoreMode sm, String path) {
        return PLog.trace(mode, sm, path);
    }

    private NetworkEngine(Context context, Looper looper, int clienttype, int version) {
        this.mContext = context;
        this.mLooper = looper;
        if (looper == null) {
            this.mLooper = Looper.getMainLooper();
        }
        this.mHelloHandler = new HelloHandler(this.mLooper);
        this.mNetworkHandler = new NetworkReportHandler(this.mLooper);
    }

    /* access modifiers changed from: protected */
    public void destroy() {
        try {
            native_release_engine();
        } catch (UnsatisfiedLinkError e) {
        }
    }

    /* access modifiers changed from: protected */
    public boolean isReleased() {
        return this.mNativeInJavaObj == 0;
    }

    /* access modifiers changed from: protected */
    public boolean create(int clienttype, int version) {
        boolean z = true;
        if (!isReleased()) {
            return false;
        }
        NetworkHelper.sharedHelper().registerNetworkSensor(this.mContext);
        int ret = -1;
        try {
            ret = native_create_engine(clienttype, version);
            native_set_support_64_uin(true);
        } catch (UnsatisfiedLinkError e) {
        }
        if (ret != 0) {
            z = false;
        }
        return z;
    }

    public void setSupportUIN64(boolean s) {
        if (!isReleased()) {
            try {
                native_set_support_64_uin(s);
            } catch (UnsatisfiedLinkError e) {
            }
        }
    }

    public void setLooper(Looper looper) {
        this.mLooper = looper;
        if (looper == null) {
            this.mLooper = Looper.getMainLooper();
        }
        this.mNetworkHandler = new NetworkReportHandler(this.mLooper);
        this.mHelloHandler = new HelloHandler(this.mLooper);
    }

    public void setHelloInterval(int timeInterval) {
        if (timeInterval != this.mHelloTimeInterval) {
            stopHello();
            this.mHelloTimeInterval = timeInterval;
            PLog.v(TAG, String.format("HelloTimeInterval:%d", new Object[]{Integer.valueOf(this.mHelloTimeInterval)}), new Object[0]);
            startHello();
        }
    }

    public void setHelloHelper(HelloHelper helper) {
        this.mHelloHelper = helper;
        if (helper == null) {
            this.mHelloTimeInterval = DEFAULT_HELLO_TIME_INTERVAL;
            return;
        }
        int interval = helper.getHelloInterval();
        if (interval > 899000) {
            interval = 899000;
        } else if (interval <= 0) {
            interval = DEFAULT_HELLO_TIME_INTERVAL;
        }
        this.mHelloTimeInterval = interval;
    }

    public void setVerifyHelper(VerifyHelper helper) {
        this.mVerifyHelper = helper;
    }

    public void setFlowController(NetworkFlowController controller) {
        this.mFlowController = controller;
    }

    public void onLogin(long uin, byte[] stDefaultkey, byte[] stNormalkey) {
        PLog.v(TAG, "onLogin", new Object[0]);
        if (stDefaultkey != null && stNormalkey != null && !isReleased()) {
            try {
                native_set_uin_default_normalkey(uin, stDefaultkey, stNormalkey);
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
            this.isSetUinSt = true;
            this.mUin = uin;
            this.mstDefaultKey = (byte[]) stDefaultkey.clone();
            this.mstNormalKey = (byte[]) stNormalkey.clone();
        }
    }

    public void setDefultkey(long uin, byte[] stdefaultKey) {
        try {
            native_set_uin_defaultkey(uin, stdefaultKey);
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public void setHosts(int type, String[] hosts, int[] ports) {
        try {
            native_set_hosts(type, hosts, ports);
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public void onLogout() {
        PLog.d("_login_QTNetwork", "onLogout", new Object[0]);
        PLog.v(TAG, "onLogout", new Object[0]);
        stopHello();
        try {
            native_close();
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public int sendRequest(int command, int subcmd, byte[] payload, MessageHandler handler) {
        return sendRequest(0, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, 20000);
    }

    public int sendRequest(int command, int subcmd, byte[] payload, MessageHandler handler, NetworkUIHandler networkErrorHandler) {
        return sendRequest(0, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, networkErrorHandler, 20000);
    }

    public int sendRequest(int command, int subcmd, byte[] payload, MessageHandler handler, int expiredTime) {
        return sendRequest(0, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, expiredTime);
    }

    public int sendRequest(int command, int subcmd, byte[] payload, MessageHandler handler, NetworkUIHandler networkErrorHandler, int expiredTime) {
        return sendRequest(0, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, networkErrorHandler, expiredTime);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, MessageHandler handler) {
        return sendRequest(type, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, 20000);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, MessageHandler handler, NetworkUIHandler networkErrorHandler) {
        return sendRequest(type, command, subcmd, payload, (byte[]) null, (byte[]) null, handler, networkErrorHandler, 20000);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] extra, MessageHandler handler) {
        return sendRequest(type, command, subcmd, payload, (byte[]) null, extra, handler, 20000);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] extra, MessageHandler handler, NetworkUIHandler networkHandler) {
        return sendRequest(type, command, subcmd, payload, (byte[]) null, extra, handler, networkHandler, 20000);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] extra, MessageHandler handler, int expiredTime) {
        return sendRequest(type, command, subcmd, payload, (byte[]) null, extra, handler, expiredTime);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] reserve, byte[] extra, MessageHandler handler) {
        return sendRequest(type, command, subcmd, payload, reserve, extra, handler, 20000);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] reserve, byte[] extra, MessageHandler handler, int expiredTime) {
        return sendRequest(type, command, subcmd, payload, reserve, extra, handler, (NetworkUIHandler) null, expiredTime);
    }

    public int sendRequest(int type, int command, int subcmd, byte[] payload, byte[] reserve, byte[] extra, MessageHandler handler, NetworkUIHandler networkHandler, int expiredTime) {
        PLog.i(TAG, String.format("sendRequest: %04x,%02x", new Object[]{Integer.valueOf(command), Integer.valueOf(subcmd)}), new Object[0]);
        if (NetworkHelper.sharedHelper().getNetworkStatus().equals(NetworkHelper.NetworkStatus.NetworkNotReachable)) {
            callRequestFail(type, command, subcmd, 0, -1);
            if (networkHandler != null) {
                Message msg = Message.obtain();
                msg.obj = networkHandler;
                msg.arg1 = command;
                msg.arg2 = subcmd;
                this.mNetworkHandler.sendMessage(msg);
                return -1;
            }
            broadcastNetworkEvent(1);
            PLog.e(TAG, String.format("%04x,%02x(net error)", new Object[]{Integer.valueOf(command), Integer.valueOf(subcmd)}), new Object[0]);
            return -1;
        }
        Request request = Request.createEncryptRequest(command, subcmd, payload, reserve, extra);
        int seq = -1;
        try {
            seq = native_send_request(type, request, new InnerWrapHandler(handler, this.mLooper, type), expiredTime);
        } catch (UnsatisfiedLinkError e) {
            PLog.e(TAG, "can't find native .so library file: " + e, new Object[0]);
        }
        request.sequenceNumber = seq;
        PLog.i(TAG, String.format("after sendRequest: %04x,%02x,%d", new Object[]{Integer.valueOf(command), Integer.valueOf(subcmd), Integer.valueOf(seq)}), new Object[0]);
        return seq;
    }

    public synchronized void addBroadcastHandler(BroadcastHandler handler) {
        if (!this.mBroadcastHandlers.contains(handler)) {
            this.mBroadcastHandlers.add(handler);
        }
    }

    public synchronized void removeBroadcastHandler(BroadcastHandler handler) {
        this.mBroadcastHandlers.remove(handler);
    }

    public boolean isLogin() {
        return this.isLogin;
    }

    public void startHello() {
        this.isLogin = true;
        Context context = this.mContext;
        if (context != null) {
            this.isNeedHello = true;
            this.mLastHelloTimestamp = System.currentTimeMillis();
            AlarmManager manager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            synchronized (this) {
                if (this.mHelloReceiver == null) {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(HELLO_ACTION);
                    this.mHelloReceiver = new HelloBroadcastReceiver();
                    context.registerReceiver(this.mHelloReceiver, filter);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(HELLO_ACTION), 134217728);
                    long triggerAtTime = SystemClock.elapsedRealtime();
                    manager.setRepeating(1, triggerAtTime, (long) this.mHelloTimeInterval, pendingIntent);
                    PLog.i(TAG, String.format("startHello interval:%d,%d", new Object[]{Long.valueOf(triggerAtTime), Integer.valueOf(this.mHelloTimeInterval)}), new Object[0]);
                }
            }
        }
    }

    public void stopHello() {
        this.isLogin = false;
        Context context = this.mContext;
        if (context != null) {
            PLog.w(TAG, "=> hello stop!", new Object[0]);
            this.isNeedHello = false;
            synchronized (this) {
                if (this.mHelloReceiver != null) {
                    try {
                        context.unregisterReceiver(this.mHelloReceiver);
                    } catch (IllegalArgumentException e) {
                    }
                    this.mHelloReceiver = null;
                    ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(PendingIntent.getService(context, 0, new Intent(HELLO_ACTION), 134217728));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStatConnected(int type, String host, int port, int elapsed, boolean isRetry) {
        NetworkFlowController controller = this.mFlowController;
        IPAdress adress = new IPAdress();
        adress.host = host;
        adress.port = port;
        this.mIPMaps.put(Integer.valueOf(type), adress);
        if (controller != null) {
            controller.onConnectionSuccess(type, host, port, elapsed, isRetry);
        }
    }

    /* access modifiers changed from: protected */
    public void onStatConnFailure(int type, String host, int port, int err, boolean isRetry) {
        this.isLogin = false;
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            if (NetworkHelper.sharedHelper().getNetworkStatus().equals(NetworkHelper.NetworkStatus.NetworkNotReachable)) {
                err = -5;
            }
            controller.onConnectionFail(type, host, port, err, isRetry);
        }
    }

    /* access modifiers changed from: protected */
    public void onStatVerityTimeout(int type, int command, int subcmd, int seq, int times) {
        callRequestFail(type, command, subcmd, seq, -2);
    }

    /* access modifiers changed from: protected */
    public void didConnectToHost(int type, String host, int port) {
        IPAdress adress = new IPAdress();
        adress.host = host;
        adress.port = port;
        this.mIPMaps.put(Integer.valueOf(type), adress);
        if (type == 0) {
            PLog.i(TAG, "proxy didConnectToHost host = %s, port = %d", host, Integer.valueOf(port));
            startHello();
        } else if (type == 1) {
            PLog.i(TAG, "dir didConnectToHost host = %s, port = %d", host, Integer.valueOf(port));
        }
        broadcastChannelEvent(type, 1);
    }

    /* access modifiers changed from: protected */
    public void didDisconnect(int type) {
        this.isLogin = false;
        if (type == 0) {
            PLog.i(TAG, "proxy didDisconnect", new Object[0]);
            stopHello();
        } else if (type == 1) {
            PLog.i(TAG, "dir didDisconnect", new Object[0]);
        }
        broadcastChannelEvent(type, 2);
    }

    /* access modifiers changed from: protected */
    public void onNetworkReceived(int type, int command, int subcmd, int seq, int len, int elapsed) {
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            controller.onPacketReceived(type, command, subcmd, seq, len, elapsed);
        }
    }

    /* access modifiers changed from: protected */
    public void onNetworkSended(int type, int command, int subcmd, int seq, int len) {
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            controller.onPacketSended(type, command, subcmd, seq, len);
        }
    }

    /* access modifiers changed from: protected */
    public void onHostResolveSuccess(int type, String host, String ip, int time) {
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            controller.onHostResolveSuccess(type, host, ip, time);
        }
    }

    /* access modifiers changed from: protected */
    public void onHostResolveFailure(int type, String host, int error) {
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            controller.onHostResloveFailure(type, host, error);
        }
    }

    private void broadcastNetworkEvent(int subcmd) {
        PLog.v(TAG, "broadcastNetworkEvent subcmd = %d", Integer.valueOf(subcmd));
        List<BroadcastHandler> handlers = getMatchedBroadcastHandler(65536, subcmd);
        if (handlers != null) {
            broadcastTo(new NetworkBroadcast(subcmd), handlers);
        }
    }

    private void broadcastChannelEvent(int type, int subcmd) {
        PLog.i(TAG, "broadcastChannelEvent type = %d, subcmd = %d", Integer.valueOf(type), Integer.valueOf(subcmd));
        List<BroadcastHandler> handlers = getMatchedBroadcastHandler(65535, subcmd);
        if (handlers != null) {
            ChannelBroadcast cb = new ChannelBroadcast(type);
            cb.subcmd = subcmd;
            broadcastTo(cb, handlers);
        }
    }

    /* access modifiers changed from: protected */
    public void onConnectionFailure(int type) {
        if (type == 0) {
            PLog.w(TAG, "proxy onConnectionFailure", new Object[0]);
            stopHello();
        } else if (type == 1) {
            PLog.w(TAG, "dir onConnectionFailure", new Object[0]);
        }
        broadcastChannelEvent(type, 3);
    }

    /* access modifiers changed from: protected */
    public Request getSTRequest(boolean withLogin) {
        VerifyHelper helper = this.mVerifyHelper;
        if (helper != null) {
            return helper.getSTRequest(withLogin);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int onSTResponse(Message msg) {
        VerifyHelper helper = this.mVerifyHelper;
        if (helper != null) {
            return helper.onSTReponse(msg);
        }
        return 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r5 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        if (r5.hasNext() == false) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        r2 = (com.tencent.qt.base.net.BroadcastHandler) r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        if (r2.match(r8, r9, 0) == false) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        if (r3 != null) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        r3 = new java.util.ArrayList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        r3.add(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        r4 = r3;
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.tencent.qt.base.net.BroadcastHandler> getMatchedBroadcastHandler(int r8, int r9) {
        /*
            r7 = this;
            r0 = 0
            r3 = 0
            monitor-enter(r7)
            java.util.List<com.tencent.qt.base.net.BroadcastHandler> r5 = r7.mBroadcastHandlers     // Catch:{ all -> 0x0039 }
            int r5 = r5.size()     // Catch:{ all -> 0x0039 }
            if (r5 != 0) goto L_0x000f
            r5 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x0039 }
            r4 = r3
        L_0x000e:
            return r5
        L_0x000f:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0039 }
            java.util.List<com.tencent.qt.base.net.BroadcastHandler> r5 = r7.mBroadcastHandlers     // Catch:{ all -> 0x0039 }
            r1.<init>(r5)     // Catch:{ all -> 0x0039 }
            monitor-exit(r7)     // Catch:{ all -> 0x0040 }
            java.util.Iterator r5 = r1.iterator()
        L_0x001b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x003c
            java.lang.Object r2 = r5.next()
            com.tencent.qt.base.net.BroadcastHandler r2 = (com.tencent.qt.base.net.BroadcastHandler) r2
            r6 = 0
            boolean r6 = r2.match(r8, r9, r6)
            if (r6 == 0) goto L_0x001b
            if (r3 != 0) goto L_0x0035
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x0035:
            r3.add(r2)
            goto L_0x001b
        L_0x0039:
            r5 = move-exception
        L_0x003a:
            monitor-exit(r7)     // Catch:{ all -> 0x0039 }
            throw r5
        L_0x003c:
            r4 = r3
            r0 = r1
            r5 = r3
            goto L_0x000e
        L_0x0040:
            r5 = move-exception
            r0 = r1
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qt.base.net.NetworkEngine.getMatchedBroadcastHandler(int, int):java.util.List");
    }

    private void broadcastTo(Message msg, List<BroadcastHandler> handlers) {
        if (handlers != null && handlers.size() != 0) {
            BroadcastData data = new BroadcastData();
            data.message = msg;
            data.handlers = handlers;
            this.mHelloHandler.obtainMessage(2, data).sendToTarget();
        }
    }

    /* access modifiers changed from: protected */
    public boolean matchBroadcast(int cmd, int subcmd) {
        PLog.v(TAG, "matchBroadcast ", new Object[0]);
        List<BroadcastHandler> handlers = getMatchedBroadcastHandler(cmd, subcmd);
        if (handlers == null) {
            return false;
        }
        PLog.v(TAG, "matchBroadcast addAll", new Object[0]);
        this.mMatchedHandlers.addAll(handlers);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBroadcast(Message msg) {
        List<BroadcastHandler> handlers = new ArrayList<>(this.mMatchedHandlers);
        this.mMatchedHandlers.clear();
        BroadcastData data = new BroadcastData();
        data.message = msg;
        data.handlers = handlers;
        this.mHelloHandler.obtainMessage(2, data).sendToTarget();
        PLog.v(TAG, "onBroadcast", new Object[0]);
    }

    private class HelloMessageHandler implements MessageHandler {
        private HelloMessageHandler() {
        }

        public boolean match(int cmd, int subcmd, int seq) {
            return true;
        }

        public void onMessage(Request request, Message msg) {
            HelloHelper helper = NetworkEngine.this.mHelloHelper;
            if (helper != null) {
                if (helper.isHelloOK(msg)) {
                    NetworkEngine.this.mLastHelloTimestamp = System.currentTimeMillis();
                    NetworkEngine.shareEngine().setHelloInterval(helper.getHelloInterval());
                    PLog.i(NetworkEngine.TAG, "=>proxy hello success", new Object[0]);
                    return;
                }
                NetworkEngine.this.onLogout();
                NetworkEngine.this.onLogin(NetworkEngine.this.mUin, NetworkEngine.this.mstDefaultKey, NetworkEngine.this.mstNormalKey);
                NetworkEngine.this.connect();
                PLog.e(NetworkEngine.TAG, "=>proxy hello fail reconnect!", new Object[0]);
            }
        }

        public void onTimeout(Request request) {
            PLog.i(NetworkEngine.TAG, "proxy hello timeout", new Object[0]);
        }
    }

    private static class BroadcastData {
        List<BroadcastHandler> handlers;
        Message message;

        private BroadcastData() {
        }

        public void handleBroadcast() {
            PLog.v(NetworkEngine.TAG, "handleBroadcast", new Object[0]);
            for (BroadcastHandler handler : this.handlers) {
                PLog.i(NetworkEngine.TAG, String.format("r %04x,%02x,%d", new Object[]{Integer.valueOf(this.message.command), Integer.valueOf(this.message.subcmd), Integer.valueOf(this.message.sequenceNumber)}), new Object[0]);
                handler.onBroadcast(this.message);
            }
        }
    }

    private static class HelloHandler extends Handler {
        public HelloHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                PLog.v(NetworkEngine.TAG, "handleMessage", new Object[0]);
                ((BroadcastData) msg.obj).handleBroadcast();
            }
        }
    }

    private static class NetworkReportHandler extends Handler {
        public NetworkReportHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            ((NetworkUIHandler) msg.obj).onNetworkUnvaliable(msg.arg1, msg.arg2);
        }
    }

    private void clearTimeout() {
        this.mLastTimeoutProtocolTime = 0;
        this.mTimeoutRequests = 0;
    }

    /* access modifiers changed from: private */
    public void onRequestTimeout() {
        long current = System.currentTimeMillis();
        if (this.mLastTimeoutProtocolTime <= 0) {
            this.mLastTimeoutProtocolTime = current;
            this.mTimeoutRequests++;
        } else if (current - this.mLastTimeoutProtocolTime >= 10000) {
            this.mLastTimeoutProtocolTime = current;
            this.mTimeoutRequests++;
        }
        if (this.mTimeoutRequests >= 3) {
            PLog.w(TAG, "protocol response lost exceed %d times, maybe connection break down", 3);
            stopHello();
            try {
                native_reconnect();
            } catch (UnsatisfiedLinkError e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void onRequestRespond() {
        clearTimeout();
    }

    /* access modifiers changed from: private */
    public void callRequestFail(int type, int command, int subcmd, int seq, int err) {
        PLog.w(TAG, String.format("t %04x,%02x,%d, error=%d", new Object[]{Integer.valueOf(command), Integer.valueOf(subcmd), Integer.valueOf(seq), Integer.valueOf(err)}), new Object[0]);
        NetworkFlowController controller = this.mFlowController;
        if (controller != null) {
            IPAdress adr = this.mIPMaps.get(Integer.valueOf(type));
            controller.onRequestFail(type, adr != null ? adr.host : "", adr != null ? adr.port : -1, command, subcmd, seq, err, this.isLogin);
        }
    }

    private class InnerWrapHandler extends WrapMessageHandler {
        int channelType;

        public InnerWrapHandler(MessageHandler msgHandler, Looper looper, int type) {
            super(msgHandler, looper);
            this.channelType = type;
        }

        /* access modifiers changed from: protected */
        public void onChildMessage(Request request, Message msg) {
            if (this.channelType == 0) {
                NetworkEngine.this.onRequestRespond();
            }
            super.onChildMessage(request, msg);
        }

        /* access modifiers changed from: protected */
        public void onChildTimeout(Request request) {
            NetworkEngine.this.callRequestFail(this.channelType, request.command, request.subcmd, request.sequenceNumber, -2);
            super.onChildTimeout(request);
            if (this.channelType == 0) {
                NetworkEngine.this.onRequestTimeout();
            }
        }
    }

    class HelloBroadcastReceiver extends BroadcastReceiver {
        HelloBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (NetworkEngine.this.isNeedHello) {
                if (System.currentTimeMillis() - NetworkEngine.this.mLastHelloTimestamp >= 900000) {
                    NetworkEngine.this.isNeedHello = false;
                    PLog.w(NetworkEngine.TAG, "=> hello response lost, maybe connection break down", new Object[0]);
                    try {
                        NetworkEngine.this.native_reconnect();
                    } catch (UnsatisfiedLinkError e) {
                    }
                } else if (NetworkHelper.sharedHelper().getNetworkStatus().equals(NetworkHelper.NetworkStatus.NetworkNotReachable)) {
                    PLog.i(NetworkEngine.TAG, "=> do hello ,networknotreachable no send", new Object[0]);
                } else {
                    HelloHelper helper = NetworkEngine.this.mHelloHelper;
                    int seq = -1;
                    if (helper == null) {
                        PLog.w(NetworkEngine.TAG, "=> no hello helper", new Object[0]);
                    } else {
                        try {
                            seq = NetworkEngine.this.native_send_request(0, helper.getHello(), new InnerWrapHandler(new HelloMessageHandler(), NetworkEngine.this.mLooper, 0), 20000);
                        } catch (UnsatisfiedLinkError e2) {
                        }
                    }
                    PLog.i(NetworkEngine.TAG, "=> do hello ,seq = " + seq, new Object[0]);
                }
            }
        }
    }

    public void connect() throws IllegalStateException {
        if (!this.isSetUinSt) {
        }
        if (isReleased()) {
        }
        try {
            native_reconnect();
        } catch (UnsatisfiedLinkError e) {
        }
    }

    private static class IPAdress {
        String host;
        int port;

        private IPAdress() {
        }
    }
}
