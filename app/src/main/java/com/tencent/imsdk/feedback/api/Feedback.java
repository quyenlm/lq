package com.tencent.imsdk.feedback.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.feedback.base.FeedbackBase;
import com.tencent.imsdk.feedback.base.IMFeedbackResult;
import com.tencent.imsdk.feedback.base.IUnreadMessage;
import com.tencent.imsdk.tool.etc.IMLogger;

public class Feedback extends FeedbackBase {
    public static final String CHARACTER_STRING_EXTRA = "character_extra";
    public static final String LANGUAGE_STRING_EXTRA = "language_extra";
    private static final String PACKAGE_NAME_BASE_FORMAT = "com.tencent.imsdk.feedback.%s";
    public static final String ZONE_STRING_EXTRA = "zone_extra";
    private static FeedbackBase instance = null;
    private static Context mContext = null;
    private String character = null;
    private String className = "com.tencent.imsdk.feedback.IMFeedbackHttp";
    private String currentChannel = null;
    private IMFeedbackListener currentListener;
    private String lang = null;
    private String module = null;
    private String zone = null;

    /* access modifiers changed from: private */
    public native void onFeedbackResult(IMFeedbackResult iMFeedbackResult, int i);

    public void initialize(Context context) {
        IMLogger.d("Feedback initialize begin");
        try {
            mContext = context;
        } catch (Exception e) {
            IMLogger.d(e.getMessage());
        }
    }

    public void setListener(IMFeedbackListener listener) {
        IMLogger.d("Feedback setListener begin");
        try {
            if (instance != null) {
                this.currentListener = listener;
                if (listener != null) {
                    instance.setListener(listener);
                    return;
                }
                return;
            }
            IMLogger.w("setListener failed, current instance is null");
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public IMFeedbackListener getListener() {
        return this.currentListener;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00f6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setChannel(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Feedback setChannel begin"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r2)
            r5.currentChannel = r6
            java.lang.String r1 = ""
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L_0x0028
            java.lang.String r2 = "IMSDK"
            boolean r2 = r2.equalsIgnoreCase(r6)
            if (r2 == 0) goto L_0x009d
        L_0x0028:
            java.lang.String r1 = "IMFeedbackHttp"
        L_0x002a:
            r5.module = r1
            java.lang.String r2 = "com.tencent.imsdk.feedback.%s"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r1
            java.lang.String r2 = java.lang.String.format(r2, r3)
            r5.className = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "className:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r5.className
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00da }
            r2.<init>()     // Catch:{ Exception -> 0x00da }
            java.lang.String r3 = "try to get class : "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00da }
            java.lang.String r3 = r5.className     // Catch:{ Exception -> 0x00da }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00da }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00da }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r2)     // Catch:{ Exception -> 0x00da }
            if (r6 == 0) goto L_0x0076
            if (r6 == 0) goto L_0x00cb
            java.lang.String r2 = "IMSDK"
            boolean r2 = r6.equalsIgnoreCase(r2)     // Catch:{ Exception -> 0x00da }
            if (r2 == 0) goto L_0x00cb
        L_0x0076:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00da }
            r2.<init>()     // Catch:{ Exception -> 0x00da }
            java.lang.String r3 = r5.className     // Catch:{ Exception -> 0x00da }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00da }
            java.lang.String r3 = "is not extend from Feedbase"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00da }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00da }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r2)     // Catch:{ Exception -> 0x00da }
            r2 = 0
            instance = r2     // Catch:{ Exception -> 0x00da }
        L_0x0091:
            com.tencent.imsdk.feedback.base.FeedbackBase r2 = instance
            if (r2 == 0) goto L_0x00f6
            com.tencent.imsdk.feedback.base.FeedbackBase r2 = instance
            android.content.Context r3 = mContext
            r2.initialize(r3)
        L_0x009c:
            return
        L_0x009d:
            java.lang.String r2 = "zalo"
            boolean r2 = r2.equalsIgnoreCase(r6)
            if (r2 == 0) goto L_0x00a8
            java.lang.String r1 = "zalo.ZaloFeedbackHelper"
            goto L_0x002a
        L_0x00a8:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r6.toLowerCase()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "."
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r3 = "FeedbackHelper"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r2.toString()
            goto L_0x002a
        L_0x00cb:
            com.tencent.imsdk.IMModules r2 = com.tencent.imsdk.IMModules.getInstance()     // Catch:{ Exception -> 0x00da }
            java.lang.String r3 = r5.className     // Catch:{ Exception -> 0x00da }
            java.lang.Object r2 = r2.getModule((java.lang.String) r3)     // Catch:{ Exception -> 0x00da }
            com.tencent.imsdk.feedback.base.FeedbackBase r2 = (com.tencent.imsdk.feedback.base.FeedbackBase) r2     // Catch:{ Exception -> 0x00da }
            instance = r2     // Catch:{ Exception -> 0x00da }
            goto L_0x0091
        L_0x00da:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Feedback getInstance catch exception : "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r0.getMessage()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r2)
            goto L_0x0091
        L_0x00f6:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "get class : "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r5.className
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " failed !"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r2)
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.feedback.api.Feedback.setChannel(java.lang.String):void");
    }

    @Deprecated
    public void sendFeedBack(String content) {
        if (mContext == null) {
            IMLogger.e("Please executed initialize() first");
        } else if (instance != null) {
            instance.sendFeedBack(content);
        }
    }

    public void showHelpCenter() {
        if (mContext == null) {
            IMLogger.w("Please executed initialize() first");
        } else if (this.className == null) {
            IMLogger.w("Please executed setChannel(String moduleName) first");
        } else if (this.className.contains("IMFeedbackHttp")) {
            try {
                Intent intent = new Intent(mContext, Class.forName(String.format(PACKAGE_NAME_BASE_FORMAT, new Object[]{"IMFeedbackActivity"})));
                try {
                    if (this.lang != null) {
                        intent.putExtra(LANGUAGE_STRING_EXTRA, this.lang);
                    }
                    if (this.zone != null) {
                        intent.putExtra(ZONE_STRING_EXTRA, this.zone);
                    }
                    if (this.character != null) {
                        intent.putExtra(CHARACTER_STRING_EXTRA, this.character);
                    }
                    mContext.startActivity(intent);
                    Intent intent2 = intent;
                } catch (ClassNotFoundException e) {
                    e = e;
                    Intent intent3 = intent;
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e2) {
                e = e2;
                e.printStackTrace();
            }
        } else {
            IMLogger.d("showHelpCenter getInstance start:" + this.className);
            if (instance != null) {
                IMLogger.d("showHelpCenter getInstance");
                instance.showHelpCenter();
                IMLogger.d("showHelpCenter showHelpCenter");
                return;
            }
            IMLogger.e("instance is null,pls check  setChannel() ");
        }
    }

    public void showHelpCenter(String content) {
        if (mContext == null) {
            IMLogger.e("Please executed initialize() first");
        } else if (this.className == null) {
            IMLogger.e("Please executed setChannel(String moduleName) first");
        } else if (this.className.contains("IMFeedbackHttp")) {
            try {
                Intent intent = new Intent(mContext, Class.forName(String.format(PACKAGE_NAME_BASE_FORMAT, new Object[]{"IMFeedbackActivity"})));
                try {
                    if (this.lang != null) {
                        intent.putExtra(LANGUAGE_STRING_EXTRA, this.lang);
                    }
                    if (this.zone != null) {
                        intent.putExtra(ZONE_STRING_EXTRA, this.zone);
                    }
                    if (this.character != null) {
                        intent.putExtra(CHARACTER_STRING_EXTRA, this.character);
                    }
                    mContext.startActivity(intent);
                    Intent intent2 = intent;
                } catch (ClassNotFoundException e) {
                    e = e;
                    Intent intent3 = intent;
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e2) {
                e = e2;
                e.printStackTrace();
            }
        } else {
            IMLogger.d("showHelpCenter getInstance start:" + this.className);
            if (instance != null) {
                IMLogger.d("showHelpCenter getInstance");
                instance.showHelpCenter(content);
                IMLogger.d("showHelpCenter showHelpCenter");
                return;
            }
            IMLogger.e("instance is null,pls check  setChannel() ");
        }
    }

    public void setLanguage(String lang2) {
        this.lang = lang2;
        if (instance != null) {
            instance.setLanguage(lang2);
        }
    }

    public void setZone(String zone2) {
        this.zone = zone2;
        try {
            if (instance != null) {
                instance.setZone(zone2);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void setCharacter(String character2) {
        this.character = character2;
        if (character2 != null) {
            try {
                instance.setCharacter(character2);
            } catch (Exception e) {
                IMLogger.w(e.getMessage());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void getLatestFeedback(final int listenerTag) {
        getLatestFeedback((IUnreadMessage.UnreadMessageListener) new IUnreadMessage.UnreadMessageListener() {
            public void getUnreadMessageCount(IMFeedbackResult feedbackResult) {
                IMLogger.d("listenerTag = " + listenerTag + " , feedbackResult = " + IMErrorDef.getErrorString(feedbackResult.retCode));
                Feedback.this.onFeedbackResult(feedbackResult, listenerTag);
            }
        });
    }

    public void getLatestFeedback(IUnreadMessage.UnreadMessageListener listener) {
        if (TextUtils.isEmpty(this.currentChannel) || this.currentChannel.equalsIgnoreCase("IMSDK")) {
            IMFeedbackResult feedbackResult = new IMFeedbackResult();
            if (listener == null) {
                IMLogger.e("UnreadMessageListener cannot be null");
            } else if (mContext == null) {
                IMLogger.e("Please executed initialize() first");
                feedbackResult.retCode = 11;
                feedbackResult.imsdkRetCode = 17;
                feedbackResult.count = -1;
                listener.getUnreadMessageCount(feedbackResult);
            } else if (this.className == null) {
                IMLogger.e("Please executed setChannel(String moduleName) first");
                feedbackResult.retCode = 9;
                feedbackResult.imsdkRetCode = 9;
                feedbackResult.count = -1;
                listener.getUnreadMessageCount(feedbackResult);
            } else {
                IMLogger.d("className = " + this.className);
                IUnreadMessage instance2 = (IUnreadMessage) IMModules.getInstance().getModule(this.className);
                if (instance2 == null) {
                    feedbackResult.retCode = 9;
                    feedbackResult.imsdkRetCode = 9;
                    feedbackResult.count = -1;
                    listener.getUnreadMessageCount(feedbackResult);
                    return;
                }
                instance2.getUnreadMessage(mContext, listener);
            }
        } else {
            FeedbackBase instance3 = (FeedbackBase) IMModules.getInstance().getModule(String.format(PACKAGE_NAME_BASE_FORMAT, new Object[]{this.module}));
            if (instance3 != null) {
                instance3.getLatestFeedback(listener);
            } else {
                IMLogger.d("getLatestFeedback instance is null !");
            }
        }
    }

    public void setLevel(String level) {
        try {
            if (instance != null) {
                instance.setLevel(level);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void setServerName(String serverName) {
        try {
            if (instance != null) {
                instance.setServerName(serverName);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void setServerId(String serverId) {
        try {
            if (instance != null) {
                instance.setServerId(serverId);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void setRoleName(String roleName) {
        try {
            if (instance != null) {
                instance.setRoleName(roleName);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void setRoleId(String roleId) {
        try {
            if (instance != null) {
                instance.setRoleId(roleId);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public String getLevel() {
        try {
            if (instance != null) {
                return instance.getLevel();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getServerName() {
        try {
            if (instance != null) {
                return instance.getServerName();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getServerId() {
        try {
            if (instance != null) {
                return instance.getServerId();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getRoleName() {
        try {
            if (instance != null) {
                return instance.getRoleName();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getRoleId() {
        try {
            if (instance != null) {
                return instance.getRoleId();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getZone() {
        try {
            if (instance != null) {
                return instance.getZone();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getLanguage() {
        try {
            if (instance != null) {
                return instance.getLanguage();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public String getCharacter() {
        try {
            if (instance != null) {
                return instance.getCharacter();
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
        return "";
    }

    public void showHelpCenter(String channel, String extraJson) {
        try {
            if (instance != null) {
                instance.showHelpCenter(channel, extraJson);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void showFAQ(String extraJson) {
        try {
            if (instance != null) {
                instance.showFAQ(extraJson);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }

    public void showCustomerService(String extraJson) {
        try {
            if (instance != null) {
                instance.showCustomerService(extraJson);
            }
        } catch (Exception e) {
            IMLogger.w(e.getMessage());
        }
    }
}
