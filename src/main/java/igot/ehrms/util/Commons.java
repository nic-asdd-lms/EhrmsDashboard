package igot.ehrms.util;

import igot.ehrms.model.metricsApiRequestBody.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Commons {
     public static Map<String,Object> buildRequestBody() throws ParseException {
        Map<String, String> authToken = new HashMap<>();
        authToken.put("authToken", "null");

        Map<String, Object> requestInfo = new HashMap<>();
        requestInfo.put("RequestInfo", authToken);

        Map<String, String> tenantId = new HashMap<>();
        tenantId.put("tenantId", "null");

        Map<String, Object> headers = new HashMap<>();
        headers.put("headers", tenantId);

        Map<String, Object> requestDate = new HashMap<>();
        requestDate.put(Constants.START_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(Constants.FROM_DATE).getTime());
        requestDate.put(Constants.END_DATE, System.currentTimeMillis());

        Map<String, Object> aggregationRequestMap = new HashMap<>();
        aggregationRequestMap.put(Constants.QUERY_TYPE, "");
        aggregationRequestMap.put(Constants.MODULE_LEVEL, "");
        aggregationRequestMap.put(Constants.AGGREGATION_FACTORS, null);
        aggregationRequestMap.put(Constants.REQUEST_DATE, requestDate);

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("RequestInfo", authToken);
        requestBody.put("headers",tenantId);
        requestBody.put("aggregationRequestDto",aggregationRequestMap);

        RequestBody request = new RequestBody();
        request.setRequestInfo(requestInfo);
        request.setHeaders(headers);
        request.setAggregationRequestDto(aggregationRequestMap);

        return requestBody;

    }
}
