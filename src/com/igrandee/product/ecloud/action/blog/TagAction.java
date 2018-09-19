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
 * TagAction.java
 * Created Jul 23, 2014 10:46:01 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.blog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.model.blog.Posttag;
import com.igrandee.product.ecloud.model.blog.Tag;
import com.igrandee.product.ecloud.service.blog.TagService;
import com.igrandee.product.ecloud.viewmodel.blog.TagViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 *
 */
@Path("/blog/tag")
@SuppressWarnings("rawtypes")
public class TagAction extends AbstractIGActionSupport{

	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	static final Logger tagActionLogger = Logger.getLogger(TagAction.class);
	
	Map<?,?> hashMap;
	
	@InjectParam
	Tag tag;
	
	@InjectParam
	Posttag posttag;
	
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	Post  post;
	
	@InjectParam
	TagService tagService;
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTag(TagViewModel tagViewModel){ 
		
		tagActionLogger.info("****************** CREATING A TAG  ******************");
		tagActionLogger.info(tagViewModel);
		tagActionLogger.info("******************  *************  ******************");
		
		tag.setName(tagViewModel.getTagName());
		tag.setDescription(java.net.URLDecoder.decode(tagViewModel.getTagDescription()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		tag.setOrgperson(orgperson);
		tag.setStatus(tagViewModel.getTagStatus());
		
		
		int isTag = tagService.save(tag);
		
		tagActionLogger.info("******************       TAGS       ******************");
		tagActionLogger.info(tag);
		tagActionLogger.info("****************** **************** ******************");
		
		if(isTag>0){
			return Response.status(Status.OK).entity(tag).build();
		}else{
			tagActionLogger.info("******************       TAGS           ******************");
			tagActionLogger.info("****************** TAG CREATION FAILED  ******************");
			tagActionLogger.info("****************** *******************  ******************");
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteTag(TagViewModel tagViewModel){
		
		tagActionLogger.info("****************** DELETING TAG  ******************");
		tagActionLogger.info(tagViewModel);
		tagActionLogger.info("****************** ************  ******************");
		
		List<?> tagList = tagService.getTagsOf(Integer.parseInt(tagViewModel.getTagId())); 
		Iterator<?> iterator = tagList.listIterator();
		
		while (iterator.hasNext()) {
			
			hashMap = new HashMap();
			hashMap = (HashMap<?, ?>) iterator.next();
			
			tag.setId( (Integer) hashMap.get("id"));
			tag.setName( (String) hashMap.get("name"));
			tag.setDescription( (String) hashMap.get("description"));
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			tag.setOrgperson(orgperson);
			tag.setStatus((String) hashMap.get("status"));
			
		}
		 
		tag.setId(Integer.parseInt(tagViewModel.getTagId()));
		tag.setName( tagViewModel.getTagName());
		tag.setDescription( tagViewModel.getTagDescription());
		tag.setStatus("D");
		
		tag = tagService.update(tag);
		
		tagActionLogger.info("******************       TAGS       ******************");
		tagActionLogger.info(tag);
		tagActionLogger.info("****************** **************** ******************");
		
		if(tag != null){
			return Response.status(Status.OK).entity(tag).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTag(TagViewModel tagViewModel){
		
		tagActionLogger.info("****************** UPDATING TAG ******************");
		tagActionLogger.info(tagViewModel);
		tagActionLogger.info("******************  *********** ******************");
	    
		List<?> tagList = tagService.getTagsOf(Integer.parseInt(tagViewModel.getTagId()));
		Iterator<?> iterator = tagList.listIterator();
		
		while (iterator.hasNext()) {
			
			hashMap = new HashMap();
			hashMap = (HashMap<?, ?>) iterator.next();
			
			tag.setId( (Integer) hashMap.get("id"));
			tag.setName( (String) hashMap.get("name"));
			tag.setDescription( java.net.URLDecoder.decode((String) hashMap.get("description")));
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			tag.setOrgperson(orgperson);
			tag.setStatus((String) hashMap.get("status"));
			
		}
		 
		tag.setId(Integer.parseInt(tagViewModel.getTagId()));
		tag.setName( tagViewModel.getTagName());
		tag.setDescription( java.net.URLDecoder.decode(tagViewModel.getTagDescription()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		tag.setOrgperson(orgperson);
		tag.setStatus(tagViewModel.getTagStatus()); 
		tag = tagService.update(tag); 
		tagActionLogger.info("******************       TAGS       ******************");
		tagActionLogger.info(tag);
		tagActionLogger.info("****************** **************** ******************");
		
		if(tag != null){
			return Response.status(Status.OK).entity(tag).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/tag")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getATag(TagViewModel tagViewModel){
		
		tagActionLogger.info("****************** GET A TAG ******************");
		tagActionLogger.info(tagViewModel);
		tagActionLogger.info("****************** ********* ******************");
		
		List<?> tagList = tagService.getTagsOf(Integer.parseInt(tagViewModel.getTagId()));  
		 
		 
		tagActionLogger.info("******************       TAGS       ******************");
		tagActionLogger.info(tag);
		tagActionLogger.info("****************** **************** ******************");
		
		if(tagList.size()>0 & !tagList.isEmpty()){
			return Response.status(Status.OK).entity(tagList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/tags")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTags(TagViewModel tagViewModel){
		
		tagActionLogger.info("****************** GETTING ALL TAGS ******************");
		tagActionLogger.info("                   NO TAG VIEW MODEL                  ");
		tagActionLogger.info("****************** **************** ******************"); 
		List<?> tagList = tagService.getTags(Long.parseLong(getAttribute("organizationid")));  
		tagActionLogger.info("******************       TAGS       ******************");
		tagActionLogger.info(tagList);
		tagActionLogger.info("****************** **************** ******************");
		
		if(tagList.size()>0 && !tagList.isEmpty()){
			return Response.status(Status.OK).entity(tagList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.NO_CONTENT | Status.ACCEPTED 
	 * @author balajichander_r
	 */
	@POST
	@Path("/duplicate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkDuplicate(TagViewModel tagViewModel){
		
		tagActionLogger.info("*************** CHECK FOR DUPLICATE CATEGORIES ******************  " );
		tagActionLogger.info("ACTION :: CLIENT categoryTitlePhrase == > " + tagViewModel.getTagName() );  
		boolean assertValue = tagService.checkDuplicate(tagViewModel.getTagName(),getAttribute("orgpersonid")); 
		tagActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			tagActionLogger.info("ACTION :: RESPONSE is category dupliate ? == > " + assertValue );
			tagActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
			
		} 
		tagActionLogger.info("ACTION :: FAILURE RESPONSE is post duplicate ?, == > " + assertValue);
		tagActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
		
	}
	
	 
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.NO_CONTENT | Status.ACCEPTED 
	 * @author balajichander_r
	 */
	@POST
	@Path("/tagroll/duplicate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response checkUpdateDuplicate(TagViewModel tagViewModel){
		tagActionLogger.info("*************** CHECK FOR DUPLICATE TAGS ******************  " );
		tagActionLogger.info("ACTION :: CLIENT categoryTitlePhrase == > " + tagViewModel.getTagName() + " category id : " + tagViewModel.getTagId() );
		
		boolean assertValue = tagService.checkDuplicate( tagViewModel.getTagName(), Integer.parseInt(tagViewModel.getTagId()),getAttribute("orgpersonid")); 
		tagActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			tagActionLogger.info("ACTION :: RESPONSE is category duplicate ? == > " + assertValue );
			tagActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		} 
		tagActionLogger.info("ACTION :: FAILURE RESPONSE is category duplicate ?, == > " + assertValue);
		tagActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
	}
	
	/**
	 * 
	 * @param tagId
	 * @return Status.ACCEPTED | Status.NO_CONTENT Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/tagroll/post/{id}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response assertPostAvailablility( @PathParam("id") Integer tagId){
		tagActionLogger.info("*************** ASSERT POST AVAILABILITY FOR THIS CATEGORY ******************  " );
		tagActionLogger.info("ACTION :: CLIENT tagId == > " + tagId );
		boolean assertValue = tagService.assertPostAvailability(tagId);
		if(assertValue == false){ 
			tagActionLogger.info("ACTION :: RESPONSE is post available for tag ? == > " + assertValue );
			tagActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		}
		tagActionLogger.info("ACTION :: FAILURE RESPONSE is post available for tag  ?, == > " + assertValue);
		tagActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
		
	}
		
	
}
