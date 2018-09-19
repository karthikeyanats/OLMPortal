package com.igrandee.product.ecloud.service.organization;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Orgplansubscription;

@Named
@Service
@Scope("prototype")
public class OrganizationWiseUsersService extends GenericEntityService<Orgperson, Long> {

	private static Logger Organizationwiseuserslogger = Logger.getLogger(OrganizationWiseUsersService.class);
	@Override
	protected Class<Orgperson> entityClass() {
		// TODO Auto-generated method stub
		return Orgperson.class;
	}

	public List<?> getorganizationwiseuserslist(Long organizationid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid","organizationid")
					.createAlias("this.personid","personid")
					.createAlias("this.login","login")
					.createAlias("organizationid.contactinfo","contactinfo")
					.createAlias("contactinfo.emails","email")
					.add(Restrictions.eq("organizationid.organizationid",organizationid))
					.add(Restrictions.eq("organizationid.orgstatus",'A'))
					.add(Restrictions.eq("this.orgpersonstatus",'A'))
					.add(Restrictions.eq("personid.personstatus",'A'))
					.add(Restrictions.eq("login.loginstatus",'A'))
					.add(Restrictions.eq("contactinfo.contactinfostatus",'A'))
					.add(Restrictions.eq("email.emailstatus",'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("organizationid.organizationid").as("organizationid"))
							.add(Projections.property("organizationid.orgname").as("orgname"))
							.add(Projections.property("email.userid").as("email"))
							.add(Projections.property("login.userid").as("users"))
							.add(Projections.property("personid.firstName").as("firstname")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Organizationwiseuserslogger.isInfoEnabled())	{
				Organizationwiseuserslogger.error("error in getorganizationwiseuserslist() in OrganizationService",e);
			}
		}
		return query;
	}
	
	
	
	public List<?> getAllOrganization() {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Organization.class)
					    .add(Restrictions.eq("this.orgstatus",'A'))
						.setProjection(Projections.projectionList()
					     	.add(Projections.property("this.orgname").as("organizationname"))
							.add(Projections.property("this.organizationid").as("organizationid")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Organizationwiseuserslogger.isInfoEnabled())	{
				Organizationwiseuserslogger.error("error in getAllOrganization() in OrganizationService",e);
			}
		}
		return query;
	}
	
	public List<?> getOrganizationPlandetails(Long organizationid ,String status) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Orgplansubscription.class)
					  .createAlias("this.organization","organization")
					  .createAlias("this.plan","plan")
					  .add(Restrictions.eq("organization.organizationid",organizationid))					  
					  .add(Restrictions.eq("organization.orgstatus",status.charAt(0)))		
 					  .add(Restrictions.or(Restrictions.eq("plan.planstatus",'A'), Restrictions.eq("plan.planstatus",'B')))
					  .add(Restrictions.eq("this.orgplanstatus","A"))
					  .setProjection(Projections.projectionList()							  
							.add(Projections.property("this.dateofsubscription").as("dateofsubscription"))
							.add(Projections.property("this.planstartdate").as("planstartdate"))
							.add(Projections.property("this.planenddate").as("planenddate"))
							.add(Projections.property("plan.id").as("planid"))
							.add(Projections.property("organization.orgname").as("orgname"))
							.add(Projections.property("plan.planname").as("planname"))
							.add(Projections.property("plan.planamount").as("planamount"))
							.add(Projections.property("plan.duration").as("duration"))
							.add(Projections.property("plan.durationtype").as("durationtype"))
							.add(Projections.property("plan.dateofcreation").as("dateofcreation"))
							.add(Projections.property("plan.maxcourse").as("maxcourse"))
							.add(Projections.property("plan.maxusers").as("maxusers")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		}
		catch(Exception e){
			if(Organizationwiseuserslogger.isInfoEnabled())	{
				Organizationwiseuserslogger.error("error in getOrganizationPlandetails() in OrganizationService",e);
			}
		}
		return query;
	}
	
	public List<?> getOrganizationdetail(Long orgid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Organization.class)
					  .add(Restrictions.eq("this.organizationid",orgid))
					  .add(Restrictions.eq("this.orgstatus",'A'))	
					  .setProjection(Projections.projectionList()							  
							.add(Projections.property("this.orgname").as("orgname"))
							.add(Projections.property("this.logopath").as("logopath")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(Organizationwiseuserslogger.isInfoEnabled())	{
				Organizationwiseuserslogger.error("error in getOrganizationdetail() in OrganizationService",e);
			}
		}
		return query;
	}
	
}
