package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.internal.XmlWriter;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;

public class MultiObjectDeleteXmlFactory {
    public byte[] convertToXmlByteArray(DeleteObjectsRequest rq) throws AmazonClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("Delete");
        if (rq.getQuiet()) {
            xml.start("Quiet").value("true").end();
        }
        for (DeleteObjectsRequest.KeyVersion keyVersion : rq.getKeys()) {
            writeKeyVersion(xml, keyVersion);
        }
        xml.end();
        return xml.getBytes();
    }

    private void writeKeyVersion(XmlWriter xml, DeleteObjectsRequest.KeyVersion keyVersion) {
        xml.start("Object");
        xml.start("Key").value(keyVersion.getKey()).end();
        if (keyVersion.getVersion() != null) {
            xml.start("VersionId").value(keyVersion.getVersion()).end();
        }
        xml.end();
    }
}
