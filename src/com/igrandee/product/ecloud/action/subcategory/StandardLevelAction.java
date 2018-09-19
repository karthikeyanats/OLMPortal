package com.igrandee.product.ecloud.action.subcategory;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.model.StandardLevel;
import com.igrandee.product.ecloud.service.subcategory.StandardLevelService;
import com.igrandee.product.ecloud.viewmodel.subcategory.StandardLevelVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/standard")
public class StandardLevelAction {

	@InjectParam
	StandardLevel standardlevel;

	@InjectParam
	StandardLevelService standardlevelService;

	@GET
	@Path("/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStandardSetails(@PathParam("status") String status) {
		try{
			List<?> standardlist = standardlevelService.getStandardLevelList(status);
			return Response.status(Status.OK).entity(standardlist).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStandardlevel(StandardLevelVM standardlevelVM) {
		try {
			String status=standardlevelVM.getStatus();
			if(status.equalsIgnoreCase("Save")){
				List<?> standardavailability=standardlevelService.getStandardavailability(standardlevelVM.getStandardlevelname());
				if(!standardavailability.isEmpty()){
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}else{
					standardlevel.setStandardlevelname(standardlevelVM.getStandardlevelname());
					standardlevel.setStandardlevelstatus(standardlevelVM.getStandardlevelstatus());
					standardlevelService.save(standardlevel);								
					return Response.status(Status.OK).entity(Status.OK).build();
				}
			}else if(status.equalsIgnoreCase("Update")){
				List<?> standardavailability=standardlevelService.getStandardavailability(standardlevelVM.getStandardlevelname());
				if(!standardavailability.isEmpty()){
					if(standardavailability.contains(standardlevelVM.getStandardlevelid())){
						if(standardlevelVM.getStandardlevelstatus()=='D'?!standardlevelService.isCoursesPublishedorDrafted(standardlevelVM.getStandardlevelid()):true){
						standardlevel.setStandardlevelid(standardlevelVM.getStandardlevelid());
						standardlevel.setStandardlevelname(standardlevelVM.getStandardlevelname());
						standardlevel.setStandardlevelstatus(standardlevelVM.getStandardlevelstatus());
						standardlevelService.update(standardlevel);			
						return Response.status(Status.OK).entity(Status.OK).build();
						}
						else{
							return Response.status(Status.OK).entity(Status.CONFLICT).build();	
						}
					}else{
						return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
					}
				}else{
					if(!standardlevelService.isCoursesPublishedorDrafted(standardlevelVM.getStandardlevelid())){
					standardlevel.setStandardlevelid(standardlevelVM.getStandardlevelid());
					standardlevel.setStandardlevelname(standardlevelVM.getStandardlevelname());
					standardlevel.setStandardlevelstatus(standardlevelVM.getStandardlevelstatus());
					standardlevelService.update(standardlevel);			
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