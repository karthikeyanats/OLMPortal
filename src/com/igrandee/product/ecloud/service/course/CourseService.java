package com.igrandee.product.ecloud.service.course;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;

/**
 *@author rubykannan_r
 *Service class for searchbox 
 * */

@Service
@Scope("prototype")
public class CourseService extends GenericEntityService<Course, Integer> {
	private static Logger Courselogger = Logger.getLogger(Course.class);

	public List<?> searchCourse(String organizationid,String keyword) {
		List<?> searchlist =null;
		String[] keywords=keyword.split(" ");
		try	{		
			Criteria Query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("orgperson.personid", "person")
					.add(Restrictions.eq("this.coursestatus","A"))
					.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			for(int i=0;i<keywords.length;i++){
				Query.add(Restrictions.or(Restrictions.or(Restrictions.like("coursegoal", keywords[i], MatchMode.ANYWHERE), 
						Restrictions.like("coursetitle", keywords[i], MatchMode.ANYWHERE)), 
						Restrictions.or(Restrictions.like("person.firstName",  keywords[i], MatchMode.ANYWHERE),
								Restrictions.like("coursedescription", keywords[i], MatchMode.ANYWHERE))));
			}  						
			searchlist=Query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("person.firstName").as("author"))
					.add(Projections.property("this.coursestatus").as("coursestatus")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Courselogger.isInfoEnabled())	{
				Courselogger.error("error in searchCourse() in CourseService",e);
			}
		}
		return searchlist;
	}
	
	public List<?> mastersearchCourse(String organizationid,String keyword,String personid) {
		List<?> searchlist =null;
		String[] keywords=keyword.split(" ");
		try	{		
			Criteria query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN) 
					.createAlias("this.courseenrollments", "courseenrollments",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.or(Restrictions.eq("courseenrollments.courseenrollmentstatus","A"), 
									Restrictions.eq("courseenrollments.courseenrollmentstatus","C")))
									.createAlias("this.orgperson", "orgperson")
									.createAlias("orgperson.organizationid", "organizationid")
									.createAlias("orgperson.personid", "person")
									.createAlias("this.prices", "prices")
									.createAlias("this.coursecategory", "coursecategory")
									.add(Restrictions.eq("this.coursestatus","A")) 
									.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(personid)));
			for(int i=0;i<keywords.length;i++){
				query.add(Restrictions.or(Restrictions.or(Restrictions.like("coursegoal", keywords[i], MatchMode.ANYWHERE), 
						Restrictions.like("coursetitle", keywords[i], MatchMode.ANYWHERE)), 
						Restrictions.or(Restrictions.like("person.firstName",  keywords[i], MatchMode.ANYWHERE),
								Restrictions.like("coursedescription", keywords[i], MatchMode.ANYWHERE))));
			}
			if(organizationid==null || organizationid.equalsIgnoreCase("null") || organizationid.equals("null") ){

			}else{
				query.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			}  
			searchlist=query.setProjection(Projections.projectionList()
					.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
					.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))
					.add(Projections.property("person.firstName").as("firstName"))
					.add(Projections.property("person.lastName").as("lastName"))
					.add(Projections.property("person.personphotourl").as("authorphoto"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("courseenrollments.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("courseratings.courseratingid").as("courseratingid"))
					.add(Projections.property("courseratings.courserating").as("courserating"))
					.add(Projections.property("organizationid.orgstatus").as("orgstatus")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			if(Courselogger.isInfoEnabled())	{
				Courselogger.error("error in ssearchCourse() in CourseService",e);
			}
		}
		return searchlist;
	}
	
	
	
	public List<?> ssearchCourse(String organizationid,String keyword) {
		List<?> searchlist =null;
		String[] keywords=keyword.split(" ");
		try	{		
			Criteria query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN) 
					.createAlias("this.courseenrollments", "courseenrollments",
							org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.or(Restrictions.eq("courseenrollments.courseenrollmentstatus","A"), 
									Restrictions.eq("courseenrollments.courseenrollmentstatus","C")))
									.createAlias("this.orgperson", "orgperson")
									.createAlias("orgperson.organizationid", "organizationid")
									.createAlias("orgperson.personid", "person")
									.createAlias("this.prices", "prices")
									.createAlias("this.coursecategory", "coursecategory")
									.add(Restrictions.eq("this.coursestatus","A"));
			for(int i=0;i<keywords.length;i++){
				query.add(Restrictions.or(Restrictions.or(Restrictions.like("coursegoal", keywords[i], MatchMode.ANYWHERE), 
						Restrictions.like("coursetitle", keywords[i], MatchMode.ANYWHERE)), 
						Restrictions.or(Restrictions.like("person.firstName",  keywords[i], MatchMode.ANYWHERE),
								Restrictions.like("coursedescription", keywords[i], MatchMode.ANYWHERE))));
			}
			if(organizationid==null || organizationid.equalsIgnoreCase("null") || organizationid.equals("null") ){

			}else{
				query.add(Restrictions.eq("organizationid.organizationid",Long.parseLong(organizationid)));
			}  
			searchlist=query.setProjection(Projections.projectionList()
					.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
					.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))
					.add(Projections.property("person.firstName").as("firstName"))
					.add(Projections.property("person.lastName").as("lastName"))
					.add(Projections.property("person.personphotourl").as("authorphoto"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("courseenrollments.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("courseratings.courseratingid").as("courseratingid"))
					.add(Projections.property("courseratings.courserating").as("courserating")))

					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			if(Courselogger.isInfoEnabled())	{
				Courselogger.error("error in ssearchCourse() in CourseService",e);
			}
		}
		return searchlist;
	}

	public List<?> LogoFolder(String courseid) {
		List<?> LogoFolderlist =null;		
		try	{			
			Criteria Query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.coursecategory", "coursecategory")					
					.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)));

			LogoFolderlist=Query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Courselogger.isInfoEnabled())	{
				Courselogger.error("error in LogoFolder() in CourseService",e);
			}
		}
		return LogoFolderlist;
	}


	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}



	public String updateCourseLogo(String courseid,String logoPath){
		try{
			String hql = "UPDATE Course set courselogo = :courselogo "  + 
					"WHERE courseid = :courseid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courselogo", logoPath);
			query.setParameter("courseid", Integer.parseInt(courseid));
			String result = Integer.toString(query.executeUpdate());
			return result;
		}
		catch(Exception e){
			if(Courselogger.isInfoEnabled())	{
				Courselogger.error("error in updateCourseLogo() in CourseService",e);
			}
			return "null";
		}
	}
}