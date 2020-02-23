package com.tencent.qqgamemi.srp.aws.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.amazonaws.regions.Regions;

public class GlobalConfig {
    public static String AWS_BUKET_NAME = null;
    public static String AWS_CDN_PATH_CONF = null;
    public static String AWS_OPEN_ID = null;
    public static String AWS_POOL_ID = null;
    public static final String AWS_PROVIDER_NAME = "com.wegame.screenrecoder";
    public static String AWS_REGION = Regions.CN_NORTH_1.getName();
    public static final int DB_UPLOADTASK_VERSION = 1;
    public static final String META_BUKET_NAME = "AWS_BUKET_NAME";
    public static final String META_CDN_PATH_CONF = "CDN_PATH_CONF";
    public static final String META_POOL_ID = "AWS_POOL_ID";
    public static final String META_REGION = "AWS_REGION";
    private static String TAG = "GlobalConfig";
    private static GlobalConfig instance = null;

    public static GlobalConfig getInstance() {
        if (instance == null) {
            synchronized (GlobalConfig.class) {
                if (instance == null) {
                    instance = new GlobalConfig();
                }
            }
        }
        return instance;
    }

    public void setAWSConfig(String bucketName, String poolID, String region, String cdnPathConf, long openID) {
        AWS_BUKET_NAME = bucketName;
        AWS_POOL_ID = poolID;
        AWS_REGION = region;
        AWS_CDN_PATH_CONF = cdnPathConf;
        AWS_OPEN_ID = String.valueOf(openID);
    }

    public void setAWSConfig(String bucketName, String poolID, String region, String cdnPathConf) {
        AWS_BUKET_NAME = bucketName;
        AWS_POOL_ID = poolID;
        AWS_REGION = region;
        AWS_CDN_PATH_CONF = cdnPathConf;
    }

    public void setOpenId(long openId) {
        AWS_OPEN_ID = String.valueOf(openId);
    }

    public void init(Context context) {
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            setAWSConfig(metaData.getString(META_BUKET_NAME), metaData.getString(META_POOL_ID), metaData.getString(META_REGION), metaData.getString(META_CDN_PATH_CONF));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getAWSUploadFileUrl(String filePath) {
        String result = AWS_CDN_PATH_CONF;
        if (TextUtils.isEmpty(result)) {
            String str = result;
            return null;
        }
        if (TextUtils.isEmpty(AWS_BUKET_NAME)) {
            AWS_BUKET_NAME = "";
        }
        if (TextUtils.isEmpty(AWS_REGION)) {
            AWS_REGION = "";
        }
        String result2 = result.replaceAll("\\{AWS_BUKET_NAME\\}", AWS_BUKET_NAME).replaceAll("\\{AWS_REGION\\}", AWS_REGION);
        if (!TextUtils.isEmpty(result2)) {
            if (result2.contains("{FILE_PATH}")) {
                result2 = result2.replaceAll("\\{FILE_PATH\\}", filePath);
            } else {
                result2 = result2 + filePath;
            }
        }
        String str2 = result2;
        return result2;
    }
}
