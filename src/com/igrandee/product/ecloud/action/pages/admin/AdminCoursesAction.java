package com.igrandee.product.ecloud.action.pages.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.admin.AdminCoursesService;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/admincourses")
public class AdminCoursesAction extends MasterActionSupport {

	private static final long serialVersionUID = 1L;

	@InjectParam
	AdminCoursesService acs;

	@GET
	@Path("/counts")
	@Produces
	public Response getAuthorCourseCounts(){
		try{
			List<?> authorCoursesList = acs.loadAuthorCoursesCountForAdmin(getAttribute("organizationid"), getAttribute("orgpersonid"));
			if(authorCoursesList.size()!=0){
				return Response.status(Status.OK).entity(authorCoursesList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();	
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}		
	}

	@GET
	@Path("/courses/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorCourses(@PathParam("status") String status){
		try{
			List<?> authorCoursesList = acs.loadAuthorCoursesForAdmin(getAttribute("organizationid"), getAttribute("orgpersonid"), status);
			if(authorCoursesList.size()!=0){
				return Response.status(Status.OK).entity(authorCoursesList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();	
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}		
	}
}
