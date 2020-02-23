package com.amazonaws.services.s3.model.transform;

import com.amazonaws.services.s3.internal.DeleteObjectsResponse;
import com.amazonaws.services.s3.model.AccessControlList;
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
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.RequestPaymentConfiguration;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser;
import com.amazonaws.transform.Unmarshaller;
import java.io.InputStream;
import java.util.List;

public class Unmarshallers {

    public static final class ListBucketsUnmarshaller implements Unmarshaller<List<Bucket>, InputStream> {
        public List<Bucket> unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListMyBucketsResponse(in).getBuckets();
        }
    }

    public static final class ListBucketsOwnerUnmarshaller implements Unmarshaller<Owner, InputStream> {
        public Owner unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListMyBucketsResponse(in).getOwner();
        }
    }

    public static final class ListObjectsUnmarshaller implements Unmarshaller<ObjectListing, InputStream> {
        public ObjectListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListBucketObjectsResponse(in).getObjectListing();
        }
    }

    public static final class ListObjectsV2Unmarshaller implements Unmarshaller<ListObjectsV2Result, InputStream> {
        public ListObjectsV2Result unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListObjectsV2Response(in).getResult();
        }
    }

    public static final class VersionListUnmarshaller implements Unmarshaller<VersionListing, InputStream> {
        public VersionListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListVersionsResponse(in).getListing();
        }
    }

    public static final class AccessControlListUnmarshaller implements Unmarshaller<AccessControlList, InputStream> {
        public AccessControlList unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseAccessControlListResponse(in).getAccessControlList();
        }
    }

    public static final class BucketLoggingConfigurationnmarshaller implements Unmarshaller<BucketLoggingConfiguration, InputStream> {
        public BucketLoggingConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseLoggingStatusResponse(in).getBucketLoggingConfiguration();
        }
    }

    public static final class BucketLocationUnmarshaller implements Unmarshaller<String, InputStream> {
        public String unmarshall(InputStream in) throws Exception {
            String location = new XmlResponsesSaxParser().parseBucketLocationResponse(in);
            if (location == null) {
                return "US";
            }
            return location;
        }
    }

    public static final class BucketVersioningConfigurationUnmarshaller implements Unmarshaller<BucketVersioningConfiguration, InputStream> {
        public BucketVersioningConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseVersioningConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketWebsiteConfigurationUnmarshaller implements Unmarshaller<BucketWebsiteConfiguration, InputStream> {
        public BucketWebsiteConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseWebsiteConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketReplicationConfigurationUnmarshaller implements Unmarshaller<BucketReplicationConfiguration, InputStream> {
        public BucketReplicationConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseReplicationConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketNotificationConfigurationUnmarshaller implements Unmarshaller<BucketNotificationConfiguration, InputStream> {
        public BucketNotificationConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseNotificationConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketTaggingConfigurationUnmarshaller implements Unmarshaller<BucketTaggingConfiguration, InputStream> {
        public BucketTaggingConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseTaggingConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketAccelerateConfigurationUnmarshaller implements Unmarshaller<BucketAccelerateConfiguration, InputStream> {
        public BucketAccelerateConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseAccelerateConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class InputStreamUnmarshaller implements Unmarshaller<InputStream, InputStream> {
        public InputStream unmarshall(InputStream in) throws Exception {
            return in;
        }
    }

    public static final class CopyObjectUnmarshaller implements Unmarshaller<XmlResponsesSaxParser.CopyObjectResultHandler, InputStream> {
        public XmlResponsesSaxParser.CopyObjectResultHandler unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseCopyObjectResponse(in);
        }
    }

    public static final class CompleteMultipartUploadResultUnmarshaller implements Unmarshaller<XmlResponsesSaxParser.CompleteMultipartUploadHandler, InputStream> {
        public XmlResponsesSaxParser.CompleteMultipartUploadHandler unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseCompleteMultipartUploadResponse(in);
        }
    }

    public static final class InitiateMultipartUploadResultUnmarshaller implements Unmarshaller<InitiateMultipartUploadResult, InputStream> {
        public InitiateMultipartUploadResult unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseInitiateMultipartUploadResponse(in).getInitiateMultipartUploadResult();
        }
    }

    public static final class ListMultipartUploadsResultUnmarshaller implements Unmarshaller<MultipartUploadListing, InputStream> {
        public MultipartUploadListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListMultipartUploadsResponse(in).getListMultipartUploadsResult();
        }
    }

    public static final class ListPartsResultUnmarshaller implements Unmarshaller<PartListing, InputStream> {
        public PartListing unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseListPartsResponse(in).getListPartsResult();
        }
    }

    public static final class DeleteObjectsResultUnmarshaller implements Unmarshaller<DeleteObjectsResponse, InputStream> {
        public DeleteObjectsResponse unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseDeletedObjectsResult(in).getDeleteObjectResult();
        }
    }

    public static final class BucketLifecycleConfigurationUnmarshaller implements Unmarshaller<BucketLifecycleConfiguration, InputStream> {
        public BucketLifecycleConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketLifecycleConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class BucketCrossOriginConfigurationUnmarshaller implements Unmarshaller<BucketCrossOriginConfiguration, InputStream> {
        public BucketCrossOriginConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseBucketCrossOriginConfigurationResponse(in).getConfiguration();
        }
    }

    public static final class RequestPaymentConfigurationUnmarshaller implements Unmarshaller<RequestPaymentConfiguration, InputStream> {
        public RequestPaymentConfiguration unmarshall(InputStream in) throws Exception {
            return new XmlResponsesSaxParser().parseRequestPaymentConfigurationResponse(in).getConfiguration();
        }
    }
}
