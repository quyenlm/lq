package com.banalytics;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.example.LogUtitlity.BuildConfig;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

public class Log2FileService extends LoggerIntentService {
    private static final int BUFF_SIZE = 16;
    public static String CRASH_SERVER_ADDRESS = "http://203.116.180.99/crash/logs/";
    public static String CRASH_SERVER_HOST = "crashreporter.beetalkmobile.com";
    private static String DIR = null;
    private static final String EXTRA_DIR_PATH = "dir_path";
    private static final String EXTRA_FILE = "file";
    private static final String EXTRA_FORCE_FLUSH = "forceFlush";
    private static final String EXTRA_IS_FATAL = "isFatal";
    private static final String EXTRA_LOG = "log";
    private static final String EXTRA_TIME = "time";
    private static final int MAX_LOGS_COUNT = 168;
    private static final String REPORT_FILE = "report.gz";
    private static final String TAG = Log2FileService.class.getSimpleName();
    private static final String TAIL = ".txt";
    private static SimpleDateFormat logsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
    /* access modifiers changed from: private */
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH", Locale.ENGLISH);
    private HashMap<String, ArrayList<LogInfo>> logBuff = new HashMap<>();

    public static void log(Context context, String log, boolean forceFlush, String dirPath) {
        Intent intent = new Intent(context, Log2FileService.class);
        intent.putExtra(EXTRA_TIME, System.currentTimeMillis());
        intent.putExtra(EXTRA_LOG, log);
        intent.putExtra(EXTRA_FORCE_FLUSH, forceFlush);
        intent.putExtra(EXTRA_DIR_PATH, dirPath);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public Log2FileService() {
        super("log2file");
    }

    private String getAppName() {
        return getString(getApplicationContext().getApplicationInfo().labelRes, new Object[]{Locale.ENGLISH});
    }

    private void cleanTheOld() {
        File[] files;
        try {
            File dir = new File(DIR);
            if (dir.exists() && (files = dir.listFiles()) != null && files.length > MAX_LOGS_COUNT) {
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File lhs, File rhs) {
                        if (lhs.lastModified() > rhs.lastModified()) {
                            return 1;
                        }
                        if (lhs.lastModified() < rhs.lastModified()) {
                            return -1;
                        }
                        return 0;
                    }
                });
                for (int i = 0; i < files.length - 168; i++) {
                    if (files[i].exists()) {
                        files[i].delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        flushBuff();
    }

    private void checkDir(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            String appName = getAppName();
            DIR = String.format(Locale.ENGLISH, Environment.getExternalStorageDirectory() + File.separator + "%s" + File.separator + BuildConfig.LOG_FILE_NAME, new Object[]{appName.toLowerCase(Locale.ENGLISH)});
        } else {
            DIR = dirPath;
        }
        File dir = new File(DIR);
        if (!dir.exists() && dir.mkdirs()) {
            Log.d(TAG, "current dir " + DIR);
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            LogInfo info = new LogInfo(intent);
            checkDir(info.dirPath);
            cleanTheOld();
            if (this.logBuff.get(info.key) == null) {
                this.logBuff.put(info.key, new ArrayList());
            }
            this.logBuff.get(info.key).add(info);
            if (getCount() > 16 || info.isFatal || info.forceFlush) {
                flushBuff();
            }
            if (info.isFatal) {
                reportToServer(info);
            }
        }
    }

    private int getCount() {
        int count = 0;
        for (String key : this.logBuff.keySet()) {
            count += this.logBuff.get(key).size();
        }
        return count;
    }

    private void flushBuff() {
        synchronized (this.logBuff) {
            for (String key : this.logBuff.keySet()) {
                StringBuffer sb = new StringBuffer();
                ArrayList<LogInfo> logs = this.logBuff.get(key);
                Collections.sort(logs, new Comparator<LogInfo>() {
                    public int compare(LogInfo lhs, LogInfo rhs) {
                        if (lhs.time - rhs.time > 0) {
                            return 1;
                        }
                        if (lhs.time - rhs.time < 0) {
                            return -1;
                        }
                        return 0;
                    }
                });
                LogInfo[] tlogs = (LogInfo[]) logs.toArray(new LogInfo[0]);
                logs.clear();
                for (LogInfo log : tlogs) {
                    String time = logsdf.format(new Date(log.time));
                    sb.append('[');
                    sb.append(time);
                    sb.append(']');
                    sb.append(9);
                    sb.append(log.log);
                    sb.append(10);
                }
                FileWriter writer = null;
                try {
                    writer = getWriter(key);
                    writer.append(sb.toString());
                    Thread.sleep(200);
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (IOException | InterruptedException e2) {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e3) {
                        }
                    }
                } catch (Throwable th) {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            }
        }
    }

    private String getFileNameByKey(String key) {
        return DIR + File.separator + key + TAIL;
    }

    private FileWriter getWriter(String tag) throws IOException {
        checkDir(DIR);
        File file = new File(getFileNameByKey(tag));
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file, true);
    }

    private void reportToServer(LogInfo info) {
        File curFile = new File(getFileNameByKey(info.key));
        File preFile = new File(getFileNameByKey(sdf.format(new Date(info.time - 3600000))));
        StringBuffer DEBUGLOG = new StringBuffer();
        DEBUGLOG.append(10).append("curFile= " + curFile.getAbsolutePath() + " exist = " + curFile.exists() + " size = " + humanReadableByteCount(curFile.length())).append(10).append("preFile= " + preFile.getAbsolutePath() + " exist = " + preFile.exists() + " size = " + humanReadableByteCount(preFile.length()));
        Log.i("reportToServer", DEBUGLOG.toString());
        try {
            File targetFile = new File(DIR + File.separator + REPORT_FILE);
            targetFile.createNewFile();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(targetFile));
            byte[] buffer = new byte[1024];
            if (preFile.exists()) {
                FileInputStream in = new FileInputStream(preFile);
                while (true) {
                    int len = in.read(buffer);
                    if (len <= 0) {
                        break;
                    }
                    gzipOutputStream.write(buffer, 0, len);
                }
                in.close();
            }
            gzipOutputStream.write("\n ---second_file--- \n".getBytes());
            if (curFile.exists()) {
                FileInputStream in2 = new FileInputStream(curFile);
                while (true) {
                    int len2 = in2.read(buffer);
                    if (len2 <= 0) {
                        break;
                    }
                    gzipOutputStream.write(buffer, 0, len2);
                }
                in2.close();
            }
            gzipOutputStream.flush();
            gzipOutputStream.finish();
            gzipOutputStream.close();
            if (post(info.filePath, targetFile.getAbsolutePath())) {
                File f = new File(info.filePath);
                if (f.delete()) {
                    Log.i("reportToServer", "file =" + f.getAbsolutePath() + " deleted");
                }
                if (targetFile.delete()) {
                    Log.i("reportToServer", "file =" + targetFile.getAbsolutePath() + " deleted");
                }
            }
        } catch (IOException e) {
        }
    }

    private static boolean post(String filePath, String attachment) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(attachment)) {
            return false;
        }
        try {
            MultipartUtility multipart = new MultipartUtility(CRASH_SERVER_ADDRESS, "UTF-8");
            multipart.addHeaderField("Host", CRASH_SERVER_HOST);
            multipart.addFilePart("logfile", new File(filePath));
            multipart.addFilePart(MessengerShareContentUtility.ATTACHMENT, new File(attachment));
            if (!multipart.finish()) {
                return false;
            }
            StringBuffer L = new StringBuffer();
            L.append("====\n").append("post LogFile = " + filePath).append(10).append("post AttachFile = " + attachment).append("\n done!");
            Log.i("post", L.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static class LogInfo {
        String dirPath;
        String filePath;
        boolean forceFlush;
        boolean isFatal;
        String key;
        String log;
        long time;

        private LogInfo(Intent intent) {
            this.time = intent.getLongExtra(Log2FileService.EXTRA_TIME, 0);
            this.log = intent.getStringExtra(Log2FileService.EXTRA_LOG);
            this.key = Log2FileService.sdf.format(new Date(this.time));
            this.isFatal = intent.getBooleanExtra(Log2FileService.EXTRA_IS_FATAL, false);
            this.forceFlush = intent.getBooleanExtra(Log2FileService.EXTRA_FORCE_FLUSH, false);
            this.filePath = intent.getStringExtra("file");
            this.dirPath = intent.getStringExtra(Log2FileService.EXTRA_DIR_PATH);
        }
    }

    private static String humanReadableByteCount(long bytes) {
        if (bytes < ((long) 1000)) {
            return bytes + " B";
        }
        int exp = (int) (Math.log((double) bytes) / Math.log((double) 1000));
        return String.format("%.1f %sB", new Object[]{Double.valueOf(((double) bytes) / Math.pow((double) 1000, (double) exp)), "kMGTPE".charAt(exp - 1) + ""});
    }
}
