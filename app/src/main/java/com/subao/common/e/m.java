package com.subao.common.e;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.facebook.internal.ServerProtocol;
import com.subao.common.e;
import com.subao.common.e.n;
import com.vk.sdk.api.model.VKAttachments;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DebugConfig */
public abstract class m {
    /* access modifiers changed from: protected */
    @NonNull
    public abstract String a();

    /* access modifiers changed from: protected */
    public abstract void a(@NonNull byte[] bArr);

    protected static String a(n.a aVar) {
        if (aVar != null) {
            switch (aVar) {
                case ROM:
                    return "rom";
                case SDK:
                    return ServerProtocol.DIALOG_PARAM_SDK_VERSION;
            }
        }
        return VKAttachments.TYPE_APP;
    }

    public boolean a(@NonNull Context context, n.a aVar) {
        InputStream inputStream;
        Throwable th;
        InputStream inputStream2 = null;
        try {
            InputStream b = b(context, aVar);
            try {
                boolean a = a(b);
                e.a((Closeable) b);
                return a;
            } catch (IOException e) {
                inputStream2 = b;
                e.a((Closeable) inputStream2);
                return false;
            } catch (Throwable th2) {
                th = th2;
                inputStream = b;
                e.a((Closeable) inputStream);
                throw th;
            }
        } catch (IOException e2) {
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            e.a((Closeable) inputStream);
            throw th;
        }
    }

    private boolean a(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read <= 0) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
        byteArrayOutputStream.close();
        if (byteArrayOutputStream.size() <= 0) {
            return false;
        }
        a(byteArrayOutputStream.toByteArray());
        return true;
    }

    /* access modifiers changed from: protected */
    public InputStream b(@NonNull Context context, n.a aVar) {
        Uri b = b(aVar);
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return null;
        }
        try {
            InputStream openInputStream = contentResolver.openInputStream(b);
            if (openInputStream != null) {
                return openInputStream;
            }
            return null;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Uri b(n.a aVar) {
        return Uri.parse(String.format("content://cn.wsds.service.config/%s.%s", new Object[]{a(), a(aVar)}));
    }
}
