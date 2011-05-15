package org.talz.domain;

public class Order {

	private String p1;
	private String p2;

	public Order() {
	}

	public String getP1() {
		return p1;
	}

	public synchronized void setP1(String p1) {		
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public synchronized void setP2(String p2) {
		this.p2 = p2;
	}

	public synchronized void setAll(String p1, String p2) {
		setP1(p1);
		setP2(p2);
	}
	
	public synchronized void controlledSet(String p1) throws InterruptedException {
		this.readOnly = false;
		Thread.sleep( 2000 );
		setP1(p1);
		this.readOnly = true;
	}
	
	public static class Builder {
		private String p1;
		private String p2;

		public Builder p1(String p1) {
			this.p1 = p1;
			return this;
		}

		public Builder p2(String p2) {
			this.p2 = p2;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}

	private Order(Builder builder) {
		this.p1 = builder.p1;
		this.p2 = builder.p2;
		this.readOnly = true;
	}
}
