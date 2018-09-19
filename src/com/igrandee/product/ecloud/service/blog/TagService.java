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
 * TagService.java
 * Created Jul 18, 2014 5:02:36 PM
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
import com.igrandee.product.ecloud.model.blog.Posttag;
import com.igrandee.product.ecloud.model.blog.Tag;

/**
 * @author balajichander_r
 *
 */
@Named
@Service
@Scope("prototype")
public class TagService extends GenericEntityService<Tag, Integer>{

	 
	@Override
	protected Class<Tag> entityClass() { 
		return Tag.class;
	}
	
	static final Logger tagServiceLogger = Logger.getLogger(TagService.class);

	/**
	 * @param tagId
	 * @return tagList List<?>
	 * @author balajichander_r
	 */
	public List<?> getTagsOf(int tagId) {
		 List<?> tagList = null;
		 Criteria query = null;
 		try {
			
			query = getCurrentSession().createCriteria(Tag.class);
			query.createAlias("this.orgperson", "orgperson");  
			
			query.add(Restrictions.eq("this.id", tagId))
				 .add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'));
			
			query.setProjection(Projections.projectionList()
				  .add(Projections.property("this.id").as("id"))
				  .add(Projections.property("this.name").as("name"))
				  .add(Projections.property("this.description").as("description"))
				  .add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
				  .add(Projections.property("this.status").as("status")) 
					);

			tagList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
 			tagServiceLogger.error("error in TagService for getTagsOf()",e);
		}
		return tagList;
	}

	/**
	 * 
	 * @return  tagList List<?>
	 * @author balajichander_r
	 */
	public List<?> getTags(long organizationid) {
		List<?> tagList = null;
		 Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Tag.class);
			query.createAlias("this.orgperson", "orgperson")
			.createAlias("orgperson.organizationid", "organization");
			query.add(Restrictions.eq("this.status", "A"))
			.add(Restrictions.eq("organization.organizationid", organizationid))
			.add(Restrictions.eq("orgperson.orgpersonstatus", 'A'));
			
			query.setProjection(Projections.projectionList()
				  .add(Projections.property("this.id").as("id"))
				  .add(Projections.property("this.name").as("name"))
				  .add(Projections.property("this.description").as("description"))
				  .add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
				  .add(Projections.property("this.status").as("status")) 
					);

			tagList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
 			tagServiceLogger.error("error in TagService for getTags()",e);
		}
		return tagList;
	}

	/**
	 * 
	 * @param phrase
	 * @return returnValue | boolean
	 * @author balajichander_r
	 */
	public boolean checkDuplicate(String phrase,String orgpersonid){
		boolean returnValue = false;
		Criteria query = null;
		try { 
			
 			query = getCurrentSession().createCriteria(Tag.class);
			query.createAlias("orgperson", "orgperson")
 				.add(Restrictions.eq("this.status", "A"))
 				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.like("this.name", phrase));  
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 			if( ! idList.isEmpty() ){
				returnValue = true;
			} 
			
		} catch (Exception e) {
 			tagServiceLogger.error("error in TagService for checkDuplicate()",e);
		} 
		return returnValue;
	}
	
	
	/**
	 * 
	 * @param titlePhrase
	 * @param skillId
	 * @return returnValue boolean
	 * @author balajichander_r
	 */
	public boolean checkDuplicate(String titlePhrase, int tagId,String orgpersonid){
		Boolean returnValue = false;
		Criteria query = null;
		try {

			query =  getCurrentSession().createCriteria(Tag.class);
			query.createAlias("orgperson", "orgperson")
				.add(Restrictions.eq("this.status", "A"))
				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.not(Restrictions.eq("this.id", tagId)))
			     .add(Restrictions.like("this.name",titlePhrase ).ignoreCase());
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 			if(idList.size()> 0 & ! idList.isEmpty() ){
				returnValue = true;
			}  
			
		} catch (Exception e) {
 			tagServiceLogger.error("error in TagService for checkDuplicate()",e);
		}
		return returnValue;
	}
	
	/**
	 * 
	 * @param tagId
	 * @return  boolean
	 * @author balajichander_r
	 */
	public boolean assertPostAvailability(Integer tagId){
		Criteria query = null;
		List<?> idList = null;
		try { 
			query = getCurrentSession().createCriteria(Posttag.class);
			query.createAlias("this.post","post")
			     .createAlias("this.tag", "tag"); 
			query.add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.eq("post.status", "A"))
			     .add(Restrictions.eq("tag.status", "A"))
			     .add(Restrictions.eq("tag.id", tagId));
			idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
 			tagServiceLogger.error("error in TagService for assertPostAvailability()",e);
		}
		return idList.isEmpty();
	}
	 
}
