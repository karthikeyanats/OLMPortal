package com.igrandee.product.ecloud.action.course;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.service.course.CourseInvitationService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.session.SocialSessionDetail;


@Namespace("/app")
public class RedirectAction extends AbstractIGActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;
	private static Logger RedirectActionlogger = Logger.getLogger(RedirectAction.class);

	private String courseid;
	private String organizationid;
	private String count;
	private String courseenrollmentid;
	private String courselectureid;
	private String invoiceid;
	private Map<String, Object> session;  
	private String orgid;
	private String organizationurl;
	private String orgname;
	private String orgcount;
	private String blogid;
	private String orgtrashcount;
	private String planid;
	private String planamount;
	private String wishlisted;
	private String orgsubscriptionid;
	private String socialmsg;
	private String emailid;
	private String orgpersonid;
	private String status;
	private String courslectureidhidden;
	private String courseenrollmentidhidden;
	private String durationtype;
	private String duration;
	private String invitationid;
	private String EnrollStatus;
	private String bankinfo;
	private String courseinvitationid;
	private String keyword;
	private String targetUrl;
	private String username;
	private String password;
	private String organizationname;
	private String orglogo;
	private String price;
	private String presenterappdetailsid;
	private String coursetitlehidden;
	private String courselogo;
	private String coursetitle;
	private String giftcourseid;
	private String priceid;
	private String livesessionid;
	private String starttime;
	private String endtime;
	private String scheduledate;
	private String livesessionenrollmentid;
	private String livesessionid1;
	private String firstname;
	private String courseenrollmentstatus;
	
	
	

	public String getCourseenrollmentstatus() {
		return courseenrollmentstatus;
	}

	public void setCourseenrollmentstatus(String courseenrollmentstatus) {
		this.courseenrollmentstatus = courseenrollmentstatus;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	public String getScheduledate() {
		return scheduledate;
	}

	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}

	

	
	public String getLivesessionid() {
		return livesessionid;
	}

	public void setLivesessionid(String livesessionid) {
		this.livesessionid = livesessionid;
	}

	
	
	public String getSocialmsg() {
		return socialmsg;
	}

	public void setSocialmsg(String socialmsg) {
		this.socialmsg = socialmsg;
	}

	@Inject
	private CourseListService courseListService;

	@Inject
	private OrganizationViewService organizationViewService;

	@Inject
	private SocialSessionDetail socialSessionDetail;

	@Inject 
	private CourseInvitationService courseinvitationservice;

	/**
	 * 
	 * @return
	 */
	
	@Action(value="/livesession",results={@Result(name="SUCCESS",location="app/user/livesession.jsp")})
	public String livesession(){
		setLivesessionid(livesessionid);
		setUsername(username);
		setPassword(password);
		setFirstname(firstname);
		setCourseid(courseid);
		setCourseenrollmentid(courseenrollmentid);
		setCourseenrollmentstatus(courseenrollmentstatus);
		
		
		return "SUCCESS";
	}
	
	@Action(value="/viewLogFile",results={@Result(name="SUCCESS",location="app/admin/logview.jsp")})
	public String viewlogFile(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview
	 * @return success string
	 */
	@Action(value="/previewuser",results={@Result(name="SUCCESS",location="app/user/preview-user.jsp")})
	public String coursepreviewuser(){
		try{	
			if(courseid!=null){				
				setCourseid(courseid);
				setPrice(price);
				setPriceid(priceid);
			}else{				
				setCourseid(""+session.get("courseid"));
			}
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in previewuser() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/giftpay",results={@Result(name="SUCCESS",location="app/user/giftpay.jsp")})
	public String giftpay(){
		try{
			setGiftcourseid(giftcourseid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in giftpay() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/browseu",results={@Result(name="SUCCESS",location="app/user/browse-user.jsp")})
	public String browseuser(){
		try{
			}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in browseuser() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/browseA",results={@Result(name="SUCCESS",location="app/admin/browse-admin.jsp")})
	public String browseAdmin(){
		try{
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in browseAdmin() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/*@Action(value="/contentPublish",results={@Result(name="SUCCESS",location="app/admin/course/contentapprove.jsp")})
	public String contentPublish(){
		try{
			setCourseid(courseid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in contentPublish() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}*/
	
	@Action(value="/contentPublish",results={@Result(name="SUCCESS",location="app/admin/contentapprove.jsp")})
	public String contentPublish(){
		try{
			setCourseid(courseid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in contentPublish() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/published",results={@Result(name="SUCCESS",location="app/admin/course/publishedmessage.jsp")})
	public String contentPublished(){
		try{
			setCoursetitlehidden(coursetitlehidden);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in contentPublished() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}


	/**
	 * @author seenivasagan_p
	 * Method to Redirect to course completion report
	 * @return success string
	 */
	@Action(value="/report",results={@Result(name="SUCCESS",location="app/user/course-report.jsp")})
	public String compeletionReport(){
		try{
			setCourseenrollmentid(courseenrollmentid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in compeletionReport() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview
	 * @return success string
	 */
	@Action(value="/searchresult",results={@Result(name="SUCCESS",location="app/user/searchresult.jsp")})
	public String searchresult(){
		try{
			setKeyword(keyword);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in searchresult() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/previewcourse",results={@Result(name="SUCCESS",location="app/user/coursehome.jsp")})
	public String previewcourse(){
		try{
			if(courseenrollmentstatus!=null){
				setCourseenrollmentstatus(courseenrollmentstatus);
				socialSessionDetail.setCourseenrollmentstatus(courseenrollmentstatus);
			}
			if(courseid!=null){
				setCourseid(courseid);
				socialSessionDetail.setCourseid(courseid);
			}
			if(courseenrollmentid!=null) {
				setCourseenrollmentid(courseenrollmentid);
				setWishlisted(wishlisted);
				socialSessionDetail.setCourseenrollmentid(courseenrollmentid);
				socialSessionDetail.setWishlisted(wishlisted);
			}
			if(courseenrollmentid==null && courseid==null){
				setCourseid(socialSessionDetail.getCourseid());
				setCourseenrollmentid(socialSessionDetail.getCourseenrollmentid());
				setWishlisted(socialSessionDetail.getWishlisted());
				setSocialmsg(socialSessionDetail.getSocialmsg());
			} 			
			socialSessionDetail.setSocialmsg("");
		}
		catch(Exception e){
			e.printStackTrace(); 
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in previewcourse() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user management for author
	 * @return success string
	 */
	@Action(value="/usermanage",results={@Result(name="SUCCESS",location="app/user/manageuser.jsp")})
	public String usermanage(){
		try{
			setCourseid(courseid); 		
			setPrice(price);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in usermanage() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user management for author
	 * @return success string
	 */
	@Action(value="/downloadpresenter",results={@Result(name="SUCCESS",location="app/guest/download.jsp")})
	public String downloadpresenter(){
		try{
			if(socialSessionDetail.isFbcheck()){
				setSocialmsg(socialSessionDetail.getSocialmsg());
			}
			socialSessionDetail.setFbcheck(false);
			socialSessionDetail.setSocialmsg("");
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in download() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview
	 * @return success string
	 */
	@Action(value="/showinvoice",results={@Result(name="SUCCESS",location="app/user/invoice.jsp")})
	public String showinvoice()	{
		try	{
			setInvoiceid(invoiceid);
			if(courseenrollmentid!=null) {
				setCourseenrollmentid(courseenrollmentid); 
			}
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in showinvoice() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/billinginvoice",results={@Result(name="SUCCESS",location="app/user/billinginvoice.jsp")})
	public String billinginvoice()	{
		try	{
			setInvoiceid(invoiceid);
			setLivesessionenrollmentid(livesessionenrollmentid);
			if(courseenrollmentid!=null) { 
				setCourseenrollmentid(courseenrollmentid); 
			}
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in billinginvoice() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author venkatraman_v
	 * @return
	 */
	@Action(value="/learntobrowse",results={@Result(name="SUCCESS",location="app/user/browsecoursesuser.jsp")})
	public String learntobrowse(){
		try{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in browsecourse() in IndexActionlogger",e);
			}
		}
		return "SUCCESS";
	}


	/**
	 * @author seenivasagan_p
	 * Method to Redirect to features page
	 * @return success string
	 */
	@Action(value="/features",results={@Result(name="SUCCESS",location="app/sample/fileuploadsample.jsp")})
	public String features(){
		try	{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in features() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to Plans page
	 * @return success string
	 */
	@Action(value="/plans",results={@Result(name="SUCCESS",location="app/guest/plans.jsp")})
	public String plans(){
		try	{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in plans() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}


	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/mycourses",results={@Result(name="SUCCESS",location="app/user/mycourses.jsp")})
	public String mycourses(){
		try	{
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in preview() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/authors",results={@Result(name="SUCCESS",location="app/user/authors.jsp")})
	public String authors(){
		try	{
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in preview() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/authorProfile",results={@Result(name="SUCCESS",location="app/user/authorProfile.jsp")})
	public String authorProfile(){
		try	{
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in authorProfile() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/learnCourse",results={@Result(name="SUCCESS",location="app/user/learncourse.jsp")})
	public String learnCourse()	{
		try	{
			setCourseenrollmentid(courseenrollmentid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled())	{
				RedirectActionlogger.error("error in learnCourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/learnCourseP",results={@Result(name="SUCCESS",location="app/user/learncourse.jsp")})
	public String learnCourseP()	{
		try	{
			setCourseenrollmentid(courseenrollmentid);
			setCourselectureid(courselectureid);			
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled())	{
				RedirectActionlogger.error("error in learnCourseP() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/courses",results={@Result(name="SUCCESS",location="app/admin/course/courselist.jsp")})
	public String courses(){
		try	{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in learnCourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/managecourse",results={@Result(name="SUCCESS",location="app/admin/course/managecourse.jsp")})
	public String managecourse(){
		try	{
			setCourseid(courseid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in learnCourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/manageprofile",results={@Result(name="SUCCESS",location="app/user/manageprofile.jsp")})
	public String manageprofile(){
		try	{	
			setBankinfo(bankinfo);
			setCourseid(courseid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in learnCourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Method to Redirect to my courses for user
	 * @return success string
	 */
	@Action(value="/organizations",results={@Result(name="SUCCESS",location="app/admin/organization/organization.jsp")})
	public String organizations(){
		try	{	}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in organizations() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/coursepayment",results={@Result(name="SUCCESS",location="app/user/coursepayment.jsp"),@Result(name="preview",location="app/user/mycourses.jsp")})
	public String coursepayment(){
		try	{ 

			if(session.get("courseid")==null ||  session.get("courseid").toString().equalsIgnoreCase("null") || session.get("courseid")=="" || session.get("courseid").equals("") || session.get("courseid").toString().equalsIgnoreCase("")){					  
				setCourseid(courseid);
				if(courseenrollmentid!=null) {
					setCourseenrollmentid(courseenrollmentid); 
				} 
			} 
			else{ 
				setCourseid(""+session.get("courseid"));
				if(courseenrollmentid!=null) {
					setCourseenrollmentid(courseenrollmentid); 
				}
			} 

			List checkEnroll = courseListService.chkentoenrollMent(Integer.parseInt(getAttribute("orgpersonid")),Integer.parseInt(getCourseid()));
			if(session.get("courseinvitationid")!=null) {
				String courseinvitationid=(String)getSession().get("courseinvitationid");
				courseinvitationservice.updateCourseInvitation(courseinvitationid);
			}
			if(checkEnroll.size()!=0){	
				Map map =(Map)checkEnroll.get(0); 
				setCourseenrollmentid(""+map.get("courseenrollmentid"));    
				setStatus("alreadytaken");
				setEnrollStatus(""+map.get("courseenrollmentstatus"));
				return "preview"; 
			}else{
				return "SUCCESS";	
			}		   	    	

		}
		catch(Exception e)	{
			e.printStackTrace();
			return null;
		}
	}

	@Action(value="/subscriptioncoursepayment",results={@Result(name="SUCCESS",location="app/organization/coursepayment.jsp")})
	public String subscriptioncoursepayment(){
		try	{ 
			setPlanid(planid);
			setPlanamount(planamount);
			setOrgsubscriptionid(orgsubscriptionid);
			setDurationtype(durationtype);
			setDuration(duration);
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return "SUCCESS";
	}

 	/**
	 * @author williambenjamin_g
	 * Method to Redirect to create Organization
	 * @return success string
	 */
	@Action(value="/myorganization",results={@Result(name="SUCCESS",location="app/organization/myorganization.jsp")})
	public String myorganization(){
		try	{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in plans() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author williambenjamin_g
	 * Method to Redirect to Organization Details
	 * @return success string
	 */
	@Action(value="/organizationdetails",results={@Result(name="SUCCESS",location="app/admin/organization/organizationdetails.jsp")})
	public String organizationDetails(){
		try	{
			setOrganizationid(organizationid);
			setOrgname(orgname);
			setOrgcount(orgcount);
			setOrgtrashcount(orgtrashcount);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in plans() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/index",results={@Result(name="SUCCESS",location="/index.jsp")})
	public String indexPage(){
		try	{
		}  
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in plans() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author muniyarasu_a
	 * redirect login template page
	 */
	@Action(value="/loginpage",results={@Result(name="SUCCESS",location="/logintemplate.jsp")})
	public String loginPage(){
		try	{
			setInvitationid(invitationid);
			setEmailid(emailid);
			setOrganizationid(organizationid);
			setOrgpersonid(orgpersonid);
			setCourseid(courseid);
		}  
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in loginPage() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author muniyarasu_a
	 * redirect royalty page
	 */
	@Action(value="/royalty",results={@Result(name="SUCCESS",location="app/admin/royalty.jsp")})
	public String royalty(){
		try	{

			setEmailid(emailid);
			setOrganizationid(organizationid);
			setOrgpersonid(orgpersonid);
		}  
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in royalty() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author muniyarasu_a
	 * redirect free course success page
	 */
	@Action (value="/freecourse",results={@Result(name="SUCCESS",location="app/user/success.jsp")})
	public String freecourse(){
		try {

		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in freecourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author sridhar_m
	 * redirect blog action
	 * */
	@Action(value="/blog",results={@Result(name="SUCCESS",location="app/blog/blog.jsp")})
	public String blogEntry(){
		try	{

		}  
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in blogEntry() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author sridhar_m
	 * redirect action single blog
	 * 
	 */
	@Action(value="/blogs",results={@Result(name="SUCCESS",location="app/blog/blog-single.jsp")})
	public String blogLists(){
		try{
			setBlogid(blogid);
		}
		catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in blogList() in RedirectAction",e);
			}
			// TODO: handle exception
		}
		return "SUCCESS";
	}

	/**
	 * GET LIST OF ALL BLOG POSTS AND DO CRUD OPERATIONS IN THIS
	 * blogpostlist.jsp PAGE.
	 * 
	 * @return "SUCCESS" String
	 * @author balajichander_r
	 */
	@Action(value = "/blogposts", results = { @Result(name = "SUCCESS", location = "app/blog/blogpostlist.jsp") })
	public String blogPostLists() {
		try {
			RedirectActionlogger.info("BLOG POST LIST REDIRECT ACTION");
		} catch (Exception e) {
			if (RedirectActionlogger.isInfoEnabled()) {
				RedirectActionlogger
				.error("error in blogList() in RedirectAction" + e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author sridhar_m
	 * redirect action in user view
	 */

	@Action(value="/blogspage",results={@Result(name="SUCCESS",location="/blogs.jsp")})
	public String blogList(){
		try{
			setBlogid(blogid);
		}
		catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in blogList() in RedirectAction",e);
			}
			// TODO: handle exception
		}
		return "SUCCESS";
	}

	@Action(value="/blogupdate",results={@Result(name="SUCCESS",location="app/blog/blogupdate.jsp")})
	public String blogUpdate(){
		try {
			RedirectActionlogger.info("blogid ==== > "+ blogid );
			setBlogid(blogid);
		} catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in blogUpdate() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author sridhar_m
	 * redirect action plan
	 */
	@Action(value="/plan",results={@Result(name="SUCCESS",location="app/user/subscriptionplan.jsp")})
	public String plan(){
		try {

		} catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in plan() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author muniyarasu_a
	 * Method to Redirect to organizationview template page
	 */
	@Action(value="/createorganizationview",results={@Result(name="SUCCESS",location="app/user/createorganization.jsp")})
	public String createorganizationview(){
		try {
			setOrganizationid(organizationid);
			setTargetUrl(targetUrl);
			setUsername(username);
			setPassword(password);
			setOrganizationname(organizationname);
			setOrglogo(orglogo);

		} catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in createorganizationview() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author muniyarasu_a
	 * Method to Redirect to Presenter License payment page
	 * return "SUCCESS" String
	 */
	@Action(value="/licensepresenter",results={@Result(name="SUCCESS",location="app/guest/presenterlicense.jsp")})
	public String licensepresenter(){
		try {
			setPrice(price);
			setPresenterappdetailsid(presenterappdetailsid);

		} catch (Exception e) {
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in licensepresenter() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 *Load Session Expired Page
	 */
	@Action(value="/sessionexpired",results={@Result(name="SUCCESS",location="/WEB-INF/web/exception/SessionExpire.jsp")})
	public String Sessionexpired(){
		try	{}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in Sessionexpired() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author raja_r
	 * redirect purchaseHistory
	 * */
	@Action(value="/purchaseHistory",results={@Result(name="SUCCESS",location="app/user/purchasehistory.jsp")})
	public String purchaseHistory(){
		try	{

		}  
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in purchaseHistory() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}

	/**
	 * @author srithar_m
	 * method to redirect the gift course 
	 * return "SUCCESS" string
	 */
	@Action(value="/giftcourse",results={@Result(name="SUCCESS",location="app/admin/course/giftcourse.jsp")})
	public String Giftcourse(){
		try{
			setCourseid(courseid);
			setCoursetitle(coursetitle);
			setCourselogo(courselogo);
			setPrice(price);
			setPriceid(priceid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in gift Giftcourse() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
/* live chat*/
	
	@Action(value="/livechat",results={@Result(name="SUCCESS",location="app/guest/live.jsp")})
	public String livechat(){
		try{
			
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in livechat() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
// schedulepayment
	
	@Action(value="/schedulepayment",results={@Result(name="SUCCESS",location="app/guest/schedulepayment.jsp")})
	public String schedulepayment(){
		try{
			setLivesessionid(livesessionid);
			setStarttime(starttime);
			setEndtime(endtime);
			setScheduledate(scheduledate);
			setPrice(price);
			setCoursetitle(coursetitle);
			setCourseid(courseid);
			setCourseenrollmentid(courseenrollmentid);
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in schedulepayment() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	
	/* success enroll process */
	@Action(value="/enrollpayment",results={@Result(name="SUCCESS",location="app/user/livesessionenrollpayment.jsp")})
	public String enrollpayment(){
		try{
			setLivesessionid(livesessionid1);
			setStatus(status);		
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in enrollpayment() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	/* success enroll process */
	@Action(value="/authorsearch",results={@Result(name="SUCCESS",location="app/user/authorsearch.jsp")})
	public String authorsearch(){
		try{
			setLivesessionid(livesessionid1);
			setStatus(status);		
		}
		catch(Exception e){
			if(RedirectActionlogger.isInfoEnabled()){
				RedirectActionlogger.error("error in enrollpayment() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	
	/*getters and setters*/


	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getCourselogo() {
		return courselogo;
	}

	public void setCourselogo(String courselogo) {
		this.courselogo = courselogo;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	} 
	public Map<String, Object> getSession() {
		return session;
	}

	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}
	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
	public String getInvoiceid() {
		return invoiceid;
	}
	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationurl() {
		return organizationurl;
	}

	public void setOrganizationurl(String organizationurl) {
		this.organizationurl = organizationurl;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgcount() {
		return orgcount;
	}

	public void setOrgcount(String orgcount) {
		this.orgcount = orgcount;
	}

	public String getOrgtrashcount() {
		return orgtrashcount;
	}

	public void setOrgtrashcount(String orgtrashcount) {
		this.orgtrashcount = orgtrashcount;
	}

	public String getBlogid() {
		return blogid;
	}

	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getPlanamount() {
		return planamount;
	}

	public void setPlanamount(String planamount) {
		this.planamount = planamount;
	}

	public String getWishlisted() {
		return wishlisted;
	}

	public void setWishlisted(String wishlisted) {
		this.wishlisted = wishlisted;
	}

	public String getOrgsubscriptionid() {
		return orgsubscriptionid;
	}

	public void setOrgsubscriptionid(String orgsubscriptionid) {
		this.orgsubscriptionid = orgsubscriptionid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getOrgpersonid() {
		return orgpersonid;
	}

	public void setOrgpersonid(String orgpersonid) {
		this.orgpersonid = orgpersonid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCourselectureid() {
		return courselectureid;
	}

	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}

	public String getCourslectureidhidden() {
		return courslectureidhidden;
	}

	public void setCourslectureidhidden(String courslectureidhidden) {
		this.courslectureidhidden = courslectureidhidden;
	}

	public String getCourseenrollmentidhidden() {
		return courseenrollmentidhidden;
	}

	public void setCourseenrollmentidhidden(String courseenrollmentidhidden) {
		this.courseenrollmentidhidden = courseenrollmentidhidden;
	}

	public String getDurationtype() {
		return durationtype;
	}

	public void setDurationtype(String durationtype) {
		this.durationtype = durationtype;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getInvitationid() {
		return invitationid;
	}

	public void setInvitationid(String invitationid) {
		this.invitationid = invitationid;
	}

	public String getEnrollStatus() {
		return EnrollStatus;
	}

	public void setEnrollStatus(String enrollStatus) {
		EnrollStatus = enrollStatus;
	}

	public String getCourseinvitationid() {
		return courseinvitationid;
	}

	public void setCourseinvitationid(String courseinvitationid) {
		this.courseinvitationid = courseinvitationid;
	}	

	public String getBankinfo() {
		return bankinfo;
	}

	public void setBankinfo(String bankinfo) {
		this.bankinfo = bankinfo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getOrglogo() {
		return orglogo;
	}

	public void setOrglogo(String orglogo) {
		this.orglogo = orglogo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPresenterappdetailsid() {
		return presenterappdetailsid;
	}

	public void setPresenterappdetailsid(String presenterappdetailsid) {
		this.presenterappdetailsid = presenterappdetailsid;
	}

	public String getCoursetitlehidden() {
		return coursetitlehidden;
	}

	public void setCoursetitlehidden(String coursetitlehidden) {
		this.coursetitlehidden = coursetitlehidden;
	}

	public String getGiftcourseid() {
		return giftcourseid;
	}

	public void setGiftcourseid(String giftcourseid) {
		this.giftcourseid = giftcourseid;
	}

	public String getPriceid() {
		return priceid;
	}

	public void setPriceid(String priceid) {
		this.priceid = priceid;
	}

	public String getLivesessionenrollmentid() {
		return livesessionenrollmentid;
	}

	public void setLivesessionenrollmentid(String livesessionenrollmentid) {
		this.livesessionenrollmentid = livesessionenrollmentid;
	}

	public String getLivesessionid1() {
		return livesessionid1;
	}

	public void setLivesessionid1(String livesessionid1) {
		this.livesessionid1 = livesessionid1;
	}

}