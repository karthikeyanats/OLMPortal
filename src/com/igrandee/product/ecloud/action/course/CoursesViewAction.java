package com.igrandee.product.ecloud.action.course;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.CourseEnrollmentService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.CourseService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;


/**
 * @author seenivasagan_p
 * Class for Courses List functionality
 *
 */
public class CoursesViewAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	private static Logger CoursesViewActionlogger = Logger.getLogger(CoursesViewAction.class);

	@Inject
	private CourseListService courseListService;

	@Inject
	private CourseService courseService;

	@Inject
	private CourseEnrollmentService courseEnrollmentService;

	@Inject
	OrganizationViewService organizationViewService;

	private String coursecategoryid;
	private String coursecategorystatus;
	private String personid;
	private String courseid;
	private String coursestatus;
	private String publishStatus;
	private String enrollmentstatus;
	private String courseenrollmentid;
	private String organizationid;
	private String keyword;
	private String masteradminid;


	/**
	 * @author seenivasagan_p
	 * Method to get All valid courses for a particular category
	 * 
	 */
	@Action(value="/loadCategoryCourses")
	public void loadCategoryCourses() {		
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> validCategoryCoursesList=courseListService.getAllValidCategoryWiseCourses(Integer.parseInt(coursecategoryid),getAttribute("orgpersonid"),organizationid);
			checkobj().toJsonResponse(validCategoryCoursesList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCategoryCourses() in CoursesViewAction",e);
			}
		} 
	} 

	/**
	 * @author seenivasagan_p
	 * Method to get All valid courses for a particular category
	 * 
	 */
	@Action(value="/loadGuestCategoryCourses")
	public void loadGuestCategoryCourses() {		
		try{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}

			List<?> validCategoryCoursesList=courseListService.getAllValidCategoryWiseCourses(Integer.parseInt(coursecategoryid),"null",organizationid);
			checkobj().toJsonResponse(validCategoryCoursesList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCategoryCourses() in CoursesViewAcltion",e);
			}
		} 
	}

	/**
	 * @author balajichander_r
	 * Method to get all valid category wise course details of a particular subscriber 
	 */
	@Action(value="/loadUserCategoryCourses")
	public void loadUserCategoryCourses(){
		try {   
			checkobj().toJsonResponse(courseListService.getAllCategoryPersonCourses( ""+getAttribute("orgpersonid"),  coursecategoryid , ""+getAttribute("organizationid") ));

		} catch (Exception e) { 
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadUserCategoryCourses() in CoursesViewAction",e);
			}
		}
	}


	/**
	 * @author seenivasagan_p
	 * Method to get All non valid courses for a passing course status
	 * 
	 */
	@Action(value="/loadOtherCourses")
	public void loadOtherCourses(){		
		try	{

			List<?> otherCoursesList=courseListService.getOtherCourses(coursestatus,Integer.parseInt(getAttribute("orgpersonid")),Long.parseLong(getAttribute("organizationid")));
			checkobj().toJsonResponse(otherCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadOtherCourses() in CoursesViewAction",e);
			}	
		} 
	}

	/**
	 * @author seenivasagan_p
	 * Method to get All published courses for a passing course status
	 * 
	 */
	@Action(value="/loadAllPublishedCourses")
	public void loadAllPublishedCourses(){		
		try	{

			List<?> otherCoursesList=courseListService.getAllPublishedCourses(coursestatus,Long.parseLong(getAttribute("organizationid")),getAttribute("orgpersonid"));
			checkobj().toJsonResponse(otherCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadAllPublishedCourses() in CoursesViewAction",e);
			}	
		} 
	}


	@Action(value="/loadPublishCourses")
	public void loadPublishCourses(){		
		try	{

			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}
			List<?> publishCoursesList=courseListService.getPublishCourses(coursestatus,getAttribute("orgpersonid"),organizationid);
			checkobj().toJsonResponse(publishCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadPublishCourses() in CoursesViewAction",e);
			}	
		} 
	}





	/**
	 * @author seenivasagan_p
	 * Method to get individual Courses
	 * 
	 */
	@Action(value="/loadEnrolledCourses")
	public void loadEnrolledCourses()	{		
		try	{
			List<?> enrolledCoursesList=courseListService.getIndividualCourses(Integer.parseInt(getAttribute("orgpersonid")),"null");
			checkobj().toJsonResponse(enrolledCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadEnrolledCourses() in CoursesViewAction",e);
			}	
		} 
	}

	@Action(value="/loadEnrollCourses")
	public void loadEnrollCourses()	{		
		try	{
			List<?> enrolledCoursesList=courseListService.getIndividualCourseList(Integer.parseInt(getAttribute("orgpersonid")),"null");
			checkobj().toJsonResponse(enrolledCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadEnrolledCourses() in CoursesViewAction",e);
			}	
		} 
	}


	/**
	 * @author seenivasagan_p
	 * Method to get individual's Courses
	 * 
	 */
	@Action(value="/loadIndividualsCourses")
	public void loadIndividualsCourses(){		
		try	{
			List<?> enrolledCoursesList=courseListService.getIndividualCourseswithTimeSpent(Integer.parseInt(personid),getAttribute("orgpersonid"));
			checkobj().toJsonResponse(enrolledCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled()){
				CoursesViewActionlogger.error("error in loadIndividualsCourses() in CoursesViewAction",e);
			}	
		} 
	}

	/**
	 * @author sridhar_m 
	 * Method to using Individual Course with out time spend
	 * 
	 */
	@Action(value="/loadIndividualCoursewithoutTimespend")
	public void loadIndividualCoursewithoutTime(){
		try {
			List<?> enrolledcoursesList=courseListService.getIndividualCourseswithoutTime(Integer.parseInt(personid),getAttribute("orgpersonid"));
			checkobj().toJsonResponse(enrolledcoursesList);
		} catch (Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled()){
				CoursesViewActionlogger.error("error in loadIndividualCoursewithoutTime() in courseViewAction",e);
			}
		}
	}

	/**
	 * @author mahalingam_a
	 * Method to get User Earning Courses
	 * 
	 */
	@Action(value="/loadMyEarningCourses")
	public void loadMyEarningCourses(){		
		try	{

			List<?> enrolledCoursesList=courseListService.getMyEarningCourses(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(enrolledCoursesList);
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled()){
				CoursesViewActionlogger.error("error in loadIndividualsCourses() in CoursesViewAction",e);
			}	
		} 
	}
	/**
	 * @author seenivasagan_p
	 * Method to get All non valid courses for a passing course status
	 * 
	 */
	@Action(value="/loadCourseContent")
	public void loadCourseContent(){		
		try	{
			List<?> courseContentList=courseListService.getCoursePreviewData(Integer.parseInt(courseid));
			List encodedNotesList=new ArrayList();
			Iterator<?> iterator=courseContentList.iterator();
			while(iterator.hasNext()){
				Map<String, String> map=(Map<String, String>) iterator.next();
				if(map.get("coursesectiontitle")!=null){
					if(map.get("courselecturetitle")!=null){
						map.put("coursesectiontitle", URLEncoder.encode(map.get("coursesectiontitle"),"UTF-8"));
						map.put("courselecturetitle", URLEncoder.encode(map.get("courselecturetitle"),"UTF-8"));
					}
					else{
						map.put("coursesectiontitle", URLEncoder.encode(map.get("coursesectiontitle"),"UTF-8"));	
					}
				}
				encodedNotesList.add(map);
			}
			checkobj().toJsonResponse(encodedNotesList);
			/*checkobj().toJsonResponse(courseContentList);*/
		}
		catch(Exception e) {
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCourseContent() in CoursesViewAction",e);
			} 
		}
	}








	/**
	 * @author seenivasagan_p
	 * Method to publish the course
	 * 
	 */
	@Action(value="/publishCourse")
	public void publishCourse(){
		try
		{
			String publishStatus=courseListService.publishCourse(Integer.parseInt(courseid)).toString();
			checkobj().toJsonResponse(publishStatus);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in publishCourse() in CoursesViewAction ",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to get Learning Details of the course
	 */
	@Action(value="/loadLearningDetails")
	public void loadLearningDetails(){
		try	{
			List<?> learningDetailsList=courseListService.getIndividualCourseForUser(Integer.parseInt(courseenrollmentid)); 
			checkobj().toJsonResponse(learningDetailsList);
		}
		catch(Exception e)	{
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadLearningDetails() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author seenivasagan_p
	 * Method to get course title and price for particular course
	 */
	@Action(value = "/loadCourseDetailsforPayment")
	public void loadCourseDetailsforPayment() {
		try {
			List<?> courseDetailsList=courseListService.getCourseDetailsforPayment(Integer.parseInt(courseid));
			checkobj().toJsonResponse(courseDetailsList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCourseDetailsforPayment() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author williambenjamin_g
	 * Method to get course for assign users 
	 */
	@Action(value = "/loadCourseDetailsforUsers")
	public void loadCourseDetailsforUsers() {
		try	{
			List<?> courseUsersList=courseListService.getPersonDetailsToAssignFn(courseid,getAttribute("organizationid"));
			checkobj().toJsonResponse(courseUsersList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled()){
				CoursesViewActionlogger.error("error in loadCourseDetailsforUsers() in CoursesViewAction",e);
			}
		}
	}



	/**
	 * @author raja_r
	 * Method to remove the user for this courses 
	 */
	@Action(value = "/removeCourseForUser")
	public void removeTheUserFromCourse() { 
		try{
			int courseErollmentid = courseEnrollmentService	.updateCourseenrollmentStatus(Integer.parseInt(courseenrollmentid));
			if(courseErollmentid!=0){
				checkobj().toJsonString("SUCCESS");
			}
			else{ 
				checkobj().toJsonString("FAILURE");
			}	
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in removeTheUserFromCourse() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author rubykannan_r
	 * Method to search courses by searchbox
	 */
	@Action(value="/searchCourses")
	public void searchCourses(){
		try	{
			String organizationid=""+getAttribute("organizationid");
			List<?> courseList=courseService.searchCourse(organizationid,keyword);
			checkobj().toJsonResponse(courseList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in searchCourses() in CoursesViewAction",e);
			}
		}
	}

	@Action(value="/ssearchCourses")
	public void ssearchCourses(){
		try	{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			String personid=null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");
				List<?> courseList=courseService.ssearchCourse(organizationid,keyword);
				checkobj().toJsonResponse(courseList);
			} else {
				organizationid=""+getAttribute("organizationid"); 
				personid=""+getAttribute("orgpersonid");
				String pstatus=""+getAttribute("rolename");

				if(pstatus.equals("admin")){			
					List<?> courseList=courseService.mastersearchCourse(organizationid,keyword,personid);
					checkobj().toJsonResponse(courseList);
				}else{
					List<?> courseList=courseService.ssearchCourse(organizationid,keyword);
					checkobj().toJsonResponse(courseList);
				}
			}

		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in searchCourses() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author willia mbenjamin_g
	 * Method to search Master Organization courses by searchbox
	 */
	@Action(value="/searchOrgcourses")
	public void searchMasterCourses(){
		try	{
			List<?> courseList=courseService.searchCourse(masteradminid,keyword);
			checkobj().toJsonResponse(courseList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in searchCourses() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author sridhar_m
	 * method to search organization wise list
	 */
	@Action(value="/searchOrganizationcourses")
	public void searchOrganizationCourses(){
		try	{
			organizationid=""+getAttribute("organizationid"); 
			List<?> courseList=courseService.searchCourse(organizationid,keyword);
			checkobj().toJsonResponse(courseList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in searchCourses() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author raja_r
	 * Method to get the list of users
	 * @param courseid
	 * @return List
	 */
	@Action(value = "/getCourseUser")
	public void getCourseUser() { 
		try	{ 
			List<?> courseUsersList=courseEnrollmentService.getCourseenrollment(courseid);
			checkobj().toJsonResponse(courseUsersList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCourseDetailsforUsers() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author raja_r
	 * Method to get the list of users
	 * @param courseid
	 * @return List
	 */
	@Action(value = "/getBlockedUser")
	public void getBlockedUser() { 
		try	{ 
			List<?> courseUsersList=courseEnrollmentService.getBlockedUser(courseid);
			checkobj().toJsonResponse(courseUsersList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in loadCourseDetailsforUsers() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author raja_r
	 * Method to get the list top courses
	 * @param courseid
	 * @return List
	 */
	@Action(value = "/getTopCourses")
	public void getTopCourses() { 
		try	{ 
			List<?> courseUsersList=courseListService.loadTopCourses();
			System.out.println(">>>>>>>>>"+courseUsersList);
			checkobj().toJsonResponse(courseUsersList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in getTopCourses() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author raja_r
	 * Method to get the Count of draft courses and trashed course
	 * @return List
	 */
	@Action(value = "/getDraftAndTrashCourseCount")
	public void getDraftAndTrashCourseCount() {  
		try	{ 
			List<?> draftCourseCountList=courseListService.getDraftAndTrashCourseCount();
			checkobj().toJsonResponse(draftCourseCountList);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in getDraftAndTrashCourseCount() in CoursesViewAction",e);
			}
		}
	}

	/**
	 * @author raja_r
	 * Method to get the Count of draft courses and trashed course
	 * @return List
	 */
	@Action(value = "/courseStatusUpdate")
	public void courseStatusUpdate() {  
		try	{ 
			Integer trashedCourse=courseEnrollmentService.updateCourseStatus(Integer.parseInt(courseid),coursestatus);
			checkobj().toJsonResponse(trashedCourse.toString());
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in courseStatusUpdate() in CoursesViewAction",e);
			}
		}
	}
	/**
	 * @author mahalingam_a
	 * @since 13-Sep-2014
	 * @purpose To get the User Spend Time in the particular course
	 */
	@Action(value = "/getCourseSpentTime")
	public void getCourseSpentTime() {  
		try	{ 
			String orgpersonid=getAttribute("orgpersonid");
			List<?>  trashedCourse=courseListService.getCourseSpentTime(courseid,orgpersonid);
			checkobj().toJsonResponse(trashedCourse);
		}
		catch(Exception e){
			if(CoursesViewActionlogger.isInfoEnabled())	{
				CoursesViewActionlogger.error("error in getCourseSpentTime() in CoursesViewAction",e);
			}
		}
	}

	/*getters and setters*/

	public String getCoursecategorystatus() {
		return coursecategorystatus;
	}


	public void setCoursecategorystatus(String coursecategorystatus) {
		this.coursecategorystatus = coursecategorystatus;
	}

	public String getCoursecategoryid() {
		return coursecategoryid;
	}


	public void setCoursecategoryid(String coursecategoryid) {
		this.coursecategoryid = coursecategoryid;
	}


	public String getCoursestatus() {
		return coursestatus;
	}


	public void setCoursestatus(String coursestatus) {
		this.coursestatus = coursestatus;
	}

	public String getCourseid() {
		return courseid;
	}


	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getPublishStatus() {
		return publishStatus;
	}


	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}


	public String getEnrollmentstatus() {
		return enrollmentstatus;
	}


	public void setEnrollmentstatus(String enrollmentstatus) {
		this.enrollmentstatus = enrollmentstatus;
	}

	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}


	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}


	public String getPersonid() {
		return personid;
	}


	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMasteradminid() {
		return masteradminid;
	}

	public void setMasteradminid(String masteradminid) {
		this.masteradminid = masteradminid;
	}


}