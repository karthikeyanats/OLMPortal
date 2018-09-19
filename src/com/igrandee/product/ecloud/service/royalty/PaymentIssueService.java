package com.igrandee.product.ecloud.service.royalty;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.royalty.Paymentissue;
import com.igrandee.product.ecloud.model.royalty.Paymentrequest;
import com.igrandee.product.ecloud.model.royalty.Requestlimit;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;

@Service
@Scope("prototype")
public class PaymentIssueService extends GenericEntityService<Paymentissue, Integer>{

	private static Logger paymentIssueServiceLogger = Logger.getLogger(PaymentIssueService.class);
	
	@Override
	protected Class<Paymentissue> entityClass() {
		return Paymentissue.class;
	}
	
	public List<?> getPaymentIssueList(int orgPersonid){
		
		List<?> paymentIssueList = null;
		
		try{
			
			paymentIssueList = getCurrentSession().createCriteria(Paymentissue.class)
					.createAlias("paymentrequest", "paymentrequest")
					.createAlias("paymentrequest.requestlimit", "requestlimit")
					.createAlias("paymentrequest.requestedby", "createdby")
					.add(Restrictions.eq("createdby.orgpersonid", orgPersonid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("paymentrequest.dateofrequest").as("dateofrequest"))
							.add(Projections.property("requestlimit.minimumamount").as("minimumamount"))
							.add(Projections.property("this.dateofissue").as("dateofissue")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(paymentIssueServiceLogger.isInfoEnabled()){
				paymentIssueServiceLogger.error("error in getPaymentIssueList() in PaymentIssueService"+e);
			}
		}
		return paymentIssueList;
	}

public List<?> getPaymentIssues(){
	
	List<?> paymentIssueList = null;
	try{
		
		paymentIssueList =getCurrentSession().createCriteria(Paymentissue.class)
							.createAlias("createdby", "orgperson")
							.createAlias("orgperson.personid", "person")
							.createAlias("paymentrequest", "paymentrequest")
							.createAlias("paymentrequest.requestedby", "requestedby")
							.createAlias("requestedby.personid", "personid")
							.createAlias("paymentrequest.requestlimit", "requestlimit")
							.add(Restrictions.eq("paymentissuestatus", 'A'))
							.addOrder(Order.desc("dateofissue"))
							.setProjection(Projections.projectionList()
									.add(Projections.property("id").as("id"))
									.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
									.add(Projections.property("person.firstName").as("Issuedby"))
									.add(Projections.property("person.lastName").as("IssuedbyLastName"))
									.add(Projections.property("personid.firstName").as("Requestedby"))
									.add(Projections.property("personid.lastName").as("RequestedbyLastName"))
									.add(Projections.property("dateofissue").as("dateofissue"))
									.add(Projections.property("requestlimit.minimumamount").as("amount"))
									.add(Projections.property("paymentissuestatus").as("paymentissuestatus"))
									.add(Projections.property("paymentrequest.id").as("paymentrequestid")))
									.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	catch(Exception e){
		if(paymentIssueServiceLogger.isInfoEnabled()){
			paymentIssueServiceLogger.error("error in getPaymentIssues() in PaymentIssueService"+e);
		}
	}
	return paymentIssueList;
	}

public Requestlimit getRequestlimit(int paymentrequestid){
	Requestlimit paymentRequestList = null;
	try{
		paymentRequestList = (Requestlimit) getCurrentSession().createCriteria(Requestlimit.class)
		.createAlias("paymentrequests", "paymentrequests")
		.add(Restrictions.eq("paymentrequests.id", paymentrequestid))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	}
	catch(Exception e){
		if(paymentIssueServiceLogger.isInfoEnabled()){
			paymentIssueServiceLogger.error("error in getRequestlimit() in PaymentIssueService"+e);
		}
	}
	
	return paymentRequestList;
	}

public int getAuthorId(int paymentrequestid){
	int paymentRequestList = 0;
	try{
		paymentRequestList = Integer.parseInt(getCurrentSession().createCriteria(Paymentrequest.class)
                .createAlias("requestedby", "requestedby")
				.add(Restrictions.eq("id", paymentrequestid))
				.setProjection(Projections.property("requestedby.orgpersonid")).uniqueResult().toString());	
	}
	catch(Exception e){
		if(paymentIssueServiceLogger.isInfoEnabled()){
			paymentIssueServiceLogger.error("error in getAuthorId() in PaymentIssueService"+e);
		}
	}
	
	return 	paymentRequestList;
}
public Royaltytotalamount getRoyaltyTotal(int authorid){
	
	Royaltytotalamount royaltytotalamount = null; 
	  try{
		  royaltytotalamount =  (Royaltytotalamount)getCurrentSession().createCriteria(Royaltytotalamount.class)
			.add(Restrictions.eq("authorid", authorid))
			.add(Restrictions.eq("royaltytotalamountstatus", 'A'))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	  }
	  catch(Exception e){
			if(paymentIssueServiceLogger.isInfoEnabled()){
				paymentIssueServiceLogger.error("error in getRoyaltyTotal() in PaymentIssueService"+e);
			}
		}
	  return royaltytotalamount;
	  
}  

public List<?> getAuthorTotalamountList(int orgPersonid){
	List<?> paymentIssueList = null;
	try{
		
		paymentIssueList = getCurrentSession().createCriteria(Paymentissue.class)
			.createAlias("paymentrequest", "paymentrequest")
			.createAlias("paymentrequest.requestlimit", "requestlimit")
			.createAlias("createdby", "createdby")
			.createAlias("createdby.personid", "person")
			.createAlias("paymentrequest.requestedby", "requestedby")
			.add(Restrictions.eq("requestedby.orgpersonid", orgPersonid))
			.setProjection(Projections.projectionList()
					.add(Projections.sum("requestlimit.minimumamount").as("minimumamount")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
	}
	catch(Exception e){
		if(paymentIssueServiceLogger.isInfoEnabled()){
			paymentIssueServiceLogger.error("error in getAuthorTotalamountList() in PaymentIssueService"+e);
		}
	}
	return paymentIssueList;
}

}
