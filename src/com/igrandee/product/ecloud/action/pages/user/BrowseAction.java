package com.igrandee.product.ecloud.action.pages.user;

import java.util.List;

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
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.Pages.user.BrowseUserService;
import com.sun.jersey.api.core.InjectParam;

@Path("/browseu")
public class BrowseAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger BrowseActionlogger = Logger.getLogger(BrowseAction.class);
	
	@InjectParam
	BrowseUserService browseUserService;
	
	@InjectParam
	private Course course;
	
	@InjectParam
	Courseenrollment courseenrollment;
	
	@InjectParam
	Orgperson orgperson;
	
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public Response userCourses(@QueryParam("categoryid") String categoryid){
		try{
			List<?> userCoursesList=browseUserService.getUserCategoryCourses(getAttribute("orgpersonid"), categoryid, getAttribute("organizationid"));
			if(userCoursesList!=null){
				return Response.status(Status.OK).entity(userCoursesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(BrowseActionlogger.isInfoEnabled())	{
				BrowseActionlogger.error("error in boards() in BrowseAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/wishlist")
	@Produces( MediaType.APPLICATION_JSON)
	public Response wishlistCourse(@QueryParam("courseid") String courseid){
		try	{ 
			course.setCourseid(Integer.parseInt(courseid));
			courseenrollment.setCourse(course);			  
			String personid=getAttribute("orgpersonid"); 
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			courseenrollment.setOrgperson(orgperson);
			courseenrollment.setCourseenrollmentstatus("W"); 
			Integer savedResult=browseUserService.save(courseenrollment);
			if(savedResult>0){
				return Response.status(Status.OK).entity(Status.OK).build();
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}
		}
		catch(Exception e){
			if(BrowseActionlogger.isInfoEnabled()){
				BrowseActionlogger.error("error in wishlistCourse() in BrowseAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
