package com.tencent.smtt.export.external;

import android.content.Context;
import android.os.Build;
import com.appsflyer.share.Constants;
import java.io.File;
import java.util.ArrayList;

public class LibraryLoader {
    private static String[] sLibrarySearchPaths = null;

    public static String[] getLibrarySearchPaths(Context context) {
        if (sLibrarySearchPaths != null) {
            return sLibrarySearchPaths;
        }
        if (context == null) {
            return new String[]{"/system/lib"};
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getNativeLibraryDir(context));
        arrayList.add("/system/lib");
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        sLibrarySearchPaths = strArr;
        return sLibrarySearchPaths;
    }

    public static String getNativeLibraryDir(Context context) {
        int i = Build.VERSION.SDK_INT;
        return i >= 9 ? context.getApplicationInfo().nativeLibraryDir : i >= 4 ? context.getApplicationInfo().dataDir + "/lib" : "/data/data/" + context.getPackageName() + "/lib";
    }

    public static void loadLibrary(Context context, String str) {
        String[] librarySearchPaths = getLibrarySearchPaths(context);
        String mapLibraryName = System.mapLibraryName(str);
        int length = librarySearchPaths.length;
        int i = 0;
        while (i < length) {
            String str2 = librarySearchPaths[i] + Constants.URL_PATH_DELIMITER + mapLibraryName;
            if (!new File(str2).exists()) {
                i++;
            } else {
                try {
                    System.load(str2);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
        try {
            System.loadLibrary(str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
