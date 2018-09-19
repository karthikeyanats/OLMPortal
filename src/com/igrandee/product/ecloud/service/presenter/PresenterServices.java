package com.igrandee.product.ecloud.service.presenter;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Named
@Scope("prototype")
public class PresenterServices extends GenericEntityService<Coursecategory, Serializable>{

	private static Logger PresenterServiceslogger = Logger.getLogger(PresenterServices.class);
	@Override
	protected Class<Coursecategory> entityClass() {
 		return Coursecategory.class;
	}	
	
	/**
	 * @author seenivasagan_p
	 * @param coursecategorystatus
	 * @return all categories
	 */
	public List<?> getCategories(String coursecategorystatus) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class)				
					.add(Restrictions.eq("this.coursecategorystatus",coursecategorystatus))					
					.setProjection(Projections.projectionList()							
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(PresenterServiceslogger.isInfoEnabled())	{
				PresenterServiceslogger.error("error in getCategories() in PresenterServices ",e);
			}
		}
		return query;
	}
	
	/**
	 * @author seenivasagan_p
	 * To Get courses for particular category
	 * @param coursecategoryid
	 * @return courses respective to the passed category id
	 */
	public List<?> getCategoryWiseCourses(int coursecategoryid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursecategory", "coursecategory",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("coursecategory.coursecategoryid",coursecategoryid))
					.add(Restrictions.eq("this.coursestatus","D"))
					.setProjection(Projections.projectionList()					
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursetitle").as("coursetitle")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(PresenterServiceslogger.isInfoEnabled())	{
				PresenterServiceslogger.error("error in getCategoryWiseCourses() in PresenterServices",e);
			}
		}
		return query;
	}
	
	/**
	 * @author seenivasagan_p
	 * To Get courses for particular category
	 * @param coursecategoryid
	 * @return courses respective to the passed category id
	 */
	public List<?> getCoursesSections(int courseid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursesections", "coursesections",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.courseid",courseid))
					.add(Restrictions.eq("coursesections.coursesectionstatus","A"))
					.setProjection(Projections.projectionList()					
							.add(Projections.property("coursesections.coursesectionid").as("coursesectionid"))
							.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(PresenterServiceslogger.isInfoEnabled())	{
				PresenterServiceslogger.error("error in getCoursesSections() in PresenterServices",e);
			}
		}
		return query;
	}
  
}
