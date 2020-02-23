package com.tencent.imsdk.tool.net;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

class RetryHandler implements HttpRequestRetryHandler {
    private static final HashSet<Class<?>> exceptionBlacklist = new HashSet<>();
    private static final HashSet<Class<?>> exceptionWhitelist = new HashSet<>();
    private final int maxRetries;
    private final int retrySleepTimeMS;

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
    }

    public RetryHandler(int maxRetries2, int retrySleepTimeMS2) {
        this.maxRetries = maxRetries2;
        this.retrySleepTimeMS = retrySleepTimeMS2;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean sent;
        boolean retry = true;
        Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        if (b == null || !b.booleanValue()) {
            sent = false;
        } else {
            sent = true;
        }
        if (executionCount > this.maxRetries) {
            retry = false;
        } else if (isInList(exceptionWhitelist, exception)) {
            retry = true;
        } else if (isInList(exceptionBlacklist, exception)) {
            retry = false;
        } else if (!sent) {
            retry = true;
        }
        if (retry && ((HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST)) == null) {
            return false;
        }
        if (retry) {
            SystemClock.sleep((long) this.retrySleepTimeMS);
        } else {
            exception.printStackTrace();
        }
        return retry;
    }

    static void addClassToWhitelist(Class<?> cls) {
        exceptionWhitelist.add(cls);
    }

    static void addClassToBlacklist(Class<?> cls) {
        exceptionBlacklist.add(cls);
    }

    /* access modifiers changed from: protected */
    public boolean isInList(HashSet<Class<?>> list, Throwable error) {
        Iterator<Class<?>> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isInstance(error)) {
                return true;
            }
        }
        return false;
    }
}
