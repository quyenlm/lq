package com.tencent.imsdk.push.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.push.base.IMPushBase;
import com.tencent.imsdk.push.entity.IMLocalMessage;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;

public class IMPush {
    protected static String currentChannel = "";
    protected static Context currentContext = null;
    protected static IMPushListener currentListener = null;
    protected static IMPushBase pInstance = null;

    public static void initialize(Context context) {
        try {
            currentContext = context;
            IMConfig.initialize(currentContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getChanel() {
        return currentChannel;
    }

    public static boolean setChannel(String channel) {
        if (TextUtils.isEmpty(channel)) {
            IMLogger.e("channel is null,please check channel value");
            return false;
        } else if (currentContext == null) {
            IMLogger.e("initialize should be called before set channel !");
            return false;
        } else {
            currentChannel = channel;
            pInstance = getInstance(currentChannel);
            if (pInstance != null) {
                pInstance.initialize(currentContext);
                return true;
            }
            IMLogger.e("get channel  " + currentChannel + " instance failed !");
            return false;
        }
    }

    private static IMPushBase getInstance(String channel) {
        currentChannel = channel;
        IMLogger.d("switch channel to : " + channel);
        String platformClass = "com.tencent.imsdk.push." + channel.toLowerCase() + "." + currentChannel + "PushHelper";
        IMLogger.d("try to get class : " + platformClass);
        IMPushBase instance = (IMPushBase) IMModules.getInstance().getModule(platformClass);
        if (instance != null) {
            instance.initialize(currentContext);
        } else {
            IMLogger.e("get class : " + platformClass + " failed !");
        }
        return instance;
    }

    public static void setPushListener(IMPushListener pushListener) {
        try {
            if (pInstance != null) {
                currentListener = pushListener;
                pInstance.setPushListener(currentListener);
                return;
            }
            IMLogger.e("setPushListener fail,currentPushInstance is null");
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setCppPushListener() {
        try {
            IMPushListener pushListener = new IMPushListener() {
                public void OnUnregister(IMResult result) {
                    IMLogger.d("setCppPushListener onUnregister start");
                    IMPushNativeListener.OnUnregister(result.retCode, result.retMsg);
                    IMLogger.d("setCppPushListener onUnregister end");
                }

                public void OnSetTag(IMResult result) {
                    IMPushNativeListener.OnSetTag(result.retCode, result.retMsg);
                }

                public void OnRegister(IMResult result) {
                    IMLogger.d("setCppPushListener onRegister start");
                    IMPushNativeListener.OnRegister(result.retCode, result.retMsg);
                    IMLogger.d("setCppPushListener onRegister end");
                }

                public void OnNotifactionShow(String notifiShowedRlt) {
                    IMPushNativeListener.OnNotifactionShow(notifiShowedRlt);
                }

                public void OnNotifactionClick(String jsonMessage) {
                    IMPushNativeListener.OnNotifactionClick(jsonMessage);
                }

                public void OnDeleteTag(IMResult result) {
                    IMPushNativeListener.OnDeleteTag(result.retCode, result.retMsg);
                }

                public void OnNotification(String jsonMessage) {
                    IMPushNativeListener.OnNotification(jsonMessage);
                }
            };
            if (pInstance != null) {
                currentListener = pushListener;
                pInstance.setPushListener(currentListener);
                return;
            }
            IMLogger.e("setPushListener fail,currentPushInstance is null");
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void registerPush() {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.registerPush(currentContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPush(IMOperateCallback callback) {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.registerPush(currentContext, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPush(String account) {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.registerPush(currentContext, account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPush(String account, IMOperateCallback callback) {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.registerPush(currentContext, account, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPush(String account, String ticket, int ticketType, String qua, IMOperateCallback callback) {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.registerPush(currentContext, account, ticket, ticketType, qua, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterPush() {
        try {
            if (currentContext != null && pInstance != null) {
                pInstance.unregisterPush(currentContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTag(String tag) {
        try {
            if (!TextUtils.isEmpty(tag)) {
                pInstance.setTag(currentContext, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTag(ArrayList<String> tags) {
        try {
            IMLogger.d("setTag tags.size()==" + tags.size());
            if (!tags.isEmpty() && tags.size() != 0) {
                for (int i = 0; i < tags.size(); i++) {
                    IMLogger.d("setTag(ArrayList) tag[" + i + "]=" + tags.get(i));
                }
                if (currentContext != null && pInstance != null) {
                    for (int i2 = 0; i2 < tags.size(); i2++) {
                        pInstance.setTag(currentContext, tags.get(i2));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTag(String tag) {
        try {
            if (!TextUtils.isEmpty(tag)) {
                new ArrayList<>().add(tag);
                if (currentContext != null && pInstance != null && !TextUtils.isEmpty(tag)) {
                    pInstance.deleteTag(currentContext, tag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTag(ArrayList<String> tags) {
        try {
            if (!tags.isEmpty() && tags.size() != 0 && !tags.isEmpty() && tags.size() > 0 && currentContext != null && pInstance != null) {
                for (int i = 0; i < tags.size(); i++) {
                    pInstance.deleteTag(currentContext, tags.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addLocalMessage(IMLocalMessage localMessage) {
        if (localMessage != null) {
            try {
                if (pInstance != null) {
                    pInstance.addLocalNotification(localMessage);
                } else {
                    IMLogger.w("please execute initialize first");
                }
            } catch (Exception ex) {
                IMLogger.e(ex.getMessage());
            }
        }
    }

    public static void clearLocalNotifications() {
        try {
            if (pInstance != null) {
                pInstance.clearLocalNotifications();
            } else {
                IMLogger.w("please execute initialize first");
            }
        } catch (Exception ex) {
            IMLogger.e(ex.getMessage());
        }
    }
}
