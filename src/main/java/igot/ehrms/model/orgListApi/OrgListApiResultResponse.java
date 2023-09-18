package igot.ehrms.model.orgListApi;

import java.util.List;

public class OrgListApiResultResponse {
    private int count;
	private List<OrgListApiResultContent> content;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OrgListApiResultContent> getContent() {
		return content;
	}

	public void setContent(List<OrgListApiResultContent> content) {
		this.content = content;
	}
}
