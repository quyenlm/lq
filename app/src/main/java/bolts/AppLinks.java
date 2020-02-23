package bolts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.Map;

public final class AppLinks {
    static final String KEY_NAME_APPLINK_DATA = "al_applink_data";
    static final String KEY_NAME_EXTRAS = "extras";
    static final String KEY_NAME_TARGET = "target_url";

    public static Bundle getAppLinkData(Intent intent) {
        return intent.getBundleExtra(KEY_NAME_APPLINK_DATA);
    }

    public static Bundle getAppLinkExtras(Intent intent) {
        Bundle appLinkData = getAppLinkData(intent);
        if (appLinkData == null) {
            return null;
        }
        return appLinkData.getBundle("extras");
    }

    public static Uri getTargetUrl(Intent intent) {
        String targetString;
        Bundle appLinkData = getAppLinkData(intent);
        if (appLinkData == null || (targetString = appLinkData.getString(KEY_NAME_TARGET)) == null) {
            return intent.getData();
        }
        return Uri.parse(targetString);
    }

    public static Uri getTargetUrlFromInboundIntent(Context context, Intent intent) {
        String targetString;
        Bundle appLinkData = getAppLinkData(intent);
        if (appLinkData == null || (targetString = appLinkData.getString(KEY_NAME_TARGET)) == null) {
            return null;
        }
        MeasurementEvent.sendBroadcastEvent(context, MeasurementEvent.APP_LINK_NAVIGATE_IN_EVENT_NAME, intent, (Map<String, String>) null);
        return Uri.parse(targetString);
    }
}
