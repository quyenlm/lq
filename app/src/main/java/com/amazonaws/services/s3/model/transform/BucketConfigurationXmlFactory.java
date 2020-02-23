package com.amazonaws.services.s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.services.s3.internal.XmlWriter;
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
import com.amazonaws.services.s3.model.RedirectRule;
import com.amazonaws.services.s3.model.ReplicationDestinationConfig;
import com.amazonaws.services.s3.model.ReplicationRule;
import com.amazonaws.services.s3.model.RoutingRule;
import com.amazonaws.services.s3.model.RoutingRuleCondition;
import com.amazonaws.services.s3.model.TagSet;
import java.util.Map;

public class BucketConfigurationXmlFactory {
    public byte[] convertToXmlByteArray(BucketVersioningConfiguration versioningConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("VersioningConfiguration", "xmlns", Constants.XML_NAMESPACE);
        xml.start("Status").value(versioningConfiguration.getStatus()).end();
        Boolean mfaDeleteEnabled = versioningConfiguration.isMfaDeleteEnabled();
        if (mfaDeleteEnabled != null) {
            if (mfaDeleteEnabled.booleanValue()) {
                xml.start("MfaDelete").value("Enabled").end();
            } else {
                xml.start("MfaDelete").value(BucketLifecycleConfiguration.DISABLED).end();
            }
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketAccelerateConfiguration accelerateConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("AccelerateConfiguration", "xmlns", Constants.XML_NAMESPACE);
        xml.start("Status").value(accelerateConfiguration.getStatus()).end();
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketLoggingConfiguration loggingConfiguration) {
        if (loggingConfiguration.getLogFilePrefix() == null) {
        }
        XmlWriter xml = new XmlWriter();
        xml.start("BucketLoggingStatus", "xmlns", Constants.XML_NAMESPACE);
        if (loggingConfiguration.isLoggingEnabled()) {
            xml.start("LoggingEnabled");
            xml.start("TargetBucket").value(loggingConfiguration.getDestinationBucketName()).end();
            xml.start("TargetPrefix").value(loggingConfiguration.getLogFilePrefix()).end();
            xml.end();
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketNotificationConfiguration notificationConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("NotificationConfiguration", "xmlns", Constants.XML_NAMESPACE);
        for (BucketNotificationConfiguration.TopicConfiguration topicConfiguration : notificationConfiguration.getTopicConfigurations()) {
            xml.start("TopicConfiguration");
            xml.start("Topic").value(topicConfiguration.getTopic()).end();
            xml.start("Event").value(topicConfiguration.getEvent()).end();
            xml.end();
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketReplicationConfiguration replicationConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("ReplicationConfiguration");
        Map<String, ReplicationRule> rules = replicationConfiguration.getRules();
        xml.start("Role").value(replicationConfiguration.getRoleARN()).end();
        for (Map.Entry<String, ReplicationRule> entry : rules.entrySet()) {
            ReplicationRule rule = entry.getValue();
            xml.start("Rule");
            xml.start("ID").value(entry.getKey()).end();
            xml.start("Prefix").value(rule.getPrefix()).end();
            xml.start("Status").value(rule.getStatus()).end();
            ReplicationDestinationConfig config = rule.getDestinationConfig();
            xml.start("Destination");
            xml.start("Bucket").value(config.getBucketARN()).end();
            if (config.getStorageClass() != null) {
                xml.start("StorageClass").value(config.getStorageClass()).end();
            }
            xml.end();
            xml.end();
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketWebsiteConfiguration websiteConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("WebsiteConfiguration", "xmlns", Constants.XML_NAMESPACE);
        if (websiteConfiguration.getIndexDocumentSuffix() != null) {
            XmlWriter indexDocumentElement = xml.start("IndexDocument");
            indexDocumentElement.start("Suffix").value(websiteConfiguration.getIndexDocumentSuffix()).end();
            indexDocumentElement.end();
        }
        if (websiteConfiguration.getErrorDocument() != null) {
            XmlWriter errorDocumentElement = xml.start("ErrorDocument");
            errorDocumentElement.start("Key").value(websiteConfiguration.getErrorDocument()).end();
            errorDocumentElement.end();
        }
        RedirectRule redirectAllRequestsTo = websiteConfiguration.getRedirectAllRequestsTo();
        if (redirectAllRequestsTo != null) {
            XmlWriter redirectAllRequestsElement = xml.start("RedirectAllRequestsTo");
            if (redirectAllRequestsTo.getprotocol() != null) {
                xml.start("Protocol").value(redirectAllRequestsTo.getprotocol()).end();
            }
            if (redirectAllRequestsTo.getHostName() != null) {
                xml.start("HostName").value(redirectAllRequestsTo.getHostName()).end();
            }
            if (redirectAllRequestsTo.getReplaceKeyPrefixWith() != null) {
                xml.start("ReplaceKeyPrefixWith").value(redirectAllRequestsTo.getReplaceKeyPrefixWith()).end();
            }
            if (redirectAllRequestsTo.getReplaceKeyWith() != null) {
                xml.start("ReplaceKeyWith").value(redirectAllRequestsTo.getReplaceKeyWith()).end();
            }
            redirectAllRequestsElement.end();
        }
        if (websiteConfiguration.getRoutingRules() != null && websiteConfiguration.getRoutingRules().size() > 0) {
            XmlWriter routingRules = xml.start("RoutingRules");
            for (RoutingRule rule : websiteConfiguration.getRoutingRules()) {
                writeRule(routingRules, rule);
            }
            routingRules.end();
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketLifecycleConfiguration config) throws AmazonClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("LifecycleConfiguration");
        for (BucketLifecycleConfiguration.Rule rule : config.getRules()) {
            writeRule(xml, rule);
        }
        xml.end();
        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketCrossOriginConfiguration config) throws AmazonClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("CORSConfiguration", "xmlns", Constants.XML_NAMESPACE);
        for (CORSRule rule : config.getRules()) {
            writeRule(xml, rule);
        }
        xml.end();
        return xml.getBytes();
    }

    private void writeRule(XmlWriter xml, BucketLifecycleConfiguration.Rule rule) {
        xml.start("Rule");
        if (rule.getId() != null) {
            xml.start("ID").value(rule.getId()).end();
        }
        xml.start("Prefix").value(rule.getPrefix()).end();
        xml.start("Status").value(rule.getStatus()).end();
        BucketLifecycleConfiguration.Transition transition = rule.getTransition();
        if (transition != null) {
            xml.start("Transition");
            if (transition.getDate() != null) {
                xml.start("Date");
                xml.value(ServiceUtils.formatIso8601Date(transition.getDate()));
                xml.end();
            }
            if (transition.getDays() != -1) {
                xml.start("Days");
                xml.value(Integer.toString(transition.getDays()));
                xml.end();
            }
            xml.start("StorageClass");
            xml.value(transition.getStorageClass().toString());
            xml.end();
            xml.end();
        }
        BucketLifecycleConfiguration.NoncurrentVersionTransition ncvTransition = rule.getNoncurrentVersionTransition();
        if (ncvTransition != null) {
            xml.start("NoncurrentVersionTransition");
            if (ncvTransition.getDays() != -1) {
                xml.start("NoncurrentDays");
                xml.value(Integer.toString(ncvTransition.getDays()));
                xml.end();
            }
            xml.start("StorageClass");
            xml.value(ncvTransition.getStorageClass().toString());
            xml.end();
            xml.end();
        }
        if (rule.getExpirationInDays() != -1) {
            xml.start("Expiration");
            xml.start("Days").value("" + rule.getExpirationInDays()).end();
            xml.end();
        }
        if (rule.getNoncurrentVersionExpirationInDays() != -1) {
            xml.start("NoncurrentVersionExpiration");
            xml.start("NoncurrentDays").value(Integer.toString(rule.getNoncurrentVersionExpirationInDays())).end();
            xml.end();
        }
        if (rule.getExpirationDate() != null) {
            xml.start("Expiration");
            xml.start("Date").value(ServiceUtils.formatIso8601Date(rule.getExpirationDate())).end();
            xml.end();
        }
        xml.end();
    }

    private void writeRule(XmlWriter xml, CORSRule rule) {
        xml.start("CORSRule");
        if (rule.getId() != null) {
            xml.start("ID").value(rule.getId()).end();
        }
        if (rule.getAllowedOrigins() != null) {
            for (String origin : rule.getAllowedOrigins()) {
                xml.start("AllowedOrigin").value(origin).end();
            }
        }
        if (rule.getAllowedMethods() != null) {
            for (CORSRule.AllowedMethods method : rule.getAllowedMethods()) {
                xml.start("AllowedMethod").value(method.toString()).end();
            }
        }
        if (rule.getMaxAgeSeconds() != 0) {
            xml.start("MaxAgeSeconds").value(Integer.toString(rule.getMaxAgeSeconds())).end();
        }
        if (rule.getExposedHeaders() != null) {
            for (String header : rule.getExposedHeaders()) {
                xml.start("ExposeHeader").value(header).end();
            }
        }
        if (rule.getAllowedHeaders() != null) {
            for (String header2 : rule.getAllowedHeaders()) {
                xml.start("AllowedHeader").value(header2).end();
            }
        }
        xml.end();
    }

    private void writeRule(XmlWriter xml, RoutingRule rule) {
        xml.start("RoutingRule");
        RoutingRuleCondition condition = rule.getCondition();
        if (condition != null) {
            xml.start(JsonDocumentFields.CONDITION);
            xml.start("KeyPrefixEquals");
            if (condition.getKeyPrefixEquals() != null) {
                xml.value(condition.getKeyPrefixEquals());
            }
            xml.end();
            if (condition.getHttpErrorCodeReturnedEquals() != null) {
                xml.start("HttpErrorCodeReturnedEquals ").value(condition.getHttpErrorCodeReturnedEquals()).end();
            }
            xml.end();
        }
        xml.start("Redirect");
        RedirectRule redirect = rule.getRedirect();
        if (redirect != null) {
            if (redirect.getprotocol() != null) {
                xml.start("Protocol").value(redirect.getprotocol()).end();
            }
            if (redirect.getHostName() != null) {
                xml.start("HostName").value(redirect.getHostName()).end();
            }
            if (redirect.getReplaceKeyPrefixWith() != null) {
                xml.start("ReplaceKeyPrefixWith").value(redirect.getReplaceKeyPrefixWith()).end();
            }
            if (redirect.getReplaceKeyWith() != null) {
                xml.start("ReplaceKeyWith").value(redirect.getReplaceKeyWith()).end();
            }
            if (redirect.getHttpRedirectCode() != null) {
                xml.start("HttpRedirectCode").value(redirect.getHttpRedirectCode()).end();
            }
        }
        xml.end();
        xml.end();
    }

    public byte[] convertToXmlByteArray(BucketTaggingConfiguration config) throws AmazonClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("Tagging");
        for (TagSet tagset : config.getAllTagSets()) {
            writeRule(xml, tagset);
        }
        xml.end();
        return xml.getBytes();
    }

    private void writeRule(XmlWriter xml, TagSet tagset) {
        xml.start("TagSet");
        for (String key : tagset.getAllTags().keySet()) {
            xml.start("Tag");
            xml.start("Key").value(key).end();
            xml.start("Value").value(tagset.getTag(key)).end();
            xml.end();
        }
        xml.end();
    }
}
