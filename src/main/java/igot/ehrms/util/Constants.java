package igot.ehrms.util;


public class Constants {
	public static final String FILTERS = "filters";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String AGGREGATION_REQUEST = "aggregationRequestDto";
	public static final String X_AUTH_TOKEN = "x-authenticated-user-token";
	public static final String X_CHANNEL_ID = "X-Channel-Id";
	public static final String AUTHORIZATION = "Authorization";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	//	Dashboard API

	public static final String DASHBOARD_ID = "dashboardId";
	public static final String VISUALIZATION_TYPE = "visualizationType";
	public static final String VISUALIZATION_CODE = "visualizationCode";
	public static final String QUERY_TYPE = "queryType";
	public static final String MDO_FILTER = "mdofilter";
	public static final String MODULE_LEVEL = "moduleLevel";
	public static final String AGGREGATION_FACTORS = "aggregationFactors";
	public static final String REQUEST_DATE = "requestDate";
	public static final String ORG_LIST_PATH = "/apis/public/v8/org/v1/list/";
	public static final String METRICS_PATH = "/api/dashboard/analytics/getChartV2/Karmayogi";
	public static final String AUTH_PATH = "/auth/realms/sunbird/protocol/openid-connect/token";
	public static final String SPV_URL = "https://spv.";
	public static final String PORTAL_URL = "https://portal.";
	public static final String DASHBOARD_ID_REGISTERED_USERS = "spvoperationalmdo";
	public static final String VISUALISATION_TYPE_REGISTERED_USERS = "METRIC";
	public static final String VISUALISATION_CODE_REGISTERED_USERS = "visualization02mdo";
	public static final String DASHBOARD_ID_ACTIVE_USERS = "spvoperationalmdo";
	public static final String VISUALISATION_TYPE_ACTIVE_USERS = "METRIC";
	public static final String VISUALISATION_CODE_ACTIVE_USERS = "visualization05a";
	public static final String DASHBOARD_ID_COURSE_ENROLMENTS = "spvoperationalmdo";
	public static final String VISUALISATION_TYPE_COURSE_ENROLMENTS = "METRIC";
	public static final String VISUALISATION_CODE_COURSE_ENROLMENTS = "visualization38";
	public static final String DASHBOARD_ID_COURSE_COMPLETIONS = "spvceodd2";
	public static final String VISUALISATION_TYPE_COURSE_COMPLETIONS = "METRIC";
	public static final String VISUALISATION_CODE_COURSE_COMPLETIONS = "visualization39";
	public static final String DASHBOARD_ID_COURSES_PUBLISHED = "spvoperationalmdo";
	public static final String VISUALISATION_TYPE_COURSES_PUBLISHED = "METRIC";
	public static final String VISUALISATION_CODE_COURSES_PUBLISHED = "visualization03a";
	public static final String DASHBOARD_ID_DAILY_TIME = "spvceo";
	public static final String VISUALISATION_TYPE_DAILY_TIME = "METRIC";
	public static final String VISUALISATION_CODE_DAILY_TIME = "visualization05b";
	public static final String DASHBOARD_ID_TOP_COURSES = "mdodashboards";
	public static final String VISUALISATION_TYPE_TOP_COURSES = "METRIC";
	public static final String VISUALISATION_CODE_TOP_COURSES = "visualization53";
	public static final String DASHBOARD_ID_TOP_USERS = "spvceodd2";
	public static final String VISUALISATION_TYPE_TOP_USERS = "CHART";
	public static final String VISUALISATION_CODE_TOP_USERS = "visualization54";
	public static final String FROM_DATE = "2021-10-01 00:01:01.000";

	private Constants() {
		throw new IllegalStateException("Utility class");
	}

}
