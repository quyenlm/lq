package com.tencent.ieg.ntv.ctrl.player;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.drive.DriveFile;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventForcePopUpData;
import com.tencent.ieg.ntv.event.net.EventPlayInfoData;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import java.util.List;

public class PlayerPopUpController {
    private static final String TAG = PlayerPopUpController.class.getSimpleName();
    private static PlayerPopUpController _instance;
    private Context mAppContext;
    private IEventListener onForcePopupEvent = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            PlayerPopUpController.this.onForcePopUpData((EventForcePopUpData) event);
            EventManager.getInstance().unregister(eventId, this);
        }
    };

    public static PlayerPopUpController getInstance() {
        if (_instance == null) {
            _instance = new PlayerPopUpController();
        }
        return _instance;
    }

    public void init(Context context) {
        if (context != null) {
            this.mAppContext = context.getApplicationContext();
            EventManager.getInstance().register(5004, this.onForcePopupEvent);
        }
    }

    public void onForcePopUpData(EventForcePopUpData eventForcePopUpData) {
        Logger.d(TAG, "onForcePopUpData");
        if (eventForcePopUpData != null) {
            EventForcePopUpData.ForcePopUpInfo popUpInfo = getCurPopUpInfo(eventForcePopUpData.forcePopUpInfoList);
            EventPlayInfoData playInfo = (EventPlayInfoData) eventForcePopUpData.playInfo;
            if (popUpInfo == null || playInfo == null || this.mAppContext == null) {
                Logger.d(TAG, "popUpInfo/playInfo null");
                TVShowManager.getInstance().closeTVShow();
                TVShowManager.getInstance().onCheckPopup(false);
                return;
            }
            TVShowManager.getInstance().onCheckPopup(true);
            Intent intent = new Intent(this.mAppContext, PopupPlayerActivity.class);
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra("bgimgurl", popUpInfo.bgImgUrl);
            this.mAppContext.startActivity(intent);
            this.mAppContext = null;
            return;
        }
        Logger.d(TAG, "forePopUpInfo null");
        TVShowManager.getInstance().closeTVShow();
        TVShowManager.getInstance().onCheckPopup(false);
    }

    private EventForcePopUpData.ForcePopUpInfo getCurPopUpInfo(List<EventForcePopUpData.ForcePopUpInfo> forePopUpList) {
        if (forePopUpList != null && forePopUpList.size() > 0) {
            for (int i = 0; i < forePopUpList.size(); i++) {
                EventForcePopUpData.ForcePopUpInfo info = forePopUpList.get(i);
                long curMillonSec = Util.getCurrentMSeconds();
                long startLocalSec = info.startTime * 1000;
                long endLocalSec = info.endTime * 1000;
                if (curMillonSec >= startLocalSec && curMillonSec <= endLocalSec) {
                    return info;
                }
            }
        }
        return null;
    }
}
