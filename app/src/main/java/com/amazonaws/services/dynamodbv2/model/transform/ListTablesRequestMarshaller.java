package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;

public class ListTablesRequestMarshaller implements Marshaller<Request<ListTablesRequest>, ListTablesRequest> {
    public Request<ListTablesRequest> marshall(ListTablesRequest listTablesRequest) {
        if (listTablesRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(ListTablesRequest)");
        }
        Request<ListTablesRequest> request = new DefaultRequest<>(listTablesRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.ListTables");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (listTablesRequest.getExclusiveStartTableName() != null) {
                String exclusiveStartTableName = listTablesRequest.getExclusiveStartTableName();
                jsonWriter.name("ExclusiveStartTableName");
                jsonWriter.value(exclusiveStartTableName);
            }
            if (listTablesRequest.getLimit() != null) {
                Integer limit = listTablesRequest.getLimit();
                jsonWriter.name("Limit");
                jsonWriter.value((Number) limit);
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
