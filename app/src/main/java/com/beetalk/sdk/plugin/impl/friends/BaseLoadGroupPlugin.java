package com.beetalk.sdk.plugin.impl.friends;

import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.GGPlugin;
import com.garena.pay.android.GGErrorCode;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

public abstract class BaseLoadGroupPlugin<S, T> extends GGPlugin<S, T> {
    /* access modifiers changed from: protected */
    public abstract URL getUri() throws MalformedURLException;

    /* access modifiers changed from: protected */
    public GGErrorCode getResponseCode(JSONObject response) {
        if (response == null) {
            return GGErrorCode.NETWORK_RESPONSE_PARSE_FAIL;
        }
        GGErrorCode code = Helper.getErrorCode(response);
        return code == null ? GGErrorCode.SUCCESS : code;
    }

    public boolean embedInActivity() {
        return false;
    }
}
