package com.igrandee.product.ecloud.action.royalty;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.royalty.Paymentrequest;
import com.igrandee.product.ecloud.model.royalty.Requestlimit;
import com.igrandee.product.ecloud.service.royalty.PaymentRequestService;
import com.igrandee.product.ecloud.viewmodel.royalty.PaymentRequestVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/paymentrequest")
public class PaymentRequestAction extends AbstractIGActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger paymentRequestActionLogger	=	Logger.getLogger(PaymentIssueAction.class);

	@InjectParam
	Paymentrequest paymentrequest;
	
	@InjectParam
	PaymentRequestService paymentRequestService;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	Requestlimit requestlimit;
	
	@InjectParam
	PaymentRequestVM paymentRequestVM;
	
	@InjectParam
	SessionFactory sessionFactory;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(PaymentRequestVM paymentRequestVM){
		try{
			if(paymentrequest!=null){
				paymentrequest.setDateofrequest(new Date());
				paymentrequest.setPaymentrequeststatus('A');
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				paymentrequest.setRequestedby(orgperson);
				requestlimit.setId(Integer.parseInt(paymentRequestVM.getRequestlimit_id()));
				paymentrequest.setRequestlimit(requestlimit);
			    int status=paymentRequestService.save(paymentrequest);
			    if(status>0){
				  return Response.ok().entity(Status.CREATED).build();
			    }
			      return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
				}
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
				paymentRequestActionLogger.error("error in create() in PaymentRequestAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentRequests(){
		try{
		List<?> paymentRequests=paymentRequestService.getPaymentRequests();
		if(paymentRequests!=null){
			  return Response.ok().entity(paymentRequests).build();
		}	
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
				paymentRequestActionLogger.error("error in getPaymentRequests() in PaymentRequestAction"+e);
			}
		}
		 return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentRequestById(@PathParam("id")int id){		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		  Paymentrequest paymentrequest=(Paymentrequest) session.get(Paymentrequest.class, id);
			paymentRequestVM.setId(""+paymentrequest.getId());
			paymentRequestVM.setPaymentrequeststatus(""+paymentrequest.getPaymentrequeststatus());
			paymentRequestVM.setRequestedby(""+paymentrequest.getRequestedby().getOrgpersonid());
			paymentRequestVM.setRequestlimit_id(""+paymentrequest.getRequestlimit().getId());
			paymentRequestVM.setDateofrequest(""+paymentrequest.getDateofrequest());
			transaction.commit();
			session.close();
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
			paymentRequestActionLogger.error("error in getPaymentRequestById() in PaymentRequestAction"+e);
			transaction.rollback();
			session.close();
			return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}		
		return Response.ok().entity(paymentRequestVM).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePaymentRequest(@QueryParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		Paymentrequest paymentrequest=(Paymentrequest) session.get(Paymentrequest.class, id);
		paymentrequest.setPaymentrequeststatus('D');
		session.update(paymentrequest);
		transaction.commit();
		session.close();
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
			paymentRequestActionLogger.error("error in deletePaymentRequest() in PaymentRequestAction"+e);
			transaction.rollback();
			session.close();
			return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}
		return Response.ok().entity(Status.ACCEPTED).build();	
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePaymentRequest(@QueryParam("id")int id,PaymentRequestVM paymentRequestVM){
		
		if(paymentRequestVM!=null){
			paymentrequest.setId(id);
			paymentrequest.setDateofrequest(new Date());
			paymentrequest.setPaymentrequeststatus('A');
			requestlimit.setId(Integer.parseInt(paymentRequestVM.getRequestlimit_id()));
			paymentrequest.setRequestlimit(requestlimit);
			orgperson.setOrgpersonid(Integer.parseInt(paymentRequestVM.getRequestedby()));
			paymentrequest.setRequestedby(orgperson);
			try{
			 paymentRequestService.update(paymentrequest);
			    return Response.ok().entity(Status.ACCEPTED).build();
			}
			catch(Exception e){
				if(paymentRequestActionLogger.isInfoEnabled()){
				paymentRequestActionLogger.error("error in updatePaymentRequest() in PaymentRequestAction"+e);
				return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}
			
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/author")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLastRequestedDetails(){
		try{
		List<?> lastRequestedDetails=paymentRequestService.getLastRequestedDetails(Integer.parseInt(getAttribute("orgpersonid")));
		if(lastRequestedDetails!=null){
			  return Response.ok().entity(lastRequestedDetails).build();
		}	
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
			paymentRequestActionLogger.error("error in getLastRequestedDetails() in PaymentRequestAction"+e);
			}
		}
		 return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}
	@POST
	@Path("/PaymentRequestList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentRequestedDetails(PaymentRequestVM paymentrequestVM){
		try{
		List<?> PaymentRequestDetails=paymentRequestService.getPaymentRequestDetail(paymentrequestVM.getPaymentrequestid());
		if(PaymentRequestDetails!=null){
			  return Response.ok().entity(PaymentRequestDetails).build();
		}	
		}
		catch(Exception e){
			if(paymentRequestActionLogger.isInfoEnabled()){
			paymentRequestActionLogger.error("error in getPaymentRequestedDetails() in PaymentRequestAction"+e);
			}
		}
		 return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}

}
