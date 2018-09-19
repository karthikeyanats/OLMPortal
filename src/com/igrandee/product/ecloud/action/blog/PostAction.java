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
 * PostAction.java
 * Created Jul 18, 2014 2:31:23 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.blog;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.hibernate.SessionFactory;

import com.igrandee.bean.validation.GenericValidation;
import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.core.login.util.User;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.blog.BlogMedia;
import com.igrandee.product.ecloud.model.blog.Category;
import com.igrandee.product.ecloud.model.blog.Comment;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.model.blog.Postcategory;
import com.igrandee.product.ecloud.model.blog.Postskill;
import com.igrandee.product.ecloud.model.blog.Posttag;
import com.igrandee.product.ecloud.model.blog.Skill;
import com.igrandee.product.ecloud.model.blog.Tag;
import com.igrandee.product.ecloud.service.blog.CategoryService;
import com.igrandee.product.ecloud.service.blog.PostCategoriesService;
import com.igrandee.product.ecloud.service.blog.PostService;
import com.igrandee.product.ecloud.service.blog.PostSkillsService;
import com.igrandee.product.ecloud.service.blog.PostTagsService;
import com.igrandee.product.ecloud.service.blog.SkillService;
import com.igrandee.product.ecloud.service.blog.TagService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.serviceadapter.PostAdapter;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.igrandee.product.ecloud.viewmodel.blog.PostViewModel;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

/** 
 *
 */
@Path("blogpost") 
@SuppressWarnings({"rawtypes","unchecked"})
public class PostAction extends AbstractIGActionSupport{
		
	/**
	 * Auto generated default serial version Id Stub
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	//APACHE Log4j logger utility 
	private static final Logger postActionLogger =  Logger.getLogger(PostAction.class);
	
	
	@InjectParam
	GenericValidation genericValidation; 
	
	@InjectParam
	Post post;
	  
	@InjectParam
	Comment comment;
	   
	@InjectParam
	User user;
	
	@InjectParam
	Category category;
	
	@InjectParam
	Postcategory postCategory;
	
	@InjectParam
	Skill  skill;
	
	@InjectParam
	Postskill postskill;
	 
	@InjectParam
	Tag tag;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	Posttag posttag;
	
	@InjectParam
	PostService postService;
	
	@InjectParam
	TagService tagService;
	
	@InjectParam
	CategoryService categoryService;
	
	@InjectParam
	SkillService skillService;
	
	@InjectParam
	BlogCommonsUtil blogCommonsUtil;
	
	@InjectParam
	PostAdapter postAdapter;
	
	@InjectParam
	PostCategoriesService postCategoriesService;
	
	@InjectParam
	PostSkillsService postSkillsService;
	
	@InjectParam
	PostTagsService postTagsService;
	
	@InjectParam
	BlogMedia blogMedia;
 	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	OrganizationViewService organizationViewService;
	
	/**
	 * 
	 * @param postViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPost(PostViewModel postViewModel){ 
		
		postActionLogger.info("*************** CREATE POST ******************  ");
		postActionLogger.info("ACTION :: CLIENT postViewModel == > " +postViewModel); 
		int orgPersonId = Integer.parseInt(getAttribute("orgpersonid"));
		int postID =0;
		postActionLogger.info("OrgPerson ID  : == ? > " + orgPersonId);
		post = postAdapter.convertNewPostEntity(post, postViewModel, orgPersonId, orgperson, postCategory, postskill, posttag, skill, tag, category);  
		
		if(post != null ){ 
			String validationErrors = genericValidation.checkValidations(post);
			if(! validationErrors.isEmpty() ){
				postActionLogger.info("ACTION :: FAILURE RESPONSE validationErrors == > " + validationErrors);
				return Response.status(422).entity(validationErrors).build(); 
			}else{ 
				postID = postService.save(post);
			}
		}
		 
		if(postID>0){ 
			postActionLogger.info("**************************************************  ");
			return Response.status(Status.CREATED).entity(Status.CREATED).build(); 
		}
		 
		postActionLogger.info("**************************************************  ");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param postViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePost(@PathParam("id") int postID, PostViewModel postViewModel){
		
		postActionLogger.info("*************** UPDATE A  POST ******************  ");  
		
		post = postAdapter.convertUpdatePostEntity(post, this, sessionFactory, postService, postViewModel, orgperson, postCategory, postskill, posttag, category, tag, skill,postID, postCategoriesService, postSkillsService, postTagsService);
		post = postService.update(post);  
		if(post != null){
			String validationErrors = genericValidation.checkValidations(post);
			if(! validationErrors.isEmpty() ){
				postActionLogger.info("ACTION :: FAILURE RESPONSE validationErrors == > " + validationErrors);
				return Response.status(422).entity(validationErrors).build(); 
			}else{  
				postActionLogger.info("**************************************************  ");
				return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
			} 
		}
		postActionLogger.info("**************************************************  ");
		return Response.status(Status.NOT_MODIFIED).entity(Status.NOT_MODIFIED).build();
	}
	
	/**
	 * @param postViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@DELETE 
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePost( @PathParam("id") int postID ){
		
		postActionLogger.info("*************** DELETE A  POST ******************  ");
		postActionLogger.info("ACTION :: CLIENT postID == > " +postID  );  
		 post = postService.get(postID);
		 post.setStatus("D");
		  
		if(postID > 0){  
			postActionLogger.info("**************************************************  ");
			Post status = postService.update(post);
			if(status != null){
			return Response.status(Status.OK).entity(Status.ACCEPTED).build();
			}
		}
		 
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * Get details releated to a post of an author provided the postid of the particular post<br>
	 *  
	 * 
	 * @param postViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@GET
	@Path("postroll/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getAPost(@PathParam("id") String postID){
		postActionLogger.info("*************** GET A  POST ******************  ");
		postActionLogger.info("ACTION :: CLIENT postID == > " + postID); 
		
		List<?> postList = postService.getPostsOf(Integer.parseInt(postID));
		  
		if(postList != null){ 
			postActionLogger.info("**************************************************  ");
			return Response.status(Status.OK).entity(postList).build();
		} 
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param postViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 */
	@GET  
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getPosts(){
		
		postActionLogger.info("*************** GET  ALL POSTS ******************  "); 
		List<?> postList=null;
		try{
		postList = postService.getPosts(Long.parseLong(getAttribute("organizationid")));
		}
		catch(ClassCastException e){
			List<?> master=organizationViewService.getMasterOrgid();
			HashMap<String, Long>  mastermap=(HashMap<String, Long>) master.get(0);
			long masterid=mastermap.get("organizationid");
			postList = postService.getPosts(masterid);
		}
		 
		if(postList.size()>0 & !postList.isEmpty()){ 
			postActionLogger.info("**************************************************  ");
			return Response.status(Status.OK).entity(postList).build();
		} 
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	 
	/**
	 * 
	 * @param titlePhrase
	 * @return Status.ACCEPTED | Status.NO_CONTENT Response
	 * @author balajichander_r
	 */
	@POST  
	@Path("postroll/duplicate")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkDuplicate(PostViewModel postViewModel){
		postActionLogger.info("*************** CHECK FOR DUPLICATE POST ******************  ");
		postActionLogger.info("ACTION :: CLIENT titlePhrase == > " + postViewModel.getPostTitle());  
		boolean assertValue = postService.checkDuplicate(postViewModel.getPostTitle());
		if(assertValue == true){
			postActionLogger.info("ACTION :: RESPONSE  is post duplicate ?,  == > " + assertValue);
			postActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		} 
		postActionLogger.info("ACTION :: FAILURE RESPONSE is post duplicate ?, == > " + assertValue);
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param postViewModel
	 * @return Status.ACCEPTED | Status.NO_CONTENT Response
	 * @author balajichander_r
	 */
	@POST
	@Path("postroll/change/duplicate")
	public Response checkUpdateDuplicate(PostViewModel postViewModel){
		postActionLogger.info("*************** CHECK FOR DUPLICATE POST ON UPDATE ******************  ");
		postActionLogger.info("ACTION :: CLIENT titlePhrase == > " + postViewModel.getPostTitle());  
		boolean assertValue = postService.checkDuplicate(postViewModel.getPostTitle(), Integer.parseInt(postViewModel.getPostId()));
		if(assertValue == true){
			postActionLogger.info("ACTION :: RESPONSE  is post duplicate on update ?,  == > " + assertValue);
			postActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		} 
		postActionLogger.info("ACTION :: FAILURE RESPONSE is post duplicate on update ?, == > " + assertValue);
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
		
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("postroll/user")
	@Produces(MediaType.APPLICATION_JSON) 
	public Response assertUser(){
		
		postActionLogger.info("*************** GET CURRENT USER ******************  ");
		postActionLogger.info("ACTION :: CLIENT titlePhrase ");  
		Integer user = Integer.parseInt(getAttribute("orgpersonid"));
		postActionLogger.info("ACTION ::  RESPONSE user " + user);
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.ACCEPTED).entity(user).build();
		
	}
	
	/**
	 * 
	 * @param inputMedia
	 * @param fileDetail
	 * @return returnURL |  String;
	 */ 
	@POST
	@Path("postroll/media")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@FormDataParam("file") List<FormDataBodyPart> file){
		postActionLogger.info("***********MULTIPLE FILE UPLOAD USING JERSEY********** ");   	 
		List<BlogMedia> blogMedias = null;String URI = null;
		try { 
			 blogMedias = new ArrayList<BlogMedia>();
			 blogMedias =  postAdapter.uploadMediaAndGetFileDetails(file, blogMedia, orgperson, Integer.parseInt(getAttribute("orgpersonid")));
			 for (Iterator iterator = blogMedias.iterator(); iterator.hasNext();) {
				BlogMedia blogMedia = (BlogMedia) iterator.next();
				URI = blogMedia.getPath();
			} 
			postActionLogger.info("ACTION :: RESPONSE  file Paths Array List  == > "+blogMedias);
			postActionLogger.info("**************************************************  "); 
			return Response.status(Status.ACCEPTED).entity(URI).build();  
		} catch (Exception e) {
			 postActionLogger.error(e);
		} 
		postActionLogger.info("ACTION :: FAILURE RESPONSE FILE(S) UPLOAD FAILED " );
		postActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}

}
