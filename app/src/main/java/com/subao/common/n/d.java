package com.subao.common.n;

import com.subao.common.e;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* compiled from: FileUtils */
public class d {

    /* compiled from: FileUtils */
    public interface a {
        public static final a a = new a() {
            public File a(String str) {
                return new File(str);
            }

            public Reader b(String str) {
                return new FileReader(str);
            }

            public InputStream c(String str) {
                return new FileInputStream(str);
            }
        };

        File a(String str);

        Reader b(String str);

        InputStream c(String str);
    }

    public static byte[] a(File file, int i) {
        FileInputStream fileInputStream;
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        int length = (int) file.length();
        if (length > i) {
            throw new IOException("File is too large.");
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                com.subao.common.d.a aVar = new com.subao.common.d.a(length);
                do {
                } while (aVar.a(fileInputStream, length) > 0);
                byte[] a2 = aVar.a();
                e.a((Closeable) fileInputStream);
                return a2;
            } catch (Throwable th) {
                th = th;
                e.a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            e.a((Closeable) fileInputStream);
            throw th;
        }
    }
}
