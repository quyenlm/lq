package com.tencent.ieg.ntv.network;

import com.tencent.ieg.ntv.utils.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class HeartBeatInfo {
    /* access modifiers changed from: private */
    public static final String TAG = HeartBeatInfo.class.getSimpleName();
    private static HeartBeat mHeartBeatInfo;
    private static HeartBeatInfoListener mHeartBeatInfoListener;

    public static class HeartBeat {
        public int iResult;
        public int iViewNum;
    }

    public interface HeartBeatInfoListener {
        void onHeartBeatInfo(HeartBeat heartBeat);
    }

    private static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public static void getHearBeatInfo(HeartBeatInfoListener listener) {
        if (mHeartBeatInfo != null) {
            listener.onHeartBeatInfo(mHeartBeatInfo);
        } else {
            mHeartBeatInfoListener = listener;
        }
    }

    public static boolean parse(int result, int num) {
        HeartBeat heartBeat = new HeartBeat();
        heartBeat.iResult = result;
        heartBeat.iViewNum = num;
        if (mHeartBeatInfoListener == null) {
            return true;
        }
        mHeartBeatInfoListener.onHeartBeatInfo(heartBeat);
        return true;
    }

    public static class HeartBeatLogInfo {
        private HashMap<String, Info> dataMap;

        public static class Ainfo {
            public String id;
            public int value;
        }

        private static class Info {
            public String id;
            public HashSet<Integer> mSet;
            public List<Integer> tList;

            private Info() {
            }
        }

        public void add(Ainfo info) {
            if (info != null) {
                Logger.d(HeartBeatInfo.TAG, "add LogInfo,info.id=" + info.id + ", info.value=" + info.value);
                if (!info.id.isEmpty() && info.value != 0) {
                    if (this.dataMap == null) {
                        this.dataMap = new HashMap<>();
                    }
                    if (!this.dataMap.containsKey(info.id)) {
                        Info tmpInfo = new Info();
                        tmpInfo.id = info.id;
                        if (info.id.equals("2")) {
                            tmpInfo.mSet = new HashSet<>();
                        } else {
                            tmpInfo.tList = new ArrayList();
                        }
                        this.dataMap.put(info.id, tmpInfo);
                    }
                    if (this.dataMap.containsKey(info.id)) {
                        Info tmpInfo2 = this.dataMap.get(info.id);
                        if (info.id.equals("2")) {
                            tmpInfo2.mSet.add(Integer.valueOf(info.value));
                        } else if (info.id.equals("0") || info.id.equals("1")) {
                            tmpInfo2.tList.add(Integer.valueOf(info.value));
                        } else if (tmpInfo2.tList.size() == 0) {
                            tmpInfo2.tList.add(Integer.valueOf(info.value));
                        }
                    }
                }
            }
        }

        public String toJSONString() {
            try {
                JSONArray jsonArray = new JSONArray();
                if (this.dataMap != null) {
                    for (String eventId : this.dataMap.keySet()) {
                        JSONObject object = new JSONObject();
                        Info info = this.dataMap.get(eventId);
                        object.put("id", info.id);
                        if (eventId.equals("2")) {
                            JSONArray tArray = new JSONArray();
                            Iterator<Integer> it = info.mSet.iterator();
                            while (it.hasNext()) {
                                tArray.put(it.next());
                            }
                            object.put("m", tArray);
                        } else {
                            JSONArray tArray2 = new JSONArray();
                            for (Integer value : info.tList) {
                                tArray2.put(value);
                            }
                            object.put("t", tArray2);
                        }
                        jsonArray.put(object);
                    }
                }
                return jsonArray.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
