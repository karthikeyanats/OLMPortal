package com.igrandee.product.ecloud.action.course;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.service.course.CourseCategoryService;
import com.igrandee.product.ecloud.service.organization.OrganizationViewService;


public class CourseCategoryAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger CourseCategoryActionlogger = Logger.getLogger(CourseCategoryAction.class);
	
	@Inject
	CourseCategoryService CourseCategoryServiceobj;
	
	@Autowired
	private Coursecategory coursecategoryobj;
	
	@Inject
	OrganizationViewService organizationViewService;
	
	private String coursecategoryid;
	private String coursecategorystatus;
	private String coursecategoryname;
	
	
	/**
	 * @author seenivasagan_p
	 * Method to get All valid categories
	 */
	@Action(value="/loadAllValidCategories")
 	public void loadAllValidCategories(){
		try	{
 			List<?> validCategoriesList=CourseCategoryServiceobj.getAllValidCategories(coursecategorystatus,getAttribute("orgpersonid"));	
			checkobj().toJsonResponse(validCategoriesList);
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled())	{
				CourseCategoryActionlogger.error("error in loadAllValidCategories() in CourseCategoryAction", e);
			}
		}  
	}
	/**
	 * @author seenivasagan_p
	 * Method to get All valid guest categories
	 */
	@Action(value="/loadGuestCategories")
 	public void loadGuestCategories(){
		try	{
			List<?> validCategoriesList=CourseCategoryServiceobj.getAllValidCategories(coursecategorystatus);	
			checkobj().toJsonResponse(validCategoriesList);
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled())	{
				CourseCategoryActionlogger.error("error in loadGuestCategories() in CourseCategoryAction", e);
			}
		}  
	}
	
	@Action(value="/loadCategorieswiseCourse")
 	public void loadCategorieswiseCourses(){
		try	{
			String userCheck = ""+SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String organizationid = null;
			if(userCheck.equalsIgnoreCase("anonymousUser"))	{
				List<?> MasterOrgId = organizationViewService.getMasterOrgid(); 
				Map<?,?> map=(Map<?, ?>) MasterOrgId.get(0);
				organizationid = ""+map.get("organizationid");	
			} else {
				organizationid=""+getAttribute("organizationid"); 
			}

			List<?> validCategoriesList=CourseCategoryServiceobj.getAllActiveCategorywiseNoofCourse(coursecategorystatus,organizationid);	
			checkobj().toJsonResponse(validCategoriesList);
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled())	{
				CourseCategoryActionlogger.error("error in loadGuestCategories() in CourseCategoryAction", e);
			}
		}  
	}
	/**
	 * @author seenivasagan_p
	 * Method to save new category
	 */
	@Action(value="/saveCategory")
	public void saveCategory(){
		try	{
			coursecategoryobj.setCoursecategoryname(coursecategoryname);
			coursecategoryobj.setCoursecategorystatus("A");
			String inserted=CourseCategoryServiceobj.save(coursecategoryobj).toString();
			checkobj().toJsonResponse(inserted);
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in saveCategory() in CourseCategoryAction", e);
			}
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to edit existing category
	 */
	@Action(value="/editCategory")
	public void editCategory(){
		try	{
			coursecategoryobj.setCoursecategoryid(Integer.parseInt(coursecategoryid));
			coursecategoryobj.setCoursecategoryname(coursecategoryname);
			coursecategoryobj.setCoursecategorystatus("A");
			CourseCategoryServiceobj.update(coursecategoryobj);
			checkobj().toJsonString("updated");
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in saveCategory() in CourseCategoryAction", e);
			}
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to delete an existing category
	 */
	@Action(value="/deleteCategory")
	public void deleteCategory(){
		try	{
			CourseCategoryServiceobj.deleteCategoryValue(Integer.parseInt(coursecategoryid));
			checkobj().toJsonString("updated");
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in saveCategory() in CourseCategoryAction", e);
			}
		}
	}
	
	/**
	 * @author muniyarasu_a
	 * Method to restore a category
	 * @param coursecategoryid
	 */
	@Action(value="/restoreCategory")
	public void restoreCategory(){
		try	{
			CourseCategoryServiceobj.restoreCategoryValue(Integer.parseInt(coursecategoryid));
			checkobj().toJsonString("updated");
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in restoreCategory() in CourseCategoryAction", e);
			}
		}
	}
	
	/**
	 * @author muniyarasu_a
	 * Method to check duplicate category
	 * @param coursecategoryname
	 */
	@Action(value="/checkCategory")
	public void checkCategory(){
		try	{
			List<?> categorylist =CourseCategoryServiceobj.categorycheck(coursecategoryname);
			if(categorylist.size()==0){
				checkobj().toJsonString("Succees");	
			}
			else{
				checkobj().toJsonString("Exists");
			}
			
		}
		catch(Exception e){
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in checkCategory() in CourseCategoryAction", e);
			}
		}
	}
	
	@Action(value="/coursecategoryexists")
	public void coursecategoryexists() 
	{
		try
		{
  			List<?> individualCoursesListAsTree=CourseCategoryServiceobj.coursevailablecheck(Integer.parseInt(coursecategoryid));
 			if(individualCoursesListAsTree!=null)
			{
				if(individualCoursesListAsTree.size()!=0)
				{
 					checkobj().toJsonString("exists");
 				}
				else
				{
					checkobj().toJsonString("false");
				}
			}
			else
			{
				checkobj().toJsonString("false");
			}
 		}
		catch(Exception e)
		{
			if(CourseCategoryActionlogger.isInfoEnabled()){
				CourseCategoryActionlogger.error("error in checkCategory() in CourseCategoryAction", e);
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
	public String getCoursecategoryname() {
		return coursecategoryname;
	}
	public void setCoursecategoryname(String coursecategoryname) {
		this.coursecategoryname = coursecategoryname;
	}
}