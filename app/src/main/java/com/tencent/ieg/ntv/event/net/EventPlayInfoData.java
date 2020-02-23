package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventPlayInfoData extends Event {
    private static final String TAG = EventPlayInfoData.class.getSimpleName();
    public List<ChannelInfo> channelInfoList;
    public int status;
    public String subTitle;
    public String title;

    public static class ChannelInfo {
        public int defaultIndex;
        public String name;
        public List<PlayUrl> playUrlList;
    }

    public static class PlayUrl {
        public String name;
        public String url;
    }

    public boolean parse(JSONObject obj) {
        if (obj != null) {
            try {
                this.status = obj.getInt("status");
                this.title = obj.getString("title");
                this.subTitle = obj.getString("sub_title");
                this.channelInfoList = new LinkedList();
                JSONArray channelInfoArr = obj.getJSONArray("urls");
                if (channelInfoArr != null) {
                    for (int c = 0; c < channelInfoArr.length(); c++) {
                        JSONObject channelObj = channelInfoArr.getJSONObject(c);
                        ChannelInfo channelInfo = new ChannelInfo();
                        channelInfo.name = channelObj.getString("name");
                        channelInfo.defaultIndex = channelObj.getInt("default_play_url");
                        JSONArray playUrlList = channelObj.getJSONArray("play_url");
                        channelInfo.playUrlList = new LinkedList();
                        if (playUrlList != null) {
                            for (int u = 0; u < playUrlList.length(); u++) {
                                JSONObject urlObj = playUrlList.getJSONObject(u);
                                PlayUrl playUrl = new PlayUrl();
                                playUrl.name = urlObj.getString("name");
                                playUrl.url = urlObj.getString("url");
                                channelInfo.playUrlList.add(playUrl);
                            }
                        }
                        this.channelInfoList.add(channelInfo);
                    }
                }
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
            }
        }
        return false;
    }
}
