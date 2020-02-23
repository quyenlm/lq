package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventPageInfoData extends Event {
    private static final String TAG = EventPageInfoData.class.getSimpleName();
    public List<PageInfo> pageInfoList;

    public static class PageInfo {
        public boolean isDefault;
        public String name;
        public RedDotInfo redDotInfo;
        public String type;
        public String url;
    }

    public static class PageType {
        public static final String TYPE_TVPLAYER = "tvplayer";
        public static final String TYPE_WEBVIEW = "webview";
    }

    public boolean parse(JSONArray arr) {
        PageInfo pageInfo;
        boolean z;
        if (arr != null) {
            try {
                this.pageInfoList = new LinkedList();
                boolean hasDefault = false;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject pageObj = arr.getJSONObject(i);
                    PageInfo pageInfo2 = new PageInfo();
                    pageInfo2.url = pageObj.optString("url");
                    pageInfo2.name = pageObj.getString("name");
                    pageInfo2.type = pageObj.getString("type");
                    if (pageObj.optInt("default") == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    pageInfo2.isDefault = z;
                    if (pageInfo2.isDefault) {
                        hasDefault = true;
                    }
                    pageInfo2.redDotInfo = new RedDotInfo();
                    pageInfo2.redDotInfo.parse(pageObj);
                    if (pageInfo2.type.equals(PageType.TYPE_TVPLAYER)) {
                        this.pageInfoList.add(0, pageInfo2);
                    } else {
                        this.pageInfoList.add(pageInfo2);
                    }
                }
                if (hasDefault) {
                    return true;
                }
                if (this.pageInfoList.size() <= 0 || (pageInfo = this.pageInfoList.get(0)) == null) {
                    return true;
                }
                pageInfo.isDefault = true;
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
            }
        }
        return false;
    }
}
