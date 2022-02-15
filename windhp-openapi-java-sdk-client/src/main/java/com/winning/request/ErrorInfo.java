package com.winning.request;

/**
 * @author xch
 * @date 2022/1/12 15:57
 */
public class ErrorInfo extends Response{
    private int statusCode;
    private String errorCode;
    private String errorMessage;
    private String requestId;
    private String traceId;
    private String errorDescription;

    /**
     * @param response
     */
    public ErrorInfo(okhttp3.Response response) {
        super(response);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorInfo{");
        sb.append("statusCode=").append(statusCode);
        sb.append(", errorCode='").append(errorCode).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", traceId='").append(traceId).append('\'');
        sb.append(", errorDescription='").append(errorDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
