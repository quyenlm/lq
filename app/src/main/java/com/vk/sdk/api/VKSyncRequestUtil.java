package com.vk.sdk.api;

import android.support.annotation.NonNull;
import com.vk.sdk.api.VKRequest;

class VKSyncRequestUtil {
    VKSyncRequestUtil() {
    }

    private static class Listener extends VKRequest.VKRequestListener {
        /* access modifiers changed from: private */
        public volatile boolean isFinish = false;
        private VKRequest.VKRequestListener listener;
        /* access modifiers changed from: private */
        public final Object syncObj = new Object();

        public Listener(VKRequest.VKRequestListener listener2) {
            this.listener = listener2;
        }

        public void onComplete(VKResponse response) {
            synchronized (this.syncObj) {
                try {
                    this.listener.onComplete(response);
                } catch (Exception e) {
                }
                this.isFinish = true;
                this.syncObj.notifyAll();
            }
        }

        public void onError(VKError error) {
            synchronized (this.syncObj) {
                try {
                    this.listener.onError(error);
                } catch (Exception e) {
                }
                this.isFinish = true;
                this.syncObj.notifyAll();
            }
        }
    }

    public static void executeSyncWithListener(@NonNull VKRequest vkRequest, @NonNull VKRequest.VKRequestListener vkListener) {
        Listener listener = new Listener(vkListener);
        vkRequest.setUseLooperForCallListener(false);
        vkRequest.executeWithListener(listener);
        synchronized (listener.syncObj) {
            while (!listener.isFinish) {
                try {
                    listener.syncObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
