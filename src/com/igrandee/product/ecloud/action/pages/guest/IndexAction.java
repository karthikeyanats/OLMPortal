package com.igrandee.product.ecloud.action.pages.guest;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;

public class IndexAction extends AbstractIGActionSupport {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger IndexActionlogger = Logger.getLogger(IndexAction.class);
	private String courseid;
	private String count;
	private String orgid;
	private String blogid;
    private String userstatus;
    private String courseinvitationid;
    private String keyword;
    private String coursesubscribers;
    private String emailid;
	@Inject
	private CourseListService courseListService;
	
	@Inject
	private OrganizationViewService organizationViewService;
	
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview 
	 * @return success string
	 */
	@Action(value="/preview",results={@Result(name="SUCCESS",location="app/guest/preview-guest.jsp")})
	public String coursepreviewguest(){
		try{
			setCourseinvitationid(courseinvitationid);
			setCourseid(courseid); 
			setUserstatus(userstatus);
			setCoursesubscribers(coursesubscribers);
			setEmailid(emailid);
			/*List countList = courseListService.getCourseCount(courseid);
			if(countList!=null) {
				Map map  =(Map)countList.get(0);  
				setCount(""+map.get("count"));
			}  else {
				setCount("0");
			}*/
		}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in preview() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/categories",results={@Result(name="SUCCESS",location="app/guest/categories.jsp")})
	public String categories(){
		try{
			
		}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in categories() in RedirectAction ",e);
			}
		}
		return "SUCCESS";
	}
 
	
	
	/**
	 * @author williambenjamin_g
	 * Method to Redirect to create Organization
	 * @return success string
	 */
	@Action(value="/createorganization",results={@Result(name="SUCCESS",location="app/organization/Organization.jsp")})
	public String organization(){
		try	{			
			List<?> getOrgid = organizationViewService.getMasterOrgid();
			for(int i=0;i<getOrgid.size();i++) {
				Map map = (Map)getOrgid.get(i);
				String orgid =""+map.get("organizationid");
				setOrgid(orgid);	
			}
 			
		}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in createorganization() in IndexActionlogger"+e);
			}
		}
		return "SUCCESS";
	}
	
	/**
	 * @author williambenjamin_g
	 * Method to Redirect to login suborganization
	 * @return success string
	 */
	@Action(value="/suborganization",results={@Result(name="SUCCESS",location="app/organization/suborganization.jsp")})
	public String suborganization(){
		try	{ 			
		}catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in suborganization() in IndexActionlogger"+e);
			}
		}
		return "SUCCESS";
	}
	
	 
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to Plans page
	 * @return success string
	 */
	@Action(value="/browsecourse",results={@Result(name="SUCCESS",location="app/guest/courses.jsp")})
	public String browsecourse(){
		try{}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in browsecourse() in IndexActionlogger"+e);
			}
		}
		return "SUCCESS";
	}
	
	@Action(value="/browse",results={@Result(name="SUCCESS",location="app/guest/browsecourses.jsp")})
	public String browse(){
		try{}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in browsecourse() in IndexActionlogger"+e);
			}
		}
		return "SUCCESS";
	}
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview
	 * @return success string
	 */
	@Action(value="/ssearchresult",results={@Result(name="SUCCESS",location="app/guest/searchresult.jsp")})
	public String ssearchresult(){
		try{
			setKeyword(keyword);
		}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in ssearchresult() in IndexActionlogger "+e);
			}
		}
		return "SUCCESS";
	}

	@Action(value="/blogs",results={@Result(name="SUCCESS",location="app/blog/blog-single.jsp")})
	public String blogLists(){
		try{
			setBlogid(blogid);
		}
		catch (Exception e) {
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in blogList() in IndexActionlogger"+e);
			}
			// TODO: handle exception
		}
		return "SUCCESS";
	}
	//vetrichevi
	/* contact process */
	@Action(value="/contact",results={@Result(name="SUCCESS",location="app/guest/contact.jsp")})
	public String contact(){
		try{
					
		}
		catch(Exception e){
			if(IndexActionlogger.isInfoEnabled()){
				IndexActionlogger.error("error in contact() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	 
	public String getCourseid() {
		return courseid;
	}
 
	public void setCourseid(String courseid) {
		this.courseid = courseid;
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


	public String getBlogid() {
		return blogid;
	}


	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}


	public String getUserstatus() {
		return userstatus;
	}


	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}


	public String getCourseinvitationid() {
		return courseinvitationid;
	}


	public void setCourseinvitationid(String courseinvitationid) {
		this.courseinvitationid = courseinvitationid;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCoursesubscribers() {
		return coursesubscribers;
	}

	public void setCoursesubscribers(String coursesubscribers) {
		this.coursesubscribers = coursesubscribers;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	
	
}