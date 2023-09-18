package igot.ehrms;

import java.io.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import igot.ehrms.model.metricsApiResponse.MetricsResponse;
import igot.ehrms.model.metricsApiResponse.Response;
import igot.ehrms.model.orgListApi.OrgListApiResponse;
import igot.ehrms.model.orgListApi.OrgListApiResultContent;
import igot.ehrms.util.Commons;
import igot.ehrms.util.Constants;

import org.json.simple.*;
import org.json.simple.parser.*;

public class EhrmsDashboard {
    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        Map<String, Object> response = new HashMap<>();
        ObjectMapper mapper  = new ObjectMapper();

        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("deptList.json");

            Object obj = parser.parse(new InputStreamReader(is));
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray departments = (JSONArray)jsonObject.get("deptList");
            Iterator iterator = departments.iterator();
            while (iterator.hasNext()) {
                String deptId = iterator.next().toString();
                OrgListApiResponse orgList = ApiCalls.getOrgList(deptId);
                List<Response> data = getMetrics(orgList.getResult().getResponse().getContent());
                response.put(deptId, data);
            }
            JSONObject responseObject = new JSONObject(response);
            String str = mapper.writeValueAsString(responseObject);
            FileWriter writer = new FileWriter(Constants.RESPONSE_PATH);
            writer.write(str);
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static List<Response> getMetrics(List<OrgListApiResultContent> orgList) throws IOException, ParseException, InterruptedException {

        List<Response> results = new ArrayList<>();
        String dashboardUrl = Constants.PORTAL_URL + Constants.METRICS_PATH;
        Map<String,Object> requestBody = Commons.buildRequestBody();

        for (OrgListApiResultContent org : orgList) {
            if(org.getSbOrgId() != null){
                MetricsResponse registeredUsers = ApiCalls.getRegisteredUsers(org.getSbOrgId(), dashboardUrl, requestBody);
                MetricsResponse activeUsers = ApiCalls.getActiveUsers(org.getSbOrgId(), dashboardUrl, requestBody);
                MetricsResponse courseEnrolments = ApiCalls.getCourseEnrolments(org.getSbOrgId(), dashboardUrl, requestBody);
                MetricsResponse courseCompletions = ApiCalls.getCourseCompletions(org.getSbOrgId(), dashboardUrl, requestBody);
                MetricsResponse coursesPublished = ApiCalls.getCoursesPublished(org.getSbOrgId(), dashboardUrl, requestBody);
                MetricsResponse dailyTime = ApiCalls.getDailyTime(org.getSbOrgId(), dashboardUrl, requestBody);
//                MetricsResponse topCourses = ApiCalls.getTopCourses(org.getSbOrgId(), dashboardUrl, requestBody);
//                MetricsResponse topUsers = ApiCalls.getTopUsers(org.getSbOrgId(), dashboardUrl, requestBody);

                List<MetricsResponse> data = new ArrayList<>();
                data.add(registeredUsers);
                data.add(activeUsers);
                data.add(courseEnrolments);
                data.add(courseCompletions);
                data.add(coursesPublished);
                data.add(dailyTime);
//                data.add(topCourses);
//                data.add(topUsers);
                results.add(new Response(org.getSbOrgId(), org.getOrgName(), data));
            }

        }

        return results;
    }


}