package igot.ehrms;

import igot.ehrms.model.metricsApiResponse.MetricsResponse;
import igot.ehrms.model.orgListApi.OrgListApiResponse;
import igot.ehrms.util.Commons;
import igot.ehrms.util.Constants;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiCalls {

    static OrgListApiResponse getOrgList(String parentOrgId) throws IOException, InterruptedException, ParseException {
        String orgListUrl = Constants.SPV_URL + Constants.ORG_LIST_PATH + parentOrgId;
        ObjectMapper mapper  = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(orgListUrl))
                .header("Content-Type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        OrgListApiResponse orgList = mapper.convertValue(jsonObject, OrgListApiResponse.class);

        return orgList;
    }

    public static MetricsResponse getRegisteredUsers(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getActiveUsers(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getCourseEnrolments(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getCourseCompletions(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getCoursesPublished(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getDailyTime(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_DAILY_TIME);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_DAILY_TIME);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_DAILY_TIME);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getTopCourses(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_TOP_COURSES);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_TOP_COURSES);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_TOP_COURSES);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }

    public static MetricsResponse getTopUsers(String orgId, String dashboardUrl, Map<String,Object> requestBody ) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper  = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"), Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_TOP_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_TOP_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_TOP_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String,Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie","")
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject)obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);

        return metricsResponse;

    }



}
