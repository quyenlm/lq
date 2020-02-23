package com.tencent.midas.oversea.data.mp;

import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APTypeChange;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APMPSendInfo {
    private static APMPSendInfo c = null;
    private ArrayList<APMPCompleteSendItem> a;
    private APMPCompleteSendItem b;
    private boolean d;
    private boolean e;
    private boolean f;
    private String g;
    private String h;

    private APMPSendInfo() {
        this.a = null;
        this.b = null;
        this.d = false;
        this.e = false;
        this.f = false;
        this.h = "";
        this.a = new ArrayList<>();
    }

    private int a(int i, ArrayList<APMPCompleteSendItem> arrayList) {
        APMPSendItem levelType = getLevelType(i, this.a);
        if (levelType != null) {
            return levelType.sendGames.getRealSendGamesNum(i);
        }
        return 0;
    }

    private APMPCompleteSendItem a(JSONObject jSONObject) {
        JSONArray jSONArray;
        APMPCompleteSendItem aPMPCompleteSendItem = new APMPCompleteSendItem();
        try {
            APMPSendItem b2 = b(jSONObject);
            b2.sectionOrPointFlag = 1;
            aPMPCompleteSendItem.sectionSend = b2;
            if (jSONObject.has("single_ex") && (jSONArray = jSONObject.getJSONArray("single_ex")) != null) {
                APMPSendItem b3 = b(jSONArray.getJSONObject(0));
                b3.sectionOrPointFlag = 2;
                aPMPCompleteSendItem.pointSend = b3;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return aPMPCompleteSendItem;
    }

    private String a(String str) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("0.00");
        return decimalFormat.format((double) (Float.valueOf(str).floatValue() / 100.0f));
    }

    private String a(ArrayList<APMPGoodsItem> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        if (arrayList != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= arrayList.size()) {
                    break;
                }
                stringBuffer.append(arrayList.get(i2).name);
                stringBuffer.append(215);
                stringBuffer.append(arrayList.get(i2).num);
                stringBuffer.append(65292);
                i = i2 + 1;
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    private void a(APMPGroupBuyInfo aPMPGroupBuyInfo, JSONArray jSONArray, String str) {
        try {
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                if (str.equals(jSONObject.getString(DownloadDBHelper.FLAG))) {
                    APMPGroupBuyItem aPMPGroupBuyItem = new APMPGroupBuyItem();
                    aPMPGroupBuyItem.setActivitymeta(jSONObject.getString("activitymeta"));
                    aPMPGroupBuyItem.setActivityname(jSONObject.getString("activityname"));
                    aPMPGroupBuyItem.setCurrenttime(jSONObject.getString("currenttime"));
                    aPMPGroupBuyItem.setEndtime(jSONObject.getString("endtime"));
                    aPMPGroupBuyItem.setExtend(jSONObject.getString("extend"));
                    aPMPGroupBuyItem.setFlag(jSONObject.getString(DownloadDBHelper.FLAG));
                    aPMPGroupBuyItem.setGoodsmeta(jSONObject.getString("goodsmeta"));
                    aPMPGroupBuyItem.setGoodsname(jSONObject.getString("goodsname"));
                    aPMPGroupBuyItem.setImg(jSONObject.getString(IMFriendInfoExViber.IMG));
                    aPMPGroupBuyItem.setMinparticipants(jSONObject.getString("minparticipants"));
                    aPMPGroupBuyItem.setOriginalprice(a(jSONObject.getString("originalprice")));
                    aPMPGroupBuyItem.setParticipants(jSONObject.getString("participants"));
                    aPMPGroupBuyItem.setPrice(a(jSONObject.getString(FirebaseAnalytics.Param.PRICE)));
                    aPMPGroupBuyItem.setProductid(jSONObject.getString("productid"));
                    aPMPGroupBuyItem.setStarttime(jSONObject.getString("starttime"));
                    aPMPGroupBuyInfo.setRecGroupBuyItem(aPMPGroupBuyItem);
                    aPMPGroupBuyInfo.setShow("1");
                    return;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private boolean a(JSONArray jSONArray, String str) {
        try {
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                if (str.equals(((JSONObject) jSONArray.get(i)).getString(DownloadDBHelper.FLAG))) {
                    return true;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private int b(int i, ArrayList<APMPCompleteSendItem> arrayList) {
        int i2 = -1;
        if (arrayList == null) {
            return -1;
        }
        int i3 = 0;
        int i4 = -1;
        while (true) {
            int i5 = i2;
            if (i3 >= arrayList.size()) {
                return i5;
            }
            try {
                int i6 = arrayList.get(i3).sectionSend.sendGames.limitNum;
                if (i >= i6 && i6 > i4) {
                    i4 = i6;
                    i5 = i3;
                }
            } catch (Exception e2) {
            }
            i2 = i5;
            i3++;
        }
    }

    private APMPSendItem b(JSONObject jSONObject) {
        APMPSendItem aPMPSendItem = new APMPSendItem();
        APMPGamesItem aPMPGamesItem = new APMPGamesItem();
        try {
            if (jSONObject.has("num")) {
                aPMPGamesItem.limitNum = APTypeChange.StringToInt(jSONObject.getString("num"));
            }
            if (jSONObject.has("send_rate")) {
                aPMPGamesItem.sendRate = APTypeChange.StringToInt(jSONObject.getString("send_rate"));
            }
            if (jSONObject.has("send_num")) {
                aPMPGamesItem.sendNum = APTypeChange.StringToInt(jSONObject.getString("send_num"));
            }
            if (jSONObject.has("send_type")) {
                aPMPGamesItem.sendType = jSONObject.getString("send_type");
            }
            if (jSONObject.has("send_ext")) {
                aPMPGamesItem.sendExt = jSONObject.getString("send_ext");
            }
            aPMPSendItem.sendGames = aPMPGamesItem;
            if (jSONObject.has("ex_send")) {
                ArrayList<APMPGoodsItem> arrayList = new ArrayList<>();
                JSONArray jSONArray = jSONObject.getJSONArray("ex_send");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                    APMPGoodsItem aPMPGoodsItem = new APMPGoodsItem();
                    if (jSONObject2.has("name")) {
                        aPMPGoodsItem.name = jSONObject2.getString("name");
                    }
                    if (jSONObject2.has("num")) {
                        aPMPGoodsItem.num = jSONObject2.getString("num");
                    }
                    arrayList.add(aPMPGoodsItem);
                }
                aPMPSendItem.sendGoodsList = arrayList;
            }
        } catch (Exception e2) {
            APLog.w("analyzeSectionOrSingleMpItem", e2.toString());
        }
        return aPMPSendItem;
    }

    public static synchronized APMPSendInfo getInstance() {
        APMPSendInfo aPMPSendInfo;
        synchronized (APMPSendInfo.class) {
            if (c == null) {
                c = new APMPSendInfo();
            }
            aPMPSendInfo = c;
        }
        return aPMPSendInfo;
    }

    public void analyzeGroupBuyInfo(JSONObject jSONObject) {
        this.g = jSONObject.toString();
        APMPGroupBuyInfo instance = APMPGroupBuyInfo.getInstance();
        instance.clear();
        APCountDown.release();
        if (jSONObject.has("tuan_mpinfo")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("tuan_mpinfo");
            instance.setMallUrl(jSONObject2.getString("mall_url"));
            instance.setResultUrl(jSONObject2.getString("result_url"));
            instance.setActsetId(jSONObject2.getString("actset_id"));
            if (jSONObject2.getJSONArray(HttpRequestParams.NOTICE_LIST).length() > 0) {
                instance.setShow("1");
            } else {
                instance.setShow("0");
            }
        } else {
            instance.setShow("0");
        }
    }

    public void analyzeJson(String str) {
        this.g = str;
        if (this.a != null) {
            this.a.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        this.h = "";
        this.d = false;
        this.e = false;
        this.f = false;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("first_mpinfo")) {
                    this.b = a(jSONObject.getJSONObject("first_mpinfo"));
                    if (getMPType(this.b).getIsHasSend()) {
                        this.d = true;
                    } else {
                        this.d = false;
                    }
                }
                if (jSONObject.has("utp_mpinfo")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("utp_mpinfo");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        APMPCompleteSendItem a2 = a(jSONArray.getJSONObject(i));
                        APMPSendItem mPType = getMPType(a2);
                        if (mPType.getIsHasSend()) {
                            this.e = true;
                        }
                        if (mPType.getIsHasSendGoods()) {
                            this.f = true;
                        }
                        this.a.add(a2);
                    }
                }
                parseGroupBuyInfo(jSONObject);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void analyzeSimpleMpJson(JSONObject jSONObject) {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        this.d = false;
        this.e = false;
        try {
            int i = jSONObject.getInt("firstsave_present_count");
            if (i > 0) {
                APMPGamesItem aPMPGamesItem = new APMPGamesItem();
                aPMPGamesItem.limitNum = 0;
                aPMPGamesItem.sendNum = i;
                aPMPGamesItem.sendType = "2";
                this.b = new APMPCompleteSendItem();
                this.b.sectionSend = new APMPSendItem();
                this.b.sectionSend.sectionOrPointFlag = 1;
                this.b.sectionSend.sendGames = aPMPGamesItem;
                this.d = true;
            }
            JSONArray jSONArray = new JSONArray(jSONObject.getString("present_level"));
            for (int i2 = 0; i2 < jSONArray.length() / 2; i2++) {
                APMPCompleteSendItem aPMPCompleteSendItem = new APMPCompleteSendItem();
                APMPGamesItem aPMPGamesItem2 = new APMPGamesItem();
                aPMPGamesItem2.limitNum = jSONArray.getInt(i2 * 2);
                aPMPGamesItem2.sendNum = jSONArray.getInt((i2 * 2) + 1);
                aPMPGamesItem2.sendType = "2";
                aPMPCompleteSendItem.sectionSend = new APMPSendItem();
                aPMPCompleteSendItem.sectionSend.sectionOrPointFlag = 1;
                aPMPCompleteSendItem.sectionSend.sendGames = aPMPGamesItem2;
                this.a.add(aPMPCompleteSendItem);
                this.e = true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public APMPCompleteSendItem getFirstMpInfo() {
        return this.b;
    }

    public String getFirstMpInfo(String str) {
        if (!TextUtils.isEmpty(this.h)) {
            return this.h;
        }
        APMPSendItem mPType = getMPType(this.b);
        StringBuffer stringBuffer = new StringBuffer();
        if (mPType.sendGames != null && APDataInterface.singleton().getUserInfo().isFirstCharge) {
            stringBuffer.append("首次充值");
            if (mPType.sendGames.limitNum > 0) {
                stringBuffer.append(mPType.sendGames.limitNum);
            } else {
                stringBuffer.append("任意数量");
            }
            stringBuffer.append(str);
            stringBuffer.append(",额外送");
            if (mPType.sendGames.getSendGamesNum() > 0) {
                stringBuffer.append(mPType.sendGames.getSendGamesNum());
                stringBuffer.append(str);
            }
            if (mPType.sendGoodsList != null && mPType.sendGoodsList.size() > 0) {
                if (mPType.sendGames.getSendGamesNum() > 0) {
                    stringBuffer.append('+');
                }
                stringBuffer.append(getFirstSendGoods());
            }
        }
        return stringBuffer.toString();
    }

    public int getFirstMpSendGameNum(int i) {
        if (this.b == null) {
            return 0;
        }
        int i2 = (this.b.sectionSend == null || this.b.sectionSend.sendGames == null) ? 0 : this.b.sectionSend.sendGames.limitNum;
        if (i == ((this.b.pointSend == null || this.b.pointSend.sendGames == null) ? 0 : this.b.pointSend.sendGames.limitNum)) {
            return this.b.pointSend.getRealSendGamesNum(i);
        }
        if (i >= i2) {
            return this.b.sectionSend.getRealSendGamesNum(i);
        }
        return 0;
    }

    public APMPSendItem getFirstMpTypeByNum(int i, APMPCompleteSendItem aPMPCompleteSendItem) {
        int i2;
        int i3 = 0;
        APMPSendItem aPMPSendItem = aPMPCompleteSendItem.sectionSend;
        APMPSendItem aPMPSendItem2 = aPMPCompleteSendItem.pointSend;
        if (aPMPSendItem2 != null && aPMPSendItem == null) {
            try {
                i3 = aPMPSendItem2.sendGames.limitNum;
            } catch (Exception e2) {
            }
            if (i == i3) {
                return aPMPSendItem2;
            }
        } else if (aPMPSendItem2 == null && aPMPSendItem != null) {
            try {
                i3 = aPMPSendItem.sendGames.limitNum;
            } catch (Exception e3) {
            }
            if (i == i3) {
                return aPMPSendItem;
            }
        } else if (!(aPMPSendItem2 == null || aPMPSendItem == null)) {
            try {
                i2 = aPMPSendItem.sendGames.limitNum;
                try {
                    i3 = aPMPSendItem2.sendGames.limitNum;
                } catch (Exception e4) {
                }
            } catch (Exception e5) {
                i2 = 0;
            }
            if (i == i3 && i != i2) {
                return aPMPSendItem2;
            }
            if (i != i3 && i == i2) {
                return aPMPSendItem;
            }
            if (i == i3 && i == i2) {
                return getMPType(aPMPCompleteSendItem);
            }
        }
        return null;
    }

    public String getFirstSendGoods() {
        return a(getMPType(this.b).sendGoodsList);
    }

    public String getFirstSendGoods(int i) {
        int i2 = 0;
        int i3 = (this.b.sectionSend == null || this.b.sectionSend.sendGames == null) ? 0 : this.b.sectionSend.sendGames.limitNum;
        if (!(this.b.pointSend == null || this.b.pointSend.sendGames == null)) {
            i2 = this.b.pointSend.sendGames.limitNum;
        }
        if (i == i2) {
            return a(this.b.pointSend.sendGoodsList);
        }
        if (i >= i3) {
            return a(this.b.sectionSend.sendGoodsList);
        }
        return null;
    }

    public boolean getIsHasFirstMPInfo() {
        return this.d && APMidasPayAPI.singleton().mBuyType == 0;
    }

    public boolean getIsHasUptoNumMpMPInfo() {
        return this.e && APMidasPayAPI.singleton().mBuyType == 0;
    }

    public APMPSendItem getLevelType(int i, ArrayList<APMPCompleteSendItem> arrayList) {
        if (arrayList != null) {
            int i2 = 0;
            while (i2 < arrayList.size()) {
                APMPCompleteSendItem aPMPCompleteSendItem = arrayList.get(i2);
                try {
                    int i3 = (aPMPCompleteSendItem.sectionSend == null || aPMPCompleteSendItem.sectionSend.sendGames == null) ? 0 : aPMPCompleteSendItem.sectionSend.sendGames.limitNum;
                    int i4 = (aPMPCompleteSendItem.pointSend == null || aPMPCompleteSendItem.pointSend.sendGames == null) ? 0 : aPMPCompleteSendItem.pointSend.sendGames.limitNum;
                    if (i == i3 && i != i4) {
                        return aPMPCompleteSendItem.sectionSend;
                    }
                    if (i != i3 && i == i4) {
                        return aPMPCompleteSendItem.pointSend;
                    }
                    if (i == i3 && i == i4) {
                        return getMPType(aPMPCompleteSendItem);
                    }
                    if (i != i3 && i == i4) {
                    }
                    i2++;
                } catch (Exception e2) {
                    APLog.w("getLevelType", e2.toString());
                }
            }
            int b2 = b(i, arrayList);
            if (b2 >= 0 && b2 < arrayList.size()) {
                return arrayList.get(b2).sectionSend;
            }
        }
        return null;
    }

    public String getMPJson() {
        return this.g;
    }

    public APMPSendItem getMPType(APMPCompleteSendItem aPMPCompleteSendItem) {
        if (aPMPCompleteSendItem.pointSend == null && aPMPCompleteSendItem.sectionSend == null) {
            return null;
        }
        if (aPMPCompleteSendItem.pointSend == null) {
            return aPMPCompleteSendItem.sectionSend;
        }
        if (aPMPCompleteSendItem.sectionSend == null) {
            return aPMPCompleteSendItem.pointSend;
        }
        String str = "";
        try {
            if (aPMPCompleteSendItem.sectionSend.sendGames != null && !TextUtils.isEmpty(aPMPCompleteSendItem.sectionSend.sendGames.sendExt)) {
                str = aPMPCompleteSendItem.sectionSend.sendGames.sendExt;
            }
        } catch (Exception e2) {
        }
        String str2 = "";
        try {
            if (aPMPCompleteSendItem.pointSend.sendGames != null && !TextUtils.isEmpty(aPMPCompleteSendItem.pointSend.sendGames.sendExt)) {
                str2 = aPMPCompleteSendItem.pointSend.sendGames.sendExt;
            }
        } catch (Exception e3) {
        }
        return (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) ? (TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) ? (!TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? aPMPCompleteSendItem.pointSend : aPMPCompleteSendItem.pointSend : aPMPCompleteSendItem.sectionSend : aPMPCompleteSendItem.pointSend;
    }

    public String getMpTitle() {
        return this.h;
    }

    public int getSendGameLevelNum(int i) {
        APMPSendItem levelType = getLevelType(i, this.a);
        if (levelType != null) {
            return levelType.sendGames.limitNum;
        }
        return 0;
    }

    public int getSendTotalGameCoins(int i) {
        int uptoNumMpSendGameNum = getUptoNumMpSendGameNum(i);
        return APDataInterface.singleton().getUserInfo().isFirstCharge ? uptoNumMpSendGameNum + getFirstMpSendGameNum(i) : uptoNumMpSendGameNum;
    }

    public String getSendTotalGoods(int i) {
        String firstSendGoods = getFirstSendGoods(i);
        String uptoNumMpSendGoods = getUptoNumMpSendGoods(i);
        return (TextUtils.isEmpty(firstSendGoods) || !APDataInterface.singleton().getUserInfo().isFirstCharge) ? uptoNumMpSendGoods : !TextUtils.isEmpty(uptoNumMpSendGoods) ? firstSendGoods + "，" + uptoNumMpSendGoods : firstSendGoods;
    }

    public ArrayList<APMPGoodsItem> getTotalSendInfo() {
        APMPSendItem levelType;
        boolean z;
        ArrayList<APMPGoodsItem> arrayList = new ArrayList<>();
        int i = APMidasPayAPI.singleton().mBuyType;
        int StringToInt = APTypeChange.StringToInt(APPayMananger.singleton().getCurBaseRequest().saveValue);
        if (i != 0) {
            return arrayList;
        }
        int sendTotalGameCoins = getInstance().getSendTotalGameCoins(StringToInt);
        if (sendTotalGameCoins > 0) {
            APMPGoodsItem aPMPGoodsItem = new APMPGoodsItem();
            aPMPGoodsItem.num = String.valueOf(sendTotalGameCoins);
            arrayList.add(aPMPGoodsItem);
        }
        try {
            if (this.b != null) {
                APMPSendItem firstMpTypeByNum = getFirstMpTypeByNum(StringToInt, this.b);
                if (!(!APDataInterface.singleton().getUserInfo().isFirstCharge || firstMpTypeByNum == null || firstMpTypeByNum.sendGames == null)) {
                    if (firstMpTypeByNum.sectionOrPointFlag == 2) {
                        if (StringToInt == firstMpTypeByNum.sendGames.limitNum) {
                            for (int i2 = 0; i2 < firstMpTypeByNum.sendGoodsList.size(); i2++) {
                                APMPGoodsItem aPMPGoodsItem2 = new APMPGoodsItem();
                                aPMPGoodsItem2.name = firstMpTypeByNum.sendGoodsList.get(i2).name;
                                aPMPGoodsItem2.num = firstMpTypeByNum.sendGoodsList.get(i2).num;
                                arrayList.add(aPMPGoodsItem2);
                            }
                        }
                    } else if (StringToInt >= firstMpTypeByNum.sendGames.limitNum) {
                        for (int i3 = 0; i3 < firstMpTypeByNum.sendGoodsList.size(); i3++) {
                            APMPGoodsItem aPMPGoodsItem3 = new APMPGoodsItem();
                            aPMPGoodsItem3.name = firstMpTypeByNum.sendGoodsList.get(i3).name;
                            aPMPGoodsItem3.num = firstMpTypeByNum.sendGoodsList.get(i3).num;
                            arrayList.add(aPMPGoodsItem3);
                        }
                    }
                }
            }
            if (!(this.a == null || (levelType = getLevelType(StringToInt, this.a)) == null)) {
                for (int i4 = 0; i4 < levelType.sendGoodsList.size(); i4++) {
                    APMPGoodsItem aPMPGoodsItem4 = new APMPGoodsItem();
                    aPMPGoodsItem4.name = levelType.sendGoodsList.get(i4).name;
                    aPMPGoodsItem4.num = levelType.sendGoodsList.get(i4).num;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= arrayList.size()) {
                            z = false;
                            break;
                        }
                        APMPGoodsItem aPMPGoodsItem5 = arrayList.get(i5);
                        if (aPMPGoodsItem5.name.equals(aPMPGoodsItem4.name)) {
                            aPMPGoodsItem5.num = String.valueOf(APTypeChange.StringToInt(aPMPGoodsItem5.num) + APTypeChange.StringToInt(aPMPGoodsItem4.num));
                            z = true;
                            break;
                        }
                        i5++;
                    }
                    if (!z) {
                        arrayList.add(aPMPGoodsItem4);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<APMPCompleteSendItem> getUptoNumMpInfo() {
        return this.a;
    }

    public int getUptoNumMpSendGameNum(int i) {
        return a(i, this.a);
    }

    public String getUptoNumMpSendGoods(int i) {
        APMPSendItem levelType;
        if (this.a == null || (levelType = getLevelType(i, this.a)) == null) {
            return null;
        }
        return a(levelType.sendGoodsList);
    }

    public ArrayList<APMPGoodsItem> getUptoNumMpSendGoodsList(int i) {
        if (this.a != null) {
            return getLevelType(i, this.a).sendGoodsList;
        }
        return null;
    }

    public String getUptoNumSendInfo(int i, String str, int i2) {
        int uptoNumMpSendGameNum = getInstance().getUptoNumMpSendGameNum(i);
        String uptoNumMpSendGoods = getInstance().getUptoNumMpSendGoods(i);
        int sendGameLevelNum = getInstance().getSendGameLevelNum(i);
        if (sendGameLevelNum <= 0) {
            return "";
        }
        if (TextUtils.isEmpty(uptoNumMpSendGoods) && uptoNumMpSendGameNum <= 0) {
            return "";
        }
        String str2 = i2 == 2 ? "充值" + str + sendGameLevelNum + "，赠送" : "充值" + str + "满" + sendGameLevelNum + "，赠送";
        if (uptoNumMpSendGameNum <= 0) {
            return str2 + uptoNumMpSendGoods;
        }
        String str3 = str2 + str + "×" + uptoNumMpSendGameNum;
        return !TextUtils.isEmpty(uptoNumMpSendGoods) ? str3 + "，" + uptoNumMpSendGoods : str3;
    }

    public boolean isHasSecondMP(JSONObject jSONObject) {
        try {
            if (jSONObject.has("first_mpinfo_ex")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("first_mpinfo_ex");
                if (jSONObject2.has("type") && !TextUtils.isEmpty(jSONObject2.getString("type"))) {
                    return true;
                }
            }
            if (jSONObject.has("utp_mpinfo_ex")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("utp_mpinfo_ex");
                return jSONObject3.has("type") && !TextUtils.isEmpty(jSONObject3.getString("type"));
            }
        } catch (Exception e2) {
        }
    }

    public boolean isHasUptoSendGoods() {
        return this.f;
    }

    public boolean isHashSend() {
        if (APMidasPayAPI.singleton().mBuyType == 0) {
            String str = APPayMananger.singleton().getCurBaseRequest().saveValue;
            int sendTotalGameCoins = getInstance().getSendTotalGameCoins(APTypeChange.StringToInt(str));
            String sendTotalGoods = getInstance().getSendTotalGoods(APTypeChange.StringToInt(str));
            if (sendTotalGameCoins > 0 || !TextUtils.isEmpty(sendTotalGoods)) {
                return true;
            }
        }
        return false;
    }

    public void parseGroupBuyInfo(JSONObject jSONObject) {
        APMPGroupBuyInfo instance = APMPGroupBuyInfo.getInstance();
        instance.clear();
        APCountDown.release();
        try {
            if (jSONObject.has("tuan_mpinfo")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("tuan_mpinfo");
                instance.setMallUrl(jSONObject2.getString("mall_url"));
                instance.setResultUrl(jSONObject2.getString("result_url"));
                instance.setActsetId(jSONObject2.getString("actset_id"));
                JSONArray jSONArray = jSONObject2.getJSONArray(HttpRequestParams.NOTICE_LIST);
                if (a(jSONArray, "1")) {
                    a(instance, jSONArray, "1");
                } else if (a(jSONArray, "2")) {
                    a(instance, jSONArray, "2");
                } else if (a(jSONArray, UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR)) {
                    a(instance, jSONArray, UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                } else {
                    instance.setShow("0");
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void parseMpTitle(JSONObject jSONObject) {
        this.h = "";
        this.g = jSONObject.toString();
        APLog.i("APMPSendInfo", "parseMpTitle :" + this.g);
        try {
            if (jSONObject.has("mpinfo_ex")) {
                String string = jSONObject.getJSONObject("mpinfo_ex").getString("title");
                if (!TextUtils.isEmpty(string)) {
                    this.h = string;
                    return;
                }
            }
            if (jSONObject.has("first_mpinfo_ex")) {
                String string2 = jSONObject.getJSONObject("first_mpinfo_ex").getString("title");
                if (!TextUtils.isEmpty(string2)) {
                    this.h = string2;
                    return;
                }
            }
            if (jSONObject.has("acc_mpinfo_ex")) {
                String string3 = jSONObject.getJSONObject("acc_mpinfo_ex").getString("title");
                if (!TextUtils.isEmpty(string3)) {
                    this.h = string3;
                    return;
                }
            }
            if (jSONObject.has("utp_mpinfo_ex")) {
                String string4 = jSONObject.getJSONObject("utp_mpinfo_ex").getString("title");
                if (!TextUtils.isEmpty(string4)) {
                    this.h = string4;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setFirstMpInfo(APMPCompleteSendItem aPMPCompleteSendItem) {
        this.b = aPMPCompleteSendItem;
    }

    public void setUptoNumMpMpInfo(ArrayList<APMPCompleteSendItem> arrayList) {
        this.a = arrayList;
    }
}
