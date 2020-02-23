package com.tencent.ieg.ntv.network;

import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.view.HolderBonusItemInfo;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RewardInfo {
    public static String KEY_ITEM_DESC = "desc";
    public static String KEY_ITEM_ID = "itemid";
    public static String KEY_ITEM_LIST = "m_astRewardItem";
    public static String KEY_ITEM_STATUS = "status";
    public static String KEY_ITEM_URL = "url";
    public static String KEY_ITEM_VIEW_TIME = "viewtime";
    private static final String TAG = RewardInfo.class.getSimpleName();
    public int iEndTime;
    public int iStartTime;
    public ArrayList<HolderBonusItemInfo> itemList;
    public short sItemNum;
    public short sResult;
    public short sRewardId;
    private String strItemList;

    public void parse(short result, short rewardId, short itemNum, int startTime, int endTime, String itemArrStr) {
        Logger.d(TAG, "initData - result:" + result + ", rewardId:" + rewardId + ", itemNum:" + itemNum + ", startTime:" + startTime + ", endTime:" + endTime + ", itemArrStr:" + itemArrStr);
        this.sResult = result;
        this.sRewardId = rewardId;
        this.sItemNum = itemNum;
        this.iStartTime = startTime;
        this.iEndTime = endTime;
        this.strItemList = itemArrStr;
        try {
            JSONObject jsonObject = new JSONObject(itemArrStr);
            if (jsonObject.has(KEY_ITEM_LIST)) {
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_ITEM_LIST);
                if (jsonArray != null) {
                    this.itemList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HolderBonusItemInfo hbi_info = new HolderBonusItemInfo();
                        JSONObject jobj = jsonArray.getJSONObject(i);
                        hbi_info.itemid = (short) jobj.getInt(KEY_ITEM_ID);
                        hbi_info.viewtime = (short) jobj.getInt(KEY_ITEM_VIEW_TIME);
                        hbi_info.itemName = jobj.getString(KEY_ITEM_DESC);
                        hbi_info.iconUrl = jobj.optString(KEY_ITEM_URL);
                        hbi_info.status = (short) jobj.getInt(KEY_ITEM_STATUS);
                        this.itemList.add(hbi_info);
                    }
                    return;
                }
                Logger.w(TAG, "initData - itemlist is null...");
                return;
            }
            Logger.w(TAG, "initData - itemlist is null.");
        } catch (Exception e) {
            Logger.w(TAG, "initData - " + e);
        }
    }

    public boolean equals(RewardInfo info) {
        if (info != null && info.sResult == this.sResult && info.sRewardId == this.sRewardId && info.sItemNum == this.sItemNum && info.iStartTime == this.iStartTime && info.iEndTime == this.iEndTime) {
            return info.strItemList.equals(this.strItemList);
        }
        return false;
    }
}
