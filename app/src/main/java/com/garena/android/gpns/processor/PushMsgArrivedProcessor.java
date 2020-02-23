package com.garena.android.gpns.processor;

import com.garena.android.gpnprotocol.gpush.MsgType;
import com.garena.android.gpnprotocol.gpush.PushMsg;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.notification.event.NotifyEvent;
import com.garena.android.gpns.storage.LocalStorage;
import com.garena.android.gpns.ui.NotificationBroadcaster;
import com.garena.android.gpns.utility.CONSTANT;
import com.garena.android.gpns.utility.TCPPacketFactory;
import com.garena.android.gpns.utility.WireUtil;
import java.util.ArrayList;
import java.util.List;

public class PushMsgArrivedProcessor extends AbstractProcessor {
    public void process(byte[] data, int offset, int length) throws Exception {
        PushMsg message = WireUtil.parsePushMsg(data, offset, length);
        for (MsgType msg : filterMessagesToShow(message.Msg)) {
            NotificationBroadcaster.broadcast(GNotificationService.getContext(), msg.Appid, msg.Data);
        }
        GNotificationService.getBus().fire(CONSTANT.BUS.ACK_PUSH_MSG, new NotifyEvent(TCPPacketFactory.newPushMessageAckPacket(message.Msg.get(0).Msgid.intValue())));
    }

    private List<MsgType> filterMessagesToShow(List<MsgType> msgList) {
        List<Integer> acknowledgedIdList = LocalStorage.getAcknowledgedMsgList();
        List<Integer> newAcknowledgeIdList = new ArrayList<>();
        List<MsgType> result = new ArrayList<>();
        for (MsgType msg : msgList) {
            if (!acknowledgedIdList.contains(msg.Msgid)) {
                result.add(msg);
            }
            newAcknowledgeIdList.add(msg.Msgid);
        }
        LocalStorage.putAcknowledgedMsgList(newAcknowledgeIdList);
        return result;
    }

    public int getCommand() {
        return 18;
    }
}
