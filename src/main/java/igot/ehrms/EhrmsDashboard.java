package igot.ehrms;

import java.io.*;

import java.util.*;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import igot.ehrms.model.metricsApiResponse.MetricsResponse;
import igot.ehrms.model.metricsApiResponse.Response;
import igot.ehrms.model.orgListApi.OrgListApiResponse;
import igot.ehrms.model.orgListApi.OrgListApiResultContent;
import igot.ehrms.util.Commons;
import igot.ehrms.util.Constants;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.slf4j.LoggerFactory;

public class EhrmsDashboard {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ApiCalls.class);

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        Map<String, Object> response = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String token = ApiCalls.login();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("deptList.json");

            Object obj = parser.parse(new InputStreamReader(is));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray departments = (JSONArray) jsonObject.get("deptList");
            var iterator = departments.iterator();

            while (iterator.hasNext()) {
                String deptId = iterator.next().toString();

                logger.info("Fetching child organisations of : " + deptId);
                OrgListApiResponse orgList = ApiCalls.getOrgList(deptId);

                List<Response> data = getMetrics(orgList.getResult().getResponse().getContent(), token);
                response.put(deptId, data);
            }

            JSONObject responseObject = new JSONObject(response);
            String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObject);

            logger.info("Writing result to file");
            FileWriter writer = new FileWriter(Constants.RESPONSE_PATH);
            writer.write(str);
            writer.close();

        } catch (Exception e) {
            logger.error("ERROR: ", e);
        }

    }

    private static List<Response> getMetrics(List<OrgListApiResultContent> orgList, String accessToken)
            throws IOException, ParseException, InterruptedException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Constants.METADATA));
        JSONObject jsonObject = (JSONObject) obj;
        
        List<Response> results = new ArrayList<>();
        String dashboardUrl = Constants.PORTAL_URL + jsonObject.get("url")+ Constants.METRICS_PATH;
        Map<String, Object> requestBody = Commons.buildRequestBody();

        String metricsToken = "Bearer " + jsonObject.get("metricsToken").toString();

        for (OrgListApiResultContent org : orgList) {
            if (org.getSbOrgId() != null) {
                logger.info("Extracting metrics for : '" + org.getOrgName() + '\'');
                MetricsResponse registeredUsers = ApiCalls.getRegisteredUsers(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse activeUsers = ApiCalls.getActiveUsers(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse courseEnrolments = ApiCalls.getCourseEnrolments(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse courseCompletions = ApiCalls.getCourseCompletions(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse coursesPublished = ApiCalls.getCoursesPublished(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse dailyTime = ApiCalls.getDailyTime(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse topCourses = ApiCalls.getTopCourses(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);
                MetricsResponse topUsers = ApiCalls.getTopUsers(org.getSbOrgId(), dashboardUrl, requestBody, accessToken, metricsToken);

                List<MetricsResponse> data = new ArrayList<>();
                data.add(registeredUsers);
                data.add(activeUsers);
                data.add(courseEnrolments);
                data.add(courseCompletions);
                data.add(coursesPublished);
                data.add(dailyTime);
                data.add(topCourses);
                data.add(topUsers);
                results.add(new Response(org.getSbOrgId(), org.getOrgName(), data));
            }

        }

        return results;
    }

}