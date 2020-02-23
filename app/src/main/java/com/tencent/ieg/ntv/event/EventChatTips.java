package com.tencent.ieg.ntv.event;

public class EventChatTips extends Event {
    public boolean show;
    public String tipText;

    public EventChatTips(boolean isShow, String tips) {
        this.show = isShow;
        this.tipText = tips;
    }
}
