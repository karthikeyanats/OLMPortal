package com.igrandee.product.ecloud.service.users;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courseenrollment;
import com.igrandee.product.ecloud.model.Notification;
import com.igrandee.product.ecloud.model.Notificationreceived;
import com.igrandee.product.ecloud.model.Orgperson;

@Service
@Scope("prototype")
public class NotificationService extends GenericEntityService<Notification, Serializable> {

	@Override
	protected Class<Notification> entityClass() { 
		return Notification.class;
	}

	/**
	 * @author mano_r
	 * This method is used to get all the users to send notification.
	 * @return
	 */
	public List<?> getPersonDetailsList(String orgid)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(Orgperson.class) 
					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.createAlias("this.organizationid", "organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personallocations", "personallocations",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personallocations.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("personid.personstatus",'A'))   
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(orgid)))
					.add(Restrictions.ne("role.rolename", "admin"))   
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))
							.add(Projections.property("organizationid.organizationid").as("organizationid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("email.emailid").as("emailid"))
							.add(Projections.property("email.userid").as("userid"))
							.add(Projections.property("role.roleid").as("roleid")) 
							.add(Projections.property("role.rolename").as("rolename")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query; 
	}	

	/**
	 * @author mano_r
	 * This method is used to get the paid user to send notification.
	 * @return
	 */
	public List<?> getPaidUsersPersonDetailsList(String orgid)
	{
		List<?> Query = null;
		try{ 
			Query = getCurrentSession().createSQLQuery("select a.personid as orgpersonid  " +
					"from courseenrollment a, payment b, orgperson d where " +
					"a.courseenrollmentid=b.courseenrollmentid " +
					"and a.personid=d.orgpersonid and d.orgpersonstatus='A' and " +
					"b.paymenttype in ('invoice-email','Online') and a.courseenrollmentstatus in ('A','C','B') " +
					"and d.organizationid='"+orgid+"' group by a.personid").
					setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			/*Query =  getCurrentSession().createCriteria(Courseenrollment.class) 
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.add(Restrictions.eq("this.courseenrollmentstatus","A"))
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(orgid)))
					.setProjection(Projections.projectionList() 
					.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.property("organizationid.organizationid").as("organizationid"))
					.add(Projections.groupProperty("orgperson.orgpersonid").as("orgpersonid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}

	/**
	 * @author mano_r
	 * This method is used to get the unpaid users to send some notification.
	 * @return
	 */
	public List<?> getPendingUsersPersonDetailsList(String Orgid)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(Courseenrollment.class) 
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.add(Restrictions.eq("this.courseenrollmentstatus","P")) 
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(Orgid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("orgperson.orgpersonid").as("orgpersonid"))
							.add(Projections.property("organizationid.organizationid").as("organizationid"))
							.add(Projections.groupProperty("orgperson.orgpersonid").as("orgpersonid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}


	/**
	 * @author mano_r
	 * This method is used to get all the learning users to send some notification. 
	 * @return List
	 */
	public List<?> getLearningUsersList(String orgid)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(Courseenrollment.class) 
					.createAlias("this.orgperson","orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.add(Restrictions.eq("this.courseenrollmentstatus","A"))
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(orgid)))
					.setProjection(Projections.projectionList() 
							.add(Projections.property("organizationid.organizationid").as("organizationid"))
							.add(Projections.groupProperty("orgperson.orgpersonid").as("orgpersonid"))) 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}

	public List<?> getNotificationMessage(int personid)
	{
		List<?> Query = null;
		Criteria query = null;
		try{
			Order personids = Order.desc("notificationreceivedid");
			query =  getCurrentSession().createCriteria(Notificationreceived.class);
			query.addOrder(personids); 
			query   .createAlias("this.notificationid","notificationid")
			.createAlias("this.orgperson","orgperson")
			.createAlias("notificationid.orgperson","orgpersonid")
			.createAlias("orgpersonid.personid","personid")
			.add(Restrictions.eq("orgperson.orgpersonid",personid))
			.add(Restrictions.or(Restrictions.eq("this.reviewedstatus","U" ),(Restrictions.eq("this.reviewedstatus", "R"))))
			.setProjection(Projections.projectionList()
					.add(Projections.property("notificationid.notificationdescription").as("notificationdescription"))
					.add(Projections.property("notificationid.notificationdate").as("notificationdate"))
					.add(Projections.property("personid.firstName").as("fromUserName")) 
					.add(Projections.property("notificationid.notificationid").as("notificationid"))
					.add(Projections.property("notificationid.notificationcategory").as("status"))
					.add(Projections.property("this.reviewedstatus").as("receivedstatus"))
					);
			query.addOrder(Order.desc("notificationid.notificationdate"));
			Query = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}

	public List<?> getNotificationMsg(String notificationid)
	{
		List<?> Query = null;
		Criteria query = null;
		try{
			Order personids = Order.desc("notificationreceivedid");
			query =  getCurrentSession().createCriteria(Notificationreceived.class);
			query.addOrder(personids); 
			query   .createAlias("this.notificationid","notificationid")
			.createAlias("this.orgperson","orgperson")
			.createAlias("this.orgperson.personid","personid") 
			.add(Restrictions.eq("notificationid.notificationid",notificationid))
			.setProjection(Projections.projectionList()
					.add(Projections.property("notificationid.notificationdescription").as("notificationdescription"))
					.add(Projections.property("notificationid.notificationdate").as("notificationdate"))
					.add(Projections.property("personid.firstName").as("fromUserName")) 
					.add(Projections.property("notificationid.notificationid").as("notificationid"))
					.add(Projections.property("notificationid.notificationcategory").as("status"))
					);
			query.addOrder(Order.desc("notificationid.notificationdate"));
			Query = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}
	public List<?> getNotification(String status,long organizationid)
	{
		List<?> Query = null;
		Criteria query = null;
		try{
			//Order personid = Order.desc("orgpersonid");
			Order timeStampOrder = Order.desc("notificationdate");
			query =  getCurrentSession().createCriteria(Notification.class);
			//query.addOrder(personid);
			query  .createAlias("this.notificationreceived","notificationreceived")
			.createAlias("notificationreceived.orgperson","orgperson")
			.createAlias("orgperson.organizationid","organizationid")
			.add(Restrictions.eq("this.notificationcategory",status))
			.add(Restrictions.eq("this.notificationstatus","A"))
			.add(Restrictions.eq("organizationid.organizationid",organizationid))
			.setProjection(Projections.projectionList()		
					.add(Projections.count("orgperson.orgpersonid").as("orgpersonid"))
					.add(Projections.groupProperty("this.notificationid").as("notificationid"))
					.add(Projections.property("this.notificationdescription").as("notificationdescription"))
					.add(Projections.property("this.notificationdate").as("notificationdate"))
					.add(Projections.property("this.notificationid").as("notificationid"))); 
			query.addOrder(timeStampOrder);
			Query = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}

	/**
	 * @author muniyarasu_a
	 * @param organizationid
	 * This method is used to get admin orgpersonid. 
	 * @return List
	 */
	public List<?> getOrgpersonid(String orgid)
	{
		List<?> Query = null;
		try{
			Query =   getCurrentSession().createSQLQuery("select c.orgpersonid as orgpersonid from role a , organization b, orgperson c where a.organizationid="+orgid+" and rolename='admin' and c.organizationid=b.organizationid group by a.organizationid") 
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}


	public int getNotificationReadMessage(String orgid)
	{
		int Query = 0;
		try{
			Query =   getCurrentSession().createSQLQuery("update notificationreceived set reviewedstatus='R' where toorgpersonid="+orgid+" ").executeUpdate();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Query;
	}

}

