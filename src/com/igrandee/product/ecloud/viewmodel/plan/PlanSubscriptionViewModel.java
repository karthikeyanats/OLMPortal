/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloud-blogutil 
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.viewmodel
 * CategoryViewModel.java
 * Created Jul 22, 2014 6:10:11 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.plan;

import java.io.Serializable;
import java.util.Date;

import com.igrandee.product.ecloud.model.Plan;

/**
 * @author satheeskumaran_ds
 * 
 */
public class PlanSubscriptionViewModel implements Serializable {

	/**
	 * Auto Generated default serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String id;
	int orgid;
	Date dateofsubscription;
	Date planenddate;
	Date planstartdate;
	Plan plan;
	int duration;
	String durationtype;
	String planid;
	String planpaymenttype;
	Date planpaymentdate;
	int orgsubscriptionid;
	String planpaymentstatus;
	String orgplanstatus;
	int planpaymentid;
	String orderno;
 	
	public String getId() {
		return id;
	}
	public int getOrgid() {
		return orgid;
	}
	public Date getDateofsubscription() {
		return dateofsubscription;
	}
	public Date getPlanenddate() {
		return planenddate;
	}
	public Date getPlanstartdate() {
		return planstartdate;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}
	public void setDateofsubscription(Date dateofsubscription) {
		this.dateofsubscription = dateofsubscription;
	}
	public void setPlanenddate(Date planenddate) {
		this.planenddate = planenddate;
	}
	public void setPlanstartdate(Date planstartdate) {
		this.planstartdate = planstartdate;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getPlanpaymenttype() {
		return planpaymenttype;
	}
	public void setPlanpaymenttype(String planpaymenttype) {
		this.planpaymenttype = planpaymenttype;
	}
	public Date getPlanpaymentdate() {
		return planpaymentdate;
	}
	public void setPlanpaymentdate(Date planpaymentdate) {
		this.planpaymentdate = planpaymentdate;
	}
	public int getOrgsubscriptionid() {
		return orgsubscriptionid;
	}
	public void setOrgsubscriptionid(int orgsubscriptionid) {
		this.orgsubscriptionid = orgsubscriptionid;
	}
	public String getPlanpaymentstatus() {
		return planpaymentstatus;
	}
	public void setPlanpaymentstatus(String planpaymentstatus) {
		this.planpaymentstatus = planpaymentstatus;
	}
	public String getOrgplanstatus() {
		return orgplanstatus;
	}
	public void setOrgplanstatus(String orgplanstatus) {
		this.orgplanstatus = orgplanstatus;
	}
	public int getPlanpaymentid() {
		return planpaymentid;
	}
	public void setPlanpaymentid(int planpaymentid) {
		this.planpaymentid = planpaymentid;
	}
	public String getDurationtype() {
		return durationtype;
	}
	public void setDurationtype(String durationtype) {
		this.durationtype = durationtype;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
}
