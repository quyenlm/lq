package com.amazonaws.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.util.XpathUtils;
import org.w3c.dom.Node;

public class StandardErrorUnmarshaller extends AbstractErrorUnmarshaller<Node> {
    public StandardErrorUnmarshaller() {
    }

    protected StandardErrorUnmarshaller(Class<? extends AmazonServiceException> exceptionClass) {
        super(exceptionClass);
    }

    public AmazonServiceException unmarshall(Node in) throws Exception {
        String errorCode = parseErrorCode(in);
        String errorType = XpathUtils.asString("ErrorResponse/Error/Type", in);
        String requestId = XpathUtils.asString("ErrorResponse/RequestId", in);
        AmazonServiceException ase = newException(XpathUtils.asString("ErrorResponse/Error/Message", in));
        ase.setErrorCode(errorCode);
        ase.setRequestId(requestId);
        if (errorType == null) {
            ase.setErrorType(AmazonServiceException.ErrorType.Unknown);
        } else if (errorType.equalsIgnoreCase("Receiver")) {
            ase.setErrorType(AmazonServiceException.ErrorType.Service);
        } else if (errorType.equalsIgnoreCase("Sender")) {
            ase.setErrorType(AmazonServiceException.ErrorType.Client);
        }
        return ase;
    }

    public String parseErrorCode(Node in) throws Exception {
        return XpathUtils.asString("ErrorResponse/Error/Code", in);
    }

    public String getErrorPropertyPath(String property) {
        return "ErrorResponse/Error/" + property;
    }
}
