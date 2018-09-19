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

/**
 * @author satheeskumaran_ds
 * 
 */
public class PlanViewModel implements Serializable {

	/**
	 * Auto Generated default serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String id, planname ;
	int duration;
	int maxusers;
	int maxcourse;
	Date dateofcreation;
	String planstatus;
	String durationtype;
	
	
	public String getDurationtype() {
		return durationtype;
	}

	public int getPlanamount() {
		return planamount;
	}

	public void setDurationtype(String durationtype) {
		this.durationtype = durationtype;
	}

	public void setPlanamount(int planamount) {
		this.planamount = planamount;
	}

	int planamount;
	
	
	

	public String getId() {
		return id;
	}

	public Date getDateofcreation() {
		return dateofcreation;
	}

	public void setDateofcreation(Date dateofcreation) {
		this.dateofcreation = dateofcreation;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	public int getMaxcourse() {
		return maxcourse;
	}

	public void setMaxcourse(int maxcourse) {
		this.maxcourse = maxcourse;
	}

	public int getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public String getPlanstatus() {
		return planstatus;
	}

	public void setPlanstatus(String planstatus) {
		this.planstatus = planstatus;
	}




}
