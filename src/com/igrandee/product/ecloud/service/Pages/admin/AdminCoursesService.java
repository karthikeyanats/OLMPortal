package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;

@Service
@Named
public class AdminCoursesService  extends GenericEntityService<Course, Integer>{

	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return Course.class;
	}

	public List<?> loadAuthorCoursesCountForAdmin(String orgid,String orgpersonid){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createSQLQuery("select count(courseid) as allcourses," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='A' " +
			"and a.personid <>"+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as approved," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='P' " +
			"and a.personid <>"+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as published," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='D' " +
			"and a.personid <>"+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as draft," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='T' " +
			"and a.personid <>"+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as trashed from course")
			.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> loadAuthorCoursesForAdmin(String orgid,String orgpersonid,String coursestatus){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createSQLQuery("select a.courseid,a.coursetitle," +
					"a.coursestatus,b.orgpersonid,c.firstName,c.lastName," +
					"e.userid as authoremailid from course a,orgperson b,person c,contactinfo d,email e " +
					"where a.personid not in ("+orgpersonid+") and a.coursestatus='"+coursestatus+"' " +
					"and a.personid=b.orgpersonid and b.organizationid='"+orgid+"' and b.orgpersonstatus='A' " +
					"and b.personid=c.personid and c.personstatus='A' and c.contactinfoid=d.contactinfoid " +
					"and d.contactinfoid=e.contactinfoid and e.emailstatus='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}

	public List<?> loadCoursesCountForAdmin(String orgid,String orgpersonid){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createSQLQuery("select count(courseid) as allcourses," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='A' " +
			"and a.personid ="+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as approved," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='P' " +
			"and a.personid ="+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as published," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='D' " +
			"and a.personid ="+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as draft," +
			"(select count(a.courseid) from course a,orgperson b  where a.coursestatus='T' " +
			"and a.personid ="+orgpersonid+" and a.personid=b.orgpersonid and b.orgpersonstatus='A' " +
			"and b.organizationid='"+orgid+"') as trashed from course")
			.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> loadCoursesForAdmin(String orgid,String orgpersonid,String coursestatus){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createSQLQuery("select a.courseid,a.coursetitle," +
					"a.coursestatus,b.orgpersonid,concat(c.firstName, ' ',c.lastName) as authorname " +
					"from course a,orgperson b,person c where a.personid ="+orgpersonid+" " +
					"and a.coursestatus='"+coursestatus+"' and a.personid=b.orgpersonid and " +
					"b.organizationid='"+orgid+"' and b.orgpersonstatus='A' " +
					"and b.personid=c.personid and c.personstatus='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
}
