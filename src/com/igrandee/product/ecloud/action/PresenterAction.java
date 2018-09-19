package com.igrandee.product.ecloud.action;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.service.presenter.PresenterServices;

public class PresenterAction extends MasterActionSupport {
	private static final long serialVersionUID = 1L;
	private static Logger PresenterActionlogger = Logger.getLogger(PresenterAction.class);
	
	@Inject
	PresenterServices PresenterServicesobj;
	
	private String coursecategoryid;
	private String courseid;
	
	@Action(value="/loadCategories")
	public void loadCategories(){
		try	{
			List<?> categoriesList=PresenterServicesobj.getCategories("A");
			checkobj().toJsonResponse(categoriesList);
		}
		catch(Exception e){
			if(PresenterActionlogger.isInfoEnabled())	{
				PresenterActionlogger.error("error in loadCategories() in PresenterAction"+ e);
			}
		}  
	}
	
	@Action(value="/loadCategoryWiseCourses")
	public void loadCategoryWiseCourses(){
		try	{
			List<?> coursesList=PresenterServicesobj.getCategoryWiseCourses(Integer.parseInt(coursecategoryid));
			checkobj().toJsonResponse(coursesList);
		}
		catch(Exception e){
			if(PresenterActionlogger.isInfoEnabled())	{
				PresenterActionlogger.error("error in loadCategoryCourses() in PresenterAction"+ e);
			}
		}  
	}
	
	@Action(value="/loadCoursesSections")
	public void loadCoursesSections(){
		try	{
			List<?> coursesList=PresenterServicesobj.getCoursesSections(Integer.parseInt(courseid));
			checkobj().toJsonResponse(coursesList);
		}
		catch(Exception e){
			if(PresenterActionlogger.isInfoEnabled())	{
				PresenterActionlogger.error("error in loadCoursesSections() in PresenterAction"+ e);
			}
		}  
	}
	
	
	/*getters and setters*/

	public String getCoursecategoryid() {
		return coursecategoryid;
	}
	public void setCoursecategoryid(String coursecategoryid) {
		this.coursecategoryid = coursecategoryid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
}