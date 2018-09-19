package com.igrandee.product.ecloud.service.admin;

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
import com.igrandee.product.ecloud.model.Course;

@Service
@Named
@Scope("prototype")
public class AdminDashBoardService extends GenericEntityService<Course, Serializable> {
	private static Logger AdminDashBoardServicelogger = Logger.getLogger(AdminDashBoardService.class);

	@Override
	protected Class<Course> entityClass() {
		// TODO Auto-generated method stub
		return Course.class;
	}

	/**
	 * @author seenivasagan_p
	 * Method to return all course for listing the courses count in dashboard
	 * @return coursesCountList
	 */
	public List<?> getAllCoursesList(int orgpersonid){
		List<?> coursesCountList = null;
		try{
			coursesCountList =
					getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
                   // .add(Restrictions.eq("this.coursestatus", "A"))					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("this.coursestatus").as("coursestatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getAllCoursesList() in AdminDashBoardService",e);
			}
		}
		return coursesCountList;
	}

	/**
	 * @author seenivasagan_p
	 * Method to list the enrolled person to list the person count in dashboard
	 * @return coursesEnrollList
	 */
	public List<?> getAllEnrollmentList(long organizationid){
		List<?> coursesEnrollList = null;
		try{
			coursesEnrollList =	getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.course", "course")
					.createAlias("orgperson.organizationid", "organization")
					.createAlias("orgperson.personid", "person")
					.add(Restrictions.eq("organization.organizationid",organizationid))
				    .add(Restrictions.eq("this.courseenrollmentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.property("person.firstName").as("firstname"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getAllEnrollmentList() in AdminDashBoardService",e);
			}
		}
		return coursesEnrollList;
	}

	/**
	 * @author seenivasagan_p
	 * Method to list the certificates that are waiting for approval
	 * @return coursesCertificatesList
	 */
	public List<?> getPendingCertificatesList(int orgpersonid){
		List<?> coursesCertificatesList = null;
		try{
			coursesCertificatesList =	
					getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecertificate.class)
					.createAlias("this.courseenrollment", "courseenrollment")
					.createAlias("courseenrollment.course", "course")
					.createAlias("course.orgperson", "orgperson")
					.add(Restrictions.eq("this.coursecertificatestatus","P"))
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					
					.setProjection(Projections.projectionList()
							.add(Projections.count("this.coursecertificateid").as("pendingcertificaterequests"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getPendingCertificatesList() in AdminDashBoardService",e);
			}
		}
		return coursesCertificatesList;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the top categories with users count and students count
	 * @return categorywiseUsersList
	 */
	public List<?> getCategorywiseUsers(int orgpersonid){
		List<?> categorywiseUsersList = null;
		try{
			categorywiseUsersList=getCurrentSession().createSQLQuery("select a.coursecategoryid,a.coursecategoryname," +
					"count(b.coursecategoryid) as coursescount," +
					"(select count(*) from courseenrollment x,course x1 where x1.courseid=x.courseid " +
					"and x1.coursecategoryid=a.coursecategoryid and x1.personid='"+orgpersonid+"' and x.courseenrollmentstatus in ('A','B','C')) as studentcount " +
					"from coursecategory a left outer join course b " +
					"on a.coursecategoryid=b.coursecategoryid where a.coursecategorystatus='A' " +
					"and b.coursestatus='A' and b.personid='"+orgpersonid+"'group by b.coursecategoryid " +
					"order by studentcount desc limit 5")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getCategorywiseUsers() in AdminDashBoardService",e);
			}
		}
		return categorywiseUsersList;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the top categories with users count and students count
	 * @return coursewiseUsersList
	 */
	public List<?> getCoursewiseUsers(int personid){
		List<?> coursewiseUsersList = null;
		try{
			coursewiseUsersList=getCurrentSession().createSQLQuery("select b.courseid,b.coursetitle,p.price," +
					"count(c.courseenrollmentid) as studentcount from course b ,courseenrollment c,price p " +
					"where b.personid='"+personid+"'and c.courseid=b.courseid  and p.courseid=b.courseid and b.coursestatus='A' and " +
					"c.courseenrollmentstatus in ('A','B','C') group by b.courseid order by studentcount desc limit 5")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getCoursewiseUsers() in AdminDashBoardService",e);
			}
		}
		return coursewiseUsersList;
	}

	/**
	 * @author seenivasagan_p
	 * Method the get the sum of amount collected in the current week
	 * @return weeklypayment
	 */
	public List<?> getWeeklyPayments(int orgpersonid){
		List<?> weeklypayment=null;
		try{
			weeklypayment=getCurrentSession().createSQLQuery("select sum(b.price) as weekamount " +
					"from payment a, price b,course c where a.priceid=b.priceid and a.paymentstatus='A' " +
					"and c.courseid=b.courseid and c.personid='"+orgpersonid+"'" +
					"and week(a.paymentdate)=week(now())")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getWeeklyPayments() in AdminDashBoardService",e);
			}
		}
		return weeklypayment;
	}

	/**
	 * @author seenivasagan_p
	 * Method the get the sum of amount collected in the current month
	 * @return monthlypayment
	 */
	public List<?> getMonthlyPayments(int orgpersonid){
		List<?> monthlypayment=null;
		try{
			monthlypayment=getCurrentSession().createSQLQuery("select sum(b.price) as monthamount " +
					"from payment a, price b,course c " +
					"where a.priceid=b.priceid and a.paymentstatus='A' " +
					"and c.courseid=b.courseid and c.personid='"+orgpersonid+"'" +
					"and monthname(a.paymentdate)=monthname(now())")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getMonthlyPayments() in AdminDashBoardService",e);
			}
		}
		return monthlypayment;
	}

	/**
	 * @author seenivasagan_p
	 * Method the get the sum of amount collected in the current year
	 * @return yearlypayment
	 */
	public List<?> getYearlyPayments(int orgpersonid){
		List<?> yearlypayment=null;
		try{
			yearlypayment=getCurrentSession().createSQLQuery("select sum(b.price) as yearamount " +
					"from payment a,price b ,course c where " +
					"a.priceid=b.priceid and a.paymentstatus='A' " +
					"and c.courseid=b.courseid and c.personid='"+orgpersonid+"'" +
					"and  year(a.paymentdate)=year(now())")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(AdminDashBoardServicelogger.isInfoEnabled())	{
				AdminDashBoardServicelogger.error("error in getYearlyPayments() in AdminDashBoardService",e);
			}
		}
		return yearlypayment;
	}
}