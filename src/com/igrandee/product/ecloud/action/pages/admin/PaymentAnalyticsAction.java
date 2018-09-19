package com.igrandee.product.ecloud.action.pages.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.admin.PaymentAnalyticsService;
import com.sun.jersey.api.core.InjectParam;

@Path("/paymentanalytics")
public class PaymentAnalyticsAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	
	@InjectParam
	PaymentAnalyticsService paymentAnalyticsService;
	
	
	@GET
	@Path("/current/{paymode}")
	public Response getTodaysPayment(@PathParam("paymode") String paymode){
		try{
			List<?> currentPaymentList = paymentAnalyticsService.loadCurrentPayment(getAttribute("organizationid"),paymode);
			if(currentPaymentList!= null && currentPaymentList.size()!=0){
				return Response.status(Status.OK).entity(currentPaymentList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/range/{from}/{to}")
	public Response getTodaysPayment(@PathParam("from") String from,
			@PathParam("to") String to){
		try{
			List<?> currentPaymentList = paymentAnalyticsService.loadRangePayment(getAttribute("organizationid"),from,to);
			if(currentPaymentList!= null && currentPaymentList.size()!=0){
				return Response.status(Status.OK).entity(currentPaymentList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}	
}
