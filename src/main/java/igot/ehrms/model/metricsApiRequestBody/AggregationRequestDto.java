package igot.ehrms.model.metricsApiRequestBody;

import java.util.Map;

public class AggregationRequestDto {
    
    private String dashboardId;
    private String visualizationType;
    private String visualizationCode;
    private String queryType;
    private Map<String, Object> filters;
    private String moduleLevel;
    private String aggregationFactors;
    private Map<String, Object> requestDate;

    public String getDashboardId() {
        return dashboardId;
    }
    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }
    public String getVisualizationType() {
        return visualizationType;
    }
    public void setVisualizationType(String visualizationType) {
        this.visualizationType = visualizationType;
    }
    public String getVisualizationCode() {
        return visualizationCode;
    }
    public void setVisualizationCode(String visualizationCode) {
        this.visualizationCode = visualizationCode;
    }
    public String getQueryType() {
        return queryType;
    }
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
    public Map<String, Object> getFilters() {
        return filters;
    }
    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }
    public String getModuleLevel() {
        return moduleLevel;
    }
    public void setModuleLevel(String moduleLevel) {
        this.moduleLevel = moduleLevel;
    }
    public String getAggregationFactors() {
        return aggregationFactors;
    }
    public void setAggregationFactors(String aggregationFactors) {
        this.aggregationFactors = aggregationFactors;
    }
    public Map<String, Object> getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Map<String, Object> requestDate) {
        this.requestDate = requestDate;
    }
    
}
