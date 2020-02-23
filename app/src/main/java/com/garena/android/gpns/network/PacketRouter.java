package com.garena.android.gpns.network;

import com.garena.android.gpns.network.tcp.TCPPacket;
import com.garena.android.gpns.processor.AbstractProcessor;
import com.garena.android.gpns.processor.PushIdResponseProcessor;
import com.garena.android.gpns.processor.PushMsgArrivedProcessor;
import com.garena.android.gpns.processor.RegionResponseProcessor;
import com.garena.android.gpns.utility.AppLogger;
import java.util.HashMap;

public final class PacketRouter {
    private static HashMap<Integer, AbstractProcessor> mProcessorMap = new HashMap<>();

    public static void registerProcessors() {
        mProcessorMap.clear();
        register(new RegionResponseProcessor());
        register(new PushIdResponseProcessor());
        register(new PushMsgArrivedProcessor());
    }

    private static void register(AbstractProcessor processor) {
        mProcessorMap.put(Integer.valueOf(processor.getCommand()), processor);
    }

    public static void route(TCPPacket packet) {
        byte[] data;
        AbstractProcessor processor = mProcessorMap.get(Integer.valueOf(packet.getCommand()));
        if (processor != null && (data = packet.getData()) != null) {
            try {
                processor.process(data, 0, data.length);
            } catch (Exception ex) {
                AppLogger.e((Throwable) ex);
            }
        }
    }

    private PacketRouter() {
    }
}
