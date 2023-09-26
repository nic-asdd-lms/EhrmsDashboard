package igot.ehrms.model.orgListApi;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import igot.ehrms.model.ApiResponseParams;

public class OrgListApiResponse {
    private String id;
	private String ver;
	private String ts;
	private ApiResponseParams params;
	private String responseCode;
	private OrgListApiRespResult result;
	
    private transient Map<String, Object> response = new HashMap<>();

	public OrgListApiResponse() {
		this.ver = "v1";
		this.ts = new Timestamp(System.currentTimeMillis()).toString();
		this.params = new ApiResponseParams();
	}
	
	public OrgListApiResponse(String id) {
		this();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public ApiResponseParams getParams() {
		return params;
	}
	public void setParams(ApiResponseParams params) {
		this.params = params;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public OrgListApiRespResult getResult() {
		return result;
	}
	public void setResult(OrgListApiRespResult result) {
		this.result = result;
	}

    public Object get(String key) {
		return response.get(key);
	}

	public void put(String key, Object vo) {
		response.put(key, vo);
	}

	public void putAll(Map<String, Object> map) {
		response.putAll(map);
	}

	public boolean containsKey(String key) {
		return response.containsKey(key);
	}
}
