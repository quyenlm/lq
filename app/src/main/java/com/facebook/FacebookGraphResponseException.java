package com.facebook;

public class FacebookGraphResponseException extends FacebookException {
    private final GraphResponse graphResponse;

    public FacebookGraphResponseException(GraphResponse graphResponse2, String errorMessage) {
        super(errorMessage);
        this.graphResponse = graphResponse2;
    }

    public final GraphResponse getGraphResponse() {
        return this.graphResponse;
    }

    public final String toString() {
        FacebookRequestError requestError = this.graphResponse != null ? this.graphResponse.getError() : null;
        StringBuilder errorStringBuilder = new StringBuilder().append("{FacebookGraphResponseException: ");
        String message = getMessage();
        if (message != null) {
            errorStringBuilder.append(message);
            errorStringBuilder.append(" ");
        }
        if (requestError != null) {
            errorStringBuilder.append("httpResponseCode: ").append(requestError.getRequestStatusCode()).append(", facebookErrorCode: ").append(requestError.getErrorCode()).append(", facebookErrorType: ").append(requestError.getErrorType()).append(", message: ").append(requestError.getErrorMessage()).append("}");
        }
        return errorStringBuilder.toString();
    }
}
