package com.igrandee.product.ecloud.viewmodel.livesession;

public class LiveSessionEnrollVM {
	
	private String livesessionid;
	private String orgpersonid;
	private String orderno;
	private String paymenttype;
	
	
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getLivesessionid() {
		return livesessionid;
	}
	public void setLivesessionid(String livesessionid) {
		this.livesessionid = livesessionid;
	}
	public String getOrgpersonid() {
		return orgpersonid;
	}
	public void setOrgpersonid(String orgpersonid) {
		this.orgpersonid = orgpersonid;
	}
	
	

}
