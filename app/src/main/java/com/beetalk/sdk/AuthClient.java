package com.beetalk.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.pay.android.GGErrorCode;
import java.io.Serializable;
import java.util.ArrayList;

public class AuthClient implements Serializable {
    private static final long serialVersionUID = 1;
    private transient ActivityLauncher activityLauncher;
    private transient AuthRequestHandler authRequestHandler;
    private transient Activity context;
    private transient OnAuthCompleted onAuthCompleted;
    /* access modifiers changed from: private */
    public AuthClientRequest pendingRequest;

    interface OnAuthCompleted {
        void onAuthComplete(Result result);
    }

    public AuthRequestHandler getCurrentHandler() {
        return this.authRequestHandler;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(final Activity context2) {
        this.context = context2;
        this.activityLauncher = new ActivityLauncher() {
            public void startActivityForResult(Intent intent, int requestCode) {
                context2.startActivityForResult(intent, requestCode);
            }

            public Activity getContext() {
                return context2;
            }
        };
        if (this.pendingRequest != null) {
            initHandler(this.pendingRequest);
        }
    }

    public ActivityLauncher getActivityLauncher() {
        if (this.activityLauncher != null) {
            return this.activityLauncher;
        }
        if (this.pendingRequest != null) {
            return new ActivityLauncher() {
                public void startActivityForResult(Intent intent, int requestCode) {
                    AuthClient.this.pendingRequest.getStartActivityDelegate().startActivityForResult(intent, requestCode);
                }

                public Activity getContext() {
                    return AuthClient.this.pendingRequest.getStartActivityDelegate().getContext();
                }
            };
        }
        return null;
    }

    public void setActivityLauncher(ActivityLauncher activityLauncher2) {
        this.activityLauncher = activityLauncher2;
    }

    public AuthClientRequest getPendingRequest() {
        return this.pendingRequest;
    }

    /* access modifiers changed from: package-private */
    public void startOrResumeAuth(AuthClientRequest request) {
        if (!getInProgress()) {
            commenceAuth(request);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean getInProgress() {
        return this.pendingRequest != null;
    }

    private void commenceAuth(AuthClientRequest request) {
        if (request == null) {
            BBLogger.d("request is null", new Object[0]);
            return;
        }
        BBLogger.d("commenceAuth()", new Object[0]);
        if (this.pendingRequest != null) {
            throw new RuntimeException("Attempted to authorize while a request is pending.");
        }
        this.pendingRequest = request;
        initHandler(request);
        tryHandler();
    }

    /* access modifiers changed from: protected */
    public void tryHandler() {
        BBLogger.d("try one handler", new Object[0]);
        if (this.authRequestHandler.startAuth(this.pendingRequest)) {
            BBLogger.d("handler started, return", new Object[0]);
        } else if (this.pendingRequest != null) {
            BBLogger.d("all handlers fail, auth fail", new Object[0]);
            failedToAuth();
        }
    }

    private void failedToAuth() {
        notifyListener(Result.createErrorResult(this.pendingRequest, GGErrorCode.APP_NOT_INSTALLED.getCode().intValue()));
    }

    public void notifyListener(Result outcome) {
        if (this.onAuthCompleted != null) {
            this.onAuthCompleted.onAuthComplete(outcome);
        }
        this.pendingRequest = null;
        this.authRequestHandler = null;
    }

    /* access modifiers changed from: package-private */
    public void cancelCurrentHandler() {
        if (this.authRequestHandler != null) {
            this.authRequestHandler.cancel();
        }
    }

    private void initHandler(AuthClientRequest request) {
        GGLoginSession.SessionProvider sessionProvider = request.getSessionProvider();
        if (sessionProvider == GGLoginSession.SessionProvider.FACEBOOK) {
            BBLogger.d("add facebook auth handler", new Object[0]);
            this.authRequestHandler = new FBAuthRequestHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.GARENA) {
            BBLogger.d("add garena auth handler", new Object[0]);
            this.authRequestHandler = new GasAuthRequestHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.GUEST) {
            BBLogger.d("add guest auth handler", new Object[0]);
            this.authRequestHandler = new GuestRegistrationHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.REFRESH_TOKEN) {
            BBLogger.d("add refresh token handler", new Object[0]);
            this.authRequestHandler = new RefreshTokenHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.VK) {
            BBLogger.d("add vk auth handler", new Object[0]);
            this.authRequestHandler = new VKAuthRequestHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.LINE) {
            BBLogger.d("add line auth handler", new Object[0]);
            this.authRequestHandler = new LineAuthRequestHandler(this);
        } else if (sessionProvider == GGLoginSession.SessionProvider.GOOGLE) {
            BBLogger.d("add google auth handler", new Object[0]);
            this.authRequestHandler = new GoogleAuthRequestHandler(this);
        }
    }

    public void setOnAuthCompleted(OnAuthCompleted onAuthCompleted2) {
        this.onAuthCompleted = onAuthCompleted2;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.pendingRequest == null) {
            return false;
        }
        BBLogger.d("PendingRequest %s", this.pendingRequest.toString());
        return getCurrentHandler().onActivityResult(requestCode, resultCode, data, this.pendingRequest);
    }

    public static class Result implements Serializable {
        public static final int NO_ERROR = 0;
        private static final long serialVersionUID = 1;
        final int errorCode;
        final String errorMessage;
        final String openId;
        final AuthClientRequest request;
        final ResultCode resultCode;
        final AuthToken token;

        public Result(AuthClientRequest request2, ResultCode resultCode2, AuthToken token2, String errorMessage2, int errorCode2, String openId2) {
            this.resultCode = resultCode2;
            this.token = token2;
            this.errorMessage = errorMessage2;
            this.errorCode = errorCode2;
            this.request = request2;
            this.openId = openId2;
        }

        static Result createTokenResult(AuthClientRequest request2, AuthToken token2) {
            return new Result(request2, ResultCode.SUCCESS, token2, (String) null, 0, (String) null);
        }

        static Result createTokenResult(AuthClientRequest request2, AuthToken token2, String openId2) {
            return new Result(request2, ResultCode.SUCCESS, token2, (String) null, 0, openId2);
        }

        static Result createCancelResult(AuthClientRequest request2, String message) {
            return new Result(request2, ResultCode.CANCEL, (AuthToken) null, message, 0, (String) null);
        }

        static Result createErrorResult(AuthClientRequest request2, int errorCode2) {
            return createErrorResult(request2, "", "", errorCode2);
        }

        static Result createErrorResult(AuthClientRequest request2, String errorType, String errorDescription, int errorCode2) {
            return new Result(request2, ResultCode.ERROR, (AuthToken) null, TextUtils.join(": ", new String[]{errorType, errorDescription}), errorCode2, (String) null);
        }

        public static boolean isError(ResultCode resultCode2) {
            return resultCode2 != ResultCode.SUCCESS;
        }

        enum ResultCode {
            SUCCESS("success"),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String value;

            private ResultCode(String value2) {
                this.value = value2;
            }

            /* access modifiers changed from: package-private */
            public String getValue() {
                return this.value;
            }
        }
    }

    public static class AuthClientRequest implements Serializable {
        private static final long serialVersionUID = 1;
        private String applicationId;
        private String applicationKey;
        private String authId;
        private boolean isLegacy;
        private AuthToken mAuthToken;
        private GGLoginSession.SessionProvider mSessionProvider;
        private ArrayList<String> permissions;
        private String redirectURI;
        private int requestCode;
        private final transient ActivityLauncher startActivityDelegate;

        public AuthClientRequest(ActivityLauncher startActivityDelegate2, String authId2, int requestCode2, boolean isLegacy2, String applicationId2, String applicatonKey) {
            this.startActivityDelegate = startActivityDelegate2;
            this.authId = authId2;
            this.requestCode = requestCode2;
            this.isLegacy = isLegacy2;
            this.applicationId = applicationId2;
            this.applicationKey = applicatonKey;
        }

        public ActivityLauncher getStartActivityDelegate() {
            return this.startActivityDelegate;
        }

        public String getAuthId() {
            return this.authId;
        }

        public void setAuthId(String authId2) {
            this.authId = authId2;
        }

        public int getRequestCode() {
            return this.requestCode;
        }

        public void setRequestCode(int requestCode2) {
            this.requestCode = requestCode2;
        }

        public boolean isLegacy() {
            return this.isLegacy;
        }

        public void setLegacy(boolean isLegacy2) {
            this.isLegacy = isLegacy2;
        }

        public String getApplicationId() {
            return this.applicationId;
        }

        public void setApplicationId(String applicationId2) {
            this.applicationId = applicationId2;
        }

        public String getApplicationKey() {
            return this.applicationKey;
        }

        public void setRedirectURI(String redirectURI2) {
            this.redirectURI = redirectURI2;
        }

        public void setPermissions(ArrayList<String> permissions2) {
            this.permissions = permissions2;
        }

        public GGLoginSession.SessionProvider getSessionProvider() {
            return this.mSessionProvider;
        }

        public void setSessionProvider(GGLoginSession.SessionProvider sessionProvider) {
            this.mSessionProvider = sessionProvider;
        }

        public AuthToken getAuthToken() {
            return this.mAuthToken;
        }

        public void setAuthToken(AuthToken authToken) {
            this.mAuthToken = authToken;
        }
    }
}
