package com.facebook.appevents.internal;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.math.BigDecimal;
import java.util.Currency;
import org.json.JSONException;
import org.json.JSONObject;

public class AutomaticAnalyticsLogger {
    private static final String INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    /* access modifiers changed from: private */
    public static final String TAG = AutomaticAnalyticsLogger.class.getCanonicalName();
    /* access modifiers changed from: private */
    @Nullable
    public static Object inAppBillingObj;

    public static void logActivateAppEvent() {
        Context context = FacebookSdk.getApplicationContext();
        String appId = FacebookSdk.getApplicationId();
        boolean autoLogAppEvents = FacebookSdk.getAutoLogAppEventsEnabled();
        Validate.notNull(context, "context");
        if (!autoLogAppEvents) {
            return;
        }
        if (context instanceof Application) {
            AppEventsLogger.activateApp((Application) context, appId);
        } else {
            Log.w(TAG, "Automatic logging of basic events will not happen, because FacebookSdk.getApplicationContext() returns object that is not instance of android.app.Application. Make sure you call FacebookSdk.sdkInitialize() from Application class and pass application context.");
        }
    }

    public static void logActivityTimeSpentEvent(String activityName, long timeSpentInSeconds) {
        Context context = FacebookSdk.getApplicationContext();
        String appId = FacebookSdk.getApplicationId();
        Validate.notNull(context, "context");
        FetchedAppSettings settings = FetchedAppSettingsManager.queryAppSettings(appId, false);
        if (settings != null && settings.getAutomaticLoggingEnabled() && timeSpentInSeconds > 0) {
            AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(context);
            Bundle params = new Bundle(1);
            params.putCharSequence(Constants.AA_TIME_SPENT_SCREEN_PARAMETER_NAME, activityName);
            appEventsLogger.logEvent(Constants.AA_TIME_SPENT_EVENT_NAME, (double) timeSpentInSeconds, params);
        }
    }

    public static boolean logInAppPurchaseEvent(final Context context, int resultCode, Intent data) {
        if (data == null || !isImplicitPurchaseLoggingEnabled()) {
            return false;
        }
        final String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
        if (resultCode != -1) {
            return true;
        }
        ServiceConnection serviceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Object unused = AutomaticAnalyticsLogger.inAppBillingObj = null;
                Utility.logd(AutomaticAnalyticsLogger.TAG, "In-app billing service disconnected");
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                Object unused = AutomaticAnalyticsLogger.inAppBillingObj = InAppPurchaseEventManager.getServiceInterface(context, service);
                try {
                    JSONObject purchaseDetails = new JSONObject(purchaseData);
                    String sku = purchaseDetails.getString(UnityPayHelper.PRODUCTID);
                    String skuDetails = InAppPurchaseEventManager.getPurchaseDetails(context, sku, AutomaticAnalyticsLogger.inAppBillingObj, purchaseDetails.has("autoRenewing"));
                    if (!skuDetails.equals("")) {
                        JSONObject jsonSkuDetails = new JSONObject(skuDetails);
                        AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(context);
                        Bundle params = new Bundle(1);
                        params.putCharSequence(Constants.IAP_PRODUCT_ID, sku);
                        params.putCharSequence(Constants.IAP_PURCHASE_TIME, purchaseDetails.getString("purchaseTime"));
                        params.putCharSequence(Constants.IAP_PURCHASE_STATE, purchaseDetails.getString("purchaseState"));
                        params.putCharSequence(Constants.IAP_PURCHASE_TOKEN, purchaseDetails.getString("purchaseToken"));
                        params.putCharSequence(Constants.IAP_PACKAGE_NAME, purchaseDetails.getString("packageName"));
                        params.putCharSequence(Constants.IAP_PRODUCT_TYPE, jsonSkuDetails.getString("type"));
                        params.putCharSequence(Constants.IAP_PRODUCT_TITLE, jsonSkuDetails.getString("title"));
                        params.putCharSequence(Constants.IAP_PRODUCT_DESCRIPTION, jsonSkuDetails.getString("description"));
                        params.putCharSequence(Constants.IAP_SUBSCRIPTION_AUTORENEWING, Boolean.toString(purchaseDetails.optBoolean("autoRenewing", false)));
                        params.putCharSequence(Constants.IAP_SUBSCRIPTION_PERIOD, jsonSkuDetails.optString("subscriptionPeriod"));
                        params.putCharSequence(Constants.IAP_FREE_TRIAL_PERIOD, jsonSkuDetails.optString("freeTrialPeriod"));
                        params.putCharSequence(Constants.IAP_INTRO_PRICE_AMOUNT_MICROS, jsonSkuDetails.optString("introductoryPriceAmountMicros"));
                        params.putCharSequence(Constants.IAP_INTRO_PRICE_CYCLES, jsonSkuDetails.optString("introductoryPriceCycles"));
                        appEventsLogger.logPurchaseImplicitly(new BigDecimal(((double) jsonSkuDetails.getLong("price_amount_micros")) / 1000000.0d), Currency.getInstance(jsonSkuDetails.getString("price_currency_code")), params);
                        context.unbindService(this);
                    }
                } catch (JSONException e) {
                    Log.e(AutomaticAnalyticsLogger.TAG, "Error parsing in-app purchase data.", e);
                } finally {
                    context.unbindService(this);
                }
            }
        };
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, serviceConnection, 1);
        return true;
    }

    public static boolean isImplicitPurchaseLoggingEnabled() {
        FetchedAppSettings settings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
        if (settings != null && FacebookSdk.getAutoLogAppEventsEnabled() && settings.getIAPAutomaticLoggingEnabled()) {
            return true;
        }
        return false;
    }
}
