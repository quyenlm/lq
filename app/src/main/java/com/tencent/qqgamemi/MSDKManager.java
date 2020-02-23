package com.tencent.qqgamemi;

import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;

public class MSDKManager {
    private static String TAG = "MSDKManager";
    private static final MSDKManager instance = new MSDKManager();
    private static String ticketCache = null;
    private String imsdkTicket = null;
    private int locationid = -1;
    private long openid = -1;

    public static MSDKManager getInstance() {
        return instance;
    }

    public void setLoginInfoWrapper(String imsdkTicket2, long openid2, int locationid2) {
        this.imsdkTicket = imsdkTicket2;
        this.openid = openid2;
        this.locationid = locationid2;
    }

    public MsdkLoginBean getMsdkLoginInfo() {
        MsdkLoginBean msdkLoginBean = new MsdkLoginBean();
        msdkLoginBean.imsdkTicket = this.imsdkTicket;
        msdkLoginBean.openid = this.openid;
        msdkLoginBean.locationid = this.locationid;
        return msdkLoginBean;
    }

    public boolean refreshMSDKTicket(String imsdkTicket2, long openid2, int locationid2) {
        if (imsdkTicket2 == null || openid2 == -1 || locationid2 == -1) {
            return false;
        }
        String ticket = String.format("imsdkTicket:%s,openid:%d,locationid:%d", new Object[]{imsdkTicket2, Long.valueOf(openid2), Integer.valueOf(locationid2)});
        if (ticketCache == null || !ticketCache.equals(ticket)) {
            LogUtil.i(TAG, ticket);
            setLoginInfoWrapper(imsdkTicket2, openid2, locationid2);
            ticketCache = ticket;
            return true;
        }
        LogUtil.i(TAG, "ticket is same !");
        return false;
    }

    public class MsdkLoginBean {
        public String imsdkTicket = "";
        public int locationid = -1;
        public long openid = -1;

        public MsdkLoginBean() {
        }

        public boolean validated() {
            if (TextUtils.isEmpty(this.imsdkTicket) || this.openid == -1 || this.locationid == -1) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "MsdkLoginBean{imsdkTicket='" + this.imsdkTicket + '\'' + ", openid=" + this.openid + ", locationid=" + this.locationid + '}';
        }
    }
}
