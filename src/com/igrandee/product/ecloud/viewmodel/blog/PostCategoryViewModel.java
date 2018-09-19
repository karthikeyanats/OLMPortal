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
 * PostCategoryViewModel.java
 * Created Jul 22, 2014 6:07:11 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.blog;

import java.io.Serializable;

/**
 * @author balajichander_r
 * 
 */
public class PostCategoryViewModel implements Serializable {

	/**
	 * Auto generated defaule serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String postCategoryId, postCategoryStatus;

	CategoryViewModel categoryViewModel;

	PostViewModel postViewModel;

	/**
	 * @return the postCategoryId
	 */
	public String getPostCategoryId() {
		return postCategoryId;
	}

	/**
	 * @param postCategoryId
	 *            the postCategoryId to set
	 */
	public void setPostCategoryId(String postCategoryId) {
		this.postCategoryId = postCategoryId;
	}

	/**
	 * @return the postCategoryStatus
	 */
	public String getPostCategoryStatus() {
		return postCategoryStatus;
	}

	/**
	 * @param postCategoryStatus
	 *            the postCategoryStatus to set
	 */
	public void setPostCategoryStatus(String postCategoryStatus) {
		this.postCategoryStatus = postCategoryStatus;
	}

	/**
	 * @return the categoryViewModel
	 */
	public CategoryViewModel getCategoryViewModel() {
		return categoryViewModel;
	}

	/**
	 * @param categoryViewModel
	 *            the categoryViewModel to set
	 */
	public void setCategoryViewModel(CategoryViewModel categoryViewModel) {
		this.categoryViewModel = categoryViewModel;
	}

	/**
	 * @return the postViewModel
	 */
	public PostViewModel getPostViewModel() {
		return postViewModel;
	}

	/**
	 * @param postViewModel
	 *            the postViewModel to set
	 */
	public void setPostViewModel(PostViewModel postViewModel) {
		this.postViewModel = postViewModel;
	}

}
