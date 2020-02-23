package com.beetalk.sdk.cache;

import android.content.Context;
import com.beetalk.sdk.GGLoginSession;
import com.tencent.smtt.sdk.TbsDownloadConfig;

public class RedeemCache {
    private static final String KEY_LAST_REDEEM_TIME = "KEY_LAST_REDEEM_TIME";
    private static final long MIN_REDEEM_INTERVAL = 28800000;
    private static final int SERVER_RESET_HOUR = 19;
    private RedeemStorage storage = new RedeemStorage();

    private class RedeemStorage extends BBBaseSharedPreference {
        private RedeemStorage() {
        }

        /* access modifiers changed from: protected */
        public Context __getApplicationContext() {
            return GGLoginSession.getApplicationContext();
        }
    }

    public void setRedeemTime() {
        this.storage._setLong(KEY_LAST_REDEEM_TIME, System.currentTimeMillis());
    }

    public void clearRedeemCache() {
        this.storage._setLong(KEY_LAST_REDEEM_TIME, -1);
    }

    public boolean isOkForRedeem() {
        long lastRedeemTime = this.storage._getLong(KEY_LAST_REDEEM_TIME, 0);
        return isPassThroughResetSinceLastRedeem(lastRedeemTime) || lastRedeemTime == 0 || System.currentTimeMillis() - lastRedeemTime > MIN_REDEEM_INTERVAL;
    }

    private boolean isPassThroughResetSinceLastRedeem(long lastRedeemTime) {
        long serverResetTime;
        if (getHourFromSystemTime() >= 19) {
            serverResetTime = getTodaysServerResetTime();
        } else {
            serverResetTime = getPreviousDaysServerResetTime();
        }
        return lastRedeemTime <= serverResetTime || lastRedeemTime >= System.currentTimeMillis();
    }

    private int getHourFromSystemTime() {
        return (((int) (System.currentTimeMillis() / 1000)) % 86400) / 3600;
    }

    private long getTodaysServerResetTime() {
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        return ((currentTimeSeconds - (currentTimeSeconds % TbsDownloadConfig.DEFAULT_RETRY_INTERVAL_SEC)) * 1000) + 68400000;
    }

    private long getPreviousDaysServerResetTime() {
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        return (((currentTimeSeconds - (currentTimeSeconds % TbsDownloadConfig.DEFAULT_RETRY_INTERVAL_SEC)) - TbsDownloadConfig.DEFAULT_RETRY_INTERVAL_SEC) * 1000) + 68400000;
    }
}
