package com.igrandee.product.ecloud.service.course;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;

@Service
@Named
@Scope("prototype")
public class CourseEnrollmentService extends GenericEntityService<Courseenrollment, Integer>{
	private static Logger CourseEnrollmentServicelogger = Logger.getLogger(CourseEnrollmentService.class);

	@Override
	protected Class<Courseenrollment> entityClass() {
 		return Courseenrollment.class;
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to get the list of invoices
	 * @param invoiceid
	 * @return
	 */
	public List<?> getInvoiceListParticulars(Integer invoiceid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Payment.class)

					.createAlias("this.courseenrollment", "courseenrollment" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.paymentdetails", "paymentdetails" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.price", "priceobj", JoinType.LEFT_OUTER_JOIN)
					.createAlias("priceobj.course", "course", JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "orgperson", JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgperson.personid", "personid", JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.paymentstatus","A"))
					.add(Restrictions.eq("this.paymentid", invoiceid))
					.setProjection(Projections.projectionList()

							.add(Projections.property("courseenrollment.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.paymentid").as("paymentid"))
							.add(Projections.property("this.paymenttype").as("paymenttype"))
							.add(Projections.property("this.paymentdescription").as("paymentdescription"))
							.add(Projections.property("this.paymentamount").as("paymentamount"))
							.add(Projections.property("this.paymentdate").as("paymentdate"))
							.add(Projections.property("priceobj.priceid").as("priceid"))
							.add(Projections.property("priceobj.price").as("price"))
							.add(Projections.property("paymentdetails.paymentdetailsid").as("paymentdetailsid"))
							.add(Projections.property("paymentdetails.bankname").as("bankname"))
							.add(Projections.property("paymentdetails.banklocation").as("banklocation"))
							.add(Projections.property("paymentdetails.expirydate").as("expirydate"))
							.add(Projections.property("paymentdetails.chequenumber").as("chequedate"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.courseid").as("courseid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getInvoiceListParticulars() in CourseEnrollmentService",e);
			}
		}
		return query;
	}

	/**
	 * @author seenivasagan_p
	 * Method to update course enrollment
	 * @param courseenrollmentid
	 * @return
	 */
	public Integer updateCourseenrollment(int courseenrollmentid){
		try	{
			java.util.Date date = new java.util.Date();

			String hql = "UPDATE Courseenrollment set courseenrollmentstatus = :courseenrollmentstatus " +
					", enrolleddate = :enrolleddate "+ 
					"WHERE courseenrollmentid = :courseenrollmentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courseenrollmentstatus", "A");
			query.setParameter("enrolleddate", new Timestamp(date.getTime()));
			query.setParameter("courseenrollmentid", courseenrollmentid);
			Integer result = query.executeUpdate();
		}
		catch(Exception e){
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in updateCourseenrollment() in CourseEnrollmentService",e);
			}
		}
		return 1;
	}

	/**
	 * @author seenivasagan_p
	 * Method to update course enrollment
	 * @param courseenrollmentid
	 * @param status
	 * @return
	 */
	public Integer updateCourseenrollment(int courseenrollmentid,String status){
		try	{
			java.util.Date date = new java.util.Date();

			String hql = "UPDATE Courseenrollment set courseenrollmentstatus = :courseenrollmentstatus " +
					", enrolleddate = :enrolleddate "+ 
					"WHERE courseenrollmentid = :courseenrollmentid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("courseenrollmentstatus", status);
			query.setParameter("enrolleddate", new Timestamp(date.getTime()));
			query.setParameter("courseenrollmentid", courseenrollmentid);
			Integer result = query.executeUpdate();
		}
		catch(Exception e)	{
			if(CourseEnrollmentServicelogger.isInfoEnabled())	{
				CourseEnrollmentServicelogger.error("error in updateCourseenrollment() in CourseEnrollmentService",e);
			}
		}
		return 1;
	} 

	/**
	 * @author seenivasagan_p
	 * Method to update the course enrollment status
	 * @param courseenrollmentid
	 * @return
	 */
	/*user block status is set to 'B' from 'T' */
	public Integer updateCourseenrollmentStatus(int courseenrollmentid){
 		try	{ 
			courseenrollmentid=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Courseenrollment set courseenrollmentstatus='B' where courseenrollmentid="+courseenrollmentid +" ").executeUpdate();
 		}
		catch(Exception e){
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in updateCourseenrollmentStatus() in CourseEnrollmentService",e);
			}
		}
		return courseenrollmentid;
	}

	/**
	 * @author seenivasagan_p
	 * Method to approve the certificate request
	 * @param paymentid
	 * @param status
	 * @return
	 */
	public Integer updateCoursePaymentStatus(int paymentid,String status,String orderno){
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Payment set paymentstatus='"+status+"',orderno='"+orderno+"' where paymentid="+paymentid +" ").executeUpdate();
		}
		catch(Exception e)	{
			if(CourseEnrollmentServicelogger.isInfoEnabled())	{
				CourseEnrollmentServicelogger.error("error in updateCoursePaymentStatus() in CourseEnrollmentService",e);
			}
		}
		return 1;
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to get the list of invoices
	 * @param invoiceid
	 * @return
	 */
	public List<?> checkEnrollmentStatus(int courseid,int personid)	{
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)

					.createAlias("this.course", "course" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson" , JoinType.LEFT_OUTER_JOIN)
							
					.add(Restrictions.eq("course.courseid", courseid))
					.add(Restrictions.eq("orgperson.orgpersonid", personid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getInvoiceListParticulars() in CourseEnrollmentService",e);
			}
		}

		return query;
	}
	
	/**
	 * @author raja_r
	 * Method to get the list of users
	 * @param courseid
	 * @return List
	 */
	public List<?> getCourseenrollment(String courseid){
		List<?> query = null;
		try{
			  query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
				  
						.createAlias("this.course", "course")
						.createAlias("this.orgperson", "orgperson") 
						.createAlias("orgperson.personid", "personid") 
						.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
						.add(Restrictions.eq("this.courseenrollmentstatus","A"))
							.setProjection(Projections.projectionList()
									.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
									.add(Projections.property("personid.firstName").as("firstName"))
									.add(Projections.property("personid.lastName").as("lastName"))
									.add(Projections.property("personid.middleName").as("middleName"))
									.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
									.add(Projections.groupProperty("orgperson.orgpersonid"))) 
								.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e) {
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getCourseenrollment() in CourseEnrollmentService",e);
			}
		} 
		return query;
	}
	
	
	/**
	 * @author raja_r
	 * Method to get the list of blockedusers
	 * @param courseid
	 * @return List
	 */
	public List<?> getBlockedUser(String courseid){
		List<?> query = null; 
		try{
			  query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
				  
						.createAlias("this.course", "course")
						.createAlias("this.orgperson", "orgperson") 
						.createAlias("orgperson.personid", "personid") 
						.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
						.add(Restrictions.eq("this.courseenrollmentstatus","B"))
							.setProjection(Projections.projectionList()
									.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
									.add(Projections.property("personid.firstName").as("firstName"))
									.add(Projections.property("personid.lastName").as("lastName"))
									.add(Projections.property("personid.middleName").as("middleName"))
									.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
									.add(Projections.groupProperty("orgperson.orgpersonid"))) 
								.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e) {
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getCourseenrollment() in CourseEnrollmentService",e);
			}
		} 
		return query;
	}
	
	public Integer updateCourseStatus(int courseid,String coursestatus){
		Integer updatedCourse = null;
		try	{
			updatedCourse=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Course set coursestatus='"+coursestatus+"' where courseid="+courseid +" ").executeUpdate();
		}
		catch(Exception e){
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in updateCourseStatus() in CourseEnrollmentService",e);
			}
		}
		return updatedCourse;
	}
	
	
	public List<?> checkEnrollment(int courseid,int personid)	{
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)

					.createAlias("this.course", "course" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.orgperson", "orgperson" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.payment", "payment" , JoinType.LEFT_OUTER_JOIN)
							
					.add(Restrictions.eq("course.courseid", courseid))
					.add(Restrictions.eq("orgperson.orgpersonid", personid))
					.setProjection(Projections.projectionList()

							.add(Projections.property("this.courseenrollmentstatus").as("courseenrollmentstatus"))
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
							.add(Projections.property("payment.paymentid").as("paymentid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getInvoiceListParticulars() in CourseEnrollmentService",e);
			}
		}

		return query;
	}

	
	public List<?> getPurchaseHistory(String personid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseenrollment.class)
 					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.payment", "payment")
					.createAlias("payment.price", "price")
					
  					.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(personid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courseenrollmentid").as("courseenrollmentid"))
  							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("payment.paymentdate").as("paymentdate"))
							.add(Projections.property("payment.paymenttype").as("paymenttype"))
							.add(Projections.property("payment.paymentid").as("paymentid"))
							.add(Projections.property("payment.orderno").as("orderno"))
   							.add(Projections.property("price.price").as("price")) 
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			e.printStackTrace();
			if(CourseEnrollmentServicelogger.isInfoEnabled()){
				CourseEnrollmentServicelogger.error("error in getPurchaseHistory() in CourseEnrollmentService",e);
			}
		}

		return query;
	}
	
	public List<?> getLivePurchaseHistory(String personid){
	
		return	 getCurrentSession().createCriteria(Livesessionenrollment.class)
						.createAlias("livesession", "livesession")
						.createAlias("orgperson", "orgperson")
						.createAlias("livesession.courseid", "course")
						.createAlias("livesessionpayments", "livesessionpayments")
							.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(personid)))
								.setProjection(Projections.projectionList()
										.add(Projections.property("this.livesessionenrollmentid").as("livesessionenrollmentid"))
										.add(Projections.property("course.coursetitle").as("coursetitle"))
										.add(Projections.property("course.courseid").as("courseid"))
										.add(Projections.property("livesessionpayments.paymenttype").as("paymenttype"))
										.add(Projections.property("livesessionpayments.orderno").as("orderno"))
										.add(Projections.property("livesession.price").as("price"))
										.add(Projections.property("livesession.description").as("description"))
										.add(Projections.property("livesession.starttime").as("starttime"))
										.add(Projections.property("livesession.endtime").as("endtime")))
											.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	public List<?> getlivesessionschedulelist(String livesessionid,String personid){
		
		return	 getCurrentSession().createCriteria(Livesessionenrollment.class)
						.createAlias("livesession", "livesession")
						.createAlias("orgperson", "orgperson")
						.createAlias("livesession.courseid", "course")
						.createAlias("livesessionpayments", "livesessionpayments")
							.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(personid)))
							.add(Restrictions.eq("livesession.livesessionid", Integer.parseInt(livesessionid)))
								.setProjection(Projections.projectionList()
										.add(Projections.property("this.livesessionenrollmentid").as("livesessionenrollmentid"))
										.add(Projections.property("course.coursetitle").as("coursetitle"))
										.add(Projections.property("course.courseid").as("courseid"))
										.add(Projections.property("livesessionpayments.paymenttype").as("paymenttype"))
										.add(Projections.property("livesessionpayments.orderno").as("orderno"))
										.add(Projections.property("livesession.price").as("price"))
										.add(Projections.property("livesession.description").as("description"))
										.add(Projections.property("livesession.starttime").as("starttime"))
										.add(Projections.property("livesession.livesessionid").as("livesessionid"))
										.add(Projections.property("livesession.endtime").as("endtime")))
											.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	public List<?> livesessionlist(String livesessionids,String personid){
		ArrayList<Integer> arry = new ArrayList<Integer>();
		String livesessionidsArray[] = livesessionids.split(",");
		for(int i=0;i<livesessionidsArray.length;i++){
			arry.add(Integer.parseInt(livesessionidsArray[i])); 
		}
		List<?> list = getCurrentSession().createCriteria(Livesessionenrollment.class)
				.createAlias("livesession", "livesession")
				.createAlias("orgperson", "orgperson")
				.createAlias("livesession.courseid", "course")
				.createAlias("livesessionpayments", "livesessionpayments")
				.add(Restrictions.eq("orgperson.orgpersonid", Integer.parseInt(personid)))
				.add(Restrictions.in("livesession.livesessionid", arry))
				.setProjection(Projections.projectionList()
						.add(Projections.property("this.livesessionenrollmentid").as("livesessionenrollmentid"))
						.add(Projections.property("course.coursetitle").as("coursetitle"))
						.add(Projections.property("course.courseid").as("courseid"))
						.add(Projections.property("livesessionpayments.paymenttype").as("paymenttype"))
						.add(Projections.property("livesessionpayments.orderno").as("orderno"))
						.add(Projections.property("livesession.price").as("price"))
						.add(Projections.property("livesession.description").as("description"))
						.add(Projections.property("livesession.starttime").as("starttime"))
						.add(Projections.property("livesession.livesessionid").as("livesessionid"))
						.add(Projections.property("livesession.endtime").as("endtime")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
}