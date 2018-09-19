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

import com.igrandee.product.ecloud.model.Medium;
import com.igrandee.product.ecloud.service.subcategory.MediumService;
import com.igrandee.product.ecloud.viewmodel.subcategory.MediumVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/medium")
public class MediumAction {

	@InjectParam
	Medium medium;

	@InjectParam
	MediumService mediumservice;

	@GET
	@Path("/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMediumList(@PathParam("status") String status) {
		try{
			List<?> mediumlist=mediumservice.getMediumList(status);
			return Response.status(Status.OK).entity(mediumlist).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMedium(MediumVM mediumVM) {
		try {
			String status=mediumVM.getStatus();
			if(status.equalsIgnoreCase("Save")){
				List<?> mediumavailability=mediumservice.getMediumavailability(mediumVM.getMediumname());
				if(!mediumavailability.isEmpty()){
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}else{
					medium.setMediumname(mediumVM.getMediumname());
					medium.setMediumstatus(mediumVM.getMediumstatus());
					mediumservice.save(medium);					
					return Response.status(Status.OK).entity(Status.OK).build();
				}
			}else if(status.equalsIgnoreCase("Update")){
				List<?> mediumavailability=mediumservice.getMediumavailability(mediumVM.getMediumname());
				if(!mediumavailability.isEmpty()){
					if(mediumavailability.contains(mediumVM.getMediumid())){
						if(mediumVM.getMediumstatus()=='D'?!mediumservice.isCoursesPublishedorDrafted(mediumVM.getMediumid()):true){
						medium.setMediumid(mediumVM.getMediumid());
						medium.setMediumname(mediumVM.getMediumname());
						medium.setMediumstatus(mediumVM.getMediumstatus());
						mediumservice.update(medium);	
						return Response.status(Status.OK).entity(Status.OK).build();
						}
						else{
							return Response.status(Status.OK).entity(Status.CONFLICT).build();	
						}
					}else{
						return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
					}
				}else{
					if(!mediumservice.isCoursesPublishedorDrafted(mediumVM.getMediumid())){
					medium.setMediumid(mediumVM.getMediumid());
					medium.setMediumname(mediumVM.getMediumname());
					medium.setMediumstatus(mediumVM.getMediumstatus());
					mediumservice.update(medium);	
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