package com.android.vending.expansion.zipfile;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class APKExpansionSupport {
    private static final String EXP_PATH = "/Android/obb/";

    static String[] getAPKExpansionFiles(Context ctx, int mainVersion, int patchVersion) {
        String packageName = ctx.getPackageName();
        Vector<String> ret = new Vector<>();
        if (Environment.getExternalStorageState().equals("mounted")) {
            File expPath = new File(String.valueOf(Environment.getExternalStorageDirectory().toString()) + EXP_PATH + packageName);
            if (expPath.exists()) {
                if (mainVersion > 0) {
                    String strMainPath = expPath + File.separator + "main." + mainVersion + "." + packageName + ".obb";
                    if (new File(strMainPath).isFile()) {
                        ret.add(strMainPath);
                    }
                }
                if (patchVersion > 0) {
                    String strPatchPath = expPath + File.separator + "patch." + mainVersion + "." + packageName + ".obb";
                    if (new File(strPatchPath).isFile()) {
                        ret.add(strPatchPath);
                    }
                }
            }
        }
        String[] retArray = new String[ret.size()];
        ret.toArray(retArray);
        return retArray;
    }

    public static ZipResourceFile getResourceZipFile(String[] expansionFiles) throws IOException {
        ZipResourceFile apkExpansionFile = null;
        for (String expansionFilePath : expansionFiles) {
            if (apkExpansionFile == null) {
                apkExpansionFile = new ZipResourceFile(expansionFilePath);
            } else {
                apkExpansionFile.addPatchFile(expansionFilePath);
            }
        }
        return apkExpansionFile;
    }

    public static ZipResourceFile getAPKExpansionZipFile(Context ctx, int mainVersion, int patchVersion) throws IOException {
        return getResourceZipFile(getAPKExpansionFiles(ctx, mainVersion, patchVersion));
    }
}
