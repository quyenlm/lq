package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonReader;
import com.amazonaws.util.json.JsonUtils;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Deprecated
public abstract class PersistableTransfer {
    public abstract String serialize();

    public final void serialize(OutputStream out) throws IOException {
        out.write(serialize().getBytes(StringUtils.UTF8));
        out.flush();
    }

    public static <T extends PersistableTransfer> T deserializeFrom(InputStream in) {
        String type = null;
        String bucketName = null;
        String key = null;
        String file = null;
        String multipartUploadId = null;
        long partSize = -1;
        long mutlipartUploadThreshold = -1;
        String versionId = null;
        long[] range = null;
        ResponseHeaderOverrides responseHeaders = null;
        boolean isRequesterPays = false;
        AwsJsonReader reader = JsonUtils.getJsonReader(new BufferedReader(new InputStreamReader(in, StringUtils.UTF8)));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("pauseType")) {
                    type = reader.nextString();
                } else if (name.equals("bucketName")) {
                    bucketName = reader.nextString();
                } else if (name.equals("key")) {
                    key = reader.nextString();
                } else if (name.equals(TransferTable.COLUMN_FILE)) {
                    file = reader.nextString();
                } else if (name.equals("multipartUploadId")) {
                    multipartUploadId = reader.nextString();
                } else if (name.equals("partSize")) {
                    partSize = Long.parseLong(reader.nextString());
                } else if (name.equals("mutlipartUploadThreshold")) {
                    mutlipartUploadThreshold = Long.parseLong(reader.nextString());
                } else if (name.equals("versionId")) {
                    versionId = reader.nextString();
                } else if (name.equals("range")) {
                    reader.beginArray();
                    range = new long[]{Long.parseLong(reader.nextString()), Long.parseLong(reader.nextString())};
                    reader.endArray();
                } else if (name.equals("responseHeaders")) {
                    ResponseHeaderOverrides responseHeaders2 = new ResponseHeaderOverrides();
                    try {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String n = reader.nextName();
                            if (n.equals(HttpRequestParams.NOTICE_CONTENT_TYPE)) {
                                responseHeaders2.setContentType(reader.nextString());
                            } else if (n.equals("contentLanguage")) {
                                responseHeaders2.setContentLanguage(reader.nextString());
                            } else if (n.equals("expires")) {
                                responseHeaders2.setExpires(reader.nextString());
                            } else if (n.equals("cacheControl")) {
                                responseHeaders2.setCacheControl(reader.nextString());
                            } else if (n.equals("contentDisposition")) {
                                responseHeaders2.setContentDisposition(reader.nextString());
                            } else if (n.equals("contentEncoding")) {
                                responseHeaders2.setContentEncoding(reader.nextString());
                            } else {
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                        responseHeaders = responseHeaders2;
                    } catch (IOException e) {
                        e = e;
                        ResponseHeaderOverrides responseHeaderOverrides = responseHeaders2;
                        throw new IllegalStateException(e);
                    }
                } else if (name.equals("isRequesterPays")) {
                    isRequesterPays = Boolean.parseBoolean(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            if (DownloadDBHelper.TABLE_DOWNLOAD.equals(type)) {
                return new PersistableDownload(bucketName, key, versionId, range, responseHeaders, isRequesterPays, file);
            }
            if ("upload".equals(type)) {
                return new PersistableUpload(bucketName, key, file, multipartUploadId, partSize, mutlipartUploadThreshold);
            }
            throw new UnsupportedOperationException("Unsupported paused transfer type: " + type);
        } catch (IOException e2) {
            e = e2;
            throw new IllegalStateException(e);
        }
    }

    public static <T extends PersistableTransfer> T deserializeFrom(String serialized) {
        if (serialized == null) {
            return null;
        }
        ByteArrayInputStream byteStream = new ByteArrayInputStream(serialized.getBytes(StringUtils.UTF8));
        try {
            T deserializeFrom = deserializeFrom((InputStream) byteStream);
            try {
                return deserializeFrom;
            } catch (IOException e) {
                return deserializeFrom;
            }
        } finally {
            try {
                byteStream.close();
            } catch (IOException e2) {
            }
        }
    }
}
