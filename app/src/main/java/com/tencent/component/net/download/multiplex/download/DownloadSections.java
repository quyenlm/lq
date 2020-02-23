package com.tencent.component.net.download.multiplex.download;

import com.tencent.component.net.download.multiplex.DownloaderLog;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DownloadSections {
    private static final String TAG = "DownloadSections";
    protected File mCfgFile;
    protected long mDownloadedSizeFromCfg = 0;
    private Map<Integer, DownloadSection> mSectionData = new ConcurrentHashMap();

    public DownloadSection getSection(int sectionId) {
        return this.mSectionData.get(Integer.valueOf(sectionId));
    }

    public DownloadSection createSection(int sectionId) {
        DownloadSection pos = new DownloadSection(sectionId);
        pos.startPos = 0;
        long unused = pos.endPos = -1;
        pos.currentPos = 0;
        this.mSectionData.put(Integer.valueOf(sectionId), pos);
        return pos;
    }

    public void clear(boolean clearFiles) {
        DownloaderLog.d(TAG, "clear, clearFiles=true");
        if (clearFiles) {
            deleteCfgFile();
        }
        this.mSectionData.clear();
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0060 A[SYNTHETIC, Splitter:B:34:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x006c A[SYNTHETIC, Splitter:B:40:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void saveConfigData(long r10, int r12) {
        /*
            r9 = this;
            java.io.File r5 = r9.mCfgFile
            if (r5 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.io.File r5 = r9.mCfgFile
            boolean r5 = r5.exists()
            if (r5 != 0) goto L_0x0012
            java.io.File r5 = r9.mCfgFile     // Catch:{ IOException -> 0x0049 }
            r5.createNewFile()     // Catch:{ IOException -> 0x0049 }
        L_0x0012:
            r0 = 0
            java.io.File r5 = r9.mCfgFile     // Catch:{ Exception -> 0x005a }
            if (r5 == 0) goto L_0x004f
            java.io.File r5 = r9.mCfgFile     // Catch:{ Exception -> 0x005a }
            boolean r5 = r5.exists()     // Catch:{ Exception -> 0x005a }
            if (r5 == 0) goto L_0x004f
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x005a }
            java.io.File r5 = r9.mCfgFile     // Catch:{ Exception -> 0x005a }
            java.lang.String r6 = "rw"
            r1.<init>(r5, r6)     // Catch:{ Exception -> 0x005a }
            if (r1 == 0) goto L_0x004e
            r6 = 0
            r1.seek(r6)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            r1.writeLong(r10)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            r3 = 0
        L_0x0033:
            if (r3 >= r12) goto L_0x004e
            java.util.Map<java.lang.Integer, com.tencent.component.net.download.multiplex.download.DownloadSections$DownloadSection> r5 = r9.mSectionData     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            java.lang.Object r4 = r5.get(r6)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            com.tencent.component.net.download.multiplex.download.DownloadSections$DownloadSection r4 = (com.tencent.component.net.download.multiplex.download.DownloadSections.DownloadSection) r4     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
            if (r4 == 0) goto L_0x0046
            r4.external(r1)     // Catch:{ Exception -> 0x0078, all -> 0x0075 }
        L_0x0046:
            int r3 = r3 + 1
            goto L_0x0033
        L_0x0049:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0004
        L_0x004e:
            r0 = r1
        L_0x004f:
            if (r0 == 0) goto L_0x0004
            r0.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0004
        L_0x0055:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0004
        L_0x005a:
            r2 = move-exception
        L_0x005b:
            r2.printStackTrace()     // Catch:{ all -> 0x0069 }
            if (r0 == 0) goto L_0x0004
            r0.close()     // Catch:{ IOException -> 0x0064 }
            goto L_0x0004
        L_0x0064:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0004
        L_0x0069:
            r5 = move-exception
        L_0x006a:
            if (r0 == 0) goto L_0x006f
            r0.close()     // Catch:{ IOException -> 0x0070 }
        L_0x006f:
            throw r5
        L_0x0070:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x006f
        L_0x0075:
            r5 = move-exception
            r0 = r1
            goto L_0x006a
        L_0x0078:
            r2 = move-exception
            r0 = r1
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadSections.saveConfigData(long, int):void");
    }

    /* access modifiers changed from: protected */
    public File getConfigFile(String fileFolderPath, String fileName) {
        return new File(fileFolderPath, "." + fileName + ".dltmp");
    }

    public void restoreConfigData(String fileFolderPath, String fileName, int aSectionCount) throws IOException {
        int sectionCount = aSectionCount;
        boolean hasValid = true;
        this.mDownloadedSizeFromCfg = -1;
        this.mCfgFile = getConfigFile(fileFolderPath, fileName);
        if (!this.mCfgFile.exists()) {
            this.mCfgFile.createNewFile();
        }
        if (this.mCfgFile != null) {
            RandomAccessFile cfgFile = new RandomAccessFile(this.mCfgFile, "r");
            try {
                if (this.mCfgFile != null) {
                    this.mDownloadedSizeFromCfg = cfgFile.readLong();
                    if (1 != 0) {
                        for (int i = 0; i < sectionCount; i++) {
                            DownloadSection pos = new DownloadSection(i);
                            pos.internal(cfgFile);
                            this.mSectionData.put(Integer.valueOf(i), pos);
                        }
                    }
                }
                if (cfgFile != null) {
                    try {
                        cfgFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                hasValid = false;
                if (cfgFile != null) {
                    try {
                        cfgFile.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (cfgFile != null) {
                    try {
                        cfgFile.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } else {
            hasValid = false;
        }
        if (!hasValid) {
            clear(true);
        }
    }

    /* access modifiers changed from: protected */
    public void deleteCfgFile() {
        deleteCfgFile(this.mCfgFile);
    }

    public void deleteCfgFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isValidSectionData(long fileSize, int sectionCount) {
        if (this.mSectionData == null || this.mSectionData.size() != sectionCount) {
            return false;
        }
        for (int i = 0; i < sectionCount; i++) {
            DownloadSection section = this.mSectionData.get(Integer.valueOf(i));
            if (section != null && section.endPos > fileSize - 1) {
                return false;
            }
        }
        return true;
    }

    public boolean hasValidConfigData(String fileFolderPath, String fileName, long fileSize, int aSectionCount) {
        int sectionCount = aSectionCount;
        boolean hasValid = true;
        Map<Integer, DownloadSection> sectionData = new ConcurrentHashMap<>();
        long downloadedSize = 0;
        File file = getConfigFile(fileFolderPath, fileName);
        if (file == null || !file.exists()) {
            hasValid = false;
        } else {
            RandomAccessFile cfgFile = null;
            try {
                cfgFile = new RandomAccessFile(file, "r");
            } catch (FileNotFoundException e1) {
                hasValid = false;
                e1.printStackTrace();
            }
            if (cfgFile != null) {
                try {
                    downloadedSize = cfgFile.readLong();
                    if (hasValid) {
                        for (int i = 0; i < sectionCount; i++) {
                            DownloadSection pos = new DownloadSection(i);
                            pos.internal(cfgFile);
                            sectionData.put(Integer.valueOf(i), pos);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hasValid = false;
                    if (cfgFile != null) {
                        try {
                            cfgFile.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    if (cfgFile != null) {
                        try {
                            cfgFile.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            if (cfgFile != null) {
                try {
                    cfgFile.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
        if (sectionData != null && sectionData.size() == sectionCount) {
            int i2 = 0;
            while (true) {
                if (i2 < sectionCount) {
                    DownloadSection section = sectionData.get(Integer.valueOf(i2));
                    if (section != null && section.endPos > fileSize - 1) {
                        hasValid = false;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        } else {
            hasValid = false;
        }
        if (hasValid) {
            this.mSectionData = sectionData;
            this.mDownloadedSizeFromCfg = downloadedSize;
            this.mCfgFile = file;
        }
        return hasValid;
    }

    public int size() {
        return this.mSectionData.size();
    }

    public long getDownloadedSizeFromCfg() {
        return this.mDownloadedSizeFromCfg;
    }

    public boolean isFinish() {
        if (this.mSectionData == null) {
            return false;
        }
        for (DownloadSection section : this.mSectionData.values()) {
            if (!section.isFinish()) {
                return false;
            }
        }
        return true;
    }

    public static class DownloadSection {
        public long currentPos;
        /* access modifiers changed from: private */
        public long endPos;
        public int sectionId = -1;
        public long startPos;

        protected DownloadSection(int sectionId2) {
            this.sectionId = sectionId2;
        }

        public void setEndPos(long pos) {
            this.endPos = pos;
        }

        public long getEndPos() {
            return this.endPos;
        }

        public void external(DataOutput output) throws IOException {
            output.writeLong(this.startPos);
            output.writeLong(this.endPos);
            output.writeLong(this.currentPos);
        }

        public void internal(DataInput input) throws IOException {
            this.startPos = input.readLong();
            this.endPos = input.readLong();
            this.currentPos = input.readLong();
        }

        public boolean isFinish() {
            return this.currentPos >= this.endPos;
        }

        public boolean isPending() {
            return false;
        }

        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("sectionId=").append(this.sectionId).append(",").append("startPos=").append(this.startPos).append(",").append("endPos=").append(this.startPos).append(",").append("currentPos=").append(this.startPos).append(",");
            return buf.toString();
        }
    }

    public void setDownloadFilePath(String fileFolderPath, String fileName) {
        this.mCfgFile = getConfigFile(fileFolderPath, fileName);
    }
}
