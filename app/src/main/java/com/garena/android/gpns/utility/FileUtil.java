package com.garena.android.gpns.utility;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class FileUtil {
    public static boolean forceCreateFolder(String strFolder) {
        File f = new File(strFolder);
        if (f.exists()) {
            if (f.isDirectory()) {
                return true;
            }
            if (f.isFile()) {
                f.delete();
            }
        }
        if (f.mkdirs()) {
            return true;
        }
        AppLogger.d("create folder error" + strFolder);
        return false;
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                sb.append(line);
            } else {
                reader.close();
                return sb.toString();
            }
        }
    }

    public static JSONObject readFile(String fileFolderPath, String fileName) {
        String jsonString = "";
        try {
            if (forceCreateFolder(fileFolderPath)) {
                File f = new File(fileFolderPath, fileName);
                if (!f.exists()) {
                    f.createNewFile();
                }
                jsonString = convertStreamToString(new FileInputStream(f));
            }
        } catch (Exception e) {
            AppLogger.e((Throwable) e);
        }
        if (jsonString.length() > 0) {
            try {
                return new JSONObject(jsonString);
            } catch (JSONException e2) {
                AppLogger.e((Throwable) e2);
            }
        }
        return new JSONObject();
    }

    public static String getAppFolderPath() {
        if (isExternalStorageWritable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.garena.pushservice" + File.separator + "v2";
        }
        return "";
    }

    public static boolean saveToFile(String fileFolderPath, String fileName, String strToSave) {
        if (forceCreateFolder(fileFolderPath)) {
            File file = new File(fileFolderPath, fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    AppLogger.e((Throwable) e);
                }
            }
            try {
                FileOutputStream f = new FileOutputStream(file);
                f.write(strToSave.getBytes("UTF-8"));
                f.close();
                return true;
            } catch (FileNotFoundException e2) {
                AppLogger.e((Throwable) e2);
            } catch (IOException e3) {
                AppLogger.e((Throwable) e3);
            }
        }
        return false;
    }

    public static void deleteFile(String filePath) {
        if (!new File(filePath).delete()) {
            AppLogger.e("Can not delete file: " + filePath);
        } else {
            AppLogger.i("delete file: " + filePath);
        }
    }

    private static boolean isExternalStorageWritable() {
        return false;
    }
}
