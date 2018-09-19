package com.igrandee.product.ecloud.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.helper.JsonHelper;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Price;
import com.igrandee.product.ecloud.service.course.CourseCategoryService;
import com.igrandee.product.ecloud.service.course.CourseLectureService;
import com.igrandee.product.ecloud.service.course.CourseListService;
import com.igrandee.product.ecloud.service.course.CourseSectionService;
import com.igrandee.product.ecloud.service.course.LectureContentService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;
import com.igrandee.product.ecloud.service.session.SessionService;

public class RemediPersenterAction   
{

	@Inject
	CourseCategoryService CourseCategoryServiceobj;
	
	@Inject
	CourseListService courseListService;
	
	@Inject
	CourseSectionService courseSectionService;
	
	@Inject
	CourseLectureService CourseLectureServiceobj;
	
	@Inject
	LectureContentService lectureContentService;
	
	@Inject
	OrganizationViewService organizationViewService;
	
	@Inject
	SessionService sessionService;
	
	private String coursecategoryname;
    private String coursetitle;
    private String personid;
    private String coursesectiontitle;
    private String courselecturetitle;
	private String coursecategoryid;
	private String courseid;
	private String coursesectionid;
	private String courselectureid;
 	private String loginid;
	private String organizationid;
	
	@Action (value="/app/savePersenterCourse")
	public void savePersenterCourse(){
	
		//Category
		Map valueMap = new HashMap();
		List valueMapList = new ArrayList();

		List<?> personList =sessionService.getLoginDetails(loginid);
		Orgperson orgperson =new Orgperson();
		String orgpersonid =null;
		if(personList.size()!=0) {
			for(int i=0;i<personList.size();i++){
				Map map = (Map)personList.get(i);
				orgpersonid = ""+map.get("orgpersonid");
			} 
		}
 		
 		List categoryList = CourseCategoryServiceobj.categorycheck(coursecategoryname);
		if(categoryList.size()==0) {
			 Coursecategory courseCategory = new Coursecategory();
			 courseCategory.setCoursecategoryname(coursecategoryname);
			 courseCategory.setCoursecategorystatus("A");
			 coursecategoryid=CourseCategoryServiceobj.save(courseCategory).toString();
		} else {
			for(int i=0;i<categoryList.size();i++){
			  Map map = (Map)categoryList.get(i); 
			  coursecategoryid = ""+map.get("coursecategoryid");
			}
		}
 		
		//Course
		List courseList = courseListService.checkCourse(coursetitle,coursecategoryid);
		if(courseList.size()==0) {
	 		Course course=new Course();
			course.setCoursetitle(coursetitle);
			course.setCoursestatus("D");
			course.setCoursedescription("");
			Price priceobj=new Price();
			priceobj.setPrice("Free");
			priceobj.setPricestatus("A");
			priceobj.setCourse(course);
			HashSet<Price> prices=new HashSet<Price>();
			prices.add(priceobj);
			course.setPrices(prices);
			java.util.Date date = new java.util.Date();
			course.setCoursecreateddate(new Timestamp(date.getTime()));
 			 
 			orgperson.setOrgpersonid(Integer.parseInt(orgpersonid));
			course.setOrgperson(orgperson);
			Coursecategory  coursecategory =new Coursecategory();
			coursecategory.setCoursecategoryid(Integer.parseInt(coursecategoryid));
			course.setCoursecategory(coursecategory);
			courseid = courseListService.save(course).toString();
		} else {
			for(int i=0;i<courseList.size();i++){
			  Map map = (Map)courseList.get(i); 
			  courseid = ""+map.get("courseid");
			}
		}
 		
		//Section
		List<?> sectionList = courseSectionService.checkSection(coursesectiontitle,courseid);
		if(sectionList.size()==0) {
			Course course=new Course();
			Coursesection courseSection = new Coursesection();
			course.setCourseid(Integer.parseInt(courseid));
			courseSection.setCoursesectiontitle(coursesectiontitle);
			courseSection.setCoursesectionstatus("A");
			courseSection.setCourse(course);
			coursesectionid=courseSectionService.save(courseSection).toString();
		} else {
			for(int i=0;i<sectionList.size();i++){
			  Map map = (Map)sectionList.get(i); 
			  coursesectionid = ""+map.get("coursesectionid");
			}
		}
		List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
		Map<?,?> map1=(Map<?, ?>) MasterOrgId.get(0);
 		valueMap.put("organizationid", ""+map1.get("organizationid"));
  		valueMap.put("orgpersonid", orgpersonid);
   		valueMap.put("coursecategoryid", coursecategoryid);
 		valueMap.put("courseid", courseid);
		valueMap.put("coursesectionid", coursesectionid);
		valueMapList.add(valueMap);
  		JsonHelper jsonHelper= new JsonHelper(); 
 		jsonHelper.toJsonResponse(valueMapList);
		
 	}

	public String getCoursecategoryname() {
		return coursecategoryname;
	}

	public void setCoursecategoryname(String coursecategoryname) {
		this.coursecategoryname = coursecategoryname;
	}

	public String getCoursetitle() {
		return coursetitle;
	}

	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getCoursesectiontitle() {
		return coursesectiontitle;
	}

	public void setCoursesectiontitle(String coursesectiontitle) {
		this.coursesectiontitle = coursesectiontitle;
	}

	public String getCourselecturetitle() {
		return courselecturetitle;
	}

	public void setCourselecturetitle(String courselecturetitle) {
		this.courselecturetitle = courselecturetitle;
	}

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

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}
	
}