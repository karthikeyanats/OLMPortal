package com.igrandee.product.ecloud.action.course;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Contentapproval;
import com.igrandee.product.ecloud.model.Lecturecontent;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.ContentApproveService;
import com.igrandee.product.ecloud.service.users.NotificationService;
import com.igrandee.product.ecloud.viewmodel.ContentApproveVO;
import com.sun.jersey.api.core.InjectParam;

@Path("/approve")
public class ContentApproveAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	private static Logger ContentApproveActionlogger = Logger.getLogger(ContentApproveAction.class);

	@InjectParam
	ContentApproveService contentApproveServiceobj;

	@InjectParam
	NotificationService notificationService;

	@InjectParam
	Contentapproval contentapproval; 

	@InjectParam
	Lecturecontent content ;

	@InjectParam
	Orgperson approvedby ;

	@GET
	@Path("/{courseid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response waitingContentApprovals(@PathParam("courseid") String courseid){
		try{
			List<?> waitingContentApprovalsList=contentApproveServiceobj.getWaitingContentApproval(courseid);
			if(waitingContentApprovalsList!=null){
				return Response.status(Status.OK).entity(waitingContentApprovalsList).build();
			}
			else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(ContentApproveActionlogger.isInfoEnabled())	{
				ContentApproveActionlogger.error("error in waitingContentApprovals() in ContentApproveAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/content/{courselectureid}")
	@Produces( MediaType.APPLICATION_JSON)
	public Response contentStatuses(@PathParam("courselectureid") String courselectureid){
		try{
			List<?> contentStatusesList=contentApproveServiceobj.contentStatuses(courselectureid);
			if(contentStatusesList!=null){
				return Response.status(Status.OK).entity(contentStatusesList).build();
			}
			else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			if(ContentApproveActionlogger.isInfoEnabled())	{
				ContentApproveActionlogger.error("error in contentStatuses() in ContentApproveAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approve(ContentApproveVO contentApproveVO){
		try{
			content.setContentid(Integer.parseInt(contentApproveVO.getContentid()));
			contentapproval.setContent(content);

			approvedby.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			contentapproval.setApprovedby(approvedby);

			contentapproval.setApprovestatus(contentApproveVO.getApprovestatus().charAt(0));
			contentapproval.setDescription(java.net.URLDecoder.decode(contentApproveVO.getDescription()));
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp dateofapproval =new Timestamp(date.getTime());
			contentapproval.setDateofapproval(dateofapproval);
			String statusfor=null;
			if(contentApproveVO.getContentapprovalid()!=null){
				contentapproval.setContentapprovalid(Integer.parseInt(contentApproveVO.getContentapprovalid()));
				Contentapproval contentapprovalstatus=contentApproveServiceobj.update(contentapproval);
				if(contentapprovalstatus!=null){
					statusfor="OK";
				}else{
					statusfor="NOTOK";
				}
			}
			else{
				Integer ApprovedStatus=contentApproveServiceobj.save(contentapproval);
				if(ApprovedStatus!=null){
					statusfor="OK";
				}else{
					statusfor="NOTOK";
				}
			}
			if(statusfor.equalsIgnoreCase("OK")){
				
				List personList=null;
				personList=new ArrayList();
				HashMap persson=new HashMap();
				persson.put("orgpersonid",contentApproveVO.getAuthororgpersonid() );
				personList.add(persson);
				
				String fromorgid=getAttribute("orgpersonid");
				List toorgid=personList;		
				String notdesc=notificationDescription(contentApproveVO.getApprovestatus(),contentApproveVO.getContentid(),contentApproveVO.getDescription());
				String notimsg="Content Approve";
				 
				NotificationAction na=new NotificationAction();
				na.notificationDetails(fromorgid,toorgid,notdesc,notimsg,notificationService);
			 
				return Response.status(Status.OK).entity(Status.OK).build();	
			}else{
				return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
			}

		}
		catch(Exception e){
			if(ContentApproveActionlogger.isInfoEnabled())	{
				ContentApproveActionlogger.error("error in create() in ContentApproveAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/course")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveCourse(ContentApproveVO contentApproveVO){
		try{
			String courseid=contentApproveVO.getCourseid();
			String coursestatus=contentApproveVO.getCoursestatus();
			String coursetitle=contentApproveVO.getCoursetitle();
			String authorpersonid=contentApproveVO.getAuthororgpersonid();
			String updatedStatus=contentApproveServiceobj.updateCourseStatus(courseid,coursestatus);
			if(updatedStatus.equalsIgnoreCase("OK")){
				String notdesc=null;
				if(coursestatus.equalsIgnoreCase("A")){
					notdesc="Your Course "+coursetitle+" has been published. Now the Course is visible to all users in the Portal.";
				}
				else if(coursestatus.equalsIgnoreCase("D")){
					notdesc="Your Course "+coursetitle+" has not been published due to the fixes that has to be made. You can find the issues in the content upload part in manage Course Section. Please Fix the issues and publish again. You will now find the course in the draft courses section.";
				}
				
				List personList=null;
				personList=new ArrayList();
				HashMap persson=new HashMap();
				persson.put("orgpersonid",authorpersonid );
				personList.add(persson);
				
				String fromorgid=getAttribute("orgpersonid");
				List toorgid=personList;				
				String notimsg="Course Approve";
  				NotificationAction na=new NotificationAction();
				na.notificationDetails(fromorgid,toorgid,notdesc,notimsg,notificationService); 
  				return Response.status(Status.OK).entity(Status.OK).build();	
			}
			else{
				return Response.status(Status.OK).entity(Status.BAD_REQUEST).build();
			}			
		}
		catch(Exception e){
			if(ContentApproveActionlogger.isInfoEnabled())	{
				ContentApproveActionlogger.error("error in approveCourse() in ContentApproveAction ",e);
			}
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String notificationDescription(String nottype,String contentid,String description){
		try{
			List<?> contentDirPath=contentApproveServiceobj.contentRootPath(contentid);
			String category="";
			String course="";
			String section="";
			String lecture="";
			for(int i=0;i<contentDirPath.size();i++){
				Map FoldertitleMap=(Map) contentDirPath.get(i);
				category=""+FoldertitleMap.get("coursecategoryname");				
				course=""+FoldertitleMap.get("coursetitle");
				section=""+FoldertitleMap.get("coursesectiontitle");
				lecture=""+FoldertitleMap.get("courselecturetitle");
			}
			String descmessage=null;
			if(nottype.equalsIgnoreCase("A")){
				descmessage = "Your Content for "+category+" / "+course+" / "+section+" / "+lecture+" has been published .";
			}
			else if(nottype.equalsIgnoreCase("R")){
				descmessage = "Your Content for "+category+" / "+course+" / "+section+" / "+lecture+" has been rejected and the reason can be found in the upload section of your content.";
			}
			return descmessage;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
