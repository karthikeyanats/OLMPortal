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
 * SkillService.java
 * Created Jul 23, 2014 11:03:20 AM
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
import com.igrandee.product.ecloud.model.blog.Postskill;
import com.igrandee.product.ecloud.model.blog.Skill;

/**
 * @author balajichander_r
 *
 */
@Named
@Service
@Scope("prototype")
public class SkillService extends GenericEntityService<Skill, Integer>{

	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<Skill> entityClass() { 
		return Skill.class;
	}
	
	final static Logger skillServiceLogger = Logger.getLogger(SkillService.class); 

	/**
	 * 
	 * @param skillId
	 * @return skillList List<?>
	 * @author balajichander_r
	 */
	public List<?> getSkillOf(int skillId) {
		
		List<?> skillList = null;
		Criteria query =  null;
 		try {
			
			query = getCurrentSession().createCriteria(Skill.class);
			
			query.createAlias("this.orgperson", "orgperson");
			query.add(Restrictions.eq("this.id",  skillId))
			     .add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'));
			
			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.description").as("description"))
					.add(Projections.property("this.mediapath").as("mediapath"))
					.add(Projections.property("this.name").as("name"))
					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.property("this.status").as("status"))
					);
			
			skillList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
			skillServiceLogger.error("error in SkillService for getSkillOf()",e);
		}
		
		return skillList;
	}
	
	
	/**
	 *
	 * @return skillList List<?>
	 * @author balajichander_r
	 */
	public List<?> getSkills(long organizationid) {
		
		List<?> skillList = null;
		Criteria query =  null; 
		try {
			
			query = getCurrentSession().createCriteria(Skill.class);
			query.createAlias("this.orgperson", "orgperson")
			.createAlias("orgperson.organizationid", "organization");
			query.add(Restrictions.eq("this.status", "A"))
			.add(Restrictions.eq("orgperson.orgpersonstatus", 'A'))
			.add(Restrictions.eq("organization.organizationid", organizationid));
			
			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.description").as("description"))
					.add(Projections.property("this.mediapath").as("mediapath"))
					.add(Projections.property("this.name").as("name"))
					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.property("this.status").as("status"))
					);
			
			skillList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
			
 		} catch (Exception e) {
			skillServiceLogger.error("error in SkillService for getSkillOf()",e);
		}
		
		return skillList;
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
			
 			query = getCurrentSession().createCriteria(Skill.class);
			query.createAlias("orgperson", "orgperson")
 				.add(Restrictions.eq("this.status", "A"))
 				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.like("this.name", phrase));  
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 			if( ! idList.isEmpty() ){
				returnValue = true;
			} 
			
		} catch (Exception e) {
			skillServiceLogger.error("error in SkillService for checkDuplicate()",e);
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
	public boolean checkDuplicate(String titlePhrase, int skillId,String orgpersonid){
		Boolean returnValue = false;
		Criteria query = null;
		try {

			query =  getCurrentSession().createCriteria(Skill.class);
			query.createAlias("orgperson", "orgperson")
				.add(Restrictions.eq("this.status", "A"))
				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
			     .add(Restrictions.not(Restrictions.eq("this.id", skillId)))
			     .add(Restrictions.like("this.name",titlePhrase ).ignoreCase());
			List<?> idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 			if(idList.size()> 0 & ! idList.isEmpty() ){
				returnValue = true;
			}  
			
		} catch (Exception e) {
			skillServiceLogger.error("error in SkillService for checkDuplicate()",e);
		}
		return returnValue;
	}
	
	/**
	 * 
	 * @param tagId
	 * @return  boolean
	 * @author balajichander_r
	 */
	public boolean assertPostAvailability(Integer skillId){
		Criteria query = null;
		List<?> idList = null;
		try { 
			query = getCurrentSession().createCriteria(Postskill.class);
			query.createAlias("this.post","post")
			     .createAlias("this.skill", "skill"); 
			query.add(Restrictions.eq("this.status", "A"))
			     .add(Restrictions.eq("post.status", "A"))
			     .add(Restrictions.eq("skill.status", "A"))
			     .add(Restrictions.eq("skill.id", skillId));
			idList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
			skillServiceLogger.error("error in SkillService for assertPostAvailability()",e);
		}
		return idList.isEmpty();
	}
	
}
