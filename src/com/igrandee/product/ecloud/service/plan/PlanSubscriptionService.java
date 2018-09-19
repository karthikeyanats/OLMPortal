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
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Orgplancourse;
import com.igrandee.product.ecloud.model.Orgplansubscription;
import com.igrandee.product.ecloud.model.Orgplanuser;

/**
 * @author balajichander_r
 *
 */
@Service
@Named
@Scope("prototype")
public class PlanSubscriptionService extends GenericEntityService<Orgplansubscription, Integer>{

	 
	@Override
	protected Class<Orgplansubscription> entityClass() { 
		return Orgplansubscription.class;
	}
	
	static final Logger planServiceLogger = Logger.getLogger(PlanSubscriptionService.class);
	
	
	/**
	 * 
	 * @param categoryId List<?>
	 * @return planList
	 */
	public List<?> getPlanSubscription(int id){
		
		List<?> planList = null;
		Criteria query = null;
		try {
			
		 query = getCurrentSession().createCriteria(Orgplansubscription.class);
 		 		query.add(Restrictions.eq("this.id", id));
 		 		query.setProjection(Projections.projectionList()
		 			 .add(Projections.property("this.id").as("id"))
		 			 .add(Projections.property("this.dateofsubscription").as("dateofsubscription"))
		 			 .add(Projections.property("this.planenddate").as("planenddate")) 
		 			 .add(Projections.property("this.planstartdate").as("planstartdate"))
		 				);
		         
		 planList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			 planServiceLogger.error("error in PlanSubscriptionService for this getPlanSubscription() ",e);
 		}
 		return planList;
 	}
	
	
	/**
	 * 
	 * @return planList List<?>
	 */
	public List<?> getPlanSubscriptions(String orgplanstatus,long orgid){
		List<?> planList = null;
		
		try {
			planList = getCurrentSession().createCriteria(Orgplansubscription.class)
					 .createAlias("this.plan","plan")
					 .createAlias("this.organization","organization")
				 .add(Restrictions.eq("this.orgplanstatus",orgplanstatus))
				 .add(Restrictions.eq("organization.organizationid",orgid))
		 			.setProjection(Projections.projectionList()
				 			 .add(Projections.property("this.id").as("id"))
				 			 .add(Projections.property("organization.organizationid").as("orgid"))
				 			 .add(Projections.property("this.dateofsubscription").as("dateofsubscription"))
				 			 .add(Projections.property("this.planenddate").as("planenddate"))
				 			 .add(Projections.property("plan.id").as("planid"))
				 			  .add(Projections.property("this.planstartdate").as("planstartdate"))
				 			 .add(Projections.property("plan.maxcourse").as("maxcourse"))
				 			  .add(Projections.property("plan.maxusers").as("maxusers")))
				 			 .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getPlanSubscriptions ",e);
		}
 		return planList;
 	}
	 
	
	public List<?> getOrgplanlist(Long orgid){
		List<?> orgPlanlist = null;
		  
		try {
			orgPlanlist=getCurrentSession().createSQLQuery("select orgplansub1_.id as y0_, count(distinct course5_.courseid) as publishedcoursecount, " +
					" (select count(a.orgplansubscriptionid) from orgplanuser a right outer join orgplansubscription orgplansub1_ on a.orgplansubscriptionid=orgplansub1_.id " +
					" inner join organization organizati2_ on orgplansub1_.orgid=organizati2_.organizationid " +
					" inner join plan plan3_ on orgplansub1_.planid=plan3_.id where organizati2_.organizationid='"+orgid+"'" +
					" and organizati2_.orgstatus='A' and orgplansub1_.orgplanstatus in ('A', 'T') and " +
					" (plan3_.planstatus='A' or plan3_.planstatus='B')) as inviteduserscount, orgplansub1_.id as id, " +
					" organizati2_.organizationid as organizationid, organizati2_.orgname as orgname, orgplansub1_.dateofsubscription as dateofsubscription, " +
					" orgplansub1_.planstartdate as planstartdate, orgplansub1_.planenddate as planenddate, plan3_.id as planid, " +
					" plan3_.planname as planname, plan3_.planamount as planamount, plan3_.dateofcreation as dateofcreation, " +
					" plan3_.duration as duration, plan3_.durationtype as durationtype, plan3_.maxcourse as maxcourse, plan3_.maxusers as maxusers " +
					" from orgplancourse this_ left outer join course course5_ on this_.courseid=course5_.courseid " +
					" right outer join orgplansubscription orgplansub1_ on this_.orgplansubscriptionid=orgplansub1_.id " +
					" inner join organization organizati2_ on orgplansub1_.orgid=organizati2_.organizationid inner join plan plan3_ " +
					" on orgplansub1_.planid=plan3_.id where organizati2_.organizationid='"+orgid+"' and organizati2_.orgstatus='A' and " +
					" orgplansub1_.orgplanstatus in ('A', 'T') and (plan3_.planstatus='A' or plan3_.planstatus='B') group by orgplansub1_.id")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			/*orgPlanlist=	getCurrentSession().createCriteria(Orgplancourse.class)
								.createAlias("orgplansubscription", "orgplansubscription",JoinType.RIGHT_OUTER_JOIN)
								.createAlias("orgplansubscription.organization", "organization")
								.createAlias("orgplansubscription.plan", "plan")
								.createAlias("orgplansubscription.orgplanusers", "orgplanuser")
								.createAlias("course", "course",JoinType.LEFT_OUTER_JOIN,Restrictions.eq("course.coursestatus", "A"))
								//.createAlias("course.courseenrollments", "courseenrollments",JoinType.LEFT_OUTER_JOIN,Restrictions.in("courseenrollmentstatus", new String[]{"A","C","B","P"}))
								.add(Restrictions.eq("organization.organizationid", orgid))
								.add(Restrictions.eq("organization.orgstatus", 'A'))
								.add(Restrictions.in("orgplansubscription.orgplanstatus", new String[]{"A","T"}))
								.add(Restrictions.or(Restrictions.eq("plan.planstatus", 'A'), Restrictions.eq("plan.planstatus", 'B')))								
								.setProjection(Projections.projectionList()
									.add(Projections.groupProperty("orgplansubscription.id"))
									.add(Projections.countDistinct("course.courseid").as("publishedcoursecount"))
									//.add(Projections.count("courseenrollments.courseenrollmentid").as("inviteduserscount"))
									.add(Projections.count("orgplanuser.id").as("inviteduserscount"))
									.add(Projections.property("orgplansubscription.id").as("id"))
									.add(Projections.property("organization.organizationid").as("organizationid"))
									.add(Projections.property("organization.orgname").as("orgname"))
									.add(Projections.property("orgplansubscription.dateofsubscription").as("dateofsubscription"))
									.add(Projections.property("orgplansubscription.planstartdate").as("planstartdate"))
									.add(Projections.property("orgplansubscription.planenddate").as("planenddate"))
									.add(Projections.property("plan.id").as("planid"))
									.add(Projections.property("plan.planname").as("planname"))
									.add(Projections.property("plan.planamount").as("planamount"))
									.add(Projections.property("plan.dateofcreation").as("dateofcreation"))
									.add(Projections.property("plan.duration").as("duration"))
									.add(Projections.property("plan.durationtype").as("durationtype"))
									.add(Projections.property("plan.maxcourse").as("maxcourse"))
									.add(Projections.property("plan.maxusers").as("maxusers"))
									.add(Projections.property("plan.duration").as("duration")))
									.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();*/
					         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getOrgplanlist ",e);
		}
 		return orgPlanlist;
 	}
	
	public List<?> getCoursesPublished(int orgplansubscriptionid){
		return	getCurrentSession().createCriteria(Orgplancourse.class)
				.createAlias("course", "course")
				.createAlias("course.prices", "prices")
				.createAlias("orgplansubscription", "orgplansubscription")
				.createAlias("orgplansubscription.plan", "plan")
				/*.add(Restrictions.eq("course.coursestatus", "A"))*/
				.add(Restrictions.eq("orgplansubscription.id", orgplansubscriptionid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("course.coursetitle").as("coursetitle"))
						.add(Projections.property("course.coursestatus").as("coursestatus"))
						.add(Projections.property("prices.price").as("price")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
	}
	
	public List<?> getEnrolledUsers(int orgplansubscriptionid){
		
		return	getCurrentSession().createCriteria(Orgplanuser.class)
				.createAlias("orgplansubscription", "orgplansubscription",JoinType.RIGHT_OUTER_JOIN)
				.createAlias("orgplansubscription.plan", "plan")
				.createAlias("orgplansubscription.organization", "organization")
				.createAlias("courseinvitation", "courseinvitation")
				.add(Restrictions.eq("orgplansubscription.id", orgplansubscriptionid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("courseinvitation.emailid").as("emailid"))
						.add(Projections.property("organization.orgname").as("orgname"))
						.add(Projections.property("courseinvitation.courseinvitationstatus").as("courseinvitationstatus")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		/*return	getCurrentSession().createCriteria(Orgplancourse.class)
				.createAlias("orgplansubscription", "orgplansubscription",JoinType.RIGHT_OUTER_JOIN)
				.createAlias("course", "course")
				.createAlias("course.courseenrollments", "courseenrollments",JoinType.INNER_JOIN,Restrictions.in("courseenrollments.courseenrollmentstatus", new String[]{"A","B","C","P"}))
				.createAlias("courseenrollments.orgperson", "orgperson")
				.createAlias("orgperson.personid", "person")
				.createAlias("person.contactinfo", "contactinfo")
				.createAlias("contactinfo.emails", "emails")
				.add(Restrictions.eq("orgplansubscription.id", orgplansubscriptionid))
				.setProjection(Projections.projectionList()
						.add(Projections.property("emails.userid").as("emailid")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();*/
		
	}
	
	public List<?> getSubscripPlanList(int id){
		List<?> orgPlanlist = null;
		  
		try {
 			orgPlanlist = getCurrentSession().createCriteria(Orgplansubscription.class)
					 .createAlias("this.plan","plan") 
					 .createAlias("this.organization","organization")
					 .add(Restrictions.eq("plan.id", id))
		 			.setProjection(Projections.projectionList()
				 			 .add(Projections.property("this.id").as("id"))
				 			 .add(Projections.property("organization.organizationid").as("orgid"))
				 			 	 .add(Projections.property("organization.orgname").as("orgname"))
				 			 .add(Projections.property("this.planenddate").as("planenddate"))
				 			 .add(Projections.property("plan.id").as("planid"))
				 			 .add(Projections.property("plan.planname").as("planname"))
				 			  .add(Projections.property("this.planstartdate").as("planstartdate"))
				 			  .add(Projections.property("plan.maxcourse").as("maxcourse"))
				 			  .add(Projections.property("plan.maxusers").as("maxusers"))
				 			  .add(Projections.property("plan.durationtype").as("durationtype"))
				 			  .add(Projections.property("plan.duration").as("duration")))
				 			 .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getSubscripPlanList ",e);
		}
		
		return orgPlanlist;
 	}
	
	public List<?> getSubscriptionPlanList(){
		List<?> orgPlanlist = null;
		  
		try {
			
			/*orgPlanlist = getCurrentSession().createCriteria(Orgplansubscription.class)
					 .createAlias("this.plan","plan") 
					 .createAlias("this.organization","organization")
					 .add(Restrictions.in("plan.planstatus", new Character[]{'A','B'}))
		 			.setProjection(Projections.projectionList()
				 			 .add(Projections.property("this.id").as("id"))
				 			 .add(Projections.property("organization.organizationid").as("orgid"))
				 			 	 .add(Projections.property("organization.orgname").as("orgname"))
				 			 .add(Projections.property("this.dateofsubscription").as("dateofsubscription"))
				 			 .add(Projections.property("this.planenddate").as("planenddate"))
				 			 .add(Projections.property("plan.id").as("planid"))
				 			 .add(Projections.property("plan.planname").as("planname"))
				 			 .add(Projections.property("plan.planamount").as("planamount"))
				 			 .add(Projections.property("plan.dateofcreation").as("dateofcreation"))
				 			  .add(Projections.property("this.planstartdate").as("planstartdate"))
				 			  .add(Projections.property("plan.maxcourse").as("maxcourse"))
				 			  .add(Projections.property("plan.maxusers").as("maxusers"))
				 			  .add(Projections.property("plan.durationtype").as("durationtype"))
				 			  .add(Projections.property("plan.duration").as("duration")))
				 			 .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();*/
			
			orgPlanlist=getCurrentSession().createSQLQuery("select count(distinct course4_.courseid) as noofcourses, " +
					/*"count(courseenro5_.courseenrollmentid) as noofusers, */ " orgplansub1_.id as id,orgplansub1_.dateofsubscription as dateofsubscription, organizati2_.organizationid as organizationid, " +
					"organizati2_.orgname as orgname, plan3_.id as planid, plan3_.planname as planname, plan3_.durationtype as durationtype, plan3_.duration as duration, plan3_.planamount as planamount," +
					"orgplansub1_.planstartdate as planstartdate, orgplansub1_.planenddate as planenddate, plan3_.maxcourse as maxcourse, " +
					"plan3_.maxusers as maxusers from orgplancourse this_ left outer join course course4_ on this_.courseid=course4_.courseid " +
					/*and ( course4_.coursestatus='A' )*/" left outer join courseenrollment courseenro5_ on course4_.courseid=courseenro5_.courseid and " +
					"( courseenro5_.courseenrollmentstatus in ('A', 'C','B','P') ) right outer join orgplansubscription orgplansub1_ on " +
					"this_.orgplansubscriptionid=orgplansub1_.id inner join organization organizati2_ on " +
					"orgplansub1_.orgid=organizati2_.organizationid inner join plan plan3_ on orgplansub1_.planid=plan3_.id and plan3_.planstatus in ('A','B') where " +
					"organizati2_.orgstatus='A' and orgplansub1_.orgplanstatus in ('A','T') group by orgplansub1_.id order by organizati2_.organizationid desc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getSubscriptionPlanList ",e); 
		}
 		return orgPlanlist;
 	}
	/**
	 * @author muniyarasu_a
	 * @return getorgplancourse list
	 * get max course and user	 
	 */
	public List<?> getorgplancourse(int orgplansubscriptionid){
		List<?> getorgplancourselist = null;
		  
		try {
			
			getorgplancourselist = getCurrentSession().createCriteria(Orgplancourse.class)
					.createAlias("this.orgplansubscription","orgplansubscription") 
					.createAlias("orgplansubscription.plan","plan") 
					 .createAlias("orgplansubscription.organization","organization")
					 .add(Restrictions.eq("orgplansubscription.orgplanstatus", "A"))
					 .add(Restrictions.eq("orgplansubscription.id", orgplansubscriptionid))
		 			.setProjection(Projections.projectionList()
		 					.add(Projections.property("this.id").as("id")) 
		 					.add(Projections.property("orgplansubscription.id").as("orgplansubscriptionid"))
				 			 .add(Projections.property("organization.organizationid").as("orgid"))
				 			 	 .add(Projections.property("organization.orgname").as("orgname"))
				 			 .add(Projections.property("orgplansubscription.dateofsubscription").as("dateofsubscription"))
				 			 .add(Projections.property("orgplansubscription.planenddate").as("planenddate"))
				 			 .add(Projections.property("plan.id").as("planid"))
				 			 .add(Projections.property("plan.planname").as("planname"))
				 			 .add(Projections.property("plan.planamount").as("planamount"))
				 			 .add(Projections.property("plan.dateofcreation").as("dateofcreation"))
				 			  .add(Projections.property("orgplansubscription.planstartdate").as("planstartdate"))
				 			  .add(Projections.property("plan.maxcourse").as("maxcourse"))
				 			  .add(Projections.property("plan.maxusers").as("maxusers"))
				 			  .add(Projections.property("plan.durationtype").as("durationtype"))
				 			  .add(Projections.property("plan.duration").as("duration")))
				 			 .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getSubscriptionPlanList ",e); 
		}
 		return getorgplancourselist;
 	}
	/**
	 * @author muniyarasu_a
	 * @return getorgplancourse list
	 * get max course and user	 
	 */
	public List<?> getorgplanuser(int orgplansubscriptionid){
		List<?> getorgplancourselist = null;
		  
		try {
			
			getorgplancourselist = getCurrentSession().createCriteria(Orgplanuser.class)
					.createAlias("this.orgplansubscription","orgplansubscription") 
					.createAlias("orgplansubscription.plan","plan") 
					//.createAlias("courseinvitation", "courseinvitation")
					//.createAlias("courseinvitation.course", "course")
					 .createAlias("orgplansubscription.organization","organization")
					 .add(Restrictions.eq("orgplansubscription.orgplanstatus", "A"))
					 .add(Restrictions.eq("orgplansubscription.id", orgplansubscriptionid))
		 			.setProjection(Projections.projectionList()
		 					.add(Projections.property("this.id").as("id")) 
		 					.add(Projections.property("orgplansubscription.id").as("orgplansubscriptionid"))
				 			 .add(Projections.property("organization.organizationid").as("orgid"))
				 			 	 .add(Projections.property("organization.orgname").as("orgname"))
				 			 .add(Projections.property("orgplansubscription.dateofsubscription").as("dateofsubscription"))
				 			 .add(Projections.property("orgplansubscription.planenddate").as("planenddate"))
				 			 .add(Projections.property("plan.id").as("planid"))
				 			 .add(Projections.property("plan.planname").as("planname"))
				 			 .add(Projections.property("plan.planamount").as("planamount"))
				 			 .add(Projections.property("plan.dateofcreation").as("dateofcreation"))
				 			  .add(Projections.property("orgplansubscription.planstartdate").as("planstartdate"))
				 			  .add(Projections.property("plan.maxcourse").as("maxcourse"))
				 			  .add(Projections.property("plan.maxusers").as("maxusers"))
				 			  .add(Projections.property("plan.durationtype").as("durationtype"))
				 			  .add(Projections.property("plan.duration").as("duration")))
				 			 .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		         
			
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for this getSubscriptionPlanList ",e); 
		}
 		return getorgplancourselist;
 	}
 	public int updateOrgSubscribePlan(int id){
		
		try {
			id = getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Orgplansubscription set orgplanstatus='T' where id="+id+"").executeUpdate();
		} catch (Exception e) {
			planServiceLogger.error("error in PlanSubscriptionService for updateOrgSubscribePlan()",e);
		}
	 return id;
	}
	 
	
}
