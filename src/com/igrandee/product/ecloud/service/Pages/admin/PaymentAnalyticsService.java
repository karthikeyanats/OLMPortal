package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Payment;

@Service
@Named
@Scope("prototype")
public class PaymentAnalyticsService extends GenericEntityService<Payment, Integer>{

	@Override
	protected Class<Payment> entityClass() {
		// TODO Auto-generated method stub
		return Payment.class;
	}

	@SuppressWarnings("unchecked")
	public List<?> loadCurrentPayment(String organizationid,String paymentInterval){
		List<?> descriptionsList = null;
		List<Integer> paymentids = null;
		try	{
			if(paymentInterval.equalsIgnoreCase("day")){
				paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
						"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
						"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
						"and date(a.paymentdate)=date(now())").list();	
			}else if(paymentInterval.equalsIgnoreCase("week")){
				paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
						"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
						"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
						"and week(a.paymentdate)=week(now())").list();	
			}else if(paymentInterval.equalsIgnoreCase("month")){
				paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
						"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
						"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
						"and month(a.paymentdate)=month(now())").list();	
			}else if(paymentInterval.equalsIgnoreCase("year")){
				paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
						"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
						"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
						"and year(a.paymentdate)=year(now())").list();		
			}else{
				paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
						"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
						"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
						"and date(a.paymentdate)=date(now())").list();
			}
			if(paymentids.size()!=0){
				descriptionsList = getCurrentSession().createCriteria(Payment.class)
						
						.createAlias("this.courseenrollment", "courseenrollment")
						.createAlias("courseenrollment.orgperson", "subscribedorgperson")
						.createAlias("subscribedorgperson.personid", "subscribedperson")
						
						.createAlias("this.price", "price")
						.createAlias("price.course", "course")
						.createAlias("course.orgperson", "authororgperson")
						.createAlias("authororgperson.personid", "authorperson")
						
								
						.add(Restrictions.in("this.paymentid",paymentids))
						
						.setProjection(Projections.projectionList()
								
								.add(Projections.property("this.paymentdate").as("paymentdate"))
								
								.add(Projections.property("course.courseid").as("courseid"))
								.add(Projections.property("course.coursetitle").as("coursetitle"))
								
								.add(Projections.property("price.price").as("price"))
								
								.add(Projections.property("subscribedperson.personid").as("userpersonid"))
								.add(Projections.property("subscribedperson.firstName").as("userfirstname"))
								.add(Projections.property("subscribedperson.lastName").as("userlastname"))
								
								.add(Projections.property("authorperson.personid").as("authorpersonid"))
								.add(Projections.property("authorperson.firstName").as("authorfirstname"))
								.add(Projections.property("authorperson.lastName").as("authorlastname"))
								
								
								).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> loadRangePayment(String organizationid,String from,String to){
		List<?> descriptionsList = null;
		List<Integer> paymentids = null;
		try	{
			paymentids = getCurrentSession().createSQLQuery("select a.paymentid  " +
					"from payment a, price b,course c,orgperson d where a.priceid=b.priceid and a.paymentstatus='A' " +
					"and c.courseid=b.courseid and b.price<> 'Free' and c.personid=d.orgpersonid and d.organizationid = '"+Long.parseLong(organizationid)+"'" +
					"and date_format(a.paymentdate,'%d-%m-%Y') BETWEEN  " +
					"date_format('"+from+"','%d-%m-%Y') AND date_format('"+to+"','%d-%m-%Y')").list();	
			
			if(paymentids.size()!=0){
				descriptionsList = getCurrentSession().createCriteria(Payment.class)
						
						.createAlias("this.courseenrollment", "courseenrollment")
						.createAlias("courseenrollment.orgperson", "subscribedorgperson")
						.createAlias("subscribedorgperson.personid", "subscribedperson")
						
						.createAlias("this.price", "price")
						.createAlias("price.course", "course")
						.createAlias("course.orgperson", "authororgperson")
						.createAlias("authororgperson.personid", "authorperson")
						
								
						.add(Restrictions.in("this.paymentid",paymentids))
						
						.setProjection(Projections.projectionList()
								.add(Projections.property("this.paymentdate").as("paymentdate"))
								
								.add(Projections.property("course.courseid").as("courseid"))
								.add(Projections.property("course.coursetitle").as("coursetitle"))
								
								.add(Projections.property("price.price").as("price"))
								
								.add(Projections.property("subscribedperson.personid").as("userpersonid"))
								.add(Projections.property("subscribedperson.firstName").as("userfirstname"))
								.add(Projections.property("subscribedperson.lastName").as("userlastname"))
								
								.add(Projections.property("authorperson.personid").as("authorpersonid"))
								.add(Projections.property("authorperson.firstName").as("authorfirstname"))
								.add(Projections.property("authorperson.lastName").as("authorlastname"))
								
								
								).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
}
