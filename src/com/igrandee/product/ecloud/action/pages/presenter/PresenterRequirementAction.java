package com.igrandee.product.ecloud.action.pages.presenter;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.service.Pages.presenter.PresenterRequirementService;
import com.sun.jersey.api.core.InjectParam;

@Path("/presenter")
public class PresenterRequirementAction {


	@InjectParam
	PresenterRequirementService prs;

	@GET
	@Path("/master/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response masterUserLoginCheck(@PathParam("username") String username,
			@PathParam("password") String password){
		try{
			List<?> loginCheck=prs.loadMasterUserLoginCheck(username, password);
			if(loginCheck.size()!=0){
				return Response.status(Status.OK).entity(loginCheck).build();	
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}				
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/orgs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOtherOrganizations(){
		try{
			List<?> otherOrganizations=prs.loadOrganizations();
			if(otherOrganizations.size()!=0){
				return Response.status(Status.OK).entity(otherOrganizations).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/other/{username}/{password}/{organizationid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOtherUserLoginCheck(@PathParam("username") String username,
			@PathParam("password") String password,@PathParam("organizationid") String organizationid){
		try{
			List<?> otherUserLoginResponseList=prs.loadOtherUserLoginCheck(username,password,organizationid);
			if(otherUserLoginResponseList.size()!=0){
				return Response.status(Status.OK).entity(otherUserLoginResponseList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getcategoryList(){
		try{
			List<?> categoryList=prs.loadCategoriesList();
			if(categoryList.size()!=0){
				return Response.status(Status.OK).entity(categoryList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/subcategory/{boardid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoriesList(@PathParam("boardid") String boardid){
		try{
			List<?> subCategoryList=prs.loadSubCategoriesList(boardid);
			if(subCategoryList.size()!=0){
				return Response.status(Status.OK).entity(subCategoryList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/courses/{categoryid}/{orgpersonid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseList(@PathParam("categoryid") String categoryid,
			@PathParam("orgpersonid") String orgpersonid){
		try{
			List<?> coursesList=prs.loadCourseList(categoryid,orgpersonid);
			if(coursesList.size()!=0){
				return Response.status(Status.OK).entity(coursesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/courseavailablity/{orgpersonid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourseList(
			@PathParam("orgpersonid") String orgpersonid){
		try{
			List<?> coursesList=prs.loadPrivateCourseList(orgpersonid);
			if(coursesList.size()!=0){
				return Response.status(Status.OK).entity(coursesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	

	@GET
	@Path("/sections/{courseid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSectionsList(@PathParam("courseid") String courseid){
		try{
			List<?> sectionsList=prs.loadSectionsList(courseid);
			if(sectionsList.size()!=0){
				return Response.status(Status.OK).entity(sectionsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}