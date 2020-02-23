package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.XmlWriter;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.RestoreObjectRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RequestXmlFactory {
    public static byte[] convertToXmlByteArray(List<PartETag> partETags) {
        XmlWriter xml = new XmlWriter();
        xml.start("CompleteMultipartUpload");
        if (partETags != null) {
            Collections.sort(partETags, new Comparator<PartETag>() {
                public int compare(PartETag tag1, PartETag tag2) {
                    if (tag1.getPartNumber() < tag2.getPartNumber()) {
                        return -1;
                    }
                    if (tag1.getPartNumber() > tag2.getPartNumber()) {
                        return 1;
                    }
                    return 0;
                }
            });
            for (PartETag partEtag : partETags) {
                xml.start("Part");
                xml.start("PartNumber").value(Integer.toString(partEtag.getPartNumber())).end();
                xml.start(Headers.ETAG).value(partEtag.getETag()).end();
                xml.end();
            }
        }
        xml.end();
        return xml.getBytes();
    }

    public static byte[] convertToXmlByteArray(RestoreObjectRequest restoreObjectRequest) throws AmazonClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("RestoreRequest");
        xml.start("Days").value(Integer.toString(restoreObjectRequest.getExpirationInDays())).end();
        xml.end();
        return xml.getBytes();
    }
}
