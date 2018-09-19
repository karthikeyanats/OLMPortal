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
 * CategoryAction.java
 * Created Jul 23, 2014 10:41:17 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.blog;

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
import com.igrandee.product.ecloud.model.blog.Category;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.model.blog.Postcategory;
import com.igrandee.product.ecloud.service.blog.CategoryService;
import com.igrandee.product.ecloud.viewmodel.blog.CategoryViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 *
 */
@Path("/blog/category") 
public class CategoryAction extends AbstractIGActionSupport{
	
	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	static final Logger categoryActionLogger = Logger.getLogger(CategoryAction.class);
	
	Map<?, ?> hashMap;
	
	@InjectParam
	Postcategory postcategory;
	
	@InjectParam
	Category category;
	
	@InjectParam
	Post post;
	
	@InjectParam
	CategoryService categoryService;
	
	@InjectParam
	Orgperson orgperson;
	
	/**
	 * 
	 * @param categoryViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/create")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response createCategory(CategoryViewModel categoryViewModel){
	
		categoryActionLogger.info("*************** CREATE CATEGORY ******************  ");
		categoryActionLogger.info("ACTION :: CLIENT categoryViewModel == > " +categoryViewModel); 
		category.setName(categoryViewModel.getCategoryName());
		category.setDescription(java.net.URLDecoder.decode(categoryViewModel.getCategoryDescription()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		category.setOrgperson(orgperson);
		category.setStatus(categoryViewModel.getCategoryStatus());
		
		int isCategory  = categoryService.save(category);
		
		categoryActionLogger.info("ACTION :: RESPONSE category == > " + category);
		categoryActionLogger.info("**************************************************  ");
		
		if(isCategory > 0){
			return Response.status(Status.OK).entity(category).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param categoryViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/delete")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response deleteCategory(CategoryViewModel categoryViewModel){
		
		categoryActionLogger.info("*************** DELETE A CATEGORY ****************  ");
		categoryActionLogger.info("ACTION :: CLIENT categoryViewModel == > " +categoryViewModel);
		
		category.setId(Integer.parseInt(categoryViewModel.getCategoryId()));
		category.setName(categoryViewModel.getCategoryName());
		category.setDescription(categoryViewModel.getCategoryDescription());
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		category.setOrgperson(orgperson);
		category.setStatus("D");
		
		category = categoryService.update(category);
		
		categoryActionLogger.info("ACTION :: RESPONSE category == > " + category);
		categoryActionLogger.info("**************************************************  ");
		if(category != null){
			return Response.status(Status.OK).entity(category).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param categoryViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */ 
	@POST
	@Path("/update")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updateCategory(CategoryViewModel categoryViewModel){
		
		categoryActionLogger.info("*************** UPDATE A CATEGORY ******************  "); 
		categoryActionLogger.info("ACTION :: CLIENT categoryViewModel == > " +categoryViewModel);
		 
		
		category.setId(Integer.parseInt(categoryViewModel.getCategoryId()));
		category.setName(categoryViewModel.getCategoryName());
		
		category.setDescription(java.net.URLDecoder.decode(categoryViewModel.getCategoryDescription()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		category.setOrgperson(orgperson);
		category.setStatus(categoryViewModel.getCategoryStatus()); 
		category = categoryService.update(category);
		
		categoryActionLogger.info("ACTION :: RESPONSE categoryList == > " +category);
		categoryActionLogger.info("**************************************************  ");
		
		if(category != null){
			return Response.status(Status.OK).entity(category).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param categoryViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/category")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getACategory(CategoryViewModel categoryViewModel){
		
		
		categoryActionLogger.info("*************** GET A CATEGORY ******************  ");
		categoryActionLogger.info("ACTION :: CLIENT categoryViewModel == > " +categoryViewModel);
		
		
		List<?> categoryList= categoryService.getCategory(Integer.parseInt(categoryViewModel.getCategoryId())); 
		 
		
		categoryActionLogger.info("ACTION :: RESPONSE categoryList == > " +categoryList);
		categoryActionLogger.info("**************************************************  ");
		if(categoryList .size() > 0 && ! categoryList.isEmpty()){
			return Response.status(Status.OK).entity(categoryList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param categoryViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/categories")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCategories(CategoryViewModel categoryViewModel){
		
		categoryActionLogger.info("*************** GET CATEGORIES ******************  " );
		categoryActionLogger.info("ACTION :: CLIENT categoryViewModel == > " + categoryViewModel ); 
		 
		List<?> categoriesList = categoryService.getCategories(Long.parseLong(getAttribute("organizationid"))); 
		
		categoryActionLogger.info("ACTION :: RESPONSE categoriesList == > " + categoriesList );
		categoryActionLogger.info("**************************************************  ");
		
		if(categoriesList.size() > 0 && ! categoriesList.isEmpty()){
			return Response.status(Status.OK).entity(categoriesList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param titlePhrase
	 * @return Status.ACCEPTED | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/duplicate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response checkCreateDuplicate(CategoryViewModel categoryViewModel){
		
		categoryActionLogger.info("*************** CHECK FOR DUPLICATE CATEGORIES ******************  " );
		categoryActionLogger.info("ACTION :: CLIENT categoryTitlePhrase == > " + categoryViewModel.getCategoryName() );  
		boolean assertValue = categoryService.checkDuplicate(categoryViewModel.getCategoryName(),getAttribute("orgpersonid")); 
		categoryActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			categoryActionLogger.info("ACTION :: RESPONSE is category dupliate ? == > " + assertValue );
			categoryActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
			
		} 
		categoryActionLogger.info("ACTION :: FAILURE RESPONSE is post duplicate ?, == > " + assertValue);
		categoryActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	@POST
	@Path("/categoryroll/duplicate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response checkUpdateDuplicate(CategoryViewModel categoryViewModel){
		categoryActionLogger.info("*************** CHECK FOR DUPLICATE CATEGORIES ******************  " );
		categoryActionLogger.info("ACTION :: CLIENT categoryTitlePhrase == > " + categoryViewModel.getCategoryName() + " category id : " +categoryViewModel.getCategoryId() );
		
		boolean assertValue = categoryService.checkDuplicate(categoryViewModel.getCategoryName(), Integer.parseInt(categoryViewModel.getCategoryId()),getAttribute("orgpersonid")); 
		categoryActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			categoryActionLogger.info("ACTION :: RESPONSE is category duplicate ? == > " + assertValue );
			categoryActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		} 
		categoryActionLogger.info("ACTION :: FAILURE RESPONSE is category duplicate ?, == > " + assertValue);
		categoryActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
	}
	
	/**
	 * 
	 * @param categoryId
	 * @return Status.ACCEPTED | Status.NO_CONTENT Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/categoryroll/post/{id}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response assertPostAvailablility( @PathParam("id") Integer categoryId){
		categoryActionLogger.info("*************** ASSERT POST AVAILABILITY FOR THIS CATEGORY ******************  " );
		categoryActionLogger.info("ACTION :: CLIENT categoryId == > " + categoryId );
		boolean assertValue = categoryService.assertPostAvailability(categoryId);
		if(assertValue == false){ 
			categoryActionLogger.info("ACTION :: RESPONSE is post available for category ? == > " + assertValue );
			categoryActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		}
		categoryActionLogger.info("ACTION :: FAILURE RESPONSE is post available for category  ?, == > " + assertValue );
		categoryActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
		
	}
	
}
