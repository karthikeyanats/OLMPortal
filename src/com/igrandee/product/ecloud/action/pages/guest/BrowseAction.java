package com.igrandee.product.ecloud.action.pages.guest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.guest.BrowseService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam;

@Path("/browse")
public class BrowseAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	private static Logger BrowseActionlogger = Logger.getLogger(BrowseAction.class);

	@InjectParam
	BrowseService browseService;

	@InjectParam
	OrganizationViewService organizationViewService; 

	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public Response boards(){
		try{
			
			List<?> boardsList=browseService.getBoards(getOrgidValue());
			if(boardsList.size()>0){
				if(boardsList!=null){
					return Response.status(Status.OK).entity(boardsList).build();
				}else{
					return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
				}	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(BrowseActionlogger.isInfoEnabled())	{
				BrowseActionlogger.error("error in boards() in BrowseAction ",e);
			}
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/lboards")
	@Produces( MediaType.APPLICATION_JSON)
	public Response limitedBoards(){
		try{
			List<?> boardsList=browseService.getLimitedBoards(getOrgidValue());
			if(boardsList.size()>0){
				return Response.status(Status.OK).entity(boardsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(BrowseActionlogger.isInfoEnabled())	{
				BrowseActionlogger.error("error in boards() in BrowseAction ",e);
			}
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}


	@GET
	@Path("/category")	
	@Produces( MediaType.APPLICATION_JSON)
	public Response categories(@QueryParam("boardid") int boardid ){
		try{
			
			List<?> categoriesList=browseService.getCategories(boardid,getOrgidValue());
			if(categoriesList!=null){
				return Response.status(Status.OK).entity(categoriesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(BrowseActionlogger.isInfoEnabled())	{
				BrowseActionlogger.error("error in boards() in BrowseAction ",e);
			}
			e.printStackTrace();
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
