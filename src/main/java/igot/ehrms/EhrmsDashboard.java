package igot.ehrms;

import java.io.*;

import java.util.*;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import igot.ehrms.model.metricsApiResponse.DrillDownL1;
import igot.ehrms.model.metricsApiResponse.MetricsResponse;
import igot.ehrms.model.metricsApiResponse.Plots;
import igot.ehrms.model.metricsApiResponse.Response;
import igot.ehrms.model.orgListApi.OrgListApiResponse;
import igot.ehrms.model.orgListApi.OrgListApiResultContent;
import igot.ehrms.util.Commons;
import igot.ehrms.util.Constants;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.slf4j.LoggerFactory;

public class EhrmsDashboard {
        private static final Logger logger = (Logger) LoggerFactory.getLogger(EhrmsDashboard.class);

        public static void main(String[] args) {

                JSONParser parser = new JSONParser();
                Map<String, Object> response = new HashMap<>();
                ObjectMapper mapper = new ObjectMapper();

                try {
                        String config = System.getenv("CONFIG");
                        String deptList = System.getenv("DEPT_LIST");
                        String output = System.getenv("OUTPUT");

                        String token = ApiCalls.login(config);

                        Object obj = parser.parse(new FileReader(deptList));
                        JSONObject jsonObject = (JSONObject) obj;
                        JSONArray departments = (JSONArray) jsonObject.get("deptList");
                        var iterator = departments.iterator();

                        while (iterator.hasNext()) {
                                String deptId = iterator.next().toString();

                                logger.info("Fetching child organisations of : " + deptId);
                                OrgListApiResponse orgList = ApiCalls.getOrgList(deptId, config);

                                List<Response> data = getMetrics(orgList.getResult().getResponse().getContent(), token,
                                                config);
                                response.put(deptId, data);
                        }

                        JSONObject responseObject = new JSONObject(response);
                        String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObject);

                        logger.info("Writing result to file - " + output);

                        FileWriter writer = new FileWriter(output);
                        writer.write(str);
                        writer.close();

                } catch (Exception e) {
                        logger.error("ERROR: ", e);
                }

        }

        private static List<Response> getMetrics(List<OrgListApiResultContent> orgList, String accessToken,
                        String config)
                        throws IOException, ParseException, InterruptedException, java.text.ParseException {
                JSONParser parser = new JSONParser();
                // Object obj = parser.parse(new FileReader(Constants.METADATA));
                Object obj = parser.parse(new FileReader(config));
                JSONObject jsonObject = (JSONObject) obj;

                List<Response> results = new ArrayList<>();
                
                List<DrillDownL1> registered = new ArrayList<>();
                List<DrillDownL1> active = new ArrayList<>();
                List<DrillDownL1> enrolments = new ArrayList<>();
                List<DrillDownL1> completions = new ArrayList<>();
                List<DrillDownL1> published = new ArrayList<>();
                List<DrillDownL1> dailyHours = new ArrayList<>();
                List<DrillDownL1> top5Users = new ArrayList<>();
                List<DrillDownL1> top5Courses = new ArrayList<>();

                Double totalRegisteredUsers = 0.0;
                Double totalActiveUsers = 0.0;
                Double totalCourseEnrolments = 0.0;
                Double totalCourseCompletions = 0.0;
                Double totalCoursesPublished = 0.0;
                Double totalDailyHours = 0.0;
                Double totalTopUsers = Constants.TOP_USER_COUNT;
                Double totalTopCourses = Constants.TOP_COURSE_COUNT;

                String dashboardUrl = Constants.PORTAL_URL + jsonObject.get("url") + Constants.METRICS_PATH;
                Map<String, Object> requestBody = Commons.buildRequestBody();

                String metricsToken = "Bearer " + jsonObject.get("metricsToken").toString();

                for (OrgListApiResultContent org : orgList) {
                        if (org.getSbOrgId() != null) {
                                logger.info("Extracting metrics for : '" + org.getOrgName() + '\'');
                                MetricsResponse registeredUsers = ApiCalls.getRegisteredUsers(org.getSbOrgId(),
                                                dashboardUrl,
                                                requestBody, accessToken, metricsToken);
                                MetricsResponse activeUsers = ApiCalls.getActiveUsers(org.getSbOrgId(), dashboardUrl,
                                                requestBody,
                                                accessToken, metricsToken);
                                MetricsResponse courseEnrolments = ApiCalls.getCourseEnrolments(org.getSbOrgId(),
                                                dashboardUrl,
                                                requestBody, accessToken, metricsToken);
                                MetricsResponse courseCompletions = ApiCalls.getCourseCompletions(org.getSbOrgId(),
                                                dashboardUrl,
                                                requestBody, accessToken, metricsToken);
                                MetricsResponse coursesPublished = ApiCalls.getCoursesPublished(org.getSbOrgId(),
                                                dashboardUrl,
                                                requestBody, accessToken, metricsToken);
                                MetricsResponse dailyTime = ApiCalls.getDailyTime(org.getSbOrgId(), dashboardUrl,
                                                requestBody,
                                                accessToken, metricsToken);
                                MetricsResponse topCourses = ApiCalls.getTopCourses(org.getSbOrgId(), dashboardUrl,
                                                requestBody,
                                                accessToken, metricsToken);
                                MetricsResponse topUsers = ApiCalls.getTopUsers(org.getSbOrgId(), dashboardUrl,
                                                requestBody,
                                                accessToken, metricsToken);

                                registered.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                registeredUsers.getResponseData()));
                                active.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                activeUsers.getResponseData()));
                                enrolments.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                courseEnrolments.getResponseData()));
                                completions.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                courseCompletions.getResponseData()));
                                published.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                coursesPublished.getResponseData()));
                                dailyHours.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                dailyTime.getResponseData()));
                                top5Courses.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                topCourses.getResponseData()));
                                top5Users.add(new DrillDownL1(org.getSbOrgId(), org.getOrgName(),
                                                topUsers.getResponseData()));

                                totalRegisteredUsers += registeredUsers.getResponseData().getData().get(0)
                                                .getHeaderValue();
                                totalActiveUsers += activeUsers.getResponseData().getData().get(0).getHeaderValue();
                                totalCourseEnrolments += courseEnrolments.getResponseData().getData().get(0)
                                                .getHeaderValue();
                                totalCourseCompletions += courseCompletions.getResponseData().getData().get(0)
                                                .getHeaderValue();
                                totalCoursesPublished += coursesPublished.getResponseData().getData().get(0)
                                                .getHeaderValue();
                                totalDailyHours += dailyTime.getResponseData().getData().get(0).getHeaderValue();

                                
                        }

                }

                List<Plots> topUsers = getTop(top5Users, Constants.TOP_USER_COUNT);
                List<Plots> topCouses = getTop(top5Courses,Constants.TOP_COURSE_COUNT);

                results.add(new Response("registeredUsers", totalRegisteredUsers, registered, null));
                results.add(new Response("activeUsers", totalActiveUsers, active, null));
                results.add(new Response("courseEnrolments", totalCourseEnrolments, enrolments, null));
                results.add(new Response("courseCompletions", totalCourseCompletions, completions, null));
                results.add(new Response("coursesPublished", totalCoursesPublished, published, null));
                results.add(new Response("dailyHours", totalDailyHours, dailyHours, null));
                results.add(new Response("topUsers", totalTopUsers, top5Users, topUsers));
                results.add(new Response("topCourses", totalTopCourses, top5Courses, topCouses));

                return results;
        }

        

        

        private static List<Plots> getTop(List<DrillDownL1> list, double topCount) {
                List<Plots> listElements = new ArrayList<>();

                for (DrillDownL1 listElement : list) {
                        for (Plots plots : listElement.getData().getData().get(0).getPlots()) {
                                listElements.add(plots);
                        }
                }

                Comparator<Plots> comparator = new Comparator<Plots>() {
                        @Override
                        public int compare(Plots o1, Plots o2) {
                                return Double.compare(o2.getValue(), o1.getValue());
                        }
                };
                Collections.sort(listElements, comparator);

                List<Plots> result = new ArrayList<>();
                int count = Integer.min((int) topCount, listElements.size());
                for(int i=0; i< count; i++)
                        result.add(listElements.get(i));

                return result;

        }

}