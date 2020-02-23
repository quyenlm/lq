package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.transform.Unmarshaller;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class ResponseHeaderHandlerChain<T> extends S3XmlResponseHandler<T> {
    private final List<HeaderHandler<T>> headerHandlers;

    public ResponseHeaderHandlerChain(Unmarshaller<T, InputStream> responseUnmarshaller, HeaderHandler<T>... headerHandlers2) {
        super(responseUnmarshaller);
        this.headerHandlers = Arrays.asList(headerHandlers2);
    }

    public AmazonWebServiceResponse<T> handle(HttpResponse response) throws Exception {
        AmazonWebServiceResponse<T> awsResponse = super.handle(response);
        T result = awsResponse.getResult();
        if (result != null) {
            for (HeaderHandler<T> handler : this.headerHandlers) {
                handler.handle(result, response);
            }
        }
        return awsResponse;
    }
}
