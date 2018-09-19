package com.igrandee.product.ecloud.service.session;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.users.UserViewService;

/**
 * @author seenivasagan_p
 * Service Method for session variables
 */
@Service
@Scope("prototype")
@Named
public class SessionService extends GenericEntityService<Orgperson, Integer>
{

	private static Logger sessionServicelogger = Logger.getLogger(UserViewService.class);

	@Override
	protected Class<Orgperson> entityClass() 
	{
		return Orgperson.class;
	}
	
	public List<?> getThemeColor(String orgid){
		List<?> themeColorList = getActualThemeColor(orgid);
		if(themeColorList!=null?themeColorList.size()!=0:false) {			
		}
		else{
			int colorid  = setDefaultThemeColor(orgid,"1");
			if(colorid>0){
				themeColorList = getActualThemeColor(orgid);
			} 
		}
		return themeColorList;
	}
	
	public int setDefaultThemeColor(String orgid,String patternid){
		int themeColorList = 0 ;
		try{
			themeColorList=
					getCurrentSession().createSQLQuery("INSERT INTO orgcolorpattern(orgid,patternid,status) " +
							"VALUES ('"+orgid+"','"+patternid+"','A')")
					.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return themeColorList;
	}
	
	private List<?> getActualThemeColor(String orgid){
		List<?> themeColorList = null;
		try{
			themeColorList=
					getCurrentSession().createSQLQuery("SELECT b.patternnumber FROM orgcolorpattern a,colorpattern b " +
							"where a.orgid='"+orgid+"' and a.patternid=b.id and a.status='A' and b.status='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return themeColorList;
	}

	
			
	
	/**
	 * @author seenivasagan_p
	 * Method to get values for Session
	 * 
	 */ 
	public List<?> getLoginDetails(String loginId,String organizationid)
	{	
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(Orgperson.class) 

					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.organizationid","organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("organizationid.contactinfo", "orgcontactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personallocations", "personallocations",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personallocations.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.urls", "url",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.phones", "orgphones",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.emails", "orgemails",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)

					
	 				.add(Restrictions.eq("this.orgpersonstatus",'A'))   
					.add(Restrictions.eq("login.loginid",Integer.parseInt(loginId)))
					.add(Restrictions.eq("organizationid.organizationid", Long.parseLong(organizationid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))
							.add(Projections.property("personid.personid").as("personid"))
							.add(Projections.property("personid.firstName").as("firstname"))
							.add(Projections.property("personid.lastName").as("lastname"))
							.add(Projections.property("email.emailid").as("emailid"))
							.add(Projections.property("phone.number").as("phonenumber"))
							.add(Projections.property("email.userid").as("userid"))
							.add(Projections.property("role.roleid").as("roleid")) 
							.add(Projections.property("role.rolename").as("rolename"))
							.add(Projections.property("organizationid.orgname").as("orgname"))
							.add(Projections.property("organizationid.organizationid").as("organizationid"))
							.add(Projections.property("organizationid.orgstatus").as("orgstatus"))
							.add(Projections.property("organizationid.logopath").as("orglogo"))
							.add(Projections.property("url.url").as("url"))
							.add(Projections.property("orgphones.number").as("orgnumber"))
							.add(Projections.property("orgemails.userid").as("orgemailid")) 
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sessionServicelogger.error("error in SessionService for this getLoginDetails()",e);
 		}
		return Query; 
	}
	
	
	public List<?> getLoginDetails(String loginId)
	{
		List<?> Query = null;
		try{
			Query =  getCurrentSession().createCriteria(Orgperson.class) 

					.createAlias("this.login", "login",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)  
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.organizationid","organizationid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.createAlias("this.personallocations", "personallocations",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personallocations.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					
	 				.add(Restrictions.eq("personid.personstatus",'A'))   
					.add(Restrictions.eq("login.loginid",Integer.parseInt(loginId)))
 					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgpersonid").as("orgpersonid"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sessionServicelogger.error("error in SessionService for this getLoginDetails(loginId)",e);
		}
		return Query; 
	}

	
}
