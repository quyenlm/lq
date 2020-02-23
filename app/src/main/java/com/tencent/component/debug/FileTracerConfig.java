package com.tencent.component.debug;

import com.tencent.component.utils.FileUtil;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

public class FileTracerConfig {
    public static final int DEF_BUFFER_SIZE = 4096;
    public static final long DEF_FLUSH_INTERVAL = 10000;
    public static final String DEF_FOLDER_FORMAT = "yyyy-MM-dd";
    public static final String DEF_THREAD_NAME = "Tracer.File";
    public static final String DEF_TRACE_FILEEXT = ".log";
    private static FileFilter DEF_TRACE_FOLDER_FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            if (pathname.isDirectory() && FileTracerConfig.getTimeFromFolder(pathname) > 0) {
                return true;
            }
            return false;
        }
    };
    public static final long FOREVER = Long.MAX_VALUE;
    public static final int NO_LIMITED = Integer.MAX_VALUE;
    public static final int PRIORITY_BACKGROUND = 10;
    public static final int PRIORITY_STANDARD = 0;
    private Comparator<? super File> blockComparetor;
    private String fileExt;
    private long flushInterval;
    private long keepPeriod;
    private int maxBlockCount;
    private int maxBlockSize;
    private int maxBufferSize;
    private String name;
    private int priority;
    private File rootFolder;
    private FileFilter traceFilter;

    public static long getTimeFromFolder(File folder) {
        try {
            return new SimpleDateFormat(DEF_FOLDER_FORMAT).parse(folder.getName()).getTime();
        } catch (Exception e) {
            return -1;
        }
    }

    public FileTracerConfig(File root) {
        this(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 4096, DEF_THREAD_NAME, 10000, 10, DEF_TRACE_FILEEXT, FOREVER);
    }

    public FileTracerConfig(File root, int blockCount, int blockSize, int bufferSize, String threadName, long interval, int priority2, String fileExt2, long keepPeriod2) {
        this.name = DEF_THREAD_NAME;
        this.maxBlockSize = Integer.MAX_VALUE;
        this.maxBlockCount = Integer.MAX_VALUE;
        this.maxBufferSize = 4096;
        this.flushInterval = 10000;
        this.priority = 10;
        this.fileExt = DEF_TRACE_FILEEXT;
        this.keepPeriod = FOREVER;
        this.traceFilter = new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(FileTracerConfig.this.getFileExt()) && FileTracerConfig.getBlockCountFromFile(pathname) != -1) {
                    return true;
                }
                return false;
            }
        };
        this.blockComparetor = new Comparator<File>() {
            public int compare(File lhs, File rhs) {
                return FileTracerConfig.getBlockCountFromFile(lhs) - FileTracerConfig.getBlockCountFromFile(rhs);
            }
        };
        setRootFolder(root);
        setMaxBlockCount(blockCount);
        setMaxBlockSize(blockSize);
        setMaxBufferSize(bufferSize);
        setName(threadName);
        setFlushInterval(interval);
        setPriority(priority2);
        setFileExt(fileExt2);
        setKeepPeriod(keepPeriod2);
    }

    public File getCurrFile() {
        return getWorkFile(System.currentTimeMillis());
    }

    private File getWorkFile(long time) {
        return ensureBlockCount(getWorkFolder(time));
    }

    public File getWorkFolder(long time) {
        File workFolder = new File(getRootFolder(), new SimpleDateFormat(DEF_FOLDER_FORMAT).format(Long.valueOf(time)));
        workFolder.mkdirs();
        return workFolder;
    }

    private File ensureBlockCount(File folder) {
        File[] files = getAllBlocksInFolder(folder);
        if (files == null || files.length == 0) {
            return new File(folder, "1" + getFileExt());
        }
        sortBlocksByIndex(files);
        File resu = files[files.length - 1];
        int cleanCount = files.length - getMaxBlockCount();
        if (((int) resu.length()) > getMaxBlockSize()) {
            resu = new File(folder, (getBlockCountFromFile(resu) + 1) + getFileExt());
            cleanCount++;
        }
        for (int i = 0; i < cleanCount; i++) {
            files[i].delete();
        }
        return resu;
    }

    public File[] getAllBlocksInFolder(File folder) {
        return folder.listFiles(this.traceFilter);
    }

    public void cleanWorkFolders() {
        File[] folders;
        if (getRootFolder() != null && (folders = getRootFolder().listFiles(DEF_TRACE_FOLDER_FILTER)) != null) {
            for (File folder : folders) {
                if (System.currentTimeMillis() - getTimeFromFolder(folder) > getKeepPeriod()) {
                    FileUtil.delete(folder);
                }
            }
        }
    }

    public long getSizeOfBlocks(File folder) {
        ensureBlockCount(folder);
        return getSizeOfBlocks(getAllBlocksInFolder(folder));
    }

    public long getSizeOfBlocks(File[] blockFiles) {
        long size = 0;
        for (File file : blockFiles) {
            if (file.exists() && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public File[] sortBlocksByIndex(File[] blockFiles) {
        Arrays.sort(blockFiles, this.blockComparetor);
        return blockFiles;
    }

    /* access modifiers changed from: private */
    public static int getBlockCountFromFile(File file) {
        try {
            String fileName = file.getName();
            return Integer.parseInt(fileName.substring(0, fileName.indexOf(46)));
        } catch (Exception e) {
            return -1;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getMaxBlockSize() {
        return this.maxBlockSize;
    }

    public void setMaxBlockSize(int maxBlockSize2) {
        this.maxBlockSize = maxBlockSize2;
    }

    public int getMaxBlockCount() {
        return this.maxBlockCount;
    }

    public void setMaxBlockCount(int maxBlockCount2) {
        this.maxBlockCount = maxBlockCount2;
    }

    public int getMaxBufferSize() {
        return this.maxBufferSize;
    }

    public void setMaxBufferSize(int maxBufferSize2) {
        this.maxBufferSize = maxBufferSize2;
    }

    public long getFlushInterval() {
        return this.flushInterval;
    }

    public void setFlushInterval(long flushInterval2) {
        this.flushInterval = flushInterval2;
    }

    public File getRootFolder() {
        return this.rootFolder;
    }

    public void setRootFolder(File rootFolder2) {
        this.rootFolder = rootFolder2;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority2) {
        this.priority = priority2;
    }

    public String getFileExt() {
        return this.fileExt;
    }

    public void setFileExt(String fileExt2) {
        this.fileExt = fileExt2;
    }

    public long getKeepPeriod() {
        return this.keepPeriod;
    }

    public void setKeepPeriod(long keepPeriod2) {
        this.keepPeriod = keepPeriod2;
    }
}
