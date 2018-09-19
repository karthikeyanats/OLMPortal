package com.igrandee.product.ecloud.action.royalty;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;
import com.igrandee.product.ecloud.service.royalty.RoyaltyTotalAmountService;
import com.igrandee.product.ecloud.viewmodel.royalty.RoyaltyTotalAmountVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;


@Path("/royaltytotalamount")
public class RoyaltyTotalAmountAction extends AbstractIGActionSupport{
	

	private static final long serialVersionUID = 1L;
	private static Logger royatyTotalAmountActionLogger = Logger.getLogger(RoyaltyTotalAmountAction.class);

	@InjectParam
	Royaltytotalamount royaltytotalamount;
	
	@InjectParam
	RoyaltyTotalAmountService royaltyTotalAmountService;
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	RoyaltyTotalAmountVM royaltyTotalAmountVM;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyTotalAmounts(){	
		try{
		List<?> royaltytotalamounts=royaltyTotalAmountService.getRoyaltytotalamounts();
		if(!royaltytotalamounts.isEmpty()){
			 return Response.ok().entity(royaltytotalamounts).build();
		}
		}
		catch(Exception e){
			if(royatyTotalAmountActionLogger.isInfoEnabled()){
				royatyTotalAmountActionLogger.info("error in getRoyaltyTotalAmounts() in RoyaltyTotalAmountAction"+e);
			}
		}
		 return Response.ok().entity(Status.BAD_REQUEST).build();		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyTotalAmountById(@PathParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		  Royaltytotalamount royaltytotalamount=(Royaltytotalamount) session.get(Royaltytotalamount.class, id);
		  royaltyTotalAmountVM.setId(""+royaltytotalamount.getId());
		  royaltyTotalAmountVM.setAuthorid(""+royaltytotalamount.getAuthorid());
		  royaltyTotalAmountVM.setPendingamount(""+royaltytotalamount.getPendingamount());
		  royaltyTotalAmountVM.setRoyaltytotalamountstatus(""+royaltytotalamount.getRoyaltytotalamountstatus());
		  royaltyTotalAmountVM.setTotalamount(""+royaltytotalamount.getTotalamount());
			transaction.commit();
			session.close();
		}
		catch(Exception e){
			if(royatyTotalAmountActionLogger.isInfoEnabled()){
				royatyTotalAmountActionLogger.info("error in getRoyaltyTotalAmountById() in RoyaltyTotalAmountAction"+e);
				transaction.rollback();
				session.close();
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}		
		return Response.ok().entity(royaltyTotalAmountVM).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRoyaltyPayment(@QueryParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		Royaltytotalamount royaltytotalamount=(Royaltytotalamount) session.get(Royaltytotalamount.class, id);
		royaltytotalamount.setRoyaltytotalamountstatus('D');
		session.update(royaltytotalamount);
		transaction.commit();
		session.close();
		}
		catch(Exception e){
			if(royatyTotalAmountActionLogger.isInfoEnabled()){
			royatyTotalAmountActionLogger.info("error in deleteRoyaltyPayment() in RoyaltyTotalAmountAction"+e);
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
	public Response updateRoyaltyTotalAmount(@QueryParam("id")int id,RoyaltyTotalAmountVM royaltyTotalAmountVM){
		
		if(royaltyTotalAmountVM!=null){
			  royaltytotalamount.setId(id);
			  royaltytotalamount.setAuthorid(Integer.parseInt(royaltyTotalAmountVM.getAuthorid()));
	          royaltytotalamount.setPendingamount(Float.parseFloat(royaltyTotalAmountVM.getPendingamount()));
	          royaltytotalamount.setRoyaltytotalamountstatus('A');
	          royaltytotalamount.setTotalamount(Float.parseFloat(royaltyTotalAmountVM.getTotalamount()));		
			try{
				royaltyTotalAmountService.update(royaltytotalamount);
			    return Response.ok().entity(Status.ACCEPTED).build();
			}
			catch(Exception e){
				if(royatyTotalAmountActionLogger.isInfoEnabled()){
					royatyTotalAmountActionLogger.info("error in updateRoyaltyTotalAmount() in RoyaltyTotalAmountAction"+e);
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}
			
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/getRoyaltyTotalAmount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyTotalAmount(){
		try{
		List<?> royaltytotalamounts=royaltyTotalAmountService.getRoyaltyTotalAmount(Integer.parseInt(getAttribute("orgpersonid")));
		if(!royaltytotalamounts.isEmpty()){
			 return Response.ok().entity(royaltytotalamounts).build();
		}
		}
		catch(Exception e){
			if(royatyTotalAmountActionLogger.isInfoEnabled()){
				royatyTotalAmountActionLogger.info("error in getRoyaltyTotalAmount() in RoyaltyTotalAmountAction"+e);
			}
		}
		 return Response.ok().entity(Status.BAD_REQUEST).build();		
	}

}
