package com.igrandee.product.ecloud.service.course;

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
import com.igrandee.product.ecloud.model.Courserating;

@Service
@Named
@Scope("prototype")
public class CourseRatingService extends GenericEntityService<Courserating, Serializable>{
	private static Logger CourseRatingServicelogger = Logger.getLogger(CourseRatingService.class);

	@Override
	protected Class<Courserating> entityClass() {
		// TODO Auto-generated method stub
		return Courserating.class;
	}
	
	public List<?> ratingList(int courseid){
		
		List<?> courseRatingsList = null;
		try{
			courseRatingsList =	getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courserating.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "ratedperson")					
					.createAlias("this.course", "course")
					.add(Restrictions.eq("course.courseid",courseid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("ratedperson.firstName").as("raterfirstname"))
							.add(Projections.property("ratedperson.lastName").as("raterlastname"))
							.add(Projections.property("ratedperson.personphotourl").as("photo"))
							.add(Projections.property("this.courserating").as("courserating"))
							.add(Projections.property("this.courseratingdescription").as("ratingdescription"))
							.add(Projections.property("this.ratingdate").as("reviewdate"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseRatingServicelogger.isInfoEnabled())	{
				CourseRatingServicelogger.error("error in getAllEnrollmentList() in CourseRatingService",e);
			}
		}
		return courseRatingsList;
	}
	
	public List<?> myratingList(int courseid,int orgpersonid){
		List<?> courseRatingsList = null;
		try{
			courseRatingsList =	getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courserating.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.personid", "ratedperson")					
					.createAlias("this.course", "course")
					.add(Restrictions.eq("course.courseid",courseid))
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("ratedperson.firstName").as("raterfirstname"))
							.add(Projections.property("ratedperson.lastName").as("raterlastname"))
							.add(Projections.property("this.courserating").as("courserating"))
							.add(Projections.property("this.courseratingdescription").as("ratingdescription"))
							
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseRatingServicelogger.isInfoEnabled())	{
				CourseRatingServicelogger.error("error in getAllEnrollmentList() in CourseRatingService",e);
			}
		}
		return courseRatingsList;
	}
	public List<?> getcoursetype(){
		List<?> coursetypeList=null;
		try{
			coursetypeList=getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Reviewtype.class)
							.setProjection(Projections.projectionList()
							.add(Projections.property("reviewtype").as("reviewtype"))
							.add(Projections.property("id").as("reviewid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
							
		}catch(Exception e){
			e.printStackTrace();
		}
		return coursetypeList;
	}


}
