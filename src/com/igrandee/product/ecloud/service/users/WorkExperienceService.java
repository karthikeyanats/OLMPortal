package com.igrandee.product.ecloud.service.users;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.person.model.Workexperience;

@Service
@Scope("prototype")
public class WorkExperienceService extends GenericEntityService<Workexperience, Integer> {

	@Override
	protected Class<Workexperience> entityClass() {
		// TODO Auto-generated method stub
		return Workexperience.class;
	}

	public List<?> getWorkExperience(long personid){
	
		List<?> expriencelist=null;
	try{
		
		expriencelist= getCurrentSession().createCriteria(Workexperience.class)
			   		.createAlias("person", "person",JoinType.RIGHT_OUTER_JOIN)
			   		.createAlias("contactinfo", "contactinfo")
			   		.createAlias("contactinfo.urls", "urls")
			   		.createAlias("person.contactinfo", "contactdetail")
			   		.createAlias("contactdetail.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactdetail.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			   		.add(Restrictions.eq("person.personid", personid))
			   		.setProjection(Projections.projectionList()
			   				.add(Projections.property("workexperienceid").as("workexperienceid"))
			   				.add(Projections.property("designation").as("designation"))
			   				.add(Projections.property("organizationname").as("organization"))
			   				.add(Projections.property("award").as("award"))
			   				.add(Projections.property("natureofwork").as("aboutauthor"))
			   				.add(Projections.property("urls.url").as("url"))
			   				.add(Projections.property("person.personphotourl").as("personphoto"))
			   				.add(Projections.property("person.firstName").as("authorname"))
			   				.add(Projections.property("phone.number").as("number"))
			   				.add(Projections.property("email.userid").as("emailid"))
			   				)
			   				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return expriencelist;
	   
	}
	
	
	public List<?> getCourseDetails(int orgpersonid,Long orgid){
		   return	getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
				   .createAlias("this.orgperson", "orgperson")
				   .createAlias("orgperson.organizationid", "organization")
					.createAlias("orgperson.personid", "personobj")  
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.courseratings", "courseratings",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					
 					
 					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
 					.add(Restrictions.eq("organization.organizationid",orgid))
 					.add(Restrictions.eq("this.coursestatus","A"))
					.setProjection(Projections.projectionList()
							
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.groupProperty("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.coursesubtitle").as("coursesubtitle"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("this.instructoinallevel").as("instructoinallevel"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.coursegoal").as("coursegoal"))

							.add(Projections.property("personobj.firstName").as("firstName"))
							.add(Projections.property("personobj.lastName").as("lastName"))
							.add(Projections.property("personobj.eduqualification").as("eduqualification"))
							.add(Projections.property("prices.priceid").as("priceid"))
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("courseratings.courserating").as("courserating")))
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setMaxResults(3).list(); 
							
 		}
	 
	public int getWorkExperienceId(long personid){
		try{
		 return	Integer.parseInt(getCurrentSession().createCriteria(Workexperience.class)
				 		.createAlias("person", "person")
				 		.createAlias("contactinfo", "contactinfo")
				 		.add(Restrictions.eq("person.personid", personid))
				 			.setProjection(Projections.projectionList()
			   				.add(Projections.property("workexperienceid").as("workexperienceid")))
			   				.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		}
		catch(Exception e){
			return 0;
		}
			   				
	}
	
	
}
