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
import com.igrandee.product.ecloud.model.royalty.Paymentissue;
import com.igrandee.product.ecloud.model.royalty.Paymentrequest;
import com.igrandee.product.ecloud.model.royalty.Requestlimit;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;
import com.igrandee.product.ecloud.service.royalty.PaymentIssueService;
import com.igrandee.product.ecloud.viewmodel.royalty.PaymentIssueVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/paymentissue")
public class PaymentIssueAction extends AbstractIGActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger paymentIssueActionLogger = Logger.getLogger(PaymentIssueAction.class);
	
	@InjectParam
	Paymentissue paymentissue;
	
	@InjectParam
	PaymentIssueService paymentIssueService;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	Paymentrequest paymentrequest;
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	PaymentIssueVM paymentIssueVM;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(PaymentIssueVM paymentIssueVM){
		
		if(paymentIssueVM!=null){
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			try{
			paymentissue.setDateofissue(new Date());
			paymentissue.setPaymentissuestatus('A');
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			paymentissue.setCreatedby(orgperson);
			paymentrequest.setId(Integer.parseInt(paymentIssueVM.getPaymentrequest()));
			paymentissue.setPaymentrequest(paymentrequest);
			
			Requestlimit requestlimit=paymentIssueService.getRequestlimit(Integer.parseInt(paymentIssueVM.getPaymentrequest()));
			int authorid=paymentIssueService.getAuthorId(paymentrequest.getId());
			
			Royaltytotalamount royaltytotalamount=paymentIssueService.getRoyaltyTotal(authorid);
			if(Math.signum(royaltytotalamount.getPendingamount()-requestlimit.getMinimumamount())==1){
			royaltytotalamount.setPendingamount(royaltytotalamount.getPendingamount()-requestlimit.getMinimumamount());
			}
			else {
				throw new Exception();
			}			
			session.update(royaltytotalamount);
			
			session.save(paymentissue);
			transaction.commit();
			session.close(); 
			return Response.ok().entity(Status.CREATED).build();
			}
			catch(Exception e){
				if(paymentIssueActionLogger.isInfoEnabled()){
					paymentIssueActionLogger.info("error in create() in PaymentIssueAction"+e);
					transaction.rollback();
					session.close();
					e.printStackTrace();
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
				
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentIssue(){
		try{
			List<?> paymentIssues=paymentIssueService.getPaymentIssues();
			if(!paymentIssues.isEmpty()){
				return Response.ok().entity(paymentIssues).build();
			}else{
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}
		catch(Exception e){
			if(paymentIssueActionLogger.isInfoEnabled()){
				paymentIssueActionLogger.info("error in getPaymentIssue() in PaymentIssueAction "+e);
			}
			String errormsg = ""+e.getMessage();
			System.out.println("errormsg us "+errormsg);
			if(errormsg.equalsIgnoreCase("java.lang.String cannot be cast to com.igrandee.core.login.util.UserDetailsAdaptor")){
				return Response.status(Status.GATEWAY_TIMEOUT).entity(Status.GATEWAY_TIMEOUT).build();		
			}else{
				return Response.ok().entity(Status.BAD_REQUEST).build();		
			}
		}
		
	}
	
	@GET
	@Path("/getPaymentIssue")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentIssueList(){
		try{
		List<?> paymentissue=paymentIssueService.getPaymentIssueList(Integer.parseInt(getAttribute("orgpersonid")));
		if(!paymentissue.isEmpty()){
			return Response.ok().entity(paymentissue).build();
		}
		}
		catch(Exception e){
			if(paymentIssueActionLogger.isInfoEnabled()){
				paymentIssueActionLogger.info("error in getPaymentIssueList() in PaymentIssueAction "+e);
			}		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	@GET
	@Path("/getAuthorTotalAmountList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorTotalamountList(){
		try{
		List<?> paymentissue=paymentIssueService.getAuthorTotalamountList(Integer.parseInt(getAttribute("orgpersonid")));
		if(!paymentissue.isEmpty()){
			return Response.ok().entity(paymentissue).build();
		}
		}
		catch(Exception e){
			if(paymentIssueActionLogger.isInfoEnabled()){
				paymentIssueActionLogger.info("error in getAuthorTotalamountList() in PaymentIssueAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentIssueById(@PathParam("id")int id){		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		  Paymentissue paymentissue=(Paymentissue) session.get(Paymentissue.class, id);
			paymentIssueVM.setId(""+paymentissue.getId());
			paymentIssueVM.setCreatedby(""+paymentissue.getCreatedby().getOrgpersonid());
			paymentIssueVM.setDateofissue(""+paymentissue.getDateofissue());
			paymentIssueVM.setPaymentissuestatus(""+paymentissue.getPaymentissuestatus());
			paymentIssueVM.setPaymentrequest(""+paymentissue.getPaymentrequest().getId());		  
			transaction.commit();
			session.close();
		}
		catch(Exception e){
			paymentIssueActionLogger.info("error in getPaymentIssueById() in PaymentIssueAction");
			transaction.rollback();
			session.close();
			return Response.ok().entity(Status.BAD_REQUEST).build();
		}		
		return Response.ok().entity(paymentIssueVM).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePaymentIssue(@QueryParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		Paymentissue paymentissue=(Paymentissue) session.get(Paymentissue.class, id);
		paymentissue.setPaymentissuestatus('D');
		session.update(paymentissue);
		transaction.commit();
		session.close();
		}
		catch(Exception e){
			paymentIssueActionLogger.info("error in deletePaymentIssue() in PaymentIssueAction");
			transaction.rollback();
			session.close();
			return Response.ok().entity(Status.BAD_REQUEST).build();
		}
		return Response.ok().entity(Status.ACCEPTED).build();	
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePaymentIssue(@QueryParam("id")int id,PaymentIssueVM paymentIssueVM){
		
		if(paymentIssueVM!=null){
			paymentissue.setId(id);
			paymentissue.setDateofissue(new Date(Long.parseLong(paymentIssueVM.getDateofissue())));
			paymentissue.setPaymentissuestatus('A');
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			paymentissue.setCreatedby(orgperson);
			paymentrequest.setId(Integer.parseInt(paymentIssueVM.getPaymentrequest()));
			paymentissue.setPaymentrequest(paymentrequest);
			try{
			    paymentIssueService.update(paymentissue);
			    return Response.ok().entity(Status.ACCEPTED).build();
			}
			catch(Exception e){
				paymentIssueActionLogger.info("error in updatePaymentIssues() in PaymentIssueAction");
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
			
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	

}
