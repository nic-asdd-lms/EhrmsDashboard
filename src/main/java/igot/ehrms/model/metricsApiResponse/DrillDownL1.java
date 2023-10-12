package igot.ehrms.model.metricsApiResponse;

public class DrillDownL1 {
    private String orgId;
    private String orgName;
    private ResponseData data;

    public DrillDownL1(String sbOrgId, String orgName, ResponseData data) {
        this.setOrgId(sbOrgId);
        this.setOrgName(orgName);
        this.setData(data);
    }
    
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public ResponseData getData() {
        return data;
    }
    public void setData(ResponseData responseData) {
        this.data = responseData;
    }
}
