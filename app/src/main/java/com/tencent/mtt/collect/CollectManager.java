package com.tencent.mtt.collect;

import MTT.AddFrvInfo;
import MTT.AddFrvReq;
import MTT.EFvrFvrType;
import MTT.FrvUserBase;
import com.qq.taf.jce.PacketUtil;
import com.tencent.mtt.engine.http.HttpUtils;
import java.util.ArrayList;

public class CollectManager {
    private static final String SERVICE_NAME = "favorite";
    private static CollectManager mInstance = new CollectManager();
    private int mId = 0;

    public static CollectManager getInstane() {
        return mInstance;
    }

    private byte[] getPostData(int reqId, String servantName, String funcName, String reqParamName, Object reqData) {
        byte[] postData = null;
        try {
            PacketUtil pack = new PacketUtil();
            pack.setRequestId(reqId);
            pack.setServantName(servantName);
            pack.setFuncName(funcName);
            pack.put(reqParamName, reqData);
            byte[] postData2 = pack.encode();
            byte[] bArr = postData2;
            return postData2;
        } catch (Exception e) {
            byte[] bArr2 = postData;
            return null;
        }
    }

    public void addFavoriteUrl(String title, String url, String uin, String sid, int statistics) {
        FrvUserBase fub = new FrvUserBase();
        fub.sUin = uin;
        fub.sSID = sid;
        fub.eChannel = statistics;
        AddFrvInfo addFrvInfo = new AddFrvInfo();
        addFrvInfo.sTitle = title;
        addFrvInfo.sURL = url;
        ArrayList<AddFrvInfo> urlInfos = new ArrayList<>();
        urlInfos.add(addFrvInfo);
        AddFrvReq reqq = new AddFrvReq();
        reqq.fub = fub;
        reqq.eFvrType = EFvrFvrType.EFVRFVR_MHT.value();
        reqq.vURLInfo = urlInfos;
        int i = this.mId;
        this.mId = i + 1;
        HttpUtils.post(getPostData(i, SERVICE_NAME, "addFavorite", "req", reqq));
    }
}
