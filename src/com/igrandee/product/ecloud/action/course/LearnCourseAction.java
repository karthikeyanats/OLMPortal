package com.igrandee.product.ecloud.action.course;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Coursecertificate;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.model.Faq;
import com.igrandee.product.ecloud.model.Learningstatistic;
import com.igrandee.product.ecloud.model.Note;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.CourseLectureViewService;
import com.igrandee.product.ecloud.service.course.CoursecertificateService;
import com.igrandee.product.ecloud.service.course.FAQService;
import com.igrandee.product.ecloud.service.course.LearningstatisticsService;
import com.igrandee.product.ecloud.service.course.NotesService;

public class LearnCourseAction extends MasterActionSupport{

	private static Logger LearnCourseActionlogger = Logger.getLogger(LearnCourseAction.class);

	private static final long serialVersionUID = 1L;

	@Inject
	FAQService fAQService;

	@Inject
	NotesService notesService;

	@Inject
	LearningstatisticsService learningstatisticsService;

	@Inject
	CourseLectureViewService courseLectureViewService;

	@Inject
	CoursecertificateService CoursecertificateServiceobj;

	@Autowired
	private Courselecture courselecture;

	@Autowired
	private Coursecertificate Coursecertificateobj;

	@Autowired
	private Orgperson askedby;

	@Autowired
	private Faq faq;

	@Autowired
	private Note note;

	@Autowired
	private Orgperson orgperson;

	@Autowired
	private Courseenrollment courseenrollment;

	@Autowired
	private Learningstatistic learningstatistic;

	@Autowired
	private Coursesection coursesection;
	

	private String courselectureid;
	private String coursesectionid;
	private String FAQquestion;
	private String notesdescription;
	private String courseenrollmentid;
	private String learningstatus;
	private String courseprogress; 
	private String courseenrollmentstatus;	
	private String coursecertificateid;
	private String starttime;
	private String endtime;
	private String timespent;

	/**
	 * @author seenivasagan_p
	 * Method Used to save the notes
	 * @param void
	 * @return response
	 */

	@Action(value="/setCompletedStatusUnit")
	public void setCompletedStatusUnit(){
		try	{
			List<?> learningstatisticslist=learningstatisticsService.checkLearningStats(Integer.parseInt(courseenrollmentid),Integer.parseInt(courselectureid));
			String learningstatisticsid=null;	
			
			for(int i=0;i<learningstatisticslist.size();i++){
				Map FoldertitleMap=(Map) learningstatisticslist.get(i);
				learningstatisticsid=""+FoldertitleMap.get("learningstatisticsid");					
			}
			
			

			
			if(learningstatisticsid!=null){
				
				learningstatisticsService.markCompleted(Integer.parseInt(learningstatisticsid));
				checkobj().toJsonResponse("success");
			}
			else{
				
				
				courseenrollment.setCourseenrollmentid(Integer.parseInt(courseenrollmentid));
				courselecture.setCourselectureid(Integer.parseInt(courselectureid));
				coursesection.setCoursesectionid(Integer.parseInt(coursesectionid));
				
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				learningstatistic.setCourseenrollment(courseenrollment);
				learningstatistic.setCourselecture(courselecture);
				learningstatistic.setOrgperson(orgperson);
				learningstatistic.setStarttime(starttime);	
				learningstatistic.setEndtime(endtime);
				learningstatistic.setTimespent(timespent);	
				learningstatistic.setCoursesection(coursesection);					
				java.util.Date date = new java.util.Date();
				
				learningstatistic.setCompletedtime(new Timestamp(date.getTime()));
				learningstatistic.setLearningstatus(learningstatus);
				String UnitStatus=learningstatisticsService.save(learningstatistic).toString();
				checkobj().toJsonResponse(UnitStatus);				
			}			
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in setCompletedStatusUnit() in LearnCourseAction",e);
			}
		}
	}

	@Action(value="/getCompletedLearningStatuses")
	public void getCompletedLearningStatuses(){
		try	{	
			List<?> learningStatsList=learningstatisticsService.learningStatsEnrollList(Integer.parseInt(courseenrollmentid));
			checkobj().toJsonResponse(learningStatsList);
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in getCompletedLearningStatuses() in LearnCourseAction",e);
			}
		}
	}

	@Action(value="/getCompletedCourseReport")
	public void getCompletedCourseReport(){
		try	{	
			List<?> completedCourseReport=learningstatisticsService.loadCompletionReportData(courseenrollmentid,getAttribute("orgpersonid"));
			checkobj().toJsonResponse(completedCourseReport);
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in getCompletedCourseReport() in LearnCourseAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the learning statistic
	 * @param courseenrollmentid
	 * @param courselectureid
	 * @return learningstatisticsid
	 */
	@Action(value="/learningstats")
	public void learningstats(){
		try	{	
			List<?> learningStatsList=learningstatisticsService.loadLearningStats(Integer.parseInt(courseenrollmentid), Integer.parseInt(courselectureid));
			checkobj().toJsonResponse(learningStatsList);
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in learningstats() in LearnCourseAction",e);
			}
		}
	}

	/**
	 *@author seenivasagan_p
	 *Method to get the learning status list 
	 *@param courseenrollmentid
	 */
	@Action(value="/learningStatusList")
	public void learningStatusList(){
		try{
			List<?> statusList=learningstatisticsService.loadCompletedLecturesList(Integer.parseInt(courseenrollmentid));
			checkobj().toJsonResponse(statusList);
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in getLearningStatusLecture() in LearnCourseAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to load all lectures for a course by passing course enrollment id
	 */
	@Action(value="/allLearningLectureList")
	public void allLearningLectureList(){
		try{
			List<?> wholeLectureList=learningstatisticsService.loadAllLecturesList(Integer.parseInt(courseenrollmentid));
			checkobj().toJsonResponse(wholeLectureList);
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in allLearningLectureList() in LearnCourseAction",e);
			}
		}
	}


	/**
	 * @author seenivasagan_p
	 * Method to update course progress
	 */
	@Action(value="/updateCourseProgress")
	public void updateCourseProgress(){
		try{
			learningstatisticsService.courseProgressUpdate(Integer.parseInt(courseenrollmentid),courseprogress,courseenrollmentstatus);
			if(courseenrollmentstatus.equals("C")){
				learningstatisticsService.addCompletedDate(Integer.parseInt(courseenrollmentid));
				saveCertificate(courseenrollmentid);
			}
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in allLearningLectureList() in LearnCourseAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to save a certificate request
	 * @param courseenrollmentid
	 */
	private void saveCertificate(String courseenrollmentid){
		try{
			List<?> certstatus=CoursecertificateServiceobj.checkcertificateStatus(Integer.parseInt(courseenrollmentid));
			if(certstatus.size()==0){
				Coursecertificateobj.setCoursecertificatestatus("P");
				Coursecertificateobj.setCertificatedescription("certificate");
				courseenrollment.setCourseenrollmentid(Integer.parseInt(courseenrollmentid));
				Coursecertificateobj.setCourseenrollment(courseenrollment);
				CoursecertificateServiceobj.save(Coursecertificateobj);	
			}
			else{

			}
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in saveCertificate() in LearnCourseAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to approve Course Certificate request
	 */
	@Action(value="/approveCertificate")
	public void approveCertificateStatus(){
		try{
			Integer updatedStatus=CoursecertificateServiceobj.updateCourseCertificateStatus(Integer.parseInt(coursecertificateid));
			checkobj().toJsonString(updatedStatus.toString());
		}
		catch(Exception e){
			if(LearnCourseActionlogger.isInfoEnabled())	{
				LearnCourseActionlogger.error("error in approveCertificateStatus() in LearnCourseAction",e);
			}
		}
	}

	public Orgperson getAskedby() {
		return askedby;
	}
	public void setAskedby(Orgperson askedby) {
		this.askedby = askedby;
	}
	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}
	public String getFAQquestion() {
		return FAQquestion;
	}
	public void setFAQquestion(String fAQquestion) {
		FAQquestion = fAQquestion;
	}
	public String getNotesdescription() {
		return notesdescription;
	}
	public void setNotesdescription(String notesdescription) {
		this.notesdescription = notesdescription;
	}
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
	public String getLearningstatus() {
		return learningstatus;
	}
	public void setLearningstatus(String learningstatus) {
		this.learningstatus = learningstatus;
	}
	public String getCourseprogress() {
		return courseprogress;
	}
	public void setCourseprogress(String courseprogress) {
		this.courseprogress = courseprogress;
	}
	public String getCourseenrollmentstatus() {
		return courseenrollmentstatus;
	}
	public void setCourseenrollmentstatus(String courseenrollmentstatus) {
		this.courseenrollmentstatus = courseenrollmentstatus;
	}

	public String getCoursecertificateid() {
		return coursecertificateid;
	}

	public void setCoursecertificateid(String coursecertificateid) {
		this.coursecertificateid = coursecertificateid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTimespent() {
		return timespent;
	}

	public void setTimespent(String timespent) {
		this.timespent = timespent;
	}

	public String getCoursesectionid() {
		return coursesectionid;
	}

	public void setCoursesectionid(String coursesectionid) {
		this.coursesectionid = coursesectionid;
	}
}