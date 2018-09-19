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
 * by satheeskumaran_ds
 */
package com.igrandee.product.ecloud.action.plan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.core.organization.model.Organization;
import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Orgplansubscription;
import com.igrandee.product.ecloud.model.Plan;
import com.igrandee.product.ecloud.service.plan.PlanService;
import com.igrandee.product.ecloud.service.plan.PlanSubscriptionService;
import com.igrandee.product.ecloud.viewmodel.plan.PlanSubscriptionViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author satheeskumaran_ds
 *
 */
@Path("/blog/plansubscription") 
public class OrgPlanSubscriptionAction extends MasterActionSupport {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final Logger planSubscriptionActionLogger = Logger.getLogger(OrgPlanSubscriptionAction.class);
	
	Map<?, ?> hashMap;
	
	
	@InjectParam
	Plan plan;
	
	@InjectParam
	Orgplansubscription planSubscription;
	
	
	@InjectParam
	PlanService planService;
	
	
	
	@InjectParam
	PlanSubscriptionService planSubscriptionService;
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/create")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response createPlanSubscription(PlanSubscriptionViewModel planSubscriptionViewModel){
	
		try{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		Organization organization=new Organization();
		planSubscription.setDateofsubscription(new Date());
		organization.setOrganizationid(Integer.parseInt(getAttribute("organizationid")));
		planSubscription.setOrganization(organization);
		plan.setId(Integer.parseInt(planSubscriptionViewModel.getPlanid()));
		planSubscription.setPlan(plan);
		
		planSubscription.setPlanstartdate(new Date());
		Calendar cal = Calendar.getInstance();    
		cal.setTime( dateFormat.parse(currentdate));
		
		if(planSubscriptionViewModel.getDurationtype().equals("Annual")){
			cal.add(Calendar.YEAR,planSubscriptionViewModel.getDuration());
			String convertedDate=dateFormat.format(cal.getTime());    
			planSubscription.setPlanenddate(dateFormat.parse(convertedDate));
		}
		else if(planSubscriptionViewModel.getDurationtype().equals("Monthly")){
			cal.add(Calendar.MONTH,planSubscriptionViewModel.getDuration());
			String convertedDate=dateFormat.format(cal.getTime());    
			planSubscription.setPlanenddate(dateFormat.parse(convertedDate));
		}
		
 		
		planSubscription.setOrgplanstatus(planSubscriptionViewModel.getOrgplanstatus());
 		int isCategory  = planSubscriptionService.save(planSubscription);
		if(isCategory > 0){
 			return Response.status(Status.OK).entity(isCategory).build();
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
			if(planSubscriptionActionLogger.isInfoEnabled()){ 
				planSubscriptionActionLogger.error("error in createPlanSubscription() in Orgplan ",e);  
			}
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/delete")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response deletePlan(PlanSubscriptionViewModel planSubscriptionViewModel){
		
		planSubscription.setDateofsubscription(planSubscriptionViewModel.getDateofsubscription());
		Organization organization=new Organization();
		organization.setOrganizationid(Integer.parseInt(getAttribute("organizationid")));
		planSubscription.setOrganization(organization);
		planSubscription.setPlanenddate(planSubscriptionViewModel.getPlanenddate());
		planSubscription.setPlanstartdate(planSubscriptionViewModel.getPlanstartdate());
		plan.setId(planSubscriptionViewModel.getPlan().getId());;
		planSubscription.setPlan(plan);
		planSubscription = planSubscriptionService.update(planSubscription);
		
		if(planSubscription != null){
			return Response.status(Status.OK).entity(planSubscription).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	
	@POST
	@Path("/update")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updatePlan(PlanSubscriptionViewModel planSubscriptionViewModel){
		
		try{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		
		planSubscription.setId(planSubscriptionViewModel.getOrgsubscriptionid());
		planSubscription.setDateofsubscription(new Date());
		Organization organization=new Organization();
		organization.setOrganizationid(Integer.parseInt(getAttribute("organizationid")));
		planSubscription.setOrganization(organization);
		plan.setId(Integer.parseInt(planSubscriptionViewModel.getPlanid()));
		planSubscription.setPlan(plan);
		
		planSubscription.setPlanstartdate(new Date());
		Calendar cal = Calendar.getInstance();    
		cal.setTime( dateFormat.parse(currentdate));
		
		if(planSubscriptionViewModel.getDurationtype().equals("Annual")){
			cal.add(Calendar.YEAR,planSubscriptionViewModel.getDuration());
			String convertedDate=dateFormat.format(cal.getTime());    
			planSubscription.setPlanenddate(dateFormat.parse(convertedDate));
		}
		else if(planSubscriptionViewModel.getDurationtype().equals("Monthly")){
			cal.add(Calendar.MONTH,planSubscriptionViewModel.getDuration());
			String convertedDate=dateFormat.format(cal.getTime());    
			planSubscription.setPlanenddate(dateFormat.parse(convertedDate));
		}
		planSubscription.setOrgplanstatus("A");
		
		planSubscription = planSubscriptionService.update(planSubscription);
		
		if(planSubscription != null){
			return Response.status(Status.OK).entity(planSubscription).build();
		}
		}
		catch(Exception e){
			if(planSubscriptionActionLogger.isInfoEnabled()){ 
				planSubscriptionActionLogger.error("error in updatePlan() in Orgplan ",e);  
			}
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/orgplanstatusupdate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response statusupdate(PlanSubscriptionViewModel planSubscriptionViewModel){
		
		//Orgplansubscription planSubscription = planSubscriptionService.get(planSubscriptionViewModel.getOrgsubscriptionid());
		//planSubscription.setOrgplanstatus(planSubscriptionViewModel.getOrgplanstatus());
		
		int planSubscription = planSubscriptionService.updateOrgSubscribePlan(planSubscriptionViewModel.getOrgsubscriptionid());
		 
		if(planSubscription != 0){
			return Response.status(Status.OK).entity(planSubscription).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/category")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPlanSubscription(PlanSubscriptionViewModel planSubscriptionViewModel){
		
		List<?> planList= planSubscriptionService.getSubscripPlanList(Integer.parseInt(planSubscriptionViewModel.getPlanid())); 
		 
		if(planList .size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
	}
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/categories")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPlanSubscriptions(PlanSubscriptionViewModel planSubscriptionViewModel){
		
		List<?> planList = planSubscriptionService.getPlanSubscriptions(planSubscriptionViewModel.getOrgplanstatus(),Long.parseLong(getAttribute("organizationid"))); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/subscriptionplandetail")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getSubscriptionPlanDetail(){
		
		
		List<?> planList = planSubscriptionService.getSubscriptionPlanList(); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/orgsubscriptionplandetail")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getOrgSubscriptionPlanDetail(){
		
		
		List<?> planList = planSubscriptionService.getOrgplanlist(Long.parseLong(getAttribute("organizationid"))); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Path("/orgsubscriptionplandetail")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCoursesPublished(@QueryParam("orgplansubscriptionid")int orgplansubscriptionid){
		
		
		List<?> planList = planSubscriptionService.getCoursesPublished(orgplansubscriptionid); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Path("/enrolledusers")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getEnrolledUsersByPlan(@QueryParam("orgplansubscriptionid")int orgplansubscriptionid){
		
		
		List<?> planList = planSubscriptionService.getEnrolledUsers(orgplansubscriptionid); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author selvapriya_m
	 */
	@POST
	@Path("/orgplanlist/{orgid}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPlanhistory(@PathParam("orgid") String orgid){
		List<?> orgPlanlist = null;		
		orgPlanlist = planSubscriptionService.getOrgplanlist(Long.parseLong(orgid));
		if(orgPlanlist.size() > 0){
			return Response.status(Status.OK).entity(orgPlanlist).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		
		
	}
	/**
	 * 
	 * @return Status.OK getorgplancourse list | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/getmaxcourseuser")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getMaxCourseUser(){
		
		List<?> orgPlanList =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));
		
		Map<?,?> map=(Map<?, ?>) orgPlanList.get(0);
		String orgplansubscriptionid= ""+map.get("id");
				
		List<?> planList = planSubscriptionService.getorgplancourse(Integer.parseInt(orgplansubscriptionid)); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	/**
	 * 
	 * @return Status.OK getorgplanuser list | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/getmaxuser")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getMaxUser(){
		
		List<?> orgPlanList =planSubscriptionService.getPlanSubscriptions("A",Long.parseLong(getAttribute("organizationid")));
		
		Map<?,?> map=(Map<?, ?>) orgPlanList.get(0);
		String orgplansubscriptionid= ""+map.get("id");
				
		List<?> planList = planSubscriptionService.getorgplanuser(Integer.parseInt(orgplansubscriptionid)); 
		
		if(planList.size() > 0 && ! planList.isEmpty()){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	/*
	*//**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author selvapriya_m
	 *//*
	@POST
	@Path("/maxcourse")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getMaxSubscribedPlanMaxCourse( @PathParam("id") String orgPlanSubcriptionId){
		
		List<?> maxCourseList = null;
		
		maxCourseList = planSubscriptionService.getMaxCourse(Integer.parseInt(orgPlanSubcriptionId));
		if(maxCourseList.size() > 0){
			return Response.status(Status.OK).entity(maxCourseList).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		
		
	}
	
	*/
	
	
	
}
