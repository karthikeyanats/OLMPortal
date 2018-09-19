package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.model.Faqanswer;
import com.igrandee.product.ecloud.model.Metainfo;
import com.igrandee.product.ecloud.service.course.CourseLectureService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.FaqanswerService;
import com.igrandee.product.ecloud.service.course.MetainfoService;

public class CourseLectureAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger CourseLectureActionlogger = Logger.getLogger(CourseLectureAction.class);
	
	@Inject
	CourseLectureService CourseLectureServiceobj;
	
	@Inject
	MetainfoService MetainfoServiceobj;
	
	@Inject
	CourseListService courselistservice;
	
	@Inject
	FaqanswerService faqanswerService;
	
	@Autowired
	private Coursesection coursesectionobj;
	
	@Autowired
	private Courselecture courselectureobj;
	
	@Autowired
	private Metainfo metainfo;
	
	@Autowired
	private Faqanswer Faqanswerobj;
	
	private String courseid;
	private String coursesectionid;
	private String courselectureid;
	private String courselecturetitle;
	private String newlecture;
	private String keywords;
	private String weblinks;
	private String summary;
	private String faqanswersid;
	private String descriptiontype;
	
	private String lectid;

	/**
	 * @author seenivasagan_p
	 * Method to edit the existing lecture
	 */
	@Action(value="/editLecture")
	public void editLecture(){
		try{
			coursesectionobj.setCoursesectionid(Integer.parseInt(coursesectionid));
			courselectureobj.setCourselectureid(Integer.parseInt(courselectureid));
			courselectureobj.setCourselecturestatus("A");
			courselectureobj.setCourselecturetitle(java.net.URLDecoder.decode(courselecturetitle));
			courselectureobj.setCoursesection(coursesectionobj);
			CourseLectureServiceobj.update(courselectureobj);
			checkobj().toJsonResponse("lectureupdated");
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in editLecture() in CourseLectureAction",e);
			}
		}
	}
	
	/**
	 * @author mahalingam_a
	 * Method to edit answer status for a particular question
	 * @param FAQid,faqanswer
	 */
	@Action(value="/editCorrectAnswer")
	public void editCorrectAnswer(){
		try{
			String[] faqansid=faqanswersid.split(",");
			for(String fid:faqansid){
				faqanswerService.updateCorrectAnswer(Integer.parseInt(fid));
			}
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in editCorrectQAnswer() in AnswerAction",e);
			}
		}
	}
	/**
	 * @author mahalingam_a
	 * Method to delete answer for a particular question
	 * @param FAQid,faqanswer
	 */
	@Action(value="/deleteAnswer")
	public void deleteAnswer(){
		try{
			String[] faqansid=faqanswersid.split(",");
			for(String fid:faqansid)
			{
				faqanswerService.deleteAnswer(Integer.parseInt(fid));
			}
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in deleteAnswer() in AnswerAction",e);
			}
		}
	}
	/**
	 * @author mahalingam_a
	 * Method to Trash answers for a particular question
	 * @param FAQid,faqanswer
	 * @since 12-11-2014
	 */
	@Action(value="/trashAnswer")
	public void trashAnswer(){
		try{
			String[] faqansid=faqanswersid.split(",");
			for(String fid:faqansid){
				faqanswerService.trashAnswer(Integer.parseInt(fid));
			}
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in trashAnswer() in AnswerAction",e);
			}
		}
	}
	
	@Action(value="/loadmetainfo")
 	public void loadmetainfo(){
		try	{
			List<?> metainfoList=courselistservice.getMetaInfo(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(metainfoList);
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in loadmetainfo() is",e);
			}
		}  
	}
	
	@Action(value="/loadmetainfoitems")
 	public void loadmetainfoitems(){
		try	{
			List<?> metainfoList=courselistservice.getLectureMetaDescription(Integer.parseInt(courselectureid),descriptiontype);
			checkobj().toJsonResponse(metainfoList);
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in loadmetainfo() is",e);
			}
		}  
	}

	@Action(value="/loadLectureApproveStatus")
 	public void loadLectureApproveStatus(){
		try	{
			List<?> metainfoList=courselistservice.getLectureApprovalStatus(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(metainfoList);
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in loadmetainfo() is",e);
			}
			e.printStackTrace();
		}  
	}
	
	@Action(value="/loadCourseApproveStatus")
 	public void loadCourseApproveStatus(){
		try	{
			List<?> metainfoList=courselistservice.getCourseApprovalStatus(Integer.parseInt(courseid));
			checkobj().toJsonResponse(metainfoList);
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in loadmetainfo() is",e);
			}
			e.printStackTrace();
		}  
	}

	
	@Action(value="/loadlecturequiz")
 	public void loadlecturequiz(){
		try	{
			List<?> metainfoList=courselistservice.getLectureQuiz(Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(metainfoList);
		}
		catch(Exception e){
			if(CourseLectureActionlogger.isInfoEnabled()){
				CourseLectureActionlogger.error("error in loadmetainfo() is",e);
			}
		}  
	}
	
	/*getters and setters*/
	public String getCoursesectionid() {
		return coursesectionid;
	}
	public void setCoursesectionid(String coursesectionid) {
		this.coursesectionid = coursesectionid;
	}
	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}
	public String getCourselecturetitle() {
		return courselecturetitle;
	}
	public void setCourselecturetitle(String courselecturetitle) {
		this.courselecturetitle = courselecturetitle;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getWeblinks() {
		return weblinks;
	}
	public void setWeblinks(String weblinks) {
		this.weblinks = weblinks;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getNewlecture() {
		return newlecture;
	}
	public void setNewlecture(String newlecture) {
		this.newlecture = newlecture;
	}


	public String getLectid() {
		return lectid;
	}


	public void setLectid(String lectid) {
		this.lectid = lectid;
	}
	
	
	public String getFaqanswersid() {
		return faqanswersid;
	}
	public void setFaqanswersid(String faqanswersid) {
		this.faqanswersid = faqanswersid;
	}

	public String getDescriptiontype() {
		return descriptiontype;
	}

	public void setDescriptiontype(String descriptiontype) {
		this.descriptiontype = descriptiontype;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	
 }