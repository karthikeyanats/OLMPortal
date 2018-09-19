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
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.service
 * PostService.java
 * Created Jul 18, 2014 2:59:27 PM
 * by balajichander_r
 */

package com.igrandee.product.ecloud.service.blog;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.blog.Post;

/**
 * @author balajichander_r
 *
 */
 @Named
 @Service
 @Scope("prototype")
public class PostService extends GenericEntityService<Post, Integer>{
	
	
  	@Override
	protected Class<Post> entityClass() { 
		return Post.class;
	}
	
	private static final Logger postServiceLogger = Logger.getLogger(PostService.class);
	
	/**
	 * <br>
	 * Gets the details of post including the list of post comments , skills ,<br>
	 * tags and other details related to a particular post of a particular author<br>
	 * author.<br>
	 * <br>
	 * <br>
	 * Object Tree order<br> 
	 * *****************<br>
	 * <br>
	 * <br>
	 * "this.author", "author" <br>
	 * "this.postcategories", "postcategories"<br>
	 * "postcategories.category", "category"<br>
	 * "this.postskills", "postskills"<br>
	 * "postskills.skill", "skill" <br>
	 * "this.posttags", "posttags" <br>
	 * "posttags.tag", "tag"<br>
	 * <br>
	 * <br>
	 * 
	 * Restrictions<br>
	 * ************ <br>
	 * <br>
	 * <br>
	 * "this.status", "A" <br>
	 * "author.status", "A"<br>
	 * "postcategories.status","A"<br>
	 * "category.status", "A" <br>
	 * "postskills.status","A" <br>
	 * "skill.status", "A" <br>
	 * "posttags.status", "A" <br>
	 * "tag.status", "A"<br>
	 * "this.id", postId (@param)<br>
	 * <br>
	 * 
	 * @param postId
	 * @return postList List<?>
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getPostsOf(int postId , int userId){
		
		List<Post> postList = null;
		Criteria query = null;
		try {
			
			 query = getCurrentSession().createCriteria(Post.class);
			 
			 query.createAlias("this.orgperson", "author") 
			      .createAlias("author.personid","authorPerson")
			 	  .createAlias("this.postcategories", "postcategories")
			 	  .createAlias("postcategories.category", "category")
			 	  .createAlias("this.postskills", "postskills")
			 	  .createAlias("postskills.skill", "skill")
			 	  .createAlias("this.posttags", "posttags")
			 	  .createAlias("posttags.tag", "tag");
			 
			 query.add(Restrictions.eq("this.status", "A"))
			 	  .add(Restrictions.eq("author.orgpersonstatus", 'A'))
			 	  .add(Restrictions.eq("authorPerson.personstatus", 'A'))
			 	  .add(Restrictions.eq("postcategories.status", "A"))
			 	  .add(Restrictions.eq("category.status", "A"))
			 	  .add(Restrictions.eq("postskills.status", "A"))
			 	  .add(Restrictions.eq("skill.status", "A"))
			 	  .add(Restrictions.eq("posttags.status", "A"))
			 	  .add(Restrictions.eq("tag.status", "A"))  
			 	  .add(Restrictions.eq("this.id", postId))
			 	  .add(Restrictions.eq("author.orgpersonid", userId));
			 
			 query.setProjection(Projections.projectionList()
					 
					 //post
					 .add(Projections.property("this.id").as("post_id"))
					 .add(Projections.property("this.title").as("post_title"))
					 .add(Projections.property("this.content").as("post_content")) 
					 .add(Projections.property("this.bannerpath").as("post_bannerpath"))
					 .add(Projections.property("this.commentable").as("post_commentable"))
					 .add(Projections.property("this.date").as("post_date"))
					 .add(Projections.property("this.media").as("post_media")) 
					 .add(Projections.property("this.status").as("post_status")) 
					 //authorperson
					 .add(Projections.property("authorPerson.personid").as("authorPersonPersonId"))
					 .add(Projections.property("authorPerson.firstName").as("authorPersonFirstName"))
					 .add(Projections.property("authorPerson.lastName").as("authorPersonLastName"))
					 .add(Projections.property("authorPerson.middleName").as("authorPersonmiddleName")) 
					 //category 
					 .add(Projections.property("postcategories.id").as("postCategoryId"))
					 .add(Projections.property("postcategories.status").as("postCategoryStatus"))
					 .add(Projections.property("category.id").as("categoryId")) 
					 .add(Projections.property("category.name").as("categoryName"))  
					 //skill
					 .add(Projections.property("postskills.id").as("postSkillsId"))
					 .add(Projections.property("postskills.status").as("postSkillsStatus"))
					 .add(Projections.property("skill.id").as("skillId")) 
					 .add(Projections.property("skill.name").as("skillName")) 
					//tag
					 .add(Projections.property("posttags.id").as("postTagsId"))
					 .add(Projections.property("posttags.status").as("postTagsStatus"))
					 .add(Projections.property("tag.id").as("tagId")) 
					 .add(Projections.property("tag.name").as("tagName"))  
					 
					 );  
			 
			 postList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			 
			   	 	 
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for getPostsOf()",e);
		}
		return postList;
	}
	
	
	/**
	 * <br>
	 * Gets the details of post including the list of post comments , skills ,<br>
	 * tags and other details related to a particular post<br>
	 * author.<br>
	 * <br>
	 * <br>
	 * Object Tree order<br> 
	 * *****************<br>
	 * <br>
	 * <br>
	 * "this.author", "author" <br>
	 * "this.postcategories", "postcategories"<br>
	 * "postcategories.category", "category"<br>
	 * "this.postskills", "postskills"<br>
	 * "postskills.skill", "skill" <br>
	 * "this.posttags", "posttags" <br>
	 * "posttags.tag", "tag"<br>
	 * <br>
	 * <br>
	 * 
	 * Restrictions<br>
	 * ************ <br>
	 * <br>
	 * <br>
	 * "this.status", "A" <br>
	 * "author.status", "A"<br>
	 * "postcategories.status","A"<br>
	 * "category.status", "A" <br>
	 * "postskills.status","A" <br>
	 * "skill.status", "A" <br>
	 * "posttags.status", "A" <br>
	 * "tag.status", "A"<br>
	 * "this.id", postId (@param)<br>
	 * <br>
	 * 
	 * @param postId
	 * @return postList List<?>
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getPostsOf(int postId){
 		List<Post> postList = null;
		Criteria query = null;
		try {
			
			 query = getCurrentSession().createCriteria(Post.class);
			 
			 query.createAlias("this.orgperson", "author") 
			      .createAlias("author.personid","authorPerson")
			 	  .createAlias("this.postcategories", "postcategories")
			 	  .createAlias("postcategories.category", "category")
			 	  .createAlias("this.postskills", "postskills")
			 	  .createAlias("postskills.skill", "skill")
			 	  .createAlias("this.posttags", "posttags")
			 	  .createAlias("posttags.tag", "tag");
			 
			 query.add(Restrictions.eq("this.status", "A"))
			 	  .add(Restrictions.eq("author.orgpersonstatus", 'A'))
			 	  .add(Restrictions.eq("authorPerson.personstatus", 'A'))
			 	  .add(Restrictions.eq("postcategories.status", "A"))
			 	  .add(Restrictions.eq("category.status", "A"))
			 	  .add(Restrictions.eq("postskills.status", "A"))
			 	  .add(Restrictions.eq("skill.status", "A"))
			 	  .add(Restrictions.eq("posttags.status", "A"))
			 	  .add(Restrictions.eq("tag.status", "A"))  
			 	  .add(Restrictions.eq("this.id", postId));
			 
			 query.setProjection(Projections.projectionList()
					 
					 //post
					 .add(Projections.property("this.id").as("post_id"))
					 .add(Projections.property("this.title").as("post_title"))
					 .add(Projections.property("this.content").as("post_content")) 
					 .add(Projections.property("this.bannerpath").as("post_bannerpath"))
					 .add(Projections.property("this.commentable").as("post_commentable"))
					 .add(Projections.property("this.date").as("post_date"))
					 .add(Projections.property("this.media").as("post_media")) 
					 .add(Projections.property("this.status").as("post_status")) 
					 //authorperson
					 .add(Projections.property("authorPerson.personid").as("authorPersonPersonId"))
					 .add(Projections.property("authorPerson.firstName").as("authorPersonFirstName"))
					 .add(Projections.property("authorPerson.lastName").as("authorPersonLastName"))
					 .add(Projections.property("authorPerson.middleName").as("authorPersonmiddleName")) 
					 //category
					 .add(Projections.property("category.id").as("categoryId")) 
					 .add(Projections.property("category.name").as("categoryName"))  
					 //skill
					 .add(Projections.property("skill.id").as("skillId")) 
					 .add(Projections.property("skill.name").as("skillName"))  
					//tag
					 .add(Projections.property("tag.id").as("tagId")) 
					 .add(Projections.property("tag.name").as("tagName")) 
					 
					 );  
			 
			 postList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
			   	 	 
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for getPostsOf()",e);
		}   
		return postList;
	}
	
	/**
	 * Get all blogs regardless of any constraint.
	 * <br>
	 * Gets the details of post including the list of post comments , skills ,<br>
	 * tags and other details related to a particular post of a particular post<br>
	 * author.<br>
	 * <br>
	 * <br>
	 * Object Tree order<br> 
	 * *****************<br>
	 * <br>
	 * <br>
	 * "this.author", "author" <br>
	 * "this.postcategories", "postcategories"<br>
	 * "postcategories.category", "category"<br>
	 * "this.postskills", "postskills"<br>
	 * "postskills.skill", "skill" <br>
	 * "this.posttags", "posttags" <br>
	 * "posttags.tag", "tag"<br>
	 * <br>
	 * <br>
	 * 
	 * Restrictions<br>
	 * ************ <br>
	 * <br>
	 * <br>
	 * "this.status", "A" <br>
	 * "author.status", "A"<br>
	 * "postcategories.status","A"<br>
	 * "category.status", "A" <br>
	 * "postskills.status","A" <br>
	 * "skill.status", "A" <br>
	 * "posttags.status", "A" <br>
	 * "tag.status", "A"<br> 
	 * <br>
	 * @return postsList List<?>
	 * @author balajichander_r
	 */
	public List<?> getPosts(long organizationid) {
		 
		List<?> postsList = null;
		Criteria query = null;
		try { 
			query = getCurrentSession().createCriteria(Post.class);  
			//aliases
			query.createAlias("this.orgperson", "author")
				 .createAlias("author.organizationid", "organization")
				 .createAlias("author.personid", "orgperson");
			//restrictions
			query.add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("author.orgpersonstatus", 'A'))
				 .add(Restrictions.eq("orgperson.personstatus", 'A'))
				 .add(Restrictions.eq("organization.organizationid", organizationid));  
			
			
			query.setProjection(Projections.projectionList()
					//post projections
					.add(Projections.distinct(Projections.property("this.id").as("id")))
					.add(Projections.property("this.bannerpath").as("bannerpath"))
					.add(Projections.property("this.commentable").as("commentable"))
					.add(Projections.property("this.content").as("content"))
					.add(Projections.property("this.date").as("date"))
					.add(Projections.property("this.media").as("media"))
					.add(Projections.property("this.status").as("status"))
					.add(Projections.property("this.title").as("title"))
					
					//author projections
					.add(Projections.property("author.orgpersonid").as("authororgpersonid")) 
					.add(Projections.property("author.orgpersonstatus").as("authororgpersonstatus"))
					
					.add(Projections.property("orgperson.personid").as("orgpersonId"))
					.add(Projections.property("orgperson.firstName").as("orgpersonFirstName"))
					.add(Projections.property("orgperson.lastName").as("orgpersonLastName"))
					.add(Projections.property("orgperson.middleName").as("orgpersonMiddleName"))
					.add(Projections.property("orgperson.personphotourl").as("orgpersonPhotourl")) 
					 
					
					);
			 
			Order order  = Order.desc("this.date");
			query.addOrder(order);
			postsList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();  
			 
		} catch (Exception e) {
			e.printStackTrace();
			postServiceLogger.error("error in PostService for getPosts()",e);
		}
		return postsList;
	}
	
	/**
	 * @param  orgpersonId int
	 * @return postsList List<?>
	 * @author balajichander_r
	 */
	public List<?> getPosts(int organizationid) {
 		List<?> postsList = null;
		Criteria query = null;
		try { 
			query = getCurrentSession().createCriteria(Post.class);  
			//aliases
			query.createAlias("this.orgperson", "author")
				 .createAlias("author.organizationid", "organization")
				 .createAlias("orgperson.personid", "orgperson")
				 .createAlias("this.postcategories", "postcategories") 
				 .createAlias("postcategories.category" , "category")
				 .createAlias("this.postskills", "postskills")
				 .createAlias("postskills.skill", "skill")
				 .createAlias("this.posttags", "posttags")
				 .createAlias("posttags.tag","tag");
			//restrictions
			query.add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("author.orgpersonstatus", 'A'))
				 .add(Restrictions.eq("orgperson.personstatus", 'A'))
				 .add(Restrictions.eq("postcategories.status", "A"))
				 .add(Restrictions.eq("category.status", "A"))
				 .add(Restrictions.eq("postskills.status", "A"))
				 .add(Restrictions.eq("skill.status", "A"))
				 .add(Restrictions.eq("posttags.status", "A"))
				 .add(Restrictions.eq("tag.status", "A"))     
			 	 .add(Restrictions.eq("organization.organizationid", organizationid));  
			
			
			query.setProjection(Projections.projectionList()
					//post projections
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.bannerpath").as("bannerpath"))
					.add(Projections.property("this.commentable").as("commentable"))
					.add(Projections.property("this.content").as("content"))
					.add(Projections.property("this.date").as("date"))
					.add(Projections.property("this.media").as("media"))
					.add(Projections.property("this.status").as("status"))
					.add(Projections.property("this.title").as("title"))
					
					//author projections
					.add(Projections.property("author.orgpersonid").as("authororgpersonid")) 
					.add(Projections.property("author.orgpersonstatus").as("authororgpersonstatus"))
					
					.add(Projections.property("orgperson.personid").as("orgpersonId"))
					.add(Projections.property("orgperson.firstName").as("orgpersonFirstName"))
					.add(Projections.property("orgperson.lastName").as("orgpersonLastName"))
					.add(Projections.property("orgperson.middleName").as("orgpersonMiddleName"))
					.add(Projections.property("orgperson.personphotourl").as("orgpersonPhotourl")) 
					
					
					//post category
					.add(Projections.property("postcategories.id").as("postCategoryId"))
					.add(Projections.property("postcategories.status").as("postCategoryStatus"))
					
					//category
					.add(Projections.property("category.id").as("categoryId"))
					.add(Projections.property("category.description").as("categoryDescription"))
					.add(Projections.property("category.name").as("categoryName"))
					.add(Projections.property("category.status").as("categoryStatus"))
					
					//post skills
					.add(Projections.property("postskills.id").as("postSkillId"))
					.add(Projections.property("postskills.status").as("postSkillStatus"))
					
					//skills
					.add(Projections.property("skill.id").as("skillId"))
					.add(Projections.property("skill.description").as("skillDescription"))
					.add(Projections.property("skill.mediapath").as("skillMediapath"))
					.add(Projections.property("skill.name").as("skillName"))
					.add(Projections.property("skill.status").as("skillStatus"))
					
					//post tags
					.add(Projections.property("posttags.id").as("postTagId"))
					.add(Projections.property("posttags.status").as("postTagStatus"))
					
					//tags
					.add(Projections.property("tag.id").as("tagId"))
					.add(Projections.property("tag.name").as("tagName"))
					.add(Projections.property("tag.description").as("tagDescription"))
					.add(Projections.property("tag.status").as("tagStatus"))
					
					);
			 
			postsList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();  
			 
			
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for getPosts()",e);
		}
		return postsList;
	}
	
	
	/**
	 * Get all the category ids of a post written by a particular author.
	 * 
	 * @param postId
	 * @param orgPersonId
	 * @return List<?>
	 */
	public List<?> getPostCategoriesId(int postId, int orgPersonId){
		List<?> categoriesIdList = null;
		Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Post.class);
			
			query.createAlias("this.postcategories", "postcategories")
				 .createAlias("postcategories.category" , "category")
				 .createAlias("this.orgperson", "author");
			
			query.add(Restrictions.eq("this.id", postId))
				 .add(Restrictions.eq("author.orgpersonid", orgPersonId))
				 .add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("postcategories.status", "A"))
				 .add(Restrictions.eq("category.status", "A"));
			query.setProjection(Projections.projectionList()
					.add(Projections.property("category.id").as("id")));
			
			categoriesIdList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for getPostCategoriesId()",e);
		}
		return categoriesIdList;
		
	}
	
	/**
	 *  
	 * @param phrase 
	 * @return returnvalue | boolean
	 * @author balajichander_r
	 */
	public boolean checkDuplicate(String phrase){
		Boolean returnvalue = false;
		Criteria query =  null;
		try {
 			query = getCurrentSession().createCriteria(Post.class);
			query.add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.like("this.title", phrase).ignoreCase());  
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 			if(! idList.isEmpty()){
				returnvalue = true;
			} 
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for checkDuplicate()",e);
		} 
		return returnvalue;
	}
	
	/**
	 * 
	 * @param phrase
	 * @param id
	 * @return
	 * @author balajichander_r
	 */
	public boolean checkDuplicate(String phrase, int id){
		Boolean returnValue = false;
		Criteria query = null;
		try {
			query = getCurrentSession().createCriteria(Post.class);
			query.add(Restrictions.eq("this.status", "A"))
		     	 .add(Restrictions.like("this.title", phrase).ignoreCase())
		     	 .add(Restrictions.not(Restrictions.eq("this.id", id)));  
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 			if(! idList.isEmpty()){
				returnValue = true;
			}
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for checkDuplicate()",e);
		}
		return returnValue;
	}
	
	
	/**
	 * 
	 * @param postid
	 * @return 
	 */
	public int deletePost(int postid){
		
		try {
			postid = getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.blog.Post set status='D' where id="+postid+"").executeUpdate();
		} catch (Exception e) {
			postServiceLogger.error("error in PostService for deletePost()",e);
		}
	 return postid;
	}
	 
 }
