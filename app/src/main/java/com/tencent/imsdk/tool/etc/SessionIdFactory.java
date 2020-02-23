package com.tencent.imsdk.tool.etc;

import java.util.Random;

public class SessionIdFactory {
    private static volatile SessionIdFactory instance = null;
    private static String sessionId;

    private SessionIdFactory() {
    }

    @Deprecated
    public static SessionIdFactory getInstance() {
        if (instance == null) {
            synchronized (SessionIdFactory.class) {
                if (instance == null) {
                    instance = new SessionIdFactory();
                    sessionId = createSessionId();
                }
            }
        }
        return instance;
    }

    public String getSessionId() {
        return sessionId;
    }

    private static String createSessionId() {
        String startTime = DeviceInfoUtils.getNowTime();
        return startTime + "" + (new Random().nextInt(900) + 100);
    }
}
