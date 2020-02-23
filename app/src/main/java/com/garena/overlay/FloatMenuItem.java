package com.garena.overlay;

import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import java.io.Serializable;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class FloatMenuItem implements Serializable {
    public int flag = 0;
    public String icon = "";
    public int priority = 0;
    public long reddotEndtime = 0;
    public long reddotStarttime = 0;
    public boolean showRedDot = false;
    public String title = "";
    public int type = 0;
    public String url = "";

    public FloatMenuItem() {
    }

    public FloatMenuItem(JSONObject json) {
        if (json != null) {
            this.title = json.optString("title");
            this.icon = json.optString("icon_url").trim();
            this.url = json.optString("link").trim();
            this.flag = json.optInt(DownloadDBHelper.FLAG);
            this.priority = json.optInt(LogFactory.PRIORITY_KEY);
            this.reddotStarttime = json.optLong("red_dot_start_time");
            this.reddotEndtime = json.optLong("red_dot_end_time");
        }
    }

    public boolean isForceEmbed() {
        return (this.flag & 1) > 0;
    }
}
