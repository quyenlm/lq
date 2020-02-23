package com.banalytics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MultipartUtility {
    private static final String LINE_FEED = "\r\n";
    private final String boundary = ("===" + System.currentTimeMillis() + "===");
    private String charset;
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;

    protected MultipartUtility(String requestURL, String charset2) throws IOException {
        this.charset = charset2;
        this.httpConn = (HttpURLConnection) new URL(requestURL).openConnection();
        this.httpConn.setUseCaches(false);
        this.httpConn.setDoOutput(true);
        this.httpConn.setDoInput(true);
        this.httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
        this.outputStream = this.httpConn.getOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, charset2), true);
    }

    private void addFormField(String name, String value) {
        this.writer.append("--" + this.boundary).append(LINE_FEED);
        this.writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        this.writer.append("Content-Type: text/plain; charset=" + this.charset).append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.append(value).append(LINE_FEED);
        this.writer.flush();
    }

    /* access modifiers changed from: protected */
    public void addFilePart(String fieldName, File uploadFile) throws IOException {
        String fileName = uploadFile.getName();
        this.writer.append("--" + this.boundary).append(LINE_FEED);
        this.writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        this.writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        this.writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        this.writer.append(LINE_FEED);
        this.writer.flush();
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                this.outputStream.write(buffer, 0, bytesRead);
            } else {
                this.outputStream.flush();
                inputStream.close();
                this.writer.append(LINE_FEED);
                this.writer.flush();
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addHeaderField(String name, String value) {
        this.writer.append(name + ": " + value).append(LINE_FEED);
        this.writer.flush();
    }

    public boolean finish() throws IOException {
        this.writer.append(LINE_FEED).flush();
        this.writer.append("--" + this.boundary + "--").append(LINE_FEED);
        this.writer.close();
        int status = this.httpConn.getResponseCode();
        if (status == 200) {
            this.httpConn.disconnect();
            return true;
        }
        throw new IOException("Server returned non-OK status: " + status);
    }
}
