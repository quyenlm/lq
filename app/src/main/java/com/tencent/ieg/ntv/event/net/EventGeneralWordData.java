package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;

public class EventGeneralWordData extends Event {
    private static final String TAG = EventGeneralWordData.class.getSimpleName();
    public List<String> generalWordList;

    public boolean parse(JSONArray arr) {
        if (arr != null) {
            try {
                this.generalWordList = new LinkedList();
                for (int i = 0; i < arr.length(); i++) {
                    this.generalWordList.add(arr.getString(i));
                }
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
                this.generalWordList = null;
            }
        }
        return false;
    }
}
