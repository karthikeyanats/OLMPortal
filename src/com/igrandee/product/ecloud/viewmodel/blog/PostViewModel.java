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
 * PostViewModel.java
 * Created Jul 18, 2014 2:39:44 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.viewmodel.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author balajichander_r
 * 
 */
@SuppressWarnings("rawtypes")
public class PostViewModel implements Serializable {

	/**
	 * Auto generated default serial version id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	String postId, postTitle, postContent, postBannerPath, isPostCommentable,
			postDate, postMediaPath, postMedia, postStatus, orgPersonId;

	List<?> categoryIds = new ArrayList();
	List<?> skillIds = new ArrayList();
	List<?> tagIds = new ArrayList();

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
	 * @return the postTitle
	 */
	public String getPostTitle() {
		return postTitle;
	}

	/**
	 * @param postTitle
	 *            the postTitle to set
	 */
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	/**
	 * @return the postContent
	 */
	public String getPostContent() {
		return postContent;
	}

	/**
	 * @param postContent
	 *            the postContent to set
	 */
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	/**
	 * @return the postBannerPath
	 */
	public String getPostBannerPath() {
		return postBannerPath;
	}

	/**
	 * @param postBannerPath
	 *            the postBannerPath to set
	 */
	public void setPostBannerPath(String postBannerPath) {
		this.postBannerPath = postBannerPath;
	}

	/**
	 * @return the isPostCommentable
	 */
	public String IsPostCommentable() {
		return isPostCommentable;
	}

	/**
	 * @param isPostCommentable
	 *            the isPostCommentable to set
	 */
	public void setPostCommentable(String isPostCommentable) {
		this.isPostCommentable = isPostCommentable;
	}

	/**
	 * @return the postDate
	 */
	public String getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate
	 *            the postDate to set
	 */
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the mediaPath
	 */
	public String getPostMediaPath() {
		return postMediaPath;
	}

	/**
	 * @param mediaPath
	 *            the mediaPath to set
	 */
	public void setPostMediaPath(String postMediaPath) {
		this.postMediaPath = postMediaPath;
	}

	/**
	 * @return the status
	 */
	public String getPostStatus() {
		return postStatus;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	/**
	 * /**
	 * 
	 * @return the postMedia
	 */
	public String getPostMedia() {
		return postMedia;
	}

	/**
	 * @param postMedia
	 *            the postMedia to set
	 */
	public void setPostMedia(String postMedia) {
		this.postMedia = postMedia;
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

	/**
	 * @return the isPostCommentable
	 */
	public String getIsPostCommentable() {
		return isPostCommentable;
	}

	/**
	 * @param isPostCommentable
	 *            the isPostCommentable to set
	 */
	public void setIsPostCommentable(String isPostCommentable) {
		this.isPostCommentable = isPostCommentable;
	}

	/**
	 * @return the categoryIds
	 */
	public List<?> getCategoryIds() {
		return categoryIds;
	}

	/**
	 * @param categoryIds
	 *            the categoryIds to set
	 */
	public void setCategoryIds(List<?> categoryIds) {
		this.categoryIds = categoryIds;
	}

	/**
	 * @return the skillIds
	 */
	public List<?> getSkillIds() {
		return skillIds;
	}

	/**
	 * @param skillIds
	 *            the skillIds to set
	 */
	public void setSkillIds(List<?> skillIds) {
		this.skillIds = skillIds;
	}

	/**
	 * @return the tagIds
	 */
	public List<?> getTagIds() {
		return tagIds;
	}

	/**
	 * @param tagIds
	 *            the tagIds to set
	 */
	public void setTagIds(List<?> tagIds) {
		this.tagIds = tagIds;
	}

}
