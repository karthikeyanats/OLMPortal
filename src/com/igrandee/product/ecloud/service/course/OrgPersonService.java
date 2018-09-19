package com.igrandee.product.ecloud.service.course;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Orgperson;

@Service
@Named
@Scope("prototype")
public class OrgPersonService extends GenericEntityService<Orgperson, Integer>
{
	private static Logger OrgPersonServicelogger = Logger.getLogger(OrgPersonService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	protected Class<Orgperson> entityClass() {
		// TODO Auto-generated method stub
		return Orgperson.class;
	}

	public List<?> getLoginandpersonid(String userid) 
	{
		List<?> personlist = null;
		try
		{
			Criteria crit = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.personid", "person")
					.createAlias("this.login", "login")
					.add(Restrictions.eq("login.userid",userid))
					.add(Restrictions.eq("login.loginstatus",'A'));

			personlist = crit.setProjection(Projections.projectionList()
					
					.add(Projections.property("person.personid").as("personid"))
					.add(Projections.property("login.loginid").as("loginid"))
					.add(Projections.property("login.password").as("password"))
					
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			if(OrgPersonServicelogger.isInfoEnabled())
			{
				OrgPersonServicelogger.error("error in getLoginandpersonid() in OrgPersonService ",e);
			}
		}
		return personlist;
	}
	
	public Integer insertOrgperson(String personid,String loginid,String organizationid) 
	{
		try
		{
			Integer personlist=getCurrentSession().createSQLQuery("INSERT INTO orgperson (personid, loginid, " +
					"organizationid, orgpersonstatus) " +
					"VALUES ('"+personid+"','"+loginid+"','"+organizationid+"','A')")
					.executeUpdate();
			if(personlist>0){
				List<?> orgpersonidList =getCurrentSession().createSQLQuery("select distinct orgpersonid from orgperson where " +
						"organizationid='"+organizationid+"' and loginid='"+loginid+"' and personid='"+personid+"'")
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
				String orgpersonid="";
				for(int i=0;i<orgpersonidList.size();i++){
					Map FoldertitleMap=(Map) orgpersonidList.get(i);
					orgpersonid=""+FoldertitleMap.get("orgpersonid");
				}
				return Integer.parseInt(orgpersonid);
			}
			else{
				return null;
			}			
		}		
		catch(Exception e)
		{
			if(OrgPersonServicelogger.isInfoEnabled())
			{
				OrgPersonServicelogger.error("error in insertOrgperson() in OrgPersonService",e);
			}
			return null;
		}
	}
	
	
	/**
	 * @author seenivasagan_p
	 * Method to get userid based on username
	 * @param username
	 * @return
	 */
	public List<?> getLoginCheck(String username,Long organizationid) 
	{
		List<?> query = null;
		try{
			query = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid", "organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("organizationid.organizationid",organizationid))
					.add(Restrictions.eq("login.userid",username))
					.add(Restrictions.eq("login.loginstatus",'A'))
					.setProjection(Projections.projectionList()                             
							.add(Projections.property("login.userid").as("userid"))
							.add(Projections.property("login.loginid").as("loginid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			}
		catch(Exception e)
		{
			if(OrgPersonServicelogger.isInfoEnabled())
			{
				OrgPersonServicelogger.error("error in getLoginCheck() in OrgPersonService",e);
			}
		}
		return query;
	}
	
	public List<?> loginCheckForOrganization(String username,Long organizationid){
		List<?> query = null;
		try{
			query = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid", "organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,
							Restrictions.eq("organizationid.organizationid",organizationid))
					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("login.userid",username))
					.add(Restrictions.eq("login.loginstatus",'A'))
					.setProjection(Projections.projectionList()                             
							.add(Projections.property("login.userid").as("userid"))
							.add(Projections.property("login.loginid").as("loginid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("login.password").as("password"))
							).setResultTransformer(Criteria.PROJECTION).list();
			}
		catch(Exception e)
		{
			if(OrgPersonServicelogger.isInfoEnabled())
			{
				OrgPersonServicelogger.error("error in getLoginCheck() in OrgPersonService",e);
			}
		}
		return query;
	}
	
	public int addOrgForUser(long organizationid,int loginid,long personid){
		int result=0;
		Session session=sessionFactory.openSession();
		Transaction transaction=null;
		try{
	    transaction=session.beginTransaction();
	    
	    //query for orgperson table insertion
		String sql="insert into orgperson(organizationid,loginid,personid,orgpersonstatus) values (:orgid,:loginid,:personid,:orgstatus)";
		Query query=session.createSQLQuery(sql);
		query.setParameter("orgid", organizationid);
		query.setParameter("loginid", loginid);
		query.setParameter("personid", personid);
		query.setParameter("orgstatus", 'A');
		query.executeUpdate();
		result=((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).intValue();
		
		if(result>=0){
	    //query for role table insertion
		sql="insert into role(rolename,rolelevel,rolestatus,organizationid) values (:rolename,:rolelevel,:rolestatus,:organizationid)";
		query=session.createSQLQuery(sql);
		query.setParameter("organizationid", organizationid);
		query.setParameter("rolename", "user");
	    query.setParameter("rolelevel", 0);
		query.setParameter("rolestatus", 'A');
		query.executeUpdate();
		int roleid=((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).intValue();
		
		 //query for department table insertion
		sql="insert into department(departmentname,departmentstatus,organizationid) values(:departmentname,:departmentstatus,:organizationid)";
		query=session.createSQLQuery(sql);
		query.setParameter("organizationid", organizationid);
		query.setParameter("departmentname", "admin");
		query.setParameter("departmentstatus", 'A');
		query.executeUpdate();
		int departmentid=((BigInteger)session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).intValue();
		
		 //query for person allocation table insertion
		sql="insert into personallocation(departmentid,orgpersonid,roleid,personallocationstatus) values (:departmentid,:orgpersonid,:roleid,:personallocationstatus)";
		query=session.createSQLQuery(sql);
		query.setParameter("departmentid", departmentid);
		query.setParameter("orgpersonid", result);
		query.setParameter("roleid", roleid);
		query.setParameter("personallocationstatus", 'A');
		query.executeUpdate();
		transaction.commit();
		}
		else{
			  transaction.rollback();
		}
			
		}
		catch(Exception e){
		   e.printStackTrace();
		   transaction.rollback();
		}
		finally{
			session.close();
		}
		
		return result;
	}
    
	
	public Integer deleteLoginStatus(int loginid){
		Integer deletedPerson = 0;
		try	{
			deletedPerson=getCurrentSession().createQuery("update com.igrandee.core.login.model.Login set loginstatus='D' where loginid='"+loginid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in updateLoginStatus() in OrganizationViewService",e);
			}
		}
		return deletedPerson;
		
	}
	
	

	public Integer deletePersonStatus(int personid){
		Integer deletedPersonid = 0;
		
		try	{
			deletedPersonid=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Orgperson set orgpersonstatus='D' where orgpersonid='"+personid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in deletePersonStatus() in OrganizationViewService",e);
			}
		}
		return deletedPersonid;
		
	}
	
	
	public Integer delLoginStatus(int loginid){
		Integer delPerson = 0;
		try	{
			delPerson=getCurrentSession().createQuery("update com.igrandee.core.login.model.Login set loginstatus='A' where loginid='"+loginid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in updateLoginStatus() in OrganizationViewService",e);
			}
		}
		return delPerson;
	}
	
	
	
	public Integer delPersonStatus(int personid){
		Integer delPersonid = 0;
		
		try	{
			delPersonid=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Orgperson set orgpersonstatus='A' where orgpersonid='"+personid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in delPersonStatus() in OrganizationViewService",e);
			}
		}
		return delPersonid;
	}
	
	public Integer updatePersonStatus(int personid,String firstname){
		Integer updatedPerson = 0;
		try	{
			
			updatedPerson=getCurrentSession().createQuery("update com.igrandee.core.person.model.Person set personstatus='A',firstName='"+firstname+"' where personid='"+personid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in updateOrganizationStatus() in OrganizationViewService",e);
			}
		}
		return updatedPerson;
		
	}
	
	 
	public Integer updateLoginStatus(String emailid,String password,int loginid){
		Integer updatedLogin = 0;
		try	{
			
			updatedLogin=getCurrentSession().createQuery("update com.igrandee.core.login.model.Login set userid='"+emailid+"',password='"+password+"',loginstatus='A' where loginid='"+loginid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in updateLoginStatus() in OrganizationViewService");
			}
		}
		return updatedLogin;
		
	}
	
	public boolean isLoginDeactivated(String username,String orgid){
	Integer id=(Integer)getCurrentSession().createCriteria(Orgperson.class)
		 .createAlias("login", "login")
		 .createAlias("organizationid", "organization")
		  .add(Restrictions.eq("login.userid", username))
		  .add(Restrictions.eq("this.orgpersonstatus", 'D'))
		  .add(Restrictions.eq("organization.organizationid",Long.parseLong(orgid)))
		  .setProjection(Projections.property("login.loginid"))
		  .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	  if(id!=null){
		  return true;
	  }
	  else{
		  return false;
	  }
	}
	
	
	
	/**
	 * @author mano_r
	 * This Method is used to get the user profile value
	 * @param personid
	 * @return
	 */

	public List<?> getUserProfile(int personid) 
	{
		List<?> personlist = null;
		try
		{
			Criteria crit = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.personid", "personid")
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.addresses", "address",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.orgpersonid",personid));	
					/*.add(Restrictions.eq("this.orgpersonstatus", 'A'));*/

			personlist = crit.setProjection(Projections.projectionList()
					.add(Projections.property("this.orgpersonid").as("personid"))
					.add(Projections.property("contactinfo.contactinfoid").as("contactinfoid"))
					.add(Projections.property("personid.firstName").as("firstName"))
					.add(Projections.property("personid.sex").as("sex"))
					.add(Projections.property("personid.dob").as("dob"))
					.add(Projections.property("personid.lastName").as("lastName"))
					.add(Projections.property("personid.middleName").as("middleName"))
					.add(Projections.property("personid.personphotourl").as("personphotourl"))
					.add(Projections.property("address.addressid").as("addressid"))
					.add(Projections.property("address.street1").as("street1"))
					.add(Projections.property("address.city").as("city"))
					.add(Projections.property("address.country").as("country"))
					.add(Projections.property("address.state").as("state"))
					.add(Projections.property("address.zipcode").as("zipcode")) 
					.add(Projections.property("phone.phoneid").as("phoneid"))
					.add(Projections.property("phone.number").as("number"))
					.add(Projections.property("email.emailid").as("emailid"))
					.add(Projections.property("email.userid").as("userid"))
					.add(Projections.property("personid.eduqualification").as("eduqualification"))
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(OrgPersonServicelogger.isInfoEnabled())
			{
				OrgPersonServicelogger.error("error in getUserProfile() in OrgPersonService");
			}
		}
		return personlist;
	}

	 public List<?> getForgetPassword(String username,String organizationid) {
			List<?> personlist = null;
			Criteria crit = getCurrentSession().createCriteria(Orgperson.class)
					
					.createAlias("this.organizationid", "organization")
					.createAlias("this.login", "login")  
					.createAlias("this.personid", "personid")							
					.createAlias("personid.contactinfo", "contactinfo")
					//.createAlias("contactinfo.addresses", "address",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("login.userid",username))
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					
					.add(Restrictions.eq("personid.personstatus", 'A')) 
					.add(Restrictions.eq("contactinfo.contactinfostatus", 'A'))
					.add(Restrictions.eq("login.loginstatus", 'A'))
					.add(Restrictions.eq("phone.phonestatus", 'A')); 
				 
					personlist = crit.setProjection(Projections.projectionList() 
							.add(Projections.property("login.loginid").as("loginid"))
							.add(Projections.property("login.userid").as("username"))
							.add(Projections.property("login.password").as("password"))
							.add(Projections.property("phone.phoneid").as("phoneid"))
							.add(Projections.property("phone.number").as("number")) 
							.add(Projections.property("email.emailid").as("emailid"))
							.add(Projections.property("email.userid").as("userid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			return personlist;
	 }
	 
	 public List<?> emailContent(Long organizationid){
		 List<?> emaillist=null;
		 try {
			 emaillist= getCurrentSession().createCriteria(Orgperson.class)
				 //.createAlias("this.login", "login")
				 .createAlias("this.organizationid", "organizationid")
				 .createAlias("this.personallocations", "personallocations")
				 .createAlias("this.personid", "person")
				 .createAlias("personallocations.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("organizationid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("contactinfo.emails", "email",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .createAlias("contactinfo.urls", "url",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				 .add(Restrictions.eq("role.rolename","admin")) 
				 .add(Restrictions.eq("organizationid.organizationid",organizationid))
				 	.setProjection(Projections.projectionList()
					.add(Projections.property("phone.number").as("number"))
					.add(Projections.property("email.userid").as("emailid"))
					.add(Projections.property("url.url").as("url"))
					.add(Projections.property("person.firstName").as("firstname"))
					.add(Projections.property("person.lastName").as("lastname"))
					.add(Projections.property("organizationid.orgname").as("organizationname")))
					
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();					
		} catch (Exception e) {
			if(OrgPersonServicelogger.isInfoEnabled()){
				OrgPersonServicelogger.error("error in emailContent() in Orgpersonservice");
			}
		}
		 
		return emaillist;
		 
	 }
	 
	 public String getMasterEmail(){
	
		 return (String) getCurrentSession().createCriteria(Orgperson.class)
				.createAlias("personid", "person")
				.createAlias("person.contactinfo", "contactinfo")
				.createAlias("contactinfo.emails", "email")
				.createAlias("personallocations", "personallocation")
				.createAlias("personallocation.role", "role")
				.createAlias("role.organization", "organization")
					.add(Restrictions.eq("organization.orgstatus", 'M'))
						.setProjection(Projections.property("email.userid").as("emailid"))
						.setResultTransformer(Criteria.PROJECTION).uniqueResult();
	 }
}
