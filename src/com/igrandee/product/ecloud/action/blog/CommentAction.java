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
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.action
 * CommentAction.java
 * Created Jul 23, 2014 10:27:46 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.blog;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.bean.validation.GenericValidation;
import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.blog.Comment;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.service.blog.CommentService;
import com.igrandee.product.ecloud.service.blog.PostService;
import com.igrandee.product.ecloud.serviceadapter.CommentAdapter;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.igrandee.product.ecloud.viewmodel.blog.CommentsViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 * 
 */
@Path("blog/comment")
@SuppressWarnings({ "unused","unchecked","rawtypes" }) 
public class CommentAction extends AbstractIGActionSupport {

	/**
	 * Auto generated default serial version id stub
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger commentActionLogger = Logger.getLogger(CommentAction.class);

	@InjectParam
	Post post;

	@InjectParam
	Comment comment;

	@InjectParam
	Orgperson orgperson;

	@InjectParam
	CommentService commentService;

	@InjectParam
	PostService postService;

	@InjectParam
	CommentAdapter commentAdapter;
	
	@InjectParam
	BlogCommonsUtil blogCommonsUtil;
	
	
	@InjectParam
	GenericValidation genericValidation; 

	/**
	 * 
	 * @param commentsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createComment(CommentsViewModel commentsViewModel){
		
		commentActionLogger.info("*************** CREATE COMMENT ******************  ");
		commentActionLogger.info("ACTION :: CLIENT commentsViewModel == > " + commentsViewModel); 
		Integer orgPersonID =  Integer.parseInt( getAttribute("orgpersonid"));
		comment = commentAdapter.convertToNewCommentEntity(comment, commentsViewModel, this, orgPersonID, post, orgperson);
		Integer commentInteger = 0;
		if(comment  != null){
			commentActionLogger.info("ACTION :: CLIENT comment == > " + comment);
			String validationErrors = genericValidation.checkValidations(comment);
			if(! validationErrors.isEmpty()){
				commentActionLogger.info("ACTION :: VALIDATION ERROR RESPONSE comment HAS ERRORS == > " + comment);
				commentActionLogger.info("**************************************************  ");
				return Response.status(422).entity(validationErrors).build();
			}else{
				commentInteger = commentService.save(comment);
			}
		}
		
		if(commentInteger > 0){
			commentActionLogger.info("ACTION :: RESPONSE comment == > " + comment);
			commentActionLogger.info("**************************************************  ");
			return Response.status(Status.OK).entity(comment).build();
		}
		
		commentActionLogger.info("ACTION :: FAILURE RESPONSE comment == > " + comment);
		commentActionLogger.info("**************************************************  ");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}

	/**
	 * 
	 * @param commentsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("id") int commentId) {
		 
		commentActionLogger.info("*************** DELETE COMMENT ******************  ");
		commentActionLogger.info("ACTION :: CLIENT commentId == > " + commentId);
		comment = commentAdapter.convertDeleteCommentEntity(comment, commentService, commentId); 
		 if(comment != null){ 
			 String validationErrors = genericValidation.checkValidations(comment);
			 if(! validationErrors.isEmpty()){
				 commentActionLogger.info("ACTION :: VALIDATION ERROR RESPONSE comment HAS ERRORS == > " + comment);
				 commentActionLogger.info("**************************************************  ");
				 return Response.status(422).entity(validationErrors).build();
				}else{
					comment = commentService.update(comment);
				} 
		 }  
		if (comment != null) {
			commentActionLogger.info("ACTION :: RESPONSE comment == > " + comment);
			commentActionLogger.info("**************************************************  ");
			return Response.status(Status.OK).entity(comment).build();
		}
		
		commentActionLogger.info("ACTION :: FAILURE RESPONSE comment == > " + comment);
		commentActionLogger.info("**************************************************  ");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build(); 
	}

	/**
	 * 
	 * @param commentsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateComment(@PathParam("id") int commentId, CommentsViewModel commentsViewModel) {
		commentActionLogger.info("*************** CREATE COMMENT ******************  ");
		commentActionLogger.info("ACTION :: CLIENT commentsViewModel == > " + commentsViewModel);
		comment = commentAdapter.convertUpdateCommentEntity(comment, commentService, commentId, commentsViewModel);
		
		if(comment != null){
		  String validationErrors = genericValidation.checkValidations(comment); 
		  if(! validationErrors.isEmpty()){
			    commentActionLogger.info("ACTION :: VALIDATION ERROR RESPONSE comment HAS ERRORS == > " + comment);
				commentActionLogger.info("**************************************************  ");
				return Response.status(422).entity(validationErrors).build();
		  }else{
			  comment = commentService.update(comment);
		  }
		}
		
		if (comment != null) {
			commentActionLogger.info("ACTION :: RESPONSE comment == > " + comment);
			commentActionLogger.info("**************************************************  ");
			return Response.status(Status.OK).entity(comment).build();
		}
		commentActionLogger.info("ACTION :: FAILURE RESPONSE comment == > " + comment);
		commentActionLogger.info("**************************************************  ");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 
	 * @param commentsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@GET
	@Path("commentroll/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAComment(@PathParam("id") int commentID) {
		
		commentActionLogger.info("*************** GET ALL DETAILS OF A COMMENT ******************  ");
		commentActionLogger.info("ACTION :: CLIENT commentID == > " + commentID);
		List<?> commentList = commentService.getCommentDetails(commentID);
		
		if(comment!=null){
			commentActionLogger.info("ACTION :: RESPONSE comment == > " + comment);
			commentActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(commentList).build();
		}
		commentActionLogger.info("ACTION :: NO CONTENT RESPONSE == > ");
		commentActionLogger.info("**************************************************  ");
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	
	/**
	 * 
	 * @param commentsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@GET
	@Path("commentroll/post/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getComments(@PathParam("id") int postID) {
		commentActionLogger.info("*************** GET LIST OF COMMENTS BELONGING TO A POST  ******************  ");
		commentActionLogger.info("ACTION :: CLIENT postID == > " + postID);
		List<?> commentList = commentService.getCommentDetailsOfAPost(postID);
		
		if (true) {
			commentActionLogger.info("ACTION :: RESPONSE commentList == > " + commentList);
			commentActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(commentList).build();
		}
		commentActionLogger.info("ACTION :: NO CONTENT RESPONSE  == > ");
		commentActionLogger.info("**************************************************  ");
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}

}
