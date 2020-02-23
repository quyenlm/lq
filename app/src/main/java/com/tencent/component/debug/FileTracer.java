package com.tencent.component.debug;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.component.data.SafeStringQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileLock;

public class FileTracer extends Tracer implements Handler.Callback {
    private static final int MSG_FLUSH = 1024;
    private volatile SafeStringQueue bufferA;
    private volatile SafeStringQueue bufferB;
    private char[] charBuffer;
    private FileTracerConfig config;
    private File currTraceFile;
    private FileOutputStream fileWriter;
    private Handler handler;
    private volatile boolean isFlushing;
    private volatile SafeStringQueue readBuffer;
    private HandlerThread thread;
    private volatile SafeStringQueue writeBuffer;

    public FileTracer(FileTracerConfig config2) {
        this(63, true, TraceFormat.DEFAULT, config2);
    }

    public FileTracer(int level, boolean enable, TraceFormat format, FileTracerConfig config2) {
        super(level, enable, format);
        this.isFlushing = false;
        setConfig(config2);
        this.bufferA = new SafeStringQueue();
        this.bufferB = new SafeStringQueue();
        this.writeBuffer = this.bufferA;
        this.readBuffer = this.bufferB;
        this.charBuffer = new char[config2.getMaxBufferSize()];
        obtainFileWriter();
        this.thread = new HandlerThread(config2.getName(), config2.getPriority());
        if (this.thread != null) {
            this.thread.start();
        }
        if (this.thread.isAlive()) {
            this.handler = new Handler(this.thread.getLooper(), this);
        }
        prepareNextFlush();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                FileTracer.this.getConfig().cleanWorkFolders();
            }
        }, 15000);
    }

    public void flush() {
        if (this.handler.hasMessages(1024)) {
            this.handler.removeMessages(1024);
        }
        this.handler.sendEmptyMessage(1024);
    }

    public void quit() {
        closeFileWriter();
        this.thread.quit();
    }

    /* access modifiers changed from: protected */
    public void doTrace(int level, Thread thread2, long time, String tag, String msg, Throwable tr) {
        doTrace(getTraceFormat().formatTrace(level, thread2, time, tag, msg, tr));
    }

    /* access modifiers changed from: protected */
    public void doTrace(String formattedTrace) {
        this.writeBuffer.addToBuffer(formattedTrace);
        if (this.writeBuffer.getBufferSize() >= getConfig().getMaxBufferSize()) {
            flush();
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1024:
                try {
                    flushBuffer();
                } catch (Throwable th) {
                }
                prepareNextFlush();
                return true;
            default:
                return true;
        }
    }

    private void prepareNextFlush() {
        this.handler.sendEmptyMessageDelayed(1024, getConfig().getFlushInterval());
    }

    private void flushBuffer() {
        if (Thread.currentThread() == this.thread && !this.isFlushing) {
            this.isFlushing = true;
            FileLock fileLock = null;
            swapBuffers();
            try {
                FileOutputStream fos = obtainFileWriter();
                if (fos != null) {
                    fileLock = fos.getChannel().lock();
                    OutputStreamWriter writer = new OutputStreamWriter(fos);
                    if (writer != null) {
                        this.readBuffer.writeAndFlush(writer, this.charBuffer);
                    }
                }
                if (fileLock != null) {
                    try {
                        fileLock.release();
                    } catch (Exception e) {
                    }
                }
                this.readBuffer.clear();
            } catch (Exception e2) {
                if (fileLock != null) {
                    try {
                        fileLock.release();
                    } catch (Exception e3) {
                    }
                }
                this.readBuffer.clear();
            } catch (Throwable th) {
                if (fileLock != null) {
                    try {
                        fileLock.release();
                    } catch (Exception e4) {
                    }
                }
                this.readBuffer.clear();
                throw th;
            }
            this.isFlushing = false;
        }
    }

    private FileOutputStream obtainFileWriter() {
        boolean forceChanged = false;
        File newFile = getConfig().getCurrFile();
        if (this.currTraceFile != null && (!this.currTraceFile.exists() || !this.currTraceFile.canWrite())) {
            forceChanged = true;
        }
        if (forceChanged || (newFile != null && !newFile.equals(this.currTraceFile))) {
            this.currTraceFile = newFile;
            closeFileWriter();
            try {
                this.fileWriter = new FileOutputStream(this.currTraceFile, true);
            } catch (IOException e) {
                return null;
            }
        }
        return this.fileWriter;
    }

    public void setCurrTraceFile(File file) {
        this.currTraceFile = file;
    }

    private void closeFileWriter() {
        try {
            if (this.fileWriter != null) {
                this.fileWriter.flush();
                this.fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void swapBuffers() {
        synchronized (this) {
            if (this.writeBuffer == this.bufferA) {
                this.writeBuffer = this.bufferB;
                this.readBuffer = this.bufferA;
            } else {
                this.writeBuffer = this.bufferA;
                this.readBuffer = this.bufferB;
            }
        }
    }

    public FileTracerConfig getConfig() {
        return this.config;
    }

    public void setConfig(FileTracerConfig config2) {
        this.config = config2;
    }
}
