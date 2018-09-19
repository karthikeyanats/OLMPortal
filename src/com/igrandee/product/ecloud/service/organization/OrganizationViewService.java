package com.igrandee.product.ecloud.service.organization;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.login.model.Login;
import com.igrandee.core.organization.model.Organization;
import com.igrandee.product.ecloud.model.Courseinvitation;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Orgplancourse;

@Service
@Named
@Scope("prototype")
public class OrganizationViewService extends GenericEntityService<Organization, Serializable>
{

	private static Logger OrganizationViewServicelogger = Logger.getLogger(OrganizationViewService.class);

	@Override
	protected Class<Organization> entityClass() {
		// TODO Auto-generated method stub
		return Organization.class;
	}

 	
	public List getTrashOrganizations(){		
		List results=null;
		String qry = null;
		try{
		//qry ="select a.organizationid,orgname,planname,maxusers,maxcourse,d.orgpersonid,e.personid,c.id as id,(select count(*) from course ax, orgperson bx where ax.personid=bx.orgpersonid and bx.personid=e.personid )as noofcourses,(select count(*) from orgperson ax1,personallocation bx1,role cx1 where ax1.orgpersonstatus='A' and ax1.organizationid=a.organizationid and ax1.orgpersonid=bx1.orgpersonid and bx1.roleid=cx1.roleid and cx1.rolename='user' and cx1.rolestatus='A' and bx1.personallocationstatus='A')as noofusers from organization a, plan b,orgplansubscription c,orgperson d,person e where b.planstatus in ('A','B') and c.orgplanstatus='A' and c.orgid=a.organizationid and b.id=c.planid and d.organizationid=a.organizationid and d.personid=e.personid and a.orgstatus='T' GROUP BY a.organizationid order by a.orgname asc";
		  qry ="select count(distinct course4_.courseid) as noofcourses, " +
					"count(courseenro5_.courseenrollmentid) as noofusers, orgplansub1_.id as id, organizati2_.organizationid as organizationid, " +
					"organizati2_.orgname as orgname, plan3_.id as planid, plan3_.planname as planname, plan3_.maxcourse as maxcourse, " +
					"plan3_.maxusers as maxusers from orgplancourse this_ left outer join course course4_ on this_.courseid=course4_.courseid and " +
					"( course4_.coursestatus='A' ) left outer join courseenrollment courseenro5_ on course4_.courseid=courseenro5_.courseid and " +
					"( courseenro5_.courseenrollmentstatus in ('A', 'C') ) right outer join orgplansubscription orgplansub1_ on " +
					"this_.orgplansubscriptionid=orgplansub1_.id inner join organization organizati2_ on " +
					"orgplansub1_.orgid=organizati2_.organizationid inner join plan plan3_ on orgplansub1_.planid=plan3_.id and plan3_.planstatus in ('A','B') where " +
					"organizati2_.orgstatus='T' and orgplansub1_.orgplanstatus ='A' group by orgplansub1_.id order by organizati2_.organizationid desc";
		results=getCurrentSession().createSQLQuery(qry).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getTrashOrganizations() in OrganizationViewService",e);
			}
		}
		return results;
	}
	
	public List<?> getMasterOrgid(){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Organization.class)
    					.add(Restrictions.eq("this.orgstatus",'M'))
  					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.organizationid").as("organizationid")))
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
 			
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled())	{
				OrganizationViewServicelogger.error("error in getMasterOrgid() in OrganizationViewService ",e);
			}
		}
		return query;
	}
	public Integer updateOrganizationStatus(long orgid,String orgstatus){
		Integer updatedOrganization = null;
		try	{
			
			Date date=new Date(System.currentTimeMillis());
			updatedOrganization=getCurrentSession().createQuery("update com.igrandee.core.organization.model.Organization set orgstatus='"+orgstatus+"', createdon='"+date+"'  where organizationid='"+orgid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in updateOrganizationStatus() in OrganizationViewService",e);
			}
		}
		return updatedOrganization;
		
	}
	public Integer deleteOrganizationStatus(long orgid,String orgstatus){
		Integer updatedOrganization = null;
		try	{
			
			Date date=new Date(System.currentTimeMillis());
			updatedOrganization=getCurrentSession().createQuery("update com.igrandee.core.organization.model.Organization set orgstatus='"+orgstatus+"', createdon='"+date+"'  where organizationid='"+orgid+"'").executeUpdate();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in deleteOrganizationStatus() in OrganizationViewService",e);
			}
		}
		return updatedOrganization;
		
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
	/**
	 * @author williambenjamin_g
	 * Method to get coursedetails of particular organization
	 * @return particularcourselist
	 */	
	public List<?> getParticularCourse(Long organizationid){
		
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("this.prices", "prices")
 					.createAlias("this.coursecategory", "coursecategory") 
					.createAlias("this.coursesections", "coursesections")
					.createAlias("coursesections.courselectures", "courselectures")
					.createAlias("courselectures.lecturecontents", "lecturecontents") 
					.add(Restrictions.eq("this.coursestatus","A"))
					.add(Restrictions.eq("organizationid.organizationid",organizationid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("organizationid.orgname").as("orgname"))
							.add(Projections.property("coursecategory.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursetitle").as("coursetitle"))
							.add(Projections.property("this.courseid").as("courseid"))
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursesubtitle").as("coursesubtitle"))
							.add(Projections.property("this.coursedescription").as("coursedescription"))
							.add(Projections.property("this.courselogo").as("courselogo"))
							.add(Projections.property("coursesections.coursesectiontitle").as("coursesectiontitle"))
							.add(Projections.property("lecturecontents.contentpath").as("contentpath"))							
							.add(Projections.property("prices.price").as("price"))
							.add(Projections.property("prices.pricestatus").as("pricestatus"))) 
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getParticularCourse() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	/**
	 * Course Analysis for Sub organization 
	 * @author venkatraman_v
	 * 
	 */
	public List getcourseAnalysisList(int personid){		
		List AnalysisList=null;
		String qry = null;
		try{
		qry ="select a.courseid,a.coursetitle ,"+
		      "(select count(*) from courseenrollment b where b.courseenrollmentstatus='A' and a.courseid=b.courseid and a.coursestatus='A' GROUP BY a.courseid) as learning,"+
		      "(select count(*) from courseenrollment b where b.courseenrollmentstatus='C' and a.courseid=b.courseid and a.coursestatus='A' GROUP BY a.courseid) as completed,"+
		      "(select count(*) from courseenrollment b where b.courseenrollmentstatus='W' and a.courseid=b.courseid and a.coursestatus='A' GROUP BY a.courseid) as wishlist,"+
		      "(select count(*) from courseenrollment b where b.courseenrollmentstatus='B' and a.courseid=b.courseid and a.coursestatus='A' GROUP BY a.courseid) as blocked"+
		      " from course a ,courseenrollment b where a.courseid=b.courseid and a.personid='"+personid+"' GROUP BY courseid";
		AnalysisList=getCurrentSession().createSQLQuery(qry).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();			
		}catch(Exception e){
			e.printStackTrace();
		}
		return AnalysisList;
	}
	
	
	
	/**
	 * @author williambenjamin_g
	 * Method to check suborganizationinfo of users
	 * @return suborguserlist
	 */	
	@SuppressWarnings("unchecked")
	public List<Map<?, ?>> getsubOrganizationInfo(String organizationid,String username,String password){
		
 		List<Map<?, ?>> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Orgperson.class)
					.createAlias("this.organizationid", "organization")
					.createAlias("this.login", "login")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))					
					.add(Restrictions.eq("login.userid",username))
					.add(Restrictions.eq("login.password",password))
					.setProjection(Projections.projectionList()
							.add(Projections.property("organization.orgname").as("orgname"))
							.add(Projections.property("login.userid").as("username"))
							.add(Projections.property("login.loginstatus").as("loginstatus"))
							.add(Projections.property("this.orgpersonstatus").as("orgstatus"))) 
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getsubOrganizationInfo() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	
	/**
	 * @author williambenjamin_g
	 * Method to get the organization and their plans
	 * return organizationlist
	 */
	
	public List getAllOrganizations(String status){		
		List results=null;
		try{
		//qry ="select a.organizationid,orgname,planname,maxusers,maxcourse,d.orgpersonid,e.personid,(select count(*) from course ax, orgperson bx where ax.personid=bx.orgpersonid and bx.personid=e.personid  and ax.coursestatus='A')as noofcourses,(select count(*) from orgperson ax1,personallocation bx1,role cx1 where ax1.orgpersonstatus='A' and ax1.organizationid=a.organizationid and ax1.orgpersonid=bx1.orgpersonid and bx1.roleid=cx1.roleid and cx1.rolename='user' and cx1.rolestatus='A' and bx1.personallocationstatus='A')as noofusers from organization a, plan b,orgplansubscription c,orgperson d,person e where b.planstatus in ('A','B') and c.orgplanstatus='A' and c.orgid=a.organizationid and b.id=c.planid and d.organizationid=a.organizationid and d.personid=e.personid and a.orgstatus='A' GROUP BY a.organizationid order by a.orgname asc";
		/*results=getCurrentSession().createCriteria(Orgplancourse.class)
				.createAlias("orgplansubscription", "orgplansubscription",JoinType.RIGHT_OUTER_JOIN,Restrictions.eq("orgplansubscription.orgplanstatus","A"))	
				.createAlias("orgplansubscription.organization", "organization")
				.createAlias("orgplansubscription.plan", "plan")
				.createAlias("course", "course",JoinType.LEFT_OUTER_JOIN,Restrictions.eq("course.coursestatus", "A"))
				.createAlias("course.courseenrollments", "courseenrollments",JoinType.LEFT_OUTER_JOIN,Restrictions.in("courseenrollments.courseenrollmentstatus", new String[]{"A","C"}))
				.add(Restrictions.eq("organization.orgstatus", 'A'))
				.add(Restrictions.eq("plan.planstatus", 'A'))
				.addOrder(Order.desc("organization.organizationid"))
				.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("orgplansubscription.id"))
					.add(Projections.count("course.courseid").as("noofcourses"))
					.add(Projections.count("courseenrollments.courseenrollmentid").as("noofusers"))
					.add(Projections.property("orgplansubscription.id").as("id"))
					.add(Projections.property("organization.organizationid").as("organizationid"))
					.add(Projections.property("organization.orgname").as("orgname"))
					.add(Projections.property("plan.id").as("planid"))
					.add(Projections.property("plan.planname").as("planname"))					
					.add(Projections.property("plan.maxcourse").as("maxcourse"))
					.add(Projections.property("plan.maxusers").as("maxusers")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	*/
			results=getCurrentSession().createSQLQuery("select count(distinct course4_.courseid) as noofcourses, " +
					/*"count(courseenro5_.courseenrollmentid) as noofusers, */"orgplansub1_.id as id, organizati2_.organizationid as organizationid, " +
					"organizati2_.orgname as orgname, plan3_.id as planid, plan3_.planname as planname, plan3_.maxcourse as maxcourse, " +
					"plan3_.maxusers as maxusers from orgplancourse this_ left outer join course course4_ on this_.courseid=course4_.courseid " +
					/* and ( course4_.coursestatus='A' )*/"left outer join courseenrollment courseenro5_ on course4_.courseid=courseenro5_.courseid and " +
					"( courseenro5_.courseenrollmentstatus in ('A', 'C','B','P') ) right outer join orgplansubscription orgplansub1_ on " +
					"this_.orgplansubscriptionid=orgplansub1_.id inner join organization organizati2_ on " +
					"orgplansub1_.orgid=organizati2_.organizationid inner join plan plan3_ on orgplansub1_.planid=plan3_.id and plan3_.planstatus in ('A','B') where " +
					"organizati2_.orgstatus='"+status+"' and orgplansub1_.orgplanstatus ='A' group by orgplansub1_.id order by organizati2_.organizationid desc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getAllOrganizations() in OrganizationViewService",e);
			}
		}
		return results;
	}
	
/**
 * @author sridhar_m
 * Method to get organization details
 * return organizationlist
 */
	
	public List<?>getOrganizationDetail(String organizationid,String status){
		
		List<?> query=null;
		
		try{
			query = getCurrentSession().createCriteria(Orgperson.class)
					.createAlias("this.organizationid", "organization",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("organizationid.contactinfo", "orgcontactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("this.personid", "personid",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					
					.createAlias("personid.contactinfo", "contactinfo",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.phones", "phone",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("contactinfo.emails", "email", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("personallocations.role", "role",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.urls", "url",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.phones", "orgphones",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("orgcontactinfo.emails", "orgemails",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					
					.add(Restrictions.eq("organization.orgstatus",status.charAt(0)))
					.add(Restrictions.eq("organization.organizationid", Long.parseLong(organizationid)))
					.setProjection(Projections.projectionList()
					.add(Projections.property("organization.organizationid").as("organizationid"))
					.add(Projections.property("organization.orgname").as("orgname"))
					.add(Projections.property("organization.orgstatus").as("orgstatus"))
					.add(Projections.property("organization.logopath").as("logopath"))
					.add(Projections.property("organization.domainurl").as("domainurl"))
					.add(Projections.property("email.userid").as("userid"))					
					.add(Projections.property("phone.number").as("number"))
					.add(Projections.property("url.url").as("orgurl"))
					.add(Projections.property("orgphones.number").as("orgphonenumber"))
					.add(Projections.property("orgemails.userid").as("orgemailid"))
					.add(Projections.groupProperty("organization.organizationid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
		}catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getOrganizationDetail() in OrganizationViewService",e);
			}
		}
		return query;
		
	}
	
	
	/**
	 * @author williambenjamin_g
	 * Method to get coursedetails of particular organization
	 * @return organizationnamelist
	 */	
	
	public List<?> getOrganizationname(Long organizationid){
		
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.core.organization.model.Organization.class)
 					.add(Restrictions.eq("this.organizationid",organizationid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgname").as("orgname"))		
							.add(Projections.property("this.logopath").as("logopath")))
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getOrganizationname() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	/** 
	 * @author williambenjamin_g
	 * This method is used to check the user in organization name
	 * @return userlist 
	 */
	public List<?> getOrganizationUser(long organizationid,String email){		
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Courseinvitation.class)
					.createAlias("this.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organizationid")
 					.add(Restrictions.eq("organizationid.organizationid",organizationid))
					.add(Restrictions.eq("this.emailid",email))		
					.add(Restrictions.ne("courseinvitationstatus", 'F'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.emailid").as("username")))	 
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		}
		catch(Exception e){
			e.printStackTrace();
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getOrganizationUser() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	/** 
	 * @author williambenjamin_g
	 * This method is used to check the user for authour
	 * @return userlist 
	 */
	public List<?> checkEmailAuthor(String email,int courseid){
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Courseinvitation.class)
					.createAlias("this.course", "course")
					.add(Restrictions.eq("course.courseid", courseid))
					.add(Restrictions.eq("this.emailid",email))
					.add(Restrictions.ne("courseinvitationstatus", 'F'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.emailid").as("username")))	
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in checkEmailAuthor() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	
	/** 
	 * @author muniyarasu_a
	 * This method is used to check the organization name
	 * @return organizationnamelist 
	 */
	public List<?> getOrganizationlist(String orgname){
		
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.core.organization.model.Organization.class)
					.add(Restrictions.eq("this.orgname",orgname))
					.add(Restrictions.eq("this.orgstatus",'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.orgname").as("orgname")))		
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getOrganizationlist() in OrganizationViewService",e);
			}
		}
		return query;
	}
	
	/** 
	 * @author williambenjamin_g
	 * This method is used to check the organization email
	 * @return organizationnamelist 
	 */
	public List<?> getEmailList(String email){
		
 		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Login.class)
					.add(Restrictions.eq("this.userid",email))
					.add(Restrictions.eq("this.loginstatus",'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.userid").as("email")))		
 							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getEmailList() in OrganizationViewService",e);
			}
		}
		return query;
	}
	public List<?> getCourseDetails(int courseid){
		
		List<?> query =null;
		try	{
		query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
				.createAlias("this.courseenrollments", "courseenrollments",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.in("courseenrollments.courseenrollmentstatus", new String[]{"A","B","C"}))
				.createAlias("this.prices", "prices",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
				.createAlias("this.orgperson", "orgperson")
				.createAlias("orgperson.personid", "author")					
				.add(Restrictions.eq("this.courseid",courseid))
				.setProjection(Projections.projectionList()
					.add(Projections.property("this.courseid").as("courseid"))
					.add(Projections.property("this.coursetitle").as("coursetitle"))
					.add(Projections.property("this.coursedescription").as("coursedescription"))
					.add(Projections.property("this.courselogo").as("courselogo"))
					.add(Projections.property("courseenrollments.courseenrollmentid").as("courseenrollmentid"))
					.add(Projections.count("courseenrollments.courseenrollmentid" ).as("personidcount")) 
					.add(Projections.property("prices.priceid").as("priceid"))
					.add(Projections.property("prices.price").as("price"))
					.add(Projections.property("author.firstName").as("firstName"))
					.add(Projections.property("author.lastName").as("lastName")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getCourseDetails() in OrganizationViewService",e);
			}
		}				
		return query;
	}
	
	public List<?> getAuthourlogin(String email,String organizationid){
		List<?> query =null;
		try	{
		query = getCurrentSession().createCriteria(Login.class)
				.add(Restrictions.eq("this.userid",email))
				.add(Restrictions.eq("this.loginstatus",'A'))
				.setProjection(Projections.projectionList()
					.add(Projections.property("this.userid").as("userid"))
					.add(Projections.property("this.password").as("password")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getAuthourlogin() in OrganizationViewService",e);
			}
		}			
		return query;
	}
	
	
	public List<?> getstatuswiseusers(int courseid,String status,long organizationid){
		List<?> query =null;
		try{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Course.class)
					.createAlias("this.orgperson", "orgperson") 
					.createAlias("this.courseenrollments", "courseenrollments")
					.createAlias("orgperson.organizationid", "organizationid")
					.createAlias("courseenrollments.orgperson", "person")
					.createAlias("person.personid", "persons")
					.createAlias("persons.contactinfo", "contactmail")
					.createAlias("contactmail.emails", "email")
					.add(Restrictions.eq("this.courseid", courseid))
					.add(Restrictions.eq("courseenrollments.courseenrollmentstatus", status))					
					.setProjection(Projections.projectionList()
					.add(Projections.property("persons.firstName").as("usersname"))
					.add(Projections.property("persons.lastName").as("lastName"))
					.add(Projections.property("courseenrollments.courseprogress").as("courseprogress"))
					.add(Projections.property("email.userid").as("usermail")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
					
		}catch(Exception e){
			e.printStackTrace();
		}
		return query;
	}
	 
	/**
	 * @author muniyarasu_a
	 * Method to get the organization max users count
	 * return getmaxuser
	 */
	
	public List getmaxuser(String status){		
		List results=null;
		try{
			results=getCurrentSession().createSQLQuery("select count(a.orgplansubscriptionid) as noofusers, organizati2_.organizationid as organizationid, " +
										" orgplansub1_.id as id from orgplanuser a right outer join orgplansubscription orgplansub1_ on " +
										" a.orgplansubscriptionid=orgplansub1_.id inner join organization organizati2_ on orgplansub1_.orgid=organizati2_.organizationid " +
										" inner join plan plan3_ on orgplansub1_.planid=plan3_.id and plan3_.planstatus in ('A','B') " +
										" where organizati2_.orgstatus='"+status+"' and orgplansub1_.orgplanstatus ='A' group by orgplansub1_.id order by organizati2_.organizationid desc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getmaxuser() in OrganizationViewService",e);
			}
		}
		return results;
	}
	
	/**
	 * @author muniyarasu_a
	 * Method to get the organization max users count
	 * return getmaxuser
	 */
	
	public List getmaxusers(){		
		List results=null;
		try{
			results=getCurrentSession().createSQLQuery("select count(a.orgplansubscriptionid) as noofusers, organizati2_.organizationid as organizationid, " +
										" orgplansub1_.id as id from orgplanuser a right outer join orgplansubscription orgplansub1_ on " +
										" a.orgplansubscriptionid=orgplansub1_.id inner join organization organizati2_ on orgplansub1_.orgid=organizati2_.organizationid " +
										" inner join plan plan3_ on orgplansub1_.planid=plan3_.id and plan3_.planstatus in ('A','B') " +
										" where organizati2_.orgstatus='A' and orgplansub1_.orgplanstatus in ('A','T') group by orgplansub1_.id order by organizati2_.organizationid desc")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}catch(Exception e){
			e.printStackTrace();
			if(OrganizationViewServicelogger.isInfoEnabled()){
				OrganizationViewServicelogger.error("error in getmaxuser() in OrganizationViewService",e);
			}
		}
		return results;
	}
}
