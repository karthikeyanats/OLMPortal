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
import com.igrandee.product.ecloud.model.royalty.Requestlimit;
import com.igrandee.product.ecloud.service.royalty.RequestLimitService;
import com.igrandee.product.ecloud.viewmodel.royalty.RequestLimitVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/requestlimit")
public class RequestLimitAction extends AbstractIGActionSupport{
	
	private static final long serialVersionUID = 1L;
	private static Logger requestLimitActionLogger	=	Logger.getLogger(RequestLimitAction.class);

	@InjectParam
	Requestlimit requestlimit;
	
	@InjectParam
	RequestLimitService requestLimitService;
	
	@InjectParam
	SessionFactory sessionFactory;
	
	@InjectParam
	Orgperson orgperson;

	@POST
	@Path("/createrequestlimit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRequestLimit(RequestLimitVM requestLimitVM){
		
		if(requestLimitVM!=null&&Integer.parseInt(requestLimitVM.getMinimumamount())>0){
			int requestlimitid=requestLimitService.getRequestLimitId();
			if(requestlimitid!=0){
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				requestlimit.setId(requestlimitid);
				requestlimit.setCreatedby(orgperson);
				requestlimit.setDateofcreation(new Date());
				requestlimit.setMinimumamount(Integer.parseInt(requestLimitVM.getMinimumamount()));
				requestlimit.setRequestlimitstatus('A');
			}
			Requestlimit status=requestLimitService.saveOrUpdate(requestlimit);						
			if(status!=null){
				return Response.ok().entity(Status.CREATED).build();
				}
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		else{
		        return Response.ok().entity(Status.BAD_REQUEST).build();
		}
		
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(RequestLimitVM requestLimitVM){
		
		if(requestLimitVM!=null&&Integer.parseInt(requestLimitVM.getMinimumamount())>0){
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			requestlimit.setCreatedby(orgperson);
			requestlimit.setDateofcreation(new Date());
			requestlimit.setMinimumamount(Integer.parseInt(requestLimitVM.getMinimumamount()));
			requestlimit.setRequestlimitstatus('A');
			 int status=requestLimitService.save(requestlimit);			
			if(status>0){
				return Response.ok().entity(Status.CREATED).build();
				}
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		else{
		        return Response.ok().entity(Status.BAD_REQUEST).build();
		}
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequestLimits(){
		try{
		List<?> requestLimits=requestLimitService.getRequestLimit();
		if(!requestLimits.isEmpty()){
			return Response.ok().status(Status.ACCEPTED).entity(requestLimits).build();
		}
		}
		catch(Exception e){
			if(requestLimitActionLogger.isInfoEnabled()){
				requestLimitActionLogger.info("error in getRequestLimits() in RequestLimitAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRequestLimits(RequestLimitVM requestLimitVM,@QueryParam("id")int id){
		
		if(requestLimitVM!=null&&Integer.parseInt(requestLimitVM.getMinimumamount())>0){
			requestlimit.setId(id);
			requestlimit.setMinimumamount(Integer.parseInt(requestLimitVM.getMinimumamount()));
			requestlimit.setRequestlimitstatus('A');
			requestlimit.setDateofcreation(new Date());
			orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			requestlimit.setCreatedby(orgperson);
			try{
			    requestLimitService.update(requestlimit);			
				return Response.ok().entity(Status.ACCEPTED).build();
			}
			catch(Exception e){
				if(requestLimitActionLogger.isInfoEnabled()){
					requestLimitActionLogger.info("error in updateRequestLimits() in RequestLimitAction"+e);
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}			
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRequestLimit(@QueryParam("id")int id){
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
	    Requestlimit requestlimit=(Requestlimit) session.load(Requestlimit.class, id);
	    requestlimit.setRequestlimitstatus('D');
		session.update(requestlimit);
		transaction.commit();
		session.close();
		}
		catch(Exception e){
			if(requestLimitActionLogger.isInfoEnabled()){
				requestLimitActionLogger.info("error in deleteRequestLimit() in RequestLimitAction"+e);
				transaction.rollback();
				session.close();
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}
		return Response.ok().entity(Status.ACCEPTED).build();	
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequestLimitById(@PathParam("id")int id){
		RequestLimitVM requestLimitVM=new RequestLimitVM();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
			Requestlimit requestlimit=(Requestlimit) session.load(Requestlimit.class, id);
			requestLimitVM.setId(""+requestlimit.getId());
			requestLimitVM.setMinimumamount(""+requestlimit.getMinimumamount());
			requestLimitVM.setRequestlimitstatus(""+requestlimit.getRequestlimitstatus());
			requestLimitVM.setDateofcreation(""+requestlimit.getDateofcreation());			
			transaction.commit();
			session.close();			
		}
		catch(Exception e){
			if(requestLimitActionLogger.isInfoEnabled()){
				requestLimitActionLogger.info("error in getRequestLimitById() in RequestLimitAction"+e);
				transaction.rollback();
				session.close();
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}		
		return Response.ok().entity(requestLimitVM).build();
		
	}
	
	@POST
	@Path("/requestlimitstatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RequestLimitStatus(RequestLimitVM requestLimitVM){

		Requestlimit requestlimit = requestLimitService.get(requestLimitVM.getRequestlimitid());
		requestlimit.setRequestlimitstatus('D');
		try{
		 requestLimitService.update(requestlimit);
		 return Response.ok().entity(Status.ACCEPTED).build();
		
		}
		catch(Exception e){
			if(requestLimitActionLogger.isInfoEnabled()){
				requestLimitActionLogger.info("error in RequestLimitStatus() in RequestLimitAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/requestlimitstatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response isRequestLimitSet(){
		int requestLimitId=requestLimitService.getRequestLimitId();
		if(requestLimitId>0){
		return Response.ok().entity(Status.OK).build();	
		}	
		return Response.ok().entity(Status.NO_CONTENT).build();
	}
}
