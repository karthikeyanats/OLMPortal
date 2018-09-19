	package com.igrandee.product.ecloud.service.royalty;
	
	import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.livesession.Livesessionroyaltypayment;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;
import com.igrandee.product.ecloud.model.royalty.Royaltypayment;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;
	
	@Service
	@Scope("prototype")
	public class RoyaltyPaymentService extends GenericEntityService<Royaltypayment, Integer> {
	
		private static Logger royaltyPaymentServiceLogger = Logger.getLogger(RoyaltyPaymentService.class);
		@Override
		protected Class<Royaltypayment> entityClass() {
			return Royaltypayment.class;
		}
	
		
	  public List<?> getRoyaltyPayments(){
		  List<?> royaltyPaymentList = null;
		  try{
			  royaltyPaymentList = getCurrentSession().createCriteria(Royaltypayment.class)
				        .createAlias("courseenrollmentid", "courseenrollment")
				        .createAlias("royaltyconfig", "royaltyconfig")
				        .add(Restrictions.eq("royaltypaymentstatus", 'A'))
				        .setProjection(Projections.projectionList()
						  .add(Projections.property("id").as("id"))
						  .add(Projections.property("authorroyaltyamount").as("authorroyaltyamount"))
						  .add(Projections.property("courseenrollment.id").as("courseenrollmentid"))
						  .add(Projections.property("dateofpyament").as("dateofpyament"))
						  .add(Projections.property("orgroyaltyamount").as("orgroyaltyamount"))
						  .add(Projections.property("royaltypaymentstatus").as("royaltypaymentstatus"))
						  .add(Projections.property("royaltyconfig.id").as("royaltyconfigid")))
						  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getRoyaltyPayments() in RoyaltyPaymentService"+e);
			  }
		  }
		  return royaltyPaymentList;
		  
	  }
	  
	  public Royaltyconfig getCurrentRoyalty(Date date,int type){
		  Royaltyconfig royaltyconfig=null;
		  try{
		  String hql="from Royaltyconfig r where r.effectfrom<:date and r.effectto>:date and r.royaltyconfigstatus='A' and r.royaltytype.royaltytypeid=:type";
		  Query query=getCurrentSession().createQuery(hql);
		  query.setParameter("date", date);
		  query.setParameter("type", type);
		  royaltyconfig=(Royaltyconfig) query.uniqueResult();
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getCurrentRoyalty() in RoyaltyPaymentService"+e);
			  }
		  }
		  return royaltyconfig;
	  }
	  
	  public int getCoursePrice(int id){
		  
		  int courseEnrollmentList = 0;
		  try{
			  courseEnrollmentList = Integer.parseInt(getCurrentSession().createCriteria(Courseenrollment.class)
					  .createAlias("this.course", "course")
					  .createAlias("course.prices", "prices")
					  .add(Restrictions.in("this.courseenrollmentstatus",new String[]{"A","P"}))
					  .add(Restrictions.eq("this.id", id))
					  .add(Restrictions.ne("prices.price", "FREE"))
					  .setProjection(Projections.projectionList()
							 .add(Projections.property("prices.price").as("price")))
							.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		  }
		  catch(Exception e){
			 e.printStackTrace();
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getCoursePrice() in RoyaltyPaymentService"+e);
			  }
		  }
		  return courseEnrollmentList;
	  }
	  
	  public int getRoyaltyPaymentId(int courseid){
		  int paymentid=0;
		  try{
			  String hql="select r.id from Royaltypayment r join r.courseenrollmentid c where c.courseenrollmentid=:courseid";
			  Query query=getCurrentSession().createQuery(hql);
			  query.setParameter("courseid", courseid);
			  if(query.uniqueResult()!=null){
		      paymentid=Integer.parseInt(query.uniqueResult().toString());
		  }
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getRoyaltyPaymentId() in RoyaltyPaymentService"+e);
			  }
		  }
	      return paymentid;
	  }
		
	  public int getOrgPersonId(int royaltypaymentid){
		  int CourseenrollmentList = 0;
		  try{
			  CourseenrollmentList = Integer.parseInt(getCurrentSession().createCriteria(Courseenrollment.class)
				  		.createAlias("course", "course")
				  		.createAlias("course.orgperson", "orgperson")
				  			.add(Restrictions.eq("courseenrollmentid", royaltypaymentid))
				  			.setProjection(Projections.projectionList()
				  					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid")))
				  					.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getOrgPersonId() in RoyaltyPaymentService"+e);
			  }
		  }
		  return  CourseenrollmentList;
		 	    
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
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getRoyaltyTotalId() in RoyaltyPaymentService"+e);
			  }
		  }
		  return royaltytotalamount;		  
		  
	  }  
	  
	  public List<?> getTotalAmountByCourse(){
		  List<?> royaltyPaymentList = null;
		  try{
			  royaltyPaymentList = getCurrentSession().createCriteria(Royaltypayment.class)
					  .createAlias("courseenrollmentid", "courseenrollment")
					  .createAlias("courseenrollment.course", "course")
					  .createAlias("courseenrollment.payment", "payment")
					  .createAlias("payment.paymentdetails", "paymentdetails")
					  .createAlias("royaltyconfig", "royaltyconfig")
					  
					  .add(Restrictions.eq("royaltypaymentstatus", 'A'))
					  .add(Restrictions.in("royaltyconfig.royaltyconfigstatus", new Character[] {'A','E'}))
					  
					  .setProjection(Projections.projectionList()
							  .add(Projections.groupProperty("course.courseid"))
							  .add(Projections.property("course.courseid").as("courseid"))
							  .add(Projections.property("course.coursetitle").as("coursetitle"))						  
							  .add(Projections.sum("authorroyaltyamount").as("authorroyaltyamount"))						  
							  .add(Projections.sum("orgroyaltyamount").as("orgroyaltyamount"))
							  .add(Projections.count("courseenrollment.courseenrollmentid").as("courseenrollmentcount")))
							  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getRoyaltyPayments() in RoyaltyPaymentService"+e);
			  }
		  }
		  return royaltyPaymentList;	  
				  
	  }
	  
	  public List<?> getOrgTotalAmount(int type){
		  List<?> royaltyPaymentList = null;
		  try{
			  
			  if(type==1){
				  royaltyPaymentList = getCurrentSession().createCriteria(Royaltypayment.class)
			               .createAlias("royaltyconfig", "royaltyconfig")
			               .createAlias("royaltyconfig.royaltytype", "royaltytype")
			               .add(Restrictions.eq("royaltytype.royaltytypeid", type))
			               .add(Restrictions.eq("royaltypaymentstatus", 'A'))
			               .add(Restrictions.in("royaltyconfig.royaltyconfigstatus", new Character[] {'A','E'}))
							.setProjection(Projections.projectionList()
									.add(Projections.sum("orgroyaltyamount").as("orgroyaltyamount")))
									.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  
			  }else{
				  royaltyPaymentList = getCurrentSession().createCriteria(Livesessionroyaltypayment.class)
			               .createAlias("royaltyconfig", "royaltyconfig")
			               .createAlias("royaltyconfig.royaltytype", "royaltytype")
			               .add(Restrictions.eq("royaltytype.royaltytypeid", type))
			               .add(Restrictions.in("royaltyconfig.royaltyconfigstatus", new Character[] {'A','E'}))
			               .add(Restrictions.eq("status", "A"))
			               .setProjection(Projections.projectionList()
									.add(Projections.sum("orgroyaltyamount").as("orgroyaltyamount")))
									.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			  }
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getOrgTotalAmount() in RoyaltyPaymentService"+e);
			  }
		  }
		return royaltyPaymentList;
	  }	  
	  
	  public List<?> getPaymentDetails(int courseid){
		  List<?> royaltyPaymentList = null;
		  try{
			  royaltyPaymentList =  getCurrentSession().createCriteria(Royaltypayment.class)
					  .createAlias("royaltyconfig", "royaltyconfig")
					  .createAlias("courseenrollmentid", "courseenrollment")
					  .createAlias("courseenrollment.orgperson", "orgperson")
					  .createAlias("orgperson.personid", "person")
					  .createAlias("person.contactinfo", "contactinfo")
					  .createAlias("contactinfo.emails", "emails")
					  .createAlias("courseenrollment.course", "course")
					  .createAlias("courseenrollment.payment", "payment")
					  .createAlias("payment.price", "price")
					  .createAlias("payment.paymentdetails", "paymentdetails")
					  .add(Restrictions.eq("course.courseid", courseid))
					  .setProjection(Projections.projectionList()
							  .add(Projections.property("orgroyaltyamount").as("orgroyaltyamount"))
							  .add(Projections.property("price.price").as("paymentamount"))
							  .add(Projections.property("payment.paymentdate").as("paymentdate"))
							  .add(Projections.property("payment.paymentdescription").as("paymentdescription"))
							  .add(Projections.property("payment.paymenttype").as("paymenttype"))
							  .add(Projections.property("paymentdetails.bankname").as("bankname"))
							  .add(Projections.property("paymentdetails.cardnumber").as("cardnumber"))
							  .add(Projections.property("paymentdetails.chequedate").as("chequedate"))
							  .add(Projections.property("paymentdetails.chequenumber").as("chequenumber"))
							  .add(Projections.property("royaltyconfig.authorroyalty").as("authorroyalty"))
							  .add(Projections.property("royaltyconfig.orgroyalty").as("orgroyalty"))
							  .add(Projections.property("royaltyconfig.effectfrom").as("effectfrom"))
							  .add(Projections.property("royaltyconfig.effectto").as("effectto"))
							  .add(Projections.property("person.firstName").as("name"))
							  .add(Projections.property("person.lastName").as("lastName"))
							  .add(Projections.property("emails.userid").as("emailid")))
							  .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		  }
		  catch(Exception e){
			  if(royaltyPaymentServiceLogger.isInfoEnabled()){
				  royaltyPaymentServiceLogger.info("error in getPaymentDetails() in RoyaltyPaymentService"+e);
			  }
		  }
		  return royaltyPaymentList;
	  }
	  
}
