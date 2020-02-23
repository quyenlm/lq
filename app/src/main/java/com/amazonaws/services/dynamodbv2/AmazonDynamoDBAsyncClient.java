package com.amazonaws.services.dynamodbv2;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AmazonDynamoDBAsyncClient extends AmazonDynamoDBClient implements AmazonDynamoDBAsync {
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;
    private ExecutorService executorService;

    @Deprecated
    public AmazonDynamoDBAsyncClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain());
    }

    @Deprecated
    public AmazonDynamoDBAsyncClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration, Executors.newFixedThreadPool(clientConfiguration.getMaxConnections()));
    }

    public AmazonDynamoDBAsyncClient(AWSCredentials awsCredentials) {
        this(awsCredentials, Executors.newFixedThreadPool(10));
    }

    public AmazonDynamoDBAsyncClient(AWSCredentials awsCredentials, ExecutorService executorService2) {
        super(awsCredentials);
        this.executorService = executorService2;
    }

    public AmazonDynamoDBAsyncClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration, ExecutorService executorService2) {
        super(awsCredentials, clientConfiguration);
        this.executorService = executorService2;
    }

    public AmazonDynamoDBAsyncClient(AWSCredentialsProvider awsCredentialsProvider) {
        this(awsCredentialsProvider, Executors.newFixedThreadPool(10));
    }

    public AmazonDynamoDBAsyncClient(AWSCredentialsProvider awsCredentialsProvider, ExecutorService executorService2) {
        this(awsCredentialsProvider, new ClientConfiguration(), executorService2);
    }

    public AmazonDynamoDBAsyncClient(AWSCredentialsProvider awsCredentialsProvider, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider, clientConfiguration, Executors.newFixedThreadPool(clientConfiguration.getMaxConnections()));
    }

    public AmazonDynamoDBAsyncClient(AWSCredentialsProvider awsCredentialsProvider, ClientConfiguration clientConfiguration, ExecutorService executorService2) {
        super(awsCredentialsProvider, clientConfiguration);
        this.executorService = executorService2;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void shutdown() {
        super.shutdown();
        this.executorService.shutdownNow();
    }

    public Future<BatchGetItemResult> batchGetItemAsync(final BatchGetItemRequest batchGetItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<BatchGetItemResult>() {
            public BatchGetItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.batchGetItem(batchGetItemRequest);
            }
        });
    }

    public Future<BatchGetItemResult> batchGetItemAsync(final BatchGetItemRequest batchGetItemRequest, final AsyncHandler<BatchGetItemRequest, BatchGetItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<BatchGetItemResult>() {
            public BatchGetItemResult call() throws Exception {
                try {
                    BatchGetItemResult result = AmazonDynamoDBAsyncClient.this.batchGetItem(batchGetItemRequest);
                    asyncHandler.onSuccess(batchGetItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<BatchWriteItemResult> batchWriteItemAsync(final BatchWriteItemRequest batchWriteItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<BatchWriteItemResult>() {
            public BatchWriteItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.batchWriteItem(batchWriteItemRequest);
            }
        });
    }

    public Future<BatchWriteItemResult> batchWriteItemAsync(final BatchWriteItemRequest batchWriteItemRequest, final AsyncHandler<BatchWriteItemRequest, BatchWriteItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<BatchWriteItemResult>() {
            public BatchWriteItemResult call() throws Exception {
                try {
                    BatchWriteItemResult result = AmazonDynamoDBAsyncClient.this.batchWriteItem(batchWriteItemRequest);
                    asyncHandler.onSuccess(batchWriteItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<CreateTableResult> createTableAsync(final CreateTableRequest createTableRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<CreateTableResult>() {
            public CreateTableResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.createTable(createTableRequest);
            }
        });
    }

    public Future<CreateTableResult> createTableAsync(final CreateTableRequest createTableRequest, final AsyncHandler<CreateTableRequest, CreateTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<CreateTableResult>() {
            public CreateTableResult call() throws Exception {
                try {
                    CreateTableResult result = AmazonDynamoDBAsyncClient.this.createTable(createTableRequest);
                    asyncHandler.onSuccess(createTableRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<DeleteItemResult> deleteItemAsync(final DeleteItemRequest deleteItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DeleteItemResult>() {
            public DeleteItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.deleteItem(deleteItemRequest);
            }
        });
    }

    public Future<DeleteItemResult> deleteItemAsync(final DeleteItemRequest deleteItemRequest, final AsyncHandler<DeleteItemRequest, DeleteItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DeleteItemResult>() {
            public DeleteItemResult call() throws Exception {
                try {
                    DeleteItemResult result = AmazonDynamoDBAsyncClient.this.deleteItem(deleteItemRequest);
                    asyncHandler.onSuccess(deleteItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<DeleteTableResult> deleteTableAsync(final DeleteTableRequest deleteTableRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DeleteTableResult>() {
            public DeleteTableResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.deleteTable(deleteTableRequest);
            }
        });
    }

    public Future<DeleteTableResult> deleteTableAsync(final DeleteTableRequest deleteTableRequest, final AsyncHandler<DeleteTableRequest, DeleteTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DeleteTableResult>() {
            public DeleteTableResult call() throws Exception {
                try {
                    DeleteTableResult result = AmazonDynamoDBAsyncClient.this.deleteTable(deleteTableRequest);
                    asyncHandler.onSuccess(deleteTableRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<DescribeLimitsResult> describeLimitsAsync(final DescribeLimitsRequest describeLimitsRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DescribeLimitsResult>() {
            public DescribeLimitsResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.describeLimits(describeLimitsRequest);
            }
        });
    }

    public Future<DescribeLimitsResult> describeLimitsAsync(final DescribeLimitsRequest describeLimitsRequest, final AsyncHandler<DescribeLimitsRequest, DescribeLimitsResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DescribeLimitsResult>() {
            public DescribeLimitsResult call() throws Exception {
                try {
                    DescribeLimitsResult result = AmazonDynamoDBAsyncClient.this.describeLimits(describeLimitsRequest);
                    asyncHandler.onSuccess(describeLimitsRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<DescribeTableResult> describeTableAsync(final DescribeTableRequest describeTableRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DescribeTableResult>() {
            public DescribeTableResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.describeTable(describeTableRequest);
            }
        });
    }

    public Future<DescribeTableResult> describeTableAsync(final DescribeTableRequest describeTableRequest, final AsyncHandler<DescribeTableRequest, DescribeTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<DescribeTableResult>() {
            public DescribeTableResult call() throws Exception {
                try {
                    DescribeTableResult result = AmazonDynamoDBAsyncClient.this.describeTable(describeTableRequest);
                    asyncHandler.onSuccess(describeTableRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<GetItemResult> getItemAsync(final GetItemRequest getItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<GetItemResult>() {
            public GetItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.getItem(getItemRequest);
            }
        });
    }

    public Future<GetItemResult> getItemAsync(final GetItemRequest getItemRequest, final AsyncHandler<GetItemRequest, GetItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<GetItemResult>() {
            public GetItemResult call() throws Exception {
                try {
                    GetItemResult result = AmazonDynamoDBAsyncClient.this.getItem(getItemRequest);
                    asyncHandler.onSuccess(getItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<ListTablesResult> listTablesAsync(final ListTablesRequest listTablesRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<ListTablesResult>() {
            public ListTablesResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.listTables(listTablesRequest);
            }
        });
    }

    public Future<ListTablesResult> listTablesAsync(final ListTablesRequest listTablesRequest, final AsyncHandler<ListTablesRequest, ListTablesResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<ListTablesResult>() {
            public ListTablesResult call() throws Exception {
                try {
                    ListTablesResult result = AmazonDynamoDBAsyncClient.this.listTables(listTablesRequest);
                    asyncHandler.onSuccess(listTablesRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<PutItemResult> putItemAsync(final PutItemRequest putItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<PutItemResult>() {
            public PutItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.putItem(putItemRequest);
            }
        });
    }

    public Future<PutItemResult> putItemAsync(final PutItemRequest putItemRequest, final AsyncHandler<PutItemRequest, PutItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<PutItemResult>() {
            public PutItemResult call() throws Exception {
                try {
                    PutItemResult result = AmazonDynamoDBAsyncClient.this.putItem(putItemRequest);
                    asyncHandler.onSuccess(putItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<QueryResult> queryAsync(final QueryRequest queryRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<QueryResult>() {
            public QueryResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.query(queryRequest);
            }
        });
    }

    public Future<QueryResult> queryAsync(final QueryRequest queryRequest, final AsyncHandler<QueryRequest, QueryResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<QueryResult>() {
            public QueryResult call() throws Exception {
                try {
                    QueryResult result = AmazonDynamoDBAsyncClient.this.query(queryRequest);
                    asyncHandler.onSuccess(queryRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<ScanResult> scanAsync(final ScanRequest scanRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<ScanResult>() {
            public ScanResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.scan(scanRequest);
            }
        });
    }

    public Future<ScanResult> scanAsync(final ScanRequest scanRequest, final AsyncHandler<ScanRequest, ScanResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<ScanResult>() {
            public ScanResult call() throws Exception {
                try {
                    ScanResult result = AmazonDynamoDBAsyncClient.this.scan(scanRequest);
                    asyncHandler.onSuccess(scanRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<UpdateItemResult> updateItemAsync(final UpdateItemRequest updateItemRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<UpdateItemResult>() {
            public UpdateItemResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.updateItem(updateItemRequest);
            }
        });
    }

    public Future<UpdateItemResult> updateItemAsync(final UpdateItemRequest updateItemRequest, final AsyncHandler<UpdateItemRequest, UpdateItemResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<UpdateItemResult>() {
            public UpdateItemResult call() throws Exception {
                try {
                    UpdateItemResult result = AmazonDynamoDBAsyncClient.this.updateItem(updateItemRequest);
                    asyncHandler.onSuccess(updateItemRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }

    public Future<UpdateTableResult> updateTableAsync(final UpdateTableRequest updateTableRequest) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<UpdateTableResult>() {
            public UpdateTableResult call() throws Exception {
                return AmazonDynamoDBAsyncClient.this.updateTable(updateTableRequest);
            }
        });
    }

    public Future<UpdateTableResult> updateTableAsync(final UpdateTableRequest updateTableRequest, final AsyncHandler<UpdateTableRequest, UpdateTableResult> asyncHandler) throws AmazonServiceException, AmazonClientException {
        return this.executorService.submit(new Callable<UpdateTableResult>() {
            public UpdateTableResult call() throws Exception {
                try {
                    UpdateTableResult result = AmazonDynamoDBAsyncClient.this.updateTable(updateTableRequest);
                    asyncHandler.onSuccess(updateTableRequest, result);
                    return result;
                } catch (Exception ex) {
                    asyncHandler.onError(ex);
                    throw ex;
                }
            }
        });
    }
}
