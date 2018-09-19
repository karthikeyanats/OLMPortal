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
package com.igrandee.product.ecloud.service.plan;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Plan;

/**
 * @author balajichander_r
 *
 */
@Service
@Named
@Scope("prototype")
public class PlanService extends GenericEntityService<Plan, Integer>{


	@Override
	protected Class<Plan> entityClass() { 
		return Plan.class;
	}

	static final Logger planServiceLogger = Logger.getLogger(PlanService.class);


	/**
	 * 
	 * @param categoryId List<?>
	 * @return planList
	 */
	public List<?> getPlan(String planname,String durationtype){

		List<?> planList = null;
		Criteria query = null;
		try {

			query = getCurrentSession().createCriteria(Plan.class);

			query.add(Restrictions.eq("this.planname", planname))
			.add(Restrictions.eq("this.durationtype", durationtype))
			.add(Restrictions.eq("this.planstatus", 'A'));

			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.planname").as("planname"))
					);

			planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			planServiceLogger.error("error in PlanService for getPlan()",e);
		}
		return planList;

	}

	public List<?> getcheckPlan(String planname,String id){

		List<?> planList = null;
		Criteria query = null;
 		try {

			query = getCurrentSession().createCriteria(Plan.class);

			query.add(Restrictions.eq("this.planname", planname))
			.add(Restrictions.ne("this.id", Integer.parseInt(id)))
			.add(Restrictions.eq("this.planstatus", 'A'));

			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.planname").as("planname"))
					);

			planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 		} catch (Exception e) {
			planServiceLogger.error("error in PlanService for getcheckPlan()",e);
 		}
 		return planList;
 	}

	/**
	 * 
	 * @return planList List<?>
	 */
	public List<?> getPlans(){
		List<?> planList = null;
		Criteria query = null;

		try {
 			query = getCurrentSession().createCriteria(Plan.class);
 			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.planname").as("name"))
					.add(Projections.property("this.maxusers").as("maxusers"))
					.add(Projections.property("this.duration").as("duration")) 
					.add(Projections.property("this.dateofcreation").as("dateofcreation"))
					.add(Projections.property("this.maxcourse").as("maxcourse"))
					.add(Projections.property("this.durationtype").as("durationtype"))
					.add(Projections.property("this.planamount").as("planamount"))
					.add(Projections.property("this.planstatus").as("planstatus")))
					.add(Restrictions.eq("this.planstatus", 'A'));
 
			planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 
		} catch (Exception e) {
			planServiceLogger.error("error in PlanService for getPlans()",e);
		}
 		return planList;
 	} 

 
	public List<?> getBasicplans(){
		List<?> planList = null;
		Criteria query = null;
 		try {
 			query = getCurrentSession().createCriteria(Plan.class);
  			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.planname").as("name"))
					.add(Projections.property("this.maxusers").as("maxusers"))
					.add(Projections.property("this.duration").as("duration")) 
					.add(Projections.property("this.dateofcreation").as("dateofcreation"))
					.add(Projections.property("this.maxcourse").as("maxcourse"))
					.add(Projections.property("this.durationtype").as("durationtype"))
					.add(Projections.property("this.planamount").as("planamount"))
					.add(Projections.property("this.planstatus").as("planstatus")))
					.add(Restrictions.eq("this.planstatus", 'B'));
 
			planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 
		} catch (Exception e) {
			planServiceLogger.error("error in PlanService for getBasicplans()",e);
		}
 		return planList;
 	}
 


	public List<?> getPlanIdList(String id){
		List<?> planList = null;
		Criteria query = null;
 		try {

			query = getCurrentSession().createCriteria(Plan.class); 
 			query.add(Restrictions.eq("this.id", Integer.parseInt(id)));
			query.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.planname").as("name"))
					.add(Projections.property("this.maxusers").as("maxusers"))
					.add(Projections.property("this.duration").as("duration")) 
					.add(Projections.property("this.dateofcreation").as("dateofcreation"))
					.add(Projections.property("this.maxcourse").as("maxcourse"))
					.add(Projections.property("this.durationtype").as("durationtype"))
					.add(Projections.property("this.planamount").as("planamount")))
					.add(Restrictions.eq("this.planstatus",'A'));
 
			planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 
		} catch (Exception e) {
			planServiceLogger.error("error in PlanService for getPlanIdList()",e);
		}
 		return planList;
 	}

}
