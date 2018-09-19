package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Lecturecontent;
import com.igrandee.product.ecloud.service.course.LectureContentService;

public class LectureContentAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger LectureContentActionlogger = Logger.getLogger(LectureContentAction.class);
	
	@Autowired
	private Courselecture courselectureobj;
	
	@Autowired
	private Lecturecontent Lecturecontentobj;
	
	@Inject
	LectureContentService LectureContentServiceobj;
	
	private String courselectureid;
	private String contentid;
	private String contentpath;
	
	/**
	 * @author seenivasagan_p
	 * Method to remove content
	 */
	@Action(value="/removeContent")
	public void removeContent(){
		try	{
			String removedSuccess = LectureContentServiceobj.updateCourseContentStatus(contentid);
			if(removedSuccess.equalsIgnoreCase("OK")){
				checkobj().toJsonResponse("1");
			}else{
				checkobj().toJsonResponse("0");
			}
		}
		catch(Exception e){
			if(LectureContentActionlogger.isInfoEnabled()){
				LectureContentActionlogger.error("error in removeContent() in LectureContentAction",e);
			}
		}
	}	
	
	/**
	 * @author seenivasagan_p
	 * Method to get content for a particular lecture
	 */
	@Action(value = "/getContent")
	public void getContent(){
		try{
			List<?> contentList = LectureContentServiceobj.getLectureContents(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(contentList);
		}
		catch(Exception e){
			if(LectureContentActionlogger.isInfoEnabled()){
				LectureContentActionlogger.error("error in getContent() in LectureContentAction",e);
			}
		}
	}

	/*getters and setters*/
	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getContentpath() {
		return contentpath;
	}
	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}
}