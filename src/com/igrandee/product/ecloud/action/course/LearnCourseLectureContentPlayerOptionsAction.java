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
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.action.course
 * LearnCourseLectureContentPlayerOptionsActions.java
 * Created Jul 8, 2014 10:34:46 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.course;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.model.Learningstatistic;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.LearnCourseLectureContentPlayerOptionService;
import com.igrandee.product.ecloud.viewmodel.LearnCourseLectureContentPlayerOptionsViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 *
 */
@Path("/learnCourseStatistics")  
public class LearnCourseLectureContentPlayerOptionsAction extends MasterActionSupport{
	 
	/**
	 * Auto generated default serialVersionUID stub
	 * long
	 */
	private static final long serialVersionUID = 1L;

	//Apache log4j logger utility
	private static final Logger learnCourseLectureContentPlayerOptionsActionLogger = Logger.getLogger(LearnCourseLectureContentPlayerOptionsAction.class);
	
	@InjectParam
	LearnCourseLectureContentPlayerOptionService learnCourseLectureContentPlayerOptionService;
	
	@InjectParam
	LearnCourseLectureContentPlayerOptionsViewModel learnCourseLectureContentPlayerOptionsViewModel;

	@InjectParam
	Learningstatistic learningstatistic;
	
	@InjectParam
	Learningstatistic learningstatistic1;

	@InjectParam
	Courseenrollment courseenrollment;

	@InjectParam
	Courselecture courselecture;

	@InjectParam
	Coursesection coursesection;

	@InjectParam
	Orgperson orgperson;

	
	
	
	
	/**
	 * @param learnCourseLectureContentPlayerOptionsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/newLearningStatistics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewLearningStatistics( LearnCourseLectureContentPlayerOptionsViewModel learnCourseLectureContentPlayerOptionsViewModel){
		
		List<Learningstatistic> learningStatisticsList = null;
		 
		learningstatistic.setStarttime( learnCourseLectureContentPlayerOptionsViewModel.getStartTime());
		learningstatistic.setEndtime( learnCourseLectureContentPlayerOptionsViewModel.getEndTime() );
		learningstatistic.setTimespent(learnCourseLectureContentPlayerOptionsViewModel.getTimeSpent()); 
		learningstatistic.setCompletedtime(null);	
		
		learningstatistic.setMarkssecured(learnCourseLectureContentPlayerOptionsViewModel.getMarksSecured());
		learningstatistic.setExamstatus(learnCourseLectureContentPlayerOptionsViewModel.getExamStatus()); 
		learningstatistic.setLearningstatus(learnCourseLectureContentPlayerOptionsViewModel.getLearningStatus());
		
		coursesection.setCoursesectionid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseSectionId()));
		courselecture.setCourselectureid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseLectureId()));
		courseenrollment.setCourseenrollmentid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseenrollmentid()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		
		learningstatistic.setCoursesection(coursesection);
		learningstatistic.setCourselecture(courselecture);
		learningstatistic.setCourseenrollment(courseenrollment);
		learningstatistic.setOrgperson(orgperson);  
		
		int createSuccess =   (Integer) learnCourseLectureContentPlayerOptionService.save(learningstatistic);
		
		if(createSuccess >0){
			return Response.status(Status.OK).entity(learningStatisticsList).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	
	/**
	 * 
	 * @param learningStatisticsid
	 * @param learnCourseLectureContentPlayerOptionsViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response 
	 * @author balajichander_r
	 */  
	@POST
	@Path("/updateALearningStatistics")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLearningStatistics( LearnCourseLectureContentPlayerOptionsViewModel learnCourseLectureContentPlayerOptionsViewModel) {  
    	learnCourseLectureContentPlayerOptionsActionLogger.info("first updateLearningStatistics ********learningstatisticsid**");		
    	Learningstatistic learningstatistic2=new Learningstatistic();
	   	learningstatistic2.setLearningstatisticsid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getLearningstatisticsid()));
    	learningstatistic2.setStarttime(learnCourseLectureContentPlayerOptionsViewModel.getStartTime());
    	learningstatistic2.setEndtime(learnCourseLectureContentPlayerOptionsViewModel.getEndTime());
    	learningstatistic2.setTimespent(learnCourseLectureContentPlayerOptionsViewModel.getTimeSpent());    	
    	learningstatistic2.setCompletedtime(learnCourseLectureContentPlayerOptionsViewModel.getCompletedtime());
    	courseenrollment.setCourseenrollmentid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseenrollmentid()));
    	learningstatistic2.setCourseenrollment(courseenrollment); 
    	 courselecture.setCourselectureid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseLectureId()));
    	 learningstatistic2.setCourselecture(courselecture);		 
		 coursesection.setCoursesectionid(Integer.parseInt(learnCourseLectureContentPlayerOptionsViewModel.getCourseSectionId()));
		 learningstatistic2.setCoursesection(coursesection); 		 
		 learningstatistic2.setMarkssecured(learnCourseLectureContentPlayerOptionsViewModel.getMarksSecured());
		 learningstatistic2.setExamstatus(learnCourseLectureContentPlayerOptionsViewModel.getExamStatus());    	
		 learningstatistic2.setMeterialdownloadstatus(learnCourseLectureContentPlayerOptionsViewModel.getMaterialdownloadstatus()); 
		 learningstatistic2.setLearningstatus(learnCourseLectureContentPlayerOptionsViewModel.getLearningStatus()); 
		
		 orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		 learningstatistic2.setOrgperson(orgperson);
		 
		learningstatistic2 =  learnCourseLectureContentPlayerOptionService.update(learningstatistic2);  
		 
		 if (learningstatistic2 != null ) {
			 return Response.status(Status.OK).entity(learningstatistic2).build();
		 } 
		 return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
			
	}
	
	
	
	/**
	 * 
	 * @param learningStatisticsid
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response 
	 * @author balajichander_r
	 */
	@SuppressWarnings({ "unchecked" })
	@GET
	@Path("/getALearningStatistics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLearningStatistics(@PathParam("learningstatisticsid") String learningStatisticsid){
		
		List<Learningstatistic> learningStatisticsList = null; 
		
		learningStatisticsList = (List<Learningstatistic>) learnCourseLectureContentPlayerOptionService.getLearningStatisticsOf(Integer.parseInt(learningStatisticsid));
		
		if (learningStatisticsList != null & learningStatisticsList.size()>0) { 
			return Response.status(Status.OK).entity(learningStatisticsList).build();
		}   
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		
	}	


    
  
    /**
     * 
     * @param learnCourseLectureContentPlayerOptionsViewModel
     * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response 
     * @author balajichander_r
     */ 
	@POST
    @Path("/findAndGetLearnigStatisticsList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLearningStatisticsListByFindingLearningStatisticsId(LearnCourseLectureContentPlayerOptionsViewModel learnCourseLectureContentPlayerOptionsViewModel){
    	List<Learningstatistic> learningStatisticsList = null; 
    	learningStatisticsList = learnCourseLectureContentPlayerOptionService.getLearningStatisticId( getAttribute("orgpersonid") , learnCourseLectureContentPlayerOptionsViewModel.getCourseLectureId(), learnCourseLectureContentPlayerOptionsViewModel.getCourseSectionId(), learnCourseLectureContentPlayerOptionsViewModel.getCourseenrollmentid());
    	if (learningStatisticsList != null & learningStatisticsList.size()>0) { 
    		return Response.status(Status.OK).entity(learningStatisticsList).build();
		}    
		return Response.status(Status.OK).entity(learnCourseLectureContentPlayerOptionsViewModel).build();
		
    }

    
    /**
	 * 
	 * @param appDate
	 * @param timeFormat
	 * @return returnDateObj Date
	 * @author balajichander_r
	 */
    protected Date dbDateParser(String appDate, String timeFormat){
    	Date returnDateObj = null; 
    	DateFormat dbDateObj  =  new SimpleDateFormat(timeFormat);
    	try {
    		returnDateObj =  dbDateObj.parse(appDate); 
		} catch (Exception e) {
			learnCourseLectureContentPlayerOptionsActionLogger.error(e);
		} 
    	return returnDateObj;
    	
    	
    }
    
}
