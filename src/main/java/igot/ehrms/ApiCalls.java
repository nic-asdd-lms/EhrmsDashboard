package igot.ehrms;

import igot.ehrms.model.metricsApiResponse.MetricsResponse;
import igot.ehrms.model.orgListApi.OrgListApiResponse;
import igot.ehrms.util.Constants;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiCalls {

    private static final Logger logger = LoggerFactory.getLogger(ApiCalls.class);

    static String login(String config) throws IOException, InterruptedException, ParseException {
        JSONParser parser = new JSONParser();
        logger.info("Reading file : " + config);
        FileReader reader = new FileReader(config);
        
        // Object obj = parser.parse(new FileReader(Constants.METADATA));
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        String authUrl = Constants.PORTAL_URL + jsonObject.get("url") + Constants.AUTH_PATH;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", "lms");
        parameters.put("client_secret", jsonObject.get("password").toString());
        parameters.put("grant_type", "client_credentials");

        String form = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        // String reqBody = "client_id=lms&client_secret=" + jsonObject.get("password")
        // + "@&grant_type=client_credentials";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authUrl))
                .header(Constants.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Constants.AUTHORIZATION, "Bearer " + jsonObject.get("loginToken").toString())
                .POST(HttpRequest.BodyPublishers.ofString(form))
                // .method("POST", HttpRequest.BodyPublishers.ofString(reqBody))
                .build();

        HttpResponse<String> response = null;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        logger.info("Authenticated");

        parser = new JSONParser();
        obj = parser.parse(response.body());
        jsonObject = (JSONObject) obj;
        String token = jsonObject.get("access_token").toString();

        return token;
    }

    static OrgListApiResponse getOrgList(String parentOrgId, String config)
            throws IOException, InterruptedException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(config));
        // Object obj = parser.parse(new FileReader(Constants.METADATA));
        JSONObject jsonObject = (JSONObject) obj;

        String orgListUrl = Constants.SPV_URL + jsonObject.get("url") + Constants.ORG_LIST_PATH + parentOrgId;
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(orgListUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        Integer noOfRetries = 5;
        while (noOfRetries > 0 && (response == null || response.statusCode() == 502)) {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            noOfRetries--;

        }
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        logger.info("Org list API response : " + response.body());

        obj = parser.parse(response.body());
        jsonObject = (JSONObject) obj;

        OrgListApiResponse orgList = mapper.convertValue(jsonObject, OrgListApiResponse.class);

        return orgList;
    }

    public static MetricsResponse getRegisteredUsers(String orgId, String dashboardUrl, Map<String, Object> requestBody,
            String accessToken, String metricsToken) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_REGISTERED_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put(Constants.AGGREGATION_REQUEST, aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "registeredUsers");

    }

    public static MetricsResponse getActiveUsers(String orgId, String dashboardUrl, Map<String, Object> requestBody,
            String accessToken, String metricsToken) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_ACTIVE_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "activeUsers");

    }

    public static MetricsResponse getCourseEnrolments(String orgId, String dashboardUrl,
            Map<String, Object> requestBody, String accessToken, String metricsToken)
            throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSE_ENROLMENTS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "courseEnrolments");

    }

    public static MetricsResponse getCourseCompletions(String orgId, String dashboardUrl,
            Map<String, Object> requestBody, String accessToken, String metricsToken)
            throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSE_COMPLETIONS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "courseCompletions");

    }

    public static MetricsResponse getCoursesPublished(String orgId, String dashboardUrl,
            Map<String, Object> requestBody, String accessToken, String metricsToken)
            throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_COURSES_PUBLISHED);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "coursesPublished");

    }

    public static MetricsResponse getDailyTime(String orgId, String dashboardUrl, Map<String, Object> requestBody,
            String accessToken, String metricsToken) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_DAILY_TIME);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_DAILY_TIME);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_DAILY_TIME);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "dailyTimeSpent");

    }

    public static MetricsResponse getTopCourses(String orgId, String dashboardUrl, Map<String, Object> requestBody,
            String accessToken, String metricsToken) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_TOP_COURSES);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_TOP_COURSES);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_TOP_COURSES);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "topCourses");

    }

    public static MetricsResponse getTopUsers(String orgId, String dashboardUrl, Map<String, Object> requestBody,
            String accessToken, String metricsToken) throws IOException, InterruptedException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> mdoFilter = new HashMap<>();
        mdoFilter.put(Constants.MDO_FILTER, orgId);

        Map<String, Object> aggregationRequestMap = mapper.convertValue(requestBody.get("aggregationRequestDto"),
                Map.class);
        aggregationRequestMap.put(Constants.DASHBOARD_ID, Constants.DASHBOARD_ID_TOP_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_TYPE, Constants.VISUALISATION_TYPE_TOP_USERS);
        aggregationRequestMap.put(Constants.VISUALIZATION_CODE, Constants.VISUALISATION_CODE_TOP_USERS);
        aggregationRequestMap.put(Constants.FILTERS, mdoFilter);

        requestBody.put("aggregationRequestDto", aggregationRequestMap);

        Map<String, Object> requestMap = mapper.convertValue(requestBody, Map.class);
        String reqBodyData = new ObjectMapper().writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(dashboardUrl))
                .header(Constants.CONTENT_TYPE, "application/json")
                .header(Constants.ACCEPT, "application/json")
                .header(Constants.X_AUTH_TOKEN, accessToken)
                .header(Constants.AUTHORIZATION, metricsToken)
                .header(Constants.X_CHANNEL_ID, orgId)
                .method("POST", HttpRequest.BodyPublishers.ofString(reqBodyData))
                .build();

        return getMetricsResponse(request, "topUsers");

    }

    private static MetricsResponse getMetricsResponse(HttpRequest request, String metricType)
            throws IOException, InterruptedException, ParseException {

ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = null;

        Integer noOfRetries = 5;
        while (noOfRetries > 0 && (response == null || response.statusCode() == 502)) {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            noOfRetries--;
        }

        logger.info("Metrics API response : " + response.body());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        JSONObject jsonObject = (JSONObject) obj;

        MetricsResponse metricsResponse = mapper.convertValue(jsonObject, MetricsResponse.class);
        // metricsResponse.setMetric(metricType);
        return metricsResponse;
               
        
    }

}
