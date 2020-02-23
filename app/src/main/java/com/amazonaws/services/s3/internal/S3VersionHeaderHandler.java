package com.amazonaws.services.s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser;

public class S3VersionHeaderHandler implements HeaderHandler<XmlResponsesSaxParser.CopyObjectResultHandler> {
    public void handle(XmlResponsesSaxParser.CopyObjectResultHandler result, HttpResponse response) {
        result.setVersionId(response.getHeaders().get(Headers.S3_VERSION_ID));
    }
}
