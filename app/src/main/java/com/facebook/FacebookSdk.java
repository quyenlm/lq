package com.facebook;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.LockOnGetVariable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    public static final String AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY = "com.facebook.sdk.AutoLogAppEventsEnabled";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized. Call FacebookSdk.setCallbackRequestCodeOffset inside your Application.onCreate method";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CALLBACK_OFFSET_PROPERTY = "com.facebook.sdk.CallbackOffset";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    public static final String CODELESS_DEBUG_LOG_ENABLED_PROPERTY = "com.facebook.sdk.CodelessDebugLogEnabled";
    private static final int DEFAULT_CALLBACK_REQUEST_CODE_OFFSET = 64206;
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
        }
    };
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = FacebookSdk.class.getCanonicalName();
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    /* access modifiers changed from: private */
    public static Context applicationContext;
    /* access modifiers changed from: private */
    public static volatile String applicationId;
    private static volatile String applicationName;
    private static volatile Boolean autoLogAppEventsEnabled;
    private static LockOnGetVariable<File> cacheDir;
    private static int callbackRequestCodeOffset = DEFAULT_CALLBACK_REQUEST_CODE_OFFSET;
    private static volatile Boolean codelessDebugLogEnabled;
    private static Executor executor;
    private static volatile String facebookDomain = "facebook.com";
    private static String graphApiVersion = ServerProtocol.getDefaultAPIVersion();
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet<>(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static AtomicLong onProgressThreshold = new AtomicLong(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
    private static Boolean sdkInitialized = false;

    public interface InitializeCallback {
        void onInitialized();
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context applicationContext2, int callbackRequestCodeOffset2) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext2, callbackRequestCodeOffset2, (InitializeCallback) null);
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context applicationContext2, int callbackRequestCodeOffset2, InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (sdkInitialized.booleanValue() && callbackRequestCodeOffset2 != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            } else if (callbackRequestCodeOffset2 < 0) {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            } else {
                callbackRequestCodeOffset = callbackRequestCodeOffset2;
                sdkInitialize(applicationContext2, callback);
            }
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(Context applicationContext2) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext2, (InitializeCallback) null);
        }
    }

    @Deprecated
    public static synchronized void sdkInitialize(final Context applicationContext2, final InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (!sdkInitialized.booleanValue()) {
                Validate.notNull(applicationContext2, "applicationContext");
                Validate.hasFacebookActivity(applicationContext2, false);
                Validate.hasInternetPermissions(applicationContext2, false);
                applicationContext = applicationContext2.getApplicationContext();
                loadDefaultsFromMetadata(applicationContext);
                if (Utility.isNullOrEmpty(applicationId)) {
                    throw new FacebookException("A valid Facebook app id must be set in the AndroidManifest.xml or set by calling FacebookSdk.setApplicationId before initializing the sdk.");
                }
                if ((applicationContext instanceof Application) && autoLogAppEventsEnabled.booleanValue()) {
                    ActivityLifecycleTracker.startTracking((Application) applicationContext, applicationId);
                }
                sdkInitialized = true;
                FetchedAppSettingsManager.loadAppSettingsAsync();
                NativeProtocol.updateAllAvailableProtocolVersionsAsync();
                BoltsMeasurementEventListener.getInstance(applicationContext);
                cacheDir = new LockOnGetVariable<>(new Callable<File>() {
                    public File call() throws Exception {
                        return FacebookSdk.applicationContext.getCacheDir();
                    }
                });
                getExecutor().execute(new FutureTask<>(new Callable<Void>() {
                    public Void call() throws Exception {
                        AccessTokenManager.getInstance().loadCurrentAccessToken();
                        ProfileManager.getInstance().loadCurrentProfile();
                        if (AccessToken.isCurrentAccessTokenActive() && Profile.getCurrentProfile() == null) {
                            Profile.fetchProfileForCurrentAccessToken();
                        }
                        if (callback != null) {
                            callback.onInitialized();
                        }
                        AppEventsLogger.initializeLib(FacebookSdk.applicationContext, FacebookSdk.applicationId);
                        AppEventsLogger.newLogger(applicationContext2.getApplicationContext()).flush();
                        return null;
                    }
                }));
            } else if (callback != null) {
                callback.onInitialized();
            }
        }
    }

    public static synchronized boolean isInitialized() {
        boolean booleanValue;
        synchronized (FacebookSdk.class) {
            booleanValue = sdkInitialized.booleanValue();
        }
        return booleanValue;
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (loggingBehaviors) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return unmodifiableSet;
    }

    public static void addLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add(behavior);
            updateGraphDebugBehavior();
        }
    }

    public static void removeLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove(behavior);
        }
    }

    public static void clearLoggingBehaviors() {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(behavior);
        }
        return z;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static void setIsDebugEnabled(boolean enabled) {
        isDebugEnabled = enabled;
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    private static void updateGraphDebugBehavior() {
        if (loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }

    public static void setLegacyTokenUpgradeSupported(boolean supported) {
        isLegacyTokenUpgradeSupported = supported;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                executor = AsyncTask.THREAD_POOL_EXECUTOR;
            }
        }
        return executor;
    }

    public static void setExecutor(Executor executor2) {
        Validate.notNull(executor2, "executor");
        synchronized (LOCK) {
            executor = executor2;
        }
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static void setFacebookDomain(String facebookDomain2) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = facebookDomain2;
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static void setGraphApiVersion(String graphApiVersion2) {
        Log.w(TAG, "WARNING: Calling setGraphApiVersion from non-DEBUG code.");
        if (!Utility.isNullOrEmpty(graphApiVersion2) && !graphApiVersion.equals(graphApiVersion2)) {
            graphApiVersion = graphApiVersion2;
        }
    }

    public static String getGraphApiVersion() {
        Utility.logd(TAG, String.format("getGraphApiVersion: %s", new Object[]{graphApiVersion}));
        return graphApiVersion;
    }

    public static void publishInstallAsync(Context context, final String applicationId2) {
        final Context applicationContext2 = context.getApplicationContext();
        getExecutor().execute(new Runnable() {
            public void run() {
                FacebookSdk.publishInstallAndWaitForResponse(applicationContext2, applicationId2);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0070, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0078, code lost:
        throw new com.facebook.FacebookException("An error occurred while publishing install.", (java.lang.Throwable) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000d, code lost:
        com.facebook.internal.Utility.logd("Facebook-publish", r0);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:2:0x0004, B:9:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void publishInstallAndWaitForResponse(android.content.Context r14, java.lang.String r15) {
        /*
            if (r14 == 0) goto L_0x0004
            if (r15 != 0) goto L_0x0013
        L_0x0004:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x000c }
            java.lang.String r12 = "Both context and applicationId must be non-null"
            r11.<init>(r12)     // Catch:{ Exception -> 0x000c }
            throw r11     // Catch:{ Exception -> 0x000c }
        L_0x000c:
            r0 = move-exception
            java.lang.String r11 = "Facebook-publish"
            com.facebook.internal.Utility.logd((java.lang.String) r11, (java.lang.Exception) r0)
        L_0x0012:
            return
        L_0x0013:
            com.facebook.internal.AttributionIdentifiers r2 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r14)     // Catch:{ Exception -> 0x000c }
            java.lang.String r11 = "com.facebook.sdk.attributionTracking"
            r12 = 0
            android.content.SharedPreferences r6 = r14.getSharedPreferences(r11, r12)     // Catch:{ Exception -> 0x000c }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x000c }
            r11.<init>()     // Catch:{ Exception -> 0x000c }
            java.lang.StringBuilder r11 = r11.append(r15)     // Catch:{ Exception -> 0x000c }
            java.lang.String r12 = "ping"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x000c }
            java.lang.String r3 = r11.toString()     // Catch:{ Exception -> 0x000c }
            r12 = 0
            long r4 = r6.getLong(r3, r12)     // Catch:{ Exception -> 0x000c }
            com.facebook.appevents.internal.AppEventsLoggerUtility$GraphAPIActivityType r11 = com.facebook.appevents.internal.AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT     // Catch:{ JSONException -> 0x0070 }
            java.lang.String r12 = com.facebook.appevents.AppEventsLogger.getAnonymousAppDeviceGUID(r14)     // Catch:{ JSONException -> 0x0070 }
            boolean r13 = getLimitEventAndDataUsage(r14)     // Catch:{ JSONException -> 0x0070 }
            org.json.JSONObject r7 = com.facebook.appevents.internal.AppEventsLoggerUtility.getJSONObjectForGraphAPICall(r11, r2, r12, r13, r14)     // Catch:{ JSONException -> 0x0070 }
            java.lang.String r11 = "%s/activities"
            r12 = 1
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x000c }
            r13 = 0
            r12[r13] = r15     // Catch:{ Exception -> 0x000c }
            java.lang.String r10 = java.lang.String.format(r11, r12)     // Catch:{ Exception -> 0x000c }
            r11 = 0
            r12 = 0
            com.facebook.GraphRequest r8 = com.facebook.GraphRequest.newPostRequest(r11, r10, r7, r12)     // Catch:{ Exception -> 0x000c }
            r12 = 0
            int r11 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r11 != 0) goto L_0x0012
            com.facebook.GraphResponse r9 = r8.executeAndWait()     // Catch:{ Exception -> 0x000c }
            android.content.SharedPreferences$Editor r1 = r6.edit()     // Catch:{ Exception -> 0x000c }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x000c }
            r1.putLong(r3, r4)     // Catch:{ Exception -> 0x000c }
            r1.apply()     // Catch:{ Exception -> 0x000c }
            goto L_0x0012
        L_0x0070:
            r0 = move-exception
            com.facebook.FacebookException r11 = new com.facebook.FacebookException     // Catch:{ Exception -> 0x000c }
            java.lang.String r12 = "An error occurred while publishing install."
            r11.<init>((java.lang.String) r12, (java.lang.Throwable) r0)     // Catch:{ Exception -> 0x000c }
            throw r11     // Catch:{ Exception -> 0x000c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):void");
    }

    public static String getSdkVersion() {
        return FacebookSdkVersion.BUILD;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context context, boolean limitEventUsage) {
        context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", limitEventUsage).apply();
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static void setOnProgressThreshold(long threshold) {
        onProgressThreshold.set(threshold);
    }

    static void loadDefaultsFromMetadata(Context context) {
        if (context != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (ai != null && ai.metaData != null) {
                    if (applicationId == null) {
                        Object appId = ai.metaData.get("com.facebook.sdk.ApplicationId");
                        if (appId instanceof String) {
                            String appIdString = (String) appId;
                            if (appIdString.toLowerCase(Locale.ROOT).startsWith("fb")) {
                                applicationId = appIdString.substring(2);
                            } else {
                                applicationId = appIdString;
                            }
                        } else if (appId instanceof Integer) {
                            throw new FacebookException("App Ids cannot be directly placed in the manifest.They must be prefixed by 'fb' or be placed in the string resource file.");
                        }
                    }
                    if (applicationName == null) {
                        applicationName = ai.metaData.getString(APPLICATION_NAME_PROPERTY);
                    }
                    if (appClientToken == null) {
                        appClientToken = ai.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                    if (callbackRequestCodeOffset == DEFAULT_CALLBACK_REQUEST_CODE_OFFSET) {
                        callbackRequestCodeOffset = ai.metaData.getInt(CALLBACK_OFFSET_PROPERTY, DEFAULT_CALLBACK_REQUEST_CODE_OFFSET);
                    }
                    if (autoLogAppEventsEnabled == null) {
                        autoLogAppEventsEnabled = Boolean.valueOf(ai.metaData.getBoolean(AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY, true));
                    }
                    if (codelessDebugLogEnabled == null) {
                        codelessDebugLogEnabled = Boolean.valueOf(ai.metaData.getBoolean(CODELESS_DEBUG_LOG_ENABLED_PROPERTY, false));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    }

    public static String getApplicationSignature(Context context) {
        PackageManager packageManager;
        Validate.sdkInitialized();
        if (context == null || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        try {
            PackageInfo pInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatures = pInfo.signatures;
            if (signatures == null || signatures.length == 0) {
                return null;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(pInfo.signatures[0].toByteArray());
                return Base64.encodeToString(md.digest(), 9);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static void setApplicationId(String applicationId2) {
        applicationId = applicationId2;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static void setApplicationName(String applicationName2) {
        applicationName = applicationName2;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static void setClientToken(String clientToken) {
        appClientToken = clientToken;
    }

    public static boolean getAutoLogAppEventsEnabled() {
        Validate.sdkInitialized();
        return autoLogAppEventsEnabled.booleanValue();
    }

    public static void setAutoLogAppEventsEnabled(boolean flag) {
        autoLogAppEventsEnabled = Boolean.valueOf(flag);
    }

    public static boolean getCodelessDebugLogEnabled() {
        Validate.sdkInitialized();
        return codelessDebugLogEnabled.booleanValue();
    }

    public static void setCodelessDebugLogEnabled(boolean flag) {
        codelessDebugLogEnabled = Boolean.valueOf(flag);
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return cacheDir.getValue();
    }

    public static void setCacheDir(File cacheDir2) {
        cacheDir = new LockOnGetVariable<>(cacheDir2);
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static boolean isFacebookRequestCode(int requestCode) {
        return requestCode >= callbackRequestCodeOffset && requestCode < callbackRequestCodeOffset + 100;
    }
}
