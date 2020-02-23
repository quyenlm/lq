package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventForcePopUpData extends Event {
    private static final String TAG = EventForcePopUpData.class.getSimpleName();
    public List<ForcePopUpInfo> forcePopUpInfoList;
    public Object playInfo;

    public static class ForcePopUpInfo {
        public String bgImgUrl;
        public long endTime;
        public long startTime;
    }

    public boolean parse(JSONArray arr) {
        if (arr != null) {
            try {
                this.forcePopUpInfoList = new LinkedList();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject forcePopUpInfoObj = arr.getJSONObject(i);
                    ForcePopUpInfo forcePopUpInfo = new ForcePopUpInfo();
                    forcePopUpInfo.startTime = (long) forcePopUpInfoObj.getInt("start_time");
                    forcePopUpInfo.endTime = (long) forcePopUpInfoObj.getInt("end_time");
                    forcePopUpInfo.bgImgUrl = forcePopUpInfoObj.getString("img_url");
                    this.forcePopUpInfoList.add(forcePopUpInfo);
                }
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
                this.forcePopUpInfoList = null;
            }
        }
        return false;
    }
}
