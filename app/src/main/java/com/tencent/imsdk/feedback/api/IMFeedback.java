package com.tencent.imsdk.feedback.api;

import android.content.Context;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.feedback.base.IUnreadMessage;

public class IMFeedback {
    private static Feedback feedback = new Feedback();

    public static void initialize(Context mContext) {
        IMConfig.initialize(mContext);
        feedback.initialize(mContext);
    }

    public static void setChannel(String channel) {
        feedback.setChannel(channel);
    }

    public static void sendFeedBack(String content) {
        feedback.sendFeedBack(content);
    }

    public static void showHelpCenter(String content) {
        feedback.showHelpCenter(content);
    }

    public static void setLanguage(String lang) {
        feedback.setLanguage(lang);
    }

    public static void setZone(String zone) {
        feedback.setZone(zone);
    }

    public static void setCharacter(String character) {
        feedback.setCharacter(character);
    }

    public static void getLatestFeedback(IUnreadMessage.UnreadMessageListener listener) {
        feedback.getLatestFeedback(listener);
    }

    public static void setListener(IMFeedbackListener listener) {
        feedback.setListener(listener);
    }

    public static void showHelpCenter() {
        feedback.showHelpCenter();
    }

    public static void showHelpCenter(String channel, String extraJson) {
        feedback.showHelpCenter(channel, extraJson);
    }

    public static void showFAQ(String extraJson) {
        feedback.showFAQ(extraJson);
    }

    public static void showCustomerService(String extraJson) {
        feedback.showCustomerService(extraJson);
    }

    public static void setLevel(String level) {
        feedback.setLevel(level);
    }

    public static void setServerName(String serverName) {
        feedback.setServerName(serverName);
    }

    public static void setServerId(String serverId) {
        feedback.setServerId(serverId);
    }

    public static void setRoleName(String roleName) {
        feedback.setRoleName(roleName);
    }

    public static void setRoleId(String roleId) {
        feedback.setRoleId(roleId);
    }

    public static String getLevel() {
        return feedback.getLevel();
    }

    public static String getServerName() {
        return feedback.getServerName();
    }

    public static String getServerId() {
        return feedback.getServerId();
    }

    public static String getRoleName() {
        return feedback.getRoleName();
    }

    public static String getRoleId() {
        return feedback.getRoleId();
    }

    public static String getZone() {
        return feedback.getZone();
    }

    public static String getLanguage() {
        return feedback.getLanguage();
    }

    public static String getCharacter() {
        return feedback.getCharacter();
    }
}
