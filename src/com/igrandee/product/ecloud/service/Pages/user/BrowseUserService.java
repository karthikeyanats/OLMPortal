package com.igrandee.product.ecloud.service.Pages.user;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;

@Service
@Named
@Scope("prototype")
public class BrowseUserService extends GenericEntityService<Courseenrollment, Integer>{

	private static Logger BrowseUserServicelogger = Logger.getLogger(BrowseUserService.class);
	
	@Override
	protected Class<Courseenrollment> entityClass() {
		// TODO Auto-generated method stub
		return Courseenrollment.class;
	}
	
	public List<?> getUserCategoryCourses(String orgPersonId, String courseCategoryId, String organizationId){
		List<?> returnList = null;
		Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Courseenrollment.class);  
			query.createAlias("this.course", "course", JoinType.LEFT_OUTER_JOIN) 
				 .createAlias("course.coursecategory", "coursecategory", JoinType.LEFT_OUTER_JOIN) 
			     .createAlias("this.orgperson", "userOrgPerson", JoinType.LEFT_OUTER_JOIN)  
			     .createAlias("userOrgPerson.organizationid", "organizationid" ) ;
			
			query.add(Restrictions.eq("course.coursestatus", "A"))
				 .add(Restrictions.eq("coursecategory.coursecategorystatus", "A")) 
				 .add(Restrictions.eq("userOrgPerson.orgpersonstatus", 'A'));
			   
			query.add(Restrictions.in("organizationid.orgstatus", new Character[] {'A','M'} ))
				 .add(Restrictions.eq("coursecategory.coursecategoryid", Integer.parseInt(courseCategoryId )))
				 .add(Restrictions.eq("organizationid.organizationid", Long.parseLong(organizationId )))
				 .add(Restrictions.eq("userOrgPerson.orgpersonid", Integer.parseInt(orgPersonId) ));
			
			
			ProjectionList projectionList = Projections.projectionList(); 
			
			projectionList.add(Projections.property("course.courseid").as("courseid")) 
						  .add(Projections.property("this.courseenrollmentid").as("courseenrollmentid")) 
						  .add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"));
			
			
			query.setProjection(projectionList);
			
			returnList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
		} catch (Exception e) {
			if(BrowseUserServicelogger.isInfoEnabled()){ 
				BrowseUserServicelogger.error("error in getUserCategoryCourses() in BrowseUserService ",e);
			}
		}
		return returnList;
	}

}
