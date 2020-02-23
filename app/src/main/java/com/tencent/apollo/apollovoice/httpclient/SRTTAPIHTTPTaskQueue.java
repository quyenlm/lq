package com.tencent.apollo.apollovoice.httpclient;

class SRTTAPIHTTPTaskQueue {
    private static String LOGTAG = "GCloudVoiceTag";
    private static SRTTAPIHTTPTaskQueueImp taskQueue = new SRTTAPIHTTPTaskQueueImp();

    SRTTAPIHTTPTaskQueue() {
    }

    public static void init() {
        taskQueue.init();
    }

    public static void setAppInfo(String key, String addr) {
        taskQueue.setAppInfo(key, addr);
    }

    public static synchronized void addTask(int type, int method, String key, byte[] body, int session) {
        synchronized (SRTTAPIHTTPTaskQueue.class) {
            taskQueue.addTask(type, method, key, body, session);
        }
    }
}
