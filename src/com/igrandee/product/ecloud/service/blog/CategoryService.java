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
 * CategoryService.java
 * Created Jul 23, 2014 11:03:31 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.service.blog;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.blog.Category;
import com.igrandee.product.ecloud.model.blog.Postcategory;

/**
 * @author balajichander_r
 *
 */
@Service
@Named
@Scope("prototype")
public class CategoryService extends GenericEntityService<Category, Integer>{

	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<Category> entityClass() { 
		return Category.class;
	}
	
	static final Logger categoryServiceLogger = Logger.getLogger(CategoryService.class);
	
	/**
	 * 
	 * @param categoryId List<?>
	 * @return categoryList
	 * @author balajichander_r
	 */
	public List<?> getCategory(int categoryId){
		
		List<?> categoryList = null;
		Criteria query = null;
 		try {
			
		 query = getCurrentSession().createCriteria(Category.class);
		 		query.createAlias("this.orgperson", "orgperson");
		 		query.add(Restrictions.eq("this.id", categoryId))
		 		     .add(Restrictions.eq("this.status", "A"))
		 		     .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'));
		 		
		 		query.setProjection(Projections.projectionList()
		 			 .add(Projections.property("this.id").as("id"))
		 			 .add(Projections.property("this.name").as("name"))
		 			 .add(Projections.property("this.description").as("description"))
		 			 .add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
		 			 .add(Projections.property("this.status").as("status")) 
		 				);
		         
		 categoryList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
  			if(categoryServiceLogger.isInfoEnabled())	{
  				categoryServiceLogger.error("error in getCategory() in categoryServiceLogger",e);
			}			  
		}
		
		return categoryList;
		
	}
	
	/**
	 * 
	 * @return categoryList List<?>
	 * @author balajichander_r
	 */
	public List<?> getCategories(long organizationid){
		List<?> categoryList = null;
		Criteria query = null;
		
		try {
			
		 query = getCurrentSession().createCriteria(Category.class);
		         query.createAlias("this.orgperson", "orgperson")
		         .createAlias("orgperson.organizationid", "organization");
		 		
		 		 query.add(Restrictions.eq("this.status", "A"))
		 		 	  .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'))
		 		 	  .add(Restrictions.eq("organization.organizationid", organizationid));	
		 	
		 		 query.setProjection(Projections.projectionList()
		 			 .add(Projections.property("this.id").as("id"))
		 			 .add(Projections.property("this.name").as("name"))
		 			 .add(Projections.property("this.description").as("description"))
		 			 .add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
		 			 .add(Projections.property("this.status").as("status")) 
		 				);
		         
		 categoryList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 			
		} catch (Exception e) {
			if(categoryServiceLogger.isInfoEnabled())	{
  				categoryServiceLogger.error("error in getCategories() in categoryServiceLogger",e);
			}
		}
		
		return categoryList;
		
	}
	
	/**
	 * 
	 * @param titlePhrase
	 * @return returnValue | boolean
	 * @author balajichander_r
	 */
	public boolean checkDuplicate(String titlePhrase,String orgpersonid){
		Boolean returnValue = false;
		Criteria query = null;
		try {

			query =  getCurrentSession().createCriteria(Category.class);
			query.createAlias("orgperson", "orgperson")
			 .add(Restrictions.eq("this.status", "A"))
			 .add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.like("this.name",titlePhrase ).ignoreCase());
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 			if(idList.size()> 0 & ! idList.isEmpty() ){
				returnValue = true;
			}  
			
		} catch (Exception e) {
			if(categoryServiceLogger.isInfoEnabled())	{
  				categoryServiceLogger.error("error in checkDuplicate() in categoryServiceLogger",e);
			}
		}
		return returnValue;
	}
	 
	
	/**
	 * 
	 * @param titlePhrase
	 * @param categoryId
	 * @return returnValue boolean
	 */
	public boolean checkDuplicate(String titlePhrase, int categoryId,String orgpersonid){
		Boolean returnValue = false;
		Criteria query = null;
		try {

			query =  getCurrentSession().createCriteria(Category.class);
			query.createAlias("orgperson", "orgperson")
			.add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.not(Restrictions.eq("this.id", categoryId)))
			      .add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.like("this.name",titlePhrase ).ignoreCase());
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 			if(idList.size()> 0 & ! idList.isEmpty() ){
				returnValue = true;
			}  
			
		} catch (Exception e) {
			if(categoryServiceLogger.isInfoEnabled())	{
  				categoryServiceLogger.error("error in checkDuplicate() in categoryServiceLogger",e);
			}
		}
		return returnValue;
	}
	
	
	/**
	 * 
	 * @param categoryId
	 * @return returnValue Boolean
	 * @author balajichander_r
	 */
	public boolean assertPostAvailability(Integer categoryId){
		Criteria query = null;
		List<?> idList = null;
		try { 
			query = getCurrentSession().createCriteria(Postcategory.class);
			query.createAlias("this.post","post")
			     .createAlias("this.category", "category"); 
			query.add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.eq("post.status", "A"))
			     .add(Restrictions.eq("category.status", "A"))
			     .add(Restrictions.eq("category.id", categoryId));
			idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
 			if(categoryServiceLogger.isInfoEnabled())	{
  				categoryServiceLogger.error("error in assertPostAvailability() in categoryServiceLogger",e);
			}
		}
		return idList.isEmpty();
	}
}
