package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.LearningstatisticsService;

public class CoursePreviewAction extends MasterActionSupport{

	private static Logger CoursePreviewActionlogger = Logger.getLogger(CoursePreviewAction.class);
	@Inject
	LearningstatisticsService learningstatisticsServiceobj;
	private static final long serialVersionUID = 1L;
	
	private String courseenrollmentid;
	/**
	 * @author seenivasagan_p
	 * Method to get All valid categories
	 */
	@Action(value="/getCoursePreviewData")
 	public void getCoursePreviewData(){
		try	{
			List<?> validCategoriesList=learningstatisticsServiceobj.loadCoursePreviewData(courseenrollmentid);	
			checkobj().toJsonResponse(validCategoriesList);
		}
		catch(Exception e){
			if(CoursePreviewActionlogger.isInfoEnabled())	{
				CoursePreviewActionlogger.error("error in getCoursePreviewData() in CoursePreviewAction ",e);
			}
		}  
	}
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
}
