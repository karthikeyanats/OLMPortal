package com.igrandee.product.ecloud.service.course;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Payment;
import com.igrandee.product.ecloud.model.livesession.Livesessionpayment;

@Named
@Service
@Scope("prototype")
public class InvoicePaymentService extends GenericEntityService<Payment, Serializable>
{

	@Override
	protected Class<Payment> entityClass() {
		// TODO Auto-generated method stub
		return Payment.class;
	}
	
	private final Logger invoicePaymentServiceLogger = Logger.getLogger(InvoicePaymentService.class);
	
	public List<?> getInvoiceListValue(Integer invoiceid){

		Criteria query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Payment.class)

				.createAlias("this.courseenrollment", "courseenrollment" , JoinType.LEFT_OUTER_JOIN)
				.createAlias("this.paymentdetails", "paymentdetails" , JoinType.LEFT_OUTER_JOIN)
				.createAlias("this.price", "priceobj", JoinType.LEFT_OUTER_JOIN)
				.createAlias("priceobj.course", "course", JoinType.LEFT_OUTER_JOIN)
				.createAlias("course.orgperson", "orgperson", JoinType.LEFT_OUTER_JOIN)
				.createAlias("orgperson.personid", "personid", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.eq("courseenrollment.courseenrollmentid", invoiceid))
				.setProjection(Projections.projectionList()

						.add(Projections.property("courseenrollment.courseenrollmentid").as("courseenrollmentid"))
						.add(Projections.property("this.paymentid").as("paymentid"))
						.add(Projections.property("this.paymenttype").as("paymenttype"))
						.add(Projections.property("this.paymentdescription").as("paymentdescription"))
						.add(Projections.property("this.paymentamount").as("paymentamount"))
						.add(Projections.property("this.paymentdate").as("paymentdate"))
						.add(Projections.property("this.orderno").as("orderno"))
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
						);
		return query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<?> getLivesessionInvoice(int livesessionenrollid){
		
	 return	getCurrentSession().createCriteria(Livesessionpayment.class)
					.createAlias("livesessionenrollment", "livesessionenrollment")
					.createAlias("livesessionenrollment.livesession", "livesession")
					.createAlias("livesession.courseid", "course")
					.createAlias("livesessionenrollment.orgperson", "orgperson")
					.createAlias("orgperson.personid", "personid")
						.add(Restrictions.eq("livesessionenrollment.livesessionenrollmentid", livesessionenrollid))
					    .setProjection(Projections.projectionList()
					    		.add(Projections.property("orderno").as("orderno"))
					    		.add(Projections.property("paymenttype").as("paymenttype"))
					    		.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
								.add(Projections.property("personid.personid").as("personid"))
								.add(Projections.property("personid.firstName").as("firstname"))
								.add(Projections.property("personid.lastName").as("lastname"))
								.add(Projections.property("course.coursetitle").as("coursetitle"))
								.add(Projections.property("course.courseid").as("courseid"))
								.add(Projections.property("livesession.price").as("price")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		
		
	}
	
	
	/**
	 * 
	 * @param courseid
	 * @return returnCourseId | int
	 */
	public List<?> getCourseAuthorDetails(int courseid) {
		List<?> authorList = null;
		Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Course.class); 
			query.createAlias("this.orgperson", "orgperson")
			     .createAlias("orgperson.personid", "author")
			     .createAlias("author.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			 .createAlias("this.prices", "prices", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			
			query.add(Restrictions.eq("this.coursestatus", "a"))
			     .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'))
			     .add(Restrictions.eq("author.personstatus", 'A'))
			     .add(Restrictions.eq("this.courseid", courseid)); 
			
			query.setProjection(Projections.projectionList()  
					.add(Projections.property("orgperson.orgpersonid").as("authorid"))
					.add(Projections.property("author.firstName").as("authorfirstname"))
					.add(Projections.property("author.lastName").as("authorlastname"))
					.add(Projections.property("email.userid").as("authoremailid")) 
					.add(Projections.property("this.coursetitle").as("coursetitle")) 
					.add(Projections.property("prices.price").as("price"))

			); 
			authorList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			invoicePaymentServiceLogger.error(e);
		}
		return authorList;
	}
	
	

}



	