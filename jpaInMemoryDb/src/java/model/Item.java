package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Item")
public class Item {


	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="property1")
	private String property1;
	
	@Column(name="property2")
	private String property2;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProperty1() {
		return property1;
	}
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	public String getProperty2() {
		return property2;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}	
	
	public String toString() {
		return id + "-[" + property1 + "," + property2 + "]";
	}
}
