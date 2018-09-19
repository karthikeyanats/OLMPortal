package com.igrandee.product.ecloud.service.course;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;

/**
 * @author seenivasagan_p
 * Service Class for Coursecategory View Object
 */
@Service
@Named
@Scope("prototype")
public class CourseCategoryService extends GenericEntityService<Coursecategory, Integer>{

	private static Logger CourseCategoryServicelogger = Logger.getLogger(CourseCategoryService.class);

	@Override
	protected Class<Coursecategory> entityClass() {
		// TODO Auto-generated method stub
		return Coursecategory.class;
	}

	/**
	 * @author seenivasagan_p
	 * @param coursecategorystatus
	 * @return all valid categories
	 */
	public List<?> getAllValidCategories(String coursecategorystatus) {

		List<?> categoriesList = null;
		Criteria query= null;
		try	{
			Order descor = Order.desc("coursecategoryid");
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class).addOrder(descor)
					.createAlias("this.courses", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("course.coursestatus","A"))
					.createAlias("course.orgperson","orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.coursecategorystatus",coursecategorystatus));
 			categoriesList=	query.setProjection(Projections.projectionList()
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
					.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("this.coursecategorystatus").as("coursecategorystatus")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in getAllValidCategories() in CourseCategoryService",e);
			}
		}
		return categoriesList;
	}
	
	
	public List<?> getAllValidCategories(String coursecategorystatus,String orgpersonid) {

		List<?> categoriesList = null;
		Criteria query= null;
		try	{
			Order descor = Order.desc("coursecategoryid");
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class).addOrder(descor)
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson","orgperson")
					.add(Restrictions.eq("this.coursecategorystatus",coursecategorystatus))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
					.add(Restrictions.eq("course.coursestatus","A"));
			categoriesList=	query.setProjection(Projections.projectionList()
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
					.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("this.coursecategorystatus").as("coursecategorystatus"))
					.add(Projections.count("this.coursecategoryid").as("coursescount"))
					.add(Projections.groupProperty("this.coursecategoryid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in getAllValidCategories() in CourseCategoryService",e);
			}
		}
		return categoriesList;
	}
	
	public List getAllActiveCategorywiseNoofCourse(String coursecategorystatus,String organizationid){
		   List categoryList = null; 
           Criteria query = null;
 				try {
					query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(organizationid)))
					.add(Restrictions.in("organizationid.orgstatus",new Character[] {'A','M'}))
 					.add(Restrictions.eq("coursecategory.coursecategorystatus", "A")) 
					.add(Restrictions.eq("this.coursestatus", "A"));
					categoryList=query.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.count("coursecategory.coursecategoryid").as("coursescount"))
							.add(Projections.groupProperty("coursecategory.coursecategoryid"))).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
				} catch (HibernateException e) {
 					if(CourseCategoryServicelogger.isInfoEnabled())	{
						CourseCategoryServicelogger.error("error in getAllValidCategories() in CourseCategoryService",e);
					}
				}					
				return categoryList;
	} 
				
		
	
	

	/**
	 * @author seenivasagan_p
	 * Method to delete a category
	 * @param coursecategoryid
	 */
	public String deleteCategoryValue(int coursecategoryid){
		String catdeleted="";
		try{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Coursecategory set coursecategorystatus='T' where coursecategoryid="+coursecategoryid +" ").executeUpdate();
			catdeleted="deleted";
		}
		catch(Exception e){
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in deleteCategoryValue() in CourseCategoryService",e);
			}
		}
		return catdeleted;
	}

	/**
	 * @author muniyarasu_a
	 * Method to restore a category
	 * @param coursecategoryid
	 */
	public String restoreCategoryValue(int coursecategoryid){
		String catrestore="";
		try{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Coursecategory set coursecategorystatus='A' where coursecategoryid="+coursecategoryid +" ").executeUpdate();
			catrestore="restore";
		}
		catch(Exception e){
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in restoreCategoryValue() in CourseCategoryService",e);
			}
		}
		return catrestore;
	}

	/**
	 * @author muniyarasu_a
	 * Method to check duplicate category
	 * @param coursecategoryname
	 */
	public List<?> categorycheck(String coursecategoryname)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class)
					.add(Restrictions.eq("this.coursecategoryname",coursecategoryname))
					.add(Restrictions.eq("this.coursecategorystatus", "A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursecategorystatus").as("coursecategorystatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in categorycheck() in CourseCategoryService",e);
			}
		}
		return Query;
	}
	
	public List<?> coursevailablecheck(int coursecategoryid)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class)
					.createAlias("this.courses", "courses")
					.add(Restrictions.eq("this.coursecategoryid",coursecategoryid))
					.add(Restrictions.eq("this.coursecategorystatus", "A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategorystatus").as("coursecategorystatus"))
							.add(Projections.property("courses.courseid").as("courseid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			if(CourseCategoryServicelogger.isInfoEnabled())	{
				CourseCategoryServicelogger.error("error in coursevailablecheck() in CourseCategoryService",e);
			}
		}
		return Query;
	}

	
}