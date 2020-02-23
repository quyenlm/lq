package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.tp.a.h;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor implements Closeable {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_CRC = "dex.crc.";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_DEX_TIME = "dex.time.";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final String LOCK_FILENAME = "MultiDex.lock";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private final FileLock cacheLock;
    private final File dexDir;
    private final FileChannel lockChannel;
    private final RandomAccessFile lockRaf;
    private final File sourceApk;
    private final long sourceCrc;

    private static class ExtractedDex extends File {
        public long crc = -1;

        public ExtractedDex(File dexDir, String fileName) {
            super(dexDir, fileName);
        }
    }

    MultiDexExtractor(File sourceApk2, File dexDir2) throws IOException {
        Throwable e;
        Log.i(TAG, "MultiDexExtractor(" + sourceApk2.getPath() + ", " + dexDir2.getPath() + h.b);
        this.sourceApk = sourceApk2;
        this.dexDir = dexDir2;
        this.sourceCrc = getZipCrc(sourceApk2);
        File lockFile = new File(dexDir2, LOCK_FILENAME);
        this.lockRaf = new RandomAccessFile(lockFile, "rw");
        try {
            this.lockChannel = this.lockRaf.getChannel();
            try {
                Log.i(TAG, "Blocking on lock " + lockFile.getPath());
                this.cacheLock = this.lockChannel.lock();
                Log.i(TAG, lockFile.getPath() + " locked");
            } catch (IOException e2) {
                e = e2;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (RuntimeException e3) {
                e = e3;
                closeQuietly(this.lockChannel);
                throw e;
            } catch (Error e4) {
                e = e4;
                closeQuietly(this.lockChannel);
                throw e;
            }
        } catch (IOException | Error | RuntimeException e5) {
            closeQuietly(this.lockRaf);
            throw e5;
        }
    }

    /* access modifiers changed from: package-private */
    public List<? extends File> load(Context context, String prefsKeyPrefix, boolean forceReload) throws IOException {
        List<ExtractedDex> files;
        Log.i(TAG, "MultiDexExtractor.load(" + this.sourceApk.getPath() + ", " + forceReload + ", " + prefsKeyPrefix + h.b);
        if (!this.cacheLock.isValid()) {
            throw new IllegalStateException("MultiDexExtractor was closed");
        }
        if (forceReload || isModified(context, this.sourceApk, this.sourceCrc, prefsKeyPrefix)) {
            if (forceReload) {
                Log.i(TAG, "Forced extraction must be performed.");
            } else {
                Log.i(TAG, "Detected that extraction must be performed.");
            }
            files = performExtractions();
            putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files);
        } else {
            try {
                files = loadExistingExtractions(context, prefsKeyPrefix);
            } catch (IOException ioe) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                files = performExtractions();
                putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files);
            }
        }
        Log.i(TAG, "load found " + files.size() + " secondary dex files");
        return files;
    }

    public void close() throws IOException {
        this.cacheLock.release();
        this.lockChannel.close();
        this.lockRaf.close();
    }

    private List<ExtractedDex> loadExistingExtractions(Context context, String prefsKeyPrefix) throws IOException {
        Log.i(TAG, "loading existing secondary dex files");
        String extractedFilePrefix = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        int totalDexNumber = multiDexPreferences.getInt(prefsKeyPrefix + KEY_DEX_NUMBER, 1);
        List<ExtractedDex> files = new ArrayList<>(totalDexNumber - 1);
        int secondaryNumber = 2;
        while (secondaryNumber <= totalDexNumber) {
            ExtractedDex extractedFile = new ExtractedDex(this.dexDir, extractedFilePrefix + secondaryNumber + ".zip");
            if (extractedFile.isFile()) {
                extractedFile.crc = getZipCrc(extractedFile);
                long expectedCrc = multiDexPreferences.getLong(prefsKeyPrefix + KEY_DEX_CRC + secondaryNumber, -1);
                long expectedModTime = multiDexPreferences.getLong(prefsKeyPrefix + KEY_DEX_TIME + secondaryNumber, -1);
                long lastModified = extractedFile.lastModified();
                if (expectedModTime == lastModified && expectedCrc == extractedFile.crc) {
                    files.add(extractedFile);
                    secondaryNumber++;
                } else {
                    throw new IOException("Invalid extracted dex: " + extractedFile + " (key \"" + prefsKeyPrefix + "\"), expected modification time: " + expectedModTime + ", modification time: " + lastModified + ", expected crc: " + expectedCrc + ", file crc: " + extractedFile.crc);
                }
            } else {
                throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
            }
        }
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc, String prefsKeyPrefix) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        return (prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append("timestamp").toString(), -1) == getTimeStamp(archive) && prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append(KEY_CRC).toString(), -1) == currentCrc) ? false : true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private List<ExtractedDex> performExtractions() throws IOException {
        ExtractedDex extractedFile;
        boolean isExtractionSuccessful;
        String extractedFilePrefix = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        clearDexDir();
        List<ExtractedDex> files = new ArrayList<>();
        ZipFile apk = new ZipFile(this.sourceApk);
        int secondaryNumber = 2;
        try {
            ZipEntry dexFile = apk.getEntry(DEX_PREFIX + 2 + ".dex");
            while (dexFile != null) {
                extractedFile = new ExtractedDex(this.dexDir, extractedFilePrefix + secondaryNumber + ".zip");
                files.add(extractedFile);
                Log.i(TAG, "Extraction is needed for file " + extractedFile);
                int numAttempts = 0;
                isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    numAttempts++;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    extractedFile.crc = getZipCrc(extractedFile);
                    isExtractionSuccessful = true;
                    Log.i(TAG, "Extraction " + (isExtractionSuccessful ? AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED : "failed") + " '" + extractedFile.getAbsolutePath() + "': length " + extractedFile.length() + " - crc: " + extractedFile.crc);
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                        }
                    }
                }
                if (!isExtractionSuccessful) {
                    throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + h.b);
                }
                secondaryNumber++;
                dexFile = apk.getEntry(DEX_PREFIX + secondaryNumber + ".dex");
            }
            try {
                apk.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
            return files;
        } catch (IOException e2) {
            isExtractionSuccessful = false;
            Log.w(TAG, "Failed to read crc from " + extractedFile.getAbsolutePath(), e2);
        } catch (Throwable th) {
            try {
                apk.close();
            } catch (IOException e3) {
                Log.w(TAG, "Failed to close resource", e3);
            }
            throw th;
        }
    }

    private static void putStoredApkInfo(Context context, String keyPrefix, long timeStamp, long crc, List<ExtractedDex> extractedDexes) {
        SharedPreferences.Editor edit = getMultiDexPreferences(context).edit();
        edit.putLong(keyPrefix + "timestamp", timeStamp);
        edit.putLong(keyPrefix + KEY_CRC, crc);
        edit.putInt(keyPrefix + KEY_DEX_NUMBER, extractedDexes.size() + 1);
        int extractedDexId = 2;
        for (ExtractedDex dex : extractedDexes) {
            edit.putLong(keyPrefix + KEY_DEX_CRC + extractedDexId, dex.crc);
            edit.putLong(keyPrefix + KEY_DEX_TIME + extractedDexId, dex.lastModified());
            extractedDexId++;
        }
        edit.commit();
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private void clearDexDir() {
        File[] files = this.dexDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return !pathname.getName().equals(MultiDexExtractor.LOCK_FILENAME);
            }
        });
        if (files == null) {
            Log.w(TAG, "Failed to list secondary dex dir content (" + this.dexDir.getPath() + ").");
            return;
        }
        for (File oldFile : files) {
            Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
            if (!oldFile.delete()) {
                Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
            } else {
                Log.i(TAG, "Deleted old file " + oldFile.getPath());
            }
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {
        InputStream in = apk.getInputStream(dexFile);
        File tmp = File.createTempFile("tmp-" + extractedFilePrefix, ".zip", extractTo.getParentFile());
        Log.i(TAG, "Extracting " + tmp.getPath());
        try {
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            try {
                ZipEntry classesDex = new ZipEntry("classes.dex");
                classesDex.setTime(dexFile.getTime());
                out.putNextEntry(classesDex);
                byte[] buffer = new byte[16384];
                for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                    out.write(buffer, 0, length);
                }
                out.closeEntry();
                out.close();
                if (!tmp.setReadOnly()) {
                    throw new IOException("Failed to mark readonly \"" + tmp.getAbsolutePath() + "\" (tmp of \"" + extractTo.getAbsolutePath() + "\")");
                }
                Log.i(TAG, "Renaming to " + extractTo.getPath());
                if (!tmp.renameTo(extractTo)) {
                    throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
                }
                closeQuietly(in);
                tmp.delete();
            } catch (Throwable th) {
                th = th;
                ZipOutputStream zipOutputStream = out;
                closeQuietly(in);
                tmp.delete();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(in);
            tmp.delete();
            throw th;
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }
}
