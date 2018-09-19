package com.igrandee.product.ecloud.action.pages.guest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.Pages.guest.GuestPreviewService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam;

@Path("/gpreview")
public class GuestPreviewAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	@InjectParam
	GuestPreviewService gps;

	@InjectParam
	OrganizationViewService organizationViewService;
	
	@GET
	@Path("/info/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseInfo(@PathParam("courseid") String courseid){
		try{
			List<?> coursepreviewlist=gps.loadCourseInfo(courseid);
			if(coursepreviewlist.size()!=0){
				return Response.status(Status.OK).entity(coursepreviewlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/curriculum/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseCurriculum(@PathParam("courseid") String courseid){
		try{
			List<?> coursecurriculumlist=gps.loadCourseCurriculum(courseid);
			if(coursecurriculumlist.size()!=0){
				return Response.status(Status.OK).entity(coursecurriculumlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/enrollstatus/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getEnrolledStatus(@PathParam("courseid") String courseid){
		try{
			List<?> courseEnrollStatusList=gps.checkEnrollmentStatus(courseid,getAttribute("orgpersonid"));
			if(courseEnrollStatusList.size()!=0){
				return Response.status(Status.OK).entity(courseEnrollStatusList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GET
	@Path("/reviews/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseReviews(@PathParam("courseid") String courseid){
		try{
			List<?> coursereviewslist=gps.loadCourseReviews(courseid);
			if(coursereviewslist.size()!=0){
				return Response.status(Status.OK).entity(coursereviewslist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/questions/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getCourseDiscussions(@PathParam("courseid") String courseid){
		try{
			List<?> coursediscussionlist=gps.loadCourseQuestions(courseid);
			if(coursediscussionlist.size()!=0){
				return Response.status(Status.OK).entity(coursediscussionlist).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/related/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response getRelatedCourse(@PathParam("courseid") String courseid){
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> relatedCoursesList=gps.loadRelatedCourse(courseid,organizationid);
			if(relatedCoursesList.size()!=0){
				return Response.status(Status.OK).entity(relatedCoursesList).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}