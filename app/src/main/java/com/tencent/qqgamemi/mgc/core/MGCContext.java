package com.tencent.qqgamemi.mgc.core;

import android.content.Context;
import com.tencent.component.utils.ProcessUtils;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.mgc.connection.ConnectionManager;
import com.tencent.qqgamemi.mgc.core.InitialDetail;
import com.tencent.qqgamemi.mgc.step.InitializeStepTable;
import java.util.Properties;

public class MGCContext {
    private static final String TAG = "MGCContext";
    private static MGCContext sTnstance;
    private ConnectionManager mConnectionManager;
    private Context mContext;
    private DebugConfig mDebugConfig;
    private InitializeStepTable mInitializeStepTable;
    private volatile Boolean sIsInit = false;

    static MGCContext create(Context context) {
        sTnstance = new MGCContext(context);
        return sTnstance;
    }

    private MGCContext(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: package-private */
    public void init() {
        createInstances();
        initStage1();
        synchronized (this) {
            this.sIsInit = true;
        }
        runInit();
    }

    private void ensureInit() {
        if (!this.sIsInit.booleanValue()) {
            synchronized (this) {
                if (!this.sIsInit.booleanValue()) {
                    LogUtil.e("MGCCore", "haven't init in the application onCreate");
                    if (!ProcessUtils.isMainProcess(this.mContext)) {
                        LogUtil.e("MGCCore", "当前进程不是主进程");
                    }
                    new Properties().setProperty("processName", ProcessUtils.myProcessName(this.mContext));
                    init();
                }
            }
        }
    }

    private void createInstances() {
        this.mConnectionManager = new ConnectionManager(this.mContext);
        this.mDebugConfig = new DebugConfig(this.mContext);
    }

    private void initStage1() {
        this.mInitializeStepTable = new InitializeStepTable();
        this.mInitializeStepTable.addStep(new InitialDetail.ConnectManagerInit().setObject(this.mConnectionManager));
    }

    private void runInit() {
        this.mInitializeStepTable.runAll(this.mContext);
    }

    public static ConnectionManager getConnectionManager() {
        sTnstance.ensureInit();
        return sTnstance.mConnectionManager;
    }

    public static DebugConfig getDebugConfig() {
        sTnstance.ensureInit();
        return sTnstance.mDebugConfig;
    }
}
