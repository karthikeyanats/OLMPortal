package com.igrandee.product.ecloud.service.users;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.PresenterappDownloadhistory;
import com.igrandee.product.ecloud.model.Presenterappdetail;


@Service
@Scope("prototype")
@Named
public class UserViewService extends GenericEntityService<Orgperson, Serializable>{
	private static Logger UserViewServicelogger = Logger.getLogger(UserViewService.class);

	@Override
	protected Class<Orgperson> entityClass() {
 		return Orgperson.class;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get Users information
	 * @return
	 */
	public List<?> loadAllValidUsers(String orgpersonid,String courseid)	{
		List<?> validUsersList =null;
		Criteria query = null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)					
					.createAlias("this.orgperson", "orgperson")
					.createAlias("this.courseenrollments", "courseenrollments")
					.createAlias("courseenrollments.orgperson", "subscriber")
					.createAlias("subscriber.personid", "personobj")
					.createAlias("personobj.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			if(courseid==null || courseid.equalsIgnoreCase("null") || courseid.equals("null") ){				
			}
			else{
				query.add(Restrictions.eq("this.courseid",Integer.parseInt(courseid)));
			}
			validUsersList=query.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
							 .setProjection(Projections.projectionList()
						    .add(Projections.property("this.courseid").as("courseid"))
						    .add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("personobj.firstName").as("username"))
							.add(Projections.property("personobj.lastName").as("lastName"))
							.add(Projections.property("email.userid").as("emailaddress"))
							.add(Projections.property("subscriber.orgpersonid").as("personid"))
							.add(Projections.groupProperty("subscriber.orgpersonid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadAllValidUsers() in UserViewService "+e);
			}
		}		
		return validUsersList;
	}
	public List<?> loadValidUsers(String organizationid){
		List<?> uersList =null;
		try	{
			uersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)					
					.createAlias("this.organizationid", "organization")					
					.createAlias("this.personid", "person")
					.createAlias("this.login", "login")
					.createAlias("person.contactinfo", "personcontactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.createAlias("personcontactinfo.emails","personemail",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.createAlias("this.personallocations","personallocations")
					.createAlias("personallocations.role","role")					
					.add(Restrictions.eq("role.rolename","user"))
					.add(Restrictions.eq("role.rolestatus",'A'))
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("this.orgpersonstatus",'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("person.firstName").as("username"))
							.add(Projections.property("person.lastName").as("lastName"))
 							.add(Projections.property("personemail.userid").as("emailaddress"))
							.add(Projections.property("organization.organizationid").as("organizationid"))		
							.add(Projections.property("organization.orgname").as("orgname"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.groupProperty("this.orgpersonid").as("orgpersonid"))
							.add(Projections.groupProperty("login.loginid").as("loginid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		}
		catch(Exception e){
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadValidUsers() in UserViewService "+e);
			}
		}		
		return uersList	;
	}
	
	 
	public List<?> loadRemovedUsers(String organizationid){
		List<?> removeuersList =null;
		try	{
			removeuersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)					
					.createAlias("this.organizationid", "organization")					
					.createAlias("this.personid", "person")
					.createAlias("this.login", "login")
					.createAlias("person.contactinfo", "personcontactinfo")
 					.createAlias("personcontactinfo.emails","personemail")
 					.createAlias("this.personallocations","personallocations")
					.createAlias("personallocations.role","role")	
					.add(Restrictions.eq("role.rolename","user"))
					.add(Restrictions.eq("role.rolestatus",'A'))
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("this.orgpersonstatus",'D'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("person.firstName").as("username"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("personemail.userid").as("emailaddress"))
							.add(Projections.property("organization.organizationid").as("organizationid"))		
							.add(Projections.property("organization.orgname").as("orgname"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.groupProperty("this.orgpersonid").as("orgpersonid"))
							.add(Projections.groupProperty("login.loginid").as("loginid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadRemovedUsers() in UserViewService "+e);
			}
		}
		return removeuersList	;
	}
	 
	
	public List<?> OrganizationUsers(String organizationid,String personid){
		List<?> uersList =null;
		try	{			
			uersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)					
					.createAlias("this.organizationid", "organization")					
					.createAlias("this.personid", "person")
					.createAlias("this.personallocations","personallocations")
					.createAlias("personallocations.role","role")					
					.createAlias("person.contactinfo", "personcontactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.createAlias("personcontactinfo.emails","personemail",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.or(Restrictions.eq("person.personstatus",'A'),Restrictions.eq("person.personstatus",'I')))
					.add(Restrictions.or(Restrictions.eq("role.rolename", "user"),Restrictions.eq("role.rolename","admin")))								
					.add(Restrictions.ne("person.personid",Long.parseLong(personid)))			
					.add(Restrictions.eq("role.rolestatus",'A'))
					.add(Restrictions.eq("this.orgpersonstatus", 'A'))					
					.setProjection(Projections.projectionList()							
							.add(Projections.property("person.firstName").as("username"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("personemail.userid").as("emailaddress"))
							.add(Projections.property("organization.organizationid").as("organizationid"))		
							.add(Projections.property("organization.orgname").as("orgname"))
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.groupProperty("this.orgpersonid").as("orgpersonid"))
							)
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadValidUsers() in UserViewService "+e);
			}			
		}
		return uersList	;		
	}

	
	public List<?> loadWaitingUsers(String organizationid){
		List<?> uersList =null;
		try	{
			uersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseinvitation.class)					
					.createAlias("this.orgperson", "orgperson")	
					.createAlias("orgperson.organizationid", "organization")	
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("this.courseinvitationstatus",'I'))
					.addOrder(Order.desc("dateofinvitation"))
 					.setProjection(Projections.projectionList()
						.add(Projections.property("this.emailid").as("emailid"))
						.add(Projections.property("this.dateofinvitation").as("createdon"))							
						.add(Projections.property("this.dateofinvitation").as("registereddate"))							
						).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadValidUsers() in UserViewService "+e);
			}
		}
		return uersList	;
	}
	
	

	public List<?> loadInviteUsers(String orgpersonid,int courseid){
		List<?> uersList =null;
		try	{
			Order order = Order.desc("emailid");
			uersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseinvitation.class).addOrder(order)					
				.createAlias("this.orgperson", "orgperson")	
				.createAlias("orgperson.organizationid", "organization")
				.createAlias("course", "course")
				.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
				.add(Restrictions.eq("this.courseinvitationstatus",'I'))
				.add(Restrictions.eq("course.courseid",courseid))
					.setProjection(Projections.projectionList()
					.add(Projections.property("this.emailid").as("emailid"))
					.add(Projections.property("this.dateofinvitation").as("createdon"))							
					.add(Projections.property("this.dateofinvitation").as("registereddate"))							
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadValidUsers() in UserViewService "+e);
			}
		}
		return uersList	;
	}



	/**
	 * @author seenivasagan_p
	 * Method to get all the certificate details irrespective of their approved status
	 * @return
	 */
	public List<?> loadAllCourseCertificates(String orgpersonid,String courseid){
		List<?> certificatesList = null;
		Criteria query = null;
		try	{
			query =  getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecertificate.class)
					.createAlias("this.courseenrollment", "courseenrollment",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseenrollment.course", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseenrollment.orgperson", "orgperson" ,org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgperson.personid", "personobj",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personobj.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "author", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			if(courseid==null || courseid.equalsIgnoreCase("null") || courseid.equals("null") ){
				
			}
			else{
				query.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)));
			}
			certificatesList=	query.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(orgpersonid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursecertificateid").as("coursecertificateid"))
							.add(Projections.property("this.certificatedescription").as("certificatedescription"))
							.add(Projections.property("this.coursecertificatestatus").as("coursecertificatestatus"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))
							.add(Projections.property("course.coursestatus").as("coursestatus"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("personobj.firstName").as("username"))
							.add(Projections.property("personobj.lastName").as("lastName"))
							.add(Projections.property("email.emailid").as("emailid"))
							.add(Projections.property("email.userid").as("emailaddress")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled())	{
				UserViewServicelogger.error("error in loadAllCourseCertificates() in UserViewService"+e);
			}
		}
		return certificatesList;
	}

	public List<?> getUsersInvoices(String paymenttype,String approvedstatus,String orgpersonid,String courseid){
		List<?> invoiceList = null;
		Criteria query= null;
		try	{
			Order order = Order.desc("courseenrollmentid");
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Payment.class).addOrder(order)
					.createAlias("this.courseenrollment", "courseenrollment", JoinType.LEFT_OUTER_JOIN)
					.createAlias("courseenrollment.orgperson", "orgperson", JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.paymentdetails", "paymentdetails" , JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.price", "priceobj", JoinType.LEFT_OUTER_JOIN)
					.createAlias("priceobj.course", "course", JoinType.LEFT_OUTER_JOIN)  
					.createAlias("orgperson.personid", "personid", JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "author", JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("author.orgpersonid",Integer.parseInt(orgpersonid)))	
					;
					/*.add(Restrictions.or(Restrictions.eq("courseenrollment.courseenrollmentstatus","A"),
					 Restrictions.eq("courseenrollment.courseenrollmentstatus","P")));*/
			if(courseid==null || courseid.equalsIgnoreCase("null") || courseid.equals("null") ){				
			} else {
				query.add(Restrictions.or(Restrictions.eq("course.courseid",Integer.parseInt(courseid))));				
			}
			if(!approvedstatus.equalsIgnoreCase("All")){
				if(approvedstatus.equalsIgnoreCase("A")){
					query.add(Restrictions.in("courseenrollment.courseenrollmentstatus", new String[] {"A","B","C"} ));
				}
				else{
					query.add(Restrictions.or(Restrictions.eq("courseenrollment.courseenrollmentstatus",approvedstatus)));
					query.add(Restrictions.or(Restrictions.eq("this.paymentstatus",approvedstatus)));
				}
			}
			if(!paymenttype.equalsIgnoreCase("All")){
				query.add(Restrictions.eq("this.paymenttype",paymenttype));
			}
			invoiceList=query.setProjection(Projections.projectionList()
					.add(Projections.property("this.paymentid").as("paymentid"))
					.add(Projections.property("this.paymenttype").as("paymenttype"))
					.add(Projections.property("this.paymentdescription").as("paymentdescription"))
					.add(Projections.property("this.paymentamount").as("paymentamount"))
					.add(Projections.property("this.paymentstatus").as("paymentstatus"))
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
					.add(Projections.property("personid.lastName").as("lastName"))
					.add(Projections.property("course.coursetitle").as("coursetitle"))
					.add(Projections.property("course.courseid").as("courseid"))
					.add(Projections.property("courseenrollment.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.property("courseenrollment.courseenrollmentstatus").as("courseenrollmentstatus"))
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(UserViewServicelogger.isInfoEnabled()){
				e.printStackTrace();
				UserViewServicelogger.error("error in getUsersInvoices() in UserViewService "+e);
			}
		}
		return invoiceList;
	}	


	public List<?> getUsernamePasswordCheck(String username,String password,String orgid) {
		List<?> loginlist = getCurrentSession().createCriteria(Orgperson.class) 
				
				.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
				.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.createAlias("this.organizationid", "organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
				.add(Restrictions.eq( "login.userid", StringUtils.lowerCase(username)))
				.add(Restrictions.eq( "login.password", StringUtils.lowerCase(password)))
				.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(orgid))) 
				.add(Restrictions.eq( "login.loginstatus", 'A'))
  				.setProjection(Projections.projectionList() 
						.add(Projections.property("personid.firstName").as("firstname"))
						.add(Projections.property("personid.lastName").as("lastname")) 
 						.add(Projections.property("login.loginid").as("loginid"))
 						.add(Projections.property("this.orgpersonid").as("orgpersonid"))) 
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
						return loginlist; 
	}
	
	public List<?> getPresenterAppDetails(String orgpersonid) {
		List<?> persenterappList=null;
 		try{
		  persenterappList = getCurrentSession().createCriteria(PresenterappDownloadhistory.class)
 				.createAlias("this.orgperson", "orgperson",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 				.createAlias("this.presenterappdetail", "presenterappdetail",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 				.createAlias("presenterappdetail.presenterapp", "presenterapp")
 				.createAlias("presenterappdetail.presentertype", "presentertype") 
   				.add(Restrictions.eq( "orgperson.orgpersonid", Integer.parseInt(orgpersonid)))
   				.setProjection(Projections.projectionList() 
						.add(Projections.property("presentertype.typename").as("typename"))
						.add(Projections.property("presenterapp.applicationname").as("applicationname")) 
 						.add(Projections.property("presenterappdetail.logincount").as("logincount"))
 						.add(Projections.property("presenterappdetail.workingduration").as("workingduration"))
 						.add(Projections.property("presenterapp.licenseapppath").as("licenseapppath"))) 
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
		return persenterappList; 
	}
	
	public List<?> getPasswordCheck(String username,String password) {

		List<?> loginlist = getCurrentSession().createCriteria(com.igrandee.core.login.model.Login.class)
				.add(Restrictions.eq( "userid", StringUtils.lowerCase(username)))
				.add(Restrictions.eq( "password", StringUtils.lowerCase(password)))
				.add(Restrictions.eq( "loginstatus", 'A'))
				.list();

		return loginlist; 
	}	
	public List<?> getUsernameCheck(String username) {

		List<?> loginlist = getCurrentSession().createCriteria(com.igrandee.core.login.model.Login.class)
				.add(Restrictions.eq( "userid", StringUtils.lowerCase(username)))
				.add(Restrictions.eq( "loginstatus", 'A'))
				.list();

		return loginlist; 
	}
	/**
	 * @author mahalingam_a
	 * @since 14-Aug-2014
	 * @param authorid
	 * @return
	 */
	public List<?> loadAllAuthors(String organizationid){
		List<?> authorsList =null;
		try	{
			authorsList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)	
					
										
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.createAlias("orgperson.personid", "person")
					.createAlias("person.contactinfo", "personcontactinfo")
 					.createAlias("personcontactinfo.emails","personemail") 
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("this.coursestatus", "A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("person.firstName").as("username"))
							.add(Projections.property("person.lastName").as("lastName"))
							.add(Projections.property("personemail.userid").as("emailaddress"))
							.add(Projections.property("organization.organizationid").as("organizationid"))							
							.add(Projections.property("person.personid").as("personid"))
							.add(Projections.count("this.courseid").as("coursecount"))
							.add(Projections.groupProperty("orgperson.orgpersonid").as("orgpersonid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadAllAuthors() in UserViewService "+e);
			}
		}
		return authorsList ;
	}
	/**
	 * @author mahalingam_a
	 * @since 16-Aug-2014
	 * @param authorid
	 * @return
	 */
	public List<?> loadAuthorCourses(String authorid){
		List<?> authorsList =null;
		try	{
			authorsList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)			
				 		.createAlias("this.orgperson", "orgperson")
				 		.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 		.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(authorid)))
				 		.add(Restrictions.eq("this.coursestatus", "A"))
				 		.setProjection(Projections.projectionList()
							.add(Projections.property("this.coursetitle").as("coursetitle"))							
							.add(Projections.property("this.courseid").as("courseid"))	
							.add(Projections.count("courseenrollments.courseenrollmentid").as("studentcount"))
							.add(Projections.groupProperty("this.courseid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
 		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadAuthorCourses() in UserViewService "+e);
			}
		}
		return authorsList	;
	}
	/**
	 * @author mahalingam_a
	 * @since 18-Aug-2014
	 * @param courseid
	 * @return
	 */
	public List<?> loadAuthorCourseStudents(String courseid){
		List<?> studentsList =null;
		try	{
			studentsList  = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.royalty.Royaltypayment.class)	
			 		.createAlias("this.courseenrollmentid", "courseenrollments",JoinType.RIGHT_OUTER_JOIN)
					.createAlias("courseenrollments.course", "course")
					.createAlias("courseenrollments.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person")
					.createAlias("person.contactinfo", "personcontactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.createAlias("personcontactinfo.emails","personemail",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
 					.createAlias("personcontactinfo.phones","phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN) 

					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))		
					.add(Restrictions.in("courseenrollments.courseenrollmentstatus", new String[] {"A","B","C"} ))
					.setProjection(Projections.projectionList()
							.add(Projections.property("course.coursetitle").as("coursetitle"))							
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("person.firstName").as("firstname"))	
							.add(Projections.property("person.middleName").as("middlename"))	
							.add(Projections.property("person.lastName").as("lastname"))	
							.add(Projections.property("personemail.userid").as("emailaddress"))
							.add(Projections.property("phone.number").as("phone"))
							.add(Projections.property("authorroyaltyamount").as("authorroyaltyamount"))
							.add(Projections.property("orgroyaltyamount").as("orgroyaltyamount"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadAuthorCourseStudents() in UserViewService "+e);
			}
		}
		return studentsList 	;
	}
	
	public List<?> courseAuthourUsers(String courseid,String userid){
		List<?> uersList =null;
		try	{
			Order order = Order.desc("emailid");
			uersList = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courseinvitation.class).addOrder(order)					
					.createAlias("this.orgperson", "orgperson")	
					.createAlias("this.course", "course")	
					.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(userid)))
					.add(Restrictions.eq("course.courseid",Integer.parseInt(courseid)))
					.add(Restrictions.eq("this.courseinvitationstatus",'I'))
 					.setProjection(Projections.projectionList()
						.add(Projections.property("this.emailid").as("emailid"))
						.add(Projections.property("this.dateofinvitation").as("registereddate"))							
						).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(UserViewServicelogger.isInfoEnabled()){
				UserViewServicelogger.error("error in loadValidUsers() in UserViewService "+e);
			}
		}
		return uersList	;
	}
	
 	public List<?> getDownloadHistory(String orgpersonid){
 		return getCurrentSession().createCriteria(PresenterappDownloadhistory.class)
  				.createAlias("orgperson", "orgperson")
  				.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)))
 				.setProjection(Projections.projectionList()
					.add(Projections.property("id").as("id")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		
    }	
	
	public List<?> getPresenterappdetails(){
 		return getCurrentSession().createCriteria(Presenterappdetail.class)
   				.add(Restrictions.eq("this.detailstatus",'F'))
 				.setProjection(Projections.projectionList()
					.add(Projections.property("id").as("id")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		 
    }	
 	
}