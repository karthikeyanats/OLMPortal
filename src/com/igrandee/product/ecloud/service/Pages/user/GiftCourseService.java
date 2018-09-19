package com.igrandee.product.ecloud.service.Pages.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.GiftCourse;
import com.igrandee.product.ecloud.model.Orgperson;

@Service
@Named
@Scope("prototype")
public class GiftCourseService extends GenericEntityService<GiftCourse, Integer>{

	@Override
	protected Class<GiftCourse> entityClass() {
		// TODO Auto-generated method stub
		return GiftCourse.class;
	}

	public List<?> getDuplicateGifting(String courseid,String orgpersonid,String recipientemail){
		List<?> giftedList=null;
		try{
			giftedList= getCurrentSession().createCriteria(GiftCourse.class)

					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")

					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
					.add(Restrictions.eq("this.recipientmail",recipientemail))

					.setProjection(Projections.projectionList()
							.add(Projections.property("this.recipientmail").as("recipientemail"))							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return giftedList;
	}

	public List<?> getGiftcourseList(String id){
		List<?> giftCourseList = null;
		try{
			giftCourseList = getCurrentSession().createSQLQuery("select a.giftcourseid as giftcourseid, b.coursetitle as coursetitle, b.courselogo as courselogo, c.priceid as priceid, c.price as price, a.recipientname as recipientname,"+ 
					" a.senddate as senddate from giftcourse a, course b, price c where a.courseid=b.courseid and b.courseid=c.courseid and a.giftcourseid="+id+"")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return giftCourseList;
	}

	public Integer updateGiftCourse(int giftcourseid,String giftcoursestatus){
		try	{
			java.util.Date date = new java.util.Date();

			String hql = "UPDATE GiftCourse set giftcoursestatus = :giftcoursestatus WHERE giftcourseid = :giftcourseid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("giftcourseid", giftcourseid);
			query.setParameter("giftcoursestatus", giftcoursestatus);
			Integer result = query.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}

	public List<?> getGiftCourseHistory(String orgpersonid){
		List<?> giftedList=null;
		try{
			giftedList= getCurrentSession().createCriteria(GiftCourse.class)

					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.payments", "payment")

					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
					.add(Restrictions.eq("this.giftcoursestatus","A"))

					.setProjection(Projections.projectionList()
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("this.recipientname").as("recipientname"))
							.add(Projections.property("this.recipientmail").as("recipientmail"))
							.add(Projections.property("this.senddate").as("senddate"))
							.add(Projections.property("this.message").as("message"))
							.add(Projections.property("payment.orderno").as("orderno"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return giftedList;
	}

	public List<?> getTodaysGifts(){

		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		Date todaysDate=new Date(calendar.getTimeInMillis());

		return	getCurrentSession().createCriteria(GiftCourse.class)

				.createAlias("this.course", "course")
				.createAlias("this.orgperson", "orgperson")
				.createAlias("orgperson.personid", "person")
				.createAlias("beneficiary", "beneficiary")
				.createAlias("beneficiary.login", "login")

				.add(Restrictions.eq("this.senddate", todaysDate))
				.add(Restrictions.eq("this.giftcoursestatus", "A"))

				.setProjection(Projections.projectionList()
						.add(Projections.property("this.recipientname").as("recipientname"))
						.add(Projections.property("recipientmail").as("recipientmail"))
						.add(Projections.property("login.userid").as("username"))
						.add(Projections.property("login.password").as("password"))
						.add(Projections.property("message").as("message"))
						.add(Projections.property("person.firstName").as("sender"))
						.add(Projections.property("course.coursetitle").as("coursetitle"))
						.add(Projections.property("course.courseid").as("courseid")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

	}

	public Object[] getMasterAdminDetails(){
		return	(Object[]) getCurrentSession().createCriteria(Orgperson.class)
				.createAlias("this.organizationid", "organization",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.createAlias("organizationid.contactinfo", "contact",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.createAlias("contact.urls", "url",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)			
				.createAlias("personid", "person")
				.createAlias("personallocations", "personallocations")
				.createAlias("personallocations.role", "role")
				.createAlias("person.contactinfo", "contactinfo")
				.createAlias("contactinfo.emails", "emails")
				.createAlias("contactinfo.phones", "phones")			
				.add(Restrictions.eq("organization.orgstatus", 'M'))
				.add(Restrictions.eq("role.rolename", "admin"))
				.setProjection(Projections.projectionList()
						.add(Projections.property("person.firstName").as("contactperson"))
						.add(Projections.property("phones.number").as("number"))
						.add(Projections.property("emails.userid").as("emailid"))
						.add(Projections.property("url.url").as("orgurl")))
						.setResultTransformer(Criteria.PROJECTION).uniqueResult();
	}

	public List<?> getMasterAdminDetailsinList(){

		List al= null;
		try{
			al = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid", "organization",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("organizationid.contactinfo", "contact",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contact.urls", "url",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)			
					.createAlias("personid", "person")
					.createAlias("personallocations", "personallocations")
					.createAlias("personallocations.role", "role")
					.createAlias("person.contactinfo", "contactinfo")
					.createAlias("contactinfo.emails", "emails")
					.createAlias("contactinfo.phones", "phones")			
					
					.add(Restrictions.eq("organization.orgstatus", 'M'))
					.add(Restrictions.eq("role.rolename", "admin"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("person.firstName").as("contactperson"))
							.add(Projections.property("phones.number").as("number"))
							.add(Projections.property("emails.userid").as("emailid"))
							.add(Projections.property("url.url").as("orgurl")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return al;	
	}

	public List<?> getGiftCoursePayment(int giftcourseid){
		List<?> giftedList=null;
		try{
			giftedList= getCurrentSession().createCriteria(GiftCourse.class)
					.createAlias("this.payments", "payments")
					.add(Restrictions.eq("this.giftcourseid",giftcourseid))
					.add(Restrictions.eq("payments.paymentstatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("payments.paymentid").as("paymentid"))
							.add(Projections.property("this.recipientname").as("recipientname"))
							.add(Projections.property("this.recipientmail").as("recipientmail"))
							.add(Projections.property("this.senddate").as("senddate"))
							.add(Projections.property("this.message").as("message"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return giftedList;
	}
	/**
	 * @author muniyarasu_a
	 * @param userid and courseid
	 * @return getDuplicateUserId list
	 */
	public List<?> getDuplicateUserId(String recipientemail,String courseid){
		List<?> giftedList=null;
		try{
			String[] courseenrollmentstatus = {"A","B","C","P","W"};
			giftedList= getCurrentSession().createCriteria(Courseenrollment.class)

					.createAlias("this.course", "course")
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.login", "login")

					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("login.userid",recipientemail))
					.add(Restrictions.in("courseenrollmentstatus", courseenrollmentstatus))

					.setProjection(Projections.projectionList()
							.add(Projections.property("login.userid").as("recipientemail"))							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return giftedList;
	}

}
