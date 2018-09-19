package com.igrandee.product.ecloud.action.course;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Courserating;
import com.igrandee.product.ecloud.model.LectureReview;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Reviewtype;
import com.igrandee.product.ecloud.service.course.CourseRatingService;
import com.igrandee.product.ecloud.service.course.ReviewLectureService;

public class CourseRatingAction extends MasterActionSupport{
	private static final long serialVersionUID = -1798768682123024218L;
	private static Logger CourseRatingActionLogger = Logger.getLogger(CourseRatingAction.class);
	private String courseid;
	private String courserating;
	private String courseratingdescription;
	private String rank;
	private String lectureid;
	private String reviewtype;
	
	@Inject
	CourseRatingService CourseRatingServiceobj;
	
	
	@Autowired
	Courserating Courseratingobj;
	
	@Autowired
	Course Courseobj;
	
	@Autowired
	Orgperson Orgpersonobj;
	
	@Inject
	ReviewLectureService lecturereviewService;
	
	@Autowired
	LectureReview lectureReview;
	
	/**
	 * @author seenivasagan_p
	 * Method to get Course Reviews
	 */
	@Action("/getCourseReviews")
	public void getCourseReviews(){
		try{
			List<?> reviewsList=CourseRatingServiceobj.ratingList(Integer.parseInt(courseid));
			checkobj().toJsonResponse(reviewsList);
		}
		catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in getCourseReviews() in CourseRatingAction",e);
			}
		}

	}
	
	
	/**
	 * @author seenivasagan_p
	 * Method to get Course Reviews
	 */
	@Action("/getMyCourseReviews")
	public void getMyCourseReviews(){
		try{
			List<?> reviewsList=CourseRatingServiceobj.myratingList(Integer.parseInt(courseid),Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(reviewsList);
		}
		catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in getCourseReviews() in CourseRatingAction",e);
			}
		}

	}
	
	/**
	 * @author venkatraman_v
	 * 
	 */
	@Action("/getAlllectureReviews")
	public void getlectureReviews(){
		try{
			List<?> reviewsList=lecturereviewService.lectureratingList(Integer.parseInt(lectureid));
			checkobj().toJsonResponse(reviewsList);
		}
		catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in getlectureReviews() in CourseRatingAction",e);
			}
		}

	}
	
	@Action("/lectureReviewList")
	public void getlectureReviewList(){
		try{
			List<?> loadReviewsList=lecturereviewService.loadLectureReviewsList(Integer.parseInt(lectureid));
			if(loadReviewsList.size()!=0){
				checkobj().toJsonResponse(loadReviewsList);	
			}else{
				checkobj().toJsonString("empty");
			}			
		}
		catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in getlectureReviews() in CourseRatingAction",e);
			}
			checkobj().toJsonString("error");
		}

	}
	
	@Action("/getMylectureReviews") 
		public void getlecturereview(){
		
			try{
		
				List<?> lectreviewsList=lecturereviewService.mylectratingList(Integer.parseInt(lectureid),Integer.parseInt(getAttribute("orgpersonid")));
				checkobj().toJsonResponse(lectreviewsList);
			}catch(Exception e){
				if(CourseRatingActionLogger.isInfoEnabled()){
					CourseRatingActionLogger.error("error in getlecturereview() in CourseRatingAction",e);
				}
			}
	}
	@Action("/getcoursetype")
	public void getCoursetype(){
		try{
			List<?> Coursetype=CourseRatingServiceobj.getcoursetype();
			checkobj().toJsonResponse(Coursetype);
		}catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in getCoursetype() in CourseRatingAction",e);
			}
		}
	}
	
	@Action("/savelectureReview") 
	public void saveLectureReview()
	{
		try{
			String orgid=getAttribute("orgpersonid");
			Date d=new Date();
			
			
			String ranks[]=rank.split(",");
			
			String reviewtypes[]=reviewtype.split(",");
			
			
			for(int i=0;i<ranks.length;i++){
				
				
				lectureReview.setRank(ranks[i]);
				Courselecture courselecture=new Courselecture();
				courselecture.setCourselectureid(Integer.parseInt(lectureid));
				lectureReview.setCourselecture(courselecture);
				lectureReview.setLecturereviewstatus('A');			
				Reviewtype reviewtype=new Reviewtype();
				reviewtype.setId(Integer.parseInt((reviewtypes[i])));
				lectureReview.setReviewtype(reviewtype);
				Orgperson orgperson=new Orgperson();
				orgperson.setOrgpersonid(Integer.parseInt(orgid));
				lectureReview.setOrgperson(orgperson);
				lectureReview.setDateofcreation(new Timestamp(d.getTime()));
				String coursere=lecturereviewService.save(lectureReview).toString();
				checkobj().toJsonResponse(coursere);

			}
			
		}catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in saveLectureReview() in CourseRatingAction",e);
			}
		}
	}
	
	@Action("/saveCourseReviews")
	public void saveCourseReviews(){
		try{
			Date d=new Date();
			Courseobj.setCourseid(Integer.parseInt(courseid));
			Courseratingobj.setCourse(Courseobj);			
			Orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			Courseratingobj.setOrgperson(Orgpersonobj);
			Courseratingobj.setCourserating(courserating);
			Courseratingobj.setCourseratingdescription(java.net.URLDecoder.decode(courseratingdescription));
			Courseratingobj.setCourseratingstatus("A");
			Courseratingobj.setRatingdate(new Timestamp(d.getTime()));
			String reviewsList=CourseRatingServiceobj.save(Courseratingobj).toString();
			checkobj().toJsonResponse(reviewsList);
		}
		catch(Exception e){
			if(CourseRatingActionLogger.isInfoEnabled()){
				CourseRatingActionLogger.error("error in saveCourseReviews() in CourseRatingAction",e);
			}
		}

	}
	
	

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCourserating() {
		return courserating;
	}

	public void setCourserating(String courserating) {
		this.courserating = courserating;
	}

	public String getCourseratingdescription() {
		return courseratingdescription;
	}

	public void setCourseratingdescription(String courseratingdescription) {
		this.courseratingdescription = courseratingdescription;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getLectureid() {
		return lectureid;
	}
	public void setLectureid(String lectureid) {
		this.lectureid = lectureid;
	}
	public String getReviewtype() {
		return reviewtype;
	}
	public void setReviewtype(String reviewtype) {
		this.reviewtype = reviewtype;
	}


}
