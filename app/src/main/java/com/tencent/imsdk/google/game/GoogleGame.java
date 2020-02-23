package com.tencent.imsdk.google.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestBuffer;
import com.google.android.gms.games.quest.QuestUpdateListener;
import com.google.android.gms.games.quest.Quests;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMProxyRunner;
import com.tencent.imsdk.IMProxyTask;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.game.api.IMGameListener;
import com.tencent.imsdk.game.base.IMAchievementResult;
import com.tencent.imsdk.game.base.IMGameBase;
import com.tencent.imsdk.game.base.IMGameInfo;
import com.tencent.imsdk.game.base.IMGoogleGamePlayer;
import com.tencent.imsdk.game.base.IMLeaderBoardResult;
import com.tencent.imsdk.game.base.IMLeaderboardVariant;
import com.tencent.imsdk.game.base.IMQuestResult;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.setup.google.GoogleSetup;
import java.util.ArrayList;
import java.util.Properties;

public class GoogleGame extends IMGameBase implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int LOGIN_REQUEST_CODE = 65537;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 65538;
    private static final int REQUEST_ACHIEVEMENTS = 2;
    private static final int REQUEST_LEADERBOARD = 1;
    private static final int REQUEST_QUESTS = 3;
    /* access modifiers changed from: private */
    public IMGameListener currentCallback = null;
    /* access modifiers changed from: private */
    public Context currentContext;
    /* access modifiers changed from: private */
    public GoogleSetup googleSetup = null;
    protected GoogleApiClient mGoogleApiClient;
    protected IMInnerStat mInnerStat;
    /* access modifiers changed from: private */
    public boolean mResolvingConnectionFailure = false;
    protected IMCallback<IMResult> setupCallback = null;

    /* access modifiers changed from: protected */
    public String getStatOpenId() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult.retCode == 1 || loginResult.imsdkRetCode == 1) {
            return loginResult.openId;
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public String getStatId() {
        return "game_google";
    }

    /* access modifiers changed from: protected */
    public String getStatVersion() {
        return "1.11.2";
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties, boolean encrypt) {
        if (this.mInnerStat == null && this.currentContext != null) {
            this.mInnerStat = new IMInnerStat(this.currentContext, getStatVersion());
            this.mInnerStat.reportEvent(getStatId(), true, "create", "create", "success", getStatOpenId(), new Properties());
        }
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(getStatId(), false, function, stage, result, getStatOpenId(), properties, encrypt);
        }
    }

    public void setIMGoogleCallback(IMGameListener callback) {
        boolean z;
        this.currentCallback = callback;
        if (callback != null) {
            z = true;
        } else {
            z = false;
        }
        reportEvent("setIMGoogleCallback", "set callback", "success", IMInnerStat.convertProperties(z), false);
    }

    /* access modifiers changed from: protected */
    public void callbackBySuccess(ArrayList<IMResult> results) {
        if (this.currentCallback != null) {
            if (results == null) {
                results = new ArrayList<>();
            }
            this.currentCallback.onSuccess(results);
        }
    }

    /* access modifiers changed from: protected */
    public void callbackByException(IMException exception) {
        if (this.currentCallback != null) {
            if (exception == null) {
                exception = new IMException(3, 3);
            }
            this.currentCallback.onError(exception);
        }
    }

    abstract class InnerTask {
        /* access modifiers changed from: package-private */
        public abstract void run(IMCallback<IMResult> iMCallback);

        InnerTask() {
        }
    }

    /* access modifiers changed from: protected */
    public void runTask(InnerTask innerTask) {
        innerTask.run(new IMCallback<IMResult>() {
            public void onSuccess(IMResult result) {
                GoogleGame.this.callbackBySuccess(new ArrayList());
            }

            public void onCancel() {
                GoogleGame.this.callbackByException(IMRetCode.rebuild(new IMException(2), 2, -1, (String) null, (String) null));
            }

            public void onError(IMException exception) {
                GoogleGame.this.callbackByException(exception);
            }
        });
    }

    public boolean initialize(Context context) {
        this.currentContext = context;
        if (this.currentContext != null) {
            IMConfig.initialize(this.currentContext);
        }
        this.mGoogleApiClient = new GoogleApiClient.Builder(this.currentContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Games.API).addScope(Games.SCOPE_GAMES).build();
        this.googleSetup = new GoogleSetup();
        boolean retFlag = this.googleSetup.initialize(context);
        reportEvent("initialize", "check google env", retFlag ? "success" : "error", new Properties(), false);
        return retFlag;
    }

    public void setup() {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.setup(callback);
            }
        });
    }

    public void setup(final IMCallback<IMResult> callback) {
        this.mResolvingConnectionFailure = false;
        this.setupCallback = callback;
        if (this.mGoogleApiClient.isConnected()) {
            IMResult imResult = new IMResult(1);
            if (callback != null) {
                reportEvent("setup", "success", "success", new Properties(), false);
                callback.onSuccess(IMRetCode.rebuildForSuccess(imResult));
                return;
            }
            return;
        }
        this.googleSetup.fix(this.googleSetup.check(), new IMCallback<IMResult>() {
            public void onSuccess(IMResult result) {
                if (GoogleGame.this.mGoogleApiClient != null) {
                    GoogleGame.this.mGoogleApiClient.connect();
                    GoogleGame.this.reportEvent("setup", "call google connect", "success", new Properties(), false);
                }
            }

            public void onCancel() {
                if (GoogleGame.this.googleSetup.check() == 0) {
                    if (GoogleGame.this.mGoogleApiClient != null) {
                        GoogleGame.this.mGoogleApiClient.connect();
                    }
                    GoogleGame.this.reportEvent("setup", "call google connect", "success", new Properties(), false);
                    return;
                }
                ((Activity) GoogleGame.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        IMException imException = new IMException(1004);
                        if (callback != null) {
                            callback.onError(IMRetCode.rebuild(imException, 2, -1, (String) null, (String) null));
                        }
                        GoogleGame.this.reportEvent("setup", "canceled", "cancel", IMInnerStat.convertProperties(imException), false);
                    }
                });
            }

            public void onError(IMException exception) {
                if (callback != null) {
                    callback.onError(exception);
                }
                GoogleGame.this.reportEvent("setup", "error", "error", IMInnerStat.convertProperties(exception), false);
            }
        });
    }

    public boolean available() {
        if (this.mGoogleApiClient.isConnected()) {
            return true;
        }
        reportEvent("available", "not available", "success", new Properties(), false);
        return false;
    }

    public void quit() {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.quit(callback);
            }
        });
    }

    public void quit(final IMCallback<IMResult> callback) {
        IMLogger.d("in google game quit");
        if (this.mGoogleApiClient == null) {
            IMLogger.d("in google game quit");
            IMException exception = new IMException(10);
            if (callback != null) {
                callback.onError(IMRetCode.rebuild(exception, 14, -1, (String) null, (String) null));
            }
        } else if (this.mGoogleApiClient.isConnected()) {
            Games.signOut(this.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                public void onResult(Status status) {
                    IMLogger.d("in google game sign out callback : " + status.getStatusCode() + "," + status.getStatusMessage());
                    if (status.isSuccess()) {
                        IMResult imResult = new IMResult(1);
                        if (callback != null) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(imResult));
                        }
                    } else if (status.isCanceled()) {
                        IMException exception = new IMException(2);
                        if (callback != null) {
                            callback.onError(IMRetCode.rebuild(exception, 2, -1, (String) null, (String) null));
                        }
                    } else {
                        IMException exception2 = new IMException(3);
                        if (callback != null) {
                            callback.onError(IMRetCode.rebuild(exception2, 9999, status.getStatusCode(), status.getStatusMessage(), (String) null));
                        }
                    }
                    GoogleGame.this.mGoogleApiClient.disconnect();
                }
            });
        } else {
            IMException exception2 = new IMException(2);
            if (callback != null) {
                callback.onError(IMRetCode.rebuild(exception2, 2, -1, (String) null, (String) null));
            }
        }
    }

    public void submitScore(final String category, final int score) {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.submitScore(category, score, callback);
            }
        });
    }

    public void submitScore(String category, int score, final IMCallback<IMResult> callback) {
        if (!this.mGoogleApiClient.isConnected()) {
            IMException exception = new IMException(10);
            if (callback != null) {
                callback.onError(IMRetCode.rebuild(exception, 14, -1, (String) null, (String) null));
                return;
            }
            return;
        }
        try {
            Games.Leaderboards.submitScoreImmediate(this.mGoogleApiClient, category, (long) score).setResultCallback(new ResultCallback<Leaderboards.SubmitScoreResult>() {
                public void onResult(Leaderboards.SubmitScoreResult submitScoreResult) {
                    if (submitScoreResult.getStatus().isSuccess()) {
                        IMResult imResult = new IMResult(1);
                        if (callback != null) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(imResult));
                            return;
                        }
                        return;
                    }
                    IMException exception = new IMException(3, "submit score error : " + submitScoreResult.getStatus().getStatusCode());
                    if (callback != null) {
                        callback.onError(IMRetCode.rebuild(exception, 9999, submitScoreResult.getStatus().getStatusCode(), submitScoreResult.getStatus().getStatusMessage(), (String) null));
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e("submit score get error : " + e.getMessage());
            if (callback != null) {
                callback.onError(IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, (String) null, (String) null));
            }
        }
    }

    public void achieve(final String achieveId, final int count) {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.achieve(achieveId, count, callback);
            }
        });
    }

    public void achieve(String achieveId, int count, final IMCallback<IMResult> callback) {
        if (!this.mGoogleApiClient.isConnected()) {
            IMException exception = new IMException(10);
            if (callback != null) {
                callback.onError(IMRetCode.rebuild(exception, 14, -1, (String) null, (String) null));
            }
        } else if (count >= 0) {
            try {
                IMLogger.d("unlock achieve : " + achieveId + " " + count + " times");
                Games.Achievements.incrementImmediate(this.mGoogleApiClient, achieveId, count).setResultCallback(new ResultCallback<Achievements.UpdateAchievementResult>() {
                    public void onResult(Achievements.UpdateAchievementResult updateAchievementResult) {
                        if (updateAchievementResult.getStatus().isSuccess()) {
                            IMResult imResult = new IMResult(1);
                            if (callback != null) {
                                callback.onSuccess(IMRetCode.rebuildForSuccess(imResult));
                                return;
                            }
                            return;
                        }
                        IMException imException = new IMException(3, "unlock achieve error : " + updateAchievementResult.getStatus().getStatusCode());
                        if (callback != null) {
                            callback.onError(IMRetCode.rebuild(imException, 9999, updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getStatus().getStatusMessage(), (String) null));
                        }
                    }
                });
            } catch (Exception e) {
                IMLogger.e("unlock achievement error : " + e.getMessage());
                IMException imException = new IMException(3, e.getMessage());
                if (callback != null) {
                    callback.onError(IMRetCode.rebuild(imException, 3, -1, (String) null, (String) null));
                }
            }
        } else {
            IMLogger.d("unlock achieve : " + achieveId);
            Games.Achievements.unlockImmediate(this.mGoogleApiClient, achieveId).setResultCallback(new ResultCallback<Achievements.UpdateAchievementResult>() {
                public void onResult(Achievements.UpdateAchievementResult updateAchievementResult) {
                    if (updateAchievementResult.getStatus().isSuccess()) {
                        IMResult imResult = new IMResult(1);
                        if (callback != null) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(imResult));
                            return;
                        }
                        return;
                    }
                    IMException imException = new IMException(3, "unlock achieve error : " + updateAchievementResult.getStatus().getStatusCode());
                    if (callback != null) {
                        callback.onError(IMRetCode.rebuild(imException, 9999, updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getStatus().getStatusMessage(), (String) null));
                    }
                }
            });
        }
    }

    public void event(final String event, final int count) {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.event(event, count, callback);
            }
        });
    }

    public void event(String event, int count, IMCallback<IMResult> callback) {
        if (this.mGoogleApiClient.isConnected()) {
            try {
                IMLogger.d("event id = " + event + " , count = " + count);
                Games.Events.increment(this.mGoogleApiClient, event, count);
                if (callback != null) {
                    callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
                }
            } catch (Exception e) {
                IMLogger.e("event error : " + e.getMessage());
                if (callback != null) {
                    callback.onError(IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, (String) null, (String) null));
                }
            }
        } else if (callback != null) {
            callback.onError(IMRetCode.rebuild(new IMException(10), 14, -1, (String) null, (String) null));
        }
    }

    public void showAchieve() {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.showAchieve(callback);
            }
        });
    }

    public void showAchieve(final IMCallback<IMResult> callback) {
        if (this.mGoogleApiClient.isConnected()) {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
                protected boolean callbacked = false;

                public void onPreProxy() {
                    IMLogger.d("show achieve ... ");
                }

                public void onPostProxy(Activity activity) {
                    try {
                        activity.startActivityForResult(Games.Achievements.getAchievementsIntent(GoogleGame.this.mGoogleApiClient), 2);
                    } catch (Exception e) {
                        IMLogger.e("show achieve error : " + e.getMessage());
                        if (callback != null && !this.callbacked) {
                            callback.onError(IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, (String) null, (String) null));
                            this.callbacked = true;
                        }
                        activity.finish();
                    }
                }

                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    IMLogger.d("achieve board activity result : " + resultCode);
                    if (callback != null && !this.callbacked) {
                        if (resultCode == -1 || resultCode == 0) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
                        } else if (resultCode == 10001) {
                            GoogleGame.this.mGoogleApiClient.disconnect();
                            callback.onError(IMRetCode.rebuild(new IMException(10), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        } else {
                            callback.onError(IMRetCode.rebuild(new IMException(3), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        }
                    }
                }
            });
        } else if (callback != null) {
            callback.onError(IMRetCode.rebuild(new IMException(10), 14, -1, (String) null, (String) null));
        }
    }

    public void showLeaderBoard(final String id) {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.showLeaderBoard(id, callback);
            }
        });
    }

    public void showLeaderBoard(final String id, final IMCallback<IMResult> callback) {
        if (this.mGoogleApiClient.isConnected()) {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
                protected boolean callbacked = false;

                public void onPreProxy() {
                    IMLogger.d("show leader board ... ");
                }

                public void onPostProxy(Activity activity) {
                    try {
                        activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(GoogleGame.this.mGoogleApiClient, id), 1);
                    } catch (Exception e) {
                        IMLogger.e("show leader board error : " + e.getMessage());
                        if (callback != null && !this.callbacked) {
                            callback.onError(IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, (String) null, (String) null));
                            this.callbacked = true;
                        }
                        activity.finish();
                    }
                }

                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    IMLogger.d("leader board activity result : " + resultCode);
                    if (callback != null && !this.callbacked) {
                        if (resultCode == -1 || resultCode == 0) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
                        } else if (resultCode == 10001) {
                            GoogleGame.this.mGoogleApiClient.disconnect();
                            callback.onError(IMRetCode.rebuild(new IMException(10), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        } else {
                            callback.onError(IMRetCode.rebuild(new IMException(3), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        }
                    }
                }
            });
        } else if (callback != null) {
            callback.onError(IMRetCode.rebuild(new IMException(10), 14, -1, (String) null, (String) null));
        }
    }

    public void showQuests() {
        runTask(new InnerTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMResult> callback) {
                GoogleGame.this.showQuests(callback);
            }
        });
    }

    public void showQuests(final IMCallback<IMResult> callback) {
        if (this.mGoogleApiClient.isConnected()) {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
                protected boolean callbacked = false;

                public void onPreProxy() {
                    IMLogger.d("show quests ... ");
                }

                public void onPostProxy(Activity activity) {
                    try {
                        activity.startActivityForResult(Games.Quests.getQuestsIntent(GoogleGame.this.mGoogleApiClient, Quests.SELECT_ALL_QUESTS), 3);
                    } catch (Exception e) {
                        IMLogger.e("show quests error : " + e.getMessage());
                        if (callback != null && !this.callbacked) {
                            callback.onError(IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, (String) null, (String) null));
                            this.callbacked = true;
                        }
                        activity.finish();
                    }
                }

                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    IMLogger.d("quests activity result : " + resultCode);
                    if (callback != null && !this.callbacked) {
                        if (resultCode == -1 || resultCode == 0) {
                            callback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
                        } else if (resultCode == 10001) {
                            GoogleGame.this.mGoogleApiClient.disconnect();
                            callback.onError(IMRetCode.rebuild(new IMException(10), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        } else {
                            callback.onError(IMRetCode.rebuild(new IMException(10), 9999, resultCode, "activity result : " + resultCode, (String) null));
                        }
                    }
                }
            });
        } else if (callback != null) {
            callback.onError(IMRetCode.rebuild(new IMException(10), 14, -1, (String) null, (String) null));
        }
    }

    public void onConnected(Bundle bundle) {
        IMLogger.d("google game service connected");
        if (this.setupCallback != null) {
            this.setupCallback.onSuccess(IMRetCode.rebuildForSuccess(new IMResult(1)));
        }
        Games.Quests.registerQuestUpdateListener(this.mGoogleApiClient, new QuestUpdateListener() {
            public void onQuestCompleted(Quest quest) {
                Games.Quests.claim(GoogleGame.this.mGoogleApiClient, quest.getQuestId(), quest.getCurrentMilestone().getMilestoneId());
            }
        });
    }

    public void onConnectionSuspended(int i) {
        IMLogger.d("google game service connect suspend : " + i);
        if (this.setupCallback != null) {
            this.setupCallback.onError(IMRetCode.rebuild(new IMException(10, "ConnectionSuspended with return code " + i), 9999, i, "google play connect suspended : " + i, (String) null));
        }
    }

    public void onConnectionFailed(final ConnectionResult connectionResult) {
        IMLogger.e("google game service connect error : " + connectionResult.getErrorCode() + ", " + connectionResult.toString());
        if (!this.mResolvingConnectionFailure) {
            IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
                boolean callbacked = false;

                public void onPreProxy() {
                    IMLogger.d("try solve play connect problem ... ");
                }

                public void onPostProxy(Activity activity) {
                    boolean unused = GoogleGame.this.mResolvingConnectionFailure = true;
                    if (connectionResult.hasResolution()) {
                        try {
                            connectionResult.startResolutionForResult(activity, 65537);
                        } catch (IntentSender.SendIntentException e) {
                            if (GoogleGame.this.setupCallback != null && !this.callbacked) {
                                GoogleGame.this.setupCallback.onError(IMRetCode.rebuild(new IMException(1004, "connecct to google play error : " + e.getMessage()), 9999, connectionResult.getErrorCode(), connectionResult.getErrorMessage(), (String) null));
                            }
                            activity.finish();
                        }
                    } else {
                        if (GoogleGame.this.setupCallback != null && !this.callbacked) {
                            GoogleGame.this.setupCallback.onError(IMRetCode.rebuild(new IMException(1004, "connecct to google play error : " + connectionResult.getErrorMessage()), 9999, connectionResult.getErrorCode(), connectionResult.getErrorMessage(), (String) null));
                        }
                        activity.finish();
                    }
                }

                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (requestCode != 65537) {
                        return;
                    }
                    if (resultCode == -1) {
                        if (!GoogleGame.this.mGoogleApiClient.isConnected() && !GoogleGame.this.mGoogleApiClient.isConnecting()) {
                            GoogleGame.this.mGoogleApiClient.connect();
                        }
                    } else if (resultCode == 0) {
                        if (GoogleGame.this.setupCallback != null) {
                            GoogleGame.this.setupCallback.onError(IMRetCode.rebuild(new IMException(2), 2, -1, (String) null, (String) null));
                        }
                    } else if (GoogleGame.this.currentCallback != null) {
                        GoogleGame.this.currentCallback.onError(new IMException(3, "fix activity result : " + resultCode));
                    }
                }
            });
        } else if (this.setupCallback != null) {
            this.setupCallback.onError(IMRetCode.rebuild(new IMException(10, "ConnectionFailed with return code " + connectionResult.getErrorCode()), 9999, connectionResult.getErrorCode(), connectionResult.getErrorMessage(), (String) null));
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<IMResult> extractAchievementResult(Achievements.LoadAchievementsResult loadAchievementsResult) {
        ArrayList<IMResult> resultArrayList = new ArrayList<>();
        AchievementBuffer achievementBuffer = loadAchievementsResult.getAchievements();
        int count = achievementBuffer.getCount();
        for (int i = 0; i < count; i++) {
            IMAchievementResult achievementResult = new IMAchievementResult();
            IMGoogleGamePlayer player = new IMGoogleGamePlayer();
            Achievement achievement = achievementBuffer.get(i);
            Player googlePlayer = achievement.getPlayer();
            if (googlePlayer != null) {
                player.displayName = googlePlayer.getDisplayName();
                player.iconImageUrl = googlePlayer.getIconImageUrl();
                player.levelNumber = googlePlayer.getLevelInfo().getCurrentLevel().getLevelNumber();
                player.retrievedTimestamp = googlePlayer.getRetrievedTimestamp();
                player.playerId = googlePlayer.getPlayerId();
                player.title = googlePlayer.getTitle();
            }
            if (achievement.getType() == 1) {
                achievementResult.currentSteps = achievement.getCurrentSteps();
                achievementResult.totalSteps = achievement.getTotalSteps();
            }
            achievementResult.retCode = 1;
            achievementResult.retMsg = loadAchievementsResult.getStatus().getStatusMessage();
            achievementResult.achievementId = achievement.getAchievementId();
            achievementResult.revealedImageUrl = achievement.getRevealedImageUrl();
            achievementResult.achievementName = achievement.getName();
            achievementResult.unlockedImageUrl = achievement.getUnlockedImageUrl();
            achievementResult.state = achievement.getState();
            achievementResult.player = player;
            achievementResult.description = achievement.getDescription();
            resultArrayList.add(achievementResult);
        }
        return resultArrayList;
    }

    public void getAchieve(final IMGameListener callback, boolean forceReload) {
        if (this.mGoogleApiClient.isConnected()) {
            if (callback == null) {
                IMLogger.e("IMGoogleGameCallback must not be NULL");
            } else {
                Games.Achievements.load(this.mGoogleApiClient, forceReload).setResultCallback(new ResultCallback<Achievements.LoadAchievementsResult>() {
                    public void onResult(Achievements.LoadAchievementsResult loadAchievementsResult) {
                        if (loadAchievementsResult.getStatus().getStatusCode() == 0) {
                            ArrayList<IMResult> resultArrayList = GoogleGame.this.extractAchievementResult(loadAchievementsResult);
                            loadAchievementsResult.getAchievements().close();
                            callback.onSuccess(resultArrayList);
                            return;
                        }
                        callback.onError(new IMException(loadAchievementsResult.getStatus().getStatusCode(), "Fail query achievement, " + loadAchievementsResult.getStatus().getStatusMessage()));
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<IMResult> extractLeaderBoardMetadataResult(Leaderboards.LeaderboardMetadataResult leaderboardMetadataResult) {
        ArrayList<IMResult> resultArrayList = new ArrayList<>();
        LeaderboardBuffer leaderboardBuffer = leaderboardMetadataResult.getLeaderboards();
        int count = leaderboardBuffer.getCount();
        for (int i = 0; i < count; i++) {
            Leaderboard leaderboard = (Leaderboard) leaderboardBuffer.get(i);
            IMLeaderBoardResult leaderBoardResult = new IMLeaderBoardResult();
            leaderBoardResult.retCode = 1;
            leaderBoardResult.displayName = leaderboard.getDisplayName();
            leaderBoardResult.leaderBoardId = leaderboard.getLeaderboardId();
            leaderBoardResult.scoreOrder = leaderboard.getScoreOrder();
            leaderBoardResult.iconImageUrl = leaderboard.getIconImageUrl();
            ArrayList<LeaderboardVariant> variants = leaderboard.getVariants();
            ArrayList<IMLeaderboardVariant> variantArrayList = new ArrayList<>();
            for (int k = 0; k < variants.size(); k++) {
                IMLeaderboardVariant variant = new IMLeaderboardVariant();
                LeaderboardVariant tmpVariant = variants.get(k);
                variant.collection = tmpVariant.getCollection();
                if (tmpVariant.hasPlayerInfo()) {
                    variant.displayPlayerRank = tmpVariant.getDisplayPlayerRank();
                    variant.displayPlayerScore = tmpVariant.getDisplayPlayerScore();
                }
                variant.timeSpan = tmpVariant.getTimeSpan();
                variantArrayList.add(variant);
            }
            leaderBoardResult.leaderboardVariant = variantArrayList;
            resultArrayList.add(leaderBoardResult);
        }
        return resultArrayList;
    }

    public void getLeaderBoard(final IMGameListener callback, boolean forceReload, String leaderBoardId) {
        PendingResult<Leaderboards.LeaderboardMetadataResult> pendingResult;
        if (this.mGoogleApiClient.isConnected()) {
            if (callback == null) {
                IMLogger.e("IMGoogleGameCallback must not be NULL");
                return;
            }
            if (TextUtils.isEmpty(leaderBoardId)) {
                pendingResult = Games.Leaderboards.loadLeaderboardMetadata(this.mGoogleApiClient, forceReload);
            } else {
                pendingResult = Games.Leaderboards.loadLeaderboardMetadata(this.mGoogleApiClient, leaderBoardId, forceReload);
            }
            pendingResult.setResultCallback(new ResultCallback<Leaderboards.LeaderboardMetadataResult>() {
                public void onResult(Leaderboards.LeaderboardMetadataResult leaderboardMetadataResult) {
                    if (leaderboardMetadataResult.getStatus().getStatusCode() == 0) {
                        ArrayList<IMResult> resultArrayList = GoogleGame.this.extractLeaderBoardMetadataResult(leaderboardMetadataResult);
                        leaderboardMetadataResult.getLeaderboards().close();
                        callback.onSuccess(resultArrayList);
                        return;
                    }
                    callback.onError(new IMException(leaderboardMetadataResult.getStatus().getStatusCode(), "Fail query achievement , " + leaderboardMetadataResult.getStatus().getStatusMessage()));
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<IMResult> extractQuestsResult(Quests.LoadQuestsResult loadQuestsResult) {
        ArrayList<IMResult> questArrayList = new ArrayList<>();
        QuestBuffer questBuffer = loadQuestsResult.getQuests();
        int count = questBuffer.getCount();
        for (int j = 0; j < count; j++) {
            Quest quest = (Quest) questBuffer.get(j);
            IMQuestResult questResult = new IMQuestResult();
            Game gameTmp = quest.getGame();
            IMGameInfo gameInfo = new IMGameInfo();
            gameInfo.achievementCount = gameTmp.getAchievementTotalCount();
            gameInfo.leaderBoardCount = gameTmp.getLeaderboardCount();
            gameInfo.applicationId = gameTmp.getApplicationId();
            gameInfo.description = gameTmp.getDescription();
            gameInfo.displayName = gameTmp.getDisplayName();
            gameInfo.iconImageUrl = gameTmp.getIconImageUrl();
            gameInfo.primaryCategory = gameTmp.getPrimaryCategory();
            gameInfo.secondaryCategory = gameTmp.getSecondaryCategory();
            questResult.game = gameInfo;
            questResult.retCode = 1;
            questResult.acceptedTimestamp = quest.getAcceptedTimestamp();
            questResult.description = quest.getDescription();
            questResult.displayName = quest.getName();
            questResult.endTimestamp = quest.getEndTimestamp();
            questResult.lastUpdatedTimestamp = quest.getLastUpdatedTimestamp();
            questResult.startTimestamp = quest.getStartTimestamp();
            questResult.questId = quest.getQuestId();
            questResult.state = quest.getState();
            questArrayList.add(questResult);
        }
        return questArrayList;
    }

    public void getQuests(final IMGameListener callback, boolean forceReload, String... questIds) {
        PendingResult<Quests.LoadQuestsResult> pendingResult;
        if (this.mGoogleApiClient.isConnected()) {
            if (callback == null) {
                IMLogger.e("IMGoogleGameCallback must not be NULL");
                return;
            }
            if (questIds.length <= 0) {
                pendingResult = Games.Quests.load(this.mGoogleApiClient, Quests.SELECT_ALL_QUESTS, 0, forceReload);
            } else {
                pendingResult = Games.Quests.loadByIds(this.mGoogleApiClient, forceReload, questIds);
            }
            pendingResult.setResultCallback(new ResultCallback<Quests.LoadQuestsResult>() {
                public void onResult(Quests.LoadQuestsResult loadQuestsResult) {
                    if (loadQuestsResult.getStatus().getStatusCode() == 0) {
                        ArrayList<IMResult> resultArrayList = GoogleGame.this.extractQuestsResult(loadQuestsResult);
                        loadQuestsResult.getQuests().close();
                        callback.onSuccess(resultArrayList);
                        return;
                    }
                    callback.onError(new IMException(loadQuestsResult.getStatus().getStatusCode(), "Fail query achievement , " + loadQuestsResult.getStatus().getStatusMessage()));
                }
            });
        }
    }

    public boolean isInstalledPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this.currentContext);
        if (result == 0) {
            return true;
        }
        if (googleAPI.isUserResolvableError(result)) {
            googleAPI.getErrorDialog((Activity) this.currentContext, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
        }
        return false;
    }
}
