package com.tencent.imsdk.game.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.game.base.IMGameBase;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMGame {
    protected static final String PACKAGE_NAME_FORMAT = "com.tencent.imsdk.%s.game.%sGame";
    protected static String currentChannel = null;
    protected static Context currentContext;
    protected static IMGameBase gameInstance;
    protected static String olderChannel = null;

    private static boolean isInitialized() {
        if (gameInstance != null) {
            return true;
        }
        IMLogger.e("initialize() hadn't executed yet");
        return false;
    }

    public static boolean initialize(Context context) {
        currentContext = context;
        if (currentContext != null) {
            IMConfig.initialize(currentContext);
        }
        return setChannel((String) null);
    }

    public static boolean setChannel(String channel) {
        if (TextUtils.isEmpty(channel)) {
            IMLogger.w("You hadn't setChannel() yet, so default classname will be used");
            currentChannel = String.format(PACKAGE_NAME_FORMAT, new Object[]{"google", "Google"});
            IMLogger.d(currentChannel);
        } else {
            currentChannel = String.format(PACKAGE_NAME_FORMAT, new Object[]{channel.toLowerCase(), channel});
        }
        if (!currentChannel.equalsIgnoreCase(olderChannel)) {
            gameInstance = (IMGameBase) IMModules.getInstance().getModule(currentChannel);
            if (isInitialized()) {
                gameInstance.initialize(currentContext);
            }
        }
        olderChannel = currentChannel;
        if (gameInstance != null) {
            return true;
        }
        return false;
    }

    public static void setListener(IMGameListener listener) {
        if (isInitialized()) {
            gameInstance.setIMGoogleCallback(listener);
        }
    }

    public static void setup() {
        if (isInitialized()) {
            gameInstance.setup();
        }
    }

    public static boolean available() {
        if (isInitialized()) {
            return gameInstance.available();
        }
        return false;
    }

    public static void submitScore(String category, int score) {
        if (isInitialized()) {
            gameInstance.submitScore(category, score);
        }
    }

    public static void achieve(String achieveId, int count) {
        if (isInitialized()) {
            gameInstance.achieve(achieveId, count);
        }
    }

    public static void event(String event, int count) {
        if (isInitialized()) {
            gameInstance.event(event, count);
        }
    }

    public static void showQuests() {
        if (isInitialized()) {
            gameInstance.showQuests();
        }
    }

    public static void showAchieve() {
        if (isInitialized()) {
            gameInstance.showAchieve();
        }
    }

    public static void showLeaderBoard(String id) {
        if (isInitialized()) {
            gameInstance.showLeaderBoard(id);
        }
    }

    public static void getAchieve(IMGameListener callback, boolean forceReload) {
        if (isInitialized()) {
            gameInstance.getAchieve(callback, forceReload);
        }
    }

    public static void getLeaderBoard(IMGameListener callback, boolean forceReload, String leaderBoardId) {
        if (isInitialized()) {
            gameInstance.getLeaderBoard(callback, forceReload, leaderBoardId);
        }
    }

    public static void getQuests(IMGameListener callback, boolean forceReload, String... questIds) {
        if (isInitialized()) {
            gameInstance.getQuests(callback, forceReload, questIds);
        }
    }

    public static boolean isInstalledPlayServices() {
        if (isInitialized()) {
            return gameInstance.isInstalledPlayServices();
        }
        return false;
    }

    public static void quit() {
        if (isInitialized()) {
            gameInstance.quit();
        }
    }

    public static void setIMGoogleGameCallback(IMGameListener callback) {
        if (isInitialized()) {
            gameInstance.setIMGoogleCallback(callback);
        }
    }
}
