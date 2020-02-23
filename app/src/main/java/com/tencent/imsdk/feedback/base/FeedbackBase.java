package com.tencent.imsdk.feedback.base;

import android.content.Context;
import com.tencent.imsdk.feedback.api.IMFeedbackListener;
import com.tencent.imsdk.feedback.base.IUnreadMessage;
import com.tencent.imsdk.tool.etc.IMLogger;

public abstract class FeedbackBase {
    public abstract void getLatestFeedback(IUnreadMessage.UnreadMessageListener unreadMessageListener);

    public abstract void initialize(Context context);

    public abstract void sendFeedBack(String str);

    public abstract void showHelpCenter(String str);

    public void setChannel(String moduleName) {
    }

    public void setLanguage(String lang) {
        IMLogger.d("not support setLanguage");
    }

    public void setZone(String zone) {
        IMLogger.d("not support setZone");
    }

    public void setCharacter(String character) {
        IMLogger.d("not support setCharacter");
    }

    public void setListener(IMFeedbackListener listener) {
    }

    public void initialize(Context context, String channel) {
        IMLogger.d("not support initialize(Context context, String channel)");
    }

    public void showHelpCenter() {
        IMLogger.d("not support showHelpCenter()");
    }

    public void showHelpCenter(String channel, String extraJson) {
        IMLogger.d("not support showHelpCenter(String channel, String extraJson)");
    }

    public void showFAQ(String extraJson) {
        IMLogger.d("not support showFAQ(String extraJson)");
    }

    public void showCustomerService(String extraJson) {
        IMLogger.d("not support ShowCustomerService(String extraJson)");
    }

    public void setLevel(String level) {
        IMLogger.d("not support setLevel");
    }

    public void setServerName(String serverName) {
        IMLogger.d("not support serServerName");
    }

    public void setServerId(String serverId) {
        IMLogger.d("not support setServerId");
    }

    public void setRoleName(String roleName) {
        IMLogger.d("not support setRoleName");
    }

    public void setRoleId(String roleId) {
        IMLogger.d("not support setRoleId");
    }

    public String getLevel() {
        return "";
    }

    public String getServerName() {
        return "";
    }

    public String getServerId() {
        return "";
    }

    public String getRoleName() {
        return "";
    }

    public String getRoleId() {
        return "";
    }

    public String getZone() {
        return "";
    }

    public String getLanguage() {
        return "";
    }

    public String getCharacter() {
        return "";
    }
}
