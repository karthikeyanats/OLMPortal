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

import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.service.subcategory.BoardService;
import com.igrandee.product.ecloud.viewmodel.subcategory.BoardVM;
import com.sun.jersey.api.core.InjectParam;

@Path("/board")
public class BoardAction {

	@InjectParam
	Board board;

	@InjectParam
	BoardService boardservice;

	@GET
	@Path("/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getboarddetails(@PathParam("status") String status) {
		List<?> boardlist = null;
		try{
			boardlist = boardservice.getBoardList(status);
		}catch(Exception e){
			e.printStackTrace();
		}
		return Response.ok().entity(boardlist).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateboard(BoardVM boardVM) {
		try {
			String status=boardVM.getStatus();
			if(status.equalsIgnoreCase("Save")){
				List<?> boardavailability=boardservice.getBoardavailability(boardVM.getBoardname());
				if(!boardavailability.isEmpty()){
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}else{
					board.setBoardname(boardVM.getBoardname());
					board.setBoardstatus(boardVM.getBoardstatus());
					boardservice.save(board);	
					return Response.status(Status.OK).entity(Status.OK).build();
				}
			}else if(status.equalsIgnoreCase("Update")){
				List<?> boardavailability=boardservice.getBoardavailability(boardVM.getBoardname());
				if(!boardavailability.isEmpty()){
					if(boardavailability.contains(boardVM.getBoardid())){
						 if(boardVM.getBoardstatus()=='D'?!boardservice.isCoursesPublishedorDrafted(boardVM.getBoardid()):true){							
						board.setBoardid(boardVM.getBoardid());
						board.setBoardname(boardVM.getBoardname());
						board.setBoardstatus(boardVM.getBoardstatus());
						boardservice.update(board);
						return Response.status(Status.OK).entity(Status.OK).build();
					  }
					  else
					    return Response.status(Status.OK).entity(Status.CONFLICT).build();
					}else{
						return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
					}
				}else{
					if(!boardservice.isCoursesPublishedorDrafted(boardVM.getBoardid())){
					board.setBoardid(boardVM.getBoardid());
					board.setBoardname(boardVM.getBoardname());
					board.setBoardstatus(boardVM.getBoardstatus());
					boardservice.update(board);
					return Response.status(Status.OK).entity(Status.OK).build();
					}
					  else
					    return Response.status(Status.OK).entity(Status.CONFLICT).build();
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