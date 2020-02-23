package com.tencent.imsdk.unity.game;

import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.game.api.IMGame;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;

public class GameHelper extends IMGame {
    private static volatile String unityGameObject = "Tencent.iMSDK.IMGame";

    public static boolean initialize() {
        currentContext = UnityPlayer.currentActivity;
        return initialize(currentContext);
    }

    protected static void sendUnityMessage(int callbackTag, String method, IMResult result) {
        try {
            IMLogger.d("send unity message : " + result.toUnityString());
            UnityPlayer.UnitySendMessage(unityGameObject, method, callbackTag + "|" + result.toUnityString());
        } catch (JSONException e) {
            IMLogger.e("convert IMResult to unity string error : " + e.getMessage());
        }
    }

    protected static boolean checkEnv(int callbackTag, String method) {
        IMResult imResult;
        if (gameInstance != null) {
            return true;
        }
        IMResult imResult2 = new IMResult(IMErrorDef.NOPACKAGE);
        if (currentContext != null) {
            imResult = IMRetCode.rebuild(imResult2, 17, -1, (String) null, (String) null);
        } else if (currentChannel == null || currentChannel.length() <= 0) {
            imResult = IMRetCode.rebuild(imResult2, 18, -1, (String) null, (String) null);
        } else {
            imResult = IMRetCode.rebuild(imResult2, 9, -1, (String) null, (String) null);
        }
        sendUnityMessage(callbackTag, method, imResult);
        return false;
    }

    protected static IMCallback<IMResult> newCallback(final int callbackTag, final String callbackFunction, final String logTag) {
        return new IMCallback<IMResult>() {
            public void onSuccess(IMResult result) {
                IMLogger.d(logTag + " success");
                GameHelper.sendUnityMessage(callbackTag, callbackFunction, IMRetCode.buildSuccess());
            }

            public void onCancel() {
                IMLogger.d(logTag + " canceled");
                GameHelper.sendUnityMessage(callbackTag, callbackFunction, IMRetCode.buildCancel());
            }

            public void onError(IMException exception) {
                IMLogger.d(logTag + " error : " + exception.getMessage());
                GameHelper.sendUnityMessage(callbackTag, callbackFunction, IMRetCode.rebuild(new IMResult(exception.errorCode, exception.getMessage()), exception.imsdkRetCode, exception.thirdRetCode, exception.thirdRetMsg, exception.retExtraJson));
            }
        };
    }

    public static void unitySetup(int callbackTag) {
        IMLogger.d("in unity setup : " + callbackTag);
        if (checkEnv(callbackTag, "OnSetup")) {
            gameInstance.setup(newCallback(callbackTag, "OnSetup", "unity setup"));
        }
    }

    public static void unitySubmitScore(int callbackTag, String board, int score) {
        IMLogger.d("in unity set score : " + callbackTag + ", " + board + ", " + score);
        if (checkEnv(callbackTag, "OnSetScore")) {
            gameInstance.submitScore(board, score, newCallback(callbackTag, "OnSetScore", "unity set score"));
        }
    }

    public static void unityIsGMSInstalled(final int callbackTag) {
        IMLogger.d("in unity check gms installed : " + callbackTag);
        GameHandler.getHandler().post(new Runnable() {
            public void run() {
                if (IMGame.isInstalledPlayServices()) {
                    GameHelper.sendUnityMessage(callbackTag, "OnCheckInstall", IMRetCode.buildSuccess());
                    return;
                }
                IMResult imResult = new IMResult(IMErrorDef.NOSUPPORT);
                IMRetCode.rebuild(imResult, 14, -1, (String) null, (String) null);
                GameHelper.sendUnityMessage(callbackTag, "OnCheckInstall", imResult);
            }
        });
    }

    public static void unityUnlockAchieve(int callbackTag, String achieve, int count) {
        IMLogger.d("in unity unlock achieve : " + callbackTag + ", " + achieve + ", " + count);
        if (checkEnv(callbackTag, "OnUnlockAchieve")) {
            gameInstance.achieve(achieve, count, newCallback(callbackTag, "OnUnlockAchieve", "unity unlock achieve"));
        }
    }

    public static void unityShowAchieves(int callbackTag) {
        IMLogger.d("in unity show achieve : " + callbackTag);
        if (checkEnv(callbackTag, "OnShowAchieve")) {
            gameInstance.showAchieve(newCallback(callbackTag, "OnShowAchieve", "unity unlock achieve"));
        }
    }

    public static void unitySetScore(int callbackTag, String board, int score) {
        IMLogger.d("in unity set score : " + callbackTag + ", " + board + ", " + score);
        if (checkEnv(callbackTag, "OnSetScore")) {
            gameInstance.submitScore(board, score, newCallback(callbackTag, "OnSetScore", "unity set score"));
        }
    }

    public static void unityShowLeaderBoard(int callbackTag, String board) {
        IMLogger.d("in unity show leader board : " + callbackTag + ", " + board);
        if (checkEnv(callbackTag, "OnShowLeaderBoard")) {
            gameInstance.showLeaderBoard(board, newCallback(callbackTag, "OnShowLeaderBoard", "unity show leader board"));
        }
    }
}
