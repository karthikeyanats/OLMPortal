package com.igrandee.product.ecloud.service.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;


@Service
@Named
@Scope("prototype")
public class CourseUserService extends GenericEntityService<Course, Integer>{

	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return Course.class;
	}

	public List<?> loadCourseUsersList(String status,String courseid){
		List<?> courseUsersList = null;
		try{
			courseUsersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson") 
					.createAlias("orgperson.personid", "personid") 
					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("this.courseenrollmentstatus",status))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("personid.firstName").as("firstName"))
							.add(Projections.property("personid.lastName").as("lastName"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.groupProperty("orgperson.orgpersonid"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
		return courseUsersList;	
	}
	
	public List<?> getRemainingUsers(String courseid,String orgid){
		List<?> personlist = null;
		try{
			List<?> query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.in("this.courseenrollmentstatus",new String[] {"A","B","W","C"}))
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(orgid))) 

					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			ArrayList<Integer> arry = new ArrayList<Integer>();
			if(query.size()!=0) {
				for(int i=0;i<query.size();i++){
					Map queryMap = (Map)query.get(i); 		 
					String orgpersonid = ""+queryMap.get("orgpersonid");
					arry.add(Integer.parseInt(orgpersonid)); 
				}
			} 

			Criteria personCreteria = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)
					.createAlias("this.organizationid", "organization")
					.createAlias("this.personid", "personid")  
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personallocations", "personallocations", JoinType.LEFT_OUTER_JOIN) 
					.createAlias("personallocations.role", "role")
					.add(Restrictions.ne("role.rolename", "admin")).add(Restrictions.eq("organization.organizationid",Long.parseLong(orgid)))
					; 	

			if(arry.isEmpty()){

			} else {
				personCreteria.add(Restrictions.not(Restrictions.in("this.orgpersonid", arry)));
			}
			personlist=	personCreteria.setProjection(Projections.projectionList()
					.add(Projections.property("personid.firstName").as("firstName"))
					.add(Projections.property("personid.lastName").as("lastName"))
					.add(Projections.property("personid.middleName").as("middleName"))
					.add(Projections.property("personid.personphotourl").as("personphotourl"))
					.add(Projections.property("email.userid").as("emailaddress"))
					.add(Projections.property("this.orgpersonid").as("personid")) 
					.add(Projections.groupProperty("this.orgpersonid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return personlist;
	}	 
	
	public String updateCourseEnrollStatus(String courseenrollmentid,String courseenrollmentstatus){
		try{
			String hql = "UPDATE Courseenrollment set courseenrollmentstatus = :courseenrollmentstatus "  + 
					"WHERE courseenrollmentid = :courseenrollmentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courseenrollmentstatus", courseenrollmentstatus);
			query.setParameter("courseenrollmentid", Integer.parseInt(courseenrollmentid));
			Integer result = query.executeUpdate();
			if(result.SIZE>0){
				return "OK";
			}
			else{
				return "NOTOK";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "NOTOK";
		}		
	}
}
