package com.tencent.liteav.a;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.liteav.videoencoder.c;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: TXCStreamRecord */
public class a implements e, c {
    private com.tencent.liteav.audio.impl.Record.e a = new com.tencent.liteav.audio.impl.Record.e();
    private com.tencent.liteav.videoencoder.a b = new com.tencent.liteav.videoencoder.a();
    private com.tencent.liteav.muxer.a c = new com.tencent.liteav.muxer.a();
    /* access modifiers changed from: private */
    public C0023a d;
    /* access modifiers changed from: private */
    public b e;
    private long f = 0;
    private long g = -1;
    private boolean h = false;
    private Handler i = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (a.this.e != null) {
                switch (message.what) {
                    case 1:
                        a.this.e.a(((Long) message.obj).longValue());
                        return;
                    case 2:
                        TXCLog.d("TXCStreamRecord", "record complete. errcode = " + message.arg1 + ", errmsg = " + ((String) message.obj) + ", outputPath = " + a.this.d.f + ", coverImage = " + a.this.d.g);
                        if (message.arg1 == 0 && a.this.d.g != null && !a.this.d.g.isEmpty() && !com.tencent.liteav.basic.util.a.a(a.this.d.f, a.this.d.g)) {
                            TXCLog.e("TXCStreamRecord", "saveVideoThumb error. sourcePath = " + a.this.d.f + ", coverImagePath = " + a.this.d.g);
                        }
                        if (message.arg1 != 0) {
                            File file = new File(a.this.d.f);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        a.this.e.a(message.arg1, (String) message.obj, a.this.d.f, a.this.d.g);
                        return;
                    default:
                        return;
                }
            }
        }
    };

    /* compiled from: TXCStreamRecord */
    public interface b {
        void a(int i, String str, String str2, String str3);

        void a(long j);
    }

    /* renamed from: com.tencent.liteav.a.a$a  reason: collision with other inner class name */
    /* compiled from: TXCStreamRecord */
    public static class C0023a {
        public int a = 544;
        public int b = 960;
        public int c = 20;
        public int d = 1000;
        public EGLContext e;
        public String f;
        public String g;
        public int h = 0;
        public int i = 0;
        public int j = 16;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TXCStreamRecordParams: [width=" + this.a);
            sb.append("; height=" + this.b);
            sb.append("; fps=" + this.c);
            sb.append("; bitrate=" + this.d);
            sb.append("; channels=" + this.h);
            sb.append("; samplerate=" + this.i);
            sb.append("; bits=" + this.j);
            sb.append("; EGLContext=" + this.e);
            sb.append("; coveriamge=" + this.g);
            sb.append("; outputpath=" + this.f + "]");
            return sb.toString();
        }
    }

    public void a(b bVar) {
        this.e = bVar;
    }

    public void a(C0023a aVar) {
        this.d = aVar;
        this.f = 0;
        this.g = -1;
        this.c.a(this.d.f);
        if (aVar.h > 0 && aVar.i > 0 && aVar.j > 0) {
            this.a.a(com.tencent.liteav.audio.c.f, aVar.i, aVar.h, aVar.j, new WeakReference(this));
            this.c.b(com.tencent.liteav.basic.util.a.a(this.d.i, this.d.h, 2));
            this.h = true;
        }
        this.b.setListener(this);
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        tXSVideoEncoderParam.width = this.d.a;
        tXSVideoEncoderParam.height = this.d.b;
        tXSVideoEncoderParam.fps = this.d.c;
        tXSVideoEncoderParam.glContext = this.d.e;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.appendSpsPps = false;
        this.b.setBitrate(this.d.d);
        this.b.start(tXSVideoEncoderParam);
    }

    public void a() {
        this.h = false;
        this.a.a();
        this.b.stop();
        if (this.c.c() < 0) {
            this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, "mp4合成失败"));
        } else {
            this.i.sendMessage(Message.obtain(this.i, 2, 0, 0, ""));
        }
    }

    public void a(int i2, long j) {
        this.b.pushVideoFrame(i2, this.d.a, this.d.b, j);
    }

    public void a(byte[] bArr, long j) {
        if (this.h) {
            this.a.a(bArr, j);
        } else {
            TXCLog.e("TXCStreamRecord", "drainAudio fail because of not init yet!");
        }
    }

    public static String a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000) + "000").longValue()));
            return new File(a(context), String.format("TXUGC_%s" + str, new Object[]{format})).getAbsolutePath();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String a(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
        }
        return context.getFilesDir().getPath();
    }

    private String a(int i2) {
        String str;
        switch (i2) {
            case 10000002:
                str = "未启动视频编码器";
                break;
            case 10000003:
                str = "非法视频输入参数";
                break;
            case 10000004:
                str = "视频编码初始化失败";
                break;
            case 10000005:
                str = "视频编码失败";
                break;
            default:
                str = "";
                break;
        }
        this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, str));
        return str;
    }

    public void a(byte[] bArr, long j, int i2, int i3, int i4) {
    }

    public void b(byte[] bArr, long j, int i2, int i3, int i4) {
        this.c.b(bArr, 0, bArr.length, 1000 * j, 1);
    }

    public void a(int i2, String str) {
    }

    public void a(com.tencent.liteav.basic.f.b bVar, int i2) {
        if (i2 == 0) {
            this.c.a(bVar.a, 0, bVar.a.length, bVar.g * 1000, bVar.k.flags);
            if (this.g < 0) {
                this.g = bVar.g;
            }
            if (bVar.g > this.f + 500) {
                this.i.sendMessage(Message.obtain(this.i, 1, new Long(bVar.g - this.g)));
                this.f = bVar.g;
                return;
            }
            return;
        }
        TXCLog.e("TXCStreamRecord", "video encode error! errmsg: " + a(i2));
    }

    public void a(MediaFormat mediaFormat) {
        this.c.a(mediaFormat);
        if (this.c.a() && this.c.b() < 0) {
            this.i.sendMessage(Message.obtain(this.i, 2, 1, 0, "mp4封装器启动失败"));
        }
    }
}
