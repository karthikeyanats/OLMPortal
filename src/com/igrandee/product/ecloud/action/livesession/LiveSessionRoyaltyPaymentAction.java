package com.igrandee.product.ecloud.action.livesession;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;
import com.igrandee.product.ecloud.model.livesession.Livesessionroyaltypayment;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;
import com.igrandee.product.ecloud.service.livesession.LiveSessionRoyaltyPaymentService;
import com.igrandee.product.ecloud.viewmodel.livesession.LiveSessionRoyaltyPaymentVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;


@Path("/livesessionroyalpayment")
public class LiveSessionRoyaltyPaymentAction {
	
	@InjectParam
	Livesessionroyaltypayment livesessionroyaltypayment;
	
	@InjectParam
	Royaltyconfig royaltyconfig;
	
	@InjectParam
	Livesessionenrollment livesessionenrollment;
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	LiveSessionRoyaltyPaymentService liveSessionRoyaltyPaymentService;
	
    @InjectParam
	Royaltytotalamount royaltytotalamount;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(LiveSessionRoyaltyPaymentVM liveSessionRoyaltyPaymentVM){
		
		if(liveSessionRoyaltyPaymentVM!=null){
			
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			try{
			Royaltyconfig currentRoyalty=liveSessionRoyaltyPaymentService.getCurrentRoyalty(new Date());
			int currentroyaltyamt=0;
			if(currentRoyalty!=null){
				currentroyaltyamt=currentRoyalty.getAuthorroyalty();
			}
			else{
				currentroyaltyamt=(Integer)liveSessionRoyaltyPaymentService.getDefaultRoyalty().get(0).get("authorroyalty");
			}
			float price=liveSessionRoyaltyPaymentService.getCoursePrice(Integer.parseInt(liveSessionRoyaltyPaymentVM.getLivesessionenrollmentid()));
			float authoramount=(currentroyaltyamt*price)/100;
			float orgamount=price-authoramount;
			
			System.out.println("authoramount is "+authoramount);
			System.out.println("price is "+price);
			System.out.println("orgamount is "+orgamount);
			System.out.println("currentroyaltyamt is "+currentroyaltyamt);
			
			//inflating livesessionroyaltypayment
		    livesessionroyaltypayment.setAuthorroylatyamount(authoramount);
		    livesessionroyaltypayment.setOrgroyaltyamount(orgamount);
		    livesessionenrollment.setLivesessionenrollmentid(Integer.parseInt(liveSessionRoyaltyPaymentVM.getLivesessionenrollmentid()));
		    livesessionroyaltypayment.setLivesessionenrollment(livesessionenrollment);
		    royaltyconfig.setId(currentRoyalty.getId());
		    livesessionroyaltypayment.setRoyaltyconfig(royaltyconfig);
		    livesessionroyaltypayment.setStatus("A");
		    session.save(livesessionroyaltypayment);
		    
		    int authorid=liveSessionRoyaltyPaymentService.getOrgPersonId(livesessionenrollment.getLivesessionenrollmentid());
		    Royaltytotalamount royaltytotalamount=liveSessionRoyaltyPaymentService.getRoyaltyTotalId(authorid);
			if(royaltytotalamount==null){
				royaltytotalamount=this.royaltytotalamount;
				royaltytotalamount.setAuthorid(authorid);
			    royaltytotalamount.setRoyaltytotalamountstatus('A');
			}
			royaltytotalamount.setPendingamount(royaltytotalamount.getPendingamount()+authoramount);
		    royaltytotalamount.setTotalamount(royaltytotalamount.getTotalamount()+authoramount);
		    session.saveOrUpdate(royaltytotalamount);
		    transaction.commit();
		    session.close();
			}
			catch(Exception e){
				{
					e.printStackTrace();
					transaction.rollback();
					session.close();
					return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
				}
			
			}	
		    return Response.ok().entity(Status.CREATED).build();			
		}
		else			
			return Response.ok(Status.BAD_REQUEST).build();
		
	}


	@Path("/live")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTotalAmountByLivesession(){
		
		List<?> totalAmountList=liveSessionRoyaltyPaymentService.getTotalAmountByLivesession();
		if(totalAmountList!=null)
			return Response.ok(totalAmountList).build();
		else
			return Response.ok(Status.BAD_REQUEST).build();		
	}
	
	@Path("/live/{id}/payment")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentDetailsList(@PathParam("id")int id){
		
		List<?> paymentDetailsList=liveSessionRoyaltyPaymentService.getPaymentDetails(id);
		
		if(paymentDetailsList!=null)
			return Response.ok(paymentDetailsList).build();
		else
			return Response.ok(Status.BAD_REQUEST).build();
		
	}
	
}
