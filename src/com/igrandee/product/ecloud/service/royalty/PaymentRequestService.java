package com.igrandee.product.ecloud.service.royalty;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.royalty.Paymentrequest;

@Service
@Scope("prototype")
public class PaymentRequestService extends GenericEntityService<Paymentrequest, Integer>{

	private static Logger paymentRequsetServiceLogger = Logger.getLogger(PaymentRequestService.class);
	@Override
	protected Class<Paymentrequest> entityClass() {
		return Paymentrequest.class;
	}

public List<?> getPaymentRequests(){
	List<?> paymentRequestList = null;
	try{
		paymentRequestList	=	getCurrentSession().
				createSQLQuery("select payment.id as id, payment.dateofrequest as dateofrequest, payment.paymentrequeststatus as paymentrequeststatus,"+ 
								"person.firstName as requestedby,person.lastName as lastName,person.personid as personid,orgperson.orgpersonid as orgpersonid,"+
								"requestlim.id as requestlimitid, requestlim.minimumamount as minimumamount,bankdetail.accountnumber as accountnumber,"+ 
								" bankdetail.id as bankdetailid, bankdetail.bankName as bankname, bankdetail.branchName as branchname, bankdetail.ifscCode as ifscCode, bankdetail.country as country, " +
								" bankdetail.location as location from paymentrequest payment , orgperson orgperson, person person, bankdetails bankdetail, requestlimit requestlim "+ 
								" where payment.personid=orgperson.orgpersonid and orgperson.personid=person.personid and payment.requestlimit_id=requestlim.id "+ 
								" and payment.paymentrequeststatus='A' and bankdetail.orgpersonid=orgperson.orgpersonid")
			 
		          .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	catch(Exception e){
		if(paymentRequsetServiceLogger.isInfoEnabled()){
			paymentRequsetServiceLogger.error("error in getPaymentRequests() in PaymentRequestService"+e);
		}
	}
	return paymentRequestList;
 }

public List<?> getLastRequestedDetails(int authorid){
	List<?> paymentRequestList = null;
	try{
		paymentRequestList =  getCurrentSession().createCriteria(Paymentrequest.class)
				.createAlias("paymentissues", "paymentissues",JoinType.LEFT_OUTER_JOIN)
				.createAlias("requestedby", "requestedby")
				.add(Restrictions.eq("requestedby.orgpersonid", authorid))
				.add(Restrictions.eq("paymentrequeststatus", 'A'))
				.setProjection(Projections.projectionList()
						.add(Projections.property("dateofrequest").as("dateofrequest"))
						.add(Projections.property("paymentissues.dateofissue").as("dateofissue")))
						.addOrder(Order.desc("dateofrequest"))
						.setMaxResults(1)
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
	}
	catch(Exception e){
		if(paymentRequsetServiceLogger.isInfoEnabled()){
			paymentRequsetServiceLogger.error("error in getLastRequestedDetails() in PaymentRequestService"+e);
		}
	}
	return paymentRequestList;
}

public List<?> getPaymentRequestDetail(int id){
	List<?> paymentRequestList = null;
	try{
		paymentRequestList =getCurrentSession().createCriteria(Paymentrequest.class)
		        .createAlias("requestedby", "orgperson")
		        .createAlias("requestlimit", "requestlimit")
		        .createAlias("orgperson.personid", "person")
		        .add(Restrictions.eq("requestlimit.id", id))
		        .setProjection(Projections.projectionList()
		        .add(Projections.property("id").as("id"))
		        .add(Projections.property("dateofrequest").as("dateofrequest"))
		        .add(Projections.property("paymentrequeststatus").as("paymentrequeststatus"))
		        .add(Projections.property("person.firstName").as("requestedby"))
		        .add(Projections.property("person.lastName").as("lastName"))
		        .add(Projections.property("requestlimit.id").as("requestlimitid"))
		        .add(Projections.property("requestlimit.minimumamount").as("minimumamount")))
		        .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	catch(Exception e){
		if(paymentRequsetServiceLogger.isInfoEnabled()){
			paymentRequsetServiceLogger.error("error in getPaymentRequestDetail() in PaymentRequestService"+e);
		}
	}
	return paymentRequestList;
}
}
