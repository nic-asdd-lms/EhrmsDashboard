package igot.ehrms.model.metricsApiResponse;

import java.util.List;

public class Response {
    private String metricName;
    private Double metricValue;
    private List<DrillDownL1> drillDown;
    private List<Plots> list;

    public Response(String metricName, Double metricValue, List<DrillDownL1> data, List<Plots> topList) {
        this.setMetricName(metricName);
        this.setMetricValue(metricValue);
        this.setDrillDown(data);
        this.setList(topList);
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Double metricValue) {
        this.metricValue = metricValue;
    }

    public List<DrillDownL1> getDrillDown() {
        return drillDown;
    }

    public void setDrillDown(List<DrillDownL1> data) {
        this.drillDown = data;
    }

    public List<Plots> getList() {
        return list;
    }

    public void setList(List<Plots> topList) {
        this.list = topList;
    }

}
