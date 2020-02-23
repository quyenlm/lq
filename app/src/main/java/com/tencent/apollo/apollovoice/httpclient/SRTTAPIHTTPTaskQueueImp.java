package com.tencent.apollo.apollovoice.httpclient;

import android.util.Log;
import com.amazonaws.services.s3.util.Mimetypes;
import com.tencent.component.net.download.multiplex.http.HttpHeader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedList;

public class SRTTAPIHTTPTaskQueueImp {
    /* access modifiers changed from: private */
    public static String LOGTAG = "GCloudVoiceTag";
    private static String apiAddr = "http://api.pr.weixin.qq.com/cgi-bin/wxvoicereco";
    private static String apiKey = "wxk158ztg8lli234j";
    /* access modifiers changed from: private */
    public LinkedList<SRTTAPIHTTPTask> taskQueue;
    private String timestampArg;
    private Thread workThread;

    public static native void callback(int i, byte[] bArr, int i2);

    public void init() {
        this.taskQueue = new LinkedList<>();
        this.workThread = new Thread(new RequestTask());
        this.workThread.start();
    }

    public void setAppInfo(String key, String addr) {
        apiKey = key;
        apiAddr = addr;
    }

    public synchronized void addTask(int type, int method, String key, byte[] body, int session) {
        SRTTAPIHTTPTask task = new SRTTAPIHTTPTask();
        task.type = type;
        task.body = body;
        task.key = key;
        task.method = method;
        task.session = session;
        this.taskQueue.offer(task);
    }

    /* access modifiers changed from: private */
    public void dealStartTask(int session) {
        int status = 0;
        try {
            try {
                HttpURLConnection reqConn = (HttpURLConnection) new URL(apiAddr + "?cmd=1&appid=" + apiKey).openConnection();
                try {
                    reqConn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                reqConn.setRequestProperty("Content-Type", Mimetypes.MIMETYPE_HTML);
                try {
                    status = reqConn.getResponseCode();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    InputStream inStrm = new BufferedInputStream(reqConn.getInputStream());
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    while (true) {
                        try {
                            int len = inStrm.read(buf);
                            if (len != -1) {
                                byteArrayOut.write(buf, 0, len);
                            } else {
                                try {
                                    byteArrayOut.flush();
                                    byteArrayOut.close();
                                    reqConn.disconnect();
                                    callback(status, byteArrayOut.toByteArray(), session);
                                    return;
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return;
                        }
                    }
                } catch (FileNotFoundException e4) {
                    e4.printStackTrace();
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        } catch (MalformedURLException e6) {
            e6.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void dealStopTask(SRTTAPIHTTPTask task) {
        int status;
        Log.i(LOGTAG, "dealStopTask");
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            try {
                HttpURLConnection reqConn = (HttpURLConnection) new URL(apiAddr + "?platform=android&cmd=6&appid=" + apiKey + "&voice_id=" + task.key).openConnection();
                try {
                    reqConn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                reqConn.setRequestProperty("Content-Type", "application/octet-stream");
                reqConn.setRequestProperty("Connection", "keep-alive");
                reqConn.setRequestProperty(HttpHeader.REQ.ACCEPT_LANGUAGE, "zh-CN");
                try {
                    OutputStream out = new BufferedOutputStream(reqConn.getOutputStream());
                    try {
                        out.write(task.body);
                        out.flush();
                        out.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        callback(400, byteArrayOut.toByteArray(), task.session);
                    }
                    try {
                        status = reqConn.getResponseCode();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        status = 400;
                        callback(400, byteArrayOut.toByteArray(), task.session);
                    }
                    try {
                        InputStream inStrm = new BufferedInputStream(reqConn.getInputStream());
                        byte[] buf = new byte[1024];
                        while (true) {
                            try {
                                int len = inStrm.read(buf);
                                if (len != -1) {
                                    byteArrayOut.write(buf, 0, len);
                                } else {
                                    try {
                                        byteArrayOut.flush();
                                        byteArrayOut.close();
                                        reqConn.disconnect();
                                        callback(status, byteArrayOut.toByteArray(), task.session);
                                        return;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        callback(503, byteArrayOut.toByteArray(), task.session);
                                        return;
                                    }
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                callback(503, byteArrayOut.toByteArray(), task.session);
                                return;
                            }
                        }
                    } catch (FileNotFoundException e5) {
                        e5.printStackTrace();
                        callback(400, byteArrayOut.toByteArray(), task.session);
                    } catch (IOException e12) {
                        e12.printStackTrace();
                        callback(503, byteArrayOut.toByteArray(), task.session);
                    }
                } catch (IOException e22) {
                    e22.printStackTrace();
                    callback(400, byteArrayOut.toByteArray(), task.session);
                }
            } catch (IOException e6) {
                e6.printStackTrace();
            }
        } catch (MalformedURLException e7) {
            e7.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void dealVoiceTask(SRTTAPIHTTPTask task) {
        int status;
        try {
            try {
                HttpURLConnection reqConn = (HttpURLConnection) new URL(apiAddr + "?platform=android&cmd=6&appid=" + apiKey + "&voice_id=" + task.key).openConnection();
                try {
                    reqConn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                reqConn.setRequestProperty("Content-Type", "application/octet-stream");
                reqConn.setRequestProperty("Connection", "keep-alive");
                reqConn.setRequestProperty(HttpHeader.REQ.ACCEPT_LANGUAGE, "zh-CN");
                try {
                    OutputStream out = new BufferedOutputStream(reqConn.getOutputStream());
                    try {
                        out.write(task.body);
                        out.flush();
                        out.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        status = reqConn.getResponseCode();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        status = 400;
                    }
                    try {
                        InputStream inStrm = new BufferedInputStream(reqConn.getInputStream());
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        while (true) {
                            try {
                                int len = inStrm.read(buf);
                                if (len != -1) {
                                    byteArrayOut.write(buf, 0, len);
                                } else {
                                    try {
                                        byteArrayOut.flush();
                                        byteArrayOut.close();
                                        reqConn.disconnect();
                                        callback(status, byteArrayOut.toByteArray(), task.session);
                                        return;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        return;
                                    }
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                return;
                            }
                        }
                    } catch (FileNotFoundException e5) {
                        e5.printStackTrace();
                    } catch (IOException e12) {
                        e12.printStackTrace();
                    }
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (IOException e6) {
                e6.printStackTrace();
            }
        } catch (MalformedURLException e7) {
            e7.printStackTrace();
        }
    }

    class RequestTask implements Runnable {
        RequestTask() {
        }

        public void run() {
            while (true) {
                if (SRTTAPIHTTPTaskQueueImp.this.taskQueue.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        SRTTAPIHTTPTask task = (SRTTAPIHTTPTask) SRTTAPIHTTPTaskQueueImp.this.taskQueue.remove();
                        switch (task.type) {
                            case 1:
                                SRTTAPIHTTPTaskQueueImp.this.dealStartTask(task.session);
                                break;
                            case 2:
                                SRTTAPIHTTPTaskQueueImp.this.dealVoiceTask(task);
                                break;
                            case 3:
                                SRTTAPIHTTPTaskQueueImp.this.dealStopTask(task);
                                break;
                            default:
                                Log.i(SRTTAPIHTTPTaskQueueImp.LOGTAG, "[SRTTAPIHTTPTaskQueueImp]Unknown type:" + task.type);
                                break;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }
}
