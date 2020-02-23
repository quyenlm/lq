package com.facebook.appevents.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.facebook.internal.Utility;
import com.garena.pay.android.inappbilling.IabHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class InAppPurchaseEventManager {
    private static final String DETAILS_LIST = "DETAILS_LIST";
    private static final String GET_INTERFACE_METHOD = "iap_get_interface";
    private static final String GET_SKU_DETAILS_METHOD = "iap_get_sku_details";
    private static final String IN_APP_BILLING_SERVICE = "com.android.vending.billing.IInAppBillingService";
    private static final String IN_APP_BILLING_SERVICE_STUB = "com.android.vending.billing.IInAppBillingService$Stub";
    private static final String ITEM_ID_LIST = "ITEM_ID_LIST";
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String TAG = InAppPurchaseEventManager.class.getCanonicalName();
    private static final HashMap<String, Class<?>> classMap = new HashMap<>();
    private static final HashMap<String, Method> methodMap = new HashMap<>();

    public static Object getServiceInterface(Context context, IBinder service) {
        try {
            Method getInterfaceMethod = methodMap.get(GET_INTERFACE_METHOD);
            if (getInterfaceMethod == null) {
                getInterfaceMethod = context.getClassLoader().loadClass(IN_APP_BILLING_SERVICE_STUB).getDeclaredMethod("asInterface", new Class[]{IBinder.class});
                methodMap.put(GET_INTERFACE_METHOD, getInterfaceMethod);
            }
            Object[] args = {service};
            Utility.logd(TAG, "In-app billing service connected");
            return getInterfaceMethod.invoke((Object) null, args);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "com.android.vending.billing.IInAppBillingService$Stub is not available, please add com.android.vending.billing.IInAppBillingService to the project.", e);
            return null;
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "com.android.vending.billing.IInAppBillingService$Stub.asInterface method not found", e2);
            return null;
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "Illegal access to method com.android.vending.billing.IInAppBillingService$Stub.asInterface", e3);
            return null;
        } catch (InvocationTargetException e4) {
            Log.e(TAG, "Invocation target exception in com.android.vending.billing.IInAppBillingService$Stub.asInterface", e4);
            return null;
        }
    }

    public static String getPurchaseDetails(Context context, String sku, Object inAppBillingObj, boolean isSubscription) {
        if (inAppBillingObj == null || sku == "") {
            return "";
        }
        try {
            Method getSkuDetailsMethod = methodMap.get(GET_SKU_DETAILS_METHOD);
            Class<?> iapClass = classMap.get(IN_APP_BILLING_SERVICE);
            if (getSkuDetailsMethod == null || iapClass == null) {
                iapClass = context.getClassLoader().loadClass(IN_APP_BILLING_SERVICE);
                getSkuDetailsMethod = iapClass.getDeclaredMethod("getSkuDetails", new Class[]{Integer.TYPE, String.class, String.class, Bundle.class});
                methodMap.put(GET_SKU_DETAILS_METHOD, getSkuDetailsMethod);
                classMap.put(IN_APP_BILLING_SERVICE, iapClass);
            }
            ArrayList<String> skuList = new ArrayList<>();
            skuList.add(sku);
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
            Object localObj = iapClass.cast(inAppBillingObj);
            Object[] args = new Object[4];
            args[0] = 3;
            args[1] = context.getPackageName();
            args[2] = isSubscription ? IabHelper.ITEM_TYPE_SUBS : IabHelper.ITEM_TYPE_INAPP;
            args[3] = querySkus;
            Bundle skuDetails = (Bundle) getSkuDetailsMethod.invoke(localObj, args);
            if (skuDetails.getInt("RESPONSE_CODE") == 0) {
                ArrayList<String> details = skuDetails.getStringArrayList("DETAILS_LIST");
                return details.size() < 1 ? "" : details.get(0);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "com.android.vending.billing.IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to the project, and import the IInAppBillingService.aidl file into this package", e);
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "com.android.vending.billing.IInAppBillingService.getSkuDetails method is not available", e2);
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Invocation target exception in com.android.vending.billing.IInAppBillingService.getSkuDetails", e3);
        } catch (IllegalAccessException e4) {
            Log.e(TAG, "Illegal access to method com.android.vending.billing.IInAppBillingService.getSkuDetails", e4);
        }
        return "";
    }
}
