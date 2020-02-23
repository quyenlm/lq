package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (i < 100) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002d, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002e, code lost:
        r3 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0040, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0041, code lost:
        r2 = r1;
        r3 = null;
     */
    @android.support.annotation.RequiresApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.nio.ByteBuffer mmap(java.io.File r10) {
        /*
            r8 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0024 }
            r7.<init>(r10)     // Catch:{ IOException -> 0x0024 }
            r9 = 0
            java.nio.channels.FileChannel r0 = r7.getChannel()     // Catch:{ Throwable -> 0x002b, all -> 0x0040 }
            long r4 = r0.size()     // Catch:{ Throwable -> 0x002b, all -> 0x0040 }
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x002b, all -> 0x0040 }
            r2 = 0
            java.nio.MappedByteBuffer r1 = r0.map(r1, r2, r4)     // Catch:{ Throwable -> 0x002b, all -> 0x0040 }
            if (r7 == 0) goto L_0x001e
            if (r8 == 0) goto L_0x0027
            r7.close()     // Catch:{ Throwable -> 0x001f }
        L_0x001e:
            return r1
        L_0x001f:
            r2 = move-exception
            r9.addSuppressed(r2)     // Catch:{ IOException -> 0x0024 }
            goto L_0x001e
        L_0x0024:
            r6 = move-exception
            r1 = r8
            goto L_0x001e
        L_0x0027:
            r7.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x001e
        L_0x002b:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002d }
        L_0x002d:
            r2 = move-exception
            r3 = r1
        L_0x002f:
            if (r7 == 0) goto L_0x0036
            if (r3 == 0) goto L_0x003c
            r7.close()     // Catch:{ Throwable -> 0x0037 }
        L_0x0036:
            throw r2     // Catch:{ IOException -> 0x0024 }
        L_0x0037:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch:{ IOException -> 0x0024 }
            goto L_0x0036
        L_0x003c:
            r7.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0036
        L_0x0040:
            r1 = move-exception
            r2 = r1
            r3 = r8
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(java.io.File):java.nio.ByteBuffer");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x004d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x004e, code lost:
        r2 = r1;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x005c, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x005d, code lost:
        r3 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0078, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0079, code lost:
        r2 = r1;
        r3 = null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x004d A[ExcHandler: all (r1v2 'th' java.lang.Throwable A[CUSTOM_DECLARE])] */
    @android.support.annotation.RequiresApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer mmap(android.content.Context r13, android.os.CancellationSignal r14, android.net.Uri r15) {
        /*
            r10 = 0
            android.content.ContentResolver r9 = r13.getContentResolver()
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r8 = r9.openFileDescriptor(r15, r1, r14)     // Catch:{ IOException -> 0x0046 }
            r11 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            java.io.FileDescriptor r1 = r8.getFileDescriptor()     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            r7.<init>(r1)     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            r12 = 0
            java.nio.channels.FileChannel r0 = r7.getChannel()     // Catch:{ Throwable -> 0x005a, all -> 0x0078 }
            long r4 = r0.size()     // Catch:{ Throwable -> 0x005a, all -> 0x0078 }
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x005a, all -> 0x0078 }
            r2 = 0
            java.nio.MappedByteBuffer r1 = r0.map(r1, r2, r4)     // Catch:{ Throwable -> 0x005a, all -> 0x0078 }
            if (r7 == 0) goto L_0x002d
            if (r10 == 0) goto L_0x0049
            r7.close()     // Catch:{ Throwable -> 0x0035, all -> 0x004d }
        L_0x002d:
            if (r8 == 0) goto L_0x0034
            if (r10 == 0) goto L_0x0056
            r8.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0034:
            return r1
        L_0x0035:
            r2 = move-exception
            r12.addSuppressed(r2)     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            goto L_0x002d
        L_0x003a:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x003c }
        L_0x003c:
            r2 = move-exception
            r3 = r1
        L_0x003e:
            if (r8 == 0) goto L_0x0045
            if (r3 == 0) goto L_0x0074
            r8.close()     // Catch:{ Throwable -> 0x006f }
        L_0x0045:
            throw r2     // Catch:{ IOException -> 0x0046 }
        L_0x0046:
            r6 = move-exception
            r1 = r10
            goto L_0x0034
        L_0x0049:
            r7.close()     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            goto L_0x002d
        L_0x004d:
            r1 = move-exception
            r2 = r1
            r3 = r10
            goto L_0x003e
        L_0x0051:
            r2 = move-exception
            r11.addSuppressed(r2)     // Catch:{ IOException -> 0x0046 }
            goto L_0x0034
        L_0x0056:
            r8.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0034
        L_0x005a:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x005c }
        L_0x005c:
            r2 = move-exception
            r3 = r1
        L_0x005e:
            if (r7 == 0) goto L_0x0065
            if (r3 == 0) goto L_0x006b
            r7.close()     // Catch:{ Throwable -> 0x0066, all -> 0x004d }
        L_0x0065:
            throw r2     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
        L_0x0066:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            goto L_0x0065
        L_0x006b:
            r7.close()     // Catch:{ Throwable -> 0x003a, all -> 0x004d }
            goto L_0x0065
        L_0x006f:
            r1 = move-exception
            r3.addSuppressed(r1)     // Catch:{ IOException -> 0x0046 }
            goto L_0x0045
        L_0x0074:
            r8.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x0045
        L_0x0078:
            r1 = move-exception
            r2 = r1
            r3 = r10
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        ByteBuffer byteBuffer = null;
        File tmpFile = getTempFile(context);
        if (tmpFile != null) {
            try {
                if (copyToFile(tmpFile, res, id)) {
                    byteBuffer = mmap(tmpFile);
                    tmpFile.delete();
                }
            } finally {
                tmpFile.delete();
            }
        }
        return byteBuffer;
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        try {
            FileOutputStream os2 = new FileOutputStream(file, false);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen != -1) {
                        os2.write(buffer, 0, readLen);
                    } else {
                        closeQuietly(os2);
                        FileOutputStream fileOutputStream = os2;
                        return true;
                    }
                }
            } catch (IOException e) {
                e = e;
                os = os2;
                try {
                    Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
                    closeQuietly(os);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(os);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                os = os2;
                closeQuietly(os);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            closeQuietly(os);
            return false;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
