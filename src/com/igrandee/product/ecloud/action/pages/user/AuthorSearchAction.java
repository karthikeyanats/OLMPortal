package com.igrandee.product.ecloud.action.pages.user;

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
import com.igrandee.product.ecloud.service.Pages.user.AuthorSearchService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.sun.jersey.api.core.InjectParam;

@Path("/tutors")
public class AuthorSearchAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	AuthorSearchService ass;

	@InjectParam
	OrganizationViewService organizationViewService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTutorsList(){
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			}else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> tutorsList = ass.loadAuthorsList(organizationid) ;
			if(tutorsList!=null){
				return Response.status(Status.OK).entity(tutorsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/ratings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRatingsList(){
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			}else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> ratingsList = ass.getUserRatings(organizationid) ;
			if(ratingsList!=null){
				return Response.status(Status.OK).entity(ratingsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/courses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthorCoursesList(){
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			}else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> ratingsList = ass.getAuthorCourses(organizationid) ;
			if(ratingsList!=null){
				return Response.status(Status.OK).entity(ratingsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/individual/profile/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParticularAuthorProfileList(@PathParam("personid") String personid){
		try{
			List<?> authorProfile = ass.getParticularUserProfile(Long.parseLong(personid)) ;
			if(authorProfile!=null){
				return Response.status(Status.OK).entity(authorProfile).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/individual/courses/{orgpersonid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParticularAuthorCoursesList(@PathParam("orgpersonid") String orgpersonid){
		try{
			List<?> authorCourses = ass.getIndividualAuthorCourses(orgpersonid) ;
			if(authorCourses!=null){
				return Response.status(Status.OK).entity(authorCourses).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/individual/reviews/{orgpersonid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParticularAuthorReviewsList(@PathParam("orgpersonid") String orgpersonid){
		try{
			List<?> authorCourses = ass.getAuthorRatings(orgpersonid);
			if(authorCourses!=null){
				return Response.status(Status.OK).entity(authorCourses).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/topthreeratingcourse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response topthreeratingcourse(){
		try{
			List<?> authorCourses = ass.getRatingCourse(getAttribute("orgpersonid"),getAttribute("organizationid"));
			if(authorCourses!=null){
				return Response.status(Status.OK).entity(authorCourses).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}