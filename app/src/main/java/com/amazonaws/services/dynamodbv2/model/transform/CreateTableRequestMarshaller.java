package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.List;

public class CreateTableRequestMarshaller implements Marshaller<Request<CreateTableRequest>, CreateTableRequest> {
    public Request<CreateTableRequest> marshall(CreateTableRequest createTableRequest) {
        if (createTableRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(CreateTableRequest)");
        }
        Request<CreateTableRequest> request = new DefaultRequest<>(createTableRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.CreateTable");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (createTableRequest.getAttributeDefinitions() != null) {
                List<AttributeDefinition> attributeDefinitions = createTableRequest.getAttributeDefinitions();
                jsonWriter.name("AttributeDefinitions");
                jsonWriter.beginArray();
                for (AttributeDefinition attributeDefinitionsItem : attributeDefinitions) {
                    if (attributeDefinitionsItem != null) {
                        AttributeDefinitionJsonMarshaller.getInstance().marshall(attributeDefinitionsItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (createTableRequest.getTableName() != null) {
                String tableName = createTableRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (createTableRequest.getKeySchema() != null) {
                List<KeySchemaElement> keySchema = createTableRequest.getKeySchema();
                jsonWriter.name("KeySchema");
                jsonWriter.beginArray();
                for (KeySchemaElement keySchemaItem : keySchema) {
                    if (keySchemaItem != null) {
                        KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (createTableRequest.getLocalSecondaryIndexes() != null) {
                List<LocalSecondaryIndex> localSecondaryIndexes = createTableRequest.getLocalSecondaryIndexes();
                jsonWriter.name("LocalSecondaryIndexes");
                jsonWriter.beginArray();
                for (LocalSecondaryIndex localSecondaryIndexesItem : localSecondaryIndexes) {
                    if (localSecondaryIndexesItem != null) {
                        LocalSecondaryIndexJsonMarshaller.getInstance().marshall(localSecondaryIndexesItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (createTableRequest.getGlobalSecondaryIndexes() != null) {
                List<GlobalSecondaryIndex> globalSecondaryIndexes = createTableRequest.getGlobalSecondaryIndexes();
                jsonWriter.name("GlobalSecondaryIndexes");
                jsonWriter.beginArray();
                for (GlobalSecondaryIndex globalSecondaryIndexesItem : globalSecondaryIndexes) {
                    if (globalSecondaryIndexesItem != null) {
                        GlobalSecondaryIndexJsonMarshaller.getInstance().marshall(globalSecondaryIndexesItem, jsonWriter);
                    }
                }
                jsonWriter.endArray();
            }
            if (createTableRequest.getProvisionedThroughput() != null) {
                ProvisionedThroughput provisionedThroughput = createTableRequest.getProvisionedThroughput();
                jsonWriter.name("ProvisionedThroughput");
                ProvisionedThroughputJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
            }
            if (createTableRequest.getStreamSpecification() != null) {
                StreamSpecification streamSpecification = createTableRequest.getStreamSpecification();
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
