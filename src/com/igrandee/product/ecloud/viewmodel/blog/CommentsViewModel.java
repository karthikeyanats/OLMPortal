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
 * CommentsViewModel.java
 * Created Jul 22, 2014 6:02:18 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.blog;

import java.io.Serializable;

/**
 * @author balajichander_r
 * 
 */
public class CommentsViewModel implements Serializable {

	/**
	 * Auto generated default serial version id serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String commentId, commentText, commentDate, commentStatus, postId,
			orgPersonId;

	/**
	 * @return the commentId
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId
	 *            the commentId to set
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText
	 *            the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @return the commentDate
	 */
	public String getCommentDate() {
		return commentDate;
	}

	/**
	 * @param commentDate
	 *            the commentDate to set
	 */
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	/**
	 * @return the commentStatus
	 */
	public String getCommentStatus() {
		return commentStatus;
	}

	/**
	 * @param commentStatus
	 *            the commentStatus to set
	 */
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	/**
	 * @return the postId
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * @return the orgPersonId
	 */
	public String getOrgPersonId() {
		return orgPersonId;
	}

	/**
	 * @param orgPersonId
	 *            the orgPersonId to set
	 */
	public void setOrgPersonId(String orgPersonId) {
		this.orgPersonId = orgPersonId;
	}

}
