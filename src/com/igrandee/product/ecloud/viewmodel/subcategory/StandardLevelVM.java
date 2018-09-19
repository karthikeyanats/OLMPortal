package com.igrandee.product.ecloud.viewmodel.subcategory;

import java.io.Serializable;

public class StandardLevelVM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int standardlevelid;
	String standardlevelname;
	char standardlevelstatus;
	String status;
	public int getStandardlevelid() {
		return standardlevelid;
	}
	public void setStandardlevelid(int standardlevelid) {
		this.standardlevelid = standardlevelid;
	}
	public String getStandardlevelname() {
		return standardlevelname;
	}
	public void setStandardlevelname(String standardlevelname) {
		this.standardlevelname = standardlevelname;
	}
	public char getStandardlevelstatus() {
		return standardlevelstatus;
	}
	public void setStandardlevelstatus(char standardlevelstatus) {
		this.standardlevelstatus = standardlevelstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
