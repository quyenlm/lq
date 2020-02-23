package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.http.ExecutionContext;
import java.net.URI;
import java.util.List;

public class S3ExecutionContext extends ExecutionContext {
    private Signer signer;

    public S3ExecutionContext(List<RequestHandler2> requestHandler2s, boolean isMetricEnabled, AmazonWebServiceClient awsClient) {
        super(requestHandler2s, isMetricEnabled, awsClient);
    }

    public void setSigner(Signer signer2) {
        this.signer = signer2;
    }

    public Signer getSignerByURI(URI uri) {
        return this.signer;
    }
}
