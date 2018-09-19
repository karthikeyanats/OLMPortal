package com.igrandee.product.ecloud.action.subcategory;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.subcategory.BoardCategoryService;
import com.igrandee.product.ecloud.viewmodel.subcategory.CategoryVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/boardcategory")
public class BoardCategoryAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	Board board;

	@InjectParam
	Coursecategory coursecategory;

	@InjectParam
	BoardCategoryService boardCategoryService;

	@InjectParam
	OrganizationViewService organizationViewService;

	private String organizationid;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getboardCategories(CategoryVM categoryVM) {
		try{
			List<?> boardlist = boardCategoryService.getBoardCategoriesList(categoryVM.getCoursecategorystatus(),categoryVM.getBoardid());
			return Response.status(Status.OK).entity(boardlist).build();
		}catch(Exception e){
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
			
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> categoryCourseslist = boardCategoryService.getCategoryCoursesList(categoryVM.getCoursecategoryid(),organizationid);
			return Response.status(Status.OK).entity(categoryCourseslist).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryAuthors(@QueryParam("coursecategoryid")int coursecategoryid){
		if(coursecategoryid>0){
		List<?> categorywiseAuthorList=boardCategoryService.getCategoryWiseAuthors(coursecategoryid, getAttribute("organizationid")) ;
		return Response.status(Status.OK).entity(categorywiseAuthorList).build();
		}
		return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateboard(CategoryVM categoryVM) {
		try {
			String status=categoryVM.getStatus();
			if(status.equalsIgnoreCase("Save")){
				List<?> categoryavailability=boardCategoryService.getCourseCategoryavailability(categoryVM.getBoardid(),categoryVM.getCoursecategoryname());
				if(!categoryavailability.isEmpty()){
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}else{
					board.setBoardid(Integer.parseInt(categoryVM.getBoardid()));
					coursecategory.setBoard(board);
					coursecategory.setCoursecategoryname(categoryVM.getCoursecategoryname());
					coursecategory.setCoursecategorydescription(categoryVM.getCoursecategorydescription());
					coursecategory.setCoursecategorystatus(categoryVM.getCoursecategorystatus());
					boardCategoryService.save(coursecategory);
					return Response.status(Status.OK).entity(Status.OK).build();
				}
			}else if(status.equalsIgnoreCase("Update")){
				List<?> categoryavailability=boardCategoryService.getCourseCategoryavailability(categoryVM.getBoardid(),categoryVM.getCoursecategoryname());
				if(!categoryavailability.isEmpty()){
					if(categoryavailability.contains(Integer.parseInt(categoryVM.getCoursecategoryid()))){												
						if(categoryVM.getCoursecategorystatus().equals("D")?!boardCategoryService.isCoursesPublishedorDrafted(Integer.parseInt(categoryVM.getCoursecategoryid())):true){
						board.setBoardid(Integer.parseInt(categoryVM.getBoardid()));
						coursecategory.setBoard(board);
						coursecategory.setCoursecategoryid(Integer.parseInt(categoryVM.getCoursecategoryid()));
						coursecategory.setCoursecategoryname(categoryVM.getCoursecategoryname());
						coursecategory.setCoursecategorydescription(categoryVM.getCoursecategorydescription());
						coursecategory.setCoursecategorystatus(categoryVM.getCoursecategorystatus());
						boardCategoryService.update(coursecategory);
						return Response.status(Status.OK).entity(Status.OK).build();
						}
						else{
							return Response.status(Status.OK).entity(Status.CONFLICT).build();	
						}
					}else{
						return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
					}
				}else{
					if(!boardCategoryService.isCoursesPublishedorDrafted(Integer.parseInt(categoryVM.getCoursecategoryid()))){
					board.setBoardid(Integer.parseInt(categoryVM.getBoardid()));
					coursecategory.setBoard(board);
					coursecategory.setCoursecategoryid(Integer.parseInt(categoryVM.getCoursecategoryid()));
					coursecategory.setCoursecategoryname(categoryVM.getCoursecategoryname());
					coursecategory.setCoursecategorydescription(categoryVM.getCoursecategorydescription());
					coursecategory.setCoursecategorystatus(categoryVM.getCoursecategorystatus());
					boardCategoryService.update(coursecategory);
					return Response.status(Status.OK).entity(Status.OK).build();
					}
					else{
						return Response.status(Status.OK).entity(Status.CONFLICT).build();	
					}
				}
			}else{
				return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}	
}