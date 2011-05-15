package domain;

public class LseAnnouncement {

	private String companyName;
	private String content;
	private String releasedTimestamp;
	private String ticker;

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReleasedTimestamp() {
		return releasedTimestamp;
	}
	public void setReleasedTimestamp(String releasedTimestamp) {
		this.releasedTimestamp = releasedTimestamp;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
}
