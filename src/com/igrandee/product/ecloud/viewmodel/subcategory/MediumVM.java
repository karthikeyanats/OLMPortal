package com.igrandee.product.ecloud.viewmodel.subcategory;

import java.io.Serializable;

public class MediumVM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int mediumid;
	String mediumname;
	char mediumstatus;
	String status;
	
	public int getMediumid() {
		return mediumid;
	}
	public void setMediumid(int mediumid) {
		this.mediumid = mediumid;
	}
	public String getMediumname() {
		return mediumname;
	}
	public void setMediumname(String mediumname) {
		this.mediumname = mediumname;
	}
	public char getMediumstatus() {
		return mediumstatus;
	}
	public void setMediumstatus(char mediumstatus) {
		this.mediumstatus = mediumstatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
