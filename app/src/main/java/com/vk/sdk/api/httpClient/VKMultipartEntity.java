package com.vk.sdk.api.httpClient;

import android.util.Pair;
import android.webkit.MimeTypeMap;
import com.vk.sdk.api.model.VKAttachments;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Random;

public class VKMultipartEntity {
    private static final String VK_BOUNDARY = "Boundary(======VK_SDK_%d======)";
    private final String mBoundary = String.format(Locale.US, VK_BOUNDARY, new Object[]{Integer.valueOf(new Random().nextInt())});
    private final File[] mFiles;
    private String mType;

    public VKMultipartEntity(File[] files) {
        this.mFiles = files;
    }

    public VKMultipartEntity(File[] files, String type) {
        this.mFiles = files;
        this.mType = type;
    }

    public long getContentLength() {
        long length = 0;
        for (int i = 0; i < this.mFiles.length; i++) {
            File f = this.mFiles[i];
            length = length + f.length() + ((long) getFileDescription(f, i).length());
        }
        return length + ((long) getBoundaryEnd().length());
    }

    public Pair<String, String> getContentType() {
        return new Pair<>("Content-Type", String.format("multipart/form-data; boundary=%s", new Object[]{this.mBoundary}));
    }

    public InputStream getContent() throws IOException, IllegalStateException {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    /* access modifiers changed from: protected */
    public String getFileDescription(File uploadFile, int i) {
        String fileName;
        if (this.mType == null || !this.mType.equals(VKAttachments.TYPE_DOC)) {
            fileName = String.format(Locale.US, "file%d", new Object[]{Integer.valueOf(i + 1)});
        } else {
            fileName = TransferTable.COLUMN_FILE;
        }
        String extension = MimeTypeMap.getFileExtensionFromUrl(uploadFile.getAbsolutePath());
        return String.format("\r\n--%s\r\n", new Object[]{this.mBoundary}) + String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s.%s\"\r\n", new Object[]{fileName, fileName, extension}) + String.format("Content-Type: %s\r\n\r\n", new Object[]{getMimeType(uploadFile.getAbsolutePath())});
    }

    private String getBoundaryEnd() {
        return String.format("\r\n--%s--\r\n", new Object[]{this.mBoundary});
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        for (int i = 0; i < this.mFiles.length; i++) {
            File uploadFile = this.mFiles[i];
            outputStream.write(getFileDescription(uploadFile, i).getBytes("UTF-8"));
            FileInputStream reader = new FileInputStream(uploadFile);
            byte[] fileBuffer = new byte[2048];
            while (true) {
                int bytesRead = reader.read(fileBuffer);
                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(fileBuffer, 0, bytesRead);
            }
            reader.close();
        }
        outputStream.write(getBoundaryEnd().getBytes("UTF-8"));
    }

    protected static String getMimeType(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return null;
    }
}
