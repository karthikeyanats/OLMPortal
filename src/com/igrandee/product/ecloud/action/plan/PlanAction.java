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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.model.Plan;
import com.igrandee.product.ecloud.service.plan.PlanService;
import com.igrandee.product.ecloud.service.plan.PlanSubscriptionService;
import com.igrandee.product.ecloud.viewmodel.plan.PlanViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author satheeskumaran_ds
 *
 */
@Path("/blog/plan") 
public class PlanAction {
	
	
	static final Logger planActionLogger = Logger.getLogger(PlanAction.class);
	
	Map<?, ?> hashMap;
	
 	@InjectParam
	Plan plan;
	
 	@InjectParam
	PlanService planService;
	
	@InjectParam
	PlanSubscriptionService planSubscriptionService;
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/savesubscribeplan")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response createPlan(PlanViewModel planViewModel){
	
		planActionLogger.info("*************** CREATE Plan ******************  ");
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel); 
		 
		plan.setDateofcreation(new Date());
		plan.setDuration(planViewModel.getDuration());
		plan.setMaxcourse(planViewModel.getMaxcourse());
		plan.setPlanname(planViewModel.getPlanname());
		plan.setPlanstatus('A');
		plan.setMaxusers(planViewModel.getMaxusers());
		plan.setDurationtype(planViewModel.getDurationtype());
		plan.setPlanamount(planViewModel.getPlanamount());
		
		
		
		int isCategory  = planService.save(plan);
		
		planActionLogger.info("ACTION :: RESPONSE plan == > " + plan);
		planActionLogger.info("**************************************************  ");
		
		if(isCategory > 0){
			return Response.status(Status.OK).entity(Status.CREATED).build();			
 		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/deletesubscribeplan")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response deletePlan(PlanViewModel planViewModel){
		
		planActionLogger.info("*************** DELETE A PLAN ****************  ");
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel);
		
		plan.setId(Integer.parseInt(planViewModel.getId()));
		plan.setDateofcreation(new Date());
		plan.setDuration(planViewModel.getDuration());
		plan.setMaxcourse(planViewModel.getMaxcourse());
		plan.setPlanname(planViewModel.getPlanname());
		
		plan.setMaxusers(planViewModel.getMaxusers());
		plan.setDurationtype(planViewModel.getDurationtype());
		plan.setPlanamount(planViewModel.getPlanamount());		
		plan.setPlanstatus('D');
		plan = planService.update(plan);
		
		planActionLogger.info("ACTION :: RESPONSE category == > " + plan);
		planActionLogger.info("**************************************************  ");
		if(plan != null){
			return Response.status(Status.OK).entity(plan).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	
	@POST
	@Path("/updatesubscribeplan")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updatePlan(PlanViewModel planViewModel){
		
		planActionLogger.info("*************** UPDATE A plan ******************  "); 
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel);
		 
		
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel);
		
		plan.setId(Integer.parseInt(planViewModel.getId()));
		plan.setDateofcreation(new Date());
		plan.setDuration(planViewModel.getDuration());
		plan.setMaxcourse(planViewModel.getMaxcourse());
		plan.setPlanname(planViewModel.getPlanname());
		
		plan.setMaxusers(planViewModel.getMaxusers());
		plan.setPlanstatus('A');
		plan.setDurationtype(planViewModel.getDurationtype());
		plan.setPlanamount(planViewModel.getPlanamount());		
		plan = planService.update(plan);
		
		planActionLogger.info("ACTION :: RESPONSE planList == > " +plan);
		planActionLogger.info("**************************************************  ");
		
		if(plan != null){
			return Response.status(Status.OK).entity(Status.OK).build();
		}
		else{
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}
		
	}
	
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/checksubscribeplan")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getDuplicatePlan(PlanViewModel planViewModel){
		
		
		planActionLogger.info("*************** GET A PLAN ******************  ");
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel);
		
 		List<?> planList= planService.getPlan(planViewModel.getPlanname(),planViewModel.getDurationtype()); 
 		
		planActionLogger.info("ACTION :: RESPONSE planList == > " +planList);
		planActionLogger.info("**************************************************  ");
		if(planList .size() > 0 ){
			return Response.status(Status.OK).entity(Status.CREATED).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/duplicatesubscribeplan")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getDupUpdatePlan(PlanViewModel planViewModel){
		
		
		planActionLogger.info("*************** GET A PLAN ******************  ");
		planActionLogger.info("ACTION :: CLIENT planViewModel == > " +planViewModel);
	 
		List<?> planList= planService.getcheckPlan(planViewModel.getPlanname(),planViewModel.getId()); 
		 
		
		planActionLogger.info("ACTION :: RESPONSE planList == > " +planList);
		planActionLogger.info("**************************************************  ");
		if(planList .size() > 0 ){
			return Response.status(Status.OK).entity(Status.CREATED).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author satheeskumaran_ds
	 */
	@POST
	@Path("/loadsubscribeplans")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPlans(PlanViewModel planViewModel){
		List<?> planList=null;
		planList = planService.getPlans(); 
		
		if(planList.size() > 0){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	 
	
 	@POST
	@Path("/loadbasicplans")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getBasicplans(PlanViewModel planViewModel){
		List<?> planList=null;
		planList = planService.getBasicplans(); 
		
		if(planList.size() > 0){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	 
	
	/**
	 * 
	 * @param planid
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/loadsubscribeplanidlist")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getPlanid(PlanViewModel planViewModel){
		List<?> planList=null;
		planList = planService.getPlanIdList(planViewModel.getId()); 
		
		if(planList.size() > 0){
			return Response.status(Status.OK).entity(planList).build();
		}
		return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
}
