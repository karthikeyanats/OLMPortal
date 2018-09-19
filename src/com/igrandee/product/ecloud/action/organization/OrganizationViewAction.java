package com.igrandee.product.ecloud.action.organization;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.action.organization.view.OrganizationView;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam;

@Path("/organizationlist")
public class OrganizationViewAction extends AbstractIGActionSupport 
{

 
	private static final long serialVersionUID = 1L;

	@InjectParam
	OrganizationViewService organizationViewService;
		
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizationList(@QueryParam("status") String status) {
		List organizationList = organizationViewService.getAllOrganizations(status);	
		if(!organizationList.isEmpty()){
				return Response.status(Status.OK).entity(organizationList).build();
			}
			else{

				  return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
			}
			
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrganization(OrganizationView organizationVO,@QueryParam("organizationid") long organizationid){
		String orgstatus=organizationVO.getOrganizationstatus();
		Integer org = null;
		{		
			org=organizationViewService.updateOrganizationStatus(organizationid, orgstatus);
		
		}
		if(org!=null)		  
			return Response.status(Status.CREATED).entity(Status.CREATED).build();
		else
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	@GET
	@Path("/trashedlist")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrashOrganizationList() {
		List<?> organizationList = organizationViewService.getTrashOrganizations();			
			if(!organizationList.isEmpty()){
				return Response.status(Status.OK).entity(organizationList).build();
			}
			else{

				  return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
	}
	@POST
	@Path("/deleteorganization")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public  Response deleteOrganization(OrganizationView organizationVO,@QueryParam("organizationid") long organizationid){
		String orgstatus=organizationVO.getOrganizationstatus();
		Integer org = null;
		{		
			org=organizationViewService.deleteOrganizationStatus(organizationid, orgstatus);
		
		}
		if(org!=null)		  
			return Response.status(Status.CREATED).entity(Status.CREATED).build();
		else
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}	
	@GET
	@Path("/organizationdetail/{organizationid}/{status}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response organizationDetail(@PathParam("organizationid") String organizationid,
			@PathParam("status") String status){
			List<?> organizationdetail = organizationViewService.getOrganizationDetail(organizationid, status);
		if(organizationdetail.size()!=0){
			return Response.status(Status.OK).entity(organizationdetail).build();			
		}
		else{
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GET
	@Path("/orgmaxuser/{status}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response orgmaxuser(@PathParam("status") String status){
			List<?> organizationdetail = organizationViewService.getmaxuser(status);
		if(organizationdetail.size()!=0){
			return Response.status(Status.OK).entity(organizationdetail).build();			
		}
		else{
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GET
	@Path("/orgmaxusers")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response orgmaxusers(){
			List<?> organizationdetail = organizationViewService.getmaxusers();
		if(organizationdetail.size()!=0){
			return Response.status(Status.OK).entity(organizationdetail).build();			
		}
		else{
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
}
