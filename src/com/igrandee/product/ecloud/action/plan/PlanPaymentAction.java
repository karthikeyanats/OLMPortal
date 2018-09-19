package com.igrandee.product.ecloud.action.plan;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Orgplansubscription;
import com.igrandee.product.ecloud.model.Planpayment;
import com.igrandee.product.ecloud.service.plan.PlanPaymentService;
import com.igrandee.product.ecloud.viewmodel.plan.PlanSubscriptionViewModel;
import com.sun.jersey.api.core.InjectParam;
/**
 * 
 * @author muniyarasu_a
 *
 */
@Path("/planpayment")
public class PlanPaymentAction extends MasterActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final Logger planPaymentActionLogger = Logger.getLogger(PlanPaymentAction.class);
	
	@InjectParam
	Planpayment planpayment;
	
	@InjectParam
	PlanPaymentService planPaymentService;
	
	@InjectParam
	Orgplansubscription planSubscription;
	
	
	/**
	 * 
	 * @param planSubscriptionViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author muniyarasu_a
	 */
	@POST
	@Path("/planpaymentcreate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response createPlanPayment(PlanSubscriptionViewModel planSubscriptionViewModel){
	
		try{
		
		planSubscription.setId(planSubscriptionViewModel.getOrgsubscriptionid());
		planpayment.setOrgplansubscription(planSubscription);
		planpayment.setPlanpaymenttype(planSubscriptionViewModel.getPlanpaymenttype());
		planpayment.setPlanpaymentdate(new Date());
		planpayment.setPlanpaymentstatus(planSubscriptionViewModel.getPlanpaymentstatus());
		planpayment.setOrderno(planSubscriptionViewModel.getOrderno());
		
		int isCategory =planPaymentService.save(planpayment);
		
		if(isCategory > 0){
			
			return Response.status(Status.OK).entity(isCategory).build();
		}
		
		}
		catch(Exception e){
			if(planPaymentActionLogger.isInfoEnabled()){ 
				planPaymentActionLogger.error("error in createPlanPayment() in planPaymentAction ",e);  
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
	@Path("/updatestatusplanpayment")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updatestatusPlanPayment(PlanSubscriptionViewModel planSubscriptionViewModel){
	
		try{
		
		planpayment = planPaymentService.get(planSubscriptionViewModel.getPlanpaymentid());
		planpayment.setPlanpaymentstatus(planSubscriptionViewModel.getPlanpaymentstatus());
		planSubscription.setId(planSubscriptionViewModel.getOrgsubscriptionid());
		planpayment.setOrgplansubscription(planSubscription);

		planpayment =planPaymentService.update(planpayment);
		
		if(planpayment != null){
			
			return Response.status(Status.OK).entity(planpayment).build();
		}
		
		}
		catch(Exception e){
			if(planPaymentActionLogger.isInfoEnabled()){ 
				planPaymentActionLogger.error("error in updatestatusPlanPayment() in planPaymentAction ",e);  
			}
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}

}
