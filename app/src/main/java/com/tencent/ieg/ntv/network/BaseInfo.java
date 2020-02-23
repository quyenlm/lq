package com.tencent.ieg.ntv.network;

import com.tencent.ieg.ntv.model.MatchInfoModel;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.utils.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaseInfo {
    private static final String TAG = BaseInfo.class.getSimpleName();
    private JSONArray mForcePopUpArray = new JSONArray();
    private boolean mForcePopUpUpdated = false;
    private JSONArray mGeneralWordArray = new JSONArray();
    private boolean mGeneralWordUpdated = false;
    private JSONArray mPageInfoArray = new JSONArray();
    private boolean mPageInfoUpdated = false;
    private JSONObject mPlayInfoObject = new JSONObject();
    private boolean mPlayInfoUpdated = false;
    private JSONArray mRedDotInfoArray = new JSONArray();
    private boolean mRedDotInfoUpdated = false;

    private static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public boolean parse(String jsonString) {
        try {
            JSONObject rootJsonObject = new JSONObject(jsonString);
            if (rootJsonObject.has("page_tag")) {
                this.mPageInfoUpdated = false;
                JSONArray arr = rootJsonObject.getJSONArray("page_tag");
                if (!this.mPageInfoArray.equals(arr)) {
                    this.mPageInfoUpdated = true;
                    this.mPageInfoArray = arr;
                }
            }
            if (rootJsonObject.has("general_word")) {
                this.mGeneralWordUpdated = false;
                JSONArray arr2 = rootJsonObject.getJSONArray("general_word");
                if (!this.mGeneralWordArray.equals(arr2)) {
                    this.mGeneralWordUpdated = true;
                    this.mGeneralWordArray = arr2;
                }
            }
            if (rootJsonObject.has(RedDotInfo.RedDotType.TYPE_MATCHINFO)) {
                JSONArray arr3 = rootJsonObject.getJSONArray(RedDotInfo.RedDotType.TYPE_MATCHINFO);
                JSONArray oldArr = MatchInfoModel.getInstance().getMatchInfoArray();
                MatchInfoModel.getInstance().setUpdatabled(false);
                if (oldArr == null || !oldArr.equals(arr3)) {
                    MatchInfoModel.getInstance().setUpdatabled(true);
                    MatchInfoModel.getInstance().setMatchInfoArray(arr3);
                }
            }
            if (rootJsonObject.has("force_pop_up")) {
                this.mForcePopUpUpdated = false;
                JSONArray arr4 = rootJsonObject.getJSONArray("force_pop_up");
                if (!this.mForcePopUpArray.equals(arr4) || arr4 == null || (arr4 != null && arr4.length() == 0)) {
                    this.mForcePopUpUpdated = true;
                    this.mForcePopUpArray = arr4;
                }
            } else {
                this.mForcePopUpUpdated = true;
                this.mForcePopUpArray = new JSONArray();
            }
            if (rootJsonObject.has("play_info")) {
                this.mPlayInfoUpdated = false;
                JSONObject obj = rootJsonObject.getJSONObject("play_info");
                if (!this.mPlayInfoObject.equals(obj)) {
                    this.mPlayInfoUpdated = true;
                    this.mPlayInfoObject = obj;
                }
            }
            if (!rootJsonObject.has("red_dot")) {
                return true;
            }
            this.mRedDotInfoUpdated = false;
            JSONArray arr5 = rootJsonObject.getJSONArray("red_dot");
            if (this.mRedDotInfoArray.equals(arr5)) {
                return true;
            }
            this.mRedDotInfoUpdated = true;
            this.mRedDotInfoArray = arr5;
            return true;
        } catch (Exception e) {
            Logger.w(TAG, e);
            return false;
        }
    }

    public JSONArray getPageInfoArray() {
        return this.mPageInfoArray;
    }

    public JSONArray getGeneralWordArray() {
        return this.mGeneralWordArray;
    }

    public JSONArray getForcePopUpArray() {
        return this.mForcePopUpArray;
    }

    public JSONObject getPlayInfoObject() {
        return this.mPlayInfoObject;
    }

    public JSONArray getRedDotInfoArray() {
        return this.mRedDotInfoArray;
    }

    public boolean isPageInfoUpdated() {
        return this.mPageInfoUpdated;
    }

    public boolean isGeneralWordUpdated() {
        return this.mGeneralWordUpdated;
    }

    public boolean isForcePopUpUpdated() {
        return this.mForcePopUpUpdated;
    }

    public boolean isPlayInfoUpdated() {
        return this.mPlayInfoUpdated;
    }

    public boolean isRedDotInfoUpdated() {
        return this.mRedDotInfoUpdated;
    }

    public void setPageInfoUpdated(boolean updated) {
        this.mPageInfoUpdated = updated;
    }

    public void setGeneralWordUpdated(boolean updated) {
        this.mGeneralWordUpdated = updated;
    }

    public void setForcePopUpUpdated(boolean updated) {
        this.mForcePopUpUpdated = updated;
    }

    public void setPlayInfoUpdated(boolean updated) {
        this.mPlayInfoUpdated = updated;
    }

    public void setRedDotInfoUpdated(boolean updated) {
        this.mRedDotInfoUpdated = updated;
    }
}
