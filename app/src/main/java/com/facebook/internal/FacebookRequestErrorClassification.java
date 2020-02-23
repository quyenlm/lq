package com.facebook.internal;

import com.facebook.FacebookRequestError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {
    public static final int EC_APP_NOT_INSTALLED = 412;
    public static final int EC_APP_TOO_MANY_CALLS = 4;
    public static final int EC_INVALID_SESSION = 102;
    public static final int EC_INVALID_TOKEN = 190;
    public static final int EC_RATE = 9;
    public static final int EC_SERVICE_UNAVAILABLE = 2;
    public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
    public static final int EC_USER_TOO_MANY_CALLS = 17;
    public static final int ESC_APP_INACTIVE = 493;
    public static final int ESC_APP_NOT_INSTALLED = 458;
    public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
    public static final String KEY_NAME = "name";
    public static final String KEY_OTHER = "other";
    public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
    public static final String KEY_TRANSIENT = "transient";
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    FacebookRequestErrorClassification(Map<Integer, Set<Integer>> otherErrors2, Map<Integer, Set<Integer>> transientErrors2, Map<Integer, Set<Integer>> loginRecoverableErrors2, String otherRecoveryMessage2, String transientRecoveryMessage2, String loginRecoverableRecoveryMessage2) {
        this.otherErrors = otherErrors2;
        this.transientErrors = transientErrors2;
        this.loginRecoverableErrors = loginRecoverableErrors2;
        this.otherRecoveryMessage = otherRecoveryMessage2;
        this.transientRecoveryMessage = transientRecoveryMessage2;
        this.loginRecoverableRecoveryMessage = loginRecoverableRecoveryMessage2;
    }

    public Map<Integer, Set<Integer>> getOtherErrors() {
        return this.otherErrors;
    }

    public Map<Integer, Set<Integer>> getTransientErrors() {
        return this.transientErrors;
    }

    public Map<Integer, Set<Integer>> getLoginRecoverableErrors() {
        return this.loginRecoverableErrors;
    }

    public String getRecoveryMessage(FacebookRequestError.Category category) {
        switch (category) {
            case OTHER:
                return this.otherRecoveryMessage;
            case LOGIN_RECOVERABLE:
                return this.loginRecoverableRecoveryMessage;
            case TRANSIENT:
                return this.transientRecoveryMessage;
            default:
                return null;
        }
    }

    public FacebookRequestError.Category classify(int errorCode, int errorSubCode, boolean isTransient) {
        Set<Integer> subCodes;
        Set<Integer> subCodes2;
        Set<Integer> subCodes3;
        if (isTransient) {
            return FacebookRequestError.Category.TRANSIENT;
        }
        if (this.otherErrors != null && this.otherErrors.containsKey(Integer.valueOf(errorCode)) && ((subCodes3 = this.otherErrors.get(Integer.valueOf(errorCode))) == null || subCodes3.contains(Integer.valueOf(errorSubCode)))) {
            return FacebookRequestError.Category.OTHER;
        }
        if (this.loginRecoverableErrors != null && this.loginRecoverableErrors.containsKey(Integer.valueOf(errorCode)) && ((subCodes2 = this.loginRecoverableErrors.get(Integer.valueOf(errorCode))) == null || subCodes2.contains(Integer.valueOf(errorSubCode)))) {
            return FacebookRequestError.Category.LOGIN_RECOVERABLE;
        }
        if (this.transientErrors == null || !this.transientErrors.containsKey(Integer.valueOf(errorCode)) || ((subCodes = this.transientErrors.get(Integer.valueOf(errorCode))) != null && !subCodes.contains(Integer.valueOf(errorSubCode)))) {
            return FacebookRequestError.Category.OTHER;
        }
        return FacebookRequestError.Category.TRANSIENT;
    }

    public static synchronized FacebookRequestErrorClassification getDefaultErrorClassification() {
        FacebookRequestErrorClassification facebookRequestErrorClassification;
        synchronized (FacebookRequestErrorClassification.class) {
            if (defaultInstance == null) {
                defaultInstance = getDefaultErrorClassificationImpl();
            }
            facebookRequestErrorClassification = defaultInstance;
        }
        return facebookRequestErrorClassification;
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
        return new FacebookRequestErrorClassification((Map<Integer, Set<Integer>>) null, new HashMap<Integer, Set<Integer>>() {
            {
                put(2, (Object) null);
                put(4, (Object) null);
                put(9, (Object) null);
                put(17, (Object) null);
                put(Integer.valueOf(FacebookRequestErrorClassification.EC_TOO_MANY_USER_ACTION_CALLS), (Object) null);
            }
        }, new HashMap<Integer, Set<Integer>>() {
            {
                put(102, (Object) null);
                put(190, (Object) null);
                put(412, (Object) null);
            }
        }, (String) null, (String) null, (String) null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject definition) {
        int code;
        JSONArray itemsArray = definition.optJSONArray("items");
        if (itemsArray.length() == 0) {
            return null;
        }
        Map<Integer, Set<Integer>> items = new HashMap<>();
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject item = itemsArray.optJSONObject(i);
            if (!(item == null || (code = item.optInt("code")) == 0)) {
                Set<Integer> subcodes = null;
                JSONArray subcodesArray = item.optJSONArray("subcodes");
                if (subcodesArray != null && subcodesArray.length() > 0) {
                    subcodes = new HashSet<>();
                    for (int j = 0; j < subcodesArray.length(); j++) {
                        int subCode = subcodesArray.optInt(j);
                        if (subCode != 0) {
                            subcodes.add(Integer.valueOf(subCode));
                        }
                    }
                }
                items.put(Integer.valueOf(code), subcodes);
            }
        }
        return items;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray jsonArray) {
        String name;
        if (jsonArray == null) {
            return null;
        }
        Map<Integer, Set<Integer>> otherErrors2 = null;
        Map<Integer, Set<Integer>> transientErrors2 = null;
        Map<Integer, Set<Integer>> loginRecoverableErrors2 = null;
        String otherRecoveryMessage2 = null;
        String transientRecoveryMessage2 = null;
        String loginRecoverableRecoveryMessage2 = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject definition = jsonArray.optJSONObject(i);
            if (!(definition == null || (name = definition.optString("name")) == null)) {
                if (name.equalsIgnoreCase("other")) {
                    otherRecoveryMessage2 = definition.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    otherErrors2 = parseJSONDefinition(definition);
                } else if (name.equalsIgnoreCase(KEY_TRANSIENT)) {
                    transientRecoveryMessage2 = definition.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    transientErrors2 = parseJSONDefinition(definition);
                } else if (name.equalsIgnoreCase(KEY_LOGIN_RECOVERABLE)) {
                    loginRecoverableRecoveryMessage2 = definition.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    loginRecoverableErrors2 = parseJSONDefinition(definition);
                }
            }
        }
        return new FacebookRequestErrorClassification(otherErrors2, transientErrors2, loginRecoverableErrors2, otherRecoveryMessage2, transientRecoveryMessage2, loginRecoverableRecoveryMessage2);
    }
}
