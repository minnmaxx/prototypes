package org.talz.domain.product;

public class Pizza {

	private String p1;
	
	public Pizza(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
}

