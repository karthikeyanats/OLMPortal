package com.igrandee.product.ecloud.service.livesession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;
import com.igrandee.product.ecloud.model.livesession.Livesessionroyaltypayment;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;

@Service
@Scope("prototype")
public class LiveSessionRoyaltyPaymentService extends GenericEntityService<Livesessionroyaltypayment, Integer> {

	@Override
	protected Class<Livesessionroyaltypayment> entityClass() {
		// TODO Auto-generated method stub
		return Livesessionroyaltypayment.class;
	}
	
	 public Royaltyconfig getCurrentRoyalty(Date date){
		  Royaltyconfig royaltyconfig=null;
		  try{
		  String hql="from Royaltyconfig r where r.effectfrom<:date and r.effectto>:date and r.royaltyconfigstatus='A' and r.royaltytype.royaltytypeid=:type";
		  Query query=getCurrentSession().createQuery(hql);
		  query.setParameter("date", date);
		  query.setParameter("type", 2);
		  royaltyconfig=(Royaltyconfig) query.uniqueResult();
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  return royaltyconfig;
	  }

	 @SuppressWarnings("unchecked")
		public List<HashMap<?, ?>> getDefaultRoyalty(){
			List<?> royaltyConfigList = null;
			try{
				royaltyConfigList	=getCurrentSession().createCriteria(Royaltyconfig.class)
						.createAlias("createdby", "orgperson")
						.createAlias("royaltytype", "royaltytype")
						.add(Restrictions.eq("royaltyconfigstatus", 'E'))
						.add(Restrictions.eq("royaltytype.royaltytypeid", 2))
				          .setProjection(Projections.projectionList()
				        		  .add(Projections.property("id").as("id"))
				        		  .add(Projections.property("authorroyalty").as("authorroyalty"))
				        		  .add(Projections.property("orgperson.orgpersonid").as("createdby"))
				        		  .add(Projections.property("dateofcreation").as("dateofcreation"))
				        		  .add(Projections.property("effectfrom").as("effectfrom"))
				        		  .add(Projections.property("effectto").as("effectto"))
				        		  .add(Projections.property("royaltyconfigstatus").as("royaltyconfigstatus"))
				        		  .add(Projections.property("orgroyalty").as("orgroyalty")))
				        		  	.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
			}
			catch(Exception e){
				 e.printStackTrace();
			}
			return (List<HashMap<?, ?>>) royaltyConfigList;
		}
	 
	 public int getCoursePrice(int id){
		  
		  int livesessionEnrollment = 0;
		  try{
			  livesessionEnrollment = Integer.parseInt(getCurrentSession().createCriteria(Livesessionenrollment.class)
					  .createAlias("this.livesession", "livesession")
					  .add(Restrictions.eq("this.status", "A"))
					  .add(Restrictions.eq("this.id", id))
					  .add(Restrictions.ne("livesession.price", "FREE"))
					  .setProjection(Projections.projectionList()
							 .add(Projections.property("livesession.price").as("price")))
							.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  return livesessionEnrollment;
	  }
	 
	 public int getOrgPersonId(int livesessionenrollmentid){
		  int livesessionEnrollmentList = 0;
		  try{
			  livesessionEnrollmentList = Integer.parseInt(getCurrentSession().createCriteria(Livesessionenrollment.class)
				  		.createAlias("livesession", "livesession")
				  		.createAlias("livesession.courseid", "course")
				  		.createAlias("course.orgperson", "orgperson")
				  			.add(Restrictions.eq("this.livesessionenrollmentid", livesessionenrollmentid))
				  			.setProjection(Projections.projectionList()
				  					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid")))
				  					.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		  }
		  catch(Exception e){
			 e.printStackTrace();
		  }
		  return  livesessionEnrollmentList;
		 	    
	   }
	 
	 public Royaltytotalamount getRoyaltyTotalId(int authorid){
		  Royaltytotalamount royaltytotalamount = null;
		  try{
			  royaltytotalamount = (Royaltytotalamount)getCurrentSession().createCriteria(Royaltytotalamount.class)
						.add(Restrictions.eq("authorid", authorid))
						.add(Restrictions.eq("royaltytotalamountstatus", 'A'))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
		  }
		  catch(Exception e){
			 e.printStackTrace();
		  }
		  return royaltytotalamount;		  
		  
	  }  
	 
	 public List<?> getTotalAmountByLivesession(){
		  List<?> royaltyPaymentList = null;
		  try{
			  royaltyPaymentList = getCurrentSession().createCriteria(Livesessionroyaltypayment.class)
					  .createAlias("royaltyconfig", "royaltyconfig")
					  .createAlias("livesessionenrollment", "livesessionenrollment")
					  .createAlias("livesessionenrollment.livesession", "livesession")
					  .createAlias("livesession.courseid", "course")
					  .add(Restrictions.eq("status", "A"))
					  .add(Restrictions.in("royaltyconfig.royaltyconfigstatus", new Character[] {'A','E'}))
					  
					  .setProjection(Projections.projectionList()
							  .add(Projections.groupProperty("livesession.livesessionid"))
							  .add(Projections.property("livesession.livesessionid").as("livesessionid"))
							  .add(Projections.property("course.coursetitle").as("coursetitle"))
							  .add(Projections.property("livesession.starttime").as("starttime"))
							  .add(Projections.property("livesession.endtime").as("endtime"))
							  .add(Projections.property("livesession.description").as("description"))
							  .add(Projections.sum("authorroylatyamount").as("authorroyaltyamount"))						  
							  .add(Projections.sum("orgroyaltyamount").as("orgroyaltyamount"))
							  
							  
							  .add(Projections.count("livesessionenrollment.livesessionenrollmentid").as("livesessionenrollmentid")))
							  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		  }
		  catch(Exception e){
			  e.printStackTrace();
 		  }
		  return royaltyPaymentList;	  
				  
	  }
	 
	 public List<?> getPaymentDetails(int livesessionid){
		  List<?> royaltyPaymentList = null;
		  try{
			  royaltyPaymentList =  getCurrentSession().createCriteria(Livesessionroyaltypayment.class)
					  .createAlias("royaltyconfig", "royaltyconfig")
					  .createAlias("livesessionenrollment", "livesessionenrollment")
					  .createAlias("livesessionenrollment.livesession", "livesession")
					  .createAlias("livesessionenrollment.orgperson", "orgperson")
					  .createAlias("orgperson.personid", "person")
					  .createAlias("person.contactinfo", "contactinfo")
					  .createAlias("contactinfo.emails", "emails")
					  .createAlias("livesession.courseid", "course")
					  .createAlias("livesessionenrollment.livesessionpayments", "payment")
					  
					  
					  .add(Restrictions.eq("livesession.livesessionid", livesessionid))
					  .setProjection(Projections.projectionList()
							  
							  .add(Projections.property("livesession.price").as("paymentamount"))
							  .add(Projections.property("payment.dateofpayment").as("paymentdate"))
							  
							  .add(Projections.property("orgroyaltyamount").as("orgroyaltyamount"))
							  .add(Projections.property("payment.paymenttype").as("paymenttype"))
 							  .add(Projections.property("royaltyconfig.authorroyalty").as("authorroyalty"))
							  .add(Projections.property("royaltyconfig.orgroyalty").as("orgroyalty"))
							  .add(Projections.property("person.firstName").as("name"))
							  .add(Projections.property("royaltyconfig.effectfrom").as("effectfrom"))
							  .add(Projections.property("royaltyconfig.effectto").as("effectto"))
							  .add(Projections.property("emails.userid").as("emailid")))
							  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			 
		  }
		  return royaltyPaymentList;
	  }
	
}
