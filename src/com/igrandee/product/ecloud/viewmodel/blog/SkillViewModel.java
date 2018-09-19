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
 * SkillViewModel.java
 * Created Jul 22, 2014 6:15:10 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.blog;

import java.io.Serializable;

/**
 * @author balajichander_r
 * 
 */
public class SkillViewModel implements Serializable {

	/**
	 * Auto generated default serail version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	private String skillId, skillName, skillStatus, skillDescription,
			skillMediaPath;

	/**
	 * @return the skillId
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId
	 *            the skillId to set
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}

	/**
	 * @param skillName
	 *            the skillName to set
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	/**
	 * @return the skillStatus
	 */
	public String getSkillStatus() {
		return skillStatus;
	}

	/**
	 * @param skillStatus
	 *            the skillStatus to set
	 */
	public void setSkillStatus(String skillStatus) {
		this.skillStatus = skillStatus;
	}

	/**
	 * @return the skillDescription
	 */
	public String getSkillDescription() {
		return skillDescription;
	}

	/**
	 * @param skillDescription
	 *            the skillDescription to set
	 */
	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}

	/**
	 * @return the skillMediaPath
	 */
	public String getSkillMediaPath() {
		return skillMediaPath;
	}

	/**
	 * @param skillMediaPath
	 *            the skillMediaPath to set
	 */
	public void setSkillMediaPath(String skillMediaPath) {
		this.skillMediaPath = skillMediaPath;
	}

}
