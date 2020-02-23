package com.amazonaws.services.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.Request;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.Presigner;
import com.amazonaws.auth.Signer;
import com.amazonaws.auth.SignerFactory;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.event.ProgressReportingInputStream;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.internal.AWSS3V4Signer;
import com.amazonaws.services.s3.internal.BucketNameUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.internal.DeleteObjectsResponse;
import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.internal.MD5DigestCalculatingInputStream;
import com.amazonaws.services.s3.internal.ObjectExpirationHeaderHandler;
import com.amazonaws.services.s3.internal.RepeatableFileInputStream;
import com.amazonaws.services.s3.internal.ResponseHeaderHandlerChain;
import com.amazonaws.services.s3.internal.S3ErrorResponseHandler;
import com.amazonaws.services.s3.internal.S3ExecutionContext;
import com.amazonaws.services.s3.internal.S3MetadataResponseHandler;
import com.amazonaws.services.s3.internal.S3QueryStringSigner;
import com.amazonaws.services.s3.internal.S3Signer;
import com.amazonaws.services.s3.internal.S3StringResponseHandler;
import com.amazonaws.services.s3.internal.S3VersionHeaderHandler;
import com.amazonaws.services.s3.internal.S3XmlResponseHandler;
import com.amazonaws.services.s3.internal.ServerSideEncryptionHeaderHandler;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.internal.XmlWriter;
import com.amazonaws.services.s3.metrics.S3ServiceMetric;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.BucketPolicy;
import com.amazonaws.services.s3.model.BucketReplicationConfiguration;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteBucketLifecycleConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteBucketPolicyRequest;
import com.amazonaws.services.s3.model.DeleteBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketTaggingConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.DeleteVersionRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GenericBucketRequest;
import com.amazonaws.services.s3.model.GetBucketAccelerateConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketAclRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetBucketPolicyRequest;
import com.amazonaws.services.s3.model.GetBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetRequestPaymentConfigurationRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.HeadBucketRequest;
import com.amazonaws.services.s3.model.HeadBucketResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.MultiFactorAuthentication;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.RequestPaymentConfiguration;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.RestoreObjectRequest;
import com.amazonaws.services.s3.model.S3AccelerateUnsupported;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.SSECustomerKey;
import com.amazonaws.services.s3.model.SetBucketAccelerateConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketAclRequest;
import com.amazonaws.services.s3.model.SetBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketLifecycleConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketNotificationConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketPolicyRequest;
import com.amazonaws.services.s3.model.SetBucketReplicationConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketTaggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.SetRequestPaymentConfigurationRequest;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.services.s3.model.transform.AclXmlFactory;
import com.amazonaws.services.s3.model.transform.BucketConfigurationXmlFactory;
import com.amazonaws.services.s3.model.transform.HeadBucketResultHandler;
import com.amazonaws.services.s3.model.transform.MultiObjectDeleteXmlFactory;
import com.amazonaws.services.s3.model.transform.RequestPaymentConfigurationXmlFactory;
import com.amazonaws.services.s3.model.transform.RequestXmlFactory;
import com.amazonaws.services.s3.model.transform.Unmarshallers;
import com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser;
import com.amazonaws.services.s3.util.Mimetypes;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.AwsHostNameUtils;
import com.amazonaws.util.Base64;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.Md5Utils;
import com.amazonaws.util.StringUtils;
import com.facebook.places.model.PlaceFields;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.protocol.HTTP;

public class AmazonS3Client extends AmazonWebServiceClient implements AmazonS3 {
    public static final String S3_SERVICE_NAME = "s3";
    private static final String S3_SIGNER = "S3SignerType";
    private static final String S3_V4_SIGNER = "AWSS3V4SignerType";
    private static final BucketConfigurationXmlFactory bucketConfigurationXmlFactory = new BucketConfigurationXmlFactory();
    private static Log log = LogFactory.getLog(AmazonS3Client.class);
    private static final RequestPaymentConfigurationXmlFactory requestPaymentConfigurationXmlFactory = new RequestPaymentConfigurationXmlFactory();
    private final AWSCredentialsProvider awsCredentialsProvider;
    private S3ClientOptions clientOptions;
    volatile String clientRegion;
    private final S3ErrorResponseHandler errorResponseHandler;
    private final S3XmlResponseHandler<Void> voidResponseHandler;

    static {
        AwsSdkMetrics.addAll(Arrays.asList(S3ServiceMetric.values()));
        SignerFactory.registerSigner(S3_SIGNER, S3Signer.class);
        SignerFactory.registerSigner(S3_V4_SIGNER, AWSS3V4Signer.class);
    }

    @Deprecated
    public AmazonS3Client() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain());
    }

    public AmazonS3Client(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AmazonS3Client(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AmazonS3Client(AWSCredentialsProvider credentialsProvider) {
        this(credentialsProvider, new ClientConfiguration());
    }

    public AmazonS3Client(AWSCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration) {
        this(credentialsProvider, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonS3Client(AWSCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(clientConfiguration, new UrlHttpClient(clientConfiguration), requestMetricCollector);
        this.errorResponseHandler = new S3ErrorResponseHandler();
        this.voidResponseHandler = new S3XmlResponseHandler<>((Unmarshaller) null);
        this.clientOptions = new S3ClientOptions();
        this.awsCredentialsProvider = credentialsProvider;
        init();
    }

    public AmazonS3Client(AWSCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(clientConfiguration, httpClient);
        this.errorResponseHandler = new S3ErrorResponseHandler();
        this.voidResponseHandler = new S3XmlResponseHandler<>((Unmarshaller) null);
        this.clientOptions = new S3ClientOptions();
        this.awsCredentialsProvider = credentialsProvider;
        init();
    }

    public AmazonS3Client(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    private void init() {
        setEndpoint(Constants.S3_HOSTNAME);
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/s3/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/s3/request.handler2s"));
    }

    public void setEndpoint(String endpoint) {
        if (endpoint.endsWith(Constants.S3_ACCELERATE_HOSTNAME)) {
            throw new IllegalStateException("To enable accelerate mode, please use AmazonS3Client.setS3ClientOptions(S3ClientOptions.builder().setAccelerateModeEnabled(true).build());");
        }
        super.setEndpoint(endpoint);
        if (!endpoint.endsWith(Constants.S3_HOSTNAME)) {
            this.clientRegion = AwsHostNameUtils.parseRegionName(this.endpoint.getHost(), "s3");
        }
    }

    public void setRegion(Region region) {
        super.setRegion(region);
        this.clientRegion = region.getName();
    }

    public void setS3ClientOptions(S3ClientOptions clientOptions2) {
        this.clientOptions = new S3ClientOptions(clientOptions2);
    }

    public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(previousVersionListing, "The previous version listing parameter must be specified when listing the next batch of versions in a bucket");
        if (previousVersionListing.isTruncated()) {
            return listVersions(new ListVersionsRequest(previousVersionListing.getBucketName(), previousVersionListing.getPrefix(), previousVersionListing.getNextKeyMarker(), previousVersionListing.getNextVersionIdMarker(), previousVersionListing.getDelimiter(), new Integer(previousVersionListing.getMaxKeys())).withEncodingType(previousVersionListing.getEncodingType()));
        }
        VersionListing emptyListing = new VersionListing();
        emptyListing.setBucketName(previousVersionListing.getBucketName());
        emptyListing.setDelimiter(previousVersionListing.getDelimiter());
        emptyListing.setKeyMarker(previousVersionListing.getNextKeyMarker());
        emptyListing.setVersionIdMarker(previousVersionListing.getNextVersionIdMarker());
        emptyListing.setMaxKeys(previousVersionListing.getMaxKeys());
        emptyListing.setPrefix(previousVersionListing.getPrefix());
        emptyListing.setEncodingType(previousVersionListing.getEncodingType());
        emptyListing.setTruncated(false);
        return emptyListing;
    }

    public VersionListing listVersions(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return listVersions(new ListVersionsRequest(bucketName, prefix, (String) null, (String) null, (String) null, (Integer) null));
    }

    public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker, String delimiter, Integer maxKeys) throws AmazonClientException, AmazonServiceException {
        return listVersions(new ListVersionsRequest().withBucketName(bucketName).withPrefix(prefix).withDelimiter(delimiter).withKeyMarker(keyMarker).withVersionIdMarker(versionIdMarker).withMaxResults(maxKeys));
    }

    public VersionListing listVersions(ListVersionsRequest listVersionsRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(listVersionsRequest.getBucketName(), "The bucket name parameter must be specified when listing versions in a bucket");
        Request<ListVersionsRequest> request = createRequest(listVersionsRequest.getBucketName(), (String) null, listVersionsRequest, HttpMethodName.GET);
        request.addParameter("versions", (String) null);
        if (listVersionsRequest.getPrefix() != null) {
            request.addParameter("prefix", listVersionsRequest.getPrefix());
        }
        if (listVersionsRequest.getKeyMarker() != null) {
            request.addParameter("key-marker", listVersionsRequest.getKeyMarker());
        }
        if (listVersionsRequest.getVersionIdMarker() != null) {
            request.addParameter("version-id-marker", listVersionsRequest.getVersionIdMarker());
        }
        if (listVersionsRequest.getDelimiter() != null) {
            request.addParameter("delimiter", listVersionsRequest.getDelimiter());
        }
        if (listVersionsRequest.getMaxResults() != null && listVersionsRequest.getMaxResults().intValue() >= 0) {
            request.addParameter("max-keys", listVersionsRequest.getMaxResults().toString());
        }
        if (listVersionsRequest.getEncodingType() != null) {
            request.addParameter("encoding-type", listVersionsRequest.getEncodingType());
        }
        return (VersionListing) invoke(request, new Unmarshallers.VersionListUnmarshaller(), listVersionsRequest.getBucketName(), (String) null);
    }

    public ObjectListing listObjects(String bucketName) throws AmazonClientException, AmazonServiceException {
        return listObjects(new ListObjectsRequest(bucketName, (String) null, (String) null, (String) null, (Integer) null));
    }

    public ObjectListing listObjects(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return listObjects(new ListObjectsRequest(bucketName, prefix, (String) null, (String) null, (Integer) null));
    }

    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(listObjectsRequest.getBucketName(), "The bucket name parameter must be specified when listing objects in a bucket");
        Request<ListObjectsRequest> request = createRequest(listObjectsRequest.getBucketName(), (String) null, listObjectsRequest, HttpMethodName.GET);
        if (listObjectsRequest.getPrefix() != null) {
            request.addParameter("prefix", listObjectsRequest.getPrefix());
        }
        if (listObjectsRequest.getMarker() != null) {
            request.addParameter("marker", listObjectsRequest.getMarker());
        }
        if (listObjectsRequest.getDelimiter() != null) {
            request.addParameter("delimiter", listObjectsRequest.getDelimiter());
        }
        if (listObjectsRequest.getMaxKeys() != null && listObjectsRequest.getMaxKeys().intValue() >= 0) {
            request.addParameter("max-keys", listObjectsRequest.getMaxKeys().toString());
        }
        if (listObjectsRequest.getEncodingType() != null) {
            request.addParameter("encoding-type", listObjectsRequest.getEncodingType());
        }
        return (ObjectListing) invoke(request, new Unmarshallers.ListObjectsUnmarshaller(), listObjectsRequest.getBucketName(), (String) null);
    }

    public ListObjectsV2Result listObjectsV2(String bucketName) throws AmazonClientException, AmazonServiceException {
        return listObjectsV2(new ListObjectsV2Request().withBucketName(bucketName));
    }

    public ListObjectsV2Result listObjectsV2(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return listObjectsV2(new ListObjectsV2Request().withBucketName(bucketName).withPrefix(prefix));
    }

    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(listObjectsV2Request.getBucketName(), "The bucket name parameter must be specified when listing objects in a bucket");
        Request<ListObjectsV2Request> request = createRequest(listObjectsV2Request.getBucketName(), (String) null, listObjectsV2Request, HttpMethodName.GET);
        request.addParameter("list-type", "2");
        if (listObjectsV2Request.getStartAfter() != null) {
            request.addParameter("start-after", listObjectsV2Request.getStartAfter());
        }
        if (listObjectsV2Request.getContinuationToken() != null) {
            request.addParameter("continuation-token", listObjectsV2Request.getContinuationToken());
        }
        if (listObjectsV2Request.getDelimiter() != null) {
            request.addParameter("delimiter", listObjectsV2Request.getDelimiter());
        }
        if (listObjectsV2Request.getMaxKeys() != null && listObjectsV2Request.getMaxKeys().intValue() >= 0) {
            request.addParameter("max-keys", listObjectsV2Request.getMaxKeys().toString());
        }
        if (listObjectsV2Request.getPrefix() != null) {
            request.addParameter("prefix", listObjectsV2Request.getPrefix());
        }
        if (listObjectsV2Request.getEncodingType() != null) {
            request.addParameter("encoding-type", listObjectsV2Request.getEncodingType());
        }
        if (listObjectsV2Request.isFetchOwner()) {
            request.addParameter("fetch-owner", Boolean.toString(listObjectsV2Request.isFetchOwner()));
        }
        return (ListObjectsV2Result) invoke(request, new Unmarshallers.ListObjectsV2Unmarshaller(), listObjectsV2Request.getBucketName(), (String) null);
    }

    public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(previousObjectListing, "The previous object listing parameter must be specified when listing the next batch of objects in a bucket");
        if (previousObjectListing.isTruncated()) {
            return listObjects(new ListObjectsRequest(previousObjectListing.getBucketName(), previousObjectListing.getPrefix(), previousObjectListing.getNextMarker(), previousObjectListing.getDelimiter(), new Integer(previousObjectListing.getMaxKeys())).withEncodingType(previousObjectListing.getEncodingType()));
        }
        ObjectListing emptyListing = new ObjectListing();
        emptyListing.setBucketName(previousObjectListing.getBucketName());
        emptyListing.setDelimiter(previousObjectListing.getDelimiter());
        emptyListing.setMarker(previousObjectListing.getNextMarker());
        emptyListing.setMaxKeys(previousObjectListing.getMaxKeys());
        emptyListing.setPrefix(previousObjectListing.getPrefix());
        emptyListing.setEncodingType(previousObjectListing.getEncodingType());
        emptyListing.setTruncated(false);
        return emptyListing;
    }

    public Owner getS3AccountOwner() throws AmazonClientException, AmazonServiceException {
        return (Owner) invoke(createRequest((String) null, (String) null, new ListBucketsRequest(), HttpMethodName.GET), new Unmarshallers.ListBucketsOwnerUnmarshaller(), (String) null, (String) null);
    }

    public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest) throws AmazonClientException, AmazonServiceException {
        return (List) invoke(createRequest((String) null, (String) null, listBucketsRequest, HttpMethodName.GET), new Unmarshallers.ListBucketsUnmarshaller(), (String) null, (String) null);
    }

    public List<Bucket> listBuckets() throws AmazonClientException, AmazonServiceException {
        return listBuckets(new ListBucketsRequest());
    }

    public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(getBucketLocationRequest, "The request parameter must be specified when requesting a bucket's location");
        String bucketName = getBucketLocationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting a bucket's location");
        Request<GetBucketLocationRequest> request = createRequest(bucketName, (String) null, getBucketLocationRequest, HttpMethodName.GET);
        request.addParameter("location", (String) null);
        return (String) invoke(request, new Unmarshallers.BucketLocationUnmarshaller(), bucketName, (String) null);
    }

    public String getBucketLocation(String bucketName) throws AmazonClientException, AmazonServiceException {
        return getBucketLocation(new GetBucketLocationRequest(bucketName));
    }

    public Bucket createBucket(String bucketName) throws AmazonClientException, AmazonServiceException {
        return createBucket(new CreateBucketRequest(bucketName));
    }

    public Bucket createBucket(String bucketName, com.amazonaws.services.s3.model.Region region) throws AmazonClientException, AmazonServiceException {
        return createBucket(new CreateBucketRequest(bucketName, region));
    }

    public Bucket createBucket(String bucketName, String region) throws AmazonClientException, AmazonServiceException {
        return createBucket(new CreateBucketRequest(bucketName, region));
    }

    public Bucket createBucket(CreateBucketRequest createBucketRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(createBucketRequest, "The CreateBucketRequest parameter must be specified when creating a bucket");
        String bucketName = createBucketRequest.getBucketName();
        String region = createBucketRequest.getRegion();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when creating a bucket");
        if (bucketName != null) {
            bucketName = bucketName.trim();
        }
        BucketNameUtils.validateBucketName(bucketName);
        Request<CreateBucketRequest> request = createRequest(bucketName, (String) null, createBucketRequest, HttpMethodName.PUT);
        if (createBucketRequest.getAccessControlList() != null) {
            addAclHeaders(request, createBucketRequest.getAccessControlList());
        } else if (createBucketRequest.getCannedAcl() != null) {
            request.addHeader(Headers.S3_CANNED_ACL, createBucketRequest.getCannedAcl().toString());
        }
        if (!this.endpoint.getHost().equals(Constants.S3_HOSTNAME) && (region == null || region.isEmpty())) {
            try {
                region = RegionUtils.getRegionByEndpoint(this.endpoint.getHost()).getName();
            } catch (IllegalArgumentException e) {
            }
        }
        if (region != null && !StringUtils.upperCase(region).equals(com.amazonaws.services.s3.model.Region.US_Standard.toString())) {
            XmlWriter xml = new XmlWriter();
            xml.start("CreateBucketConfiguration", "xmlns", Constants.XML_NAMESPACE);
            xml.start("LocationConstraint").value(region).end();
            xml.end();
            byte[] bytes = xml.getBytes();
            request.addHeader("Content-Length", String.valueOf(bytes.length));
            request.setContent(new ByteArrayInputStream(bytes));
        }
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
        return new Bucket(bucketName);
    }

    public AccessControlList getObjectAcl(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        return getObjectAcl(bucketName, key, (String) null);
    }

    public AccessControlList getObjectAcl(String bucketName, String key, String versionId) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting an object's ACL");
        assertParameterNotNull(key, "The key parameter must be specified when requesting an object's ACL");
        return getAcl(bucketName, key, versionId, (AmazonWebServiceRequest) null);
    }

    public void setObjectAcl(String bucketName, String key, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setObjectAcl(bucketName, key, (String) null, acl);
    }

    public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setObjectAcl(bucketName, key, (String) null, acl);
    }

    public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setObjectAcl0(bucketName, key, versionId, acl, (RequestMetricCollector) null);
    }

    public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        setObjectAcl0(bucketName, key, versionId, acl, requestMetricCollector);
    }

    private void setObjectAcl0(String bucketName, String key, String versionId, AccessControlList acl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting an object's ACL");
        assertParameterNotNull(key, "The key parameter must be specified when setting an object's ACL");
        assertParameterNotNull(acl, "The ACL parameter must be specified when setting an object's ACL");
        setAcl(bucketName, key, versionId, acl, new GenericBucketRequest(bucketName).withRequestMetricCollector(requestMetricCollector));
    }

    public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setObjectAcl0(bucketName, key, versionId, acl, (RequestMetricCollector) null);
    }

    public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl, RequestMetricCollector requestMetricCollector) {
        setObjectAcl0(bucketName, key, versionId, acl, requestMetricCollector);
    }

    private void setObjectAcl0(String bucketName, String key, String versionId, CannedAccessControlList acl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting an object's ACL");
        assertParameterNotNull(key, "The key parameter must be specified when setting an object's ACL");
        assertParameterNotNull(acl, "The ACL parameter must be specified when setting an object's ACL");
        setAcl(bucketName, key, versionId, acl, new GenericBucketRequest(bucketName).withRequestMetricCollector(requestMetricCollector));
    }

    public AccessControlList getBucketAcl(String bucketName) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting a bucket's ACL");
        return getAcl(bucketName, (String) null, (String) null, (AmazonWebServiceRequest) null);
    }

    public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = getBucketAclRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting a bucket's ACL");
        return getAcl(bucketName, (String) null, (String) null, getBucketAclRequest);
    }

    public void setBucketAcl(String bucketName, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setBucketAcl0(bucketName, acl, (RequestMetricCollector) null);
    }

    public void setBucketAcl(String bucketName, AccessControlList acl, RequestMetricCollector requestMetricCollector) {
        setBucketAcl0(bucketName, acl, requestMetricCollector);
    }

    private void setBucketAcl0(String bucketName, AccessControlList acl, RequestMetricCollector requestMetricCollector) {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting a bucket's ACL");
        assertParameterNotNull(acl, "The ACL parameter must be specified when setting a bucket's ACL");
        setAcl(bucketName, (String) null, (String) null, acl, new GenericBucketRequest(bucketName).withRequestMetricCollector(requestMetricCollector));
    }

    public void setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = setBucketAclRequest.getBucketName();
        AccessControlList acl = setBucketAclRequest.getAcl();
        CannedAccessControlList cannedAcl = setBucketAclRequest.getCannedAcl();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting a bucket's ACL");
        if (acl != null) {
            setAcl(bucketName, (String) null, (String) null, acl, (AmazonWebServiceRequest) setBucketAclRequest);
        } else if (cannedAcl != null) {
            setAcl(bucketName, (String) null, (String) null, cannedAcl, (AmazonWebServiceRequest) setBucketAclRequest);
        } else {
            assertParameterNotNull((Object) null, "The ACL parameter must be specified when setting a bucket's ACL");
        }
    }

    public void setBucketAcl(String bucketName, CannedAccessControlList acl) throws AmazonClientException, AmazonServiceException {
        setBucketAcl0(bucketName, acl, (RequestMetricCollector) null);
    }

    public void setBucketAcl(String bucketName, CannedAccessControlList acl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        setBucketAcl0(bucketName, acl, requestMetricCollector);
    }

    private void setBucketAcl0(String bucketName, CannedAccessControlList acl, RequestMetricCollector col) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting a bucket's ACL");
        assertParameterNotNull(acl, "The ACL parameter must be specified when setting a bucket's ACL");
        setAcl(bucketName, (String) null, (String) null, acl, new GenericBucketRequest(bucketName).withRequestMetricCollector(col));
    }

    public ObjectMetadata getObjectMetadata(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        return getObjectMetadata(new GetObjectMetadataRequest(bucketName, key));
    }

    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(getObjectMetadataRequest, "The GetObjectMetadataRequest parameter must be specified when requesting an object's metadata");
        String bucketName = getObjectMetadataRequest.getBucketName();
        String key = getObjectMetadataRequest.getKey();
        String versionId = getObjectMetadataRequest.getVersionId();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting an object's metadata");
        assertParameterNotNull(key, "The key parameter must be specified when requesting an object's metadata");
        Request<GetObjectMetadataRequest> request = createRequest(bucketName, key, getObjectMetadataRequest, HttpMethodName.HEAD);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        populateSseCpkRequestParameters(request, getObjectMetadataRequest.getSSECustomerKey());
        return (ObjectMetadata) invoke(request, new S3MetadataResponseHandler(), bucketName, key);
    }

    public S3Object getObject(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        return getObject(new GetObjectRequest(bucketName, key));
    }

    public boolean doesBucketExist(String bucketName) throws AmazonClientException, AmazonServiceException {
        try {
            headBucket(new HeadBucketRequest(bucketName));
            return true;
        } catch (AmazonServiceException ase) {
            if (ase.getStatusCode() == 301 || ase.getStatusCode() == 403) {
                return true;
            }
            if (ase.getStatusCode() == 404) {
                return false;
            }
            throw ase;
        }
    }

    public boolean doesObjectExist(String bucketName, String objectName) throws AmazonServiceException, AmazonClientException {
        try {
            getObjectMetadata(bucketName, objectName);
            return true;
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

    public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = headBucketRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucketName parameter must be specified.");
        return (HeadBucketResult) invoke(createRequest(bucketName, (String) null, headBucketRequest, HttpMethodName.HEAD), new HeadBucketResultHandler(), bucketName, (String) null);
    }

    public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucketName parameter must be specified when changing an object's storage class");
        assertParameterNotNull(key, "The key parameter must be specified when changing an object's storage class");
        assertParameterNotNull(newStorageClass, "The newStorageClass parameter must be specified when changing an object's storage class");
        copyObject(new CopyObjectRequest(bucketName, key, bucketName, key).withStorageClass(newStorageClass.toString()));
    }

    public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucketName parameter must be specified when changing an object's storage class");
        assertParameterNotNull(key, "The key parameter must be specified when changing an object's storage class");
        assertParameterNotNull(newRedirectLocation, "The newStorageClass parameter must be specified when changing an object's storage class");
        copyObject(new CopyObjectRequest(bucketName, key, bucketName, key).withRedirectLocation(newRedirectLocation));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: com.amazonaws.util.ServiceClientHolderInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: com.amazonaws.util.ServiceClientHolderInputStream} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amazonaws.services.s3.model.S3Object getObject(com.amazonaws.services.s3.model.GetObjectRequest r25) throws com.amazonaws.AmazonClientException, com.amazonaws.AmazonServiceException {
        /*
            r24 = this;
            java.lang.String r20 = "The GetObjectRequest parameter must be specified when requesting an object"
            r0 = r24
            r1 = r25
            r2 = r20
            r0.assertParameterNotNull(r1, r2)
            java.lang.String r20 = r25.getBucketName()
            java.lang.String r21 = "The bucket name parameter must be specified when requesting an object"
            r0 = r24
            r1 = r20
            r2 = r21
            r0.assertParameterNotNull(r1, r2)
            java.lang.String r20 = r25.getKey()
            java.lang.String r21 = "The key parameter must be specified when requesting an object"
            r0 = r24
            r1 = r20
            r2 = r21
            r0.assertParameterNotNull(r1, r2)
            java.lang.String r20 = r25.getBucketName()
            java.lang.String r21 = r25.getKey()
            com.amazonaws.http.HttpMethodName r22 = com.amazonaws.http.HttpMethodName.GET
            r0 = r24
            r1 = r20
            r2 = r21
            r3 = r25
            r4 = r22
            com.amazonaws.Request r17 = r0.createRequest(r1, r2, r3, r4)
            java.lang.String r20 = r25.getVersionId()
            if (r20 == 0) goto L_0x0056
            java.lang.String r20 = "versionId"
            java.lang.String r21 = r25.getVersionId()
            r0 = r17
            r1 = r20
            r2 = r21
            r0.addParameter(r1, r2)
        L_0x0056:
            long[] r15 = r25.getRange()
            if (r15 == 0) goto L_0x00af
            java.lang.StringBuilder r20 = new java.lang.StringBuilder
            r20.<init>()
            java.lang.String r21 = "bytes="
            java.lang.StringBuilder r20 = r20.append(r21)
            r21 = 0
            r22 = r15[r21]
            java.lang.String r21 = java.lang.Long.toString(r22)
            java.lang.StringBuilder r20 = r20.append(r21)
            java.lang.String r21 = "-"
            java.lang.StringBuilder r20 = r20.append(r21)
            java.lang.String r16 = r20.toString()
            r20 = 1
            r20 = r15[r20]
            r22 = 0
            int r20 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1))
            if (r20 < 0) goto L_0x00a4
            java.lang.StringBuilder r20 = new java.lang.StringBuilder
            r20.<init>()
            r0 = r20
            r1 = r16
            java.lang.StringBuilder r20 = r0.append(r1)
            r21 = 1
            r22 = r15[r21]
            java.lang.String r21 = java.lang.Long.toString(r22)
            java.lang.StringBuilder r20 = r20.append(r21)
            java.lang.String r16 = r20.toString()
        L_0x00a4:
            java.lang.String r20 = "Range"
            r0 = r17
            r1 = r20
            r2 = r16
            r0.addHeader(r1, r2)
        L_0x00af:
            boolean r20 = r25.isRequesterPays()
            if (r20 == 0) goto L_0x00c2
            java.lang.String r20 = "x-amz-request-payer"
            java.lang.String r21 = "requester"
            r0 = r17
            r1 = r20
            r2 = r21
            r0.addHeader(r1, r2)
        L_0x00c2:
            com.amazonaws.services.s3.model.ResponseHeaderOverrides r20 = r25.getResponseHeaders()
            r0 = r17
            r1 = r20
            addResponseHeaderParameters(r0, r1)
            java.lang.String r20 = "If-Modified-Since"
            java.util.Date r21 = r25.getModifiedSinceConstraint()
            r0 = r17
            r1 = r20
            r2 = r21
            addDateHeader(r0, r1, r2)
            java.lang.String r20 = "If-Unmodified-Since"
            java.util.Date r21 = r25.getUnmodifiedSinceConstraint()
            r0 = r17
            r1 = r20
            r2 = r21
            addDateHeader(r0, r1, r2)
            java.lang.String r20 = "If-Match"
            java.util.List r21 = r25.getMatchingETagConstraints()
            r0 = r17
            r1 = r20
            r2 = r21
            addStringListHeader(r0, r1, r2)
            java.lang.String r20 = "If-None-Match"
            java.util.List r21 = r25.getNonmatchingETagConstraints()
            r0 = r17
            r1 = r20
            r2 = r21
            addStringListHeader(r0, r1, r2)
            com.amazonaws.services.s3.model.SSECustomerKey r20 = r25.getSSECustomerKey()
            r0 = r17
            r1 = r20
            populateSseCpkRequestParameters(r0, r1)
            com.amazonaws.event.ProgressListener r12 = r25.getGeneralProgressListener()
            com.amazonaws.event.ProgressListenerCallbackExecutor r13 = com.amazonaws.event.ProgressListenerCallbackExecutor.wrapListener(r12)
            com.amazonaws.services.s3.internal.S3ObjectResponseHandler r20 = new com.amazonaws.services.s3.internal.S3ObjectResponseHandler     // Catch:{ AmazonS3Exception -> 0x01df }
            r20.<init>()     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r21 = r25.getBucketName()     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r22 = r25.getKey()     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r24
            r1 = r17
            r2 = r20
            r3 = r21
            r4 = r22
            java.lang.Object r18 = r0.invoke(r1, r2, (java.lang.String) r3, (java.lang.String) r4)     // Catch:{ AmazonS3Exception -> 0x01df }
            com.amazonaws.services.s3.model.S3Object r18 = (com.amazonaws.services.s3.model.S3Object) r18     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r20 = r25.getBucketName()     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r18
            r1 = r20
            r0.setBucketName(r1)     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r20 = r25.getKey()     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r18
            r1 = r20
            r0.setKey(r1)     // Catch:{ AmazonS3Exception -> 0x01df }
            com.amazonaws.services.s3.model.S3ObjectInputStream r10 = r18.getObjectContent()     // Catch:{ AmazonS3Exception -> 0x01df }
            com.amazonaws.util.ServiceClientHolderInputStream r11 = new com.amazonaws.util.ServiceClientHolderInputStream     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r24
            r11.<init>(r10, r0)     // Catch:{ AmazonS3Exception -> 0x01df }
            if (r13 == 0) goto L_0x0173
            com.amazonaws.event.ProgressReportingInputStream r14 = new com.amazonaws.event.ProgressReportingInputStream     // Catch:{ AmazonS3Exception -> 0x01df }
            r14.<init>(r11, r13)     // Catch:{ AmazonS3Exception -> 0x01df }
            r20 = 1
            r0 = r20
            r14.setFireCompletedEvent(r0)     // Catch:{ AmazonS3Exception -> 0x01df }
            r10 = r14
            r20 = 2
            r0 = r24
            r1 = r20
            r0.fireProgressEvent(r13, r1)     // Catch:{ AmazonS3Exception -> 0x01df }
            r11 = r10
        L_0x0173:
            boolean r20 = com.amazonaws.services.s3.internal.ServiceUtils.skipMd5CheckPerRequest(r25)     // Catch:{ AmazonS3Exception -> 0x01df }
            if (r20 != 0) goto L_0x01cb
            com.amazonaws.services.s3.model.ObjectMetadata r20 = r18.getObjectMetadata()     // Catch:{ AmazonS3Exception -> 0x01df }
            boolean r20 = com.amazonaws.services.s3.internal.ServiceUtils.skipMd5CheckPerResponse(r20)     // Catch:{ AmazonS3Exception -> 0x01df }
            if (r20 != 0) goto L_0x01cb
            r19 = 0
            com.amazonaws.services.s3.model.ObjectMetadata r20 = r18.getObjectMetadata()     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r9 = r20.getETag()     // Catch:{ AmazonS3Exception -> 0x01df }
            if (r9 == 0) goto L_0x01c9
            boolean r20 = com.amazonaws.services.s3.internal.ServiceUtils.isMultipartUploadETag(r9)     // Catch:{ AmazonS3Exception -> 0x01df }
            if (r20 != 0) goto L_0x01c9
            com.amazonaws.services.s3.model.ObjectMetadata r20 = r18.getObjectMetadata()     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r20 = r20.getETag()     // Catch:{ AmazonS3Exception -> 0x01df }
            byte[] r19 = com.amazonaws.util.BinaryUtils.fromHex(r20)     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r20 = "MD5"
            java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r20)     // Catch:{ NoSuchAlgorithmException -> 0x01bd }
            com.amazonaws.services.s3.internal.DigestValidationInputStream r10 = new com.amazonaws.services.s3.internal.DigestValidationInputStream     // Catch:{ NoSuchAlgorithmException -> 0x01bd }
            r0 = r19
            r10.<init>(r11, r7, r0)     // Catch:{ NoSuchAlgorithmException -> 0x01bd }
        L_0x01ae:
            com.amazonaws.services.s3.model.S3ObjectInputStream r20 = new com.amazonaws.services.s3.model.S3ObjectInputStream     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r20
            r0.<init>(r10)     // Catch:{ AmazonS3Exception -> 0x01df }
            r0 = r18
            r1 = r20
            r0.setObjectContent((com.amazonaws.services.s3.model.S3ObjectInputStream) r1)     // Catch:{ AmazonS3Exception -> 0x01df }
        L_0x01bc:
            return r18
        L_0x01bd:
            r8 = move-exception
            org.apache.commons.logging.Log r20 = log     // Catch:{ AmazonS3Exception -> 0x01df }
            java.lang.String r21 = "No MD5 digest algorithm available.  Unable to calculate checksum and verify data integrity."
            r0 = r20
            r1 = r21
            r0.warn(r1, r8)     // Catch:{ AmazonS3Exception -> 0x01df }
        L_0x01c9:
            r10 = r11
            goto L_0x01ae
        L_0x01cb:
            com.amazonaws.util.LengthCheckInputStream r10 = new com.amazonaws.util.LengthCheckInputStream     // Catch:{ AmazonS3Exception -> 0x01df }
            com.amazonaws.services.s3.model.ObjectMetadata r20 = r18.getObjectMetadata()     // Catch:{ AmazonS3Exception -> 0x01df }
            long r20 = r20.getContentLength()     // Catch:{ AmazonS3Exception -> 0x01df }
            r22 = 1
            r0 = r20
            r2 = r22
            r10.<init>(r11, r0, r2)     // Catch:{ AmazonS3Exception -> 0x01df }
            goto L_0x01ae
        L_0x01df:
            r6 = move-exception
            int r20 = r6.getStatusCode()
            r21 = 412(0x19c, float:5.77E-43)
            r0 = r20
            r1 = r21
            if (r0 == r1) goto L_0x01f8
            int r20 = r6.getStatusCode()
            r21 = 304(0x130, float:4.26E-43)
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x0204
        L_0x01f8:
            r20 = 16
            r0 = r24
            r1 = r20
            r0.fireProgressEvent(r13, r1)
            r18 = 0
            goto L_0x01bc
        L_0x0204:
            r20 = 8
            r0 = r24
            r1 = r20
            r0.fireProgressEvent(r13, r1)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.services.s3.AmazonS3Client.getObject(com.amazonaws.services.s3.model.GetObjectRequest):com.amazonaws.services.s3.model.S3Object");
    }

    public ObjectMetadata getObject(final GetObjectRequest getObjectRequest, File destinationFile) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(destinationFile, "The destination file parameter must be specified when downloading an object directly to a file");
        boolean mode = false;
        if (getObjectRequest.getRange() != null && getObjectRequest.getRange()[0] > 0) {
            mode = true;
        }
        S3Object s3Object = ServiceUtils.retryableDownloadS3ObjectToFile(destinationFile, new ServiceUtils.RetryableS3DownloadTask() {
            public S3Object getS3ObjectStream() {
                return AmazonS3Client.this.getObject(getObjectRequest);
            }

            public boolean needIntegrityCheck() {
                return !ServiceUtils.skipMd5CheckPerRequest(getObjectRequest);
            }
        }, mode);
        if (s3Object == null) {
            return null;
        }
        return s3Object.getObjectMetadata();
    }

    public void deleteBucket(String bucketName) throws AmazonClientException, AmazonServiceException {
        deleteBucket(new DeleteBucketRequest(bucketName));
    }

    public void deleteBucket(DeleteBucketRequest deleteBucketRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(deleteBucketRequest, "The DeleteBucketRequest parameter must be specified when deleting a bucket");
        String bucketName = deleteBucketRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting a bucket");
        invoke(createRequest(bucketName, (String) null, deleteBucketRequest, HttpMethodName.DELETE), this.voidResponseHandler, bucketName, (String) null);
    }

    public PutObjectResult putObject(String bucketName, String key, File file) throws AmazonClientException, AmazonServiceException {
        return putObject(new PutObjectRequest(bucketName, key, file).withMetadata(new ObjectMetadata()));
    }

    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws AmazonClientException, AmazonServiceException {
        return putObject(new PutObjectRequest(bucketName, key, input, metadata));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v0, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v4, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v5, resolved type: com.amazonaws.event.ProgressReportingInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v8, resolved type: com.amazonaws.services.s3.internal.MD5DigestCalculatingInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: com.amazonaws.event.ProgressReportingInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v2, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v10, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v3, resolved type: com.amazonaws.util.LengthCheckInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v11, resolved type: com.amazonaws.services.s3.internal.RepeatableFileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v14, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: com.amazonaws.services.s3.internal.RepeatableFileInputStream} */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r17v6 */
    /* JADX WARNING: type inference failed for: r17v7, types: [java.io.InputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amazonaws.services.s3.model.PutObjectResult putObject(com.amazonaws.services.s3.model.PutObjectRequest r37) throws com.amazonaws.AmazonClientException, com.amazonaws.AmazonServiceException {
        /*
            r36 = this;
            java.lang.String r32 = "The PutObjectRequest parameter must be specified when uploading an object"
            r0 = r36
            r1 = r37
            r2 = r32
            r0.assertParameterNotNull(r1, r2)
            java.lang.String r6 = r37.getBucketName()
            java.lang.String r19 = r37.getKey()
            com.amazonaws.services.s3.model.ObjectMetadata r24 = r37.getMetadata()
            java.io.InputStream r17 = r37.getInputStream()
            com.amazonaws.event.ProgressListener r25 = r37.getGeneralProgressListener()
            com.amazonaws.event.ProgressListenerCallbackExecutor r26 = com.amazonaws.event.ProgressListenerCallbackExecutor.wrapListener(r25)
            if (r24 != 0) goto L_0x002a
            com.amazonaws.services.s3.model.ObjectMetadata r24 = new com.amazonaws.services.s3.model.ObjectMetadata
            r24.<init>()
        L_0x002a:
            java.lang.String r32 = "The bucket name parameter must be specified when uploading an object"
            r0 = r36
            r1 = r32
            r0.assertParameterNotNull(r6, r1)
            java.lang.String r32 = "The key parameter must be specified when uploading an object"
            r0 = r36
            r1 = r19
            r2 = r32
            r0.assertParameterNotNull(r1, r2)
            boolean r31 = com.amazonaws.services.s3.internal.ServiceUtils.skipMd5CheckPerRequest(r37)
            java.io.File r32 = r37.getFile()
            if (r32 == 0) goto L_0x0089
            java.io.File r13 = r37.getFile()
            long r32 = r13.length()
            r0 = r24
            r1 = r32
            r0.setContentLength(r1)
            java.lang.String r32 = r24.getContentMD5()
            if (r32 != 0) goto L_0x01db
            r7 = 1
        L_0x005e:
            java.lang.String r32 = r24.getContentType()
            if (r32 != 0) goto L_0x0075
            com.amazonaws.services.s3.util.Mimetypes r32 = com.amazonaws.services.s3.util.Mimetypes.getInstance()
            r0 = r32
            java.lang.String r32 = r0.getMimetype((java.io.File) r13)
            r0 = r24
            r1 = r32
            r0.setContentType(r1)
        L_0x0075:
            if (r7 == 0) goto L_0x0082
            if (r31 != 0) goto L_0x0082
            java.lang.String r11 = com.amazonaws.util.Md5Utils.md5AsBase64((java.io.File) r13)     // Catch:{ Exception -> 0x01de }
            r0 = r24
            r0.setContentMD5(r11)     // Catch:{ Exception -> 0x01de }
        L_0x0082:
            com.amazonaws.services.s3.internal.RepeatableFileInputStream r17 = new com.amazonaws.services.s3.internal.RepeatableFileInputStream     // Catch:{ FileNotFoundException -> 0x0200 }
            r0 = r17
            r0.<init>(r13)     // Catch:{ FileNotFoundException -> 0x0200 }
        L_0x0089:
            com.amazonaws.http.HttpMethodName r32 = com.amazonaws.http.HttpMethodName.PUT
            r0 = r36
            r1 = r19
            r2 = r37
            r3 = r32
            com.amazonaws.Request r27 = r0.createRequest(r6, r1, r2, r3)
            com.amazonaws.services.s3.model.AccessControlList r32 = r37.getAccessControlList()
            if (r32 == 0) goto L_0x020f
            com.amazonaws.services.s3.model.AccessControlList r32 = r37.getAccessControlList()
            r0 = r27
            r1 = r32
            addAclHeaders(r0, r1)
        L_0x00a8:
            java.lang.String r32 = r37.getStorageClass()
            if (r32 == 0) goto L_0x00bd
            java.lang.String r32 = "x-amz-storage-class"
            java.lang.String r33 = r37.getStorageClass()
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
        L_0x00bd:
            java.lang.String r32 = r37.getRedirectLocation()
            if (r32 == 0) goto L_0x00ec
            java.lang.String r32 = "x-amz-website-redirect-location"
            java.lang.String r33 = r37.getRedirectLocation()
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
            if (r17 != 0) goto L_0x00ec
            r0 = r36
            r1 = r27
            r0.setZeroContentLength(r1)
            java.io.ByteArrayInputStream r17 = new java.io.ByteArrayInputStream
            r32 = 0
            r0 = r32
            byte[] r0 = new byte[r0]
            r32 = r0
            r0 = r17
            r1 = r32
            r0.<init>(r1)
        L_0x00ec:
            com.amazonaws.services.s3.model.SSECustomerKey r32 = r37.getSSECustomerKey()
            r0 = r27
            r1 = r32
            populateSseCpkRequestParameters(r0, r1)
            java.lang.String r32 = "Content-Length"
            r0 = r24
            r1 = r32
            java.lang.Object r9 = r0.getRawMetadataValue(r1)
            java.lang.Long r9 = (java.lang.Long) r9
            if (r9 != 0) goto L_0x0245
            boolean r32 = r17.markSupported()
            if (r32 != 0) goto L_0x022a
            org.apache.commons.logging.Log r32 = log
            java.lang.String r33 = "No content length specified for stream data.  Stream contents will be buffered in memory and could result in out of memory errors."
            r32.warn(r33)
            r0 = r36
            r1 = r17
            java.io.ByteArrayInputStream r5 = r0.toByteArray(r1)
            java.lang.String r32 = "Content-Length"
            int r33 = r5.available()
            java.lang.String r33 = java.lang.String.valueOf(r33)
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
            r17 = r5
            r18 = r17
        L_0x0131:
            if (r26 == 0) goto L_0x033c
            com.amazonaws.event.ProgressReportingInputStream r17 = new com.amazonaws.event.ProgressReportingInputStream
            r0 = r17
            r1 = r18
            r2 = r26
            r0.<init>(r1, r2)
            r32 = 2
            r0 = r36
            r1 = r26
            r2 = r32
            r0.fireProgressEvent(r1, r2)
        L_0x0149:
            r21 = 0
            java.lang.String r32 = r24.getContentMD5()
            if (r32 != 0) goto L_0x015e
            if (r31 != 0) goto L_0x015e
            com.amazonaws.services.s3.internal.MD5DigestCalculatingInputStream r21 = new com.amazonaws.services.s3.internal.MD5DigestCalculatingInputStream
            r0 = r21
            r1 = r17
            r0.<init>(r1)
            r17 = r21
        L_0x015e:
            java.lang.String r32 = r24.getContentType()
            if (r32 != 0) goto L_0x016d
            java.lang.String r32 = "application/octet-stream"
            r0 = r24
            r1 = r32
            r0.setContentType(r1)
        L_0x016d:
            r0 = r27
            r1 = r24
            populateRequestMetadata(r0, r1)
            r0 = r27
            r1 = r17
            r0.setContent(r1)
            java.lang.String r32 = "Expect"
            java.lang.String r33 = "100-continue"
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
            r29 = 0
            com.amazonaws.services.s3.internal.S3MetadataResponseHandler r32 = new com.amazonaws.services.s3.internal.S3MetadataResponseHandler     // Catch:{ AmazonClientException -> 0x0294 }
            r32.<init>()     // Catch:{ AmazonClientException -> 0x0294 }
            r0 = r36
            r1 = r27
            r2 = r32
            r3 = r19
            java.lang.Object r29 = r0.invoke(r1, r2, (java.lang.String) r6, (java.lang.String) r3)     // Catch:{ AmazonClientException -> 0x0294 }
            com.amazonaws.services.s3.model.ObjectMetadata r29 = (com.amazonaws.services.s3.model.ObjectMetadata) r29     // Catch:{ AmazonClientException -> 0x0294 }
            r17.close()     // Catch:{ AbortedException -> 0x0336, Exception -> 0x0271 }
        L_0x01a0:
            java.lang.String r10 = r24.getContentMD5()
            if (r21 == 0) goto L_0x01ae
            byte[] r32 = r21.getMd5Digest()
            java.lang.String r10 = com.amazonaws.util.BinaryUtils.toBase64(r32)
        L_0x01ae:
            if (r29 == 0) goto L_0x02c8
            if (r10 == 0) goto L_0x02c8
            if (r31 != 0) goto L_0x02c8
            byte[] r8 = com.amazonaws.util.BinaryUtils.fromBase64(r10)
            java.lang.String r32 = r29.getETag()
            byte[] r30 = com.amazonaws.util.BinaryUtils.fromHex(r32)
            r0 = r30
            boolean r32 = java.util.Arrays.equals(r8, r0)
            if (r32 != 0) goto L_0x02c8
            r32 = 8
            r0 = r36
            r1 = r26
            r2 = r32
            r0.fireProgressEvent(r1, r2)
            com.amazonaws.AmazonClientException r32 = new com.amazonaws.AmazonClientException
            java.lang.String r33 = "Unable to verify integrity of data upload.  Client calculated content hash didn't match hash calculated by Amazon S3.  You may need to delete the data stored in Amazon S3."
            r32.<init>(r33)
            throw r32
        L_0x01db:
            r7 = 0
            goto L_0x005e
        L_0x01de:
            r12 = move-exception
            com.amazonaws.AmazonClientException r32 = new com.amazonaws.AmazonClientException
            java.lang.StringBuilder r33 = new java.lang.StringBuilder
            r33.<init>()
            java.lang.String r34 = "Unable to calculate MD5 hash: "
            java.lang.StringBuilder r33 = r33.append(r34)
            java.lang.String r34 = r12.getMessage()
            java.lang.StringBuilder r33 = r33.append(r34)
            java.lang.String r33 = r33.toString()
            r0 = r32
            r1 = r33
            r0.<init>(r1, r12)
            throw r32
        L_0x0200:
            r16 = move-exception
            com.amazonaws.AmazonClientException r32 = new com.amazonaws.AmazonClientException
            java.lang.String r33 = "Unable to find file to upload"
            r0 = r32
            r1 = r33
            r2 = r16
            r0.<init>(r1, r2)
            throw r32
        L_0x020f:
            com.amazonaws.services.s3.model.CannedAccessControlList r32 = r37.getCannedAcl()
            if (r32 == 0) goto L_0x00a8
            java.lang.String r32 = "x-amz-acl"
            com.amazonaws.services.s3.model.CannedAccessControlList r33 = r37.getCannedAcl()
            java.lang.String r33 = r33.toString()
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
            goto L_0x00a8
        L_0x022a:
            r0 = r36
            r1 = r17
            long r22 = r0.calculateContentLength(r1)
            java.lang.String r32 = "Content-Length"
            java.lang.String r33 = java.lang.String.valueOf(r22)
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
            r18 = r17
            goto L_0x0131
        L_0x0245:
            long r14 = r9.longValue()
            r32 = 0
            int r32 = (r14 > r32 ? 1 : (r14 == r32 ? 0 : -1))
            if (r32 < 0) goto L_0x026d
            com.amazonaws.util.LengthCheckInputStream r20 = new com.amazonaws.util.LengthCheckInputStream
            r32 = 0
            r0 = r20
            r1 = r17
            r2 = r32
            r0.<init>(r1, r14, r2)
            r17 = r20
            java.lang.String r32 = "Content-Length"
            java.lang.String r33 = r9.toString()
            r0 = r27
            r1 = r32
            r2 = r33
            r0.addHeader(r1, r2)
        L_0x026d:
            r18 = r17
            goto L_0x0131
        L_0x0271:
            r12 = move-exception
            org.apache.commons.logging.Log r32 = log
            java.lang.StringBuilder r33 = new java.lang.StringBuilder
            r33.<init>()
            java.lang.String r34 = "Unable to cleanly close input stream: "
            java.lang.StringBuilder r33 = r33.append(r34)
            java.lang.String r34 = r12.getMessage()
            java.lang.StringBuilder r33 = r33.append(r34)
            java.lang.String r33 = r33.toString()
            r0 = r32
            r1 = r33
            r0.debug(r1, r12)
            goto L_0x01a0
        L_0x0294:
            r4 = move-exception
            r32 = 8
            r0 = r36
            r1 = r26
            r2 = r32
            r0.fireProgressEvent(r1, r2)     // Catch:{ all -> 0x02a1 }
            throw r4     // Catch:{ all -> 0x02a1 }
        L_0x02a1:
            r32 = move-exception
            r17.close()     // Catch:{ AbortedException -> 0x0339, Exception -> 0x02a6 }
        L_0x02a5:
            throw r32
        L_0x02a6:
            r12 = move-exception
            org.apache.commons.logging.Log r33 = log
            java.lang.StringBuilder r34 = new java.lang.StringBuilder
            r34.<init>()
            java.lang.String r35 = "Unable to cleanly close input stream: "
            java.lang.StringBuilder r34 = r34.append(r35)
            java.lang.String r35 = r12.getMessage()
            java.lang.StringBuilder r34 = r34.append(r35)
            java.lang.String r34 = r34.toString()
            r0 = r33
            r1 = r34
            r0.debug(r1, r12)
            goto L_0x02a5
        L_0x02c8:
            r32 = 4
            r0 = r36
            r1 = r26
            r2 = r32
            r0.fireProgressEvent(r1, r2)
            com.amazonaws.services.s3.model.PutObjectResult r28 = new com.amazonaws.services.s3.model.PutObjectResult
            r28.<init>()
            java.lang.String r32 = r29.getETag()
            r0 = r28
            r1 = r32
            r0.setETag(r1)
            java.lang.String r32 = r29.getVersionId()
            r0 = r28
            r1 = r32
            r0.setVersionId(r1)
            java.lang.String r32 = r29.getSSEAlgorithm()
            r0 = r28
            r1 = r32
            r0.setSSEAlgorithm(r1)
            java.lang.String r32 = r29.getSSEKMSKeyId()
            r0 = r28
            r1 = r32
            r0.setSSEKMSKeyId(r1)
            java.lang.String r32 = r29.getSSECustomerAlgorithm()
            r0 = r28
            r1 = r32
            r0.setSSECustomerAlgorithm(r1)
            java.lang.String r32 = r29.getSSECustomerKeyMd5()
            r0 = r28
            r1 = r32
            r0.setSSECustomerKeyMd5(r1)
            java.util.Date r32 = r29.getExpirationTime()
            r0 = r28
            r1 = r32
            r0.setExpirationTime(r1)
            java.lang.String r32 = r29.getExpirationTimeRuleId()
            r0 = r28
            r1 = r32
            r0.setExpirationTimeRuleId(r1)
            r0 = r28
            r0.setContentMd5(r10)
            return r28
        L_0x0336:
            r32 = move-exception
            goto L_0x01a0
        L_0x0339:
            r33 = move-exception
            goto L_0x02a5
        L_0x033c:
            r17 = r18
            goto L_0x0149
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.services.s3.AmazonS3Client.putObject(com.amazonaws.services.s3.model.PutObjectRequest):com.amazonaws.services.s3.model.PutObjectResult");
    }

    private long calculateContentLength(InputStream is) {
        long len = 0;
        byte[] buf = new byte[8192];
        is.mark(-1);
        while (true) {
            try {
                int read = is.read(buf);
                if (read != -1) {
                    len += (long) read;
                } else {
                    is.reset();
                    return len;
                }
            } catch (IOException ioe) {
                throw new AmazonClientException("Could not calculate content length.", ioe);
            }
        }
    }

    private static void addAclHeaders(Request<? extends AmazonWebServiceRequest> request, AccessControlList acl) {
        Set<Grant> grants = acl.getGrants();
        Map<Permission, Collection<Grantee>> grantsByPermission = new HashMap<>();
        for (Grant grant : grants) {
            if (!grantsByPermission.containsKey(grant.getPermission())) {
                grantsByPermission.put(grant.getPermission(), new LinkedList());
            }
            grantsByPermission.get(grant.getPermission()).add(grant.getGrantee());
        }
        for (Permission permission : Permission.values()) {
            if (grantsByPermission.containsKey(permission)) {
                boolean seenOne = false;
                StringBuilder granteeString = new StringBuilder();
                for (Grantee grantee : grantsByPermission.get(permission)) {
                    if (!seenOne) {
                        seenOne = true;
                    } else {
                        granteeString.append(", ");
                    }
                    granteeString.append(grantee.getTypeIdentifier()).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append("\"").append(grantee.getIdentifier()).append("\"");
                }
                request.addHeader(permission.getHeaderName(), granteeString.toString());
            }
        }
    }

    public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) throws AmazonClientException, AmazonServiceException {
        return copyObject(new CopyObjectRequest(sourceBucketName, sourceKey, destinationBucketName, destinationKey));
    }

    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(copyObjectRequest.getSourceBucketName(), "The source bucket name must be specified when copying an object");
        assertParameterNotNull(copyObjectRequest.getSourceKey(), "The source object key must be specified when copying an object");
        assertParameterNotNull(copyObjectRequest.getDestinationBucketName(), "The destination bucket name must be specified when copying an object");
        assertParameterNotNull(copyObjectRequest.getDestinationKey(), "The destination object key must be specified when copying an object");
        String destinationKey = copyObjectRequest.getDestinationKey();
        String destinationBucketName = copyObjectRequest.getDestinationBucketName();
        Request<CopyObjectRequest> request = createRequest(destinationBucketName, destinationKey, copyObjectRequest, HttpMethodName.PUT);
        populateRequestWithCopyObjectParameters(request, copyObjectRequest);
        setZeroContentLength(request);
        try {
            XmlResponsesSaxParser.CopyObjectResultHandler copyObjectResultHandler = (XmlResponsesSaxParser.CopyObjectResultHandler) invoke(request, new ResponseHeaderHandlerChain<>(new Unmarshallers.CopyObjectUnmarshaller(), new ServerSideEncryptionHeaderHandler(), new S3VersionHeaderHandler(), new ObjectExpirationHeaderHandler()), destinationBucketName, destinationKey);
            if (copyObjectResultHandler.getErrorCode() != null) {
                String errorCode = copyObjectResultHandler.getErrorCode();
                String errorMessage = copyObjectResultHandler.getErrorMessage();
                String requestId = copyObjectResultHandler.getErrorRequestId();
                String hostId = copyObjectResultHandler.getErrorHostId();
                AmazonS3Exception ase = new AmazonS3Exception(errorMessage);
                ase.setErrorCode(errorCode);
                ase.setErrorType(AmazonServiceException.ErrorType.Service);
                ase.setRequestId(requestId);
                ase.setExtendedRequestId(hostId);
                ase.setServiceName(request.getServiceName());
                ase.setStatusCode(200);
                throw ase;
            }
            CopyObjectResult copyObjectResult = new CopyObjectResult();
            copyObjectResult.setETag(copyObjectResultHandler.getETag());
            copyObjectResult.setLastModifiedDate(copyObjectResultHandler.getLastModified());
            copyObjectResult.setVersionId(copyObjectResultHandler.getVersionId());
            copyObjectResult.setSSEAlgorithm(copyObjectResultHandler.getSSEAlgorithm());
            copyObjectResult.setSSEKMSKeyId(copyObjectResultHandler.getSSEKMSKeyId());
            copyObjectResult.setSSECustomerAlgorithm(copyObjectResultHandler.getSSECustomerAlgorithm());
            copyObjectResult.setSSECustomerKeyMd5(copyObjectResultHandler.getSSECustomerKeyMd5());
            copyObjectResult.setExpirationTime(copyObjectResultHandler.getExpirationTime());
            copyObjectResult.setExpirationTimeRuleId(copyObjectResultHandler.getExpirationTimeRuleId());
            return copyObjectResult;
        } catch (AmazonS3Exception ase2) {
            if (ase2.getStatusCode() == 412) {
                return null;
            }
            throw ase2;
        }
    }

    public CopyPartResult copyPart(CopyPartRequest copyPartRequest) {
        assertParameterNotNull(copyPartRequest.getSourceBucketName(), "The source bucket name must be specified when copying a part");
        assertParameterNotNull(copyPartRequest.getSourceKey(), "The source object key must be specified when copying a part");
        assertParameterNotNull(copyPartRequest.getDestinationBucketName(), "The destination bucket name must be specified when copying a part");
        assertParameterNotNull(copyPartRequest.getUploadId(), "The upload id must be specified when copying a part");
        assertParameterNotNull(copyPartRequest.getDestinationKey(), "The destination object key must be specified when copying a part");
        assertParameterNotNull(Integer.valueOf(copyPartRequest.getPartNumber()), "The part number must be specified when copying a part");
        String destinationKey = copyPartRequest.getDestinationKey();
        String destinationBucketName = copyPartRequest.getDestinationBucketName();
        Request<CopyPartRequest> request = createRequest(destinationBucketName, destinationKey, copyPartRequest, HttpMethodName.PUT);
        populateRequestWithCopyPartParameters(request, copyPartRequest);
        request.addParameter("uploadId", copyPartRequest.getUploadId());
        request.addParameter("partNumber", Integer.toString(copyPartRequest.getPartNumber()));
        setZeroContentLength(request);
        try {
            XmlResponsesSaxParser.CopyObjectResultHandler copyObjectResultHandler = (XmlResponsesSaxParser.CopyObjectResultHandler) invoke(request, new ResponseHeaderHandlerChain<>(new Unmarshallers.CopyObjectUnmarshaller(), new ServerSideEncryptionHeaderHandler(), new S3VersionHeaderHandler()), destinationBucketName, destinationKey);
            if (copyObjectResultHandler.getErrorCode() != null) {
                String errorCode = copyObjectResultHandler.getErrorCode();
                String errorMessage = copyObjectResultHandler.getErrorMessage();
                String requestId = copyObjectResultHandler.getErrorRequestId();
                String hostId = copyObjectResultHandler.getErrorHostId();
                AmazonS3Exception ase = new AmazonS3Exception(errorMessage);
                ase.setErrorCode(errorCode);
                ase.setErrorType(AmazonServiceException.ErrorType.Service);
                ase.setRequestId(requestId);
                ase.setExtendedRequestId(hostId);
                ase.setServiceName(request.getServiceName());
                ase.setStatusCode(200);
                throw ase;
            }
            CopyPartResult copyPartResult = new CopyPartResult();
            copyPartResult.setETag(copyObjectResultHandler.getETag());
            copyPartResult.setPartNumber(copyPartRequest.getPartNumber());
            copyPartResult.setLastModifiedDate(copyObjectResultHandler.getLastModified());
            copyPartResult.setVersionId(copyObjectResultHandler.getVersionId());
            copyPartResult.setSSEAlgorithm(copyObjectResultHandler.getSSEAlgorithm());
            copyPartResult.setSSECustomerAlgorithm(copyObjectResultHandler.getSSECustomerAlgorithm());
            copyPartResult.setSSECustomerKeyMd5(copyObjectResultHandler.getSSECustomerKeyMd5());
            return copyPartResult;
        } catch (AmazonS3Exception ase2) {
            if (ase2.getStatusCode() == 412) {
                return null;
            }
            throw ase2;
        }
    }

    public void deleteObject(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(deleteObjectRequest, "The delete object request must be specified when deleting an object");
        assertParameterNotNull(deleteObjectRequest.getBucketName(), "The bucket name must be specified when deleting an object");
        assertParameterNotNull(deleteObjectRequest.getKey(), "The key must be specified when deleting an object");
        invoke(createRequest(deleteObjectRequest.getBucketName(), deleteObjectRequest.getKey(), deleteObjectRequest, HttpMethodName.DELETE), this.voidResponseHandler, deleteObjectRequest.getBucketName(), deleteObjectRequest.getKey());
    }

    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
        Request<DeleteObjectsRequest> request = createRequest(deleteObjectsRequest.getBucketName(), (String) null, deleteObjectsRequest, HttpMethodName.POST);
        request.addParameter("delete", (String) null);
        if (deleteObjectsRequest.getMfa() != null) {
            populateRequestWithMfaDetails(request, deleteObjectsRequest.getMfa());
        }
        byte[] content = new MultiObjectDeleteXmlFactory().convertToXmlByteArray(deleteObjectsRequest);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(content));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(content)));
            DeleteObjectsResponse response = (DeleteObjectsResponse) invoke(request, new Unmarshallers.DeleteObjectsResultUnmarshaller(), deleteObjectsRequest.getBucketName(), (String) null);
            if (response.getErrors().isEmpty()) {
                return new DeleteObjectsResult(response.getDeletedObjects());
            }
            throw new MultiObjectDeleteException(response.getErrors(), response.getDeletedObjects());
        } catch (Exception e) {
            throw new AmazonClientException("Couldn't compute md5 sum", e);
        }
    }

    public void deleteVersion(String bucketName, String key, String versionId) throws AmazonClientException, AmazonServiceException {
        deleteVersion(new DeleteVersionRequest(bucketName, key, versionId));
    }

    public void deleteVersion(DeleteVersionRequest deleteVersionRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(deleteVersionRequest, "The delete version request object must be specified when deleting a version");
        String bucketName = deleteVersionRequest.getBucketName();
        String key = deleteVersionRequest.getKey();
        String versionId = deleteVersionRequest.getVersionId();
        assertParameterNotNull(bucketName, "The bucket name must be specified when deleting a version");
        assertParameterNotNull(key, "The key must be specified when deleting a version");
        assertParameterNotNull(versionId, "The version ID must be specified when deleting a version");
        Request<DeleteVersionRequest> request = createRequest(bucketName, key, deleteVersionRequest, HttpMethodName.DELETE);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        if (deleteVersionRequest.getMfa() != null) {
            populateRequestWithMfaDetails(request, deleteVersionRequest.getMfa());
        }
        invoke(request, this.voidResponseHandler, bucketName, key);
    }

    public void setBucketVersioningConfiguration(SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(setBucketVersioningConfigurationRequest, "The SetBucketVersioningConfigurationRequest object must be specified when setting versioning configuration");
        String bucketName = setBucketVersioningConfigurationRequest.getBucketName();
        BucketVersioningConfiguration versioningConfiguration = setBucketVersioningConfigurationRequest.getVersioningConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting versioning configuration");
        assertParameterNotNull(versioningConfiguration, "The bucket versioning parameter must be specified when setting versioning configuration");
        if (versioningConfiguration.isMfaDeleteEnabled() != null) {
            assertParameterNotNull(setBucketVersioningConfigurationRequest.getMfa(), "The MFA parameter must be specified when changing MFA Delete status in the versioning configuration");
        }
        Request<SetBucketVersioningConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketVersioningConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("versioning", (String) null);
        if (!(versioningConfiguration.isMfaDeleteEnabled() == null || setBucketVersioningConfigurationRequest.getMfa() == null)) {
            populateRequestWithMfaDetails(request, setBucketVersioningConfigurationRequest.getMfa());
        }
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(versioningConfiguration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when querying versioning configuration");
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("versioning", (String) null);
        return (BucketVersioningConfiguration) invoke(request, new Unmarshallers.BucketVersioningConfigurationUnmarshaller(), bucketName, (String) null);
    }

    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        return getBucketWebsiteConfiguration(new GetBucketWebsiteConfigurationRequest(bucketName));
    }

    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = getBucketWebsiteConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting a bucket's website configuration");
        Request<GetBucketWebsiteConfigurationRequest> request = createRequest(bucketName, (String) null, getBucketWebsiteConfigurationRequest, HttpMethodName.GET);
        request.addParameter(PlaceFields.WEBSITE, (String) null);
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        try {
            return (BucketWebsiteConfiguration) invoke(request, new Unmarshallers.BucketWebsiteConfigurationUnmarshaller(), bucketName, (String) null);
        } catch (AmazonServiceException ase) {
            if (ase.getStatusCode() == 404) {
                return null;
            }
            throw ase;
        }
    }

    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("lifecycle", (String) null);
        try {
            return (BucketLifecycleConfiguration) invoke(request, new Unmarshallers.BucketLifecycleConfigurationUnmarshaller(), bucketName, (String) null);
        } catch (AmazonServiceException ase) {
            switch (ase.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw ase;
            }
        }
    }

    public void setBucketLifecycleConfiguration(String bucketName, BucketLifecycleConfiguration bucketLifecycleConfiguration) {
        setBucketLifecycleConfiguration(new SetBucketLifecycleConfigurationRequest(bucketName, bucketLifecycleConfiguration));
    }

    public void setBucketLifecycleConfiguration(SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
        assertParameterNotNull(setBucketLifecycleConfigurationRequest, "The set bucket lifecycle configuration request object must be specified.");
        String bucketName = setBucketLifecycleConfigurationRequest.getBucketName();
        BucketLifecycleConfiguration bucketLifecycleConfiguration = setBucketLifecycleConfigurationRequest.getLifecycleConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting bucket lifecycle configuration.");
        assertParameterNotNull(bucketLifecycleConfiguration, "The lifecycle configuration parameter must be specified when setting bucket lifecycle configuration.");
        Request<SetBucketLifecycleConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketLifecycleConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("lifecycle", (String) null);
        byte[] content = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketLifecycleConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(content));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(content)));
            invoke(request, this.voidResponseHandler, bucketName, (String) null);
        } catch (Exception e) {
            throw new AmazonClientException("Couldn't compute md5 sum", e);
        }
    }

    public void deleteBucketLifecycleConfiguration(String bucketName) {
        deleteBucketLifecycleConfiguration(new DeleteBucketLifecycleConfigurationRequest(bucketName));
    }

    public void deleteBucketLifecycleConfiguration(DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
        assertParameterNotNull(deleteBucketLifecycleConfigurationRequest, "The delete bucket lifecycle configuration request object must be specified.");
        String bucketName = deleteBucketLifecycleConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting bucket lifecycle configuration.");
        Request<DeleteBucketLifecycleConfigurationRequest> request = createRequest(bucketName, (String) null, deleteBucketLifecycleConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("lifecycle", (String) null);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("cors", (String) null);
        try {
            return (BucketCrossOriginConfiguration) invoke(request, new Unmarshallers.BucketCrossOriginConfigurationUnmarshaller(), bucketName, (String) null);
        } catch (AmazonServiceException ase) {
            switch (ase.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw ase;
            }
        }
    }

    public void setBucketCrossOriginConfiguration(String bucketName, BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
        setBucketCrossOriginConfiguration(new SetBucketCrossOriginConfigurationRequest(bucketName, bucketCrossOriginConfiguration));
    }

    public void setBucketCrossOriginConfiguration(SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
        assertParameterNotNull(setBucketCrossOriginConfigurationRequest, "The set bucket cross origin configuration request object must be specified.");
        String bucketName = setBucketCrossOriginConfigurationRequest.getBucketName();
        BucketCrossOriginConfiguration bucketCrossOriginConfiguration = setBucketCrossOriginConfigurationRequest.getCrossOriginConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting bucket cross origin configuration.");
        assertParameterNotNull(bucketCrossOriginConfiguration, "The cross origin configuration parameter must be specified when setting bucket cross origin configuration.");
        Request<SetBucketCrossOriginConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketCrossOriginConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("cors", (String) null);
        byte[] content = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketCrossOriginConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(content));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(content)));
            invoke(request, this.voidResponseHandler, bucketName, (String) null);
        } catch (Exception e) {
            throw new AmazonClientException("Couldn't compute md5 sum", e);
        }
    }

    public void deleteBucketCrossOriginConfiguration(String bucketName) {
        deleteBucketCrossOriginConfiguration(new DeleteBucketCrossOriginConfigurationRequest(bucketName));
    }

    public void deleteBucketCrossOriginConfiguration(DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
        assertParameterNotNull(deleteBucketCrossOriginConfigurationRequest, "The delete bucket cross origin configuration request object must be specified.");
        String bucketName = deleteBucketCrossOriginConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting bucket cross origin configuration.");
        Request<DeleteBucketCrossOriginConfigurationRequest> request = createRequest(bucketName, (String) null, deleteBucketCrossOriginConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("cors", (String) null);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("tagging", (String) null);
        try {
            return (BucketTaggingConfiguration) invoke(request, new Unmarshallers.BucketTaggingConfigurationUnmarshaller(), bucketName, (String) null);
        } catch (AmazonServiceException ase) {
            switch (ase.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw ase;
            }
        }
    }

    public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
        setBucketTaggingConfiguration(new SetBucketTaggingConfigurationRequest(bucketName, bucketTaggingConfiguration));
    }

    public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
        assertParameterNotNull(setBucketTaggingConfigurationRequest, "The set bucket tagging configuration request object must be specified.");
        String bucketName = setBucketTaggingConfigurationRequest.getBucketName();
        BucketTaggingConfiguration bucketTaggingConfiguration = setBucketTaggingConfigurationRequest.getTaggingConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting bucket tagging configuration.");
        assertParameterNotNull(bucketTaggingConfiguration, "The tagging configuration parameter must be specified when setting bucket tagging configuration.");
        Request<SetBucketTaggingConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketTaggingConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("tagging", (String) null);
        byte[] content = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketTaggingConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(content));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(content)));
            invoke(request, this.voidResponseHandler, bucketName, (String) null);
        } catch (Exception e) {
            throw new AmazonClientException("Couldn't compute md5 sum", e);
        }
    }

    public void deleteBucketTaggingConfiguration(String bucketName) {
        deleteBucketTaggingConfiguration(new DeleteBucketTaggingConfigurationRequest(bucketName));
    }

    public void deleteBucketTaggingConfiguration(DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
        assertParameterNotNull(deleteBucketTaggingConfigurationRequest, "The delete bucket tagging configuration request object must be specified.");
        String bucketName = deleteBucketTaggingConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting bucket tagging configuration.");
        Request<DeleteBucketTaggingConfigurationRequest> request = createRequest(bucketName, (String) null, deleteBucketTaggingConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("tagging", (String) null);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration) throws AmazonClientException, AmazonServiceException {
        setBucketWebsiteConfiguration(new SetBucketWebsiteConfigurationRequest(bucketName, configuration));
    }

    public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = setBucketWebsiteConfigurationRequest.getBucketName();
        BucketWebsiteConfiguration configuration = setBucketWebsiteConfigurationRequest.getConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting a bucket's website configuration");
        assertParameterNotNull(configuration, "The bucket website configuration parameter must be specified when setting a bucket's website configuration");
        if (configuration.getRedirectAllRequestsTo() == null) {
            assertParameterNotNull(configuration.getIndexDocumentSuffix(), "The bucket website configuration parameter must specify the index document suffix when setting a bucket's website configuration");
        }
        Request<SetBucketWebsiteConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketWebsiteConfigurationRequest, HttpMethodName.PUT);
        request.addParameter(PlaceFields.WEBSITE, (String) null);
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(configuration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public void deleteBucketWebsiteConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        deleteBucketWebsiteConfiguration(new DeleteBucketWebsiteConfigurationRequest(bucketName));
    }

    public void deleteBucketWebsiteConfiguration(DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        String bucketName = deleteBucketWebsiteConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting a bucket's website configuration");
        Request<DeleteBucketWebsiteConfigurationRequest> request = createRequest(bucketName, (String) null, deleteBucketWebsiteConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter(PlaceFields.WEBSITE, (String) null);
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public void setBucketNotificationConfiguration(String bucketName, BucketNotificationConfiguration bucketNotificationConfiguration) throws AmazonClientException, AmazonServiceException {
        setBucketNotificationConfiguration(new SetBucketNotificationConfigurationRequest(bucketName, bucketNotificationConfiguration));
    }

    public void setBucketNotificationConfiguration(SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(setBucketNotificationConfigurationRequest, "The set bucket notification configuration request object must be specified.");
        String bucketName = setBucketNotificationConfigurationRequest.getBucketName();
        BucketNotificationConfiguration bucketNotificationConfiguration = setBucketNotificationConfigurationRequest.getNotificationConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting bucket notification configuration.");
        assertParameterNotNull(bucketNotificationConfiguration, "The notification configuration parameter must be specified when setting bucket notification configuration.");
        Request<SetBucketNotificationConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketNotificationConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("notification", (String) null);
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(bucketNotificationConfiguration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when querying notification configuration");
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("notification", (String) null);
        return (BucketNotificationConfiguration) invoke(request, new Unmarshallers.BucketNotificationConfigurationUnmarshaller(), bucketName, (String) null);
    }

    public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when requesting a bucket's logging status");
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.GET);
        request.addParameter("logging", (String) null);
        return (BucketLoggingConfiguration) invoke(request, new Unmarshallers.BucketLoggingConfigurationnmarshaller(), bucketName, (String) null);
    }

    public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(setBucketLoggingConfigurationRequest, "The set bucket logging configuration request object must be specified when enabling server access logging");
        String bucketName = setBucketLoggingConfigurationRequest.getBucketName();
        BucketLoggingConfiguration loggingConfiguration = setBucketLoggingConfigurationRequest.getLoggingConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when enabling server access logging");
        assertParameterNotNull(loggingConfiguration, "The logging configuration parameter must be specified when enabling server access logging");
        Request<SetBucketLoggingConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketLoggingConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("logging", (String) null);
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(loggingConfiguration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        return getBucketAccelerateConfiguration(new GetBucketAccelerateConfigurationRequest(bucketName));
    }

    public BucketAccelerateConfiguration getBucketAccelerateConfiguration(GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        assertParameterNotNull(getBucketAccelerateConfigurationRequest, "getBucketAccelerateConfigurationRequest must be specified.");
        String bucketName = getBucketAccelerateConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when querying accelerate configuration");
        Request<GetBucketAccelerateConfigurationRequest> request = createRequest(bucketName, (String) null, getBucketAccelerateConfigurationRequest, HttpMethodName.GET);
        request.addParameter("accelerate", (String) null);
        return (BucketAccelerateConfiguration) invoke(request, new Unmarshallers.BucketAccelerateConfigurationUnmarshaller(), bucketName, (String) null);
    }

    public void setBucketAccelerateConfiguration(String bucketName, BucketAccelerateConfiguration accelerateConfiguration) throws AmazonServiceException, AmazonClientException {
        setBucketAccelerateConfiguration(new SetBucketAccelerateConfigurationRequest(bucketName, accelerateConfiguration));
    }

    public void setBucketAccelerateConfiguration(SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        assertParameterNotNull(setBucketAccelerateConfigurationRequest, "setBucketAccelerateConfigurationRequest must be specified");
        String bucketName = setBucketAccelerateConfigurationRequest.getBucketName();
        BucketAccelerateConfiguration accelerateConfiguration = setBucketAccelerateConfigurationRequest.getAccelerateConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting accelerate configuration.");
        assertParameterNotNull(accelerateConfiguration, "The bucket accelerate configuration parameter must be specified.");
        assertParameterNotNull(accelerateConfiguration.getStatus(), "The status parameter must be specified when updating bucket accelerate configuration.");
        Request<SetBucketAccelerateConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketAccelerateConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("accelerate", (String) null);
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(accelerateConfiguration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public BucketPolicy getBucketPolicy(String bucketName) throws AmazonClientException, AmazonServiceException {
        return getBucketPolicy(new GetBucketPolicyRequest(bucketName));
    }

    public void setBucketPolicy(String bucketName, String policyText) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(bucketName, "The bucket name must be specified when setting a bucket policy");
        assertParameterNotNull(policyText, "The policy text must be specified when setting a bucket policy");
        Request<GenericBucketRequest> request = createRequest(bucketName, (String) null, new GenericBucketRequest(bucketName), HttpMethodName.PUT);
        request.addParameter("policy", (String) null);
        byte[] bytes = ServiceUtils.toByteArray(policyText);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public void deleteBucketPolicy(String bucketName) throws AmazonClientException, AmazonServiceException {
        deleteBucketPolicy(new DeleteBucketPolicyRequest(bucketName));
    }

    public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(getBucketPolicyRequest, "The request object must be specified when getting a bucket policy");
        String bucketName = getBucketPolicyRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name must be specified when getting a bucket policy");
        Request<GetBucketPolicyRequest> request = createRequest(bucketName, (String) null, getBucketPolicyRequest, HttpMethodName.GET);
        request.addParameter("policy", (String) null);
        BucketPolicy result = new BucketPolicy();
        try {
            result.setPolicyText((String) invoke(request, new S3StringResponseHandler(), bucketName, (String) null));
        } catch (AmazonServiceException ase) {
            if (!ase.getErrorCode().equals("NoSuchBucketPolicy")) {
                throw ase;
            }
        }
        return result;
    }

    public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(setBucketPolicyRequest, "The request object must be specified when setting a bucket policy");
        String bucketName = setBucketPolicyRequest.getBucketName();
        String policyText = setBucketPolicyRequest.getPolicyText();
        assertParameterNotNull(bucketName, "The bucket name must be specified when setting a bucket policy");
        assertParameterNotNull(policyText, "The policy text must be specified when setting a bucket policy");
        Request<SetBucketPolicyRequest> request = createRequest(bucketName, (String) null, setBucketPolicyRequest, HttpMethodName.PUT);
        request.addParameter("policy", (String) null);
        byte[] bytes = ServiceUtils.toByteArray(policyText);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(deleteBucketPolicyRequest, "The request object must be specified when deleting a bucket policy");
        String bucketName = deleteBucketPolicyRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name must be specified when deleting a bucket policy");
        Request<DeleteBucketPolicyRequest> request = createRequest(bucketName, (String) null, deleteBucketPolicyRequest, HttpMethodName.DELETE);
        request.addParameter("policy", (String) null);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws AmazonClientException {
        return generatePresignedUrl(bucketName, key, expiration, HttpMethod.GET);
    }

    public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method) throws AmazonClientException {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, method);
        request.setExpiration(expiration);
        return generatePresignedUrl(request);
    }

    public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws AmazonClientException {
        assertParameterNotNull(generatePresignedUrlRequest, "The request parameter must be specified when generating a pre-signed URL");
        String bucketName = generatePresignedUrlRequest.getBucketName();
        String key = generatePresignedUrlRequest.getKey();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when generating a pre-signed URL");
        assertParameterNotNull(generatePresignedUrlRequest.getMethod(), "The HTTP method request parameter must be specified when generating a pre-signed URL");
        if (generatePresignedUrlRequest.getExpiration() == null) {
            generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + 900000));
        }
        Request<GeneratePresignedUrlRequest> request = createRequest(bucketName, key, generatePresignedUrlRequest, HttpMethodName.valueOf(generatePresignedUrlRequest.getMethod().toString()));
        for (Map.Entry<String, String> entry : generatePresignedUrlRequest.getRequestParameters().entrySet()) {
            request.addParameter(entry.getKey(), entry.getValue());
        }
        if (generatePresignedUrlRequest.getContentType() != null) {
            request.addHeader("Content-Type", generatePresignedUrlRequest.getContentType());
        }
        if (generatePresignedUrlRequest.getContentMd5() != null) {
            request.addHeader(Headers.CONTENT_MD5, generatePresignedUrlRequest.getContentMd5());
        }
        populateSseCpkRequestParameters(request, generatePresignedUrlRequest.getSSECustomerKey());
        addResponseHeaderParameters(request, generatePresignedUrlRequest.getResponseHeaders());
        Signer signer = createSigner(request, bucketName, key);
        if (signer instanceof Presigner) {
            ((Presigner) signer).presignRequest(request, this.awsCredentialsProvider.getCredentials(), generatePresignedUrlRequest.getExpiration());
        } else {
            presignRequest(request, generatePresignedUrlRequest.getMethod(), bucketName, key, generatePresignedUrlRequest.getExpiration(), (String) null);
        }
        return ServiceUtils.convertRequestToUrl(request, true);
    }

    public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(abortMultipartUploadRequest, "The request parameter must be specified when aborting a multipart upload");
        assertParameterNotNull(abortMultipartUploadRequest.getBucketName(), "The bucket name parameter must be specified when aborting a multipart upload");
        assertParameterNotNull(abortMultipartUploadRequest.getKey(), "The key parameter must be specified when aborting a multipart upload");
        assertParameterNotNull(abortMultipartUploadRequest.getUploadId(), "The upload ID parameter must be specified when aborting a multipart upload");
        String bucketName = abortMultipartUploadRequest.getBucketName();
        String key = abortMultipartUploadRequest.getKey();
        Request<AbortMultipartUploadRequest> request = createRequest(bucketName, key, abortMultipartUploadRequest, HttpMethodName.DELETE);
        request.addParameter("uploadId", abortMultipartUploadRequest.getUploadId());
        invoke(request, this.voidResponseHandler, bucketName, key);
    }

    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(completeMultipartUploadRequest, "The request parameter must be specified when completing a multipart upload");
        String bucketName = completeMultipartUploadRequest.getBucketName();
        String key = completeMultipartUploadRequest.getKey();
        String uploadId = completeMultipartUploadRequest.getUploadId();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when completing a multipart upload");
        assertParameterNotNull(key, "The key parameter must be specified when completing a multipart upload");
        assertParameterNotNull(uploadId, "The upload ID parameter must be specified when completing a multipart upload");
        assertParameterNotNull(completeMultipartUploadRequest.getPartETags(), "The part ETags parameter must be specified when completing a multipart upload");
        Request<CompleteMultipartUploadRequest> request = createRequest(bucketName, key, completeMultipartUploadRequest, HttpMethodName.POST);
        request.addParameter("uploadId", uploadId);
        byte[] xml = RequestXmlFactory.convertToXmlByteArray(completeMultipartUploadRequest.getPartETags());
        request.addHeader("Content-Type", "text/plain");
        request.addHeader("Content-Length", String.valueOf(xml.length));
        request.setContent(new ByteArrayInputStream(xml));
        ResponseHeaderHandlerChain<XmlResponsesSaxParser.CompleteMultipartUploadHandler> responseHandler = new ResponseHeaderHandlerChain<>(new Unmarshallers.CompleteMultipartUploadResultUnmarshaller(), new ServerSideEncryptionHeaderHandler(), new ObjectExpirationHeaderHandler());
        XmlResponsesSaxParser.CompleteMultipartUploadHandler handler = (XmlResponsesSaxParser.CompleteMultipartUploadHandler) invoke(request, responseHandler, bucketName, key);
        if (handler.getCompleteMultipartUploadResult() != null) {
            handler.getCompleteMultipartUploadResult().setVersionId(responseHandler.getResponseHeaders().get(Headers.S3_VERSION_ID));
            return handler.getCompleteMultipartUploadResult();
        }
        throw handler.getAmazonS3Exception();
    }

    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(initiateMultipartUploadRequest, "The request parameter must be specified when initiating a multipart upload");
        assertParameterNotNull(initiateMultipartUploadRequest.getBucketName(), "The bucket name parameter must be specified when initiating a multipart upload");
        assertParameterNotNull(initiateMultipartUploadRequest.getKey(), "The key parameter must be specified when initiating a multipart upload");
        Request<InitiateMultipartUploadRequest> request = createRequest(initiateMultipartUploadRequest.getBucketName(), initiateMultipartUploadRequest.getKey(), initiateMultipartUploadRequest, HttpMethodName.POST);
        request.addParameter("uploads", (String) null);
        if (initiateMultipartUploadRequest.getStorageClass() != null) {
            request.addHeader(Headers.STORAGE_CLASS, initiateMultipartUploadRequest.getStorageClass().toString());
        }
        if (initiateMultipartUploadRequest.getRedirectLocation() != null) {
            request.addHeader(Headers.REDIRECT_LOCATION, initiateMultipartUploadRequest.getRedirectLocation());
        }
        if (initiateMultipartUploadRequest.getAccessControlList() != null) {
            addAclHeaders(request, initiateMultipartUploadRequest.getAccessControlList());
        } else if (initiateMultipartUploadRequest.getCannedACL() != null) {
            request.addHeader(Headers.S3_CANNED_ACL, initiateMultipartUploadRequest.getCannedACL().toString());
        }
        if (initiateMultipartUploadRequest.objectMetadata != null) {
            populateRequestMetadata(request, initiateMultipartUploadRequest.objectMetadata);
        }
        populateSseCpkRequestParameters(request, initiateMultipartUploadRequest.getSSECustomerKey());
        setZeroContentLength(request);
        request.setContent(new ByteArrayInputStream(new byte[0]));
        return (InitiateMultipartUploadResult) invoke(request, new ResponseHeaderHandlerChain<>(new Unmarshallers.InitiateMultipartUploadResultUnmarshaller(), new ServerSideEncryptionHeaderHandler()), initiateMultipartUploadRequest.getBucketName(), initiateMultipartUploadRequest.getKey());
    }

    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(listMultipartUploadsRequest, "The request parameter must be specified when listing multipart uploads");
        assertParameterNotNull(listMultipartUploadsRequest.getBucketName(), "The bucket name parameter must be specified when listing multipart uploads");
        Request<ListMultipartUploadsRequest> request = createRequest(listMultipartUploadsRequest.getBucketName(), (String) null, listMultipartUploadsRequest, HttpMethodName.GET);
        request.addParameter("uploads", (String) null);
        if (listMultipartUploadsRequest.getKeyMarker() != null) {
            request.addParameter("key-marker", listMultipartUploadsRequest.getKeyMarker());
        }
        if (listMultipartUploadsRequest.getMaxUploads() != null) {
            request.addParameter("max-uploads", listMultipartUploadsRequest.getMaxUploads().toString());
        }
        if (listMultipartUploadsRequest.getUploadIdMarker() != null) {
            request.addParameter("upload-id-marker", listMultipartUploadsRequest.getUploadIdMarker());
        }
        if (listMultipartUploadsRequest.getDelimiter() != null) {
            request.addParameter("delimiter", listMultipartUploadsRequest.getDelimiter());
        }
        if (listMultipartUploadsRequest.getPrefix() != null) {
            request.addParameter("prefix", listMultipartUploadsRequest.getPrefix());
        }
        if (listMultipartUploadsRequest.getEncodingType() != null) {
            request.addParameter("encoding-type", listMultipartUploadsRequest.getEncodingType());
        }
        return (MultipartUploadListing) invoke(request, new Unmarshallers.ListMultipartUploadsResultUnmarshaller(), listMultipartUploadsRequest.getBucketName(), (String) null);
    }

    public PartListing listParts(ListPartsRequest listPartsRequest) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(listPartsRequest, "The request parameter must be specified when listing parts");
        assertParameterNotNull(listPartsRequest.getBucketName(), "The bucket name parameter must be specified when listing parts");
        assertParameterNotNull(listPartsRequest.getKey(), "The key parameter must be specified when listing parts");
        assertParameterNotNull(listPartsRequest.getUploadId(), "The upload ID parameter must be specified when listing parts");
        Request<ListPartsRequest> request = createRequest(listPartsRequest.getBucketName(), listPartsRequest.getKey(), listPartsRequest, HttpMethodName.GET);
        request.addParameter("uploadId", listPartsRequest.getUploadId());
        if (listPartsRequest.getMaxParts() != null) {
            request.addParameter("max-parts", listPartsRequest.getMaxParts().toString());
        }
        if (listPartsRequest.getPartNumberMarker() != null) {
            request.addParameter("part-number-marker", listPartsRequest.getPartNumberMarker().toString());
        }
        if (listPartsRequest.getEncodingType() != null) {
            request.addParameter("encoding-type", listPartsRequest.getEncodingType());
        }
        return (PartListing) invoke(request, new Unmarshallers.ListPartsResultUnmarshaller(), listPartsRequest.getBucketName(), listPartsRequest.getKey());
    }

    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException {
        InputStream inputStream;
        assertParameterNotNull(uploadPartRequest, "The request parameter must be specified when uploading a part");
        String bucketName = uploadPartRequest.getBucketName();
        String key = uploadPartRequest.getKey();
        String uploadId = uploadPartRequest.getUploadId();
        int partNumber = uploadPartRequest.getPartNumber();
        long partSize = uploadPartRequest.getPartSize();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when uploading a part");
        assertParameterNotNull(key, "The key parameter must be specified when uploading a part");
        assertParameterNotNull(uploadId, "The upload ID parameter must be specified when uploading a part");
        assertParameterNotNull(Integer.valueOf(partNumber), "The part number parameter must be specified when uploading a part");
        assertParameterNotNull(Long.valueOf(partSize), "The part size parameter must be specified when uploading a part");
        Request<UploadPartRequest> request = createRequest(bucketName, key, uploadPartRequest, HttpMethodName.PUT);
        request.addParameter("uploadId", uploadId);
        request.addParameter("partNumber", Integer.toString(partNumber));
        addHeaderIfNotNull(request, Headers.CONTENT_MD5, uploadPartRequest.getMd5Digest());
        request.addHeader("Content-Length", Long.toString(partSize));
        request.addHeader("Expect", HTTP.EXPECT_CONTINUE);
        populateSseCpkRequestParameters(request, uploadPartRequest.getSSECustomerKey());
        if (uploadPartRequest.getInputStream() != null) {
            inputStream = uploadPartRequest.getInputStream();
        } else if (uploadPartRequest.getFile() != null) {
            try {
                inputStream = new InputSubstream(new RepeatableFileInputStream(uploadPartRequest.getFile()), uploadPartRequest.getFileOffset(), partSize, true);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("The specified file doesn't exist", e);
            }
        } else {
            throw new IllegalArgumentException("A File or InputStream must be specified when uploading part");
        }
        MD5DigestCalculatingInputStream md5DigestStream = null;
        if (uploadPartRequest.getMd5Digest() == null && !ServiceUtils.skipMd5CheckPerRequest(uploadPartRequest)) {
            md5DigestStream = new MD5DigestCalculatingInputStream(inputStream);
            inputStream = md5DigestStream;
        }
        ProgressListenerCallbackExecutor progressListenerCallbackExecutor = ProgressListenerCallbackExecutor.wrapListener(uploadPartRequest.getGeneralProgressListener());
        if (progressListenerCallbackExecutor != null) {
            InputStream progressReportingInputStream = new ProgressReportingInputStream(inputStream, progressListenerCallbackExecutor);
            fireProgressEvent(progressListenerCallbackExecutor, 1024);
            inputStream = progressReportingInputStream;
        }
        try {
            request.setContent(inputStream);
            ObjectMetadata metadata = (ObjectMetadata) invoke(request, new S3MetadataResponseHandler(), bucketName, key);
            if (metadata == null || md5DigestStream == null || ServiceUtils.skipMd5CheckPerResponse(metadata) || Arrays.equals(md5DigestStream.getMd5Digest(), BinaryUtils.fromHex(metadata.getETag()))) {
                fireProgressEvent(progressListenerCallbackExecutor, 2048);
                UploadPartResult result = new UploadPartResult();
                result.setETag(metadata.getETag());
                result.setPartNumber(partNumber);
                result.setSSEAlgorithm(metadata.getSSEAlgorithm());
                result.setSSEKMSKeyId(metadata.getSSEKMSKeyId());
                result.setSSECustomerAlgorithm(metadata.getSSECustomerAlgorithm());
                result.setSSECustomerKeyMd5(metadata.getSSECustomerKeyMd5());
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                    }
                }
                return result;
            }
            throw new AmazonClientException("Unable to verify integrity of data upload.  Client calculated content hash didn't match hash calculated by Amazon S3.  You may need to delete the data stored in Amazon S3.");
        } catch (AmazonClientException ace) {
            fireProgressEvent(progressListenerCallbackExecutor, 4096);
            throw ace;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return (S3ResponseMetadata) this.client.getResponseMetadataForRequest(request);
    }

    public void restoreObject(RestoreObjectRequest restoreObjectRequest) throws AmazonServiceException {
        String bucketName = restoreObjectRequest.getBucketName();
        String key = restoreObjectRequest.getKey();
        String versionId = restoreObjectRequest.getVersionId();
        int expirationIndays = restoreObjectRequest.getExpirationInDays();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when copying a glacier object");
        assertParameterNotNull(key, "The key parameter must be specified when copying a glacier object");
        if (expirationIndays == -1) {
            throw new IllegalArgumentException("The expiration in days parameter must be specified when copying a glacier object");
        }
        Request<RestoreObjectRequest> request = createRequest(bucketName, key, restoreObjectRequest, HttpMethodName.POST);
        request.addParameter(APNetworkManager2.HTTP_KEY_OVERSEARESTORE, (String) null);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        byte[] content = RequestXmlFactory.convertToXmlByteArray(restoreObjectRequest);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(content));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(content)));
            invoke(request, this.voidResponseHandler, bucketName, key);
        } catch (Exception e) {
            throw new AmazonClientException("Couldn't compute md5 sum", e);
        }
    }

    public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
        restoreObject(new RestoreObjectRequest(bucketName, key, expirationInDays));
    }

    private void assertParameterNotNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void fireProgressEvent(ProgressListenerCallbackExecutor progressListenerCallbackExecutor, int eventType) {
        if (progressListenerCallbackExecutor != null) {
            ProgressEvent event = new ProgressEvent(0);
            event.setEventCode(eventType);
            progressListenerCallbackExecutor.progressChanged(event);
        }
    }

    private AccessControlList getAcl(String bucketName, String key, String versionId, AmazonWebServiceRequest originalRequest) {
        if (originalRequest == null) {
            originalRequest = new GenericBucketRequest(bucketName);
        }
        Request<AmazonWebServiceRequest> request = createRequest(bucketName, key, originalRequest, HttpMethodName.GET);
        request.addParameter("acl", (String) null);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        return (AccessControlList) invoke(request, new Unmarshallers.AccessControlListUnmarshaller(), bucketName, key);
    }

    private void setAcl(String bucketName, String key, String versionId, CannedAccessControlList cannedAcl, AmazonWebServiceRequest originalRequest) {
        if (originalRequest == null) {
            originalRequest = new GenericBucketRequest(bucketName);
        }
        Request<AmazonWebServiceRequest> request = createRequest(bucketName, key, originalRequest, HttpMethodName.PUT);
        request.addParameter("acl", (String) null);
        request.addHeader(Headers.S3_CANNED_ACL, cannedAcl.toString());
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        invoke(request, this.voidResponseHandler, bucketName, key);
    }

    private void setAcl(String bucketName, String key, String versionId, AccessControlList acl, AmazonWebServiceRequest originalRequest) {
        if (originalRequest == null) {
            originalRequest = new GenericBucketRequest(bucketName);
        }
        Request<AmazonWebServiceRequest> request = createRequest(bucketName, key, originalRequest, HttpMethodName.PUT);
        request.addParameter("acl", (String) null);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }
        byte[] aclAsXml = new AclXmlFactory().convertToXmlByteArray(acl);
        request.addHeader("Content-Type", "text/plain");
        request.addHeader("Content-Length", String.valueOf(aclAsXml.length));
        request.setContent(new ByteArrayInputStream(aclAsXml));
        invoke(request, this.voidResponseHandler, bucketName, key);
    }

    /* access modifiers changed from: protected */
    public Signer createSigner(Request<?> request, String bucketName, String key) {
        Signer signer = getSigner();
        if (upgradeToSigV4(request) && !(signer instanceof AWSS3V4Signer)) {
            AWSS3V4Signer v4Signer = new AWSS3V4Signer();
            v4Signer.setServiceName(getServiceNameIntern());
            String regionOverride = getSignerRegionOverride();
            if (regionOverride == null) {
                regionOverride = this.clientRegion;
            }
            if (regionOverride == null) {
                throw new AmazonClientException("Signature Version 4 requires knowing the region of the bucket you're trying to access. You can configure a region by calling AmazonS3Client.setRegion(Region) or AmazonS3Client.setEndpoint(String) with a region-specific endpoint such as \"s3-us-west-2.amazonaws.com\".");
            }
            v4Signer.setRegionName(regionOverride);
            return v4Signer;
        } else if (!(signer instanceof S3Signer)) {
            return signer;
        } else {
            StringBuilder append = new StringBuilder().append(com.appsflyer.share.Constants.URL_PATH_DELIMITER).append(bucketName != null ? bucketName + com.appsflyer.share.Constants.URL_PATH_DELIMITER : "");
            if (key == null) {
                key = "";
            }
            return new S3Signer(request.getHttpMethod().toString(), append.append(key).toString());
        }
    }

    private boolean upgradeToSigV4(Request<?> request) {
        if (System.getProperty(SDKGlobalConfiguration.ENFORCE_S3_SIGV4_SYSTEM_PROPERTY) == null && this.endpoint.getHost().endsWith(Constants.S3_HOSTNAME)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public <T> void presignRequest(Request<T> request, HttpMethod methodName, String bucketName, String key, Date expiration, String subResource) {
        beforeRequest(request);
        String resourcePath = (com.appsflyer.share.Constants.URL_PATH_DELIMITER + (bucketName != null ? bucketName + com.appsflyer.share.Constants.URL_PATH_DELIMITER : "") + (key != null ? HttpUtils.urlEncode(key, true) : "") + (subResource != null ? "?" + subResource : "")).replaceAll("(?<=/)/", "%2F");
        AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
        AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
        if (!(originalRequest == null || originalRequest.getRequestCredentials() == null)) {
            credentials = originalRequest.getRequestCredentials();
        }
        new S3QueryStringSigner(methodName.toString(), resourcePath, expiration).sign(request, credentials);
        if (request.getHeaders().containsKey(Headers.SECURITY_TOKEN)) {
            request.addParameter(Headers.SECURITY_TOKEN, request.getHeaders().get(Headers.SECURITY_TOKEN));
            request.getHeaders().remove(Headers.SECURITY_TOKEN);
        }
    }

    private <T> void beforeRequest(Request<T> request) {
        if (this.requestHandler2s != null) {
            for (RequestHandler2 requestHandler2 : this.requestHandler2s) {
                requestHandler2.beforeRequest(request);
            }
        }
    }

    private URI convertToVirtualHostEndpoint(String bucketName) {
        try {
            return new URI(this.endpoint.getScheme() + "://" + bucketName + "." + this.endpoint.getAuthority());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid bucket name: " + bucketName, e);
        }
    }

    protected static void populateRequestMetadata(Request<?> request, ObjectMetadata metadata) {
        Map<String, Object> rawMetadata = metadata.getRawMetadata();
        if (rawMetadata.get(Headers.SERVER_SIDE_ENCRYPTION_KMS_KEY_ID) == null || ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION.equals(rawMetadata.get(Headers.SERVER_SIDE_ENCRYPTION))) {
            if (rawMetadata != null) {
                for (Map.Entry<String, Object> entry : rawMetadata.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            Date httpExpiresDate = metadata.getHttpExpiresDate();
            if (httpExpiresDate != null) {
                request.addHeader(Headers.EXPIRES, DateUtils.formatRFC822Date(httpExpiresDate));
            }
            Map<String, String> userMetadata = metadata.getUserMetadata();
            if (userMetadata != null) {
                for (Map.Entry<String, String> entry2 : userMetadata.entrySet()) {
                    String key = entry2.getKey();
                    String value = entry2.getValue();
                    if (key != null) {
                        key = key.trim();
                    }
                    if (value != null) {
                        value = value.trim();
                    }
                    request.addHeader(Headers.S3_USER_METADATA_PREFIX + key, value);
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("If you specify a KMS key id for server side encryption, you must also set the SSEAlgorithm to ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION");
    }

    private void populateRequestWithMfaDetails(Request<?> request, MultiFactorAuthentication mfa) {
        if (mfa != null) {
            String endpoint = request.getEndpoint().toString();
            if (endpoint.startsWith("http://")) {
                request.setEndpoint(URI.create(endpoint.replace("http://", "https://")));
                log.info("Overriding current endpoint to use HTTPS as required by S3 for requests containing an MFA header");
            }
            request.addHeader(Headers.S3_MFA, mfa.getDeviceSerialNumber() + " " + mfa.getToken());
        }
    }

    private static void populateRequestWithCopyObjectParameters(Request<? extends AmazonWebServiceRequest> request, CopyObjectRequest copyObjectRequest) {
        String copySourceHeader = com.appsflyer.share.Constants.URL_PATH_DELIMITER + HttpUtils.urlEncode(copyObjectRequest.getSourceBucketName(), true) + com.appsflyer.share.Constants.URL_PATH_DELIMITER + HttpUtils.urlEncode(copyObjectRequest.getSourceKey(), true);
        if (copyObjectRequest.getSourceVersionId() != null) {
            copySourceHeader = copySourceHeader + "?versionId=" + copyObjectRequest.getSourceVersionId();
        }
        request.addHeader("x-amz-copy-source", copySourceHeader);
        addDateHeader(request, Headers.COPY_SOURCE_IF_MODIFIED_SINCE, copyObjectRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.COPY_SOURCE_IF_UNMODIFIED_SINCE, copyObjectRequest.getUnmodifiedSinceConstraint());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_MATCH, copyObjectRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_NO_MATCH, copyObjectRequest.getNonmatchingETagConstraints());
        if (copyObjectRequest.getAccessControlList() != null) {
            addAclHeaders(request, copyObjectRequest.getAccessControlList());
        } else if (copyObjectRequest.getCannedAccessControlList() != null) {
            request.addHeader(Headers.S3_CANNED_ACL, copyObjectRequest.getCannedAccessControlList().toString());
        }
        if (copyObjectRequest.getStorageClass() != null) {
            request.addHeader(Headers.STORAGE_CLASS, copyObjectRequest.getStorageClass());
        }
        if (copyObjectRequest.getRedirectLocation() != null) {
            request.addHeader(Headers.REDIRECT_LOCATION, copyObjectRequest.getRedirectLocation());
        }
        ObjectMetadata newObjectMetadata = copyObjectRequest.getNewObjectMetadata();
        if (newObjectMetadata != null) {
            request.addHeader(Headers.METADATA_DIRECTIVE, "REPLACE");
            populateRequestMetadata(request, newObjectMetadata);
        }
        populateSourceSseCpkRequestParameters(request, copyObjectRequest.getSourceSSECustomerKey());
        populateSseCpkRequestParameters(request, copyObjectRequest.getDestinationSSECustomerKey());
    }

    private static void populateRequestWithCopyPartParameters(Request<?> request, CopyPartRequest copyPartRequest) {
        String copySourceHeader = com.appsflyer.share.Constants.URL_PATH_DELIMITER + HttpUtils.urlEncode(copyPartRequest.getSourceBucketName(), true) + com.appsflyer.share.Constants.URL_PATH_DELIMITER + HttpUtils.urlEncode(copyPartRequest.getSourceKey(), true);
        if (copyPartRequest.getSourceVersionId() != null) {
            copySourceHeader = copySourceHeader + "?versionId=" + copyPartRequest.getSourceVersionId();
        }
        request.addHeader("x-amz-copy-source", copySourceHeader);
        addDateHeader(request, Headers.COPY_SOURCE_IF_MODIFIED_SINCE, copyPartRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.COPY_SOURCE_IF_UNMODIFIED_SINCE, copyPartRequest.getUnmodifiedSinceConstraint());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_MATCH, copyPartRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_NO_MATCH, copyPartRequest.getNonmatchingETagConstraints());
        if (!(copyPartRequest.getFirstByte() == null || copyPartRequest.getLastByte() == null)) {
            request.addHeader(Headers.COPY_PART_RANGE, "bytes=" + copyPartRequest.getFirstByte() + "-" + copyPartRequest.getLastByte());
        }
        populateSourceSseCpkRequestParameters(request, copyPartRequest.getSourceSSECustomerKey());
        populateSseCpkRequestParameters(request, copyPartRequest.getDestinationSSECustomerKey());
    }

    private static void populateSseCpkRequestParameters(Request<?> request, SSECustomerKey sseKey) {
        if (sseKey != null) {
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM, sseKey.getAlgorithm());
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY, sseKey.getKey());
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, sseKey.getMd5());
            if (sseKey.getKey() != null && sseKey.getMd5() == null) {
                request.addHeader(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, Md5Utils.md5AsBase64(Base64.decode(sseKey.getKey())));
            }
        }
    }

    private static void populateSourceSseCpkRequestParameters(Request<?> request, SSECustomerKey sseKey) {
        if (sseKey != null) {
            addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM, sseKey.getAlgorithm());
            addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY, sseKey.getKey());
            addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, sseKey.getMd5());
            if (sseKey.getKey() != null && sseKey.getMd5() == null) {
                request.addHeader(Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, Md5Utils.md5AsBase64(Base64.decode(sseKey.getKey())));
            }
        }
    }

    private static void addHeaderIfNotNull(Request<?> request, String header, String value) {
        if (value != null) {
            request.addHeader(header, value);
        }
    }

    private static void addDateHeader(Request<?> request, String header, Date value) {
        if (value != null) {
            request.addHeader(header, ServiceUtils.formatRfc822Date(value));
        }
    }

    private static void addStringListHeader(Request<?> request, String header, List<String> values) {
        if (values != null && !values.isEmpty()) {
            request.addHeader(header, ServiceUtils.join(values));
        }
    }

    private static void addResponseHeaderParameters(Request<?> request, ResponseHeaderOverrides responseHeaders) {
        if (responseHeaders != null) {
            if (responseHeaders.getCacheControl() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CACHE_CONTROL, responseHeaders.getCacheControl());
            }
            if (responseHeaders.getContentDisposition() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_DISPOSITION, responseHeaders.getContentDisposition());
            }
            if (responseHeaders.getContentEncoding() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_ENCODING, responseHeaders.getContentEncoding());
            }
            if (responseHeaders.getContentLanguage() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_LANGUAGE, responseHeaders.getContentLanguage());
            }
            if (responseHeaders.getContentType() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_TYPE, responseHeaders.getContentType());
            }
            if (responseHeaders.getExpires() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_EXPIRES, responseHeaders.getExpires());
            }
        }
    }

    public String getResourceUrl(String bucketName, String key) {
        try {
            return getUrl(bucketName, key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public URL getUrl(String bucketName, String key) {
        Request<?> request = new DefaultRequest<>(Constants.S3_SERVICE_NAME);
        configRequest(request, bucketName, key);
        return ServiceUtils.convertRequestToUrl(request);
    }

    public com.amazonaws.services.s3.model.Region getRegion() {
        String authority = this.endpoint.getAuthority();
        if (Constants.S3_HOSTNAME.equals(authority)) {
            return com.amazonaws.services.s3.model.Region.US_Standard;
        }
        Matcher m = com.amazonaws.services.s3.model.Region.S3_REGIONAL_ENDPOINT_PATTERN.matcher(authority);
        if (m.matches()) {
            return com.amazonaws.services.s3.model.Region.fromValue(m.group(1));
        }
        throw new IllegalStateException("S3 client with invalid S3 endpoint configured");
    }

    /* access modifiers changed from: protected */
    public <X extends AmazonWebServiceRequest> Request<X> createRequest(String bucketName, String key, X originalRequest, HttpMethodName httpMethod) {
        Request<X> request = new DefaultRequest<>(originalRequest, Constants.S3_SERVICE_NAME);
        request.setHttpMethod(httpMethod);
        configRequest(request, bucketName, key);
        return request;
    }

    private void configRequest(Request<?> request, String bucketName, String key) {
        String str;
        if (this.clientOptions.isAccelerateModeEnabled() && !(request.getOriginalRequest() instanceof S3AccelerateUnsupported) && BucketNameUtils.isDNSBucketName(bucketName)) {
            request.setEndpoint(URI.create(this.clientConfiguration.getProtocol() + "://" + bucketName + "." + Constants.S3_ACCELERATE_HOSTNAME));
            if (key == null || !key.startsWith(com.appsflyer.share.Constants.URL_PATH_DELIMITER)) {
                str = key;
            } else {
                str = com.appsflyer.share.Constants.URL_PATH_DELIMITER + key;
            }
            request.setResourcePath(str);
        } else if (this.clientOptions.isPathStyleAccess() || !BucketNameUtils.isDNSBucketName(bucketName) || validIP(this.endpoint.getHost())) {
            request.setEndpoint(this.endpoint);
            if (bucketName != null) {
                request.setResourcePath(bucketName + com.appsflyer.share.Constants.URL_PATH_DELIMITER + (key != null ? key : ""));
            }
        } else {
            request.setEndpoint(convertToVirtualHostEndpoint(bucketName));
            if (key != null && key.startsWith(com.appsflyer.share.Constants.URL_PATH_DELIMITER)) {
                key = com.appsflyer.share.Constants.URL_PATH_DELIMITER + key;
            }
            request.setResourcePath(key);
        }
    }

    private boolean validIP(String IP) {
        if (IP == null) {
            return false;
        }
        String[] tokens = IP.split("\\.");
        if (tokens.length != 4) {
            return false;
        }
        int length = tokens.length;
        int i = 0;
        while (i < length) {
            try {
                int tokenInt = Integer.parseInt(tokens[i]);
                if (tokenInt < 0 || tokenInt > 255) {
                    return false;
                }
                i++;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private <X, Y extends AmazonWebServiceRequest> X invoke(Request<Y> request, Unmarshaller<X, InputStream> unmarshaller, String bucketName, String key) {
        return invoke(request, new S3XmlResponseHandler(unmarshaller), bucketName, key);
    }

    /* access modifiers changed from: protected */
    public final ExecutionContext createExecutionContext(AmazonWebServiceRequest req) {
        return new S3ExecutionContext(this.requestHandler2s, isRequestMetricsEnabled(req) || isProfilingEnabled(), this);
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [com.amazonaws.Request, com.amazonaws.Request<Y>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <X, Y extends com.amazonaws.AmazonWebServiceRequest> X invoke(com.amazonaws.Request<Y> r8, com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonWebServiceResponse<X>> r9, java.lang.String r10, java.lang.String r11) {
        /*
            r7 = this;
            com.amazonaws.AmazonWebServiceRequest r3 = r8.getOriginalRequest()
            com.amazonaws.http.ExecutionContext r2 = r7.createExecutionContext(r3)
            com.amazonaws.util.AWSRequestMetrics r0 = r2.getAwsRequestMetrics()
            r8.setAWSRequestMetrics(r0)
            com.amazonaws.util.AWSRequestMetrics$Field r5 = com.amazonaws.util.AWSRequestMetrics.Field.ClientExecuteTime
            r0.startEvent((com.amazonaws.metrics.MetricType) r5)
            r4 = 0
            int r5 = r7.timeOffset     // Catch:{ all -> 0x0057 }
            r8.setTimeOffset(r5)     // Catch:{ all -> 0x0057 }
            java.util.Map r5 = r8.getHeaders()     // Catch:{ all -> 0x0057 }
            java.lang.String r6 = "Content-Type"
            boolean r5 = r5.containsKey(r6)     // Catch:{ all -> 0x0057 }
            if (r5 != 0) goto L_0x002d
            java.lang.String r5 = "Content-Type"
            java.lang.String r6 = "application/x-www-form-urlencoded; charset=utf-8"
            r8.addHeader(r5, r6)     // Catch:{ all -> 0x0057 }
        L_0x002d:
            com.amazonaws.auth.AWSCredentialsProvider r5 = r7.awsCredentialsProvider     // Catch:{ all -> 0x0057 }
            com.amazonaws.auth.AWSCredentials r1 = r5.getCredentials()     // Catch:{ all -> 0x0057 }
            com.amazonaws.auth.AWSCredentials r5 = r3.getRequestCredentials()     // Catch:{ all -> 0x0057 }
            if (r5 == 0) goto L_0x003d
            com.amazonaws.auth.AWSCredentials r1 = r3.getRequestCredentials()     // Catch:{ all -> 0x0057 }
        L_0x003d:
            com.amazonaws.auth.Signer r5 = r7.createSigner(r8, r10, r11)     // Catch:{ all -> 0x0057 }
            r2.setSigner(r5)     // Catch:{ all -> 0x0057 }
            r2.setCredentials(r1)     // Catch:{ all -> 0x0057 }
            com.amazonaws.http.AmazonHttpClient r5 = r7.client     // Catch:{ all -> 0x0057 }
            com.amazonaws.services.s3.internal.S3ErrorResponseHandler r6 = r7.errorResponseHandler     // Catch:{ all -> 0x0057 }
            com.amazonaws.Response r4 = r5.execute(r8, r9, r6, r2)     // Catch:{ all -> 0x0057 }
            java.lang.Object r5 = r4.getAwsResponse()     // Catch:{ all -> 0x0057 }
            r7.endClientExecution(r0, r8, r4)
            return r5
        L_0x0057:
            r5 = move-exception
            r7.endClientExecution(r0, r8, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.services.s3.AmazonS3Client.invoke(com.amazonaws.Request, com.amazonaws.http.HttpResponseHandler, java.lang.String, java.lang.String):java.lang.Object");
    }

    public void enableRequesterPays(String bucketName) {
        setBucketRequestPayment(new SetRequestPaymentConfigurationRequest(bucketName, new RequestPaymentConfiguration(RequestPaymentConfiguration.Payer.Requester)));
    }

    public void disableRequesterPays(String bucketName) {
        setBucketRequestPayment(new SetRequestPaymentConfigurationRequest(bucketName, new RequestPaymentConfiguration(RequestPaymentConfiguration.Payer.BucketOwner)));
    }

    public boolean isRequesterPaysEnabled(String bucketName) {
        return getBucketRequestPayment(new GetRequestPaymentConfigurationRequest(bucketName)).getPayer() == RequestPaymentConfiguration.Payer.Requester;
    }

    private void setBucketRequestPayment(SetRequestPaymentConfigurationRequest setRequestPaymentConfigurationRequest) {
        String bucketName = setRequestPaymentConfigurationRequest.getBucketName();
        RequestPaymentConfiguration configuration = setRequestPaymentConfigurationRequest.getConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified while setting the Requester Pays.");
        assertParameterNotNull(configuration, "The request payment configuration parameter must be specified when setting the Requester Pays.");
        Request<SetRequestPaymentConfigurationRequest> request = createRequest(bucketName, (String) null, setRequestPaymentConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("requestPayment", (String) null);
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        byte[] bytes = requestPaymentConfigurationXmlFactory.convertToXmlByteArray(configuration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.setContent(new ByteArrayInputStream(bytes));
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }

    private RequestPaymentConfiguration getBucketRequestPayment(GetRequestPaymentConfigurationRequest getRequestPaymentConfigurationRequest) {
        String bucketName = getRequestPaymentConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified while getting the Request Payment Configuration.");
        Request<GetRequestPaymentConfigurationRequest> request = createRequest(bucketName, (String) null, getRequestPaymentConfigurationRequest, HttpMethodName.GET);
        request.addParameter("requestPayment", (String) null);
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        return (RequestPaymentConfiguration) invoke(request, new Unmarshallers.RequestPaymentConfigurationUnmarshaller(), bucketName, (String) null);
    }

    private void setZeroContentLength(Request<?> req) {
        req.addHeader("Content-Length", String.valueOf(0));
    }

    private ByteArrayInputStream toByteArray(InputStream is) {
        byte[] buf = new byte[262144];
        int len = 0;
        int available = 262144;
        while (available > 0) {
            try {
                int read = is.read(buf, len, available);
                if (read == -1) {
                    break;
                }
                len += read;
                available -= read;
            } catch (IOException ioe) {
                throw new AmazonClientException("Failed to read from inputstream", ioe);
            }
        }
        if (is.read() != -1) {
            throw new AmazonClientException("Input stream exceeds 256k buffer.");
        }
        is.close();
        return new ByteArrayInputStream(buf, 0, len);
    }

    public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration) throws AmazonServiceException, AmazonClientException {
        setBucketReplicationConfiguration(new SetBucketReplicationConfigurationRequest(bucketName, configuration));
    }

    public void setBucketReplicationConfiguration(SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        assertParameterNotNull(setBucketReplicationConfigurationRequest, "The set bucket replication configuration request object must be specified.");
        String bucketName = setBucketReplicationConfigurationRequest.getBucketName();
        BucketReplicationConfiguration bucketReplicationConfiguration = setBucketReplicationConfigurationRequest.getReplicationConfiguration();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when setting replication configuration.");
        assertParameterNotNull(bucketReplicationConfiguration, "The replication configuration parameter must be specified when setting replication configuration.");
        Request<SetBucketReplicationConfigurationRequest> request = createRequest(bucketName, (String) null, setBucketReplicationConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("replication", (String) null);
        byte[] bytes = bucketConfigurationXmlFactory.convertToXmlByteArray(bucketReplicationConfiguration);
        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.addHeader("Content-Type", Mimetypes.MIMETYPE_XML);
        request.setContent(new ByteArrayInputStream(bytes));
        try {
            request.addHeader(Headers.CONTENT_MD5, BinaryUtils.toBase64(Md5Utils.computeMD5Hash(bytes)));
            invoke(request, this.voidResponseHandler, bucketName, (String) null);
        } catch (Exception e) {
            throw new AmazonClientException("Not able to compute MD5 of the replication rule configuration. Exception Message : " + e.getMessage(), e);
        }
    }

    public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        return getBucketReplicationConfiguration(new GetBucketReplicationConfigurationRequest(bucketName));
    }

    public BucketReplicationConfiguration getBucketReplicationConfiguration(GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        assertParameterNotNull(getBucketReplicationConfigurationRequest, "The bucket request parameter must be specified when retrieving replication configuration");
        String bucketName = getBucketReplicationConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket request must specify a bucket name when retrieving replication configuration");
        Request<GetBucketReplicationConfigurationRequest> request = createRequest(bucketName, (String) null, getBucketReplicationConfigurationRequest, HttpMethodName.GET);
        request.addParameter("replication", (String) null);
        return (BucketReplicationConfiguration) invoke(request, new Unmarshallers.BucketReplicationConfigurationUnmarshaller(), bucketName, (String) null);
    }

    public void deleteBucketReplicationConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        deleteBucketReplicationConfiguration(new DeleteBucketReplicationConfigurationRequest(bucketName));
    }

    public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        String bucketName = deleteBucketReplicationConfigurationRequest.getBucketName();
        assertParameterNotNull(bucketName, "The bucket name parameter must be specified when deleting replication configuration");
        Request<DeleteBucketReplicationConfigurationRequest> request = createRequest(bucketName, (String) null, deleteBucketReplicationConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("replication", (String) null);
        invoke(request, this.voidResponseHandler, bucketName, (String) null);
    }
}
