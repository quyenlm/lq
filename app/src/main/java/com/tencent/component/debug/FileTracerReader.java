package com.tencent.component.debug;

import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.IOUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTracerReader {
    private static final int DEF_BUFFER_SIZE = 8192;
    public static final String ZIP_FILE_EXT = ".zip";
    private FileTracerConfig config;

    @Deprecated
    public interface ReaderCallback {
        void onTraceRead(FileTracerReader fileTracerReader, byte[] bArr, int i);
    }

    public FileTracerReader(FileTracerConfig config2) {
        setConfig(config2);
    }

    public FileTracerReader(FileTracer fileTracer) {
        this(fileTracer.getConfig());
    }

    public File pack(long time, File tempFolder) {
        return pack(time, tempFolder, true);
    }

    public File pack(long time, File tempFolder, boolean needZip) {
        File resu = doPack(time, tempFolder);
        if (resu == null) {
            return null;
        }
        if (!needZip) {
            return resu;
        }
        File dest = new File(resu.getAbsolutePath() + ZIP_FILE_EXT);
        if (!FileUtil.zip(resu, dest)) {
            dest = null;
        }
        return dest;
    }

    private File doPack(long time, File tempFolder) {
        File resu;
        BufferedInputStream bis;
        File workFolder = getConfig().getWorkFolder(time);
        File[] blockFiles = getConfig().getAllBlocksInFolder(workFolder);
        File tempFile = new File(tempFolder, workFolder.getName() + getConfig().getFileExt());
        if (tempFile.exists()) {
            tempFile.delete();
        }
        if (blockFiles == null) {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
            }
            return tempFile;
        }
        getConfig().sortBlocksByIndex(blockFiles);
        BufferedInputStream bis2 = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[8192];
        try {
            BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(tempFile, true));
            try {
                int length = blockFiles.length;
                int i = 0;
                while (true) {
                    bis = bis2;
                    if (i >= length) {
                        break;
                    }
                    try {
                        File file = blockFiles[i];
                        IOUtils.closeQuietly((Closeable) bis);
                        bis2 = new BufferedInputStream(new FileInputStream(file));
                        while (true) {
                            int readLen = bis2.read(buffer, 0, buffer.length);
                            if (readLen <= 0) {
                                break;
                            }
                            bos2.write(buffer, 0, readLen);
                        }
                        i++;
                    } catch (IOException e2) {
                        e = e2;
                        bos = bos2;
                        bis2 = bis;
                        try {
                            e.printStackTrace();
                            resu = null;
                            IOUtils.closeQuietly((Closeable) bos);
                            IOUtils.closeQuietly((Closeable) bis2);
                            File file2 = resu;
                            return resu;
                        } catch (Throwable th) {
                            th = th;
                            IOUtils.closeQuietly((Closeable) bos);
                            IOUtils.closeQuietly((Closeable) bis2);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bos = bos2;
                        bis2 = bis;
                        IOUtils.closeQuietly((Closeable) bos);
                        IOUtils.closeQuietly((Closeable) bis2);
                        throw th;
                    }
                }
                bos2.flush();
                resu = tempFile;
                IOUtils.closeQuietly((Closeable) bos2);
                IOUtils.closeQuietly((Closeable) bis);
                BufferedOutputStream bufferedOutputStream = bos2;
                BufferedInputStream bufferedInputStream = bis;
            } catch (IOException e3) {
                e = e3;
                bos = bos2;
                e.printStackTrace();
                resu = null;
                IOUtils.closeQuietly((Closeable) bos);
                IOUtils.closeQuietly((Closeable) bis2);
                File file22 = resu;
                return resu;
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                IOUtils.closeQuietly((Closeable) bos);
                IOUtils.closeQuietly((Closeable) bis2);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            resu = null;
            IOUtils.closeQuietly((Closeable) bos);
            IOUtils.closeQuietly((Closeable) bis2);
            File file222 = resu;
            return resu;
        }
        File file2222 = resu;
        return resu;
    }

    @Deprecated
    public boolean read(long time, byte[] buffer, int startFileIndex, int startDataIndex, int eachTimeReadLen, ReaderCallback callback) {
        BufferedInputStream bis;
        if (callback == null) {
            return false;
        }
        if (buffer == null) {
            buffer = new byte[8192];
        }
        if (eachTimeReadLen > buffer.length) {
            int eachTimeReadLen2 = buffer.length;
        }
        boolean resu = false;
        File[] blockFiles = getConfig().getAllBlocksInFolder(getConfig().getWorkFolder(time));
        if (blockFiles == null) {
            return false;
        }
        getConfig().sortBlocksByIndex(blockFiles);
        int readSkip = startDataIndex;
        int i = startFileIndex;
        BufferedInputStream bis2 = null;
        while (i < blockFiles.length) {
            try {
                File file = blockFiles[i];
                if (((long) readSkip) > file.length()) {
                    readSkip -= (int) file.length();
                    bis = bis2;
                } else {
                    IOUtils.closeQuietly((Closeable) bis2);
                    bis = new BufferedInputStream(new FileInputStream(file));
                    if (readSkip > 0) {
                        try {
                            bis.skip((long) readSkip);
                            readSkip = 0;
                        } catch (IOException e) {
                            e = e;
                            try {
                                e.printStackTrace();
                                IOUtils.closeQuietly((Closeable) bis);
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                IOUtils.closeQuietly((Closeable) bis);
                                throw th;
                            }
                        }
                    }
                    while (true) {
                        int readLen = bis.read(buffer, 0, buffer.length);
                        if (readLen <= 0) {
                            break;
                        }
                        callback.onTraceRead(this, buffer, readLen);
                    }
                    resu = true;
                }
                i++;
                bis2 = bis;
            } catch (IOException e2) {
                e = e2;
                bis = bis2;
                e.printStackTrace();
                IOUtils.closeQuietly((Closeable) bis);
                return false;
            } catch (Throwable th2) {
                th = th2;
                bis = bis2;
                IOUtils.closeQuietly((Closeable) bis);
                throw th;
            }
        }
        IOUtils.closeQuietly((Closeable) bis2);
        BufferedInputStream bufferedInputStream = bis2;
        return resu;
    }

    public FileTracerConfig getConfig() {
        return this.config;
    }

    public void setConfig(FileTracerConfig config2) {
        this.config = config2;
    }
}
