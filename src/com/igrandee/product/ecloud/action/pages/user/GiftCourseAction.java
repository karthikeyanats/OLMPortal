package com.igrandee.product.ecloud.action.pages.user;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.GiftCourse;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Payment;
import com.igrandee.product.ecloud.model.Price;
import com.igrandee.product.ecloud.service.Pages.user.GiftCourseService;
import com.igrandee.product.ecloud.service.course.InvoicePaymentService;
import com.igrandee.product.ecloud.viewmodel.GiftVM;
import com.sun.jersey.api.core.InjectParam;


@Path("/gift")
public class GiftCourseAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	GiftCourseService gcsobj;

	@InjectParam
	private GiftCourse gc;

	@InjectParam
	private Course course;

	@InjectParam
	private Orgperson orgperson;
	
	@InjectParam
	private Payment payment;
	
	@InjectParam
	private Price price;
	
	@InjectParam
	private InvoicePaymentService invoicepaymentservice;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response gifttheCourse(GiftVM giftVM){
		try{
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			gc.setOrgperson(orgperson);
			course.setCourseid(Integer.parseInt(giftVM.getCourseid()));
			gc.setCourse(course);
			gc.setGiftcoursestatus(giftVM.getGiftcoursestatus());
			gc.setRecipientmail(giftVM.getRecipientmail());
			gc.setRecipientname(giftVM.getRecipientname());
			gc.setMessage(giftVM.getMessage());
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = dateFormat.parse(giftVM.getSenddate());
			long time = date.getTime();
			gc.setSenddate(new Timestamp(time));
			Integer savedobj=gcsobj.save(gc);
			if(savedobj>0){
				return Response.status(Status.OK).entity(savedobj).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/checkDup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CheckDuplicateGifting(GiftVM giftVM){
		try{
			List<?> dupUserId = gcsobj.getDuplicateUserId(giftVM.getRecipientmail(), giftVM.getCourseid());
			if(dupUserId.size()>0){
				List<?> giftedList = gcsobj.getDuplicateGifting(giftVM.getCourseid(), getAttribute("orgpersonid"), giftVM.getRecipientmail());
				if(giftedList.size()>0){
					return Response.status(Status.OK).entity(Status.CONFLICT).build();	
				}else{
					return Response.status(Status.OK).entity(Status.OK).build();
				}	
			}
			else{
				List<?> giftedList = gcsobj.getDuplicateGifting(giftVM.getCourseid(), getAttribute("orgpersonid"), giftVM.getRecipientmail());
				if(giftedList.size()>0){
					return Response.status(Status.OK).entity(Status.CONFLICT).build();	
				}else{
					return Response.status(Status.OK).entity(Status.ACCEPTED).build();
				}	
			}
			

		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	} 
	
	@GET
	@Path("/{id}")
	@Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGiftCourseList(@PathParam("id")String id){
		try{
			List<?> giftCourseList = gcsobj.getGiftcourseList(id);
			
			if(giftCourseList.size()>0){
				return Response.ok().entity(giftCourseList).build();
			}
			else{
				return Response.ok().entity(Status.PRECONDITION_FAILED).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT
	@Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGiftCourse(GiftVM giftVM){
		
			try{
			    Integer updategiftcourse	=	gcsobj.updateGiftCourse(Integer.parseInt(giftVM.getGiftcourseid()),giftVM.getGiftcoursestatus());
			    return Response.ok().entity(updategiftcourse).build();
			}
			catch(Exception e){
				e.printStackTrace();
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
			
	}
	
	@POST
	@Path("/createpayment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPayment(GiftVM giftVM){
		try{
			
			payment.setPaymenttype(giftVM.getPaymenttype());
			
			Date date	=	new Date();
			long time = date.getTime();
			payment.setPaymentdate(new Timestamp(time));
			
			price.setPriceid(Integer.parseInt(giftVM.getPriceid()));
			payment.setPrice(price);
			
			gc.setGiftcourseid(Integer.parseInt(giftVM.getGiftcourseid()));
			payment.setGiftcourse(gc);
			payment.setPaymentstatus(giftVM.getPaymentstatus());
			payment.setOrderno(giftVM.getOrderno());
			Integer savedobj=(Integer) invoicepaymentservice.save(payment);
			if(savedobj>0){
				return Response.status(Status.OK).entity(savedobj).build();	
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/giftcoursehistory")
	@Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGiftCourseHistory(){
		try{
			List<?> giftCourseList = gcsobj.getGiftCourseHistory(getAttribute("orgpersonid"));
			
			if(giftCourseList.size()>0){
				return Response.ok().entity(giftCourseList).build();
			}
			else{
				return Response.ok().entity(Status.PRECONDITION_FAILED).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	@POST
	@Path("/giftcoursepayment")
	@Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGiftCoursePayment(GiftVM giftVM){
		try{
			List<?> giftCourseList = gcsobj.getGiftCoursePayment(Integer.parseInt(giftVM.getGiftcourseid()));
			
			if(giftCourseList.size()>0){
				return Response.ok().entity(giftCourseList).build();
			}
			else{
				return Response.ok().entity(Status.PRECONDITION_FAILED).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
