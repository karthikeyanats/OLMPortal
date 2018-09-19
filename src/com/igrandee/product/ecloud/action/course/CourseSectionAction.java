package com.igrandee.product.ecloud.action.course;

import java.net.URLDecoder;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.CourseSectionService;

public class CourseSectionAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	
	private static Logger CourseSectionActionlogger = Logger.getLogger(CourseSectionAction.class);

	@Autowired
	private Course courseobj;
	
	@Autowired
	private Coursesection coursesectionobj;
	
	@Inject
	CourseSectionService courseSectionService;
	
	@Inject
	CourseListService courseListService;	
	
	
	private String courseid;
	private String coursesectionid;
	private String coursesectiontitle;
	private String coursesectionstatus;
	private String courselectureid;
	private String courselecturestatus;
	/**
	 * @author seenivasagan_p
	 * Method to add new Section
	 */
	@Action(value="/addNewSection")
	public void addNewSection(){
		try	{
			courseobj.setCourseid(Integer.parseInt(courseid));
			coursesectionobj.setCoursesectiontitle(URLDecoder.decode(coursesectiontitle));
			coursesectionobj.setCoursesectionstatus("A");
			coursesectionobj.setCourse(courseobj);
			String newsection=courseSectionService.save(coursesectionobj).toString();
			checkobj().toJsonResponse(newsection);
		}
		catch(Exception e){
			if(CourseSectionActionlogger.isInfoEnabled()){
				CourseSectionActionlogger.error("error in addNewSection() in CourseSectionAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to edit the existing sections
	 */
	@Action(value="/editSection")
	public void editSection(){
		try	{
			courseobj.setCourseid(Integer.parseInt(courseid));
			coursesectionobj.setCoursesectionid(Integer.parseInt(coursesectionid));
			coursesectionobj.setCoursesectiontitle(java.net.URLDecoder.decode(coursesectiontitle));
			coursesectionobj.setCoursesectionstatus("A");
			coursesectionobj.setCourse(courseobj);
			courseSectionService.update(coursesectionobj);
			checkobj().toJsonResponse("sectionupdated");
		}
		catch(Exception e){
			if(CourseSectionActionlogger.isInfoEnabled()){
				CourseSectionActionlogger.error("error in editSection() in CourseSectionAction",e);
			}
		}
	}
	
	/**
	 * @author selvapriya_m
	 * Method to delete  the course sections
	 */
	@Action(value="/checkcoursesection")
	public void checkcoursesection(){
		try	{
			courseListService.checksectionstatus(Integer.parseInt(coursesectionid),coursesectionstatus);
			checkobj().toJsonString("success");						
		 }catch(Exception e) {
			if(CourseSectionActionlogger.isInfoEnabled()){
				CourseSectionActionlogger.error("error in checkcoursesection() in CourseSectionAction",e);
			} 
		}		
	}
	
	/**
	 * @author williambenjamin_g
	 * Method to delete  the course lecture
	 */
	@Action(value="/checkcourselecture")
	public void checkcourselecture(){
		try	{
			courseListService.checklecturestatus(Integer.parseInt(courselectureid),courselecturestatus);
			checkobj().toJsonString("success");
		}
		catch(Exception e) {
			if(CourseSectionActionlogger.isInfoEnabled())	{
				CourseSectionActionlogger.error("error in checkcourselecture() in CourseSectionAction",e);
			} 
		}		
	}

	/*Getters and Setters*/
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCoursesectionid() {
		return coursesectionid;
	}
	public void setCoursesectionid(String coursesectionid) {
		this.coursesectionid = coursesectionid;
	}
	public String getCoursesectiontitle() {
		return coursesectiontitle;
	}
	public void setCoursesectiontitle(String coursesectiontitle) {
		this.coursesectiontitle = coursesectiontitle;
	}

	public String getCoursesectionstatus() {
		return coursesectionstatus;
	}

	public void setCoursesectionstatus(String coursesectionstatus) {
		this.coursesectionstatus = coursesectionstatus;
	}

	public String getCourselectureid() {
		return courselectureid;
	}

	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}

	public String getCourselecturestatus() {
		return courselecturestatus;
	}

	public void setCourselecturestatus(String courselecturestatus) {
		this.courselecturestatus = courselecturestatus;
	}	
}