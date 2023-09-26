package igot.ehrms.model.metricsApiResponse;

public class MetricsResponse {
    private String metric;
    private StatusInfo statusInfo;
    private ResponseData responseData;
    
    public String getMetric() {
        return metric;
    }
    public void setMetric(String metric) {
        this.metric = metric;
    }
    public StatusInfo getStatusInfo() {
        return statusInfo;
    }
    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }
    public ResponseData getResponseData() {
        return responseData;
    }
    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }
}
