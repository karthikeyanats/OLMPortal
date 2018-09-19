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
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.serviceadapter
 * CommentAdapter.java
 * Created Aug 13, 2014 2:56:26 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.serviceadapter;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.action.blog.CommentAction;
import com.igrandee.product.ecloud.model.blog.Comment;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.service.blog.CommentService;
import com.igrandee.product.ecloud.viewmodel.blog.CommentsViewModel;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;

/**
 * @author balajichander_r
 * 
 */
@Component
@Scope("prototype")
public class CommentAdapter implements Serializable {

	/**
	 * Auto generated default serial version Id stub serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;
	private static final BlogCommonsUtil BLOG_COMMONS_UTIL = new BlogCommonsUtil();

	private static final Logger commentAdapterLogger = Logger.getLogger(CommentAdapter.class);

	/**
	 * 
	 * @param comment
	 * @param commentsViewModel
	 * @param commentAction
	 * @param orgPersonID
	 * @param post
	 * @param orgPerson
	 * @return comment Comment
	 * @author balajichander_r
	 */
	public Comment convertToNewCommentEntity(Comment comment,CommentsViewModel commentsViewModel, CommentAction commentAction, Integer orgPersonID,Post post, Orgperson orgPerson) { 
		try {
			comment.setCommentText(commentsViewModel.getCommentText());
			comment.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
			comment.setStatus("A"); 
			  
			post.setId(Integer.parseInt(commentsViewModel.getPostId()));
			comment.setPost(post);
			
			orgPerson.setOrgpersonid(orgPersonID);
			comment.setOrgperson(orgPerson);
			
		} catch (Exception e) {
			commentAdapterLogger.error(e);
		}
		return comment;
	}
	
	/**
	 * 
	 * @param comment
	 * @param commentService
	 * @param CommentId
	 * @return comment Comment
	 * @author balajichander_r
	 */
	public Comment convertDeleteCommentEntity(Comment comment, CommentService commentService,Integer CommentId ){  
		
		try {
			comment = commentService.get(CommentId); 
			if(comment != null){
				comment.setStatus("D");
			} 
		} catch (Exception e) {
			commentAdapterLogger.error(e);
		}
		
		return comment;
	}

	/**
	 * 
	 * @param comment
	 * @param commentService
	 * @param commentId
	 * @param commentsViewModel
	 * @return
	 */
	public Comment convertUpdateCommentEntity(Comment comment, CommentService commentService, Integer commentId, CommentsViewModel commentsViewModel){
		
		try {
			  comment = commentService.get(commentId);
			  if(comment != null){
				  comment.setId(commentId);
				  comment.setCommentText(commentsViewModel.getCommentText());
				  comment.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
				  comment.setStatus("U"); 
			  }
		} catch (Exception e) {
			 commentAdapterLogger.error(e);
		} 
		return comment;
	}
	
}
