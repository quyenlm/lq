package com.amazonaws.services.dynamodbv2;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import java.util.List;
import java.util.Map;

public interface AmazonDynamoDB {
    BatchGetItemResult batchGetItem(BatchGetItemRequest batchGetItemRequest) throws AmazonClientException, AmazonServiceException;

    BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> map) throws AmazonClientException, AmazonServiceException;

    BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> map, String str) throws AmazonClientException, AmazonServiceException;

    BatchWriteItemResult batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) throws AmazonClientException, AmazonServiceException;

    BatchWriteItemResult batchWriteItem(Map<String, List<WriteRequest>> map) throws AmazonClientException, AmazonServiceException;

    CreateTableResult createTable(CreateTableRequest createTableRequest) throws AmazonClientException, AmazonServiceException;

    CreateTableResult createTable(List<AttributeDefinition> list, String str, List<KeySchemaElement> list2, ProvisionedThroughput provisionedThroughput) throws AmazonClientException, AmazonServiceException;

    DeleteItemResult deleteItem(DeleteItemRequest deleteItemRequest) throws AmazonClientException, AmazonServiceException;

    DeleteItemResult deleteItem(String str, Map<String, AttributeValue> map) throws AmazonClientException, AmazonServiceException;

    DeleteItemResult deleteItem(String str, Map<String, AttributeValue> map, String str2) throws AmazonClientException, AmazonServiceException;

    DeleteTableResult deleteTable(DeleteTableRequest deleteTableRequest) throws AmazonClientException, AmazonServiceException;

    DeleteTableResult deleteTable(String str) throws AmazonClientException, AmazonServiceException;

    DescribeLimitsResult describeLimits(DescribeLimitsRequest describeLimitsRequest) throws AmazonClientException, AmazonServiceException;

    DescribeTableResult describeTable(DescribeTableRequest describeTableRequest) throws AmazonClientException, AmazonServiceException;

    DescribeTableResult describeTable(String str) throws AmazonClientException, AmazonServiceException;

    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    GetItemResult getItem(GetItemRequest getItemRequest) throws AmazonClientException, AmazonServiceException;

    GetItemResult getItem(String str, Map<String, AttributeValue> map) throws AmazonClientException, AmazonServiceException;

    GetItemResult getItem(String str, Map<String, AttributeValue> map, Boolean bool) throws AmazonClientException, AmazonServiceException;

    ListTablesResult listTables() throws AmazonClientException, AmazonServiceException;

    ListTablesResult listTables(ListTablesRequest listTablesRequest) throws AmazonClientException, AmazonServiceException;

    ListTablesResult listTables(Integer num) throws AmazonClientException, AmazonServiceException;

    ListTablesResult listTables(String str) throws AmazonClientException, AmazonServiceException;

    ListTablesResult listTables(String str, Integer num) throws AmazonClientException, AmazonServiceException;

    PutItemResult putItem(PutItemRequest putItemRequest) throws AmazonClientException, AmazonServiceException;

    PutItemResult putItem(String str, Map<String, AttributeValue> map) throws AmazonClientException, AmazonServiceException;

    PutItemResult putItem(String str, Map<String, AttributeValue> map, String str2) throws AmazonClientException, AmazonServiceException;

    QueryResult query(QueryRequest queryRequest) throws AmazonClientException, AmazonServiceException;

    ScanResult scan(ScanRequest scanRequest) throws AmazonClientException, AmazonServiceException;

    ScanResult scan(String str, List<String> list) throws AmazonClientException, AmazonServiceException;

    ScanResult scan(String str, List<String> list, Map<String, Condition> map) throws AmazonClientException, AmazonServiceException;

    ScanResult scan(String str, Map<String, Condition> map) throws AmazonClientException, AmazonServiceException;

    void setEndpoint(String str) throws IllegalArgumentException;

    void setRegion(Region region) throws IllegalArgumentException;

    void shutdown();

    UpdateItemResult updateItem(UpdateItemRequest updateItemRequest) throws AmazonClientException, AmazonServiceException;

    UpdateItemResult updateItem(String str, Map<String, AttributeValue> map, Map<String, AttributeValueUpdate> map2) throws AmazonClientException, AmazonServiceException;

    UpdateItemResult updateItem(String str, Map<String, AttributeValue> map, Map<String, AttributeValueUpdate> map2, String str2) throws AmazonClientException, AmazonServiceException;

    UpdateTableResult updateTable(UpdateTableRequest updateTableRequest) throws AmazonClientException, AmazonServiceException;

    UpdateTableResult updateTable(String str, ProvisionedThroughput provisionedThroughput) throws AmazonClientException, AmazonServiceException;
}
