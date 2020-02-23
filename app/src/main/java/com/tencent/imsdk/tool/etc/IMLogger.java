package com.tencent.imsdk.tool.etc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class IMLogger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    private static final String DEFAULT_TAG = "iMSDK";
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int LOG_BOTH = 3;
    public static final int LOG_CONSOLE = 1;
    public static final int LOG_FILE = 2;
    private static final long LOG_FILE_SIZE = 10485760;
    public static final int LOG_NULL = 0;
    private static final int STACK_TRACE_DEEP = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static FileLogHandler fileLog;
    private static int logDevice = 1;
    private static int logDeviceLevel = 2;

    public static void init(int logType, int logLevel) {
        if (logType >= 0 && logType <= 3) {
            logDevice = logType;
        }
        if (logLevel >= 2 && logLevel <= 7) {
            logDeviceLevel = logLevel;
        }
        if (logDevice > 1) {
            fileLog = new FileLogHandler();
        }
    }

    private static class FileLogHandler extends Handler {
        private boolean hasSDCard;
        private File logFile;
        private FileOutputStream logOutput;

        FileLogHandler() {
            this.hasSDCard = true;
            this.hasSDCard = FileUtils.hasExternalStorage();
            if (this.hasSDCard) {
                try {
                    this.logFile = FileUtils.getLogFile();
                    if (this.logFile.exists()) {
                        long logLength = this.logFile.length();
                        if (logLength > IMLogger.LOG_FILE_SIZE) {
                            Log.d(IMLogger.DEFAULT_TAG, "Log size larger than LOG_FILE_SIZE:" + String.valueOf(logLength));
                            if (!this.logFile.delete()) {
                                IMLogger.w("logFile delete failed");
                            }
                            if (!this.logFile.createNewFile()) {
                                IMLogger.w("create new log file failed");
                            }
                        }
                    } else if (!this.logFile.createNewFile()) {
                        IMLogger.w("create new log file failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void handleMessage(Message msg) {
            if (this.hasSDCard) {
                try {
                    String log = ((String) msg.obj) + "\n";
                    if (log != null) {
                        byte[] logData = log.getBytes(Charset.forName("UTF-8"));
                        getLogOutput().write(logData, 0, logData.length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public FileOutputStream getLogOutput() throws Exception {
            if (this.logOutput == null) {
                this.logOutput = new FileOutputStream(this.logFile, true);
            }
            return this.logOutput;
        }
    }

    private static void showLog(int logType, String tag, String msg, int device) {
        if (CommonUtil.ckIsEmpty(msg)) {
            msg = "NULL MSG";
        }
        if (tag.length() > 89) {
            showInConsole(6, DEFAULT_TAG, "tag is longer than 89");
            tag = tag.substring(0, 86) + "...";
        }
        switch (device) {
            case 1:
                showInConsole(logType, tag, msg);
                return;
            case 2:
                writeToLog((System.currentTimeMillis() / 1000) + "\t" + tag + "\t" + msg);
                return;
            case 3:
                showInConsole(logType, tag, msg);
                writeToLog((System.currentTimeMillis() / 1000) + "\t" + tag + "\t" + msg);
                return;
            default:
                return;
        }
    }

    private static void showInConsole(int logType, String tag, String msg) {
        if (msg == null) {
            msg = "NULL MSG";
        }
        if (logType >= logDeviceLevel) {
            switch (logType) {
                case 2:
                    Log.v(tag, msg);
                    return;
                case 3:
                    Log.d(tag, msg);
                    return;
                case 4:
                    Log.i(tag, msg);
                    return;
                case 5:
                    Log.w(tag, msg);
                    return;
                case 6:
                    Log.e(tag, msg);
                    return;
                default:
                    return;
            }
        }
    }

    private static void writeToLog(String log) {
        Message msg = fileLog.obtainMessage();
        msg.obj = log;
        fileLog.sendMessage(msg);
    }

    private static String getTag(String subTag, int index) {
        String tag;
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        if (index < 0 || index >= traces.length) {
            Object obj = "";
            return "";
        }
        String clsName = traces[index].getClassName();
        String methodName = traces[index].getMethodName();
        String shortClsName = "";
        int dot = clsName.lastIndexOf(46);
        if (dot != -1) {
            shortClsName = clsName.substring(dot + 1);
        }
        if (CommonUtil.ckIsEmpty(subTag)) {
            tag = "iMSDK " + shortClsName + "->" + methodName;
        } else {
            tag = "iMSDK>" + subTag + " " + shortClsName + "->" + methodName;
        }
        String str = tag;
        return tag;
    }

    public static void e(String msg) {
        if (logDevice > 0) {
            showLog(6, getTag((String) null, 4), msg, logDevice);
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (throwable != null) {
            StackTraceElement[] stacks = new Throwable().getStackTrace();
            if (stacks.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("class : ").append(stacks[1].getClassName()).append("; line : ").append(stacks[1].getLineNumber());
                showLog(6, tag, sb.toString(), logDevice);
            }
            throwable.printStackTrace();
        }
    }

    public static void e(String tag, String msg) {
        if (logDevice > 0) {
            showLog(6, tag, msg, logDevice);
        }
    }

    public static void w(String msg) {
        if (logDevice > 0) {
            showLog(5, getTag((String) null, 4), msg, logDevice);
        }
    }

    public static void w(String tag, String msg) {
        if (logDevice > 0) {
            showLog(5, tag, msg, logDevice);
        }
    }

    public static void d(String msg) {
        if (logDevice > 0) {
            showLog(3, getTag((String) null, 4), " " + msg, logDevice);
        }
    }

    public static void d(Object msg) {
        if (logDevice != 0) {
            String tag = getTag((String) null, 4);
            if (msg == null) {
                showLog(3, tag, "empty msg", logDevice);
            } else {
                showLog(3, tag, msg.toString(), logDevice);
            }
        }
    }

    public static void d(Bundle b) {
        if (logDevice != 0) {
            String tag = getTag((String) null, 4);
            if (b == null) {
                showLog(3, tag, "empty bundle", logDevice);
                return;
            }
            for (String key : b.keySet()) {
                if (b.get(key) instanceof byte[]) {
                    showLog(3, tag, key + ":" + HexUtil.bytes2HexStr(b.getByteArray(key)), logDevice);
                } else if (b.get(key) instanceof String) {
                    showLog(3, tag, key + ":" + b.getString(key), logDevice);
                } else if (b.get(key) instanceof Long) {
                    showLog(3, tag, key + ":" + b.getLong(key), logDevice);
                } else if (b.get(key) instanceof Integer) {
                    showLog(3, tag, key + ":" + b.getInt(key), logDevice);
                } else {
                    showLog(3, tag, key, logDevice);
                }
            }
        }
    }

    public static void d(Intent i) {
        if (logDevice != 0) {
            String tag = getTag((String) null, 4);
            if (i == null || i.getExtras() == null) {
                d("********************** INTENT START **************************");
                showLog(3, tag, "empty Intent", logDevice);
                d("********************** INTENT END **************************");
                return;
            }
            d("********************** INTENT START **************************");
            showLog(3, tag, "Action: " + i.getAction(), logDevice);
            showLog(3, tag, "Component: " + i.getComponent(), logDevice);
            showLog(3, tag, "Flags: " + i.getFlags(), logDevice);
            showLog(3, tag, "Scheme: " + i.getScheme(), logDevice);
            Bundle b = i.getExtras();
            for (String key : b.keySet()) {
                if (b.get(key) instanceof byte[]) {
                    showLog(3, tag, key + ":" + HexUtil.bytes2HexStr(b.getByteArray(key)), logDevice);
                } else if (b.get(key) instanceof String) {
                    showLog(3, tag, key + ":" + b.getString(key), logDevice);
                } else if (b.get(key) instanceof Long) {
                    showLog(3, tag, key + ":" + b.getLong(key), logDevice);
                } else if (b.get(key) instanceof Integer) {
                    showLog(3, tag, key + ":" + b.getInt(key), logDevice);
                } else {
                    showLog(3, tag, key, logDevice);
                }
            }
            d("********************** INTENT END **************************");
        }
    }

    public static void d(String tag, String msg) {
        if (logDevice > 0) {
            showLog(3, tag, msg, logDevice);
        }
    }
}
