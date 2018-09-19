package com.igrandee.product.ecloud.action.pages.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.user.MyCoursesService;
import com.sun.jersey.api.core.InjectParam;

@Path("/mycourses")
public class MyCoursesAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	MyCoursesService mcs;
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserCounts(){
		try{
			List<?> list = mcs.loadMyCoursesCount(getAttribute("orgpersonid"),getAttribute("organizationid"));
			return ResponseBuilder(list);
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/teaching/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachingCourses(@PathParam("status") String coursestatus){
		try{
			List<?> list = mcs.getTeachingCourses(coursestatus,Long.parseLong(getAttribute("personid")));
			return ResponseBuilder(list);
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/learning/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLearningCourses(@PathParam("status") String status){
		try{
			List<?> list = mcs.learningCourses(Integer.parseInt(getAttribute("orgpersonid")),status);
			return ResponseBuilder(list);
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private Response ResponseBuilder(List<?> list){
		if(list.size()!=0){
			return Response.status(Status.OK).entity(list).build();	
		}else{
			return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
		}
	}
}
