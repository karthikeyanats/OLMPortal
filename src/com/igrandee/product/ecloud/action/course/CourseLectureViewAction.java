package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.CourseLectureViewService;

public class CourseLectureViewAction extends MasterActionSupport{
	
	private static final long serialVersionUID = 1L;

	private static Logger CourseLectureViewActionlogger = Logger.getLogger(CourseLectureViewAction.class);

	@Inject
	private CourseLectureViewService courseLectureViewService;
	
	private String courselectureid;	
		
	/**
	 * @author seenivasagan_p
	 * Method to get All valid categories
	 * 
	 */
	@Action(value="/loadLectureContent")
 	public void loadLectureContent(){
		try{
			List<?> lectureContentList=courseLectureViewService.loadLectureContent(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(lectureContentList);
		}
		catch(Exception e){
			if(CourseLectureViewActionlogger.isInfoEnabled()){
				CourseLectureViewActionlogger.error("error in loadLectureContent() in CourseLectureViewAction",e);
			}
		}  
	}
	
	
	
	

	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}
}
