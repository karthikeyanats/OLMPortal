package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.CourseUserService;
import com.sun.jersey.api.core.InjectParam;

@Path("/courseusers")
public class CourseUserAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	
	@InjectParam
	CourseUserService cus;
	
	@GET
	@Path("/{status}/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)	
	public Response getCourseUsersList(@PathParam("status") String status,
			@PathParam("courseid") String courseid){
		try{
			List<?> courseUsersList;
			if(status.equalsIgnoreCase("N")){
				courseUsersList = cus.getRemainingUsers(courseid,getAttribute("organizationid"));
			}else{
				courseUsersList = cus.loadCourseUsersList(status, courseid);	
			}
			if(courseUsersList.size()>0){
				return Response.status(Status.OK).entity(courseUsersList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT
	@Path("/update/{status}/{courseenrollmentid}")
	@Produces( MediaType.APPLICATION_JSON)	
	public Response updateEnrollmentStatus(@PathParam("status") String status,
			@PathParam("courseenrollmentid") String courseenrollmentid){
		try{
			String confirmation = cus.updateCourseEnrollStatus(courseenrollmentid,status);
			if(confirmation.equalsIgnoreCase("OK")){
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
			}			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
