package com.igrandee.product.ecloud.action.pages.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.igrandee.product.ecloud.service.Pages.admin.LiveSessionConductService;
import com.sun.jersey.api.core.InjectParam;

@Path("/conduct")
public class LiveSessionConductAction {

	@InjectParam
	LiveSessionConductService lscs;
	
	@GET
	@Path("/participants/{livesessionid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecipientsList(@PathParam("livesessionid") String livesessionid){
		try{
			List<?> participantsList = lscs.loadParticipantsList(livesessionid);
			if(participantsList.size()!=0){
				return Response.status(Status.OK).entity(participantsList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}		
	}
	
	@GET
	@Path("/desc/{livesessionid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionDescription(@PathParam("livesessionid") String livesessionid){
		try{
			List<?> sessionDescriptionList = lscs.loadSessionDescription(livesessionid);
			if(sessionDescriptionList.size()!=0){
				return Response.status(Status.OK).entity(sessionDescriptionList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}		
	}	
	
	@GET
	@Path("/lectures/{courseid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLectureContentList(@PathParam("courseid") String courseid){
		try{
			List<?> lecturesList = lscs.getLectureContentList(courseid);
			if(lecturesList.size()!=0){
				return Response.status(Status.OK).entity(lecturesList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}	
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();	
		}		
	}
	

}