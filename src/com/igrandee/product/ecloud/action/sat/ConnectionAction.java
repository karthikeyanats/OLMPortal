/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 *  
 * com.igrandee.products.action
 * ConnectionAction.java
 * Created Aug 23, 2014 10:41:17 AM
 * by Selvarajan_j
 */
 
package com.igrandee.product.ecloud.action.sat;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.adapter.SatConnectionAdapter;
import com.igrandee.product.ecloud.service.sat.ConnectionService;
import com.igrandee.product.ecloud.viewmodel.sat.BulkQuestionVM;
import com.igrandee.product.ecloud.viewmodel.sat.QuestionViewModel;
import com.igrandee.product.ecloud.viewmodel.sat.StudentAnsVM;
import com.igrandee.product.ecloud.viewmodel.sat.UpdateIlegalChar;

import com.igrandee.product.satutil.adapter.ConnectionAdapter;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author selvarajan_j
 * @date Sep 06 2014
 */ 
@Path("/connector")  
public class ConnectionAction {
	
	/**
	 * 
	 * @param planViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author selvarajan_j
	 * 
	 */
	
	Logger connectionActionLogger = Logger.getLogger(ConnectionAction.class);
	
	@InjectParam
	SatConnectionAdapter SatConnectionAdapter;  
	
	@InjectParam
	ConnectionAdapter connectionAdapter;
	
	@InjectParam
	ConnectionService connectionService;  
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveQuestion(QuestionViewModel questionViewModel){
		int isCategory= SatConnectionAdapter.saveQuestionData(questionViewModel);
		if(isCategory != 0){
			return Response.ok().status(Status.OK).entity(isCategory).build();
		}
		return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	@POST
	@Path("/saveBulkQst") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBulkQuestion(BulkQuestionVM bulkQuestionVM){
		int isCategory= SatConnectionAdapter.saveBulkQuestionData(bulkQuestionVM);
		if(isCategory != 0){
			return Response.ok().status(Status.OK).entity(isCategory).build();
		}
		return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Path("/getQuestionType")
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestionType(){
		List<?> questionType=connectionService.getQuestionType();
		
		if (questionType != null && questionType.size() != 0)
			return Response.status(Status.OK).entity(questionType).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/getChapter")
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getChapter(@QueryParam("lessionid") String lessionid){
		List<?> chapter=connectionService.getChapter(lessionid);
		if (chapter != null && chapter.size() != 0)
			return Response.status(Status.OK).entity(chapter).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/getbreadcrumb")
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getBreadCrumb(@QueryParam("lessionid") String lessionid){
		List<?> chapter=connectionService.getBreadCrumb(lessionid);
		if (chapter != null && chapter.size() != 0)
			return Response.status(Status.OK).entity(chapter).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getQuestions") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestions(@QueryParam("lectureid") String lectureid,@QueryParam("typeId") String typeId){
		List<?> question=connectionService.getQuestions(Integer.parseInt(lectureid),Integer.parseInt(typeId));
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}

	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getQuestionbyid") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestionbyid(@QueryParam("questionid") String questionid){
		List<?> question=connectionService.getQuestionbyid(Integer.parseInt(questionid));
		 
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getQuestionQuiz") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestionQuiz(@QueryParam("questionid") int questionid,@QueryParam("userid") int userid,@QueryParam("sessionid") int sessionid,@QueryParam("lquizid") int lquizid){
		List<?> quiz= SatConnectionAdapter.getQuestionQuiz(questionid,userid,sessionid,lquizid);
		if (quiz != null && quiz.size() != 0)
			return Response.status(Status.OK).entity(quiz).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of questions IDs 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getQuestionID") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getQuestionID(@QueryParam("lectureid") String lectureid,@QueryParam("typeId") String typeId,@QueryParam("category") String category){
		List<?> question=connectionService.questionID(Integer.parseInt(lectureid),Integer.parseInt(typeId),category);
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of questions Evaluation 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/evaluation") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getEvalucation(@QueryParam("lectureid") String lectureid){
		List<?> question=connectionService.getEvaluation(Integer.parseInt(lectureid));
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @param lectureid
	 * @return Student Answer status for the current question
	 */
	@GET
	@Path("/studentquizans") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getStudentquizans(@QueryParam("questionid") String questionid){
		List<?> question=connectionService.getStudentquizans(questionid);
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	
	
	/**
	 * 
	 * @return List Collections of questions Evaluation 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getSTDAnswer") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getSTDAnswer(@QueryParam("questionid") String questionid){
		List<?> question=SatConnectionAdapter.getSTDAnswer(Integer.parseInt(questionid));
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of student Result 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getSDTResult") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getSTDResult(@QueryParam("lectureid") String lectureid, @QueryParam("time") String time){
		List<?> question= SatConnectionAdapter.getSTDResult(Integer.parseInt(lectureid),time);
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	
	/**
	 * 
	 * @return List Collections of questions IDs 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/questionID") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getquestionID(@QueryParam("lectureid") String lectureid){
		List<?> question=SatConnectionAdapter.getquestionID(Integer.parseInt(lectureid));
		if (question != null && question.size() != 0)
			return Response.status(Status.OK).entity(question).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * Get SAT EXAM STATUS
	 * @param lectureid
	 * @return
	 */
	@GET
	@Path("/getSATSatus") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getSATSatus(@QueryParam("lectureid") String lectureid){
		List<?> satStatus=SatConnectionAdapter.getSATSatus(lectureid);
		if (satStatus != null && satStatus.size() != 0)
			return Response.status(Status.OK).entity(satStatus).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * Save Student Answer For the question
	 * @param studentAnsVM
	 * @return
	 */
	
	@POST
	@Path("/saveAnswer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveAnswer(StudentAnsVM studentAnsVM){
		int isCategory= SatConnectionAdapter.saveAnswer(studentAnsVM);
		if(isCategory != 0){
			return Response.ok().status(Status.OK).entity(isCategory).build();
		}
		return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * Update Student Answer For the question
	 * @param studentAnsVM
	 * @return
	 */
	
	@POST
	@Path("/updateAnswer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAnswer(StudentAnsVM studentAnsVM){
		int isCategory= SatConnectionAdapter.updateAnswer(studentAnsVM);
		if(isCategory != 0){
			return Response.ok().status(Status.OK).entity(isCategory).build();
		}
		return Response.ok().status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/fileUri")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFileUploadedUri(@FormDataParam("file") List<FormDataBodyPart> files,@FormDataParam("drawobject") String drawobject){
		Collection<String> uriCollection = new LinkedHashSet<String>();
		try {
			if(drawobject.length() >0 ){
				connectionActionLogger.info("IDFFFFF "+drawobject);
				uriCollection.add(saveimage(drawobject));
			}else{
				
				for (int i = 0; i < files.size(); i++) {
					FormDataBodyPart this_formDataBodyPartFile = files.get(i);
					ContentDisposition this_contentDispositionHeader = this_formDataBodyPartFile.getContentDisposition();
					InputStream this_fileInputStream = this_formDataBodyPartFile.getValueAs(InputStream.class);
					String actualFilePath = SatConnectionAdapter.uploadMedia(this_fileInputStream, (FormDataContentDisposition) this_contentDispositionHeader); 
					uriCollection.add(actualFilePath);
				};
			}
			if(! uriCollection.isEmpty()){
				return Response.status(Status.ACCEPTED).entity(uriCollection).build();
			};
		} catch (Exception e) {
			connectionActionLogger.error(e);
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	private String saveimage(String drawObject){
		String dir=null;
		try{
		    byte[] decodedBytes = DatatypeConverter.parseBase64Binary(drawObject);
	        BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));    
	        
	          dir = SatConnectionAdapter.getFileName();
	        File outputfile = new File(dir);
	        ImageIO.write(bfi , "png", outputfile);
	        bfi.flush();
		}catch(Exception e)
        {  
			connectionActionLogger.error(e);
        }
		return dir;
	}
	
	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@GET
	@Path("/getilligalchar") 
	@Produces(MediaType.APPLICATION_JSON)
 	public Response getIlligalChar(@QueryParam("illigaltype") String illigaltype){
		List<?> illigal= connectionService.getIlligalChar(illigaltype);
		if (illigal != null && illigal.size() != 0)
			return Response.status(Status.OK).entity(illigal).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_FOUND).build();
	}
	
	/**
	 * 
	 * @return List Collections of questions 
	 * @param courselectureid
	 * @author selvarajan_j
	 */
	@POST
	@Path("/updategetilligal") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
 	public Response updategetilligal(UpdateIlegalChar illigalchar){
		int count= connectionService.updategetilligal(illigalchar.getIlligalchar(),illigalchar.getId(),illigalchar.getType());
		if (count != 0)
			return Response.status(Status.OK).entity(count).build();
		else
			return Response.ok().status(Status.OK).entity(Status.NOT_MODIFIED).build();
	}
	
}

