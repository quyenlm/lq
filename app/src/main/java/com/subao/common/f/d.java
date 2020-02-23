package com.subao.common.f;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

/* compiled from: PersistentFactory */
public class d {
    @NonNull
    public static c a(@NonNull File file) {
        return new a(file);
    }

    /* compiled from: PersistentFactory */
    private static class a implements c {
        @NonNull
        private final File a;

        a(@NonNull File file) {
            this.a = file;
        }

        public String a() {
            return this.a.getName();
        }

        public String i() {
            return this.a.getAbsolutePath();
        }

        public boolean b() {
            return this.a.exists();
        }

        @NonNull
        public InputStream c() {
            return new FileInputStream(this.a);
        }

        @NonNull
        public OutputStream d() {
            return new FileOutputStream(this.a);
        }

        @NonNull
        public OutputStream e() {
            return new FileOutputStream(this.a, true);
        }

        public boolean f() {
            return this.a.delete();
        }

        @NonNull
        public c a(String str) {
            if (!this.a.exists() || !this.a.isDirectory()) {
                this.a.mkdirs();
            }
            return new a(new File(this.a, str));
        }

        @Nullable
        public byte[] g() {
            return a(0);
        }

        @Nullable
        public byte[] a(int i) {
            byte[] bArr = null;
            int length = (int) this.a.length();
            if (i <= 0 || length <= i) {
                FileInputStream fileInputStream = new FileInputStream(this.a);
                try {
                    byte[] bArr2 = new byte[length];
                    if (fileInputStream.read(bArr2) == length) {
                        bArr = bArr2;
                    }
                } finally {
                    e.a((Closeable) fileInputStream);
                }
            }
            return bArr;
        }

        @Nullable
        public Iterable<c> h() {
            File[] listFiles = this.a.listFiles();
            if (listFiles == null) {
                return null;
            }
            return new C0009a(listFiles);
        }

        /* renamed from: com.subao.common.f.d$a$a  reason: collision with other inner class name */
        /* compiled from: PersistentFactory */
        private static class C0009a implements Iterable<c> {
            /* access modifiers changed from: private */
            @NonNull
            public final File[] a;

            C0009a(@NonNull File[] fileArr) {
                this.a = fileArr;
            }

            @NonNull
            public Iterator<c> iterator() {
                return new C0010a();
            }

            /* renamed from: com.subao.common.f.d$a$a$a  reason: collision with other inner class name */
            /* compiled from: PersistentFactory */
            private class C0010a implements Iterator<c> {
                private int b;

                private C0010a() {
                }

                public boolean hasNext() {
                    return this.b < C0009a.this.a.length;
                }

                /* renamed from: a */
                public c next() {
                    File[] a2 = C0009a.this.a;
                    int i = this.b;
                    this.b = i + 1;
                    return new a(a2[i]);
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            }
        }
    }
}
