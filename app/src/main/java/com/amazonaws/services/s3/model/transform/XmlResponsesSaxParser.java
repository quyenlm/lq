package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.internal.DeleteObjectsResponse;
import com.amazonaws.services.s3.internal.ObjectExpirationResult;
import com.amazonaws.services.s3.internal.ServerSideEncryptionResult;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.BucketReplicationConfiguration;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.ReplicationDestinationConfig;
import com.amazonaws.services.s3.model.ReplicationRule;
import com.amazonaws.services.s3.model.RequestPaymentConfiguration;
import com.amazonaws.services.s3.model.RoutingRule;
import com.amazonaws.services.s3.model.RoutingRuleCondition;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.TagSet;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.StringUtils;
import com.tencent.imsdk.expansion.downloader.impl.DownloadsDB;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlResponsesSaxParser {
    /* access modifiers changed from: private */
    public static final Log log = LogFactory.getLog(XmlResponsesSaxParser.class);
    private final boolean sanitizeXmlDocument = true;
    private XMLReader xr = null;

    public XmlResponsesSaxParser() throws AmazonClientException {
        try {
            this.xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
            try {
                this.xr = XMLReaderFactory.createXMLReader();
            } catch (SAXException e2) {
                throw new AmazonClientException("Couldn't initialize a sax driver for the XMLReader", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseXmlInputStream(DefaultHandler handler, InputStream inputStream) throws IOException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Parsing XML response document with handler: " + handler.getClass());
            }
            BufferedReader breader = new BufferedReader(new InputStreamReader(inputStream, Constants.DEFAULT_ENCODING));
            this.xr.setContentHandler(handler);
            this.xr.setErrorHandler(handler);
            this.xr.parse(new InputSource(breader));
        } catch (IOException e) {
            throw e;
        } catch (Throwable t) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                if (log.isErrorEnabled()) {
                    log.error("Unable to close response InputStream up after XML parse failure", e2);
                }
            }
            throw new AmazonClientException("Failed to parse XML document with handler " + handler.getClass(), t);
        }
    }

    /* access modifiers changed from: protected */
    public InputStream sanitizeXmlDocument(DefaultHandler handler, InputStream inputStream) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Sanitizing XML document destined for handler " + handler.getClass());
        }
        try {
            StringBuilder listingDocBuffer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Constants.DEFAULT_ENCODING));
            char[] buf = new char[8192];
            while (true) {
                int read = br.read(buf);
                if (read != -1) {
                    listingDocBuffer.append(buf, 0, read);
                } else {
                    br.close();
                    return new ByteArrayInputStream(listingDocBuffer.toString().replaceAll("\r", "&#013;").getBytes(StringUtils.UTF8));
                }
            }
        } catch (IOException e) {
            throw e;
        } catch (Throwable t) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                if (log.isErrorEnabled()) {
                    log.error("Unable to close response InputStream after failure sanitizing XML document", e2);
                }
            }
            throw new AmazonClientException("Failed to sanitize XML document destined for handler " + handler.getClass(), t);
        }
    }

    /* access modifiers changed from: private */
    public static String checkForEmptyString(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return null;
        }
        return s;
    }

    /* access modifiers changed from: private */
    public static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            log.error("Unable to parse integer value '" + s + "'", nfe);
            return -1;
        }
    }

    /* access modifiers changed from: private */
    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException nfe) {
            log.error("Unable to parse long value '" + s + "'", nfe);
            return -1;
        }
    }

    public ListBucketHandler parseListBucketObjectsResponse(InputStream inputStream) throws IOException {
        ListBucketHandler handler = new ListBucketHandler();
        parseXmlInputStream(handler, sanitizeXmlDocument(handler, inputStream));
        return handler;
    }

    public ListObjectsV2Handler parseListObjectsV2Response(InputStream inputStream) throws IOException {
        ListObjectsV2Handler handler = new ListObjectsV2Handler();
        parseXmlInputStream(handler, sanitizeXmlDocument(handler, inputStream));
        return handler;
    }

    public ListVersionsHandler parseListVersionsResponse(InputStream inputStream) throws IOException {
        ListVersionsHandler handler = new ListVersionsHandler();
        parseXmlInputStream(handler, sanitizeXmlDocument(handler, inputStream));
        return handler;
    }

    public ListAllMyBucketsHandler parseListMyBucketsResponse(InputStream inputStream) throws IOException {
        ListAllMyBucketsHandler handler = new ListAllMyBucketsHandler();
        parseXmlInputStream(handler, sanitizeXmlDocument(handler, inputStream));
        return handler;
    }

    public AccessControlListHandler parseAccessControlListResponse(InputStream inputStream) throws IOException {
        AccessControlListHandler handler = new AccessControlListHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketLoggingConfigurationHandler parseLoggingStatusResponse(InputStream inputStream) throws IOException {
        BucketLoggingConfigurationHandler handler = new BucketLoggingConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketLifecycleConfigurationHandler parseBucketLifecycleConfigurationResponse(InputStream inputStream) throws IOException {
        BucketLifecycleConfigurationHandler handler = new BucketLifecycleConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketCrossOriginConfigurationHandler parseBucketCrossOriginConfigurationResponse(InputStream inputStream) throws IOException {
        BucketCrossOriginConfigurationHandler handler = new BucketCrossOriginConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public String parseBucketLocationResponse(InputStream inputStream) throws IOException {
        BucketLocationHandler handler = new BucketLocationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler.getLocation();
    }

    public BucketVersioningConfigurationHandler parseVersioningConfigurationResponse(InputStream inputStream) throws IOException {
        BucketVersioningConfigurationHandler handler = new BucketVersioningConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketWebsiteConfigurationHandler parseWebsiteConfigurationResponse(InputStream inputStream) throws IOException {
        BucketWebsiteConfigurationHandler handler = new BucketWebsiteConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketNotificationConfigurationHandler parseNotificationConfigurationResponse(InputStream inputStream) throws IOException {
        BucketNotificationConfigurationHandler handler = new BucketNotificationConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketReplicationConfigurationHandler parseReplicationConfigurationResponse(InputStream inputStream) throws IOException {
        BucketReplicationConfigurationHandler handler = new BucketReplicationConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketTaggingConfigurationHandler parseTaggingConfigurationResponse(InputStream inputStream) throws IOException {
        BucketTaggingConfigurationHandler handler = new BucketTaggingConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public BucketAccelerateConfigurationHandler parseAccelerateConfigurationResponse(InputStream inputStream) throws IOException {
        BucketAccelerateConfigurationHandler handler = new BucketAccelerateConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public DeleteObjectsHandler parseDeletedObjectsResult(InputStream inputStream) throws IOException {
        DeleteObjectsHandler handler = new DeleteObjectsHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public CopyObjectResultHandler parseCopyObjectResponse(InputStream inputStream) throws IOException {
        CopyObjectResultHandler handler = new CopyObjectResultHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public CompleteMultipartUploadHandler parseCompleteMultipartUploadResponse(InputStream inputStream) throws IOException {
        CompleteMultipartUploadHandler handler = new CompleteMultipartUploadHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public InitiateMultipartUploadHandler parseInitiateMultipartUploadResponse(InputStream inputStream) throws IOException {
        InitiateMultipartUploadHandler handler = new InitiateMultipartUploadHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public ListMultipartUploadsHandler parseListMultipartUploadsResponse(InputStream inputStream) throws IOException {
        ListMultipartUploadsHandler handler = new ListMultipartUploadsHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public ListPartsHandler parseListPartsResponse(InputStream inputStream) throws IOException {
        ListPartsHandler handler = new ListPartsHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public RequestPaymentConfigurationHandler parseRequestPaymentConfigurationResponse(InputStream inputStream) throws IOException {
        RequestPaymentConfigurationHandler handler = new RequestPaymentConfigurationHandler();
        parseXmlInputStream(handler, inputStream);
        return handler;
    }

    public static class ListBucketHandler extends AbstractHandler {
        private S3ObjectSummary currentObject = null;
        private Owner currentOwner = null;
        private String lastKey = null;
        private final ObjectListing objectListing = new ObjectListing();

        public ObjectListing getObjectListing() {
            return this.objectListing;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListBucketResult")) {
                if (in("ListBucketResult", "Contents") && name.equals("Owner")) {
                    this.currentOwner = new Owner();
                }
            } else if (name.equals("Contents")) {
                this.currentObject = new S3ObjectSummary();
                this.currentObject.setBucketName(this.objectListing.getBucketName());
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!atTopLevel()) {
                if (!in("ListBucketResult")) {
                    if (!in("ListBucketResult", "Contents")) {
                        if (!in("ListBucketResult", "Contents", "Owner")) {
                            if (in("ListBucketResult", "CommonPrefixes") && name.equals("Prefix")) {
                                this.objectListing.getCommonPrefixes().add(getText());
                            }
                        } else if (name.equals("ID")) {
                            this.currentOwner.setId(getText());
                        } else if (name.equals("DisplayName")) {
                            this.currentOwner.setDisplayName(getText());
                        }
                    } else if (name.equals("Key")) {
                        this.lastKey = getText();
                        this.currentObject.setKey(this.lastKey);
                    } else if (name.equals("LastModified")) {
                        this.currentObject.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                    } else if (name.equals(Headers.ETAG)) {
                        this.currentObject.setETag(ServiceUtils.removeQuotes(getText()));
                    } else if (name.equals("Size")) {
                        this.currentObject.setSize(XmlResponsesSaxParser.parseLong(getText()));
                    } else if (name.equals("StorageClass")) {
                        this.currentObject.setStorageClass(getText());
                    } else if (name.equals("Owner")) {
                        this.currentObject.setOwner(this.currentOwner);
                        this.currentOwner = null;
                    }
                } else if (name.equals("Name")) {
                    this.objectListing.setBucketName(getText());
                    if (XmlResponsesSaxParser.log.isDebugEnabled()) {
                        XmlResponsesSaxParser.log.debug("Examining listing for bucket: " + this.objectListing.getBucketName());
                    }
                } else if (name.equals("Prefix")) {
                    this.objectListing.setPrefix(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("Marker")) {
                    this.objectListing.setMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("NextMarker")) {
                    this.objectListing.setNextMarker(getText());
                } else if (name.equals("MaxKeys")) {
                    this.objectListing.setMaxKeys(XmlResponsesSaxParser.parseInt(getText()));
                } else if (name.equals("Delimiter")) {
                    this.objectListing.setDelimiter(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("EncodingType")) {
                    this.objectListing.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("IsTruncated")) {
                    String isTruncatedStr = StringUtils.lowerCase(getText());
                    if (isTruncatedStr.startsWith("false")) {
                        this.objectListing.setTruncated(false);
                    } else if (isTruncatedStr.startsWith("true")) {
                        this.objectListing.setTruncated(true);
                    } else {
                        throw new IllegalStateException("Invalid value for IsTruncated field: " + isTruncatedStr);
                    }
                } else if (name.equals("Contents")) {
                    this.objectListing.getObjectSummaries().add(this.currentObject);
                    this.currentObject = null;
                }
            } else if (name.equals("ListBucketResult") && this.objectListing.isTruncated() && this.objectListing.getNextMarker() == null) {
                String nextMarker = null;
                if (!this.objectListing.getObjectSummaries().isEmpty()) {
                    nextMarker = this.objectListing.getObjectSummaries().get(this.objectListing.getObjectSummaries().size() - 1).getKey();
                } else if (!this.objectListing.getCommonPrefixes().isEmpty()) {
                    nextMarker = this.objectListing.getCommonPrefixes().get(this.objectListing.getCommonPrefixes().size() - 1);
                } else {
                    XmlResponsesSaxParser.log.error("S3 response indicates truncated results, but contains no object summaries or common prefixes.");
                }
                this.objectListing.setNextMarker(nextMarker);
            }
        }
    }

    public static class ListObjectsV2Handler extends AbstractHandler {
        private S3ObjectSummary currentObject = null;
        private Owner currentOwner = null;
        private String lastKey = null;
        private final ListObjectsV2Result result = new ListObjectsV2Result();

        public ListObjectsV2Result getResult() {
            return this.result;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListBucketResult")) {
                if (in("ListBucketResult", "Contents") && name.equals("Owner")) {
                    this.currentOwner = new Owner();
                }
            } else if (name.equals("Contents")) {
                this.currentObject = new S3ObjectSummary();
                this.currentObject.setBucketName(this.result.getBucketName());
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!atTopLevel()) {
                if (!in("ListBucketResult")) {
                    if (!in("ListBucketResult", "Contents")) {
                        if (!in("ListBucketResult", "Contents", "Owner")) {
                            if (in("ListBucketResult", "CommonPrefixes") && name.equals("Prefix")) {
                                this.result.getCommonPrefixes().add(getText());
                            }
                        } else if (name.equals("ID")) {
                            this.currentOwner.setId(getText());
                        } else if (name.equals("DisplayName")) {
                            this.currentOwner.setDisplayName(getText());
                        }
                    } else if (name.equals("Key")) {
                        this.lastKey = getText();
                        this.currentObject.setKey(this.lastKey);
                    } else if (name.equals("LastModified")) {
                        this.currentObject.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                    } else if (name.equals(Headers.ETAG)) {
                        this.currentObject.setETag(ServiceUtils.removeQuotes(getText()));
                    } else if (name.equals("Size")) {
                        this.currentObject.setSize(XmlResponsesSaxParser.parseLong(getText()));
                    } else if (name.equals("StorageClass")) {
                        this.currentObject.setStorageClass(getText());
                    } else if (name.equals("Owner")) {
                        this.currentObject.setOwner(this.currentOwner);
                        this.currentOwner = null;
                    }
                } else if (name.equals("Name")) {
                    this.result.setBucketName(getText());
                    if (XmlResponsesSaxParser.log.isDebugEnabled()) {
                        XmlResponsesSaxParser.log.debug("Examining listing for bucket: " + this.result.getBucketName());
                    }
                } else if (name.equals("Prefix")) {
                    this.result.setPrefix(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("MaxKeys")) {
                    this.result.setMaxKeys(XmlResponsesSaxParser.parseInt(getText()));
                } else if (name.equals("NextContinuationToken")) {
                    this.result.setNextContinuationToken(getText());
                } else if (name.equals("ContinuationToken")) {
                    this.result.setContinuationToken(getText());
                } else if (name.equals("StartAfter")) {
                    this.result.setStartAfter(getText());
                } else if (name.equals("KeyCount")) {
                    this.result.setKeyCount(XmlResponsesSaxParser.parseInt(getText()));
                } else if (name.equals("Delimiter")) {
                    this.result.setDelimiter(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("EncodingType")) {
                    this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (name.equals("IsTruncated")) {
                    String isTruncatedStr = StringUtils.lowerCase(getText());
                    if (isTruncatedStr.startsWith("false")) {
                        this.result.setTruncated(false);
                    } else if (isTruncatedStr.startsWith("true")) {
                        this.result.setTruncated(true);
                    } else {
                        throw new IllegalStateException("Invalid value for IsTruncated field: " + isTruncatedStr);
                    }
                } else if (name.equals("Contents")) {
                    this.result.getObjectSummaries().add(this.currentObject);
                    this.currentObject = null;
                }
            } else if (name.equals("ListBucketResult") && this.result.isTruncated() && this.result.getNextContinuationToken() == null) {
                String nextContinuationToken = null;
                if (!this.result.getObjectSummaries().isEmpty()) {
                    nextContinuationToken = this.result.getObjectSummaries().get(this.result.getObjectSummaries().size() - 1).getKey();
                } else {
                    XmlResponsesSaxParser.log.error("S3 response indicates truncated results, but contains no object summaries.");
                }
                this.result.setNextContinuationToken(nextContinuationToken);
            }
        }
    }

    public static class ListAllMyBucketsHandler extends AbstractHandler {
        private final List<Bucket> buckets = new ArrayList();
        private Owner bucketsOwner = null;
        private Bucket currentBucket = null;

        public List<Bucket> getBuckets() {
            return this.buckets;
        }

        public Owner getOwner() {
            return this.bucketsOwner;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListAllMyBucketsResult")) {
                if (in("ListAllMyBucketsResult", "Buckets") && name.equals("Bucket")) {
                    this.currentBucket = new Bucket();
                    this.currentBucket.setOwner(this.bucketsOwner);
                }
            } else if (name.equals("Owner")) {
                this.bucketsOwner = new Owner();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("ListAllMyBucketsResult", "Owner")) {
                if (!in("ListAllMyBucketsResult", "Buckets")) {
                    if (!in("ListAllMyBucketsResult", "Buckets", "Bucket")) {
                        return;
                    }
                    if (name.equals("Name")) {
                        this.currentBucket.setName(getText());
                    } else if (name.equals("CreationDate")) {
                        this.currentBucket.setCreationDate(DateUtils.parseISO8601Date(getText()));
                    }
                } else if (name.equals("Bucket")) {
                    this.buckets.add(this.currentBucket);
                    this.currentBucket = null;
                }
            } else if (name.equals("ID")) {
                this.bucketsOwner.setId(getText());
            } else if (name.equals("DisplayName")) {
                this.bucketsOwner.setDisplayName(getText());
            }
        }
    }

    public static class AccessControlListHandler extends AbstractHandler {
        private final AccessControlList accessControlList = new AccessControlList();
        private Grantee currentGrantee = null;
        private Permission currentPermission = null;

        public AccessControlList getAccessControlList() {
            return this.accessControlList;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("AccessControlPolicy")) {
                if (in("AccessControlPolicy", "AccessControlList", "Grant") && name.equals("Grantee")) {
                    String type = XmlResponsesSaxParser.findAttributeValue("xsi:type", attrs);
                    if ("AmazonCustomerByEmail".equals(type)) {
                        this.currentGrantee = new EmailAddressGrantee((String) null);
                    } else if ("CanonicalUser".equals(type)) {
                        this.currentGrantee = new CanonicalGrantee((String) null);
                    } else {
                        if ("Group".equals(type)) {
                        }
                    }
                }
            } else if (name.equals("Owner")) {
                this.accessControlList.setOwner(new Owner());
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("AccessControlPolicy", "Owner")) {
                if (!in("AccessControlPolicy", "AccessControlList")) {
                    if (!in("AccessControlPolicy", "AccessControlList", "Grant")) {
                        if (!in("AccessControlPolicy", "AccessControlList", "Grant", "Grantee")) {
                            return;
                        }
                        if (name.equals("ID")) {
                            this.currentGrantee.setIdentifier(getText());
                        } else if (name.equals("EmailAddress")) {
                            this.currentGrantee.setIdentifier(getText());
                        } else if (name.equals(DownloadsDB.DownloadColumns.URI)) {
                            this.currentGrantee = GroupGrantee.parseGroupGrantee(getText());
                        } else if (name.equals("DisplayName")) {
                            ((CanonicalGrantee) this.currentGrantee).setDisplayName(getText());
                        }
                    } else if (name.equals("Permission")) {
                        this.currentPermission = Permission.parsePermission(getText());
                    }
                } else if (name.equals("Grant")) {
                    this.accessControlList.grantPermission(this.currentGrantee, this.currentPermission);
                    this.currentGrantee = null;
                    this.currentPermission = null;
                }
            } else if (name.equals("ID")) {
                this.accessControlList.getOwner().setId(getText());
            } else if (name.equals("DisplayName")) {
                this.accessControlList.getOwner().setDisplayName(getText());
            }
        }
    }

    public static class BucketLoggingConfigurationHandler extends AbstractHandler {
        private final BucketLoggingConfiguration bucketLoggingConfiguration = new BucketLoggingConfiguration();

        public BucketLoggingConfiguration getBucketLoggingConfiguration() {
            return this.bucketLoggingConfiguration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("BucketLoggingStatus", "LoggingEnabled")) {
                return;
            }
            if (name.equals("TargetBucket")) {
                this.bucketLoggingConfiguration.setDestinationBucketName(getText());
            } else if (name.equals("TargetPrefix")) {
                this.bucketLoggingConfiguration.setLogFilePrefix(getText());
            }
        }
    }

    public static class BucketLocationHandler extends AbstractHandler {
        private String location = null;

        public String getLocation() {
            return this.location;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (atTopLevel() && name.equals("LocationConstraint")) {
                String elementText = getText();
                if (elementText.length() == 0) {
                    this.location = null;
                } else {
                    this.location = elementText;
                }
            }
        }
    }

    public static class CopyObjectResultHandler extends AbstractSSEHandler implements ObjectExpirationResult {
        private String errorCode = null;
        private String errorHostId = null;
        private String errorMessage = null;
        private String errorRequestId = null;
        private boolean receivedErrorResponse = false;
        private final CopyObjectResult result = new CopyObjectResult();

        public /* bridge */ /* synthetic */ String getSSEKMSKeyId() {
            return super.getSSEKMSKeyId();
        }

        public /* bridge */ /* synthetic */ void setSSEKMSKeyId(String str) {
            super.setSSEKMSKeyId(str);
        }

        /* access modifiers changed from: protected */
        public ServerSideEncryptionResult sseResult() {
            return this.result;
        }

        public Date getLastModified() {
            return this.result.getLastModifiedDate();
        }

        public String getVersionId() {
            return this.result.getVersionId();
        }

        public void setVersionId(String versionId) {
            this.result.setVersionId(versionId);
        }

        public Date getExpirationTime() {
            return this.result.getExpirationTime();
        }

        public void setExpirationTime(Date expirationTime) {
            this.result.setExpirationTime(expirationTime);
        }

        public String getExpirationTimeRuleId() {
            return this.result.getExpirationTimeRuleId();
        }

        public void setExpirationTimeRuleId(String expirationTimeRuleId) {
            this.result.setExpirationTimeRuleId(expirationTimeRuleId);
        }

        public String getETag() {
            return this.result.getETag();
        }

        public String getErrorCode() {
            return this.errorCode;
        }

        public String getErrorHostId() {
            return this.errorHostId;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public String getErrorRequestId() {
            return this.errorRequestId;
        }

        public boolean isErrorResponse() {
            return this.receivedErrorResponse;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!atTopLevel()) {
                return;
            }
            if (name.equals("CopyObjectResult") || name.equals("CopyPartResult")) {
                this.receivedErrorResponse = false;
            } else if (name.equals("Error")) {
                this.receivedErrorResponse = true;
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("CopyObjectResult")) {
                if (!in("CopyPartResult")) {
                    if (!in("Error")) {
                        return;
                    }
                    if (name.equals("Code")) {
                        this.errorCode = getText();
                        return;
                    } else if (name.equals("Message")) {
                        this.errorMessage = getText();
                        return;
                    } else if (name.equals("RequestId")) {
                        this.errorRequestId = getText();
                        return;
                    } else if (name.equals("HostId")) {
                        this.errorHostId = getText();
                        return;
                    } else {
                        return;
                    }
                }
            }
            if (name.equals("LastModified")) {
                this.result.setLastModifiedDate(ServiceUtils.parseIso8601Date(getText()));
            } else if (name.equals(Headers.ETAG)) {
                this.result.setETag(ServiceUtils.removeQuotes(getText()));
            }
        }
    }

    public static class RequestPaymentConfigurationHandler extends AbstractHandler {
        private String payer = null;

        public RequestPaymentConfiguration getConfiguration() {
            return new RequestPaymentConfiguration(RequestPaymentConfiguration.Payer.valueOf(this.payer));
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (in("RequestPaymentConfiguration") && name.equals("Payer")) {
                this.payer = getText();
            }
        }
    }

    public static class ListVersionsHandler extends AbstractHandler {
        private Owner currentOwner;
        private S3VersionSummary currentVersionSummary;
        private final VersionListing versionListing = new VersionListing();

        public VersionListing getListing() {
            return this.versionListing;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListVersionsResult")) {
                if (!in("ListVersionsResult", JsonDocumentFields.VERSION)) {
                    if (!in("ListVersionsResult", "DeleteMarker")) {
                        return;
                    }
                }
                if (name.equals("Owner")) {
                    this.currentOwner = new Owner();
                }
            } else if (name.equals(JsonDocumentFields.VERSION)) {
                this.currentVersionSummary = new S3VersionSummary();
                this.currentVersionSummary.setBucketName(this.versionListing.getBucketName());
            } else if (name.equals("DeleteMarker")) {
                this.currentVersionSummary = new S3VersionSummary();
                this.currentVersionSummary.setBucketName(this.versionListing.getBucketName());
                this.currentVersionSummary.setIsDeleteMarker(true);
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("ListVersionsResult")) {
                if (!in("ListVersionsResult", "CommonPrefixes")) {
                    if (!in("ListVersionsResult", JsonDocumentFields.VERSION)) {
                        if (!in("ListVersionsResult", "DeleteMarker")) {
                            if (!in("ListVersionsResult", JsonDocumentFields.VERSION, "Owner")) {
                                if (!in("ListVersionsResult", "DeleteMarker", "Owner")) {
                                    return;
                                }
                            }
                            if (name.equals("ID")) {
                                this.currentOwner.setId(getText());
                                return;
                            } else if (name.equals("DisplayName")) {
                                this.currentOwner.setDisplayName(getText());
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    if (name.equals("Key")) {
                        this.currentVersionSummary.setKey(getText());
                    } else if (name.equals("VersionId")) {
                        this.currentVersionSummary.setVersionId(getText());
                    } else if (name.equals("IsLatest")) {
                        this.currentVersionSummary.setIsLatest("true".equals(getText()));
                    } else if (name.equals("LastModified")) {
                        this.currentVersionSummary.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                    } else if (name.equals(Headers.ETAG)) {
                        this.currentVersionSummary.setETag(ServiceUtils.removeQuotes(getText()));
                    } else if (name.equals("Size")) {
                        this.currentVersionSummary.setSize(Long.parseLong(getText()));
                    } else if (name.equals("Owner")) {
                        this.currentVersionSummary.setOwner(this.currentOwner);
                        this.currentOwner = null;
                    } else if (name.equals("StorageClass")) {
                        this.currentVersionSummary.setStorageClass(getText());
                    }
                } else if (name.equals("Prefix")) {
                    this.versionListing.getCommonPrefixes().add(XmlResponsesSaxParser.checkForEmptyString(getText()));
                }
            } else if (name.equals("Name")) {
                this.versionListing.setBucketName(getText());
            } else if (name.equals("Prefix")) {
                this.versionListing.setPrefix(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("KeyMarker")) {
                this.versionListing.setKeyMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("VersionIdMarker")) {
                this.versionListing.setVersionIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("MaxKeys")) {
                this.versionListing.setMaxKeys(Integer.parseInt(getText()));
            } else if (name.equals("Delimiter")) {
                this.versionListing.setDelimiter(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("EncodingType")) {
                this.versionListing.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("NextKeyMarker")) {
                this.versionListing.setNextKeyMarker(getText());
            } else if (name.equals("NextVersionIdMarker")) {
                this.versionListing.setNextVersionIdMarker(getText());
            } else if (name.equals("IsTruncated")) {
                this.versionListing.setTruncated("true".equals(getText()));
            } else if (name.equals(JsonDocumentFields.VERSION) || name.equals("DeleteMarker")) {
                this.versionListing.getVersionSummaries().add(this.currentVersionSummary);
                this.currentVersionSummary = null;
            }
        }
    }

    public static class BucketWebsiteConfigurationHandler extends AbstractHandler {
        private final BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration((String) null);
        private RoutingRuleCondition currentCondition = null;
        private RedirectRule currentRedirectRule = null;
        private RoutingRule currentRoutingRule = null;

        public BucketWebsiteConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("WebsiteConfiguration")) {
                if (!in("WebsiteConfiguration", "RoutingRules")) {
                    if (!in("WebsiteConfiguration", "RoutingRules", "RoutingRule")) {
                        return;
                    }
                    if (name.equals(JsonDocumentFields.CONDITION)) {
                        this.currentCondition = new RoutingRuleCondition();
                    } else if (name.equals("Redirect")) {
                        this.currentRedirectRule = new RedirectRule();
                    }
                } else if (name.equals("RoutingRule")) {
                    this.currentRoutingRule = new RoutingRule();
                }
            } else if (name.equals("RedirectAllRequestsTo")) {
                this.currentRedirectRule = new RedirectRule();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("WebsiteConfiguration")) {
                if (!in("WebsiteConfiguration", "IndexDocument")) {
                    if (!in("WebsiteConfiguration", "ErrorDocument")) {
                        if (!in("WebsiteConfiguration", "RoutingRules")) {
                            if (!in("WebsiteConfiguration", "RoutingRules", "RoutingRule")) {
                                if (!in("WebsiteConfiguration", "RoutingRules", "RoutingRule", JsonDocumentFields.CONDITION)) {
                                    if (!in("WebsiteConfiguration", "RedirectAllRequestsTo")) {
                                        if (!in("WebsiteConfiguration", "RoutingRules", "RoutingRule", "Redirect")) {
                                            return;
                                        }
                                    }
                                    if (name.equals("Protocol")) {
                                        this.currentRedirectRule.setProtocol(getText());
                                    } else if (name.equals("HostName")) {
                                        this.currentRedirectRule.setHostName(getText());
                                    } else if (name.equals("ReplaceKeyPrefixWith")) {
                                        this.currentRedirectRule.setReplaceKeyPrefixWith(getText());
                                    } else if (name.equals("ReplaceKeyWith")) {
                                        this.currentRedirectRule.setReplaceKeyWith(getText());
                                    } else if (name.equals("HttpRedirectCode")) {
                                        this.currentRedirectRule.setHttpRedirectCode(getText());
                                    }
                                } else if (name.equals("KeyPrefixEquals")) {
                                    this.currentCondition.setKeyPrefixEquals(getText());
                                } else if (name.equals("HttpErrorCodeReturnedEquals")) {
                                    this.currentCondition.setHttpErrorCodeReturnedEquals(getText());
                                }
                            } else if (name.equals(JsonDocumentFields.CONDITION)) {
                                this.currentRoutingRule.setCondition(this.currentCondition);
                                this.currentCondition = null;
                            } else if (name.equals("Redirect")) {
                                this.currentRoutingRule.setRedirect(this.currentRedirectRule);
                                this.currentRedirectRule = null;
                            }
                        } else if (name.equals("RoutingRule")) {
                            this.configuration.getRoutingRules().add(this.currentRoutingRule);
                            this.currentRoutingRule = null;
                        }
                    } else if (name.equals("Key")) {
                        this.configuration.setErrorDocument(getText());
                    }
                } else if (name.equals("Suffix")) {
                    this.configuration.setIndexDocumentSuffix(getText());
                }
            } else if (name.equals("RedirectAllRequestsTo")) {
                this.configuration.setRedirectAllRequestsTo(this.currentRedirectRule);
                this.currentRedirectRule = null;
            }
        }
    }

    public static class BucketVersioningConfigurationHandler extends AbstractHandler {
        private final BucketVersioningConfiguration configuration = new BucketVersioningConfiguration();

        public BucketVersioningConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("VersioningConfiguration")) {
                return;
            }
            if (name.equals("Status")) {
                this.configuration.setStatus(getText());
            } else if (name.equals("MfaDelete")) {
                String mfaDeleteStatus = getText();
                if (mfaDeleteStatus.equals(BucketLifecycleConfiguration.DISABLED)) {
                    this.configuration.setMfaDeleteEnabled(false);
                } else if (mfaDeleteStatus.equals("Enabled")) {
                    this.configuration.setMfaDeleteEnabled(true);
                } else {
                    this.configuration.setMfaDeleteEnabled((Boolean) null);
                }
            }
        }
    }

    public static class BucketAccelerateConfigurationHandler extends AbstractHandler {
        private final BucketAccelerateConfiguration configuration = new BucketAccelerateConfiguration((String) null);

        public BucketAccelerateConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (in("AccelerateConfiguration") && name.equals("Status")) {
                this.configuration.setStatus(getText());
            }
        }
    }

    public static class CompleteMultipartUploadHandler extends AbstractSSEHandler implements ObjectExpirationResult {
        private AmazonS3Exception ase;
        private String errorCode;
        private String hostId;
        private String requestId;
        private CompleteMultipartUploadResult result;

        public /* bridge */ /* synthetic */ String getSSEKMSKeyId() {
            return super.getSSEKMSKeyId();
        }

        public /* bridge */ /* synthetic */ void setSSEKMSKeyId(String str) {
            super.setSSEKMSKeyId(str);
        }

        /* access modifiers changed from: protected */
        public ServerSideEncryptionResult sseResult() {
            return this.result;
        }

        public Date getExpirationTime() {
            if (this.result == null) {
                return null;
            }
            return this.result.getExpirationTime();
        }

        public void setExpirationTime(Date expirationTime) {
            if (this.result != null) {
                this.result.setExpirationTime(expirationTime);
            }
        }

        public String getExpirationTimeRuleId() {
            if (this.result == null) {
                return null;
            }
            return this.result.getExpirationTimeRuleId();
        }

        public void setExpirationTimeRuleId(String expirationTimeRuleId) {
            if (this.result != null) {
                this.result.setExpirationTimeRuleId(expirationTimeRuleId);
            }
        }

        public CompleteMultipartUploadResult getCompleteMultipartUploadResult() {
            return this.result;
        }

        public AmazonS3Exception getAmazonS3Exception() {
            return this.ase;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (atTopLevel() && name.equals("CompleteMultipartUploadResult")) {
                this.result = new CompleteMultipartUploadResult();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!atTopLevel()) {
                if (!in("CompleteMultipartUploadResult")) {
                    if (!in("Error")) {
                        return;
                    }
                    if (name.equals("Code")) {
                        this.errorCode = getText();
                    } else if (name.equals("Message")) {
                        this.ase = new AmazonS3Exception(getText());
                    } else if (name.equals("RequestId")) {
                        this.requestId = getText();
                    } else if (name.equals("HostId")) {
                        this.hostId = getText();
                    }
                } else if (name.equals("Location")) {
                    this.result.setLocation(getText());
                } else if (name.equals("Bucket")) {
                    this.result.setBucketName(getText());
                } else if (name.equals("Key")) {
                    this.result.setKey(getText());
                } else if (name.equals(Headers.ETAG)) {
                    this.result.setETag(ServiceUtils.removeQuotes(getText()));
                }
            } else if (name.equals("Error") && this.ase != null) {
                this.ase.setErrorCode(this.errorCode);
                this.ase.setRequestId(this.requestId);
                this.ase.setExtendedRequestId(this.hostId);
            }
        }
    }

    public static class InitiateMultipartUploadHandler extends AbstractHandler {
        private final InitiateMultipartUploadResult result = new InitiateMultipartUploadResult();

        public InitiateMultipartUploadResult getInitiateMultipartUploadResult() {
            return this.result;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("InitiateMultipartUploadResult")) {
                return;
            }
            if (name.equals("Bucket")) {
                this.result.setBucketName(getText());
            } else if (name.equals("Key")) {
                this.result.setKey(getText());
            } else if (name.equals("UploadId")) {
                this.result.setUploadId(getText());
            }
        }
    }

    public static class ListMultipartUploadsHandler extends AbstractHandler {
        private MultipartUpload currentMultipartUpload;
        private Owner currentOwner;
        private final MultipartUploadListing result = new MultipartUploadListing();

        public MultipartUploadListing getListMultipartUploadsResult() {
            return this.result;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListMultipartUploadsResult")) {
                if (!in("ListMultipartUploadsResult", "Upload")) {
                    return;
                }
                if (name.equals("Owner") || name.equals("Initiator")) {
                    this.currentOwner = new Owner();
                }
            } else if (name.equals("Upload")) {
                this.currentMultipartUpload = new MultipartUpload();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("ListMultipartUploadsResult")) {
                if (!in("ListMultipartUploadsResult", "CommonPrefixes")) {
                    if (!in("ListMultipartUploadsResult", "Upload")) {
                        if (!in("ListMultipartUploadsResult", "Upload", "Owner")) {
                            if (!in("ListMultipartUploadsResult", "Upload", "Initiator")) {
                                return;
                            }
                        }
                        if (name.equals("ID")) {
                            this.currentOwner.setId(XmlResponsesSaxParser.checkForEmptyString(getText()));
                        } else if (name.equals("DisplayName")) {
                            this.currentOwner.setDisplayName(XmlResponsesSaxParser.checkForEmptyString(getText()));
                        }
                    } else if (name.equals("Key")) {
                        this.currentMultipartUpload.setKey(getText());
                    } else if (name.equals("UploadId")) {
                        this.currentMultipartUpload.setUploadId(getText());
                    } else if (name.equals("Owner")) {
                        this.currentMultipartUpload.setOwner(this.currentOwner);
                        this.currentOwner = null;
                    } else if (name.equals("Initiator")) {
                        this.currentMultipartUpload.setInitiator(this.currentOwner);
                        this.currentOwner = null;
                    } else if (name.equals("StorageClass")) {
                        this.currentMultipartUpload.setStorageClass(getText());
                    } else if (name.equals("Initiated")) {
                        this.currentMultipartUpload.setInitiated(ServiceUtils.parseIso8601Date(getText()));
                    }
                } else if (name.equals("Prefix")) {
                    this.result.getCommonPrefixes().add(getText());
                }
            } else if (name.equals("Bucket")) {
                this.result.setBucketName(getText());
            } else if (name.equals("KeyMarker")) {
                this.result.setKeyMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("Delimiter")) {
                this.result.setDelimiter(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("Prefix")) {
                this.result.setPrefix(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("UploadIdMarker")) {
                this.result.setUploadIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("NextKeyMarker")) {
                this.result.setNextKeyMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("NextUploadIdMarker")) {
                this.result.setNextUploadIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("MaxUploads")) {
                this.result.setMaxUploads(Integer.parseInt(getText()));
            } else if (name.equals("EncodingType")) {
                this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("IsTruncated")) {
                this.result.setTruncated(Boolean.parseBoolean(getText()));
            } else if (name.equals("Upload")) {
                this.result.getMultipartUploads().add(this.currentMultipartUpload);
                this.currentMultipartUpload = null;
            }
        }
    }

    public static class ListPartsHandler extends AbstractHandler {
        private Owner currentOwner;
        private PartSummary currentPart;
        private final PartListing result = new PartListing();

        public PartListing getListPartsResult() {
            return this.result;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("ListPartsResult")) {
                return;
            }
            if (name.equals("Part")) {
                this.currentPart = new PartSummary();
            } else if (name.equals("Owner") || name.equals("Initiator")) {
                this.currentOwner = new Owner();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("ListPartsResult")) {
                if (!in("ListPartsResult", "Part")) {
                    if (!in("ListPartsResult", "Owner")) {
                        if (!in("ListPartsResult", "Initiator")) {
                            return;
                        }
                    }
                    if (name.equals("ID")) {
                        this.currentOwner.setId(XmlResponsesSaxParser.checkForEmptyString(getText()));
                    } else if (name.equals("DisplayName")) {
                        this.currentOwner.setDisplayName(XmlResponsesSaxParser.checkForEmptyString(getText()));
                    }
                } else if (name.equals("PartNumber")) {
                    this.currentPart.setPartNumber(Integer.parseInt(getText()));
                } else if (name.equals("LastModified")) {
                    this.currentPart.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                } else if (name.equals(Headers.ETAG)) {
                    this.currentPart.setETag(ServiceUtils.removeQuotes(getText()));
                } else if (name.equals("Size")) {
                    this.currentPart.setSize(Long.parseLong(getText()));
                }
            } else if (name.equals("Bucket")) {
                this.result.setBucketName(getText());
            } else if (name.equals("Key")) {
                this.result.setKey(getText());
            } else if (name.equals("UploadId")) {
                this.result.setUploadId(getText());
            } else if (name.equals("Owner")) {
                this.result.setOwner(this.currentOwner);
                this.currentOwner = null;
            } else if (name.equals("Initiator")) {
                this.result.setInitiator(this.currentOwner);
                this.currentOwner = null;
            } else if (name.equals("StorageClass")) {
                this.result.setStorageClass(getText());
            } else if (name.equals("PartNumberMarker")) {
                this.result.setPartNumberMarker(parseInteger(getText()).intValue());
            } else if (name.equals("NextPartNumberMarker")) {
                this.result.setNextPartNumberMarker(parseInteger(getText()).intValue());
            } else if (name.equals("MaxParts")) {
                this.result.setMaxParts(parseInteger(getText()).intValue());
            } else if (name.equals("EncodingType")) {
                this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
            } else if (name.equals("IsTruncated")) {
                this.result.setTruncated(Boolean.parseBoolean(getText()));
            } else if (name.equals("Part")) {
                this.result.getParts().add(this.currentPart);
                this.currentPart = null;
            }
        }

        private Integer parseInteger(String text) {
            String text2 = XmlResponsesSaxParser.checkForEmptyString(getText());
            if (text2 == null) {
                return null;
            }
            return Integer.valueOf(Integer.parseInt(text2));
        }
    }

    public static class BucketNotificationConfigurationHandler extends AbstractHandler {
        private final BucketNotificationConfiguration configuration = new BucketNotificationConfiguration();
        private String event;
        private String topic;

        public BucketNotificationConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("NotificationConfiguration", "TopicConfiguration")) {
                if (in("NotificationConfiguration") && name.equals("TopicConfiguration")) {
                    if (!(this.topic == null || this.event == null)) {
                        this.configuration.getTopicConfigurations().add(new BucketNotificationConfiguration.TopicConfiguration(this.topic, this.event));
                    }
                    this.topic = null;
                    this.event = null;
                }
            } else if (name.equals("Topic")) {
                this.topic = getText();
            } else if (name.equals("Event")) {
                this.event = getText();
            }
        }
    }

    public static class BucketReplicationConfigurationHandler extends AbstractHandler {
        private static final String BUCKET = "Bucket";
        private static final String DESTINATION = "Destination";
        private static final String ID = "ID";
        private static final String PREFIX = "Prefix";
        private static final String REPLICATION_CONFIG = "ReplicationConfiguration";
        private static final String ROLE = "Role";
        private static final String RULE = "Rule";
        private static final String STATUS = "Status";
        private static final String STORAGECLASS = "StorageClass";
        private final BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        private ReplicationRule currentRule;
        private String currentRuleId;
        private ReplicationDestinationConfig destinationConfig;

        public BucketReplicationConfiguration getConfiguration() {
            return this.bucketReplicationConfiguration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in(REPLICATION_CONFIG)) {
                if (in(REPLICATION_CONFIG, RULE) && name.equals(DESTINATION)) {
                    this.destinationConfig = new ReplicationDestinationConfig();
                }
            } else if (name.equals(RULE)) {
                this.currentRule = new ReplicationRule();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in(REPLICATION_CONFIG)) {
                if (!in(REPLICATION_CONFIG, RULE)) {
                    if (!in(REPLICATION_CONFIG, RULE, DESTINATION)) {
                        return;
                    }
                    if (name.equals(BUCKET)) {
                        this.destinationConfig.setBucketARN(getText());
                    } else if (name.equals(STORAGECLASS)) {
                        this.destinationConfig.setStorageClass(getText());
                    }
                } else if (name.equals(ID)) {
                    this.currentRuleId = getText();
                } else if (name.equals(PREFIX)) {
                    this.currentRule.setPrefix(getText());
                } else if (name.equals(STATUS)) {
                    this.currentRule.setStatus(getText());
                } else if (name.equals(DESTINATION)) {
                    this.currentRule.setDestinationConfig(this.destinationConfig);
                }
            } else if (name.equals(RULE)) {
                this.bucketReplicationConfiguration.addRule(this.currentRuleId, this.currentRule);
                this.currentRule = null;
                this.currentRuleId = null;
                this.destinationConfig = null;
            } else if (name.equals(ROLE)) {
                this.bucketReplicationConfiguration.setRoleARN(getText());
            }
        }
    }

    public static class BucketTaggingConfigurationHandler extends AbstractHandler {
        private final BucketTaggingConfiguration configuration = new BucketTaggingConfiguration();
        private String currentTagKey;
        private Map<String, String> currentTagSet;
        private String currentTagValue;

        public BucketTaggingConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (in("Tagging") && name.equals("TagSet")) {
                this.currentTagSet = new HashMap();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("Tagging")) {
                if (!in("Tagging", "TagSet")) {
                    if (!in("Tagging", "TagSet", "Tag")) {
                        return;
                    }
                    if (name.equals("Key")) {
                        this.currentTagKey = getText();
                    } else if (name.equals("Value")) {
                        this.currentTagValue = getText();
                    }
                } else if (name.equals("Tag")) {
                    if (!(this.currentTagKey == null || this.currentTagValue == null)) {
                        this.currentTagSet.put(this.currentTagKey, this.currentTagValue);
                    }
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (name.equals("TagSet")) {
                this.configuration.getAllTagSets().add(new TagSet(this.currentTagSet));
                this.currentTagSet = null;
            }
        }
    }

    public static class DeleteObjectsHandler extends AbstractHandler {
        private DeleteObjectsResult.DeletedObject currentDeletedObject = null;
        private MultiObjectDeleteException.DeleteError currentError = null;
        private final DeleteObjectsResponse response = new DeleteObjectsResponse();

        public DeleteObjectsResponse getDeleteObjectResult() {
            return this.response;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("DeleteResult")) {
                return;
            }
            if (name.equals("Deleted")) {
                this.currentDeletedObject = new DeleteObjectsResult.DeletedObject();
            } else if (name.equals("Error")) {
                this.currentError = new MultiObjectDeleteException.DeleteError();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("DeleteResult")) {
                if (!in("DeleteResult", "Deleted")) {
                    if (!in("DeleteResult", "Error")) {
                        return;
                    }
                    if (name.equals("Key")) {
                        this.currentError.setKey(getText());
                    } else if (name.equals("VersionId")) {
                        this.currentError.setVersionId(getText());
                    } else if (name.equals("Code")) {
                        this.currentError.setCode(getText());
                    } else if (name.equals("Message")) {
                        this.currentError.setMessage(getText());
                    }
                } else if (name.equals("Key")) {
                    this.currentDeletedObject.setKey(getText());
                } else if (name.equals("VersionId")) {
                    this.currentDeletedObject.setVersionId(getText());
                } else if (name.equals("DeleteMarker")) {
                    this.currentDeletedObject.setDeleteMarker(getText().equals("true"));
                } else if (name.equals("DeleteMarkerVersionId")) {
                    this.currentDeletedObject.setDeleteMarkerVersionId(getText());
                }
            } else if (name.equals("Deleted")) {
                this.response.getDeletedObjects().add(this.currentDeletedObject);
                this.currentDeletedObject = null;
            } else if (name.equals("Error")) {
                this.response.getErrors().add(this.currentError);
                this.currentError = null;
            }
        }
    }

    public static class BucketLifecycleConfigurationHandler extends AbstractHandler {
        private final BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration(new ArrayList());
        private BucketLifecycleConfiguration.NoncurrentVersionTransition currentNcvTransition;
        private BucketLifecycleConfiguration.Rule currentRule;
        private BucketLifecycleConfiguration.Transition currentTransition;

        public BucketLifecycleConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("LifecycleConfiguration")) {
                if (!in("LifecycleConfiguration", "Rule")) {
                    return;
                }
                if (name.equals("Transition")) {
                    this.currentTransition = new BucketLifecycleConfiguration.Transition();
                } else if (name.equals("NoncurrentVersionTransition")) {
                    this.currentNcvTransition = new BucketLifecycleConfiguration.NoncurrentVersionTransition();
                }
            } else if (name.equals("Rule")) {
                this.currentRule = new BucketLifecycleConfiguration.Rule();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("LifecycleConfiguration")) {
                if (!in("LifecycleConfiguration", "Rule")) {
                    if (!in("LifecycleConfiguration", "Rule", "Expiration")) {
                        if (!in("LifecycleConfiguration", "Rule", "Transition")) {
                            if (!in("LifecycleConfiguration", "Rule", "NoncurrentVersionExpiration")) {
                                if (!in("LifecycleConfiguration", "Rule", "NoncurrentVersionTransition")) {
                                    return;
                                }
                                if (name.equals("StorageClass")) {
                                    this.currentNcvTransition.setStorageClass(StorageClass.fromValue(getText()));
                                } else if (name.equals("NoncurrentDays")) {
                                    this.currentNcvTransition.setDays(Integer.parseInt(getText()));
                                }
                            } else if (name.equals("NoncurrentDays")) {
                                this.currentRule.setNoncurrentVersionExpirationInDays(Integer.parseInt(getText()));
                            }
                        } else if (name.equals("StorageClass")) {
                            this.currentTransition.setStorageClass(StorageClass.fromValue(getText()));
                        } else if (name.equals("Date")) {
                            this.currentTransition.setDate(ServiceUtils.parseIso8601Date(getText()));
                        } else if (name.equals("Days")) {
                            this.currentTransition.setDays(Integer.parseInt(getText()));
                        }
                    } else if (name.equals("Date")) {
                        this.currentRule.setExpirationDate(ServiceUtils.parseIso8601Date(getText()));
                    } else if (name.equals("Days")) {
                        this.currentRule.setExpirationInDays(Integer.parseInt(getText()));
                    }
                } else if (name.equals("ID")) {
                    this.currentRule.setId(getText());
                } else if (name.equals("Prefix")) {
                    this.currentRule.setPrefix(getText());
                } else if (name.equals("Status")) {
                    this.currentRule.setStatus(getText());
                } else if (name.equals("Transition")) {
                    this.currentRule.setTransition(this.currentTransition);
                    this.currentTransition = null;
                } else if (name.equals("NoncurrentVersionTransition")) {
                    this.currentRule.setNoncurrentVersionTransition(this.currentNcvTransition);
                    this.currentNcvTransition = null;
                }
            } else if (name.equals("Rule")) {
                this.configuration.getRules().add(this.currentRule);
                this.currentRule = null;
            }
        }
    }

    public static class BucketCrossOriginConfigurationHandler extends AbstractHandler {
        private List<String> allowedHeaders = null;
        private List<CORSRule.AllowedMethods> allowedMethods = null;
        private List<String> allowedOrigins = null;
        private final BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration(new ArrayList());
        private CORSRule currentRule;
        private List<String> exposedHeaders = null;

        public BucketCrossOriginConfiguration getConfiguration() {
            return this.configuration;
        }

        /* access modifiers changed from: protected */
        public void doStartElement(String uri, String name, String qName, Attributes attrs) {
            if (!in("CORSConfiguration")) {
                if (!in("CORSConfiguration", "CORSRule")) {
                    return;
                }
                if (name.equals("AllowedOrigin")) {
                    if (this.allowedOrigins == null) {
                        this.allowedOrigins = new ArrayList();
                    }
                } else if (name.equals("AllowedMethod")) {
                    if (this.allowedMethods == null) {
                        this.allowedMethods = new ArrayList();
                    }
                } else if (name.equals("ExposeHeader")) {
                    if (this.exposedHeaders == null) {
                        this.exposedHeaders = new ArrayList();
                    }
                } else if (name.equals("AllowedHeader") && this.allowedHeaders == null) {
                    this.allowedHeaders = new LinkedList();
                }
            } else if (name.equals("CORSRule")) {
                this.currentRule = new CORSRule();
            }
        }

        /* access modifiers changed from: protected */
        public void doEndElement(String uri, String name, String qName) {
            if (!in("CORSConfiguration")) {
                if (!in("CORSConfiguration", "CORSRule")) {
                    return;
                }
                if (name.equals("ID")) {
                    this.currentRule.setId(getText());
                } else if (name.equals("AllowedOrigin")) {
                    this.allowedOrigins.add(getText());
                } else if (name.equals("AllowedMethod")) {
                    this.allowedMethods.add(CORSRule.AllowedMethods.fromValue(getText()));
                } else if (name.equals("MaxAgeSeconds")) {
                    this.currentRule.setMaxAgeSeconds(Integer.parseInt(getText()));
                } else if (name.equals("ExposeHeader")) {
                    this.exposedHeaders.add(getText());
                } else if (name.equals("AllowedHeader")) {
                    this.allowedHeaders.add(getText());
                }
            } else if (name.equals("CORSRule")) {
                this.currentRule.setAllowedHeaders(this.allowedHeaders);
                this.currentRule.setAllowedMethods(this.allowedMethods);
                this.currentRule.setAllowedOrigins(this.allowedOrigins);
                this.currentRule.setExposedHeaders(this.exposedHeaders);
                this.allowedHeaders = null;
                this.allowedMethods = null;
                this.allowedOrigins = null;
                this.exposedHeaders = null;
                this.configuration.getRules().add(this.currentRule);
                this.currentRule = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public static String findAttributeValue(String qnameToFind, Attributes attrs) {
        for (int i = 0; i < attrs.getLength(); i++) {
            if (attrs.getQName(i).trim().equalsIgnoreCase(qnameToFind.trim())) {
                return attrs.getValue(i);
            }
        }
        return null;
    }
}
