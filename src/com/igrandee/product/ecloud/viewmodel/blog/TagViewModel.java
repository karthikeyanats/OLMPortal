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
 * TagViewModel.java
 * Created Jul 22, 2014 6:18:12 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.blog;

import java.io.Serializable;

/**
 * @author balajichander_r
 * 
 */
public class TagViewModel implements Serializable {

	/**
	 * Auto generated default serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	private String tagId, tagDescription, tagName, tagStatus;

	/**
	 * @return the tagId
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * @param tagId
	 *            the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * @return the tagDescription
	 */
	public String getTagDescription() {
		return tagDescription;
	}

	/**
	 * @param tagDescription
	 *            the tagDescription to set
	 */
	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the tagStatus
	 */
	public String getTagStatus() {
		return tagStatus;
	}

	/**
	 * @param tagStatus
	 *            the tagStatus to set
	 */
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}

}
