package com.igrandee.product.ecloud.viewmodel.royalty;

import org.springframework.stereotype.Component;

@Component
public class PaymentRequestVM {

	String id;
	String dateofrequest;
	String paymentrequeststatus;
	String requestedby;
	String requestlimit_id;
	int paymentrequestid;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDateofrequest() {
		return dateofrequest;
	}
	public String getRequestedby() {
		return requestedby;
	}
	public void setRequestedby(String requestedby) {
		this.requestedby = requestedby;
	}
	public void setDateofrequest(String dateofrequest) {
		this.dateofrequest = dateofrequest;
	}
	public String getPaymentrequeststatus() {
		return paymentrequeststatus;
	}
	public void setPaymentrequeststatus(String paymentrequeststatus) {
		this.paymentrequeststatus = paymentrequeststatus;
	}

	public String getRequestlimit_id() {
		return requestlimit_id;
	}
	public void setRequestlimit_id(String requestlimit_id) {
		this.requestlimit_id = requestlimit_id;
	}
	public int getPaymentrequestid() {
		return paymentrequestid;
	}
	public void setPaymentrequestid(int paymentrequestid) {
		this.paymentrequestid = paymentrequestid;
	}

	
}
