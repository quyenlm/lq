package com.tencent.qqgamemi.mgc.core;

import com.tencent.qqgamemi.mgc.connection.ConnectionManager;
import com.tencent.qqgamemi.mgc.step.InitializeStepTable;

public class InitialDetail {

    static class ConnectManagerInit extends InitializeStepTable.IntializeStep<ConnectionManager> {
        ConnectManagerInit() {
        }

        /* access modifiers changed from: protected */
        public void run() {
            ((ConnectionManager) getObject()).init();
        }

        /* access modifiers changed from: protected */
        public String getName() {
            return "ConnectManager Init";
        }
    }
}
