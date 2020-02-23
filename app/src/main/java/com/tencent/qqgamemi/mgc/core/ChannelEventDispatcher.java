package com.tencent.qqgamemi.mgc.core;

import com.tencent.qqgamemi.mgc.eventbus.EventBus;
import com.tencent.qt.base.net.BroadcastHandler;
import com.tencent.qt.base.net.ChannelBroadcast;
import com.tencent.qt.base.net.Message;

public class ChannelEventDispatcher implements BroadcastHandler {
    public boolean match(int command, int subcmd, int seq) {
        return command == 65535 || command == 65536;
    }

    public void onBroadcast(Message msg) {
        if (msg.command == 65535) {
            onChannelBroadcast(msg);
        } else if (msg.command == 65536) {
            onNetworkBroadcast(msg);
        }
    }

    private void onNetworkBroadcast(Message msg) {
        if (msg.subcmd == 1) {
        }
    }

    private void onChannelBroadcast(Message msg) {
        if (((ChannelBroadcast) msg).getChannelType() == 0) {
            switch (msg.subcmd) {
                case 1:
                    broadcastEvent(1);
                    return;
                case 2:
                    broadcastEvent(2);
                    return;
                case 3:
                    broadcastEvent(3);
                    return;
                default:
                    return;
            }
        }
    }

    private void broadcastEvent(int eventType) {
        EventBus.getInstance().publish(new NetworkConnectEvent(eventType));
    }

    public static class NetworkConnectEvent {
        public static final int EVENT_TYPE_BREAKDONW = 3;
        public static final int EVENT_TYPE_CONNECTED = 1;
        public static final int EVENT_TYPE_DISCONNECTED = 2;
        private int eventType;

        public NetworkConnectEvent(int eventType2) {
            this.eventType = eventType2;
        }

        public int getEventType() {
            return this.eventType;
        }
    }
}
