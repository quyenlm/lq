package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.internal.XmlWriter;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Owner;
import com.tencent.imsdk.expansion.downloader.impl.DownloadsDB;

public class AclXmlFactory {
    public byte[] convertToXmlByteArray(AccessControlList acl) throws AmazonClientException {
        Owner owner = acl.getOwner();
        if (owner == null) {
            throw new AmazonClientException("Invalid AccessControlList: missing an S3Owner");
        }
        XmlWriter xml = new XmlWriter();
        xml.start("AccessControlPolicy", "xmlns", Constants.XML_NAMESPACE);
        xml.start("Owner");
        if (owner.getId() != null) {
            xml.start("ID").value(owner.getId()).end();
        }
        if (owner.getDisplayName() != null) {
            xml.start("DisplayName").value(owner.getDisplayName()).end();
        }
        xml.end();
        xml.start("AccessControlList");
        for (Grant grant : acl.getGrants()) {
            xml.start("Grant");
            convertToXml(grant.getGrantee(), xml);
            xml.start("Permission").value(grant.getPermission().toString()).end();
            xml.end();
        }
        xml.end();
        xml.end();
        return xml.getBytes();
    }

    /* access modifiers changed from: protected */
    public XmlWriter convertToXml(Grantee grantee, XmlWriter xml) throws AmazonClientException {
        if (grantee instanceof CanonicalGrantee) {
            return convertToXml((CanonicalGrantee) grantee, xml);
        }
        if (grantee instanceof EmailAddressGrantee) {
            return convertToXml((EmailAddressGrantee) grantee, xml);
        }
        if (grantee instanceof GroupGrantee) {
            return convertToXml((GroupGrantee) grantee, xml);
        }
        throw new AmazonClientException("Unknown Grantee type: " + grantee.getClass().getName());
    }

    /* access modifiers changed from: protected */
    public XmlWriter convertToXml(CanonicalGrantee grantee, XmlWriter xml) {
        xml.start("Grantee", new String[]{"xmlns:xsi", "xsi:type"}, new String[]{"http://www.w3.org/2001/XMLSchema-instance", "CanonicalUser"});
        xml.start("ID").value(grantee.getIdentifier()).end();
        xml.end();
        return xml;
    }

    /* access modifiers changed from: protected */
    public XmlWriter convertToXml(EmailAddressGrantee grantee, XmlWriter xml) {
        xml.start("Grantee", new String[]{"xmlns:xsi", "xsi:type"}, new String[]{"http://www.w3.org/2001/XMLSchema-instance", "AmazonCustomerByEmail"});
        xml.start("EmailAddress").value(grantee.getIdentifier()).end();
        xml.end();
        return xml;
    }

    /* access modifiers changed from: protected */
    public XmlWriter convertToXml(GroupGrantee grantee, XmlWriter xml) {
        xml.start("Grantee", new String[]{"xmlns:xsi", "xsi:type"}, new String[]{"http://www.w3.org/2001/XMLSchema-instance", "Group"});
        xml.start(DownloadsDB.DownloadColumns.URI).value(grantee.getIdentifier()).end();
        xml.end();
        return xml;
    }
}
