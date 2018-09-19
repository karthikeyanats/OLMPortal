package com.igrandee.product.ecloud.action.pages.guest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.guest.GuestCoursesService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam; 
 
@Path("/home")
public class GuestCoursesAction extends MasterActionSupport{
	
	private static final long serialVersionUID = 1L;

	private static Logger GuestCoursesServicelogger = Logger.getLogger(GuestCoursesService.class);
	
	@InjectParam
	GuestCoursesService guestCoursesService;
	
	@InjectParam
	OrganizationViewService organizationViewService;
	
  	
	@Path("/tc")
 	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public Response Topboards(){
		try{
			List<?> boardcategoriesList=guestCoursesService.getBoardWiseCategories(getOrgidValue());
			if(boardcategoriesList!=null){
				return Response.status(Status.OK).entity(boardcategoriesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(GuestCoursesServicelogger.isInfoEnabled())	{
				GuestCoursesServicelogger.error("error in boards() in GuestCoursesAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	private String getOrgidValue(){
		String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String organizationid = null;
		if(userCheck.equalsIgnoreCase("anonymousUser"))	{
			List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
			Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
			organizationid = ""+map.get("organizationid");	
		} else {
			organizationid=""+getAttribute("organizationid"); 
		}
		return organizationid;
	}
}
