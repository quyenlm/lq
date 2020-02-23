package com.tencent.ieg.ntv.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import org.json.JSONObject;

public class RedDotInfo {
    private static final String TAG = RedDotInfo.class.getSimpleName();
    private long mEnd;
    private int mId;
    private long mStart;
    private String mType;

    public static class RedDotType {
        public static final String TYPE_MATCHINFO = "match_info";
        public static final String TYPE_PAGE = "webview";
    }

    public String getType() {
        return this.mType;
    }

    public int getId() {
        return this.mId;
    }

    public long getStart() {
        return this.mStart;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public boolean parse(JSONObject obj) {
        try {
            this.mType = obj.getString("type");
            this.mId = obj.optInt("id");
            if (obj.has("dot_start")) {
                this.mStart = obj.optLong("dot_start");
            } else {
                this.mStart = obj.optLong("start");
            }
            if (obj.has("dot_end")) {
                this.mEnd = obj.optLong("dot_end");
            } else {
                this.mEnd = obj.optLong("end");
            }
            return true;
        } catch (Exception e) {
            Logger.w(TAG, e);
            return false;
        }
    }

    public boolean shouldShowUnread() {
        long nowTime = Util.getCurrentMSeconds() / 1000;
        Logger.d(TAG, "shouldShowUnread(), start: " + this.mStart + ", end:" + this.mEnd + ", nowTime:" + nowTime);
        return nowTime >= this.mStart && nowTime < this.mEnd;
    }

    public boolean checkUnreadStatus(Context ctx) {
        if (ctx == null) {
            return false;
        }
        String itemId = getItemId();
        if (itemId.length() <= 0) {
            return false;
        }
        String sKey = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID) + itemId;
        String sValue = ctx.getSharedPreferences("dotdata", 0).getString(sKey, "");
        Logger.d(TAG, "checkUnreadStatus(), sKey: " + sKey + ", sValue: " + sValue);
        if (!sValue.equals(String.valueOf(this.mStart))) {
            return true;
        }
        return false;
    }

    public void clearUnreadStatus(Context ctx) {
        if (ctx != null) {
            String itemId = getItemId();
            if (itemId.length() > 0) {
                SharedPreferences sp = ctx.getSharedPreferences("dotdata", 0);
                String sKey = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID) + itemId;
                String sValue = String.valueOf(this.mStart);
                Logger.d(TAG, "clearUnreadStatus(), sKey: " + sKey + ", sValue: " + sValue);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(sKey, sValue);
                editor.commit();
            }
        }
    }

    private String getItemId() {
        if (this.mType.equals("webview")) {
            return "" + this.mId;
        }
        if (this.mType.equals(RedDotType.TYPE_MATCHINFO)) {
            return this.mType + this.mStart;
        }
        return "";
    }
}
