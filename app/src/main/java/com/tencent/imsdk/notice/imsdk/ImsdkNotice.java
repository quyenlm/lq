package com.tencent.imsdk.notice.imsdk;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.api.IMSDKApi;
import com.tencent.imsdk.notice.api.IMNoticeListener;
import com.tencent.imsdk.notice.base.IMNoticeBase;
import com.tencent.imsdk.notice.entity.IMNoticeResult;
import com.tencent.imsdk.sns.base.IMHttpClient;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.tool.etc.T;
import java.util.HashMap;
import org.json.JSONException;

public class ImsdkNotice extends IMNoticeBase {
    private static final int INTERVAL_REQUEST = 300000;
    private static final String NOTICE_HOST_NAME = "com.tencent.imsdk.notice.SdkServer";
    private static final String NOTICE_INTERVAL_REQUEST = "com.tencent.imsdk.notice.IntervalRequest";
    protected static long mLastRequestStartTime = 0;
    private Context mContext;
    public String mGetNoticeUrl = "/notice/getNotice";
    private IMHttpClient mHttpClient;
    private HashMap<String, String> mLastRequestParams = new HashMap<>();
    /* access modifiers changed from: private */
    public IMNoticeCache mNoticeCache;
    /* access modifiers changed from: private */
    public IMNoticeListener mNoticeListener;
    /* access modifiers changed from: private */
    public IMNoticeResult mNoticeResult = null;

    public void init(Context con) {
        if (con != null && this.mContext == null) {
            this.mContext = con;
            this.mNoticeCache = new IMNoticeCache(con);
            IMConfig.initialize(con);
            this.mGetNoticeUrl = "/notice/getNotice";
            if (!"".equals(IMConfig.getSdkUrl(NOTICE_HOST_NAME))) {
                this.mGetNoticeUrl = IMConfig.getSdkUrl(NOTICE_HOST_NAME) + IMConfig.getSdkServerVersion() + this.mGetNoticeUrl;
            } else {
                this.mGetNoticeUrl = IMConfig.getSdkServerUrl() + this.mGetNoticeUrl;
            }
            IMLogger.d(this.mGetNoticeUrl);
            this.mHttpClient = new IMHttpClient();
        }
    }

    public void setListener(IMNoticeListener listener) {
        IMLogger.d("listener " + (listener == null ? " is null " : " is set"));
        this.mNoticeListener = listener;
    }

    private boolean isNewRequest(HashMap<String, String> params) {
        boolean isNewRequest = !this.mLastRequestParams.equals(params);
        if (isNewRequest) {
            this.mLastRequestParams.clear();
            this.mLastRequestParams.putAll(params);
        }
        return isNewRequest;
    }

    /* access modifiers changed from: private */
    public void errorCallback(int errorCode) {
        if (this.mNoticeResult == null) {
            this.mNoticeResult = new IMNoticeResult();
            this.mNoticeResult.imsdkRetCode = errorCode;
            this.mNoticeResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(errorCode);
        }
        if (this.mNoticeListener != null) {
            this.mNoticeListener.onLoadNoticeCallback(this.mNoticeResult);
        }
        mLastRequestStartTime = 0;
    }

    public void loadNoticeData(String version, String language, int region, int partition, boolean isUseCache, int noticeType, String extraJson) {
        IMLoginResult loginResult = IMSDKApi.Login.getLoginResult();
        IMLogger.d("Url = " + this.mGetNoticeUrl);
        HashMap<String, String> params = new HashMap<>();
        params.put("sLang", language);
        params.put("sVersion", version);
        params.put("iChannel", loginResult != null ? String.valueOf(loginResult.channelId) : "");
        params.put("iRegion", String.valueOf(region));
        params.put("iPartition", String.valueOf(partition));
        params.put("sExtra", extraJson);
        if (noticeType == 2) {
            params.put("iOpenid", loginResult != null ? loginResult.openId : "");
        } else if (noticeType == 1) {
            params.put("sGuestId", DeviceInfoUtils.getGuestId(this.mContext));
        }
        int intervalRequest = 300000;
        String intervalRequestTmp = MetaDataUtil.readMetaDataFromApplication(this.mContext, NOTICE_INTERVAL_REQUEST);
        if (!T.ckIsEmpty(intervalRequestTmp)) {
            try {
                intervalRequest = Integer.valueOf(intervalRequestTmp).intValue();
            } catch (ClassCastException ex) {
                IMLogger.w(ex.getMessage());
            }
        }
        if (isNewRequest(params) || mLastRequestStartTime == 0 || System.currentTimeMillis() - mLastRequestStartTime >= ((long) intervalRequest)) {
            IMLogger.d("init notice request start time ...");
            mLastRequestStartTime = System.currentTimeMillis();
            final boolean z = isUseCache;
            this.mHttpClient.get(this.mGetNoticeUrl, params, new IMCallback<String>() {
                public void onSuccess(String result) {
                    try {
                        IMNoticeResult unused = ImsdkNotice.this.mNoticeResult = new IMNoticeResult(result);
                        if (ImsdkNotice.this.mNoticeResult.noticesList == null || ImsdkNotice.this.mNoticeResult.imsdkRetCode != 1) {
                            if (ImsdkNotice.this.mNoticeListener != null) {
                                ImsdkNotice.this.mNoticeListener.onLoadNoticeCallback(ImsdkNotice.this.mNoticeResult);
                            }
                            if (ImsdkNotice.this.mNoticeResult.imsdkRetCode == -802) {
                                ImsdkNotice.this.mNoticeCache.clearNoticePicOutOfDate(new String[0]);
                                return;
                            }
                            IMLogger.d("current return code is " + ImsdkNotice.this.mNoticeResult.imsdkRetCode);
                            ImsdkNotice.mLastRequestStartTime = 0;
                        } else if (!z || ImsdkNotice.this.mNoticeResult.noticesList == null) {
                            ImsdkNotice.this.mNoticeListener.onLoadNoticeCallback(ImsdkNotice.this.mNoticeResult);
                        } else {
                            ImsdkNotice.this.mNoticeCache.saveAndClearExpiredNoticePicture(ImsdkNotice.this.mNoticeResult, ImsdkNotice.this.mNoticeListener);
                        }
                    } catch (JSONException e) {
                        ImsdkNotice.this.errorCallback(11);
                    } catch (Exception ex) {
                        IMLogger.w(ex.getMessage());
                        ImsdkNotice.this.errorCallback(11);
                    }
                }

                public void onCancel() {
                    IMLogger.d("cancel action of get notice");
                    ImsdkNotice.this.errorCallback(2);
                }

                public void onError(IMException exception) {
                    IMLogger.e("exception occurred during get notice " + exception.getMessage());
                    ImsdkNotice.this.errorCallback(4);
                }
            });
            return;
        }
        IMLogger.d("last request : " + mLastRequestStartTime + " , " + (System.currentTimeMillis() - mLastRequestStartTime));
        if (this.mNoticeResult != null && this.mNoticeListener != null) {
            this.mNoticeListener.onLoadNoticeCallback(this.mNoticeResult);
        }
    }

    public void loadNoticeData(String noticeId, int loadDataType, String scene, int noticeType, String extraJson) {
        super.loadNoticeData(noticeId, loadDataType, scene, noticeType, extraJson);
    }
}
