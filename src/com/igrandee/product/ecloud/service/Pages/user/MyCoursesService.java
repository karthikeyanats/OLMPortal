package com.igrandee.product.ecloud.service.Pages.user;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;

@Service
@Named
@Scope("prototype")
public class MyCoursesService  extends GenericEntityService<Courseenrollment, Integer>{

	@Override
	protected Class<Courseenrollment> entityClass() {
		// TODO Auto-generated method stub
		return Courseenrollment.class;
	}

	public List<?> loadMyCoursesCount(String orgpersonid,String orgid){
		List<?> countList =null;
		try	{
			countList = getCurrentSession().createSQLQuery("select count(courseenrollmentid) as total, " +
					"(select count(courseenrollmentid) from courseenrollment where courseenrollmentstatus='A' and personid='"+orgpersonid+"') as learning, " +
					"(select count(courseenrollmentid) from courseenrollment where courseenrollmentstatus='C' and personid='"+orgpersonid+"') as completed, " +
					"(select count(courseenrollmentid) from courseenrollment where courseenrollmentstatus='W' and personid='"+orgpersonid+"') as wishlisted, " +
					"(select count(courseenrollmentid) from courseenrollment where courseenrollmentstatus='B' and personid='"+orgpersonid+"') as blocked , " +
					"(select count(a.courseid) from course a,orgperson b where a.personid='"+orgpersonid+"' and coursestatus='D' " +
							"and a.personid=b.orgpersonid and b.orgpersonstatus='A' and b.organizationid='"+orgid+"') as draftcourses, " +
					"(select count(a.courseid) from course a,orgperson b where a.personid='"+orgpersonid+"' and coursestatus='P' " +
							"and a.personid=b.orgpersonid and b.orgpersonstatus='A' and b.organizationid='"+orgid+"') as publishedcourses, " +
					"(select count(a.courseid) from course a,orgperson b where a.personid='"+orgpersonid+"' and coursestatus='A' " +
							"and a.personid=b.orgpersonid and b.orgpersonstatus='A' and b.organizationid='"+orgid+"') as approvedcourses, " +
					"(select count(a.courseid) from course a,orgperson b where a.personid='"+orgpersonid+"' and coursestatus='T' " +
							"and a.personid=b.orgpersonid and b.orgpersonstatus='A' and b.organizationid='"+orgid+"') as trashedcourses " +
					"from courseenrollment where personid='"+orgpersonid+"'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return countList;
	}

	public List<?> getTeachingCourses(String coursestatus,Long personid){
		List<?> teachingCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")	
					.createAlias("orgperson.personid", "author")					
					.add(Restrictions.eq("author.personid",personid)).
					add(Restrictions.eq("this.coursestatus",coursestatus));

			teachingCourses=query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("this.coursestatus").as("coursestatus"))
					.add(Projections.property("organizationid.orgname").as("orgname"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("author.firstName").as("firstName"))
					.add(Projections.property("author.lastName").as("lastName"))
					.add(Projections.property("prices.price").as("price")))							
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return teachingCourses;
	}
	
	public List<?> learningCourses(int orgpersonid,String status){
		List<?> individualCourses=null;
		Criteria query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.coursecategory", "coursecategory")
					.createAlias("coursecategory.board", "board")

					.createAlias("course.orgperson", "author")
					.createAlias("author.personid","authorperson")

					.createAlias("course.prices", "prices")
					.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					.add(Restrictions.eq("this.courseenrollmentstatus",status));

			individualCourses=	query.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
					.add(Projections.property("this.courseprogress").as("courseprogress"))							
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.coursedescription").as("coursedescription"))
					.add(Projections.property("course.courselogo").as("courselogo"))
					.add(Projections.property("course.coursestatus").as("coursestatus"))
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("authorperson.firstName").as("firstName"))
					.add(Projections.property("authorperson.lastName").as("lastName"))

					.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
					.add(Projections.property("board.boardname").as("boardname"))

					).addOrder(Order.desc("courseenrollmentid")).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return individualCourses;
	}
}
