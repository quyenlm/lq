package com.garena.android.gpns.strategy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import com.garena.android.gpns.strategy.BootStrategy;
import com.garena.android.gpns.utility.AppLogger;

public class CompetitiveBootStrategy extends BootStrategy {
    public static final int INTENT_ACTION_COMMAND_KILL = 2;
    public static final int INTENT_ACTION_COMMAND_QUERY = 1;
    public static final String INTENT_ACTION_KEY = "_action";
    public static final String INTENT_COMPONENT_NAME = "component";
    public static final String INTENT_PROCESS_ID = "process_id";
    public static final String INTENT_QUERY_INIT = "com.garena.android.gpns.enquiry";
    public static final String INTENT_QUERY_RESPONSE = "com.garena.android.gpns.check";
    public static final String INTENT_QUERY_VERSION = "query_version";
    public static final String INTENT_STATUS = "status_is_running";
    /* access modifiers changed from: private */
    public String candidatePackage = "";
    /* access modifiers changed from: private */
    public int count;
    IntentFilter filterResponse = new IntentFilter(INTENT_QUERY_RESPONSE);
    private Runnable finalCheck = new Runnable() {
        public void run() {
            if (CompetitiveBootStrategy.this.count > 0) {
                AppLogger.d("execute final check:" + CompetitiveBootStrategy.this.count);
                int unused = CompetitiveBootStrategy.this.count = 1;
                Intent fakeForForceStop = new Intent();
                fakeForForceStop.putExtra(CompetitiveBootStrategy.INTENT_STATUS, fakeForForceStop);
                fakeForForceStop.putExtra(CompetitiveBootStrategy.INTENT_QUERY_VERSION, CompetitiveBootStrategy.this.maxVersion);
                fakeForForceStop.putExtra(CompetitiveBootStrategy.INTENT_COMPONENT_NAME, new ComponentName(CompetitiveBootStrategy.this.candidatePackage, "com.garena.android.gpns.GNotificationService"));
                CompetitiveBootStrategy.this.onResponse(CompetitiveBootStrategy.this.mContext, fakeForForceStop);
            }
        }
    };
    private Handler handler;
    private boolean isWinnerRunning = false;
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            AppLogger.d("receive feedback!");
            CompetitiveBootStrategy.this.onResponse(context, intent);
        }
    };
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int maxVersion = 0;
    private ComponentName winningCandidateComponent;
    private int winningVersion = 0;

    public CompetitiveBootStrategy(BootStrategy.BootStrategyListener strategyListener) {
        this.mListener = strategyListener;
    }

    /* access modifiers changed from: protected */
    public void fireStrategy(Context applicationContext) {
        if (applicationContext != null) {
            this.mContext = applicationContext;
            Intent query = new Intent(INTENT_QUERY_INIT);
            query.putExtra(INTENT_ACTION_KEY, 1);
            query.putExtra(INTENT_COMPONENT_NAME, applicationContext.getPackageName());
            this.count = applicationContext.getPackageManager().queryBroadcastReceivers(query, 64).size();
            if (this.count == 0) {
                this.mListener.onStrategySuccess();
                return;
            }
            for (ApplicationInfo applicationInfo : applicationContext.getPackageManager().getInstalledApplications(128)) {
                if (applicationInfo.metaData != null) {
                    Integer version = Integer.valueOf(applicationInfo.metaData.getInt(ServiceLoaderIntentService.SERVICE_VERSION, -1));
                    if (version.intValue() > this.maxVersion || (version.intValue() == this.maxVersion && isSuperior(applicationInfo.packageName, this.candidatePackage))) {
                        AppLogger.d("we have a better choice now:" + applicationInfo.packageName);
                        this.maxVersion = version.intValue();
                        this.candidatePackage = applicationInfo.packageName;
                    } else {
                        AppLogger.d("service_version:" + version + " " + applicationInfo.packageName);
                    }
                }
            }
            this.handler = new Handler(Looper.getMainLooper());
            this.handler.postDelayed(this.finalCheck, 10000);
            applicationContext.getApplicationContext().registerReceiver(this.mBroadcastReceiver, this.filterResponse);
            applicationContext.sendBroadcast(query);
        }
    }

    /* access modifiers changed from: protected */
    public void onResponse(Context context, Intent intent) {
        this.count--;
        AppLogger.d("receive query response on" + context.getPackageName() + " count:" + this.count);
        boolean isRunning = intent.getBooleanExtra(INTENT_STATUS, false);
        int version = intent.getIntExtra(INTENT_QUERY_VERSION, 0);
        ComponentName componentName = (ComponentName) intent.getParcelableExtra(INTENT_COMPONENT_NAME);
        AppLogger.d("responder: " + componentName + " v:" + version + (isRunning ? " running" : " not running"));
        if (isRunning) {
            if (version < 4) {
                killService(context, componentName);
            } else if (version > this.winningVersion) {
                if (this.isWinnerRunning && this.winningCandidateComponent != null) {
                    killService(context, this.winningCandidateComponent);
                }
                this.winningVersion = version;
                this.winningCandidateComponent = componentName;
                AppLogger.d("a winner is:" + this.winningCandidateComponent.flattenToString());
                this.isWinnerRunning = true;
            } else if (isSuperior(componentName.getPackageName(), this.winningCandidateComponent.getPackageName())) {
                if (this.isWinnerRunning) {
                    killService(context, this.winningCandidateComponent);
                    this.isWinnerRunning = false;
                }
                this.winningVersion = version;
                this.winningCandidateComponent = componentName;
                AppLogger.d("b winner is:" + this.winningCandidateComponent.flattenToString());
                this.isWinnerRunning = true;
            } else {
                killService(context, componentName);
            }
        } else if (version > this.winningVersion) {
            if (this.isWinnerRunning && this.winningCandidateComponent != null) {
                killService(context, this.winningCandidateComponent);
            }
            this.winningCandidateComponent = componentName;
            AppLogger.d("c winner is:" + this.winningCandidateComponent.flattenToString());
            this.winningVersion = version;
            this.isWinnerRunning = false;
        } else if (version == this.winningVersion) {
            if (this.winningCandidateComponent == null) {
                this.winningCandidateComponent = componentName;
                AppLogger.d("c winner is:" + this.winningCandidateComponent.flattenToString());
                this.winningVersion = version;
                this.isWinnerRunning = false;
            } else if (isSuperior(componentName.getPackageName(), this.winningCandidateComponent.getPackageName())) {
                if (this.isWinnerRunning) {
                    killService(context, this.winningCandidateComponent);
                }
                this.winningCandidateComponent = componentName;
                AppLogger.d("c winner is:" + this.winningCandidateComponent.flattenToString());
                this.winningVersion = version;
                this.isWinnerRunning = false;
            }
        }
        if (this.count == 0) {
            AppLogger.i("get all feedback");
            this.handler.removeCallbacks(this.finalCheck);
            if (this.isWinnerRunning) {
                AppLogger.i("start & bind service:" + this.winningCandidateComponent.flattenToString());
                this.mListener.onStrategyFailure(this.winningCandidateComponent);
            } else {
                AppLogger.i("no service running");
                if (this.winningVersion <= 0 || context.getPackageName().equals(this.winningCandidateComponent.getPackageName())) {
                    AppLogger.i("I am the candidate to run " + context.getPackageName());
                    this.mListener.onStrategySuccess();
                } else {
                    AppLogger.i("found a better candidate rather than myself " + context.getPackageName());
                    AppLogger.i("start & bind service:" + this.winningCandidateComponent.flattenToString());
                    Intent candidateIntent = new Intent();
                    candidateIntent.setComponent(this.winningCandidateComponent);
                    context.startService(candidateIntent);
                    this.mListener.onStrategyFailure(this.winningCandidateComponent);
                }
            }
            this.mListener = null;
            context.getApplicationContext().unregisterReceiver(this.mBroadcastReceiver);
            this.handler = null;
            this.mContext = null;
        }
    }

    private void killService(Context context, ComponentName componentName) {
        AppLogger.i("Attempt to kill:" + componentName.flattenToString());
        Intent query = new Intent(INTENT_QUERY_INIT);
        query.setPackage(componentName.getPackageName());
        query.putExtra(INTENT_ACTION_KEY, 2);
        context.sendBroadcast(query);
    }

    public static boolean isSuperior(String packageNameA, String packageNameB) {
        return packageNameA.compareTo(packageNameB) > 0;
    }
}
