package com.igrandee.product.ecloud.viewmodel.royalty;

import org.springframework.stereotype.Component;

@Component
public class PaymentIssueVM {
	
	String id;
	String createdby;
	String dateofissue;
	String paymentissuestatus;
	String paymentrequest;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getDateofissue() {
		return dateofissue;
	}
	public void setDateofissue(String dateofissue) {
		this.dateofissue = dateofissue;
	}
	public String getPaymentissuestatus() {
		return paymentissuestatus;
	}
	public void setPaymentissuestatus(String paymentissuestatus) {
		this.paymentissuestatus = paymentissuestatus;
	}
	public String getPaymentrequest() {
		return paymentrequest;
	}
	public void setPaymentrequest(String paymentrequest) {
		this.paymentrequest = paymentrequest;
	}

}
