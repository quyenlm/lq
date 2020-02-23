package com.tencent.qqgamemi.protocol.pbproxy;

import android.os.Handler;
import android.os.Looper;
import com.amazonaws.services.s3.internal.Constants;
import com.squareup.wire.Message;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.mgc.connection.IPManager;
import com.tencent.qqgamemi.mgc.connection.NetworkAddress;
import com.tencent.qqgamemi.mgc.connection.ProtoFactory;
import com.tencent.qqgamemi.mgc.core.MGCContext;
import com.tencent.qqgamemi.mgc.pb.ProtoUtils;
import com.tencent.qqgamemi.util.CollectionUtils;
import com.tencent.qt.base.net.MessageHandler;
import com.tencent.qt.base.net.NetworkEngine;
import com.tencent.qt.base.net.Request;
import java.util.Collection;
import java.util.List;

public abstract class BaseProxy<Rsp extends Message> {
    public static final long IDENTITY = 1111;
    private static final int MAX_RETRY_COUNT = 3;
    private static final int TIMEOUT = 5000;
    private static final int what_retry = 0;
    private String TAG = "BaseProxy";
    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    BaseProxy.this.sendRequest();
                    return;
                default:
                    return;
            }
        }
    };
    MessageListener messageListener;
    private int retryAuthorizeCount;
    protected final Class<Rsp> rspClass;

    public interface MessageListener {
        void onError(ProxyStatus proxyStatus, String str);

        void onSuccess(Object... objArr);

        void onTimeOut(Request request);
    }

    /* access modifiers changed from: protected */
    public abstract int getCommand();

    /* access modifiers changed from: protected */
    public abstract byte[] getRequestContent();

    /* access modifiers changed from: protected */
    public abstract int getSubcmd();

    /* access modifiers changed from: protected */
    public abstract void parseRspPB(Rsp rsp);

    /* access modifiers changed from: protected */
    public String getTAG() {
        return getClass().getSimpleName();
    }

    public BaseProxy(MessageListener messageListener2, Class<Rsp> rspClass2) {
        this.rspClass = rspClass2;
        prepareNetWorkState();
        this.messageListener = messageListener2;
    }

    private void prepareNetWorkState() {
        List accessHosts = IPManager.getInstance().getAccessHosts();
        NetworkEngine.shareEngine().setDefultkey(IDENTITY, MGCContext.getConnectionManager().getDefaultKey());
        String[] ips = new String[accessHosts.size()];
        int[] port = new int[accessHosts.size()];
        NetworkAddress.split(accessHosts, ips, port);
        LogUtil.i(this.TAG, "prepareAuthorize, host[0]=" + (CollectionUtils.isEmpty((Collection<?>) accessHosts) ? Constants.NULL_VERSION_ID : accessHosts.get(0)));
        NetworkEngine.shareEngine().setHosts(1, ips, port);
    }

    public void sendRequest() {
        try {
            ProtoFactory.ProtoRequest protoRequest = new ProtoFactory.ProtoRequest(getCommand(), getSubcmd(), getRequestContent());
            byte[] reserve = protoRequest.reverve;
            byte[] extra = protoRequest.extra;
            LogUtil.i(this.TAG, String.format("sendRequest: %04x,%02x", new Object[]{Integer.valueOf(getCommand()), Integer.valueOf(getSubcmd())}));
            if (NetworkEngine.shareEngine().sendRequest(1, getCommand(), getSubcmd(), getRequestContent(), reserve, extra, new MessageHandler() {
                public boolean match(int command, int subcmd, int seq) {
                    return false;
                }

                public void onMessage(Request request, com.tencent.qt.base.net.Message msg) {
                    try {
                        Rsp rsp = ProtoUtils.parseFrom(msg.payload, BaseProxy.this.rspClass);
                        BaseProxy.this.parseRspPB(rsp);
                        if (rsp == null) {
                            BaseProxy.this.handlerFailResult(ProxyStatus.ERROR_UNKNOW, (Object) null);
                        }
                    } catch (Exception e) {
                        BaseProxy.this.handlerFailResult(ProxyStatus.ERROR_SERVER, e.getMessage());
                    }
                }

                public void onTimeout(Request request) {
                    BaseProxy.this.handlerFailResult(ProxyStatus.TIMEOUT, request);
                }
            }, 5000) == -1) {
                handlerFailResult(ProxyStatus.ERROR_NETWORK, (Object) null);
            }
        } catch (Exception e) {
            handlerFailResult(ProxyStatus.ERROR_BUILD, e.getMessage());
        }
    }

    public enum ProxyStatus {
        ERROR_UNKNOW(-1),
        ERROR_NETWORK(-2),
        ERROR_SERVER(-3),
        TIMEOUT(-4),
        ERROR_BUILD(-5),
        PROXY_SUCCESS(0);
        
        private int errorCode;

        private ProxyStatus(int errorCode2) {
            this.errorCode = errorCode2;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }

    /* access modifiers changed from: protected */
    public void handlerFailResult(ProxyStatus status, Object param) {
        if (status == ProxyStatus.ERROR_NETWORK) {
            retryDelayIfNeed(1000);
        } else {
            retryDelayIfNeed(3000);
        }
        if (this.messageListener != null) {
            switch (status) {
                case ERROR_BUILD:
                    if (param != null && (param instanceof String)) {
                        String erro = (String) param;
                        this.messageListener.onError(ProxyStatus.ERROR_BUILD, erro);
                        LogUtil.e(this.TAG, getTAG() + " onMessage error: " + erro);
                        return;
                    }
                    return;
                case ERROR_NETWORK:
                    this.messageListener.onError(ProxyStatus.ERROR_NETWORK, (String) null);
                    LogUtil.e(this.TAG, getTAG() + " onMessage error: erroNetWork");
                    return;
                case TIMEOUT:
                    if (param != null && (param instanceof Request)) {
                        this.messageListener.onTimeOut((Request) param);
                    }
                    LogUtil.e(this.TAG, getTAG() + " onMessage error: Timeout");
                    return;
                case ERROR_UNKNOW:
                    this.messageListener.onError(ProxyStatus.ERROR_UNKNOW, "null result");
                    LogUtil.e(this.TAG, getTAG() + " onMessage error: null result");
                    return;
                case ERROR_SERVER:
                    if (param != null && (param instanceof String)) {
                        String erro2 = (String) param;
                        this.messageListener.onError(ProxyStatus.ERROR_SERVER, erro2);
                        LogUtil.e(this.TAG, getTAG() + " onMessage error: " + erro2);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void resetQueryCount() {
        this.retryAuthorizeCount = 0;
    }

    public void retryDelayIfNeed(int delayTime) {
        boolean needRetry;
        if (this.retryAuthorizeCount < 3) {
            needRetry = true;
        } else {
            needRetry = false;
        }
        LogUtil.i(this.TAG, "retryFromAuthorizeDelayIfNeed: needRetry=" + needRetry);
        if (needRetry) {
            this.retryAuthorizeCount++;
            this.mainHandler.sendEmptyMessageDelayed(0, (long) delayTime);
        }
    }
}
