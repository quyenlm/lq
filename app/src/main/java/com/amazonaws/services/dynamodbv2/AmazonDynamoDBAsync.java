package com.amazonaws.services.dynamodbv2;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
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
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
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
import java.util.concurrent.Future;

public interface AmazonDynamoDBAsync extends AmazonDynamoDB {
    Future<BatchGetItemResult> batchGetItemAsync(BatchGetItemRequest batchGetItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<BatchGetItemResult> batchGetItemAsync(BatchGetItemRequest batchGetItemRequest, AsyncHandler<BatchGetItemRequest, BatchGetItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<BatchWriteItemResult> batchWriteItemAsync(BatchWriteItemRequest batchWriteItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<BatchWriteItemResult> batchWriteItemAsync(BatchWriteItemRequest batchWriteItemRequest, AsyncHandler<BatchWriteItemRequest, BatchWriteItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<CreateTableResult> createTableAsync(CreateTableRequest createTableRequest) throws AmazonServiceException, AmazonClientException;

    Future<CreateTableResult> createTableAsync(CreateTableRequest createTableRequest, AsyncHandler<CreateTableRequest, CreateTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<DeleteItemResult> deleteItemAsync(DeleteItemRequest deleteItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<DeleteItemResult> deleteItemAsync(DeleteItemRequest deleteItemRequest, AsyncHandler<DeleteItemRequest, DeleteItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<DeleteTableResult> deleteTableAsync(DeleteTableRequest deleteTableRequest) throws AmazonServiceException, AmazonClientException;

    Future<DeleteTableResult> deleteTableAsync(DeleteTableRequest deleteTableRequest, AsyncHandler<DeleteTableRequest, DeleteTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<DescribeLimitsResult> describeLimitsAsync(DescribeLimitsRequest describeLimitsRequest) throws AmazonServiceException, AmazonClientException;

    Future<DescribeLimitsResult> describeLimitsAsync(DescribeLimitsRequest describeLimitsRequest, AsyncHandler<DescribeLimitsRequest, DescribeLimitsResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<DescribeTableResult> describeTableAsync(DescribeTableRequest describeTableRequest) throws AmazonServiceException, AmazonClientException;

    Future<DescribeTableResult> describeTableAsync(DescribeTableRequest describeTableRequest, AsyncHandler<DescribeTableRequest, DescribeTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<GetItemResult> getItemAsync(GetItemRequest getItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<GetItemResult> getItemAsync(GetItemRequest getItemRequest, AsyncHandler<GetItemRequest, GetItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<ListTablesResult> listTablesAsync(ListTablesRequest listTablesRequest) throws AmazonServiceException, AmazonClientException;

    Future<ListTablesResult> listTablesAsync(ListTablesRequest listTablesRequest, AsyncHandler<ListTablesRequest, ListTablesResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<PutItemResult> putItemAsync(PutItemRequest putItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<PutItemResult> putItemAsync(PutItemRequest putItemRequest, AsyncHandler<PutItemRequest, PutItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<QueryResult> queryAsync(QueryRequest queryRequest) throws AmazonServiceException, AmazonClientException;

    Future<QueryResult> queryAsync(QueryRequest queryRequest, AsyncHandler<QueryRequest, QueryResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<ScanResult> scanAsync(ScanRequest scanRequest) throws AmazonServiceException, AmazonClientException;

    Future<ScanResult> scanAsync(ScanRequest scanRequest, AsyncHandler<ScanRequest, ScanResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<UpdateItemResult> updateItemAsync(UpdateItemRequest updateItemRequest) throws AmazonServiceException, AmazonClientException;

    Future<UpdateItemResult> updateItemAsync(UpdateItemRequest updateItemRequest, AsyncHandler<UpdateItemRequest, UpdateItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException;

    Future<UpdateTableResult> updateTableAsync(UpdateTableRequest updateTableRequest) throws AmazonServiceException, AmazonClientException;

    Future<UpdateTableResult> updateTableAsync(UpdateTableRequest updateTableRequest, AsyncHandler<UpdateTableRequest, UpdateTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException;
}
