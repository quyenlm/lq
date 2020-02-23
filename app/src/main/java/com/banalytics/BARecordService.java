package com.banalytics;

import android.content.Intent;
import android.text.TextUtils;
import com.banalytics.BATrackerConst;
import com.btalk.helper.BBAppLogger;

public class BARecordService extends LoggerIntentService {
    public BARecordService() {
        super("ba-record-service");
    }

    public void onCreate() {
        super.onCreate();
        BBAppLogger.i("%s service has started", this);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        int commandType = getCommandType(intent);
        BATracker mBATracker = BATracker.getInstance();
        mBATracker.init(this);
        switch (commandType) {
            case 2:
                BBAppLogger.i("record event command received", new Object[0]);
                String cmdType = intent.hasExtra(BATrackerConst.JSON_KEYS.CMD_TYPE) ? intent.getStringExtra(BATrackerConst.JSON_KEYS.CMD_TYPE) : BATrackerConst.EVENT_TYPES.NOT_AVAILABLE;
                String remarks = intent.hasExtra("description") ? intent.getStringExtra("description") : "";
                String uid = intent.getStringExtra(BATrackerConst.JSON_KEYS.USER_ID_STR);
                if (TextUtils.isEmpty(uid)) {
                    uid = String.valueOf(intent.getLongExtra(BATrackerConst.JSON_KEYS.USER_ID, 0));
                }
                mBATracker.recordNewEvent(cmdType, remarks, uid, intent.hasExtra("country") ? intent.getStringExtra("country") : "", intent.getIntExtra("app_version", 0));
                return;
            case 4:
                BBAppLogger.i("record installation command received", new Object[0]);
                mBATracker.recordNewInstallEvent(this, intent.hasExtra("description") ? intent.getStringExtra("description") : "", intent.hasExtra("channel") ? intent.getStringExtra("channel") : "", intent.getIntExtra("api_level", 0), intent.hasExtra("device_info") ? intent.getStringExtra("device_info") : "", intent.getIntExtra("app_version", 0), intent.hasExtra("referrer") ? intent.getStringExtra("referrer") : "", intent.hasExtra("manufacturer") ? intent.getStringExtra("manufacturer") : "", intent.hasExtra("app_key") ? intent.getStringExtra("app_key") : "");
                return;
            default:
                BBAppLogger.i("record-service invalid command %d received", Integer.valueOf(commandType));
                return;
        }
    }

    private int getCommandType(Intent intent) {
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("command", 0);
    }
}
