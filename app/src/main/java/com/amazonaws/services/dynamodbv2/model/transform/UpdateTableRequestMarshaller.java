package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.List;

public class UpdateTableRequestMarshaller implements Marshaller<Request<UpdateTableRequest>, UpdateTableRequest> {
    public Request<UpdateTableRequest> marshall(UpdateTableRequest updateTableRequest) {
        if (updateTableRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateTableRequest)");
        }
        Request<UpdateTableRequest> request = new DefaultRequest<>(updateTableRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.UpdateTable");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (updateTableRequest.getAttributeDefinitions() != null) {
                List<AttributeDefinition> attributeDefinitions = updateTableRequest.getAttributeDefinitions();
                jsonWriter.name("AttributeDefinitions");
                jsonWriter.beginArray();
                for (AttributeDefinition attributeDefinitionsItem : attributeDefinitions) {
                    if (attributeDefinitionsItem != null) {
                        AttributeDefinitionJsonMarshaller.getInstance().marshall(attributeDefinitionsItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (updateTableRequest.getTableName() != null) {
                String tableName = updateTableRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (updateTableRequest.getProvisionedThroughput() != null) {
                ProvisionedThroughput provisionedThroughput = updateTableRequest.getProvisionedThroughput();
                jsonWriter.name("ProvisionedThroughput");
                ProvisionedThroughputJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
            }
            if (updateTableRequest.getGlobalSecondaryIndexUpdates() != null) {
                List<GlobalSecondaryIndexUpdate> globalSecondaryIndexUpdates = updateTableRequest.getGlobalSecondaryIndexUpdates();
                jsonWriter.name("GlobalSecondaryIndexUpdates");
                jsonWriter.beginArray();
                for (GlobalSecondaryIndexUpdate globalSecondaryIndexUpdatesItem : globalSecondaryIndexUpdates) {
                    if (globalSecondaryIndexUpdatesItem != null) {
                        GlobalSecondaryIndexUpdateJsonMarshaller.getInstance().marshall(globalSecondaryIndexUpdatesItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (updateTableRequest.getStreamSpecification() != null) {
                StreamSpecification streamSpecification = updateTableRequest.getStreamSpecification();
                jsonWriter.name("StreamSpecification");
                StreamSpecificationJsonMarshaller.getInstance().marshall(streamSpecification, jsonWriter);
            }
            jsonWriter.endObject();
            jsonWriter.close();
            String snippet = stringWriter.toString();
            byte[] content = snippet.getBytes(StringUtils.UTF8);
            request.setContent(new StringInputStream(snippet));
            request.addHeader("Content-Length", Integer.toString(content.length));
            if (!request.getHeaders().containsKey("Content-Type")) {
                request.addHeader("Content-Type", "application/x-amz-json-1.0");
            }
            return request;
        } catch (Throwable t) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
        }
    }
}
