package com.igrandee.product.ecloud.action.livesession;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Livesession;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;
import com.igrandee.product.ecloud.model.livesession.Livesessionpayment;
import com.igrandee.product.ecloud.service.livesession.LiveSessionEnrollService;
import com.igrandee.product.ecloud.viewmodel.livesession.LiveSessionEnrollVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/livesessionenroll")
public class LiveSessionEnrollmentAction extends MasterActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@InjectParam
	Livesessionenrollment livesessionenrollment;
	
	@InjectParam
	LiveSessionEnrollService liveSessionEnrollService;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	Livesession livesession;
	
	/*@InjectParam
	Livesessionpayment livesessionpayment;*/
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(LiveSessionEnrollVM liveSessionEnrollVM){
		
		if(liveSessionEnrollVM!=null){
			
			livesession.setLivesessionid(Integer.parseInt(liveSessionEnrollVM.getLivesessionid()));			
			livesessionenrollment.setLivesession(livesession);
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			livesessionenrollment.setOrgperson(orgperson);
			livesessionenrollment.setStatus("A");
			Livesessionpayment livesessionpayment=new Livesessionpayment();
			livesessionpayment.setLivesessionenrollment(livesessionenrollment);
			livesessionpayment.setOrderno(liveSessionEnrollVM.getOrderno());
			livesessionpayment.setStatus("A");
			livesessionpayment.setPaymenttype(liveSessionEnrollVM.getPaymenttype());
			livesessionpayment.setDateofpayment(new Timestamp(new Date().getTime()));
				
			HashSet<Livesessionpayment> livesessionpayments=new HashSet<Livesessionpayment>();
			livesessionpayments.add(livesessionpayment);
			livesessionenrollment.setLivesessionpayments(livesessionpayments);
			
			int status=liveSessionEnrollService.save(livesessionenrollment);
			
			if(status>0)
				return Response.ok().entity(status).build();
			else
				return Response.ok(Status.INTERNAL_SERVER_ERROR).build();			
		}
		else
			return Response.ok(Status.BAD_REQUEST).build();		
		
	}

}
