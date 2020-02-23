package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventRedDotInfoData extends Event {
    private static final String TAG = EventRedDotInfoData.class.getSimpleName();
    public List<RedDotInfo> redDotInfoList;

    public boolean parse(JSONArray arr) {
        if (arr != null) {
            try {
                this.redDotInfoList = new LinkedList();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    RedDotInfo redDotInfo = new RedDotInfo();
                    redDotInfo.parse(obj);
                    this.redDotInfoList.add(redDotInfo);
                }
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
            }
        }
        return false;
    }
}
