package com.apollo.iips;

public class ApolloIIPSUpdateInterface {
    private int apolloUpdateHandle = 0;
    private ApolloIIPSUpdateCallBack updateCallBack = null;

    public class DataVersion {
        public short dataVersion = 0;

        public DataVersion() {
        }
    }

    static {
        System.loadLibrary("apollo");
    }

    private native void cancelUpdateNative(int i);

    private native boolean checkAppUpdateNative(int i);

    private native int createApolloUpdateHandleNative();

    private native boolean deleteApolloUpdateHandleNative(int i);

    private native DataVersion getCurDataVersionNative(int i);

    private native long getCurrentDownloadSpeedNative(int i);

    private native long getLastErrorNative(int i);

    private native boolean initApolloUpdateHandleNative(int i, ApolloIIPSUpdateCallBack apolloIIPSUpdateCallBack, String str);

    private native boolean pollCallBackNative(int i);

    private native boolean sentMsgToCurrentActionNative(int i, String str);

    private native boolean setNextStageNative(int i, boolean z);

    private native boolean uninitApolloUpdateHandleNative(int i);

    public void cancelUpdate() {
        if (this.apolloUpdateHandle != 0) {
            cancelUpdateNative(this.apolloUpdateHandle);
        }
    }

    public boolean checkAppUpdate() {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        return checkAppUpdateNative(this.apolloUpdateHandle);
    }

    public boolean createApolloUpdateHandle() {
        if (this.apolloUpdateHandle != 0) {
            return false;
        }
        this.apolloUpdateHandle = createApolloUpdateHandleNative();
        return true;
    }

    public boolean deleteApolloUpdateHandle() {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        boolean deleteApolloUpdateHandleNative = deleteApolloUpdateHandleNative(this.apolloUpdateHandle);
        this.apolloUpdateHandle = 0;
        return deleteApolloUpdateHandleNative;
    }

    public DataVersion getCurDataVersion() {
        if (this.apolloUpdateHandle == 0) {
            return null;
        }
        return getCurDataVersionNative(this.apolloUpdateHandle);
    }

    public long getCurrentDownloadSpeed() {
        if (this.apolloUpdateHandle == 0) {
            return 0;
        }
        return getCurrentDownloadSpeedNative(this.apolloUpdateHandle);
    }

    public long getLastError() {
        if (this.apolloUpdateHandle == 0) {
            return 0;
        }
        return getLastErrorNative(this.apolloUpdateHandle);
    }

    public boolean initApolloUpdateHandle(ApolloIIPSUpdateCallBack apolloIIPSUpdateCallBack, String str) {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        this.updateCallBack = apolloIIPSUpdateCallBack;
        return initApolloUpdateHandleNative(this.apolloUpdateHandle, apolloIIPSUpdateCallBack, str);
    }

    public boolean pollCallBack() {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        return pollCallBackNative(this.apolloUpdateHandle);
    }

    public boolean sentMsgToCurrentAction(String str) {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        return sentMsgToCurrentActionNative(this.apolloUpdateHandle, str);
    }

    public boolean setNextStage(boolean z) {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        return setNextStageNative(this.apolloUpdateHandle, z);
    }

    public boolean uninitApolloUpdateHandle() {
        if (this.apolloUpdateHandle == 0) {
            return false;
        }
        return uninitApolloUpdateHandleNative(this.apolloUpdateHandle);
    }
}
