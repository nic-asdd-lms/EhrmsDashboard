package igot.ehrms.model.metricsApiRequestBody;

import java.util.Map;

public class RequestBody {
    private Map<String, Object> requestInfo;
    private Map<String, Object> headers;
    private Map<String, Object> aggregationRequestDto;
    
    public Map<String, Object> getRequestInfo() {
        return requestInfo;
    }
    public void setRequestInfo(Map<String, Object> requestInfo) {
        this.requestInfo = requestInfo;
    }
    public Map<String, Object> getHeaders() {
        return headers;
    }
    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }
    public Map<String, Object> getAggregationRequestDto() {
        return aggregationRequestDto;
    }
    public void setAggregationRequestDto(Map<String, Object> aggregationRequestDto) {
        this.aggregationRequestDto = aggregationRequestDto;
    }
    
}
