package com.amazonaws.services.dynamodbv2;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.PredefinedRetryPolicies;
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
import com.amazonaws.services.dynamodbv2.model.transform.BatchGetItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.BatchGetItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.BatchWriteItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.BatchWriteItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ConditionalCheckFailedExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.CreateTableRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.CreateTableResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DeleteItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DeleteItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DeleteTableRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DeleteTableResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DescribeLimitsRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DescribeLimitsResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DescribeTableRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.DescribeTableResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.GetItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.GetItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.InternalServerErrorExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ItemCollectionSizeLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.LimitExceededExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ListTablesRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ListTablesResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ProvisionedThroughputExceededExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.PutItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.PutItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.QueryRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.QueryResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ResourceInUseExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ScanRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.ScanResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.UpdateItemRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.UpdateItemResultJsonUnmarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.UpdateTableRequestMarshaller;
import com.amazonaws.services.dynamodbv2.model.transform.UpdateTableResultJsonUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AmazonDynamoDBClient extends AmazonWebServiceClient implements AmazonDynamoDB {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    @Deprecated
    public AmazonDynamoDBClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AmazonDynamoDBClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AmazonDynamoDBClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AmazonDynamoDBClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AmazonDynamoDBClient(AWSCredentialsProvider awsCredentialsProvider2) {
        this(awsCredentialsProvider2, new ClientConfiguration());
    }

    public AmazonDynamoDBClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider2, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonDynamoDBClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    public AmazonDynamoDBClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    private void init() {
        this.jsonErrorUnmarshallers = new ArrayList();
        this.jsonErrorUnmarshallers.add(new ConditionalCheckFailedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InternalServerErrorExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ItemCollectionSizeLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new LimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ProvisionedThroughputExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceInUseExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("dynamodb.us-east-1.amazonaws.com");
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/dynamodbv2/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/dynamodbv2/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        ClientConfiguration clientConfiguration = orig;
        ClientConfiguration config = new ClientConfiguration(orig);
        if (config.getRetryPolicy() == PredefinedRetryPolicies.DEFAULT) {
            config.setRetryPolicy(PredefinedRetryPolicies.DYNAMODB_DEFAULT);
        }
        return config;
    }

    public BatchGetItemResult batchGetItem(BatchGetItemRequest batchGetItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) batchGetItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<BatchGetItemRequest> request = null;
        Response<BatchGetItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new BatchGetItemRequestMarshaller().marshall(batchGetItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new BatchGetItemResultJsonUnmarshaller()), executionContext);
            BatchGetItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public BatchWriteItemResult batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) batchWriteItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<BatchWriteItemRequest> request = null;
        Response<BatchWriteItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new BatchWriteItemRequestMarshaller().marshall(batchWriteItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new BatchWriteItemResultJsonUnmarshaller()), executionContext);
            BatchWriteItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public CreateTableResult createTable(CreateTableRequest createTableRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) createTableRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<CreateTableRequest> request = null;
        Response<CreateTableResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new CreateTableRequestMarshaller().marshall(createTableRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new CreateTableResultJsonUnmarshaller()), executionContext);
            CreateTableResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DeleteItemResult deleteItem(DeleteItemRequest deleteItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<DeleteItemRequest> request = null;
        Response<DeleteItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new DeleteItemRequestMarshaller().marshall(deleteItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new DeleteItemResultJsonUnmarshaller()), executionContext);
            DeleteItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DeleteTableResult deleteTable(DeleteTableRequest deleteTableRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteTableRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<DeleteTableRequest> request = null;
        Response<DeleteTableResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new DeleteTableRequestMarshaller().marshall(deleteTableRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new DeleteTableResultJsonUnmarshaller()), executionContext);
            DeleteTableResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DescribeLimitsResult describeLimits(DescribeLimitsRequest describeLimitsRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeLimitsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<DescribeLimitsRequest> request = null;
        Response<DescribeLimitsResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new DescribeLimitsRequestMarshaller().marshall(describeLimitsRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new DescribeLimitsResultJsonUnmarshaller()), executionContext);
            DescribeLimitsResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DescribeTableResult describeTable(DescribeTableRequest describeTableRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeTableRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<DescribeTableRequest> request = null;
        Response<DescribeTableResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new DescribeTableRequestMarshaller().marshall(describeTableRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new DescribeTableResultJsonUnmarshaller()), executionContext);
            DescribeTableResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetItemResult getItem(GetItemRequest getItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<GetItemRequest> request = null;
        Response<GetItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new GetItemRequestMarshaller().marshall(getItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new GetItemResultJsonUnmarshaller()), executionContext);
            GetItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListTablesResult listTables(ListTablesRequest listTablesRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listTablesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<ListTablesRequest> request = null;
        Response<ListTablesResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new ListTablesRequestMarshaller().marshall(listTablesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new ListTablesResultJsonUnmarshaller()), executionContext);
            ListTablesResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public PutItemResult putItem(PutItemRequest putItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) putItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<PutItemRequest> request = null;
        Response<PutItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new PutItemRequestMarshaller().marshall(putItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new PutItemResultJsonUnmarshaller()), executionContext);
            PutItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public QueryResult query(QueryRequest queryRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) queryRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<QueryRequest> request = null;
        Response<QueryResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new QueryRequestMarshaller().marshall(queryRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new QueryResultJsonUnmarshaller()), executionContext);
            QueryResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ScanResult scan(ScanRequest scanRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) scanRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<ScanRequest> request = null;
        Response<ScanResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new ScanRequestMarshaller().marshall(scanRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new ScanResultJsonUnmarshaller()), executionContext);
            ScanResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UpdateItemResult updateItem(UpdateItemRequest updateItemRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateItemRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<UpdateItemRequest> request = null;
        Response<UpdateItemResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new UpdateItemRequestMarshaller().marshall(updateItemRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new UpdateItemResultJsonUnmarshaller()), executionContext);
            UpdateItemResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UpdateTableResult updateTable(UpdateTableRequest updateTableRequest) throws AmazonServiceException, AmazonClientException {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateTableRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
        Request<UpdateTableRequest> request = null;
        Response<UpdateTableResult> response = null;
        try {
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            request = new UpdateTableRequestMarshaller().marshall(updateTableRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestMarshallTime);
            response = invoke(request, new JsonResponseHandler<>(new UpdateTableResultJsonUnmarshaller()), executionContext);
            UpdateTableResult awsResponse = response.getAwsResponse();
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public PutItemResult putItem(String tableName, Map<String, AttributeValue> item) throws AmazonServiceException, AmazonClientException {
        PutItemRequest putItemRequest = new PutItemRequest();
        putItemRequest.setTableName(tableName);
        putItemRequest.setItem(item);
        return putItem(putItemRequest);
    }

    public PutItemResult putItem(String tableName, Map<String, AttributeValue> item, String returnValues) throws AmazonServiceException, AmazonClientException {
        PutItemRequest putItemRequest = new PutItemRequest();
        putItemRequest.setTableName(tableName);
        putItemRequest.setItem(item);
        putItemRequest.setReturnValues(returnValues);
        return putItem(putItemRequest);
    }

    public UpdateItemResult updateItem(String tableName, Map<String, AttributeValue> key, Map<String, AttributeValueUpdate> attributeUpdates) throws AmazonServiceException, AmazonClientException {
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest.setTableName(tableName);
        updateItemRequest.setKey(key);
        updateItemRequest.setAttributeUpdates(attributeUpdates);
        return updateItem(updateItemRequest);
    }

    public UpdateItemResult updateItem(String tableName, Map<String, AttributeValue> key, Map<String, AttributeValueUpdate> attributeUpdates, String returnValues) throws AmazonServiceException, AmazonClientException {
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest.setTableName(tableName);
        updateItemRequest.setKey(key);
        updateItemRequest.setAttributeUpdates(attributeUpdates);
        updateItemRequest.setReturnValues(returnValues);
        return updateItem(updateItemRequest);
    }

    public DescribeTableResult describeTable(String tableName) throws AmazonServiceException, AmazonClientException {
        DescribeTableRequest describeTableRequest = new DescribeTableRequest();
        describeTableRequest.setTableName(tableName);
        return describeTable(describeTableRequest);
    }

    public ScanResult scan(String tableName, List<String> attributesToGet) throws AmazonServiceException, AmazonClientException {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setTableName(tableName);
        scanRequest.setAttributesToGet(attributesToGet);
        return scan(scanRequest);
    }

    public ScanResult scan(String tableName, Map<String, Condition> scanFilter) throws AmazonServiceException, AmazonClientException {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setTableName(tableName);
        scanRequest.setScanFilter(scanFilter);
        return scan(scanRequest);
    }

    public ScanResult scan(String tableName, List<String> attributesToGet, Map<String, Condition> scanFilter) throws AmazonServiceException, AmazonClientException {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setTableName(tableName);
        scanRequest.setAttributesToGet(attributesToGet);
        scanRequest.setScanFilter(scanFilter);
        return scan(scanRequest);
    }

    public DeleteItemResult deleteItem(String tableName, Map<String, AttributeValue> key) throws AmazonServiceException, AmazonClientException {
        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setTableName(tableName);
        deleteItemRequest.setKey(key);
        return deleteItem(deleteItemRequest);
    }

    public DeleteItemResult deleteItem(String tableName, Map<String, AttributeValue> key, String returnValues) throws AmazonServiceException, AmazonClientException {
        DeleteItemRequest deleteItemRequest = new DeleteItemRequest();
        deleteItemRequest.setTableName(tableName);
        deleteItemRequest.setKey(key);
        deleteItemRequest.setReturnValues(returnValues);
        return deleteItem(deleteItemRequest);
    }

    public DeleteTableResult deleteTable(String tableName) throws AmazonServiceException, AmazonClientException {
        DeleteTableRequest deleteTableRequest = new DeleteTableRequest();
        deleteTableRequest.setTableName(tableName);
        return deleteTable(deleteTableRequest);
    }

    public CreateTableResult createTable(List<AttributeDefinition> attributeDefinitions, String tableName, List<KeySchemaElement> keySchema, ProvisionedThroughput provisionedThroughput) throws AmazonServiceException, AmazonClientException {
        CreateTableRequest createTableRequest = new CreateTableRequest();
        createTableRequest.setAttributeDefinitions(attributeDefinitions);
        createTableRequest.setTableName(tableName);
        createTableRequest.setKeySchema(keySchema);
        createTableRequest.setProvisionedThroughput(provisionedThroughput);
        return createTable(createTableRequest);
    }

    public GetItemResult getItem(String tableName, Map<String, AttributeValue> key) throws AmazonServiceException, AmazonClientException {
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName(tableName);
        getItemRequest.setKey(key);
        return getItem(getItemRequest);
    }

    public GetItemResult getItem(String tableName, Map<String, AttributeValue> key, Boolean consistentRead) throws AmazonServiceException, AmazonClientException {
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName(tableName);
        getItemRequest.setKey(key);
        getItemRequest.setConsistentRead(consistentRead);
        return getItem(getItemRequest);
    }

    public ListTablesResult listTables() throws AmazonServiceException, AmazonClientException {
        return listTables(new ListTablesRequest());
    }

    public ListTablesResult listTables(String exclusiveStartTableName) throws AmazonServiceException, AmazonClientException {
        ListTablesRequest listTablesRequest = new ListTablesRequest();
        listTablesRequest.setExclusiveStartTableName(exclusiveStartTableName);
        return listTables(listTablesRequest);
    }

    public ListTablesResult listTables(String exclusiveStartTableName, Integer limit) throws AmazonServiceException, AmazonClientException {
        ListTablesRequest listTablesRequest = new ListTablesRequest();
        listTablesRequest.setExclusiveStartTableName(exclusiveStartTableName);
        listTablesRequest.setLimit(limit);
        return listTables(listTablesRequest);
    }

    public ListTablesResult listTables(Integer limit) throws AmazonServiceException, AmazonClientException {
        ListTablesRequest listTablesRequest = new ListTablesRequest();
        listTablesRequest.setLimit(limit);
        return listTables(listTablesRequest);
    }

    public UpdateTableResult updateTable(String tableName, ProvisionedThroughput provisionedThroughput) throws AmazonServiceException, AmazonClientException {
        UpdateTableRequest updateTableRequest = new UpdateTableRequest();
        updateTableRequest.setTableName(tableName);
        updateTableRequest.setProvisionedThroughput(provisionedThroughput);
        return updateTable(updateTableRequest);
    }

    public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> requestItems, String returnConsumedCapacity) throws AmazonServiceException, AmazonClientException {
        BatchGetItemRequest batchGetItemRequest = new BatchGetItemRequest();
        batchGetItemRequest.setRequestItems(requestItems);
        batchGetItemRequest.setReturnConsumedCapacity(returnConsumedCapacity);
        return batchGetItem(batchGetItemRequest);
    }

    public BatchGetItemResult batchGetItem(Map<String, KeysAndAttributes> requestItems) throws AmazonServiceException, AmazonClientException {
        BatchGetItemRequest batchGetItemRequest = new BatchGetItemRequest();
        batchGetItemRequest.setRequestItems(requestItems);
        return batchGetItem(batchGetItemRequest);
    }

    public BatchWriteItemResult batchWriteItem(Map<String, List<WriteRequest>> requestItems) throws AmazonServiceException, AmazonClientException {
        BatchWriteItemRequest batchWriteItemRequest = new BatchWriteItemRequest();
        batchWriteItemRequest.setRequestItems(requestItems);
        return batchWriteItem(batchWriteItemRequest);
    }

    @Deprecated
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return this.client.getResponseMetadataForRequest(request);
    }

    /* JADX INFO: finally extract failed */
    private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request, HttpResponseHandler<AmazonWebServiceResponse<X>> responseHandler, ExecutionContext executionContext) {
        request.setEndpoint(this.endpoint);
        request.setTimeOffset(this.timeOffset);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.CredentialsRequestTime);
        try {
            AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.CredentialsRequestTime);
            AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
            if (!(originalRequest == null || originalRequest.getRequestCredentials() == null)) {
                credentials = originalRequest.getRequestCredentials();
            }
            executionContext.setCredentials(credentials);
            return this.client.execute(request, responseHandler, new JsonErrorResponseHandler(this.jsonErrorUnmarshallers), executionContext);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.CredentialsRequestTime);
            throw th;
        }
    }
}
