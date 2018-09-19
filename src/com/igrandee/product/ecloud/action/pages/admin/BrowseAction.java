package com.igrandee.product.ecloud.action.pages.admin;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.admin.BrowseServices;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.viewmodel.subcategory.CategoryVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/browseA")
public class BrowseAction extends MasterActionSupport{
	
	private static final long serialVersionUID = 1L;

	private static Logger BrowseActionlogger = Logger.getLogger(BrowseAction.class);
	
	@InjectParam
	BrowseServices browseService;
	
	@InjectParam
	OrganizationViewService organizationViewService; 
	
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public Response boards(){
		try{
			List<?> boardsList=browseService.getadminBoards(getAttribute("organizationid"),getAttribute("orgpersonid"));
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
	@Path("/category")	
	@Produces( MediaType.APPLICATION_JSON)
	public Response categories(@QueryParam("boardid") int boardid ){
		try{
			List<?> categoriesList=browseService.getadminCategories(boardid,getAttribute("organizationid"),getAttribute("orgpersonid")); 
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
	
	@POST
	@Path("/courses")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCategoryCourse(CategoryVM categoryVM) {
		try{			
			List<?> categoryCourseslist = browseService.getCategoryCoursesList(categoryVM.getCoursecategoryid(),getAttribute("organizationid"),getAttribute("orgpersonid"));
			return Response.status(Status.OK).entity(categoryCourseslist).build(); 
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}		
	}
	

}

