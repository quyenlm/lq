package com.apollo.iips;

public interface ApolloIIPSUpdateCallBack {

    public static class ApolloIIPSAppVersion {
        public short versionNumberFour = 0;
        public short versionNumberOne = 0;
        public short versionNumberThree = 0;
        public short versionNumberTwo = 0;
    }

    public static class ApolloIIPSVersionInfo {
        public boolean isAppDiffUpdating = false;
        public boolean isAppUpdating = false;
        public boolean isForcedUpdating = false;
        public long needDownloadSize = 0;
        public ApolloIIPSAppVersion newAppVersion;
    }

    boolean onActionMsgArrive(String str);

    void onError(int i, int i2);

    boolean onGetNewVersionInfo(ApolloIIPSVersionInfo apolloIIPSVersionInfo);

    boolean onNoticeInstallAPK(String str);

    void onProgress(int i, long j, long j2);

    void onSuccess();
}
