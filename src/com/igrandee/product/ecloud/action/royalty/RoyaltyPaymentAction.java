package com.igrandee.product.ecloud.action.royalty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;
import com.igrandee.product.ecloud.model.royalty.Royaltypayment;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;
import com.igrandee.product.ecloud.service.royalty.RoyaltyConfigService;
import com.igrandee.product.ecloud.service.royalty.RoyaltyPaymentService;
import com.igrandee.product.ecloud.viewmodel.royalty.RoyaltyPaymentVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/royaltypayment")
public class RoyaltyPaymentAction {
	
	private static Logger royaltyPaymentActionLogger	=	Logger.getLogger(RoyaltyPaymentAction.class);
	
	@InjectParam
	RoyaltyPaymentService royaltyPaymentService;
	
	@InjectParam
	Royaltypayment royaltypayment;
	
	@InjectParam
	Courseenrollment courseenrollment;
	
	@InjectParam
	Royaltyconfig royaltyconfig;
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	RoyaltyPaymentVM royaltyPaymentVM;
	
	@InjectParam
	Royaltytotalamount royaltytotalamount;
	
	@InjectParam
	RoyaltyConfigService royaltyConfigService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@QueryParam("type")int type,RoyaltyPaymentVM royaltyPaymentVM){		
		
		if(royaltyPaymentVM!=null&&type!=0){	
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
			try{
		Royaltyconfig currentRoyalty=royaltyPaymentService.getCurrentRoyalty(new Date(),type);
		int currentroyaltyamt=0;
		if(currentRoyalty!=null){
			currentroyaltyamt=currentRoyalty.getAuthorroyalty();
		}
		else{			
			List<HashMap<?,?>> defaultRoyaltyList=royaltyConfigService.getDefaultRoyalty(type);
			currentroyaltyamt=(Integer)defaultRoyaltyList.get(0).get("authorroyalty");
			currentRoyalty=new Royaltyconfig();
			currentRoyalty.setId((Integer)defaultRoyaltyList.get(0).get("id"));
		}
		float price=royaltyPaymentService.getCoursePrice(Integer.parseInt(royaltyPaymentVM.getCourseenrollmentid()));
		float authoramount=(currentroyaltyamt*price)/100;
		float orgamount=price-authoramount;
		
		//inflating royalty payment
	    royaltypayment.setAuthorroyaltyamount(authoramount);
		courseenrollment.setCourseenrollmentid(Integer.parseInt(royaltyPaymentVM.getCourseenrollmentid()));
		royaltypayment.setCourseenrollmentid(courseenrollment);
		royaltypayment.setDateofpyament(new Date());
		royaltypayment.setOrgroyaltyamount(orgamount);
		royaltyconfig.setId(currentRoyalty.getId());
		royaltypayment.setRoyaltyconfig(royaltyconfig);
		royaltypayment.setRoyaltypaymentstatus('A');		
		session.save(royaltypayment);
		
		int authorid=royaltyPaymentService.getOrgPersonId(courseenrollment.getCourseenrollmentid());
		Royaltytotalamount royaltytotalamount=royaltyPaymentService.getRoyaltyTotalId(authorid);
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
	    return Response.ok().entity(Status.CREATED).build();
	   }
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in create() in RoyaltyPaymentAction"+e);
				e.printStackTrace();
				transaction.rollback();
				session.close();
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
			}
		
		}	
	}
		return Response.ok().entity(Status.BAD_REQUEST).build();		
		
 }
		
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyPayments(){
		try{
		List<?> royalpayments=royaltyPaymentService.getRoyaltyPayments();
		if(!royalpayments.isEmpty()){
			return Response.ok().entity(royalpayments).build();
		}
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in getRoyaltyPayments() in RoyaltyPaymentAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();	
		
	}
	

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyPaymentById(@PathParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		  Royaltypayment royaltypayment=(Royaltypayment) session.get(Royaltypayment.class, id);
		  royaltyPaymentVM.setId(""+royaltypayment.getId());
		  royaltyPaymentVM.setAuthorroyaltyamount(""+royaltypayment.getAuthorroyaltyamount());
		  royaltyPaymentVM.setDateofpyament(""+royaltypayment.getDateofpyament());
		  royaltyPaymentVM.setCourseenrollmentid(""+royaltypayment.getCourseenrollmentid().getCourseenrollmentid());
		  royaltyPaymentVM.setRoyaltyconfig(""+royaltypayment.getRoyaltyconfig().getId());
		  royaltyPaymentVM.setRoyaltypaymentstatus(""+royaltypayment.getRoyaltypaymentstatus());
		  royaltyPaymentVM.setOrgroyaltyamount(""+royaltypayment.getOrgroyaltyamount());			
			transaction.commit();
			session.close();
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in getRoyaltyPaymentById() in RoyaltyPaymentAction"+e);
			transaction.rollback();
			session.close();
			return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}		
		return Response.ok().entity(royaltyPaymentVM).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRoyaltyPayment(@QueryParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		Royaltypayment royaltypayment=(Royaltypayment) session.get(Royaltypayment.class, id);
		royaltypayment.setRoyaltypaymentstatus('D');
		session.update(royaltypayment);
		transaction.commit();
		session.close();
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in deleteRoyaltyPayment() in RoyaltyPaymentAction"+e);
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
	public Response updatePaymentRequest(@QueryParam("id")int id,RoyaltyPaymentVM royaltyPaymentVM){
		
		if(royaltyPaymentVM!=null){
			royaltypayment.setId(id);
			royaltypayment.setAuthorroyaltyamount(Float.parseFloat(royaltyPaymentVM.getAuthorroyaltyamount()));
			courseenrollment.setCourseenrollmentid(Integer.parseInt(royaltyPaymentVM.getCourseenrollmentid()));
			royaltypayment.setCourseenrollmentid(courseenrollment);
			royaltypayment.setDateofpyament(new Date());
			royaltypayment.setOrgroyaltyamount(Float.parseFloat(royaltyPaymentVM.getOrgroyaltyamount()));
			royaltyconfig.setId(Integer.parseInt(royaltyPaymentVM.getRoyaltyconfig()));
			royaltypayment.setRoyaltyconfig(royaltyconfig);
			royaltypayment.setRoyaltypaymentstatus('A');
			try{
				royaltyPaymentService.update(royaltypayment);
			    return Response.ok().entity(Status.ACCEPTED).build();
			}
			catch(Exception e){
				if(royaltyPaymentActionLogger.isInfoEnabled()){
					royaltyPaymentActionLogger.info("error in updatePaymentRequest() in RoyaltyPaymentAction"+e);
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}
			
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	@GET
	@Path("/course")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalAmountByCourse(){
		try{
		List<?> totalamount=royaltyPaymentService.getTotalAmountByCourse();
		if(totalamount!=null){
			 return Response.ok().entity(totalamount).build();
		}
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in getTotalAmountByCourse() in RoyaltyPaymentAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();		
		
	}
	
	@GET
	@Path("/org")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrgTotalAmount(@QueryParam("type")int type){
		if(type!=0){
		try{
		List<?> totalamount=royaltyPaymentService.getOrgTotalAmount(type);
		Map hashMap=(Map) totalamount.get(0);
		hashMap.put("upto", new Date());
		if(totalamount!=null){
			 return Response.ok().entity(totalamount).build();
		}
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in getOrgTotalAmount() in RoyaltyPaymentAction"+e);
			}
		}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
		
	}
	
	@GET
	@Path("/course/{id}/payment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaymentDetails(@PathParam("id")int id){
		try{
		List<?> paymentdetails=royaltyPaymentService.getPaymentDetails(id);
		if(paymentdetails!=null){
			 return Response.ok().entity(paymentdetails).build();
		}
		}
		catch(Exception e){
			if(royaltyPaymentActionLogger.isInfoEnabled()){
				royaltyPaymentActionLogger.info("error in getPaymentDetails() in RoyaltyPaymentAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
}
