package com.igrandee.product.ecloud.service.organization;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Plan;


@Service
@Named
@Scope("prototype")
public class OrganizationCourseViewService extends GenericEntityService<Coursecategory, Serializable>
{

	private static Logger OrganizationCourseViewServicelogger = Logger.getLogger(OrganizationCourseViewService.class);

	@Override
	protected Class<Coursecategory> entityClass() {
 		return Coursecategory.class;
	}

    public List<?> getAllValidCategories(Long organizationid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Coursecategory.class)
					.createAlias("this.courses", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("course.coursestatus","A"))
					.createAlias("course.orgperson","orgperson")
					.createAlias("orgperson.organizationid","organizationid")
					.add(Restrictions.eq("organizationid.organizationid",organizationid))					
					.setProjection(Projections.projectionList()
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursecategorystatus").as("coursecategorystatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationCourseViewServicelogger.isInfoEnabled())	{
				OrganizationCourseViewServicelogger.error("error in getAllValidCategories() in CourseCategoryService",e);
			}
		}
		return query;
	}
    public List<?> getParicularOrganizationdetails(int orgpersonid) 
	{
		List<?> organizationslist = null;
		try{
			Criteria Query = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid", "organization", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.createAlias("organizationid.contactinfo", "contactinfo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.urls", "url", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)					
					.add(Restrictions.eq("this.orgpersonid",orgpersonid)); 
			organizationslist = Query.setProjection(Projections.projectionList() 
					.add(Projections.property("organization.organizationid").as("organizationid"))
					.add(Projections.property("organization.orgname").as("organizationname"))
					.add(Projections.property("organization.logopath").as("organizationlogo"))
					.add(Projections.property("organization.orgstatus").as("orgstatus"))
					.add(Projections.property("organization.sizeoforg").as("teamsize"))
					.add(Projections.property("organization.domainurl").as("domainurl"))
					.add(Projections.property("contactinfo.contactinfoid").as("contactinfoid"))
					.add(Projections.property("phone.phoneid").as("orgphoneid"))
					.add(Projections.property("phone.number").as("orgphonenumber"))
					.add(Projections.property("email.emailid").as("emailid"))
					.add(Projections.property("email.userid").as("orgemailaddress"))
					.add(Projections.property("url.urlid").as("urlid"))
					.add(Projections.property("url.url").as("orgurl"))
					.add(Projections.property("this.orgpersonid").as("orgpersonid"))
					).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}

		catch(Exception e)
		{
			if(OrganizationCourseViewServicelogger.isInfoEnabled())
			{
				OrganizationCourseViewServicelogger.error("error in getParicularOrganizationdetails() in OrganizationViewService",e);
			}
		}

		return organizationslist;
	}
    
    public Integer updateOrganizationLogo(long orgid,String logopath){
		Integer updatedOrganization = null;
		try	{
			updatedOrganization=getCurrentSession().createQuery("update com.igrandee.core.organization.model.Organization set logopath='"+logopath+"' where organizationid='"+orgid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrganizationCourseViewServicelogger.isInfoEnabled()){
				OrganizationCourseViewServicelogger.error("error in updateOrganizationStatus() in OrganizationViewService",e);
			}
		}
		return updatedOrganization;
	}
    
    public List<?> getPlandetails() {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Plan.class)
					.add(Restrictions.eq("this.planstatus",'B'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("planid"))
							.add(Projections.property("this.planname").as("planname"))
							.add(Projections.property("this.planamount").as("planamount"))
							.add(Projections.property("this.planstatus").as("planstatus"))
							.add(Projections.property("this.duration").as("duration"))
							.add(Projections.property("this.durationtype").as("durationtype"))
							.add(Projections.property("this.dateofcreation").as("dateofcreation"))
							.add(Projections.property("this.maxcourse").as("maxcourse"))
							.add(Projections.property("this.maxusers").as("maxusers")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationCourseViewServicelogger.isInfoEnabled())	{
				OrganizationCourseViewServicelogger.error("error in getPlandetails() in OrganizationCourseViewService",e);
			}
		}
		return query;
	}
    
    
    

}
